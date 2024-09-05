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
                 edit.portal.taglib.*,
                 edit.portal.exceptions.PortalEditingException"%>

<!--
 * User: dlataill
 * Date: April 30, 2007
 * Time: 7:56:50 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<jsp:useBean id="contractGroup" class="group.ContractGroup" scope="request"/>
<jsp:useBean id="clientDetail" class="client.ClientDetail" scope="request"/>
<jsp:useBean id="clientAddress" class="client.ClientAddress" scope="request"/>

<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    PortalEditingException editingException = (PortalEditingException) session.getAttribute("portalEditingException");
    String editingExceptionExists = editingException == null ? "false" : "true";
    
    String pageToShow = request.getParameter("pageToShow");

    String pageFunction = Util.initString((String) request.getAttribute("pageFunction"), null);
    boolean turnOffEditing = false;
    if (pageFunction != null)
    {
        turnOffEditing = true;
    }

    CodeTableVO[] statusVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("CASESTATUS");
    InputSelect statusCTs = new InputSelect(statusVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    CodeTableVO[] trustTypeCTVOs = CodeTableWrapper.getSingleton().getCodeTableEntries("TRUSTTYPE");
    InputSelect trustTypeCTs = new InputSelect(trustTypeCTVOs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    UserSession userSession = (UserSession) session.getAttribute("userSession");
    
    String shouldResetForm = shouldResetForm(request);

    String linksDisabledInd = "Y";

    String contractGroupNumber = Util.initString((String)request.getAttribute("contractGroupNumber"), "");
    String groupID = Util.initString((String)request.getAttribute("groupID"), "");

    // As a hidden field (below), we are trying to preserve the "active" Case so that it may be
    // maintained across the set of pages without having to use a Session variable.
    ContractGroup activeGroup= (ContractGroup) request.getAttribute("contractGroup");
    if (activeGroup.getContractGroupPK() != null)
    {
        linksDisabledInd = "N";
    }

    String billingStatus = "";
    if (contractGroup.getBillSchedule() != null)
    {
        billingStatus = "checked";
    }

    String prdStatus = "";
    if (!contractGroup.getPayrollDeductionSchedules().isEmpty())
    {
        prdStatus = "checked";
    }

    String deptLocStatus = "";
    if (!contractGroup.getDepartmentLocations().isEmpty())
    {
        deptLocStatus = "checked";
    }
%>
<%!

    /**
    * Evaluates the existence of the ContractGroup and ClientDetail entities to see if the
    * the form on this page should be reset.
    * @return the word "YES" if both ContractGroup and ClientDetail exist, "NO" if either or both do
    * @param request to get access to the request variables
    */
    private String shouldResetForm(HttpServletRequest request)
    {
        String shouldResetForm = null;
        
        Long contractGroupPK = ((ContractGroup) request.getAttribute("contractGroup")).getContractGroupPK();

        Long clientDetailPK = ((ClientDetail) request.getAttribute("clientDetail")).getClientDetailPK();
        
        String resetForm = Util.initString((String) request.getAttribute("resetForm"), "");
        
        if ((clientDetailPK == null) && (contractGroupPK == null))
        {
            shouldResetForm = "YES";
        }
        else if (resetForm.equals("YES"))
        {
            shouldResetForm = "YES";
        }
        else 
        {
            shouldResetForm = "NO";
        }
        
        return shouldResetForm;
    }
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Group Detail</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/contract/javascript/caseMainTabFunctions.js"></script>
<script src="/PORTAL/common/javascript/widgetFunctions.js"></script>
<script src="/PORTAL/common/javascript/scrollTable.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script> 


<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";
    
    var shouldResetForm = "<%= shouldResetForm %>";
    
    var shouldShowLockAlert = true;

    var linksDisabledInd = "<%= linksDisabledInd %>";

    var editingExceptionExists = "<%= editingExceptionExists %>";

    var turnOffEditing =  "<%= turnOffEditing %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        setActiveImage("group");

        f.uIEffectiveDate.focus();

        if (shouldResetForm == "YES")
        {
            // Can't lost this value before after resetting the form.
            var tmpPageName = f.pageName.value;

            resetForm();

            f.pageName.value = tmpPageName;
        }

        if (linksDisabledInd == "Y")
        {
            f.btnDelete.disabled = true;
            f.btnCancel.disabled = true;
        }

        checkForResponseMessage();

        // Initialize scroll tables
        initScrollTable(document.getElementById("GroupSummaryTableModelScrollTable"));
        
        checkForEditingException();

        var groupIsLocked = <%= userSession.getGroupIsLocked()%>;
        var username = "<%= userSession.getUsername() %>";
        var elementPK = "<%= userSession.getGroupPK() %>";
		top.frames["header"].updateLockState(groupIsLocked, username, elementPK);

        shouldShowLockAlert = !groupIsLocked;

        if (turnOffEditing == "false")
        {
            for (var i = 0; i < f.elements.length; i++)
            {
                var elementType = f.elements[i].type;

                if ( (elementType == "text") && (shouldShowLockAlert == true) )
                {
                    f.elements[i].onclick = showLockAlert;
                    f.elements[i].onkeydown = showLockAlert;
                }
            }
        }

        if (turnOffEditing == "true")
        {
            top.frames["header"].setButtonStateForLockedMode();
        }
        else
        {
            top.frames["header"].setButtonStateForUnlockedMode();
        }
    }

    function showLockAlert()
    {
        if (shouldShowLockAlert == true)
        {
            alert("The Group can not be edited.");

            return false;
        }
    }

    //To get the proper form sent
    function lockCurrentTab()
    {
    	sendTransactionAction("CaseDetailTran", "lockCurrentTab", "main");
    }

    function saveCurrentPage()
    {
        sendTransactionAction("CaseDetailTran", "saveCurrentPage", "main");
    }

	function groupBilling()
    {
        if (linksDisabledInd == "N")
        {
            var width = screen.width * .90;
            var height = screen.height * .95;

            openDialog("groupBillingDialog","left=0,top=0,resizable=no,status=yes", width, height);

            prepareToSendTransactionAction("CaseDetailTran", "showGroupBillingDialog", "groupBillingDialog");
        }
        else
        {
            alert("Please Select/Save Group Before Viewing Billing");
        }
	}

	function prd()
    {
        if (linksDisabledInd == "N")
        {
            var width = screen.width * .90;
            var height = screen.height * .80;

            openDialog("groupPRDDialog","left=0,top=0,resizable=no", width, height);

            prepareToSendTransactionAction("CaseDetailTran", "showGroupPRDDialog", "groupPRDDialog");
         }
        else
        {
            alert("Please Select/Save Group Before Viewing PRD");
        }
	}

	function deptLoc()
    {
        if (linksDisabledInd == "N")
        {
            var width = screen.width * .90;
            var height = screen.height * .60;

            openDialog("groupDeptLocDialog","left=0,top=0,resizable=no", width, height);

            prepareToSendTransactionAction("CaseDetailTran", "showGroupDeptLocDialog", "groupDeptLocDialog");
        }
        else
        {
            alert("Please Select/Save Group Before Viewing Dept/Loc");
        }
    }

    function addGroup()
    {
        var width = 0.95 * screen.width;

        var height = 0.90 * screen.height;

        openDialog("groupAddDialog", "top=0,left=0,resizable=no", width,  height);

        sendTransactionAction("CaseDetailTran", "showAddCaseEntry", "groupAddDialog");
    }

    function deleteGroup()
    {
        sendTransactionAction("CaseDetailTran", "deleteGroup", "main");
    }

    function cancelGroup()
    {
        sendTransactionAction("CaseDetailTran", "cancelGroupEntry", "main");
    }

    function resetFormValues()
    {
        f.groupNumber.value = "";
        f.groupID.value = "";
        f.groupName.value = "";
        f.effectiveDate.value = "";
        f.terminationDate.value = "";
        f.statusCT.selectedIndex = 0;
        f.operator.value = "";
        f.creationDate.value = "";

        sendTransactionAction("CaseDetailTran", "cancelGroupEntry", "main");
    }
	function prepareToSendTransactionAction(transaction, action, target)
    {
        sendTransactionAction(transaction, action, target);
	}

    /**
     * While a tranaction is being saved, we want to disable all buttons to prevent double-saves.
     */
    function disableActionButtons()
    {
        document.all.trxMessage.innerHTML = "<font color='blue' face=''>Processing...<font>";

<%--        document.all.btnSave.style.backgroundColor = "#99BBBB";--%>
        document.all.btnCancel.style.backgroundColor = "#99BBBB";

<%--        document.all.btnSave.disabled = true;--%>
        document.all.btnCancel.disabled = true;
    }

    /*
    Callback method from TableModel implemenation.
    */
    function onTableRowSingleClick(tableId)
    {
        showGroupDetail();
    }

    /**
    * Shows the detail(s) of the just-selected Group Summary Table.
    */
    function showGroupDetail()
    {
        sendTransactionAction("CaseDetailTran", "showGroupDetail", "_self");
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
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** Tab Content ****************************** --%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td>
        <jsp:include page="caseMainTabContent.jsp" flush="true"/>
      </td>
    </tr>
</table>

<%-- ****************************** BEGIN Form Data ****************************** --%>
<span style="border-style:solid; border-width:1; width:100%">

<table border="0" cellspacing="3" cellpadding="3">
<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap>
            </span>&nbsp;Group ID:&nbsp;
        </td>
        <td align="left" nowrap>
           <input:text name="groupID" bean="contractGroup"  attributesText="id='groupID'" size="20"/>
       </td>
       <td align="left" colspan="1"></td>
       <td align="left" colspan="2">
       </td>
        <td align="left" nowrap colspan="1">&nbsp;</td>
    </tr>
    <tr>
        <td align="right" nowrap>
            <span class="requiredField">*</span>&nbsp;Group Number:&nbsp;
        </td>
        <td disabled align="left" nowrap>
           <input:text name="contractGroupNumber" bean="contractGroup"  attributesText="id='contractGroupNumber' REQUIRED" size="20"/>
       </td>
       <td align="left" colspan="1"></td>
       <td align="left" colspan="2">
       		<span>Actively at Work:</span>
       </td>
        <td align="left" nowrap colspan="1">&nbsp;</td>
    </tr>
    <tr>
        <td align="right" nowrap>
            <span class="requiredField">*</span>&nbsp;Group Name:&nbsp;
        </td>
        <td disabled align="left" nowrap>
           <input:text name="name" bean="clientDetail" attributesText="id='contractGroupName' REQUIRED" size="50"/> 
        </td>
        <td align="left" colspan="1"></td>
        <td align="left" colspan="2">
                        	<input type="text" name="activeEligibilityHours" value="<%= contractGroup == null || contractGroup.getActiveEligibilityHours() == null ? "" : 
                        					contractGroup.getActiveEligibilityHours().stripTrailingZeros().toPlainString() %>" size="8"/>
                        	<span>Hours</span>        	
        <td>
        <td align="left" nowrap colspan="1">&nbsp;</td>
    </tr>
    <tr>
        <td align="right" nowrap>
            <span class="requiredField">*</span>&nbsp;Address 1:&nbsp;
        </td>
        <td disabled align="left" nowrap>
          <input:text name="addressLine1" bean="clientAddress" attributesText="id='addressLine1' REQUIRED" size="35"/> 
        </td>
        <td align="left" colspan="1"></td>
		<td align="left" nowrap="nowrap" colspan="2">
	                   	<input type="text" name="ineligibilityPeriodUnits" value="<%= contractGroup == null || contractGroup.getIneligibilityPeriodUnits() == null ? "" : 
	                   			contractGroup.getIneligibilityPeriodUnits().stripTrailingZeros().toPlainString() %>"  size="8"/>
	                   	<input type="radio" name="ineligibilityPeriodType" value="<%= ContractGroup.INELIGIBILITY_PERIOD_DAYS %>"
	                   		<%= contractGroup != null && contractGroup.getIneligibilityPeriodType() != null && 
	                   		contractGroup.getIneligibilityPeriodType().equals(ContractGroup.INELIGIBILITY_PERIOD_DAYS) ? "checked" : "" %>> Days
	                   	<input type="radio" name="ineligibilityPeriodType" value="<%= ContractGroup.INELIGIBILITY_PERIOD_MONTHS %>"
	                   	<%= contractGroup != null && contractGroup.getIneligibilityPeriodType() != null &&
	                   	contractGroup.getIneligibilityPeriodType().equals(ContractGroup.INELIGIBILITY_PERIOD_MONTHS) ? "checked" : "" %>> Months
	                   </td>        
        <td align="left" nowrap colspan="1">&nbsp;</td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Address 2:
        </td>
        <td disabled align="left" nowrap>
            <input:text name="addressLine2" bean="clientAddress" attributesText="id='addressLine2'" size="35"/> 
        </td>
        <td align="left" nowrap colspan="4">&nbsp;</td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Address 3:
        </td>
        <td disabled align="left" nowrap>
            <input:text name="addressLine3" bean="clientAddress" attributesText="id='addressLine3'" size="35"/> 
        </td>
        <td align="left" nowrap colspan="4">&nbsp;</td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Address 4:
        </td>
        <td disabled align="left" nowrap>
            <input:text name="addressLine4" bean="clientAddress" attributesText="id='addressLine4'" size="35"/> 
        </td>
        <td align="left" nowrap colspan="4">&nbsp;</td>
    </tr>
    <tr>
        <td align="right" nowrap>
            <span class="requiredField">*</span>&nbsp;City:&nbsp;
        </td>
        <td disabled align="left" nowrap>
            <input:text name="city" bean="clientAddress" attributesText="id='city' REQUIRED" size="35"/> 
        </td>
        <td align="right" nowrap>
            State:&nbsp;
        </td>
        <td disabled align="left" nowrap>
            <input:text name="stateCT" bean="clientAddress" attributesText="id='stateCT' REQUIRED" size="2"/> 
        </td>
        <td align="right" nowrap>
            Zip Code:&nbsp;
        </td>
        <td disabled align="left" nowrap>
            <input:text name="zipCode" bean="clientAddress" attributesText="id='zipCode' REQUIRED" size="15"/> 
        </td>
    </tr>
    <tr>
        <td align="left" nowrap colspan="6">&nbsp;</td>
    </tr>
    <tr>
        <td align="right" nowrap>Effective Date:&nbsp;
         </td>
        <td align="left" nowrap>
              <input:text name="uIEffectiveDate" bean="contractGroup"
                    attributesText="id='uIEffectiveDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
                    <a href="javascript:show_calendar('f.uIEffectiveDate', f.uIEffectiveDate.value);"><img
                       src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                       alt="Select a date from the calendar"></a>
        </td>
        <td  align="right" nowrap>
            Termination Date:&nbsp;
         </td>
        <td align="left" nowrap colspan="3">
                <input:text name="uITerminationDate" bean="contractGroup"
                      attributesText="id='uITerminationDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
                      <a href="javascript:show_calendar('f.uITerminationDate', f.uITerminationDate.value);"><img
                         src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                         alt="Select a date from the calendar"></a>
        </td>
    </tr>
    <tr>
        <td align="left" nowrap colspan="6">&nbsp;</td>
    </tr>
    <tr>
        <td align="center" nowrap colspan="6">
    	    <a name="billingLink" href ="javascript:groupBilling()">Billing&nbsp;</a>
            <input type="checkbox" name="billingIndStatus" <%= billingStatus %>/>
            
            &nbsp;&nbsp;
    	    <a name="prdLink" href ="javascript:prd()">PRD&nbsp;</a>
            <input type="checkbox" name="prdIndStatus" <%= prdStatus %>/>

            &nbsp;&nbsp;
    	    <a name="deptLocLink" href ="javascript:deptLoc()">Dept/Loc&nbsp;</a>
            <input type="checkbox" name="deptLocIndStatus" <%= deptLocStatus %>/>
        </td>
    </tr>
    <tr>
        <td align="left" nowrap colspan="6">&nbsp;</td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Deduction Amt Min Tolerance:&nbsp;
        </td>
        <td align="left" nowrap>
            <input:text name="deductionAmtMinTol" bean="contractGroup" attributesText="id='deductionAmtMinTol'" size="19"/>
        </td>
        <td align="right" nowrap>
            Deduction Amt Max Tolerance:&nbsp;
        </td>
        <td align="left" nowrap colspan="3">
            <input:text name="deductionAmtMaxTol" bean="contractGroup" attributesText="id='deductionAmtMaxTol'" size="19"/>
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Creation Date:&nbsp;
        </td>
        <td disabled align="left" nowrap>
          <input:text name="uICreationDate" bean="contractGroup" attributesText="id='uICreationDate'" size="10"/> 
        </td>
        <td align="right" nowrap>
            Creation Operator:&nbsp;
        </td>
        <td disabled align="left" nowrap colspan="3">
            <input:text name="creationOperator" bean="contractGroup" attributesText="id='creationOperator'" size="10"/>
        </td>
    </tr>
    <tr>
        <td align="left" nowrap colspan="6">&nbsp;</td>
    </tr>
</table>
<table width="100%" border="0">
    <tr>
        <td nowrap align="left" width="2%">
            <input id="btnAdd" type="button" value=" Add " onClick="addGroup()">
            <input id="btnCancel" type="button" value="Cancel" onClick="cancelGroup()">
            <input id="btnDelete" type="button" value="Delete" onClick="deleteGroup()">
        </td>
        <td id="groupsMessage" width="98%">
            <span class="tableHeading">Group Summary</span>
        </td>
    </tr>
<%--    END Form Content --%>
</table>
</span>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="GroupSummaryTableModel"/>
    <jsp:param name="tableHeight" value="60"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
    <input:hidden name="transaction"/>
    <input:hidden name="action"/>
    <input:hidden name="pageToShow" default="<%= pageToShow %>"/>
    <input type="hidden" name="contractGroupTypeCT" value="Group" CONTENTEDITABLE="false"> <!-- Default to "Group" because that is the tab we are on. -->
    <input type="hidden" name="pageName" value="group">
    <input:hidden name="contractGroupPK" bean="contractGroup"/> 
    <input type="hidden" name="contractGroupNumber" value="<%= contractGroupNumber %>">
    <input type="hidden" name="pageFunction" value="<%= pageFunction %>">
    <input type="hidden" name="groupID" value="<%= groupID %>">


<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
