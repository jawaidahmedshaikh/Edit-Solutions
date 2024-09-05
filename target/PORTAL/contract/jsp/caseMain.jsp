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
 * User: cgleason
 * Date: Dec 11, 2005
 * Time: 1:59:50 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<jsp:useBean id="contractGroup" class="group.ContractGroup" scope="request"/>
<jsp:useBean id="clientDetail" class="client.ClientDetail" scope="request"/>
<jsp:useBean id="clientAddress" class="client.ClientAddress" scope="request"/>

<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    String pageFunction = Util.initString((String) request.getAttribute("pageFunction"), null);
    boolean turnOffEditing = false;
    if (pageFunction != null)
    {
        turnOffEditing = true;
    }

    PortalEditingException editingException = (PortalEditingException) session.getAttribute("portalEditingException");
    String editingExceptionExists = editingException == null ? "false" : "true";

    CodeTableVO[] caseTypeVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("CASETYPE");
    InputSelect caseTypeCTs = new InputSelect(caseTypeVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);
        
    CodeTableVO[] statusVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("CASESTATUS");
    InputSelect statusCTs = new InputSelect(statusVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    CodeTableVO[] stateVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("STATE");
    InputSelect stateCTs = new InputSelect(stateVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    CodeTableVO[] weekDayVOCTs = CodeTableWrapper.getSingleton().getCodeTableEntries("WEEKDAY");
    InputSelect weekDayCTs = new InputSelect(weekDayVOCTs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    String sicCode = clientDetail.getSICCodeCT();
    String sicCodeDesc = "";
    if (sicCode != null)
    {
       sicCodeDesc = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("SICCODE", sicCode);
    }

    String contractGroupNumber = Util.initString((String)request.getAttribute("contractGroupNumber"), "");

    UserSession userSession = (UserSession) session.getAttribute("userSession");

    String productAddDisabledInd = "Y";
    String linksDisabledInd = "Y";

    // As a hidden field (below), we are trying to preserve the "active" Case so that it may be
    // maintained across the set of pages without having to use a Session variable.
    ContractGroup activeCase= (ContractGroup) request.getAttribute("contractGroup");
    if (activeCase.getContractGroupPK() != null)
    {
        productAddDisabledInd = "N";
        linksDisabledInd = "N";
    }

    String origCaseStatus = Util.initString((String)request.getAttribute("origCaseStatus"), null);
    if (origCaseStatus == null)
    {
        origCaseStatus = Util.initString(activeCase.getCaseStatusCT(), "");
    }

    String contactStatus = "unchecked";
    if (!clientDetail.getContactInformations().isEmpty())
    {
        contactStatus = "checked";
    }

    String statusHistoryStatus = "unchecked";
    if (!activeCase.getCaseStatusChangeHistories().isEmpty())
    {
        statusHistoryStatus = "checked";
    }

    String enrollmentStatus = "unchecked";
    if (!activeCase.getEnrollments().isEmpty())
    {
        enrollmentStatus = "checked";
    }

    String notesStatus = "unchecked";
    if (!activeCase.getContractGroupNotes().isEmpty())
    {
        notesStatus = "checked";
    }

    String caseSetupStatus= "unchecked";
    if (!activeCase.getCaseSetups().isEmpty())
    {
        caseSetupStatus = "checked";
    }

    String shouldResetForm = Util.initString((String) request.getAttribute("resetForm"), "NO");
%>

<html>
    <head>
        <title>EDIT SOLUTIONS - Case Summary</title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"></meta>
        <meta http-equiv="Cache-Control" content="no-store"></meta>
        <meta http-equiv="Pragma" content="no-cache"></meta>
        <meta http-equiv="Expires" content="0"></meta>
        <link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet"
              type="text/css"></link>
        <%-- ****************************** BEGIN JavaScript ******************************--%>
        <script src="/PORTAL/common/javascript/commonJavascriptFunctions.js" type="text/javascript"></script>
        <script src="/PORTAL/contract/javascript/caseMainTabFunctions.js" type="text/javascript"></script>
        <script src="/PORTAL/common/javascript/widgetFunctions.js" type="text/javascript"></script>
        <script src="/PORTAL/common/javascript/scrollTable.js" type="text/javascript"></script>
        <script src="/PORTAL/common/javascript/formatDateFunctions.js" type="text/javascript"></script>
        <script src="/PORTAL/common/javascript/calendarSelector.js" type="text/javascript"></script>        
        <script type="text/javascript">

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    var turnOffEditing =  "<%= turnOffEditing %>";

    var shouldShowLockAlert = true;

    var shouldResetForm = "<%= shouldResetForm %>";

    var productAddDisabledInd = "<%= productAddDisabledInd %>";

    var linksDisabledInd = "<%= linksDisabledInd %>";
    
    var editingExceptionExists = "<%= editingExceptionExists %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        setActiveImage("case");

        f.uIEffectiveDate.focus();

        if (shouldResetForm == "YES")
        {
            resetFormValues();
        }

        checkForResponseMessage();

        if (productAddDisabledInd == "Y")
        {
            f.btnAdd.disabled = true;
        }

        // Initialize scroll tables
        initScrollTable(document.getElementById("ProductSummaryTableModelScrollTable"));

        checkForEditingException();

        var caseIsLocked = <%= userSession.getCaseIsLocked()%>;
        var username = "<%= userSession.getUsername() %>";
        var elementPK = "<%= userSession.getCasePK() %>";
		top.frames["header"].updateLockState(caseIsLocked, username, elementPK);

        shouldShowLockAlert = !caseIsLocked;

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
            alert("The Case can not be edited.");

            return false;
        }
    }

    //To get the proper form sent
    function lockCurrentTab()
    {
    	sendTransactionAction("CaseDetailTran", "lockCurrentTab", "main");
    }

    //To get the proper form sent
   function saveCurrentPage()
   {
       sendTransactionAction("CaseDetailTran", "saveCurrentPage", "main");
   }

    /**
    * Likely called from an opened dialog to re-display this page
    * after some external modifications.
    */
    function showCaseMain()
    {
        sendTransactionAction("CaseDetailTran", "showCaseMain", "_self");
    }

    function showGroupDialog()
    {
        var width = 0.80 * screen.width;
        var height = 0.50 * screen.height;

        openDialog("caseGroupSummaryDialog", "top=0,left=0,resizable=no", width,  height);
        sendTransactionAction("CaseDetailTran", "showGroupSummary", "caseGroupSummaryDialog");
    }

    function cancelCase()
    {
        sendTransactionAction("CaseDetailTran", "cancelCaseEntry", "main");
    }

	function contactInformation()
    {
        if (linksDisabledInd == "N")
        {
            var width = screen.width * .90;
            var height = screen.height * .40;

            openDialog("contactInformationDialog","left=0,top=0,resizable=no,status=yes", width, height);

            sendTransactionAction("CaseDetailTran", "showContactInformationDialog", "contactInformationDialog");
        }
        else
        {
            alert("Please Select/Save Case Before Viewing Contact Information");
        }
	}

	function statusHistory()
    {
        if (linksDisabledInd == "N")
        {
            var width = screen.width * .90;
            var height = screen.height * .40;

            openDialog("statusHistoryDialog","left=0,top=0,resizable=no,status=yes", width, height);

            sendTransactionAction("CaseDetailTran", "showCaseStatusHistoryDialog", "statusHistoryDialog");
        }
        else
        {
            alert("Please Select/Save Case Before Viewing Status History");
        }
	}

	function enrollment()
    {
        if (linksDisabledInd == "N")
        {
            var width = screen.width * .90;
            var height = screen.height * .90;

            openDialog("enrollmentDialog","left=0,top=0,resizable=no,status=yes", width, height);

            sendTransactionAction("CaseDetailTran", "showEnrollmentDialog", "enrollmentDialog");
        }
        else
        {
            alert("Please Select/Save Case Before Viewing Enrollment Information");
        }
	}

	function notes()
    {
        if (linksDisabledInd == "N")
        {
            var width = .99 * screen.width;
            var height = .90 * screen.height;

            openDialog("caseNotesDialog","left=0,top=0,resizable=no,status=yes", width, height);

            sendTransactionAction("CaseDetailTran", "showCaseNotesDialog", "caseNotesDialog");
        }
        else
        {
            alert("Please Select/Save Case Before Viewing Notes Information");
        }
	}

	function caseSetup()
    {
        if (linksDisabledInd == "N")
        {
            var width = .70 * screen.width;
            var height = .55 * screen.height;

            openDialog("caseSetupDialog","left=0,top=0,resizable=no,status=yes", width, height);

            sendTransactionAction("CaseDetailTran", "showCaseSetup", "caseSetupDialog");
        }
        else
        {
            alert("Please Select/Save Case Before Viewing Notes Information");
        }
	}

    /**
     * Shows dialog to find the desired client to begin adding the case.
     */
    function addProduct()
    {
        var width = 0.90 * screen.width;

        var height = 0.75 * screen.height;

        openDialog("caseProductAddDialog", "top=0,left=0,resizable=no", width,  height);

        sendTransactionAction("CaseDetailTran", "showCaseProductAdd", "caseProductAddDialog");
    }

    function deleteProduct()
    {
    }

    function resetFormValues()
    {
        f.statusChangeEffectiveDate.value = "";
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
    function onTableRowDoubleClick(tableId)
    {
        showMasterContractInformation();
    }

    /**
    * Shows the Master Contract information of the just-selected Product.
    */
    function showMasterContractInformation()
    {
        var width = 0.75 * screen.width;

        var height = 0.65 * screen.height;

        openDialog("masterContractInfoDialog", "top=0,left=0,resizable=no", width,  height);

        sendTransactionAction("CaseDetailTran", "showMasterContractInformation", "masterContractInfoDialog");
    }

    function checkForEditingException()
    {
        if (editingExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("exceptionDialog", "resizable=no", width, height);

            sendTransactionAction("CaseDetailTran", "showEditingExceptionDialog", "exceptionDialog");
        }
    }

    function continueTransactionAction(transaction, action)
    {
        f.ignoreEditWarnings.value = "true";
        sendTransactionAction(transaction, action, "");
    }

</script>
        <%-- ****************************** END JavaScript  ******************************--%>
    </head>
    <body class="mainTheme" onload="init()"><form name="theForm" method="post"
                                                  action="/PORTAL/servlet/RequestManager">
            <%-- ****************************** Tab Content   ******************************--%>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <jsp:include page="caseMainTabContent.jsp"
                                     flush="true"/>
                    </td>
                </tr>
            </table>
            <%-- ****************************** BEGIN Form Data ******************************--%>
            <span style="border-style:solid; border-width:1; width:100%">
                <table border="0" cellspacing="3" cellpadding="3">
                    <tr>
                        <td align="right" nowrap="nowrap"><span class="requiredField">*</span>&nbsp;Case Number:&nbsp;</td>
                        <td align="left" nowrap="nowrap">
                          <input:text name="contractGroupNumber" bean="contractGroup" attributesText="id='contractGroupNumber'" size="20"/>                                   
                        </td>
                        <td align="right" nowrap="nowrap" colspan="2">&nbsp;</td>
                        <td align="left" nowrap="nowrap">
                        	<span>Actively at Work:</span>
                        </td>
                        <td align="right" />
                    </tr>
                    <tr>
                        <td align="right" nowrap="nowrap">Case Name:&nbsp;</td>
                        <td disabled align="left" nowrap="nowrap">
                            <input:text name="name" bean="clientDetail"
                                        attributesText="id=\'name\' CONTENTEDITABLE=\'false\' REQUIRED" size="35"/>                                   
                        </td>
                        <td align="right" nowrap="nowrap">SICCode:&nbsp;</td>
                        <td align="left" nowrap="nowrap">
                            <input type="text" name="sICCodeCT" value="<%= sicCodeDesc %>" maxlength="20" disabled>
                        </td>
                        <td align="left" nowrap="nowrap" colspan="1">
                        	<input type="text" name="activeEligibilityHours" value="<%= contractGroup == null || contractGroup.getActiveEligibilityHours() == null ? "" : 
                        					contractGroup.getActiveEligibilityHours().stripTrailingZeros().toPlainString() %>" size="8"/>
                        	<span>Hours</span>
                        </td>
						<td align="left" nowrap="nowrap" />
                    </tr>
                    <tr>
                        <td align="right" nowrap="nowrap">Address 1:&nbsp;</td>
                        <td disabled align="left" nowrap="nowrap">
                            <input:text name="addressLine1" bean="clientAddress"
                                        attributesText="id='addressLine1' CONTENTEDITABLE='false'" size="35"/> 
                                   
                        </td>
                        <td align="right" nowrap="nowrap" colspan="2">&nbsp;</td>
						<td align="left" nowrap="nowrap" colspan="1">
                        	<input type="text" name="ineligibilityPeriodUnits" value="<%= contractGroup == null || contractGroup.getActiveEligibilityHours() == null ? "" : 
                        			contractGroup.getIneligibilityPeriodUnits().stripTrailingZeros().toPlainString() %>"  size="8"/>
                        	<input type="radio" name="ineligibilityPeriodType" value="<%= ContractGroup.INELIGIBILITY_PERIOD_DAYS %>"
                        		<%= contractGroup != null && contractGroup.getIneligibilityPeriodType() != null && 
                        		contractGroup.getIneligibilityPeriodType().equals(ContractGroup.INELIGIBILITY_PERIOD_DAYS) ? "checked" : "" %>> Days
                        	<input type="radio" name="ineligibilityPeriodType" value="<%= ContractGroup.INELIGIBILITY_PERIOD_MONTHS %>"
                        	<%= contractGroup != null && contractGroup.getIneligibilityPeriodType() != null &&
                        	contractGroup.getIneligibilityPeriodType().equals(ContractGroup.INELIGIBILITY_PERIOD_MONTHS) ? "checked" : "" %>> Months
                        </td>
						<td align="left" nowrap="nowrap" />
                    </tr>
                    <tr>
                        <td align="right" nowrap="nowrap">Address 2:&nbsp;</td>
                        <td disabled align="left" nowrap="nowrap">
                            <input:text name="addressLine2" bean="clientAddress"
                                        attributesText="id='addressLine2' CONTENTEDITABLE='false'" size="35"/> 
                        </td>
                        <td align="right" nowrap="nowrap">&nbsp;</td>
                        <td align="left" nowrap="nowrap">
                            <a name="contactLink" href ="javascript:contactInformation()">Contact Information&nbsp;</a>
                            <input:checkbox name="contactIndStatus" attributesText="<%= contactStatus %>" value="Y"/>
                        </td>
                        <td align="right" nowrap="nowrap">&nbsp;</td>
                        <td align="right" nowrap="nowrap">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="right" nowrap="nowrap">Address 3:&nbsp;</td>
                        <td disabled align="left" nowrap="nowrap">
                            <input:text name="addressLine3" bean="clientAddress"
                                        attributesText="id='addressLine3' CONTENTEDITABLE='false'" size="35"/> 
                        </td>
                        <td align="right" nowrap="nowrap">&nbsp;</td>
                        <td align="right" nowrap="nowrap">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="right" nowrap="nowrap">Address 4:&nbsp;</td>
                        <td disabled align="left" nowrap="nowrap">
                            <input:text name="addressLine4" bean="clientAddress"
                                        attributesText="id='addressLine4' CONTENTEDITABLE='false'" size="35"/> 
                        </td>
                        <td align="right" nowrap="nowrap">&nbsp;</td>
                        <td align="right" nowrap="nowrap">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="right" nowrap="nowrap">City:&nbsp;</td>
                        <td disabled align="left" nowrap="nowrap">
                            <input:text name="city" bean="clientAddress"
                                        attributesText="id='city' CONTENTEDITABLE='false'" size="35"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" nowrap="nowrap">State:&nbsp;</td>
                        <td disabled align="left" nowrap="nowrap">
                            <input:text name="stateCT" bean="clientAddress"
                                        attributesText="id='stateCT' CONTENTEDITABLE='false'" size="2"/>
                         </td>
                         <td align="right" nowrap="nowrap">ZipCode:&nbsp;</td>
                         <td disabled align="left" nowrap="nowrap">
                            <input:text name="zipCode" bean="clientAddress"
                                      attributesText="id='zipCode' CONTENTEDITABLE='false'" size="15"/>
                         </td>
                    </tr>
                    <tr>
                        <td align="right" nowrap="nowrap">Effective Date:&nbsp;</td>
                        <td align="left" nowrap="nowrap">
                          <input:text name="uIEffectiveDate" bean="contractGroup"
                                attributesText="id='uIEffectiveDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
                                <a href="javascript:show_calendar('f.uIEffectiveDate', f.uIEffectiveDate.value);"><img
                                   src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                                   alt="Select a date from the calendar"></a>
                        </td>
                        <td align="right" nowrap="nowrap">Termination Date:&nbsp;</td>
                        <td align="left" nowrap="nowrap">
                          <input:text name="uITerminationDate" bean="contractGroup"
                                attributesText="id='uITerminationDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
                                <a href="javascript:show_calendar('f.uITerminationDate', f.uITerminationDate.value);"><img
                                   src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                                   alt="Select a date from the calendar"></a>
                        </td>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="right" nowrap="nowrap">&nbsp;Case Association Code:&nbsp;</td>
                        <td align="left" nowrap="nowrap">
                            <input:text name="caseAssociationCode" bean="contractGroup" size="35"/>
                        </td>

                        <td colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="right" nowrap="nowrap">Case Type:&nbsp;</td>
                        <td align="left" nowrap="nowrap">
                          <input:select bean="contractGroup" name="caseTypeCT" options="<%= caseTypeCTs.getOptions() %>"
                          attributesText="id='caseTypeCT'"/>
                        </td>
                        <td colspan="4">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="right" nowrap="nowrap">&nbsp;Group Trust State:&nbsp;</td>
                        <td align="left" nowrap="nowrap">
                          <input:select bean="contractGroup" name="groupTrustStateCT" options="<%= stateCTs.getOptions() %>"
                          attributesText="id='groupTrustStateCT'"/>
                        </td>
                        <td colspan="4">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="right" nowrap="nowrap">&nbsp;Domicile State:&nbsp;</td>
                        <td align="left" nowrap="nowrap">
                          <input:select bean="contractGroup" name="domicileStateCT" options="<%= stateCTs.getOptions() %>"
                          attributesText="id='domicileStateCT'"/>
                        </td>
                        <td colspan="4">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="right" nowrap="nowrap">&nbsp;Case Status:&nbsp;</td>
                        <td align="left" nowrap="nowrap">
                          <input:select bean="contractGroup" name="caseStatusCT" options="<%= statusCTs.getOptions() %>"
                          attributesText="id='caseStatusCT'"/>
                        </td>
                        <td align="right" nowrap="nowrap">Status Change Effective Date:&nbsp;</td>
                        <td>
                            <input:text name="statusChangeEffectiveDate"
                                  attributesText="id='statusChangeEffectiveDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10'"/>
                            <a href="javascript:show_calendar('f.statusChangeEffectiveDate', f.statusChangeEffectiveDate.value);"><img
                                  src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                                  alt="Select a date from the calendar"></a>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" nowrap="nowrap">&nbsp;Requirement Notify Day:&nbsp;</td>
                        <td align="left" nowrap="nowrap">
                            <input:select bean="contractGroup" name="requirementNotifyDayCT" options="<%= weekDayCTs.getOptions() %>"
                            attributesText="id='requirementNotifyDayCT'"/></td>
                        <td colspan="4">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="right" nowrap="nowrap">CreationDate:&nbsp;</td>
                        <td disabled>
                          <input:text name="uICreationDate" bean="contractGroup"
                                      attributesText="id='uICreationDate' CONTENTEDITABLE='false'" size="10"/> 
                        </td>
                        <td align="right" nowrap="nowrap">Operator:&nbsp;</td>
                        <td disabled >
                            <input:text name="creationOperator" bean="contractGroup"
                                        attributesText="id='creationOperator' CONTENTEDITABLE='false'" size="35"/> 
                        </td>
                    </tr>
                    <tr>
                        <td align="right" nowrap="nowrap">&nbsp;</td>
                        <td align="left" nowrap="nowrap">
                            <a name="enrollmentLink" href ="javascript:enrollment()">Enrollment&nbsp;</a>
                            <input:checkbox name="enrollmentIndStatus" attributesText="<%= enrollmentStatus %>" value="Y"/>
                        </td>
                        <td align="left" nowrap="nowrap">
                            <a name="notesLink" href ="javascript:notes()">Notes&nbsp;</a>
                            <input:checkbox name="notesStatus" attributesText="<%= notesStatus %>" value="Y"/>
                        </td>
                        <td align="left" nowrap="nowrap">
                            <a name="statusHistoryLink" href ="javascript:statusHistory()">Status History&nbsp;</a>
                            <input:checkbox name="statusIndStatus" attributesText="<%= statusHistoryStatus %>" value="Y"/>
                        </td>
                        <td align="left" nowrap="nowrap">
                            <a name="caseSetupLink" href ="javascript:caseSetup()">Setup&nbsp;</a>
                            <input:checkbox name="caseSetupStatus" attributesText="<%= caseSetupStatus %>" value="Y"/>
                        </td>
                    </tr>

                </table>
<table width="100%">
                    <tr>
                        <td nowrap="nowrap" align="left">
                            <input id="btnAdd" type="button" value=" Add "
                                   onclick="addProduct()" title="Attach products to this Case."></input>
                           <input id="btnCancel" type="button" value="Cancel" onClick="cancelCase()">
                        </td>
                        <td id="productsMessage" align="left">
                            <span class="tableHeading">Products</span>
                        </td>
                        <td align="right"><span class="requiredField">* required fields</span>&nbsp;</td>

                    </tr>
</table>
            </span>
            <%-- END Form Content--%>
            <%-- ****************************** END Form Data ******************************--%>

            <%-- ****************************** BEGIN Summary Area ******************************--%>
            <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
                <jsp:param name="tableId" value="ProductSummaryTableModel"/>
                <jsp:param name="tableHeight" value="60"/>
                <jsp:param name="multipleRowSelect" value="false"/>
                <jsp:param name="singleOrDoubleClick" value="double"/>
            </jsp:include>
            <%-- ****************************** END Summary Area   ******************************--%>

            <%-- ****************************** BEGIN Hidden Variables  ******************************--%>
            <input:hidden name="transaction"/>
            <input:hidden name="action"/>
            <input type="hidden" name="contractGroupTypeCT" value="Case" CONTENTEDITABLE="false"> <!-- Default to "Case" because that is the tab we are on. -->
            <input:hidden name="contractGroupPK" bean="contractGroup"/>
            <input:hidden name="pageToShow" default="caseMain"/>
            <input type="hidden" name="pageName" value="caseMain">
            <input:hidden name="clientDetailPK" bean="clientDetail"/>
            <input type="hidden" name="origCaseStatus" value="<%= origCaseStatus %>">
            <input type="hidden" name="pageFunction" value="<%= pageFunction %>">
            <input type="hidden" name="contractGroupNumber"value="<%= contractGroupNumber %>">
            <input type="hidden" name="ignoreEditWarnings" value="">

            <%-- ****************************** END Hidden Variables ******************************--%>
        </form></body>
</html>