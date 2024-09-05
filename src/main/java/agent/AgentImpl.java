package agent;

import agent.common.*;

import agent.dm.dao.*;

import edit.common.*;
import edit.common.exceptions.*;
import edit.common.vo.*;

import edit.services.db.*;
import edit.services.db.hibernate.*;

import engine.*;

import role.*;
import contract.Segment;
import client.ClientDetail;
import client.Preference;
import client.TaxProfile;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 24, 2003
 * Time: 11:23:14 AM
 * To change this template use Options | File Templates.
 */
public class AgentImpl extends CRUDEntityImpl
{
    protected void updateAgentCommissions(Agent agent,
                                          EDITBigDecimal commissionAmount,
                                          String redirectEffectiveDate,
                                          EDITBigDecimal commissionTaxable,
                                          String commissionTypeCT,
                                          EDITBigDecimal advanceAmount,
                                          EDITBigDecimal debitBalanceAmount,
                                          long checkTo, long placedAgentFK) throws Exception
    {
        if (agent.holdingCommissions()) // This is a required field - nulls not allowed.
        {
            return; // do nothing
        }

        ClientRoleFinancial clientRoleFinancial = null;

        ClientRole redirectClientRole = getRedirect(checkTo);

        // There was a Reversion Redirect
        if (redirectClientRole != null &&
            redirectClientRole.getClientRoleFinancial() != null)
        {
            PlacedAgent placedAgent = PlacedAgent.findBy_PK(new Long(placedAgentFK));
            
            ClientRole agentClientRole = ClientRole.findByPK(placedAgent.getClientRoleFK());

            ClientRoleFinancial agentClientRoleFinancial = agentClientRole.getClientRoleFinancial();

            clientRoleFinancial = redirectClientRole.getClientRoleFinancial();

            clientRoleFinancial.addToCommissionBalance(commissionAmount, commissionTypeCT);
            clientRoleFinancial.updateAmountTaxable(commissionTaxable, commissionTypeCT);

            //The advance and advance recovery needs to be updated on the original agent (not the reversion agent)
            agentClientRoleFinancial.updateAdvanceAndRecovery(advanceAmount, commissionTypeCT);
        }

        // There was no Redirect or the redirect was an assignee so get the current Agent's ClientRoleFinancial
        else
        {
            PlacedAgent placedAgent = PlacedAgent.findBy_PK(new Long(placedAgentFK));
            
            ClientRole clientRole = ClientRole.findByPK(placedAgent.getClientRoleFK());

            clientRoleFinancial = clientRole.getClientRoleFinancial();

            if (checkTo > 0)
            {
                clientRoleFinancial.addToRedirectBalance(commissionAmount, commissionTypeCT);
                clientRoleFinancial.updateAdvanceAndRecovery(advanceAmount, commissionTypeCT);
            }
            else
            {
                clientRoleFinancial.addToCommissionBalance(commissionAmount, commissionTypeCT);
                clientRoleFinancial.updateAdvanceAndRecovery(advanceAmount, commissionTypeCT);
                clientRoleFinancial.updateAmountTaxable(commissionTaxable, commissionTypeCT);
                clientRoleFinancial.updateCumFields(commissionAmount, commissionTypeCT);

                if (!debitBalanceAmount.isEQ("0"))
                {
                    clientRoleFinancial.subtractToDBAmount(debitBalanceAmount);
                }
            }
        }
    }

    // protected void updateBonusCommissions(Agent agent,
    //                                       double commissionAmount,
    //                                        String redirectEffectiveDate,
    //                                         double commissionTaxable,
    //                                          String commissionTypeCT) throws Exception
    protected void updateBonusCommissions(Agent agent,
                                          EDITBigDecimal commissionAmount,
                                          String redirectEffectiveDate,
                                          EDITBigDecimal commissionTaxable,
                                          String commissionTypeCT,
                                          long placedAgentFK) throws Exception
    {
        if (agent.holdingCommissions()) // This is a required field - nulls not allowed.
        {
            return; // do nothing
        }

        PlacedAgent placedAgent = PlacedAgent.findBy_PK(new Long(placedAgentFK));

        ClientRole clientRole = ClientRole.findByPK(placedAgent.getClientRoleFK());

        ClientRoleFinancial clientRoleFinancial = clientRole.getClientRoleFinancial();

        clientRoleFinancial.addToBonusCommissionBalance(commissionAmount, commissionTypeCT);

        // Update AmountTaxable
        if (commissionTypeCT.equals("ChargeBack"))
        {
            // clientRoleFinancial.addToAmountTaxableYTD(- commissionTaxable);
            clientRoleFinancial.addToAmountTaxableYTD( commissionTaxable.negate() );
        }
        else
        {
            clientRoleFinancial.addToAmountTaxableYTD(commissionTaxable);
        }
    }

    protected ClientRole getRedirect(long checkTo)
    {
        ClientRole clientRoleRedirect = null;
        if (checkTo > 0)
        {
            clientRoleRedirect = ClientRole.findBy_ClientRolePK(new Long(checkTo));
        }

        return clientRoleRedirect;
    }

    protected void load(CRUDEntity crudEntity, long pk)
    {
        super.load(crudEntity, pk, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void save(Agent agent) throws Exception
    {
        boolean newAgent = false;
        if (agent.getPK() == 0)
        {
            newAgent = true;
        }

//        super.save(agent, ConnectionFactory.EDITSOLUTIONS_POOL, false);

        saveChildren(agent, newAgent);
    }

    private void saveChildren(Agent agent, boolean newAgent) throws Exception
    {
        AgentVO agentVO = (AgentVO) agent.getVO();

        if (agentVO.getAgentContractVOCount() > 0)
        {
            AgentContractVO[] agentContractVO = agentVO.getAgentContractVO();

            for (int i = 0; i < agentContractVO.length; i++)
            {
                if (!agentContractVO[i].getVoShouldBeDeleted())
                {
                    AgentContract agentContract =  new AgentContract(agentContractVO[i]);
                    agentContract.associateAgent(agent);
                }
                else
                {
                    agent.removeAgentContract(new AgentContract(agentContractVO[i]));
                }
            }
        }

        if (agentVO.getAgentLicenseVOCount() > 0)
        {
            AgentLicenseVO[] agentLicenseVO = agentVO.getAgentLicenseVO();

            for (int i = 0; i < agentLicenseVO.length; i ++)
            {
                AgentLicense agentLicense = new AgentLicense(agentLicenseVO[i]);
                if (agentLicenseVO[i].getVoShouldBeDeleted())
                {
                    agentLicense.delete();
                }
                else
                {
                    agentLicense.setOperator(agentVO.getOperator());
    //                agentLicense.associateAgent(agent);
                    agentLicense.setAgent(agent);
                    agentLicense.setNewAgentInd(newAgent);
                    agentLicense.save();
                }
            }
        }

        if (agentVO.getAgentNoteVOCount() > 0)
        {
            AgentNoteVO[] agentNoteVO = agentVO.getAgentNoteVO();

            for (int i = 0; i < agentNoteVO.length; i++)
            {
                AgentNote agentNote = new AgentNote(agentNoteVO[i]);

                if (!agentNoteVO[i].getVoShouldBeDeleted())
                {
                    agentNote.associateAgent(agent);
                }
                else
                {
                    agentNote.hDelete();
                }
            }
        }

        if (agentVO.getCheckAdjustmentVOCount() > 0)
        {
            CheckAdjustmentVO[] checkAdjustmentVO = agentVO.getCheckAdjustmentVO();

            for (int i = 0; i < checkAdjustmentVO.length; i++)
            {
                CheckAdjustment checkAdjustment = new CheckAdjustment(checkAdjustmentVO[i]);
                
                if (!checkAdjustmentVO[i].getVoShouldBeDeleted())
                {
//                  updateForDebitAutoRepay(checkAdjustment, agentVO.getClientRoleFK());
                    checkAdjustment.associateAgent(agent);

                    Long placedAgentFK = checkAdjustmentVO[i].getPlacedAgentFK();
                    PlacedAgent placedAgent = PlacedAgent.findByPK(placedAgentFK);

                    checkAdjustment.associatePlacedAgent(placedAgent);
//                  checkAdjustment.save();
                }
                else
                {
                    checkAdjustment.hDelete();                
                }
            }
        }

        if (agentVO.getRedirectVOCount() > 0)
        {
            RedirectVO[] redirectVO = agentVO.getRedirectVO();

            for (int i = 0; i < redirectVO.length; i++)
            {
                Redirect redirect = new Redirect(redirectVO[i]);
                redirect.associateAgent(agent);
//                redirect.save();
            }
        }

        if (agentVO.getVestingVOCount() > 0)
        {
            VestingVO[] vestingVO = agentVO.getVestingVO();

            for (int i = 0; i < vestingVO.length; i++)
            {
                Vesting vesting = new Vesting(vestingVO[i]);
                vesting.associateAgent(agent);
//                vesting.save();
            }
        }

        if (agentVO.getClientRoleVOCount() > 0)
        {
            ClientRoleVO[] clientRoleVO = agentVO.getClientRoleVO();

            for (int i = 0; i < clientRoleVO.length; i++)
            {
                ClientRole clientRole = (ClientRole) SessionHelper.mapVOToHibernateEntity(clientRoleVO[i], SessionHelper.EDITSOLUTIONS);
                ClientDetail clientDetail = new ClientDetail((ClientDetailVO) clientRoleVO[i].getParentVO(ClientDetailVO.class));
                clientRole.setClientDetail(clientDetail);
                if (clientRoleVO[i].getParentVO(PreferenceVO.class) != null)
                {
                    Preference preference = new Preference((PreferenceVO) clientRoleVO[i].getParentVO(PreferenceVO.class));
                    clientRole.setPreference(preference);
                }

                if (clientRoleVO[i].getParentVO(TaxProfileVO.class) != null)
                {
                    TaxProfile taxProfile = new TaxProfile((TaxProfileVO) clientRoleVO[i].getParentVO(TaxProfileVO.class));
                    clientRole.setTaxProfile(taxProfile);
                }
                
                clientRole.associateAgent(agent);
            }
        }
    }

    private void updateForDebitAutoRepay(CheckAdjustment checkAdjustment, long clientRoleFK)
    {

        String adjustmentType = checkAdjustment.getAdjustmentTypeCT();
        if (adjustmentType.equalsIgnoreCase(CheckAdjustment.DEBIT_BALANCE_AUTO_REPAY))
        {
            checkAdjustment.updateForAutoRepay(clientRoleFK);    
        }
    }

    protected void delete(CRUDEntity crudEntity) throws Exception
    {
        AgentContract[] agentContract = AgentContract.findByAgentPK(crudEntity.getPK());
        boolean hasPlacedAgent = false;
        if (agentContract != null)
        {
            for (int c = 0; c < agentContract.length; c++)
            {
                if (agentContract[c].hasPlacedAgent())
                {
                    hasPlacedAgent = true;
                }
            }
        }
        if (hasPlacedAgent)
        {
            throw new EDITDeleteException("Agent Has Been Placed - Cannot Be Deleted");
        }
        else
        {
            deleteChildren(crudEntity);
            super.delete(crudEntity, ConnectionFactory.EDITSOLUTIONS_POOL);
        }
    }

    private void deleteChildren(CRUDEntity crudEntity) throws Exception
    {
        AgentVO agentVO = (AgentVO) crudEntity.getVO();

        if (agentVO.getAgentContractVOCount() > 0)
        {
            AgentContractVO[] agentContractVO = agentVO.getAgentContractVO();

            for (int i = 0; i < agentContractVO.length; i++)
            {
                AgentContract agentContract =  new AgentContract(agentContractVO[i]);
                agentContract.delete();
            }
        }

        if (agentVO.getAgentLicenseVOCount() > 0)
        {
            AgentLicenseVO[] agentLicenseVO = agentVO.getAgentLicenseVO();

            for (int i = 0; i < agentLicenseVO.length; i ++)
            {
                AgentLicense agentLicense = new AgentLicense(agentLicenseVO[i]);
                agentLicense.delete();
            }
        }

        if (agentVO.getAgentNoteVOCount() > 0)
        {
            AgentNoteVO[] agentNoteVO = agentVO.getAgentNoteVO();

            for (int i = 0; i < agentNoteVO.length; i++)
            {
                AgentNote agentNote = new AgentNote(agentNoteVO[i]);
                agentNote.delete();
            }
        }

        if (agentVO.getCheckAdjustmentVOCount() > 0)
        {
            CheckAdjustmentVO[] checkAdjustmentVO = agentVO.getCheckAdjustmentVO();

            for (int i = 0; i < checkAdjustmentVO.length; i++)
            {
                CheckAdjustment checkAdjustment = new CheckAdjustment(checkAdjustmentVO[i]);
                checkAdjustment.delete();
            }
        }

        if (agentVO.getRedirectVOCount() > 0)
        {
            RedirectVO[] redirectVO = agentVO.getRedirectVO();

            for (int i = 0; i < redirectVO.length; i++)
            {
                Redirect redirect = new Redirect(redirectVO[i]);
                redirect.delete();
            }
        }

        if (agentVO.getVestingVOCount() > 0)
        {
            VestingVO[] vestingVO = agentVO.getVestingVO();

            for (int i = 0; i < vestingVO.length; i++)
            {
                Vesting vesting = new Vesting(vestingVO[i]);
                vesting.delete();
            }
        }

        AgentRequirement[] agentRequirements = AgentRequirement.findByAgent(new Agent(agentVO));
        if (agentRequirements != null)
        {
            for (int i = 0; i < agentRequirements.length; i++)
            {
                agentRequirements[i].delete();
            }
        }
    }

    public void validateAgentLicense(Agent agent,
                                     String trxEffectiveDate,
                                     String issueState,
                                     long productStructureFK,
                                     String enrollmentMethod,
                                     String productType) throws EDITValidationException
    {
        if (performAgentLicensing(trxEffectiveDate, issueState, productStructureFK))
        {
            AgentVO agentVO = (AgentVO) agent.getVO();

            if (!agentVO.getAgentStatusCT().equalsIgnoreCase("Active"))
            {
                throw new EDITValidationException(Constants.ErrorMsg.AGENT_IS_NOT_ACTIVE);
            }

            try
            {
                AgentLicense[] agentLicense = AgentLicense.findByAgentPK_AND_IssueState(agent.getPK(), issueState);

                if (agentLicense == null)
                {
                    throw new EDITValidationException(Constants.ErrorMsg.AGENT_LICENSE_DOES_NOT_EXIST_FOR_APPROPRIATE_STATE);
                }
                else
                {
                    agentLicense[0].validateLicense(trxEffectiveDate, issueState, productStructureFK); // Should only be one AgentLicense by this criteria.
                }
            }
            catch (Exception e)
            {
                System.out.println(e);
            
                e.printStackTrace();  //To change body of catch statement use Options | File Templates.

                throw new EDITValidationException(e.getMessage());
            }
        }
    }

    /**
     * Get the list of transactions (if any) that need to be generated at the time the
     * contract is issued.
     * @param segmentVO
     * @return
     */
    public boolean performAgentLicensing(String trxEffDate, String issueState, long productStructureFK)
    {
        String grouping = "AUTOISSUE";

        EDITDate effectiveDate = new EDITDate(trxEffDate);
        String qualifierCT = "*";
        String field = "CHECKAGENTLICENSE";

        Area area = new Area(productStructureFK, issueState, grouping, effectiveDate, qualifierCT);

        AreaValue areaValue = area.getAreaValue(field);

        boolean checkAgentLicense = true;

        if (areaValue != null)
        {
            AreaValueVO areaValueVO = (AreaValueVO) areaValue.getVO();
            if (areaValueVO.getAreaValue().equalsIgnoreCase("N"))
            {
                checkAgentLicense = false;
            }
        }

        return checkAgentLicense;
    }
}
