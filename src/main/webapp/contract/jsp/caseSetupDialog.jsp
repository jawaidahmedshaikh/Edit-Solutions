<!--
 * User: cgleason
 * Date: Dec. 9, 2008
 * Time: 1:59:50 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

<!-- ****** JAVA CODE ***** //-->
<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.CodeTableVO,
                 fission.utility.*,
                 fission.utility.Util,
                 edit.services.db.hibernate.*,
                 group.*,
                 edit.portal.common.session.*,
                 edit.portal.taglib.InputSelect" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="caseSetup" class="group.CaseSetup" scope="request"/>


<%
    String responseMessage = (String) request.getAttribute("responseMessage");
    String pageCommand = Util.initString((String) request.getAttribute("pageCommand"), "");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] stateVOs = codeTableWrapper.getCodeTableEntries("STATE");
    InputSelect stateCTs = new InputSelect(stateVOs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    CodeTableVO[] caseSetupOptionVOs = codeTableWrapper.getCodeTableEntries("CASESETUPOPTION");
    InputSelect caseSetupOptionCTs = new InputSelect(caseSetupOptionVOs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    CodeTableVO[] caseSetupOptionValueVOs = codeTableWrapper.getCodeTableEntries("CASESETUPVALUE");
    InputSelect caseSetupOptionValueCTs = new InputSelect(caseSetupOptionValueVOs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    EDITDate effDate = caseSetup.getEffectiveDate();
    String effectiveDate  = "";
    if (effDate != null)
    {
        effectiveDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(effDate);
    }

%>

<html>

<head>
<title>Case Setup</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" conte="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script type="text/javascript">

	var f = null;

    var responseMessage = "<%= responseMessage %>";

    var pageCommand = "<%= pageCommand %>";

	function init() {

		f = document.caseSetupForm;

        checkForResponseMessage();

        checkForPageCommand();

        // Initialize scroll tables
        initScrollTable(document.getElementById("CaseSetupTableModelScrollTable"));
	}

    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("CaseDetailTran", "showCaseSetupDetail", "_self");
    }

    function checkForPageCommand()
    {
        if (!valueIsEmpty(pageCommand))
        {
            if (pageCommand == "resetPage")
            {
                resetForm();
            }
        }
    }

	function saveCaseSetup()
    {
		sendTransactionAction("CaseDetailTran", "saveCaseSetup", "caseSetupDialog");
	}

	function addCaseSetup()
    {
        resetForm();
        clearForm();
	}

	function cancelCaseSetup()
    {
        resetForm();
        clearForm();
	}


	function deleteCaseSetup()
    {
		sendTransactionAction("CaseDetailTran","deleteCaseSetup","caseSetupDialog")
	}

	function clearForm()
    {
		sendTransactionAction("CaseDetailTran","cancelCaseSetup","caseSetupDialog")
	}

</script>


<body class="dialog" onLoad="init()">
<form name="caseSetupForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>

<span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="50%" border="0" cellspacing="6" cellpadding="0">
    <tr>
        <td align="right" nowrap="nowrap">Effective Date:&nbsp;</td>
        <td align="left" nowrap="nowrap">
           <input type="text" name="effectiveDate" value="<%= effectiveDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>

        <td align="right" nowrap>Case Setup Option:&nbsp;</td>
        <td align="left" nowrap="nowrap">
            <input:select bean="caseSetup" name="caseSetupOptionCT" options="<%= caseSetupOptionCTs.getOptions() %>"
                          attributesText="id='caseSetupOptionCTs'"/>
        </td>
    </tr>

    <tr>
        <td align="right" nowrap>Case Setup Value:&nbsp;</td>
        <td align="left" nowrap="nowrap">
            <input:select bean="caseSetup" name="caseSetupOptionValueCT" options="<%= caseSetupOptionValueCTs.getOptions() %>"
                          attributesText="id='caseSetupOptionValueCTs'"/>
        </td>

        <td align="right" nowrap>State:&nbsp;</td>
        <td align="left" nowrap="nowrap">
            <input:select bean="caseSetup" name="stateCT" options="<%= stateCTs.getOptions() %>"
                          attributesText="id='stateCTs'"/>
        </td>
    </tr>

  </table>

  <table width="100%" height="3%" border="0" cellspacing="0" cellpadding="0">
    <tr height="2%">
      <td nowrap align="left" colspan="4">
		<input type="button" value="   Add   " style="background-color:#DEDEDE" onClick="addCaseSetup()">
		<input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="saveCaseSetup()">
		<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelCaseSetup()">
		<input type="button" value="  Delete " style="background-color:#DEDEDE" onClick="deleteCaseSetup()">
	  </td>
	</tr>
  </table>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="CaseSetupTableModel"/>
    <jsp:param name="tableHeight" value="40"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>
  <table id="table3" width="100%" border="0" cellspacing="6" cellpadding="0">
    <tr>
      <td align="right" nowrap colspan="3">
        <input type="button" name="close" value="Close" onClick="window.close()">

      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input:hidden name="caseSetupPK" bean="caseSetup"/>

</form>
</body>
</html>
