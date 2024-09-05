package codetable;

import edit.common.vo.CodeTableDefVO;
import edit.common.vo.VOObject;
import edit.services.db.CRUDEntity;
import engine.ProductStructure;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Nov 4, 2003
 * Time: 9:55:40 AM
 * To change this template use Options | File Templates.
 */
public class CodeTableDef implements CRUDEntity
{
    private CodeTableDefVO codeTableDefVO;

    private CodeTableDefImpl codeTableDefImpl;

    public CodeTableDef()
    {
        codeTableDefVO = new CodeTableDefVO();
        codeTableDefImpl = new CodeTableDefImpl();
    }

    public CodeTableDef(CodeTableDefVO codeTableDefVO)
    {
        this();
        this.codeTableDefVO = codeTableDefVO;
    }

    public CodeTableDef(long codeTableDefPK) throws Exception
    {
        this();
        this.codeTableDefImpl.load(this, codeTableDefPK);
    }

    public VOObject getVO()
    {
        return codeTableDefVO;
    }

    public void save() throws Exception
    {
        codeTableDefImpl.save(this);
    }

    public void delete() throws Exception
    {
        codeTableDefImpl.delete(this);
    }

    public long getPK()
    {
        return codeTableDefVO.getCodeTableDefPK();
    }

    public void setVO(VOObject voObject)
    {
        this.codeTableDefVO = (CodeTableDefVO) voObject;
    }

    public boolean isNew()
    {
        return codeTableDefImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity()
    {
        return codeTableDefImpl.cloneCRUDEntity(this);
    }

    public CodeTableDefVO[] getAllCodeTableDefVOs() throws Exception
    {
        return codeTableDefImpl.getAllCodeTableDefVOs();
    }

    public CodeTable[] getCodeTableEntries(ProductStructure productStructure) throws Exception
    {
        return codeTableDefImpl.getCodeTableEntries(this.getPK(), productStructure);
    }

    public void getCodeTableName() throws Exception
    {
        codeTableDefVO = codeTableDefImpl.getCodeTableName(this);
    }
}
