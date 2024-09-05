<%@ page import="edit.common.EDITBigDecimal"%><!-- ************* JSP Code ************* -->
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<jsp:useBean id="pageBean"
    class="fission.beans.PageBean" scope="request"/>

<%
	String issueAge = pageBean.getValue("issueAge");
	String interestRate = pageBean.getValue("interestRate");
	String coverageAmount = pageBean.getValue("coverageAmount");

	String genderId = pageBean.getValue("genderId");

	if (genderId.equalsIgnoreCase("M"))
		genderId = "Male";
	else if (genderId.equalsIgnoreCase("F"))
		genderId = "Female";

	String classId = pageBean.getValue("classId");

	if (classId.equalsIgnoreCase("N"))
		classId = "Non Smoker";
	else if (classId.equalsIgnoreCase("S"))
		classId = "Smoker";

	String deathBenOptId = pageBean.getValue("deathBenOptId");

	if (deathBenOptId.equalsIgnoreCase("I"))
		deathBenOptId = "Increasing";
	else if (deathBenOptId.equalsIgnoreCase("L"))
		deathBenOptId = "Level";

	String GSP = pageBean.getValue("GSP");
	String GLP = pageBean.getValue("GLP");
	String TAMRA = pageBean.getValue("TAMRA");

	String[] durationGuar = pageBean.getValues("durationGuar");
	String[] premiumGuar = pageBean.getValues("premiumGuar");
	String[] deathBenefitGuar = pageBean.getValues("deathBenefitGuar");
	String[] cashValueGuar = pageBean.getValues("cashValueGuar");
	String[] cashSurrenderValueGuar = pageBean.getValues("cashSurrenderValueGuar");

	String[] durationCurr = pageBean.getValues("durationCurr");
	String[] premiumCurr = pageBean.getValues("premiumCurr");
	String[] deathBenefitCurr = pageBean.getValues("deathBenefitCurr");
	String[] cashValueCurr = pageBean.getValues("cashValueCurr");
	String[] cashSurrenderValueCurr = pageBean.getValues("cashSurrenderValueCurr");
%>

<html>
<head>
<title>Projection</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">

<!-- ************* Java Script Code ************* -->
<script>
var f = null;

function init()  {

	f = document.projectForm;
}
</script>
</head>

<!-- ************* HTML Code ************* -->
<body onLoad="init()">

<h3 class="Heading">
    <%-- <jsp:getProperty name="aProductBean"  property="longName"/> --%>
</h3>

<hr size="3">

<table align="center" cellpadding="2" cellspacing="4">
  <tr>
    <td  class="headerData2" colspan="4">
       <%= genderId %>&nbsp
       Age
       <%= issueAge %> &nbsp
       <%= classId %> &nbsp
       <%= deathBenOptId %>&nbsp
    </td>
  </tr>
  <tr>
    <td  class="headerData2" colspan="4">
        Coverage Amount
<%--        $ <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",--%>
<%--                Double.parseDouble(coverageAmount)) %>--%>

        $ <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",
                new EDITBigDecimal(coverageAmount)) %>


    </td>
  </tr>
  <tr>
    <th class="headerHeading">
        Guideline Single Premium
    </th>
    <td class="headerData">
<%--        $--%>
<%--        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",--%>
<%--                Double.parseDouble(GSP)) %>--%>
        $
        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",
                new EDITBigDecimal(GSP)) %>

        &nbsp
    </td>
    <th class="headerHeading">
        Guideline Level Premium
    </th>
    <td class="headerData">
<%--        $--%>
<%--        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",--%>
<%--                Double.parseDouble(GLP)) %>--%>
        $
        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",
                new EDITBigDecimal(GLP)) %>

    </td>
  </tr>
  <tr>
    <th class="headerHeading">
        TAMRA
    </th>
    <td class="headerData">
<%--        $--%>
<%--        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",--%>
<%--                Double.parseDouble(TAMRA)) %>--%>
        $
        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",
                new EDITBigDecimal(TAMRA)) %>

        &nbsp
    </td>
    <th class="headerHeading">
        Gross Interest Rate
    </th>
    <td class="headerData">
<%--        <%= fission.utility.Util.formatDecimal("##0.00",Double.parseDouble(interestRate)) %>--%>
        <%= fission.utility.Util.formatDecimal("##0.00",new EDITBigDecimal(interestRate)) %>
        %
    </td>
  </tr>
  <tr>
    <td colspan="4">
        <hr size="1" width="50%">
    </td>
  </tr>
  <tr>

    <th colspan="4">
        <a href="#Guaranteed">Show Guaranteed</a> &nbsp &nbsp
        <a href="#Current"   >Show Current</a>
    </th>
  </tr>
</table>

<hr size="3">

<h4 class="Heading">
    <a name="Guaranteed">Guaranteed</a>
</h4>
<table align="center" cellpadding="0" cellspacing="0">
  <tr>
    <th class="heading" width="18%">
        <br>
        <br>
        Duration<br>
        <hr size="1">
    </th>
    <th width="2%">
    </th>
    <th class="heading" width="18%">
        <br>
        <br>
        Premium
        <hr size="1">
    </th>
    <th width="2%">
    </th>
    <th class="heading" width="18%">
        <br>
        Death<br>
        Benefit
        <hr size="1">
    </th>
    <th width="2%">
    </th>
    <th class="heading" width="18%">
        <br>
        Cash<br>
        Value
        <hr size="1">
    </th>
    <th width="2%">
    </th>
    <th class="heading" width="18%">
        Cash<br>
        Surrender<br>
        Value
        <hr size="1">
    </th>
  </tr>
  <tr>
    <td colspan="9">
        <br>
    </td>
  </tr>
  <% for (int i=0; i < durationGuar.length; i++) { %>
   <tr>
     <td class="data2" align="center" width="18%">
        <%= durationGuar[i] %>
     </td>
     <td width="2%">
     </td>
     <td class="data" width="18%">
<%--        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",--%>
<%--                Double.parseDouble(premiumGuar[i])) %>--%>
        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",
                new EDITBigDecimal(premiumGuar[i])) %>
     </td>
     <td width="2%">
     </td>
     <td class="data" width="18%">
<%--        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",--%>
<%--                Double.parseDouble(deathBenefitGuar[i])) %>--%>
        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",
                new EDITBigDecimal(deathBenefitGuar[i])) %>

     </td>
     <td width="2%">
     </td>
     <td class="data" width="18%">
<%--        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",--%>
<%--                Double.parseDouble(cashValueGuar[i])) %>--%>

        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",
                new EDITBigDecimal(cashValueGuar[i])) %>

     </td>
     <td width="2%">
     </td>
     <td class="data" width="18%">
<%--        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",--%>
<%--                Double.parseDouble(cashSurrenderValueGuar[i])) %>--%>
        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",
                new EDITBigDecimal(cashSurrenderValueGuar[i])) %>

     </td>
   </tr>
  <% } %>
</table>

<br>

<hr size="3">
<p>
    <br><br><br><br><br><br><br><br><br><br>
    <br><br><br><br><br><br><br><br><br>
</p>

<h4 class="Heading">
    <a name="Current">Current</a>
</h4>

<table align="center" cellpadding="0" cellspacing="0">
  <tr>
    <th class="heading" width="18%">
        <br>
        <br>
        Duration
        <hr size="1">
    </th>
    <th width="2%">
    </th>
    <th class="heading" width="18%">
        <br>
        <br>
        Premium
        <hr size="1">
    </th>
    <th width="2%">
    </th>
    <th class="heading" width="18%">
        <br>
        Death<br>
        Benefit
        <hr size="1">
    </th>
    <th width="2%">
    </th>
    <th class="heading" width="18%">
        <br>
        Cash<br>
        Value
        <hr size="1">
    </th>
    <th width="2%">
    </th>
    <th class="heading" width="18%">
        Cash<br>
        Surrender<br>
        Value
        <hr size="1">
    </th>
  </tr>
  <tr>
    <td colspan="9">
        <br>
    </td>
  </tr>
  <% for (int i=0; i < durationCurr.length; i++) { %>
  <tr>
     <td class="data2" width="18%">
        <%= durationCurr[i] %>
     </td>
     <td width="2%">
     </td>
     <td class="data" width="18%">
<%--        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",--%>
<%--                Double.parseDouble(premiumCurr[i])) %>--%>
        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",
                new EDITBigDecimal(premiumCurr[i])) %>

     </td>
     <td width="2%">
     </td>
     <td class="data" width="18%">
<%--        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",--%>
<%--                Double.parseDouble(deathBenefitCurr[i])) %>--%>
        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",
                new EDITBigDecimal(deathBenefitCurr[i])) %>

     </td>
     <td width="2%">
     </td>
     <td class="data" width="18%">
<%--        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",--%>
<%--                Double.parseDouble(cashValueCurr[i])) %>--%>
        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",
                new EDITBigDecimal(cashValueCurr[i])) %>

     </td>
     <td width="2%">
     </td>
     <td class="data" width="20%">
<%--        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",--%>
<%--                Double.parseDouble(cashSurrenderValueCurr[i])) %>--%>
        <%= fission.utility.Util.formatDecimal("#,###,###,##0.00",
                new EDITBigDecimal(cashSurrenderValueCurr[i])) %>

     </td>
   </tr>
  <% } %>
</table>

<br><br>
<hr size="1">
<form name="projectForm" method="post" action="/PORTAL/servlet/RequestManager">
<table>
<tr>
    <td>
        Export Projection data to a File:
    </td>
	<td>
       	<select NAME="fileType">

			<option value="XML File" selected>
				XML File
			</option>

			<option value="Comma Delimited File">
				Comma Delimited File
			</option>
		</select>
    </td>
	<td>
        <input type="submit" name="export"      value="      Export      "
            title="Export Projection data to a File"
            onclick="transaction.value='ProjectTran';action.value='saveProjection';">
    </td>
	<td>
		<input type="submit" name="expandScript" value="ScriptCallChain"
		       title="Expand the projection script"
			   onClick="transaction.value='ParamTran';action.value='callScriptChain';">

	</td>
</tr>
</table>

<!-- ************* Hidden Fields ************* -->
<input id="transaction" type="hidden"   name="transaction" value="">
<input id="action"      type="hidden"   name="action"      value="">
<input id="scriptName"  type="hidden"   name="scriptName"  value="Schedule">
</form>
</body>
</html>
