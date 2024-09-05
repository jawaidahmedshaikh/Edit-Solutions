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
                 edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 edit.common.EDITDate,
                 fission.utility.*" %>

<jsp:useBean id="codeTableBean"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="interestRateSessionBean"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="interestRates"
    class="fission.beans.SessionBean" scope="session"/>

<jsp:useBean id="fundInterestSummaries"
    class="fission.beans.SessionBean" scope="session"/>

<%
    CodeTableWrapper codeTableWrapper = CodeTableWrapper.getSingleton();

	PageBean formBean            = interestRateSessionBean.getPageBean("formBean");

    String key                   = formBean.getValue("key");

	String rowToMatchBase       = formBean.getValue("key");
    String interestRateParamId  = formBean.getValue("interestRateParamId");
    String rowToMatchBase1      = formBean.getValue("interestRateId");
	String fundNumber       = formBean.getValue("fundNumber");
    String fundName         = formBean.getValue("fundName");

    String originalDate     = formBean.getValue("originalDate");
	String originalMonth    = formBean.getValue("originalMonth");
	String originalDay      = formBean.getValue("originalDay");
	String originalYear     = formBean.getValue("originalYear");

    String stopDate         = formBean.getValue("stopDate");
    String stopMonth        = formBean.getValue("stopMonth");
    String stopDay        = formBean.getValue("stopDay");
    String stopYear        = formBean.getValue("stopYear");

	String effectiveDate    = formBean.getValue("effectiveDate");
	String effectiveMonth   = formBean.getValue("effectiveMonth");
	String effectiveDay     = formBean.getValue("effectiveDay");
	String effectiveYear    = formBean.getValue("effectiveYear");
    String interestRate     = formBean.getValue("interestRate");
    String guaranteeDuration = formBean.getValue("guaranteeDuration");
    String optionCode	        = formBean.getValue("optionCode");

    CodeTableVO[] options   = codeTableWrapper.getCodeTableEntries("INTERESTPARAMOPTION");

%>

<%!
	private TreeMap sortRatesValuesByEffectiveDate(SessionBean interestRates) {

		Map rateValueBeans = interestRates.getPageBeans();

		TreeMap sortedRateValues = new TreeMap();

		Iterator enumer  = rateValueBeans.values().iterator();


		while (enumer.hasNext()) {

			PageBean rateValueBean = (PageBean) enumer.next();

			String key = rateValueBean.getValue("interestRateId");

			sortedRateValues.put(rateValueBean.getValue("effectiveDate") + key, rateValueBean);
		}
		return sortedRateValues;
	}
%>

<%!
    private TreeMap sortFundSummary(SessionBean fundInterestSummaries) {

		Map fundBeans = fundInterestSummaries.getPageBeans();

		TreeMap sortedFundBeans = new TreeMap();

		Iterator enumer  = fundBeans.values().iterator();

		while (enumer.hasNext()) {

			PageBean fundBean = (PageBean) enumer.next();

            String key = fundBean.getValue("key");
            String fundNumber = fundBean.getValue("fundNumber");
//			String originalDate = fundBean.getValue("originalDate");

			sortedFundBeans.put(fundNumber + key, fundBean);
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

<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="Javascript1.2">

	var dialog = null;

	var f = null;

	function init() {

		f = document.interestRateForm;
	}

	function selectRow() {
		var tdElement  = window.event.srcElement;
		var trElement  = tdElement.parentElement;
		var key        = trElement.id;
		f.key.value    = key;
		sendTransactionAction("TableTran", "showInterestRateDetailSummary", "_self");
	}

	function selectInterestRateRow() {

		var tdElement = window.event.srcElement;
		var trElement = tdElement.parentElement;

		var key     = trElement.id;

		f.interestRateId.value = key;

       	sendTransactionAction("TableTran", "showInterestRates", "_self");
	}

	function initializeInterestRates() {


        f.originalMonth.value  = "";
        f.originalDay.value    = "";
        f.originalYear.value   = "";
        f.stopMonth.value      = "";
        f.stopDay.value        = "";
        f.stopYear.value       = "";
        f.optionCode.value     = "";
		f.effectiveMonth.value = "";
		f.effectiveDay.value   = "";
		f.effectiveYear.value  = "";
		f.interestRate.value   = "";
		f.interestRateId.value = "";
        f.guaranteeDuration.value = "";
     }

	function cancelInterestRates() {

        f.fundNumber.value     = "";
        f.fundName.value       = "";
        f.originalMonth.value  = "";
        f.originalDay.value    = "";
        f.originalYear.value   = "";
        f.stopMonth.value      = "";
        f.stopDay.value        = "";
        f.stopYear.value       = "";
        f.optionCode.value       = "";
        f.effectiveMonth.value = "";
		f.effectiveDay.value   = "";
		f.effectiveYear.value  = "";
		f.interestRate.value   = "";
        f.guaranteeDuration.value        = "";

 		sendTransactionAction("TableTran", "cancelInterestRates", "_self");
	}

	function updateInterestRates() {

 		sendTransactionAction("TableTran", "updateInterestRates", "_self");
	}

	function deleteInterestRates() {

  		sendTransactionAction("TableTran", "deleteInterestRates", "_self");
    }

	function sendTransactionAction(transaction, action, target) {

		f.transaction.value= transaction;
		f.action.value= action;

		f.target = target;

		f.submit();
	}

</script>
</head>

<body bgcolor="#DDDDDD" onLoad="init()">
<form name= "interestRateForm" method="post" action="/PORTAL/servlet/RequestManager">
<span id="mainContent" style="border-style:solid; border-width:1;  position:relative; width:100%; height:48%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" border="0" cellspacing="6" cellpadding="0" bgcolor="#DDDDDD" height="162">
    <tr>
      <td>Fund Number</td>
      <td>Fund Name</td>
      <td>Original Date</td>
      <td>Stop Date</td>
      <td>Option</td>
    </tr>
    <tr>
       <td>
        <input type="text" name="fundNumber" disabled size="10" maxlength="10" value="<%= fundNumber %>">
      </td>
       <td>
        <input type="text" name="fundName" disabled size="30" maxlength="50" value="<%= fundName %>">
      </td>
      <td>
        <input type="text" name="originalMonth" size="2" maxlength="2" value="<%= originalMonth %>">
        /
        <input type="text" name="originalDay" size="2" maxlength="2" value="<%= originalDay %>">
        /
        <input type="text" name="originalYear" size="4" maxlength="4" value="<%=originalYear %>">
      </td>
      <td>
        <input type="text" name="stopMonth" size="2" maxlength="2" value="<%= stopMonth %>">
        /
        <input type="text" name="stopDay" size="2" maxlength="2" value="<%= stopDay %>">
        /
        <input type="text" name="stopYear" size="4" maxlength="4" value="<%=stopYear %>">
      </td>
      <td nowrap>
        <select name="optionCode">
          <option> Please Select </option>
          <%
              for(int i = 0; i < options.length; i++) {

                  String codeTablePK = options[i].getCodeTablePK() + "";
                  String codeDesc    = options[i].getCodeDesc();
                  String code        = options[i].getCode();

                 if (optionCode.equalsIgnoreCase(codeTablePK)) {

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
      <td>&nbsp;</td>
      <td>Effective Date</td>
      <td>Rate</td>
      <td>Guaranteed Duration</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td nowrap>&nbsp;</td>
      <td nowrap>
        <input type="text" name="effectiveMonth" size="2" maxlength="2" value="<%= effectiveMonth %>">
        /
        <input type="text" name="effectiveDay" size="2" maxlength="2" value="<%= effectiveDay %>">
        /
        <input type="text" name="effectiveYear" size="4" maxlength="4" value="<%= effectiveYear %>">
      </td>
      <td nowrap>
        <input type="text" name="interestRate" size="9" maxlength="9" value="<%= interestRate %>">
      </td>
      <td nowrap>
        <input type="text" name="guaranteeDuration" size="5" maxlength="5" value="<%= guaranteeDuration %>">
      </td>
      <td nowrap>&nbsp;</td>
    </tr>
  </table>
 </span>

<span id="buttonContent" style="position:relative; width:100%; height:2%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0">
    <tr height="5%">

      <td align="left" height="14">
        <input type="button" name="add" value= " Add  " onClick="initializeInterestRates()">
		    <input type="button" name="save" value=" Save " onClick="updateInterestRates()">
		    <input type="button" name="cancel" value="Cancel" onClick="cancelInterestRates()">
		    <input type="button" name="delete" value="Delete" onClick="deleteInterestRates()">
		  </td>

		</tr>
	</table>
</span>
 <span id="scrollableContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:30%; top:0; left:0; z-index:0; overflow:scroll">

  <table id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr bgcolor="#30548E">
              <th align="left"><font color= "#FFFFFF">Effective Date</font></th>
              <th align="left"><font color= "#FFFFFF">Rate</font></th>
              <th align="left"><font color= "#FFFFFF">Guaranteed Duration</font></th>
            </tr>
		<%
				int rowId1   = 0;
				int columnId1 = 0;

				String rowToMatch1 = "";
				String trClass1 = "";
				String selected1 = "";

                Map sortedRatesValues = sortRatesValuesByEffectiveDate(interestRates);

                Iterator it2 = sortedRatesValues.values().iterator();

				while (it2.hasNext())  {

					PageBean rateValueBean = (PageBean)it2.next();

					String iInterestRateId    = rateValueBean.getValue("interestRateId");
					String iEffectiveDate     = DateTimeUtil.formatEDITDateAsMMDDYYYY(new EDITDate(rateValueBean.getValue("effectiveDate")));
					String iInterestRate      = rateValueBean.getValue("interestRate");
                    String iGuaranteeDuration = rateValueBean.getValue("guaranteeDuration");

					rowToMatch1 = iInterestRateId;

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
				<tr class="<%= trClass1 %>" selected="<%= selected1 %>" id="<%= iInterestRateId %>" onClick="selectInterestRateRow()">
					<td nowrap>
						<%= iEffectiveDate %>
					</td>
					<td nowrap>
						<%= iInterestRate %>
					</td>
					<td nowrap>
						<%= iGuaranteeDuration %>
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
   			  <th align="left" nowrap><font color= "#FFFFFF">Fund Number</font>&nbsp&nbsp;</th>
			  <th align="left" nowrap><font color= "#FFFFFF">Fund Name</font>&nbsp&nbsp;</th>
              <th align="left" nowrap><font color= "#FFFFFF">Original Date</font>&nbsp&nbsp;</th>
              <th align="left" nowrap><font color= "#FFFFFF">Stop Date</font>&nbsp&nbsp;</th>
              <th align="left" nowrap><font color= "#FFFFFF">Option</font>&nbsp&nbsp;</th>
            </tr>
		<%
				int rowId   = 0;
				int columnId = 0;

				String rowToMatch = "";
				String trClass = "";
				String selected = "";

                Map sortedFundBeans = sortFundSummary(fundInterestSummaries);

				Iterator it = sortedFundBeans.values().iterator();

				while (it.hasNext())  {
                    PageBean fundSummary = (PageBean)it.next();

//				Map allFundSummaries = fundInterestSummaries.getPageBeans();
//              Iterator enum  = allFundSummaries.elements();
//
//				while (enum.hasNext())  {
//
//                    PageBean fundSummary = (PageBean) enum.next();

					String iKey               = fundSummary.getValue("key");
					String iFundNumber        = fundSummary.getValue("fundNumber");
					String iFundName          = fundSummary.getValue("fundName");
                    String iOriginalDate      = Util.initString(fundSummary.getValue("originalDate"), "");
                    if (!iOriginalDate.equals(""))
                    {
                        iOriginalDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(new EDITDate(iOriginalDate));
                    }
                    String iStopDate          = Util.initString(fundSummary.getValue("stopDate"), "");
                    if (!iStopDate.equals(""))
                    {
                        iStopDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(new EDITDate(iStopDate));
                    }
                    String iOptionCode        = fundSummary.getValue("optionCode");

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
						<%= iFundNumber %>
					</td>
					<td nowrap>
						<%= iFundName %>
					</td>
                    <td nowrap>
						<%= iOriginalDate %>
					</td>
                    <td nowrap>
						<%= iStopDate %>
					</td>
                    <td nowrap>
						<%= iOptionCode %>
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
 <input type="hidden" name="interestRateParamId" value="<%= interestRateParamId %>">
 <input type="hidden" name="optionCode"    value="<%= optionCode %>">
 <input type="hidden" name="fundNumber"    value="<%= fundNumber %>">
 <input type="hidden" name="fundName"    value="<%= fundName %>">
 <input type="hidden" name="interestRateId"    value="<%= rowToMatchBase1 %>">

</form>
</body>
</html>
