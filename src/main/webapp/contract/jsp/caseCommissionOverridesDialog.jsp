<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO,
                 edit.common.vo.UIAgentHierarchyVO,
                 edit.common.vo.AgentHierarchyVO,
                 edit.common.vo.AgentSnapshotVO,
                 edit.portal.common.session.UserSession,
                 java.math.BigDecimal,
                 fission.utility.Util,
                 edit.common.EDITBigDecimal,
                 edit.common.EDITDate,
                 edit.services.db.hibernate.SessionHelper,
                 agent.CommissionProfile" %>

<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    CommissionProfile[] commissionProfiles = (CommissionProfile[]) request.getAttribute("commissionProfiles");

    UIAgentHierarchyVO[] uiAgentHierarchyVOs = (UIAgentHierarchyVO[]) session.getAttribute("uiAgentHierarchyVOs");
    String selectedAgentHierarchyPK = (String) request.getAttribute("selectedAgentHierarchyPK");
    String selectedAgentSnapshotPK = (String) request.getAttribute("selectedAgentSnapshotPK");
    String selectedCommProfileFK = (String) request.getAttribute("selectedCommProfileFK");
    String selectedCommProfileOvrdFK = Util.initString((String) request.getAttribute("selectedCommProfileOvrdFK"), "");
    String servicingAgentIndStatus = "unchecked";
    long commissionProfileFK = 0;
    boolean snapshotFound = false;

    EDITBigDecimal advancePercent = new EDITBigDecimal("0");
    EDITBigDecimal recoveryPercent = new EDITBigDecimal("0");

    if (uiAgentHierarchyVOs != null)
    {
        for (int h = 0; h < uiAgentHierarchyVOs.length; h++)
        {
            AgentHierarchyVO agentHierarchyVO = uiAgentHierarchyVOs[h].getAgentHierarchyVO();
            AgentSnapshotVO[] agentSnapshotVOs = agentHierarchyVO.getAgentSnapshotVO();
            for (int s = 0; s < agentSnapshotVOs.length; s++)
            {
                String agentSnapshotPK = agentSnapshotVOs[s].getAgentSnapshotPK() + "";

                if (agentSnapshotPK.equals(selectedAgentSnapshotPK))
                {
                    if (!selectedCommProfileOvrdFK.equals(""))
                    {
                        commissionProfileFK = Long.parseLong(selectedCommProfileOvrdFK);
                    }
                    else
                    {
                        commissionProfileFK = agentSnapshotVOs[s].getCommissionProfileFK();
                    }
                    servicingAgentIndStatus = Util.initString(agentSnapshotVOs[s], "servicingAgentIndicator", "N");
                    if (servicingAgentIndStatus.equalsIgnoreCase("Y"))
                    {
                        servicingAgentIndStatus = "checked";
                    }
                    else
                    {
                        servicingAgentIndStatus = "unchecked";
                    }

                    advancePercent = new EDITBigDecimal(Util.initBigDecimal(agentSnapshotVOs[s], "AdvancePercent", new BigDecimal("0.00")));

                    recoveryPercent = new EDITBigDecimal(Util.initBigDecimal(agentSnapshotVOs[s], "RecoveryPercent", new BigDecimal("0.00")));

                    snapshotFound = true;
                    break;
                }
            }

            if (snapshotFound)
            {
                break;
            }
        }
    }
%>

<!-- caseCommissionOverridesDialog.jsp //-->

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<script language="Javascript1.2">

	var f = null;
    var shouldShowLockAlert = false;
    var responseMessage = "<%= responseMessage %>";
    var advancePercent = "<%= advancePercent %>";
    var recoveryPercent = "<%= recoveryPercent %>";
	function init()
    {
        f = document.commissionOverridesForm;

        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if (f.elements[i].name != "cancel")
            {
                if ((elementType == "checkbox" || elementType == "button") &&
                    (shouldShowLockAlert == true))
                {
                    f.elements[i].onclick = showLockAlert;
                    f.elements[i].onkeydown = showLockAlert;
                }
            }
        }

        checkForResponseMessage();

        formatCurrency();
	}

    function showLockAlert()
    {
<%--        if (shouldShowLockAlert == true)--%>
<%--        {--%>
<%--            alert("The Contract can not be edited.");--%>
<%----%>
<%--            return false;--%>
<%--        }--%>
    }


	function saveCommissionOverrides()
    {
        if (f.servicingAgentInd.checked == true)
        {
            f.servicingAgentIndicator.value = "Y";
        }
        else
        {
            f.servicingAgentIndicator.value = "N";
        }
        
        if(f.advancePercent.value > 1 || f.recoveryPercent.value > 1)
        {
            alert("Advance/Recovery amount can not be greater then 100%");
             return false;
        }
        else if (isPercentConfirmationNeeded('advancePercent', .5) && !isPercentValueConfirmed('advancePercent'))
        {
    		document.getElementById('advancePercentConfirmationCell').style.visibility = "visible";
            
			alert("When the Advance Percent is not equal to .5 the value must be confirmed. Please enter the correct matching value in the confirmation field.");
			return false;
        }
        else if (isPercentConfirmationNeeded('recoveryPercent', 1) && !isPercentValueConfirmed('recoveryPercent'))
        {
    		document.getElementById('recoveryPercentConfirmationCell').style.visibility = "visible";

    		alert("When the Recovery Percent is not equal to 1 the value must be confirmed. Please enter the correct matching value in the confirmation field.");
			return false;
        }

        sendTransactionAction("CaseDetailTran", "saveCommissionOverrides", "commissionOverrides");
<%--        closeWindow();--%>
	}

	function isPercentValueConfirmed(fieldName) 
	{
		var confirmationValue = document.getElementById(fieldName + 'Confirmation').value;
		var fieldValue = document.getElementById(fieldName).value;

		if (Number(confirmationValue) != Number(fieldValue))
		{
			return false;
		}
		return true;
	}

    /**
     * Sets ServicingAgentIndicator to "N"
     */
    function deleteCommissionOverrides()
    {
        sendTransactionAction("CaseDetailTran", "deleteCommissionOverrides", "_main");

        closeWindow();
    }

	function cancelCommissionOverrides()
    {
		closeWindow();
	}

	function closeCommissionOverrides()
    {
        sendTransactionAction("CaseDetailTran", "showCaseAgents", "_main");
		closeWindow();
	}

    function displayPercentConfirmationIfNeeded(fieldName, normalPercent)
    {
    	if (isPercentConfirmationNeeded(fieldName, normalPercent))
       	{
    		document.getElementById(fieldName + 'ConfirmationCell').style.visibility = "visible";
    		document.getElementById(fieldName + 'Confirmation').value = "";
    		return true;
        }

    	document.getElementById(fieldName + 'ConfirmationCell').style.visibility = "hidden";
    	document.getElementById(fieldName + 'Confirmation').value = "";
    	return false;
    }

    function isPercentConfirmationNeeded(fieldName, normalPercent)
    {
    	var currentPercent = document.getElementById(fieldName).value;
    	if (normalPercent != currentPercent)
       	{
    		return true;
        }
    	return false;		
    }
	


</script>

<head>
<title>Commission Overrides</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>
</head>

<body class="dialog" bgcolor="#DDDDDD" onLoad="init()">
<form name="commissionOverridesForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="80%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td align="left" nowrap colspan="1">Servicing Agent:&nbsp;
	    <input type="checkbox" name="servicingAgentInd" <%= servicingAgentIndStatus %> >
      </td>
    </tr>
    <tr>
      <td align="left" nowrap colspan="1">Level/Option:&nbsp;
        <!-- Summary Table for CommissionProfiles  -->
        <select name="selectedCommProfileOvrdFK">
          <option name="id" value="">Please Select</option>
          <%
            if (commissionProfiles != null)
            {
                // We need to sort the select list by a means that is not natural with the VOs.
                List listItems = new ArrayList();

                for (int i = 0; i < commissionProfiles.length; i++)
                {
                    CommissionProfile currentCommissionProfile = commissionProfiles[i];

                    long currentCommissionProfilePK = currentCommissionProfile.getCommissionProfilePK().longValue();
                    String commissionLevelCT = currentCommissionProfile.getCommissionLevelCT();
                    String commissionOptionCT = currentCommissionProfile.getCommissionOptionCT();

                    String listItem = commissionLevelCT + "," + commissionOptionCT + "," + currentCommissionProfilePK;

                    listItems.add(listItem);
                }

                Collections.sort(listItems);

                int listItemsCount = listItems.size();

                for (int i = 0; i < listItemsCount; i++)
                {
                    String listItemLine = (String) listItems.get(i);

                    String[] listTokens = Util.fastTokenizer(listItemLine, ",");

                    String commissionLevel = listTokens[0];
                    String commissionOption = listTokens[1];
                    long currentCommissionProfilePK = Long.parseLong(listTokens[2]);

                    if (commissionProfileFK == currentCommissionProfilePK)
                    {
                        out.println("<option selected name=\"id\" value=\"" + currentCommissionProfilePK + "\">" + commissionLevel + " - " + commissionOption +  "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + currentCommissionProfilePK + "\">" + commissionLevel + " - " + commissionOption +  "</option>");
                    }
                }
            }
          %>
        </select>
      </td>
    </tr>
   <tr>
     <td align="left" colspan="1">Advance Percent:&nbsp;
       <input type="text" id="advancePercent" name="advancePercent" maxlength="30" size="10" value="<%= advancePercent %>" onblur="displayPercentConfirmationIfNeeded('advancePercent', .5)">
     </td>
     <td style="visibility:hidden;" id="advancePercentConfirmationCell" align="left" colspan="1">Confirm Advance Percent:&nbsp;
       <input type="text" onpaste="return false;" name="advancePercentConfirmation" id="advancePercentConfirmation" maxlength="30" size="10">
     </td>
   </tr>
   <tr>
     <td align="left" colspan="1">Recovery Percent:&nbsp;
       <input type="text" id="recoveryPercent" name="recoveryPercent" maxlength="30" size="10" value="<%= recoveryPercent %>" onblur="displayPercentConfirmationIfNeeded('recoveryPercent', 1)">
     </td>
     <td style="visibility:hidden;" id="recoveryPercentConfirmationCell" align="left" colspan="1">Confirm Recovery Percent:&nbsp;
       <input type="text" onpaste="return false;" name="recoveryPercentConfirmation" id="recoveryPercentConfirmation" maxlength="30" size="10">
     </td>
   </tr>
    <tr class="buttonPadding">    
      <td colspan="8" align="left" valign="bottom" nowrap>
        <input type="button" name="enter" value="Enter" onClick="saveCommissionOverrides()">
        <input type="button" name="cancel" value="Cancel" onClick="cancelCommissionOverrides()">
        <input type="button" name="delete" value="Delete" onClick="deleteCommissionOverrides()">
         <input type="button" name="close" value="Close" onClick="closeCommissionOverrides()">
      </td>
    </tr>
  </table>
  
     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="selectedAgentHierarchyPK" value="<%= selectedAgentHierarchyPK %>">
  <input type="hidden" name="selectedAgentSnapshotPK" value="<%= selectedAgentSnapshotPK %>">
  <input type="hidden" name="selectedCommProfileFK" value="<%= selectedCommProfileFK %>">
  <input type="hidden" name="servicingAgentIndicator" value="">

</form>
</body>
</html>
