<%@ page import="edit.common.vo.*,
                 edit.common.vo.user.*,
                 java.util.*,
                 fission.utility.*"%>
<%
    UIRulesVO[] uiRulesVOs = (UIRulesVO[]) request.getAttribute("uiRulesVOs");

    TableDefVO[] tableDefVOs = (TableDefVO[]) request.getAttribute("tableDefVOs");
%>
<html>

<head>
<title>Attached Rules By Company Structure</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">

<script language="Javascript1.2">

	function init() {

	}

    function closeWindow()
    {
        window.close();
    }

</script>
</head>
<body  class="mainTheme" onLoad="init()">

<span class="tableHeading">Table Information</span><br>
<table border="0" width="100%" height="15%" align="center" cellspacing="0" class="summaryArea" style="overflow:visible">

    <tr>
        <th width="4%">
            &nbsp;
        </th>
        <th width="11%">
            Name
        </th>
        <th width="10%">
            Eff Date
        </th>
        <th width="11%">
            Sex
        </th>
        <th width="10%">
            Class
        </th>
        <th width="11%">
            Band Amt
        </th>
        <th width="10%">
            Area
        </th>
        <th width="11%">
            Type
        </th>
        <th width="11%">
            User Key
        </th>
        <th width="11%">
            Access Type
        </th>
    </tr>
    <tr>
        <td colspan="10" height="95%">
            <span class="scrollableContent">
            <table class="scrollableArea" border="0" width="100%" height="100%" cellspacing="0">
<%
    if (tableDefVOs != null)
    {
        for (int i = 0; i < tableDefVOs.length; i++)
        {
            TableDefVO currentTableDefVO = tableDefVOs[i];
            String currentTableName = Util.initString(currentTableDefVO.getTableName(), "");
            String currentAccessType = Util.initString(currentTableDefVO.getAccessType(), "");
            String currentEffectiveDate = "";
            String currentGender = "";
            String currentClass = "";
            String currentBandAmount = "";
            String currentArea = "";
            String currentTableType = "";
            String currentUserKey = "";

            TableKeysVO[] currentTableKeysVOs = currentTableDefVO.getTableKeysVO();

            if (currentTableKeysVOs != null)
            {
                for (int j = 0; j < currentTableKeysVOs.length; j++)
                {
                    currentEffectiveDate = Util.initString(currentTableKeysVOs[j].getEffectiveDate(), "");
                    currentGender = Util.initString(currentTableKeysVOs[j].getGender(), "");
                    currentClass = Util.initString(currentTableKeysVOs[j].getClassType(), "");
//                    currentBandAmount = Util.formatDecimal("#,##0.00", currentTableKeysVOs[j].getBandAmount());
                    currentBandAmount = currentTableKeysVOs[j].getBandAmount().toString();
                    currentArea = Util.initString(currentTableKeysVOs[j].getState(), "");
                    currentTableType = Util.initString(currentTableKeysVOs[j].getTableType(), "");
                    currentUserKey = Util.initString(currentTableKeysVOs[j].getUserKey(), "");
%>
                <tr class="mainEntry">
                    <td width="4%">
                        &nbsp;
                    </td>
                    <td width="11%">
                        <%= currentTableName %>&nbsp;
                    </td>
                    <td width="10%">
                        <%= currentEffectiveDate %>&nbsp;
                    </td>
                    <td width="11%">
                        <%= currentGender %>&nbsp;
                    </td>
                    <td width="10%">
                        <%= currentClass %>&nbsp;
                    </td>
                    <td width="11%">
                        <%= currentBandAmount %>&nbsp;
                    </td>
                    <td width="10%">
                        <%=  currentArea %>&nbsp;
                    </td>
                    <td width="11%">
                        <%= currentTableType %>&nbsp;
                    </td>
                    <td width="11%">
                        <%= currentUserKey %>&nbsp;
                    </td>
                    <td width="11%">
                        <%= currentAccessType %>&nbsp;
                    </td>
                </tr>
<%
                }
            }
        }
    }
%>
            </span>
            </table>
        </td>
    </tr>

</table>


    <br>
    <span class="tableHeading">Associated Rules</span><br>
    <table class="summaryArea" width="100%" height="50%" border="0" cellspacing="0" cellpadding="0">

        <tr>

            <th width="4%">
                &nbsp;
            </th>
            <th width="16%">
                Effective Date
            </th>
            <th width="16%">
                Process
            </th>
            <th width="16%">
                Event
            </th>
            <th width="16%">
                Event Type
            </th>
            <th width="16%">
                Rule Name
            </th>
            <th width="16%">
                Table Name
            </th>

        </tr>

        <tr height="100%">

            <td colspan="8" valign="top">
            <span class="scrollableContent">


                    <table id="rulesTable" width="100%" class="scrollableArea">
<%
                        if (uiRulesVOs != null){

                            Arrays.sort(uiRulesVOs);

                            for (int i = 0; i < uiRulesVOs.length; i++){

                                String currentEffectiveDate = uiRulesVOs[i].getRulesVO().getEffectiveDate();
                                String currentProcess = uiRulesVOs[i].getRulesVO().getProcessName();
                                String currentEvent = uiRulesVOs[i].getRulesVO().getEventName();
                                String currentEventType = uiRulesVOs[i].getRulesVO().getEventTypeName();
                                String currentRule = uiRulesVOs[i].getRulesVO().getRuleName();
                                String scriptOrTableName = null;
                                boolean isScript = false;

                                if (uiRulesVOs[i].getRulesVO().getScriptFK() != 0)
                                {
                                    scriptOrTableName = uiRulesVOs[i].getScriptVO().getScriptName();
                                    isScript = true;
                                }
                                else
                                {
                                    scriptOrTableName = uiRulesVOs[i].getTableDefVO().getTableName();
                                    isScript = false;
                                }

                                String className = "mainEntry";
%>
                                <tr height="15" class="<%= className %>">

                                    <td width="4%">
                                        &nbsp;
                                    </td>
                                    <td width="16%">
                                        <%= currentEffectiveDate %>
                                    </td>
                                    <td width="16%">
                                        <%= currentProcess %>
                                    </td>
                                    <td width="16%">
                                        <%= currentEvent %>
                                    </td>
                                    <td width="16%">
                                        <%= currentEventType %>
                                    </td>
                                    <td width="16%">
                                        <%= currentRule %>
                                    </td>
                                    <td width="16%">
                                        <%= scriptOrTableName %>
                                    </td>
                                </tr>
<%
                            }
                        }
%>
                    </table>

                    </span>
            </td>
        </tr>

    </table>

<table width="100%">
    <tr>
         <td align="right" valign="bottom">
            <input type="button" name="clone" value="Close" onClick="closeWindow()">
        </td>
    </tr>
</table>
</body>
</html>
