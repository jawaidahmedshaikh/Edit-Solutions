package codetable;

import codetable.dm.dao.DAOFactory;
import edit.common.CodeTableWrapper;
import edit.common.vo.BIZCodeTableVO;
import edit.common.vo.CodeTableVO;
import edit.common.vo.FilteredCodeTableVO;
import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import engine.ProductStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 24, 2003
 * Time: 11:23:14 AM
 * To change this template use Options | File Templates.
 */
public class CodeTableImpl extends CRUDEntityImpl
 {
    protected void load(CRUDEntity crudEntity, long pk) throws Exception
    {
        super.load(crudEntity, pk, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void save(CodeTable codeTable) throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        super.save(codeTable, ConnectionFactory.EDITSOLUTIONS_POOL, false);

        codeTableWrapper.reloadCodeTables();
    }

    protected void delete(CRUDEntity crudEntity) throws Exception
    {
        super.delete(crudEntity, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void deleteCodeTable(CodeTable codeTable) throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        deleteChildren(codeTable);
        delete(codeTable);

        codeTableWrapper.reloadCodeTables();
    }

    private void deleteChildren(CodeTable codeTable) throws Exception
    {
        CodeTableVO codeTableVO = (CodeTableVO) codeTable.getVO();

        long codeTablePK = codeTableVO.getCodeTablePK();

        FilteredCodeTableVO[] filteredCodeTableVOs = DAOFactory.getFilteredCodeTableDAO().findByCodeTablePK(codeTablePK);

        if (filteredCodeTableVOs != null) {
            for (int i = 0; i < filteredCodeTableVOs.length; i++)
            {
                FilteredCodeTable filteredCodeTable = new FilteredCodeTable(filteredCodeTableVOs[i]);
                filteredCodeTable.delete();
            }
        }

    }

    protected CodeTableVO[] getSelectedCodeTableEntries(long codeTableDefPK) throws Exception
    {
        return DAOFactory.getCodeTableDAO().findSelectedCodeTableEntries(codeTableDefPK);
    }

    protected CodeTableVO getSpecificCodeTable(long codeTablePK) throws Exception
    {
        CodeTableVO codeTableVO = null;
        CodeTableVO[] codeTableVOs = DAOFactory.getCodeTableDAO().findSpecificCodeTableEntry(codeTablePK);

        if (codeTableVOs != null)
        {
            codeTableVO = codeTableVOs[0];
        }

        return codeTableVO;
    }

    protected CodeTable[] getCodeTableEntriesWithProductStructure(long codeTableDefPK, ProductStructure productStructure) throws Exception
    {
        CodeTableVO[] codeTableVOs = DAOFactory.getCodeTableDAO().findSelectedCodeTableEntries(codeTableDefPK);
        List ctEntries = new ArrayList();

        if (codeTableVOs != null)
        {
            long productStructurePK = productStructure.getPK();

            for (int i = 0; i < codeTableVOs.length; i++)
            {
                CodeTable codeTable = new CodeTable(codeTableVOs[i]);

                FilteredCodeTable filteredCodeTable = new FilteredCodeTable();

                filteredCodeTable.findAttached(productStructurePK, codeTableVOs[i].getCodeTablePK());

                FilteredCodeTableVO filteredCodeTableVO = (FilteredCodeTableVO) filteredCodeTable.getVO();

                long filteredCodeTablePK = 0;

                if (filteredCodeTableVO != null)
                {
                    codeTable.setIsAttached(true);
                    filteredCodeTablePK = filteredCodeTableVO.getFilteredCodeTablePK();
                    codeTable.setFilteredCodeTablePK(filteredCodeTablePK);
                    codeTable.setCodeDescriptionOverride(filteredCodeTableVO.getCodeDesc());
                }
                else
                {
                    codeTable.setIsAttached(false);
                }

                codeTable.setFilteredCodeTablePK(filteredCodeTablePK);

                ctEntries.add(codeTable);
            }
        }

        return (CodeTable[]) ctEntries.toArray(new CodeTable[ctEntries.size()]);
    }


    protected static BIZCodeTableVO[] mapEntityToBIZVO(CodeTable[] codeTableEntities)
    {
        List bizVOs = new ArrayList();

        for (int i = 0; i < codeTableEntities.length; i++)
        {
            BIZCodeTableVO bizCodeTableVO = setBIZCodeTable(codeTableEntities[i]);

            bizVOs.add(bizCodeTableVO);
        }

        return (BIZCodeTableVO[]) bizVOs.toArray(new BIZCodeTableVO[bizVOs.size()]);
    }

     public static BIZCodeTableVO setBIZCodeTable(CodeTable codeTableEntity)
    {
        BIZCodeTableVO bizCodeTableVO = new BIZCodeTableVO();

        bizCodeTableVO.setCodeTableVO((CodeTableVO) codeTableEntity.getVO());
        bizCodeTableVO.setFilteredCodeTablePK(codeTableEntity.getFilteredCodeTablePK());
        bizCodeTableVO.setIsCodeTableAttached(codeTableEntity.getIsAttached());
        bizCodeTableVO.setCodeDescriptionOverride(codeTableEntity.getCodeDescriptionOverride());

        return bizCodeTableVO;
    }
}










