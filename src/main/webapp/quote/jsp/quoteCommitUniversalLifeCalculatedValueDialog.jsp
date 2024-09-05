<!-- quoteCommitUniversalLifeCalculatedValueDialog.jsp //-->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.*,
                 event.*,
                 contract.*,
                 fission.utility.*" %>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
	PageBean formBean  = quoteMainSessionBean.getPageBean("formBean");

	String segmentPK   = formBean.getValue("segmentPK");
	Segment baseSegment = Segment.findByPK(new Long(segmentPK));   // includes Life table

	Life baseLife = baseSegment.getLife();
	String guidelineSinglePremium = Util.initEDITBigDecimal(baseLife.getGuidelineSinglePremium().toString(), new EDITBigDecimal()).toString();
	String guidelineLevelPremium = Util.initEDITBigDecimal(baseLife.getGuidelineLevelPremium().toString(), new EDITBigDecimal()).toString();
 	String mecStatus = (baseLife.getMECStatusCT() == null ? "" : baseLife.getMECStatusCT());
    String tamra = Util.initEDITBigDecimal(baseLife.getTamra().toString(), new EDITBigDecimal()).toString();
    String MAPEndDate = (baseLife.getMAPEndDate() == null ? "" : DateTimeUtil.formatEDITDateAsMMDDYYYY(baseLife.getMAPEndDate()));
    
    PremiumDue premiumDue = PremiumDue.findBySegmentPK_LatestEffectiveDate(baseSegment.getSegmentPK());
    String minimumMonthlyPremium = "";
    if (premiumDue != null)
    {
    	Map<String, EDITBigDecimal> totalExpectedMonthlyPremiums = CommissionPhase.findTotalExpectedMonthlyPremiums(premiumDue.getPremiumDuePK());
		EDITBigDecimal totalExpectedMonthlyPremium = totalExpectedMonthlyPremiums.get("totalExpectedMonthlyPremium");
		
		if (totalExpectedMonthlyPremium != null)
		{
    		minimumMonthlyPremium = totalExpectedMonthlyPremium.toString();
		}
    }
%>

<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.calculatedValueForm;

        formatCurrency();
	}
</script>

<title>Calculated Value</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
<body class="mainTheme" bgcolor="#DDDDDD" onLoad="init()">
<form name="calculatedValueForm" method="post" action="/PORTAL/servlet/RequestManager">
<table class="fieldGroupContainer" id="tableAll" >
  <tr>
  <td>
  <table class="fieldGroup1" id="table1" border="0" cellspacing="6" cellpadding="0">
    <tr>
      <td nowrap align="right">Contract Number:</td>
      <td nowrap align="left">
        <input type="text" name="contractNumber" size="19" maxlength="19" disabled value="<%= baseSegment.getContractNumber() %>" >
      </td>
      <td nowrap align="right">Contract Status:</td>
      <td nowrap align="left">
        <input type="text" name="contractStatus" size="19" maxlength="19" disabled value="<%= baseSegment.getSegmentStatusCT() %>">
      </td>
    </tr>
  </table>
  </td>
  </tr>
  <tr>
  <td>
  <table class="fieldGroup1" id="table2" width="100%" border="0" cellspacing="6" cellpadding="0">
  	<caption>Definition of Life Insurance</caption>
    <tr>
      <td nowrap align="right">Guideline Single Premium:</td>
      <td nowrap align="left">
         <input type="text" name="guidelineSinglePremium" size="19" maxlength="19" disabled value="<%= guidelineSinglePremium %>" CURRENCY>
      </td>
      <td nowrap align="right">Guideline Level Premium:</td>
      <td nowrap align="left">
         <input type="text" name="guidelineLevelPremium" size="19" maxlength="19" disabled value="<%= guidelineLevelPremium %>" CURRENCY>
      </td>
    </tr>
  </table>
  </td>
  </tr>
  <tr>
  <td>
  <table class="fieldGroup1" id="table3" width="100%" border="0" cellspacing="6" cellpadding="0">
  	<caption>TAMRA - Section 7702A</caption>
    <tr>
      <td nowrap align="right">MEC Status:</td>
      <td nowrap align="left">
        <input type="text" name="mecStatus" size="19" maxlength="19" disabled value="<%= mecStatus %>" >
      </td>
      <td nowrap align="right">7-Pay Premium:</td>
      <td nowrap align="left">
         <input type="text" name="tamra" size="19" maxlength="19" disabled value="<%= tamra %>" CURRENCY>
      </td>
    </tr>
  </table>
  </td>
  </tr>
  <tr>
  <td>
  <table class="fieldGroup1" id="table4" width="100%" border="0" cellspacing="6" cellpadding="0">
  	<caption>Minimum Premium - No Lapse Guarantee</caption>
    <tr>
      <td nowrap align="right">MAP End Date:</td>
      <td nowrap align="left">
        <input type="text" name="MAPEndDate" size="19" maxlength="19" disabled value="<%= MAPEndDate %>">
      </td>
      <td nowrap align="right">Minimum Monthly Premium:</td>
      <td nowrap align="left">
        <input type="text" name="minimumMonthlyPremium" size="19" maxlength="19" disabled value="<%= minimumMonthlyPremium %>" CURRENCY>
      </td>
    </tr>
  </table>
  </td>
  </tr>
    <tr>
      <td align="center">
        <input type="button" name="close" value="Close" onClick="closeWindow()">
      </td>
    </tr>
</table>

     <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction" value="">
  <input type="hidden" name="action"      value="">

</form>
</body>
</html>
