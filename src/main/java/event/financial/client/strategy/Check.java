/*
 * User: gfrosti
 * Date: Aug 13, 2003
 * Time: 1:43:57 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package event.financial.client.strategy;

import edit.common.CodeTableWrapper;
import edit.common.EDITBigDecimal;
import edit.common.EDITDateTime;
import edit.common.exceptions.EDITCRUDException;
import edit.common.exceptions.EDITEventException;
import edit.common.vo.AgentVO;
import edit.common.vo.BucketHistoryVO;
import edit.common.vo.ChargeHistoryVO;
import edit.common.vo.CheckDocVO;
import edit.common.vo.ClientRoleFinancialVO;
import edit.common.vo.ClientRoleVO;
import edit.common.vo.CommissionHistoryVO;
import edit.common.vo.ProductStructureVO;
import edit.common.vo.EDITTrxHistoryVO;
import edit.common.vo.EDITTrxVO;
import edit.common.vo.FinancialHistoryVO;
import edit.common.vo.InSuspenseVO;
import edit.common.vo.InvestmentHistoryVO;
import edit.common.vo.ReinsuranceHistoryVO;
import edit.common.vo.ReinsurerVO;
import edit.common.vo.SegmentHistoryVO;
import edit.common.vo.SuspenseVO;
import edit.common.vo.VOObject;
import edit.common.vo.WithholdingHistoryVO;

import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.VOClass;

import engine.business.Calculator;

import engine.component.CalculatorComponent;

import engine.sp.SPOutput;

import event.EDITTrx;

import event.dm.composer.CheckDocComposer;

import event.financial.client.trx.ClientTrx;

import java.util.ArrayList;
import java.util.List;

import role.ClientRoleFinancial;


public class Check extends ClientStrategy
{
    private static final String POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;

    public Check(ClientTrx clientTrx)
    {
        super(clientTrx);
    }

     public Check(ClientTrx clientTrx, String sortStatus)
    {
        super(clientTrx);
        super.setSortStatus(sortStatus);
    }

    public ClientStrategy[] execute() throws EDITEventException
    {
        try
        {
            CheckDocVO checkDocVO = buildCheckDocVO();

            EDITTrxVO editTrxVO = super.getClientTrx().getEDITTrxVO();
            String trxStatusInd = editTrxVO.getStatus();
            String trxEffDate = editTrxVO.getEffectiveDate();
            String transactionType = editTrxVO.getTransactionTypeCT();
            CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
            String name = codeTableWrapper.getCodeDescByCodeTableNameAndCode("TRXTYPE", transactionType);

            engine.business.Lookup engineLookup = new engine.component.LookupComponent();

            ProductStructureVO[] productStructureVO = new edit.common.vo.ProductStructureVO[0];
            if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_REINSURANCE_CHECK))
            {
                productStructureVO = engineLookup.findProductStructureByNames("Reinsurance", "*", "*", "*", "*");
            }
            else
            {
                productStructureVO = engineLookup.findProductStructureByNames("Commission", "*", "*", "*", "*");
            }

            if (productStructureVO == null)
            {
                if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_REINSURANCE_CHECK))
                {
                    throw new RuntimeException("Product Structure of [Reinsurance, *, *, *, *] Was Not Found");
                }
                else
                {
                    throw new RuntimeException("Product Structure of [Commission, *, *, *, *] Was Not Found");
                }
            }

            Calculator calcComponent = new CalculatorComponent();

            SPOutput spOutput = null;

            spOutput = calcComponent.processScript("CheckDocVO", checkDocVO, name, trxStatusInd, "*", trxEffDate, productStructureVO[0].getProductStructurePK(), true);

            VOObject[] voObjects = spOutput.getSPOutputVO().getVOObject();

            if (voObjects != null)
            {
                doUpdates(voObjects, checkDocVO);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new EDITEventException(e.getMessage());
        }

        return null;
    }

    /**
     * Build the appropriate document for the check transaction passed in  
     * @return
     * @throws EDITEventException
     */
    private CheckDocVO buildCheckDocVO() throws EDITEventException, Exception
    {
        EDITTrxVO editTrxVO = super.getClientTrx().getEDITTrxVO();

        CheckDocVO checkDocVO = null;
        CheckDocComposer checkDocComposer = new CheckDocComposer();

        if (editTrxVO.getTransactionTypeCT().equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_REINSURANCE_CHECK))
        {
            checkDocVO = checkDocComposer.composeForReinsuranceCheck(editTrxVO);
        }
        else
        {
            checkDocVO = checkDocComposer.compose(editTrxVO);
        }

        return checkDocVO;
    }

    private void doUpdates(Object[] voObjects, CheckDocVO checkDocVO) throws EDITEventException
    {
        NaturalSave naturalSave = new NaturalSave();
        
        
        naturalSave.setCycleDate(this.getClientTrx().getCycleDate());
        
        EDITTrxVO editTrxVO = checkDocVO.getEDITTrxVO(0);
        EDITTrxHistoryVO editTrxHistoryVO = null;

        naturalSave.setCycleDate(super.getClientTrx().getCycleDate());
        naturalSave.setExecutionMode(super.getClientTrx().getExecutionMode());

        CRUD crud = CRUDFactory.getSingleton().getCRUD(POOLNAME);

        try
        {
            crud.startTransaction();

            for (int i = 0; i < voObjects.length; i++)
            {
                Object voObject = voObjects[i];

                String currentTableName = VOClass.getTableName(voObject.getClass());

                if (currentTableName.equalsIgnoreCase("ClientRoleFinancial"))
                {
                    ClientRoleFinancialVO clientRoleFinancialVO = (ClientRoleFinancialVO) voObject;
                    new ClientRoleFinancial(clientRoleFinancialVO).save();
                }
                if (currentTableName.equalsIgnoreCase("EDITTrxHistory"))
                {

                    editTrxHistoryVO = (EDITTrxHistoryVO) voObject;
                    naturalSave.populateEDITTrxHistoryFields(editTrxHistoryVO, editTrxVO);
                    naturalSave.saveEditTrxHistories(editTrxHistoryVO, editTrxVO, crud);
                }
                else if (currentTableName.equalsIgnoreCase("BucketHistory"))
                {
                    editTrxHistoryVO.addBucketHistoryVO((BucketHistoryVO) voObject);
                }
                else if (currentTableName.equalsIgnoreCase("WithholdingHistory"))
                {
                    editTrxHistoryVO.addWithholdingHistoryVO((WithholdingHistoryVO) voObject);
                }
                else if (currentTableName.equalsIgnoreCase("ChargeHistory"))
                {
                    editTrxHistoryVO.addChargeHistoryVO((ChargeHistoryVO) voObject);
                }
                else if (currentTableName.equalsIgnoreCase("CommissionHistory"))
                {
                    editTrxHistoryVO.addCommissionHistoryVO((CommissionHistoryVO) voObject);
                }
                else if (currentTableName.equalsIgnoreCase("FinancialHistory"))
                {
                    editTrxHistoryVO.addFinancialHistoryVO((FinancialHistoryVO) voObject);
                }
                else if (currentTableName.equalsIgnoreCase("ReinsuranceHistory"))
                {
                    editTrxHistoryVO.addReinsuranceHistoryVO((ReinsuranceHistoryVO) voObject);
                }
                else if (currentTableName.equalsIgnoreCase("InvestmentHistory"))
                {
                    editTrxHistoryVO.addInvestmentHistoryVO((InvestmentHistoryVO) voObject);
                }
                else if (currentTableName.equalsIgnoreCase("SegmentHistory"))
                {
                    editTrxHistoryVO.addSegmentHistoryVO((SegmentHistoryVO) voObject);
                }

            }  //end for loop of voObjects

            naturalSave.saveEditTrxHistories(editTrxHistoryVO, editTrxVO, crud);

            //save the edit trx for a new status
            editTrxVO.setPendingStatus("H");

            crud.createOrUpdateVOInDB(editTrxVO);

            SuspenseVO suspenseVO = createSuspense(checkDocVO, editTrxHistoryVO);

            crud.createOrUpdateVOInDBRecursively(suspenseVO, false);

            crud.commitTransaction();
        }
        catch (Exception e) // RuntimeException(s) need to be considered as well.
        {
            EDITEventException editEventException = new EDITEventException(e.getMessage());

            System.out.println(e);

            e.printStackTrace();

            try
            {
                crud.rollbackTransaction();
            }
            catch (EDITCRUDException e1)
            {
                System.out.println(e1);

                e1.printStackTrace();

                EDITEventException editEventException2 = new EDITEventException(e.getMessage());

                editEventException = editEventException2;
            }

            throw editEventException;
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }
    }

    private SuspenseVO createSuspense(CheckDocVO checkDocVO, EDITTrxHistoryVO editTrxHistoryVO) throws Exception
    {
        List agentInclusionList = new ArrayList();
        agentInclusionList.add(AgentVO.class);

        EDITTrxVO editTrxVO = checkDocVO.getEDITTrxVO(0);
//        ClientSetupVO clientSetupVO = new ClientSetupComposer(new ArrayList()).compose(editTrxVO.getClientSetupFK());
//        ClientRoleVO clientRoleVO = new ClientRoleComposer(agentInclusionList).compose(clientSetupVO.getClientRoleFK());
//        AgentVO[] agentVO = clientRoleVO.getAgentVO();

        String transactionType = editTrxVO.getTransactionTypeCT();

        String userDefNumber = null;
        if (transactionType.equalsIgnoreCase(EDITTrx.TRANSACTIONTYPECT_REINSURANCE_CHECK))
        {
            ReinsurerVO reinsurerVO = checkDocVO.getClientDetailVO(0).getReinsurerVO(0);
            userDefNumber = reinsurerVO.getReinsurerNumber();
        }
        else
        {
            //AgentVO agentVO = (AgentVO) checkDocVO.getClientRoleVO(0).getParentVO(AgentVO.class);
            //userDefNumber = agentVO.getAgentNumber();
             ClientRoleVO clientRolevo = (ClientRoleVO) checkDocVO.getClientRoleVO(0);
            userDefNumber = clientRolevo.getReferenceID();
        }

        FinancialHistoryVO[] financialHistoryVO = editTrxHistoryVO.getFinancialHistoryVO();

        String effectiveDate = editTrxVO.getEffectiveDate();
        EDITBigDecimal suspenseAmount = new EDITBigDecimal(financialHistoryVO[0].getCheckAmount());

        InSuspenseVO inSuspenseVO = new InSuspenseVO();
        inSuspenseVO.setAmount(suspenseAmount.getBigDecimal());
        inSuspenseVO.setEDITTrxHistoryFK(editTrxHistoryVO.getEDITTrxHistoryPK());
        inSuspenseVO.setInSuspensePK(0);
        inSuspenseVO.setSuspenseFK(0);

        SuspenseVO suspenseVO = new SuspenseVO();
        suspenseVO.setAccountingPendingInd("N");
        suspenseVO.setMaintenanceInd("M");
        suspenseVO.setDirectionCT("Remove");
        suspenseVO.setEffectiveDate(effectiveDate);
        suspenseVO.setMaintDateTime(editTrxVO.getMaintDateTime());
        suspenseVO.setOperator(editTrxVO.getOperator());
        suspenseVO.addInSuspenseVO(inSuspenseVO);
//        suspenseVO.setProcessDate(editTrxHistoryVO.getProcessDate());
        String processDate = new EDITDateTime(editTrxHistoryVO.getProcessDateTime()).getEDITDate().getFormattedDate();
        suspenseVO.setProcessDate(processDate);
        suspenseVO.setSuspenseAmount(suspenseAmount.getBigDecimal());
        suspenseVO.setSuspensePK(0);
        suspenseVO.setUserDefNumber(userDefNumber);

        return suspenseVO;
    }
}
