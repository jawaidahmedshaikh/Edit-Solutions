<%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Nov 12, 2004
  Time: 3:08:17 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- BEGIN Java Code -->
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    Object activeTableSummaryVO = (Object) request.getAttribute("activeTableSummaryVO");

    long activeTableSummaryPK = 0;

    if (activeTableSummaryVO != null)
    {
        activeTableSummaryPK = 999; // activeTableSummaryVO.getTableSummaryPK();
    }

    Object[] tableSummaryVOs = (Object[]) request.getAttribute("tableSummaryVOs");
%>

<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
  <title>Trace</title>
  <link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js" type="text/javascript"></script>

<script type="text/javascript">

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();
    }

    /**
     * Dummary method to show how one might show the detail of a selected row in the summary table.
     */
    function showTableSummaryDetail()
    {
        var selectedRowId = getSelectedRowId("tableSummary");

        if (valueIsEmpty(selectedRowId))
        {
            alert("Value Is Empty");
        }
        else
        {
            f.activeTableSummaryPK.value = selectedRowId;

            sendTransaactionAction("FooTran", "showTableSummaryDetail", "main");
        }
    }
</script><%-- ****************************** END JavaScript ****************************** --%>
</head>

<body class="mainTheme" onload="init()">
  <form name="theForm" method="post" action="/PORTAL/servlet/RequestManager" id="theForm">
    <%-- ****************************** BEGIN Summary Area ****************************** --%>


    <%-- Column Headings --%>

    <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr class="heading">
        <td width="50%" nowrap>Stack Trace</td>

        <td width="50%" nowrap>Message Trace</td>
      </tr>
    </table><%--Summary--%>

<table class="summary" width="100%" height="94%" border="0" cellspacing="0" cellpadding="0">
<tr>
    <td width="50%">
    <span class="scrollableContent" style="position:relative; width:100%; height:100%; top:0; left:0">

    <table id="tableSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
      <%
          if (tableSummaryVOs != null) // Test for the existence of the target VOs.
          {
              for (int i = 0; i < tableSummaryVOs.length; i++) // Loop through the target VOs.
              {
                  Object currentTableSummaryVO = tableSummaryVOs[i];

                  long currentTableSummaryPK = 111;// currentTableSummaryVO.getTableSummaryPK();

                  String columnValue1 = "column value 1"; // currentTableSummaryVO.getColumnValue1();

                  String columnValue2 = "column value 2"; // currentTableSummaryVO.getColumnValue2();

                  String columnValue3 = "column value 3"; // currentTableSummaryVO.getColumnValue3();

                  String columnValue4 = "column value 4"; // currentTableSummaryVO.getColumnValue4();

                  boolean isSelected = false;

                  boolean isAssociated = false; // (e.g. CompanyStructure to Foo associations)

                  String className = null;

                  if (currentTableSummaryPK == activeTableSummaryPK)
                  {
                      isSelected = true;

                      className = "highlighted";
                  }
                  else
                  {
                      className = "default";
                  }
      %>

      <tr class="<%= className %>" id="<%= currentTableSummaryPK %>" isassociated="<%= isAssociated %>" isselected="<%= isSelected %>" onmouseover="highlightRow()" onmouseout="unhighlightRow()" onclick="selectRow(false);showTableSummaryDetail()">
        <td width="25%" nowrap><%= columnValue1 %></td>

        <td width="25%" nowrap><%= columnValue2 %></td>

        <td width="25%" nowrap><%= columnValue3 %></td>

        <td width="25%" nowrap><%= columnValue4 %></td>
      </tr>
      <%
              }// end for
          } // end if
      %>

      <tr class="filler">
        <td colspan="4">&nbsp;</td>
      </tr>
    </table>
    </span>
    </td>

    <td width="50%">
    <span class="scrollableContent" style="position:relative; width:100%; height:100%; top:0; left:0">
    <table id="tableSummary" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
      <%
          if (tableSummaryVOs != null) // Test for the existence of the target VOs.
          {
              for (int i = 0; i < tableSummaryVOs.length; i++) // Loop through the target VOs.
              {
                  Object currentTableSummaryVO = tableSummaryVOs[i];

                  long currentTableSummaryPK = 111;// currentTableSummaryVO.getTableSummaryPK();

                  String columnValue1 = "column value 1"; // currentTableSummaryVO.getColumnValue1();

                  String columnValue2 = "column value 2"; // currentTableSummaryVO.getColumnValue2();

                  String columnValue3 = "column value 3"; // currentTableSummaryVO.getColumnValue3();

                  String columnValue4 = "column value 4"; // currentTableSummaryVO.getColumnValue4();

                  boolean isSelected = false;

                  boolean isAssociated = false; // (e.g. CompanyStructure to Foo associations)

                  String className = null;

                  if (currentTableSummaryPK == activeTableSummaryPK)
                  {
                      isSelected = true;

                      className = "highlighted";
                  }
                  else
                  {
                      className = "default";
                  }
      %>

      <tr class="<%= className %>" id="<%= currentTableSummaryPK %>" isassociated="<%= isAssociated %>" isselected="<%= isSelected %>" onmouseover="highlightRow()" onmouseout="unhighlightRow()" onclick="selectRow(false);showTableSummaryDetail()">
        <td width="25%" nowrap><%= columnValue1 %></td>

        <td width="25%" nowrap><%= columnValue2 %></td>

        <td width="25%" nowrap><%= columnValue3 %></td>

        <td width="25%" nowrap><%= columnValue4 %></td>
      </tr>
      <%
              }// end for
          } // end if
      %>

      <tr class="filler">
        <td colspan="4">&nbsp;</td>
      </tr>
    </table>
    </span>
    </td>
    </tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="right">
            <input type="button" value="Close" onClick="closeWindow()">
        </td>
    </tr>
</table>

<%-- ****************************** END Summary Area ****************************** --%>
<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="activeTableSummaryPK" value="<%= activeTableSummaryPK %>">

<%-- ****************************** END Hidden Variables ****************************** --%>
  </form>
</body>
</html>
