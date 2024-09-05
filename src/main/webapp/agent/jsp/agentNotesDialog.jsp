<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 fission.beans.*,
                 edit.common.*,
                 edit.common.vo.CodeTableVO,
                 edit.common.vo.AgentVO,
                 edit.common.vo.AgentNoteVO,
                 fission.utility.Util" %>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] noteTypes = codeTableWrapper.getCodeTableEntries("NOTETYPE");
    CodeTableVO[] noteQualifiers = codeTableWrapper.getCodeTableEntries("NOTEQUALIFIER");

    String agentNotePK = (String) request.getAttribute("agentNotePK");
    String selectedNoteType = (String) request.getAttribute("selectedNoteType");
    String newAgentNote = (String) request.getAttribute("NewAgentNote");

    if (agentNotePK == null) {

        agentNotePK = "0";
    }
    if (selectedNoteType == null) {

        selectedNoteType = "";
    }
    if (newAgentNote == null) {

        newAgentNote = "false";
    }

    AgentVO agentVO = (AgentVO) session.getAttribute("agentVO");
    AgentNoteVO[] agentNoteVOs = null;
    long agentPK = 0;

    if (agentVO != null) {
        agentPK = agentVO.getAgentPK();
        agentNoteVOs = agentVO.getAgentNoteVO();
    }

    String noteType = "";
    String sequence = "";
    String noteQualifier = "";
    String note = "";
    String operator = "";
    String maintDate = "";
    String notePK = "";

    if ( !newAgentNote.trim().equals("true") ) {

        if (agentNoteVOs != null) {

            for (int n = 0; n < agentNoteVOs.length; n++) {

                notePK = agentNoteVOs[n].getAgentNotePK() + "";

                if (agentNotePK.equals(notePK) ||
                    (agentNotePK.equals("0") &&
                    selectedNoteType.equals(agentNoteVOs[n].getNoteTypeCT()))) {

                    noteType = Util.initString(agentNoteVOs[n].getNoteTypeCT(), "");
                    sequence = agentNoteVOs[n].getSequence() + "";
                    noteQualifier = Util.initString(agentNoteVOs[n].getNoteQualifierCT(), "");
                    note = agentNoteVOs[n].getNote();

                    operator = agentNoteVOs[n].getOperator();
                    maintDate = agentNoteVOs[n].getMaintDateTime();
                    break;
                }
            }
        }
    }
    else
    {
        sequence = Util.initString((String) request.getAttribute("NewSeqenceNumber"), "");
    }

    String rowToMatchBase = agentNotePK + noteType;
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;
    var agentNotePK = "<%= agentNotePK %>";
    var agentPK = 0;

	function init()
    {
		f = document.notesForm;

		f.noteTypeId.focus();

        var scrollToRow = document.getElementById("<%= agentNotePK %>");

        if (scrollToRow != null)
        {

            scrollToRow.scrollIntoView(false);
        }

<%--        var className = currentRow.className;--%>

<%--        if (currentRow.isSelected != "true") {--%>
<%----%>
<%--            if (className == "highLighted") {--%>
<%----%>
<%--                currentRow.style.backgroundColor = colorHighlighted;--%>
<%--            }--%>
<%----%>
<%--            else {--%>
<%----%>
<%--                currentRow.style.backgroundColor = colorMainEntry;--%>
<%--            }--%>
<%--        }--%>
<%--        else {--%>
<%----%>
<%--            currentRow.style.backgroundColor = colorHighlighted;--%>
<%--        }--%>
    }

	function closeNotesDialog()
    {
        sendTransactionAction("AgentDetailTran", "closeNotesDialog", "contentIFrame");
		window.close();
	}

	function selectRow()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key = trElement.id;
        var type = trElement.noteType;

		f.agentNotePK.value = key;
        f.selectedNoteType.value = type;

		sendTransactionAction("AgentDetailTran", "showNotesDetailSummary", "_self");
	}

	function addNote()
    {
		clearForm();
        sendTransactionAction("AgentDetailTran", "addNewAgentNote", "_self");
	}

	function cancelCurrentNote()
    {
		clearForm();
	}

	function saveNoteToSummary()
    {
		sendTransactionAction("AgentDetailTran", "saveNoteToSummary", "_self");
	}

	function deleteCurrentNote()
    {
        if (agentNotePK == "0")
        {
            alert("Please Select Note For Deletion");
        }
        else
        {
            sendTransactionAction("AgentDetailTran","deleteCurrentNote","_self")
        }
	}

    function saveNotesDialog()
    {
        sendTransactionAction("AgentDetailTran", "saveNotesDialog", "contentIFrame");
		window.close();
    }

    function cancelNotesDialog()
    {
        sendTransactionAction("AgentDetailTran", "cancelNotesDialog", "contentIFrame");
		window.close();
    }

	function clearForm()
    {
   	    f.noteTypeId.selectedIndex = 0;
        // note: the [index] has to be used bcos there exists two sequence
        // one is disabled text and other is hidden parameter
		f.sequence[0].value         = "";
		f.noteQualifierId.selectedIndex  = 0;
        f.note.value             = "";
        f.maintDate.value        = "";
        f.operator.value         = "";
        f.agentNotePK.value      = "";

		f.noteTypeId.focus();
	}

</script>
<head>
<title>Agent Notes</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" conte="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>
<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name="notesForm" method="post" action="/PORTAL/servlet/RequestManager">
<span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table id="table1" width="100%" height="50%" border="0" cellspacing="6" cellpadding="0">
    <tr>
      <td align="left" nowrap>Type&nbsp;
        <select name="noteTypeId">
          <option selected> Please Select </option>
          <%

              for(int i = 0; i < noteTypes.length; i++) {

                  String codeTablePK = noteTypes[i].getCodeTablePK() + "";
                  String codeDesc    = noteTypes[i].getCodeDesc();
                  String code        = noteTypes[i].getCode();

                 if (noteType.equalsIgnoreCase(code)) {

                     out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
              }

          %>
	    </select>
      </td>
      <td align="left" nowrap>Sequence&nbsp;
        <input type="text" name="sequence" size="3" maxlength="3" value="<%= sequence %>" disabled>
      </td>
      <td align="left" nowrap>Qualifier&nbsp;
	    <select name="noteQualifierId">
          <option selected> Please Select </option>
          <%

              for(int i = 0; i < noteQualifiers.length; i++) {

                  String codeTablePK = noteQualifiers[i].getCodeTablePK() + "";
                  String codeDesc    = noteQualifiers[i].getCodeDesc();
                  String code        = noteQualifiers[i].getCode();

                 if (noteQualifier.equalsIgnoreCase(code)) {

                     out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
              }

          %>
	    </select>
      </td>
    </tr>

    <tr>
      <td align="left" colspan="3" nowrap>Notes:&nbsp;</td>
    </tr>
	<tr>
      <td align="left" nowrap colspan="3">
        <textarea name="note" rows="15" cols="80"><%= note %></textarea>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Last Maintenance Date/Time
        <input type="text" name="maintDate" size="25" maxlength="35" value="<%= maintDate %>" disabled>
      </td>
      <td align="left" nowrap colspan="2">Last Maintenance Operator
        <input type="text" name="operator" size="10" maxlength="10" value="<%= operator %>" disabled>
      </td>
    </tr>
  </table>

  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
	<tr height="2%">
	  <td nowrap align="left" colspan="4">
	    <input type="button" value="   Add   " style="background-color:#DEDEDE" onClick="addNote()">
		<input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="saveNoteToSummary()">
		<input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelCurrentNote()">
		<input type="button" value="  Delete " style="background-color:#DEDEDE" onClick="deleteCurrentNote()">
	  </td>
	</tr>
  </table>
  <table class="summary" id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th align="left" width="25%" nowrap>Type</th>
      <th align="left" width="25%" nowrap>Sequence</th>
      <th align="left" width="25%" nowrap>Qualifier</th>
      <th align="left" width="25%" nowrap>Notes</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:20%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="notesSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
  	    String rowToMatch = "";
  		String trClass = "";
  		boolean selected = false;

        if (agentNoteVOs != null)
        {
            for (int a = 0; a < agentNoteVOs.length; a++)
            {
                if (!agentNoteVOs[a].getVoShouldBeDeleted())
                {
                    String sAgentNotePK = agentNoteVOs[a].getAgentNotePK() + "";
                    String sNoteType = Util.initString(agentNoteVOs[a].getNoteTypeCT(), "");
                    String sSequence = agentNoteVOs[a].getSequence() + "";
                    if (sSequence.equals("0"))
                    {
                        sSequence = "";
                    }
                    String sNoteQualifier = Util.initString(agentNoteVOs[a].getNoteQualifierCT(), "");
                    String sNote = Util.initString(agentNoteVOs[a].getNote(), "");
                    int sNoteLength = sNote.length();
                    String sNote1 = "";
                    if (sNoteLength > 20)
                    {
                        sNote1 = sNote.substring(0, 20);
                    }
                    else
                    {
                        sNote1 = sNote;
                    }

                    String sKey = sAgentNotePK + sNoteType;

                    rowToMatch = sKey;

                    if (rowToMatch.equals(rowToMatchBase))
                    {
                        trClass = "highlighted";
                        selected = true;
                    }
                    else
                    {
                        trClass = "default";
                        selected = false;
                    }
      %>
      <tr class="<%= trClass %>" isSelected="<%= selected %>" id="<%= sAgentNotePK %>" noteType="<%= sNoteType %>"
          onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow()">
	    <td nowrap width="25%" align="left">
		  <%= sNoteType %>
		</td>
	    <td nowrap width="25%" align="left">
		  <%= sSequence %>
	    </td>
	    <td nowrap width="25%" align="left">
		  <%= sNoteQualifier %>
		</td>
		<td nowrap width="25%" align="left">
		  <%= sNote1 %>
        </td>
      </tr>
	  <%
                } //end if
            } // end for
        } // end if
      %>
    </table>
  </span>

  <table id="table3" width="100%" border="0" cellspacing="6" cellpadding="0">
    <tr>
     <td align="right" nowrap colspan="3">
        <% if (agentPK > 0) { %>
            <input type="button" name="enter" value="Save" onClick="saveNotesDialog()">
        <% } else  {%>
            <input type="button" name="enter" value="Enter" onClick="saveNotesDialog()">
        <% } %>
        <input type="button" name="cancel" value="Cancel" onClick="cancelNotesDialog()">
     </td>
   </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">
 <input type="hidden" name="agentNotePK" value="<%= agentNotePK %>">
 <input type="hidden" name="selectedNoteType" value="">
 <input type="hidden" name="sequence" value="<%= sequence %>">

</form>
</body>
</html>
