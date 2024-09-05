<%@ page import="edit.common.vo.*,
                 edit.common.CodeTableWrapper,
                 fission.utility.Util"%>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%
    OnlineReportVO    selectedOnlineReportVO    = (OnlineReportVO) request.getAttribute("selectedOnlineReportVO");
    OnlineReportVO[] onlineReportVOs = (OnlineReportVO[]) request.getAttribute("onlineReportVOs");

     // User selected items...
    CodeTableVO[] reportCategoryCTVOs = CodeTableWrapper.getSingleton().getCodeTableEntries("REPORTCATEGORY");

    String fileName = "";
    String description = "";
    String reportCategoryCT = "";
    String selectedOnlineReportPK = "";
    String message = (String) request.getAttribute("message");

    if (selectedOnlineReportVO != null)
    {
        selectedOnlineReportPK = selectedOnlineReportVO.getOnlineReportPK() + "";
        fileName = Util.initString(selectedOnlineReportVO.getFileName(), "");
        description = Util.initString(selectedOnlineReportVO.getDescription(), "");
        reportCategoryCT = Util.initString(selectedOnlineReportVO.getReportCategoryCT(), "");
    }

    String pageMode = (String) request.getAttribute("pageMode");
 %>


<html>
<head>
<title>EDITSOLUTIONS - Online Report Summary</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">


<!-- ***** JAVASCRIPT *****  -->

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

    var f           = null;
    var previousRow = null;
    var tdElement = null;
    var currentRow = null;
    var message = "<%= message %>";
    var pageMode = "<%= pageMode %>";

    function init()
    {
	    f = document.theForm;
        var scrollToRowRT = document.getElementById("<%= selectedOnlineReportPK %>");

        if (scrollToRowRT != null)
        {
            scrollToRowRT.scrollIntoView(true);
        }

        setButtonState();

        f.fileName.disabled = true;

        alertMessage();
    }

    function alertMessage()
    {
        if (message != "null")
        {
            alert(message);
        }
    }

    function setButtonState()
    {
        if (pageMode == "defaultMode")
        {
            document.getElementById("Add").disabled = false;
            document.getElementById("Save").disabled = true;
            document.getElementById("Cancel").disabled = true;
            document.getElementById("Delete").disabled = true;
        }

        else if (pageMode == "addMode")
        {
            document.getElementById("Add").disabled = true;
            document.getElementById("Save").disabled = false;
            document.getElementById("Cancel").disabled = false;
            document.getElementById("Delete").disabled = true;
            document.getElementById("Code").disabled = false;
            document.getElementById("Code").focus();
        }

        else if (pageMode == "editMode")
        {
            document.getElementById("Add").disabled = false;
            document.getElementById("Save").disabled = false;
            document.getElementById("Cancel").disabled = false;
            document.getElementById("Delete").disabled = false;
        }
    }

    function highlightRow()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        currentRow.style.backgroundColor = "#FFFFCC";
    }

    function unhighlightRow()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

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

    function showOnlineReportSummary()
    {
    	var tdElement = window.event.srcElement;
    	var currentRow = tdElement.parentElement;

        f.selectedOnlineReportPK.value = currentRow.onlineReportPK;

        sendTransactionAction("CodeTableTran", "showOnlineReportSummary", "main");
    }

    function saveOnlineReport()
    {
        if (selectElementIsEmpty(f.currentFileName))
        {
            alert("Please Select A File Name");
        }
        else if (textElementIsEmpty(f.description))
        {
            alert("Please Enter The Description");
        }
        else if (selectElementIsEmpty(f.reportCategoryCT))
        {
            alert("Please Select A Report Category");
        }
        else
        {
            sendTransactionAction("CodeTableTran", "saveOnlineReport", "main");
        }
    }

    function addOnlineReport()
    {
        sendTransactionAction("CodeTableTran", "addOnlineReport", "main");
    }

    function cancelOnlineReport()
    {
        sendTransactionAction("CodeTableTran", "cancelOnlineReport", "main");
    }

    function deleteOnlineReport()
    {
        sendTransactionAction("CodeTableTran", "deleteOnlineReport", "main");
    }

    function showRelation() {

        sendTransactionAction("CodeTableTran", "showOnlineReportRelations", "main");
    }

    function checkForNew()
    {
        var select = window.event.srcElement;

        var selectName = select.name;
        var rootSelectName = selectName.substring(7, selectName.length);
        var textField = document.getElementById(rootSelectName);

        if (select.selectedIndex == 0) // If "Please Select"
        {
            textField.value = "";
            textField.disabled = true;
        }
        else if (select.selectedIndex == 1) // if <new>
        {
            textField.value = "";
            textField.disabled = false;
            textField.focus();
        }
        else if (select.selectedIndex > 1) // Anything other than "Please Select"
        {
            textField.value = select.options[select.selectedIndex].text;
            textField.disabled = true;
        }
    }

    function sendTransactionAction(transaction, action, target)
    {
		f.transaction.value = transaction;
    	f.action.value = action;

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
</style>

</head>

<body class="mainTheme" onLoad="init()">

<form  name="theForm" method="post" action="/PORTAL/servlet/RequestManager">
    <table class="formData" width="100%" border="0"  cellspacing="6" cellpadding="0">
        <tr>
          <td nowrap align="right">File Name:&nbsp;</td>
          <td nowrap align="left">
            <select name="currentFileName" onChange="checkForNew()">
            <option value="0">Please Select</option>
            <option value="1">&lt;new&gt;</option>
              <%
                  String currentFileName = "";
                  for (int i = 0; i < onlineReportVOs.length; i++)
                  {
                      currentFileName = onlineReportVOs[i].getFileName();

                      if (currentFileName.equals(fileName))
                      {
                          out.println("<option selected value=\"" + currentFileName + "\">" + currentFileName + "</option>");
                      }
                      else
                      {
                          out.println("<option value=\"" + currentFileName + "\">" + currentFileName + "</option>");
                      }
                  }

              %>
            </select>
          </td>
          <td nowrap align="left">
            <input type="text" size="100" maxlength="100" name="fileName" value="<%= fileName %>">
          </td>
        </tr>
        <tr>
          <td align="right">Description:&nbsp;</td>
          <td align="left" colspan="2">
            <input type="text" name="description" size="100" maxSize="100" value="<%= description %>">
          </td>
        </tr>
        <tr>
          <td align="right">Report Category</td>
          <td nowrap align="left" colspan="2">
            <select name="reportCategoryCT">
            <option value="0">Please Select</option>
              <%
                  if (reportCategoryCTVOs != null)
                  {
                      for (int i = 0; i < reportCategoryCTVOs.length; i++)
                      {
                          String currentReportCategoryCT = reportCategoryCTVOs[i].getCode();

                          String currentReportCategoryDesc = reportCategoryCTVOs[i].getCodeDesc();

                          if (currentReportCategoryCT.equals(reportCategoryCT))
                          {
                              out.println("<option selected value=\"" + currentReportCategoryCT + "\">" + currentReportCategoryDesc + "</option>");
                          }
                          else
                          {
                              out.println("<option value=\"" + currentReportCategoryCT + "\">" + currentReportCategoryDesc + "</option>");
                          }
                      }
                  }
              %>
            </select>
          </td>
        </tr>
     </table>
<br><br>

<span id="buttonContent" style="position:relative; width:100%; height:2%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
    <tr height="5%">

      <td align="left" height="14">
            <input type="button" name="add" value= " Add  " onClick="addOnlineReport()">
            <input type="button" name="save" value=" Save " onClick="saveOnlineReport()">
		    <input type="button" name="cancel" value="Cancel" onClick="cancelOnlineReport()">
		    <input type="button" name="delete" value="Delete" onClick="deleteOnlineReport()">
	  </td>
        <td align="right">
            <input type="button" id="btnRelateOnlineReport" name="relateOnlineReport" value="Online Report Relation" onClick="showRelation()">
        </td>
     </tr>
  </table>
</span>

<br><br>

<span class="tableHeading">Online Report Summary</span>
  <table class="summary" width="100%" height="40%" border="0" cellspacing="0" cellpadding="0">
    <tr height="10" class="heading">
      <th width="33%" align="left">File Name</th>
      <th width="33%" align="left">Description  </th>
      <th width="33%" align="left">Category  </th>
    </tr>
    <tr>
      <td height="99%" colspan="3">
        <span class="scrollableContent">
          <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
          <%
              String className = null;
              String isSelected = null;
              String currentOnlineReportPK = "";
              String sFileName  = "";
              String sDescription = "";
              String sReportCategory = "";

              if (onlineReportVOs != null)
              {
                  for (int i = 0; i < onlineReportVOs.length; i++)
                  {
                      currentOnlineReportPK = onlineReportVOs[i].getOnlineReportPK() + "";

                      if (currentOnlineReportPK.equals(selectedOnlineReportPK))
                      {
                          className = "highlighted";
                          isSelected = "true";
                      }
                      else
                      {
                          className = "default";
                          isSelected="false";
                      }

                      sFileName = onlineReportVOs[i].getFileName();
                      sDescription = onlineReportVOs[i].getDescription();
                      sReportCategory = onlineReportVOs[i].getReportCategoryCT();
          %>
          <tr class="<%= className %>" id="<%= currentOnlineReportPK %>" onlineReportPK="<%= currentOnlineReportPK %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showOnlineReportSummary()">
            <td width="33%" align="left">
              <%= sFileName %>
            </td>
            <td width="33%" align="left">
              <%= sDescription %>
            </td>
            <td width="33%" align="left">
              <%= sReportCategory %>
            </td>
          </tr>
          <%
                  } // end for loop
              } // end if
          %>
          <tr class="filler">
            <td colspan="3">&nbsp;</td>
          </tr>
          </table>
        </span>
      </td>
    </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction"    value="">
<input type="hidden" name="action"         value="">

<input type="hidden" name="selectedOnlineReportPK" value="<%= selectedOnlineReportPK %>">

</form>
</body>
</html>