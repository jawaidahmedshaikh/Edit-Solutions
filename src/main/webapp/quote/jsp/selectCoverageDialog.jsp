<%@ page import="edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO,
                 fission.utility.Util,
                 fission.beans.PageBean"%>

<%@ taglib uri="/WEB-INF/SecurityTaglib.tld" prefix="security" %>

<jsp:useBean id="formBean"
    class="fission.beans.PageBean" scope="request"/>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<html>

<head>
<title>Select Coverage</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
    CodeTableVO[] options = codeTableWrapper.getCodeTableEntries("SEGMENTNAME");

    // not needed now.  tag selectForUserCompanyStructures will get comp structures for the user.
    //CompanyStructureVO[] companyStructureVOs = new engine.component.LookupComponent().getCompanyStructuresByTypeCode("Product");

    String optionId = formBean.getValue("optionId");
    String companyStructureId  = formBean.getValue("companyStructureId");
    String companyStructure = formBean.getValue("companyStructure");

    String contractTarget = (String) request.getAttribute("contractTarget");

    String contractNumber = formBean.getValue("contractNumber");

    String checkBoxStatus      = formBean.getValue("checkBoxStatus");

    if (checkBoxStatus.equals("true")){

        checkBoxStatus = "checked";
    }
    else {

        checkBoxStatus = "";
    }
%>
<script language="Javascript1.2">



	var f = null;
    var contractTarget = "<%= contractTarget %>";

	function init()
    {
        f = document.coverageSelectionForm;
	}

    function selectCoverage(){

        if (f.optionId.value == "" ||
            f.optionId.value == "Please Select")
        {

            alert ("Coverage Must Be Selected");
            f.optionId.focus();
        }
        else if (f.companyStructureId.value == "" ||
                     f.companyStructureId.value ==  "0")
            {

                 alert ("Company Structure Must Be Selected");
                 f.companyStructureId.focus();
            }
        else if (f.contractNumber.value == "")
            {
                 alert ("Enter the Contract Number or Check the Auto Assign Number box");
                 f.companyStructureId.focus();
            }
        else {
            sendTransactionAction("QuoteDetailTran", "addNewQuote", "contentIFrame");
            window.close();
        }
    }

	function cancelDialog() {

		window.close();
	}

	function setCompanyStructure() {

		f.companyStructure.value = f.companyStructureId.options[f.companyStructureId.selectedIndex].text;

    }

    function checkForEnter()
    {
        var eventObj = window.event;

        if (eventObj.keyCode == 13){
            selectCoverage();
        }
    }

    function sendTransactionAction(transaction, action, target) {

        f.transaction.value= transaction;
        f.action.value= action;

        f.target = target;

        f.companyStructure.value = f.companyStructureId.options[f.companyStructureId.selectedIndex].text;

        f.submit();
    }

    function autoGenerateContractNumber() {

		if (f.autoAssignNumberBox.checked == true) {

			f.checkBoxStatus.value = "true";
			f.contractTarget.value = "<%=contractTarget%>";
			sendTransactionAction('QuoteDetailTran', 'autoGenerateContractNumber', '_self');
		}
		else {

			f.contractNumber.value        = "";
			f.autoAssignNumberBox.checked = false;
			f.checkBoxStatus.value = "false";
			f.contractTarget.value = "<%=contractTarget%>";
		}
	}

</script>
</head>
<body bgcolor="#DDDDDD" onLoad="init()">
<form name="coverageSelectionForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" width="43%" border="0" cellspacing="0" cellpadding="8" bgcolor="#DDDDDD">

    <tr>
      <td nowrap align="right">Select Segment Name:</td>
      <td align="left">
        <select name="optionId" tabindex="1" onKeyDown="checkForEnter()">
          <option selected value="Please Select">Please Select</option>
            <%

                  for(int i = 0; i < options.length; i++)
                  {
                      String codeDesc    = options[i].getCodeDesc();
                      String code        = options[i].getCode();

                      if (code.equalsIgnoreCase(optionId))
                      {
                          out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                      }
                      else
                      {
                          out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                      }
                  }

            %>
        </select>
      </td>
      </tr>

      <tr>
      <td align="right" nowrap>Select Company Structure:</td>
      <td align="left">


                <%--
                    Select pulldown for CompanyStructures.  dynamicAttributes is optional.
                    This will get the product-type company structures that the user is
                    allowed.  If development mode, then all product-type companies are displayed.
                    Note - the selected company structure PK will be assigned to the name
                    variable.

                    In the Tran java class, there is no magic.  You must manually put the
                    selected PK value into the UserSession.  There is a helper method
                    that will do this for you.

                        CompanyStructure.setCurrentCompanyStructureInSession(
                                appReqBlock,
                                "companyStructurePK");

                    If the variable is null or 0, it won't change the current
                    company structure in the session.
                --%>
                <security:selectForUserCompanyStructures
                        name="companyStructureId"
                        dynamicAttributes="tabindex='2' onChange='setCompanyStructure()' onKeyDown='checkForEnter()'"
                />


                <%--        <select name="companyStructureId" tabindex="2" onClick="setCompanyStructure()" onKeyDown="checkForEnter()">--%>
                <%--	        <option value="Please Select">Please Select</option>--%>
                <%--	        <%--%>
                <%----%>
                <%--			for(int i=0; i< companyStructureVOs.length; i++){--%>
                <%----%>
                <%--                String currentCompanyStructurePK = companyStructureVOs[i].getCompanyStructurePK() + "";--%>
                <%----%>
                <%--			if (companyStructureId.equals(currentCompanyStructurePK)) {--%>
                <%----%>
                <%--				out.println("<option selected value=\""+ currentCompanyStructurePK + "\">" + Util.getCompanyStructure(companyStructureVOs[i], ",") + "</option>");--%>
                <%--			}else--%>
                <%----%>
                <%--				out.println("<option value=\""+ currentCompanyStructurePK + "\">" + Util.getCompanyStructure(companyStructureVOs[i], ",") + "</option>");--%>
                <%--			}--%>
                <%--	       %>--%>
                <%----%>
                <%--        </select>--%>



      </td>
    </tr>

    <!-- these fields are brought from quoteCommitContractNumberDialog.jsp change as per business requirment -->
    <tr>
      <td align="right" nowrap>Application Number:</td>
      <td align="left">
        <input type="text" name="contractNumber" maxlength="15" size="15" value="<%= contractNumber%>" tabindex="3" onKeyDown="checkForEnter()">
      </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td align="left" nowrap>
	    <input type="checkbox" name="autoAssignNumberBox" <%= checkBoxStatus %> onClick="autoGenerateContractNumber()" tabindex="4" onKeyDown="checkForEnter()">
        Auto Assign Number</td>
    </tr>

    <tr>
      <td>&nbsp;</td>
      <td align="left" nowrap>
        <input type="button" name="enter" value="Enter" onClick="selectCoverage()">
        <input type="button" name="cancel" value="Cancel" onClick="cancelDialog()">
      </td>
    </tr>
  </table>

  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">
  <input type="hidden" name="companyStructure" value="<%= companyStructure %>">
  <input type="hidden" name="checkBoxStatus" value="">
  <input type="hidden" name="contractTarget" value="">

</form>
</body>
</html>
