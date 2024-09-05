package codetable;

import codetable.dm.dao.DAOFactory;
import edit.common.vo.FilteredCodeTableVO;
import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import edit.services.db.hibernate.SessionHelper;
import engine.ProductStructure;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 24, 2003
 * Time: 11:23:14 AM
 * To change this template use Options | File Templates.
 */
public class FilteredCodeTableImpl extends CRUDEntityImpl
{
    protected void load(CRUDEntity crudEntity, long pk) throws Exception
    {
        super.load(crudEntity, pk, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void save(FilteredCodeTable filteredCodeTable) throws Exception
    {
        super.save(filteredCodeTable, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    protected void delete(CRUDEntity crudEntity) throws Exception
    {
        super.delete(crudEntity, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected FilteredCodeTableVO findAttached(long productStructurePK, long codeTablePK) throws Exception
    {
        FilteredCodeTableVO filteredCodeTableVO = null;
        FilteredCodeTableVO[] filteredCodeTableVOs = DAOFactory.getFilteredCodeTableDAO().findPKByCodeTablePKAndProductStructure(codeTablePK, productStructurePK);

        if (filteredCodeTableVOs != null)
        {
            filteredCodeTableVO = filteredCodeTableVOs[0];
        }

        return filteredCodeTableVO;
    }

    protected void detachCodeTableFromProductStructure(FilteredCodeTable filteredCodeTable, FilteredCodeTableVO filteredCodeTableVO) throws Exception
    {
        FilteredCodeTableVO[] filteredCodeTableVOs = DAOFactory.getFilteredCodeTableDAO().findPKByCodeTablePKAndProductStructure(filteredCodeTableVO.getCodeTableFK(), filteredCodeTableVO.getProductStructureFK());

        if (filteredCodeTableVOs != null)
        {
            filteredCodeTable = new FilteredCodeTable(filteredCodeTableVOs[0]);
            delete(filteredCodeTable);
        }
    }

    protected FilteredCodeTableVO findFilteredCodeTable(FilteredCodeTable filteredCodeTable) throws Exception
    {
        FilteredCodeTableVO[] filteredCodeTableVOs = DAOFactory.getFilteredCodeTableDAO().findByPK(filteredCodeTable.getPK());

        FilteredCodeTableVO filteredCodeTableVO = null;

        if (filteredCodeTableVOs != null)
        {
            filteredCodeTableVO = filteredCodeTableVOs[0];
        }

        return filteredCodeTableVO;
    }

    protected void cloneFilteredCodeTableVO(ProductStructure productStructure, FilteredCodeTable filteredCodeTable) throws Exception
    {
        long productStructurePK = productStructure.getPK();

        FilteredCodeTableVO filteredCodeTableVO = (FilteredCodeTableVO)filteredCodeTable.getVO();

        filteredCodeTableVO.setFilteredCodeTablePK(0);
        filteredCodeTableVO.setProductStructureFK(productStructurePK);
        filteredCodeTable.save();
    }

    public FilteredCodeTableVO findByCodeTablePKAndProductStructure(long codeTablePK, long productStructureId)
    {
        FilteredCodeTableVO[] filteredCodeTableVOs = null;
        FilteredCodeTableVO filteredCodeTableVO = null;

        try
        {
            filteredCodeTableVOs = DAOFactory.getFilteredCodeTableDAO().findPKByCodeTablePKAndProductStructure(codeTablePK, productStructureId);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }


        if (filteredCodeTableVOs != null)
        {
            filteredCodeTableVO = filteredCodeTableVOs[0];
        }

        return filteredCodeTableVO;
    }

}
