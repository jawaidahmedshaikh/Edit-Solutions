package codetable;

import edit.common.vo.BIZCodeTableVO;
import edit.common.vo.CodeTableVO;
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
public class CodeTable implements CRUDEntity
{
    private CodeTableVO codeTableVO;

    private CodeTableImpl codeTableImpl;

    private boolean isAttached;

    private long filteredCodeTablePK;

    private String codeDescriptionOverride;

    public CodeTable()
    {
        codeTableVO = new CodeTableVO();
        codeTableImpl = new CodeTableImpl();
    }

    public CodeTable(CodeTableVO codeTableVO)
    {
        this();
        this.codeTableVO = codeTableVO;
    }

    public CodeTable(long codeTablePK) throws Exception
    {
        this();
        this.codeTableImpl.load(this, codeTablePK);
    }

    public VOObject getVO()
    {
        return codeTableVO;
    }

    public void save() throws Exception
    {
        codeTableImpl.save(this);
    }

    public void delete() throws Exception
    {
        codeTableImpl.delete(this);
    }

     public void deleteCodeTable() throws Exception
    {
        codeTableImpl.deleteCodeTable(this);
    }

    public long getPK()
    {
        return codeTableVO.getCodeTablePK();
    }

    public void setVO(VOObject voObject)
    {
        this.codeTableVO = (CodeTableVO) voObject;
    }

    public boolean isNew()
    {
        return codeTableImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity() 
    {
        return codeTableImpl.cloneCRUDEntity(this);
    }

    public CodeTableVO[] getSelectedCodeTableEntries(long codeTableDefPK) throws Exception
    {
        return codeTableImpl.getSelectedCodeTableEntries(codeTableDefPK);
    }

    public CodeTableVO getSpecificCodeTable(CodeTable codeTable) throws Exception
    {
        return codeTableImpl.getSpecificCodeTable(codeTable.getPK());
    }

    public CodeTable[] getCodeTableEntriesWithProductStructure(long codeTableDefPK, ProductStructure productStructure) throws Exception
    {
       return codeTableImpl.getCodeTableEntriesWithProductStructure(codeTableDefPK, productStructure);
    }

    public boolean getIsAttached()
    {
        return this.isAttached;
    }

    public void setIsAttached(boolean isAttachedValue)
    {
        this.isAttached = isAttachedValue;
    }

    public static BIZCodeTableVO[] mapEntityToBIZVO(CodeTable[] codeTableEntities)
    {
        return CodeTableImpl.mapEntityToBIZVO(codeTableEntities);
    }

    public long getFilteredCodeTablePK()
    {
        return this.filteredCodeTablePK;
    }

    public void setFilteredCodeTablePK(long filteredCodeTablePK)
    {
        this.filteredCodeTablePK = filteredCodeTablePK;
    }

    public String getCodeDescriptionOverride()
    {
        return this.codeDescriptionOverride;
    }

    public void setCodeDescriptionOverride(String codeDesc)
    {
        this.codeDescriptionOverride = codeDesc;
    }
}
