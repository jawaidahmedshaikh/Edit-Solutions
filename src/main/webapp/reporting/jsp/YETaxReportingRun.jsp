<%--
  Created by IntelliJ IDEA.
  User: sramamurthy
  Date: Oct 22, 2004
  Time: 1:55:32 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.ProductStructureVO,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 fission.utility.Util,
                 engine.*" %>


<%
	ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("productStructureVOs");

    String message = Util.initString((String) request.getAttribute("message"), "");
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script language="Javascript1.2">

	var f = null;
    var message = "<%= message %>";

	function init()
    {
		f = document.taxExtractForm;
        if (message != "")
        {
            alert(message);
        }
	}

	function runTaxExtract()
    {
        if (validateForm(f, "REQUIRED"))
        {
            if (f.startDate.value > f.endDate.value)
            {
                alert("End Date Is Less Than Start Date");
            }
            else if (f.tabSeparatedFileInd.checked == true &&
                     f.xmlFileInd.checked == true)
            {
                alert("Please Select Only One Output File Type");
            }
            else if (f.tabSeparatedFileInd.checked == false &&
                     f.xmlFileInd.checked == false)
            {
                alert("Please Select Output File Type");
            }
            else
            {
                if (f.tabSeparatedFileInd.checked == true)
                {
                    f.fileType.value = "T";
                }
                else
                {
                    f.fileType.value = "X";
                }

                sendTransactionAction("ReportingDetailTran", "runTaxExtract", "main");
            }
        }
	}

	function bCancel()
    {
		sendTransactionAction("ReportingDetailTran", "showDailySelection", "main");
	}


</script>

<title>Year-End Tax Reporting Extract</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="taxExtractForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="taxExtract" width="56%" border="0" cellspacing="6" cellpadding="0" height="110" bgcolor="#DDDDDD">
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
      <td align="right" nowrap>Company Structure:&nbsp;</td>
      <td align="left" nowrap>
        <select name="productStructure" REQUIRED>
        <option selected value="Please Select">Please Select</option>
	    <option>All</option>

        <%
        	for(int i = 0; i < productStructureVOs.length; i++)
            {
                Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));
                if (!company.getCompanyName().equals("Commission"))
                {

                    String productStructure = company.getCompanyName() + "," +
                                              productStructureVOs[i].getMarketingPackageName() + "," +
                                              productStructureVOs[i].getGroupProductName() + "," +
                                              productStructureVOs[i].getAreaName() + "," +
                                              productStructureVOs[i].getBusinessContractName();

                    out.println("<option name=\"id\" value=\"" + productStructureVOs[i].getProductStructurePK() + "\">" + productStructure + "</option>");
                }
            }
        %>
        </select>
      </td>
      <td align="right" nowrap>Tax Year:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="taxYear" maxlength="4" size="4" REQUIRED>
      </td>
    </tr>
    <tr>
      <td align="right"nowrap>Tax Report Type:&nbsp;</td>
      <td align="left" nowrap colspan="3">
        <select name="taxReportType" REQUIRED>
        <option selected value="Please Select">Please Select</option>
	    <option>All</option>
        <%
            CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
            CodeTableVO[] taxRptTypeVOs = codeTableWrapper.getCodeTableEntries("TAXRPTTYPE");
            for(int i = 0; i < taxRptTypeVOs.length; i++)
            {
                  out.println("<option name=\"id\" value=\""
                    + taxRptTypeVOs[i].getCode() + "\">" + taxRptTypeVOs[i].getCodeDesc() + "</option>");

            }
        %>
        </select>
      </td>
    </tr>
    <tr>
      <td align="right" nowrap>&nbsp;</td>
      <td nowrap align="left">Tab-Separated File&nbsp;
        <input type="checkbox" name="tabSeparatedFileInd">
      </td>
      <td align="right" nowrap>&nbsp;</td>
      <td nowrap align="left">XML File&nbsp;
        <input type="checkbox" name="xmlFileInd">
      </td>
    </tr>
    <tr>
      <td align="right" nowrap colspan="4">
        <input type="button" name="enter" value=" Enter " onClick="runTaxExtract()">
        <input type="button" name="cancel" value="Cancel" onClick="bCancel()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">
  <input type="hidden" name="fileType" value="">

</form>
</body>
</html>