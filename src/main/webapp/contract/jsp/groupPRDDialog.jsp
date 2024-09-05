<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="edit.common.EDITDate,
                 fission.utility.Util,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.services.db.hibernate.*,
                 java.util.*,
                 group.*,
                 client.*,
                 engine.*,
                 edit.portal.common.session.*,
                 fission.utility.DateTimeUtil,
                 edit.portal.taglib.InputSelect,
                 edit.portal.exceptions.PortalEditingException"%>
<!--
 * User: dlataill
 * Date: May 1, 2007
 * Time: 3:38:50 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<jsp:useBean id="contractGroup" class="group.ContractGroup" scope="request"/>
<jsp:useBean id="clientDetail" class="client.ClientDetail" scope="request"/>
<jsp:useBean id="payrollDeductionSchedule" class="group.PayrollDeductionSchedule" scope="request"/>
<jsp:useBean id="billSchedule" class="billing.BillSchedule" scope="request"/>

<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    PortalEditingException editingException = (PortalEditingException) session.getAttribute("portalEditingException");
    String editingExceptionExists = editingException == null ? "false" : "true";

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] prdTypeVOCTs = codeTableWrapper.getCodeTableEntries("PRDTYPE");
    InputSelect prdTypeCTs = new InputSelect(prdTypeVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    CodeTableVO[] prdConsolidationVOCTs = codeTableWrapper.getCodeTableEntries("YESNO");
    InputSelect prdConsolidationCTs = new InputSelect(prdConsolidationVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    CodeTableVO[] prdReportTypeVOCTs = codeTableWrapper.getCodeTableEntries("PRDREPORTTYPE");
    InputSelect prdReportTypeCTs = new InputSelect(prdReportTypeVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    CodeTableVO[] sortOptionVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("SORTOPTION");
    InputSelect sortOptionCTs = new InputSelect(sortOptionVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    CodeTableVO[] prdSummaryVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("PRDSUMMARY");
    InputSelect prdSummaryCTs = new InputSelect(prdSummaryVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    CodeTableVO[] prdOutputTypeVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("PRDOUTPUTTYPE");
    InputSelect prdOutputTypeCTs = new InputSelect(prdOutputTypeVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    PayrollDeductionSchedule payDedSched = (PayrollDeductionSchedule) request.getAttribute("payrollDeductionSchedule");

    String payDedSchedPK = "";
    String prdType = "";

    if (payDedSched != null)
    {
        Long pk = payDedSched.getPayrollDeductionSchedulePK();
        if (pk != null)
        {
            payDedSchedPK = payDedSched.getPayrollDeductionSchedulePK().toString();
            prdType = Util.initString(payDedSched.getPRDTypeCT(), "");
        }
    }

    String calendarIsDisabled = "disabled";

    if (!payDedSchedPK.equals("") && prdType.equals(PayrollDeductionSchedule.PRD_CUSTOM))
    {
        calendarIsDisabled = "";
    }

    EDITDate changeEffectiveDate = null;
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Group PRD</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>

<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    var shouldShowLockAlert = true;

	var calendarIsDisabled = "<%= calendarIsDisabled %>";

    var editingExceptionExists = "<%= editingExceptionExists %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();

        if (calendarIsDisabled == "disabled")
        {
            calendarLink.disabled = true;
        }
        else
        {
            calendarLink.disabled = false;
        }

        // Initialize scroll tables
<%--        initScrollTable(document.getElementById("BillingHistoryTableModelScrollTable"));--%>

        checkForEditingException();
    }

    function showLockAlert()
    {
<%--        if (shouldShowLockAlert == true)--%>
<%--        {--%>
<%--            alert("The Case can not be edited.");--%>
<%----%>
<%--            return false;--%>
<%--        }--%>
    }

    function savePRDChange()
    {
        f.effectiveDate.value = f.uIPRDEffectiveDate.value;

        f.terminationDate.value = f.uIPRDTerminationDate.value;

        sendTransactionAction("CaseDetailTran", "savePRDChange", "groupPRDDialog");
    }

    function cancelGroupPRDChange()
    {
    }

    /**
    * Renders the Payroll Deduction Calendar (Flex based).
    */
    function prdCalendar()
    {
        if (calendarIsDisabled != "disabled")
        {
            var width = .75 * screen.width;

            var height = .75 * screen.height;

            var theFeatures = "left=0,top=0,location=no,menubar=no,scrollbars=no,status=no,titlebar=no,toolbar=no";

            theFeatures += ",width=" + width + ",height=" + height;

            var dialog = window.open("/PORTAL/flex/PayrollDeductionCalendarApplication.jsp?PayrollDeductionSchedulePK=<%= payrollDeductionSchedule.getPayrollDeductionSchedulePK() %>", "prdCalendar", theFeatures);
        }
    }

    function resetFormValues()
    {
<%--        f.groupNumber.value = "";--%>
<%--        f.groupName.value = "";--%>
<%--        f.effectiveMonth.value = "";--%>
<%--        f.effectiveDay.value = "";--%>
<%--        f.effectiveYear.value = "";--%>
<%--        f.terminationMonth.value = "";--%>
<%--        f.terminationDay.value = "";--%>
<%--        f.terminationYear.value = "";--%>
<%--        f.operator.value = "";--%>
<%--        f.creationDay.value = "";--%>
<%--        f.creationMonth.value = "";--%>
<%--        f.creationYear.value = "";--%>
<%----%>
<%--        sendTransactionAction("CaseDetailTran", "cancelGroupEntry", "main");--%>
    }

    /**
     * While a tranaction is being saved, we want to disable all buttons to prevent double-saves.
     */
    function disableActionButtons()
    {
        document.all.trxMessage.innerHTML = "<font color='blue' face=''>Processing...<font>";

        document.all.btnSave.style.backgroundColor = "#99BBBB";
        document.all.btnCancel.style.backgroundColor = "#99BBBB";

        document.all.btnSave.disabled = true;
        document.all.btnCancel.disabled = true;
    }
    
    function checkForEditingException()
    {
        if (editingExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("exceptionDialog", "resizable=no", width, height);

            prepareToSendTransactionAction("CaseDetailTran", "showEditingExceptionDialog", "exceptionDialog");
        }
    }

    function continueTransactionAction(transaction, action)
    {
        f.ignoreEditWarnings.value = "true";
        prepareToSendTransactionAction(transaction, action, "");
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="dialog" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:70%; top:0; left:0; z-index:0; overflow:visible">
<%--    BEGIN Form Content --%>
  <table width="100%" height="95%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" nowrap>
            Group Number:&nbsp;
            <input:text name="contractGroupNumber" bean="contractGroup" attributesText="id='contractGroupNumber' disabled" size="20"/>
            &nbsp;&nbsp;&nbsp;&nbsp;
        </td>
        <td align="left" nowrap colspan="4">
            Group Name:&nbsp;
            <input:text name="name" bean="clientDetail" attributesText="id='contractGroupName' disabled" size="50"/>
        </td>
    </tr>
    <tr>
        <td align="left" valign="top" nowrap colspan="5">&nbsp;</td>
    </tr>
    <tr>
        <td align="left" valign="top" colspan="5" rowpsan="4">
            <span style="width:27%; position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="100%">
                <tr>
                    <td align="left" nowrap>PRD Type:&nbsp;</td>
                    <td align="left" nowrap="nowrap"><input:select bean="payrollDeductionSchedule" name="pRDTypeCT" options="<%= prdTypeCTs.getOptions() %>"
                                        attributesText="id='pRDTypeCT'"/>
                        &nbsp;&nbsp;
                        <a href ="javascript:prdCalendar()" ID="calendarLink" >Calendar</a>
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>First Deduction Date:&nbsp;</td>
                    <td align="left" nowrap>
                        <input:text name="firstDeductionDate" bean="billSchedule"
                              attributesText="disabled id='firstDeductionDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>
                        Deduction Frequency:&nbsp;
                    </td>
                    <td align="left" nowrap="nowrap">
                        <input:text name="deductionFrequencyCT" bean="billSchedule" attributesText="id='deductionFrequencyCT' disabled" size="20"/>
                    </td>
                </tr>
              </table>
            </span>
            <span style="width:17%; position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="100%">
                <tr>
                    <td align="left" nowrap>&nbsp;</td>
                </tr>
              </table>
            </span>
            <fieldset style="width:28%; border-style:solid; border-width:1px; border-color:gray">
            <span style="position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="1">
                <tr>
                    <td align="left" nowrap>Last PRD Extract Date:&nbsp;</td>
                    <td align="left" nowrap>
                        <input:text name="lastPRDExtractDate" bean="payrollDeductionSchedule"
                              attributesText="disabled id='lastPRDExtractDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>Next PRD Extract Date:&nbsp;</td>
                    <td align="left" nowrap>
                        <input:text name="nextPRDExtractDate" bean="payrollDeductionSchedule"
                              attributesText="disabled id='nextPRDExtractDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>Current Date Thru:&nbsp;</td>
                    <td align="left" nowrap>
                        <input:text name="currentDateThru" bean="payrollDeductionSchedule"
                              attributesText="disabled id='currentDateThru' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
                    </td>
                </tr>
              </table>
            </span>
            </fieldset>
        </td>
    </tr>
    <tr>
        <td align="left" valign="top" colspan="5" rowpsan="2">
            <span style="width:27%; position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="100%">
                <tr>
                    <td align="left" nowrap>
                        First Deduction Lead Days:&nbsp;
                    </td>
                    <td align="left" nowrap>
                        <input:text name="initialLeadDays" bean="payrollDeductionSchedule" attributesText="id='initialLeadDays'" size="3"/>
                    </td>
                    <td align="left" nowrap colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td align="left" nowrap>
                        Subsequent Days:&nbsp;
                    </td>
                    <td align="left" nowrap>
                        <input:text name="subsequentLeadDays" bean="payrollDeductionSchedule" attributesText="id='subsequentLeadDays'" size="3"/>
                    </td>
                    <td align="left" nowrap colspan="2">&nbsp;</td>
                </tr>
              </table>
            </span>
        </td>
    </tr>
    <tr>
        <td align="left" valign="top" colspan="5" rowpsan="5">
            <span style="width:27%; position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="100%">
                <tr>
                    <td align="left" nowrap>
                        Effective Date:&nbsp;
                    </td>
                    <td align="left" nowrap>
                        <input:text name="uIPRDEffectiveDate" bean="payrollDeductionSchedule"
                              attributesText="id='uIPRDEffectiveDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
                        <a href="javascript:show_calendar('f.uIPRDEffectiveDate', f.uIPRDEffectiveDate.value);"><img
                               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                               alt="Select a date from the calendar"></a>
                    </td>
                </tr>
                <tr>
                    <td  align="left" nowrap>
                        Termination Date:&nbsp;
                    </td>
                    <td align="left" nowrap>
                        <input:text name="uIPRDTerminationDate" bean="payrollDeductionSchedule"
                              attributesText="id='uIPRDTerminationDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
                        <a href="javascript:show_calendar('f.uIPRDTerminationDate', f.uIPRDTerminationDate.value);"><img
                               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                               alt="Select a date from the calendar"></a>
                    </td>
                </tr>
                <tr>
                    <td  align="left" nowrap colspan="2">
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td  align="left" nowrap colspan="2">
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td  align="left" nowrap colspan="2">
                        &nbsp;
                    </td>
                </tr>
              </table>
            </span>
            <span style="width:17%; position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="100%">
                <tr>
                    <td align="left" nowrap>&nbsp;</td>
                </tr>
              </table>
            </span>
            <span style="width:27%; position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="1">
                <tr>
                    <td align="left" nowrap>
                        PRD Consolidation:&nbsp;
                    </td>
                    <td align="left" nowrap="nowrap"><input:select bean="payrollDeductionSchedule" name="pRDConsolidationCT" options="<%= prdConsolidationCTs.getOptions() %>"
                                        attributesText="id='pRDConsolidationCT'"/></td>
                </tr>
                <tr>
                    <td align="left" nowrap>
                        Report Type:&nbsp;
                    </td>
                    <td align="left" nowrap="nowrap"><input:select bean="payrollDeductionSchedule" name="reportTypeCT" options="<%= prdReportTypeCTs.getOptions() %>"
                                        attributesText="id='reportTypeCT'"/></td>
                </tr>
                <tr>
                    <td align="left" nowrap>
                        Sort Option:&nbsp;
                    </td>
                    <td align="left" nowrap="nowrap"><input:select bean="payrollDeductionSchedule" name="sortOptionCT" options="<%= sortOptionCTs.getOptions() %>"
                                        attributesText="id='sortOptionCT'"/></td>
                </tr>
                <tr>
                    <td align="left" nowrap>
                        Summary:&nbsp;
                    </td>
                    <td align="left" nowrap="nowrap"><input:select bean="payrollDeductionSchedule" name="summaryCT" options="<%= prdSummaryCTs.getOptions() %>"
                                        attributesText="id='summaryCT'"/></td>
                </tr>
                <tr>
                    <td align="left" nowrap>
                        Output Type:&nbsp;
                    </td>
                    <td align="left" nowrap="nowrap"><input:select bean="payrollDeductionSchedule" name="outputTypeCT" options="<%= prdOutputTypeCTs.getOptions() %>"
                                        attributesText="id='outputTypeCT'"/></td>
                </tr>
              </table>
            </span>
        </td>
    </tr>
    <tr>
        <td align="left" nowrap colspan="5">
            Change Effective Date:&nbsp;
            <input:text name="changeEffectiveDate" bean="payrollDeductionSchedule"
                  attributesText="id='changeEffectiveDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
            <a href="javascript:show_calendar('f.changeEffectiveDate', f.changeEffectiveDate.value);"><img
                  src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                  alt="Select a date from the calendar"></a>
        </td>
    </tr>
    <tr>
        <td align="left" nowrap width="95%">
                &nbsp;
        </td>
        <td nowrap align="right" width="5%">
			<input id="btnSave" type="button" value=" Save " onClick="savePRDChange()">
			<input id="btnCancel" type="button" value="Cancel" onClick="cancelPRDChange()">
	  	</td>
    </tr>
  </table>
  
  <table width="100%" height="5%">
    <tr>
	  	<td align="left" nowrap width="5%">
                &nbsp;
                </td>
	  	<td nowrap width="95%">
            <span class="tableHeading">PRD Changes(Pending and Processed)</span>
	  	</td>
    </tr>
  </table>
<%--    END Form Content --%>
</span>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="GroupPRDTableModel"/>
    <jsp:param name="tableHeight" value="30"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="none"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>

  <table id="closeDialog" width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
    <tr>
	  <td colspan="8" nowrap align="right">
        <input type="button" name="close" value="Close" onClick="window.close()">
      </td>
    </tr>
  </table>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
    <input:hidden name="transaction"/>
    <input:hidden name="action"/>
    <input:hidden name="creationOperator" bean="payrollDeductionSchedule"/>
    <input:hidden name="creationDate" bean="payrollDeductionSchedule"/>
    <input:hidden name="effectiveDate"/>
    <input:hidden name="terminationDate"/>
    <input:hidden name="payrollDeductionSchedulePK" bean="payrollDeductionSchedule"/>
    <input:hidden name="lastPRDExtractDate" bean="payrollDeductionSchedule"/>
    <input:hidden name="nextPRDExtractDate" bean="payrollDeductionSchedule"/>
    <input:hidden name="currentDateThru" bean="payrollDeductionSchedule"/>
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
