package engine.unittest;

import edit.portal.common.transactions.*;
import edit.portal.common.transactions.Transaction;
import edit.portal.widget.*;
import edit.common.vo.*;
import edit.services.db.hibernate.*;
import fission.global.*;
import engine.*;

import java.sql.*;


/**
 * User: cgleason
 * Date: Mar 3, 2006
 * Time: 2:59:51 PM
 * <p/>
 * (c) 2000 - 2006 Systems Engineering Group, LLC.  All rights Reserved
 * Systems Engineering Group, LLC Propietary and confidential.  Any use
 * subject to the license agreement.
 */
public class ScriptExportTran extends Transaction
{
    private static final String COPY_SELECTED_SCRIPTS = "copySelectedScripts";
    private static final String SHOW_SCRIPTLIST = "showScriptList";

    private String SCRIPT_EXPORT = "/unittest/scriptExport.jsp";
    private Connection conn = null;

    public String execute(AppReqBlock appReqBlock)  throws Exception
    {
        String action = appReqBlock.getReqParm("action");

        String returnPage = null;

        if (action.equals(SHOW_SCRIPTLIST))
        {
            returnPage = showScriptList(appReqBlock);
        }
        else if (action.equals(COPY_SELECTED_SCRIPTS))
        {
            returnPage = copySelectedScripts(appReqBlock);
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
        getConnection();
        ScriptSummaryTableModel scriptSummaryTableModel =  new ScriptSummaryTableModel(appReqBlock, conn);

        return SCRIPT_EXPORT;
    }


   private String copySelectedScripts(AppReqBlock appReqBlock)
   {
       String keys[] = new ScriptSummaryTableModel(appReqBlock, conn).getSelectedRowIdsFromRequestScope();

       SessionHelper.beginTransaction(SessionHelper.ENGINE);

       for (int i = 0; i < keys.length; i++)
       {
           long scriptPK = Long.parseLong(keys[i]);
           ScriptLineVO[] scriptLineVOs = getScriptLines(scriptPK);
           ScriptVO scriptVO = getScript(scriptPK);
           String operator = appReqBlock.getUserSession().getUsername();
           String scriptName = scriptVO.getScriptName();
           addLinesToOuptut(scriptLineVOs, scriptName, operator);
       }

       SessionHelper.commitTransaction(SessionHelper.ENGINE);
//       closeConnection();

       return showScriptList(appReqBlock);
    }

    private ScriptVO getScript(long scriptPK)
    {
        ScriptVO scriptVO = new ScriptFastDAO().getScript(scriptPK, conn);

        return scriptVO;
    }

    private ScriptLineVO[] getScriptLines(long scriptPK)
    {
        ScriptLineVO[] scriptLineVOs = new ScriptFastDAO().getScriptLines(scriptPK, conn);
        return scriptLineVOs;
    }

    private void addLinesToOuptut(ScriptLineVO[] scriptLineVOs, String scriptName, String operator)
    {
        Script script = new Script();
        script.setScriptName(scriptName);
        script.setOperator(operator);

        for (int i = 0; i < scriptLineVOs.length; i++)
        {
            ScriptLineVO scriptLineVO = scriptLineVOs[i];
            ScriptLine scriptLine = new ScriptLine();
            scriptLine.setLineNumber(scriptLineVO.getLineNumber());
            scriptLine.setScriptLine(scriptLineVO.getScriptLine());
            script.add(scriptLine);
        }

        SessionHelper.saveOrUpdate(script, SessionHelper.ENGINE);
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
