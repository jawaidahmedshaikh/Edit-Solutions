<!--
 * User: sprasad
 * Date: Jun 5, 2006
 * Time: 4:16:16 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->

<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>


<script>
    function showLogDetail(logPK)
    {
        var width = 0.90 * getScreenWidth();

        var height = 0.80 * getScreenHeight();

        openDialog("logDetailDialog", "top=0,left=0,resizable=yes", width, height);

        f.logPK.value = logPK;

        sendTransactionAction("LoggingAdminTran", "showLogEntries", "logDetailDialog");
    }

    function showGeneralExceptionsLog()
    {
        var width = 0.90 * getScreenWidth();

        var height = 0.80 * getScreenHeight();

        openDialog("logGeneralExceptionsDialog", "top=0,left=0,resizable=yes", width, height);

        sendTransactionAction("LoggingAdminTran", "showGeneralExceptionsLog", "logGeneralExceptionsDialog");
    }

</script>

<span class="tableHeading">Log Summary</span><br><br>

<table border="0" cellspacing="6" cellpadding="0">
    <tr>
        <td>
            <input type="button" id="generalExceptionsLog" value="General Exceptions" onclick="showGeneralExceptionsLog()"/>
        </td>
    </tr>
</table>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
  <jsp:param name="tableId" value="LogIndexTableModel"/>
  <jsp:param name="tableHeight" value="90"/>
  <jsp:param name="tableWidth" value="110"/>
  <jsp:param name="multipleRowSelect" value="false"/>
  <jsp:param name="singleOrDoubleClick" value="none"/>
</jsp:include>

<input:hidden name="logPK"/>

