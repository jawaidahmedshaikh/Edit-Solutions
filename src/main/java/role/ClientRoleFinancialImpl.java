/*
 * User: gfrosti
 * Date: Nov 4, 2003
 * Time: 10:49:57 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package role;

import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.vo.ClientRoleFinancialVO;
import edit.common.vo.ClientRoleVO;
import edit.common.vo.PreferenceVO;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import event.ClientSetup;
import event.financial.group.trx.GroupTrx;
import role.dm.composer.ClientRoleComposer;
import role.dm.dao.DAOFactory;

import java.util.ArrayList;
import java.util.List;


public class ClientRoleFinancialImpl extends CRUDEntityImpl
{
    public void setupCheckTransaction(ClientRoleFinancial clientRoleFinancial, String forceOutMinBal, String operator)
    {
        List voInclusionList = new ArrayList();
        voInclusionList.add(PreferenceVO.class);

        ClientRoleVO clientRoleVO = new ClientRoleComposer(voInclusionList).compose(((ClientRoleFinancialVO) clientRoleFinancial.getVO()).getClientRoleFK());

        EDITBigDecimal commissionBalance = new EDITBigDecimal(((ClientRoleFinancialVO) clientRoleFinancial.getVO()).getCommBalance());

        EDITBigDecimal redirectBalance = new EDITBigDecimal(((ClientRoleFinancialVO) clientRoleFinancial.getVO()).getRedirectBalance());

        EDITBigDecimal checkAmount = commissionBalance.addEditBigDecimal(redirectBalance);

        if (shouldSetupCheckTransaction(forceOutMinBal, checkAmount, clientRoleVO))
        {
            new GroupTrx().buildCheckTransactionGroup("CK", clientRoleVO.getClientRolePK(),
                                                      ClientSetup.TYPE_CLIENT_ROLE, checkAmount, operator,
                                                      new EDITDate().getFormattedDate(), new EDITBigDecimal("0", 2),
                                                      new EDITBigDecimal("0", 2), forceOutMinBal);

            // Zero-out fields and set LastCheckDate.
            clientRoleFinancial.setCommBalance(new EDITBigDecimal("0.0"));
            clientRoleFinancial.setRedirectBalance(new EDITBigDecimal("0.0"));
            clientRoleFinancial.setLastCheckDateTime(new EDITDateTime());
            clientRoleFinancial.setLastCheckAmount(checkAmount);

            clientRoleFinancial.save();
        }
    }

    protected void save(ClientRoleFinancial clientRoleFinancial)
    {
        super.save(clientRoleFinancial, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    protected void load(ClientRoleFinancial clientRoleFinancial, long clientRoleFinancialPK)
    {
        super.load(clientRoleFinancial, clientRoleFinancialPK, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void delete(ClientRoleFinancial clientRoleFinancial)
    {
        super.delete(clientRoleFinancial, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    private boolean shouldSetupCheckTransaction(String forceOutMinBal, EDITBigDecimal checkAmount, ClientRoleVO clientRoleVO)
    {
        if (forceOutMinBal.equalsIgnoreCase("Y"))
        {
            return true;
        }
        else
        {
            EDITBigDecimal minimumCheck = new EDITBigDecimal();
            if (clientRoleVO.getParentVOs() != null)
            {
                PreferenceVO preferenceVO = (PreferenceVO) clientRoleVO.getParentVO(PreferenceVO.class);
                if (preferenceVO != null)
                {
                    minimumCheck = new EDITBigDecimal(preferenceVO.getMinimumCheck());
                }
            }

            return (checkAmount.isGTE(minimumCheck));
        }
    }

    protected static ClientRoleFinancial[] findByClientRolePK(long clientRolePK)
    {
        ClientRoleFinancialVO[] clientRoleFinancialVO = DAOFactory.getClientRoleFinancialDAO().findByClientRolePK(clientRolePK);

        ClientRoleFinancial[] clientRoleFinancial = null;

        if (clientRoleFinancialVO != null)
        {
            clientRoleFinancial = new ClientRoleFinancial[clientRoleFinancialVO.length];

            for (int i = 0; i < clientRoleFinancialVO.length; i++)
            {
                clientRoleFinancial[i] = new ClientRoleFinancial(clientRoleFinancialVO[i]);
            }
        }

        return clientRoleFinancial;
    }

    protected void associateClientRole(ClientRoleFinancial clientRoleFinancial, ClientRole clientRole) throws Exception
    {
        ClientRoleFinancialVO clientRoleFinancialVO = (ClientRoleFinancialVO) clientRoleFinancial.getVO();

        clientRoleFinancialVO.setClientRoleFK(clientRole.getPK());

        clientRoleFinancial.save();
    }

    protected static void runYearEndClientBalance() throws Exception
    {
        ClientRoleFinancialVO[] clientRoleFinancialVO = DAOFactory.getClientRoleFinancialDAO().findAll();

        ClientRoleFinancial[] clientRoleFinancial = null;

        if (clientRoleFinancialVO != null)
        {
            clientRoleFinancial = new ClientRoleFinancial[clientRoleFinancialVO.length];

            for (int i = 0; i < clientRoleFinancialVO.length; i++)
            {
                clientRoleFinancial[i] = new ClientRoleFinancial(clientRoleFinancialVO[i]);
                clientRoleFinancial[i].setAmountTaxableYTD(new EDITBigDecimal("0.0"));
                clientRoleFinancial[i].save();
            }
        }
    }
}
