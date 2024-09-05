<!--
 * User: cgleason
 * Date: Mar 18, 2008
 * Time: 1:20:50 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="fission.utility.Util,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.services.db.hibernate.*,
                 java.util.*,
                 group.*,
                 client.*,
                 engine.*,
                 edit.portal.common.session.*,
                 fission.utility.DateTimeUtil,
                 edit.portal.taglib.InputSelect"%>

 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<jsp:useBean id="enrollmentState" class="group.EnrollmentState" scope="request"/>

<%
    String responseMessage = (String) request.getAttribute("responseMessage");
    String pageCommand = Util.initString((String) request.getAttribute("pageCommand"), "");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] stateVOCTs = codeTableWrapper.getCodeTableEntries("STATE");
    InputSelect stateCTs = new InputSelect(stateVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - EnrollmentState</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>

<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    var pageCommand = "<%= pageCommand %>";


    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();

        checkForPageCommand();

        // Initialize scroll tables
        initScrollTable(document.getElementById("EnrollmentStateTableModelScrollTable"));
    }

    function showLockAlert()
    {
<%--        if (shouldShowLockAlert == true)--%>
<%--        {--%>
<%--            alert("The Case can not be edited.");--%>
<%----%>
<%--            return false;--%>
<%--        }--%>
    }

    function checkForPageCommand()
    {
        if (!valueIsEmpty(pageCommand))
        {
            if (pageCommand == "resetForm")
            {
                resetForm();
            }
        }
    }

    function saveEnrollmentState()
    {
        sendTransactionAction("CaseDetailTran", "saveEnrollmentState", "enrollmentStateDialog");
    }


    function deleteEnrollmentState()
    {
        sendTransactionAction("CaseDetailTran", "deleteEnrollmentState", "enrollmentStateDialog");
    }

    /**
     * While a transaction is being saved, we want to disable all buttons to prevent double-saves.
     */
    function disableActionButtons()
    {
        document.all.trxMessage.innerHTML = "<font color='blue' face=''>Processing...<font>";

        document.all.btnSave.style.backgroundColor = "#99BBBB";
        document.all.btnCancel.style.backgroundColor = "#99BBBB";

        document.all.btnSave.disabled = true;
        document.all.btnCancel.disabled = true;
    }

    /*
    Callback method from TableModel implemenation.
    */
    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("CaseDetailTran", "showEnrollmentStateDetail", "_self");
    }


</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="dialog" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:40%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="45%" border="0" cellspacing="0" cellpadding="0">
    <tr>

        <td align="right" nowrap>&nbspState:&nbsp;</td>
        <td align="left" nowrap>
            <input:select bean="enrollmentState" name="stateCT" options="<%= stateCTs.getOptions() %>"
                          attributesText="id='stateCT' REQUIRED"/>
        </td>
    </tr>

  </table>

    <tr>
	  	<td nowrap align="left" width="5%">
			<input id="btnSave" type="button" value="Save" onClick="saveEnrollmentState()">
            <input id="btnDelete" type="button" value="Delete" onClick="deleteEnrollmentState()">
	  	</td>
    </tr>
  </table>

</span>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="EnrollmentStateTableModel"/>
    <jsp:param name="tableHeight" value="40"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>

  <table id="closeDialog" width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
    <tr>
	  <td colspan="8" nowrap align="right">
        <input type="button" name="close" value="Close" onClick="window.close()">
      </td>
    </tr>
  </table>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
    <input:hidden name="transaction"/>
    <input:hidden name="action"/>
    <input:hidden name="enrollmentStatePK" bean="enrollmentState"/>
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
