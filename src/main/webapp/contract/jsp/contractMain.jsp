<!--
 * User: cgleason
 * Date: Aug 27, 2004
 * Time: 1:43:03 PM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<!-- contractMain.jsp //-->

<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 edit.common.vo.user.*,
                 fission.beans.*,
                 edit.portal.exceptions.*,
                 edit.common.exceptions.VOEditException,
                 fission.utility.*,
                 edit.portal.common.session.UserSession" %>

<jsp:useBean id="contractMainSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="contractRiders"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="statusBean"
    class="fission.beans.PageBean" scope="request"/>

<%

    PortalEditingException editingException = (PortalEditingException) session.getAttribute("portalEditingException");
    String editingExceptionExists = "false";
    if (editingException != null){

        editingExceptionExists = "true";
    }

    VOEditException voEditException = (VOEditException) session.getAttribute("VOEditException");
    String voEditExceptionExists = "false";
    if (voEditException != null){

        voEditExceptionExists = "true";
    }

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

	PageBean formBean       = contractMainSessionBean.getPageBean("formBean");

    String segmentPK        = formBean.getValue("segmentPK");
    String payoutPK         = formBean.getValue("payoutPK");
    String segmentStatus    = formBean.getValue("segmentStatus");
	String riderType        = formBean.getValue("riderType");
    String companyStructureId = Util.initString(formBean.getValue("companyStructureId"), "0");
//    String policyGroupFK          = formBean.getValue("policyGroupFK");

	String amount           = Util.initString(formBean.getValue("purchaseAmount"), "0.00");
    String faceAmount       = formBean.getValue("faceAmount");
    String savingsPercent   = formBean.getValue("savingsPercent");
    String dismembermentPercent = formBean.getValue("dismembermentPercent");

	String effectiveDate   = formBean.getValue("effectiveDate");

	String startDate       = formBean.getValue("startDate");

    String appSignedDate  = formBean.getValue("appSignedDate");

    String appReceivedDate  = formBean.getValue("appReceivedDate");

    String issueDate        = formBean.getValue("issueDate");

    String terminateDate = formBean.getValue("terminateDate");

    String creationDate = formBean.getValue("creationDate");

    String creationOperator = formBean.getValue("creationOperator");

    CodeTableVO[] options   = codeTableWrapper.getCodeTableEntries("OPTIONCODE", Long.parseLong(companyStructureId));
	String   optionId	    = formBean.getValue("optionId");
    String optionKey = "";
    if (optionId != null && !optionId.equals("") && !optionId.equalsIgnoreCase("Please Select"))
    {
        optionKey = codeTableWrapper.getCodeTablePKByCodeTableNameAndCode("OPTIONCODE", optionId) + "";
    }

    CodeTableVO[] states    = codeTableWrapper.getCodeTableEntries("STATE", Long.parseLong(companyStructureId));
	String   areaId	         = formBean.getValue("areaId");
    String premiumTaxSitusOverride = formBean.getValue("premiumTaxSitusOverride");

    CodeTableVO[] statusChanges = codeTableWrapper.getCodeTableEntries("STATUSCHANGE", Long.parseLong(companyStructureId));
    String statusChangeId   = formBean.getValue("statusChange");

    CodeTableVO[] contractTypes = codeTableWrapper.getCodeTableEntries("CONTRACTTYPE");
    String contractType = formBean.getValue("contractType");

    CodeTableVO[] frequencies = codeTableWrapper.getCodeTableEntries("FREQUENCY", Long.parseLong(companyStructureId));
	String   frequencyId     = formBean.getValue("frequencyId");

    CodeTableVO[] qualNonQuals = codeTableWrapper.getCodeTableEntries("QUALNONQUAL", Long.parseLong(companyStructureId));
	String qualNonQual       = formBean.getValue("qualNonQual");

    CodeTableVO[] qualifiedTypes = codeTableWrapper.getCodeTableEntries("QUALIFIEDTYPE", Long.parseLong(companyStructureId));
	String qualifiedType     = formBean.getValue("qualifiedType");

	String certainDuration     = formBean.getValue("certainDuration");
    String lastDayOfMonthInd = formBean.getValue("lastDayOfMonthInd");

	String reduce1           = formBean.getValue("reduce1");
	String reduce2           = formBean.getValue("reduce2");

	String postJune301986InvestmentIndStatus = formBean.getValue("postJune301986InvestmentIndStatus");
	String financialIndStatus = formBean.getValue("financialIndStatus");
	String dateIndStatus = formBean.getValue("dateIndStatus");
    String notesIndStatus = formBean.getValue("notesIndStatus");
	String taxesIndStatus = formBean.getValue("taxesIndStatus");
    String depositsIndStatus = formBean.getValue("depositsIndStatus");
    String rmdIndStatus = formBean.getValue("rmdIndStatus");
    String suppIndStatus = formBean.getValue("suppIndStatus");

	String purchaseAmount            = formBean.getValue("purchaseAmount");
	String costBasis                 = formBean.getValue("costBasis");
	String exclusionRatio            = formBean.getValue("exclusionRatio");
	String amountPaidToDate          = formBean.getValue("amountPaidToDate");
	String accumulatedLoads          = formBean.getValue("accumulatedLoads");
	String withdrawlYrToDate         = formBean.getValue("withdrawlYrToDate");
	String yearlyTaxableBenefit      = formBean.getValue("yearlyTaxableBenefit");
//	String mrdAmount                 = formBean.getValue("mrdAmount");
	String lumpSumPaidToDate         = formBean.getValue("lumpSumPaidToDate");
	String paymentAmount             = formBean.getValue("paymentAmount");
	String totalExpectedReturn       = formBean.getValue("totalExpectedReturn");
	String recoveredCostBasis        = formBean.getValue("recoveredCostBasis");
	String finalDistributionAmount   = formBean.getValue("finalDistributionAmount");
	String numberOfRemainingPayments = formBean.getValue("numberOfRemainingPayments");
	String lumpSumYrToDate        	 = formBean.getValue("lumpSumYrToDate");
	String accumulatedFees        	 = formBean.getValue("accumulatedFees");
	String amountPaidYrToDate     	 = formBean.getValue("amountPaidYrToDate");
	String finalPayDate              = formBean.getValue("finalPayDate");
	String statusChangeDate          = formBean.getValue("statusChangeDate");
	String lastCheckDate             = formBean.getValue("lastCheckDate");
	String nextPaymentDate           = formBean.getValue("nextPaymentDate");
    String ownerId                   = formBean.getValue("ownerId");
    String certainPeriodEndDate      = formBean.getValue("certainPeriodEndDate");
    String lastAnnivDate             = formBean.getValue("lastAnnivDate");
    String freeAmount                = formBean.getValue("freeAmount");
    String freeAmountRemaining       = formBean.getValue("freeAmountRemaining");
    String segmentName               = formBean.getValue("segmentName");
    String annuitizationValue        = formBean.getValue("annuitizationValue");
    String dateOfDeathValue          = formBean.getValue("dateOfDeathValue");
    String suppOriginalContractNumber = formBean.getValue("suppOriginalContractNumber");
    String openClaimEndDate          = formBean.getValue("openClaimEndDate");
    String chargeCodeStatus = formBean.getValue("chargeCodeStatus");
    String ageAtIssue = formBean.getValue("ageAtIssue");
    String originalStateCT = formBean.getValue("originalStateCT");
    CodeTableVO[] ratedGenders = codeTableWrapper.getCodeTableEntries("RATEDGENDER", Long.parseLong(companyStructureId));
    String ratedGenderCT     = formBean.getValue("ratedGenderCT"); 
    
    String worksheetTypeCT = formBean.getValue("worksheetTypeCT");

	String rowToMatchBase            = optionId;

    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage == null)
    {
        errorMessage = "";
    }

    UserSession userSession = (UserSession) session.getAttribute("userSession");
%>

<html>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>
<script src="/PORTAL/common/javascript/formatDateFunctions.js"></script>
<script src="/PORTAL/common/javascript/calendarSelector.js"></script>

<script language="Javascript1.2">

    var editingExceptionExists = "<%= editingExceptionExists %>";
    var voEditExceptionExists = "<%= voEditExceptionExists %>";
    var errorMessage = "<%= errorMessage %>";
	var dialog = null;
	var f = null;
    var segmentPK = "<%= segmentPK %>";
    var origOptionId = "<%= optionKey %>";

    var shouldShowLockAlert = true;;
    var editableContractStatus = true;
    
	function init()
    {
		f = document.contractMainForm;

		top.frames["main"].setActiveTab("mainTab");

        if (errorMessage != "")
        {
            alert(errorMessage);
        }
        
        if(segmentPK == "" || segmentPK==null){
        	top.frames["header"].showSearchDialog();
        }

        var contractIsLocked = <%= userSession.getSegmentIsLocked()%>;
        var username = "<%= userSession.getUsername() %>";
        var elementPK = "<%= userSession.getSegmentPK() %>";
		top.frames["header"].updateLockState(contractIsLocked, username, elementPK);

        shouldShowLockAlert = !contractIsLocked;

        // check for terminated status to disallow contract updates
        editableContractStatus = <%= userSession.getEditableSegmentStatus()%>;
        
        for (var i = 0; i < f.elements.length; i++)
        {
            var elementType = f.elements[i].type;

            if ( (elementType == "text" || elementType == "button") && (shouldShowLockAlert == true || editableContractStatus == false) )
            {
                f.elements[i].onclick = showLockAlert;
                f.elements[i].onkeydown = showLockAlert;
            }
        }

        formatCurrency();
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

	function saveBaseRider()
    {
		prepareToSendTransactionAction("ContractDetailTran", "saveBaseRider", "contentIFrame");
	}

	function addRider()
    {
		clearForm();
	}

	function clearForm()
    {
	    f.optionId.selectedIndex       = 0;
		f.areaId.selectedIndex         = 0;
		f.frequencyId.selectedIndex    = 0;
		f.excessInterest.selectedIndex = 0;
<%--		f.mrdElection.selectedIndex    = 0;--%>

		f.effectiveDate.value   = "";
		f.startDate.value       = "";
		f.applicationDate.value = "";

		f.reduce1.value       = "";
		f.reduce2.value       = "";

		f.financialInd.checked     = false;
		f.financialIndStatus.value = "";
		f.dateInd.checked          = false;
		f.dateIndStatus.value      = "";
        f.notesInd.checked         = false;
        f.notesIndStatus.value     = "";
	    f.taxesInd.checked         = false;
		f.taxesIndStatus.value     = "";
        f.depositsInd.checked      = false;
        f.depositsIndStatus.value  = "";
        f.rmdInd.checked           = false;
        f.rmdIndStatus.value       = "";

		f.purchaseAmount.value    = "";
		f.costBasis.value         = "";
		f.exclusionRatio.value    = "";
		f.amountPaidToDate.value  = "";
		f.accumulatedLoads.value  = "";
		f.withdrawlYrToDate.value = "";
		f.yearlyTaxableBenefit.value      = "";
<%--		f.mrdAmount.value           = "";--%>
		f.lumpSumPaidToDate.value   = "";
		f.paymentAmount.value       = "";
		f.totalExpectedReturn.value = "";
		f.recoveredCostBasis.value        = "";
		f.finalDistributionAmount.value   = "";
		f.numberOfRemainingPayments.value = "";
		f.lumpSumYrToDate.value     = "";
		f.accumulatedFees.value     = "";
		f.amountPaidYrToDate.value  = "";
		f.terminateDate.value    = "";
		f.finalPayDate.value     = "";
		f.statusChangeDate.value = "";
		f.lastCheckDate.value    = "";
		f.nextPaymentDate.value  = "";
	}

	function prepareToSendTransactionAction(transaction, action, target)
    {
        if ((segmentPK != "0" &&
             segmentPK != "") &&
            f.optionId.value != origOptionId)
        {
            alert("Cannot Change Coverage on Active Contract");
        }
        else
        {
            if (f.financialInd.checked == true) {
                f.financialIndStatus.value = "checked";
            }

            if (f.dateInd.checked == true) {
                f.dateIndStatus.value = "checked";
            }

            if (f.notesInd.checked == true) {
                f.notesIndStatus.value = "checked";
            }

            if (f.taxesInd.checked == true) {
                f.taxesIndStatus.value = "checked";
            }

            if (f.postJune301986InvestmentInd.checked == true) {
                f.postJune301986InvestmentIndStatus.value = "checked";
            }

            if (f.depositsInd.checked == true) {

                f.depositsIndStatus.value = "checked";
            }

            if (f.rmdInd.checked == true) {

                f.rmdIndStatus.value = "checked";
            }

            sendTransactionAction(transaction, action, target);
        }
	}

	function selectRow()
    {
		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var rowId     = trElement.id;
		var parsedRowId = (rowId.split("_"))[1];

		var optionTd  = document.getElementById("optionId_" + parsedRowId);
		var optionId  = optionTd.innerText;

		f.selectedOptionId.value = optionId;

		prepareToSendTransactionAction("ContractDetailTran", "showRiderDetailSummary", "contentIFrame");
	}

    function cancelRiderForm()
    {
		var trElement = null;

		var rows = document.all.summaryTable.rows;

		for (var i = 0; i < rows.length; i++)
        {
			if (rows[i].selected == "true")
            {
				trElement = rows[i];
			}
		}

		if (trElement == null)
        {
			prepareToSendTransactionAction("ContractDetailTran", "cancelRiderForm", "contentIFrame");

			return;
		}

		var rowId     = trElement.id;
		var parsedRowId = (rowId.split("_"))[1];

		var optionTd  = document.getElementById("optionId_" + parsedRowId);
		var optionId  = optionTd.innerText;

		prepareToSendTransactionAction("ContractDetailTran", "cancelRiderForm", "contentIFrame");
		clearForm();
	}

	function deleteSelectedRider()
    {
		var trElement = null;

		var rows = document.all.summaryTable.rows;

		for (var i = 0; i < rows.length; i++)
        {
			if (rows[i].selected == "true")
            {
				trElement = rows[i];
			}
		}

		var rowId     = trElement.id;
		var parsedRowId = (rowId.split("_"))[1];

		var optionTd  = document.getElementById("optionId_" + parsedRowId);
		var optionId  = optionTd.innerText;

		prepareToSendTransactionAction("ContractDetailTran", "deleteSelectedRider", "contentIFrame");
		clearForm();
	}

	function financialValues()
    {
        var width = screen.width * .80;
        var height = screen.height * .70;

        openDialog("financialValues", "left=0,top=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showFinancialValues", "financialValues");
	}

	function dateValues()
    {
        var width = screen.width * .40;
        var height = screen.height * .50;

		openDialog("dateValues", "top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showDateValues", "dateValues");
	}

	function notes()
    {
        var width = .99 * screen.width;
        var height = .90 * screen.height;

		openDialog("notes", "top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showNotesDialog", "notes");
	}

	function showTaxesDialog()
    {
        var width = .50 * screen.width;
        var height = .32 * screen.height;

		openDialog("taxesDialog", "top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showTaxesDialog", "taxesDialog");
	}

	function showDepositDialog()
    {
        var width = .95 * screen.width;
        var height = .55 * screen.height;

		openDialog("depositDialog", "top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showDepositDialog", "depositDialog");
	}

	function showRMDDialog()
    {
        var width = .95 * screen.width;
        var height = .50 * screen.height;

		openDialog("rmdDialog", "top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showRMDDialog", "rmdDialog");
	}

	function showSupplementalDialog()
    {
        var width = .50 * screen.width;
        var height = .25 * screen.height;

		openDialog("supplementalDialog","top=0,left=0,resizable=no", width, height);

        prepareToSendTransactionAction("ContractDetailTran", "showSupplementalDialog", "supplementalDialog");
	}

    function checkForEditingException()
    {
        if (editingExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("exceptionDialog", "resizable=no", width, height);

            prepareToSendTransactionAction("ContractDetailTran", "showEditingExceptionDialog", "exceptionDialog");
        }
    }

    function checkForVOEditException()
    {
        if (voEditExceptionExists == "true")
        {
            var width = .75 * screen.width;
            var height = screen.height/3;

            openDialog("voEditExceptionDialog", "resizable=no", width, height);

            prepareToSendTransactionAction("ContractDetailTran", "showVOEditExceptionDialog", "voEditExceptionDialog");
        }
    }

    function continueTransactionAction(transaction, action)
    {
        f.ignoreEditWarnings.value = "true";
        prepareToSendTransactionAction(transaction, action, "contentIFrame");
    }

</script>

<head>
<title>Contract Main</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
</head>

<body class="mainTheme" onLoad="init(); checkForEditingException(); checkForVOEditException()" style="border-style:solid; border-width:1;">
<form name="contractMainForm" method="post" action="/PORTAL/servlet/RequestManager">

<input type="hidden" name="page" value="">
<jsp:include page="contractInfoHeader.jsp" flush="true"/>

  <table width="100%" height="48%" border="0" cellspacing="0" cellpadding="5">
	<tr>
	  <td nowrap align="left">EffectiveDate:&nbsp;&nbsp;
	    <input disabled type="text" name="effectiveDate" maxlength="10" size="10" value="<%= effectiveDate%>">
      </td>
      <td align="left" nowrap>Issue Date:&nbsp;
	    <input disabled type="text" name="issueDate" maxlength="10" size="10" value="<%= issueDate %>">
      </td>
      <td align="left" nowrap>Start Date:&nbsp;
           <input type="text" name="startDate" value="<%= startDate %>" size='10' maxlength="10" onKeyUp="DateFormat(this,this.value,event,false)" onBlur="DateFormat(this,this.value,event,true)">
           <a href="javascript:show_calendar('f.startDate', f.startDate.value);"><img src="/PORTAL/common/images/calendarIcon.gif" width="16" height="16" border="0" alt="Select a date from the calendar"></a>
	  </td>

    </tr>
    <tr>
      <td align="left" nowrap>Application Signed Date:&nbsp;
	    <input disabled type="text" name="appSignedDate" maxlength="10" size="10" value="<%= appSignedDate%>">
      </td>
      <td align="left" nowrap>Application Received Date:&nbsp;
	    <input disabled type="text" name="appReceivedDate" maxlength="10" size="10" value="<%= appReceivedDate%>">
      </td>
    </tr>
    <tr>
	  <td nowrap align="left">Annuity Option:
	    <select name="optionId">
          <option selected value="Please Select"> Please Select </option>
         	<%

              for(int i = 0; i < options.length; i++) {

                  String codeTablePK = options[i].getCodeTablePK() + "";
                  String codeDesc    = options[i].getCodeDesc();
                  String code        = options[i].getCode();

                  if (optionId.equalsIgnoreCase(code)) {

                      out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                  }
                  else  {

                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
              }

         	%>
        </select>
      </td>
      <td nowrap align="left">Certain Period:&nbsp;
		<input type="text" name="certainDuration" maxlength="2" size="2" value="<%= certainDuration %>">
      </td>
      <td nowrap align="left">Frequency:&nbsp;&nbsp;&nbsp;
		<select name="frequencyId">
          <option> Please Select </option>
          	<%
              for(int i = 0; i < frequencies.length; i++) {

                  String codeTablePK = frequencies[i].getCodeTablePK() + "";
                  String codeDesc    = frequencies[i].getCodeDesc();
                  String code        = frequencies[i].getCode();

                  if (frequencyId.equalsIgnoreCase(code)) {

                      out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                  }
                  else  {

                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
              }

		    %>
		</select>
      </td>
	</tr>
    <tr>
      <td nowrap align="left">Qual/NonQual:&nbsp;
        <select name="qualNonQual" tabindex="11">
          <option> Please Select </option>
          <%
              for(int i = 0; i < qualNonQuals.length; i++) {

                  String codeTablePK = qualNonQuals[i].getCodeTablePK() + "";
                  String codeDesc    = qualNonQuals[i].getCodeDesc();
                  String code        = qualNonQuals[i].getCode();

                 if (qualNonQual.equalsIgnoreCase(code)) {

                     out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
              }
          %>
        </select>
      </td>
      <td align="left" nowrap>Rated Gender:&nbsp;
        <select  name="ratedGenderCT">
	      <option value="Please Select">Please Select</option>
	        <%
                for(int i = 0; i < ratedGenders.length; i++)
                {
                    String codeTablePK = ratedGenders[i].getCodeTablePK() + "";
                    String codeDesc    = ratedGenders[i].getCodeDesc();
                    String code        = ratedGenders[i].getCode();

                    if (ratedGenderCT.equalsIgnoreCase(code))
                    {
                        out.println("<option selected name=\"id\" value=\"" + code+ "\">" + codeDesc + "</option>");
                    }
                    else
                    {
                        out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                    }
                }
	       %>
      </td>
      <td nowrap align="left">Qualified Type:
        <select name="qualifiedType" tabindex="12">
          <option> Please Select </option>
          <%
              for(int i = 0; i < qualifiedTypes.length; i++) {

                  String codeTablePK = qualifiedTypes[i].getCodeTablePK() + "";
                  String codeDesc    = qualifiedTypes[i].getCodeDesc();
                  String code        = qualifiedTypes[i].getCode();

                 if (qualifiedType.equalsIgnoreCase(code)) {

                     out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                 }
              }
          %>
        </select>
      </td>
	  <td nowrap align="left">Post June 30 1986 Investment:&nbsp;
		<input type="checkbox" name="postJune301986InvestmentInd" <%=postJune301986InvestmentIndStatus %>>
	  </td>
    </tr>
    <tr>
	  <td nowrap  align="left">Excess Interest:
	    <select name="excessInterest">
       	  <option> Please Select </option>
       		<%
//		  		for(int i = 0; i < excessInterestCodes.length; i++) {
//
//		 		    if (excessInterest.equalsIgnoreCase(excessInterestCodes[i])) {
//
//		 		 		out.println("<option selected name=\"id\" value=\"" + excessInterestCodes[i] + "\">" + excessInterests[i] + "</option>");
//		 			}
//		 			else  {
//
//		 		 		out.println("<option name=\"id\" value=\"" + excessInterestCodes[i] + "\">" + excessInterests[i] + "</option>");
//		 			}
//		 	    }
		   	%>
		</select>
	  </td>
	  <td nowrap align="left">Reduce %1:&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="text" name="reduce1" size="7" value="<%= reduce1%>" maxlength="9">
	  </td>
	  <td nowrap align="left">Reduce %2:&nbsp;
 		<input type="text" name="reduce2" size="7" value="<%= reduce2%>" maxlength="9">
	  </td>
	</tr>
	<tr>
	  <td nowrap align="left">Issue State:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <select name="areaId">
          <option> Please Select </option>
            <%
              for(int i = 0; i < states.length; i++) {

                  String codeTablePK = states[i].getCodeTablePK() + "";
                  String codeDesc    = states[i].getCodeDesc();
                  String code        = states[i].getCode();

                  if (areaId.equalsIgnoreCase(code)) {

                      out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                  }
                  else  {

                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
              }
            %>
        </select>
	  </td>
	  <td nowrap align="left">Status Change:
        <select name="statusChange">
          <option> Please Select </option>
            <%
              for(int i = 0; i < statusChanges.length; i++) {

                  String codeTablePK = statusChanges[i].getCodeTablePK() + "";
                  String codeDesc    = statusChanges[i].getCodeDesc();
                  String code        = statusChanges[i].getCode();

                  if (statusChangeId.equalsIgnoreCase(code)) {

                      out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                  }
                  else  {

                      out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                  }
              }
            %>
        </select>
      </td>
      <td align="left" nowrap>Contract Type:&nbsp;
        <select name="contractType">
          <option value="null">Please Select</option>
          <%
              for(int i = 0; i < contractTypes.length; i++) {

                  String codeDesc    = contractTypes[i].getCodeDesc();
                  String code        = contractTypes[i].getCode();

                 if (contractType.equalsIgnoreCase(code)) {

                     out.println("<option selected name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                 }
                 else  {

                     out.println("<option name=\"id\" value=\"" + code + "\">" + codeDesc + "</option>");
                 }
              }
          %>
        </select>
      </td>
	</tr>
    <tr>
      <td nowrap align="left">Creation Date:&nbsp;
        <input disabled type="text" name="creationDate" maxlength="10" size="10" value="<%= creationDate %>">
      </td>
	  <td nowrap align="left">Creation Operator:&nbsp;
        <input disabled type="text" name="creationOperator" maxlength="15" size="15" value="<%= creationOperator %>">
      </td>
    </tr>
	<tr>
	  <td nowrap colspan="6">
		<input disabled type="checkbox" name="financialInd" <%=financialIndStatus %>><a href ="javascript:financialValues()">Financial</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input disabled type="checkbox" name="dateInd" <%=dateIndStatus %>><a href ="javascript:dateValues()">Dates</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input disabled type="checkbox" name="notesInd" <%=notesIndStatus %>><a href ="javascript:notes()">Notes</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input disabled type="checkbox" name="taxesInd" <%=taxesIndStatus %>><a href ="javascript:showTaxesDialog()">Taxes</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input disabled type="checkbox" name="depositsInd" <%= depositsIndStatus %>><a href ="javascript:showDepositDialog()">Deposits</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input disabled type="checkbox" name="rmdInd" <%= rmdIndStatus %>><a href ="javascript:showRMDDialog()">RMD</a>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input disabled type="checkbox" name="supplementalInd" <%= suppIndStatus %>><a href = "javascript:showSupplementalDialog()">Supplemental</a>
	  </td>
	</tr>
	<tr>
	  <td nowrap colspan="3">
	  </td>
	</tr>
  </table>
  <table width="100%" height="2%" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td align="left">
		<input type="button" name="add" value="   Add   " onClick="addRider()">
		<input type="button" name="save" value=" Save " onClick="saveBaseRider()">
		<input type="button" name="cancel" value="Cancel" onClick="cancelRiderForm()">
		<input type="button" name="Delete" value="Delete" onClick="deleteSelectedRider()">
	  </td>
	</tr>
  </table>
  <table class="summary" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr class="heading">
      <th width="25%" align="left">Coverage</th>
      <th width="25%" align="left">Effective Date</th>
      <th width="25%" align="left">Amount</th>
	  <th width="25%" align="left">End Date</th>
    </tr>
  </table>
  <span class="scrollableContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:30%; top:0; left:0; background-color:#BBBBBB">
    <table class="summary" id="contractMainSummary" width="100%" style="border-style:solid; border-width:0;" cellspacing="0" cellpadding="0">
      <%
          int rowId   = 0;

          String rowToMatch = "";
          String trClass = "";
          boolean selected = false;

          String endDate = terminateDate;

          rowToMatch =  optionId;

          if (rowToMatch.equals(rowToMatchBase) &&
              !rowToMatchBase.equals(""))
          {
              trClass = "highlighted";
              selected = true;
          }
          else
          {
              trClass = "default";
              selected = false;
          }
      %>
        <tr class="<%= trClass %>" isSelected="<%= selected %>" id="rowId_<%= rowId %>"  onClick="selectRow()"
            onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
          <td width="25%" nowrap id="optionId_<%= rowId %>">
            <%= optionId %>
          </td>
          <td width="25%" nowrap id="effectiveDateId_<%= rowId %>">
            <%= effectiveDate %>
          </td>
          <td width="25%" nowrap id="amountId_<%= rowId %>">
            <%
                if (amount.equals("0.00"))
                {
            %>
            &nbsp;
            <%
                }
                else
                {
            %>
            <script>document.write(formatAsCurrency(<%= amount %>))</script>
            <%
                }
            %>
          </td>
          <td width="25%" nowrap id="endDateId_<%= rowId %>">
            <%= endDate %>
          </td>
        </tr>
      <%

          Map allRiders = contractRiders.getPageBeans();

          Iterator it = allRiders.values().iterator();

          while (it.hasNext())  {

              rowId++;

              PageBean rider = (PageBean)it.next();

              optionId      = rider.getValue("optionId");
              effectiveDate = "";
              String riderEffDate = rider.getValue("effectiveDate");
              amount        = rider.getValue("amount");
              endDate       = "";
              String riderEndDate = rider.getValue("endDate");

              rowToMatch =  optionId;

              if (rowToMatch.equals(rowToMatchBase) &&
                  !rowToMatchBase.equals(""))
              {
                  trClass = "highlighted";
                  selected = true;
              }
              else
              {
                  trClass = "default";
                  selected = false;
              }
      %>
        <tr class="<%= trClass %>" isSelected="<%= selected %>" id="rowId_<%= rowId %>"  onClick="selectRow()"
            onMouseOver="highlightRow()" onMouseOut="unhighlightRow()">
          <td width="25%" nowrap id="optionId_<%= rowId %>">
            <%= optionId %>
          </td>
          <td width="25%" nowrap id="effectiveDateId_<%= rowId %>">
            <%= riderEffDate %>
          </td>
          <td width="25%" nowrap id="amountId_<%= rowId %>">
            <%
                if (amount.equals("0.00"))
                {
            %>
            &nbsp;
            <%
                }
                else
                {
            %>
            <script>document.write(formatAsCurrency(<%= amount %>))</script>
            <%
                }
            %>
          </td>
          <td width="25%" nowrap id="endDateId_<%= rowId %>">
            <%= riderEndDate %>
          </td>
        </tr>
      <%
          }// end while
      %>
    </table>
  </span>

<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction"  value="">
 <input type="hidden" name="action"       value="">
 <input type="hidden" name="selectedOptionId" value="">
 <input type="hidden" name="companyStructureId" value="<%= companyStructureId %>">

 <input type="hidden" name="financialIndStatus" value="<%= financialIndStatus %>">
 <input type="hidden" name="investmentIndStatus" value="">
 <input type="hidden" name="postJune301986InvestmentIndStatus" value="<%= postJune301986InvestmentIndStatus %>">
 <input type="hidden" name="dateIndStatus" value="<%= dateIndStatus %>">
 <input type="hidden" name="notesIndStatus" value="<%= notesIndStatus %>">
 <input type="hidden" name="taxesIndStatus" value="<%= taxesIndStatus %>">
 <input type="hidden" name="depositsIndStatus" value="<%= depositsIndStatus %>">
 <input type="hidden" name="rmdIndStatus" value="<%= rmdIndStatus %>">


 <input type="hidden" name="segmentPK" value="<%= segmentPK %>">
 <input type="hidden" name="payoutPK" value="<%= payoutPK %>">
 <input type="hidden" name="segmentStatus" value = "<%= segmentStatus %>">

 <!--- *****Dialog Values for Financial*****//-->
 <input type="hidden" name="purchaseAmount" value="<%=purchaseAmount %>">
 <input type="hidden" name="faceAmount" value="<%= faceAmount %>">
 <input type="hidden" name="savingsPercent" value="<%= savingsPercent %>">
 <input type="hidden" name="dismembermentPercent" value="<%= dismembermentPercent %>">
 <input type="hidden" name="costBasis" value="<%=costBasis %>">
 <input type="hidden" name="exclusionRatio" value="<%=exclusionRatio %>">
 <input type="hidden" name="amountPaidToDate" value="<%=amountPaidToDate %>">
 <input type="hidden" name="accumulatedLoads" value="<%=accumulatedLoads %>">
 <input type="hidden" name="withdrawlYrToDate" value="<%=withdrawlYrToDate %>">
 <input type="hidden" name="yearlyTaxableBenefit" value="<%=yearlyTaxableBenefit %>">
<%-- <input type="hidden" name="mrdAmount" value="<%=mrdAmount %>">--%>
 <input type="hidden" name="lumpSumPaidToDate" value="<%=lumpSumPaidToDate %>">

 <input type="hidden" name="paymentAmount" value="<%=paymentAmount %>">
 <input type="hidden" name="totalExpectedReturn" value="<%=totalExpectedReturn %>">
 <input type="hidden" name="recoveredCostBasis" value="<%=recoveredCostBasis %>">
 <input type="hidden" name="finalDistributionAmount" value="<%=finalDistributionAmount %>">
 <input type="hidden" name="numberOfRemainingPayments" value="<%=numberOfRemainingPayments %>">
 <input type="hidden" name="lumpSumYrToDate" value="<%=lumpSumYrToDate %>">
 <input type="hidden" name="accumulatedFees" value="<%=accumulatedFees %>">
 <input type="hidden" name="amountPaidYrToDate" value="<%=amountPaidYrToDate %>">
 <input type="hidden" name="ownerId" value="<%= ownerId %>">
 <input type="hidden" name="freeAmount" value="<%= freeAmount %>">
 <input type="hidden" name="freeAmountRemaining" value="<%= freeAmountRemaining %>">
<%-- <input type="hidden" name="policyGroupFK" value="<%= policyGroupFK %>">--%>

 <!--- *****Dialog Values for Dates*****//-->
 <input type="hidden" name="effectiveDate" value="<%= effectiveDate %>">
 <input type="hidden" name="terminateDate" value="<%= terminateDate %>">
 <input type="hidden" name="finalPayDate" value="<%=finalPayDate %>">
 <input type="hidden" name="statusChangeDate" value="<%=statusChangeDate %>">
 <input type="hidden" name="lastCheckDate" value="<%=lastCheckDate %>">
 <input type="hidden" name="nextPaymentDate" value="<%=nextPaymentDate %>">
 <input type="hidden" name="certainPeriodEndDate" value="<%= certainPeriodEndDate %>">
 <input type="hidden" name="lastAnnivDate" value="<%= lastAnnivDate %>">
 <input type="hidden" name="appSignedDate" value="<%= appSignedDate %>">
 <input type="hidden" name="appReceivedDate" value="<%= appReceivedDate %>">
 <input type="hidden" name="issueDate" value="<%= issueDate %>">
 <input type="hidden" name="ignoreEditWarnings" value="">
 <input type="hidden" name="creationDate" value="<%= creationDate %>">
 <input type="hidden" name="creationOperator" value="<%= creationOperator %>">
 <input type="hidden" name="guidelineSinglePrem" value="">
 <input type="hidden" name="guidelineLevelPrem" value="">
 <input type="hidden" name="tamra" value="">
 <input type="hidden" name="tamraStartDate" value="">
 <input type="hidden" name="MAPEndDate" value="">
 <input type="hidden" name="mecGuidelineLevelPremium" value="">
 <input type="hidden" name="mecGuidelineSinglePremium" value="">
 <input type="hidden" name="cumGuidelineLevelPremium" value="">
 <input type="hidden" name="segmentName" value="<%= segmentName %>">
 <input type="hidden" name="annuitizationValue" value="<%= annuitizationValue %>">
 <input type="hidden" name="dateOfDeathValue" value="<%= dateOfDeathValue %>">
 <input type="hidden" name="suppOriginalContractNumber" value="<%= suppOriginalContractNumber %>">
 <input type="hidden" name="openClaimEndDate" value="<%= openClaimEndDate %>">
 <input type="hidden" name="chargeCodeStatus" value="<%= chargeCodeStatus %>">
 <input type="hidden" name="premiumTaxSitusOverride" value="<%= premiumTaxSitusOverride %>">
 <input type="hidden" name="lastDayOfMonthInd" value="<%= lastDayOfMonthInd %>">
 <input type="hidden" name="ageAtIssue" value="<%= ageAtIssue %>">
 <input type="hidden" name="originalStateCT" value="<%= originalStateCT %>">
 <input type="hidden" name="ratedGenderCT" value="<%= ratedGenderCT %>">
 <input type="hidden" name="worksheetTypeCT" value="<%= worksheetTypeCT %>">

 <!-- recordPRASEEvents is set by the toolbar -->
 <input type="hidden" name="recordPRASEEvents" value="false">
</form>

</body>
</html>
