package engine.dm.composer;

import edit.common.vo.FilteredFundVO;
import edit.common.vo.FundVO;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.Composer;
import edit.services.db.ConnectionFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 7, 2003
 * Time: 4:13:24 PM
 * To change this template use Options | File Templates.
 */
public class FundComposer extends Composer
{
    private CRUD crud;

    private List voInclusionList;

    public FundComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public FundVO compose(long fundPK)
    {
        FundVO fundVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            fundVO = (FundVO) crud.retrieveVOFromDB(FundVO.class, fundPK);

            compose(fundVO);
        }
        catch(Exception e)
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

        return fundVO;
    }

    public void compose(FundVO fundVO)
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            if (voInclusionList.contains(FilteredFundVO.class)) appendFilteredFundVO(fundVO);
        }
        catch(Exception e)
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
    }

    private void appendFilteredFundVO(FundVO fundVO)
    {
        voInclusionList.remove(FundVO.class);

        FilteredFundVO[] filteredFundVO = (FilteredFundVO[]) crud.retrieveVOFromDB(FilteredFundVO.class, FundVO.class, fundVO.getFundPK());

        if (filteredFundVO != null)
        {
            fundVO.setFilteredFundVO(filteredFundVO);

            for (int i = 0; i < filteredFundVO.length; i++)
            {
                filteredFundVO[i].setParentVO(FundVO.class, fundVO);

                new FilteredFundComposer(voInclusionList).compose(filteredFundVO[i]);
            }
        }

        voInclusionList.add(FundVO.class);
    }
}
