/*
 * User: unknown
 * Date: Aug 26, 2003
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package role.component;

import agent.PlacedAgent;
import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.common.*;
import edit.services.component.AbstractComponent;
import edit.services.db.ConnectionFactory;
import role.*;
import role.business.Role;
import role.dm.StorageManager;
import role.dm.composer.ClientRoleFinancialComposer;

import java.util.*;

import event.financial.client.strategy.*;
import contract.*;
import businesscalendar.*;


/**
 * The Role request controller
 */
public class RoleComponent extends AbstractComponent implements Role {


    // Member variables
    private StorageManager  sm;

    /**
     * ClientController constructor.
     */
    public RoleComponent() {

        init();
    }

     public long createOrUpdateVO(Object valueObject, boolean recursively) throws Exception{

       // sm.saveVO(valueObject);
         return 0;
    }

    public int deleteVO(Class voClass, long primaryKey, boolean recursively) throws Exception {

        return super.deleteVO(voClass, primaryKey, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    public Object retrieveVO(Class voClass, long primaryKey, boolean recursively, List voInclusionList) throws Exception
    {
        return super.retrieveVO(voClass, primaryKey, ConnectionFactory.EDITSOLUTIONS_POOL, false, null);
    }

    public String[] findVOs(){return null;}


    private final void init(){

        sm = new StorageManager();
    }

    public ElementLockVO lockElement(long segmentPK, String username) throws EDITLockException {

        return sm.lockElement(segmentPK, username);
    }

    public int unlockElement(long lockTablePK) {

        return sm.unlockElement(lockTablePK);
    }

    public long saveOrUpdateClientRole(ClientRoleVO clientRoleVO) throws Exception {

		return sm.saveOrUpdateClientRole(clientRoleVO);
    }

    /**
     * @see Role#setupAgentCommissionChecks(String, String, String, String)
     * @param paymentMode
     * @param forceOutMinBal (indicator yes/no)
     * @param disbursementSourceCT
     * @param operator
     * @throws Exception
     */
    public void setupAgentCommissionChecks(String paymentModeCT, String forceOutMinBal, String disbursementSourceCT, String operator) throws Exception
    {
        CheckController checkController = new CheckController();

        checkController.setupAgentCommissionChecks(paymentModeCT, forceOutMinBal, disbursementSourceCT, operator);
    }

    public void runYearEndClientBalance() throws Exception
    {
        ClientRoleFinancial.runYearEndClientBalance();
    }

    public ClientRoleFinancialVO composeClientRoleFinancialByClientRoleFK(long clientRoleFK, List voInclusionList) throws Exception
    {
        return new ClientRoleFinancialComposer(voInclusionList).composeClientRoleFinancialByClientRoleFK(clientRoleFK);
    }

    public void updateLastCheckDateTime(long placedAgentPK, EDITDateTime lastCheckDateTime) throws Exception
    {
        PlacedAgent placedAgent = new PlacedAgent(placedAgentPK);

        ClientRole clientRole = placedAgent.findByClientRoleFK(placedAgent.getClientRoleFK().longValue());

        ClientRoleFinancial clientRoleFinancial = clientRole.getClientRoleFinancial();

        clientRoleFinancial.setLastCheckDateTime(lastCheckDateTime);

        clientRoleFinancial.save();
    }

    /**
     * After a clientDetail is saved, the clientRole is saved.  ChangeHistory is generated for the fields that changed
     * on the ClientRole table.  If the newIssuesEligibilityStatus has changed that change is received back and
     * processed.
     * @param clientRoleVOs
     * @param operator
     */
    public void saveClientRole(ClientRoleVO[] clientRoleVOs, String operator)
    {
        for (int i = 0; i < clientRoleVOs.length; i++)
        {
            ClientRole clientRole = new ClientRole(clientRoleVOs[i]);
            Change change = clientRole.checkForNonFinancialChanges(operator);
            clientRole.save();

            if (change != null)
            {
                processNewIssuesStatusChange(change, operator, clientRole);
            }
        }
    }

    /**
      * Find contract/investments affected by the status change and generate transfter units trxes
      * @param change
      */
    private void processNewIssuesStatusChange(Change change, String operator, ClientRole clientRole)
    {
         List voExclusionList = new ArrayList();
         voExclusionList.add(AgentHierarchyVO.class);
         voExclusionList.add(SegmentBackupVO.class);
         voExclusionList.add(ContractRequirementVO.class);
         voExclusionList.add(RequiredMinDistributionVO.class);
         voExclusionList.add(LifeVO.class);
         voExclusionList.add(ContractTreatyVO.class);
         voExclusionList.add(DepositsVO.class);
         voExclusionList.add(PayoutVO.class);
         voExclusionList.add(NoteReminderVO.class);
         voExclusionList.add(RealTimeActivityVO.class);
         voExclusionList.add(InherentRiderVO.class);
         voExclusionList.add(ContractSetupVO.class);
         voExclusionList.add(InvestmentAllocationOverrideVO.class);
         voExclusionList.add(BucketVO.class);
         voExclusionList.add(InvestmentHistoryVO.class);
         voExclusionList.add(CommissionInvestmentHistoryVO.class);

         ClientRoleVO clientRoleVO = (ClientRoleVO)clientRole.getVO();
         long clientRolePK = clientRoleVO.getClientRolePK();
         long clientDetailPK = clientRoleVO.getClientDetailFK();
         EDITDate newIssuesEligibilityStartDate = new EDITDate(clientRoleVO.getNewIssuesEligibilityStartDate());

         SegmentVO[] segmentVOs = contract.dm.dao.DAOFactory.getSegmentDAO().findByClientDetailFK(clientDetailPK, true, voExclusionList);

         if (segmentVOs != null)
         {
            long longInitValue = 0;

            for (int i = 0; i < segmentVOs.length; i++)
            {
                try
                {
                    long contractClientPK = getOwnerContractClientPK(segmentVOs[i]);
                    EDITDate tuEffectiveDate = getTUEffectiveDate(newIssuesEligibilityStartDate);
                    boolean generateTrx = checkContractForTrxGeneration(segmentVOs[i], tuEffectiveDate);

                    if (generateTrx)
                    {
                        TrxGeneration trxGeneration = new TrxGeneration();
                        trxGeneration.createTransferUnitsTrx(tuEffectiveDate, operator, segmentVOs[i], longInitValue, clientRolePK, contractClientPK);
                    }
                }

                catch (EDITEventException e)
                {
                  System.out.println(e);

                      e.printStackTrace();
                      throw new RuntimeException("problem with TrxGeneration for TU trx", e);
                }
            }
         }
     }

     /**
      * The filteredFund must have chargeCodes and the newIssuesStatusInd set to "N" or "Y", for the 'TU' trx to be created
      * for a contract.  If a contract has one fund set for New Issues,  all the funds used will also be new issue.
      * @param segmentVO
      * @param tuEffectiveDate, last business day of the month for the date requested
      * @return
      */
     private boolean checkContractForTrxGeneration(SegmentVO segmentVO, EDITDate tuEffectiveDate)
     {
         boolean generateTrx = false;

         if (tuEffectiveDate.before(new EDITDate(segmentVO.getEffectiveDate())))
         {
             return  generateTrx;
         }

         InvestmentVO[] investmentVOs = segmentVO.getInvestmentVO();

         List voExclusionList = new ArrayList();
         voExclusionList.add(UnitValuesVO.class);
         voExclusionList.add(FeeVO.class);
         voExclusionList.add(InterestRateParametersVO.class);
         voExclusionList.add(ProductFilteredFundStructureVO.class);
         voExclusionList.add(FeeDescriptionVO.class);

         FilteredFundVO[] filteredFundVOs = engine.dm.dao.DAOFactory.getFilteredFundDAO().findByFilteredFundPK(investmentVOs[0].getFilteredFundFK(), true, voExclusionList);

         if (filteredFundVOs[0].getChargeCodeIndicator() != null)
         {
             if (filteredFundVOs[0].getChargeCodeVO(0).getNewIssuesIndicatorCT() != null)
             {
                 generateTrx = true;
             }
         }

         return generateTrx;
     }

    /**
     * Find the owner role of the ContractClient
     * @param segmentVO
     * @return
     */
    private long getOwnerContractClientPK(SegmentVO segmentVO)
    {
        long contractClientPK = 0;

        if (segmentVO.getContractClientVOCount() > 0)
        {
            ContractClientVO[] contractClientVOs = segmentVO.getContractClientVO();

            ContractClient[] contractClients = this.convertContractClientVOs(contractClientVOs);

            ContractClient contractClient = ContractClient.getOwnerContractClient(contractClients);

            if (contractClient != null)
            {
                contractClientPK = contractClient.getContractClientPK().longValue();
            }
        }

        return contractClientPK;
    }

    /**
     * The 'TU' trx must be set with the last business day of the month as the effectiveDate.  This is the
     * calculation for that date.
     * @param newIssuesEligibilityStartDate
     * @return
     */
    private EDITDate getTUEffectiveDate(EDITDate newIssuesEligibilityStartDate)
    {
        BusinessCalendar businessCalendar = new BusinessCalendar();

        BusinessDay businessDay = businessCalendar.getBestBusinessDay(businessCalendar.getMaximumDate(newIssuesEligibilityStartDate));

        EDITDate effectiveDate = businessDay.getBusinessDate();

        return effectiveDate;
    }

    // The following method is temporary until we fully go to Hibernate
    private ContractClient[] convertContractClientVOs(ContractClientVO[] contractClientVOs)
    {
        List contractClients = new ArrayList();

        for (int i = 0; i < contractClientVOs.length; i++)
        {
            ContractClient contractClient = new ContractClient(contractClientVOs[i]);

            contractClients.add(contractClient);
        }

        return (ContractClient[]) contractClients.toArray(new ContractClient[contractClients.size()]);
    }
}
