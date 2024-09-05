/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 26, 2003
 * Time: 12:23:29 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package engine.unittest;

import edit.common.vo.*;
import edit.services.db.*;
import fission.utility.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.*;

import engine.*;


public class ScriptFastDAO extends AbstractFastDAO
{


    public ScriptFastDAO()
    {

    }


   /**
    * Getting All scripts from an Oracle db
    * @param conn
    * @return
    */
    public ScriptVO[] findAllScripts(Connection conn)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ScriptVO scriptVO = null;
        List scripts = new ArrayList();

        String sql = "  SELECT * FROM ENGINE.\"Script\" ORDER BY ENGINE.\"Script\".\"ScriptName\"";

        try
        {

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

           while (rs.next())
            {

                scriptVO = new ScriptVO();
                scriptVO.setScriptPK(rs.getLong("ScriptPK"));
                scriptVO.setScriptName(rs.getString("ScriptName"));
                scripts.add(scriptVO);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
            catch (SQLException e)
            {
              System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        return (ScriptVO[]) scripts.toArray(new ScriptVO[scripts.size()]);
    }

    /**
     * For a selected script get the scriptLines - Specific to an oracle db
     * @param scriptPK
     * @param conn
     * @return
     */
    public ScriptLineVO[] getScriptLines(long scriptPK, Connection conn)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ScriptLineVO scriptLineVO = null;

        List scriptLines = new ArrayList();
        String sql = "SELECT * FROM ENGINE.\"ScriptLine\" WHERE ENGINE.\"ScriptLine\".\"ScriptFK\" = " + scriptPK +
                     "ORDER BY ENGINE.\"ScriptLine\".\"LineNumber\"";
        try
        {

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next())
            {

                scriptLineVO = new ScriptLineVO();
                scriptLineVO.setScriptLinePK(rs.getLong("ScriptLinePK"));
                scriptLineVO.setLineNumber(rs.getInt("LineNumber"));
                scriptLineVO.setScriptLine(rs.getString("ScriptLine"));
                scriptLines.add(scriptLineVO);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
            catch (SQLException e)
            {
              System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        return (ScriptLineVO[]) scriptLines.toArray(new ScriptLineVO[scriptLines.size()]);
    }

    /**
     * Get a specific Script - from an Oracle db
     * @param scriptPK
     * @param conn
     * @return
     */
    public ScriptVO getScript(long scriptPK, Connection conn)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ScriptVO scriptVO = null;
        List scripts = new ArrayList();

        String sql = "  SELECT * FROM ENGINE.\"Script\"WHERE ENGINE.\"Script\".\"ScriptPK\" = " +scriptPK;

        try
        {

            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();

            if (rs.next())
            {

                scriptVO = new ScriptVO();
                scriptVO.setScriptPK(rs.getLong("ScriptPK"));
                scriptVO.setScriptName(rs.getString("ScriptName"));
                scripts.add(scriptVO);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            }
            catch (SQLException e)
            {
              System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        return scriptVO;
    }
}


