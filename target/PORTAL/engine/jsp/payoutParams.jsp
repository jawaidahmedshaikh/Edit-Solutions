<!-- ****** JAVA CODE ***** //-->

<%@ page import="java.util.*,
                 edit.common.*" %>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<jsp:useBean id="codeTableBean"
    class="fission.beans.SessionBean" scope="session"/>    
	
<jsp:useBean id="paramBean"
    class="fission.beans.SessionBean" scope="session"/>
	
<jsp:useBean id="pageBean"
    class="fission.beans.PageBean" scope="request"/>	
<%

    // For the drop downs... These should always have values
	String[] areaIds           = codeTableBean.getValues("stateCodes");
	String[] areas             = codeTableBean.getValues("stateCodeDescriptions");
	String   areaId	           = pageBean.getValue("areaId");
	if (areaId == null || areaId.length() == 0){areaId = "";}	
				
	String[] genderCodeIds     = codeTableBean.getValues("genderCodes");
	String[] genders           = codeTableBean.getValues("genderCodeDescriptions");
	String genderId 	       = pageBean.getValue("genderId");
	if (genderId == null || genderId.length() == 0){genderId = "";}		
	

	String[] classCodeIds      = codeTableBean.getValues("classCodes");
	String[] classes           = codeTableBean.getValues("classCodeDescriptions");
	String classId 	           = pageBean.getValue("classId");	
	if (classId == null || classId.length() == 0){classId = "";}	
	
	String[] annuityOptionIds  = codeTableBean.getValues("optionCodes");
	String[] annuityOptions    = codeTableBean.getValues("optionCodeDescriptions");
	String annuityOptionId 	   = pageBean.getValue("annuityOptionId");	
	if (annuityOptionId == null || annuityOptionId.length() == 0){annuityOptionId = "";}	
	
	String[] frequencyIds  = codeTableBean.getValues("frequencyCodes");
	String[] frequencies       = codeTableBean.getValues("frequencyCodeDescriptions");
	String frequencyId 	       = pageBean.getValue("frequencyId");	
	if (frequencyId == null || frequencyId.length() == 0){frequencyId = "";}	
					
	String[] relationshipIds   = codeTableBean.getValues("lifeRelationTypesCodes");
	String[] relationships     = codeTableBean.getValues("lifeRelationTypesCodeDescriptions");
	String relationshipId 	   = pageBean.getValue("relationshipId");	
	if (relationshipId == null || relationshipId.length() == 0){relationshipId = "";}	
	
	String[] disburseSourceIds = codeTableBean.getValues("disbursementSourceCodes");	                                                       
	String[] disburseSources   = codeTableBean.getValues("disbursementSourceCodeDescriptions");
	String disburseSourceId    = pageBean.getValue("disburseSourceId");	
	if (disburseSourceId == null || disburseSourceId.length() == 0){disburseSourceId = "";}	
	
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
	
	String startDateDay 	= pageBean.getValue("startDateDay");
	if (startDateDay == null || startDateDay.length() == 0) {startDateDay = "9";}
	
	String startDateMonth = pageBean.getValue("startDateMonth");
	if (startDateMonth == null || startDateMonth.length() == 0) {startDateMonth = "9";}	
	
	String startDateYear 	= pageBean.getValue("startDateYear");
	if (startDateYear == null || startDateYear.length() == 0) {startDateYear = EDITDate.DEFAULT_MAX_YEAR;}
		
	
//	String nonQualInd = pageBean.getValue("nonQualInd");
//	if (nonQualInd == null || nonQualInd.length()== 0) {nonQualInd = "0.0";}		
	
	String issueAge = pageBean.getValue("issueAge");
	if (issueAge == null || issueAge.length() == 0) {issueAge = "0";}	
	
	
	String purchaseAmount = pageBean.getValue("purchaseAmount");
	if (purchaseAmount == null || purchaseAmount.length() == 0) {purchaseAmount = "0";}	
	
	String paymentAmount = pageBean.getValue("paymentAmount");
	if (paymentAmount == null || paymentAmount.length() == 0) {paymentAmount = "0";}	
	
	String costBasis = pageBean.getValue("costBasis");
	if (costBasis == null || costBasis.length() == 0) {costBasis = "0";}	
		
	String certainPeriod 	= pageBean.getValue("certainPeriod");	
	if (certainPeriod == null || certainPeriod.length() == 0) {certainPeriod = "0";}
	
	String nonQualInitial = pageBean.getValue("nonQualInd");
	String checkedInitial;
	if(nonQualInitial.equals("Y"))
	{
		checkedInitial = "CHECKED";		
	}
	else
	{
		checkedInitial = "UNCHECKED";
	}
	
	String exchangeInd = pageBean.getValue("exchangeInd");
	String checkedExchange;
	if(exchangeInd.equals("Y"))
	{
		checkedExchange = "CHECKED";		
	}
	else
	{
		checkedExchange = "UNCHECKED";
	}
	
	String post86Investment = pageBean.getValue("post86Investment");
	String checkedInvestment;
	if(post86Investment.equals("Y"))
	{
		checkedInvestment = "CHECKED";		
	}
	else
	{
		checkedInvestment = "UNCHECKED";
	}
	
	
	 
%>

<html>
<head>
<title>ENGINE - Payout Parameter Entry Page</title>
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
		
			f.effectiveDateMonth.disabled = true;	
			f.effectiveDateDay.disabled = true;
			f.effectiveDateYear.disabled = true;
			f.startDateMonth.disabled = true;	
			f.startDateDay.disabled = true;
			f.startDateYear.disabled = true;
			f.areaId.disabled = true;
			f.nonQualInitial.disabled = true;
			f.exchange.disabled = true;
			f.annuityOption.disabled = true;
			f.certainPeriod.disabled = true;
			f.frequency.disabled = true;
			f.purchaseAmount.disabled = true;
			f.paymentAmount.disabled = true;
			f.post86Invest.disabled = true;
			f.costBasis.disabled = true;
			f.issueAge.disabled = true;
			f.sex.disabled = true;
			f.classIds.disabled = true;
			f.relationshipInd.disabled = true;
			f.disbursementSource.disabled = true;
			
		}
		else
		{					
			f.effectiveDateMonth.disabled = false;	
			f.effectiveDateDay.disabled = false;
			f.effectiveDateYear.disabled = false;
			f.startDateMonth.disabled = false;	
			f.startDateDay.disabled = false;
			f.startDateYear.disabled = false;
			f.areaId.disabled = false;
			f.nonQualInitial.disabled = false;
			f.exchange.disabled = false;
			f.annuityOption.disabled = false;
			f.certainPeriod.disabled = false;
			f.frequency.disabled = false;
			f.purchaseAmount.disabled = false;
			f.paymentAmount.disabled = false;
			f.post86Invest.disabled = false;
			f.costBasis.disabled = false;
			f.issueAge.disabled = false;
			f.sex.disabled = false;
			f.classIds.disabled = false;
			f.relationshipInd.disabled = false;
			f.disbursementSource.disabled = false;
			
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
	
	function applyPayoutBaseParameters() {
	
		buildDates();
		
		sendTransactionAction("ParamTran", "applyPayoutBaseParameters", "main");
	}
	
	function clearPayoutBaseParameters() {
	
		sendTransactionAction("ParamTran", "clearPayoutBaseParameters", "main");		
	}
	
	function doProjection() {
	
		buildDates();

		buildCheckBoxValues();
	
		sendTransactionAction("ScriptTran", "doPayoutProjection", "main");
	}
	
	function buildDates() {
		
		f.effectiveDate.value = formatDate(f.effectiveDateMonth.value, f.effectiveDateDay.value, f.effectiveDateYear.value, false);
		
 	    f.startDate.value = formatDate(f.startDateMonth.value, f.startDateDay.value, f.startDateYear.value);	
	}
	
	function applyPayoutParameters() {
	
		buildDates();

		buildCheckBoxValues();
	
		sendTransactionAction("ParamTran", "applyPayoutParameters", "main");
	}	
	
	function savePayoutParameters() {
	
		buildDates();

		buildCheckBoxValues();

		sendTransactionAction("ParamTran", "savePayoutParameters", "main");		
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

		sendTransactionAction("ParamTran","showPayoutBaseParametersScreen", "main");
	}
	
	function buildCheckBoxValues()
	{

		if(f.nonQualInitial.checked == true)
		{
			f.nonQualInd.value = "Y";
		}
		else
		{
			f.nonQualInd.value = "N";
		}

		if(f.exchange.checked == true)
		{
			f.exchangeInd.value = "Y";
		}
		else
		{
			f.exchangeInd.value = "N";
		}
		
		if(f.post86Invest.checked == true)
		{
			f.post86Investment.value = "Y";
		}
		else
		{
			f.post86Investment.value = "N";
		}
	}	

</script>

</head>

<!-- ****** HTML CODE ***** //-->

<body style="margin-top:5;margin-left:10" onLoad="init()" bgColor="#99BBBB">
<form name="paramForm" method="post" action="/PORTAL/servlet/RequestManager">
<p></p>
<table width="89%" border="0" rules="none" cellspacing="0" cellpadding="0" height="489">
  <tr> 
    <td width="19%" height="25">&nbsp;</td>
    <td width="1%" height="25">&nbsp;</td>
  </tr>
  <tr> 
    <td width="19%" height="135"> 
      <table bgColor="#DDDDDD" width="75%" border="0" rule="none" cellspacing="0" cellpadding="0"  style="border-style:solid; border-width:1; border-color: black">
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
                <font size="2" color="#FFFFFF">Policy Information</font>
            </td>
        </tr>

          <tr>
            <td width="50%" height="28"><font color="#000000">Effective Date<font color="#A0365B">*</font>:
              </font></td>
            <td colspan="3" height="28"> <font color="#000000">
              <input type="text" name="effectiveDateMonth" tabindex='4' size="2" maxlength="2"
              value= "<%= effectiveDateMonth %>" title="Effective Month (MM)"
              onFocus="this.select()"onKeyUp="testForTab(this, f.effectiveDateDay, 2)">
              /
              <input type="text" name="effectiveDateDay" tabindex='5' size="2" maxlength="2"
              value= "<%= effectiveDateDay %>" title="Effective Day (DD)"
              onFocus="this.select()" onKeyUp="testForTab(this, f.effectiveDateYear, 2)">
              / 
              <input type="text" name="effectiveDateYear" tabindex='6' size="4" maxlength="4"
              value= "<%= effectiveDateYear %>" title="Effective Year (YYYY)"
              onFocus="this.select()" onKeyUp="testForTab(this, f.startDateMonth, 4)">
              </font></td>
          </tr>
		  <tr> 
            <td width="50%" height="28"><font color="#000000">Start Date:<font color="#A0365B">*</font>: 
              </font>
            </td>
            <td colspan="3" height="28"> <font color="#000000"> 
              <input type="text" name="startDateMonth" tabindex='7'size="2" maxlength="2"
					value= "<%= startDateMonth %>"
			 		title="Start Month (MM)"
			 		onFocus="this.select()" onKeyUp="testForTab(this, f.startDateDay, 2)">
              / 
              <input type="text" name="startDateDay" tabindex='8' size="2" maxlength="2"
					value="<%= startDateDay %>" title="Start Day (DD)"
					onFocus="this.select()" onKeyUp="testForTab(this, f.startDateYear, 2)">
              / 
              <input type="text" name="startDateYear" tabindex='9' size="4" maxlength="4"
			 		value=	"<%= startDateYear %>"  title="Start Year (YYYY)"
			  		onFocus="this.select()">
              		</font>
             </td>
          </tr>
          <tr> 
            <td width="50%" height="18"><font color="#000000">Issue State<font color="#A0365B">*</font>:</font></td>
            <td colspan="3" 
            title="Issue State (Usage: push param:IssueState)" height="18" width="62%"> 
              <font color="#000000" size="+6"> 
              <select name="areaId" title = "Area" tabindex="10">
              <option>
                 Please Select
              </option>			
              
              <%
              	for(int i = 0; i < areaIds.length; i++) {
  
              		if (areaId.equalsIgnoreCase(areaIds[i])) {				
              		
              			out.println("<option selected name=\"id\" value=\"" + areaIds[i] + "\">" + areas[i] + "</option>");
              		}
              		else  {
              		
              			out.println("<option name=\"id\" value=\"" + areaIds[i] + "\">" + areas[i] + "</option>");					
              		}		
              	}			
              %> 
              </select>
              </font>
             </td>
		 </tr>
		 <tr>
		 	<td colspan="4" align="left">
			<input type="checkbox" <%= checkedInitial %> id="nonQualInitial" tabindex='11'><font color="#000000">NonQual Initial Purchase</font>
			</td>
          </tr>
		  <tr>
		 	<td colspan="2" align="left">
			<input type="checkbox" <%= checkedExchange %> id="exchange" tabindex='12'><font color="#000000">1035 Exchange</font>
			</td>
          </tr>
        </table>
      </div>
    </td>
    <td rowspan="3" height="179" valign="top" width="1%">&nbsp;</td>
    <td rowspan="3" height="179" valign="top" width="33%">
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
            <input type="text" name="issueAge" size="15" maxlength="3" tabindex='21'
			value= "<%= issueAge %>"
			onFocus="this.select()">
            </font></td>
        </tr>
        <tr> 
          <td height="32" width="13%"><font color="#000000">Sex<font color="#A0365B">*</font> 
            :</font></td>
          <td colspan="5" title="Gender (Usage: push param:Sex)" height="37" width="36%"> 
            <font color="#000000"> 
            <select name="sex" title = "gender" tabindex="22">
                <option>
            	   Please Select
            	</option>			
            	
            	<%
            		for(int i = 0; i < genderCodeIds.length; i++) {
    
            			if (genderId.equalsIgnoreCase(genderCodeIds[i])) {				
            			
            				out.println("<option selected name=\"id\" value=\"" + genderCodeIds[i] + "\">" + genders[i] + "</option>");
            			}
            			else  {
            			
            				out.println("<option name=\"id\" value=\"" + genderCodeIds[i] + "\">" + genders[i] + "</option>");					
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
            <select name="classIds" title = "Class Type" tabindex="23">
                <option>
            	   Please Select
            	</option>			
            	
            	<%
            		for(int i = 0; i < classCodeIds.length; i++) {
    
            			if (classId.equalsIgnoreCase(classCodeIds[i])) {				
            			
            				out.println("<option selected name=\"id\" value=\"" + classCodeIds[i] + "\">" + classes[i] + "</option>");
            			}
            			else  {
            			
            				out.println("<option name=\"id\" value=\"" + classCodeIds[i] + "\">" + classes[i] + "</option>");					
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
            <select name="relationshipInd" title = "Relationship" tabindex="24">
                <option>
            	   Please Select
            	</option>			
            	
            	<%
            		for(int i = 0; i < relationshipIds.length; i++) {
    
            			if (relationshipId.equalsIgnoreCase(relationshipIds[i])) {				
            			
            				out.println("<option selected name=\"id\" value=\"" + relationshipIds[i] + "\">" + relationships[i] + "</option>");
            			}
            			else  {
            			
            				out.println("<option name=\"id\" value=\"" + relationshipIds[i] + "\">" + relationships[i] + "</option>");					
            			}		
            		}			
            	%>
    
            </select>
            </font></td>
        </tr>
        <tr> 
          <td height="33" width="13%"><font color="#000000">Disburse Source<font color="#A0365B">*</font>:</font></td>
          <td colspan="5" title="DisbursementSource (Usage: push param:DisbursementSource)" height="33" width="36%"> 
            <font color="#000000"> 
            <select name="disbursementSource" title = "Disbursement Source" tabindex="25">
                <option>
            	   Please Select
            	</option>			
            	
            	<%
            		for(int i = 0; i < disburseSourceIds.length; i++) {
    
            			if (disburseSourceId.equalsIgnoreCase(disburseSourceIds[i])) {				
            			
            				out.println("<option selected name=\"id\" value=\"" + disburseSourceIds[i] + "\">" + disburseSources[i] + "</option>");
            			}
            			else  {
            			
            				out.println("<option name=\"id\" value=\"" + disburseSourceIds[i] + "\">" + disburseSources[i] + "</option>");					
            			}		
            		}			
            	%>
    
            </select>
            </font></td>
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
            <td colspan="6" bgColor="#30548E" align="center">
                <font size="2" color="#FFFFFF">Coverage Information</font>
            </td>
        </tr>
          <tr>
          <td height="33" width="13%"><font color="#000000">Annuity Option<font color="#A0365B">*</font>:</font></td>
          <td colspan="5" title="Annuity Option (Usage: push param:payoutOption)" height="33" width="36%"> 
            <font color="#000000"> 
            <select name="annuityOption" title = "Annuity Option" tabindex='13'>
                <option>
            	   Please Select
            	</option>			
            	
            	<%
            		for(int i = 0; i < annuityOptionIds.length; i++) {
    
            			if (annuityOptionId.equalsIgnoreCase(annuityOptionIds[i])) {				
            			
            				out.println("<option selected name=\"id\" value=\"" + annuityOptionIds[i] + "\">" + annuityOptions[i] + "</option>");
            			}
            			else  {
            			
            				out.println("<option name=\"id\" value=\"" + annuityOptionIds[i] + "\">" + annuityOptions[i] + "</option>");					
            			}		
            		}			
            	%>
    
            </select>
            </font></td>
        </tr>
		<tr> 
          <td height="43" width="13%"><font color="#000000">Certain Period: 
            </font></td>
          <td colspan="5"  title="Certain Period(Usage: push param:CertainPeriod)" height="43" width="36%"> 
            <font color="#000000"> 
            <input type="text" name="certainPeriod" size="15" maxlength="3" tabindex='14'
			value= "<%= certainPeriod %>"
			onFocus="this.select()">
            </font>
          </td>
        </tr>
          <tr> 
          <td height="33" width="13%"><font color="#000000">Frequency<font color="#A0365B">*</font>:</font></td>
          <td colspan="5" title="Frequency (Usage: push param:Frequency)" height="33" width="36%"> 
            <font color="#000000"> 
            <select name="frequency" title = "Frequency Option" tabindex="15">
                <option>
            	   Please Select
            	</option>
            	<%
            		for(int i = 0; i < frequencyIds.length; i++) {
    
            			if (frequencyId.equalsIgnoreCase(frequencyIds[i])) {				
            			
            				out.println("<option selected name=\"id\" value=\"" + frequencyIds[i] + "\">" + frequencies[i] + "</option>");
            			}
            			else  {
            			
            				out.println("<option name=\"id\" value=\"" + frequencyIds[i] + "\">" + frequencies[i] + "</option>");					
            			}		
            		}			
            	%>
    
            </select>
            </font></td>
        </tr>
		<tr>
			<td width="50%" height="28"><font color="#000000">Purchase Amount:</font>
            </td>
			<td title="Client purchaseAmount (Usage: push param:Amount)"> 
            <font color="#000000"> 
            	<input type="text" name="purchaseAmount" size="15" maxlength="8" tabindex='17'
				value= "<%= purchaseAmount %>"
				onFocus="this.select()">
            </font>
            </td>
		</tr>
		<tr>
			<td width="50%" height="28"><font color="#000000">Payment Amount:</font>
            </td>
			<td title="Client PaymentAmount (Usage: push param:ModalAmount)"> 
            <font color="#000000"> 
            	<input type="text" name="paymentAmount" size="15" maxlength="8" tabindex='18'
				value= "<%= paymentAmount %>"
				onFocus="this.select()">
            </font>
            </td>
		</tr>
		<tr>
		 	<td colspan="4" align="center">
			<input type="checkbox" <%= checkedInvestment %> id="post86Invest" tabindex='19' ><font color="#000000">Post June 30, 1986 Investment</font>
			</td>
        </tr>
		<tr>
			<td width="50%" height="28"><font color="#000000">Cost Basis $:</font>
            </td>
			<td title="Client costBasis (Usage: push param:OrigInvestment)"> 
            <font color="#000000"> 
            	<input type="text" name="costBasis" size="15" maxlength="8" tabindex='20'
				value= "<%= costBasis %>"
				onFocus="this.select()">
            </font>
            </td>
		</tr>
        </table>
      </div>
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
                 onClick="applyPayoutParameters()">
      <input type="button" name="save"  value="   Save    "
           	     title="Save Parameters and Debug Script"
                 onClick="savePayoutParameters()">
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
		if (projectionMode.equalsIgnoreCase("Base Parameters") && editMode.equals(""))  {
	%>	  
      <input type="button" name="apply"  value="   Apply   "
				title="Save parameters and Close Window"
				onClick="applyPayoutBaseParameters()">
      <input type="button"  name="clear" value="    Clear    "
    			title="Clear parameters"
                onClick="clearPayoutBaseParameters()">
      <input type="button" name="project"  value="  Project  "
      		   title="Do Projection"
               onClick="doProjection()">			   
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
  <input id="startDate" 	    type="hidden" name="startDate" value="">
  <input id="rider"				type="hidden" name="rider" 			 value="<%= rider %>">
  <input id="financialType"     type="hidden" name="financialType"   value="<%= financialType %>">
  <input id="selectedIndex" 	type="hidden" name="selectedIndex" 	 value="<%= selectedIndex %>">
  <input id="nonQualInd"        type="hidden" name="nonQualInd" >
  <input id="exchangeInd"       type="hidden" name="exchangeInd" >
  <input id="post86Investment"  type="hidden" name="post86Investment" >

</p>


</body>
</form>
</html>