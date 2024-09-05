<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.RequirementVO,
                 fission.utility.Util" %>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    PageBean formBean = quoteMainSessionBean.getPageBean("formBean");
    String companyStructureId = Util.initString(formBean.getValue("companyStructureId"), "0");

    CodeTableVO[] allowableStatuses = codeTableWrapper.getCodeTableEntries("ALLOWABLESTATUS", Long.parseLong(companyStructureId));
    CodeTableVO[] finalStatuses = codeTableWrapper.getCodeTableEntries("FINALSTATUS", Long.parseLong(companyStructureId));

    String requirementId  = "";
    String description = "";
    String allowableStatus = "";
    String followupDays = "";
    String manualIndStatus = "unchecked";
    String agentViewIndStatus = "unchecked";
    String updatePolicyDeliveryDateIndStatus = "unchecked";
    String autoReceiptIndStatus = "unchecked";
    String finalStatus = "";
    String requirementPK = "0";
    String selectedRequirementPK = (String) request.getAttribute("selectedRequirementPK");
    if (selectedRequirementPK == null) {

        selectedRequirementPK = "";
    }

    String rowToMatchBase = selectedRequirementPK;

    RequirementVO[] requirementVOs = (RequirementVO[]) request.getAttribute("requirementVOs");
    if (requirementVOs != null) {

        for (int a = 0; a < requirementVOs.length; a++) {

            if ((requirementVOs[a].getRequirementPK() + "").equals(selectedRequirementPK)) {

                requirementPK = requirementVOs[a].getRequirementPK() + "";
                requirementId = requirementVOs[a].getRequirementId();
                description = Util.initString(requirementVOs[a].getRequirementDescription(), "");
                allowableStatus = Util.initString(requirementVOs[a].getAllowableStatusCT(), "");
                followupDays = requirementVOs[a].getFollowupDays() + "";
                if (requirementVOs[a].getManualInd().equalsIgnoreCase("Y")) {

                    manualIndStatus = "checked";
                }
                if (requirementVOs[a].getAgentViewInd().equalsIgnoreCase("Y")) {

                    agentViewIndStatus = "checked";
                }
                if ("Y".equalsIgnoreCase(requirementVOs[a].getUpdatePolicyDeliveryDateInd())) {

                    updatePolicyDeliveryDateIndStatus = "checked";
                }

                if (requirementVOs[a].getAutoReceipt().equalsIgnoreCase("Y"))
                {
                    autoReceiptIndStatus = "checked";
                }

                finalStatus = Util.initString(requirementVOs[a].getFinalStatusCT(), "");

                break;
            }
        }
    }
%>

<%!
	private TreeMap sortRequirements(RequirementVO[] requirementVOs) {

		TreeMap sortedRequirements = new TreeMap();

        for (int r = 0; r < requirementVOs.length; r++) {

            String requirementId = requirementVOs[r].getRequirementId();

            sortedRequirements.put(requirementId, requirementVOs[r]);
        }

		return sortedRequirements;
	}
%>

<html>


<head>

<title>Requirement Table</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var dialog = null;

	var f = null;

	function init() {

		f = document.requirementForm;
	}

	function selectRow() {

		var tdElement  = window.event.srcElement;
		var trElement  = tdElement.parentElement;

		var selectedRequirementPK = trElement.id;
		f.selectedRequirementPK.value = selectedRequirementPK;

		sendTransactionAction("QuoteDetailTran", "showRequirementDetailSummary", "_self");
	}

	function addRequirements() {

 		sendTransactionAction("QuoteDetailTran", "clearRequirementFormForAddOrCancel", "_self");
     }

	function cancelRequirements() {

 		sendTransactionAction("QuoteDetailTran", "clearRequirementFormForAddOrCancel", "_self");
	}

	function updateRequirements() {

		if (f.manualIndStatus.checked == true) {

			f.manualIndStatus.value = "checked";
		}
        else {

            f.manualIndStatus.value = "unchecked";
        }

		if (f.agentViewIndStatus.checked == true) {

			f.agentViewIndStatus.value = "checked";
		}
        else {

            f.agentViewIndStatus.value = "unchecked";
        }

        if (f.autoReceiptIndStatus.checked == true)
        {
            f.autoReceiptIndStatus.value = "checked";
        }
        else
        {
            f.autoReceiptIndStatus.value = "unchecked";
        }

        if (f.updatePolicyDeliveryDateIndStatus.checked == true) {

			f.updatePolicyDeliveryDateIndStatus.value = "checked";
		}
        else {

            f.updatePolicyDeliveryDateIndStatus.value = "unchecked";
        }

 		sendTransactionAction("QuoteDetailTran", "updateRequirement", "_self");
	}

	function deleteRequirements() {

  		sendTransactionAction("QuoteDetailTran", "deleteRequirement", "_self");
    }

    function showRequirementRelation()
    {
        sendTransactionAction("QuoteDetailTran", "showRequirementRelationPage", "_self");
    }

</script>
</head>

<body class="mainTheme" onLoad="init()">
<form name="requirementForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
  <table class="formData" width="100%" border="0" cellspacing="6" cellpadding="0">
    <tr>
      <td align="left" nowrap>Requirement Id:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="requirementId" size="5" maxlength="5" value="<%= requirementId %>">
      </td>
      <td align="left" nowrap>Description:&nbsp;
        <input type="text" name="description" size="50" maxlength="50" value="<%= description %>">
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>AllowableStatus:&nbsp;</td>
      <td align="left" nowrap>
        <select name="allowableStatus">
          <option selected value="Please Select"> Please Select </option>
          <%
            for(int i = 0; i < allowableStatuses.length; i++) {

                String codeTablePK = allowableStatuses[i].getCodeTablePK() + "";
                String codeDesc    = allowableStatuses[i].getCodeDesc();
                String code        = allowableStatuses[i].getCode();

               if (allowableStatus.equalsIgnoreCase(code)) {

                   out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
               }
               else  {

                   out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
               }
            }
          %>
        </select>
      </td>
      <td align="left" nowrap>Followup Days:&nbsp;
        <input type="text" name="followupDays" size="3" maxlength="3" value="<%= followupDays %>">
        &nbsp;&nbsp;
        Final Status:&nbsp;
        <select name="finalStatus">
          <option selected value="Please Select"> Please Select </option>
          <%
            for(int i = 0; i < finalStatuses.length; i++) {

                String codeTablePK = finalStatuses[i].getCodeTablePK() + "";
                String codeDesc    = finalStatuses[i].getCodeDesc();
                String code        = finalStatuses[i].getCode();

               if (finalStatus.equalsIgnoreCase(code)) {

                   out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
               }
               else  {

                   out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
               }
            }
          %>
        </select>
      </td>
    </tr>
    <tr>
      <td align="center" nowrap colspan="3">Manual
        <input type="checkbox" name="manualIndStatus" <%= manualIndStatus %> >
        &nbsp;&nbsp;
        Agent View
        <input type="checkbox" name="agentViewIndStatus" <%= agentViewIndStatus %> >
        &nbsp;&nbsp;
        Update Policy Delivery Date
        <input type="checkbox" name="updatePolicyDeliveryDateIndStatus" <%= updatePolicyDeliveryDateIndStatus %> >
        &nbsp;&nbsp;
        Auto Receipt
        <input type="checkbox" name="autoReceiptIndStatus" <%= autoReceiptIndStatus %> >
      </td>
    </tr>
  </table>
<%-- ****************************** END Form Data ****************************** --%>

  <br>
  <br>
  <br>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td align="left">
        <input type="button" name="add" value= " Add  " onClick="addRequirements()">
	    <input type="button" name="save" value=" Save " onClick="updateRequirements()">
	    <input type="button" name="cancel" value="Cancel" onClick="cancelRequirements()">
	    <input type="button" name="delete" value="Delete" onClick="deleteRequirements()">
	  </td>
      <td width="33%" align="center">
        <span class="tableHeading">Requirements Summary</span>
      </td>
      <td align="right" width="33%">
        <input type="button" value="Requirement Relation" onClick="showRequirementRelation()">
      </td>
	</tr>
  </table>

  <%-- Header --%>
  <table class="summary" id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <td align="left" width="50%">Requirement Id</td>
      <td align="left" width="50%">Requirement Description</td>
    </tr>
  </table>

  <%-- Summary --%>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:60%; top:0; left:0;">
    <table class="summary" id="requirementsSummary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <%
        String rowToMatch = "";
        String trClass = "";

        if (requirementVOs != null)
        {
            Map sortedRequirements = sortRequirements(requirementVOs);

            Iterator it2 = sortedRequirements.values().iterator();

            while (it2.hasNext())
            {
                RequirementVO requirementVO = (RequirementVO) it2.next();

                String sRequirementPK = requirementVO.getRequirementPK() + "";
                String sRequirementId = requirementVO.getRequirementId();
                String sDescription   = Util.initString(requirementVO.getRequirementDescription(), "");

                rowToMatch = sRequirementPK;

                boolean isSelected = false;

                if (rowToMatch.equals(rowToMatchBase))
                {
                    trClass  = "highlighted";
                    isSelected = true;
                }
                else
                {
                    trClass  = "default";
                }
    %>
    <tr class="<%= trClass %>" isSelected="<%= isSelected %>" id="<%= sRequirementPK %>"
        onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow()">
      <td align="left" nowrap width="50%">
        <%= sRequirementId %>
      </td>
      <td align="left" nowrap width="50%">
        <%= sDescription %>
      </td>
    </tr>
    <%
            }// end while
        }// end if
    %>
    <tr class="filler">
        <td colspan="3">
            &nbsp;
        </td>
    </tr>
    </table>
  </span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="requirementPK" value="<%= requirementPK %>">
 <input type="hidden" name="selectedRequirementPK" value="<%= selectedRequirementPK %>">

</form>
</body>
</html>
