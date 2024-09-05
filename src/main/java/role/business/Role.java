/*
 * User: unknown
 * Date: Aug 26, 2003
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package role.business;

import edit.common.vo.ClientRoleFinancialVO;
import edit.common.vo.ClientRoleVO;
import edit.common.EDITDateTime;
import edit.services.component.ICRUD;
import edit.services.component.ILockableElement;

import java.util.List;
    
/**
 * The Engine request controller
 */
public interface Role extends ICRUD, ILockableElement  {

    public long saveOrUpdateClientRole(ClientRoleVO clientRoleVO) throws Exception;

    /**
     * Generates CK transactions for any commission history recods with a "Check" or "EFT" disbursement source
     * @param paymentModeCT
     * @param forceOutMinBal (indicator yes/no)
     * @param disbursementSourceCT
     * @param operator
     * @throws Exception
     */
    public void setupAgentCommissionChecks(String paymentModeCT, String forceOutMinBal, String disbursementSourceCT, String operator) throws Exception;

    /**
     * Zeros-out CommissionTaxableYTD on ClientRoleFinancial
     * @throws Exception
     */
    public void runYearEndClientBalance() throws Exception;

    public ClientRoleFinancialVO composeClientRoleFinancialByClientRoleFK(long clientRoleFK, List voInclusionList) throws Exception;

    public void updateLastCheckDateTime(long placedAgentPK, EDITDateTime lastCheckDateTime) throws Exception;

    public void saveClientRole(ClientRoleVO[] clientRoleVOs, String operator);
}