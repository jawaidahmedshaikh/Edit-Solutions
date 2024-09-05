/*
 * User: gfrosti
 * Date: Nov 4, 2003
 * Time: 9:55:40 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package codetable;

import edit.common.vo.FilteredCodeTableVO;
import edit.common.vo.VOObject;
import edit.services.db.CRUDEntity;
import edit.services.db.hibernate.*;
import engine.ProductStructure;

import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class FilteredCodeTable extends HibernateEntity implements CRUDEntity
{
    private FilteredCodeTableVO filteredCodeTableVO;

    private FilteredCodeTableImpl filteredCodeTableImpl;

    /**
     * Target database to be used for lookups, etc.
     */
    public static String DATABASE = SessionHelper.EDITSOLUTIONS;


    public FilteredCodeTable()
    {
        filteredCodeTableVO = new FilteredCodeTableVO();
        filteredCodeTableImpl = new FilteredCodeTableImpl();
    }

    public FilteredCodeTable(FilteredCodeTableVO filteredCodeTableVO)
    {
        this();
        this.filteredCodeTableVO = filteredCodeTableVO;
    }

    public FilteredCodeTable(long filteredCodeTablePK) throws Exception
    {
        this();
        this.filteredCodeTableImpl.load(this, filteredCodeTablePK);
    }

    public FilteredCodeTable(Long codeTableFK, String codeDesc, ProductStructure productStructure)
    {
        this();
        filteredCodeTableVO = new FilteredCodeTableVO();
        filteredCodeTableVO.setFilteredCodeTablePK(0);
        filteredCodeTableVO.setProductStructureFK(productStructure.getPK());
        filteredCodeTableVO.setCodeTableFK(codeTableFK.longValue());
        if (codeDesc != null)
        {
            filteredCodeTableVO.setCodeDesc(codeDesc);
        }
    }

    public VOObject getVO()
    {
        return filteredCodeTableVO;
    }

    /**
     * Getter
     * @return
     */
    public Long getFilteredCodeTablePK()
    {
        return SessionHelper.getPKValue(this.filteredCodeTableVO.getFilteredCodeTablePK());
    }

    /**
     * Setter.
     * @param filteredCodeTablePK
     */
    public void setFilteredCodeTablePK(Long filteredCodeTablePK)
    {
        this.filteredCodeTableVO.setFilteredCodeTablePK(SessionHelper.getPKValue(filteredCodeTablePK));
    }

    /**
     * Getter.
     * @return
     */
    public Long getProductStructureFK()
    {
        return SessionHelper.getPKValue(this.filteredCodeTableVO.getProductStructureFK());
    }

    /**
     * Setter.
     * @param productStructureFK
     */
    public void setProductStructureFK(Long productStructureFK)
    {
        this.filteredCodeTableVO.setProductStructureFK(SessionHelper.getPKValue(productStructureFK));
    }

    /**
     * Getter.
     * @return
     */
    public Long getCodeTableFK()
    {
        return SessionHelper.getPKValue(this.filteredCodeTableVO.getCodeTableFK());
    }

    /**
     * Setter.
     * @param codeTableFK
     */                                               
    public void setCodeTableFK(Long codeTableFK)
    {
        this.filteredCodeTableVO.setCodeTableFK(SessionHelper.getPKValue(codeTableFK));
    }

    /**
     * Getter
     * @return
     */
    public String getCodeDesc()
    {
        return this.filteredCodeTableVO.getCodeDesc();
    }

    /**
     * Setter
     * @param codeDesc
     */
    public void setCodeDesc(String codeDesc)
    {
        this.filteredCodeTableVO.setCodeDesc(codeDesc);
    }

    public String getCodeDescription()
    {
        return filteredCodeTableVO.getCodeDesc();
    }

    public void save() throws Exception
    {
        filteredCodeTableImpl.save(this);
    }

    public void delete() throws Exception
    {
        filteredCodeTableImpl.delete(this);
    }

    public long getPK()
    {
        return filteredCodeTableVO.getFilteredCodeTablePK();
    }

    public void setVO(VOObject voObject)
    {
        this.filteredCodeTableVO = (FilteredCodeTableVO) voObject;
    }

    public boolean isNew()
    {
        return filteredCodeTableImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity() 
    {
        return filteredCodeTableImpl.cloneCRUDEntity(this);
    }

    public void findAttached(long productStructurePK, long codeTablePK) throws Exception
    {
        filteredCodeTableVO = filteredCodeTableImpl.findAttached(productStructurePK, codeTablePK);
    }

    public void detachCodeTableFromProductStructure() throws Exception
    {
        filteredCodeTableImpl.detachCodeTableFromProductStructure(this, filteredCodeTableVO);
    }

    public void findFilteredCodeTable() throws Exception
    {
        filteredCodeTableVO = filteredCodeTableImpl.findFilteredCodeTable(this) ;
    }

    public void cloneFilteredCodeTableVO(ProductStructure productStructure, FilteredCodeTable filteredCodeTable) throws Exception
    {
        filteredCodeTableImpl.cloneFilteredCodeTableVO(productStructure, filteredCodeTable);
    }

    public void findByCodeTablePKAndProductStructure(long codeTablePK, long productStructureId)
    {
        filteredCodeTableVO = filteredCodeTableImpl.findByCodeTablePKAndProductStructure(codeTablePK, productStructureId);
    }

    /**
     * Finder.
     * @param productStructure
     * @return
     */
    public static final FilteredCodeTable[] findByProductStructure(ProductStructure productStructure)
    {
        Long productStructureFK = productStructure.getProductStructurePK();

        String hql = "select fct from FilteredCodeTable fct where fct.ProductStructureFK = :productStructureFK";

        Map params = new HashMap();

        params.put("productStructureFK", productStructureFK);

        List filteredCodeTables = SessionHelper.executeHQL(hql, params, FilteredCodeTable.DATABASE);

        return (FilteredCodeTable[]) filteredCodeTables.toArray(new FilteredCodeTable[filteredCodeTables.size()]);
    }

    /**
    * Save the entity using Hibernate
    */
    public void hSave()
    {
       SessionHelper.saveOrUpdate(this, FilteredCodeTable.DATABASE);
    }

    /**
    * Delete the entity using Hibernate
    */
    public void hDelete()
    {
       SessionHelper.delete(this, FilteredCodeTable.DATABASE);
    }

    /**
     * Gets the database this object belongs to
     *
     * @return  string containing the hibernate database name for this object
     */
    public String getDatabase()
    {
        return FilteredCodeTable.DATABASE;
    }
}
