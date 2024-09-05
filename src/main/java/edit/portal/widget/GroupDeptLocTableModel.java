/*
 * User: dlataille
 * Date: May 2, 2007
 * Time: 8:02:25 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.portal.widget;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import edit.portal.widgettoolkit.TableModel;
import edit.portal.widgettoolkit.TableRow;
import fission.global.AppReqBlock;
import fission.utility.Util;
import group.DepartmentLocation;


public class GroupDeptLocTableModel extends TableModel
{
    public GroupDeptLocTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(GroupDeptLocTableColumns.getColumnDisplayNames()));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        String selectedGroupPK = getAppReqBlock().getUserSession().getParameter("activeGroupPK");

        HttpServletRequest request = getAppReqBlock().getHttpServletRequest();
        String orderByColumn = Util.initString(request.getParameter("orderByColumn"), GroupDeptLocTableColumns.COLUMN_DEPT_LOC_CODE.getDisplayText());
        String filterTerminated = Util.initString(request.getParameter("filterTerminated"), "false");
        String descInd = Util.initString(request.getParameter("descInd"), "asc");

        if (selectedGroupPK != null)
        {
            String selectedDeptLocPK = getAppReqBlock().getUserSession().getParameter("activeDeptLocPK");

            DepartmentLocation[] departmentLocations = null;
            
            if (orderByColumn == null && "true".equals(filterTerminated)) {
            	departmentLocations = DepartmentLocation.findActiveBy_ContractGroupFK(new Long(selectedGroupPK));
            } else if (orderByColumn != null && "false".equals(filterTerminated)) {
            	departmentLocations = DepartmentLocation.findBy_ContractGroupFK_OrderBy(new Long(selectedGroupPK), GroupDeptLocTableColumns.getViaDisplayText(orderByColumn).getDbText(), descInd);            	
            } else if (orderByColumn != null && "true".equals(filterTerminated)) {
            	departmentLocations = DepartmentLocation.findActiveBy_ContractGroupFK_OrderBy(new Long(selectedGroupPK), GroupDeptLocTableColumns.getViaDisplayText(orderByColumn).getDbText(), descInd);            	
            } else {
            	departmentLocations = DepartmentLocation.findBy_ContractGroupFK(new Long(selectedGroupPK));
            }
            	
            if (departmentLocations != null) {
	            for (DepartmentLocation departmentLocation:departmentLocations)
	            {
	                GroupDeptLocTableRow tableRow = new GroupDeptLocTableRow(departmentLocation);
	
	                if (tableRow.getRowId().equals(this.getSelectedRowId()))
	                {
	                    tableRow.setRowStatus(TableRow.ROW_STATUS_SELECTED);
	                }
	                
	                addRow(tableRow);
	            }
            }
        }
    }
}
