<!--
 * User: cgleason
 * Date: Apr 24, 2008
 * Time: 12:41:39 PM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*,
                 codetable.business.*,
                 fission.utility.Util,
                 event.*" %>
                 
<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>             

<%
	CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();
	CodeTableVO[] trxTypes = codeTableWrapper.getTRXCODE_CodeTableEntries();

	PageBean formBean = contractMainSessionBean.getPageBean("formBean");

    String filterDisplayUndo = (String) session.getAttribute("filterDisplayUndo");
    String filterTransaction = (String) session.getAttribute("filterTransaction");

    String financialMessage = Util.initString((String) request.getAttribute("financialMessage"), "");

%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>

<script language="JavaScript1.2">

<!--******* JAVASCRIPT ******//-->

    var f = null;

    var filterDisplayUndo = "<%= filterDisplayUndo %>";
   
    var financialMessage = "<%= financialMessage %>";

    function init()
    {
        f = document.financialHistoryForm;

        if (financialMessage != "")
        {
            alert(financialMessage);
        }

        if (filterDisplayUndo == "false") {
        	f.filterUndoCheckbox.checked = false;
        } else if (filterDisplayUndo == "true") {
        	f.filterUndoCheckbox.checked = true;
        }

        // Initialize scroll tables
        initScrollTable(document.getElementById("FinancialHistoryTableModelScrollTable"));
    }

    function closeHistory()
    {
       window.close();
    }

    function reloadHistory()
    {
    	if (f.filterUndoCheckbox.checked == true)
        {
            f.filterDisplayUndo.value = "true";
        } else {
            f.filterDisplayUndo.value = "false";
        }
        
        sendTransactionAction("ContractDetailTran", "showFinancialHistoryDialog", "_self");
    }

    function sendTransactionAction(transaction, action, target)
    {
		f.transaction.value=transaction;
		f.action.value=action;

		f.target = target;

		f.submit();
	}


</script>
<head>
<title>Financial History</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>


<body class="mainTheme" onLoad="init()" style="border-style:solid; border-width:1;">
<form name="financialHistoryForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="mainContent" style="position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">

<br>
<br>
  <table id="table" width="90%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td>
        &nbsp;&nbsp;
        <input type="checkbox" name="filterUndoCheckbox" onclick="reloadHistory()" <%= filterDisplayUndo %>> Display Undo/Reversals
      </td>
      
      <td nowrap align="right">Filter By Transaction Type:&nbsp;</td>
      <td nowrap align="left">
        <select name="filterTransaction" onchange="reloadHistory()">
          <option value="null">Please Select</option>
          <%
              for(int i = 0; i < trxTypes.length; i++) {

                  String code       = trxTypes[i].getCode();
                  String codeDesc   = trxTypes[i].getCodeDesc();
                  
                  if (code.equalsIgnoreCase(filterTransaction))
                  {
                      out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
                  else
                  {
                      out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                  }
              }
          %>
        </select>
      </td>
    </tr>
  </table>
  
  <br/>
    
<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="FinancialHistoryTableModel"/>
    <jsp:param name="tableHeight" value="85"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>


  <br>

 <table  width="100%" border="0">
      <tr>
        <td align="right" colspan="2">
            &nbsp;
        </td>
        <td align="right" nowrap>
          <input type="button" name="close" value="Close" onClick="closeHistory()">
        </td>
      </tr>
</table>

</span>


<!-- ****** HIDDEN FIELDS ***** //-->
  	<input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
	<input type="hidden" name="filterDisplayUndo"      value="">
	<input type="hidden" name="filterTransaction"      value="">
</form>
</body>

</html>
