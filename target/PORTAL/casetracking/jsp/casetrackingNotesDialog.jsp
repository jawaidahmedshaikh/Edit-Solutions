<%@ page import="edit.common.CodeTableWrapper,
                 edit.common.vo.CodeTableVO,
                 casetracking.CasetrackingNote,
                 edit.common.EDITDateTime,
                 fission.utility.Util"%>
<!--
 * User: sprasad
 * Date: Mar 18, 2005
 * Time: 4:17:04 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] noteTypes = codeTableWrapper.getCodeTableEntries("NOTETYPE");
    CodeTableVO[] noteQualifiers = codeTableWrapper.getCodeTableEntries("NOTEQUALIFIER");

    CasetrackingNote activeCasetrackingNote = (CasetrackingNote) request.getAttribute("activeCasetrackingNote");

    long activeCasetrackingNotePK   = Util.initLong(activeCasetrackingNote, "casetrackingNotePK", 0L);
    int activeSequence              = Util.initInt(activeCasetrackingNote, "sequence", 0);
    String activeNoteTypeCT         = (String) Util.initObject(activeCasetrackingNote, "noteTypeCT", "");
    String activeNoteQualifierCT    = (String) Util.initObject(activeCasetrackingNote, "noteQualifierCT", "");
    String activeNote               = (String) Util.initObject(activeCasetrackingNote, "note", "");
    String activeOperator           = (String) Util.initObject(activeCasetrackingNote, "operator", "");
    EDITDateTime activeMaintDateTime = (EDITDateTime) Util.initObject(activeCasetrackingNote, "maintDateTime", null);
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Claims - Notes</title>
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

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        var scrollToRow = document.getElementById("<%= activeCasetrackingNotePK %>");

        if (scrollToRow != null)
        {

            scrollToRow.scrollIntoView(false);
        }

        checkForResponseMessage();

        // Initialize scroll tables
        initScrollTable(document.getElementById("CasetrackingNoteTableModelScrollTable"));
    }

    function addNote()
    {
        sendTransactionAction("CaseTrackingTran", "addNote", "_self");
    }

    function saveOrUpdateNote()
    {
        if (f.sequenceNumber.value == 0)
        {
            alert('Please Select Add button or Note');
            return;
        }

        if(validateForm(f))
        {
            sendTransactionAction("CaseTrackingTran", "saveOrUpdateNote", "_self");
        }
    }

    function cancelNote()
    {
        sendTransactionAction("CaseTrackingTran", "cancelNote", "_self");
    }

    function deleteNote()
    {
        if (f.casetrackingNotePK.value == "0")
        {
            alert('Please Select the Note to Delete');
            return;
        }

        sendTransactionAction("CaseTrackingTran", "deleteNote", "_self");
    }

    function onTableRowSingleClick(tableId)
    {
        sendTransactionAction("CaseTrackingTran", "showNoteDetail", "_self");
    }

    function closeAndShowClientPage()
    {
        sendTransactionAction("CaseTrackingTran", "showCasetrackingClient", "main");

        window.close();
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>
    <tr>
      <td>
        <table width="75%" cellspacing="0" cellpadding="2">
          <tr>
            <td align="left" nowrap>
                <span class="requiredField">*</span>
                Type:&nbsp;
            </td>
            <td align="left" nowrap>
              <select name="noteTypeCT" REQUIRED>
                <option value="null">Please Select</option>
                <%
                  for(int i = 0; i < noteTypes.length; i++) {

                      String codeDesc    = noteTypes[i].getCodeDesc();
                      String code        = noteTypes[i].getCode();

                     if (activeNoteTypeCT.equalsIgnoreCase(code)) {

                         out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                     }
                     else  {

                         out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                     }
                  }
                %>
              </select>
            </td>
            <td align="right" nowrap>
                Sequence:&nbsp;
            </td>
            <td align="left" nowrap>
                <input disabled type="text" name="sequenceNumber" size="2" value="<%= activeSequence %>">
            </td>
            <td align="right" nowrap>
                Qualifier:&nbsp;
            </td>
            <td align="left" nowrap>
              <select name="noteQualifierCT">
                <option value="null">Please Select</option>
                <%
                  for(int i = 0; i < noteQualifiers.length; i++) {

                      String codeDesc    = noteQualifiers[i].getCodeDesc();
                      String code        = noteQualifiers[i].getCode();

                     if (activeNoteQualifierCT.equalsIgnoreCase(code)) {

                         out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                     }
                     else  {

                         out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                     }
                  }
              %>
              </select>
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td nowrap>
        <span class="requiredField">*</span>
        Notes:
      </td>
    </tr>
    <tr>
        <td align="left" nowrap>
            <textarea name="note" rows="15" cols="80" REQUIRED><%= activeNote %></textarea>
        </td>
    </tr>
    <tr>
      <td>
        <table width="75%" cellspacing="0" cellpadding="2">
          <tr>
            <td align="left" nowrap>
                Last Maintenance Date/Time:&nbsp;
            </td>
            <td align="left" nowrap>
                <input disabled type="text" name="maintDateTime" size="35"
                    value="<%= activeMaintDateTime != null? activeMaintDateTime.toString(): "" %>">
            </td>
            <td align="right" nowrap>
                &nbsp;
            </td>
            <td align="left" nowrap>
                &nbsp;
            </td>
            <td align="right" nowrap>
                Last Maintenance Operator:
            </td>
            <td align="left" nowrap>
                <input disabled type="text" name="operator" size="15" value="<%= activeOperator %>">
            </td>
          </tr>
        </table>
      </td>
    </tr>
<%--    END Form Content --%>

    <tr class="filler">
        <td>
            &nbsp; <!--Filler Row -->
        </td>
    </tr>
</table>
<%-- ****************************** END Form Data ****************************** --%>

<br>

<%--Buttons--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            <input type="button" value=" Add  " onClick="addNote()">
            <input type="button" value=" Save " onClick="saveOrUpdateNote()">
            <input type="button" value="Cancel" onClick="cancelNote()">
            <input type="button" value="Delete" onClick="deleteNote()">
        </td>
        <td width="33%">
            &nbsp;
        </td>
        <td align="right" width="33%">
            &nbsp;
        </td>
    </tr>
</table>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="CasetrackingNoteTableModel"/>
    <jsp:param name="tableHeight" value="30"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>

<%-- Additional Buttons --%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" width="33%">
            &nbsp;
        </td>
        <td width="33%">
            &nbsp;
        </td>
        <td align="right" width="33%">
              <input type="button" value=" Close " onClick="closeAndShowClientPage()">
        </td>
    </tr>
</table>
<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="casetrackingNotePK" value="<%= activeCasetrackingNotePK %>">
<input type="hidden" name="sequence" value="<%= activeSequence %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>