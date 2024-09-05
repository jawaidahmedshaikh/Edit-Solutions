package engine.dm.composer;

import edit.common.vo.InterestRateParametersVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 7, 2003
 * Time: 4:04:56 PM
 * To change this template use Options | File Templates.
 */
public class InterestRateParametersComposer extends Composer
{
    private CRUD crud;

    private List voInclusionList;

    public InterestRateParametersComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public InterestRateParametersVO compose(long interestRateParametersPK) throws Exception
    {
        InterestRateParametersVO interestRateParametersVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            interestRateParametersVO = (InterestRateParametersVO) crud.retrieveVOFromDB(InterestRateParametersVO.class, interestRateParametersPK);

            compose(interestRateParametersVO);
        }
        catch(Exception e)
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

        return interestRateParametersVO;
    }

    public void compose(InterestRateParametersVO interestRateParametersVO) throws Exception
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);
        }
        catch(Exception e)
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
    }
}
