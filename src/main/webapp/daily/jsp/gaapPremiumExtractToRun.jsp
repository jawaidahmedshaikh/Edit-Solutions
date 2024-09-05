<!-- ****** JAVA CODE ***** //-->
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.ProductStructureVO,
                 engine.*" %>


<%
	ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("companyStructureVOs");
    String message = (String) request.getAttribute("message");

%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script language="Javascript1.2">

	var f = null;
    var message = "<%= message %>";

	function init() {

		f = document.gaapPremiumExtractToRunForm;

        if (message != "null")
        {
            alert(message);
        }
	}

	function runGAAPPremiumExtract()
    {
        if (f.selectedCompanyStructure.value == "Please Select")
        {
            alert("Please Select Company Structure");
        }
        else if (f.startDate.value > f.endDate.value)
        {
            alert("End Date Is Less Than Start Date");
        }
        else
        {
            sendTransactionAction("DailyDetailTran", "runGAAPPremiumExtract", "main");
        }
	}

	function bCancel() {

		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}

</script>

<title>GAAP Premium Reserves Extract</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="gaapPremiumExtractToRunForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="banking" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
    <tr>
      <td nowrap align="right">Start Date:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="startDate"
              attributesText="id='startDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.startDate', f.startDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
      <td nowrap align="right">End Date:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="endDate"
              attributesText="id='endDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.endDate', f.endDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td width="26%" nowrap>Company Structure:</td>
      <td width="9%" nowrap>
      <select name="selectedCompanyStructure">
      <option selected value="Please Select">Please Select</option>
	  <option>All</option>

      <%
      	for(int i = 0; i < productStructureVOs.length; i++)
        {
            Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));
            if (!company.getCompanyName().equals("Commission"))
            {

                String companyStructure = company.getCompanyName() + "," +
                                          productStructureVOs[i].getMarketingPackageName() + "," +
                                          productStructureVOs[i].getGroupProductName() + "," +
                                          productStructureVOs[i].getAreaName() + "," +
                                          productStructureVOs[i].getBusinessContractName();

                out.println("<option name=\"id\" value=\"" + productStructureVOs[i].getProductStructurePK() + "\">" + companyStructure + "</option>");
            }
      	}
      %>
      </select>
      </td>
      <td width="65%">&nbsp;</td>
    </tr>
    <tr>
      <td width="26%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
      <td width="65%" align="right" nowrap>
        <input type="button" name="enter" value=" Enter " onClick="runGAAPPremiumExtract()">
        <input type="button" name="cancel" value="Cancel" onClick="bCancel()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">

</form>
</body>
</html>