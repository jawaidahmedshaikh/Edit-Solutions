<!-- ****** JAVA CODE ***** //-->
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 fission.utility.*,
                 engine.*" %>


<%
	Company[] companyStructures = (Company[]) request.getAttribute("companyStructures");

    String analyzeTrx = Util.initString((String)request.getAttribute("analyzeJobToRun"), "");

    String errorMessage = Util.initString((String) request.getAttribute("errorMessage"), "");

%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script language="Javascript1.2">

	var f = null;
    var analyzeTrx = "<%= analyzeTrx %>";
    var errorMessage = "<%= errorMessage %>";

	function init() {

		f = document.equityIndexHedgeToRunForm;

        if (errorMessage != "")
        {
            alert(errorMessage);
            return;
        }

        if (analyzeTrx == "true")
        {
            showAnalyzer();
        }
	}

	function runEquityIndexHedge()
    {
        if (f.selectedCompanyStructure.value == "Please Select")
        {
            alert("Please Select Company Structure");
            return;
        }
        else
        {
            try
            {
                sendTransactionAction("DailyDetailTran", "runEquityIndexHedge", "main");
            }
            catch (e)
            {
                alert("Run Date Invalid");
            }
        }
	}

	function bCancel() {

		sendTransactionAction("DailyDetailTran", "showDailySelection", "main");
	}

	function analyzeJobToRun()
    {
        if (textElementIsEmpty(f.contractNumber))
        {
             alert("Please Enter ContractNumber For Analyze");
             return;
        }

         try
        {
		    sendTransactionAction("DailyDetailTran", "analyzeJobToRun", "_self");
        }
        catch (e)
        {
            alert("Run Date Invalid");
            return;
        }
	}

 	function showAnalyzer()
    {
        var width = screen.width;
        var height = screen.height;

		openDialog("analyzeJobToRun", "left=0,top=0,resizable=yes", width, height);

        sendTransactionAction("DailyDetailTran", "showAnalyzer","analyzeJobToRun");
    }

</script>

<title>Equity Index Hedge</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name="equityIndexHedgeToRunForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table id="table1" name="banking" width="56%" border="0" cellspacing="0" cellpadding="0" height="110" bgcolor="#DDDDDD">
    <tr>
      <td width="26%" nowrap>Company Structure:&nbsp;&nbsp;</td>
      <td width="9%" nowrap>
      <select name="selectedCompanyStructure">
      <option selected value="Please Select">Please Select</option>
	  <option>All</option>

      <%
      	for(int i = 0; i < companyStructures.length; i++)
        {
            if (!companyStructures[i].getCompanyName().equals("Commission"))
            {
                Set productStructures = companyStructures[i].getProductStructures();
                for (Iterator iterator = productStructures.iterator(); iterator.hasNext();)
                {
                    ProductStructure productStructure = (ProductStructure) iterator.next();



                    String companyStructure = companyStructures[i].getCompanyName() + "," +
                                              productStructure.getMarketingPackageName() + "," +
                                              productStructure.getGroupProductName() + "," +
                                              productStructure.getAreaName() + "," +
                                              productStructure.getBusinessContractName();

                    out.println("<option name=\"id\" value=\"" + productStructure.getProductStructurePK() + "\">" + companyStructure + "</option>");
                 }
            }
      	}
      %>
      </select>
      </td>
      <td width="65%">&nbsp;</td>
    </tr>
    <tr>
      <td nowrap align="right">Run Date:&nbsp;</td>
      <td nowrap align="left">
        <input:text name="runDate"
              attributesText="id='runDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
        <a href="javascript:show_calendar('f.runDate', f.runDate.value);"><img
               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
               alt="Select a date from the calendar"></a>
      </td>
    </tr>
    <tr>
      <td width="26%" nowrap align="right">Create Sub-buckets:</td>
      <td width="9%" nowrap align="left">
        <input type="checkbox" name="createSubBucketsInd">
      </td>
    </tr>

    <tr>
    <br>
    </tr>

    <tr>
      <td align="right" nowrap>Contract Number:&nbsp;</td>
      <td align="left" nowrap>
        <input type="text" name="contractNumber" maxlength="15" size="15" >
      </td>
    </tr>

    <tr>
      <td width="26%">&nbsp;</td>
      <td width="9%">&nbsp;</td>
      <td width="65%" align="right" nowrap>
        <input type="button" name="enter" value=" Enter " onClick="runEquityIndexHedge()">
        <input type="button" name="cancel" value="Cancel" onClick="bCancel()">
        <input type="button" name="analyze" value=" Analyze " onClick="analyzeJobToRun()">
      </td>
    </tr>
  </table>

<!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action" value="">
 <input type="hidden" name="jobName" value="<%= "EquityIndexHedge" %>">

</form>
</body>
</html>
