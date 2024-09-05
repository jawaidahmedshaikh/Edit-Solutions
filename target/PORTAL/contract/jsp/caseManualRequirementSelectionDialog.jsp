<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 contract.Requirement" %>

<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    String requirementPK = (String) request.getAttribute("selectedRequirementPK");
    if (requirementPK == null)
    {
        requirementPK = "";
    }

    Requirement[] requirements = (Requirement[]) request.getAttribute("manualRequirements");
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js" type="text/javascript"></script>

<script language="Javascript1.2">

	var f = null;
    var responseMessage = "<%= responseMessage %>";

	function init() {

		f = document.manualRequirementSelectionForm;

        checkForResponseMessage();
	}

	function cancelDialog() {

		window.close();
	}

    function showDescription() {

        sendTransactionAction("CaseDetailTran", "showManualRequirementDescription", "_self");
    }

	function saveManualRequirement() {

        f.selectedRequirementPK.value = "<%= requirementPK %>"
        if (f.selectedRequirementPK.value == "") {

            alert("Please Select Requirement to Add to Contract");
        }
        else {

            sendTransactionAction("CaseDetailTran", "saveManualRequirement", "main");
            window.close();
        }
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value=transaction;
		f.action.value=action;
		f.target = target;

		f.submit();
	}

</script>

<title>Select Manual Requirement</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="dialog" bgColor="#DDDDDD" onLoad="init()">
<form name="manualRequirementSelectionForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="51%" border="0" cellspacing="0" cellpadding="0" bgcolog="#DDDDDD">
    <tr>
      <td align="right" nowrap>Requirement Id:&nbsp;</td>
      <td align="left" nowrap>
		<select name="requirementPK" tabindex="1" onChange="showDescription()" value="<%= requirementPK %>">
		  <option> Please Select </option>
			<%
                if (requirements != null) {

                    for(int r = 0; r < requirements.length; r++) {

                        String sRequirementPK = requirements[r].getRequirementPK().toString();
                        String sRequirementId = requirements[r].getRequirementId();

                        if (requirementPK.equalsIgnoreCase(sRequirementPK)) {

                            out.println("<option selected name=\"id\" value=\"" + sRequirementPK+ "\">" + sRequirementId + "</option>");
                        }
                        else  {

                            out.println("<option name=\"id\" value=\"" + sRequirementPK + "\">" + sRequirementId + "</option>");
                        }
                    }
                }
			%>
		</select>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">&nbsp;</td>
    </tr>
    <tr>
      <td align="right" nowrap>Requirement Description:&nbsp;</td>
      <td disabled align="left" nowrap>
        <%

          String sDescription = "";
          if (requirements != null) {

              for(int r = 0; r < requirements.length; r++) {

                  String sRequirementPK = requirements[r].getRequirementPK().toString();
                  if (sDescription == null) {

                      sDescription = "";
                  }

                  if (requirementPK.equalsIgnoreCase(sRequirementPK)) {

                      sDescription = requirements[r].getRequirementDescription();
                      break;
                  } // end if
              } // end for
          } // end if
        %>
        <input type="text" name="description" maxlength="50" size="50" value="<%= sDescription %>">
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="2" align="right" valign="bottom" nowrap>
        <input type="button" name="enter" value="Save" onClick="saveManualRequirement()">
        <input type="button" name="cancel" value="Cancel" onClick="cancelDialog()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="selectedRequirementPK" value="">

</form>
</body>
</html>
