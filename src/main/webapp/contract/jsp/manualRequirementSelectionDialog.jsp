<!-- quoteCommitContractNumberDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.RequirementVO,
                 contract.*" %>

<%
    String requirementMessage = (String) request.getAttribute("requirementMessage");
    if (requirementMessage == null)
    {
        requirementMessage = "";
    }

    String requirementPK = (String) request.getAttribute("selectedRequirementPK");
    if (requirementPK == null)
    {
        requirementPK = "";
    }

    RequirementVO[] requirementVOs = (RequirementVO[]) session.getAttribute("manualRequirementVOs");

    //  Determines if the description will be editable or not
    boolean disableDescription = true;
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script language="Javascript1.2">

	var f = null;
    var requirementMessage = "<%= requirementMessage %>";

	function init() {

		f = document.manualRequirementSelectionForm;

        if (requirementMessage != "")
        {
            alert(requirementMessage);
        }
	}

	function cancelDialog() {

		window.close();
	}

    function showDescription() {

        sendTransactionAction("ContractDetailTran", "showManualRequirementDescription", "_self");
    }

	function saveManualRequirement() {

        f.selectedRequirementPK.value = "<%= requirementPK %>"
        if (f.selectedRequirementPK.value == "") {

            alert("Please Select Requirement to Add to Contract");
        }
        else {

            sendTransactionAction("ContractDetailTran", "saveManualRequirement", "contentIFrame");
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
                if (requirementVOs != null) {

                    for(int r = 0; r < requirementVOs.length; r++) {

                        String sRequirementPK = requirementVOs[r].getRequirementPK() + "";
                        String sRequirementId = requirementVOs[r].getRequirementId();

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
      <td align="left" nowrap>
        <%
          String sDescription = "";

          if (requirementVOs != null)
          {
              for(int r = 0; r < requirementVOs.length; r++)
              {
                  String sRequirementPK = requirementVOs[r].getRequirementPK() + "";

                  if (sDescription == null)
                  {
                      sDescription = "";
                  }

                  if (requirementPK.equalsIgnoreCase(sRequirementPK))
                  {
                      sDescription = requirementVOs[r].getRequirementDescription();

                      if (requirementVOs[r].getRequirementId().equals(Requirement.REQUIREMENT_ID_TEXT))
                      {
                          disableDescription = false;
                      }

                      break;
                  } // end if
              } // end for
          } // end if
        %>
        <input <%= (disableDescription)?"DISABLED":"" %> type="text" name="description" maxlength="50" size="50" value="<%= sDescription %>">
      </td>
    </tr>
    <tr>
      <td align="right" valign="top" nowrap>Requirement Information:&nbsp;</td>
      <td align="left" valign="top">
        <textarea name="requirementInfo" rows="4" cols="70" onKeyUp='checkTextAreaLimit()' maxLength='250'></textarea>
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
