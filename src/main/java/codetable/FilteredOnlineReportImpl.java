/**
 * Created by IntelliJ IDEA.
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

import codetable.dm.dao.DAOFactory;
import engine.ProductStructure;
import engine.*;


public class FilteredOnlineReportImpl extends CRUDEntityImpl
{
    protected void load(CRUDEntity crudEntity, long pk) throws Exception
    {
        super.load(crudEntity, pk, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    protected void save(FilteredOnlineReport filteredOnlineReport) throws Exception
    {
        super.save(filteredOnlineReport, ConnectionFactory.EDITSOLUTIONS_POOL, false);
    }

    protected void delete(CRUDEntity crudEntity) throws Exception
    {
        super.delete(crudEntity, ConnectionFactory.EDITSOLUTIONS_POOL);
    }

    /**
     * For the selected productStructure and onlineReport keys, access the filteredOnlineReport records that satisfy the request.
     * @param productStructurePK
     * @param onlineReportPK
     * @return FilteredOnlineReportVO
     * @throws Exception
     */
    protected FilteredOnlineReportVO findAttached(long productStructurePK, long onlineReportPK) throws Exception
    {
        FilteredOnlineReportVO filteredOnlineReportVO = null;
        FilteredOnlineReportVO[] filteredOnlineReportVOs = DAOFactory.getFilteredOnlineReportDAO().findPKByOnlineReportPKAndProductStructure(onlineReportPK, productStructurePK);

        if (filteredOnlineReportVOs != null)
        {
            filteredOnlineReportVO = filteredOnlineReportVOs[0];
        }

        return filteredOnlineReportVO;
    }

    /**
     * For the selected productStructure and onlineReport keys, access the filteredOnlineReport records that satisfy the request
     * and delete the filteredOnlineReport records.
     * @param filteredOnlineReport
     * @param filteredOnlineReportVO
     * @throws Exception
     */
    protected void detachOnlineReportFromProductStructure(FilteredOnlineReport filteredOnlineReport, FilteredOnlineReportVO filteredOnlineReportVO) throws Exception
    {
        FilteredOnlineReportVO[] filteredOnlineReportVOs = DAOFactory.getFilteredOnlineReportDAO().findPKByOnlineReportPKAndProductStructure(filteredOnlineReportVO.getOnlineReportFK(), filteredOnlineReportVO.getProductStructureFK());

        if (filteredOnlineReportVOs != null)
        {
            filteredOnlineReport = new FilteredOnlineReport(filteredOnlineReportVOs[0]);
            delete(filteredOnlineReport);
        }
    }

    /**
     * For the selected ProductStructures, copy the from Product filteredOnlineReport records to requested ProductStructure.
     * @param productStructure
     * @param filteredOnlineReport
     * @throws Exception
     */
    protected void cloneFilteredOnlineReportVO(ProductStructure productStructure, FilteredOnlineReport filteredOnlineReport) throws Exception
    {
        long productStructurePK = productStructure.getPK();

        FilteredOnlineReportVO filteredOnlineReportVO = (FilteredOnlineReportVO)filteredOnlineReport.getVO();

        filteredOnlineReportVO.setFilteredOnlineReportPK(0);
        filteredOnlineReportVO.setProductStructureFK(productStructurePK);
        filteredOnlineReport.save();
    }

    /**
     * For the OnlineReport and ProductStructure keys selected, access the FilteredOnlineReport record that satisfies the request.
     * @param onlineReportPK
     * @param productStructureId
     * @return FilteredOnlineReportVO
     */
    public FilteredOnlineReportVO findByOnlineReportPKAndProductStructure(long onlineReportPK, long productStructureId)
    {
        FilteredOnlineReportVO[] filteredOnlineReportVOs = null;
        FilteredOnlineReportVO filteredOnlineReportVO = null;

        try
        {
            filteredOnlineReportVOs = DAOFactory.getFilteredOnlineReportDAO().findPKByOnlineReportPKAndProductStructure(onlineReportPK, productStructureId);
        }
        catch (Exception e)
        {
          System.out.println(e);

            e.printStackTrace();
        }


        if (filteredOnlineReportVOs != null)
        {
            filteredOnlineReportVO = filteredOnlineReportVOs[0];
        }

        return filteredOnlineReportVO;
    }
}
