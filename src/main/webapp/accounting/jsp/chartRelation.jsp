<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="java.util.*, fission.beans.*,
                 edit.common.vo.ElementVO,
                 edit.common.EDITDate" %>

<jsp:useBean id="elements"
	class="fission.beans.SessionBean" scope="request"/>

<jsp:useBean id="allCompanyStructures"
	class="fission.beans.SessionBean" scope="request"/>

<jsp:useBean id="formBean"
	class="fission.beans.FormBean" scope="request"/>

<%
    String message = (String) request.getAttribute("message");
    message = (message == null)?"":message;

	String selectedElementId = "";
	String companyStructureId = formBean.getValue("selectedCompanyStructureId");

    ElementVO[] attachedElementVOs = (ElementVO[]) request.getAttribute("attachedElementVOs");

    // Utility objects...
    List attachedElementPKs  = loadAttachedElementPKs(attachedElementVOs);
//	String rowToMatchBase = companyStructureId;

//	if (rowToMatchBase.equals("")) {
//
//		rowToMatchBase = "dummy";
//	}

	String rowToMatchBase2 =  selectedElementId;

	if (rowToMatchBase2.equals("")) {

		rowToMatchBase2 = "dummy";
	}

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

    private List loadAttachedElementPKs(ElementVO[] attachedElementVOs){

        List attachedElementPKs = new ArrayList();

        if (attachedElementVOs != null){

            for (int i = 0; i < attachedElementVOs.length; i++){

                attachedElementPKs.add(attachedElementVOs[i].getElementPK() + "");
            }
        }

        return attachedElementPKs;
    }
%>

<html>
<head>
<!-- ****** STYLE CODE ***** //-->
<style>

    .attached {

		background-color: #00BB00;
	}
	.dummy {
	}

</style>

<title>chartRelation.jsp</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<!-- ***** JAVASCRIPT *****  -->

<script language="Javascript1.2">

    var isCancelled = false;
    var f           = null;
    var message = "<%= message %>";

    function highlightRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

<%--        if (currentRow.tagName != "TR") // Bubble up if necessary--%>
<%--        {--%>
<%--            currentRow = currentRow.parentElement;--%>
<%--        }--%>

        currentRow.style.backgroundColor = "#FFFFCC";
    }

    function unhighlightRow(){

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (currentRow.isSelected != "true") {

            if (className == "attached")
            {
                currentRow.style.backgroundColor = "#00BB00";
            }
            else if (className == "highLighted")
            {
                currentRow.style.backgroundColor = "#FFFFCC";
            }
            else {

                currentRow.style.backgroundColor = "#DCDCDC";
            }
        }
    }

    function init() {

	    f = document.accountingRelationForm;
	    top.frames["main"].setActiveTab("relationTab");

        if (message.length > 0){

            alert(message);
        }
	}

    function isCancelled() {

        return isCancelled;
    }

    function selectElementRow() {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (currentRow.isSelected == "false"){

            currentRow.style.backgroundColor = "#FFFFCC";
            currentRow.isSelected = "true";
        }
<%--        else if (currentRow.isSelected == "true")--%>
        else
        {
            if (className == "mainEntry")
            {
                currentRow.style.backgroundColor = "#DCDCDC";
            }
            else if (className == "attached")
            {
                currentRow.style.backgroundColor = "#00BB00";
            }

            currentRow.isSelected = "false";
        }
    }

	function selectCompanyStructureRow() {

    	var tdElement = window.event.srcElement;
    	var trElement = tdElement.parentElement;

    	var rowId     = trElement.id;

    	f.selectedCompanyStructureId.value = rowId;

    	sendTransactionAction("AccountingDetailTran", "showAttachedElements", "contentIFrame");
	}

	function attachElementCompany() {

	    f.selectedElementPKs.value = getSelectedElementPKs();

		sendTransactionAction("AccountingDetailTran", "attachElementCompany", "contentIFrame");
	}

	function detachElementCompany() {

	    f.selectedElementPKs.value = getSelectedElementPKs();

		sendTransactionAction("AccountingDetailTran", "detachElementCompany", "contentIFrame");
	}

	function cancelRelation() {

		sendTransactionAction("AccountingDetailTran", "cancelRelation", "contentIFrame");
	}

	function deleteElement() {

	    f.selectedElementPKs.value = getSelectedElementPKs();

		sendTransactionAction("AccountingDetailTran", "deleteElement", "contentIFrame");
	}

    function showCloneCompanyStructureDialog()
    {
        var width   = 1.00 * screen.width;
        var height  = 0.90 * screen.height;

        openDialog("","cloneDialog","left=0,top=0,resizable=yes,scrollbars=yes,width=" + width + ",height=" + height,"AccountingDetailTran", "showCloneCompanyStructureDialog", "cloneDialog");
    }

    function openDialog(theURL, winName, features, transaction, action, target) {

        dialog = window.open(theURL,winName,features);

        sendTransactionAction(transaction, action, target);
    }

    function getSelectedElementPKs(){

        var elementTable = document.all.summaryTable;

        var selectedElementPKs = "";

        for (var i = 0; i < elementTable.rows.length; i++) {

            if (elementTable.rows[i].isSelected == "true") {

                selectedElementPKs += elementTable.rows[i].id + ",";
            }
        }

        return selectedElementPKs;
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

<form  name="accountingRelationForm" method="post" action="/PORTAL/servlet/RequestManager">
<span id="scrollableCompanyContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:30%; top:0; left:0; z-index:0; overflow:scroll">

        <table id="summaryTable1" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" >
           <tr bgcolor="#30548E">
              <th align="left"><font color= "#FFFFFF">Company</font></th>
              <th align="left"><font color= "#FFFFFF">Marketing Package</font></th>
              <th align="left"><font color= "#FFFFFF">Group Product</font></th>
			  <th align="left"><font color= "#FFFFFF">Area</font></th>
			  <th align="left"><font color= "#FFFFFF">Business Contract</font></th>
		   </tr>
		<%

				String rowToMatch = "";

				Map allCompanyStructureBeans = allCompanyStructures.getPageBeans();

				Iterator it = allCompanyStructureBeans.values().iterator();

				while (it.hasNext())  {

				    String className = "";
					PageBean companyStructureBean = (PageBean)it.next();

			        String iCompanyId        = companyStructureBean.getValue("companyStructureId");
					String iCompany          = companyStructureBean.getValue("company");
			        String iMarketingPackage = companyStructureBean.getValue("marketingPackage");
			        String iGroupProduct     = companyStructureBean.getValue("groupProduct");
			        String iArea             = companyStructureBean.getValue("area");
			        String iBusinessContract = companyStructureBean.getValue("businessContract");

//					rowToMatch = rowToMatchBase;

					if (companyStructureId.equals(iCompanyId)) {

							className = "highLighted";
					}
					else {

						className = "mainEntry";
					}
			%>
				<tr class="<%= className %>" isSelected="false"  id="<%= iCompanyId %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectCompanyStructureRow()">
					<td nowrap>
						<%= iCompany %>
					</td>
					<td nowrap>
						<%= iMarketingPackage %>
					</td>
					<td nowrap>
						<%= iGroupProduct %>
					</td>
					<td nowrap>
						<%= iArea %>
					</td>
					<td nowrap>
						<%= iBusinessContract %>
					</td>
				</tr>
		<%
				}// end while
		%>
		</table>
</span>
<span id="scrollableContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:50%; top:0; left:0; z-index:0; overflow:scroll">

        <table id="summaryTable" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
           <tr bgcolor="#30548E">
              <th align="left"><font color= "#FFFFFF">Sequence</font></th>
              <th align="left"><font color= "#FFFFFF">Effective Date</font></th>
              <th align="left"><font color= "#FFFFFF">Process</font></th>
			  <th align="left"><font color= "#FFFFFF">Event</font></th>
			  <th align="left"><font color= "#FFFFFF">Event Type</font></th>
			  <th align="left"><font color= "#FFFFFF">Element</font></th>
		   </tr>
		<%
				String rowToMatch2 = "";
                String attached = "";

                Map sortedElements = sortElementsByElementName(elements);

                Iterator it2 = sortedElements.values().iterator();

				while (it2.hasNext())  {

					PageBean elementBean = (PageBean)it2.next();

			        String jElementId     =	elementBean.getValue("elementId");
			        String jSequence      =	elementBean.getValue("sequence");
			        String jEffectiveDate =	elementBean.getValue("effectiveDate");
			        String jProcess       =	elementBean.getValue("process");
			        String jEvent         =	elementBean.getValue("event");
			        String jEventType     =	elementBean.getValue("eventType");
			        String jElement		 =	elementBean.getValue("element");

					rowToMatch2 = rowToMatchBase2;
				    String className = "";

					if (rowToMatch2.equals(jElementId)) {

							className  = "highLighted";
					}
					else {

						className  = "dummy";
					}

                     if (attachedElementPKs.contains(jElementId)) {

                        className = "attached";
                        attached = "true";
                    }
                    else {

                        className = "mainEntry";
                        attached="false";
                    }
			%>
				<tr class="<%= className %>" attached="<%= attached %>" isSelected="false" id="<%= jElementId %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectElementRow()">
					<td nowrap>
						<%= jSequence %>
					</td>
					<td nowrap>
						<%= jEffectiveDate %>
					</td>
					<td nowrap>
						<%= jProcess %>
					</td>
					<td nowrap>
						<%= jEvent %>
					</td>
					<td nowrap>
						<%= jEventType %>
					</td>
					<td nowrap>
						<%= jElement %>
					</td>
				</tr>
		<%
				}// end while
		%>
		</table>
</span>
<span id="buttonContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:2%; top:0; left:0; z-index:0; overflow:visible">
  <table width="100%" height="1%" border="0" cellspacing="0" cellpadding="0" bgcolor="#DDDDDD">
    <tr height="5%">
	  <td colspan="3" align="right" height="14">
        	<input type="button" name="attach" value="Attach" onClick="attachElementCompany()">
		    <input type="button" name="detach" value="Detach" onClick="detachElementCompany()">
            <input type="button" name="clone" value=" Clone " onClick="showCloneCompanyStructureDialog()">
		    <input type="button" name="cancel" value="Cancel" onClick="cancelRelation()">
		    <input type="button" name="delete" value="Delete" onClick="deleteElement()">
	  </td>
	</tr>
  </table>
</span>

<!-- ****** HIDDEN FIELDS ***** //-->
<input type="hidden" name="transaction" value="">
<input type="hidden" name="action"      value="">

<input type="hidden" name="selectedCompanyStructureId" value="<%= companyStructureId%>">
<input type="hidden" name="selectedElementId" value="<%= selectedElementId%>">
<input type="hidden" name="selectedElementPKs">
</form>
