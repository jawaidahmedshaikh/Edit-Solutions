<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO,
                 edit.common.vo.UIAgentHierarchyVO,
                 edit.common.vo.AgentHierarchyVO,
                 edit.common.vo.AgentSnapshotVO,
                 edit.portal.common.session.UserSession,
                 fission.utility.Util,
                 java.math.BigDecimal,
                 edit.common.EDITBigDecimal,
                 edit.common.EDITDate,
                 edit.services.db.hibernate.SessionHelper" %>

<%
    UIAgentHierarchyVO[] uiAgentHierarchyVOs = (UIAgentHierarchyVO[]) session.getAttribute("uiAgentHierarchyVOs");
    String selectedAgentHierarchyPK = (String) request.getAttribute("selectedAgentHierarchyPK");
    String selectedAgentSnapshotPK = (String) request.getAttribute("selectedAgentSnapshotPK");
    String selectedCommProfileFK = (String) request.getAttribute("selectedCommProfileFK");

    String servicingAgentIndStatus = "unchecked";
    String advancePercent = "";
    String recoveryPercent = "";

    boolean snapshotFound = false;
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
                    servicingAgentIndStatus = Util.initString(agentSnapshotVOs[s], "servicingAgentIndicator", "N");
                    if (servicingAgentIndStatus.equalsIgnoreCase("Y"))
                    {
                        servicingAgentIndStatus = "checked";
                    }
                    else
                    {
                        servicingAgentIndStatus = "unchecked";
                    }

                    EDITBigDecimal advPercent = new EDITBigDecimal(agentSnapshotVOs[s].getAdvancePercent());
                    if (advPercent.isGT("0"))
                    {
                        advancePercent = agentSnapshotVOs[s].getAdvancePercent().toString();
                    }
                    else
                    {
                        advancePercent = Util.formatDecimal("#########0.0", agentSnapshotVOs[s].getAdvancePercent());
                    }

                    EDITBigDecimal recovPercent = new EDITBigDecimal(agentSnapshotVOs[s].getRecoveryPercent());
                    if (recovPercent.isGT("0"))
                    {
                        recoveryPercent = agentSnapshotVOs[s].getRecoveryPercent().toString();
                    }
                    else
                    {
                        recoveryPercent = Util.formatDecimal("#########0.0", agentSnapshotVOs[s].getRecoveryPercent());
                    }

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

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;
    var shouldShowLockAlert = true;
    var editableContractStatus = true;

	function init()
    {
        f = document.commissionOverridesForm;

        shouldShowLockAlert = <%= ! userSession.getSegmentIsLocked() %>;

        // check for terminated status to disallow contract updates
        editableContractStatus = <%= userSession.getEditableSegmentStatus()%>;
        
        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if (f.elements[i].name != "cancel")
            {
                if ((elementType == "checkbox" || elementType == "button") &&
                    (shouldShowLockAlert == true || editableContractStatus == false))
                {
                    f.elements[i].onclick = showLockAlert;
                    f.elements[i].onkeydown = showLockAlert;
                }
            }
        }
	}

    function showLockAlert()
    {
    	if (shouldShowLockAlert == true)
        {
            alert("The Contract Cannot Be Edited.");

            return false;
            
        } else if (editableContractStatus == false) {
        	
        	alert("This Contract Cannot Be Edited Due to Terminated Status.");

            return false;
        }
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

        sendTransactionAction("QuoteDetailTran", "saveCommissionOverrides", "contentIFrame");
        closeWindow();
	}

    /**
     * Sets ServicingAgentIndicator to "N"
     */
    function deleteCommissionOverrides()
    {
        sendTransactionAction("QuoteDetailTran", "deleteCommissionOverrides", "contentIFrame");

        closeWindow();
    }

	function cancelCommissionOverrides()
    {
		closeWindow();
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
  <table id="table1" width="51%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td align="left" nowrap colspan="2">Servicing Agent:&nbsp;
	    <input type="checkbox" name="servicingAgentInd" <%= servicingAgentIndStatus %> >
      </td>
    </tr>
    <tr>
        <td align="right">
            Advance Percent:&nbsp;
        </td>
        <td align="left">
            <input type="text" name="advancePercent" value="<%= advancePercent %>" length="20" maxlength="15">
        </td>
    </tr>
    <tr>
        <td align="right">
            Recovery Percent:&nbsp;
        </td>
        <td align="left">
            <input type="text" name="recoveryPercent" value="<%= recoveryPercent %>" length="20" maxlength="15">
        </td>
    </tr>
    <tr>
      <td colspan="2" nowrap>&nbsp;</td>
    </tr>

    <tr>
      <td colspan="2" align="center" valign="bottom" nowrap>
        <input type="button" name="enter" value="Enter" onClick="saveCommissionOverrides()">
        <input type="button" name="cancel" value="Cancel" onClick="cancelCommissionOverrides()">
        <input type="button" name="delete" value="Delete" onClick="deleteCommissionOverrides()">
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
