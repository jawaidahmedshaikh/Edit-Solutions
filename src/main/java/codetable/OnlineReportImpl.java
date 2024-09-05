/**
 *
 *
 * User: gfrosti
 * Date: Oct 24, 2003
 * Time: 11:23:14 AM
 * (c) 2000-2004 Systems Engineering Group, LLC. All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential. Any use is
 * subject to the license agreement.
 */

package codetable;

import edit.services.db.*;
import edit.common.vo.*;

import java.util.*;

import codetable.dm.dao.DAOFactory;
import engine.ProductStructure;

public class OnlineReportImpl extends CRUDEntityImpl
 {
    protected void load(CRUDEntity crudEntity, long pk) throws Exception
    {
        super.load(crudEntity, pk, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void save(OnlineReport onlineReport) throws Exception
    {
        super.save(onlineReport, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    protected void delete(CRUDEntity crudEntity) throws Exception
    {
        super.delete(crudEntity, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * Delete the requested OnlineReport record but first delete its children
     * @param onlineReport
     * @throws Exception
     */
    protected void deleteOnlineReport(OnlineReport onlineReport) throws Exception
    {
        deleteChildren(onlineReport);
        delete(onlineReport);

    }

    /**
     * Delete the children if any of the OnlineReport record requested
     * @param onlineReport
     * @throws Exception
     */
    private void deleteChildren(OnlineReport onlineReport) throws Exception
    {
        OnlineReportVO onlineReportVO = (OnlineReportVO) onlineReport.getVO();

        long onlineReportPK = onlineReportVO.getOnlineReportPK();

        FilteredOnlineReportVO[] filteredOnlineReportVOs = DAOFactory.getFilteredOnlineReportDAO().findByOnlineReportPK(onlineReportPK);

        if (filteredOnlineReportVOs != null) {
            for (int i = 0; i < filteredOnlineReportVOs.length; i++)
            {
                FilteredOnlineReport filteredOnlineReport = new FilteredOnlineReport(filteredOnlineReportVOs[i]);
                filteredOnlineReport.delete();
            }
        }

    }

    /**
     * Get the requested OnlineReport record
     * @param onlineReportPK
     * @return OnlineReportVO
     * @throws Exception
     */
    protected OnlineReportVO getSpecificOnlineReport(long onlineReportPK) throws Exception
    {
        OnlineReportVO onlineReportVO = null;
        OnlineReportVO[] onlineReportVOs = DAOFactory.getOnlineReportDAO().findSpecificOnlineReportEntry(onlineReportPK);

        if (onlineReportVOs != null)
        {
            onlineReportVO = onlineReportVOs[0];
        }

        return onlineReportVO;
    }

    /**
     * Build an array of OnlineReport entities for a specific product structure
     * @param onlineReportPK
     * @param productStructure
     * @return  OnlineReport array
     * @throws Exception
     */
    protected OnlineReport[] getOnlineReportEntriesWithProductStructure(ProductStructure productStructure) throws Exception
    {
        long productStructurePK = productStructure.getPK();
        List orEntries = new ArrayList();

        OnlineReportVO[] onlineReportVOs = DAOFactory.getOnlineReportDAO().findAllOnlineReportVOs();

        if (onlineReportVOs != null)
        {
            for (int i = 0; i < onlineReportVOs.length; i++)
            {
                OnlineReport onlineReport = new OnlineReport(onlineReportVOs[i]);
                onlineReport.setIsAttached(false);

                if (productStructurePK != 0)
                {
                    FilteredOnlineReport filteredOnlineReport = new FilteredOnlineReport();

                    filteredOnlineReport.findAttached(productStructurePK, onlineReportVOs[i].getOnlineReportPK());

                    FilteredOnlineReportVO filteredOnlineReportVO = (FilteredOnlineReportVO) filteredOnlineReport.getVO();

                    long filteredOnlineReportPK = 0;

                    if (filteredOnlineReportVO != null)
                    {
                        onlineReport.setIsAttached(true);
                        filteredOnlineReportPK = filteredOnlineReportVO.getFilteredOnlineReportPK();
                        onlineReport.setFilteredOnlineReportPK(filteredOnlineReportPK);
                    }
                    else
                    {
                        onlineReport.setIsAttached(false);
                    }

                    onlineReport.setFilteredOnlineReportPK(filteredOnlineReportPK);
                }

                orEntries.add(onlineReport);
                }
        }

        return (OnlineReport[]) orEntries.toArray(new OnlineReport[orEntries.size()]);
    }

    /**
     * For the reportCategory in OnlineReport and ProductStructure, find the OnlineReport record that satisfies the criteria.
     * @param onlineReport
     * @param productStructure
     * @throws Exception
     */
    public void getOnlineReportForCategory(OnlineReport onlineReport, ProductStructure productStructure) throws Exception
    {
        long productStructurePK = productStructure.getPK();
        String reportCateogory = onlineReport.getReportCategory();
        OnlineReportVO onlineReportVO = null;
        OnlineReportVO[] onlineReportVOs = DAOFactory.getOnlineReportDAO().findOnlineReportForCategory(reportCateogory, productStructurePK);

        if (onlineReportVOs != null)
        {
            onlineReportVO = onlineReportVOs[0];
        }

        onlineReport.setVO(onlineReportVO);
    }

    /**
     * Get all OnlineReportRecords
     * @return OnlineReportVO array
     * @throws Exception
     */
    public OnlineReportVO[] getAllOnlineReportVOs() throws Exception
    {
        return DAOFactory.getOnlineReportDAO().findAllOnlineReportVOs();
    }

    /**
     * Build an array of OnlineReport entities for a specific product structure
     * @param onlineReportPK
     * @param productStructure
     * @return  OnlineReportVO
     */
    public OnlineReport[] getOnlineReportEntries(ProductStructure productStructure) throws Exception
    {
        OnlineReport[] onlineReports = getOnlineReportEntriesWithProductStructure(productStructure);

        return onlineReports;
    }

    /**
     * BIZVO returns the OnlineReport
     * @param onlineReportEntities
     * @return  BIZOnlineReportVO array
     */
    protected BIZOnlineReportVO[] mapEntityToBIZVO(OnlineReport[] onlineReportEntities)
    {
        List bizVOs = new ArrayList();

        for (int i = 0; i < onlineReportEntities.length; i++)
        {
            BIZOnlineReportVO bizOnlineReportVO = setBIZOnlineReport(onlineReportEntities[i]);

            bizVOs.add(bizOnlineReportVO);
        }

        return (BIZOnlineReportVO[]) bizVOs.toArray(new BIZOnlineReportVO[bizVOs.size()]);
    }

    /**
     * Set the OnlineReport data into the BIZOnlineReportVO
     * @param onlineReportEntity
     * @return BIZOnlineReportVO
     */

    private BIZOnlineReportVO setBIZOnlineReport(OnlineReport onlineReportEntity)
    {
        BIZOnlineReportVO bizOnlineReportVO = new BIZOnlineReportVO();

        bizOnlineReportVO.setOnlineReportVO((OnlineReportVO) onlineReportEntity.getVO());
        bizOnlineReportVO.setFilteredOnlineReportPK(onlineReportEntity.getFilteredOnlineReportPK());
        bizOnlineReportVO.setIsOnlineReportAttached(onlineReportEntity.getIsAttached());

        return bizOnlineReportVO;
    }

    public OnlineReport[] getSpecificOnlineReportEntries(ProductStructure productStructure)  throws Exception
    {
        long productStructurePK = productStructure.getPK();
        List orEntries = new ArrayList();
        OnlineReportVO onlineReportVO = null;

        FilteredOnlineReportVO[] filteredOnlineReportVOs = DAOFactory.getFilteredOnlineReportDAO().findByProductStructure(productStructurePK);

        if (filteredOnlineReportVOs != null)
        {
            for (int i = 0; i < filteredOnlineReportVOs.length; i++)
            {
                OnlineReport onlineReport = new  OnlineReport(filteredOnlineReportVOs[i].getOnlineReportFK());
                onlineReportVO = onlineReport.getSpecificOnlineReport(onlineReport);

                if (onlineReportVO != null)
                {
                    onlineReport.setIsAttached(true);
                    onlineReport.setVO(onlineReportVO);
                    onlineReport.setFilteredOnlineReportPK(filteredOnlineReportVOs[i].getFilteredOnlineReportPK());
                    orEntries.add(onlineReport);
                }
            }
        }

        return (OnlineReport[]) orEntries.toArray(new OnlineReport[orEntries.size()]);
    }
}










