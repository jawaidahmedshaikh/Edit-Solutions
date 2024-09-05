package codetable;

import codetable.dm.dao.DAOFactory;
import edit.common.CodeTableWrapper;
import edit.common.vo.CodeTableDefVO;
import edit.services.db.CRUDEntity;
import edit.services.db.CRUDEntityImpl;
import edit.services.db.ConnectionFactory;
import engine.ProductStructure;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Oct 24, 2003
 * Time: 11:23:14 AM
 * To change this template use Options | File Templates.
 */
public class CodeTableDefImpl extends CRUDEntityImpl
{
    protected void load(CRUDEntity crudEntity, long pk) throws Exception
    {
        super.load(crudEntity, pk, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void save(CodeTableDef codeTableDef) throws Exception
    {
        CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

        super.save(codeTableDef, ConnectionFactory.EDITSOLUTIONS_POOL, false);

        codeTableWrapper.reloadCodeTables();
    }

    protected void delete(CRUDEntity crudEntity) throws Exception
    {
        // cannot delete codeTableFefs - if implemented delete children first
        super.delete(crudEntity, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected CodeTableDefVO[] getAllCodeTableDefVOs() throws Exception
    {
        return DAOFactory.getCodeTableDefDAO().findAllCodeTableDefs();
    }

    protected CodeTable[] getCodeTableEntries(long codeTableDefPK, ProductStructure productStructure) throws Exception
    {
        CodeTable codeTable = new CodeTable();

        CodeTable[] codeTables = codeTable.getCodeTableEntriesWithProductStructure(codeTableDefPK, productStructure);

        return codeTables;
    }

    protected CodeTableDefVO getCodeTableName(CodeTableDef codeTableDef) throws Exception
    {
        CodeTableDefVO[] codeTableDefVOs = DAOFactory.getCodeTableDefDAO().findByCodeTableDefPK(codeTableDef.getPK());

        CodeTableDefVO codeTableDefVO = null;
        if (codeTableDefVOs != null)
        {
            codeTableDefVO = codeTableDefVOs[0];
        }

        return codeTableDefVO;
    }
}
