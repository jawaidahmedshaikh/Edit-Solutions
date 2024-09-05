<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 fission.utility.Util" %>

<jsp:useBean id="contractHistoryChargesSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    String historyKey = (String) request.getAttribute("editTrxHistoryPK");
%>
<html>
<head>
<title>Adjustments</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.chargesForm;

        formatCurrency();
	}

</script>
</head>
<body bgcolor="#DDDDDD" onLoad="init()">
<form name="chargesForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th align="left">Adjustment Type</th>
      <th align="left">   Amount  </th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:80%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="chargesSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
	  <%
        if (contractHistoryChargesSessionBean.hasPageBeans()) {

            PageBean chargeBean = contractHistoryChargesSessionBean.getPageBean(historyKey);

            Map displayValues = chargeBean.getDisplayValues();

            Iterator chargeBeanKeys = displayValues.keySet().iterator();

            while (chargeBeanKeys.hasNext()) {

                String chargeType = (String) chargeBeanKeys.next();
                String chargeAmount = (String) chargeBean.getValue(chargeType);
      %>
        <tr>
          <td nowrap>
            <%= chargeType %>
          </td>
          <td nowrap>
            <script>document.write(formatAsCurrency(<%= chargeAmount %>))</script>
          </td>
        </tr>
	  <%
            }// end while
        }// end if
	  %>
	</table>
  </span>

  <table id="closeTable" width="100%" border="0" cellspacing="0" cellpadding="0" height="10">
    <tr>
	  <td colspan="8" nowrap align="right">
	    <input type="button" value="   Close  " onClick="closeWindow()">
      </td>
    </tr>
  </table>

  <!-- ****** HIDDEN FIELDS ***** //-->
  <input type="hidden" name="transaction"  value="">
  <input type="hidden" name="action"       value="">

</form>

</body>
</html>
