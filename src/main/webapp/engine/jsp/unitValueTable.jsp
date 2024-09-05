<!--
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.exceptions.VOEditException,
                 edit.common.EDITDate,
				 engine.UnitValues,
                 fission.utility.*" %>

<%
    SessionBean unitValueSessionBean = (SessionBean) session.getAttribute("unitValueSessionBean");
    SessionBean unitValues = (SessionBean) session.getAttribute("unitValues");
    SessionBean fundSummaries = (SessionBean) session.getAttribute("fundSummaries");

    VOEditException voEditException = (VOEditException) session.getAttribute("VOEditException");
    String voEditExceptionExists = "false";
    if (voEditException != null){

        voEditExceptionExists = "true";
    }

	PageBean formBean = unitValueSessionBean.getPageBean("formBean");

    String message = (String) unitValueSessionBean.getValue("message");

    if (message == null) {

        message = "";
    }
    unitValueSessionBean.putValue("message", "");

 	String rowToMatchBase   = formBean.getValue("key");
    String rowToMatchBase1  = formBean.getValue("unitValueId");
    String key              = formBean.getValue("key");
    String unitValueId      = formBean.getValue("unitValueId");

	String fundNumber       = formBean.getValue("fundNumber");
	String fundName         = formBean.getValue("fundName");
    String effectiveDate    = formBean.getValue("effectiveDate");
	String effectiveMonth   = formBean.getValue("effectiveMonth");
	String effectiveDay     = formBean.getValue("effectiveDay");
	String effectiveYear    = formBean.getValue("effectiveYear");
    String unitValue        = formBean.getValue("unitValue");
    String annuityUnitValue = formBean.getValue("annuityUnitValue");
    String updateStatus     = formBean.getValue("updateStatus");
    String netAssetValue1   = formBean.getValue("netAssetValue1");
    String netAssetValue2   = formBean.getValue("netAssetValue2");
    String nav1Assets       = formBean.getValue("nav1Assets");
    String nav2Assets       = formBean.getValue("nav2Assets");
    String uvalAssets       = formBean.getValue("uvalAssets");
    String chargeCode       = formBean.getValue("chargeCode");     // selected
    String chargeCodeFK     = formBean.getValue("chargeCodeFK");   // selected

    // optional selection values
    String[] chargeCodes    = formBean.getValues("chargeCodes");
    String[] chargeCodeFKs  = formBean.getValues("chargeCodeFKs");

    String hasPriorOrCurrentMonthUnitValue = formBean.getValue("hasPriorOrCurrentMonthUnitValue");

%>

<%!
	private TreeMap sortUnitValuesByEffectiveDate(SessionBean unitValues) {

		Map unitValueBeans = unitValues.getPageBeans();

		TreeMap sortedUnitValues = new TreeMap();

		Iterator enumer  = unitValueBeans.values().iterator();

		while (enumer.hasNext()) {

			PageBean unitValueBean = (PageBean) enumer.next();

			String key = unitValueBean.getValue("unitValueId");

			sortedUnitValues.put(unitValueBean.getValue("effectiveDate") + key, unitValueBean);
		}
		return sortedUnitValues;
	}
%>

<%!
    private TreeMap sortFundSummary(SessionBean fundSummaries) {

		Map fundBeans = fundSummaries.getPageBeans();

		TreeMap sortedFundBeans = new TreeMap();

		Iterator enumer  = fundBeans.values().iterator();

		while (enumer.hasNext()) {

			PageBean fundBean = (PageBean) enumer.next();

			String fundNumber = fundBean.getValue("fundNumber");
//            String companyName = fundBean.getValue("companyName");
//            String businessContract = fundBean.getValue("businessContract");

			sortedFundBeans.put(fundNumber, fundBean);
		}
		return sortedFundBeans;
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

<title>EDIT Solutions</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="Javascript1.2">

    var message = "<%= message %>";
    var voEditExceptionExists = "<%= voEditExceptionExists %>";

	var dialog = null;

	var f = null;

    var hasPriorOrCurrentMonthUnitValue = "<%= hasPriorOrCurrentMonthUnitValue %>";

	function init() {

		f = document.unitValuesForm;

        var scrollToRow = document.getElementById("<%= key %>");

        if (scrollToRow != null)
        {

            scrollToRow.scrollIntoView(false);
        }

         var scrollToRow2 = document.getElementById("<%= rowToMatchBase1 %>");

        if (scrollToRow2 != null)
        {

            scrollToRow2.scrollIntoView(false);
        }

        if (message != "" &&
            message != null) {

            alert(message);
        }

        if (hasPriorOrCurrentMonthUnitValue == "false")
        {
            var response = confirm("This fund is missing the prior month final hedge final price.");

            if (response)
            {
                f.verifyPriorOrCurrenMonthUnitValueExistence.value = "false";
                updateUnitValues();
            }
            else
            {
                cancelUnitValues();
            }
        }
    }

	function selectRow() {

		var tdElement  = window.event.srcElement;
		var trElement  = tdElement.parentElement;
		var key        = trElement.id;
		f.key.value    = key;
		sendTransactionAction("TableTran", "showUnitValueDetailSummary", "_self");
	}

	function selectUnitValueRow() {

		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var unitValueId     = trElement.id;

		f.unitValueId.value = unitValueId;

       	sendTransactionAction("TableTran", "showUnitValues", "_self");
	}

	function initializeUnitValues() {

		f.effectiveMonth.value   = "";
		f.effectiveDay.value     = "";
		f.effectiveYear.value    = "";
		f.unitValue.value        = "";
		f.unitValueId.value      = "";
        f.annuityUnitValue.value = "";
        f.netAssetValue1.value   = "";
        f.netAssetValue2.value   = "";
        f.chargeCode.value       = "";
        f.chargeCodeFK.value     = "";
        f.chargeCodeSelect.options.selectedIndex = 0;
        f.updateStatus.options.selectedIndex = 0;

        sendTransactionAction("TableTran", "cancelUnitValues", "_self");
     }

	function cancelUnitValues() {

		f.effectiveMonth.value = "";
		f.effectiveDay.value   = "";
		f.effectiveYear.value  = "";
		f.unitValue.value      = "";
        f.chargeCodeSelect.options.selectedIndex = 0;
        f.updateStatus.options.selectedIndex = 0;

 		sendTransactionAction("TableTran", "cancelUnitValues", "_self");
	}

	function updateUnitValues() {

        if (checkIfOKToUpdate())
        {
 		    sendTransactionAction("TableTran", "updateUnitValues", "_self");
        }
	}

    function checkIfOKToUpdate()
    {

        var selected =  f.chargeCodeSelect.options.selectedIndex;

        var chargeCodeSelected = f.chargeCodeSelect.options[selected].value;

        //alert("charge code selected is " +  chargeCodeSelected);
        if (chargeCodeSelected == "-1")
        {
            alert("Please select a Charge Code");
            return false;
        }

        return true;

    }

	function deleteUnitValues() {

  		sendTransactionAction("TableTran", "deleteUnitValues", "_self");
    }

	function importUnitValues() {

        var importForm = document.FileForm;

        importForm.target = "_self";

		importForm.submit();

        // display file selection box
    }

    function checkForVOEditException(){

        if (voEditExceptionExists == "true"){

            openDialog("", "voEditExceptionDialog", "resizable=no,width=" + .75 * screen.width + ",height=" + screen.height/3,"TableTran", "showVOEditExceptionDialog", "voEditExceptionDialog");
        }
    }


    function setChargeCode(selectCCode)
    {
        var selected =  selectCCode.options.selectedIndex;

        var chargeCodeSelected = selectCCode.options[selected].value;

        f.chargeCodeFK.value = chargeCodeSelected;
    }

    function openDialog(theURL,winName,features,transaction,action) {

	    dialog = window.open(theURL,winName,features);

	    sendTransactionAction(transaction, action, winName);
	}

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value= transaction;
		f.action.value= action;

		f.target = target;

		f.submit();
	}

</script>
</head>

<body bgcolor="#DDDDDD" onLoad="init(); checkForVOEditException()">
<form name= "unitValuesForm" method="post" action="/PORTAL/servlet/RequestManager">

<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:48%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" border="0" cellspacing="6" cellpadding="0" bgcolor="#DDDDDD">
    <tr>
      <td align="left">
      <table width="100%" border="0" cellspacing="0" cellpadding="4">
        <tr>
          <td align="right" nowrap>Fund Number:&nbsp;</td>
          <td align="left">
            <input type="text" name="fundNumber" disabled size="10" maxlength="10" value="<%= fundNumber %>">
          </td>
          <td align="right" nowrap>Fund Name:&nbsp;</td>
          <td align="left">
            <input type="text" name="fundName" disabled size="30" maxlength="50" value="<%= fundName %>">
          </td>
          <td align="right" nowrap>Effective Date:&nbsp;</td>
          <td align="left nowrap">
            <input type="text" name="effectiveMonth" size="2" maxlength="2" value="<%= effectiveMonth %>">
            /
            <input type="text" name="effectiveDay" size="2" maxlength="2" value="<%= effectiveDay %>">
            /
            <input type="text" name="effectiveYear" size="4" maxlength="4" value="<%= effectiveYear %>">
          </td>
        </tr>
        <tr>
          <td align="right" nowrap>Unit Value:&nbsp;</td>
          <td align="left">
            <input type="text" name="unitValue" size="19" maxlength="19" value="<%= unitValue %>">
          </td>
          <td align="right" nowrap>Annuity Unit Value:&nbsp;</td>
          <td align="left">
            <input type="text" name="annuityUnitValue" size="19" maxlength="19" value="<%= annuityUnitValue %>">
          </td>
          <td align="right" nowrap>Update Status:&nbsp;</td>
          <td align="left">
            <select id="updateStatus" name="updateStatus">
                <option value="null">Please Select</option>
                <% if (UnitValues.UPDATESTATUS_HEDGE.equals(updateStatus))
                        out.println("<option value=\"" + UnitValues.UPDATESTATUS_HEDGE + "\" selected>Hedge</option>");
                   else
                        out.println("<option value=\"" + UnitValues.UPDATESTATUS_HEDGE + "\">Hedge</option>");
                %>
            </select>
          </td>
        </tr>
        <tr>
          <td align="right" nowrap>Net Asset Value 1:&nbsp;</td>
          <td align="left">
            <input type="text" name="netAssetValue1" maxlength="19" size="19" value="<%= netAssetValue1 %>">
          </td>
          <td align="right" nowrap>Net Asset Value 2:&nbsp;</td>
          <td align="left">
            <input type="text" name="netAssetValue2" maxlength="19" size="19" value="<%= netAssetValue2 %>">
          </td>
          <td align="right" nowrap>Charge Code:&nbsp;</td>
          <td align="left">
            <select id="chargeCodeSelectID" name="chargeCodeSelect"
                onChange="setChargeCode(this)"
                <%
                    if (chargeCodes.length==0) {
                        out.print("disabled");
                    }
                %>
             >  <!-- end of select tag start -->

            <%
                if (chargeCodes.length==0) {
                    out.println("<option value=\"0\">None</option>");
                }
                else {
                    out.println("<option value=\"-1\">Please Select</option>");
                    for (int i=0; i<chargeCodes.length; i++)
                    {
                        String optionVal = chargeCodeFKs[i];
                        String optionDisplay = chargeCodes[i];
                        if(optionVal.equalsIgnoreCase(chargeCodeFK))
                        {
                            out.println(
                                "<option selected value='" + optionVal + "' >" + optionDisplay + "</option>");
                        }
                        else
                        {
                            out.println(
                                "<option value='" + optionVal + "' >" + optionDisplay + "</option>");
                        }
                    }
                }
            %>
            </select>
          </td>
          <td align="right" nowrap>&nbsp;</td>
          <td align="left">&nbsp;</td>
        </tr>
        <tr>
          <td align="right" nowrap>NAV1 Assets:&nbsp;</td>
          <td align="left">
            <input type="text" name="nav1Assets" maxlength="19" size="19" value="<%= nav1Assets %>">
          </td>
          <td align="right" nowrap>NAV2 Assets:&nbsp;</td>
          <td align="left">
            <input type="text" name="nav2Assets" maxlength="19" size="19" value="<%= nav2Assets %>">
          </td>
          <td align="right" nowrap>UVAL Assets:&nbsp;</td>
          <td align="left">
            <input type="text" name="uvalAssets" maxlength="19" size="19" value="<%= uvalAssets %>">
          </td>
          <td align="right" nowrap>&nbsp;</td>
          <td align="left">&nbsp;</td>
          <td align="right" nowrap>&nbsp;</td>
          <td align="left">&nbsp;</td>
        </tr>
      </table>
      </td>
    </tr>
  </table>

 </span>

<span id="buttonContent" style="position:relative; width:100%; height:2%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
    <tr height="5%">
      <td align="left" height="14">
        <input type="button" name="add" value= " Add  " onClick="initializeUnitValues()">
	    <input type="button" name="save" value=" Save " onClick="updateUnitValues()">
	    <input type="button" name="cancel" value="Cancel" onClick="cancelUnitValues()">
	    <input type="button" name="delete" value="Delete" onClick="deleteUnitValues()">
      </td>
	</tr>
  </table>
</span>
 <span id="scrollableContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:30%; top:0; left:0; z-index:0; overflow:scroll">
        <table id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
           <tr bgcolor="#30548E">
              <th align="left"><font color= "#FFFFFF">Effective Date</font></th>
              <th align="left"><font color= "#FFFFFF">Unit Value</font></th>
			  <th align="left"><font color= "#FFFFFF">AnnuityUnit Value</font></th>
			  <th align="left"><font color= "#FFFFFF">Charge Code</font></th>
            </tr>
		<%
				int rowId1   = 0;
				int columnId1 = 0;

				String rowToMatch1 = "";
				String trClass1 = "";
				String selected1 = "";

				String[] optionIds1 = null;

                boolean firstUV = true;

                Map sortedUnitValues = sortUnitValuesByEffectiveDate(unitValues);

                Iterator it2 = sortedUnitValues.values().iterator();

				while (it2.hasNext())  {

					PageBean unitValueBean = (PageBean) it2.next();

					String iUnitValueId      = unitValueBean.getValue("unitValueId");
					String iEffectiveDate    = DateTimeUtil.formatEDITDateAsMMDDYYYY(new EDITDate(unitValueBean.getValue("effectiveDate")));
					String iUnitValue        = unitValueBean.getValue("unitValue");
                    String iAnnuityUnitValue = unitValueBean.getValue("annuityUnitValue");
                    String iChargeCode       = unitValueBean.getValue("chargeCode");

                    if (firstUV) {

                        firstUV = false;
                        if (rowToMatchBase1.equals("")) {

                            rowToMatchBase1 = iUnitValueId;
                        }
                    }

					rowToMatch1 = iUnitValueId;

					if (rowToMatch1.equals(rowToMatchBase1)) {

							trClass1 = "highLighted";

							selected1 = "true";
					}
					else {

						trClass1 = "dummy";

						selected1="false";
					}
			%>

				<!-- Diplay the owner row no matter what //-->
				<tr class="<%= trClass1 %>" selected1="<%= selected1 %>" id="<%= iUnitValueId %>" onClick="selectUnitValueRow()">
					<td nowrap>
						<%= iEffectiveDate %>
					</td>
					<td nowrap>
						<%= iUnitValue %>
					</td>
					<td nowrap>
						<%= iAnnuityUnitValue %>
					</td>
                    <td nowrap>
						<%= iChargeCode %>
					</td>
				</tr>
		<%
				}// end while
		%>
		</table>
</span>
<span id="scrollableContent2" style="border-style:solid; border-width:1; position:relative; width:100%; height:30%; top:0; left:0; z-index:0; overflow:scroll">
        <table id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
           <tr bgcolor="#30548E">
			  <th align="left"><font color= "#FFFFFF">Fund Number</font></th>
			  <th align="left"><font color= "#FFFFFF">Fund Name</font></th>
            </tr>
		<%
				int rowId   = 0;
				int columnId = 0;

				String rowToMatch = "";
				String trClass = "";
				String selected = "";

				String[] optionIds = null;

                Map sortedFundBeans = sortFundSummary(fundSummaries);

				Iterator it = sortedFundBeans.values().iterator();

				while (it.hasNext())  {

					PageBean fundSummary = (PageBean)it.next();

					String iKey               = fundSummary.getValue("key");
					String iFundNumber        = fundSummary.getValue("fundNumber");
					String iFundName          = fundSummary.getValue("fundName");

					rowToMatch = iKey;
					//System.out.println(key + " " + rowToMatchBase);

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
						<%= iFundNumber %>
					</td>
					<td nowrap>
						<%= iFundName %>
					</td>
				</tr>
		<%
				}// end while
		%>
		</table>
	</span>
<!-- ****** HIDDEN FIELDS ***** //-->
 <input type="hidden" name="transaction" value="">
 <input type="hidden" name="action"      value="">
 <input type="hidden" name="key"         value="<%= key %>">
 <input type="hidden" name="unitValueId" value="<%= unitValueId %>">
 <input type="hidden" name="rowToMatchBase1" value="<%= rowToMatchBase1 %>">

 <input type="hidden" name="fundNumber" value="<%= fundNumber %>">
 <input type="hidden" name="fundName" value="<%= fundName %>">

 <input type="hidden" name="chargeCode" value="<%= chargeCode %>">
 <input type="hidden" name="chargeCodeFK" value="<%= chargeCodeFK %>" >

 <input type="hidden" name="verifyPriorOrCurrenMonthUnitValueExistence" value="true">

</form>
         <td colspan="2" width="80%" align="right" nowrap >
            <br>
        <form name="FileForm" method="post" action="/PORTAL/servlet/RequestManager?transaction=TableTran&action=importUnitValues" enctype="multipart/form-data">
			<input type="file" name="fileName" accept="text" size="17" title="Select File for Import">
             <input type="button" name="import" value="Import" onClick="importUnitValues()">
		</form>
</body>
</html>
