/*
 * User: dlataill
 * Date: Aug 5, 2004
 * Time: 2:13:36 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package codetable;

import contract.*;

import edit.common.*;
import edit.common.exceptions.EDITEventException;

import edit.common.vo.*;

import event.*;

import event.dm.dao.*;

import event.financial.client.trx.*;

import org.dom4j.*;

import org.dom4j.tree.*;

import java.util.*;

import client.*;


public class TrxProcessDocforPerformance extends PRASEDocument
{
    private ClientTrx clientTrx;
    private EDITTrx editTrx;
    private EDITTrxVO editTrxVO;
    private Element naturalDocElement;
    private GroupSetup groupSetup;
    private ContractSetup contractSetup = null;
    private Segment segment;
    private Document document;
    private Element amount;

    public TrxProcessDocforPerformance(ClientTrx clientTrx)
    {
        super();
        this.clientTrx = clientTrx;
        this.editTrxVO = clientTrx.getEDITTrxVO();
    }

     public TrxProcessDocforPerformance(ClientTrx clientTrx, EDITTrx editTrx)
    {
        super();
        this.clientTrx = clientTrx;
        this.editTrxVO = clientTrx.getEDITTrxVO();
        this.editTrx = editTrx;
        this.contractSetup = editTrx.getClientSetup().getContractSetup();
    }

    public TrxProcessDocforPerformance(EDITTrxVO editTrxVO)
    {
        super();
        this.editTrxVO = editTrxVO;
    }

    /**
     * Build NaturalDoc for Transaction Process
     */
    public void buildDocument() throws EDITEventException
    {
        try
        {

            document = new DefaultDocument();

            naturalDocElement = new DefaultElement("NaturalDocVO");

            Long editTrxPK = new Long(editTrxVO.getEDITTrxPK());

            PRASEDocResult segmentElement = PRASEDocHelper.buildSegment(editTrxPK, contractSetup);
            segment = (Segment) segmentElement.getEntity();

            Element baseSegment = new DefaultElement("BaseSegmentVO");
            baseSegment.add(segmentElement.getElement());

            PRASEDocResult groupTrxElement = PRASEDocHelper.buildGroupSetup(editTrxPK);
            groupSetup = (GroupSetup) groupTrxElement.getEntity();

            naturalDocElement.add(groupTrxElement.getElement());

            PRASEDocResult clientElement = PRASEDocHelper.buildClient(editTrxPK, PRASEDocHelper.FINANCIAL_TRX);
            baseSegment.add(clientElement.getElement());

            String trxType = editTrxVO.getTransactionTypeCT();
            if (trxType.equalsIgnoreCase("LS") || trxType.equalsIgnoreCase("SLS"))
            {
                Element deathInformation = getDeathInfoFromClient(segment.getSegmentPK().longValue());
                naturalDocElement.add(deathInformation);;
            }

//            if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase("MF"))
//            {
//                List investmentArrays = PRASEDocHelper.buildInvestmentArray(segment, new Long(editTrxVO.getEDITTrxPK()), "MF");
//
//                for (int i = 0; i < investmentArrays.size(); i++)
//                {
//                    Element investmentArrayElement = (Element) investmentArrays.get(i);
//
//                    naturalDocElement.add(investmentArrayElement);
//                }
//            }

            checkForTransactionAccumGeneration();

            naturalDocElement.add(baseSegment);

            document.setRootElement(naturalDocElement);

            //            StringWriter writer = new StringWriter();
            //
            //            SessionHelper.prettyPrint(naturalDocElement, writer);
            //
            //            System.out.println(writer.toString());
            //            super.buildDocument();      //todo - getinvestments, etc using overrides
            //            super.associateBillingInfo(); //todo - for 'POR' role type ignore
            //            super.setGroupInfo();  //todo - from master            ignore
            //            segmentVO = checkForServicingAgent(segmentVO); //todo - put of base build?
            //            if (clientTrx != null && clientTrx.hasReinsurance())
            //            {
            //                associateReinsuranceInformation();    //todo - built at time of basesegment? ignore
            //            }
            //            super.composeOverdueChargesRemaining(); //todo - ignore
        }
        catch (EDITEventException e)
        {
            if (e.getErrorNumber() == EDITEventException.CONSTANT_NO_DATA_FOUND)
            {
                // Potentially at this point of time, identified that the transaction could error when ClientAddress is not found.
                EDITEventException eventException = new EDITEventException("Transaction Errored - Client Address Not Found");

                eventException.setErrorNumber(e.getErrorNumber());

                throw eventException;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace(); //To change body of catch statement use Options | File Templates.

            throw new RuntimeException(e);
        }
    }

    /**
     * Transactions that are reinsurable have [possibly] Treaties mapped to the Segment as ContractTreaties. The
     * Reinsurance information is driven by these ContractTreaties.
     */
    public void associateReinsuranceInformation()
    {
        // This is assumed to be the ReinsuranceDocument at the base Segment level only. Riders would have to be added
        // in the future.
        //        BaseSegmentVO baseSegmentVO = naturalDocVO.getBaseSegmentVO();
        //
        //        Segment segment = new Segment(baseSegmentVO.getSegmentVO());
        //
        //        ContractTreaty[] contractTreaties = segment.getContractTreaty();
        //
        //        if (contractTreaties != null)
        //        {
        //            for (int i = 0; i < contractTreaties.length; i++)
        //            {
        //                ContractTreaty contractTreaty = contractTreaties[i];
        //
        //                ReinsuranceDocument reinsuranceDocument = PRASEDocumentFactory.getSingleton().getReinsuranceDocument(contractTreaty);
        //
        //                reinsuranceDocument.buildDocument();
        //
        //                ReinsuranceVO reinsuranceVO = (ReinsuranceVO) reinsuranceDocument.getDocumentAsVO();
        //
        //                baseSegmentVO.addReinsuranceVO(reinsuranceVO);
        //            }
        //        }
    }

    private SegmentVO checkForServicingAgent(SegmentVO segmentVO)
    {
//        AgentHierarchyVO[] agentHierarchyVOs = segmentVO.getAgentHierarchyVO();
//        segmentVO.removeAllAgentHierarchyVO();
//
//        for (int i = 0; i < agentHierarchyVOs.length; i++)
//        {
//            if ((agentHierarchyVOs[i].getServicingAgentIndicator() != null) && agentHierarchyVOs[i].getServicingAgentIndicator().equalsIgnoreCase("Y"))
//            {
//                segmentVO.addAgentHierarchyVO(agentHierarchyVOs[i]);
//            }
//        }
//
//        if (segmentVO.getAgentHierarchyVOCount() == 0)
//        {
//            segmentVO.setAgentHierarchyVO(agentHierarchyVOs);
//        }

        return segmentVO;
    }

    /**
     * Set the Inclusion List for the EDITTrx Composer.
     */
    public void checkForTransactionAccumGeneration()
    {
        String trxType = editTrxVO.getTransactionTypeCT();

        HashMap trxAccums = new HashMap();

        trxAccums = getTransactionAccumFields(trxType, segment);
        setAccumsInDocument(trxAccums);
    }

    /**
      * For certain transactions accumulate values from the EDITTrx as specified.  The fields
      * not calculated for a certain transaction are set to zero.
      * @param trxType
      * @param editTrxVO
      * @param segmentVO
      * @return
      */
    public HashMap getTransactionAccumFields(String trxType, Segment segment)
    {
        HashMap trxAccums = initAccumMap();

        TransactionAccumsFastDAO transAccumFastDAO = new TransactionAccumsFastDAO();
        EDITBigDecimal premToDate = null;

        try
        {
            //PremiumToDate is needed for all transactions
            premToDate = transAccumFastDAO.accumPY_PremiumToDate(segment.getPK(), editTrxVO.getEffectiveDate());
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }

        trxAccums.put("PremiumToDate", (premToDate.getBigDecimal()));

        if (trxType.equalsIgnoreCase("PY"))
        {
            getPYFields(segment, trxAccums);
        }
        else if (trxType.equalsIgnoreCase("CC"))
        {
            getCC_FD_Fields(segment, trxAccums);
        }
        else if (trxType.equalsIgnoreCase("FD"))
        {
            getCC_FD_Fields(segment, trxAccums);
        }
        else if (trxType.equalsIgnoreCase("WI"))
        {
            getWIFields(segment, trxAccums);
        }
        else if (trxType.equalsIgnoreCase("TF"))
        {
            getTFTrxFields(segment, trxAccums);
        }
        else if (trxType.equalsIgnoreCase("RC"))
        {
            getRC_RWTrxFields(segment, trxAccums);
        }
        else if (trxType.equalsIgnoreCase("RW"))
        {
            getRC_RWTrxFields(segment, trxAccums);
        }

        return trxAccums;
    }

    private HashMap initAccumMap()
    {
        HashMap trxAccums = new HashMap();

        trxAccums.put("PremiumToDate", (new EDITBigDecimal("0").getBigDecimal()));
        trxAccums.put("PremiumYearToDate", (new EDITBigDecimal("0").getBigDecimal()));
        trxAccums.put("NetWithDYearToDate", (new EDITBigDecimal("0").getBigDecimal()));
        trxAccums.put("WithDToDate", (new EDITBigDecimal("0").getBigDecimal()));
        trxAccums.put("CumInitialPrem", (new EDITBigDecimal("0").getBigDecimal()));
        trxAccums.put("Cum1035Prem", (new EDITBigDecimal("0").getBigDecimal()));
        trxAccums.put("PremSinceLast7Pay", (new EDITBigDecimal("0").getBigDecimal()));
        trxAccums.put("WithDSinceLast7Pay", (new EDITBigDecimal("0").getBigDecimal()));
        trxAccums.put("NumberWithDToDate", (new Integer(0)));
        trxAccums.put("NumberTransfersToDate", (new Integer(0)));
        trxAccums.put("PremiumCalYearToDate", (new EDITBigDecimal("0").getBigDecimal()));
        trxAccums.put("WithDTaxYearToDate", (new EDITBigDecimal("0").getBigDecimal()));
        trxAccums.put("RMDTaxYearToDate", (new EDITBigDecimal("0").getBigDecimal()));
        trxAccums.put("AccumulatedValue", (new EDITBigDecimal("0").getBigDecimal()));

        return trxAccums;
    }

    /**
      * Get the accums for the PY trx Natural or Redo
      * @param editTrxVO
      * @param segmentVO
      * @return
      */
    private void getPYFields(Segment segment, HashMap trxAccums)
    {
        EDITDate lastAnnivDate = segment.getLastAnniversaryDate();
        EDITDate effectiveDate = new EDITDate(editTrxVO.getEffectiveDate());
        EDITDate beginningOfYear = new EDITDate(effectiveDate.getFormattedYear(), "01", "01");
        long segmentPK = segment.getPK();

        TransactionAccumsFastDAO transAccumFastDAO = new TransactionAccumsFastDAO();

        try
        {
            EDITBigDecimal withdToDate = transAccumFastDAO.accumWI_WithdrawalsToDate(segmentPK, effectiveDate.getFormattedDate());
            EDITBigDecimal withdYearToDate = transAccumFastDAO.accumWI_WithdrawalsToDateForDateRange(segmentPK, lastAnnivDate.getFormattedDate(), effectiveDate.getFormattedDate());
            EDITBigDecimal cumIntialPrem = transAccumFastDAO.accumPY_PremiumTypeAndDate(segmentPK, effectiveDate.getFormattedDate(), "Issue");
            EDITBigDecimal exchangePrem = transAccumFastDAO.accumPY_PremiumTypeAndDate(segmentPK, effectiveDate.getFormattedDate(), "1035Exchange");
            EDITBigDecimal premPolYearToDate = transAccumFastDAO.accumPY_PremiumToDateForDateRange(segmentPK, lastAnnivDate.getFormattedDate(), effectiveDate.getFormattedDate());
            EDITBigDecimal premCalYearToDate = transAccumFastDAO.accumPY_PremiumToDateForDateRange(segmentPK, beginningOfYear.getFormattedDate(), effectiveDate.getFormattedDate());

            trxAccums.put("PremiumYearToDate", (premPolYearToDate.getBigDecimal()));
            trxAccums.put("PremiumCalYearToDate", (premCalYearToDate.getBigDecimal()));
            trxAccums.put("NetWithDYearToDate", (withdYearToDate.getBigDecimal()));
            trxAccums.put("WithDToDate", (withdToDate.getBigDecimal()));
            trxAccums.put("CumInitialPrem", (cumIntialPrem.getBigDecimal()));
            trxAccums.put("Cum1035Prem", (exchangePrem.getBigDecimal()));
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }
    }

    /**
     * Get the accums for the CC or FD trx,  Natural or Redo
     * @param editTrxVO
     * @param segmentVO
     * @return
     */
    private void getCC_FD_Fields(Segment segment, HashMap trxAccums)
    {
        EDITDate lastAnnivDate = segment.getLastAnniversaryDate();
        EDITDate effectiveDate = new EDITDate(editTrxVO.getEffectiveDate());
        long segmentPK = segment.getPK();
        EDITDate tamraStartDate = segment.getLife().getTamraStartDate();

        TransactionAccumsFastDAO transAccumFastDAO = new TransactionAccumsFastDAO();

        try
        {
            EDITBigDecimal withdToDate = transAccumFastDAO.accumWI_WithdrawalsToDate(segmentPK, effectiveDate.getFormattedDate());
            EDITBigDecimal withdYearToDate = transAccumFastDAO.accumWI_WithdrawalsToDateForDateRange(segmentPK, lastAnnivDate.getFormattedDate(), effectiveDate.getFormattedDate());
            EDITBigDecimal cumIntialPrem = transAccumFastDAO.accumPY_PremiumTypeAndDate(segmentPK, effectiveDate.getFormattedDate(), "Issue");
            EDITBigDecimal exchangePrem = transAccumFastDAO.accumPY_PremiumTypeAndDate(segmentPK, effectiveDate.getFormattedDate(), "1035Exchange");
            EDITBigDecimal premPolYearToDate = transAccumFastDAO.accumPY_PremiumToDateForDateRange(segmentPK, lastAnnivDate.getFormattedDate(), effectiveDate.getFormattedDate());

            trxAccums.put("PremiumYearToDate", (premPolYearToDate.getBigDecimal()));
            trxAccums.put("NetWithDYearToDate", (withdYearToDate.getBigDecimal()));
            trxAccums.put("WithDToDate", (withdToDate.getBigDecimal()));
            trxAccums.put("CumInitialPrem", (cumIntialPrem.getBigDecimal()));
            trxAccums.put("Cum1035Prem", (exchangePrem.getBigDecimal()));

            if (tamraStartDate != null)
            {
                EDITBigDecimal premSinceLast7Pay = transAccumFastDAO.accumPY_PremiumToDateForDateRange(segmentPK, tamraStartDate.getFormattedDate(), effectiveDate.getFormattedDate());
                EDITBigDecimal withdSinceLast7Pay = transAccumFastDAO.accumWI_WithdrawalsToDateForDateRange(segmentPK, tamraStartDate.getFormattedDate(), effectiveDate.getFormattedDate());
                trxAccums.put("PremSinceLast7Pay", (premSinceLast7Pay.getBigDecimal()));
                trxAccums.put("WithDSinceLast7Pay", (withdSinceLast7Pay.getBigDecimal()));
            }
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }
    }

    /**
     * Get the accums for the WI trx Natural or Redo
     * @param editTrxVO
     * @param segmentVO
     * @return
     */
    private void getWIFields(Segment segment, HashMap trxAccums)
    {
        EDITDate lastAnnivDate = segment.getLastAnniversaryDate();
        EDITDate effectiveDate = new EDITDate(editTrxVO.getEffectiveDate());
        long segmentPK = segment.getPK();

        TransactionAccumsFastDAO transAccumFastDAO = new TransactionAccumsFastDAO();

        try
        {
            EDITBigDecimal withdToDate = transAccumFastDAO.accumWI_WithdrawalsToDate(segmentPK, effectiveDate.getFormattedDate());
            EDITBigDecimal withdYearToDate = transAccumFastDAO.accumWI_WithdrawalsToDateForDateRange(segmentPK, lastAnnivDate.getFormattedDate(), effectiveDate.getFormattedDate());

            int withdTrxCount = transAccumFastDAO.trxCountForDateRange(segmentPK, lastAnnivDate.getFormattedDate(), effectiveDate.getFormattedDate(), "WI");

            trxAccums.put("NetWithDYearToDate", (withdYearToDate.getBigDecimal()));
            trxAccums.put("WithDToDate", (withdToDate.getBigDecimal()));
            trxAccums.put("NumberWithDToDate", (new Integer(withdTrxCount)));
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }
    }

    /**
     * Get the accums for the TF trx Natural or Redo
     * @param editTrxVO
     * @param segmentVO
     * @return
     */
    private void getTFTrxFields(Segment segment, HashMap trxAccums)
    {
        EDITDate lastAnnivDate = segment.getLastAnniversaryDate();
        EDITDate effectiveDate = new EDITDate(editTrxVO.getEffectiveDate());
        long segmentPK = segment.getPK();

        TransactionAccumsFastDAO transAccumFastDAO = new TransactionAccumsFastDAO();

        try
        {
            int transferTrxCount = transAccumFastDAO.trxCountForDateRange(segmentPK, lastAnnivDate.getFormattedDate(), effectiveDate.getFormattedDate(), "TF");

            trxAccums.put("NumberTransfersToDate", (new Integer(transferTrxCount)));
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }
    }

    private void getRC_RWTrxFields(Segment segment, HashMap trxAccums)
    {
        try
        {
            int taxYear = editTrxVO.getTaxYear();
            long segmentPK = segment.getPK();
            EDITBigDecimal withdrawalsTaxYearToDate = new FastDAO().getWithdrawalsTaxYearToDate(taxYear, segmentPK);
            EDITBigDecimal rmdsTaxYearToDate = new FastDAO().getRmdsTaxYearToDate(taxYear, segmentPK);
            EDITBigDecimal accumulatedValue = new FastDAO().getPriorCYAccumulatedValue(segmentPK);

            trxAccums.put("WithDTaxYearToDate", withdrawalsTaxYearToDate.getBigDecimal());
            trxAccums.put("RMDTaxYearToDate", rmdsTaxYearToDate.getBigDecimal());
            trxAccums.put("AccumulatedValue", accumulatedValue.getBigDecimal());
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }
    }

    /**
       * For the Accumulations performed now update the Natural Doc.  If the fields were null, zeros were
       * set into its value.
       * @param trxAccums
       * @param trxType
       */
    private void setAccumsInDocument(HashMap trxAccums)
    {
        amount = new DefaultElement("PremiumToDate");
        amount.setText(trxAccums.get("PremiumToDate").toString());
        naturalDocElement.add(amount);

        amount = new DefaultElement("PremiumYearToDate");
        amount.setText(trxAccums.get("PremiumYearToDate").toString());
        naturalDocElement.add(amount);

        amount = new DefaultElement("PremiumCalYearToDate");
        amount.setText(trxAccums.get("PremiumCalYearToDate").toString());
        naturalDocElement.add(amount);

        amount = new DefaultElement("NetWithdrawalsToDate");
        amount.setText(trxAccums.get("WithDToDate").toString());
        naturalDocElement.add(amount);

        amount = new DefaultElement("NetWithdrawalsYearToDate");
        amount.setText(trxAccums.get("NetWithDYearToDate").toString());
        naturalDocElement.add(amount);

        amount = new DefaultElement("CumInitialPrem");
        amount.setText(trxAccums.get("CumInitialPrem").toString());
        naturalDocElement.add(amount);

        amount = new DefaultElement("Cum1035Prem");
        amount.setText(trxAccums.get("Cum1035Prem").toString());
        naturalDocElement.add(amount);

        amount = new DefaultElement("PremiumSinceLast7PayDate");
        amount.setText(trxAccums.get("PremSinceLast7Pay").toString());
        naturalDocElement.add(amount);

        amount = new DefaultElement("WithdrawalsSinceLast7PayDate");
        amount.setText(trxAccums.get("WithDSinceLast7Pay").toString());
        naturalDocElement.add(amount);

        amount = new DefaultElement("NumberWithdrawalsPolYearToDate");
        amount.setText(trxAccums.get("NumberWithDToDate").toString());
        naturalDocElement.add(amount);

        amount = new DefaultElement("NumberTransfersPolYearToDate");
        amount.setText(trxAccums.get("NumberTransfersToDate").toString());
        naturalDocElement.add(amount);

        amount = new DefaultElement("WithdrawalsTaxYTD");
        amount.setText(trxAccums.get("WithDTaxYearToDate").toString());
        naturalDocElement.add(amount);

        amount = new DefaultElement("RMDTaxYTD");
        amount.setText(trxAccums.get("RMDTaxYearToDate").toString());
        naturalDocElement.add(amount);

        amount = new DefaultElement("PriorCYAccumulatedValue");
        amount.setText(trxAccums.get("AccumulatedValue").toString());
        naturalDocElement.add(amount);
    }

    public Element getDeathInfoFromClient(long segmentPK)
    {
//        ClientDetailVO[] clientDetailVO = client.dm.dao.DAOFactory.getClientDetailDAO().findBy_SegmentPK_RoleType(segmentPK, "OWN");

        ClientDetail clientDetail = null;
        if (segment.getSegmentNameCT().equalsIgnoreCase("Life"))
        {
            clientDetail = ClientDetail.findBy_Segment_RoleType(segment, "Insured");
        }
        else
        {
            clientDetail = ClientDetail.findBy_Segment_RoleType(segment, "OWN");
            if (!clientDetail.isStatusNatural())
            {
                clientDetail = ClientDetail.findBy_Segment_RoleType(segment, "ANN");
            }
        }

        Element deathInformation = new DefaultElement("DeathInformationVO");
        Element columnElement = null;

        if (clientDetail != null)
        {
            columnElement = new DefaultElement("DateOfDeath");
            columnElement.setText(clientDetail.getDateOfDeath().getFormattedDate());
            deathInformation.add(columnElement);

            columnElement = new DefaultElement("NotificationReceivedDate");
            EDITDate notificationReceivedDate = clientDetail.getNotificationReceivedDate();
            if (notificationReceivedDate != null)
            {
                columnElement.setText(notificationReceivedDate.getFormattedDate());
                deathInformation.add(columnElement);
            }

            columnElement = new DefaultElement("ProofOfDeathReceivedDate");
//            String proofOfDeathReceivedDate = clientDetailVO[0].getProofOfDeathReceivedDate();
            EDITDate proofOfDeathReceivedDate = clientDetail.getProofOfDeathReceivedDate();
            if (proofOfDeathReceivedDate != null)
            {
                columnElement.setText(proofOfDeathReceivedDate.getFormattedDate());
                deathInformation.add(columnElement);
            }

            columnElement = new DefaultElement("ResidentStateAtDeathCT");
            String residentStateAtDeath = clientDetail.getResidentStateAtDeathCT();
            if (residentStateAtDeath != null)
            {
                columnElement.setText(residentStateAtDeath);
                deathInformation.add(columnElement);
            }

            columnElement = new DefaultElement("StateOfDeathCT");
            String stateOfDeath = clientDetail.getStateOfDeathCT();
            if (stateOfDeath != null)
            {
                columnElement.setText(stateOfDeath);
                deathInformation.add(columnElement);
            }
        }

        return deathInformation;
    }

    public Document getDocument()
    {
        return document;
    }

    public Segment getSegmentEntity()
    {
        return segment;
    }

    public GroupSetup getGroupSetupEntity()
    {
        return groupSetup;
    }

    public VOObject getDocumentAsVO()
    {
        return null;
    }
}
