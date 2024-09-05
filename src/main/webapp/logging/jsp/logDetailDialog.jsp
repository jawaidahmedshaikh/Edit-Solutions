<!--
 * User: sprasad
 * Date: Jun 7, 2006
 * Time: 10:43:38 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

<%
    Integer pageNumber = (Integer) request.getAttribute("pageNumber");

    if (pageNumber == null)
    {
        pageNumber = new Integer(1);
    }

    String logPK = request.getParameter("logPK");
%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
  <jsp:param name="tableId" value="LogDetailTableModel"/>
  <jsp:param name="tableHeight" value="80"/>
  <jsp:param name="tableWidth" value="150"/>
  <jsp:param name="horizontalScroll" value="true"/>
  <jsp:param name="multipleRowSelect" value="false"/>
  <jsp:param name="singleOrDoubleClick" value="none"/>
</jsp:include>

<script>
    
    /**
    * Shows the next set of log entries (page left or page right).
    */
    function showLogEntries()
    {
        sendTransactionAction("LoggingAdminTran", "showLogEntries", "_self");
    }
    
</script>

<span style="position:relative; width:100%; height:10%; top:0; left:0; z-index:0">
    <table width="100%" border="0" cellspacing="6" cellpadding="0">
        <tr>
            <td width="100%" align="right" valign="top">
                <input name="btnLeft" value="  &#8592;  " type="button" onClick="pageDirection.value = 'LEFT'; showLogEntries()">
                &nbsp;<font style="font-size:7pt; font-style:normal">page:<%= pageNumber %></font>&nbsp;
                <input name="btnRight" value="  &#8594;  " type="button" onClick="pageDirection.value = 'RIGHT'; showLogEntries()">
                &nbsp;&nbsp;&nbsp;
                <input type="button" name="close" value="Close" onClick ="closeWindow()">
            </td>            
        </tr>
    </table>
</span>

<input type="hidden" name="pageNumber" value="<%= pageNumber.intValue() %>"/>
<input type="hidden" name="pageDirection" value=""/>
<input type="hidden" name="logPK" value="<%= logPK %>"/>