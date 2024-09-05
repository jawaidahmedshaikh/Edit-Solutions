<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 fission.beans.*,
                 edit.common.*,
                 edit.common.vo.CodeTableVO,
                 fission.utility.Util,
                 edit.portal.common.session.UserSession" %>

<jsp:useBean id="quoteNotesSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="quoteMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    PageBean mainBean = quoteMainSessionBean.getPageBean("formBean");
    String companyStructureId = Util.initString(mainBean.getValue("companyStructureId"), "0");

    CodeTableVO[] noteTypes = codeTableWrapper.getCodeTableEntries("NOTETYPE", Long.parseLong(companyStructureId));
    CodeTableVO[] noteQualifiers = codeTableWrapper.getCodeTableEntries("NOTEQUALIFIER", Long.parseLong(companyStructureId));

    PageBean formBean = quoteNotesSessionBean.getPageBean("formBean");

	String noteReminderPK  = formBean.getValue("noteReminderPK");
    String segmentFK       = formBean.getValue("segmentFK");
	String noteTypeId      = formBean.getValue("noteTypeId");
    String sequence        = formBean.getValue("sequence");
    String noteQualifierId = formBean.getValue("noteQualifierId");
    String note            = formBean.getValue("note");
    String maintDate       = formBean.getValue("maintDate");
    String operator        = formBean.getValue("operator");
    String key             = Util.initString(formBean.getValue("key"), "");

    String rowToMatchBase  = key;
    long segmentPkey = 0;
    UserSession userSession = (UserSession) session.getAttribute("userSession");
    segmentPkey = userSession.getSegmentPK();
%>

<%!
    //Sort notes by sequence number
	private TreeMap sortNotesBySequenceNumber(SessionBean quoteNotesSessionBean)
    {
		Map noteBeans = quoteNotesSessionBean.getPageBeans();

		TreeMap sortedNotes = new TreeMap();

		Iterator enumer  = noteBeans.values().iterator();

		while (enumer.hasNext())
        {
			PageBean noteBean = (PageBean) enumer.next();

			String sequenceNumber = noteBean.getValue("sequence");
            if (sequenceNumber.length() == 1)
            {
                sequenceNumber = "0" + sequenceNumber;
            }

            sortedNotes.put(sequenceNumber, noteBean);
		}

		return sortedNotes;
	}
%>

<html>
<head>
<title>Notes</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;

	function init()
    {
		f = document.notesForm;
		f.noteTypeId.focus();

        var scrollToRow = document.getElementById("<%= rowToMatchBase %>");

        if (scrollToRow != null)
        {

            scrollToRow.scrollIntoView(false);
        }
	}

    function cancelNotesDialog()
    {
		sendTransactionAction("QuoteDetailTran", "cancelNotes", "contentIFrame");
    	window.close();
    }

	function saveNotesDialog()
    {
		sendTransactionAction("QuoteDetailTran", "saveNotesOnly", "contentIFrame");
		window.close();
	}

	function saveEnterNotesDialog()
    {
		sendTransactionAction("QuoteDetailTran", "saveNotes", "contentIFrame");
		window.close();
	}

	function selectRow()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key = trElement.id;

		f.key.value = key;

		sendTransactionAction("QuoteDetailTran", "showNotesDetailSummary", "_self");
	}

	function addNote()
    {
		clearForm();
	}

	function cancelCurrentNote()
    {
		clearForm();
	}

	function saveNoteToSummary()
    {
		sendTransactionAction("QuoteDetailTran", "saveNoteToSummary", "_self");
	}

	function deleteCurrentNote()
    {
		sendTransactionAction("QuoteDetailTran","deleteCurrentNote","_self")
	}

	function deleteCurrentNoteOnly()
    {
		sendTransactionAction("QuoteDetailTran","deleteCurrentNoteOnly","_self")
	}

	function clearForm()
    {
		sendTransactionAction("QuoteDetailTran","clearNotesForAddOrCancel","_self")
	}

</script>
</head>
<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name="notesForm" method="post" action="/PORTAL/servlet/RequestManager">
<span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">
  <table id="table1" width="100%" height="50%" border="0" cellspacing="6" cellpadding="0">
    <tr>
      <td align="left" nowrap>Type&nbsp;
	    <select name="noteTypeId">
          <option selected value="null"> Please Select </option>
          <%
              for(int i = 0; i < noteTypes.length; i++) {

                  String codeTablePK = noteTypes[i].getCodeTablePK() + "";
                  String codeDesc    = noteTypes[i].getCodeDesc();
                  String code        = noteTypes[i].getCode();

                  if (noteTypeId.equalsIgnoreCase(code))
                  {
                      out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
                  else
                  {
                     out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
              }
          %>
	    </select>
      </td>
      <td align="left" nowrap>Sequence&nbsp;
        <input type="text" name="sequence" size="3" maxlength="3" disabled value="<%= sequence %>">
      </td>
      <td align="left" nowrap>Qualifier&nbsp;
	    <select name="noteQualifierId">
          <option selected value="null"> Please Select </option>
          <%
              for(int i = 0; i < noteQualifiers.length; i++) {

                  String codeTablePK = noteQualifiers[i].getCodeTablePK() + "";
                  String codeDesc    = noteQualifiers[i].getCodeDesc();
                  String code        = noteQualifiers[i].getCode();

                  if (noteQualifierId.equalsIgnoreCase(code))
                  {
                      out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
                  else
                  {
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
        <textarea name="note" rows="15" cols="80" onKeyUp='checkTextAreaLimit()' maxLength='1200'><%= note %></textarea>
      </td>
    </tr>
    <tr>
      <td align="left" nowrap>Last Maintenance Date/Time
        <input type="text" name="maintDate" size="35" maxlength="35" value="<%= maintDate %>" disabled>
      </td>
      <td align="left" nowrap colspan="2">Last Maintenance Operator
        <input type="text" name="operator" size="15" maxlength="15" value="<%= operator %>" disabled>
      </td>
    </tr>
   </table>

   <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
     <tr>
	   <td nowrap align="left">
		 <input type="button" value="   Add   " style="background-color:#DEDEDE" onClick="addNote()">
		 <input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="saveNoteToSummary()">
		 <input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelCurrentNote()">
         <% if (segmentPkey == 0){%>
		 <input type="button" value="  Delete " style="background-color:#DEDEDE" onClick="deleteCurrentNote()">
         <%}else{%>
         <input type="button" value="  Delete " style="background-color:#DEDEDE" onClick="deleteCurrentNoteOnly()">
         <%}%>
 	   </td>
	 </tr>
   </table>

   <table class="summary" id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
	 <tr class="heading">
	   <th width="25%" align="left">Type</th>
	   <th width="25%" align="left">Sequence</th>
	   <th width="25%" align="left">Qualifier</th>
	   <th width="25%" align="left">Notes</th>
	 </tr>
   </table>
   <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:20%; top:0; left:0; background-color:#BBBBBB">
     <table class="summary" id="notesSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
  	  <%
  		  String rowToMatch = "";
  		  String trClass = "";
  		  boolean selected = false;

          Map sortedNotes = sortNotesBySequenceNumber(quoteNotesSessionBean);

          Iterator it = sortedNotes.values().iterator();

       	  while (it.hasNext())
          {
              PageBean noteBean = (PageBean) it.next();

              String currentBeanKey = (String) noteBean.getValue("key");
              if (!currentBeanKey.equals("formBean") && !currentBeanKey.equals(""))
              {
                  String iType 	    = noteBean.getValue("noteTypeId");
                  String iSequence  = noteBean.getValue("sequence");
                  String iQualifier = noteBean.getValue("noteQualifierId");
                  String iNote      = noteBean.getValue("note");
                  if (iNote.length() > 20)
                  {
                      iNote = iNote.substring(0, 20);
                  }

                  String iKey = noteBean.getValue("key");

                  // Store behind the scenes...

                  rowToMatch = iKey;

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
 	  <tr class="<%= trClass %>" isSelected="<%= selected %>" id="<%= iKey %>"
          onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow()">
		<td nowrap align="left" width="25%">
		  <%= iType %>
		</td>
		<td nowrap align="left" width="25%">
		  <%= iSequence %>
		</td>
		<td nowrap align="left" width="25%">
		  <%= iQualifier %>
		</td>
		<td nowrap align="left" width="25%">
		  <%= iNote %>
		</td>
	  </tr>
	  <%
                } // end if
          } // end for
 	  %>
   </table>
  </span>

  <table id="table3" width="100%" border="0" cellspacing="6" cellpadding="0">
    <tr>
      <td align="right" nowrap colspan="3">
        <% if (segmentPkey == 0){%>
        <input type="button" name="enter" value="Enter" onClick="saveEnterNotesDialog()">
        <%}else{%>
        <input type="button" name="SaveOnly" value="Save" onClick="saveNotesDialog()">
        <%}%>
        <input type="button" name="cancel" value="Cancel" onClick="cancelNotesDialog()">
      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">

 <input type="hidden" name="noteReminderPK" value="<%= noteReminderPK %>">
 <input type="hidden" name="segmentFK" value="<%= segmentPkey %>">
 <input type="hidden" name="key" value="<%= key %>">
 <input type="hidden" name="sequence" value="<%= sequence %>">

</form>
</body>
</html>
