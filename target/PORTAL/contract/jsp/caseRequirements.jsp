<%@ taglib uri="/WEB-INF/taglibs-input.tld" prefix="input" %>
<!-- ****** JAVA CODE ***** //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 fission.utility.*,
                 edit.portal.common.session.UserSession,
                 edit.services.db.hibernate.SessionHelper,
                 edit.portal.taglib.InputSelect" %>

<jsp:useBean id="contractGroupRequirement" class="group.ContractGroupRequirement" scope="request"/>
<jsp:useBean id="requirement" class="contract.Requirement" scope="request"/>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");
    
    CodeTableVO[] requirementStatusVOs = CodeTableWrapper.getSingleton().getCodeTableEntries("REQUIREMENTSTATUS");
    InputSelect requirementStatusCTs = new InputSelect(requirementStatusVOs, CodeTableVO.class, new String[]{"codeDesc"}, "code", InputSelect.ASCENDING);

    String shouldResetForm = Util.initString((String) request.getAttribute("resetForm"), "NO");

    String batchID = Util.initString((String) request.getAttribute("batchID"), "");

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Requirements</title>
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

    var shouldShowLockAlert = true;

    var shouldResetForm = "<%= shouldResetForm %>";

	function init()
    {
		f = document.theForm;

        setActiveImage("requirements");

        checkForResponseMessage();

        if (shouldResetForm == "YES")
        {
            resetForm();
        }


        var caseRequirementIsLocked = <%= userSession.getCaseRequirementIsLocked()%>;
        var username = "<%= userSession.getUsername() %>";
        var elementPK = "<%= userSession.getCaseRequirementPK() %>";
		top.frames["header"].updateLockState(caseRequirementIsLocked, username, elementPK);

        shouldShowLockAlert = !caseRequirementIsLocked;

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

    function showLockAlert(){

        if (shouldShowLockAlert == true){

            alert("The Case Requirement can not be edited.");

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
        if (f.requirementId.value == "")
        {
            alert("Requirement Must Be Selected For Save");
        }
        else
        {
            if (isReceivedDateEnteredForReceivedStatus() && isStatusReceivedIfReceivedDateEntered(f.receivedDate.value))
            {
                sendTransactionAction("CaseDetailTran", "saveCurrentPage", "main");
            }
        }
    }


	function cancelRequirement()
    {
        sendTransactionAction("CaseDetailTran", "cancelRequirement", "main");
	}


    /**
     For Status = 'Received' the ReceivedDate must be entered.
     */
    function isReceivedDateEnteredForReceivedStatus()
    {
        if (f.requirementStatusCT.value == 'Received')
        {
            if (valueIsEmpty(f.receivedDate.value))
            {
                alert('Status = Received. Received Date Required.');
                return false;
            }
        }

        return true;
    }

    /**
     If ReceivedDate is entered the status should be 'Received
     */
    function isStatusReceivedIfReceivedDateEntered(receivedDate)
    {
        if (!valueIsEmpty(receivedDate) && (f.requirementStatusCT.value != 'Received'))
        {
            alert('Received Date is Entered. Status Invalid.');
            return false;
        }

        return true;
    }

    /*
    Callback method from TableModel implemenation.
    */
    function onTableRowSingleClick(tableId)
    {
        showContractGroupRequirementDetail();
    }

    /**
    * Shows the detail of the just-selected ContractGroupRequirement.
    */
    function showContractGroupRequirementDetail()
    {
        sendTransactionAction("CaseDetailTran", "showContractGroupRequirementDetail", "main");
    }
</script>

</head>

<body class="mainTheme" onLoad="init()">
<form name= "theForm" method="post" action="/PORTAL/servlet/RequestManager">

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
    <tr>
      <td align="right" nowrap>Batch Id:&nbsp;</td>
	  <td align="left" nowrap>
            <input type="text" name="batchId" value="<%= batchID %>" id='batchId' disabled>
	  </td>
      <td align="right" nowrap>Requirement Id:&nbsp;</td>
	  <td align="left" nowrap>
            <input:text name="requirementId" bean="requirement" attributesText="id='requirementId' REQUIRED CONTENTEDITABLE=\'false\'" size="5"/>
	  </td>
    </tr>
    <tr>
      <td align="right" nowrap>Requirement Description:&nbsp;</td>
      <td align="left" colspan="3" nowrap>
        <input:text name="requirementDescription" bean="requirement" attributesText="id='requirementDescription' REQUIRED CONTENTEDITABLE=\'false\'" size="50"/>
      </td>
        <td align="right" nowrap>Effective Date:&nbsp;</td>
        <td align="left" nowrap>
                <input:text name="uIEffectiveDate" bean="contractGroupRequirement"
                      attributesText="id='uIEffectiveDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10' REQUIRED"/>
                      <a href="javascript:show_calendar('f.uIEffectiveDate', f.uIEffectiveDate.value);"><img
                         src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                         alt="Select a date from the calendar"></a>
        </td>
    </tr>
    <tr>
      <td align="right" nowrap>Status:&nbsp;</td>
      <td align="left" nowrap>
        <input:select bean="contractGroupRequirement" name="requirementStatusCT" options="<%= requirementStatusCTs.getOptions() %>"
                          attributesText="id='requirementStatusCT' REQUIRED"/>
		</td>
        <td align="right" nowrap>Received Date:&nbsp;</td>
        <td align="left" nowrap>
                <input:text name="uIReceivedDate" bean="contractGroupRequirement"
                      attributesText="id='uIReceivedDate' onBlur='DateFormat(this, this.value, event, true)' onKeyUp='DateFormat(this, this.value, event, false)' maxlength='10' size='10' REQUIRED"/>
                      <a href="javascript:show_calendar('f.uIReceivedDate', f.uIReceivedDate.value);"><img
                         src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0"
                         alt="Select a date from the calendar"></a>          
        </td>

    </tr>
    <tr>
      <td align="right" nowrap colspan="4">&nbsp;</td>
    </tr>

</table>
<table>
	<tr>
	    <td nowrap align="left">
<%--		  <input type="button" value="   Save  " style="background-color:#DEDEDE" onClick="saveRequirementToSummary()">--%>
		  <input type="button" value="  Cancel " style="background-color:#DEDEDE" onClick="cancelRequirement()">
	    </td>
	</tr>
</table>
</span>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Summary Area ****************************** --%>

<jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
    <jsp:param name="tableId" value="CaseRequirementsTableModel"/>
    <jsp:param name="tableHeight" value="60"/>
    <jsp:param name="multipleRowSelect" value="false"/>
    <jsp:param name="singleOrDoubleClick" value="single"/>
</jsp:include>

<%-- ****************************** END Summary Area ****************************** --%>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">
 <input type="hidden" name="pageToShow">

 <input:hidden name="contractGroupRequirementPK" bean="contractGroupRequirement"/>
 <input:hidden name="contractGroup" bean="contractGroupRequirement"/>
 <input:hidden name="filteredRequirement" bean="contractGroupRequirement"/>
 <input type="hidden" name="pageName" value="requirement">
 <input type="hidden" name="pageToShow" value="requirements"/>

</form>
</body>
</html>






