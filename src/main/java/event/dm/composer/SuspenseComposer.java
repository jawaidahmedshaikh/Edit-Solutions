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
 * Date: Oct 3, 2003
 * Time: 9:18:47 AM
 * To change this template use Options | File Templates.
 */
public class SuspenseComposer extends Composer
{
    private CRUD crud = null;

    private List voInclusionList;

    public SuspenseComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public SuspenseVO compose(long suspensePK) throws Exception
    {
        SuspenseVO suspenseVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            suspenseVO = (SuspenseVO) crud.retrieveVOFromDB(SuspenseVO.class, suspensePK);

            if (suspenseVO != null) {

                compose(suspenseVO);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw e;
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }

        return suspenseVO;
    }

    public void compose(SuspenseVO suspenseVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.EDITSOLUTIONS_POOL);

            if (voInclusionList.contains(InSuspenseVO.class)) appendInSuspenseVO(suspenseVO);

            if (voInclusionList.contains(OutSuspenseVO.class)) appendOutSuspenseVO(suspenseVO);
        }
        catch (Exception e)
        {
            System.out.println(e);
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
            throw e;
        }
        finally
        {
            if (crud != null) crud.close();

            crud = null;
        }
    }

    private void appendInSuspenseVO(SuspenseVO suspenseVO) throws Exception
    {
        voInclusionList.remove(SuspenseVO.class);

        InSuspenseVO[] inSuspenseVO = (InSuspenseVO[]) crud.retrieveVOFromDB(InSuspenseVO.class, SuspenseVO.class, suspenseVO.getSuspensePK());

        if (inSuspenseVO != null)
        {
            suspenseVO.setInSuspenseVO(inSuspenseVO);

            for (int i = 0; i < inSuspenseVO.length; i++)
            {
                if (voInclusionList.contains(EDITTrxHistoryVO.class)) associateEDITTrxHistoryVO(inSuspenseVO[i]);
            }
        }

        voInclusionList.add(SuspenseVO.class);
    }

    private void appendOutSuspenseVO(SuspenseVO suspenseVO) throws Exception
    {
        voInclusionList.remove(SuspenseVO.class);

        OutSuspenseVO[] outSuspenseVO = (OutSuspenseVO[]) crud.retrieveVOFromDB(OutSuspenseVO.class, SuspenseVO.class, suspenseVO.getSuspensePK());

        if (outSuspenseVO != null)
        {
            for (int i = 0; i < outSuspenseVO.length; i++)
            {
                OutSuspenseComposer composer = new OutSuspenseComposer(voInclusionList);

                composer.compose(outSuspenseVO[i]);

                suspenseVO.setOutSuspenseVO(outSuspenseVO);
            }
        }

        voInclusionList.add(SuspenseVO.class);
    }

    private void associateEDITTrxHistoryVO(InSuspenseVO inSuspenseVO) throws Exception
    {
        voInclusionList.remove(InSuspenseVO.class);

        EDITTrxHistoryComposer composer = new EDITTrxHistoryComposer(voInclusionList);

        EDITTrxHistoryVO editTrxHistoryVO = composer.compose(inSuspenseVO.getEDITTrxHistoryFK());

        inSuspenseVO.setParentVO(EDITTrxHistoryVO.class, editTrxHistoryVO);

        voInclusionList.add(InSuspenseVO.class);
    }
}
