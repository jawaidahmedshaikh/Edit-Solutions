/**
 * FilteredOnlineReport.java    Version 2.0  05/20/2004
 *
 * User: gfrosti
 * Date: May 20, 2003
 * Time: 9:55:40 AM
 * (c) 2000-2004 Systems Engineering Group, LLC. All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential. Any use is
 * subject to the license agreement.
 */

package codetable;

import edit.common.vo.FilteredOnlineReportVO;
import edit.common.vo.VOObject;
import edit.services.db.CRUDEntity;
import engine.ProductStructure;

public class FilteredOnlineReport implements CRUDEntity
{
    private FilteredOnlineReportVO filteredOnlineReportVO;

    private FilteredOnlineReportImpl filteredOnlineReportImpl;

    /**
     * Constructor - set FilteredOnlineReportVO and FilteredOnlineReportImpl
     */
    public FilteredOnlineReport()
    {
        filteredOnlineReportVO = new FilteredOnlineReportVO();
        filteredOnlineReportImpl = new FilteredOnlineReportImpl();
    }

    /**
     * Constructor - set FilteredOnlineReportVO from the VO passed in.
     * @param filteredOnlineReportVO
     */
    public FilteredOnlineReport(FilteredOnlineReportVO filteredOnlineReportVO)
    {
        this();
        this.filteredOnlineReportVO = filteredOnlineReportVO;
    }

    /**
     * Constructor -  set FilteredOnlineReportVO and FilteredOnlineReportImpl and the FilteredOnlineReportPK passed in.
     * @param filteredOnlineReportPK
     * @throws Exception
     */
    public FilteredOnlineReport(long filteredOnlineReportPK) throws Exception
    {
        this();
        this.filteredOnlineReportImpl.load(this, filteredOnlineReportPK);
    }

    /**
     * Gets the FilteredOnlineReportVO contained in this entity.
     * @return   filteredOnlineReportVO
     */
    public VOObject getVO()
    {
        return filteredOnlineReportVO;
    }

    /**
     * Save the FilteredOnlineReportVo contained in this entity
     * @throws Exception
     */
    public void save() throws Exception
    {
        filteredOnlineReportImpl.save(this);
    }

    /**
     * Delete the FilteredOnlineReportVo contained in this entity
     * @throws Exception
     */
    public void delete() throws Exception
    {
        filteredOnlineReportImpl.delete(this);
    }

    /**
     * Get the PK contain in the FilteredOnlineReportVO of this entity.
     * @return  filteredOnlineReportPK
     */
    public long getPK()
    {
        return filteredOnlineReportVO.getFilteredOnlineReportPK();
    }

    /**
     * Set the  FilteredOnlineReportVO passed into this entity.
     * @param voObject
     */
    public void setVO(VOObject voObject)
    {
        this.filteredOnlineReportVO = (FilteredOnlineReportVO) voObject;
    }

    public boolean isNew()
    {
        return filteredOnlineReportImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity() 
    {
        return filteredOnlineReportImpl.cloneCRUDEntity(this);
    }

    /**
     * Set the FilteredOnlineReportVO into this entity for the productStructure and onlineReport keys passed in.
     * @param productStructurePK
     * @param onlineReportPK
     * @throws Exception
     */
    public void findAttached(long productStructurePK, long onlineReportPK) throws Exception
    {
        filteredOnlineReportVO = filteredOnlineReportImpl.findAttached(productStructurePK, onlineReportPK);
    }

    public void detachOnlineReportFromProductStructure() throws Exception
    {
        filteredOnlineReportImpl.detachOnlineReportFromProductStructure(this, filteredOnlineReportVO);
    }


    public void cloneFilteredOnlineReportVO(ProductStructure productStructure, FilteredOnlineReport filteredOnlineReport) throws Exception
    {
        filteredOnlineReportImpl.cloneFilteredOnlineReportVO(productStructure, filteredOnlineReport);
    }

    public void findByOnlineReportPKAndProductStructure(long onlineReportPK, long productStructureId)
    {
        filteredOnlineReportVO = filteredOnlineReportImpl.findByOnlineReportPKAndProductStructure(onlineReportPK, productStructureId);
    }
}
