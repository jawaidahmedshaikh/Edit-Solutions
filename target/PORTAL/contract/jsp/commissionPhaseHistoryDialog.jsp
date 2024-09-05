<!--
 * User: cgleason
 * Date: May 06, 2009
 * Time: 02:41:39 PM
 *
 * (c) 2000-2009 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.utility.Util,
                 event.*" %>

<%
    String message = Util.initString((String) request.getAttribute("message"), "");

%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>

<script language="JavaScript1.2">

<!--******* JAVASCRIPT ******//-->

    var f = null;

    var message = "<%= message %>";

    function init()
    {
        f = document.commissionPhaseHistoryForm;

        if (message != "")
        {
            alert(message);
        }

        // Initialize scroll tables
        initScrollTable(document.getElementById("CommissionPhaseHistoryTableModelScrollTable"));
    }

    function closeHistory()
    {
       window.close();
    }


</script>
<head>
<title>Commission Phase History</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>


<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name="commissionPhaseHistoryForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="mainContent" style="position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
<%--<table class="formData" width="100%" height="4%" border="0" cellspacing="0" cellpadding="5">--%>


<br>
<br>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="CommissionPhaseHistoryTableModel"/>
    <jsp:param name="tableHeight" value="60"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>


  <br>

 <table  width="100%" border="0">
      <tr>
        <td align="right" colspan="2">
            &nbsp;
        </td>
        <td align="right" nowrap>
          <input type="button" name="close" value="Close" onClick="closeHistory()">
        </td>
      </tr>
</table>

</span>


<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">

</form>
</body>

</html>
