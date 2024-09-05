package engine.dm.composer;

import edit.common.vo.*;
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
public class FilteredFundComposer extends Composer
{
    private CRUD crud;

    private List voInclusionList;

    public FilteredFundComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public FilteredFundVO compose(long filteredFundPK)
    {
        FilteredFundVO filteredFundVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            filteredFundVO = (FilteredFundVO) crud.retrieveVOFromDB(FilteredFundVO.class, filteredFundPK);

            compose(filteredFundVO);
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

        return filteredFundVO;
    }

    public void compose(FilteredFundVO filteredFundVO)
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            if (voInclusionList.contains(FundVO.class)) associateFundVO(filteredFundVO);
            if (voInclusionList.contains(InterestRateParametersVO.class)) appendInterestRateParametersVO(filteredFundVO);
            if (voInclusionList.contains(UnitValuesVO.class)) appendUnitValuesVO(filteredFundVO);
            if (voInclusionList.contains(ProductFilteredFundStructureVO.class)) appendProductFilteredFundStructureVO(filteredFundVO);
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

    private void associateFundVO(FilteredFundVO filteredFundVO)
    {
        if (filteredFundVO.getFundFK() != 0)
        {
            voInclusionList.remove(FilteredFundVO.class);

            FundVO fundVO = new FundComposer(voInclusionList).compose(filteredFundVO.getFundFK());

            filteredFundVO.setParentVO(FundVO.class, fundVO);

            fundVO.addFilteredFundVO(filteredFundVO);

            voInclusionList.add(FilteredFundVO.class);
        }
    }

    private void appendInterestRateParametersVO(FilteredFundVO filteredFundVO)
    {
        voInclusionList.remove(FilteredFundVO.class);

        InterestRateParametersVO[] interestRateParametersVO = (InterestRateParametersVO[]) crud.retrieveVOFromDB(InterestRateParametersVO.class, FilteredFundVO.class, filteredFundVO.getFilteredFundPK());

        if (interestRateParametersVO != null)
        {
            filteredFundVO.setInterestRateParametersVO(interestRateParametersVO);

            for (int i = 0; i < interestRateParametersVO.length; i++)
            {
                interestRateParametersVO[i].setParentVO(FilteredFundVO.class,  filteredFundVO);
            }
        }

        voInclusionList.add(FilteredFundVO.class);
    }

    private void appendUnitValuesVO(FilteredFundVO filteredFundVO)
    {
        voInclusionList.remove(FilteredFundVO.class);

        UnitValuesVO[] unitValuesVO = (UnitValuesVO[]) crud.retrieveVOFromDB(UnitValuesVO.class, FilteredFundVO.class, filteredFundVO.getFilteredFundPK());

        if (unitValuesVO != null)
        {
            filteredFundVO.setUnitValuesVO(unitValuesVO);

            for (int i = 0; i < unitValuesVO.length; i++)
            {
                unitValuesVO[i].setParentVO(FilteredFundVO.class, filteredFundVO);
            }
        }

        voInclusionList.add(FilteredFundVO.class);
    }

    private void appendProductFilteredFundStructureVO(FilteredFundVO filteredFundVO)
    {
        voInclusionList.remove(FilteredFundVO.class);

        ProductFilteredFundStructureVO[] cffsVO = (ProductFilteredFundStructureVO[]) crud.retrieveVOFromDB(ProductFilteredFundStructureVO.class,
                                                                                                           FilteredFundVO.class,
                                                                                                           filteredFundVO.getFilteredFundPK());

        if (cffsVO != null)
        {
            filteredFundVO.setProductFilteredFundStructureVO(cffsVO);

            for (int i = 0; i < cffsVO.length; i++)
            {
                cffsVO[i].setParentVO(FilteredFundVO.class, filteredFundVO);

                new ProductFilteredFundStructureComposer(voInclusionList).compose(cffsVO[i]);
            }
        }

        voInclusionList.add(FilteredFundVO.class);
    }
}
