/*
 * User: sdorman
 * Date: Oct 17, 2006
 * Time: 8:55:58 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package engine.sp;

import edit.common.*;
import edit.common.vo.*;
import edit.common.exceptions.*;
import event.*;
import event.financial.group.strategy.*;
import event.financial.group.trx.*;
import event.transaction.*;
import contract.*;
import edit.services.db.hibernate.HibernateEntityDifference;
import edit.services.db.hibernate.SessionHelper;
import fission.utility.*;
import org.dom4j.*;
import group.*;

import java.util.*;


/**
 * Instruction to create a transaction. This instruction builds the GroupSetup, ContractSetup, and partial EDITTrx.
 * NOTE: The clientSetup is not created here, it is created during the saveGroupSetup since it has to figure out which
 * kind of client to use based on the trxType
 * <P>
 * Information received from the stack:
 * <P>
 * effectiveDate              effectiveDate of the transaction
 * trxType                    type of transaction
 * trxAmount                  amount of transaction
 * segmentPK                  pk of segment the new transaction will belong to
 * complexChangeTypeCT        complexChangeType to be set on the ContractGroup (may not be set)
 * billSchedulePK             pk of the BillSchedule associated with the desired ContractGroup (may not be set) for which the transaction will be created for all of its segments
 * 
 * <P>
 * Information put on the stack after completion:
 * <P>
 * saveSuccessful       string containing "true" or "false" that denotes whether the transaction was successfully saved or not
 */
public class Createtransaction extends Inst
{
    private final static String DEFAULT_OPERATOR = "System";


    public void compile(ScriptProcessor aScriptProcessor) throws SPException
    {
        sp = aScriptProcessor;  // Save instance of ScriptProcessor

        // Note: No compiling is required for this instruction
    }

    /**
     * Get the information from working storage.  If the Group ContractGroupPK is supplied, create a transaction for
     * all segments in the group.  If not supplied, just create a transaction for the supplied Segment.
     *
     * @param execSP
     *
     * @throws SPException
     */
    public void exec(ScriptProcessor execSP) throws SPException
    {
        sp = execSP;

        //  Remove the items from the data stack
        String effectiveDateString = (String) sp.getWSEntry("EffectiveDate");
        String trxType = (String) sp.getWSEntry("TrxType");
        String trxAmount = (String) sp.getWSEntry("TrxAmount");
        String segmentPKString = (String) sp.getWSEntry("SegmentPK");
        String suspenseFK = (String) sp.getWSEntry("SuspenseFK");
        String originatingTrxFKString = (String) sp.getWSEntry("OriginatingTrxFK");
        String complexChangeTypeCT = (String) sp.getWSEntry("ComplexChangeTypeCT");
        String billSchedulePKString = (String) sp.getWSEntry("BillSchedulePK");
        String nfChange = (String) sp.getWSEntry("NFChange");
        String memoCode = (String) sp.getWSEntry("MemoCode");
        String noCommissionInd = Util.initString((String) sp.getWSEntry("NoCommissionInd"), "N");
        String zeroLoadInd = Util.initString((String) sp.getWSEntry("ZeroLoadInd"), "N");
        Long selectedRiderPK = new Long(0);
        EDITBigDecimal originalAmount = new EDITBigDecimal("0.00");
        if (sp.getWSEntry("OriginalAmount") != null) {
            originalAmount = new EDITBigDecimal(sp.getWSEntry("OriginalAmount"));
        }
        if (sp.getWSEntry("SelectedRiderPK") != null) {
            selectedRiderPK = Long.parseLong(sp.getWSEntry("SelectedRiderPK"));
        }
        //  Convert items from stack to useful objects
        EDITDate effectiveDate = new EDITDate(effectiveDateString);

        EDITBigDecimal amount = new EDITBigDecimal(trxAmount);

        Long originatingTrxFK = (originatingTrxFKString != null)? new Long(originatingTrxFKString):null;

        if (billSchedulePKString != null)
        {
            //  Create the transaction for all the group's segments with an acceptable SegmentStatusCT
            Long billSchedulePK = new Long(billSchedulePKString);

            ContractGroup groupContractGroup = ContractGroup.findBy_BillSchedulePK(billSchedulePK);

            if (groupContractGroup != null)
            {
                List acceptableSegmentStatusCTs = getAcceptableSegmentStatusCTs();

                java.util.Set<Segment> segments = groupContractGroup.getSegments();

                for (Iterator iterator = segments.iterator(); iterator.hasNext();)
                {
                    Segment segment = (Segment) iterator.next();

                    if (shouldCreateTransaction(segment, acceptableSegmentStatusCTs))
                    {
                        createTransaction(segment, amount, trxType, effectiveDate, originatingTrxFK, suspenseFK, 
                        		complexChangeTypeCT, nfChange, trxAmount, memoCode, noCommissionInd, zeroLoadInd, 
                        		selectedRiderPK, originalAmount);
                    }
                }
            }
            else
            {
                Segment[] segment = Segment.findBy_BillScheduleFK(billSchedulePK);
                createTransaction(segment[0], amount, trxType, effectiveDate, originatingTrxFK, suspenseFK, 
                		complexChangeTypeCT, nfChange, trxAmount, memoCode, noCommissionInd, zeroLoadInd, 
                		selectedRiderPK, originalAmount);
            }
        }
        else if (segmentPKString != null)
        {
            //  Create the transaction for this segment only
            Long segmentPK = new Long(segmentPKString);

            Segment segment = Segment.findByPK(segmentPK.longValue());

            createTransaction(segment, amount, trxType, effectiveDate, originatingTrxFK, suspenseFK, 
            		complexChangeTypeCT, nfChange, trxAmount, memoCode, noCommissionInd, zeroLoadInd, 
            		selectedRiderPK, originalAmount);
        }

        // Increment instruction pointer
        sp.incrementInstPtr();
    }

    /**
     * Create the transaction
     *
     * @param segment
     * @param amount
     * @param trxType
     * @param effectiveDate
     * @param originatingTrxFK
     * @param suspenseFK
     * @param complexChangeTypeCT
     * @param nfChange a flag set in SP to dictate that this trx is being created out of the NonFinancial framework
     * @param trxAmount
     * @param memoCode
     * @param noCommissionInd
     * @param zeroLoadInd
     */
    private void createTransaction(Segment segment, EDITBigDecimal amount, String trxType, EDITDate effectiveDate,
                                   Long originatingTrxFK, String suspenseFK, String complexChangeTypeCT, String nfChange, String trxAmount,
                                   String memoCode, String noCommissionInd, String zeroLoadInd, Long selectedRiderPK,
                                   EDITBigDecimal originalAmount)
    {
        //  Build all the necessary objects using GroupSetup as the parent
        GroupSetupVO groupSetupVO = buildSetupObjects(segment, amount, memoCode);

        //  Build a partial editTrx
        EDITTrx editTrx = buildPartialEDITTrx(trxType, effectiveDate, amount, originatingTrxFK, 
        		nfChange, noCommissionInd, zeroLoadInd, selectedRiderPK, originalAmount);

        SaveGroup saveGroup = new SaveGroup(groupSetupVO, editTrx.getAsVO(), trxType, segment.getOptionCodeCT());

        //  If a complexChangeTypeCT exists, set it on the ContractSetup
        if (complexChangeTypeCT != null)
        {
            ContractSetupVO contractSetupVO = groupSetupVO.getContractSetupVO(0);
            
            contractSetupVO.setComplexChangeTypeCT(complexChangeTypeCT);
        }        

        if (participatingInNonFinancialFramework(nfChange))
        {
            try
            {
                //This method that will check for executingRealTime after saving the trx
                new GroupTrx().saveGroupSetup(groupSetupVO, editTrx.getAsVO(), trxType,
                        segment.getOptionCodeCT(), segment.getProductStructureFK().longValue());
            }
            catch (EDITEventException e)
            {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        else 
        {
            try
            {
                if (trxType.equalsIgnoreCase("BCDA"))
                {
                    EDITTrx bcdaTrx = EDITTrx.findByTrxType_EffDate_CCType(segment.getSegmentPK(), effectiveDate, "BCDA", null);
                    if (bcdaTrx == null)
                    {
                        saveGroup.build();
                    }
                    else
                    {
                        bcdaTrx.setTrxAmount(new EDITBigDecimal(trxAmount));
                        bcdaTrx.save();
                    }
                }
                saveGroup.build();
            }
            catch (Exception e)
            {
                System.out.println(e);

                  e.printStackTrace();
            }
//            catch (EDITEventException e)
//            {
//              System.out.println(e);
//
//                e.printStackTrace();
//            }


          if (suspenseFK != null)
          {
              //Create outSuspense for Trx
              OutSuspenseVO outSuspenseVO = createOutSuspenseVO(suspenseFK, amount);
              groupSetupVO.getContractSetupVO(0).addOutSuspenseVO(outSuspenseVO);
          }

          //  Convert groupSetup to xml
          String groupSetupVOAsXML = Util.marshalVO(groupSetupVO);
          String contractSetupVOAsXML = Util.marshalVO(groupSetupVO.getContractSetupVO(0));
          String clientSetupVOAsXML = Util.marshalVO(groupSetupVO.getContractSetupVO(0).getClientSetupVO(0));
          String editTrxVOAsXML = Util.marshalVO(groupSetupVO.getContractSetupVO(0).getClientSetupVO(0).getEDITTrxVO(0));

          InvestmentAllocationOverrideVO[] investmentAllocationOverrideVOs = groupSetupVO.getContractSetupVO(0).getInvestmentAllocationOverrideVO();

          try
          {
              //  Convert the xml to a document and add the root element (GroupSetupVO) to the calculation output
              //  Each VO in trx must be saved separately
              Document groupSetupDocument = XMLUtil.parse(groupSetupVOAsXML);

  //            XMLUtil.printDocumentToSystemOut(groupSetupDocument);

              Element groupSetupElement = groupSetupDocument.getRootElement();

              sp.addCalculationOutput(groupSetupElement);

              Document contractSetupDocument = XMLUtil.parse(contractSetupVOAsXML);
              Element contractSetupElement = contractSetupDocument.getRootElement();
              sp.addCalculationOutput(contractSetupElement);

              if (investmentAllocationOverrideVOs != null)
              {
                  for (int i = 0; i < investmentAllocationOverrideVOs.length; i++)
                  {
                      String investmentAllocationOverrideVOAsXML = Util.marshalVO(investmentAllocationOverrideVOs[i]);
                      Document investmentAllocOvrdDocument = XMLUtil.parse(investmentAllocationOverrideVOAsXML);
                      Element investmentAllocOvrdElement = investmentAllocOvrdDocument.getRootElement();
                      sp.addCalculationOutput(investmentAllocOvrdElement);
                  }
              }

              Document clientSetupDocument = XMLUtil.parse(clientSetupVOAsXML);
              Element clientSetupElement = clientSetupDocument.getRootElement();
              sp.addCalculationOutput(clientSetupElement);

              Document editTrxDocument = XMLUtil.parse(editTrxVOAsXML);
              Element editTrxElement = editTrxDocument.getRootElement();
              sp.addCalculationOutput(editTrxElement);

              if (suspenseFK != null)
              {
                  String outSuspenseVOAsXML = Util.marshalVO(groupSetupVO.getContractSetupVO(0).getOutSuspenseVO(0));
                  Document outSuspenseDocument = XMLUtil.parse(outSuspenseVOAsXML);
                  Element outSuspenseElement = outSuspenseDocument.getRootElement();
                  sp.addCalculationOutput(outSuspenseElement);
              }
          }
          catch (DocumentException e)
          {
            System.out.println(e);

              e.printStackTrace();
          }
        }
    }


    /**
     * Creates the GroupSetup and ContractSetup using the specified values and some defaults
     *
     * @param segment
     * @param amount
     *
     * @return newly created GroupSetup with the newly created ContractSetup attached
     */
    private GroupSetupVO buildSetupObjects(Segment segment, EDITBigDecimal amount, String memoCode)
    {
        GroupSetup groupSetup = initializeGroupSetup(segment, amount, memoCode);

        ContractSetup contractSetup = initializeContractSetup(segment, amount);

        //  Attach contractSetup to groupSetup (do VOs too since it's the VOs that get used downstream)
        GroupSetupVO groupSetupVO = groupSetup.getAsVO();
        groupSetupVO.addContractSetupVO(contractSetup.getAsVO());

        return groupSetupVO;
    }

    /**
     * Creates the GroupSetup object
     *
     * @param segment
     * @param amount
     *
     * @return newly created GroupSetup
     */
    private GroupSetup initializeGroupSetup(Segment segment, EDITBigDecimal amount, String memoCode)
    {
        GroupSetup groupSetup = TransactionProcessor.buildDefaultGroupSetup(segment);

        groupSetup.setGroupAmount(amount);

        groupSetup.setMemoCode(memoCode);

        return groupSetup;
    }

    /**
     * Creates the ContractSetup object
     *
     * @param segment
     * @param amount
     *
     * @return newly created ContractSetup
     */
    private ContractSetup initializeContractSetup(Segment segment, EDITBigDecimal amount)
    {
        ContractSetup contractSetup = TransactionProcessor.buildDefaultContractSetup(segment);

        contractSetup.setSegmentFK(segment.getSegmentPK());
        contractSetup.setPolicyAmount(amount);

        return contractSetup;
    }

    /**
     * Creates an EDITTrx with the specified values and some defaults.  The EDITTrx is not complete at this point,
     * it gets completed during the saveGroupSetup process.
     *
     * @param trxType
     * @param effectiveDate
     * @param trxAmount
     * @param originatingTrxFK
     * @param nfChange
     * @param noCommissionInd
     * @param zeroLoadInd
     *
     * @return partially created EDITTrx
     */
    private EDITTrx buildPartialEDITTrx(String transactionTypeCT, EDITDate effectiveDate, EDITBigDecimal amount, Long originatingTrxFK, 
    		String nfChange, String noCommissionInd, String zeroLoadInd, Long selectedRiderPK, EDITBigDecimal originalAmount)
    {
        int taxYear = effectiveDate.getYear();

        String operator = null;

        // We want to track the user making the change.
        if (participatingInNonFinancialFramework(nfChange))
        {
            operator = (String) SessionHelper.getFromThreadLocal(HibernateEntityDifference.OPERATOR);
        }
        else
        {
            operator = DEFAULT_OPERATOR;
        }

        EDITTrx editTrx = TransactionProcessor.buildDefaultEDITTrx(transactionTypeCT, effectiveDate, taxYear, operator);

        editTrx.setTrxAmount(amount);
        editTrx.setOriginatingTrxFK(originatingTrxFK);
        
        if (noCommissionInd != null && noCommissionInd.equalsIgnoreCase("Y"))
        {
        	editTrx.setNoCommissionInd(noCommissionInd);
        }
        
        if (zeroLoadInd != null) {
        	editTrx.setZeroLoadInd(zeroLoadInd);
        }
        editTrx.setSelectedRiderPK(selectedRiderPK);
        editTrx.setOriginalAmount(originalAmount);

        if (transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_ADDRESS_CHANGE) ||
    		transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_BILLING_CHANGE) ||
    		transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_COMPLEXCHANGE) ||
    		transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_DEATH) ||
    		transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_DEATHPENDING) ||
    		transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_FACEINCREASE) ||
    		transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_ISSUE) ||
    		transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LAPSE_PENDING) ||
    		transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_LOAN_BILL) ||
    		transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MATURITY) ||
    		transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MONTHLYINTEREST) ||
    		transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_MONTHLIVERSARY) ||
    		transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_NOTTAKEN) ||
    		transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_RMDCORRESPONDENCE) ||
    		transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_RIDER_CLAIM) ||
    		transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_STATEMENT) ||
    		transactionTypeCT.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SUBMIT))
        {
            editTrx.setNoAccountingInd("Y");
        }

        return editTrx;
    }

    private OutSuspenseVO createOutSuspenseVO(String suspenseFK, EDITBigDecimal amount)
    {
        OutSuspenseVO outSuspenseVO = new OutSuspenseVO();

        outSuspenseVO.setSuspenseFK(Long.parseLong(suspenseFK));
        outSuspenseVO.setAmount(amount.getBigDecimal());

        return outSuspenseVO;
    }

    /**
     * Finds the list of acceptable SegmentStatusCTs.  Acceptable ones are those that are listed in the CodeTable under
     * CREATEBILLSTATUSCT.  They are "acceptable" because any Segment whose SegmentStatusCT matches any of the codes
     * in CREATEBILLSTATUSCT will have a transaction created.
     * @return
     */
    private List getAcceptableSegmentStatusCTs()
    {
        List codes = new ArrayList();

        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        CodeTableVO[] codeTableVOs = codeTableWrapper.getCodeTableEntries("CREATEBILLSTATUS");

        for (int i = 0; i < codeTableVOs.length; i++)
        {
            CodeTableVO codeTableVO = codeTableVOs[i];

            String code = codeTableVO.getCode();

            codes.add(code);
        }

        return codes;
    }

    /**
     * Determines if the transaction should be created for a group segment or not.
     * The segment must be the base segment and the segment's SegmentStatusCT must
     * match one of the acceptable ones in the codeTable.
     *
     * @param segment
     * @param acceptableSegmentStatusCTs
     *
     * @return  false if segment is not base segment, true if the SegmentStatusCT is an acceptable one, false otherwise
     */
    private boolean shouldCreateTransaction(Segment segment, List acceptableSegmentStatusCTs)
    {
        String segmentStatusCT = segment.getSegmentStatusCT();

        if (segment.getSegmentFK() != null)
        {
            return false;
        }
        else
        {
            if (acceptableSegmentStatusCTs.contains(segmentStatusCT))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
    
    /**
   * True if the nfChange as taken from WS is 'Y'.
   * @param nfChange
   * @return
   */
    private boolean participatingInNonFinancialFramework(String nfChange)
    {
      boolean participatingInNonFinancialFramework = false;
      
      if (nfChange != null)
      {
        participatingInNonFinancialFramework = nfChange.toUpperCase().equals("Y");
      }
      
      return participatingInNonFinancialFramework;
    }
}