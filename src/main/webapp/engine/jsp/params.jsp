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
	String[] areaIds = codeTableBean.getValues("stateCodes");
	String[] areas   = codeTableBean.getValues("stateCodeDescriptions");
	String   areaId	 = pageBean.getValue("areaId");
	if (areaId == null || areaId.length() == 0){areaId = "";}	
		
	String[] option7702Ids = codeTableBean.getValues("option7702Codes");
	String[] option7702s   = codeTableBean.getValues("option7702CodeDescriptions");
	String option7702Id    = pageBean.getValue("option7702Id");	
	if (option7702Id == null || option7702Id.length() == 0){option7702Id = "";}

	String[] deathBenOptCodeIds = codeTableBean.getValues("deathBenOptCodes");
	String[] deathBenOpts       = codeTableBean.getValues("deathBenOptCodeDescriptions");
	String deathBenOptId 		= pageBean.getValue("deathBenOptId");	
	if (deathBenOptId == null || deathBenOptId.length() == 0){deathBenOptId = "";}
			
	String[] genderCodeIds = codeTableBean.getValues("genderCodes");
	String[] genders       = codeTableBean.getValues("genderCodeDescriptions");
	String genderId 	   = pageBean.getValue("genderId");
	if (genderId == null || genderId.length() == 0){genderId = "";}		
	
	String[] classCodeIds = codeTableBean.getValues("classCodes");
	String[] classes      = codeTableBean.getValues("classCodeDescriptions");
	String classId 	      = pageBean.getValue("classId");	
	if (classId == null || classId.length() == 0){classId = "";}	
	
	String[] relationshipIds   = codeTableBean.getValues("lifeRelationTypesCodes");
	String[] relationships     = codeTableBean.getValues("lifeRelationTypesCodeDescriptions");
	String relationshipId 	   = pageBean.getValue("relationshipId");	
	if (relationshipId == null || relationshipId.length() == 0){relationshipId = "";}	
	
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
	
	String terminationDateDay 	= pageBean.getValue("terminationDateDay");
	if (terminationDateDay == null || terminationDateDay.length() == 0) {terminationDateDay = "9";}
	
	String terminationDateMonth = pageBean.getValue("terminationDateMonth");
	if (terminationDateMonth == null || terminationDateMonth.length() == 0) {terminationDateMonth = "9";}	
	
	String terminationDateYear 	= pageBean.getValue("terminationDateYear");
	if (terminationDateYear == null || terminationDateYear.length() == 0) {terminationDateYear = EDITDate.DEFAULT_MAX_YEAR;}
	
	
	String premPayTerm = pageBean.getValue("premPayTerm");
	if (premPayTerm == null || premPayTerm.length() == 0) {premPayTerm = "NA";}	
	
	String interestRate = pageBean.getValue("interestRate");
	if (interestRate == null || interestRate.length()== 0) {interestRate = "0.0";}		
	
	String coverageAmount = pageBean.getValue("coverageAmount");
	if (coverageAmount == null || coverageAmount.length() == 0) {coverageAmount = "0.0";}	
	
	String issueAge = pageBean.getValue("issueAge");
	if (issueAge == null || issueAge.length() == 0) {issueAge = "0";}	
	
	
	String tableRating = pageBean.getValue("tableRating");
	if (tableRating == null || tableRating.length() == 0) {tableRating = "NA";}	
	
	String flatExtra = pageBean.getValue("flatExtra");
	if (flatExtra == null || flatExtra.length() == 0) {flatExtra = "0";}	
	
	String flatAge = pageBean.getValue("flatAge");
	if (flatAge == null || flatAge.length() == 0) {flatAge = "0";}	
	
	String flatDuration = pageBean.getValue("flatDuration");
	if (flatDuration == null || flatDuration.length() == 0) {flatDuration = "0";}	
	
	String percentExtra = pageBean.getValue("percentExtra");
	if (percentExtra == null || percentExtra.length() == 0) {percentExtra = "0.0";}	
	
	String percentAge = pageBean.getValue("percentAge");
	if (percentAge == null || percentAge.length() == 0) {percentAge = "0";}	
	
	String percentDuration 	= pageBean.getValue("percentDuration");	
	if (percentDuration == null || percentDuration.length() == 0) {percentDuration = "0";}

%>

<html>
<head>
<title>ENGINE - Parameter Entry Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/ENGINE.css" rel="stylesheet" type="text/css">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>


<script language="JavaScript">

    var keyIsSet = null;
	var f = null;
	var enableRidersLink = null;
	var projectionMode = null;
    var width = screen.width;
    var height = screen.height;

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
		
			f.interestRate.disabled = true;	
			f.premPayTerm.disabled = true;
			f.issueAge.disabled = true;
			f.tableRating.disabled = true;
			f.flatExtra.disabled = true;
			f.flatAge.disabled = true;
			f.flatDuration.disabled = true;
			f.percentExtra.disabled = true;
			f.percentAge.disabled = true;
			f.percentDuration.disabled = true;
			f.effectiveDateMonth.disabled = true;
			f.effectiveDateDay.disabled = true;
			f.effectiveDateYear.disabled = true;
			f.terminationDateMonth.disabled = true;
			f.terminationDateDay.disabled = true;
			f.terminationDateYear.disabled = true;
			f.coverageAmount.disabled = true;
			f.areaId.disabled = true;
			f.option7702Id.disabled = true;
			f.deathBenOptId.disabled = true;
			f.genderId.disabled = true;
			f.classId.disabled = true;
			f.relationshipInd.disabled = true;
		}
		else
		{					
			f.interestRate.disabled = false;	
			f.premPayTerm.disabled = false;
			f.issueAge.disabled = false;
			f.tableRating.disabled = false;
			f.flatExtra.disabled = false;
			f.flatAge.disabled = false;
			f.flatDuration.disabled = false;
			f.percentExtra.disabled = false;
			f.percentAge.disabled = false;
			f.percentDuration.disabled = false;
			f.effectiveDateMonth.disabled = false;
			f.effectiveDateDay.disabled = false;
			f.effectiveDateYear.disabled = false;
			f.terminationDateMonth.disabled = false;
			f.terminationDateDay.disabled = false;
			f.terminationDateYear.disabled = false;
			f.coverageAmount.disabled = false;
			f.areaId.disabled = false;
			f.option7702Id.disabled = false;
			f.deathBenOptId.disabled = false;
			f.genderId.disabled = false;
			f.genderId.disabled = false;
			f.interestRate.focus();
			f.relationshipInd.disabled = false;
			
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

	function applyBaseParameters() {

		buildDates();

		sendTransactionAction("ParamTran", "applyBaseParameters", "main");
	}

	function clearBaseParameters() {

		sendTransactionAction("ParamTran", "clearBaseParameters", "main");
	}

	function doProjection() {

		buildDates();

		sendTransactionAction("ScriptTran", "doProjection", "main");
	}

	function buildDates() {

		f.effectiveDate.value = formatDate(f.effectiveDateMonth.value, f.effectiveDateDay.value, f.effectiveDateYear.value, false);

 	    f.terminationDate.value = formatDate(f.terminationDateMonth.value, f.terminationDateDay.value, f.terminationDateYear.value, false);
	}

	function applyDebugParameters() {

		buildDates();

		sendTransactionAction("ParamTran", "applyDebugParameters", "main");
	}

	function saveDebugParameters() {

		buildDates();

        openDialog("","analyzerDialog","left=0,top=0,scrollbars=yes,resizable=no,width=" + width + ",height=" + height)
		sendTransactionAction("ParamTran", "saveDebugParameters", "analyzerDialog");
	}

	function openDialog(theURL,winName,features,transaction,action) {

		dialog = window.open(theURL,winName,features);
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

</script>

</head>

<!-- ****** HTML CODE ***** //-->

<body style="margin-top:5;margin-left:10" onLoad="init()" bgColor="#99BBBB">

<form name="paramForm" method="post" action="/PORTAL/servlet/RequestManager">
<p></p>
<table width="89%" border="0" rules="none" cellspacing="0" cellpadding="0" height="70%">

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
        <table width="100%" bgColor="#DDDDDD" border="3" rules="none" cellspacing="0" cellpadding="3" height="96" bordercolor="#000000">
        <tr>
            <td colspan="4" bgColor="#30548E" align="center">
                <font size="2" color="#FFFFFF">Policy Information</font>
            </td>
        </tr>


          <tr>
            <td width="50%" height="18"><font color="#000000">Interest Rate%<font color="#A0365B">*</font>:</font></td>
            <td colspan="3"
            title="Interest Rate of Return (Usage: push param:InterestRate)" height="18" width="62%">
              <font color="#000000" size="+6">
              <input type="text" name="interestRate" size="15" maxlength="3" tabindex='1'
             value="<%= interestRate %>"
                onFocus="this.select()">
              </font></td>
          </tr>
          <tr> 
            <td width="50%" height="18"><font color="#000000">Resident State<font color="#A0365B">*</font>:</font></td>
            <td colspan="3" 
            title="Resident State (Usage: push param:ResidentState)" height="18" width="62%"> 
              <font color="#000000" size="+6"> 
              <select name="areaId" title = "Area" tabindex="2">
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
              </font></td>
          </tr>
          <tr> 
            <td width="50%" height="30"><font color="#000000">Prem Pay Term: </font></td>
            <td colspan="3" 
            title="Premium Payment Term (Usage: push param:PremPayTerm)" height="30" width="62%"> 
              <font color="#000000" size="+6"> 
              <input type="text" name="premPayTerm" size="15" maxlength="3" tabindex='3'
                value= "<%= premPayTerm %>"
                onFocus="this.select()" >
              </font></td>
          </tr>
        </table>
      </div>
    </td>
    <td rowspan="3" height="179" valign="top" width="1%">&nbsp;</td>
    <td rowspan="3" height="179" valign="top" width="33%"> 
      <table width="100%" bgColor="#DDDDDD" border="3" rules="none" cellspacing="0" cellpadding="3" height="400" bordercolor="#000000">
        <tr>
            <td colspan="6" bgColor="#30548E" align="center">
                <font size="2" color="#FFFFFF">Client Information</font>
            </td>
        </tr>

        <tr>
          <td height="43" width="13%"><font color="#000000">Issue Age<font color="#A0365B">*</font>: 
            </font></td>
          <td colspan="5"  title="Client Issue Age (Usage: push param:IssueAge)" height="43" width="36%"> 
            <font color="#000000"> 
            <input type="text" name="issueAge" size="15" maxlength="3" tabindex='13'
			value= "<%= issueAge %>"
			onFocus="this.select()">
            </font></td>
        </tr>
        <tr> 
          <td height="37" width="13%"><font color="#000000">Sex<font color="#A0365B">*</font> 
            :</font></td>
          <td colspan="5" title="Gender (Usage: push param:Sex)" height="37" width="36%"> 
            <font color="#000000"> 
            <select name="genderId" title = "gender" tabindex="14">
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
            <select name="classId" title = "Class Type" tabindex="15">
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
            <select name="relationshipInd" title = "Relationship" tabindex="16">
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
          <td width="13%" height="39"><font color="#000000">Table Rating: </font></td>
          <td colspan="5" title="Table Rating (Usage: push param:TableRating)" height="39" width="36%"> 
            <font color="#000000"> 
            <input type="text" name="tableRating" tabindex='17' size="15" maxlength="2"
			value= "<%= tableRating %>"
			onFocus="this.select()">
            </font></td>
        </tr>
        <tr> 
          <td width="13%" height="40"><font color="#000000">Flat Extra: </font></td>
          <td colspan="5" 
            title="Flat Extra (Usage: push param:FlatExtra)" height="40" width="36%"> 
            <font color="#000000"> 
            <input type="text" name="flatExtra" size="15" maxlength="8" tabindex='18'
                value= "<%= flatExtra %>"
                onFocus="this.select()">
            </font></td>
        </tr>
        <tr> 
          <td class="dataHeading" width="13%" height="39" align="left" ><font color="#000000"> 
            Flat Age: </font></td>
          <td colspan="5" height="39" width="36%"> <font color="#000000"> 
            <input type="text" name="flatAge" size="3" maxlength="3" tabindex='19'
                value="<%= flatAge %>"                    ""
                title="Flat Age (Usage: push param:FlatAge)"
                onFocus="this.select()">
            &nbsp; </font></td>
        </tr>
        <tr> 
          <td class="dataHeading" width="13%" height="40" align="left"><font color="#000000"> 
            Flat Dur: </font></td>
          <td colspan="5" title="Table Rating (Usage: push param:TableRating)" height="40" width="36%"> 
            <font color="#000000"> 
            <input type="text" name="flatDuration" size="3" maxlength="3" tabindex='20'
                value= "<%= flatDuration %>"
                title="Flat Duration (Usage: push param:FlatDur)"
                onFocus="this.select()">
            </font></td>
        </tr>
        <tr> 
          <td class="dataHeading" nowrap width="13%" height="40"><font color="#000000"> 
            Percent Extra: </font></td>
          <td colspan="5" 
            title="Percent Extra (Usage: push param:PercentExtra)" height="40" width="36%"> 
            <font color="#000000"> 
            <input type="text" name="percentExtra" size="15" maxlength="6" tabindex='21'
                value="<%= percentExtra %>"
                onFocus="this.select()">
            </font></td>
        </tr>
        <tr> 
          <td class="dataHeading" width="13%" height="43"><font color="#000000"> 
            Percent Age: </font></td>
          <td colspan="5" height="43" width="36%"> <font color="#000000"> 
            <input type="text" name="percentAge" size="3" maxlength="3" tabindex='22'
                value= "<%= percentAge %>"
                title="Percent Age (Usage: push param:PercentAge)"
                onFocus="this.select()">
            &nbsp; </font></td>
        </tr>
        <tr> 
          <td class="dataHeading" height="41" width="13%"><font color="#000000">Percent 
            Dur:</font></td>
          <td colspan="5" height="41" width="36%"> <font color="#000000"> 
            <input type="text" name="percentDuration" size="3" maxlength="3" tabindex='23'
                value= "<%= percentDuration %>"
                title="Percent Duration (Usage: push param:PercentDur)"
                onFocus="this.select()">
            </font></td>
        </tr>
      </table>
    </td>
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
              onFocus="this.select()" onKeyUp="testForTab(this, f.terminationDateMonth, 4)">
              </font></td>
          </tr>
          <tr> 
            <td width="50%" height="28"><font color="#000000">Termination Date: 
              </font></td>
            <td colspan="3" height="28"> <font color="#000000"> 
              <input type="text" name="terminationDateMonth" tabindex='7'
		  size="2" maxlength="2"
		value= "<%= terminationDateMonth %>"
			 title="Termination Month (MM)"
			 onFocus="this.select()" onKeyUp="testForTab(this, f.terminationDateDay, 2)">
              / 
              <input type="text" name="terminationDateDay" tabindex='8' size="2" maxlength="2"
			value="<%= terminationDateDay %>" title="Termination Day (DD)"
			onFocus="this.select()" onKeyUp="testForTab(this, f.terminationDateYear, 2)">
              / 
              <input type="text" name="terminationDateYear" tabindex='9' size="4" maxlength="4"
			 value=	"<%= terminationDateYear %>"  title="Termination Year (YYYY)"
			  onFocus="this.select()">
              </font></td>
          </tr>
          <tr> 
            <td width="50%" height="28"><font color="#000000">7702 Option<font color="#A0365B">*</font>: 
              </font></td>
            <td colspan="3" height="28"> <font color="#000000"> 
              <select name="option7702Id" title = "7702Option" tabindex="10">
              <option>
                 Please Select
              </option>			
              
              <%
              	for(int i = 0; i < option7702Ids.length; i++) {
  
              		if (option7702Id.equalsIgnoreCase(option7702Ids[i])) {				
              		
              			out.println("<option selected name=\"id\" value=\"" + option7702Ids[i] + "\">" + option7702s[i] + "</option>");
              		}
              		else  {
              		
              			out.println("<option name=\"id\" value=\"" + option7702Ids[i] + "\">" + option7702s[i] + "</option>");
              		}		
              	}			
              %> 
              </select>
              </font></td>
          </tr>
          <tr> 
            <td width="50%" height="28"><font color="#000000">Death Benefit Option<font color="#A0365B">*</font>:</font></td>
            <td colspan="3" 
            title="Death Benefit Option (Usage: push param:DeathBenefitOpt)" height="28"> 
              <font color="#000000"> 
              <select name="deathBenOptId" title = "gender" tabindex="11">
                  <option>
              	   Please Select
              	</option>			
              	
              	<%
              		for(int i = 0; i < deathBenOptCodeIds.length; i++) {
      
              			if (deathBenOptId.equalsIgnoreCase(deathBenOptCodeIds[i])) {				
              			
              				out.println("<option selected name=\"id\" value=\"" + deathBenOptCodeIds[i] + "\">" + deathBenOpts[i] + "</option>");
              			}
              			else  {
              			
              				out.println("<option name=\"id\" value=\"" + deathBenOptCodeIds[i] + "\">" + deathBenOpts[i] + "</option>");
              			}		
              		}			
              	%>
              </select>		
              </font></td>
          </tr>
          <tr> 
            <td width="50%" height="45"><font color="#000000">Coverage Amount:</font></td>
            <td colspan="3" 
           title="Coverage Amount on Policy (Usage: push param:CoverageAmount)" height="45"> 
              <font color="#000000"> 
              <input type="text" name="coverageAmount" tabindex='12' size="15" maxlength="15"
             value= "<%= coverageAmount %>"
               onFocus="this.select()">
              </font></td>
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
                 onClick="applyDebugParameters()">
      <input type="button" name="save"  value="   Save    "
           	     title="Save Parameters and Debug Script"
                 onClick="saveDebugParameters()">
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
				onClick="applyBaseParameters()">
      <input type="button"  name="clear" value="    Clear    "
    			title="Clear parameters"
                onClick="clearBaseParameters()">
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
  <input id="terminationDate" 	type="hidden" name="terminationDate" value="">
  <input id="rider"				type="hidden" name="rider" 			 value="<%= rider %>">
  <input id="financialType"     type="hidden" name="financialType"   value="<%= financialType %>">
  <input id="selectedIndex" 	type="hidden" name="selectedIndex" 	 value="<%= selectedIndex %>">
</p>


</body>
</form>
</html>