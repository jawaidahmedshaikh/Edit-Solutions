<!--
* User: cgleason
* Date: June 10, 2008
* Time: 3:07:16 PM
*
* (c) 2000-2008 Systems Engineering Group, LLC. All Rights Reserved
* Systems Engineering Group, LLC Proprietary and confidential. Any use is
* subject to the license agreement.
-->
<%@ page import="codetable.dm.dao.CodeTableDAO,
                 edit.common.CodeTableWrapper,
                 edit.common.EDITDate,
                 edit.common.vo.*,
                 fission.utility.*,
                 fission.beans.*,
                 contract.*" %>

<jsp:useBean id="contractMainSessionBean"
             class="fission.beans.SessionBean" scope="session"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");
    PageBean formBean = contractMainSessionBean.getPageBean("formBean");

    BillScheduleVO billScheduleVO = (BillScheduleVO) session.getAttribute("BillScheduleVO");

    String transitionPeriodEndDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getTransitionPeriodEndDate());

    String lifePK = formBean.getValue("lifePK");

    Life life = Life.findByPK(new Long(lifePK));

    String paidToDate = "";
    if (life.getPaidToDate() != null && !life.getPaidToDate().equals("")) {
     	paidToDate = life.getPaidToDate().getFormattedDate();
    }

    String nextBillDueDate = billScheduleVO.getNextBillDueDate();

    String effectiveDate = formBean.getValue("effectiveDate");

    String effectiveDay = effectiveDate.substring(3, 5);


%>

<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
    <title>EDIT SOLUTIONS - Billing Change</title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <meta http-equiv="Cache-Control" content="no-store">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

    <%-- ****************************** BEGIN JavaScript ****************************** --%>
    <script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
    <script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
    <script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
    <script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>


    <script>

        var f = null;
        String.prototype.paddingLeft = function (paddingValue) {
            return String(paddingValue + this).slice(-paddingValue.length);
        };
        var responseMessage = "<%= responseMessage %>";


        /**
         * Called after the body of the page is loaded.
         */
        function init() {
            f = document.theForm;

            checkForResponseMessage();
        }

        function transitionPeriodEndDateLessThanToday() {


            var today = new Date();
            var mm = today.getMonth() + 1;
            var yyyy = today.getFullYear();
            var dd = today.getDate();
            var yyyymmmddTodayDate = '' + yyyy +'/'+ mm.toString().paddingLeft("00") +'/'+ dd.toString().paddingLeft("00");
            var yyyymmddTransitionDate = convertMMDDYYYYToYYYYMMDD(f.transitionPeriodEndDate.value);

            if (yyyymmddTransitionDate < yyyymmmddTodayDate) {
                return true;
            }
            else {
                return false;
            }
        }


        /*
         * Compares the transitionPeriodEndDate to the paidToDate using a string comparison of yyyy/mm/dd dates (because
         * that's all that javascript is capable of). If the transitionPeriodEndDate is less than the paidToDate, returns
         * true, otherwise, returns false.    True is the invalid compare.
         */
        function transitionPeriodEndDateLessThanPaidToDate() {
            var yyyymmddTransitionDate = convertMMDDYYYYToYYYYMMDD(f.transitionPeriodEndDate.value);
            
            if (f.paidToDate.value !== '' && yyyymmddTransitionDate < f.paidToDate.value) {
                return true;
            } else {
                return false;
            }
        }

        /*
         * Compares the transitionPeriodEndDate to the nextBillDueDate using a string comparison of yyyy/mm/dd dates (because
         * that's all that javascript is capable of). If the transitionPeriodEndDate is less than the nextBillDueDate, returns
         * true, otherwise, returns false.   True is the valid compare.
         */
        function transitionPeriodEndDateLessThanOrEqualToNextBillDueDate() {
            var yyyymmddTransitionDate = convertMMDDYYYYToYYYYMMDD(f.transitionPeriodEndDate.value);
            if (yyyymmddTransitionDate <= f.nextBillDueDate.value) {
                return true;
            }
            else {
                return false;
            }
        }

        /*
         * Compares the transitionPeriodEndDay to the effectiveDay of contract. If the transitionPeriodEndDay is
         * equal to the effectiveDay, returns true, otherwise, returns false.   True is the valid compare.
         */
        function transitionPeriodEndDayEqualMonthiversaryDay() {
            <%--        var yyyymmddTransitionDate = convertMMDDYYYYToYYYYMMDD(f.transitionPeriodEndDate.value);--%>

            var day = (f.transitionPeriodEndDate.value).substr(3, 2);

            if (day == f.effectiveDay.value) {
                return true;
            }
            else {
                return false;
            }
        }

        function saveBillingChangeDialog() {


            if (transitionPeriodEndDateLessThanToday()) {
                alert("The TransitionPeriodEndDate must be greater than or equal to today. ")
            } else if (f.transitionPeriodEndDate.value == "") {
                alert("TransitionPeriodEndDate Required");
            }
            else if (transitionPeriodEndDateLessThanPaidToDate()) {
                alert("TransitionPeriodEndDate cannot be less than PaidToDate " + convertYYYYMMDDToMMDDYYYY(f.paidToDate.value));
            }
            // else if (!transitionPeriodEndDateLessThanOrEqualToNextBillDueDate()) {
            //     alert("TransitionPeriodEndDate cannot be greater than BilledToDate " + convertYYYYMMDDToMMDDYYYY(f.nextBillDueDate.value));
            // }
            else if (!transitionPeriodEndDayEqualMonthiversaryDay()) {
                alert("TransitionPeriodEndDate must equal Monthiversary Day " + (f.effectiveDay.value));
            }
            else {
                sendTransactionAction("ContractDetailTran", "saveBillingChangeDialog", "contractBillingDialog");
                window.close();
            }
        }

        function closeDialog() {
            window.close();
        }


    </script>
    <%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

    <%-- ****************************** BEGIN Form Data ****************************** --%>
    <table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
        <%--    BEGIN Form Content --%>

        <tr>
            <td align="left" nowrap>
                Transition Period End Date:&nbsp;
                <input type="text" name="transitionPeriodEndDate" size='10' maxlength="10"
                       value="<%= transitionPeriodEndDate %>" onKeyUp="DateFormat(this,this.value,event,false)"
                       onBlur="DateFormat(this,this.value,event,true)">
                <a href="javascript:show_calendar('f.transitionPeriodEndDate', f.transitionPeriodEndDate.value);"><img
                        src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                        alt="Select a date from the calendar"></a>
            </td>
        </tr>

        <%--    END Form Content --%>

        <tr>
            <td align="right">
                <input id="btnSave" type="button" value=" Save " onClick="saveBillingChangeDialog()">
                <input id="btnCancel" type="button" value=" Cancel " onClick="closeDialog()">
            </td>
        </tr>
    </table>
    <%-- ****************************** END Form Data ****************************** --%>

    <%-- ****************************** BEGIN Hidden Variables ****************************** --%>
    <input type="hidden" name="paidToDate" value="<%= paidToDate %>">
    <input type="hidden" name="nextBillDueDate" value="<%= nextBillDueDate %>">
    <input type="hidden" name="effectiveDay" value="<%= effectiveDay %>">

    <input type="hidden" name="transaction">
    <input type="hidden" name="action">

    <%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>