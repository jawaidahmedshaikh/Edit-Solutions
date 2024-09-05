/**
 * User: cgleason
 * Date: Mar 3, 2006
 * Time: 2:59:51 PM
 * <p/>
 * (c) 2000 - 2006 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.ui.servlet;

import edit.common.EDITList;

import edit.portal.common.transactions.Transaction;
import edit.portal.widget.ScriptExportSummaryTableModel;
import edit.portal.widgettoolkit.TableModel;

import fission.global.AppReqBlock;

import fission.utility.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class FileExportTran extends Transaction
{
    private static final String EXPORT_SELECTED_SCRIPTS = "exportSelectedScripts";
    private static final String SHOW_SCRIPTLIST = "showScriptList";

    private static final String EXPORT_TOOLBAR = "/engine/jsp/exportToolBar.jsp";
    private static final String SCRIPT_EXPORT = "/engine/jsp/scriptExport.jsp";
    private static final String TEMPLATE_TOOLBAR_MAIN = "/common/jsp/template/template-toolbar-main.jsp";

    private Connection conn = null;

    public String execute(AppReqBlock appReqBlock)  throws Exception
    {
        String action = appReqBlock.getReqParm("action");

        String returnPage = null;

        if (action.equals(SHOW_SCRIPTLIST))
        {
            returnPage = showScriptList(appReqBlock);
        }
        else if (action.equals(EXPORT_SELECTED_SCRIPTS))
        {
            returnPage = exportSelectedScripts(appReqBlock);
        }
        else
        {
            String transaction = appReqBlock.getReqParm("transaction");

            throw new RuntimeException("Unrecognized Transaction/Action [" + transaction + " / " + action + "]");
        }

        return returnPage;
    }


    private String showScriptList(AppReqBlock appReqBlock)
    {
//        new ScriptExportSummaryTableModel(appReqBlock);
//
//        return SCRIPT_EXPORT;


        TableModel.get(appReqBlock, ScriptExportSummaryTableModel.class);

        appReqBlock.putInRequestScope("toolbar", EXPORT_TOOLBAR);

        appReqBlock.putInRequestScope("main", SCRIPT_EXPORT);

        return TEMPLATE_TOOLBAR_MAIN;
    }


   private String exportSelectedScripts(AppReqBlock appReqBlock) throws Exception
   {
       String scriptPKs[] = new ScriptExportSummaryTableModel(appReqBlock).getSelectedRowIdsFromRequestScope();

       EDITList editList = new EDITList();

       boolean errorFound = false;

       if(scriptPKs.length == 1 && scriptPKs[0].equals(""))
       {
           editList.addTo("NO SELECTIONS MADE");
           errorFound = true;
       }

       engine.business.Calculator calculator = new engine.component.CalculatorComponent();

       try
       {
           if (!errorFound)
           {
               calculator.exportScriptsToXML(scriptPKs);
               
               editList.addTo("EXPORT SUCCESSFUL");
           }
       }
       catch(Exception e)
       {
            //logging here?
            throw e;
       }

       appReqBlock.putInRequestScope("pageMessage", editList);


       return showScriptList(appReqBlock);
    }


    private void getConnection()
    {
        if (conn == null)
        {
            try
            {
//            <DriverClassName>oracle.jdbc.driver.OracleDriver</DriverClassName>
//            <URL>jdbc:oracle:thin:@192.168.0.176:1521:orcl</URL>
//            <SchemaName>ENGINE</SchemaName>
//            <Username>SEGADMIN</Username>
//            <Password>segllc</Password>
//            <MinPoolSize>5</MinPoolSize>
//            <MaxPoolSize>10</MaxPoolSize>
//        <Dialect>org.hibernate.dialect.Oracle9Dialect</Dialect>
//  C:\Perforce\EditSolutions\EQDevelopment\webapps\WEB-INF\EDITServicesConfig.xml
                Class.forName("oracle.jdbc.driver.OracleDriver");
                String connectURI =  "jdbc:oracle:thin:@192.168.0.176:1521:orcl";
                String username = "SEGADMIN";
                String password = "segllc";
                conn = DriverManager.getConnection(connectURI, username, password);

            }
            catch (Throwable e)
            {
                System.out.println("OracleConnection.static{}: " + e);
            }
        }
    }

    private void closeConnection()
    {
       try
       {
           conn.close();
       }
       catch (SQLException e)
       {
           System.out.println("OracleConnection.static{}: " + e);
       }
    }
}
