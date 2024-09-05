<!-- agentVestingDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.AgentVO,
                 edit.common.vo.VestingVO,
                 edit.portal.common.session.UserSession" %>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] vestingBasisCT = codeTableWrapper.getCodeTableEntries("TERMVESTINGBASIS");
    CodeTableVO[] vestingStatusCT = codeTableWrapper.getCodeTableEntries("TERMVESTINGSTATUS");

    String vestingBasis = "";
    String vestingStatus = "";
    String vestingPercent = "";
    String vestingPK = "";
    String vestingDuration = "";

    AgentVO agentVO = (AgentVO) session.getAttribute("agentVO");
    if (agentVO != null) {

        VestingVO[] vestingVO = agentVO.getVestingVO();
        if (vestingVO != null && vestingVO.length > 0) {

            vestingBasis = vestingVO[0].getTermVestingBasisCT();
            vestingStatus = vestingVO[0].getTermVestingStatusCT();
            vestingPercent = vestingVO[0].getTermVestingPercent() + "";
            vestingPK = vestingVO[0].getVestingPK() + "";
            vestingDuration = vestingVO[0].getVestingDuration() + "";
        }
    }

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>
<head>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.agentVestingForm;
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
		f.action.value = action;

		f.target = target;

		f.submit();
	}

	function saveVesting() {

        if (f.vestingBasis.selectedIndex == 0) {

            alert("Vesting Basis not Selected");
        }
        else if (f.vestingStatus.selectedIndex == 0) {

            alert("Vesting Status not Selected");
        }
        else if (f.vestingPercent.value == "") {

            alert("Vesting Percent not Entered");
        }
        else {

            sendTransactionAction("AgentDetailTran", "saveVesting", "contentIFrame");
            window.close();
        }
	}

	function cancelVesting() {

		window.close();
	}

</script>

<title>Agent Vesting</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="dialog" onLoad="init()" bgcolor="#DDDDDD">
<form name="agentVestingForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="left">Basis:&nbsp;
        <select name="vestingBasis">
          <option>Please Select</option>
          <%
              for(int i = 0; i < vestingBasisCT.length; i++) {

                  String codeTablePK = vestingBasisCT[i].getCodeTablePK() + "";
                  String codeDesc    = vestingBasisCT[i].getCodeDesc();
                  String code        = vestingBasisCT[i].getCode();

                 if (vestingBasis.equalsIgnoreCase(code)) {

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
      <td nowrap align="left">Status:&nbsp;
        <select name="vestingStatus">
          <option>Please Select</option>
          <%
              for(int i = 0; i < vestingStatusCT.length; i++) {

                  String codeTablePK = vestingStatusCT[i].getCodeTablePK() + "";
                  String codeDesc    = vestingStatusCT[i].getCodeDesc();
                  String code        = vestingStatusCT[i].getCode();

                 if (vestingStatus.equalsIgnoreCase(code)) {

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
      <td nowrap align="left">Vesting %:&nbsp;
        <input type="text" name="vestingPercent" maxlength="11" size="11" value="<%= vestingPercent %>">
      </td>
    </tr>
    <tr>
      <td nowrap align="left">Vesting Duration:&nbsp;
        <input type="text" name="vestingDuration" maxlength="3" size="3" value="<%= vestingDuration %>">
      </td>
    </tr>
    <tr>
      <td width="57%" align="right" nowrap>
        <input type="button" name="enter" value="Enter" onClick="saveVesting()">
        <input type="button" name="enter" value="Cancel" onClick="cancelVesting()">
      </td>
    </tr>
  </table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">
  <input type="hidden" name="vestingPK" value="<%= vestingPK %>">

</form>
</body>
</html>
