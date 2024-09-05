<%@ page import="engine.Script" %>
<%@ page import="engine.ScriptLine" %>
<%@ page import="edit.services.db.hibernate.SessionHelper" %>
<!--
 * User: sdorman
 * Date: Nov 18, 2010
 * Time: 2:25:44 PM
 *
 * (c) 2000-2010 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 -->
<%
    //  Fixes any ScriptLines whose line number do not start with 1.  We don't know what caused this problem
    //  but having the line numbers messed up, messes up our comparison programs.
    //
    //  This gets all scripts, loops through them to get the ScriptLines in order by line number.  If the
    //  first ScriptLine does not have a lineNumber equal to 1, the lineNumbers are set to start at 1.
    Script[] scripts = Script.findAll();

    for (int i = 0; i < scripts.length; i++)
    {
        Script script = scripts[i];

        ScriptLine[] scriptLines = ScriptLine.findScriptLinesByScriptPK(script.getScriptPK());

        if (scriptLines[0].getLineNumber() != 1)
        {
            SessionHelper.beginTransaction(ScriptLine.DATABASE);

            //  Reorder the line numbers starting at 1
            for (int j = 0; j < scriptLines.length; j++)
            {
                ScriptLine scriptLine = scriptLines[j];

                scriptLine.setLineNumber(j + 1);

                SessionHelper.saveOrUpdate(scriptLine, ScriptLine.DATABASE);
            }

            SessionHelper.commitTransaction(ScriptLine.DATABASE);
        }
    }

    SessionHelper.clearSessions();
    
%>
