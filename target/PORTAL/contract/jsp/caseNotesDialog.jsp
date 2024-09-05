<!--
 * User: cgleason
 * Date: Oct 30, 2007
 * Time: 1:59:50 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
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

<jsp:useBean id="contractGroupNote" class="group.ContractGroupNote" scope="request"/>


<%
    String responseMessage = (String) request.getAttribute("responseMessage");
    String pageCommand = Util.initString((String) request.getAttribute("pageCommand"), "");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] noteTypeVOs = codeTableWrapper.getCodeTableEntries("NOTETYPE");
    InputSelect noteTypeCTs = new InputSelect(noteTypeVOs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    CodeTableVO[] noteQualifierVOs = codeTableWrapper.getCodeTableEntries("NOTEQUALIFIER");
    InputSelect noteQualifierCTs = new InputSelect(noteQualifierVOs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    String note                = contractGroupNote.getNote();
    if (note == null)
    {
        note = "";
    }
    EDITDateTime maintDateTime = contractGroupNote.getMaintDateTime();
    String maintDate  = "";
    if (maintDateTime != null)
    {
        maintDate = DateTimeUtil.formatEDITDateTimeAsMMDDYYYY(maintDateTime);
    }
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>

<script language="Javascript1.2">

	var f = null;

    var responseMessage = "<%= responseMessage %>";

    var pageCommand = "<%= pageCommand %>";

	function init() {

		f = document.notesForm;

        checkForResponseMessage();

        checkForPageCommand();

        // Initialize scroll tables
        initScrollTable(document.getElementById("CaseNoteTableModelScrollTable"));
	}

    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("CaseDetailTran", "showCaseNoteDetail", "_self");

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

	function saveNoteDetail()
    {

		sendTransactionAction("CaseDetailTran", "saveCaseNoteDetail", "caseNotesDialog");
<%--		window.close();--%>
	}

	function addNote()
    {
        resetForm();
        clearForm();
	}

	function cancelNotesDialog()
    {
        resetForm();
        clearForm();
	}


	function deleteCurrentNote()
    {
		sendTransactionAction("CaseDetailTran","deleteCaseNote","caseNotesDialog")
	}

	function clearForm()
    {
		sendTransactionAction("CaseDetailTran","cancelCaseNote","caseNotesDialog")
	}

</script>

<head>
<title>Case - Notes</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" conte="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>

<body class="dialog" onLoad="init()">
<form name="notesForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>

<span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="50%" border="0" cellspacing="6" cellpadding="0">
    <tr>
        <td align="left" width="16%" nowrap>Type:&nbsp;
            <input:select bean="contractGroupNote" name="noteTypeCT" options="<%= noteTypeCTs.getOptions() %>"
                          attributesText="id='noteTypeCT'"/>
        </td>

        <td align="left" width="16%" nowrap>Sequence&nbsp;
           <input:text name="sequence"  bean="contractGroupNote"
                                        attributesText="id=\'sequence\'"
                                        size="3"/>
        </td>
        <td align="left" width="16%" nowrap>Qualifier&nbsp;
            <input:select bean="contractGroupNote" name="noteQualifierCT" options="<%= noteQualifierCTs.getOptions() %>"
                          attributesText="id='noteQualifierCT'"/>
        </td>
    </tr>
    <tr>
      <td align="left" colspan="3" nowrap>Notes:&nbsp;</td>
    </tr>
	<tr>
      <td align="left" nowrap colspan="3">
        <textarea name="note" rows="15" cols="80" onKeyUp='checkTextAreaLimit()' maxLength='1200'><%= note %></textarea>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Last Maintenance Date/Time
        <input type="text" name="maintDate" size="35" maxlength="35" value="<%= maintDate %>" disabled>
      </td>
      <td align="left" nowrap colspan="2">Last Maintenance Operator
           <input:text name="operator"  bean="contractGroupNote"
                                        attributesText="id=\'operator\'"
                                        size="5"/>
      </td>
    </tr>
  </table>

  <table width="100%" height="3%" border="0" cellspacing="0" cellpadding="0">
    <tr height="2%">
      <td nowrap align="left" colspan="4">
		<input type="button" value="   Add   " style="background-color:#DEDEDE" onClick="addNote()">
		<input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="saveNoteDetail()">
		<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelNotesDialog()">
		<input type="button" value="  Delete " style="background-color:#DEDEDE" onClick="deleteCurrentNote()">
	  </td>
	</tr>
  </table>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="CaseNoteTableModel"/>
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

 <input:hidden name="contractGroupNotePK" bean="contractGroupNote"/>

</form>
</body>
</html>
