<%@ page import="edit.common.vo.HierarchyReportVO,
                 edit.common.vo.HierarchyReportEntryVO,
                 edit.common.vo.PlacedAgentVO,
                 fission.utility.Util,
                 agent.ui.*,
                 agent.*,
                 edit.services.db.hibernate.*"%>

<%
    HierarchyReport hierarchyReport = (HierarchyReport) request.getAttribute("hierarchyReport");

    boolean includeExpiredAgents = hierarchyReport.getIncludeExpiredAgents();
    
    int reportType = hierarchyReport.getHierarchyReportType();

    String contractCode = hierarchyReport.getContractCode();

    HierarchyReportEntry[] reportEntries = hierarchyReport.getHierarchyReportEntries();

    // Just default this - may change this back in the future.
    boolean showFullDetail = false;

    String activePlacedAgentPK = (String) request.getAttribute("activePlacedAgentPK");
%>
<html>
    <head>

        <title>EDITSOLUTIONS - Agent Hierarchies</title>

        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <meta http-equiv="Cache-Control" content="no-store">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">

        <link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

        <script src="/PORTAL/common/javascript/commonJavascriptFunctions.js" type="text/javascript"></script>
        
        <script language="javascript1.2">
            
            var f = null;

            var activePlacedAgentPK = "<%= activePlacedAgentPK %>";
            
            var reportType = <%= reportType %>;

            function init()
            {
                f = document.theForm;
            
                if (activePlacedAgentPK != "null")
                {
                    document.getElementById(activePlacedAgentPK).scrollIntoView(true);
                }
            }

            /**
             * Regenerates this report with the intention of including/excluding the terminated agents.
             */
            function generateReport()
            {
                if (f.includeExpiredAgentsCheckBox.checked)
                {
                    f.includeExpiredAgents.value= "true";
                }
                else
                {
                    f.includeExpiredAgents.value = "false";
                }
            
                if (reportType == <%=  HierarchyReport.BY_CONTRACTCODECT %>)
                {
                    sendTransactionAction("AgentDetailTran", "showHierarchyReport", "_self");
                }
                else if (reportType == <%= HierarchyReport.BY_PLACEDAGENT %>)
                {
                    sendTransactionAction("AgentDetailTran", "generateHierarchyReportByPlacedAgentPK", "_self");
                }
            }
            
        </script>
        
        
        
        <style>
            
            table tr
            {
                color: #000000;
                height: 20px;
                text-align: left;            
            }
            
            tr.highlighted
            {
                background-color: #FFFFCC;
            }     
       
            tr.disabled 
            {
                font: italic;
                color: Gray;
            }  
          
            tr.default
            {

            }
            
        </style>

    </head>

    <body class="mainTheme" onLoad="init()">
    
        

            <h3 align="center">Contract Code: <%= contractCode %></h3>
            <form name="theForm" method="post" action="/PORTAL/servlet/RequestManager" style="width:100%; height:100%;">    

            <table border="0" width="100%" cellspacing="0" cellpadding="0">
                <tr>
                    <th width="10%" align="left" nowrap>[ Name ]</th>
                    <th width="10%" align="left" nowrap>[ Agent Id ]</th>
                    <th width="10%" align="left" nowrap>[ Level ]</th>
                    <th width="10%" align="left" nowrap>&nbsp;</th>
                    <th width="10%" align="left" nowrap>&nbsp;</th>
                    <th width="10%" align="left" nowrap>&nbsp;</th>
                    <th width="10%" align="left" nowrap>&nbsp;</th>
                    <th width="10%" align="left" nowrap>&nbsp;</th>
                    <th width="10%" align="left" nowrap>&nbsp;</th>
                    <th width="10%" align="left" nowrap>Include Terminated Agents:&nbsp;<input type="checkbox" name="includeExpiredAgentsCheckBox" <%= includeExpiredAgents?"CHECKED":"" %> onclick="generateReport()"/>&nbsp;&nbsp;&nbsp;&nbsp;</th>
                </tr>
            </table>

            <span STYLE="border-style:solid; background-color:#BBBBBB; border-width:1px; width:100%; height:90%; overflow: scroll">

                <table border="0" width="100%" border="0" cellspacing="4" cellpadding="0">
                    <%
                        if (reportEntries != null)
                        {
            for (int i = 0; i < reportEntries.length; i++)
            {
                ReportViewHelper helper = new ReportViewHelper();

                out.println("<tr><td colspan='10'><font face='' color='blue'>Hierarchy # " + (i + 1) + "</font></td></tr>");

                long placedAgentPK = (activePlacedAgentPK == null)?0:Long.parseLong(activePlacedAgentPK);

                helper.treePrintHierarchyReportEntry(reportEntries[i], i, out, showFullDetail, placedAgentPK, includeExpiredAgents);

                out.println("<tr><td align='right' bgColor='' colspan='10'><font face='' >Total Agents For Hierarchy # " + (i + 1) + ":</font> <font face='' color=''>&nbsp;&nbsp;[ " + helper.getAgentCount() + " ]</font><hr noshade color='black' size='1'></td></tr>");

                out.println("<tr><td colspan='10'>&nbsp;</td></tr>");
            }
                        }
                    %>
                </table>

            </span>

            <table border="0" width="100%" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="right">
                        <input type="button" value="Close" onClick="window.close()">
                    </td>
                </tr>
            </table>
            
            <input type="hidden" name="movingFromToPlacedAgentPK" value="MOVING:<%= activePlacedAgentPK %>"/>
            <input type="hidden" name="activePlacedAgentPK" value="<%= activePlacedAgentPK %>"/>
            <input type="hidden" name="contractCodeCT" value="<%= contractCode %>"/>
            <input type="hidden" name="includeExpiredAgents"/>
            <input type="hidden" name="transaction"/>
            <input type="hidden" name="action"/>
            
 
            </form>

    </body>

</html>

<%
    hierarchyReport.clear();

    System.gc();// It's a killer with memory.
%>
