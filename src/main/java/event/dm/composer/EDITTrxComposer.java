package event.dm.composer;

import edit.common.vo.*;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Sep 19, 2003
 * Time: 8:41:08 AM
 * To change this template use Options | File Templates.
 */
public class EDITTrxComposer extends Composer
{
    private List voInclusionList;

    private CRUD crud;

    public EDITTrxComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public EDITTrxVO compose(long editTrxPK) throws Exception
    {
        EDITTrxVO editTrxVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            editTrxVO = (EDITTrxVO) crud.retrieveVOFromDB(EDITTrxVO.class, editTrxPK);

            compose(editTrxVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return editTrxVO;
    }

    public void compose(EDITTrxVO editTrxVO)
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(ClientSetupVO.class)) associateClientSetupVO(editTrxVO);

            if (voInclusionList.contains(EDITTrxHistoryVO.class)) appendEDITTrxHistoryVO(editTrxVO);

            if (voInclusionList.contains(EDITTrxCorrespondenceVO.class)) appendEDITTrxCorrespondenceVO(editTrxVO);

            if (voInclusionList.contains(OverdueChargeVO.class)) appendOverdueChargeVO(editTrxVO);

            if (voInclusionList.contains(OverdueChargeSettledVO.class)) appendOverdueChargeSettledVO(editTrxVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }
    }

    private void appendEDITTrxHistoryVO(EDITTrxVO editTrxVO) throws Exception
    {
        voInclusionList.remove(EDITTrxVO.class);

        EDITTrxHistoryVO[] editTrxHistoryVO = (EDITTrxHistoryVO[]) crud.retrieveVOFromDB(EDITTrxHistoryVO.class, EDITTrxVO.class, editTrxVO.getEDITTrxPK());

        if (editTrxHistoryVO != null)
        {
            editTrxVO.setEDITTrxHistoryVO(editTrxHistoryVO);

            EDITTrxHistoryComposer composer = new EDITTrxHistoryComposer(voInclusionList);

            for (int i = 0; i < editTrxHistoryVO.length; i++)
            {
                composer.compose(editTrxHistoryVO[i]);

                editTrxHistoryVO[i].setParentVO(EDITTrxVO.class, editTrxVO);
            }
        }

        voInclusionList.add(EDITTrxVO.class);
    }

    private void associateClientSetupVO(EDITTrxVO editTrxVO) throws Exception
    {
        voInclusionList.remove(EDITTrxVO.class);

        ClientSetupVO clientSetupVO = (ClientSetupVO) crud.retrieveVOFromDB(ClientSetupVO.class, editTrxVO.getClientSetupFK());

        ClientSetupComposer composer = new ClientSetupComposer(voInclusionList);

        composer.compose(clientSetupVO);

        editTrxVO.setParentVO(ClientSetupVO.class, clientSetupVO);

        clientSetupVO.addEDITTrxVO(editTrxVO);

        voInclusionList.add(EDITTrxVO.class);
    }

    private void appendEDITTrxCorrespondenceVO(EDITTrxVO editTrxVO) throws Exception
    {
        voInclusionList.remove(EDITTrxVO.class);

        EDITTrxCorrespondenceVO[] editTrxCorrespondenceVO = (EDITTrxCorrespondenceVO[]) crud.retrieveVOFromDB(EDITTrxCorrespondenceVO.class, EDITTrxVO.class, editTrxVO.getEDITTrxPK());

        if (editTrxCorrespondenceVO != null)
        {
            if (voInclusionList.contains(TransactionCorrespondenceVO.class)) associateTransactionCorrespondenceVO(editTrxCorrespondenceVO);
            editTrxVO.setEDITTrxCorrespondenceVO(editTrxCorrespondenceVO);
        }

        voInclusionList.add(EDITTrxVO.class);
    }

    private void associateTransactionCorrespondenceVO(EDITTrxCorrespondenceVO[] editTrxCorrespondenceVO) throws Exception
    {
        voInclusionList.remove(EDITTrxCorrespondenceVO.class);

        for (int e = 0; e < editTrxCorrespondenceVO.length; e++)
        {
            TransactionCorrespondenceVO[] trxCorrVO = event.dm.dao.DAOFactory.getTransactionCorrespondenceDAO().findByPK(editTrxCorrespondenceVO[e].getTransactionCorrespondenceFK());
            if (trxCorrVO != null && trxCorrVO.length > 0)
            {
                editTrxCorrespondenceVO[e].setParentVO(TransactionCorrespondenceVO.class, trxCorrVO[0]);
            }
        }

        voInclusionList.add(EDITTrxCorrespondenceVO.class);
    }

    private void appendOverdueChargeVO(EDITTrxVO editTrxVO)
    {
        voInclusionList.remove(EDITTrxVO.class);

        OverdueChargeVO[] overdueChargeVO =
                (OverdueChargeVO[]) crud.retrieveVOFromDB(OverdueChargeVO.class, EDITTrxVO.class, editTrxVO.getEDITTrxPK());

        if (overdueChargeVO != null && overdueChargeVO.length > 0 )
        {
            if (voInclusionList.contains(OverdueChargeSettledVO.class))
            {
                for (int i = 0; i < overdueChargeVO.length; i++)
                {
                    appendOverdueChargeSettledVO(overdueChargeVO[i]);
                }
            }

            editTrxVO.setOverdueChargeVO(overdueChargeVO);
        }

        voInclusionList.add(EDITTrxVO.class);
    }

    private void appendOverdueChargeSettledVO(OverdueChargeVO overdueChargeVO)
    {
        voInclusionList.remove(OverdueChargeVO.class);

        OverdueChargeSettledVO[] overdueChargeSettledVO =
                (OverdueChargeSettledVO[]) crud.retrieveVOFromDB(OverdueChargeSettledVO.class, OverdueChargeVO.class, overdueChargeVO.getOverdueChargePK());

        if (overdueChargeSettledVO != null && overdueChargeSettledVO.length > 0 )
        {
            overdueChargeVO.setOverdueChargeSettledVO(overdueChargeSettledVO);
        }

        voInclusionList.add(OverdueChargeVO.class);
    }

    private void appendOverdueChargeSettledVO(EDITTrxVO editTrxVO)
    {
        voInclusionList.remove(EDITTrxVO.class);

        OverdueChargeSettledVO[] overdueChargeSettledVO =
                (OverdueChargeSettledVO[]) crud.retrieveVOFromDB(OverdueChargeSettledVO.class, EDITTrxVO.class, editTrxVO.getEDITTrxPK());

        if (overdueChargeSettledVO != null && overdueChargeSettledVO.length > 0 )
        {
            editTrxVO.setOverdueChargeSettledVO(overdueChargeSettledVO);
        }

        voInclusionList.add(EDITTrxVO.class);
    }
}
