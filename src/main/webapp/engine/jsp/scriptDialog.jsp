<!--
 * User: sprasad
 * Date: May 5, 2006
 * Time: 11:17:49 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 -->
 
<%@ page import="engine.Script,
                 engine.ScriptLine"%>
 
<%
    String scriptPK = (String) request.getAttribute("scriptPK");

    Script script = Script.findByPK(new Long(scriptPK));

    ScriptLine[] scriptLines = ScriptLine.findScriptLinesByScriptPK(new Long(scriptPK));        
%>

<script>

  function init()
  {
    // null
  }

</script>
<div class="scrollableContent" style="padding:0px; margin:0px; border-style: solid; border-width:1; background:#FFFFFF; position:relative; width:100%; height:90%; top:0; left:0;">

 <table id="scriptTable" bgColor="FFFFFF" border="0" width="100%" cellpadding="2" cellspacing="0">

   <tr>
      <td colspan="2"> <%= script.getScriptName() %></td>
   </tr>

   <%
       for (int i = 0; i <scriptLines.length; i++)
       {
   %>

   <tr>
        <td width="15"valign="top"><%= scriptLines[i].getLineNumber() %></td>
        <td valign="top"><%= scriptLines[i].getScriptLine() %></td>
   </tr>

   <%
       }
   %>
 </table>

</div>

<div align="right">
    <input id="btnclose" type="button" name="close" value="Close" onclick="closeWindow()">
</div>
