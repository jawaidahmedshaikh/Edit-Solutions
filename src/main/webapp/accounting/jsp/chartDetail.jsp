<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*,
                 edit.common.*,
                 edit.common.vo.*,
                 fission.beans.*" %>

<jsp:useBean id="elementStructureLists"
    class="fission.beans.SessionBean" scope="request"/>

<jsp:useBean id="structures"
    class="fission.beans.SessionBean" scope="request"/>

<jsp:useBean id="formBean"
	class="fission.beans.FormBean" scope="request"/>

<%
    String message = (String) request.getAttribute("message");
    message = (message == null)?"":message;

    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

	PageBean elementNameLists             = elementStructureLists.getPageBean("elementNameLists");
	PageBean elementStructureNameLists    = elementStructureLists.getPageBean("elementStructureNameLists");

    CodeTableVO[] accountEffectCTVO = codeTableWrapper.getCodeTableEntries("ACCOUNTEFFECT");
    CodeTableVO[] qualNonQualCTVO   = codeTableWrapper.getCodeTableEntries("QUALNONQUAL");

	String   accountEffectId     = formBean.getValue("accountEffectId");
    String companyStructureId    =  elementNameLists.getValue("companyStructureId");

	String[] processNames = elementNameLists.getValues("processNames");
	if(processNames == null){processNames = new String[0];}

	String[] eventNames = elementNameLists.getValues("eventNames");
	if(eventNames == null){eventNames = new String[0];}

	String[] eventTypeNames = elementNameLists.getValues("eventTypeNames");
	if(eventTypeNames == null){eventTypeNames = new String[0];}

	String[] elementNames = elementNameLists.getValues("elementNames");
	if(elementNames == null){elementNames = new String[0];}

	String[] memoCodeNames = elementStructureNameLists.getValues("memoCodeNames");
	if(memoCodeNames == null){memoCodeNames = new String[0];}

	String[] durationNames = elementStructureNameLists.getValues("durationNames");
	if(durationNames == null){durationNames = new String[0];}

    String   qualNonQual      = formBean.getValue("qualNonQual");

	String[] fundNames = elementStructureNameLists.getValues("fundNames");
	if(fundNames == null){fundNames = new String[0];}

    String[] fundKeys = elementStructureNameLists.getValues("fundKeys");
	if(fundKeys == null){fundKeys = new String[0];}

    // These two delimited strings are used to build associative arrays
    // so that charge code select will vary with fund selected.
    // EXAMPLE  "fundname1|123|456^fundname2|678^fundname3|123|789|666"
    String chargeCodes = elementStructureNameLists.getValue("chargeCodes");
    if(chargeCodes == null)
        chargeCodes = "";
    // EXAMPLE  "fundname1|444444|222222^fundname2|111111^fundname3|3333333|3333339|4444444"
    String chargeCodeFKs = elementStructureNameLists.getValue("chargeCodeFKs");
    if(chargeCodeFKs == null)
        chargeCodeFKs = "";

	String elementId   = formBean.getValue("elementId");
	String key         = formBean.getValue("key");

	String process     = formBean.getValue("process");
	String event       = formBean.getValue("event");
	String eventType   = formBean.getValue("eventType");
	String element     = formBean.getValue("element");
    String operator    = formBean.getValue("operator");
    String dateTime    = formBean.getValue("maintDate");

	String memoCode    = formBean.getValue("memoCode");
	String duration    = formBean.getValue("duration");
	String fund        = formBean.getValue("fundName");

    String chargeCodeFK = formBean.getValue("chargeCodeFK");

	String sequence        = formBean.getValue("sequence");
	String effectiveMonth  = formBean.getValue("effectiveMonth");
	String effectiveDay    = formBean.getValue("effectiveDay");
	String effectiveYear   = formBean.getValue("effectiveYear");

	String switchEffectIndStatus = formBean.getValue("switchEffectIndStatus");
	String suppressedIndStatus   = formBean.getValue("suppressedIndStatus");

	String accountNumber      = formBean.getValue("accountNumber");
	String accountName        = formBean.getValue("accountName");
	String accountDescription = formBean.getValue("accountDescription");

	String rowToMatchBase = key;

	if (rowToMatchBase.equals("")) {

		rowToMatchBase = "dummy";
	}
%>

<html>
<head>
<!-- ****** STYLE CODE ***** //-->

<style>

	.highLighted {

		background-color: #FFFFCC;
	}

	.dummy {
	}

	table {

		background-color: #DCDCDC;
	}

</style>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<html>
<script language="JavaScript">

	var f = null;
    var message = "<%= message %>";

	var currentPage = "";

	var selectedProcessValue   = "*";
	var selectedEventValue 	   = "*";
	var selectedEventTypeValue = "*";
	var selectedElementValue   = "*";

	var selectedMemoCodeValue      = "*";
	var selectedDurationValue    = "0";
	var selectedQualNonQualValue = "*";
	var selectedFundNameValue    = "0";

    // associative arrays so we can reset the charge code select
    // whenever the fund changes
    var fundsCCodes = new Array();      // charge code numbers
    var fundsCCodesPKs = new Array();   // their PK values

 	function init()  {

		f = document.accountDetailForm;
		top.frames["main"].setActiveTab("detailTab");
        f.sequence.focus();

		currentPage = "accountingDetail";

        loadArrays();

        if (message != "")
        {
            alert(message);
        }
 	}

 	function selectRow() {

 		var tdElement = window.event.srcElement;
 		var trElement = tdElement.parentElement;

 		var rowId     = trElement.id;

		f.key.value = rowId;

 		sendTransactionAction("AccountingDetailTran", "showSelectedDetails", "contentIFrame");
 	}

	function deleteChartDetails() {

		sendTransactionAction("AccountingDetailTran", "deleteDetailsForElement", "contentIFrame");
	}

	function setProcessSelection() {

		selectedProcessValue = f.processSelect.options[f.processSelect.selectedIndex].text;

		if (selectedProcessValue == "<new>") {
			f.processText.disabled = false;
			f.processText.focus();
			selectedProcessValue = f.processText.value;
            if (selectedProcessValue != ""){
                testForTab(f.eventSelect);
            }
		}
		else if (selectedProcessValue == "Please Select") {

			selectedProcessValue = filterSelection(selectedProcessValue);
		}
		else {
			f.processText.disabled = true;
			f.processText.value = "";
		}
		f.process.value = selectedProcessValue;
	}

	function setEventSelection() {

		selectedEventValue = f.eventSelect.options[f.eventSelect.selectedIndex].text;

		if (selectedEventValue == "<new>") {
			f.eventText.disabled = false;
			f.eventText.focus();
			selectedEventValue = f.eventText.value;
            if (selectedEventValue != ""){
                testForTab(f.eventTypeSelect);
            }
		}
		else if (selectedEventValue == "Please Select") {

			selectedEventValue = filterSelection(selectedEventValue);
		}
		else {
			f.eventText.disabled = true;
			f.eventText.value = "";
		}

		f.event.value = selectedEventValue;
	}

	function setEventTypeSelection() {

		selectedEventTypeValue = f.eventTypeSelect.options[f.eventTypeSelect.selectedIndex].text;

		if (selectedEventTypeValue == "<new>") {
			f.eventTypeText.disabled = false;
			f.eventTypeText.focus();
			selectedEventTypeValue = f.eventTypeText.value;
            if (selectedEventTypeValue != ""){
                testForTab(f.elementSelect);
            }
		}
		else if (selectedEventTypeValue == "Please Select") {

			selectedEventTypeValue = filterSelection(selectedEventTypeValue);
		}
		else {
			f.eventTypeText.disabled = true;
			f.eventTypeText.value = "";
		}

		f.eventType.value = selectedEventTypeValue;
	}

	function setElementSelection() {

		selectedElementValue = f.elementSelect.options[f.elementSelect.selectedIndex].text;

		if (selectedElementValue == "<new>") {
			f.elementText.disabled = false;
			f.elementText.focus();
			selectedElementValue = f.elementText.value;
            if (selectedElementValue != ""){
                testForTab(f.memoCodeSelect);
            }
		}
		else if (selectedElementValue == "Please Select") {

			selectedElementValue = filterSelection(selectedElementValue);
		}
		else {
			f.elementText.disabled = true;
			f.elementText.value = "";
		}

		f.element.value = selectedElementValue;
	}

	function setMemoCodeSelection() {

		selectedMemoCodeValue = f.memoCodeSelect.options[f.memoCodeSelect.selectedIndex].text;
		if (selectedMemoCodeValue == "<new>") {
			f.memoCodeText.disabled = false;
			f.memoCodeText.focus();
			selectedMemoCodeValue = f.memoCodeText.value;
            if (selectedMemoCodeValue != ""){
                testForTab(f.durationSelect);
            }
		}
		else if (selectedMemoCodeValue == "Please Select") {

			selectedMemoCodeValue = filterSelection(selectedMemoCodeValue);
		}
		else {
			f.memoCodeText.disabled = true;
			f.memoCodeText.value = "";
		}

		f.memoCode.value = selectedMemoCodeValue;
	}

	function setDurationSelection() {

		selectedDurationValue = f.durationSelect.options[f.durationSelect.selectedIndex].text;

		if (selectedDurationValue == "<new>") {
			f.durationText.disabled = false;
			f.durationText.focus();
			selectedDurationValue = f.durationText.value;
            if (selectedDurationValue != ""){
                testForTab(f.qualNonQualSelect);
            }
		}
		else if (selectedDurationValue == "Please Select") {

			selectedDurationValue = filterNumericSelection(selectedDurationValue);
		}
		else {
			f.durationText.disabled = true;
			f.durationText.value = "";
		}

		f.duration.value = selectedDurationValue;
	}

	function setFundNameSelection() {

		selectedFundNameValue = f.fundNameSelect.options[f.fundNameSelect.selectedIndex].text;

		if (selectedFundNameValue == "<new>") {
			f.fundNameText.disabled = false;
			f.fundNameText.focus();
			selectedFundNameValue = f.fundNameText.value;
            if (selectedFundNameValue != ""){
                testForTab(f.switchEffectInd);
            }
		}
		else if (selectedFundNameValue == "Please Select") {

			selectedFundNameValue = filterNumericSelection(selectedFundNameValue);
		}
		else {
			f.fundNameText.disabled = true;
			f.fundNameText.value = "";
		}

		f.fundName.value = selectedFundNameValue;
	}

    function setChargeCodeSelection(selectCCode)
    {
        var selected =  selectCCode.options.selectedIndex;
	    var chargeCodeSelected = "";
        if (selected <= 0)
        {
            f.chargeCodeFK.value = "";
            return;
        }
        var fund = f.fundName.value;
        var chgCodesPKsStr = fundsCCodesPKs[fund];
        var arrayOfPKs = chgCodesPKsStr.split("|");
        selected--;    // first option is Please Select
        var selectedPK =  arrayOfPKs[selected];
        f.chargeCodeFK.value = selectedPK;
    }

	function setQualNonQualSelection() {

        selectedQualNonQualValue = f.qualNonQualSelect.options[f.qualNonQualSelect.selectedIndex].text;

        if (selectedQualNonQualValue == "Please Select") {

            selectedQualNonQualValue = filterSelection(selectedQualNonQualValue);
            f.qualNonQual.value = selectedQualNonQualValue;
        }

        else {

            f.qualNonQual.value = f.qualNonQualSelect.options[f.qualNonQualSelect.selectedIndex].value;
        }
	}

	function clearChartDetail() {

		sendTransactionAction("AccountingDetailTran","clearChartDetails", "_self");
	}

	function sendTransactionAction(transaction, action, target) {

		setProcessSelection();
		setEventSelection();
		setEventTypeSelection();
		setElementSelection();

		setMemoCodeSelection();
		setDurationSelection();
		setQualNonQualSelection();
		setFundNameSelection();

		if (f.switchEffectInd.checked == true) {


			f.switchEffectIndStatus.value = "checked";
		}

        else {

            f.switchEffectIndStatus.value = "unchecked";
        }

		if (f.suppressedInd.checked == true) {

			f.suppressedIndStatus.value = "checked";
		}

        else {

            f.suppressedIndStatus.value = "unchecked";
        }

		f.transaction.value=transaction;
		f.action.value=action;
		f.target = target;

		f.submit();
	}

	function filterSelection(val) {

		return "*";

	}

    function filterNumericSelection(val) {

        return "0";
    }

	function initializeChartDetails() {

		f.memoCodeSelect.selectedIndex    = 0;
		f.durationSelect.selectedIndex  = 0;
		f.qualNonQualSelect.selectedIndex = 0;
		f.fundNameSelect.selectedIndex = 0;
		f.switchEffectInd.checked = false;
		f.suppressedInd.checked   = false;
		f.accountNumber.value    = "";
		f.accountName.value      = "";
		f.accountEffectId.selectedIndex = 0;
		f.accountDescription.value = "";

		f.processText.value   = "";
		f.eventText.value     = "";
		f.eventTypeText.value = "";
		f.elementText.value   = "";
		f.memoCodeText.value  = "";
		f.durationText.value  = "";
		f.fundNameText.value  = "";
        f.key.value           = "";

        f.chargeCodeSelect.selectedIndex = 0;
        f.chargeCodeFK.value = "";
	}

	function updateChartDetails() {

		sendTransactionAction("AccountingDetailTran","updateChartDetails", "_self");
	}

	function testForTab(nextField) {

        nextField.focus()
	}

   // load the associative arrays for charge codes
   // keyed by fund number
   function loadArrays()
   {
        loadArray(f.fundarray1.value, fundsCCodes);
        loadArray(f.fundarray2.value, fundsCCodesPKs);
   }


   function loadArray(fundString, fundAssocArray)
   {
   	    var aString = fundString;
   	    var arr1 = aString.split("^");
   	    var i;
   	    for (i=0; i < arr1.length; i++)
   	    {
   	        var aString2 = arr1[i];
   	        var arr2 = aString2.split("|");
   	        var fundname = arr2[0];
   	        var j;
   	        var fundvalues = "";
   	        for (j=1; j < arr2.length; j++)
   	        {
   	    	    if (j > 1)
   	    	    {
   	        	    fundvalues += "|";
   	            }
   	    	    fundvalues += arr2[j];

   	        }
   	        fundAssocArray[fundname] = fundvalues;
	    }
   }

   function rebuildChargeCodeSelect()
   {
        // the fund changed: reset the filtered fund FK
        // if it had been chosen already
        f.chargeCodeFK.value = "";

        var fund = f.fundName.value;

	    // go get the charge codes for the fund
	    var chgCodesStr = fundsCCodes[fund];
        var chgCodesPKsStr = fundsCCodes[fund];

        // clear the charge code select
	    f.chargeCodeSelect.options.length = 0;

        if (chgCodesStr == null)
	    {
            // add None option
	        var newOption = new Option("None Available", "-1");
            var opt = f.chargeCodeSelect.options;
	        opt[opt.length] = newOption;
            f.chargeCodeSelect.disabled = true;
            return;
	    }

        // add Please Select option
        var newOption = new Option("Please Select", "-1");
        var opt = f.chargeCodeSelect.options;
        opt[opt.length] = newOption;

        var array = chgCodesStr.split("|");
        var arrayPKs = chgCodesPKsStr.split("|");
	    var i;
	    for (i=0; i<array.length; i++)
	    {
	        var newOption = new Option(array[i], arrayPKs[i]);
	        opt[opt.length] = newOption;

	    }
	    f.chargeCodeSelect.disabled = false;
   }

</script>
<body bgcolor="#DDDDDD" onLoad="init()" style="border-style:solid; border-width:1;" >
<form name="accountDetailForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="100%" height="150%" border="0" cellspacing="6" cellpadding="0" height="100%" bgcolor="#DDDDDD">
    <tr>
      <td width="45%">Sequence:</td>
      <td nowrap width="10%">Effective Date:</td>
      <td width="12%">Process:</td>
      <td width="11%">Event:</td>
      <td width="11%">Event Type:</td>
      <td width="11%">Element:</td>
    </tr>
    <tr>
      <td width="45%">
        <input type="text" name="sequence" size="4" maxlength="4" tabindex="1" value="<%= sequence %>">
      </td>
      <td nowrap width="10%">
        <input type="text" name="effectiveMonth" size="2" maxlength="2" tabindex="2" value="<%= effectiveMonth %>">
        /
        <input type="text" name="effectiveDay" size="2" maxlength="2" tabindex="3" value="<%= effectiveDay %>">
        /
        <input type="text" name="effectiveYear" size="4" maxlength="4" tabindex="4" value="<%= effectiveYear %>">
      </td>
      <td width="12%">
       <select name="processSelect" tabindex="5" onChange="setProcessSelection()">
        <option selected value="Please Select"> Please Select </option>
	     <%
      	 	out.println("<option>&lt;new&gt;</option>");

            String processListItem = null;

      		for(int i=0; i<processNames.length; ++i) {

                processListItem = processNames[i].trim();

      			if(process.equalsIgnoreCase(processListItem)) {

      				out.println("<option selected>"+processNames[i]+"</option>");
      			}

      			else {

      				out.println("<option>"+processNames[i]+"</option>");
      			}
      		}
         %>
       </select>
      </td>
      <td width="11%">
       <select name="eventSelect" tabindex="6" onChange="setEventSelection()">
	    <%
        	out.println("<option>Please Select</option>");
			out.println("<option>&lt;new&gt;</option>");

            String eventListItem = null;

			for(int i=0; i<eventNames.length; ++i) {

                eventListItem = eventNames[i].trim();

				if(event.equalsIgnoreCase(eventListItem)) {

					out.println("<option selected>"+eventNames[i]+"</option>");
				}

				else {

					out.println("<option>"+eventNames[i]+"</option>");
				}
			}
	    %>
       </select>
      </td>
      <td width="11%">
	    <select name="eventTypeSelect" tabindex="7" onChange="setEventTypeSelection()">
	     <%
	        	out.println("<option>Please Select</option>");
	      		out.println("<option>&lt;new&gt;</option>");

	            String eventTypeListItem = null;

	      		for(int i=0; i<eventTypeNames.length; ++i) {

	                eventTypeListItem = eventTypeNames[i].trim();

	      			if(eventType.equalsIgnoreCase(eventTypeListItem)) {

	      			out.println("<option selected>"+eventTypeNames[i]+"</option>");
	      		}

	      		else {

	      			out.println("<option>"+eventTypeNames[i]+"</option>");
	      		}
	      	}
	      %>
	    </select>
      </td>
      <td width="11%">
       <select name="elementSelect" tabindex="8" onChange="setElementSelection()">
        <%
        	out.println("<option>Please Select</option>");
      		out.println("<option>&lt;new&gt;</option>");

            String elementListItem = null;

      		for(int i=0; i<elementNames.length; ++i) {

                elementListItem = elementNames[i].trim();

      			if(element.equalsIgnoreCase(elementListItem)) {

                    out.println("<option selected>"+elementNames[i]+"</option>");
      		    }

      		    else {

      			    out.println("<option>"+elementNames[i]+"</option>");
      		    }
      	    }
        %>
       </select>
      </td>
    </tr>
    <tr>
      <td colspan="2">&nbsp; </td>
      <td width="12%">
        <input type="text" name="processText" size="20" maxlength="20" disabled="true" onChange="setProcessSelection()">
      </td>
      <td width="11%">
        <input type="text" name="eventText" size="15" maxlength="15" disabled="true" onChange="setEventSelection()">
      </td>
      <td width="11%">
        <input type="text" name="eventTypeText" size="20" maxlength="30"  disabled="true" onChange="setEventTypeSelection()">
      </td>
      <td width="11%">
        <input type="text" name="elementText" size="15" maxlength="15"  disabled="true" onChange="setElementSelection()">
      </td>
    </tr>
    <tr valign="top" bordercolor="#000000">
      <td colspan="6" nowrap>
    	<table width="100%" border="0" cellspacing="1" cellpadding="0" height="100%" bgcolor="#DDDDDD">
          <tr>
            <td>Memo Code:</td>
            <td>Duration:</td>
            <td>Qual/Non-Qual:</td>
            <td>Fund:</td>
            <td>Charge Code:</td>
          </tr>
          <tr>
	        <td height="2">
		     <select name="memoCodeSelect" tabindex="9" onChange="setMemoCodeSelection()">
		      <%
		        	out.println("<option>Please Select</option>");
		      		out.println("<option>&lt;new&gt;</option>");

		            String memoCodeListItem = null;

		      		for(int i=0; i<memoCodeNames.length; ++i) {

		                memoCodeListItem = memoCodeNames[i].trim();

		      			if(memoCode.equalsIgnoreCase(memoCodeListItem)) {

		      			out.println("<option selected>"+memoCodeNames[i]+"</option>");
		      		}

		      		else {

		      			out.println("<option>"+memoCodeNames[i]+"</option>");
		      		}
		      	}
		      %>
		     </select>
            </td>
            <td height="2">
             <select name="durationSelect" tabindex="10" onChange="setDurationSelection()">
		      <%
		        	out.println("<option>Please Select</option>");
		      		out.println("<option>&lt;new&gt;</option>");

		            String durationListItem = null;

                    if (duration.equals("0")) {

                        out.println("<option selected>"+"*"+"</option>");
                    }

                    else {

                        for(int i=0; i< durationNames.length; ++i) {

                            durationListItem = durationNames[i].trim();

                            if(duration.equalsIgnoreCase(durationListItem)) {

                                out.println("<option selected>"+durationNames[i]+"</option>");
                            }

                            else {

                                out.println("<option>"+durationNames[i]+"</option>");
                            }
                        }
                    }
		      %>
		     </select>
            </td>
            <td height="2">
             <select name="qualNonQualSelect" tabindex="11" onChange="setQualNonQualSelection()">
		      <%
		        	out.println("<option>Please Select</option>");

                    for(int i = 0; i < qualNonQualCTVO.length; i++) {

                        if (qualNonQual.equalsIgnoreCase(qualNonQualCTVO[i].getCode())) {

                            out.println("<option selected name=\"id\" value=\""
                              + qualNonQualCTVO[i].getCode() + "\">" + qualNonQualCTVO[i].getCodeDesc() + "</option>");
                        }

                        else  {

                            out.println("<option name=\"id\" value=\""
                              + qualNonQualCTVO[i].getCode() + "\">" + qualNonQualCTVO[i].getCodeDesc() + "</option>");
                        }
                    }

                   if (qualNonQual.equals("*")) {

                       out.println("<option selected name=\"id\" value=\""
                               + qualNonQual + "\">" + qualNonQual + "</option>");
                   }
		       %>
		      </select>
			 </td>
             <td height="2">
              <select name="fundNameSelect" tabindex="12" onChange="setFundNameSelection();rebuildChargeCodeSelect();">
		       <%
		        	out.println("<option>Please Select</option>");
		      		out.println("<option>&lt;new&gt;</option>");

		            String fundListItem = null;

		      		for (int i=0; i< fundNames.length; ++i) {

		                fundListItem = fundNames[i].trim();

                        if (fund.equals("0")) {

                            out.println("<option selected>" + "*" + "</option>");
                        }

                        else {

                            if (fund.equalsIgnoreCase(fundListItem)) {

                                out.println("<option selected>"+fundNames[i]+"</option>");
                            }

                            else {

                                out.println("<option>"+fundNames[i]+"</option>");
                            }
                        }
                    }

		      %>
		     </select>
			</td>


            <td height="2">
		       <%
                    // We are going to use the same data used by the
                    // JavaScript associative arrays.
                    // This complication is due to them being allowed
                    // to select a different fund or tyep it in
                    // and then have this select for charge codes
                    // be alterred to correspond to only the charge codes
                    // and their FKs for that fund.
                    List chargeCodeList = new ArrayList();
                    List chargeCodePKsList = new ArrayList();

                    StringTokenizer st1 = new StringTokenizer(chargeCodes, "^");
                    StringTokenizer st2 = new StringTokenizer(chargeCodeFKs, "^");
                    while (st1.hasMoreTokens())
                    {
                        String chargecodes = st1.nextToken();
                        String chargecodePKs = st2.nextToken(); // st1 and st2 are in sync
                        if (chargecodes.startsWith(fund))
                        {
                            StringTokenizer st3 = new StringTokenizer(chargecodes, "|");
                            StringTokenizer st4 = new StringTokenizer(chargecodePKs, "|");
                            st3.nextToken();  // fund
                            st4.nextToken();  // fund
                            
                            while(st3.hasMoreTokens())
                            {
                                String aChargeCode = st3.nextToken();
                                String aChargeCodePK = st4.nextToken();
                                chargeCodeList.add(aChargeCode);
                                chargeCodePKsList.add(aChargeCodePK);
                            }
                        }
                    }

                    // For the fund chosen, we now have a simple array of charge codes
                    String[] chargeCodesSel =  (String[])chargeCodeList.toArray(new String[0]);
                    // and an array of their PKs
                    String[] chargeCodeFKsSel =  (String[])chargeCodePKsList.toArray(new String[0]);

                    if (chargeCodesSel.length == 0)
                    {
                        out.println("<select disabled name=\"chargeCodeSelect\" tabindex=\"12\" onChange=\"setChargeCodeSelection(this)\">");
                        out.println("<option value='0'>None Available</option>");
                    }
                    else
                    {
                        out.println("<select name=\"chargeCodeSelect\" tabindex=\"12\" onChange=\"setChargeCodeSelection(this)\">");
                        out.println("<option value='0'>Please Select</option>");
                    }

		            String chargeListItem = null;
                    String chargeCodeFKItem = null;
		      		for (int i=0; i< chargeCodesSel.length; ++i) {
		                chargeListItem = chargeCodesSel[i].trim();
                        chargeCodeFKItem = chargeCodeFKsSel[i].trim();
                        if (chargeCodeFK.equalsIgnoreCase(chargeCodeFKItem))
                        {
                            out.println("<option selected value='" +
                                    chargeCodeFKItem + "' >" + chargeListItem + "</option>");
                        }
                        else
                        {
                            out.println("<option value='" +
                                    chargeCodeFKItem + "' >" + chargeListItem + "</option>");
                        }
                    }
		      %>
		     </select>
			</td>



            <td height="2">
              <input type="checkbox" name="switchEffectInd" tabindex="13" <%= switchEffectIndStatus %> >
                Switch Effect
			</td>
          </tr>
          <tr>
            <td height="2">
              <input type="text" name="memoCodeText" size="15" disabled="true" maxlength="15" onChange="setMemoCodeSelection()">
            </td>
            <td height="2">
              <input type="text" name="durationText" size="15" disabled="true" maxlength="15" onChange="setDurationSelection()">
            </td>
            <td height="2">
            </td>
            <td height="2">
              <input type="text" name="fundNameText" size="15" disabled="true" maxlength="15" onChange="setFundNameSelection();rebuildChargeCodeSelect();">
            </td>
            <td></td>
            <td height="2">
              <input type="checkbox" name="suppressedInd" tabindex="14" <%= suppressedIndStatus %> >
              Suppress
			</td>
          </tr>
          <tr>
            <td height="0"  colspan="2" nowrap align="left">Account Number:</td>
            <td height="24" colspan="2" align="left">Account Name:</td>
            <td height="24" colspan="2" align="left" nowrap>Account Effect: </td>
          </tr>
          <tr>
            <td height="31" align="left" valign="top">
              <input type="text" name="accountNumber" size="20" maxlength="20" tabindex="15" value="<%= accountNumber %>">
            </td>
            <td height="31">&nbsp;</td>
            <td colspan="2" height="31" valign="top">
              <input type="text" name="accountName" size="30" maxlength="30" tabindex="16" value="<%= accountName %>">
            </td>
            <td height="31" align="left" valign="top">
             <select tabindex="17" name="accountEffectId">
              <option>Please Select</option>
               <%
                for(int i = 0; i < accountEffectCTVO.length; i++) {

            	 	if (accountEffectId.equalsIgnoreCase(accountEffectCTVO[i].getCode())) {

                        out.println("<option selected name=\"id\" value=\""
                          + accountEffectCTVO[i].getCode() + "\">" + accountEffectCTVO[i].getCodeDesc() + "</option>");
            	 	}

            	 	else  {

                        out.println("<option name=\"id\" value=\""
                          + accountEffectCTVO[i].getCode() + "\">" + accountEffectCTVO[i].getCodeDesc() + "</option>");
            	 	}
                }
              %>
             </select>
            </td>
          </tr>
          <tr align="left" valign="top">
            <td colspan="5">Account Description:</td>
          </tr>
          <tr align="left" valign="top">
            <td colspan="5">
              <input type="text" name="accountDescription" size="100" maxlength="1500" tabindex="18" value="<%= accountDescription%>">
            </td>
	      </tr>
	   </table>
	  </td>
    </tr>
    <tr>
      <td colspan="6">
		<span id="buttonContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:2%; top:0; left:0; z-index:0; overflow:visible">
		  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
		    <tr height="5%">
		      <td align="left" height="14">
	        	<input type="button" name="add" value= " Add  " onClick="initializeChartDetails()">
			    <input type="button" name="save" value=" Save " onClick="updateChartDetails()">
			    <input type="button" name="cancel" value="Cancel" onClick="initializeChartDetails()">
			    <input type="button" name="delete" value="Delete" onClick="deleteChartDetails()">
			  </td>
			</tr>
		  </table>
		</span>
	  </td>
    </tr>
    <tr>
      <td width="100%" height="100%" colspan="6">
        <span id="scrollableContent2" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:scroll">
          <table id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
 	        <tr bgcolor="#30548E">
	          <th align="left"><font color= "#FFFFFF">Memo Code</font></th>
	          <th align="left"><font color= "#FFFFFF">Duration</font></th>
	          <th align="left"><font color= "#FFFFFF">Qual/Non-Qual</font></th>
			  <th align="left"><font color= "#FFFFFF">Fund</font></th>
			  <th align="left"><font color= "#FFFFFF">Account Number</font></th>
			  <th align="left"><font color= "#FFFFFF">Account Name</font></th>
			  <th align="left"><font color= "#FFFFFF">Account Effect</font></th>
	        </tr>
            <%
				int rowId   = 0;
				int columnId = 0;

				String rowToMatch = "";
				String trClass = "";
				String selected = "";

				String[] memoCodeIds = null;

				Map allElementStructures = structures.getPageBeans();

				Iterator it = allElementStructures.values().iterator();

				while (it.hasNext())  {

					PageBean structureSummary = (PageBean)it.next();

					String iKey               = structureSummary.getValue("key");
					String iMemoCodeId        = structureSummary.getValue("memoCode");
					String iDuration          = structureSummary.getValue("duration");
                    if (iDuration.equals("0")) {

                        iDuration = "*";
                    }

					String iQualNonQualId     = structureSummary.getValue("qualNonQual");
                    for(int i = 0; i < qualNonQualCTVO.length; i++) {

                        if (iQualNonQualId.equalsIgnoreCase(qualNonQualCTVO[i].getCode())) {

                            iQualNonQualId = qualNonQualCTVO[i].getCodeDesc();
                        }
                    }

					String iFund              = structureSummary.getValue("fundName");
                    String iFundNumber = "";
                    if (iFund.equals("0")) {

                        iFundNumber = "*";
                    }

                    else {

                        String fundKey = "";
                        for (int f = 0; f < fundKeys.length; f++) {

                            fundKey = fundKeys[f].trim();
                            if (iFund.equalsIgnoreCase(fundKey)) {

                                iFundNumber = fundNames[f].trim();
                                break;
                            }
                        }
                    }

					String iAccountNumber     = structureSummary.getValue("accountNumber");
					String iAccountName       = structureSummary.getValue("accountName");
					String iAccountEffect     = structureSummary.getValue("accountEffectId");
                    for(int i = 0; i < accountEffectCTVO.length; i++) {

                        if (iAccountEffect.equalsIgnoreCase(accountEffectCTVO[i].getCode())) {

                            iAccountEffect = accountEffectCTVO[i].getCodeDesc();
                        }
                    }

					rowToMatch = iKey;

					if (rowToMatch.equals(rowToMatchBase)) {

							trClass = "highLighted";

							selected = "true";
					}
					else {

						trClass = "dummy";

						selected="false";
					}
                %>
				<!-- Diplay the owner row no matter what //-->
				<tr class="<%= trClass %>" selected="<%= selected %>" id="<%= iKey %>" onClick="selectRow()">
					<td nowrap>
						<%= iMemoCodeId %>
					</td>
					<td nowrap>
						<%= iDuration %>
					</td>
					<td nowrap>
						<%= iQualNonQualId %>
					</td>
					<td nowrap>
						<%= iFundNumber %>
					</td>
					<td nowrap>
						<%= iAccountNumber %>
					</td>
					<td nowrap>
						<%= iAccountName %>
					</td>
					<td nowrap>
						<%= iAccountEffect %>
					</td>
				</tr>
            <%
				}// end while
            %>
	   </table>
 	 </span>
   </td>
 </tr>
 <tr>
   <td colspan="3" align="left">Operator:
       <input type="text" name="operator" value="<%= operator %>" size="15" maxlength="15" disabled="true">
   </td>
   <td colspan="3" align="left">Date/Time:
       <input type="text" name="dateTime" value="<%= dateTime %>" size="35" maxlength="35" disabled="true">
   </td>
 </tr>
 <tr>
   <td colspan="6" align="right">
       <input type="button" name="clear" value="Clear" onClick="clearChartDetail()">
   </td>
 </tr>
</table>
<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">

<input type="hidden" name="elementId" value ="<%= elementId %>">

<input type="hidden" name="elementStructure" value ="">
<input type="hidden" name="key" value ="<%= key %>">

<input type="hidden" name="companyStructureId" value="<%= companyStructureId %>">

<input type="hidden" name="process" value ="">
<input type="hidden" name="event" value ="">
<input type="hidden" name="eventType" value ="">
<input type="hidden" name="element" value ="">
<input type="hidden" name="memoCode" value ="">
<input type="hidden" name="duration" value ="">
<input type="hidden" name="qualNonQual" value ="">
<input type="hidden" name="fundName" value ="<%= fund %>">
<input type="hidden" name="switchEffectIndStatus" value="<%=switchEffectIndStatus%>">
<input type="hidden" name="suppressedIndStatus" value="<%=suppressedIndStatus%>">
<input type="hidden" name="chargeCodeFK" value="<%= chargeCodeFK %>">

<!-- the next two are used to create the associative arrays -->
<input type="hidden" name="fundarray1" value="<%= chargeCodes %>">
<input type="hidden" name="fundarray2" value="<%= chargeCodeFKs %>">

</form>

</body>
</html>
