package edit.portal.common.session;

import agent.business.*;
import agent.component.*;
import client.business.*;
import client.component.*;
import contract.*;
import contract.business.*;
import contract.component.*;
import edit.common.*;
import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.services.db.hibernate.*;
import engine.*;
import engine.business.*;
import engine.component.*;
import event.business.*;
import event.component.*;
import fission.utility.*;
import org.hibernate.*;
import role.business.Role;
import role.component.*;
import security.*;
import security.business.*;
import security.component.*;

import javax.servlet.http.*;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 18, 2004
 * Time: 11:34:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserSession implements HttpSessionBindingListener
{
    private Cloudland cloudland;

    private String sessionId;

    private String username;
    private String contractNumber;
    private String segmentName;

    private long lockedSegmentPK;
    private boolean segmentIsLocked;
    private boolean editableSegmentStatus;

    private long lockedClientDetailPK;
    private boolean clientDetailIsLocked;

    private long lockedAgentPK;
    private boolean agentIsLocked;

    private long lockedClientRolePK;
    private boolean clientRoleIsLocked;

    private long lockedCashBatchContractPK;
    private boolean cashBatchContractIsLocked;

    private long lockedSuspensePK;
    private boolean suspenseIsLocked;

    private long lockedScriptPK;
    private boolean scriptIsLocked;

    private long lockedReinsurerPK;
    private boolean reinsurerIsLocked;

    private long lockedCasePK;
    private boolean caseIsLocked;

    private long lockedGroupPK;
    private boolean groupIsLocked;

    private long lockedCaseRequirementPK;
    private boolean caseRequirementIsLocked;

    private long lockedCaseAgentPK;
    private boolean caseAgentIsLocked;

    private HttpSession httpSession;
    private DepositsVO[] depositsVOs;

    private Queue<String> searchHistory;

    private ProductStructure currentProductStructure;
    
    /**
     * A user's session may have to retain flags/markers while traversing
     * a set of stateful pages (typically tabs in our system).
     * Since the the user's session is itself stateful, this is an ideal
     * place to localize such parameter values.
     * 
     * There are some useful features. A parameter name can be compound. Example:
     * 
     * A.B.name
     * 
     * This suggests that parameters can be naturally grouped. Example:
     * 
     * A.B.name1
     * A.B.name2
     * A.C.name1
     * A.C.name2
     * D.E.name1
     * D.E.name2
     * 
     * As such, the method of "clearParameters(parameterRoot)" allows for select
     * clearing. Example:
     * 
     * clearParameters("A.B") would leave A.C.name1, A.C.name2, D.E.name1, D.E.name2
     * in the parameter list.
     * 
     * clearParameters("A") would leave D.E.name1 and D.E.name2 in the parameter list.
     * 
     * @see #clearParameters(String)
     * @see #clearParameters()
     * @see #clearParameter(String)
     */
    private Map<String, String> parameters = new HashMap<String, String>();

    /**
     * The union of all product sturctures for the operators roles.
     * The user may not be able to do particular use cases for one of
     * these allowable product structures.
     */
    private ProductStructure[] allowableProductStructuresForUser;

    public UserSession(String username, HttpSession httpSession)
    {
        this.username = username;

        this.httpSession = httpSession;

        cloudland = new Cloudland();
        
        searchHistory = new ArrayDeque<String>();
    }

    private void invalidate()
    {
        sessionId = null;

        username = null;
    }

    public void setSegmentPK(long lockedSegmentPK)
    {
        // This is set after a search and select for quote detail tran
        // and for contract detail tran.
        // Set the associated product structure at the same time.
        // This way the current product structure for security
        // will be in sync.
        if (lockedSegmentPK > 0)
        {
            Segment s = new Segment(lockedSegmentPK);
            long productStructureFK = s.getProductStructureFK().longValue();
            setCurrentProductStructurePK(productStructureFK);
            
            this.editableSegmentStatus = verifyEditableSegmentStatus(lockedSegmentPK);
            
            this.segmentName = lookupSegmentName(lockedSegmentPK);
        }

        this.lockedSegmentPK = lockedSegmentPK;
    }

    public void setClientDetailPK(long lockedClientDetailPK)
    {
        this.lockedClientDetailPK = lockedClientDetailPK;
    }

    public void setAgentPK(long lockedAgentPK)
    {
        this.lockedAgentPK = lockedAgentPK;
    }

    public void setClientRolePK(long lockedClientRolePK)
    {
        this.lockedClientRolePK = lockedClientRolePK;
    }

    public void setCashBatchContractPK(long lockedCashBatchContractPK)
    {
        this.lockedCashBatchContractPK = lockedCashBatchContractPK;
    }

    public void setSuspensePK(long lockedSuspensePK)
    {
        this.lockedSuspensePK = lockedSuspensePK;
    }

    public void setReinsurerPK(long lockedReinsurerPK)
    {
        this.lockedReinsurerPK = lockedReinsurerPK;
    }

    public void setCasePK(long lockedCasePK)
    {
        this.lockedCasePK = lockedCasePK;
    }

    public void setGroupPK(long lockedGroupPK)
    {
        this.lockedGroupPK = lockedGroupPK;
    }

    public void setCaseRequirementPK(long lockedCaseRequirementPK)
    {
        this.lockedCaseRequirementPK = lockedCaseRequirementPK;
    }

    public void setCaseAgentPK(long lockedCaseAgentPK)
    {
        this.lockedCaseAgentPK = lockedCaseAgentPK;
    }

    public String getUsername()
    {
        return username;
    }

    public String getSessionId()
    {
        return sessionId;
    }

    public boolean userLoggedIn()
    {
        return (sessionId != null);
    }

    public void unlock()
    {
        if (getSegmentIsLocked())
        {
            this.unlockSegment();
        }
        else
        {
            setSegmentPK(0);
        }

        if (getClientDetailIsLocked())
        {
            this.unlockClientDetail();
        }
        else
        {
            setClientDetailPK(0);
        }

        if (getAgentIsLocked())
        {
            this.unlockAgent();
        }
        else
        {
            setAgentPK(0);
        }

        if (getClientRoleIsLocked())
        {
            this.unlockClientRole();
        }
        else
        {
            setClientRolePK(0);
        }

        if (getCashBatchContractIsLocked())
        {
            this.unlockCashBatchContract();
        }
        else
        {
            setCashBatchContractPK(0);
        }

        if (getSuspenseIsLocked())
        {
            this.unlockSuspense();
        }
        else
        {
            setSuspensePK(0);
        }

        if (getScriptIsLocked())
        {
            this.unlockScript();
        }
        else
        {
            setScriptPK(0);
        }

        if (getReinsurerIsLocked())
        {
            this.unlockReinsurer();
        }
        else
        {
            setReinsurerPK(0);
        }

        if (getCaseIsLocked())
        {
            this.unlockCase();
        }
        else
        {
            setCasePK(0);
            setParameter("activeCasePK", null);
            setParameter("activeCaseClientDetailPK", null);
        }

        if (getGroupIsLocked())
        {
            this.unlockGroup();
        }
        else
        {
            setGroupPK(0);
        }

        if (getCaseRequirementIsLocked())
        {
            this.unlockCaseRequirement();
        }
        else
        {
            setCaseRequirementPK(0);
        }

        if (getCaseAgentIsLocked())
        {
            this.unlockCaseAgent();
        }
        else
        {
            setCaseAgentPK(0);
        }
    }

    public void lockSegment(long segmentPK) throws EDITLockException
    {
        try
        {
            if (segmentPK != 0)
            {
                Contract contractComponent = new ContractComponent();

                contractComponent.lockElement(segmentPK, username);

                //this.lockedSegmentPK = segmentPK;

                //this.segmentIsLocked = true;
                
                //this.editableSegmentStatus = verifyEditableSegmentStatus(segmentPK);
            }
            /*else
            {*/
                this.lockedSegmentPK = segmentPK;

                this.segmentIsLocked = true;
                
                this.editableSegmentStatus = verifyEditableSegmentStatus(segmentPK);
                
                this.segmentName = lookupSegmentName(segmentPK);
            //}
        }
        catch (EDITLockException e)
        {
            throw e;
        }
    }
    
    public boolean verifyEditableSegmentStatus(long segmentPK)
    {
    	/*String status = Segment.getSegmentStatusBySegmentPK(segmentPK);
    	
    	if (status.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_LAPSE) || 
    			status.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_SURRENDERED) || 
    			status.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_REDUCED_PAIDUP) || 
    			status.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_DEATH) || 
    			status.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_FROZEN) || 
    			status.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_DECLINED) || 
    			status.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_DECLINEDMED) || 
    			status.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_DECLINEELIG) || 
    			status.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_DECLINEREQ) || 
    			status.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_INCOMPLETE) || 
    			status.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_NOT_TAKEN) || 
    			status.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_POSTPONED) || 
    			status.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_TERMINATED) ||
    			status.equalsIgnoreCase(Segment.SEGMENTSTATUSCT_WITHDRAWN) ) 
    	{
    		return false;
    		
    	} else {
    		return true;
    	}*/
    	
    	return true;
    }
    
    public String lookupSegmentName(long segmentPK)
    {
    	return Segment.getSegmentNameBySegmentPK(segmentPK);
    }

    public void unlockSegment()
    {
        if (segmentIsLocked)
        {
            if (lockedSegmentPK != 0)
            {
                Contract contractComponent = new ContractComponent();
                contractComponent.unlockElement(lockedSegmentPK);
            }

            resetSuspenseEntries();
        }

        this.segmentIsLocked = false;
    }

    public long getSegmentPK()
    {
        return this.lockedSegmentPK;
    }

    public void lockClientDetail(long clientDetailPK) throws EDITLockException
    {
        try
        {
            if (clientDetailPK != 0)
            {
                Client clientComponent = new ClientComponent();

                clientComponent.lockElement(clientDetailPK, username);

                this.lockedClientDetailPK = clientDetailPK;

                this.clientDetailIsLocked = true;
            }
            else
            {
                this.lockedClientDetailPK = clientDetailPK;

                this.clientDetailIsLocked = true;
            }
        }
        catch (EDITLockException e)
        {
            throw (e);
        }
    }

    public void unlockClientDetail()
    {
        if (lockedClientDetailPK != 0 && clientDetailIsLocked)
        {
            Client clientComponent = new ClientComponent();

            clientComponent.unlockElement(lockedClientDetailPK);
        }

        this.clientDetailIsLocked = false;
        this.lockedClientDetailPK = 0;
    }

    public long getClientDetailPK()
    {
        return this.lockedClientDetailPK;
    }

    public void lockAgent(long agentPK) throws EDITLockException
    {
        try
        {
            if (agentPK != 0)
            {
                Agent agent = new AgentComponent();

                agent.lockElement(agentPK, username);

                this.lockedAgentPK = agentPK;

                this.agentIsLocked = true;
            }
            else
            {
                this.lockedAgentPK = agentPK;

                this.agentIsLocked = true;
            }
        }
        catch (EDITLockException e)
        {
            throw e;
        }
    }

    public void unlockAgent()
    {
        if (lockedAgentPK != 0 && agentIsLocked)
        {
            Agent agent = new AgentComponent();

            agent.unlockElement(lockedAgentPK);
        }

        this.agentIsLocked = false;
        this.lockedAgentPK = 0;
    }

    public long getAgentPK()
    {
        return this.lockedAgentPK;
    }

    public void lockClientRole(long clientRolePK) throws EDITLockException
    {
        try
        {
            if (clientRolePK != 0)
            {
                Role role = new RoleComponent();

                role.lockElement(clientRolePK, username);

                this.lockedClientRolePK = clientRolePK;

                this.clientRoleIsLocked = true;
            }
            else
            {
                this.lockedClientRolePK = clientRolePK;

                this.clientRoleIsLocked = true;
            }
        }
        catch (EDITLockException e)
        {
            throw (e);
        }
    }

    public void unlockClientRole()
    {
        if (lockedClientRolePK != 0 && clientRoleIsLocked)
        {
            Role role = new RoleComponent();

            role.unlockElement(lockedClientRolePK);
        }

        this.clientRoleIsLocked = false;
        this.lockedClientRolePK = 0;
    }

    public long getClientRolePK()
    {
        return this.lockedClientRolePK;
    }

    public void lockCashBatchContract(long cashBatchContractPK) throws EDITLockException
    {
        try
        {
            if (cashBatchContractPK != 0)
            {
                Event event = new EventComponent();
                event.lockElement(cashBatchContractPK, username);

                this.lockedCashBatchContractPK = cashBatchContractPK;
                this.cashBatchContractIsLocked = true;
            }
            else
            {
                this.lockedCashBatchContractPK = cashBatchContractPK;
                this.cashBatchContractIsLocked = true;
            }
        }
        catch (EDITLockException e)
        {
            throw (e);
        }
    }

    public void unlockCashBatchContract()
    {
        if (lockedCashBatchContractPK != 0 && cashBatchContractIsLocked)
        {
            Event event = new EventComponent();
            event.unlockElement(lockedCashBatchContractPK);
        }

        this.cashBatchContractIsLocked = false;
        this.lockedCashBatchContractPK = 0;
    }

    public long getCashBatchContractPK()
    {
        return this.lockedCashBatchContractPK;
    }

    public void lockSuspense(long suspensePK) throws EDITLockException
    {
        try
        {
            if (suspensePK != 0)
            {
                Event event = new EventComponent();
                event.lockElement(suspensePK, username);

                this.lockedSuspensePK = suspensePK;
                this.suspenseIsLocked = true;
            }
            else
            {
                this.lockedSuspensePK = suspensePK;
                this.suspenseIsLocked = true;
            }
        }
        catch (EDITLockException e)
        {
            throw (e);
        }
    }

    public void unlockSuspense()
    {
        if (lockedSuspensePK != 0 && suspenseIsLocked)
        {
            Event event = new EventComponent();
            event.unlockElement(lockedSuspensePK);
        }

        this.suspenseIsLocked = false;
        this.lockedSuspensePK = 0;
    }

    public long getSuspensePK()
    {
        return this.lockedSuspensePK;
    }

    public void login(String password) throws EDITSecurityException
    {
        if (! userLoggedIn())
        {
            Security securityComponent = new SecurityComponent();

            String sessionId = securityComponent.login(username, password);

            this.sessionId = sessionId;
        }
    }

    public void logout()
    {
        if (userLoggedIn())
        {
            unlock();

            Security securityComponent = new SecurityComponent();

            securityComponent.logout(sessionId);

            invalidate();

            SessionHelper.closeSessions();

            httpSession.invalidate();
        }
    }

    public void valueBound(HttpSessionBindingEvent event)
    {
    }

    public void valueUnbound(HttpSessionBindingEvent event)
    {
        if (userLoggedIn())
        {
            logout();
        }
    }

    public boolean getSegmentIsLocked()
    {
        return this.segmentIsLocked;
    }
    
    public boolean getEditableSegmentStatus()
    {
        return this.editableSegmentStatus;
    }

    public boolean getClientDetailIsLocked()
    {
        return this.clientDetailIsLocked;
    }

    public boolean getAgentIsLocked()
    {
        return this.agentIsLocked;
    }

    public boolean getClientRoleIsLocked()
    {
        return this.clientRoleIsLocked;
    }

    public boolean getCashBatchContractIsLocked()
    {
        return this.cashBatchContractIsLocked;
    }

    public boolean getSuspenseIsLocked()
    {
        return this.suspenseIsLocked;
    }

    public void resetAgentBoolean()
    {
        this.agentIsLocked = false;
    }

    public void lockScript(long scriptPK) throws EDITLockException
    {
         try
        {
            if (scriptPK != 0)
            {
                Calculator calculatorComponent = new CalculatorComponent();
                calculatorComponent.lockElement(scriptPK, username);

                this.lockedScriptPK = scriptPK;
                this.scriptIsLocked = true;
            }
            else
            {
                this.lockedScriptPK = scriptPK;
                this.scriptIsLocked = true;
            }
        }
        catch (EDITLockException e)
        {
            throw (e);
        }
    }

    public void unlockScript()
    {
        if (lockedScriptPK != 0 && scriptIsLocked)
        {
            Calculator calculatorComponent = new CalculatorComponent();
            calculatorComponent.unlockElement(lockedScriptPK);
        }

        this.scriptIsLocked = false;
        this.lockedScriptPK = 0;
    }

    public long getScriptPK()
    {
        return this.lockedScriptPK;
    }

    public void setScriptPK(long lockedscriptPK)
    {
        this.lockedScriptPK = lockedscriptPK;
    }

    public boolean getScriptIsLocked()
    {
        return this.scriptIsLocked;
    }

    public boolean getReinsurerIsLocked()
    {
        return this.reinsurerIsLocked;
    }

    public boolean getCaseIsLocked()
    {
        return this.caseIsLocked;
    }

    public boolean getGroupIsLocked()
    {
        return this.groupIsLocked;
    }

    public boolean getCaseRequirementIsLocked()
    {
        return this.caseRequirementIsLocked;
    }

    public boolean getCaseAgentIsLocked()
    {
        return this.caseAgentIsLocked;
    }

    public void lockReinsurer(long reinsurerPK) throws EDITLockException
    {
        try
        {
            if (reinsurerPK != 0)
            {
                reinsurance.business.Reinsurance reinsuranceComponent = new reinsurance.component.ReinsuranceComponent();

                reinsuranceComponent.lockElement(reinsurerPK, username);

                this.lockedReinsurerPK = reinsurerPK;

                this.reinsurerIsLocked = true;
            }
            else
            {
                this.lockedReinsurerPK = reinsurerPK;

                this.reinsurerIsLocked = true;
            }
        }
        catch (EDITLockException e)
        {
            throw (e);
        }
    }

    public void unlockReinsurer()
    {
        if (lockedReinsurerPK != 0 && reinsurerIsLocked)
        {
            reinsurance.business.Reinsurance reinsuranceComponent = new reinsurance.component.ReinsuranceComponent();

            reinsuranceComponent.unlockElement(lockedReinsurerPK);
        }

        this.reinsurerIsLocked = false;
        this.lockedReinsurerPK = 0;
    }

    public long getReinsurerPK()
    {
        return this.lockedReinsurerPK;
    }

    public void lockCase(long casePK) throws EDITLockException
    {
        try
        {
            if (casePK != 0)
            {
                contract.business.Contract contractComponent = new contract.component.ContractComponent();

                contractComponent.lockElement(casePK, username);

                this.lockedCasePK = casePK;

                this.caseIsLocked = true;
            }
            else
            {
                this.lockedCasePK = casePK;

                this.caseIsLocked = true;
            }
        }
        catch (EDITLockException e)
        {
            throw (e);
        }
    }


   public void unlockCase()
    {
        if (lockedCasePK != 0 && caseIsLocked)
        {
            contract.business.Contract contractComponent = new contract.component.ContractComponent();

            contractComponent.unlockElement(lockedCasePK);
        }

        this.caseIsLocked = false;
        this.lockedCasePK = 0;
    }

    public long getCasePK()
    {
        return this.lockedCasePK;
    }

    public void lockGroup(long groupPK) throws EDITLockException
    {
        try
        {
            if (groupPK != 0)
            {
                contract.business.Contract contractComponent = new contract.component.ContractComponent();

                contractComponent.lockElement(groupPK, username);

                this.lockedGroupPK = groupPK;

                this.groupIsLocked = true;
            }
            else
            {
                this.lockedGroupPK = groupPK;

                this.groupIsLocked = true;
            }
        }
        catch (EDITLockException e)
        {
            throw (e);
        }
    }


   public void unlockGroup()
    {
        if (lockedGroupPK != 0 && groupIsLocked)
        {
            contract.business.Contract contractComponent = new contract.component.ContractComponent();

            contractComponent.unlockElement(lockedGroupPK);
        }

        this.groupIsLocked = false;
        this.lockedGroupPK = 0;
    }

    public long getGroupPK()
    {
        return this.lockedGroupPK;
    }


    public void lockCaseRequirement(long caseRequirementPK) throws EDITLockException
    {
        try
        {
            if (caseRequirementPK != 0)
            {
                contract.business.Contract contractComponent = new contract.component.ContractComponent();

                contractComponent.lockElement(caseRequirementPK, username);

                this.lockedCaseRequirementPK = caseRequirementPK;

                this.caseRequirementIsLocked = true;
            }
            else
            {
                this.lockedCaseRequirementPK = caseRequirementPK;

                this.caseRequirementIsLocked = true;
            }
        }
        catch (EDITLockException e)
        {
            throw (e);
        }
    }

   public void unlockCaseRequirement()
    {
        if (lockedCaseRequirementPK != 0 && caseRequirementIsLocked)
        {
            contract.business.Contract contractComponent = new contract.component.ContractComponent();

            contractComponent.unlockElement(lockedCaseRequirementPK);
        }

        this.caseRequirementIsLocked = false;
        this.lockedCaseRequirementPK = 0;
    }

    public long getCaseRequirementPK()
    {
        return this.lockedCaseRequirementPK;
    }

    public void lockCaseAgent(long caseAgentPK) throws EDITLockException
    {
        try
        {
            if (caseAgentPK != 0)
            {
                contract.business.Contract contractComponent = new contract.component.ContractComponent();

                contractComponent.lockElement(caseAgentPK, username);

                this.lockedCaseAgentPK = caseAgentPK;

                this.caseAgentIsLocked = true;
            }
            else
            {
                this.lockedCaseAgentPK = caseAgentPK;

                this.caseAgentIsLocked = true;
            }
        }
        catch (EDITLockException e)
        {
            throw (e);
        }
    }


   public void unlockCaseAgent()
    {
        if (lockedCaseAgentPK != 0 && caseAgentIsLocked)
        {
            contract.business.Contract contractComponent = new contract.component.ContractComponent();

            contractComponent.unlockElement(lockedCaseAgentPK);
        }

        this.caseAgentIsLocked = false;
        this.lockedCaseAgentPK = 0;
    }

    public long getCaseAgentPK()
    {
        return this.lockedCaseAgentPK;
    }

    /**
     * Returns the dedicated instance of this user's Cloudland.
     * @return
     */
    public Cloudland getCloudland()
    {
        return cloudland;        
    }

    /**
     * Sets the Contract Number
     * @param contractNumber
     */
    public void setContractNumber(String contractNumber)
    {
        this.contractNumber = contractNumber;
    }

    /**
     * Returns the Contract Number
     * @return
     */
    public String getContractNumber()
    {
        return this.contractNumber;
    }
    
    /**
     * Sets the Contract Number
     * @param contractNumber
     */
    public void setSegmentName(String segmentName)
    {
        this.segmentName = segmentName;
    }

    /**
     * Returns the Contract Number
     * @return
     */
    public String getSegmentName()
    {
        return this.segmentName;
    }

    /**
     * Setter.
     * @param depositsVOs
     */
    public void setDepositsVO(DepositsVO[] depositsVOs)
    {
        this.depositsVOs = depositsVOs;
    }

    /**
     * Getter.
     * @return
     */
    public DepositsVO[] getDepositsVO()
    {
        return this.depositsVOs;
    }

    /**
     * Resets the suspense amounts when session gets invalidated.
     */
    private void resetSuspenseEntries()
    {
        try
        {
            if (depositsVOs != null)
            {
                for (int i = 0; i < depositsVOs.length; i++)
                {
                    if (depositsVOs[i].getDepositsPK() < 0)
                    {
                        Event eventComponent = new EventComponent();

                        if (depositsVOs[i].getSuspenseFK() > 0)
                        {
                            SuspenseVO suspenseVO = eventComponent.composeSuspenseVO(depositsVOs[i].getSuspenseFK(), new ArrayList());

                            if (suspenseVO != null)
                            {
                                EDITBigDecimal  pendingAmt = Util.roundToNearestCent(suspenseVO.getPendingSuspenseAmount());
                                pendingAmt = pendingAmt.subtractEditBigDecimal(Util.roundToNearestCent(depositsVOs[i].getAmountReceived()));
                                suspenseVO.setPendingSuspenseAmount(Util.roundToNearestCent(pendingAmt).getBigDecimal());
                                eventComponent.saveSuspenseNonRecursively(suspenseVO);
                            }
                        }
                    }
                }

                depositsVOs = null;
            }
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }
    }

    public void setCurrentProductStructure(ProductStructure aProductStructure)
    {
        this.currentProductStructure = aProductStructure;
    }

    /**
     * A convenience method for Tran's to use.
     * @param aProductStructurePK
     */
    public void setCurrentProductStructurePK(long aProductStructurePK)
    {
        // Short-circuit if it is 0 even tho the code would loop past it.
        if (aProductStructurePK == 0)  return;

        ProductStructure[] productStructures = getProductStucturesForUser();
        for (int i = 0; i < productStructures.length; i++)
        {
            ProductStructure productStructure = productStructures[i];
            long productStructurePK = productStructure.getPK();
            if (productStructurePK == aProductStructurePK)
            {
                setCurrentProductStructure(productStructure);
                return;
            }
        }

        // trying to set a non-zero product structure PK but it is
        // not in the allowable set of product structures.
        // throw a security exception
        ProductStructure productStructure = new ProductStructure(aProductStructurePK);

        ProductStructureVO productStructureVO = (ProductStructureVO) productStructure.getVO();

        String nameSwitchingTo = productStructureVO.getBusinessContractName();

        String detailedMessage = "You have no security for product structure " +
                nameSwitchingTo;

        EDITSecurityAccessException e = new EDITSecurityAccessException(
            detailedMessage);

        e.setErrorType(EDITSecurityAccessException.COMPANY_STRUCTURE_NOT_SET_EXCEPTION);

        throw e;

    }

    /**
     * Return the current product structure that the user is using.
     * <p>
     * If the user has no current product structure, find the first one
     * in his allowable product structures and assign that.  This handles
     * the issue of what product they have when logging on before they
     * can get to a screen that will allow them to set a different
     * current product structure.
     * @return
     */
    public ProductStructure getCurrentProductStructure()
    {
        // the operator could have a default product
        // structure column in the future.  Now we will default
        // to using the first one for which the operator has some
        // security.
        if (this.currentProductStructure == null)
        {
            ProductStructure[] productStructures = getProductStucturesForUser();
            if (productStructures != null && productStructures.length > 0)
            {
                // default - use the first one as the default after signon
                this.currentProductStructure = productStructures[0];
            }
        }
        return this.currentProductStructure;
    }

    /**
     * Return back an array of product type product structures for the
     * user.  If we are running in development mode and security is
     * not compiled, then return back an array of all product type
     * product structures.  Note - this array in itself does not
     * control use-cases.  It is only a union of all possible
     * ProductStructure's for which the operator has at least
     * one FilteredRole (and hence at least one SecuredMethod/use case).
     * @return
     */
    public ProductStructure[] getProductStucturesForUser()
    {
        if (this.allowableProductStructuresForUser == null)
        {
            Operator operator = Operator.findByOperatorName(this.username);

            // if operator is null, then we are running in development mode
            // so return back all product structures
            ProductStructure[] productStructures = null;

            if (operator == null) 
            {
                productStructures = ProductStructure.findByTypeCodeforSecurity();
            }
            else
            {
                productStructures = operator.getProductTypeProductStructures();
            }

            if (productStructures != null)
            {

                this.allowableProductStructuresForUser = productStructures;
            }
            else
            {
                // save it with an empty array
                this.allowableProductStructuresForUser = new ProductStructure[0];
            }
        }
        return this.allowableProductStructuresForUser;
    }

    /**
     * Convenience method.
     * @return
     */
    public ProductStructureVO[] getProductStructureVOsForUser()
    {
        return ProductStructure.mapEntityToVO(getProductStucturesForUser());
    }
    
    /**
     * Clears the parameter value of the specified parameterName (case insensitive). The name
     * is expected to be fully qualified (partial compound names are ignored). 
     * Example:
     * 
     * A.B.name1 // a valid parameter
     * 
     * clearParameter(A.B) // request to clear parameter is ignored
     * 
     * clearParameter(A.B.name1) // the parameter is cleared
     * 
     * @param parameterName
     * @see #parameters
     */
    public void clearParameter(String parameterName)
    {
        getParameters().remove(parameterName.toUpperCase());
    }
    
    /**
     * Clears all parameters whose compound name (case insensitive) begins with 
     * the specified partialCompounParameterName. Example;
     * 
     * A.B.C.name1 // a valid parameter
     * A.B.name2 // a valid parameter
     * A.D.name3 // a valid parameter
     * 
     * clearParameters("A.B") // leaves A.D.name3 in the parameters list
     * 
     * @param partialCompoundParameterName
     * @see #parameters
     */
    public void clearParameters(String partialCompoundParameterName)
    {
        partialCompoundParameterName = partialCompoundParameterName.toUpperCase();
    
        Set<String> parameterKeys = getParameters().keySet();        
        
        for (String parameterKey:parameterKeys)
        {
            if (parameterKey.startsWith(partialCompoundParameterName))
            {
                getParameters().remove(parameterKey);
            }
        }
    }

    /**
     * @see #parameters
     * @return
     */
    private Map<String, String> getParameters()
    {
        return parameters;
    }
    
    /**
     * Sets a name/value pairing.
     * 
     * The name may be compound. Example:
     * 
     * setParameter("A.B.name1", "value1");
     * 
     * All parameter names are converted to uppercase for internal processing.
     * If the parameter name/value pair exists, it is replaced.
     * @param parameterName
     * @param parameterValue
     */
    public void setParameter(String parameterName, String parameterValue)
    {
        getParameters().put(parameterName.toUpperCase(), parameterValue);        
    }
    
    /**
     * Clears all parameters stored in this User's session.
     * 
     * @see #parameters
     */
    public void clearParameters()
    {
        getParameters().clear();
    }
    
    /**
     * Gets the single paramter value specified by the parameter name.
     * @param parameterName
     * @return
     */
    public String getParameter(String parameterName)
    {
        return getParameters().get(parameterName.toUpperCase());        
    }
    
    /**
     * Adds to the list of user's five latest searches.
     * @param contractNumber
     */
    public void addSearchHistory(String contractNumber)
    {
    	if(!searchHistory.contains(contractNumber))
    	{
    		if(searchHistory.size() == 5)
    		{
    			searchHistory.remove();
    		}
    		searchHistory.add(contractNumber);
    	}
    }
    
    /**
     * Retrieves user's search history.
     * @return
     */
    public String[] getSearchHistory()
    {
    	return searchHistory.toArray(new String[searchHistory.size()]);
    	//return (String[])searchHistory.toArray();
    }
}
