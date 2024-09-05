/**
 * OnlineReport.java   Version 2.0 05/03/2004
 *
 * User: gfrosti
 * Date: May 03, 2004
 * Time: 9:55:40 AM
 * (c) 2000-2004 Systems Engineering Group, LLC. All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential. Any use is
 * subject to the license agreement.
 */

package codetable;

import edit.common.vo.*;
import edit.services.db.*;
import engine.ProductStructure;


public class OnlineReport implements CRUDEntity
{
    private OnlineReportVO onlineReportVO;

    private OnlineReportImpl onlineReportImpl;

    private boolean isAttached;

    private long filteredOnlineReportPK;

    private String reportCategory;

    /**
     * Constructor - set OnlineReportVO and OnlineReportImpl
     */
    public OnlineReport()
    {
        onlineReportVO = new OnlineReportVO();
        onlineReportImpl = new OnlineReportImpl();
    }

    public OnlineReport(OnlineReportVO onlineReportVO)
    {
        this();
        this.onlineReportVO = onlineReportVO;
    }

    public OnlineReport(long onlineReportPK) throws Exception
    {
        this();
        this.onlineReportImpl.load(this, onlineReportPK);
    }

   public OnlineReport(String reportCategory) throws Exception
    {
        this();
        this.reportCategory = reportCategory;
    }

    public VOObject getVO()
    {
        return onlineReportVO;
    }

    public void save() throws Exception
    {
        onlineReportImpl.save(this);
    }

    public void delete() throws Exception
    {
        onlineReportImpl.delete(this);
    }

     public void deleteOnlineReport() throws Exception
    {
        onlineReportImpl.deleteOnlineReport(this);
    }

    public long getPK()
    {
        return onlineReportVO.getOnlineReportPK();
    }

    public void setVO(VOObject voObject)
    {
        this.onlineReportVO = (OnlineReportVO) voObject;
    }

    public boolean isNew()
    {
        return onlineReportImpl.isNew(this);
    }

    public CRUDEntity cloneCRUDEntity() 
    {
        return onlineReportImpl.cloneCRUDEntity(this);
    }

    public OnlineReportVO getSpecificOnlineReport(OnlineReport onlineReport) throws Exception
    {
        return onlineReportImpl.getSpecificOnlineReport(onlineReport.getPK());
    }

    public OnlineReport[] getOnlineReportEntriesWithProductStructure( ProductStructure productStructure) throws Exception
    {
       return onlineReportImpl.getOnlineReportEntriesWithProductStructure(productStructure);
    }

    public boolean getIsAttached()
    {
        return this.isAttached;
    }

    public void setIsAttached(boolean isAttachedValue)
    {
        this.isAttached = isAttachedValue;
    }

    public long getFilteredOnlineReportPK()
    {
        return this.filteredOnlineReportPK;
    }

    public void setFilteredOnlineReportPK(long filteredOnlineReportPK)
    {
        this.filteredOnlineReportPK = filteredOnlineReportPK;
    }

    public OnlineReportVO getOnlineReportForCategory(ProductStructure productStructure) throws Exception
    {
        onlineReportImpl.getOnlineReportForCategory(this, productStructure);

        return (OnlineReportVO)getVO();
    }

    public String getReportCategory()
    {
        return this.reportCategory;
    }


    public OnlineReportVO[] getAllOnlineReportVOs() throws Exception
    {
        return onlineReportImpl.getAllOnlineReportVOs();
    }


    public OnlineReport[] getOnlineReportEntries(ProductStructure productStructure) throws Exception
    {
        return onlineReportImpl.getOnlineReportEntries(productStructure);
    }


    public  BIZOnlineReportVO[] mapEntityToBIZVO(OnlineReport[] onlineReportEntities)
    {
        return onlineReportImpl.mapEntityToBIZVO(onlineReportEntities);
    }

    public OnlineReport[] getSpecificOnlineReportEntries(ProductStructure productStructure) throws Exception
    {
        return onlineReportImpl.getSpecificOnlineReportEntries(productStructure);
    }
}
