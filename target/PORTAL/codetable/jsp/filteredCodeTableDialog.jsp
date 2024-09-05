<%@ page import="edit.common.vo.*,
                 edit.common.vo.user.*,
                 java.util.*,
                 fission.utility.Util"%>
<%
    String message = (String) request.getAttribute("message");
    message = (message != null)?message:null;

    System.out.println("message = " + message);

    String selectedFilteredCodeTablePK = (String)request.getAttribute("selectedFilteredCodeTablePK");
    FilteredCodeTableVO selectedFilteredCodeTableVO = (FilteredCodeTableVO) request.getAttribute("selectedFilteredCodeTableVO");

    CodeTableVO codeTableVO = (CodeTableVO)request.getAttribute("selectedCodeTableVO");

    String selectedCodeTableName = (String)request.getAttribute("selectedCodeTableName");
    String code = "";
    String codeDescription = "";
    String filteredCodeDescription = "";

    if (selectedFilteredCodeTableVO != null)
    {
        filteredCodeDescription = Util.initString(selectedFilteredCodeTableVO.getCodeDesc(), "");
    }

    if (codeTableVO != null)
    {
        code = codeTableVO.getCode();

        if (!filteredCodeDescription.equals(""))
        {
            codeDescription = filteredCodeDescription;
        }
        else
        {
            codeDescription = codeTableVO.getCodeDesc();
        }
    }

%>
<html>

<head>
<title>Filtered Code Table</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<%--<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">--%>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<script language="Javascript1.2">

var message = "<%= message %>";

function init()
{
		f = document.filteredCodeTableForm;
        
        alertMessage();

		window.resizeTo(getPreferredWidth(), getPreferredHeight());
		f.codeDescription.focus();


}

function getPreferredWidth()
{
    return .75 * screen.width;
}

function getPreferredHeight()
{
    return .45 * screen.height;
}

function saveFilteredCodeTable()
{
	sendTransactionAction("CodeTableTran", "saveFilteredCodeTable", "_self");
}

function cancelFilteredCodeTable()
{
	sendTransactionAction("CodeTableTran", "cancelFilteredCodeTable", "_self");
}

function alertMessage()
{
    if (message != "null")
    {
        alert(message);
    }
}

function closeWindow()
{
    window.close();
}

function sendTransactionAction(transaction, action, target)
{

	f.transaction.value = transaction;
	f.action.value = action;
	f.target = target;
	f.submit();
}
</script>


</head>
<body  class="mainTheme" onLoad="init()">

<form name="filteredCodeTableForm">

    <span class="tableHeading">Filtered Code Table</span><br>
    <table width="100%" height="15%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td nowrap align="right">Code Table Name:&nbsp;</td>
            <td nowrap align="left">
                <input type="text" name="codeTableName" size="20" maxSize="20" disabled value="<%= selectedCodeTableName %>">
            </td>
        </tr>
        <br>

        <tr>
            <td nowrap align="right">Code:&nbsp;</td>
            <td nowrap align="left">
                <input type="text" name="code" size="20" maxSize="20" disabled value="<%= code %>">
            </td>
       </tr>
       <tr>
            <td align="right">Code Description:&nbsp;</td>
            <td align="left">
                <input type="text" name="codeDesc" size="100" maxSize="100" value="<%= codeDescription %>">
            </td>
       </tr>

    </table>

    <br>

<span id="buttonContent" style="position:relative; width:100%; height:2%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
    <tr height="5%">

      <td align="right" height="14">
            <input type="button" name="save" value=" Save " onClick="saveFilteredCodeTable()">
		    <input type="button" name="cancel" value="Cancel" onClick="cancelFilteredCodeTable()">
            <input type="button" name="clone" value="Close" onClick="closeWindow()">
      </td>

     </tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">
<input type="hidden" name="selectedFilteredCodeTablePK" value="<%= selectedFilteredCodeTablePK %>">
<input type="hidden" name="selectedCodeTableName" value="<%= selectedCodeTableName %>">

</form>
</body>
</html>
