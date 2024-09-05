<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 fission.utility.Util,
                 edit.portal.common.session.UserSession,
                 engine.ProductStructure,
                 casetracking.CaseRequirement,
                 contract.FilteredRequirement,
                 contract.Requirement,
                 agent.AgentRequirement" %>

<!--
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    long companyStructureId = ProductStructure.findByNames("Agent","*","*","*","*").getProductStructurePK().longValue();

    CodeTableVO[] requirementStatuses = codeTableWrapper.getCodeTableEntries("REQUIREMENTSTATUS", companyStructureId);

    AgentRequirement activeAgentRequirement = (AgentRequirement) request.getAttribute("activeAgentRequirement");
    FilteredRequirement filteredRequirement = (FilteredRequirement) Util.initObject(activeAgentRequirement, "FilteredRequirement", null);
    Requirement activeRequirement = (Requirement) Util.initObject(filteredRequirement, "Requirement", null);

    AgentVO agentVO = (AgentVO) session.getAttribute("agentVO");
    String agentPK = "0";
    if (agentVO != null)
    {
        agentPK = agentVO.getAgentPK() + "";
    }

    long activeAgentRequirementPK = Util.initLong(activeAgentRequirement, "agentRequirementPK", 0L);
    String activeRequirementId = (String) Util.initObject(activeRequirement, "requirementId", "");
    String activeRequirementDesc = (String) Util.initObject(activeRequirement, "requirementDescription", "");
    String activeStatusCT = (String) Util.initObject(activeAgentRequirement, "requirementStatusCT", "");
    EDITDate activeReceivedDate = (EDITDate) Util.initObject(activeAgentRequirement, "receivedDate", null);
    String receivedMonth = activeReceivedDate == null ? "" : activeReceivedDate.getFormattedMonth();
    String receivedDay = activeReceivedDate == null ? "" : activeReceivedDate.getFormattedDay();
    String receivedYear = activeReceivedDate == null ? "" : activeReceivedDate.getFormattedYear();
    EDITDate activeEffectiveDate = (EDITDate) Util.initObject(activeAgentRequirement, "effectiveDate", null);
    String effectiveMonth = activeEffectiveDate == null ? "" : activeEffectiveDate.getFormattedMonth();
    String effectiveDay = activeEffectiveDate == null ? "" : activeEffectiveDate.getFormattedDay();
    String effectiveYear = activeEffectiveDate == null ? "" : activeEffectiveDate.getFormattedYear();
    EDITDate activeFollowupDate = (EDITDate) Util.initObject(activeAgentRequirement, "followupDate", null);
    String followupMonth = activeFollowupDate == null ? "" : activeFollowupDate.getFormattedMonth();
    String followupDay = activeFollowupDate == null ? "" : activeFollowupDate.getFormattedDay();
    String followupYear = activeFollowupDate == null ? "" : activeFollowupDate.getFormattedYear();

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>


<script language="Javascript1.2">

	var f = null;

    var shouldShowLockAlert = true;
    var agentPK = "<%= agentPK %>";
    var responseMessage = "<%= responseMessage %>";

	function init()
    {
		f = document.agentRequirementsForm;

		top.frames["main"].setActiveTab("requirementsTab");

        shouldShowLockAlert = <%= ! userSession.getAgentIsLocked() %>;

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "button") && (shouldShowLockAlert == true) )
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }

        if (agentPK == "0")
        {
            f.addButton.disabled = true;
        }
        else
        {
            f.addButton.disabled = false;
        }

        checkForResponseMessage();

        // Initialize scroll tables
        initScrollTable(document.getElementById("AgentRequirementsTableModelScrollTable"));
	}

    function showLockAlert()
    {
        if (shouldShowLockAlert == true)
        {
            alert("The Agent can not be edited.");

            return false;
        }
    }

    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("AgentDetailTran", "showRequirementDetail", "_self");
    }

	function addNewRequirement()
    {
        var width = 0.60 * screen.width;
        var height = 0.20 * screen.height;

        openDialog("addManualRequirement", "top=0,left=0,resizable=no", width,  height);

        sendTransactionAction("AgentDetailTran", "addRequirement", "addManualRequirement");
	 }

	function cancelRequirement()
    {
        sendTransactionAction("AgentDetailTran", "cancelRequirement", "contentIFrame");
	}

	function saveRequirement()
    {
        if (f.agentRequirementPK.value == 0)
        {
            alert("Please Select the Requirement to Modify");
        }
        else
        {
            try
            {
                f.receivedDate.value = formatDate(f.receivedMonth.value, f.receivedDay.value, f.receivedYear.value, false);
                f.effectiveDate.value = formatDate(f.effectiveMonth.value, f.effectiveDay.value, f.effectiveYear.value, false);
                f.followupDate.value = formatDate(f.followupMonth.value, f.followupDay.value, f.followupYear.value, false);

                sendTransactionAction("AgentDetailTran", "saveRequirement", "contentIFrame");
            }
            catch (e)
            {
                alert(e);
            }
        }
	}

	function deleteRequirement()
    {
        if (f.agentRequirementPK.value == 0)
        {
            alert('Please Select the Requirement to Delete');
        }
        else
        {
		    sendTransactionAction("AgentDetailTran", "deleteSelectedRequirement", "contentIFrame");
        }
	}

</script>

<head>
<title>Agent Requirements</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name= "agentRequirementsForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="agentInfoHeader.jsp" flush="true"/>

<span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:40%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="3">
    <tr>
      <td align="right" nowrap>Requirement Id:&nbsp;</td>
	  <td align="left" nowrap colspan="3">
        <input disabled type="text" name="requirementId" maxlength="5" size="5" value="<%= activeRequirementId %>">
	  </td>
    </tr>
    <tr>
      <td align="right" nowrap>Requirement Description:&nbsp;</td>
      <td align="left" nowrap colspan="3">
        <input disabled type="text" name="requirementDescription" maxlength="50" size="50" value="<%= activeRequirementDesc %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Status:&nbsp;</td>
      <td align="left" nowrap>
		<select name="requirementStatusCT" tabindex="1" value="<%= activeStatusCT %>">
		  <option> Please Select </option>
			<%
               if (requirementStatuses != null)
               {
                   for(int s = 0; s < requirementStatuses.length; s++)
                   {
                       String codeTablePK = requirementStatuses[s].getCodeTablePK() + "";
                       String codeDesc    = requirementStatuses[s].getCodeDesc();
                       String code        = requirementStatuses[s].getCode();

                       if (activeStatusCT.equalsIgnoreCase(code))
                       {
                           out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                       }
                       else
                       {
                           out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                       }
                   }
               }
			%>
		</select>
      </td>
      <td align="right" nowrap>Received Date:&nbsp;</td>
      <td align="left" nowrap>
        <input name="receivedMonth" type="text" size="2" maxlength="2" value="<%= receivedMonth %>">
        /
        <input name="receivedDay" type="text" size="2" maxlength="2" value="<%= receivedDay %>">
        /
        <input name="receivedYear" type="text" size="4" maxlength="4" value="<%= receivedYear %>">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>Effective Date:&nbsp;</td>
      <td align="left" nowrap>
        <input name="effectiveMonth" type="text" size="2" maxlength="2" value="<%= effectiveMonth %>">
        /
        <input name="effectiveDay" type="text" size="2" maxlength="2" value="<%= effectiveDay %>">
        /
        <input name="effectiveYear" type="text" size="4" maxlength="4" value="<%= effectiveYear %>">
      </td>
      <td align="right" nowrap>Followup Date:&nbsp;</td>
      <td align="left" norwap>
        <input name="followupMonth" type="text" size="2" maxlength="2" value="<%= followupMonth %>">
        /
        <input name="followupDay" type="text" size="2" maxlength="2" value="<%= followupDay %>">
        /
        <input name="followupYear" type="text" size="4" maxlength="4" value="<%= followupYear %>">
	  </td>
    </tr>
  </table>
</span>

<table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
  <tr>
	<td nowrap align="left">
      <input type="button" name="addButton" value="   Add   " style="background-color:#DEDEDE" onClick="addNewRequirement()">
	  <input type="button" name="saveButton" value="   Save  " style="background-color:#DEDEDE" onClick="saveRequirement()">
	  <input type="button" name="cancelButton" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelRequirement()">
	  <input type="button" name="deleteButton" value="  Delete " style="background-color:#DEDEDE" onClick="deleteRequirement()">
	</td>
  </tr>
</table>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="AgentRequirementsTableModel"/>
    <jsp:param name="tableHeight" value="50"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="agentRequirementPK" value="<%= activeAgentRequirementPK %>">
 <input type="hidden" name="selectedRequirementsPK" value="">
 <input type="hidden" name="selectedRequirementId" value="">
 <input type="hidden" name="receivedDate" value="">
 <input type="hidden" name="effectiveDate" value="">
 <input type="hidden" name="followupDate" value="">

</form>
</body>
</html>






