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
                 edit.common.EDITBigDecimal,
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
 <!--  This page is displayed when the billing is individual (i.e. anything other than  List) -->


 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<jsp:useBean id="clientDetail" class="client.ClientDetail" scope="request"/>
<jsp:useBean id="contractMainSessionBean" class="fission.beans.SessionBean" scope="session"/>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] billingCompanies = codeTableWrapper.getCodeTableEntries("BILLINGCOMPANY");
    CodeTableVO[] billingModes = codeTableWrapper.getCodeTableEntries("BILLFREQUENCY");
    CodeTableVO[] methods = codeTableWrapper.getCodeTableEntries("BILLINGMETHOD");
    CodeTableVO[] weekDays = codeTableWrapper.getCodeTableEntries("WEEKDAY");
    CodeTableVO[] months = codeTableWrapper.getCodeTableEntries("MONTH");
    CodeTableVO[] numberOfMonths = codeTableWrapper.getCodeTableEntries("NUMBEROFMONTHS");
    CodeTableVO[] statuses = CodeTableWrapper.getSingleton().getCodeTableEntries("CASESTATUS");

    BillScheduleVO billScheduleVO = (BillScheduleVO) session.getAttribute("BillScheduleVO");

    String lifePK = contractMainSessionBean.getPageBean("formBean").getValue("lifePK");
    String segmentPK = contractMainSessionBean.getPageBean("formBean").getValue("segmentPK");

    Life life = Life.findByPK(new Long(lifePK));

    PremiumDue premiumDue = null;
    if (billScheduleVO != null)
    {
        premiumDue = PremiumDue.findBySegmentPK_NextBillDueDate(new Long(segmentPK), new EDITDate(billScheduleVO.getNextBillDueDate()));
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
    String skipMonthStart1CT = "";
    String skipNumberOfMonths1CT = "";
    String skipMonthStart2CT = "";
    String skipNumberOfMonths2CT = "";
    String skipMonthStart3CT = "";
    String skipNumberOfMonths3CT = "";
    String effectiveDate = "";
    String existingEffectiveDate = "";
    String terminationDate = "";
    String statusCT = "";
    String creationOperator = "";
    String creationDate = "";
    String paidToDate = "";
    String billedToDate = "";
    String requiredPremiumDueDate = "";
    String transitionPeriodEndDate = "";
    String billAmount = "";
    String monthlyExpPremium = "";
    String requiredPremiumAmount = "";
    String lastPremiumChangeStartDate = "";
    int eftDraftDay = 1;
    String eftDraftDayStartMonthCT = "";

    if (billScheduleVO != null)
    {
        billSchedulePK = billScheduleVO.getBillSchedulePK();
        billingCompanyCT = Util.initString(billScheduleVO.getBillingCompanyCT(), "");
        billingModeCT = Util.initString(billScheduleVO.getBillingModeCT(), "");
        billMethodCT = Util.initString(billScheduleVO.getBillMethodCT(), "");
        billTypeCT = Util.initString(billScheduleVO.getBillTypeCT(), "");
        nextBillExtractDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getNextBillExtractDate());
        nextBillDueDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getNextBillDueDate());
        lastBillDueDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getLastBillDueDate());
        firstBillDueDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getFirstBillDueDate());
        leadDaysOR = billScheduleVO.getLeadDaysOR();
        weekDayCT = Util.initString(billScheduleVO.getWeekDayCT(), "");
        skipMonthStart1CT = Util.initString(billScheduleVO.getSkipMonthStart1CT(), "");
        skipNumberOfMonths1CT = Util.initString(billScheduleVO.getSkipNumberOfMonths1CT(), "");
        skipMonthStart2CT = Util.initString(billScheduleVO.getSkipMonthStart2CT(), "");
        skipNumberOfMonths2CT = Util.initString(billScheduleVO.getSkipNumberOfMonths2CT(), "");
        skipMonthStart3CT = Util.initString(billScheduleVO.getSkipMonthStart3CT(), "");
        skipNumberOfMonths3CT = Util.initString(billScheduleVO.getSkipNumberOfMonths3CT(), "");

        EDITDate currentDate = new EDITDate();
    	String strCurrentDate = currentDate.getFormattedDate();
    	
    	effectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(strCurrentDate);
    	 
        if (billScheduleVO.getEffectiveDate() != null)
        {
            existingEffectiveDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getEffectiveDate());
        }

        terminationDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getTerminationDate());

        statusCT = Util.initString(billScheduleVO.getStatusCT(), "");
        creationOperator = Util.initString(billScheduleVO.getCreationOperator(), "");
        creationDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getCreationDate());

        //  BilledToDate is one day less than the nextBillDueDate
        String nextBillDueDateYYYYMMDD = billScheduleVO.getNextBillDueDate();

        if (nextBillDueDateYYYYMMDD != null)
        {
            EDITDate billedToDateEDITDate = new EDITDate(nextBillDueDateYYYYMMDD);
            billedToDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billedToDateEDITDate.getFormattedDate());
        }

        requiredPremiumAmount = new EDITBigDecimal(billScheduleVO.getRequiredPremiumAmount()).toString();
        transitionPeriodEndDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getTransitionPeriodEndDate());
        lastPremiumChangeStartDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(billScheduleVO.getLastPremiumChangeStartDate());
        eftDraftDay = billScheduleVO.getEFTDraftDay();
        eftDraftDayStartMonthCT = Util.initString(billScheduleVO.getEFTDraftDayStartMonthCT(), "");
        if (!eftDraftDayStartMonthCT.equals(""))
        {
            eftDraftDayStartMonthCT = EDITDate.getMonthName(eftDraftDayStartMonthCT);
        }
    }

    if (life != null)
    {
        EDITDate paidToDateEDITDate = life.getPaidToDate();
        EDITDate lapseDateEDITDate = life.getLapseDate();

        if (paidToDateEDITDate != null)
        {
            paidToDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(paidToDateEDITDate.getFormattedDate());
        }

        if (lapseDateEDITDate != null)
        {
            requiredPremiumDueDate = DateTimeUtil.formatYYYYMMDDToMMDDYYYY(lapseDateEDITDate.getFormattedDate());
        }
    }

    if (premiumDue != null)
    {
        billAmount = premiumDue.getBillAmount().toString();
        monthlyExpPremium = CommissionPhase.findTotalExpectedMonthlyPremium(premiumDue.getPremiumDuePK()).toString();
    }

    UserSession userSession = (UserSession) session.getAttribute("userSession");

%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Contract Billing - Individual</title>
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
        
        // Resize for the purposes of reverting to this screen when there is an error (rather than loading in the confirmation-size dialog box)
        window.resizeTo(0.9 * screen.width, 0.9 * screen.height);

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

    function changeToListBill()
    {
        var width = .30 * screen.width;
        var height = .30 * screen.height;

		openDialog("changeToListBillDialog","top=0,left=0,resizable", width, height);

        sendTransactionAction("ContractDetailTran", "changeToListBill", "changeToListBillDialog");
    }




    function saveGroupBillingChange()
    {
        var width = 0.32 * screen.width;
    	var height = 0.20 * screen.height;

    	sendTransactionAction("ContractDetailTran", "saveBillingChange", "_self");
    }

    function cancelGroupBillingChange()
    {
    	window.close();
    	
    	sendTransactionAction("ContractDetailTran", "cancelContract", "contentIFrame");
    }

    function showBillingChangeDialog()
    {
        var width = 0.70 * screen.width;
        var height = 0.25 * screen.height;

		openDialog("billingChangeDialog","top=0,left=0,resizable", width, height);

        sendTransactionAction("ContractDetailTran", "showBillingChangeDialog", "billingChangeDialog");
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
        <td align="left" valign="top" nowrap colspan="5">&nbsp;</td>
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
                        <select name="billingCompanyCT">
                          <option selected value="null"> Please Select </option>
                            <%

                              for(int i = 0; i < billingCompanies.length; i++)
                              {
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
                        <select name="billingModeCT">
                          <option selected value="null"> Please Select </option>
                            <%

                              for (int i = 0; i < billingModes.length; i++)
                              {
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
                        <select name="billMethodCT">
                          <option selected value="null"> Please Select </option>
                            <%

                              for (int i = 0; i < methods.length; i++)
                              {
                                  String codeDesc    = methods[i].getCodeDesc();
                                  String code        = methods[i].getCode();


                                  if (billMethodCT.equalsIgnoreCase(code))
                                  {
                                      out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                                  }
                                  else
                                  {
                                      //    Display the ListBill in the BillMethod
                                      //    The user is never able to change TO a List Bill
                                      if (!code.equalsIgnoreCase(BillSchedule.BILL_METHOD_LISTBILL))
                                      {
                                          out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                                      }
                                  }
                              }

                            %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="left" valign="top" nowrap>
                        First Bill Due Date:&nbsp;
                    </td>
                    <td align="left" nowrap>
                        <input type="text" name="firstBillDueDate" size='10' maxlength="10" value="<%= firstBillDueDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
                        <a href="javascript:show_calendar('f.firstBillDueDate', f.firstBillDueDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
                    </td>
                </tr>
                <tr>
                    <td align="left" valign="top" nowrap>
                        Lead Days O/R:&nbsp;
                    </td>
                    <td align="left" valign="top" nowrap>
                        <input type="text" name="leadDaysOR" maxlength="3" size="3" value="<%= leadDaysOR %>">
                    </td>
                </tr>
                <tr>
                    <td align="left" valign="top" nowrap>
                        Week Day:&nbsp;
                    </td>
                    <td align="left" nowrap="nowrap">
                        <select name="weekDayCT">
                          <option selected value="null"> Please Select </option>
                            <%

                              for (int i = 0; i < weekDays.length; i++)
                              {
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
                    <td align="left" nowrap>Paid To Date:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="paidToDate" size='10' maxlength="10" value="<%= paidToDate %>" >
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>Billed To Date:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="billedToDate" size='10' maxlength="10" value="<%= billedToDate %>">
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>Bill Amount:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="billAmount" maxlength="3" size="3" value="<%= billAmount %>">
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>Monthly Exp Premium:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="monthlyExpPremium" maxlength="3" size="3" value="<%= monthlyExpPremium %>">
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>Required Premium Due:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="requiredPremiumAmount" maxlength="3" size="3" value="<%= requiredPremiumAmount %>">
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>Required Premium Due Date:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="requiredPremiumDueDate" size='10' maxlength="10" value="<%= requiredPremiumDueDate %>" >
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>Transition Period End Date:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="transitionPeriodEndDate" size='10' maxlength="10" value="<%= transitionPeriodEndDate %>" >
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>Last Premium Change Start Date:&nbsp;</td>
                    <td align="left" nowrap>
                        <input disabled type="text" name="lastPremiumChangeStartDate" maxlength="10" size="10" value="<%= lastPremiumChangeStartDate %>">
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>EFT Draft Day:&nbsp;</td>
                    <td align="left" valign="top" nowrap>
                        <input type="text" name="eFTDraftDay" maxlength="3" size="3" value="<%= eftDraftDay %>">
                    </td>
                </tr>
                <tr>
                    <td align="left" nowrap>EFT Draft Day Start Month:&nbsp;</td>
                    <td align="left" nowrap="nowrap">
                      <select disabled name="eFTDraftDayStartMonthCT">
                        <option selected value="null"> Please Select </option>
                          <%
                            for (int i = 0; i < months.length; i++)
                            {
                                String codeDesc    = months[i].getCodeDesc();
                                String code        = months[i].getCode();

                                if (eftDraftDayStartMonthCT.equalsIgnoreCase(code))
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
                      <select name="skipMonthStart1CT">
                        <option selected value="null"> Please Select </option>
                          <%
                            for (int i = 0; i < months.length; i++)
                            {
                                String codeDesc    = months[i].getCodeDesc();
                                String code        = months[i].getCode();

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
                      <select name="skipNumberOfMonths1CT">
                        <option selected value="null"> Please Select </option>
                          <%
                            for (int i = 0; i < numberOfMonths.length; i++)
                            {
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
                      <select name="skipMonthStart2CT">
                        <option selected value="null"> Please Select </option>
                          <%
                            for (int i = 0; i < months.length; i++)
                            {
                                String codeDesc    = months[i].getCodeDesc();
                                String code        = months[i].getCode();

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
                      <select name="skipNumberOfMonths2CT">
                        <option selected value="null"> Please Select </option>
                          <%
                            for (int i = 0; i < numberOfMonths.length; i++)
                            {
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
                      <select name="skipMonthStart3CT">
                        <option selected value="null"> Please Select </option>
                          <%
                            for (int i = 0; i < months.length; i++)
                            {
                                String codeDesc    = months[i].getCodeDesc();
                                String code        = months[i].getCode();

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
                      <select name="skipNumberOfMonths3CT">
                        <option selected value="null"> Please Select </option>
                          <%
                            for (int i = 0; i < numberOfMonths.length; i++)
                            {
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
            Existing Effective Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="existingEffectiveDate" size='10' maxlength="10" value="<%= existingEffectiveDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)" disabled>
            <!-- <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a> -->
        </td>
        <td align="left" nowrap width="17%">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="left" nowrap width="5%">
            New Transaction Effective Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="effectiveDate" size='10' maxlength="10" value="<%= effectiveDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)" disabled>
            <!-- <a href="javascript:show_calendar('f.effectiveDate', f.effectiveDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a> -->
        </td>
        <td align="left" nowrap width="17%">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td  align="left" nowrap width="5%">
            Termination Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="terminationDate" size='10' maxlength="10" value="<%= terminationDate %>" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
            <a href="javascript:show_calendar('f.terminationDate', f.terminationDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
        </td>
        <td align="left" nowrap width="17%">
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="left" nowrap width="%5">
            Status:&nbsp;
        </td>
        <td align="left" nowrap="nowrap">
          <select name="statusCT">
            <option selected value="null"> Please Select </option>
              <%
                for (int i = 0; i < statuses.length; i++)
                {
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
    </tr>
    <tr>
        <td align="left" nowrap colspan="5">&nbsp;</td>
    </tr>
    <tr>
        <td align="left" nowrap>
            Bill Change Start Date:&nbsp;
        </td>
        <td align="left" nowrap>
            <input:text name="billChangeStartDate" bean="billSchedule"
                  attributesText="id='billChangeStartDate'  onBlur='DateFormat(this, this.value, event, true)'  onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
            <a href="javascript:show_calendar('f.billChangeStartDate', f.billChangeStartDate.value);"><img
                  src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                  alt="Select a date from the calendar"></a>
        </td>
        <td>
            <a href ="javascript:showBillingChangeDialog()">Transition Period End Date</a>
        </td>
    </tr>
    <tr>
        <td align="left" nowrap colspan="5">&nbsp;</td>
    </tr>
    <tr>
	  	<td nowrap align="left" >
			<input id="btnSave" type="button" value=" Save " onClick="saveGroupBillingChange()">
			<input id="btnCancel" type="button" value="Cancel" onClick="cancelGroupBillingChange()">
			<input id="btnChangeToListBill" type="button" value="Change To Group Bill" onClick="changeToListBill()">
        </td>
        <td id="indMessage" align="center" colpsan="2">
            <span class="tableHeading">Billing History</span>
        </td>
    </tr>
  </table>
<%--    END Form Content --%>

<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="ContractBillingTableModel"/>
    <jsp:param name="tableHeight" value="25"/>
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
<input type="hidden" name="billSchedulePK" value="<%= billSchedulePK %>">
<input type="hidden" name="segmentPK" value="<%= segmentPK %>">
<input type="hidden" name="billingCompanyCT" value="<%= billingCompanyCT %>">
<input type="hidden" name="billingModeCT" value="<%= billingModeCT %>">
<input type="hidden" name="billMethodCT" value="<%= billMethodCT %>">
<input type="hidden" name="weekDayCT" value="<%= weekDayCT %>">
<input type="hidden" name="skipMonthStart1CT" value="<%= skipMonthStart1CT %>">
<input type="hidden" name="skipNumberOfMonths1CT" value="<%= skipNumberOfMonths1CT %>">
<input type="hidden" name="skipMonthStart2CT" value="<%= skipMonthStart2CT %>">
<input type="hidden" name="skipNumberOfMonths2CT" value="<%= skipNumberOfMonths2CT %>">
<input type="hidden" name="skipMonthStart3CT" value="<%= skipMonthStart3CT %>">
<input type="hidden" name="skipNumberOfMonths3CT" value="<%= skipNumberOfMonths3CT %>">
<input type="hidden" name="statusCT" value="<%= statusCT %>">
<input type="hidden" name="billTypeCT" value="<%= billTypeCT %>">
<input type="hidden" name="lastBillDueDate" value="<%= lastBillDueDate %>">
<input type="hidden" name="nextBillDueDate" value="<%= nextBillDueDate %>">
<input type="hidden" name="nextBillExtractDate" value="<%= nextBillExtractDate %>">
<input type="hidden" name="lastBillDueDate" value="<%= lastBillDueDate %>">
<input type="hidden" name="paidToDate" value="<%= paidToDate %>">
<input type="hidden" name="billedToDate" value="<%= billedToDate %>">
<input type="hidden" name="billAmount" value="<%= billAmount %>">
<input type="hidden" name="monthlyExpPremium" value="<%= monthlyExpPremium %>">
<input type="hidden" name="requiredPremiumAmount" value="<%= requiredPremiumAmount %>">
<input type="hidden" name="requiredPremiumDueDate" value="<%= requiredPremiumDueDate %>">
<input type="hidden" name="lastPremiumChangeStartDate" value="<%= lastPremiumChangeStartDate %>">
<input type="hidden" name="transitionPeriodEndDate" value="<%= transitionPeriodEndDate %>">

<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
