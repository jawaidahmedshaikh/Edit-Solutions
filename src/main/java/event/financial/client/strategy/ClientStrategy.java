package event.financial.client.strategy;

import edit.common.vo.*;
import edit.common.exceptions.*;
import edit.services.component.ICRUD;
import event.component.EventComponent;
import event.dm.dao.DAOFactory;
import event.financial.client.trx.ClientTrx;
import event.financial.client.trx.EDITTrxCompare;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Aug 14, 2003
 * Time: 9:27:09 AM
 * (c) 2000 - 2005 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */
public abstract class ClientStrategy implements Comparable
{
    private ClientTrx clientTrx;


    private String sortStatus;

    public long suspenseFK = 0;

    protected ClientStrategy()
    {

    }

    public ClientStrategy(ClientTrx clientTrx)
    {
        this.clientTrx = clientTrx;
    }

    public ClientTrx getClientTrx()
    {
        return clientTrx;
    }

    public abstract ClientStrategy[] execute() throws EDITEventException;

    public int compareTo(Object o)
    {
        ClientStrategy visitorClientStrategy = (ClientStrategy) o;

//        return new EDITTrxCompare().compare(clientTrx.getEDITTrxVO(), visitorClientStrategy.getClientTrx().getEDITTrxVO());
        return new EDITTrxCompare().compare(this, visitorClientStrategy);

    }
    public void deleteRenewalTrx(long editTrxPK) throws Exception
    {
        EDITTrxVO[] editTrxVOs = DAOFactory.getEDITTrxDAO().findRenewalByOriginatingPK(editTrxPK);

        if (editTrxVOs != null)
        {
            long renewalPK = editTrxVOs[0].getEDITTrxPK();

            ClientSetupVO[] clientSetupVO = DAOFactory.getClientSetupDAO().findByEDITTrxPK(renewalPK);

            ContractSetupVO[] contractSetupVO = DAOFactory.getContractSetupDAO().findByEDITTrxPK(renewalPK);

            GroupSetupVO[] groupSetupVO = DAOFactory.getGroupSetupDAO().findByEditTrxPK(renewalPK);

            InvestmentAllocationOverrideVO[] investmentAllocationOverrideVO = DAOFactory.getInvestmentAllocationOverrideDAO().findByContractSetupPK(contractSetupVO[0].getContractSetupPK());

//            BucketAllocationOverrideVO[] bucketAllocationOverrideVO = DAOFactory.getBucketAllocationOverrideDAO().findByContractSetupPK(contractSetupVO[0].getContractSetupPK());

            ICRUD eventComponent = new EventComponent();

            eventComponent.deleteVO(EDITTrxVO.class, editTrxVOs[0].getEDITTrxPK(), false);
            eventComponent.deleteVO(ClientSetupVO.class, clientSetupVO[0].getClientSetupPK(), false);
            eventComponent.deleteVO(InvestmentAllocationOverrideVO.class,investmentAllocationOverrideVO[0].getInvestmentAllocationOverridePK(), false);
//            eventComponent.deleteVO(BucketAllocationOverrideVO.class, bucketAllocationOverrideVO[0].getBucketAllocationOverridePK(), false);
            eventComponent.deleteVO(ContractSetupVO.class, contractSetupVO[0].getContractSetupPK(), false);
            eventComponent.deleteVO(GroupSetupVO.class, groupSetupVO[0].getGroupSetupPK(), false);
        }
    }

    /**
     * Getter for sorting type
     * @return
     */
    public String getSortStatus()
    {
        return sortStatus;
    }


    /**
     * Setter for sorting type (undo.natural,redo)
     * @param sortStatus
     */
    public void setSortStatus(String sortStatus)
     {
         this.sortStatus = sortStatus;
     }
}
