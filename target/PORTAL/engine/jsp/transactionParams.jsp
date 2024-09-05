<!-- ****** JAVA CODE ***** //-->

<%@ page import="java.util.*,
                 edit.common.vo.*,
                 edit.common.*"%>

<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<jsp:useBean id="codeTableBean"
    class="fission.beans.SessionBean" scope="session"/>    
	
<jsp:useBean id="paramBean"
    class="fission.beans.SessionBean" scope="session"/>
	
<jsp:useBean id="pageBean"
    class="fission.beans.PageBean" scope="request"/>	
<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

    CodeTableVO[] stateCTVO = codeTableWrapper.getCodeTableEntries("STATE");
    CodeTableVO[] genderCTVO = codeTableWrapper.getCodeTableEntries("GENDER");
    CodeTableVO[] classCTVO  = codeTableWrapper.getCodeTableEntries("CLASS");
    CodeTableVO[] optionCTVO = codeTableWrapper.getCodeTableEntries("OPTIONCODE");
    CodeTableVO[] frequencyCTVO = codeTableWrapper.getCodeTableEntries("FREQUENCY");
    CodeTableVO[] lifeRelationCTVO = codeTableWrapper.getCodeTableEntries("LIFERELATIONTYPE");
    CodeTableVO[] disbursementCTVO = codeTableWrapper.getCodeTableEntries("DISBURSESOURCE");
    CodeTableVO[] taxFilingStatusCTVO = codeTableWrapper.getCodeTableEntries("TAXFILINGSTATUS");
    CodeTableVO[] trxTypeCTVO = codeTableWrapper.getCodeTableEntries("TRXTYPE");
    CodeTableVO[] netGrossCTVO = codeTableWrapper.getCodeTableEntries("NETGROSSIND");

    // For the drop downs... These should always have values
	String   areaId	           = pageBean.getValue("areaId");
	if (areaId == null || areaId.length() == 0) {

        areaId = "";
    }
				
	String genderId 	       = pageBean.getValue("genderId");
	if (genderId == null || genderId.length() == 0) {

        genderId = "";
    }

	String classId 	           = pageBean.getValue("classId");
	if (classId == null || classId.length() == 0) {

        classId = "";
    }
	
	String annuityOptionId 	   = pageBean.getValue("annuityOptionId");
	if (annuityOptionId == null || annuityOptionId.length() == 0) {

        annuityOptionId = "";
    }
	
	String frequencyId 	       = pageBean.getValue("frequencyId");
	if (frequencyId == null || frequencyId.length() == 0) {

        frequencyId = "";
    }

	String relationshipId 	   = pageBean.getValue("relationshipId");
	if (relationshipId == null || relationshipId.length() == 0) {

        relationshipId = "";
    }
	
	String disburseSourceId    = pageBean.getValue("disburseSourceId");
	if (disburseSourceId == null || disburseSourceId.length() == 0) {

        disburseSourceId = "";
    }
	
	String taxFilingStatusId              = pageBean.getValue("taxFilingStatusId");
    if (taxFilingStatusId == null || taxFilingStatusId.length() == 0) {

        taxFilingStatusId = "";
    }

	String[] fundNamesCodeIds = paramBean.getValues("fundNamesCodes");
	String[] fundNamesCodes   = paramBean.getValues("fundNamesCodeDescriptions");	String fund1         = pageBean.getValue("fund1");
	if (fund1 == null || fund1.length() == 0){fund1 = "";}

	String fund2         = pageBean.getValue("fund2");	
	if (fund2 == null || fund2.length() == 0){fund2 = "";}

	String fund3         = pageBean.getValue("fund3");	
	if (fund3 == null || fund3.length() == 0){fund3 = "";}

	String fund4         = pageBean.getValue("fund4");	
	if (fund4 == null || fund4.length() == 0){fund4 = "";}	
	
	String trxTypeId         = pageBean.getValue("trxTypeId");
    if (trxTypeId == null || trxTypeId.length() == 0) {

        trxTypeId = "";
    }
	
	String netGrossId         = pageBean.getValue("netGrossId");
	if (netGrossId == null || netGrossId.length() == 0) {

        netGrossId = "";
    }

	String[] selectedRiders = paramBean.getValues("selectedRiders");
	if (selectedRiders == null) {selectedRiders = new String[0];}
	

	// There is always a set mode
	// projection mode is session scope 
	// and edit mode is request scope
	
	String projectionMode = paramBean.getValue("projectionMode");
	
    String editMode = pageBean.getValue("editMode");
	
	String enableRidersLink = paramBean.getValue("enableRidersLink");
	
	String enableCancelButton = paramBean.getValue("enableCancelButton");
	
	// This page supports Base Params, Debug, and Riders.
	// We need to know if we are working with a Rider.
	
	String rider = pageBean.getValue("rider");
	if (rider == null) {rider = "";}
    
    String financialType = pageBean.getValue("financialType");
    if (financialType == null) {financialType = "";}
	
	String selectedIndex = pageBean.getValue("selectedIndex");
	if (selectedIndex == null) {selectedIndex = "";}
	
	String keyIsSet	 	  = paramBean.getValue("keyIsSet");		
	if (keyIsSet == null || keyIsSet.length() == 0){keyIsSet = "false";}	
	
	// For textfields...
	String effectiveDateDay = pageBean.getValue("effectiveDateDay");
	if (effectiveDateDay == null || effectiveDateDay.length() == 0) {effectiveDateDay = "";}
		
	String effectiveDateMonth = pageBean.getValue("effectiveDateMonth");
	if (effectiveDateMonth == null || effectiveDateMonth.length() == 0) {effectiveDateMonth = "";}
	
	String effectiveDateYear = pageBean.getValue("effectiveDateYear");
	if (effectiveDateYear == null || effectiveDateYear.length() == 0) {effectiveDateYear = "";}
		
	String issueAge = pageBean.getValue("issueAge");
	if (issueAge == null || issueAge.length() == 0) {issueAge = "0";}	
	
	String grossAmount = pageBean.getValue("grossAmount");
	if (grossAmount == null || grossAmount.length() == 0) {grossAmount = "0";}
	
	String payeeAllocation = pageBean.getValue("payeeAllocation");
	if (payeeAllocation == null || payeeAllocation.length() == 0) {payeeAllocation = "1.00";}	
	
	String yearlyTaxableBenefit = pageBean.getValue("yearlyTaxableBenefit");
	if (yearlyTaxableBenefit == null || yearlyTaxableBenefit.length() == 0) {yearlyTaxableBenefit = "0.00";}	
	
	String exclusionRatio = pageBean.getValue("exclusionRatio");
	if (exclusionRatio == null || exclusionRatio.length() == 0) {exclusionRatio = "0.00";}	
	
	String recoveredCostBasis = pageBean.getValue("recoveredCostBasis");
	if (recoveredCostBasis == null || recoveredCostBasis.length() == 0) {recoveredCostBasis = "0.00";}	
	
	String fedWithholdingOvrd = pageBean.getValue("fedWithholdingOvrd");
	if (fedWithholdingOvrd == null || fedWithholdingOvrd.length() == 0) {fedWithholdingOvrd = "0.00";}	
	
	String stateWithholdingOvrd = pageBean.getValue("stateWithholdingOvrd");
	if (stateWithholdingOvrd == null || stateWithholdingOvrd.length() == 0) {stateWithholdingOvrd = "0.00";}	
	
	String cityWithholdingOvrd = pageBean.getValue("cityWithholdingOvrd");
	if (cityWithholdingOvrd == null || cityWithholdingOvrd.length() == 0) {cityWithholdingOvrd = "0.00";}	
	
	String countyWithholdingOvrd = pageBean.getValue("countyWithholdingOvrd");
	if (countyWithholdingOvrd == null || countyWithholdingOvrd.length() == 0) {countyWithholdingOvrd = "0.00";}	
	
	String paymentAmount = pageBean.getValue("paymentAmount");
	if (paymentAmount == null || paymentAmount.length() == 0) {paymentAmount = "0.00";}	
	
	String costBasis = pageBean.getValue("costBasis");
	if (costBasis == null || costBasis.length() == 0) {costBasis = "0.00";}	

	String fedWithholdingInd = pageBean.getValue("fedwithholdingInd");
	String checkedFedWithholdingInd = "UNCHECKED";
	if(fedWithholdingInd.equals("Y"))
	{
		checkedFedWithholdingInd = "CHECKED";
	}
	else
	{
		checkedFedWithholdingInd = "UNCHECKED";
	}

	String stateWithholdingInd = pageBean.getValue("statewithholdingInd");
	String checkedStateWithholdingInd = "CHECKED";
	if(stateWithholdingInd.equals("Y"))
	{
		checkedStateWithholdingInd = "CHECKED";
	}
	else
	{
		checkedStateWithholdingInd = "UNCHECKED";
	}

	String cityWithholdingInd = pageBean.getValue("citywithholdingInd");
	String checkedCityWithholdingInd = "UNCHECKED";

	if(cityWithholdingInd.equals("Y"))
	{
		checkedCityWithholdingInd = "CHECKED";
	}
	else
	{
		checkedCityWithholdingInd = "UNCHECKED";
	}

	String countyWithholdingInd = pageBean.getValue("countywithholdingInd");
	String checkedCountyWithholdingInd = "UNCHECKED";

	if(countyWithholdingInd.equals("Y"))
	{
		checkedCountyWithholdingInd = "CHECKED";
	}
	else
	{
		checkedCountyWithholdingInd = "UNCHECKED";
	}
	
	String allocationPct1 = pageBean.getValue("allocationPct1");
	if (allocationPct1 == null || allocationPct1.length() == 0) {allocationPct1 = "";}	
	
	String allocationPct2 = pageBean.getValue("allocationPct2");
	if (allocationPct2 == null || allocationPct2.length() == 0) {allocationPct2 = "";}	
	
	String allocationPct3 = pageBean.getValue("allocationPct3");
	if (allocationPct3 == null || allocationPct3.length() == 0) {allocationPct3 = "";}	
	
	String allocationPct4 = pageBean.getValue("allocationPct4");
	if (allocationPct4 == null || allocationPct4.length() == 0) {allocationPct4 = "";}	
	
%>

<html>
<head>
<title>PRASE - Transaction Parameter Entry Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/ENGINE.css" rel="stylesheet" type="text/css">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="JavaScript">

    var keyIsSet = null;
	var f = null;
	var enableRidersLink = null;
	var projectionMode = null;

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
		f.action.value		= action;
		f.target 			= target;
		f.submit();
	}

	function init() {

		keyIsSet = "<%= keyIsSet %>";
				
		f = document.paramForm;

		if (keyIsSet == "false") {
			
			f.transactionType.disabled = true;
			f.effectiveDateMonth.disabled = true;	
			f.effectiveDateDay.disabled = true;
			f.effectiveDateYear.disabled = true;
			f.grossAmount.disabled = true;
			f.netGross.disabled = true;
			f.areaId.disabled = true;		
			f.annuityOption.disabled = true;			
			f.frequency.disabled = true;
			f.paymentAmount.disabled = true;			
			f.yearlyTaxableBenefit.disabled = true;
			f.exclusionRatio.disabled = true;
			f.costBasis.disabled = true;
			f.recoveredCostBasis.disabled = true;
			f.issueAge.disabled = true;
			f.sex.disabled = true;
			f.classIds.disabled = true;
			f.relationshipInd.disabled = true;
			f.disbursementSource.disabled = true;
			f.taxFilingStatus.disabled = true;
			f.payeeAllocation.disabled = true;
			f.fedWithholdingOvrd.disabled = true;
			f.fedWithholdingInd.disabled = true;
			f.stateWithholdingOvrd.disabled = true;
			f.stateWithholdingInd.disabled = true;
			f.cityWithholdingOvrd.disabled = true;
			f.cityWithholdingInd.disabled = true;
			f.countyWithholdingOvrd.disabled = true;
			f.countyWithholdingInd.disabled = true;
			f.fund1.disabled = true;
			f.fund2.disabled = true;
			f.fund3.disabled = true;
			f.fund4.disabled = true;
			f.allocationPct1.disabled = true;
			f.allocationPct2.disabled = true;
			f.allocationPct3.disabled = true;
			f.allocationPct4.disabled = true;
			
		}
		else
		{		
			f.transactionType.focus();
			f.transactionType.disabled = false;
			f.effectiveDateMonth.disabled = false;	
			f.effectiveDateDay.disabled = false;
			f.effectiveDateYear.disabled = false;
			f.grossAmount.disabled = false;
			f.netGross.disabled = false;
			f.areaId.disabled = false;		
			f.annuityOption.disabled = false;			
			f.frequency.disabled = false;
			f.paymentAmount.disabled = false;			
			f.yearlyTaxableBenefit.disabled = false;
			f.exclusionRatio.disabled = false;
			f.costBasis.disabled = false;
			f.recoveredCostBasis.disabled = false;
			f.issueAge.disabled = false;
			f.sex.disabled = false;
			f.classIds.disabled = false;
			f.relationshipInd.disabled = false;
			f.disbursementSource.disabled = false;
			f.taxFilingStatus.disabled = false;
			f.payeeAllocation.disabled = false;
			f.fedWithholdingInd.disabled = false;
			f.fedWithholdingOvrd.disabled = false;
			f.stateWithholdingInd.disabled = false;
			f.stateWithholdingOvrd.disabled = false;
			f.cityWithholdingOvrd.disabled = false;
			f.cityWithholdingInd.disabled = false;
			f.countyWithholdingOvrd.disabled = false;
			f.countyWithholdingInd.disabled = false;
			f.fund1.disabled = false;
			f.fund2.disabled = false;
			f.fund3.disabled = false;
			f.fund4.disabled = false;
			f.allocationPct1.disabled = false;
			f.allocationPct2.disabled = false;
			f.allocationPct3.disabled = false;
			f.allocationPct4.disabled = false;
			
		}
		
		enableRidersLink = "<%= enableRidersLink %>";
		
		if (enableRidersLink == "false")  {			
			document.all.ridersLink.disabled = true;	
		} else  {
			document.all.ridersLink.disabled = false;
		}
		
		projectionMode = "<%= projectionMode %>";
		enableCancelButton = "<%= enableCancelButton %>";
		
		if (projectionMode == "Debug") {
		
			if (enableCancelButton == "false") {
				f.cancel.disabled = true;
			} else  {
				f.cancel.disabled = false;
			}
		}	
	}
	
	function testForTab(currentField, nextField, length) {
	
		if (currentField.value.length == length) {
		
			nextField.focus();
		}		
	}   
	
	function applyTransactionBaseParameters() {
	
		buildDates();
		
		sendTransactionAction("ParamTran", "applyPayoutBaseParameters", "main");
	}
	
	function clearPayoutBaseParameters() {
	
		sendTransactionAction("ParamTran", "clearPayoutBaseParameters", "main");		
	}
	
	function doProjection() {
	
	}
	
	function buildDates() {
		
		f.effectiveDate.value = formatDate(f.effectiveDateMonth.value, f.effectiveDateDay.value, f.effectiveDateYear.value, false);
		
	}
	
	function applyTransactionParameters() {
	
		buildDates();

		buildCheckBoxValues();
	
		sendTransactionAction("ParamTran", "applyTransactionParameters", "main");
	}	
	
	function saveTransactionParameters() {
	
		buildDates();

		buildCheckBoxValues();

		sendTransactionAction("ParamTran", "saveTransactionParameters", "main");		
	}
	
	function clearDebugParameters() {
	
		sendTransactionAction("ParamTran", "clearDebugParameters", "main");		
	}
	
	function cancelDebugParameters() {
	
		sendTransactionAction("ParamTran", "cancelDebugParameters", "main");		
	}

	function saveRiderParameters() {
	
		buildDates();	
		
		sendTransactionAction("ParamTran", "saveRiderParameters", "main");		
	}
	
	function clearRiderParameters() {
	
		sendTransactionAction("ParamTran", "clearRiderParameters", "main");		
	}
	
	function cancelRiderParameters() {
	
		sendTransactionAction("ParamTran", "cancelRiderParameters", "main");		
	}

	function showRiderSelectionScreen() {
	
		if (document.all.ridersLink.disabled == false) {
			sendTransactionAction("ParamTran", "showRiderSelectionScreen", "main");
		}
	}
	
	function showBaseParametersScreen() {		

		sendTransactionAction("ParamTran","showBaseParametersScreen", "main");
	}

	function buildCheckBoxValues()
	{

		if(f.fedWithholdingInd.checked == true)
		{
			f.fedwithholdingInd.value = "Y";
		}
		else
		{
			f.fedwithholdingInd.value = "N";
		}

		if(f.stateWithholdingInd.checked == true)
		{
			f.statewithholdingInd.value = "Y";
		}
		else
		{
			f.statewithholdingInd.value = "N";
		}
		
		if(f.cityWithholdingInd.checked == true)
		{
			f.citywithholdingInd.value = "Y";
		}
		else
		{
			f.citywithholdingInd.value = "N";
		}

		if(f.countyWithholdingInd.checked == true)
		{
			f.countywithholdingInd.value = "Y";
		}
		else
		{
			f.countywithholdingInd.value = "N";
		}
	}	

</script>

</head>

<!-- ****** HTML CODE ***** //-->

<body style="margin-top:5;margin-left:10" onLoad="init()" bgColor="#99BBBB">
<form name="paramForm" method="post" action="/PORTAL/servlet/RequestManager">
<p></p>
<table width="89%" border="0" rules="none" cellspacing="0" cellpadding="0" height="70%">
  <tr> 
    <td width="19%" height="25">&nbsp;</td>
    <td width="1%" height="25">&nbsp;</td>
  </tr>
  <tr> 
    <td width="19%" height="135"> 
      <table width="75%" bgColor="#DDDDDD" border="0" rule="none" cellspacing="0" cellpadding="0" style="border-style:solid; border-width:1; border-color: black">
        <tr> 
          <td width="12%" nowrap align="center" height="40"> 
            <div align="left"><font color="#000000">
			<a href="javascript:showBaseParametersScreen()">
			<u>Base Params</u> </a> </font></div>
          </td>
        </tr>
        <tr> 
          <td width="12%" align="center" height="40"> 
            <div align="left">
			<font color="#000000">
				<a id="ridersLink" href="javascript:showRiderSelectionScreen()">
					<u>Riders</u></a> 
					
					<%
						out.println("<br>");
						
						for (int i = 0; i < selectedRiders.length; i++) {
					
							out.println("<br>&nbsp;&nbsp;&nbsp;" + selectedRiders[i]);
						}
					%>		
			</font>
			</div>
          </td>
        </tr>
        <tr> 
          <td width="12%" align="center" title="Under Construction" height="40"> 
            <div align="left"><font color="#000000"><u>Lump Sum</u> </font></div>
          </td>
        </tr>
        <tr> 
          <td width="12%" align="center" title="Under Construction" height="40"> 
            <div align="left"><font color="#000000"><u>Income</u></font></div>
          </td>
        </tr>
      </table>
    </td>
    <td width="47%" height="135" valign="top"> 
      <div align="left"> 
        <table width="101%" bgColor="#DDDDDD" border="3" rules="none" cellspacing="0" cellpadding="3" height="96" bordercolor="#000000">
        <tr>
            <td colspan="4" bgColor="#30548E" align="center">
                <font size="2" color="#FFFFFF">Transaction Information</font>
            </td>
        </tr>

		 <tr>
            <td width="30%" height="18"><font color="#000000">Transaction Type<font color="#A0365B">*</font>:</font></td>
            <td colspan="3" 
            title="Transaction Type (Usage: push param:TransactionType)" height="18" width="62%"> 
              <font color="#000000" size="+4"> 
              <select name="transactionType" title = "TransactionType" tabindex="1">
              <option>
                 Please Select
              </option>			
              
              <%
                  for(int i = 0; i < trxTypeCTVO.length; i++) {

                      String codeTablePK = trxTypeCTVO[i].getCodeTablePK() + "";
                      String codeDesc    = trxTypeCTVO[i].getCodeDesc();
                      String code        = trxTypeCTVO[i].getCode();

                     if (trxTypeId.equalsIgnoreCase(code)) {

                         out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                     }
                     else  {

                         out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                     }
                  }
              %>
              </select>
              </font>
             </td>
		 </tr>
          <tr> 
            <td width="50%" height="28"><font color="#000000">Effective Date<font color="#A0365B">*</font>: 
              </font></td>
            <td colspan="3" height="28"> <font color="#000000"> 
              <input type="text" name="effectiveDateMonth" tabindex='2' size="2" maxlength="2"
              value= "<%= effectiveDateMonth %>" title="Effective Month (MM)"
              onFocus="this.select()"onKeyUp="testForTab(this, f.effectiveDateDay, 2)">
              /
              <input type="text" name="effectiveDateDay" tabindex='3' size="2" maxlength="2"
              value= "<%= effectiveDateDay %>" title="Effective Day (DD)"
              onFocus="this.select()" onKeyUp="testForTab(this, f.effectiveDateYear, 2)">
              / 
              <input type="text" name="effectiveDateYear" tabindex='4' size="4" maxlength="4"
              value= "<%= effectiveDateYear %>" title="Effective Year (YYYY)"
              onFocus="this.select()">
              </font></td>
          </tr>
		  <tr> 
          <td height="43" width="13%"><font color="#000000">Amount<font color="#A0365B">*</font>: 
            </font></td>
          <td colspan="5"  title="Gross Amount(Usage: push param:GrossAmount)" height="43" width="36%"> 
            <font color="#000000"> 
            <input type="text" name="grossAmount" size="15" maxlength="10" tabindex='5'
			value= "<%= grossAmount %>"
			onFocus="this.select()">
            </font>
          </td>
        </tr>
		  <tr> 
            <td width="50%" height="18"><font color="#000000">Net/Gross:</font></td>
            <td colspan="3" title="Net/Gross (Usage: push param:Net/Gross)" height="18" width="62%"> 
              <font color="#000000" size="+6"> 
              <select name="netGross" title = "Area" tabindex="6">
              <option>
                 Please Select
              </option>			
              
              <%
                  for(int i = 0; i < netGrossCTVO.length; i++) {

                      String codeTablePK = netGrossCTVO[i].getCodeTablePK() + "";
                      String codeDesc    = netGrossCTVO[i].getCodeDesc();

                     if (netGrossId.equalsIgnoreCase(codeTablePK)) {

                         out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                     }
                     else  {

                         out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                     }
                  }
              %>
              </select>
              </font>
             </td>
		 </tr>
		 <tr>
		 	<td>
		  		&nbsp;
			</td>
		 </tr>
		 <tr>
		 	<td>
		  		&nbsp;
			</td>
		 </tr>
		 <tr>
		 	<td>
		  		&nbsp;
			</td>
		 </tr>
        </table>
      </div>
    </td>
    <td rowspan="1" colspan="3" height="179" valign="top" width="100%">&nbsp;</td>
    <td rowspan="1" colspan="3" height="179" valign="top" width="100%"> 
      <table width="70%" bgColor="#DDDDDD" border="3" rules="none" cellspacing="0" cellpadding="3" height="180" bordercolor="#000000">

        <tr>
            <td colspan="6" bgColor="#30548E" align="center">
                <font size="2" color="#FFFFFF">Client Information</font>
            </td>
        </tr>
        <tr>
          <td height="42" width="13%"><font color="#000000">Issue Age<font color="#A0365B">*</font>: 
            </font></td>
          <td colspan="5"  title="Client Issue Age (Usage: push param:IssueAge)" height="43" width="36%"> 
            <font color="#000000"> 
            <input type="text" name="issueAge" size="15" maxlength="3" tabindex='15'
			value= "<%= issueAge %>"
			onFocus="this.select()">
            </font></td>
        </tr>
        <tr> 
          <td height="32" width="13%"><font color="#000000">Sex<font color="#A0365B">*</font> 
            :</font></td>
          <td colspan="5" title="Gender (Usage: push param:Sex)" height="37" width="36%"> 
            <font color="#000000"> 
            <select name="sex" title = "gender" tabindex="16">
                <option>
            	   Please Select
            	</option>			
            	
            	<%
                    for(int i = 0; i < genderCTVO.length; i++) {

                        String codeTablePK = genderCTVO[i].getCodeTablePK() + "";
                        String codeDesc    = genderCTVO[i].getCodeDesc();

                       if (genderId.equalsIgnoreCase(codeTablePK)) {

                           out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                       }
                       else  {

                           out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                       }
                    }
            	%>
            </select>		
            </font></td>
        </tr>
        <tr> 
          <td height="33" width="13%"><font color="#000000">Class<font color="#A0365B">*</font>:</font></td>
          <td colspan="5" title="Rate Class (Usage: push param:Class)" height="33" width="36%"> 
            <font color="#000000"> 
            <select name="classIds" title = "Class Type" tabindex="17">
                <option>
            	   Please Select
            	</option>			
            	
            	<%
                    for(int i = 0; i < classCTVO.length; i++) {

                        String codeTablePK = classCTVO[i].getCodeTablePK() + "";
                        String codeDesc    = classCTVO[i].getCodeDesc();

                       if (classId.equalsIgnoreCase(codeTablePK)) {

                           out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                       }
                       else  {

                           out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                       }
                    }
            	%>
    
            </select>
            </font></td>
        </tr>
        <tr> 
          <td height="33" width="13%"><font color="#000000">Relationship<font color="#A0365B">*</font>:</font></td>
          <td colspan="5" title="RelationShip (Usage: push param:RelationshipInd)" height="33" width="36%"> 
            <font color="#000000"> 
            <select name="relationshipInd" title = "Relationship" tabindex="18">
                <option>
            	   Please Select
            	</option>			
            	
            	<%
                    for(int i = 0; i < lifeRelationCTVO.length; i++) {

                        String codeTablePK = lifeRelationCTVO[i].getCodeTablePK() + "";
                        String codeDesc    = lifeRelationCTVO[i].getCodeDesc();

                       if (relationshipId.equalsIgnoreCase(codeTablePK)) {

                           out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                       }
                       else  {

                           out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                       }
                    }
            	%>
    
            </select>
            </font></td>
        </tr>
        <tr> 
          <td height="33" width="13%"><font color="#000000">Disburse Source:</font></td>
          <td colspan="5" title="DisbursementSource (Usage: push param:DisbursementSource)" height="33" width="36%"> 
            <font color="#000000"> 
            <select name="disbursementSource" title = "Disbursement Source" tabindex="19">
                <option>
            	   Please Select
            	</option>			
            	
            	<%

                    for(int i = 0; i < disbursementCTVO.length; i++) {

                        String codeTablePK = disbursementCTVO[i].getCodeTablePK() + "";
                        String codeDesc    = disbursementCTVO[i].getCodeDesc();

                       if (disburseSourceId.equalsIgnoreCase(codeTablePK)) {

                           out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                       }
                       else  {

                           out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                       }
                    }
            	%>
    
            </select>
            </font></td>
        </tr>
		<tr> 
          <td height="33" width="13%"><font color="#000000">Tax Filing Status:</font></td>
          <td colspan="5" title="Tax Filing Status(Usage: push param:TaxFilingStatus)" height="33" width="36%"> 
            <font color="#000000"> 
            <select name="taxFilingStatus" title = "Tax Filing Status" tabindex="20">
                <option>
            	   Please Select
            	</option>			
            	
            	<%

                    for(int i = 0; i < taxFilingStatusCTVO.length; i++) {

                        String codeTablePK = taxFilingStatusCTVO[i].getCodeTablePK() + "";
                        String codeDesc    = taxFilingStatusCTVO[i].getCodeDesc();

                       if (taxFilingStatusId.equalsIgnoreCase(codeTablePK)) {

                           out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                       }
                       else  {

                           out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                       }
                    }
            	%>
    
            </select>
            </font></td>
        </tr>
		<tr> 
          <td height="43" width="13%"><font color="#000000">Payee Alloc %: 
            </font></td>
          <td colspan="5"  title="Payee Alloc(Usage: push param:Payee Allocation)" height="43" width="36%"> 
            <font color="#000000"> 
            <input type="text" name="payeeAllocation" size="15" maxlength="5" tabindex='21'
			value= "<%= payeeAllocation %>"
			onFocus="this.select()">
            </font>
          </td>
        </tr>
       </table>
    </td>
  </tr>
  <tr> 
    <td width="19%" height="20">&nbsp;</td>
  </tr>
  <tr> 
    <td width="19%" height="165">&nbsp;</td>
    <td width="47%" height="165" valign="top"> 
      <div align="left"> 
        <table width="100%" bgColor="#DDDDDD" border="3" rules="none" cellspacing="0" cellpadding="3" height="151" bordercolor="#000000">
        <tr>
            <td colspan="4" bgColor="#30548E" align="center">
                <font size="2" color="#FFFFFF">Coverage Information</font>
            </td>
        </tr>

          <tr>
            <td width="33%" height="13%"><font color="#000000">Issue State<font color="#A0365B">*</font>:</font></td>
            <td colspan="3" title="Issue State (Usage: push param:IssueState)" > 
              <font color="#000000"> 
              <select name="areaId" title = "Area" tabindex="7">
              <option>
                 Please Select
              </option>			   
              <%

                  for(int i = 0; i < stateCTVO.length; i++) {

                      String codeTablePK = stateCTVO[i].getCodeTablePK() + "";
                      String codeDesc    = stateCTVO[i].getCodeDesc();

                     if (areaId.equalsIgnoreCase(codeTablePK)) {

                         out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                     }
                     else  {

                         out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                     }
                  }
              %>
              </select>
              </font>
             </td>
		 </tr>
          <tr> 
          <td height="33" width="13%"><font color="#000000">Annuity Option:</font></td>
          <td colspan="5" title="Annuity Option (Usage: push param:payoutOption)" height="33" width="36%"> 
            <font color="#000000"> 
            <select name="annuityOption" title = "Annuity Option" tabindex='8'>
                <option>
            	   Please Select
            	</option>			
            	
            	<%

                    for(int i = 0; i < optionCTVO.length; i++) {

                        String codeTablePK = optionCTVO[i].getCodeTablePK() + "";
                        String codeDesc    = optionCTVO[i].getCodeDesc();

                       if (annuityOptionId.equalsIgnoreCase(codeTablePK)) {

                           out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                       }
                       else  {

                           out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                       }
                    }
            	%>
    
            </select>
            </font></td>
        </tr>
          <tr> 
          <td height="33" width="13%"><font color="#000000">Frequency:</font></td>
          <td colspan="5" title="Frequency (Usage: push param:Frequency)" height="33" width="36%"> 
            <font color="#000000"> 
            <select name="frequency" title = "Frequency Option" tabindex="9">
                <option>
            	   Please Select
            	</option>
            	<%
                    for(int i = 0; i < frequencyCTVO.length; i++) {

                        String codeTablePK = frequencyCTVO[i].getCodeTablePK() + "";
                        String codeDesc    = frequencyCTVO[i].getCodeDesc();

                       if (frequencyId.equalsIgnoreCase(codeTablePK)) {

                           out.println("<option selected name=\"id\" value=\"" + codeTablePK+ "\">" + codeDesc + "</option>");
                       }
                       else  {

                           out.println("<option name=\"id\" value=\"" + codeTablePK + "\">" + codeDesc + "</option>");
                       }
                    }
            	%>
    
            </select>
            </font></td>
        </tr>
		<tr>
			<td width="50%" height="28"><font color="#000000">Payment Amount:</font>
            </td>
			<td title="Client PaymentAmount (Usage: push param:ModalAmount)"> 
            <font color="#000000"> 
            	<input type="text" name="paymentAmount" size="15" maxlength="10" tabindex='10'
				value= "<%= paymentAmount %>"
				onFocus="this.select()">
            </font>
            </td>
		</tr>
		<tr>
			<td width="50%" height="28"><font color="#000000">Yearly Taxable Benefit:</font>
            </td>
			<td title="Client YearlyTaxable Benefit (Usage: push param:YearlyTaxableBenefit)"> 
            <font color="#000000"> 
            	<input type="text" name="yearlyTaxableBenefit" size="15" maxlength="10" tabindex='11'
				value= "<%= yearlyTaxableBenefit %>"
				onFocus="this.select()">
            </font>
            </td>
		</tr>
		<tr>
			<td width="50%" height="28"><font color="#000000">Exclusion Ratio:</font>
            </td>
			<td title="Client ExclusionRatio(Usage: push param:ExclusionRatio)"> 
            <font color="#000000"> 
            	<input type="text" name="exclusionRatio" size="15" maxlength="10" tabindex='12'
				value= "<%= exclusionRatio %>"
				onFocus="this.select()">
            </font>
            </td>
		</tr>
		<tr>
			<td width="50%" height="28"><font color="#000000">Cost Basis $:</font>
            </td>
			<td title="Client costBasis (Usage: push param:OrigInvestment)"> 
            <font color="#000000"> 
            	<input type="text" name="costBasis" size="15" maxlength="10" tabindex='13'
				value= "<%= costBasis %>"
				onFocus="this.select()">
            </font>
            </td>
		</tr>
		<tr>
			<td width="50%" height="28"><font color="#000000">Recovered Cost Basis:</font>
            </td>
			<td title="Client RecoveredCostBasis (Usage: push param:RecoveredCostBasis)"> 
            <font color="#000000"> 
            	<input type="text" name="recoveredCostBasis" size="15" maxlength="10" tabindex='14'
				value= "<%= recoveredCostBasis %>"
				onFocus="this.select()">
            </font>
            </td>
		</tr>
		<tr>
		 	<td>
		  		&nbsp;
			</td>
		 </tr>
		 <tr>
		 	<td>
		  		&nbsp;
			</td>
		 </tr>
        </table>
      </div>
	 <td rowspan="3" colspan="3" height="179" valign="top" width="5%">&nbsp;</td>
     <td rowspan="3" colspan="3" height="179" valign="top" width="55%"> 
      <table width="100%" bgColor="#DDDDDD" border="3" rules="none" cellspacing="0" cellpadding="3" height="180" bordercolor="#000000">
        <tr>
            <td colspan="8" bgColor="#30548E" align="center">
                <font size="2" color="#FFFFFF">With/Fund Information</font>
            </td>
        </tr>

        <tr>
          <td height="42" width="13%"><font color="#000000">Fed W/H: 
            </font></td>
          <td colspan="5"  title="Client Fed W/H (Usage: push param:FedWithholdingOvrd)" height="43" width="36%"> 
            <font color="#000000"> 
            <input type="text" name="fedWithholdingOvrd" size="15" maxlength="10" tabindex='22'
			value= "<%= fedWithholdingOvrd %>"
			onFocus="this.select()">
            </font>
          </td>
		  <td>
			<input type="checkbox" <%= checkedFedWithholdingInd %> id="fedWithholdingInd" tabindex='23'><font color="#000000">No Fed W/H</font>
          </td>
        </tr>
        <tr> 
          <td height="42" width="13%"><font color="#000000">State W/H: 
            </font></td>
          <td colspan="5"  title="Client StateWithholdingOvrd (Usage: push param:StateWithholdingOvrd)" height="43" width="36%"> 
            <font color="#000000"> 
            <input type="text" name="stateWithholdingOvrd" size="15" maxlength="10" tabindex='24'
			value= "<%= stateWithholdingOvrd %>"
			onFocus="this.select()">
            </font>
          </td>
		  <td colspan="4">
			<input type="checkbox" <%= checkedStateWithholdingInd %> id="stateWithholdingInd" tabindex='25'><font color="#000000">No St W/H</font>
          </td>
        </tr>
		<tr> 
          <td height="42" width="13%"><font color="#000000">City W/H: 
            </font></td>
          <td colspan="5"  title="Client CityWithholdingOvrd (Usage: push param:CityWithholdingOvrd)" height="43" width="36%"> 
            <font color="#000000"> 
            <input type="text" name="cityWithholdingOvrd" size="15" maxlength="10" tabindex='26'
			value= "<%= cityWithholdingOvrd %>"
			onFocus="this.select()">
            </font>
          </td>
		  <td colspan="4">
			<input type="checkbox" <%= checkedCityWithholdingInd %> id="cityWithholdingInd" tabindex='27'><font color="#000000">No City W/H</font>
          </td>
        </tr>
		<tr> 
          <td height="42" width="13%"><font color="#000000">Co W/H: 
            </font></td>
          <td colspan="5"  title="Client CountyWithholdingOvrd(Usage: push param:CountyWithholdingOvrd)" height="43" width="36%"> 
            <font color="#000000"> 
            <input type="text" name="countyWithholdingOvrd" size="15" maxlength="10" tabindex='28'
			value= "<%= countyWithholdingOvrd %>"
			onFocus="this.select()">
            </font>
          </td>
		  <td colspan="4">
			<input type="checkbox" <%= checkedCountyWithholdingInd %> id="countyWithholdingInd" tabindex='29'><font color="#000000">No Co W/H</font>
          </td>
        </tr>
		<tr> 
          <td height="33" width="13%"><font color="#000000">Fund<font color="#A0365B">*</font>:</font></td>
          <td colspan="5" title="Fund (Usage: push param:fund)" height="33" width="36%"> 
            <font color="#000000"> 
            <select name="fund1" title = "Fund Option" tabindex="30">
                <option>
            	   Please Select
            	</option>
            	<%
            		for(int i = 0; i < fundNamesCodeIds.length; i++) {
    
            			if (fund1.equalsIgnoreCase(fundNamesCodeIds[i])) {				
            			
            				out.println("<option selected name=\"id\" value=\"" + fundNamesCodeIds[i] + "\">" + fundNamesCodes[i] + "</option>");
            			}
            			else  {
            			
            				out.println("<option name=\"id\" value=\"" + fundNamesCodeIds[i] + "\">" + fundNamesCodes[i] + "</option>");					
            			}		
            		}			
            	%>
    
            </select>
            </font>
            </td>
			<td width="50%" height="28"><font color="#000000">%<font color="#A0365B">*</font>:</font>
            </td>
			<td title="Client Allocation Pct (Usage: push param:Allocation Pct)"> 
            <font color="#000000"> 
            	<input type="text" name="allocationPct1" size="5" maxlength="5" tabindex='31'
				value= "<%= allocationPct1 %>"
				onFocus="this.select()">
            </font>
            </td>
        </tr>
		<tr> 
          <td height="33" width="13%"><font color="#000000">Fund:</font></td>
          <td colspan="5" title="Fund (Usage: push param:Fund)" height="33" width="36%"> 
            <font color="#000000"> 
            <select name="fund2" title = "Fund Option" tabindex="32">
                <option>
            	   Please Select
            	</option>
            	<%
            		for(int i = 0; i < fundNamesCodeIds.length; i++) {
    
            			if (fund2.equalsIgnoreCase(fundNamesCodeIds[i])) {				
            			
            				out.println("<option selected name=\"id\" value=\"" + fundNamesCodeIds[i] + "\">" + fundNamesCodes[i] + "</option>");
            			}
            			else  {
            			
            				out.println("<option name=\"id\" value=\"" + fundNamesCodeIds[i] + "\">" + fundNamesCodes[i] + "</option>");					
            			}		
            		}			
            	%>
            </select>
            </font></td>
			<td width="50%" height="28"><font color="#000000">%:</font>
            </td>
			<td title="Client Allocation Pct (Usage: push param:Allocation Pct)"> 
            <font color="#000000"> 
            	<input type="text" name="allocationPct2" size="5" maxlength="5" tabindex='33'
				value= "<%= allocationPct2 %>"
				onFocus="this.select()">
            </font>
            </td>
        </tr>
		<tr> 
          <td height="33" width="13%"><font color="#000000">Fund:</font></td>
          <td colspan="5" title="Fund (Usage: push param:Fund)" height="33" width="36%"> 
            <font color="#000000"> 
            <select name="fund3" title = "Fund Option" tabindex="34">
                <option>
            	   Please Select
            	</option>
            	<%
            		for(int i = 0; i < fundNamesCodeIds.length; i++) {
    
            			if (fund3.equalsIgnoreCase(fundNamesCodeIds[i])) {				
            			
            				out.println("<option selected name=\"id\" value=\"" + fundNamesCodeIds[i] + "\">" + fundNamesCodes[i] + "</option>");
            			}
            			else  {
            			
            				out.println("<option name=\"id\" value=\"" + fundNamesCodeIds[i] + "\">" + fundNamesCodes[i] + "</option>");					
            			}		
            		}			
            	%>
            </select>
            </font></td>
			<td width="50%" height="28"><font color="#000000">%:</font>
            </td>
			<td title="Client AllocationPct (Usage: push param:Allocation Pct)"> 
            <font color="#000000"> 
            	<input type="text" name="allocationPct3" size="5" maxlength="5" tabindex='35'
				value= "<%= allocationPct3 %>"
				onFocus="this.select()">
            </font>
            </td>
        </tr>
		<tr> 
          <td height="33" width="13%"><font color="#000000">Fund:</font></td>
          <td colspan="5" title="Fund (Usage: push param:Fund)" height="33" width="36%"> 
            <font color="#000000"> 
            <select name="fund4" title = "Fund Option" tabindex="36">
                <option>
            	   Please Select
            	</option>
            	<%
            		for(int i = 0; i < fundNamesCodeIds.length; i++) {
    
            			if (fund4.equalsIgnoreCase(fundNamesCodeIds[i])) {				
            			
            				out.println("<option selected name=\"id\" value=\"" + fundNamesCodeIds[i] + "\">" + fundNamesCodes[i] + "</option>");
            			}
            			else  {
            			
            				out.println("<option name=\"id\" value=\"" + fundNamesCodeIds[i] + "\">" + fundNamesCodes[i] + "</option>");					
            			}		
            		}			
            	%>
            </select>
            </font>
           </td>
		   <td width="50%" height="28"><font color="#000000">%:</font>
            </td>
			<td title="Client Allocation Pct (Usage: push param:Allocation Pct)"> 
            <font color="#000000"> 
            	<input type="text" name="allocationPct4" size="5" maxlength="5" tabindex='37'
				value= "<%= allocationPct4 %>"
				onFocus="this.select()">
            </font>
            </td>
        </tr>
		</table>
    </td>
  </tr>
  <tr> 
    <td width="19%">&nbsp;</td>
    <td width="47%"><font color="#A0365B">*</font><font color="#000000"> Denotes 
      a Required Field </font></td>
    <td width="1%">&nbsp;</td>
    <td width="33%">&nbsp;</td>
  </tr>
</table>
<hr size="1">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td colspan="4" valign="top">Mode:
	<% if(editMode.trim().length() > 0)
		out.print(editMode);
	   else 
		out.print(projectionMode); %>
    <td width="2%">&nbsp;</td>
    <td colspan="17" align="right" width="59%" valign="top"> <font color="#000000"> 

     
	 <%
	 	if (projectionMode.equalsIgnoreCase("Debug") && editMode.equals(""))  {
	 %>
      <input type="button" name="apply"  value="   Apply   " 
           	     title="Save parameters"
                 onClick="applyTransactionParameters()">
      <input type="button" name="save"  value="   Save    "
           	     title="Save Parameters and Debug Script"
                 onClick="saveTransactionParameters()">
      <input type="button"  name="clear" value="   Clear    "
    			title="Clear parameters"
                onClick="clearDebugParameters()">
      <input type="button" name="cancel" value="  Cancel  "
				title="Cancel Parameter Change"
                onClick="cancelDebugParameters()">
      <%
	 	}
	  %>
	<%
		if (editMode.equalsIgnoreCase("Rider"))  {
	%>
	
        <input type="button" name="save"  value="  Save  "
           	     title="Save parameters and Close Window"
                 onClick="saveRiderParameters()">
        <input type="button"  name="clear" value="  Clear  "
    			title="Clear parameters"
				onClick="clearRiderParameters()">
        <input type="button" name="cancel" value="Cancel"
				title="Cancel Parameter screen"
				onClick="cancelRiderParameters()">
	
			   	</font></td>
		  	</tr>
		  	<tr>
			  	<td align="left">
			  		<%
						if ((rider != null) && (!rider.equals(""))) {			
							out.println("Rider: " + rider);
						}
					%>
		  	  	</td>	 
		  	</tr>
  		<%
			}
		%>	
</table>
<p>&nbsp;</p>

<!-- ****** HIDDEN CODE ***** //-->
<p> 
  <input id="transaction"   	type="hidden" name="transaction" 	 value="">
  <input id="action"        	type="hidden" name="action"      	 value="">  
  <input id="effectiveDate" 	type="hidden" name="effectiveDate" 	 value="">
  <input id="rider"				type="hidden" name="rider" 			 value="<%= rider %>">
  <input id="financialType"     type="hidden" name="financialType"   value="<%= financialType %>">
  <input id="selectedIndex" 	type="hidden" name="selectedIndex" 	 value="<%= selectedIndex %>">
  <input id="fedwithholdingInd"    type="hidden" name="fedwithholdingInd" >
  <input id="statewithholdingInd"  type="hidden" name="statewithholdingInd" >  
  <input id="citywithholdingInd"   type="hidden" name="citywithholdingInd" >
  <input id="countywithholdingInd" type="hidden" name="countywithholdingInd" >
  
</p>
</body>
</form>
</html>