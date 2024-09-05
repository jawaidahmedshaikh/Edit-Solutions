package event.financial.client.strategy;

import edit.common.vo.*;
import edit.common.exceptions.*;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import event.dm.composer.EDITTrxComposer;
import event.dm.composer.GroupSetupComposer;
import event.dm.composer.ContractSetupComposer;
import event.dm.composer.ClientSetupComposer;
import event.dm.dao.DAOFactory;
import event.financial.client.trx.ClientTrx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Sep 24, 2003
 * Time: 10:06:59 AM
 * To change this template use Options | File Templates.
 */
public class DeleteTrx extends ClientStrategy
{
    public DeleteTrx(ClientTrx clientTrx)
    {
        super(clientTrx);
    }

    public ClientStrategy[] execute() throws EDITEventException
    {
        CRUD crud = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            ClientTrx clientTrx = super.getClientTrx();

            List voInclusionList = new ArrayList();

            new EDITTrxComposer(voInclusionList).compose(clientTrx.getEDITTrxVO());

            checkForEDITTrxCorrespondence(clientTrx.getEDITTrxVO().getEDITTrxPK(), crud);

            // Delete the EditTrx
            crud.deleteVOFromDB(EDITTrxVO.class, clientTrx.getEDITTrxVO().getEDITTrxPK());

            // Delete the ClientSetup if there are no additional children
            voInclusionList.clear();
            voInclusionList.add(EDITTrxVO.class);
            ClientSetupVO clientSetupVO = new ClientSetupComposer(voInclusionList).compose(clientTrx.getEDITTrxVO().getClientSetupFK());

            if (clientSetupVO.getEDITTrxVOCount() == 0)
            {
                crud.deleteVOFromDBRecursively(ClientSetupVO.class, clientSetupVO.getClientSetupPK());
                voInclusionList.clear();
                voInclusionList.add(ClientSetupVO.class);
                ContractSetupVO contractSetupVO = new ContractSetupComposer(voInclusionList).compose(clientSetupVO.getContractSetupFK());
                if (contractSetupVO.getClientSetupVOCount() == 0)
                {
                    crud.deleteVOFromDBRecursively(ContractSetupVO.class, contractSetupVO.getContractSetupPK());
                    voInclusionList.clear();
                    voInclusionList.add(ContractSetupVO.class);
                    GroupSetupVO groupSetupVO = new GroupSetupComposer(voInclusionList).compose(contractSetupVO.getGroupSetupFK());
                    if (groupSetupVO.getContractSetupVOCount() == 0)
                    {
                        crud.deleteVOFromDBRecursively(GroupSetupVO.class, groupSetupVO.getGroupSetupPK());
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return null;
    }

    private void checkForEDITTrxCorrespondence(long editTrxPK, CRUD crud)  throws Exception
    {
        EDITTrxCorrespondenceVO[] editTrxCorrespondenceVO = DAOFactory.getEDITTrxCorrespondenceDAO().findByEDITTrxPK(editTrxPK);

        if (editTrxCorrespondenceVO != null)
        {
            for (int i = 0; i < editTrxCorrespondenceVO.length; i++)
            {
                crud.deleteVOFromDB(EDITTrxCorrespondenceVO.class, editTrxCorrespondenceVO[i].getEDITTrxCorrespondencePK());
            }
        }
    }
}
