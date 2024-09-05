<%@ page import="edit.common.vo.*,
                 fission.utility.Util"%>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%
    CodeTableVO    selectedCodeTableVO    = (CodeTableVO) request.getAttribute("selectedCodeTableVO");
    CodeTableDefVO[] codeTableDefVOs = (CodeTableDefVO[]) request.getAttribute("codeTableDefVOs");
    CodeTableVO[] codeTableVOs = (CodeTableVO[]) request.getAttribute(("codeTableVOs"));


    String code = "";
    String codeDesc = "";
    String selectedCodeTableDefPK = "";
    String selectedCodeTablePK = "";
    String message = (String) request.getAttribute("message");

    if (selectedCodeTableVO != null)
    {
        selectedCodeTablePK = selectedCodeTableVO.getCodeTablePK() + "";
        code = Util.initString(selectedCodeTableVO.getCode(), "");
        codeDesc = Util.initString(selectedCodeTableVO.getCodeDesc(), "");
    }

    selectedCodeTableDefPK = (String)request.getAttribute("selectedCodeTableDefPK");

    String pageMode = (String) request.getAttribute("pageMode");

 %>


<html>
<head>
<title>EDITSOLUTIONS - Code Table Summary</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">


<!-- ***** JAVASCRIPT *****  -->

<script language="Javascript1.2">

    var f           = null;
    var previousRow = null;
    var tdElement = null;
    var currentRow = null;
    var message = "<%= message %>";
    var pageMode = "<%= pageMode %>";

    function init() {

	    f = document.theForm;
        var scrollToRowCTD = document.getElementById("<%= selectedCodeTableDefPK %>");
        var scrollToRowCT = document.getElementById("<%= selectedCodeTablePK %>");
        if (scrollToRowCTD != null)
        {

            scrollToRowCTD.scrollIntoView(true);
        }

        if (scrollToRowCT != null)
        {

            scrollToRowCT.scrollIntoView(true);
        }

        setButtonState();

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

    function highlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        currentRow.style.backgroundColor = "#FFFFCC";
    }

    function unhighlightRow(){

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        var className = currentRow.className;

        if (currentRow.isSelected != "true") {

            if (className == "assocEntry")
            {
                currentRow.style.backgroundColor = "#00BB00";
            }
            else if (className == "highLighted")
            {
                currentRow.style.backgroundColor = "#FFFFCC";
            }
            else {

                currentRow.style.backgroundColor = "#BBBBBB";
            }
        }
    }

    function showCodeTableDetail() {

        var tdElement = window.event.srcElement;
    	var currentRow = tdElement.parentElement;

        f.selectedCodeTablePK.value = currentRow.id;

        sendTransactionAction("CodeTableTran", "showCodeTableDefSummary", "main");
    }

    function showCodeTableSummary() {

    	var tdElement = window.event.srcElement;
    	var currentRow = tdElement.parentElement;

        f.selectedCodeTableDefPK.value = currentRow.codeTableDefPK;

        sendTransactionAction("CodeTableTran", "showCodeTableDefSummary", "main");
    }

    function saveCodeTable() {

        sendTransactionAction("CodeTableTran", "saveCodeTable", "main");

    }

    function addCodeTable(){

        sendTransactionAction("CodeTableTran", "addCodeTable", "main");
    }

    function cancelCodeTable() {

        sendTransactionAction("CodeTableTran", "cancelCodeTable", "main");
    }

    function deleteCodeTable()
    {
        sendTransactionAction("CodeTableTran", "deleteCodeTable", "main");
    }


    function showRelation() {

        sendTransactionAction("CodeTableTran", "showCodeTableRelations", "main");
    }


    function sendTransactionAction(transaction, action, target) {

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

<%--<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%;  top:0; left:0; z-index:0; overflow:visible">--%>

<form  name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

    <table class="formData" width="100%" border="0"  cellspacing="6" cellpadding="0">
        <tr>
            <td nowrap align="right">Code:&nbsp;</td>
            <td nowrap align="left">
                <input type="text" name="code" size="20" maxSize="20" disabled value="<%= code %>">
            </td>
            <td align="right">Code Description:&nbsp;</td>
            <td align="left">
                <input type="text" name="codeDesc" size="100" maxSize="100" value="<%= codeDesc %>">
            </td>
        </tr>
     </table>
<%--</span>--%>

<br><br>

<span id="buttonContent" style="position:relative; width:100%; height:2%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
    <tr height="5%">

      <td align="left" height="14">
            <input type="button" name="add" value= " Add  " onClick="addCodeTable()">
            <input type="button" name="save" value=" Save " onClick="saveCodeTable()">
		    <input type="button" name="cancel" value="Cancel" onClick="cancelCodeTable()">
		    <input type="button" name="delete" value="Delete" onClick="deleteCodeTable()">
	  </td>
        <td align="right">
            <input type="button" id="btnRelateCodeTables" name="relateCodeTables" value="Code Table Relation" onClick="showRelation()">
        </td>
     </tr>
  </table>
</span>


    <span class="tableHeading">Code Table Summary</span><br>

<table class="summary" width="100%" height="35%" border="0" cellspacing="0" cellpadding="0">

   <tr height="10" class="heading">
      <th width="33%">Code</th>
      <th width="33%">Code Description  </th>
   </tr>
   <tr>
    <td height="98%" colspan="3">
        <span class="scrollableContent">
            <table id="summaryTable" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">


<%
        String selected = "";
        String currentCodeTablePK = "";
        String sCode  = "";
        String sCodeDesc = "";

        if (codeTableVOs != null) {

            for (int i = 0; i < codeTableVOs.length; i++) {

                String className = "";

                currentCodeTablePK = codeTableVOs[i].getCodeTablePK() + "";

                if (currentCodeTablePK.equals(selectedCodeTablePK)) {

                    className = "highLighted";

                    selected = "true";
                }
                else {

                    className = "mainEntry";

                    selected="false";
                }

                sCode = codeTableVOs[i].getCode();

                sCodeDesc = codeTableVOs[i].getCodeDesc();

        %>
                <tr class="<%= className %>" isSelected="<%= selected %>" id="<%= currentCodeTablePK %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showCodeTableDetail()">
                    <td nowrap width="33%">
                        <%= sCode %>
                    </td>
                    <td nowrap width="33%">
                        <%= sCodeDesc %>
                    </td>
                </tr>
<%
            } //end for loop

      } // end if
%>
                <tr class="filler">
                    <td colspan="3">
                         &nbsp;
                    </td>
                </tr>

              </table>
        </span>
        </td>
    </tr>
</table>
<br>

<table class="summary" width="100%" height="40%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
        <th width="20%">
            Code Table Name
        </th>
    </tr>
    <tr>
        <td height="99%" colspan="5">
            <span class="scrollableContent">

            <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

<%
    String className = null;
    String isSelected = null;
    String currentCodeTableDefPK = "";
    String sTableName  = "";

    if (codeTableDefVOs != null) {

        for (int i = 0; i < codeTableDefVOs.length; i++) {

            currentCodeTableDefPK = codeTableDefVOs[i].getCodeTableDefPK() + "";

            if (currentCodeTableDefPK.equals(selectedCodeTableDefPK)) {

                    className = "highLighted";

                    isSelected = "true";
            }
            else {

                className = "mainEntry";

                isSelected="false";
            }

            sTableName = codeTableDefVOs[i].getCodeTableName();
%>

            <tr class="<%= className %>" id="<%= currentCodeTableDefPK %>" codeTableDefPK="<%= currentCodeTableDefPK %>" isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showCodeTableSummary()">
                <td width="20%">
                    <%= sTableName %>
                </td>
            </tr>

<%
        } // end for loop

     } // end if
%>
                        <tr class="filler">
                            <td colspan="3">
                                 &nbsp;
                            </td>
                        </tr>

                </table>
            </span>
        </td>
    </tr>
</table>
<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction"    value="">
<input type="hidden" name="action"         value="">

<input type="hidden" name="selectedCodeTableDefPK" value="<%= selectedCodeTableDefPK %>">
<input type="hidden" name="selectedCodeTablePK" value="<%= selectedCodeTablePK %>">
<input type="hidden" name="code" value="<%= code %>">

</form>
</body>
</html>

