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
                 edit.portal.taglib.InputSelect"%>
<!--
 * User: dlataill
 * Date: Jul 19, 2007
 * Time: 1:20:50 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<jsp:useBean id="enrollment" class="group.Enrollment" scope="request"/>
<jsp:useBean id="projectedBusinessByMonth" class="group.ProjectedBusinessByMonth" scope="request"/>

<%
    String responseMessage = (String) request.getAttribute("responseMessage");
    String pageCommand = Util.initString((String) request.getAttribute("pageCommand"), "");

    String clearProjectedFields = Util.initString((String)request.getAttribute("clearProjectedFields"), "false");

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] enrollmentTypeVOCTs = codeTableWrapper.getCodeTableEntries("ENROLLMENTTYPE");
    InputSelect enrollmentTypeCTs = new InputSelect(enrollmentTypeVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    CodeTableVO[] enrollmentStatusVOCTs = codeTableWrapper.getCodeTableEntries("ENROLLMENTSTATUS");
    InputSelect enrollmentStatusCTs = new InputSelect(enrollmentStatusVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    String productUnderwritingStatus = "unchecked";
    String linksDisabledInd = "Y";
    EDITDate EndingPolicyDate = enrollment.getEndingPolicyDate();
    String uIEndingPolicyDate = "";
    if(EndingPolicyDate !=null){
        String IEndingPolicyDate = EndingPolicyDate.getFormattedDate();
        uIEndingPolicyDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(IEndingPolicyDate);
    }
    
    
    if (enrollment.getEnrollmentPK() != null)
    {
        linksDisabledInd = "N";
    }    
    if (!enrollment.getCaseProductUnderwritings().isEmpty())
    {
        productUnderwritingStatus = "checked";
    }
    else
    {
        //if set to checked already
        productUnderwritingStatus = "unchecked";
    }

    String enrollmentStateStatus = "unchecked";
    if (!enrollment.getEnrollmentStates().isEmpty())
    {
        enrollmentStateStatus = "checked";
    }
    
    boolean leadServiceAgentsExist = false;
    
    if (enrollment.getEnrollmentPK() != null)
    {
        leadServiceAgentsExist = enrollment.enrollmentLeadServiceAgentsExist();    
    }
    


%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Enrollment</title>
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

    var pageCommand = "<%= pageCommand %>";

    var shouldShowLockAlert = true;

    var linksDisabledInd = "<%= linksDisabledInd %>";

    var defaultMaxDate = "<%= EDITDate.DEFAULT_MAX_MONTH + EDITDate.DATE_DELIMITER + EDITDate.DEFAULT_MAX_DAY + EDITDate.DATE_DELIMITER +  EDITDate.DEFAULT_MAX_YEAR %>";

    var clearProjectedFields = "<%= clearProjectedFields %>";
    var uIEndingPolicyDate ="<%= uIEndingPolicyDate%>";
    
    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;
        checkForResponseMessage();
        
        f.uIEndingPolicyDate.value = uIEndingPolicyDate;
        if (clearProjectedFields == "true")
        {
            f.uIDate.value = "";
            f.enrollmentStatusCT.value = "";
            f.projBusNumberOfEligibles.value = "";
            f.percentExpected.value = "";
            f.closedUnpaidPercent.value = "";
            f.uIClosedDate.value = defaultMaxDate;
            f.projectedBusinessByMonthPK.value = "";            
        }

        checkForPageCommand();

        // Initialize scroll tables
        initScrollTable(document.getElementById("EnrollmentTableModelScrollTable"));
        initScrollTable(document.getElementById("ProjectedBusinessTableModelScrollTable"));
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

    function checkForPageCommand()
    {
        if (!valueIsEmpty(pageCommand))
        {
            if (pageCommand == "resetForm")
            {
                resetForm();
                f.uIClosedDate.value = defaultMaxDate;                
            }
        }
    }

    /**
    * Renders the dialog showing all associated EnrollmentLeadServiceAgents for the currently selected Enrollment.
    */
    function showEnrollmentLeadServiceAgents()
    {
        if (linksDisabledInd == "N")
        {
            var width = screen.width * .60;
            var height = screen.height * .45;

            openDialog("enrollmentLeadServiceAgentsDialog","left=0,top=0,resizable=no,status=yes", width, height);

            sendTransactionAction("CaseDetailTran", "showEnrollmentLeadServiceAgents", "enrollmentLeadServiceAgentsDialog");
        }
        else
        {
            alert("Please Select/Save Enrollment Before Viewing Lead Service Agents Information");
        }
    }

    function saveEnrollment()
    {
        if (f.uIBeginningPolicyDate.value == "")
        {
            alert("BeginningPolicyDate Required");
            return;
        }

        if (f.enrollmentTypeCT.value == "")
        {
            alert("Type of Enrollment Required");
            return;
        }
        if(!isInteger(f.numberOfEligibles.value))
        {
            alert("Number Of Eligibles should be an integer");
            return;
        }
            
        f.beginningPolicyDate.value = f.uIBeginningPolicyDate.value;
        
        f.endingPolicyDate.value = f.uIEndingPolicyDate.value;

        f.offerDateNeeded.value = f.uIOfferDateNeeded.value;
        f.anticipatedEnrollmentDate.value = f.uIAnticipatedEnrollmentDate.value;
        f.anticipatedInhouseDate.value = f.uIAnticipatedInhouseDate.value;

        sendTransactionAction("CaseDetailTran", "saveEnrollment", "enrollmentDialog");
    }

    /**
    * Clears all form values in prreparation for a new roll entry.
    */
    function addEnrollment()
    {
        sendTransactionAction("CaseDetailTran", "addEnrollment", "enrollmentDialog");
    }

    function closeEnrollment()
    {
        sendTransactionAction("CaseDetailTran", "closeEnrollment", "enrollmentDialog");
        window.close()
    }

    function deleteEnrollment()
    {
        sendTransactionAction("CaseDetailTran", "deleteEnrollment", "enrollmentDialog");
    }

    function addProjectedBusiness()
    {
        f.uIDate.value = "";
        f.enrollmentStatusCT.value = "";
        f.projBusNumberOfEligibles.value = "";
        f.percentExpected.value = "";
        f.closedUnpaidPercent.value = "";
        f.uIClosedDate.value = defaultMaxDate;
        f.projectedBusinessByMonthPK.value = "";

        sendTransactionAction("CaseDetailTran", "addProjectedBusiness", "enrollmentDialog");

    }

    function saveProjectedBusiness()
    {
        
        if (linksDisabledInd == "N")
        {
            if(!isInteger(f.projBusNumberOfEligibles.value))
            {
            alert("Number Of Eligibles should be an integer");
            }
            else
            {
            f.date.value = f.uIDate.value;

            f.closedDate.value = f.uIClosedDate.value;
            
            sendTransactionAction("CaseDetailTran", "saveProjectedBusiness", "enrollmentDialog");
            }
        }
        else
        {
            alert("Please Select/Save Enrollment Before Adding/Modifying Projected Business Information");
        }
    }

    function deleteProjectedBusiness()
    {
        f.uIDate.value = "";
        f.enrollmentStatusCT.value = "";
        f.projBusNumberOfEligibles.value = "";
        f.percentExpected.value = "";
        f.closedUnpaidPercent.value = "";
        f.uIClosedDate.value = defaultMaxDate;
<%--        f.projectedBusinessByMonthPK.value = "";--%>
        
        sendTransactionAction("CaseDetailTran", "deleteProjectedBusiness", "enrollmentDialog");
    }

	function productUnderwriting()
    {
        if (linksDisabledInd == "N")
        {
            var width = screen.width * .85;
            var height = screen.height * .95;

            openDialog("productUnderwritingDialog","left=0,top=0,resizable=no,status=yes", width, height);

            sendTransactionAction("CaseDetailTran", "showProductUnderwritingDialog", "productUnderwritingDialog");
        }
        else
        {
            alert("Please Select/Save Enrollment Before Viewing Product Underwriting Information");
        }
	}

    function showEnrollmentState()
    {
        if (linksDisabledInd == "N")
        {
            var width = screen.width * .60;
            var height = screen.height * .40;

            openDialog("enrollmentStateDialog","left=0,top=0,resizable=no,status=yes", width, height);

            sendTransactionAction("CaseDetailTran", "showEnrollmentStateDialog", "enrollmentStateDialog");
        }
        else
        {
            alert("Please Select/Save Enrollment Before Viewing EnrollmentState Information");
        }
    }

    function resetFormValues()
    {

    }

    /**
     * While a transaction is being saved, we want to disable all buttons to prevent double-saves.
     */
    function disableActionButtons()
    {
        document.all.trxMessage.innerHTML = "<font color='blue' face=''>Processing...<font>";

        document.all.btnSave.style.backgroundColor = "#99BBBB";
        document.all.btnCancel.style.backgroundColor = "#99BBBB";

        document.all.btnSave.disabled = true;
        document.all.btnCancel.disabled = true;
    }

    /*
    Callback method from TableModel implemenation.
    */
    function onTableRowSingleClick(tableId)
    {
        if (tableId == "EnrollmentTableModel")
        {
            showEnrollmentDetail();
        }
        else
        {
            showProjectedBusinessDetail();
        }
    }

    /**
    * Shows the detail(s) of the just-selected Enrollment.
    */
    function showEnrollmentDetail()
    {
        sendTransactionAction("CaseDetailTran", "showEnrollmentDetail", "_self");
    }

    /**
    * Shows the detail(s) of the just-selected ProjectedBusinessByMonth.
    */
    function showProjectedBusinessDetail()
    {
        sendTransactionAction("CaseDetailTran", "showProjectedBusinessDetail", "_self");
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="dialog" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:70%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="95%" border="0" cellspacing="0" cellpadding="0">
    <tr height="7%">
        <td align="right" nowrap><span class="requiredField">*</span>&nbsp;Beginning Policy Date:&nbsp;</td>
        <td align="left" nowrap>
            <input:text name="uIBeginningPolicyDate" bean="enrollment"
                  attributesText="REQUIRED id='uIBeginningPolicyDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
            <a href="javascript:show_calendar('f.uIBeginningPolicyDate', f.uIBeginningPolicyDate.value);"><img
                   src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                   alt="Select a date from the calendar"></a>
        </td>
        <td align="right" nowrap>Ending Policy Date:&nbsp;</td>
        <td align="left" nowrap>
            <input:text name="uIEndingPolicyDate" bean="enrollment"
                  attributesText="id='uIEndingPolicyDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
            <a href="javascript:show_calendar('f.uIEndingPolicyDate', f.uIEndingPolicyDate.value);"><img
                   src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                   alt="Select a date from the calendar"></a>
        </td>
        <td align="right" nowrap><span class="requiredField">*</span>&nbsp;Type Of Enrollment:&nbsp;</td>
        <td align="left" nowrap>
            <input:select bean="enrollment" name="enrollmentTypeCT" options="<%= enrollmentTypeCTs.getOptions() %>"
                          attributesText="id='enrollmentTypeCT' REQUIRED"/>
        </td>
    </tr>
    <tr height="7%">
        <td align="right" nowrap>Offer Date Needed:&nbsp;</td>
        <td align="left" nowrap>
            <input:text name="uIOfferDateNeeded" bean="enrollment"
                  attributesText="id='uIOfferDateNeeded' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
            <a href="javascript:show_calendar('f.uIOfferDateNeeded', f.uIOfferDateNeeded.value);"><img
                   src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                   alt="Select a date from the calendar"></a>
        </td>
        <td align="right" nowrap>Anticipated Enrollment Date:&nbsp;</td>
        <td align="left" nowrap>
            <input:text name="uIAnticipatedEnrollmentDate" bean="enrollment"
                  attributesText="id='uIAnticipatedEnrollmentDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
            <a href="javascript:show_calendar('f.uIAnticipatedEnrollmentDate', f.uIAnticipatedEnrollmentDate.value);"><img
                   src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                   alt="Select a date from the calendar"></a>
        </td>
    </tr>
    <tr height="7%">
        <td align="right" nowrap="nowrap">Number Of Eligibles:&nbsp;</td>
        <td align="left" nowrap="nowrap">
            <input:text name="numberOfEligibles"
                        bean="enrollment"
                        attributesText="id=\'numberOfEligibles\'"
                        size="5"/>
        </td>
        <td align="right" nowrap>Date Anticipated In-house:&nbsp;</td>
        <td align="left" nowrap>
            <input:text name="uIAnticipatedInhouseDate" bean="enrollment"
                  attributesText="id='uIAnticipatedInhouseDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
            <a href="javascript:show_calendar('f.uIAnticipatedInhouseDate', f.uIAnticipatedInhouseDate.value);"><img
                   src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                   alt="Select a date from the calendar"></a>
        </td>
     </tr>

     <tr height="7%">
        <td align="right" nowrap>&nbsp;</td>
<%--        <td align="left" nowrap>&nbsp;</td>--%>
        <td align="left" nowrap="nowrap">
            <a name="enrollmentStateLink" href ="javascript:showEnrollmentState()">Enrollment States&nbsp;</a>
	    <input  type="checkbox" name="enrollmentStateStatus" <%=enrollmentStateStatus %> >
            
            &nbsp;&nbsp;
            
            <a href="javascript:showEnrollmentLeadServiceAgents()">Lead/Servicing Agents</a><input  type="checkbox" name="enrollmentLeadServiceAgents" <%= (leadServiceAgentsExist == true)?"CHECKED":"" %>>
            
        </td>
        <td align="right" nowrap="nowrap">
            <a name="productUnderwritingLink" href ="javascript:productUnderwriting()">Case Underwriting&nbsp;</a>
	    <input  type="checkbox" name="productUnderwritingIndStatus" <%=productUnderwritingStatus %> >
        </td>
    </tr>
    <tr height="86%">
        <td align="center" valign="top" colspan="4">
            <fieldset style="width:92%; height:100%; border-style:solid; border-width:1px; border-color:gray">
            <span style="width:90%; height:100%; position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="20%">
                <tr>
                    <td align="right" nowrap width="16%">Date:&nbsp;</td>
                    <td align="left" nowrap width="16%">
                        <input:text name="uIDate" bean="projectedBusinessByMonth"
                              attributesText="id='uIDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
                        <a href="javascript:show_calendar('f.uIDate', f.uIDate.value);"><img
                               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                               alt="Select a date from the calendar"></a>
                    </td>
                        <td align="right" nowrap="nowrap"
                            width="16%">Date Closed:&nbsp;&nbsp;</td>
                        <td align="left" nowrap="nowrap"
                            width="16%">
                            <input:text name="uIClosedDate"
                                        bean="projectedBusinessByMonth"
                                        attributesText="id=\'uIClosedDate\' onBlur=\'DateFormat(this, this.value, event, true)\' onKeyUp=\'DateFormat(this, this.value, event, false)\' maxlength=\'10\' size=\'10\'"/>
                        <a href="javascript:show_calendar('f.uIClosedDate', f.uIClosedDate.value);"><img
                               src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                               alt="Select a date from the calendar"></a>
                            &nbsp;</td>
                        <td align="right" nowrap
                            width="16%">Enrollment Status:&nbsp;</td>
                    <td align="left" nowrap="nowrap" width="16%"><input:select bean="projectedBusinessByMonth" name="enrollmentStatusCT" options="<%= enrollmentStatusCTs.getOptions() %>"
                                        attributesText="id='enrollmentStatusCT'"/>
                    </td>
                </tr>
                <tr>
                    <td align="right" nowrap width="28%">Number Of Eligibles:&nbsp;</td>
                    <td align="left" nowrap width="14%">
                        <input:text name="projBusNumberOfEligibles" bean="projectedBusinessByMonth" attributesText="id='projBusNumberOfEligibles'" size="5"/>
                    </td>
                    <td align="right" nowrap width="10%">% Expected:&nbsp;</td>
                    <td align="left" nowrap width="21%">
                        <input:text name="percentExpected" bean="projectedBusinessByMonth" attributesText="id='percentExpected'" size="5"/>
                    </td>
                    <td align="right" nowrap width="10%">Close/Unpaid % Expected:&nbsp;</td>
                    <td align="left" nowrap width="21%">
                        <input:text name="closedUnpaidPercent" bean="projectedBusinessByMonth" attributesText="id='closedUnpaidPercent'" size="5"/>
                    </td>
                </tr>
              </table>
              <table width="100%" height="1%">
                <tr>
                    <td nowrap align="left">
                        <input id="btnProjectedBusinessAdd" type="button" value="Add" onClick="addProjectedBusiness()">
                        <input id="btnProjectedBusinessSave" type="button" value="Save" onClick="saveProjectedBusiness()">
                        <input id="btnProjectedBusinessDelete" type="button" value="Delete" onClick="deleteProjectedBusiness()">
                    </td>
                </tr>
              </table>

              <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                  <jsp:param name="tableId" value="ProjectedBusinessTableModel"/>
                  <jsp:param name="tableHeight" value="70"/>
                  <jsp:param name="multipleRowSelect" value="false"/>
                  <jsp:param name="singleOrDoubleClick" value="single"/>
              </jsp:include>

            </span>
            </fieldset>
        </td>
    </tr>
  </table>
  <table width="100%">
    <tr>
	  	<td nowrap align="left" width="5%">
			<input id="btnEnrollmentAdd" type="button" value="Add" onClick="addEnrollment()">
			<input id="btnEnrollmentSave" type="button" value="Save" onClick="saveEnrollment()">
            <input id="btnDelete" type="button" value="Delete" onClick="deleteEnrollment()">
	  	</td>
        <td id="enrollmentsMessage" width="95%">
            <span class="tableHeading">Enrollments</span>
        </td>
    </tr>
  </table>
</span> 
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="EnrollmentTableModel"/>
    <jsp:param name="tableHeight" value="30"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>

  <table id="closeDialog" width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
    <tr>
	  <td colspan="8" nowrap align="right">
        <input type="button" name="close" value="Close" onClick="opener.showCaseMain();window.close()">
      </td>
    </tr>
  </table>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
    <input:hidden name="transaction"/>
    <input:hidden name="action"/>
    <input:hidden name="beginningPolicyDate"/>
    <input type="hidden" name="endingPolicyDate" value="<%= uIEndingPolicyDate %>">
    <input:hidden name="anticipatedEnrollmentDate"/>
    <input:hidden name="anticipatedInhouseDate"/>
    <input:hidden name="offerDateNeeded"/>
    <input:hidden name="date"/>
    <input:hidden name="closedDate"/>
    <input:hidden name="projectedBusinessByMonthPK" bean="projectedBusinessByMonth"/>
    <input:hidden name="enrollmentPK" bean="enrollment"/>
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
