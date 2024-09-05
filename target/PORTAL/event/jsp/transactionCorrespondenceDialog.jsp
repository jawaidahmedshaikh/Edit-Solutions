<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 fission.beans.*,
                 edit.common.*,
                 edit.common.vo.CodeTableVO,
                 edit.common.vo.TransactionCorrespondenceVO,
                 fission.utility.*" %>

<%
    // User selected items...
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] priorPostCTs = codeTableWrapper.getCodeTableEntries("PRIORPOST");
    CodeTableVO[] correspondenceTypeCTs = codeTableWrapper.getCodeTableEntries("CORRESPONDENCETYPE");
    CodeTableVO[] trxTypeQualifierCTs = codeTableWrapper.getCodeTableEntries("TRXTYPEQUALIFIER");

    // Summary items
    TransactionCorrespondenceVO[] uiTransactionCorrespondenceVOs = (TransactionCorrespondenceVO[]) request.getAttribute("uiTransactionCorrespondenceVOs");

    if (uiTransactionCorrespondenceVOs == null)
    {
        uiTransactionCorrespondenceVOs = new TransactionCorrespondenceVO[0];
    }

    String transactionCorrespondencePK = "";
    String correspondenceTypePK = "";
    String numberOfDays = "";
    String priorPostPK = "";
    String trxTypeQualifierPK = "";
    String includeHistoryIndicator = "";
    String startDate = "";
    String stopDate = "";

    //  Selected TransactionPriority that correspondence belongs to
    String transactionPriorityPK = (String) request.getAttribute("transactionPriorityPK");

    // Selected Transaction Correspondence
    TransactionCorrespondenceVO transactionCorrespondenceVO = (TransactionCorrespondenceVO) request.getAttribute("transactionCorrespondenceVO");

    if (transactionCorrespondenceVO != null)
    {
        transactionCorrespondencePK = transactionCorrespondenceVO.getTransactionCorrespondencePK() + "";
        numberOfDays                = transactionCorrespondenceVO.getNumberOfDays() + "";
        String correspondenceTypeCT = transactionCorrespondenceVO.getCorrespondenceTypeCT();
        String priorPostCT          = transactionCorrespondenceVO.getPriorPostCT();
        String trxTypeQualifierCT = Util.initString(transactionCorrespondenceVO.getTransactionTypeQualifierCT(), "");
        includeHistoryIndicator = transactionCorrespondenceVO.getIncludeHistoryIndicator();
        if (includeHistoryIndicator != null && includeHistoryIndicator.equalsIgnoreCase("Y"))
        {
            includeHistoryIndicator = "checked";
        }
        else
        {
            includeHistoryIndicator = "unchecked";
        }

         if (correspondenceTypeCT != null)
        {
            correspondenceTypePK = codeTableWrapper.getCodeTablePKByCodeTableNameAndCode("CORRESPONDENCETYPE", correspondenceTypeCT) + "";
        }

        if (priorPostCT != null)
        {
            priorPostPK = codeTableWrapper.getCodeTablePKByCodeTableNameAndCode("PRIORPOST", priorPostCT) + "";
        }
        
        if (trxTypeQualifierCT != null)
        {
            trxTypeQualifierPK = codeTableWrapper.getCodeTablePKByCodeTableNameAndCode("TRXTYPEQUALIFIER", trxTypeQualifierCT) + "";
        }

        startDate = Util.initString(transactionCorrespondenceVO.getStartDate(), "");
        if (!startDate.equals(""))
        {
            startDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(startDate);
        }
        
        stopDate  = Util.initString(transactionCorrespondenceVO.getStopDate(), "");
        if (!stopDate.equals(""))
        {
            stopDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(stopDate);
        }
    }

    correspondenceTypePK        = Util.initString(correspondenceTypePK, "");
    numberOfDays                = Util.initString(numberOfDays, "");
    priorPostPK                 = Util.initString(priorPostPK, "");
    transactionCorrespondencePK = Util.initString(transactionCorrespondencePK, "");
    transactionPriorityPK       = Util.initString(transactionPriorityPK, "");
    trxTypeQualifierPK = Util.initString(trxTypeQualifierPK, "");

    String message = (String) request.getAttribute("message");
    message = Util.initString(message, "");

    String pageMode = (String) request.getAttribute("pageMode");
    pageMode = Util.initString(pageMode, "VIEW");
%>


<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>

<head>
<title>Transaction Correspondence</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">




<script language="Javascript1.2">

	var f = null;

    var previousRow = null;
    var tdElement = null;
    var currentRow = null;

    var message = "<%= message %>";

    var pageMode = "<%= pageMode %>";

    function init()
    {
        f = document.theForm;

        if (message.length > 0)
        {
            alert(message);
        }

        var scrollToRow = document.getElementById("<%= transactionCorrespondencePK %>");
        if (scrollToRow != null)
        {
            scrollToRow.scrollIntoView(false);
        }

		window.resizeTo(getPreferredWidth(), getPreferredHeight());

    }

	function getPreferredWidth() {

		return .75 * screen.width;
	}

	function getPreferredHeight() {

		return .55 * screen.height;
	}

    function closeTransactionCorrespondenceDialog()
    {

        <%-- Check to see if there are any correspondences  --%>
        var corrTable = document.getElementById("table2");

        var tableRows = corrTable.rows;

        var correspondenceExists = false;

        if (tableRows.length > 1)   // 1st row is the header
        {
            for (var i = 1; i < tableRows.length; i++)
            {
                if (tableRows[i].id >= 0)
                {
                    correspondenceExists = true;

                    break;
                }
            }
        }

        <%-- If there are correspondences, let the calling page know --%>
        if (correspondenceExists)
        {
            opener.setCorrespondenceCheck(true);
        }
    }

    function closeWindow()
    {
    	window.close();
    }

    function showDetail()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        f.transactionCorrespondencePK.value = currentRow.transactionCorrespondencePK;

        sendTransactionAction("EventAdminTran", "showTransactionCorrespondenceDetails", "_self");
    }

    function selectCorrespondenceType(selector)
    {
        if (selector.selectedIndex >= 1) // Anything other than "Please Select"
        {
            f.correspondenceTypePK.value = selector.options[selector.selectedIndex].value;
        }
    }

    function selectPriorPost(selector)
    {
        if (selector.selectedIndex >= 1) // Anything other than "Please Select"
        {
            f.priorPostPK.value = selector.options[selector.selectedIndex].value;
        }
    }

    function selectTrxTypeQualifier(selector)
    {
        if (selector.selectedIndex >= 1) // Anything other than "Please Select"
        {
            f.trxTypeQualifierPK.value = selector.options[selector.selectedIndex].value;
        }
    }

    function addTransactionCorrespondence()
    {
        sendTransactionAction("EventAdminTran", "addTransactionCorrespondence", "_self");
    }

    function saveTransactionCorrespondence()
    {
        if (f.correspondenceTypeCT.value == "-1")    // -1 = "Please Select"
        {
            alert("Correspondence Type Required");

            return;
        }
        if (f.priorPostCT.value == "-1")             // -1 = "Please Select"
        {
            alert("Prior/Post Required");

            return;
        }

        if (f.includeHistoryIndStatus.checked == true)
        {
            f.includeHistoryIndicator.value = "Y";
        }
        else
        {
            f.includeHistoryIndicator.value = "N";
        }

        if (f.startDate.value != "")
        {
           f.startDate.value = convertMMDDYYYYToYYYYMMDD(f.startDate.value);
        }

        if (f.stopDate.value != "")
        {
           f.stopDate.value = convertMMDDYYYYToYYYYMMDD(f.stopDate.value);
        }

        sendTransactionAction("EventAdminTran", "saveTransactionCorrespondence", "_self");
    }

    function cancelTransactionCorrespondenceEdits()
    {
        sendTransactionAction("EventAdminTran", "cancelTransactionCorrespondenceEdits", "_self");
    }

    function deleteTransactionCorrespondence()
    {
        sendTransactionAction("EventAdminTran", "deleteTransactionCorrespondence", "_self");
    }


    function highlightRow()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        currentRow.style.backgroundColor = "#FFFFCC";
    }

    function unhighlightRow()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (currentRow.isSelected != "true")
        {
            if (className == "assocEntry")
            {
                currentRow.style.backgroundColor = "#00BB00";
            }
            else if (className == "highLighted")
            {
                currentRow.style.backgroundColor = "#FFFFCC";
            }
            else
            {
                currentRow.style.backgroundColor = "#BBBBBB";
            }
        }
    }

    function selectDeselectRow()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        var containingTable = currentRow.parentElement;

        for (var i = 0; i < containingTable.rows.length; i++)
        {
            containingTable.rows[i].style.backgroundColor = "#BBBBBB";
            containingTable.rows[i].isSelected = "false";
        }

        // Deactivate the selection in the "other" table
        var otherTable = null;
        if (containingTable.parentElement.id == "scriptsTable")
        {
            otherTable = document.all.tablesTable;
        }
        else
        {
            otherTable = document.all.scriptsTable;
        }

        for (var i = 0; i < otherTable.rows.length; i++)
        {
            otherTable.rows[i].style.backgroundColor = "#BBBBBB";
            otherTable.rows[i].isSelected = "false";
        }

        currentRow.style.backgroundColor = "#FFFFCC";
        currentRow.isSelected = "true";
    }

    function sendTransactionAction(transaction, action, target) {

    	f.transaction.value=transaction;
    	f.action.value=action;

    	f.target = target;

    	f.submit();
    }

</script>

<!-- ****** STYLE CODE ***** //-->

<style>

	.highLighted {

		background-color: #FFFFCC;
	}

	.dummy {
	}

	table {

		background-color: #BBBBBB;
	}
</style>


</head>
<body bgcolor="#BBBBBB" onLoad="init()">

<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

    <span id="mainContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:40%; top:0; left:0; z-index:0; overflow:visible">
        <table id="table1" width="100%" height="100%" border="0" cellspacing="6" cellpadding="0" bgcolor="#BBBBBB">
            <tr>
              <td align="left" nowrap>Correspondence Type:&nbsp;
                  <select name="correspondenceTypeCT" onChange="selectCorrespondenceType(this)">
                      <%
                          out.println("<option name=\"id\" value=-1>Please Select</option>");

                          for(int i = 0; i < correspondenceTypeCTs.length; i++) {

                              String codeTablePK = correspondenceTypeCTs[i].getCodeTablePK() + "";
                              String codeDesc    = correspondenceTypeCTs[i].getCodeDesc();

                             if (codeTablePK.equals(correspondenceTypePK)) {

                                 out.println("<option selected name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                             }
                             else  {

                                 out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                             }
                          }
                     %>
                  </select>
              </td>
               <td align="left" nowrap>Start Date:&nbsp;
                 <input type="text" name="startDate" size="10" maxlength="10" value="<%= startDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
                 <a href="javascript:show_calendar('f.startDate', f.startDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
               </td>
            </tr>
            <tr>
              <td align="left" nowrap>Number of Days:&nbsp;
                <input type="text" name="numberOfDays" size="10" maxlength="10" value="<%= numberOfDays %>">
              </td>
              <td align="left" nowrap>Stop Date:&nbsp&nbsp;
                <input type="text" name="stopDate" size="10" maxlength="10" value="<%= stopDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
                <a href="javascript:show_calendar('f.stopDate', f.stopDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
              </td>
            </tr>
            <tr>
              <td align="left" nowrap>Prior/Post:&nbsp;
                  <select name="priorPostCT" onChange="selectPriorPost(this)">
                      <%
                          out.println("<option name=\"id\" value=-1>Please Select</option>");

                          for(int i = 0; i < priorPostCTs.length; i++)
                          {
                              String codeTablePK = priorPostCTs[i].getCodeTablePK() + "";
                              String codeDesc    = priorPostCTs[i].getCodeDesc();

                              if (codeTablePK.equals(priorPostPK))
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
              <td align="left" nowrap>Transaction Type Qualifier:&nbsp;
                  <select name="typeCT" onChange="selectTrxTypeQualifier(this)">
                      <%
                          out.println("<option name=\"id\" value=-1>Please Select</option>");

                          for (int i = 0; i < trxTypeQualifierCTs.length; i++)
                          {
                              String codeTablePK = trxTypeQualifierCTs[i].getCodeTablePK() + "";
                              String codeDesc    = trxTypeQualifierCTs[i].getCodeDesc();

                              if (codeTablePK.equals(trxTypeQualifierPK))
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
              <td nowrap>Include History:&nbsp;
                <input type="checkbox" name="includeHistoryIndStatus" <%= includeHistoryIndicator %>>
              </td>
            </tr>
        </table>
    </span>


    <span id="buttonContent" style="position:relative; border-style:solid;  border-width:1; width:100%; height:2%; top:0; left:0; z-index:0; overflow:visible">

        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr height="5%">
                <td nowrap align="left" colspan="3">
                    <input type="button" id="btnAdd"    name="add"    value=" Add  " onClick="addTransactionCorrespondence()">
                    <input type="button" id="btnSave"   name="save"   value=" Save " onClick="saveTransactionCorrespondence()">
                    <input type="button" id="btnCancel" name="cancel" value="Cancel" onClick="cancelTransactionCorrespondenceEdits()">
                    <input type="button" id="btnDelete" name="delete" value="Delete" onClick="deleteTransactionCorrespondence()">
                </td>
            </tr>
       </table>

    </span>

    <span id="scrollableContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:40%; top:0; left:0; z-index:0; overflow:scroll">
        <table id="table2" width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#BBBBBB">

            <tr bgcolor="#30548E">
              <td align="left" nowrap><font color= "#FFFFFF">Correspondence Type</font></td>
              <td align="left" nowrap><font color= "#FFFFFF"># Days</font></td>
              <td align="left" nowrap><font color= "#FFFFFF">Prior/Post</font></td>
              <td align="left" nowrap><font color= "#FFFFFF">Trx Type Qualifier</font></td>
            </tr>

  	        <%
                String currentPK = "";
                String sCorrespondenceType  = "";
                String sNumberOfDays = "";
                String sPriorPost = "";
                String sTrxTypeQualifier = "";

                if (uiTransactionCorrespondenceVOs != null)
                {
                    for (int i = 0; i < uiTransactionCorrespondenceVOs.length; i++)
                    {
                        currentPK = uiTransactionCorrespondenceVOs[i].getTransactionCorrespondencePK() + "";

                        String className = null;

                        if (currentPK.equals(transactionCorrespondencePK))
                        {
                            className = "highLighted";
                        }
                        else
                        {
                            className = "mainEntry";
                        }

                        sCorrespondenceType = uiTransactionCorrespondenceVOs[i].getCorrespondenceTypeCT();
                        sNumberOfDays       = uiTransactionCorrespondenceVOs[i].getNumberOfDays() + "";
                        sPriorPost          = uiTransactionCorrespondenceVOs[i].getPriorPostCT();
                        sTrxTypeQualifier   = Util.initString(uiTransactionCorrespondenceVOs[i].getTransactionTypeQualifierCT(), "");

            %>
            <tr class="<%= className %>" id="<%= currentPK %>" isSelected="false"
                transactionCorrespondencePK="<%= currentPK %>" onMouseOver="highlightRow()"
                onMouseOut="unhighlightRow()" onClick="showDetail()">
              <td align="left" nowrap>
                <%= sCorrespondenceType %>
              </td>
              <td align="left" nowrap>
                <%= sNumberOfDays %>
              </td>
              <td align="left" nowrap>
                <%= sPriorPost %>
              </td>
              <td align="left" nowrap>
                <%= sTrxTypeQualifier %>
              </td>
            </tr>
            <%
                    } // end for
                }// end if
		    %>
        </table>
    </span>

    <table id="table3" width="100%" border="0" cellspacing="6" cellpadding="0" bgcolor="#BBBBBB">
        <tr>
          <td align="right" nowrap colspan="3">
            <input type="button" id="btnClose" name="close" value="Close" onClick="closeWindow()">
          </td>
        </tr>
    </table>


<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">

<input type="hidden" name="transactionCorrespondencePK" value="<%= transactionCorrespondencePK %>">
<input type="hidden" name="transactionPriorityPK" value="<%= transactionPriorityPK %>">
<input type="hidden" name="correspondenceTypePK" value="<%= correspondenceTypePK %>">
<input type="hidden" name="priorPostPK" value="<%= priorPostPK %>">
<input type="hidden" name="trxTypeQualifierPK" value="<%= trxTypeQualifierPK %>">
<input type="hidden" name="includeHistoryIndicator" value="">


</form>
</body>
</html>
