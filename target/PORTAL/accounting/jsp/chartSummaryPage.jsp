<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.EDITDate" %>

<jsp:useBean id="elements"
	class="fission.beans.SessionBean" scope="request"/>

<jsp:useBean id="accountingSummarySessionBean"
	class="fission.beans.SessionBean" scope="session"/>


<%
	String companyName = accountingSummarySessionBean.getPageBean("formBean").getValue("companyName");

	String marketingPackage = accountingSummarySessionBean.getPageBean("formBean").getValue("marketingPackage");

	String groupProduct = accountingSummarySessionBean.getPageBean("formBean").getValue("groupProduct");

	String areaName = accountingSummarySessionBean.getPageBean("formBean").getValue("areaName");

	String businessContract = accountingSummarySessionBean.getPageBean("formBean").getValue("businessContract");

    String companyStructureId = accountingSummarySessionBean.getPageBean("formBean").getValue("companyStructureId");

	String rowToMatchBase     = "";
%>
<%!
    private TreeMap sortElementsByElementName(SessionBean elements) {

		Map allElements = elements.getPageBeans();

		TreeMap sortedElements = new TreeMap();

		Iterator enumer  = allElements.values().iterator();

		while (enumer.hasNext()) {

			PageBean elementBean = (PageBean) enumer.next();

			String elementName = elementBean.getValue("element");
            String process     = elementBean.getValue("process");
            String event       = elementBean.getValue("event");
            String eventType   = elementBean.getValue("eventType");
            String sequence    = elementBean.getValue("sequence");
            String effectiveDate = elementBean.getValue("effectiveDate");
            String elementId = elementBean.getValue("elementId");

            sortedElements.put(process + event + eventType + elementName + effectiveDate + sequence + elementId, elementBean);
        }

		return sortedElements;
	}
%>

<html>
<head>
<title>ChartSummaryPage.jsp</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<!-- ***** JAVASCRIPT *****  -->

<script language="Javascript1.2">

    var isCancelled = false;
    var f           = null;

    function init() {

	    f = document.accountingSummaryForm;
	    top.frames["main"].setActiveTab("mainTab");
		f.company.disabled          = true;
        f.marketingPackage.disabled = true;
        f.groupProduct.disabled     = true;
        f.area.disabled             = true;
        f.businessContract.disabled = true;
    }

    function isCancelled() {

        return isCancelled;
    }

    function selectRow() {

    	var tdElement = window.event.srcElement;
    	var trElement = tdElement.parentElement;

    	var rowId       = trElement.id;
    	var parsedRowId = (rowId.split("_"))[1];

    	f.elementId.value = parsedRowId;

    	sendTransactionAction("AccountingDetailTran", "loadDetailsForElement", "contentIFrame");
    }

    function sendTransactionAction(transaction, action, target) {

		f.transaction.value = transaction;
    	f.action.value = action;

    	f.target = target;

    	f.submit();
    }

</script>
</head>

<body bgcolor="#DDDDDD" onLoad="init()"  style="border-style:solid; border-width:1;">

<form  name="accountingSummaryForm" method="post" action="/PORTAL/servlet/RequestManager">
  <table width="97%" border="0" cellspacing="0" cellpadding="0" height="86">
    <tr>
      <td width="13%" align="right" height="33">Company: </td>
      <td width="16%" height="33">
        <input type="text" name="company" size="15"
            value="<%= companyName %>">
      </td>
      <td width="24%" align="right" height="33">Marketing Package: </td>
      <td width="13%" height="33">
        <input type="text" name="marketingPackage" size="15" maxlength="15"
            value="<%= marketingPackage %>">
      </td>
      <td width="18%" align="right" height="33">Group Product: </td>
      <td width="16%" height="33">
        <input type="text" name="groupProduct" size="15" maxlength="15"
            value="<%= groupProduct %>">
      </td>
    </tr>
    <tr>
      <td width="13%" align="right" height="30">Area: </td>
      <td width="16%" height="30">
        <input type="text" name="area" size="15" maxlength="15"
            value="<%= areaName %>">
      </td>
      <td width="24%" height="30">&nbsp;</td>
      <td colspan="2" align="right" height="30">Business Contract: </td>
      <td width="16%" height="30">
        <input type="text" name="businessContract" size="15" maxlength="15"
            value="<%= businessContract %>">
      </td>
    </tr>
    <tr><td></td></tr>
    <tr><td></td> </tr>
  </table>
<span id="scrollableContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:scroll">

        <table id="summaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">
           <tr bgcolor="#30548E">
              <th align="left"><font color= "#FFFFFF">Sequence</font></th>
              <th align="left"><font color= "#FFFFFF">Effective Date</font></th>
              <th align="left"><font color= "#FFFFFF">Process</font></th>
			  <th align="left"><font color= "#FFFFFF">Event</font></th>
			  <th align="left"><font color= "#FFFFFF">Event Type</font></th>
			  <th align="left"><font color= "#FFFFFF">Element</font></th>
		   </tr>
		<%
				int rowId   = 0;
				int columnId = 0;

				String rowToMatch = "";
				String trClass = "";
				String selected = "";

                Map sortedElements = sortElementsByElementName(elements);

                Iterator it = sortedElements.values().iterator();

                while (it.hasNext())  {

					PageBean elementBean = (PageBean)it.next();

			        String elementId     =	elementBean.getValue("elementId");
			        String sequence      =	elementBean.getValue("sequence");
			        String effectiveDate =	elementBean.getValue("effectiveDate");
                    String process       =	elementBean.getValue("process");
			        String event         =	elementBean.getValue("event");
			        String eventType     =	elementBean.getValue("eventType");
			        String element		 =	elementBean.getValue("element");

					rowToMatch = elementId;

					if (rowToMatch.equals(elementId)) {

							trClass = "highLighted";

							selected = "true";
					}
					else {

						trClass = "dummy";

						selected="false";
					}
			%>
				<tr class="<%= trClass %>" selected="<%= selected %>" id="rowId_<%= elementId %>" onClick="selectRow()">
					<td nowrap id="sequenceId_<%= columnId %>">
						<%= sequence %>
					</td>
					<td nowrap id="effectiveDateId_<%= columnId %>">
						<%= effectiveDate %>
					</td>
					<td nowrap id="processId_<%= columnId++ %>">
						<%= process %>
					</td>
					<td nowrap id="eventId_<%= columnId++ %>">
						<%= event %>
					</td>
					<td nowrap id="eventTypeId_<%= columnId++ %>">
						<%= eventType %>
					</td>
					<td nowrap id="elementId_<%= columnId++ %>">
						<%= element %>
					</td>
				</tr>
		<%
				}// end while
		%>
		</table>
</span>
</table>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">
<input type="hidden" name="companyStructureId" value="<%= companyStructureId %>">
<input type="hidden" name="elementId" value="">

</form>
</body>
</html>
