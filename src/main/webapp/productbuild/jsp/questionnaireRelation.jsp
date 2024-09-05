<!--
 *
 * User: cgleason
 * Date: Jun 21, 2007
 * Time: 9:11:21 AM
 *
 * (c) 2000 - 2007 Systems Engineering Group, LLC.  All rights Reserved
 *  Systems Engineering Group, LLC Propietary and confidential.  Any use is
 *  subject to the license agreement.
 -->

<%@ page import="fission.utility.*,
                 engine.*,
                 productbuild.*"%>


<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    String activeProductStructurePK = Util.initString((String) request.getAttribute("selectedRowIds_ProductStructureTableModel"), "0");

    String activeQuestionnairePK = (String)request.getAttribute("questionnairePK");

%>
<%-- ****************************** End Java Code ****************************** ---%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>
            Questionnaire Relations
        </title>

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>

<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();

        var scrollToRow = document.getElementById("<%= activeProductStructurePK %>");

        // Initialize scroll tables
        initScrollTable(document.getElementById("QuestionnaireTableModelScrollTable"));
        initScrollTable(document.getElementById("ProductStructureTableModelScrollTable"));
    }

    /**
     * Convenience link to the Questionnaire page.
     */
    function showQuestionnaire()
    {
        sendTransactionAction("ProductBuildTran", "showQuestionnaire", "main");
    }


    /**
     * Shows the clone Questionnaire dialog.
     */
    function showBuildQuestionnaireDialog()
    {
        if (f.selectedRowIds_ProductStructureTableModel.value == "0")
        {
            alert("Please Select Company Structure For Build")
        }
        else
        {
            var dialogHeight = 0.90 * getScreenHeight();

            var dialogWidth = getScreenWidth();

            openDialog("buildQuestionnaireDialog", null, dialogWidth, dialogHeight);

            sendTransactionAction("ProductBuildTran", "showBuildQuestionnaireDialog", "buildQuestionnaireDialog");
        }
    }

    /**
     * Cancels current questionnaire relation operations.
     */
    function cancelQuestionnaireRelation()
    {
        sendTransactionAction("ProductBuildTran", "cancelQuestionnaireRelation", "main");
    }

    function onTableRowSingleClick(tableId)
    {

        sendTransactionAction("ProductBuildTran", "showAssociatedQuestionaires", "main");
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN CompanyStructure Summary ****************************** --%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%">
      <span class="tableHeading">Company Structure Summary</span>
    </td>
  </tr>
</table>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="ProductStructureTableModel"/>
    <jsp:param name="tableHeight" value="40"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END CompanyStructure Summary ****************************** --%>

  <br>
<%-- ****************************** BEGIN Summary Questionnaire ****************************** --%>
<table width="100%">
  <tr>
    <td width="33%">
      &nbsp;
    </td>
    <td align="center" valign="bottom" width="33%">
      <span class="tableHeading">Questionnaire Summary</span>
    </td>
    <td align="right" width="33%">
      <input type="button" value="Questionnaire Summary" onClick="showQuestionnaire()">
    </td>
  </tr>
</table>


<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="QuestionnaireTableModel"/>
    <jsp:param name="tableHeight" value="40"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Questionnaire ****************************** --%>

<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right">
            <input type="button" name="build" value="Build" onClick="showBuildQuestionnaireDialog()">
            <input type="button" name="cancel" value="Cancel" onClick="cancelQuestionnaireRelation()">
        </td>
    </tr>
</table>


<%-- ****************************** END Area Values Summary ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">

<input type="hidden" name="activeProductStructure" default="<%= activeProductStructurePK %>"/>
<input type="hidden" name="questionnairePK" value="<%= activeQuestionnairePK %>">
<input type="hidden" name="companyStructure" value="">

<%-- ****************************** END Hidden Variables ****************************** --%>
</form>
</body>

</html>