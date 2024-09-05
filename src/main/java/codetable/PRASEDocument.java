/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: May 3, 2004
 * Time: 2:08:46 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC. All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential. Any use is
 * subject to the license agreement.
 */
package codetable;

import client.ClientDetail;
import client.dm.composer.ClientDetailComposer;
import contract.*;
import contract.dm.composer.VOComposer;
import edit.common.*;
import edit.common.exceptions.EDITEventException;
import edit.common.vo.*;
import edit.services.db.*;
import event.dm.dao.*;
import event.*;
import fission.utility.*;
import role.dm.dao.DAOFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

import businesscalendar.*;


public abstract class PRASEDocument implements RecursionListener
{
    EDITDate priorGADate = null;
    /**
     * Implementing subclasses are required to implement this method to successfully build a PRASEDocument
     */
    public abstract void buildDocument() throws EDITEventException;

    /**
     * Implementing subclasses are required to implement this method to present a value object reprentation of the PRASEDocument entity
     * @return
     */
    public abstract VOObject getDocumentAsVO();

    public SegmentVO getSegmentVO(long segmentPK, List voInclusionList) throws Exception
    {
        SegmentVO segmentVO = new VOComposer().composeSegmentVO(segmentPK, voInclusionList);

        return segmentVO;
    }

    public InvestmentVO[] getInvestments(long segmentFK, InvestmentAllocationOverrideVO[] invAllocOverrideVO, List voInclusionList, String trxType) throws Exception
    {
        return new VOComposer().composeInvestmentVO(segmentFK, invAllocOverrideVO, voInclusionList, trxType);
    }

    public ClientRoleVO getClientRoleVO(long clientRoleFK) throws Exception
    {
        ClientRoleVO[] clientRoleVOs = DAOFactory.getClientRoleDAO().findByClientRolePK(clientRoleFK, false, new ArrayList());
        ClientRoleVO clientRoleVO = null;

        if (clientRoleVOs != null)
        {
            clientRoleVO = clientRoleVOs[0];
        }

        return clientRoleVO;
    }

    public ClientDetailVO getClientDetailVO(long clientDetailFK) throws Exception
    {
        Vector voInclusionList = new Vector();
        voInclusionList.add(TaxInformationVO.class);
        voInclusionList.add(PreferenceVO.class);

        ClientDetailVO clientDetailVO = new ClientDetailComposer(voInclusionList).compose(clientDetailFK);

        if (clientDetailVO != null)
        {
            ClientAddressVO[] clientAddressVOs = client.dm.dao.DAOFactory.getClientAddressDAO().findByClientDetailPK_AND_AddressTypeCT(clientDetailVO.getClientDetailPK(), "PrimaryAddress", false, new ArrayList());

            if (clientAddressVOs != null)
            {
                for (int i = 0; i < clientAddressVOs.length; i++)
                {
                    if (clientAddressVOs[i].getTerminationDate().equals(EDITDate.DEFAULT_MAX_DATE))
                    {
                        clientDetailVO.addClientAddressVO(clientAddressVOs[i]);
                    }
                }
            }
        }

        return clientDetailVO;
    }

    public ClientRoleVO getAgentClientInfo(long agentFK, Vector voInclusionList) throws Exception
    {
        //get client role for the agent
        AgentVO agentVO = new agent.dm.composer.VOComposer().composeAgentVO(agentFK, voInclusionList);
        ClientRoleVO clientRoleVO = null;

        if (agentVO != null)
        {
            clientRoleVO = (ClientRoleVO) agentVO.getClientRoleVO(0);
        }

        return clientRoleVO;
    }

    public ProductStructureVO getProductStructureVO(long productStructureFK) throws Exception
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        ProductStructureVO[] productStructureVOs = engineLookup.findProductStructureVOByPK(productStructureFK, false, null);
        ProductStructureVO productStructureVO = null;

        if (productStructureVOs != null)
        {
            productStructureVO = productStructureVOs[0];
        }

        return productStructureVO;
    }

    public ClientVO buildInsuredClientVO(SegmentVO segmentVO) throws Exception
    {
        ClientVO clientVO = null;

        List insuredInclusionList = new ArrayList();
        insuredInclusionList.add(ContractClientAllocationVO.class);
        insuredInclusionList.add(ClientRoleVO.class);

        ContractClientVO[] contractClientVOs = new VOComposer().composeContractClientVO(segmentVO.getSegmentPK(), "P", insuredInclusionList);

        if (contractClientVOs != null)
        {
            for (int i = 0; i < contractClientVOs.length; i++)
            {
                ClientRoleVO clientRole = (ClientRoleVO) contractClientVOs[i].getParentVO(ClientRoleVO.class);

                if (clientRole.getRoleTypeCT().equalsIgnoreCase("Insured") ||
                    clientRole.getRoleTypeCT().equalsIgnoreCase("TermInsured"))
                {
                    ClientSetupVO insuredClientSetupVO = new ClientSetupVO();
                    insuredClientSetupVO.setContractClientFK(contractClientVOs[i].getContractClientPK());
                    insuredClientSetupVO.setClientRoleFK(clientRole.getClientRolePK());
                    clientVO = new event.dm.composer.ClientComposer().compose(insuredClientSetupVO);
                }
            }
        }

        return clientVO;
    }

    protected DeathInformationVO getDeathInformationFromClient(long segmentPK, String segmentName)
    {
        ClientDetailVO[] clientDetailVO = new edit.common.vo.ClientDetailVO[0];
        if (segmentName.equalsIgnoreCase("Life"))
        {
            clientDetailVO = client.dm.dao.DAOFactory.getClientDetailDAO().findBy_SegmentPK_RoleType(segmentPK, "Insured");
        }
        else
        {
            clientDetailVO = client.dm.dao.DAOFactory.getClientDetailDAO().findBy_SegmentPK_RoleType(segmentPK, "OWN");
            if (clientDetailVO != null)
            {
                ClientDetail clientDetail = new ClientDetail(clientDetailVO[0]);
                if (!clientDetail.isStatusNatural())
                {
                    clientDetailVO = client.dm.dao.DAOFactory.getClientDetailDAO().findBy_SegmentPK_RoleType(segmentPK, "ANN");
                }
            }
        }
        
        DeathInformationVO deathInformationVO = null;

        if (clientDetailVO != null)
        {
            deathInformationVO = new DeathInformationVO();
            deathInformationVO.setDateOfDeath(clientDetailVO[0].getDateOfDeath());
            deathInformationVO.setNotificationReceivedDate(clientDetailVO[0].getNotificationReceivedDate());
            deathInformationVO.setProofOfDeathReceivedDate(clientDetailVO[0].getProofOfDeathReceivedDate());
            deathInformationVO.setResidentStateAtDeathCT(clientDetailVO[0].getResidentStateAtDeathCT());
            deathInformationVO.setStateOfDeathCT(clientDetailVO[0].getStateOfDeathCT());
        }

        return deathInformationVO;
    }
    /**
     * For certain transactions accumulate values from the EDITTrx as specified.  The fields
     * not calculated for a certain transaction are set to zero.
     * @param trxType
     * @param editTrxVO
     * @param segmentVO
     * @return
     */
    protected HashMap getTransactionAccumFields(String trxType, EDITTrxVO editTrxVO, SegmentVO segmentVO)
    {
        HashMap trxAccums = initAccumMap();

        TransactionAccumsFastDAO transAccumFastDAO = new TransactionAccumsFastDAO();
        EDITBigDecimal premToDate = null;
        EDITBigDecimal premSinceLast7Pay = null;

        try
        {
            String effectiveDate = new EDITDate().getFormattedDate();
            if (editTrxVO != null)
            {
                effectiveDate = editTrxVO.getEffectiveDate();
            }

            //PremiumToDate and PremSinceLast7Pay are needed for all transactions
            premToDate = transAccumFastDAO.accumPY_PremiumToDate(segmentVO.getSegmentPK(), effectiveDate);
            String tamraStartDate = null;
            if (segmentVO.getLifeVOCount() > 0)
            {
                tamraStartDate = segmentVO.getLifeVO(0).getTamraStartDate();
            }

            if (tamraStartDate != null)
            {
                premSinceLast7Pay = transAccumFastDAO.accumPY_PremSinceLast7PayForDateRange(segmentVO.getSegmentPK(), tamraStartDate, effectiveDate);
            }
            else
            {
                premSinceLast7Pay = new EDITBigDecimal();
            }
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }

        trxAccums.put("PremiumToDate", (premToDate.getBigDecimal()));
        trxAccums.put("PremSinceLast7Pay", (premSinceLast7Pay.getBigDecimal()));

        if (trxType == null)
        {
            getAllAccumFields(new EDITDate().getFormattedDate(), segmentVO, trxAccums);
        }
        else if (trxType.equalsIgnoreCase("PY"))
        {
            getPYFields(editTrxVO, segmentVO, trxAccums);
        }
        else if (trxType.equalsIgnoreCase("CC"))
        {
            getCC_FD_Fields(editTrxVO, segmentVO, trxAccums);
        }
        else if (trxType.equalsIgnoreCase("FD"))
        {
            getCC_FD_Fields(editTrxVO, segmentVO, trxAccums);
        }
        else if (trxType.equalsIgnoreCase("WI"))
        {
            getWIFields(editTrxVO, segmentVO, trxAccums);
        }
        else if (trxType.equalsIgnoreCase("TF") ||
                 trxType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_SERIES_TRANSFER))
        {
            getTFTrxFields(editTrxVO, segmentVO, trxAccums);
        }

        return trxAccums;
    }

    /**
     * Get the accums for the PY trx Natural or Redo
     * @param editTrxVO
     * @param segmentVO
     * @return
     */
    private void getPYFields(EDITTrxVO editTrxVO, SegmentVO segmentVO, HashMap trxAccums)
    {
        String lastAnnivDate = segmentVO.getLastAnniversaryDate();
        EDITDate effectiveDate = new EDITDate(editTrxVO.getEffectiveDate());
        EDITDate beginningOfYear = effectiveDate.getStartOfYearDate();
        long segmentPK = segmentVO.getSegmentPK();

        TransactionAccumsFastDAO transAccumFastDAO = new TransactionAccumsFastDAO();

        try
        {
            EDITBigDecimal withdToDate = transAccumFastDAO.accumWI_WithdrawalsToDate(segmentPK, effectiveDate.getFormattedDate());
            EDITBigDecimal withdYearToDate = transAccumFastDAO.accumWI_WithdrawalsToDateForDateRange(segmentPK, lastAnnivDate, effectiveDate.getFormattedDate());
            EDITBigDecimal cumIntialPrem = transAccumFastDAO.accumPY_PremiumTypeAndDate(segmentPK, effectiveDate.getFormattedDate(), "Issue");
            EDITBigDecimal exchangePrem = transAccumFastDAO.accumPY_PremiumTypeAndDate(segmentPK, effectiveDate.getFormattedDate(), "Exchange");
            EDITBigDecimal premPolYearToDate = transAccumFastDAO.accumPY_PremiumToDateForDateRange(segmentPK, lastAnnivDate, effectiveDate.getFormattedDate());
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
    private void getCC_FD_Fields(EDITTrxVO editTrxVO, SegmentVO segmentVO, HashMap trxAccums)
    {
        String lastAnnivDate = segmentVO.getLastAnniversaryDate();
        String effectiveDate = editTrxVO.getEffectiveDate();
        long segmentPK = segmentVO.getSegmentPK();
        String tamraStartDate = null;
        if (segmentVO.getLifeVOCount() > 0)
        {
            tamraStartDate = segmentVO.getLifeVO(0).getTamraStartDate();
        }

        TransactionAccumsFastDAO transAccumFastDAO = new TransactionAccumsFastDAO();

        try
        {
            EDITBigDecimal withdToDate = transAccumFastDAO.accumWI_WithdrawalsToDate(segmentPK, effectiveDate);
            EDITBigDecimal withdYearToDate = transAccumFastDAO.accumWI_WithdrawalsToDateForDateRange(segmentPK, lastAnnivDate, effectiveDate);
            EDITBigDecimal cumIntialPrem = transAccumFastDAO.accumPY_PremiumTypeAndDate(segmentPK, effectiveDate, "Issue");
            EDITBigDecimal exchangePrem = transAccumFastDAO.accumPY_PremiumTypeAndDate(segmentPK, effectiveDate, "Exchange");
            EDITBigDecimal premPolYearToDate = transAccumFastDAO.accumPY_PremiumToDateForDateRange(segmentPK, lastAnnivDate, effectiveDate);

            trxAccums.put("PremiumYearToDate", (premPolYearToDate.getBigDecimal()));
            trxAccums.put("NetWithDYearToDate", (withdYearToDate.getBigDecimal()));
            trxAccums.put("WithDToDate", (withdToDate.getBigDecimal()));
            trxAccums.put("CumInitialPrem", (cumIntialPrem.getBigDecimal()));
            trxAccums.put("Cum1035Prem", (exchangePrem.getBigDecimal()));

            if (tamraStartDate != null)
            {
                EDITBigDecimal withdSinceLast7Pay = transAccumFastDAO.accumWI_WithdrawalsToDateForDateRange(segmentPK, tamraStartDate, effectiveDate);
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
    private void getWIFields(EDITTrxVO editTrxVO, SegmentVO segmentVO, HashMap trxAccums)
    {
        String lastAnnivDate = segmentVO.getLastAnniversaryDate();
        String effectiveDate = editTrxVO.getEffectiveDate();
        long segmentPK = segmentVO.getSegmentPK();

        TransactionAccumsFastDAO transAccumFastDAO = new TransactionAccumsFastDAO();

        try
        {
            EDITBigDecimal withdToDate = transAccumFastDAO.accumWI_WithdrawalsToDate(segmentPK, effectiveDate);
            EDITBigDecimal withdYearToDate = transAccumFastDAO.accumWI_WithdrawalsToDateForDateRange(segmentPK, lastAnnivDate, effectiveDate);

            int withdTrxCount = transAccumFastDAO.trxCountForDateRange(segmentPK, lastAnnivDate, effectiveDate, "WI");

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
    private void getTFTrxFields(EDITTrxVO editTrxVO, SegmentVO segmentVO, HashMap trxAccums)
    {
        String lastAnnivDate = segmentVO.getLastAnniversaryDate();
        String effectiveDate = editTrxVO.getEffectiveDate();
        long segmentPK = segmentVO.getSegmentPK();

        TransactionAccumsFastDAO transAccumFastDAO = new TransactionAccumsFastDAO();

        try
        {
            int transferTrxCount = transAccumFastDAO.trxCountForDateRange(segmentPK, lastAnnivDate, effectiveDate, "TF");
            int stfTrxCount = transAccumFastDAO.trxCountForDateRange(segmentPK, lastAnnivDate, effectiveDate, "STF");

            trxAccums.put("NumberTransfersToDate", (new Integer(transferTrxCount + stfTrxCount)));
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }
    }

    private void getAllAccumFields(String currentDate, SegmentVO segmentVO, HashMap trxAccums)
    {
        EDITDate editCurrentDate = new EDITDate();

        EDITDate beginningOfYear = editCurrentDate.getStartOfYearDate();

        String lastAnnivDate = segmentVO.getLastAnniversaryDate();
        long segmentPK = segmentVO.getSegmentPK();
        String tamraStartDate = null;
        if (segmentVO.getLifeVOCount() > 0)
        {
            tamraStartDate = segmentVO.getLifeVO(0).getTamraStartDate();
        }

        TransactionAccumsFastDAO transAccumFastDAO = new TransactionAccumsFastDAO();

        try
        {
            EDITBigDecimal withdToDate = transAccumFastDAO.accumWI_WithdrawalsToDate(segmentPK, currentDate);
            EDITBigDecimal withdYearToDate = transAccumFastDAO.accumWI_WithdrawalsToDateForDateRange(segmentPK, lastAnnivDate, currentDate);
            EDITBigDecimal cumIntialPrem = transAccumFastDAO.accumPY_PremiumTypeAndDate(segmentPK, currentDate, "Issue");
            EDITBigDecimal exchangePrem = transAccumFastDAO.accumPY_PremiumTypeAndDate(segmentPK, currentDate, "Exchange");
            EDITBigDecimal premPolYearToDate = transAccumFastDAO.accumPY_PremiumToDateForDateRange(segmentPK, lastAnnivDate, currentDate);
            EDITBigDecimal premCalYearToDate = transAccumFastDAO.accumPY_PremiumToDateForDateRange(segmentPK, beginningOfYear.getFormattedDate(), currentDate);

            trxAccums.put("NetWithDYearToDate", (withdYearToDate.getBigDecimal()));
            trxAccums.put("WithDToDate", (withdToDate.getBigDecimal()));
            trxAccums.put("PremiumYearToDate", (premPolYearToDate.getBigDecimal()));
            trxAccums.put("PremiumCalYearToDate", (premCalYearToDate.getBigDecimal()));
            trxAccums.put("CumInitialPrem", (cumIntialPrem.getBigDecimal()));
            trxAccums.put("Cum1035Prem", (exchangePrem.getBigDecimal()));



            if (tamraStartDate != null)
            {
                EDITBigDecimal withdSinceLast7Pay = transAccumFastDAO.accumWI_WithdrawalsToDateForDateRange(segmentPK, tamraStartDate, currentDate);
                trxAccums.put("WithDSinceLast7Pay", (withdSinceLast7Pay.getBigDecimal()));
            }
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }
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

        return trxAccums;
    }

    /**
     * Prints this associated NaturalDocVO in nested format.
     */
    public final void prettyPrint(Writer writer)
    {
        VOObject voObject = getDocumentAsVO();

    }

    private String getTrxType()
    {
        VOObject document = getDocumentAsVO();

        String trxType = null;

        if (document instanceof NaturalDocVO)
        {
            trxType = ((NaturalDocVO) document).getGroupSetupVO(0).getContractSetupVO(0).getClientSetupVO(0).getEDITTrxVO(0).getTransactionTypeCT();
        }

        else if (document instanceof RedoDocVO)
        {
            trxType = ((RedoDocVO) document).getGroupSetupVO(0).getContractSetupVO(0).getClientSetupVO(0).getEDITTrxVO(0).getTransactionTypeCT();
        }

        return trxType;
    }

    /**
     * Recursion method for the prettyPrint feature.
     * @see PRASEDocument#prettyPrint(Writer)
     * @param currentNode
     * @param parentNode
     * @param recursionContext
     */
    public void currentNode(Object currentNode, Object parentNode, RecursionContext recursionContext)
    {
        Writer writer = (Writer) recursionContext.getFromMemory("writer");

        int level = recursionContext.getRecursionLevel();

        try
        {
            for (int i = 0; i < level; i++)
            {
                writer.write("\t");

                if (i > 0)
                {
                    writer.write("|  ");
                }
            }

            writer.write(currentNode.toString() + "\n");
        }
        catch (IOException e)
        {
          System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param segmentVO
     * @param editTrxVO
     * @return
     */
    public TamraRetestVO[] getTamraRetestVOs(SegmentVO segmentVO, EDITTrxVO editTrxVO)
    {
        EDITDate trxEffectiveDate = new EDITDate(editTrxVO.getEffectiveDate());
        boolean trxWithinSevenYears = sevenYearTest(new EDITDate(segmentVO.getEffectiveDate()), trxEffectiveDate);

        TamraRetestVO[] tamraRetestVOs = null;
        long segmentPK = segmentVO.getSegmentPK();
        EDITDate effectiveDate = new EDITDate(editTrxVO.getEffectiveDate());
        if (trxWithinSevenYears)
        {
            tamraRetestVOs = buildWithin7TamraRetestInfo(segmentPK, effectiveDate);
        }
        else
        {
            tamraRetestVOs = buildAfter7TamraRetestInfo(segmentPK, effectiveDate);
        }

        return tamraRetestVOs;
    }

    private TamraRetestVO[] buildWithin7TamraRetestInfo(long segmentPK, EDITDate effectiveDate)
    {
        TamraRetestVO[] tamraRetestVOs = null;

        EDITTrxVO[] editTrxVOs = EDITTrx.findAllTamraRetest_UsingCRUD(segmentPK, effectiveDate);
        if (editTrxVOs != null)
        {
            tamraRetestVOs = buildTamraRetestInfo(editTrxVOs);
        }

        return tamraRetestVOs;
    }

    /**
     *
     * @param editTrxs
     * @return
     */
    private TamraRetestVO[] buildTamraRetestInfo(EDITTrxVO[] editTrxVOs)
    {
        List history = new ArrayList();
        List tamraRetest = new ArrayList();
        TamraRetestVO tamraRetestVO = null;

        for (int i = 0; i < editTrxVOs.length; i++)
        {
            String transactionType = editTrxVOs[i].getTransactionTypeCT();
            FinancialHistory financialHistory = null;
            String effectiveDate = editTrxVOs[i].getEffectiveDate();
            long editTrxPK = editTrxVOs[i].getEDITTrxPK();

            if (transactionType.equalsIgnoreCase("IS"))
            {
                tamraRetestVO = new TamraRetestVO();
                tamraRetestVO.setTamraStartDate(effectiveDate);
                financialHistory = FinancialHistory.findBy_EDITTrxPK_UsingCRUD(editTrxPK);
                tamraRetestVO.setAccumulatedValue(financialHistory.getAccumulatedValue().getBigDecimal());
                tamraRetest.add(tamraRetestVO);
            }
            else if (transactionType.equalsIgnoreCase("PY") || transactionType.equalsIgnoreCase("WI"))
            {
                HistoryVO historyVO = new HistoryVO();
                historyVO.setEffectiveDate(effectiveDate);
                financialHistory = FinancialHistory.findBy_EDITTrxPK_UsingCRUD(editTrxPK);
                historyVO.setAmount(financialHistory.getGrossAmount().getBigDecimal());
                historyVO.setTransactionTypeCT(transactionType);
                history.add(historyVO);
            }
            else if (transactionType.equalsIgnoreCase("CC"))
            {
                boolean startNew7PayIndicatorSet = Life.hasStartNew7PayIndicator(editTrxPK);
                //if any premium history created an IS trx or another CC created the tamra retest
                if (startNew7PayIndicatorSet)
                {
                    if (!history.isEmpty())
                    {
                        tamraRetestVO.setHistoryVO((HistoryVO[]) history.toArray(new HistoryVO[history.size()]));
                        history = new ArrayList();
                    }

                    tamraRetestVO = new TamraRetestVO();
                    tamraRetestVO.setTamraStartDate(effectiveDate);
                    financialHistory = FinancialHistory.findBy_EDITTrxPK_UsingCRUD(editTrxPK);
                    tamraRetestVO.setAccumulatedValue(financialHistory.getAccumulatedValue().getBigDecimal());
                    tamraRetest.add(tamraRetestVO);
                }
            }
        }

        //At end of transactions get the last tamra retest into the List
        if (!history.isEmpty())
        {
            tamraRetestVO.setHistoryVO((HistoryVO[]) history.toArray(new HistoryVO[history.size()]));
        }


        return (TamraRetestVO[]) tamraRetest.toArray(new TamraRetestVO[tamraRetest.size()]);
    }

    private boolean sevenYearTest(EDITDate secondDate, EDITDate trxEffectiveDate)
    {
        boolean trxWithinSevenYears = false;
        int monthsDiff = trxEffectiveDate.getElapsedMonths(secondDate);

        int years = monthsDiff / 12;
        if (years <= 7)
        {
            trxWithinSevenYears = true;
        }

        return trxWithinSevenYears;
    }

    private TamraRetestVO[] buildAfter7TamraRetestInfo(long segmentPK, EDITDate effectiveDate)
    {
        TamraRetestVO[] tamraRetestVOs = null;

        EDITTrxVO[] editTrxVOs = afterSevenYearTest(segmentPK, effectiveDate);
        if (editTrxVOs != null)
        {
            tamraRetestVOs = buildAfter7TamraRetest(editTrxVOs);
        }

        return tamraRetestVOs;
    }

    /**
     * Only one tamraRetestVO will be built for the after 7 years test, if there is qualifying data.
     * @param editTrxVOs
     * @return
     */
    private TamraRetestVO[] buildAfter7TamraRetest(EDITTrxVO[] editTrxVOs)
    {
        List history = new ArrayList();
        List tamraRetest = new ArrayList();
        TamraRetestVO tamraRetestVO = null;

        for (int i = 0; i < editTrxVOs.length; i++)
        {
            String transactionType = editTrxVOs[i].getTransactionTypeCT();
            FinancialHistory financialHistory = null;
            String effectiveDate = editTrxVOs[i].getEffectiveDate();
            long editTrxPK = editTrxVOs[i].getEDITTrxPK();


            if (transactionType.equalsIgnoreCase("PY") || transactionType.equalsIgnoreCase("WI"))
            {
                HistoryVO historyVO = new HistoryVO();
                historyVO.setEffectiveDate(effectiveDate);
                financialHistory = FinancialHistory.findBy_EDITTrxPK_UsingCRUD(editTrxPK);
                historyVO.setAmount(financialHistory.getGrossAmount().getBigDecimal());
                historyVO.setTransactionTypeCT(transactionType);
                history.add(historyVO);
            }
            else if (transactionType.equalsIgnoreCase("CC"))
            {
                tamraRetestVO = new TamraRetestVO();
                tamraRetestVO.setTamraStartDate(effectiveDate);
                financialHistory = FinancialHistory.findBy_EDITTrxPK_UsingCRUD(editTrxPK);
                tamraRetestVO.setAccumulatedValue(financialHistory.getAccumulatedValue().getBigDecimal());
            }
        }

        //At end of transactions get the history into tamra retest
        if (!history.isEmpty())
        {
            tamraRetestVO.setHistoryVO((HistoryVO[]) history.toArray(new HistoryVO[history.size()]));
        }

        tamraRetest.add(tamraRetestVO);

        return (TamraRetestVO[]) tamraRetest.toArray(new TamraRetestVO[tamraRetest.size()]);
    }
     /**
      * Check the TamraStartDate of the Base segment to determine if the CC qualifies for testing.
      * @param segmentPK
      * @param effectiveDate
      * @return
      */
    private EDITTrxVO[] afterSevenYearTest(long segmentPK, EDITDate effectiveDate)
    {
        Life life = Life.findBy_SegmentPK(segmentPK);
        EDITDate tamraStartDate = life.getTamraStartDate();
        EDITTrxVO[] editTrxVOs = null;
        boolean dateWithinSevenYears = sevenYearTest(tamraStartDate, effectiveDate);

        //if the effective date is within 7 years of the tamra start date find the transactions needed for retest
        if (dateWithinSevenYears)
        {
            editTrxVOs = EDITTrx.findPartialTamraRetest_UsingCRUD(segmentPK, effectiveDate);
        }

        return editTrxVOs;
    }

	/**
     * Sorts Buckets ascending by DepositDate.
     * @param investmentVOs
     */
    public void sortBuckets(InvestmentVO[] investmentVOs, SegmentVO segmentVO, EDITTrxVO editTrxVO)
    {
        String sortOrder = getSortOrder(segmentVO, editTrxVO);

        for (int i = 0; i < investmentVOs.length; i++)
        {
            InvestmentVO investmentVO = investmentVOs[i];

            BucketVO[] bucketVOs = investmentVO.getBucketVO();

            if (bucketVOs != null)
            {
                bucketVOs = (BucketVO[]) Util.sortObjects(bucketVOs, new String[]{"getDepositDate"});
                if (sortOrder != null && sortOrder.equalsIgnoreCase("LIFO"))
                {
                    bucketVOs = reverseSortBuckets(bucketVOs);
                }
            }

            investmentVO.removeAllBucketVO();
            investmentVO.setBucketVO(bucketVOs);
        }
    }

    private String getSortOrder(SegmentVO segmentVO, EDITTrxVO editTrxVO)
    {
        engine.business.Lookup engineLookup = new engine.component.LookupComponent();

        String grouping = "TRANSACTION";
        String field = "REMOVALORDER";
        EDITDate effDate = new EDITDate(editTrxVO.getEffectiveDate());

        AreaValueVO areaValueVO = engineLookup.getAreaValue(segmentVO.getProductStructureFK(), segmentVO.getIssueStateCT(), grouping, effDate, field, segmentVO.getQualNonQualCT());

        String sortOrder = null;
        if (areaValueVO != null)
        {
            sortOrder = areaValueVO.getAreaValue();
        }

        return sortOrder;
    }

    /**
     * Sorts Buckets descending by DepositDate.
     * @param investmentVOs
     */
    private BucketVO[] reverseSortBuckets(BucketVO[] bucketVOs)
    {
        List sortedBucketVOs = new ArrayList();

        for (int j = 0; j < bucketVOs.length; j++)
        {
            sortedBucketVOs.add(bucketVOs[j]);
        }

        Collections.reverse(sortedBucketVOs);
        return (BucketVO[]) sortedBucketVOs.toArray(new BucketVO[sortedBucketVOs.size()]);
	}


    public GeneralAccountArrayVO[] createGeneralAccountArrayVO(BusinessDay[] businessDays, EDITTrxHistoryVO[] editTrxHistoryVOs, InvestmentVO investmentVO, EDITDate edPriorMFDate)
    {
        Map generalAccountArrayVOMap = new TreeMap();
        EDITBigDecimal previousCumDollars = new EDITBigDecimal();
        priorGADate = null;

        BucketVO[] bucketVOs = investmentVO.getBucketVO();

        for (int i = 0; i < businessDays.length; i++)
        {
            EDITDate businessDate = businessDays[i].getBusinessDate();
            GeneralAccountArrayVO generalAccountArrayVO = new GeneralAccountArrayVO();
            generalAccountArrayVO.setInvestmentFK(investmentVO.getInvestmentPK());
            generalAccountArrayVO.setDollars(new EDITBigDecimal().getBigDecimal());
            generalAccountArrayVO.setCumDollars(new EDITBigDecimal().getBigDecimal());
            generalAccountArrayVO.setBusinessDate(businessDate.getFormattedDate());

            if (priorGADate == null)
            {
                priorGADate = businessDate;
            }

            if (i == 0 && !businessDate.equals(edPriorMFDate))
            {
                generalAccountArrayVOMap = addArrayVOToMap(investmentVO.getInvestmentPK(), businessDate, generalAccountArrayVO, generalAccountArrayVOMap);
            }
            else
            {
                if (editTrxHistoryVOs != null)
                {
                    for (int j = 0; j < editTrxHistoryVOs.length; j++)
                    {
                        EDITTrxVO editTrxVO = (EDITTrxVO) editTrxHistoryVOs[j].getParentVO(EDITTrxVO.class);
                        EDITDate edTrxEffDate = new EDITDate(editTrxVO.getEffectiveDate());
                        previousCumDollars = matchToBucket(editTrxHistoryVOs[j], bucketVOs, edTrxEffDate, generalAccountArrayVO, previousCumDollars, generalAccountArrayVOMap);
                        generalAccountArrayVOMap = addArrayVOToMap(investmentVO.getInvestmentPK(), businessDate, generalAccountArrayVO, generalAccountArrayVOMap);
                    }
                }

                priorGADate = businessDate;
            }
        }   //end of for loop for business days

        if (businessDays.length > generalAccountArrayVOMap.size())
        {
            for (int i = generalAccountArrayVOMap.size(); i < businessDays.length; i++)
            {
                EDITDate businessDate = businessDays[i].getBusinessDate();
                GeneralAccountArrayVO generalAccountArrayVO = new GeneralAccountArrayVO();
                generalAccountArrayVO.setInvestmentFK(investmentVO.getInvestmentPK());
                generalAccountArrayVO.setCumDollars(previousCumDollars.getBigDecimal());
                generalAccountArrayVO.setDollars(new EDITBigDecimal().getBigDecimal());
                generalAccountArrayVO.setBusinessDate(businessDate.getFormattedDate());

                generalAccountArrayVOMap = addArrayVOToMap(investmentVO.getInvestmentPK(), businessDate, generalAccountArrayVO, generalAccountArrayVOMap);
            }
        }

        List generalAccountArrayVOList = new ArrayList();

        if (!generalAccountArrayVOMap.isEmpty())
        {
            Set keys = generalAccountArrayVOMap.keySet();
            Iterator it = keys.iterator();
            while (it.hasNext())
            {
                String mapKey = (String) it.next();
                generalAccountArrayVOList.add(generalAccountArrayVOMap.get(mapKey));
            }
        }

        return (GeneralAccountArrayVO[]) generalAccountArrayVOList.toArray(new GeneralAccountArrayVO[generalAccountArrayVOList.size()]);
    }

    private EDITBigDecimal matchToBucket(EDITTrxHistoryVO editTrxHistoryVO,
                                         BucketVO[] bucketVOs,
                                         EDITDate edTrxEffDate,
                                         GeneralAccountArrayVO generalAccountArrayVO,
                                         EDITBigDecimal previousCumDollars,
                                         Map generalAccountArrayVOMap)
    {
        BucketHistoryVO[]  bucketHistoryVOs = editTrxHistoryVO.getBucketHistoryVO();
        EDITDate businessDate = new EDITDate(generalAccountArrayVO.getBusinessDate());
        EDITBigDecimal accumCumDollars = new EDITBigDecimal();

        for (int i = 0; i < bucketVOs.length; i++)
        {
            BucketVO bucketVO = bucketVOs[i];
            for (int j = 0; j < bucketHistoryVOs.length; j++)
            {
                if (bucketVOs[i].getBucketPK() == bucketHistoryVOs[j].getBucketFK())
                {
                    if (edTrxEffDate.after(priorGADate) &&
                        edTrxEffDate.before(businessDate))
                    {
                        generalAccountArrayVOMap = checkForMissingDays(edTrxEffDate, previousCumDollars, generalAccountArrayVO.getInvestmentFK(), generalAccountArrayVOMap);

                        accumCumDollars = accumCumDollars.addEditBigDecimal(bucketHistoryVOs[j].getCumDollars());
                        EDITBigDecimal dollars = new EDITBigDecimal(bucketHistoryVOs[j].getDollars());
                        String toFromStatus = bucketHistoryVOs[j].getToFromStatus();
                        if (toFromStatus != null && toFromStatus.equalsIgnoreCase("F") && !dollars.isEQ("0"))
                        {
                            dollars = dollars.multiplyEditBigDecimal("-1");
                        }

                        GeneralAccountArrayVO newGAArrayVO = new GeneralAccountArrayVO();
                        newGAArrayVO.setInvestmentFK(generalAccountArrayVO.getInvestmentFK());
                        newGAArrayVO.setCumDollars(accumCumDollars.getBigDecimal());
                        newGAArrayVO.setDollars(dollars.getBigDecimal());
                        newGAArrayVO.setBusinessDate(edTrxEffDate.getFormattedDate());

                        generalAccountArrayVOMap = addArrayVOToMap(newGAArrayVO.getInvestmentFK(), edTrxEffDate, newGAArrayVO, generalAccountArrayVOMap);

                        previousCumDollars = accumCumDollars;
                        priorGADate = edTrxEffDate;
                    }
                    else if (businessDate.equals(edTrxEffDate))
                    {
                        generalAccountArrayVOMap = checkForMissingDays(edTrxEffDate, previousCumDollars, generalAccountArrayVO.getInvestmentFK(), generalAccountArrayVOMap);

                        accumCumDollars = accumCumDollars.addEditBigDecimal(bucketHistoryVOs[j].getCumDollars());
                        EDITBigDecimal dollars = new EDITBigDecimal(bucketHistoryVOs[j].getDollars());
                        String toFromStatus = bucketHistoryVOs[j].getToFromStatus();
                        if (toFromStatus != null && toFromStatus.equalsIgnoreCase("F") && !dollars.isEQ("0"))
                        {
                            dollars = dollars.multiplyEditBigDecimal("-1");
                        }

                        generalAccountArrayVO.setCumDollars(accumCumDollars.getBigDecimal());
                        generalAccountArrayVO.setDollars(dollars.getBigDecimal());
                        previousCumDollars = accumCumDollars;
                    }
                    else if (businessDate.before(edTrxEffDate))
                    {
                        generalAccountArrayVO.setCumDollars(previousCumDollars.getBigDecimal());
//                        generalAccountArrayVO.setDollars(new EDITBigDecimal().getBigDecimal());
                    }
                }
//               else
//                {
//                    generalAccountArrayVO.setCumDollars(previousCumDollars.getBigDecimal());
//                    generalAccountArrayVO.setDollars(new EDITBigDecimal().getBigDecimal());
//                }
            }
        }

        return previousCumDollars;
    }

    private Map checkForMissingDays(EDITDate edTrxEffDate,
                                    EDITBigDecimal previousCumDollars, long investmentFK,
                                    Map generalAccountArrayVOMap)
    {
        int noDaysToGenerate = edTrxEffDate.getElapsedDays(priorGADate) - 1;

        for (int m = 0; m < noDaysToGenerate; m++)
        {
            priorGADate.addDays(1);
            GeneralAccountArrayVO newGAArrayVO = new GeneralAccountArrayVO();
            newGAArrayVO.setInvestmentFK(investmentFK);
            newGAArrayVO.setCumDollars(previousCumDollars.getBigDecimal());
            newGAArrayVO.setDollars(new EDITBigDecimal().getBigDecimal());
            newGAArrayVO.setBusinessDate(priorGADate.getFormattedDate());
            
            generalAccountArrayVOMap = addArrayVOToMap(investmentFK, priorGADate, newGAArrayVO, generalAccountArrayVOMap);
        }
        
        return generalAccountArrayVOMap;
    }

    private Map addArrayVOToMap(long investmentPK, EDITDate businessDate, 
                                GeneralAccountArrayVO generalAccountArrayVO, Map generalAccountArrayVOMap)
    {
        generalAccountArrayVOMap.put(investmentPK + "_" + businessDate, generalAccountArrayVO);
        
        return generalAccountArrayVOMap;
    }
    
    public EDITBigDecimal setLoanCapInfo(long segmentPK)
    {
        EDITBigDecimal lastLoanCapAmount = new EDITBigDecimal();

        EDITTrx editTrx = EDITTrx.getGreatestLCTrx_UsingCRUD(segmentPK);
        if (editTrx != null)
        {
            EDITTrxVO editTrxVO = editTrx.getAsVO();

            lastLoanCapAmount = new EDITBigDecimal(editTrxVO.getEDITTrxHistoryVO(0).getFinancialHistoryVO(0).getGrossAmount());
        }

        return lastLoanCapAmount;
    }
}
