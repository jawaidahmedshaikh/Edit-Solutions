<!-- agentAdditionalCompensationDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.CodeTableWrapper,
                 edit.common.vo.*,
                 fission.utility.Util" %>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] adaTypes = codeTableWrapper.getCodeTableEntries("ADATYPE");

    String adaType = (String) request.getAttribute("adaType");
    String annualizedMax = (String) request.getAttribute("annualizedMax");
    String serviceFeeStatus = (String) request.getAttribute("serviceFeeStatus");
    String bonusCommissionStatus = (String) request.getAttribute("bonusCommissionStatus");
    String ny91PctStatus = (String) request.getAttribute("ny91PctStatus");
    String additionalCompPK = (String) request.getAttribute("additionalCompensationPK");
    String agentContractPK = (String) request.getAttribute("agentContractPK");
    String contractCodeCT = (String) request.getAttribute("contractCodeCT");

    AdditionalCompensationVO[] addtnlCompVOs = null;
    AgentVO agentVO = (AgentVO) session.getAttribute("agentVO");
    if (agentVO != null)
    {
        AgentContractVO[] agentContractVOs = agentVO.getAgentContractVO();
        if (agentContractVOs != null)
        {
            for (int c = 0; c < agentContractVOs.length; c++)
            {
                if ((agentContractVOs[c].getAgentContractPK() + "").equals(agentContractPK) &&
                     (agentContractVOs[c].getContractCodeCT() + "").equals(contractCodeCT))
                {
                    addtnlCompVOs = agentContractVOs[c].getAdditionalCompensationVO();
                    if (addtnlCompVOs != null && addtnlCompVOs.length > 0)
                    {
                        adaType = addtnlCompVOs[0].getADATypeCT();
                        annualizedMax = addtnlCompVOs[0].getAnnualizedMax().toString();
                        serviceFeeStatus = addtnlCompVOs[0].getExpenseAllowanceStatus();
                        if (serviceFeeStatus.equalsIgnoreCase("Y"))
                        {
                            serviceFeeStatus = "checked";
                        }
                        bonusCommissionStatus = addtnlCompVOs[0].getBonusCommissionStatus();
                        if (bonusCommissionStatus.equalsIgnoreCase("Y"))
                        {
                            bonusCommissionStatus = "checked";
                        }
                        ny91PctStatus = addtnlCompVOs[0].getNY91PercentStatus();
                        if (ny91PctStatus.equalsIgnoreCase("Y"))
                        {
                            ny91PctStatus = "checked";
                        }
                        additionalCompPK = addtnlCompVOs[0].getAdditionalCompensationPK() + "";
                    }
                }
            }
        }
    }

    // AgentContract fields
    String effectiveMonth = (String) request.getAttribute("effMonth");
    String effectiveDay = (String) request.getAttribute("effDay");
    String effectiveYear = (String) request.getAttribute("effYear");
    String stopMonth = (String) request.getAttribute("stopMonth");
    String stopDay = (String) request.getAttribute("stopDay");
    String stopYear = (String) request.getAttribute("stopYear");
    String commissionProcess = (String) request.getAttribute("commissionProcess");
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init() {

		f = document.addtnlCompForm;

        formatCurrency();
	}

    function saveAdditionalCompensationDialog() {

		if (f.serviceFeeStatus.checked == true) {

			f.serviceFeeStatus.value = "checked";
		}
        else {

            f.serviceFeeStatus.value = "unchecked";
        }

		if (f.bonusCommissionStatus.checked == true) {

			f.bonusCommissionStatus.value = "checked";
		}
        else {

            f.bonusCommissionStatus.value = "unchecked";
        }

		if (f.ny91PctStatus.checked == true) {

			f.ny91PctStatus.value = "checked";
		}
        else {

            f.ny91PctStatus.value = "unchecked";
        }

        sendTransactionAction("AgentDetailTran","saveAdditionalCompensation","contentIFrame");
        closeWindow();
    }

	function closeAdditionalCompensationDialog()
    {
        sendTransactionAction("AgentDetailTran","cancelAdditionalCompensation","contentIFrame");
		closeWindow();
	}

</script>

<title>Additional Compensation</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="dialog" onLoad="init()" bgcolor="#DDDDDD">
<form name="addtnlCompForm" method="post" action="/PORTAL/servlet/RequestManager">
  <b>Agency Development Allowance</b>
  <span style="border-style:solid; border-width:1; position:relative; width:95%; height:40%; top:0; left:0; z-index:0; overflow:visible">
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
      <tr>
        <td nowrap align="left">Type:&nbsp;
        <select name="adaType">
          <option> Please Select </option>
          <%
              for(int i = 0; i < adaTypes.length; i++) {

                  String codeTablePK = adaTypes[i].getCodeTablePK() + "";
                  String codeDesc    = adaTypes[i].getCodeDesc();
                  String code        = adaTypes[i].getCode();

                 if (adaType.equalsIgnoreCase(code)) {

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
        <td nowrap align="left">Annualized Maximum:&nbsp;
          <input type="text" name="annualizedMax" maxlength="11" size="11" value="<%= annualizedMax %>" CURRENCY>
        </td>
      </tr>
    </table>
  </span>
  <br>
  <br>
  <span style="position:relative; width:95%; height:30%; top:0; left:0; z-index:0; overflow:visible">
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
      <tr>
        <td nowrap align="left">Service Fee&nbsp;
          <input type="checkbox" name="serviceFeeStatus" <%= serviceFeeStatus %> >
        </td>
      </tr>
      <tr>
        <td nowrap align="left">Bonus Commission:&nbsp;
          <input type="checkbox" name="bonusCommissionStatus" <%= bonusCommissionStatus %> >
        </td>
      </tr>
      <tr>
        <td nowrap align="left">NY 91% Rule:&nbsp;
          <input type="checkbox" name="ny91PctStatus" <%= ny91PctStatus %> >
        </td>
      </tr>
    </table>
  </span>
  <span>
    <table width="90%" height="10%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
      <tr>
        <td width="100%" align="right" nowrap>
          <input type="button" name="enter" value="Enter" onClick="saveAdditionalCompensationDialog()">
          <input type="button" name="cancel" value="Cancel" onClick="closeAdditionalCompensationDialog()">
        </td>
      </tr>
    </table>
  </span>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">

  <input type="hidden" name="agentContractPK" value="<%= agentContractPK %>">
  <input type="hidden" name="commissionContractFK" value="<%= contractCodeCT %>">
  <input type="hidden" name="additionalCompensationPK" value="<%= additionalCompPK %>">

  <input type="hidden" name="effMonth" value="<%= effectiveMonth %>">
  <input type="hidden" name="effDay" value="<%= effectiveDay %>">
  <input type="hidden" name="effYear" value="<%= effectiveYear %>">
  <input type="hidden" name="stopMonth" value="<%= stopMonth %>">
  <input type="hidden" name="stopMonth" value="<%= stopDay %>">
  <input type="hidden" name="stopMonth" value="<%= stopYear %>">
  <input type="hidden" name="commissionProcess" value="<%= commissionProcess %>">
</form>
</body>
</html>
