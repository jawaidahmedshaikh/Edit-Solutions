<%@ page import="edit.common.vo.*,
                 edit.common.vo.user.*,
                 java.util.*,
                 fission.utility.*"%>
<%
    UIRulesVO[] uiRulesVOs = (UIRulesVO[]) request.getAttribute("uiRulesVOs");

    ScriptVO[] scriptVOs = (ScriptVO[]) request.getAttribute("scriptVOs");
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

<span class="tableHeading">Script Name:&nbsp;<%= scriptVOs[0].getScriptName() %></span>
<br>
<span class="tableHeading">Associated Rules</span><br>
  <table class="summaryArea" width="100%" height="65%" border="0" cellspacing="0" cellpadding="0">
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
          Script Name
      </th>
    </tr>
    <tr height="100%">
      <td colspan="8" valign="top">
        <span class="scrollableContent">
          <table id="rulesTable" width="100%" class="scrollableArea">
          <%
              if (uiRulesVOs != null) {

                  Arrays.sort(uiRulesVOs);
                  for (int i = 0; i < uiRulesVOs.length; i++){

                      String currentEffectiveDate = uiRulesVOs[i].getRulesVO().getEffectiveDate();
                      String currentProcess = uiRulesVOs[i].getRulesVO().getProcessName();
                      String currentEvent = uiRulesVOs[i].getRulesVO().getEventName();
                      String currentEventType = uiRulesVOs[i].getRulesVO().getEventTypeName();
                      String currentRule = uiRulesVOs[i].getRulesVO().getRuleName();
                      String scriptOrTableName = null;
                      boolean isScript = false;

                      if (uiRulesVOs[i].getRulesVO().getScriptFK() != 0) {

                          scriptOrTableName = uiRulesVOs[i].getScriptVO().getScriptName();
                          isScript = true;
                      }
                      else {

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
