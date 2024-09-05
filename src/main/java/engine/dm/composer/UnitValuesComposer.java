/*
 * User: dlataill
 * Date: Jan 26, 2005
 * Time: 1:38:42 PM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement
 */
package engine.dm.composer;

import edit.services.db.Composer;
import edit.services.db.CRUD;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.common.vo.*;

import java.util.List;

public class UnitValuesComposer extends Composer
{
    private CRUD crud;

    private List voInclusionList;

    public UnitValuesComposer(List voInclusionList)
    {
        this.voInclusionList = voInclusionList;
    }

    public UnitValuesVO compose(long unitValuesPK)
    {
        UnitValuesVO unitValuesVO = null;

        try
        {
            crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            unitValuesVO = (UnitValuesVO) crud.retrieveVOFromDB(UnitValuesVO.class, unitValuesPK);

            compose(unitValuesVO);
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

        return unitValuesVO;
    }

    public void compose(UnitValuesVO unitValuesVO)
    {
        try
        {
            if (crud == null) crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.ENGINE_POOL);

            if (voInclusionList.contains(FundVO.class)) associateFilteredFundVO(unitValuesVO);
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

    private void associateFilteredFundVO(UnitValuesVO unitValuesVO)
    {
        if (unitValuesVO.getFilteredFundFK() != 0)
        {
            voInclusionList.remove(UnitValuesVO.class);

            FilteredFundVO filteredFundVO = new FilteredFundComposer(voInclusionList).compose(unitValuesVO.getFilteredFundFK());

            unitValuesVO.setParentVO(FilteredFundVO.class, filteredFundVO);

            filteredFundVO.addUnitValuesVO(unitValuesVO);

            voInclusionList.add(UnitValuesVO.class);
        }
    }
}
