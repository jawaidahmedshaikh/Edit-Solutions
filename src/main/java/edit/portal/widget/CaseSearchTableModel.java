/*
 * User: cgleason
 * Date: Jan 12, 2006
 * Time: 10:26:26 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package edit.portal.widget;

import fission.global.*;
import fission.utility.*;
import edit.portal.widgettoolkit.*;

import java.util.*;

import contract.*;
import client.*;
import group.*;


public class CaseSearchTableModel  extends TableModel
{
    public static final String COLUMN_CASE_NUMBER = "Case Number";
    
    public static final String COLUMN_NAME = "Case Name";

    private static final String[] COLUMN_NAMES = {COLUMN_CASE_NUMBER, COLUMN_NAME};

    public CaseSearchTableModel(AppReqBlock appReqBlock)
    {
        super(appReqBlock);

        getColumnNames().addAll(Arrays.asList(COLUMN_NAMES));
    }

    /**
     * Builds the superset TableModel
     */
    protected void buildTableRows()
    {
        String name = Util.initString((String) getAppReqBlock().getHttpServletRequest().getParameter("caseName"), null);
        
        String caseNumber = Util.initString((String) getAppReqBlock().getHttpServletRequest().getParameter("caseNumber"), null);

        if (caseNumber != null)
        {
            ContractGroup caseDetail = ContractGroup.findBy_ContractGroupNumber_ContractGroupTypeCT(caseNumber, ContractGroup.CONTRACTGROUPTYPECT_CASE);
            
            if (caseDetail != null)
            {
                ClientDetail clientDetail = caseDetail.getOwnerClient();
                
                TableRow tableRow = new CaseSearchTableRow(caseDetail, clientDetail);
                
                super.addRow(tableRow);
            }
        }
        else if (name != null)
        {
            ContractGroup[] contractGroups = ContractGroup.findBy_PartialName_AssociatedWithContractGroup(name);
            
            for (ContractGroup contractGroup:contractGroups)
            {
              ClientDetail clientDetail = contractGroup.getOwnerClient();
              
              TableRow tableRow = new CaseSearchTableRow(contractGroup, clientDetail);
              
              super.addRow(tableRow);              
            }
        }
    }
}
