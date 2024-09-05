<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<%@ page import="edit.common.EDITDate,
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.services.db.hibernate.*,
                 java.util.*,
                 contract.*,
                 client.*,
                 engine.*,
                 edit.portal.common.session.*,
                 edit.portal.taglib.InputSelect,
                 edit.common.vo.BillScheduleVO,
                 billing.*,
                 fission.utility.*,
                 group.*"%>
<!--
 * User: dlataill
 * Date: July 10, 2007
 * Time: 9:31:50 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <!--  This page is displayed when the billing is List -->


 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<jsp:useBean id="clientDetail" class="client.ClientDetail" scope="request"/>
<jsp:useBean id="contractMainSessionBean" class="fission.beans.SessionBean" scope="session"/>

<%
	CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] billingCompanies = codeTableWrapper.getCodeTableEntries("BILLINGCOMPANY");
    CodeTableVO[] billingModes = codeTableWrapper.getCodeTableEntries("BILLFREQUENCY");
    CodeTableVO[] deductionFrequencies = codeTableWrapper.getCodeTableEntries("DEDUCTIONFREQUENCY");
    CodeTableVO[] methods = codeTableWrapper.getCodeTableEntries("BILLINGMETHOD");
    CodeTableVO[] weekDays = codeTableWrapper.getCodeTableEntries("WEEKDAY");
    CodeTableVO[] sortOptions = codeTableWrapper.getCodeTableEntries("SORTOPTION");
    CodeTableVO[] billingConsolidations = codeTableWrapper.getCodeTableEntries("YESNO");
    CodeTableVO[] socialSecurityMasks = CodeTableWrapper.getSingleton().getCodeTableEntries("SOCIALSECURITYMASK");
    CodeTableVO[] skipMonths = codeTableWrapper.getCodeTableEntries("MONTH");
    CodeTableVO[] numberOfMonths = codeTableWrapper.getCodeTableEntries("NUMBEROFMONTHS");
    CodeTableVO[] statuses = CodeTableWrapper.getSingleton().getCodeTableEntries("CASESTATUS");

    BillScheduleVO billScheduleVO = (BillScheduleVO) session.getAttribute("BillScheduleVO");

    String lifePK = contractMainSessionBean.getPageBean("formBean").getValue("lifePK");
    String segmentPK = contractMainSessionBean.getPageBean("formBean").getValue("segmentPK");
    String contractGroupFK = contractMainSessionBean.getPageBean("formBean").getValue("contractGroupFK");
    String segmentDepartmentLocationFK = Util.initString((contractMainSessionBean.getPageBean("formBean").getValue("departmentLocationFK")), "0");
    String priorContractGroupFK = contractMainSessionBean.getPageBean("formBean").getValue("priorContractGroupFK");

    Life life = Life.findByPK(new Long(lifePK));

    PremiumDue premiumDue = null;
    if (billScheduleVO != null)
    {
        premiumDue = PremiumDue.findBySegmentPK_maxEffectiveDate(new Long(segmentPK), new EDITDate(billScheduleVO.getNextBillDueDate()));
    }
    else
    {
        premiumDue = PremiumDue.findBySegmentPK_LatestEffectiveDate(new Long(segmentPK));
    }

    String responseMessage = (String) request.getAttribute("responseMessage");

    long billSchedulePK = 0;
    String billingCompanyCT = "";
    String billingModeCT = "";
    String billTypeCT = "";
    String billMethodCT = "";
    String nextBillExtractDate = "";
    String nextBillDueDate = "";
    String lastBillDueDate = "";
    String firstBillDueDate = "";
    int leadDaysOR = 0;
    String weekDayCT = "";
    String sortOptionCT = "";
    String billingConsolidationCT = "";
    String socialSecurityMaskCT = "";
    String skipMonthStart1CT = "";
    String skipNumberOfMonths1CT = "";
    String skipMonthStart2CT = "";
    String skipNumberOfMonths2CT = "";
    String skipMonthStart3CT = "";
    String skipNumberOfMonths3CT = "";
    int numberOfCopiesGroup = 0;
    int numberOfCopiesAgent = 0;
    String effectiveDate = "";
    String repName = "";
    String terminationDate = "";
    String repPhoneNumber = "";
    String statusCT = "";
    String creationOperator = "";
    String creationDate = "";
    String deductionFrequencyCT = "";
    String firstDeductionDate = "";
    String billingCompanyNumber = "";
    String paidToDate = "";
    String billedToDate = "";
    String billAmount = "";
    String monthlyExpPremium = "";
    String deductionAmount = "";
    String lastPremiumChangeStartDate = "";
    String previousGroupNumber = "";
    String scheduledPremiumAmount = "";
    String conversionPeriodEndDate = Util.initString(contractMainSessionBean.getPageBean("formBean").getValue("conversionDate"), "");

    if (billScheduleVO != null)
    {
        billSchedulePK = billScheduleVO.getBillSchedulePK();
        billingCompanyCT = Util.initString(billScheduleVO.getBillingCompanyCT(), "");
        billingModeCT = Util.initString(billScheduleVO.getBillingModeCT(), "");
        billMethodCT = Util.initString(billScheduleVO.getBillMethodCT(), "");
        billTypeCT = Util.initString(billScheduleVO.getBillTypeCT(), "");
        scheduledPremiumAmount = (premiumDue != null ? premiumDue.getScheduledPremiumAmount(billTypeCT) : "");
        nextBillExtractDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getNextBillExtractDate());
        nextBillDueDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getNextBillDueDate());
        lastBillDueDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getLastBillDueDate());
        firstBillDueDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getFirstBillDueDate());
        leadDaysOR = billScheduleVO.getLeadDaysOR();
        weekDayCT = Util.initString(billScheduleVO.getWeekDayCT(), "");
        sortOptionCT = Util.initString(billScheduleVO.getSortOptionCT(), "");
        billingConsolidationCT = Util.initString(billScheduleVO.getBillingConsolidationCT(), "");
        socialSecurityMaskCT = Util.initString(billScheduleVO.getSocialSecurityMaskCT(), "");
        skipMonthStart1CT = Util.initString(billScheduleVO.getSkipMonthStart1CT(), "");
        skipNumberOfMonths1CT = Util.initString(billScheduleVO.getSkipNumberOfMonths1CT(), "");
        skipMonthStart2CT = Util.initString(billScheduleVO.getSkipMonthStart2CT(), "");
        skipNumberOfMonths2CT = Util.initString(billScheduleVO.getSkipNumberOfMonths2CT(), "");
        skipMonthStart3CT = Util.initString(billScheduleVO.getSkipMonthStart3CT(), "");
        skipNumberOfMonths3CT = Util.initString(billScheduleVO.getSkipNumberOfMonths3CT(), "");
        numberOfCopiesGroup = billScheduleVO.getNumberOfCopiesGroup();
        numberOfCopiesAgent = billScheduleVO.getNumberOfCopiesAgent();

        if (billScheduleVO.getEffectiveDate() != null)
        {
            effectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getEffectiveDate());
        }

        repName = Util.initString(billScheduleVO.getRepName(), "");
        terminationDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getTerminationDate());
        repPhoneNumber = Util.initString(billScheduleVO.getRepPhoneNumber(), "");

        Company company = Company.findBy_CompanyName(billScheduleVO.getBillingCompanyCT());

        billingCompanyNumber = Util.initString(company.getBillingCompanyNumber(), "");

        statusCT = Util.initString(billScheduleVO.getStatusCT(), "");
        creationOperator = Util.initString(billScheduleVO.getCreationOperator(), "");
        creationDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getCreationDate());
        deductionFrequencyCT = Util.initString(billScheduleVO.getDeductionFrequencyCT(), "");
        firstDeductionDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getFirstDeductionDate());

        String nextBillDueDateYYYYMMDD = billScheduleVO.getNextBillDueDate();

        if (nextBillDueDateYYYYMMDD != null)
        {
            EDITDate billedToDateEDITDate = new EDITDate(nextBillDueDateYYYYMMDD);
            billedToDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billedToDateEDITDate.getFormattedDate());
        }

        lastPremiumChangeStartDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getLastPremiumChangeStartDate());
    }

    if (life != null)
    {
        EDITDate paidToDateEDITDate = life.getPaidToDate();

        if (paidToDateEDITDate != null)
        {
            paidToDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(paidToDateEDITDate.getFormattedDate());
        }
    }

    //  Load ContractGroup
    ContractGroup contractGroup = ContractGroup.findBy_ContractGroupPK(new Long(contractGroupFK));
    if (!priorContractGroupFK.equals("0") && !priorContractGroupFK.equals(""))
    {
        ContractGroup priorContractGroup = ContractGroup.findBy_ContractGroupPK(new Long(priorContractGroupFK));
        previousGroupNumber = priorContractGroup.getContractGroupNumber();
    }

    //  Load all DepartmentLocations for the contractGroup
    DepartmentLocation[] departmentLocations = DepartmentLocation.findBy_ContractGroupFK(contractGroup.getContractGroupPK());

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Contract Billing - List contract/jsp/contractListUniversalLifeBilling</title>
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

    var shouldShowLockAlert = true;
    var editableContractStatus = true;

    var responseMessage = "<%= responseMessage %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        var contractIsLocked = <%= userSession.getSegmentIsLocked()%>;

        shouldShowLockAlert = !contractIsLocked;

     	// check for terminated status to disallow contract updates
        editableContractStatus = <%= userSession.getEditableSegmentStatus()%>;
        
        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "button") && ((shouldShowLockAlert == true) ||
            		(editableContractStatus == false)) && !(f.elements[i].name == "close"))
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }

        checkForResponseMessage();

        // Initialize scroll tables
        initScrollTable(document.getElementById("BillingHistoryTableModelScrollTable"));
    }

    function showLockAlert()
    {
    	if (shouldShowLockAlert == true)
        {
            alert("The Contract Cannot Be Edited.");

            return false;
            
        } else if (editableContractStatus == false) {
        	
        	alert("This Contract Cannot Be Edited Due to Terminated Status.");

            return false;
        }
    }

    function saveBillingChange()
    {
    	sendTransactionAction("ContractDetailTran", "saveContractDetail", "_self");
    }

    function cancelBillingChange()
    {
    	window.close();
    	
    	sendTransactionAction("ContractDetailTran", "cancelContract", "contentIFrame");
    }

    function changeToIndividualBill()
    {
        var width = .30 * screen.width;
        var height = .30 * screen.height;

		openDialog("changeToIndividualBillDialog","top=0,left=0,resizable", width, height);

        sendTransactionAction("ContractDetailTran", "changeToIndividualBill", "changeToIndividualBillDialog");
    }

    function changeToDifferentListBill()
    {
        var width = .30 * screen.width;
        var height = .30 * screen.height;

		openDialog("changeToListBillDialog","top=0,left=0,resizable", width, height);

        sendTransactionAction("ContractDetailTran", "changeToListBill", "changeToListBillDialog");
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
<%--        f.statusCT.selectedIndex = 0;--%>
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

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="dialog" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:99%; top:0; left:0; z-index:0; overflow:scroll">
<%--    BEGIN Form Content --%>
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="left" nowrap>
            Group Number:&nbsp;
            <input type="text" name="contractGroupNumber" size="20" disabled value="<%= contractGroup.getContractGroupNumber() %>">
            &nbsp;&nbsp;&nbsp;&nbsp;
        </td>
        <td align="left" nowrap colspan="4">
            Group Name:&nbsp;
            <input:text name="name" bean="clientDetail" attributesText="id='contractGroupName' disabled" size="50"/>
        </td>
    </tr>
    <tr>
        <td align="right" valign="bottom" nowrap colspan="4">Contract Info</td>
    </tr>
    <tr>
        <td align="left" valign="top" colspan="5">
            <span style="vertical-align:top; width:27%; position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="100%">
                <tr>
                    <td align="left" valign="top" nowrap>
                        Billing Company:&nbsp;
                    </td>
                    <td align="left" nowrap="nowrap">
                        <select disabled name="billingCompanyCT">
                          <option selected value="null"> Please Select </option>
                            <%

                              for(int i = 0; i < billingCompanies.length; i++)
                              {
                                  String codeTablePK = billingCompanies[i].getCodeTablePK() + "";
                                  String codeDesc    = billingCompanies[i].getCodeDesc();
                                  String code        = billingCompanies[i].getCode();

                                  if (billingCompanyCT.equalsIgnoreCase(code))
                                  {
                                      out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
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
                <tr>
                    <td align="left" valign="top" nowrap>
                        Billing Frequency:&nbsp;
                    </td>
                    <td align="left" nowrap="nowrap">
                        <select disabled name="billingModeCT">
                          <option selected value="null"> Please Select </option>
                            <%

                              for (int i = 0; i < billingModes.length; i++)
                              {
                                  String codeTablePK = billingModes[i].getCodeTablePK() + "";
                                  String codeDesc    = billingModes[i].getCodeDesc();
                                  String code        = billingModes[i].getCode();

                                  if (billingModeCT.equalsIgnoreCase(code))
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
                <tr>
                    <td align="left" valign="top" nowrap>
                        Billing Method:&nbsp;
                    </td>
                    <td align="left" nowrap="nowrap">
                        <select disabled name="billMethodCT">
                          <option selected value="null"> Please Select </option>
                            <%

                              for (int i = 0; i < methods.length; i++)
                              {
                                  String codeTablePK = methods[i].getCodeTablePK() + "";
                                  String codeDesc    = methods[i].getCodeDesc();
                                  String code        = methods[i].getCode();


                                  if (billMethodCT.equalsIgnoreCase(code))
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
            </span>

            <!-- dummy table -->
            <span style="width:7%; position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="100%">
                <tr>
                    <td align="left" nowrap>&nbsp;</td>
                </tr>
              </table>
            </span>

            <fieldset style="vertical-align:top; width:28%; border-style:solid; border-width:1px; border-color:gray">
            <span style="position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="1">
                <tr>
                    <td align="left" nowrap>Next Bill Extract Date:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="nextBillExtractDate" size='10' maxlength="10" value="<%= nextBillExtractDate %>" >
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>Next Bill Due Date:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="nextBillDueDate" size='10' maxlength="10" value="<%= nextBillDueDate %>" >
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>Last Bill Due Date:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="lastBillDueDate" size='10' maxlength="10" value="<%= lastBillDueDate %>" >
                    </td>
                </tr>
              </table>
            </span>
            </fieldset>

            <!-- dummy table -->
            <span style="width:7%; position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="100%">
                <tr>
                    <td align="left" nowrap>&nbsp;</td>
                </tr>
              </table>
            </span>

            <fieldset style="vertical-align:top; width:28%; border-style:solid; border-width:1px; border-color:gray">
            <legend>Contract Info:</legend>
            <span style="position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="1">
                <tr>
                    <td align="left" nowrap>Scheduled Premium:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="scheduledPremiumAmount" size='10' maxlength="10" value="<%= scheduledPremiumAmount %>" >
                    </td>
                </tr>
                <tr> 
                    <td align="left" nowrap>Bill Amount:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="billAmount" maxlength="3" size="3" value="<%= billAmount %>">
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>Deduction Amount:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="deductionAmount" maxlength="3" size="3" value="<%= deductionAmount %>">
                    </td>
                </tr>
                <tr>
                    <td align="left" valign="top" nowrap>
                        Department/Location:&nbsp;
                    </td>
                    <td align="left" nowrap="nowrap">
                        <select disabled name="departmentLocationPK">
                          <option selected value="null"> Please Select </option>
                            <%
                                for(int i = 0; i < departmentLocations.length; i++)
                                {
                                    String departmentLocationName = departmentLocations[i].getDeptLocName();
                                    String departmentLocationCode = departmentLocations[i].getDeptLocCode();
                                    Long departmentLocationPK = departmentLocations[i].getDepartmentLocationPK();

                                    if (departmentLocationPK.equals(new Long(segmentDepartmentLocationFK)))
                                    {
                                        out.println("<option selected name=\"departmentLocationPK\" value=\"" + departmentLocationPK + "\">" + departmentLocationCode + " - " + departmentLocationName + "</option>");
                                    }
                                    else
                                    {
                                        out.println("<option name=\"departmentLocationPK\" value=\"" + departmentLocationPK + "\">" + departmentLocationCode + " - " + departmentLocationName + "</option>");
                                    }
                                }
                            %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>Previous Group Number:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="previousGroupNumber" maxlength="20" size="15" value="<%= previousGroupNumber %>">
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>Conversion Period End Date:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="conversionPeriodEndDate" maxlength="10" size="10" value="<%= conversionPeriodEndDate %>">
                    </td>
                </tr>
              </table>
            </span>
            </fieldset>

        </td>
    </tr>
    <tr>
        <td align="left" valign="top" colspan="5" rowpsan="3">
            <span style="width:27%; position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="100%">
                <tr>
                    <td align="left" valign="top" nowrap>
                        Deduction Frequency:&nbsp;
                    </td>
                    <td align="left" nowrap="nowrap">
                        <select disabled name="deductionFrequencyCT">
                          <option selected value="null"> Please Select </option>
                            <%

                              for (int i = 0; i < deductionFrequencies.length; i++)
                              {
                                  String codeTablePK = deductionFrequencies[i].getCodeTablePK() + "";
                                  String codeDesc    = deductionFrequencies[i].getCodeDesc();
                                  String code        = deductionFrequencies[i].getCode();

                                  if (deductionFrequencyCT.equalsIgnoreCase(code))
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
                <tr>
                    <td align="left" valign="top" nowrap>
                        First Deduction Date:&nbsp;
                    </td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="firstDeductionDate" size='10' maxlength="10" value="<%= firstDeductionDate %>" >
                    </td>
                </tr>
                <tr>
                    <td align="left" valign="top" nowrap>
                        First Bill Due Date:&nbsp;
                    </td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="firstBillDueDate" size='10' maxlength="10" value="<%= firstBillDueDate %>" >
                    </td>
                </tr>
                <tr>
                    <td align="left" valign="top" nowrap>
                        Lead Days O/R:&nbsp;
                    </td>
                    <td align="left" valign="top" nowrap>
                        <input disabled type="text" name="leadDaysOR" maxlength="3" size="3" value="<%= leadDaysOR %>">
                    </td>
                </tr>
                <tr>
                    <td align="left" valign="top" nowrap>
                        Week Day:&nbsp;
                    </td>
                    <td align="left" nowrap="nowrap">
                        <select disabled name="weekDayCT">
                          <option selected value="null"> Please Select </option>
                            <%

                              for (int i = 0; i < weekDays.length; i++)
                              {
                                  String codeTablePK = weekDays[i].getCodeTablePK() + "";
                                  String codeDesc    = weekDays[i].getCodeDesc();
                                  String code        = weekDays[i].getCode();

                                  if (weekDayCT.equalsIgnoreCase(code))
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
            </span>
            <span style="width:17%; position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="100%">
                <tr>
                    <td align="left" nowrap>&nbsp;</td>
                </tr>
              </table>
            </span>
            <span style="width:28%; position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="100%">
                <tr>
                  <td align="left" valign="top" nowrap>
                      Sort Option:&nbsp;
                  </td>
                  <td align="left" nowrap="nowrap">
                        <select disabled name="sortOptionCT">
                          <option selected value="null"> Please Select </option>
                            <%

                              for (int i = 0; i < sortOptions.length; i++)
                              {
                                  String codeTablePK = sortOptions[i].getCodeTablePK() + "";
                                  String codeDesc    = sortOptions[i].getCodeDesc();
                                  String code        = sortOptions[i].getCode();

                                  if (sortOptionCT.equalsIgnoreCase(code))
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
                <tr>
                  <td align="left" valign="top" nowrap>
                      Billing Consolidation:&nbsp;
                  </td>
                  <td align="left" nowrap="nowrap">
                        <select disabled name="billingConsolidationCT">
                          <option selected value="null"> Please Select </option>
                            <%

                              for (int i = 0; i < billingConsolidations.length; i++)
                              {
                                  String codeTablePK = billingConsolidations[i].getCodeTablePK() + "";
                                  String codeDesc    = billingConsolidations[i].getCodeDesc();
                                  String code        = billingConsolidations[i].getCode();

                                  if (billingConsolidationCT.equalsIgnoreCase(code))
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
                <tr>
                  <td align="left" valign="top" nowrap>
                      Social Security Mask:&nbsp;
                  </td>
                  <td align="left" nowrap="nowrap">
                        <select disabled name="socialSecurityMaskCT">
                          <option selected value="null"> Please Select </option>
                            <%

                              for (int i = 0; i < socialSecurityMasks.length; i++)
                              {
                                  String codeTablePK = socialSecurityMasks[i].getCodeTablePK() + "";
                                  String codeDesc    = socialSecurityMasks[i].getCodeDesc();
                                  String code        = socialSecurityMasks[i].getCode();

                                  if (socialSecurityMaskCT.equalsIgnoreCase(code))
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
            </span>
        </td>
    </tr>
    <tr>
        <td align="left" valign="top" nowrap colspan="5" rowspan="3">
            <fieldset style="width:27%; border-style:solid; border-width:1px; border-color:gray">
            <span style="position:relative; top:0; left:0; z-index:0; overflow:visible">
              <table width="100%" height="100%" border="0" cellspacing="1" cellpadding="1">
                <tr>
                    <td align="left" nowrap>Skip Month Start</td>
                    <td align="left" nowrap># of Month</td>
                </tr>
                <tr>
                    <td align="left" nowrap="nowrap">
                      <select disabled name="skipMonthStart1CT">
                        <option selected value="null"> Please Select </option>
                          <%
                            for (int i = 0; i < skipMonths.length; i++)
                            {
                                String codeTablePK = skipMonths[i].getCodeTablePK() + "";
                                String codeDesc    = skipMonths[i].getCodeDesc();
                                String code        = skipMonths[i].getCode();

                                if (skipMonthStart1CT.equalsIgnoreCase(code))
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
                    <td align="left" nowrap="nowrap">
                      <select disabled name="skipNumberOfMonths1CT">
                        <option selected value="null"> Please Select </option>
                          <%
                            for (int i = 0; i < numberOfMonths.length; i++)
                            {
                                String codeTablePK = numberOfMonths[i].getCodeTablePK() + "";
                                String codeDesc    = numberOfMonths[i].getCodeDesc();
                                String code        = numberOfMonths[i].getCode();

                                if (skipNumberOfMonths1CT.equalsIgnoreCase(code))
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
                <tr>
                    <td align="left" nowrap="nowrap">
                      <select disabled name="skipMonthStart2CT">
                        <option selected value="null"> Please Select </option>
                          <%
                            for (int i = 0; i < skipMonths.length; i++)
                            {
                                String codeTablePK = skipMonths[i].getCodeTablePK() + "";
                                String codeDesc    = skipMonths[i].getCodeDesc();
                                String code        = skipMonths[i].getCode();

                                if (skipMonthStart2CT.equalsIgnoreCase(code))
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
                    <td align="left" nowrap="nowrap">
                      <select disabled name="skipNumberOfMonths2CT">
                        <option selected value="null"> Please Select </option>
                          <%
                            for (int i = 0; i < numberOfMonths.length; i++)
                            {
                                String codeTablePK = numberOfMonths[i].getCodeTablePK() + "";
                                String codeDesc    = numberOfMonths[i].getCodeDesc();
                                String code        = numberOfMonths[i].getCode();

                                if (skipNumberOfMonths2CT.equalsIgnoreCase(code))
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
                <tr>
                    <td align="left" nowrap="nowrap">
                      <select disabled name="skipMonthStart3CT">
                        <option selected value="null"> Please Select </option>
                          <%
                            for (int i = 0; i < skipMonths.length; i++)
                            {
                                String codeTablePK = skipMonths[i].getCodeTablePK() + "";
                                String codeDesc    = skipMonths[i].getCodeDesc();
                                String code        = skipMonths[i].getCode();

                                if (skipMonthStart3CT.equalsIgnoreCase(code))
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
                    <td align="left" nowrap="nowrap">
                      <select disabled name="skipNumberOfMonths3CT">
                        <option selected value="null"> Please Select </option>
                          <%
                            for (int i = 0; i < numberOfMonths.length; i++)
                            {
                                String codeTablePK = numberOfMonths[i].getCodeTablePK() + "";
                                String codeDesc    = numberOfMonths[i].getCodeDesc();
                                String code        = numberOfMonths[i].getCode();

                                if (skipNumberOfMonths3CT.equalsIgnoreCase(code))
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
            </span>
            </fieldset>
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
                    <td align="left" nowrap>&nbsp;</td>
                    <td align="left" nowrap># Copies</td>
                </tr>
                <tr>
                    <td align="left" nowrap>Group:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="numberOfCopiesGroup" maxlength="3" size="3" value="<%= numberOfCopiesGroup %>">
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>Agent:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="numberOfCopiesAgent" maxlength="3" size="3" value="<%= numberOfCopiesAgent %>">
                    </td>
                </tr>
              </table>
            </span>
            </fieldset>
        </td>
    </tr>
    <tr>
        <td align="left" valign="top" nowrap colspan="5">&nbsp;</td>
    </tr>
    <tr>
        <td align="left" valign="top" nowrap colspan="5">&nbsp;</td>
    </tr>
    <tr>
        <td align="left" nowrap width="5%">
            Effective Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input disabled type="text" name="effectiveDate" size='10' maxlength="10" value="<%= effectiveDate %>" >
        </td>
        <td align="left" nowrap width="17%">
            &nbsp;
        </td>
        <td align="left" nowrap width="5%">
            Rep Name:&nbsp;
        </td>
        <td align="left" nowrap width="23%">
            <input disabled type="text" name="repName" maxlength="35" size="35" value="<%= repName %>">
        </td>
    </tr>
    <tr>
        <td  align="left" nowrap width="5%">
            Termination Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input disabled type="text" name="terminationDate" size='10' maxlength="10" value="<%= terminationDate %>" >
        </td>
        <td align="left" nowrap width="17%">
            &nbsp;
        </td>
        <td align="left" nowrap width="5%">
            Rep Ext:&nbsp;
        </td>
        <td align="left" nowrap width="23%">
            <input disabled type="text" name="repPhoneNumber" maxlength="12" size="12" value="<%= repPhoneNumber %>">
        </td>
    </tr>
    <tr>
        <td align="left" nowrap width="%5">
            Status:&nbsp;
        </td>
        <td align="left" nowrap="nowrap">
          <select disabled name="statusCT">
            <option selected value="null"> Please Select </option>
              <%
                for (int i = 0; i < statuses.length; i++)
                {
                    String codeTablePK = statuses[i].getCodeTablePK() + "";
                    String codeDesc    = statuses[i].getCodeDesc();
                    String code        = statuses[i].getCode();

                    if (statusCT.equalsIgnoreCase(code))
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
        <td align="left" nowrap width="17%">
            &nbsp;
        </td>
        <td align="left" nowrap width="5%">
            Billing Company #:&nbsp;
        </td>
        <td align="left" nowrap width="23%">
            <input disabled type="text" name="billingCompanyNumber" maxlength="20" size="20" value="<%= billingCompanyNumber %>">
        </td>
    </tr>
    <tr>
        <td align="left" nowrap colspan="5">&nbsp;</td>
    </tr>
    <tr>
        <td align="left" nowrap colspan="5">&nbsp;</td>
    </tr>
    <tr>
        <td nowrap align="left" >
        	<input id="btnSave" type="button" value=" Save " onClick="saveBillingChange()">
			<input id="btnCancel" type="button" value="Cancel" onClick="cancelBillingChange()">
			<input id="btnChangeToIndividualBill" type="button" value="Change To Direct Bill" onClick="changeToIndividualBill()">
			<input id="btnChangeToDiffListBill" type="button" value="Change Group Bill" onClick="changeToDifferentListBill()">
	  	</td>
        <td id="groupsMessage" align="center" colpsan="2">
            <span class="tableHeading">Billing History</span>
        </td>
    </tr>
  </table>
<%--    END Form Content --%>

<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="ContractBillingTableModel"/>
    <jsp:param name="tableHeight" value="30"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="none"/>
</jsp:include>
</span>
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
<input type="hidden" name="creationOperator" value="<%= creationOperator %>">
<input type="hidden" name="creationDate" value="<%= creationDate %>">
<input type="hidden" name="segmentPK" value="<%= segmentPK %>">
<input type="hidden" name="billSchedulePK" value="<%= billSchedulePK %>">
<input type="hidden" name="repName" value="<%= repName %>">
<input type="hidden" name="repPhoneNumber" value="<%= repPhoneNumber %>">
<input type="hidden" name="billingCompanyCT" value="<%= billingCompanyCT %>">
<input type="hidden" name="scheduledPremiumAmount" value="<%= scheduledPremiumAmount %>">
<input type="hidden" name="billingModeCT" value="<%= billingModeCT %>">
<input type="hidden" name="billMethodCT" value="<%= billMethodCT %>">
<input type="hidden" name="weekDayCT" value="<%= weekDayCT %>">
<input type="hidden" name="sortOptionCT" value="<%= sortOptionCT %>">
<input type="hidden" name="billingConsolidationCT" value="<%= billingConsolidationCT %>">
<input type="hidden" name="socialSecurityMaskCT" value="<%= socialSecurityMaskCT %>">
<input type="hidden" name="skipMonthStart1CT" value="<%= skipMonthStart1CT %>">
<input type="hidden" name="skipNumberOfMonths1CT" value="<%= skipNumberOfMonths1CT %>">
<input type="hidden" name="skipMonthStart2CT" value="<%= skipMonthStart2CT %>">
<input type="hidden" name="skipNumberOfMonths2CT" value="<%= skipNumberOfMonths2CT %>">
<input type="hidden" name="skipMonthStart3CT" value="<%= skipMonthStart3CT %>">
<input type="hidden" name="skipNumberOfMonths3CT" value="<%= skipNumberOfMonths3CT %>">
<input type="hidden" name="statusCT" value="<%= statusCT %>">
<input type="hidden" name="billTypeCT" value="<%= billTypeCT %>">
<input type="hidden" name="deductionFrequencyCT" value="<%= deductionFrequencyCT %>">
<input type="hidden" name="firstDeductionDate" value="<%= firstDeductionDate %>">
<input type="hidden" name="departmentLocationPK" value="">

<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
