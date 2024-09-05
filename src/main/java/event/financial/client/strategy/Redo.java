/*
 * User: gfrosti
 * Date: Aug 11, 2003
 * Time: 4:59:57 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package event.financial.client.strategy;

import codetable.ReinsuranceCheckDocument;
import codetable.component.CodeTableComponent;
import contract.Deposits;
import contract.Segment;
import contract.dm.dao.DAOFactory;
import edit.common.CodeTableWrapper;
import edit.common.EDITMap;
import edit.common.exceptions.EDITEventException;
import edit.common.vo.DepositsVO;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.GroupSetupVO;
import edit.common.vo.InvestmentVO;
import edit.common.vo.RedoDocVO;
import edit.common.vo.ReinsuranceCheckDocVO;
import edit.common.vo.SegmentVO;


import engine.UnitValues;
import engine.business.Calculator;
import engine.component.CalculatorComponent;
import engine.sp.SPException;
import engine.sp.SPOutput;


import engine.sp.custom.document.GroupSetupDocument;
import engine.sp.custom.document.PRASEDocBuilder;
import event.EDITTrx;
import event.financial.client.trx.ClientTrx;
import fission.utility.*;

import java.util.List;



import org.dom4j.*;



public class Redo extends ClientStrategy
{
    public Redo(ClientTrx clientTrx)
    {
        super(clientTrx);
    }

    public Redo(ClientTrx clientTrx, String sortStatus)
    {
        super(clientTrx);
        super.setSortStatus(sortStatus);
    }

    public ClientStrategy[] execute() throws EDITEventException
    {
       ClientStrategy[] clientStrategy = null;

       EDITTrxVO editTrxVO = super.getClientTrx().getEDITTrxVO();

//        if (editTrxVO.getReapplyEDITTrxFK() == 0)
//        {
//            editTrxVO.setReapplyEDITTrxFK(editTrxVO.getEDITTrxPK());
//            editTrxVO.setEDITTrxPK(0);
//            editTrxVO.setStatus("A");
//            editTrxVO.setPendingStatus("P");
//        }
//
//        super.getClientTrx().save(); // Persists, even should the RedoTrx ultimately fail.


        //shutdown redoDOC/create the groupSetupDOC with history/load the doc with the SP
         Object praseDocument = null;
        long newReapplyEditTrxPK = editTrxVO.getEDITTrxPK();
        long oldUndoneEditTrxPK = editTrxVO.getReapplyEDITTrxFK();
        EDITMap editMap = new EDITMap(GroupSetupDocument.BUILDING_PARAMETER_NAME_EDITTRXPK, newReapplyEditTrxPK + "");
        editMap.put(GroupSetupDocument.BUILDING_PARAMETER_NAME_INCLUDEHISTORYIND,"Y");
        editMap.put(GroupSetupDocument.PARAMETER_NAME_OLDREAPPLYEDITTRPK,oldUndoneEditTrxPK + "");

        praseDocument = new GroupSetupDocument(editMap);
        ((PRASEDocBuilder) praseDocument).build();

        //GroupSetupDocument resetGroupsetupDoc = ((GroupSetupDocument) praseDocument).resetPknFkEditTrxHistoriesForREDO((GroupSetupDocument)praseDocument, newReapplyEditTrxPK, oldUndoneEditTrxPK);


        //RedoDocVO redoDocVO = buildRedoDocVO(); // Based on CloudLand Histories not yet committed to DB

        String transactionType = editTrxVO.getTransactionTypeCT();
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
        String name = codeTableWrapper.getCodeDescByCodeTableNameAndCode("TRXTYPE", transactionType);
        if (name == null)
         {
             name = codeTableWrapper.getCodeDescByCodeTableNameAndCode("DEATHTRXTYPE", transactionType);
         }

        String trxStatusInd = editTrxVO.getStatus();
        String trxEffDate = editTrxVO.getEffectiveDate();

        Segment segmentDoc = ((GroupSetupDocument) praseDocument).getGroupSetup().getContractSetup().getSegment();
        SegmentVO segmentVO = (SegmentVO)segmentDoc.getVO();

        // Since we are not updating the deposits in undo process, do not touch deposits in the redo process.
        // discussed with Rob. - SP 5/31/2007
        // if we update the deposits the Deposits.AmountReceived is being set to Deposits.AnticipatedAmount which is zero.
//        updateDepositsForPremiumTrxs(editTrxVO);

        // The EDITTrxFK of Deposits need to be updated with reapplied EDITTrxPK. Otherwise deposits will be hanging off of the Undone transactions.
        updateDepositsForPremiumTrxs(editTrxVO);
        
        // Added the transactionType BCDA to the if statement - B.Phillips 11/17/2014

        if ((segmentVO.getSegmentStatusCT().equalsIgnoreCase("IssuePendingPremium") &&
             (transactionType.equalsIgnoreCase("IS") || transactionType.equalsIgnoreCase("PY") ||
              transactionType.equalsIgnoreCase("BC") || transactionType.equalsIgnoreCase("BCDA"))) ||
            (!segmentVO.getSegmentStatusCT().equalsIgnoreCase("IssuePendingPremium")) ||
             transactionType.equalsIgnoreCase("SB") ||
             transactionType.equalsIgnoreCase("ADC") ||
             transactionType.equalsIgnoreCase("MI") ||
             transactionType.equalsIgnoreCase("MV") ||
             transactionType.equalsIgnoreCase("CC"))
        {
            String pendingStatus = editTrxVO.getPendingStatus();

            if (transactionType.equalsIgnoreCase("MF") ||
                transactionType.equalsIgnoreCase("TU"))
            {
                InvestmentVO[] allInvestmentVOs = segmentVO.getInvestmentVO();

                try
                {
                    boolean missing =
                            UnitValues.areUnitValuesMissingForInvestmentsWithChargeCodes(allInvestmentVOs,
                                                                                         editTrxVO.getEffectiveDate());
                    // if we are missing some of the unit values, then it is M
                    // and will wait, otherwise it is P and will execute now.
                    pendingStatus = missing ? "M" : "P";
                }
                catch(Exception e)
                {
                    throw new RuntimeException("Problem evaluating pending status", e);
                }
            }

            if (!pendingStatus.equalsIgnoreCase("M"))
            {
                Segment segment = new Segment(segmentVO);
                String eventType = segment.setEventTypeForDriverScript();

                long productKey = segmentVO.getProductStructureFK();

                Calculator calcComponent = new CalculatorComponent();

                SPOutput spOutput = null;

                //System.out.println("praseDocument into prase:" + ((PRASEDocBuilder)praseDocument).asXML());//??remove
                try
                {
                    if (praseDocument instanceof GroupSetupDocument)
                    {
                        spOutput = calcComponent.processScriptWithDocument((PRASEDocBuilder)praseDocument, name, trxStatusInd, eventType, trxEffDate, productKey, true);
                    }
                    //spOutput = calcComponent.processScript("RedoDocVO", redoDocVO, name, trxStatusInd, eventType, trxEffDate, companyKey, true);
                }
                catch (SPException e)
                {
                	EDITEventException editEventException = new EDITEventException(e.getMessage());
                	
                	if (!e.isLogged()) 
                	{
	                    System.out.println(e);
	
	                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	                    
	                    e.setLogged(true);
                	}
                	
                	if (e.isLogged())
                	{
    	                editEventException.setLogged(true);
                	}

                    throw editEventException;
                }

                if (spOutput.hasCalculationOutputs())
                {
                    RedoSave redoSave = new RedoSave();
                    NaturalSave naturalRedoSave = new NaturalSave();
                    GroupSetupVO groupSetupVO = getGroupSetupVO(praseDocument);


                    //System.out.println("spoutput after prase:" + Util.marshalVO(spOutput.getSPOutputVO()));//??remove


                    updateSPOUTPUTforHistoryElements(spOutput);


                    //System.out.println("spoutput after prase & update history elem:" + Util.marshalVO(spOutput.getSPOutputVO()));//??remove


                    try
                    {
                        //clientStrategy = naturalRedoSave.doUpdates(spOutput, editTrxVO, super.getClientTrx().getCycleDate(), groupSetupVO, segmentVO, super.getClientTrx().getExecutionMode());
                        clientStrategy = redoSave.doUpdates(spOutput, groupSetupVO, praseDocument, editTrxVO, super.getClientTrx().getCycleDate(), productKey);
                    }
                    catch (EDITEventException e)
                    {
                        throw new EDITEventException(e.getMessage());
                    }
                }
            }
            else
            {
                editTrxVO.setPendingStatus(pendingStatus);

                try
                {
                    new EDITTrx(editTrxVO).saveNonRecursively();
                }
                catch (Exception e)
                {
                    throw new RuntimeException("Problem Saving " + editTrxVO.getTransactionTypeCT() + " Transaction", e);
                }
            }
        }

        return clientStrategy;
    }
/**
     *manupulate spOutput to add history elements, BA may not have touched it at all in the script
     * Add history elements to the spOutput so that those gets saved for the new reapply record
     * @param spOutput
     */
    private void updateSPOUTPUTforHistoryElements(SPOutput spOutput)
    {
        Document document = spOutput.getDocument("GroupSetupDocVO");
        List eDITTrxHistoryElements = DOMUtil.getElements("GroupSetupDocVO.GroupSetupVO.ContractSetupVO.ClientSetupVO.EDITTrxVO.EDITTrxHistoryVO", document);

        for (int i = 0; i < eDITTrxHistoryElements.size(); i++)
            {
                Element editTrxHistoryElement = (Element)eDITTrxHistoryElements.get(i);
                if (!checkSPOUTPUThasTheVOorNOT(spOutput, "EDITTrxHistoryVO"))
                    {
                    spOutput.addCalculationOutput(editTrxHistoryElement);
                    }

                if (!checkSPOUTPUThasTheVOorNOT(spOutput, "ReinsuranceHistoryVO"))
                    {
                    List reinsuranceHistElements = DOMUtil.getChildren("ReinsuranceHistoryVO", editTrxHistoryElement);

                    for (int j = 0; j < reinsuranceHistElements.size(); j++)
                            {

                        spOutput.addCalculationOutput((Element)reinsuranceHistElements.get(j));
                            }
                    }

                if (!checkSPOUTPUThasTheVOorNOT(spOutput, "ChargeHistoryVO"))
                    {
                    List  chargeHistoryElements = DOMUtil.getChildren("ChargeHistoryVO", editTrxHistoryElement);

                    for (int j = 0; j < chargeHistoryElements.size(); j++)
                            {
                        spOutput.addCalculationOutput((Element)chargeHistoryElements.get(j));

                            }
                    }

                if (!checkSPOUTPUThasTheVOorNOT(spOutput, "FinancialHistoryVO"))
                    {
                    List  financialHistoryElements = DOMUtil.getChildren("FinancialHistoryVO", editTrxHistoryElement);

                    for (int j = 0; j < financialHistoryElements.size(); j++)
                            {
                        spOutput.addCalculationOutput((Element)financialHistoryElements.get(j));
                            }
                    }

                if (!checkSPOUTPUThasTheVOorNOT(spOutput, "CommissionHistoryVO"))
                    {
                    List  commissionHistoryElements = DOMUtil.getChildren("CommissionHistoryVO", editTrxHistoryElement);

                    for (int j = 0; j < commissionHistoryElements.size(); j++)
                            {
                        spOutput.addCalculationOutput((Element)commissionHistoryElements.get(j));
                            }
                    }

//                if (!checkSPOUTPUThasTheVOorNOT(spOutput, "SegmentHistoryVO"))
//                    {
//                List  segmentHistoryElements = DOMUtil.getChildren("SegmentHistoryVO", editTrxHistoryElement);
//
//                for (int j = 0; j < segmentHistoryElements.size(); j++)
//                            {
//                    spOutput.addCalculationOutput((Element)segmentHistoryElements.get(j));
//                            }
//                    }

                if (!checkSPOUTPUThasTheVOorNOT(spOutput, "InSuspenseVO"))
                    {
                    List  inSuspenseElements = DOMUtil.getChildren("InSuspenseVO", editTrxHistoryElement);

                    for (int j = 0; j < inSuspenseElements.size(); j++)
                            {
                        spOutput.addCalculationOutput((Element)inSuspenseElements.get(j));
                            }
                    }

                if (!checkSPOUTPUThasTheVOorNOT(spOutput, "WithholdingHistoryVO"))
                    {
                    List  withholdingHistoryElements = DOMUtil.getChildren("WithholdingHistoryVO", editTrxHistoryElement);

                    for (int j = 0; j < withholdingHistoryElements.size(); j++)
                            {
                        spOutput.addCalculationOutput((Element)withholdingHistoryElements.get(j));
                            }
                    }
            }
    }

    private RedoDocVO buildRedoDocVO()
    {
        CodeTableComponent codeTableComponent = new CodeTableComponent();

        RedoDocVO redoDocVO = codeTableComponent.buildRedoDocument(super.getClientTrx().getEDITTrxVO());

        return redoDocVO;
    }

    /**
     * Updates the deposits for premium transactions.
     *
     * @param editTrxVO         EDITTrx that determines whether this is a premium trx or not
     */
    private void updateDepositsForPremiumTrxs(EDITTrxVO editTrxVO)
    {
        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("PY"))
        {
            // Find the deposits for the original editTrx (the "undone" trx)
            DepositsVO[] depositsVOs = DAOFactory.getDepositsDAO().findDepositsByEDITTrxPK(editTrxVO.getReapplyEDITTrxFK());

            if (depositsVOs != null)
            {
                for (int i = 0; i < depositsVOs.length; i++)
                {
                    Deposits deposit = new Deposits(depositsVOs[i]);

                    deposit.setEDITTrxFK(new Long(editTrxVO.getEDITTrxPK()));

                    deposit.save();     // CRUD save
                }
            }
        }
    }

    private GroupSetupVO getGroupSetupVO(Object praseDocument)
    {
        GroupSetupVO groupSetupVO = null;

        if (praseDocument instanceof ReinsuranceCheckDocument)
            {
                ReinsuranceCheckDocument reinsuranceCheckDocument = (ReinsuranceCheckDocument)praseDocument;

                ReinsuranceCheckDocVO reinsuranceCheckDocVO =
                    (ReinsuranceCheckDocVO)reinsuranceCheckDocument.getDocumentAsVO();

                groupSetupVO = reinsuranceCheckDocVO.getGroupSetupVO(0);
            }
        else if (praseDocument instanceof GroupSetupDocument)
            {
                groupSetupVO = ((GroupSetupDocument)praseDocument).getAsGroupSetupVODocument(true);
            }

        return groupSetupVO;
    }

    private boolean checkSPOUTPUThasTheVOorNOT(SPOutput spOutput, String objectType)
    {
        List<Element> spouputElements = spOutput.getCalculationOutputs();
        for (Element elem: spouputElements)
            {
                if (elem.getName().equalsIgnoreCase(objectType))
                    {
                        return true;
                    }
            }

        return false;
    }

}

