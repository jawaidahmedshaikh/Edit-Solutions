<%@ page import="edit.common.vo.*,
                 java.util.*,
                 fission.utility.*,
                 edit.common.vo.user.*,
                 fission.beans.PageBean,
                 fission.beans.SessionBean,
                 edit.common.EDITDate,
                 engine.*"%>

<%
    String message = (String) request.getAttribute("message");
    message = (message == null)?"":message;

    String closeOnMessage = (String) request.getAttribute("closeOnMessage");
    closeOnMessage = (closeOnMessage == null)?"":closeOnMessage;

    ProductStructureVO[] productStructureVO = (ProductStructureVO[]) request.getAttribute("companyStructureVO");
    String companyStructureId = null;
    if (productStructureVO != null)
    {
        companyStructureId = productStructureVO[0].getProductStructurePK() + "";
    }
    companyStructureId = Util.initString(companyStructureId, "0");

    ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("companyStructureVOs");

    ElementVO[] elementVOs = (ElementVO[]) request.getAttribute("elementVOs");

%>
<%!
    private TreeMap sortElementsByElementName(ElementVO[] elements) {

		TreeMap sortedElements = new TreeMap();

		for (int i = 0; i < elements.length; i++) {

			String elementName = elements[i].getElementName();
            String process     = elements[i].getProcess();
            String event       = elements[i].getEvent();
            String eventType   = elements[i].getEventType();
            String sequence    = elements[i].getSequenceNumber() + "";
            String effectiveDate = elements[i].getEffectiveDate();

            sortedElements.put(process + event + eventType + elementName + effectiveDate + sequence, elements[i]);
		}

		return sortedElements;
	}
%>

<html>

<head>
<title>Clone Dialog</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
<script language="Javascript1.2">

	var f = null;
    var message = "<%= message %>";
    var closeOnMessage = "<%= closeOnMessage %>";

	function init() {

		f = document.theForm;

        if (message.length > 0){

            alert(message);
            window.close();
        }

        if (closeOnMessage.length > 0){

            alert(closeOnMessage);

            window.close();
        }
    }

    function sendTransactionAction(transaction, action, target) {

        f.transaction.value = transaction;
        f.action.value = action;

        f.target = target;

        f.submit();
    }

	function cancelDialog() {

		window.close();
	}

function selectDeselectRow() {

    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement;

    var containingTable = currentRow.parentElement;

    for (var i = 0; i < containingTable.rows.length; i++)
    {
        containingTable.rows[i].style.backgroundColor = "#BBBBBB";
        containingTable.rows[i].isSelected = "false";
    }

    currentRow.style.backgroundColor = "#FFFFCC";
    currentRow.isSelected = "true";

    f.cloneCompanyStructurePK.value = currentRow.companyStructureId;
}

function highlightRow() {

    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement;

    if (currentRow.isSelected != "true")
    {
        currentRow.style.backgroundColor = "#FFFFCC";
    }
}

function unhighlightRow(){

    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement;

    if (currentRow.isSelected != "true") {

        currentRow.style.backgroundColor = "#BBBBBB";
    }
}

function cloneCompanyStructure()
{
    if (f.cloneCompanyStructurePK.value == "null")
    {
        alert("Clone To Company Structure Required");
    }
    else
    {
        sendTransactionAction("AccountingDetailTran", "cloneCompanyStructure", "_self");
    }
}

</script>
</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">


    <h3 align="center">- Cloning From -</h3>

    Company Structure:
    <span class="contentArea" style="border-style:solid; border-width:1; position:relative; width:100%; height:10%; top:0; left:0; z-index:0; overflow:visible">

        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

            <tr>

                <th class="dataHeading3" width="20%">
                    Company
                </th>
                <th class="dataHeading3" width="20%">
                    Marketing Package
                </th>
                <th class="dataHeading3" width="20%">
                    Group Product
                </th>
                <th class="dataHeading3" width="20%">
                    Area
                </th>
                <th class="dataHeading3" width="20%">
                    Business Contract
                </th>

            </tr>

            <tr height="100%">

                <td colspan="5">

                    <span class="summaryTable" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">

                        <table id="companyStructureTable" width="100%" border="0" cellspacing="0" cellpadding="0" >
<%
                            if (productStructureVO != null){

                                for (int i = 0; i < productStructureVO.length; i++){

                                    String currentCompanyStructurePK = productStructureVO[i].getProductStructurePK() + "";
                                    Company company = Company.findByPK(new Long(productStructureVO[i].getCompanyFK()));
                                    String currentCompany = company.getCompanyName();
                                    String currentMarketingPackage = productStructureVO[i].getMarketingPackageName();
                                    String currentGroupProduct = productStructureVO[i].getGroupProductName();
                                    String currentArea = productStructureVO[i].getAreaName();
                                    String currentBusinessContract = productStructureVO[i].getBusinessContractName();

                                    String className = "mainEntry";
%>
                                    <tr height="15" class="<%= className %>" companyStructureId="<%= currentCompanyStructurePK %>">

                                        <td align="center" width="20%">
                                            <%= currentCompany %>
                                        </td>
                                        <td align="center" width="20%">
                                            <%= currentMarketingPackage %>
                                        </td>
                                        <td align="center" width="20%">
                                            <%= currentGroupProduct %>
                                        </td>
                                        <td align="center" width="20%">
                                            <%= currentArea %>
                                        </td>
                                        <td align="center" width="20%">
                                            <%= currentBusinessContract %>
                                        </td>
                                    </tr>
<%
                                }
                            }
%>
                        </table>

                    </span>
                </td>

            </tr>
        </table>

        </span>

    <br><br>
    Elements:
    <br>

    <span id="contentArea" style="border-style:solid; border-width:1; position:relative; width:100%; height:50%; top:0; left:0; z-index:0; overflow:visible">

         <table  width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <th class="dataHeading4" width="15%">
                    Sequence
                </th>
                <th class="dataHeading4" width="15%">
                    Effective Date
                </th>
                <th class="dataHeading4" width="15%">
                    Process
                </th>
                <th class="dataHeading4" width="15%">
                    Event
                </th>
                <th class="dataHeading4" width="15%">
                    Event Type
                </th>
                 <th class="dataHeading3" width="15%">
                    Element
                </th>
            </tr>

            <tr height="100%">

                <td colspan="6">

                    <span class="summaryTable" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:scroll">

                        <table  width="100%" border="0" cellspacing="0" cellpadding="0" >
<%
                            Map sortedElements = null;
                            if (elementVOs != null)
                            {
                                sortedElements = sortElementsByElementName(elementVOs);

                                Iterator it2 = sortedElements.values().iterator();

                                    while (it2.hasNext())  {

                                        ElementVO elementVO = (ElementVO)it2.next();

                                        String jElementId     =	elementVO.getElementPK() + "";
                                        String jSequence      =	elementVO.getSequenceNumber() + "";
                                        String jEffectiveDate = DateTimeUtil.formatEDITDateAsMMDDYYYY(new EDITDate(elementVO.getEffectiveDate()));
                                        String jProcess       =	elementVO.getProcess();
                                        String jEvent         =	elementVO.getEvent();
                                        String jEventType     =	elementVO.getEventType();
                                        String jElement		 =	elementVO.getElementName();

                                        String className = "mainEntry";
    %>
                                       <tr height="15" class="<%= className %>" >

                                        <td align="left" width="14%">
                                            <%= jSequence %>
                                        </td>
                                        <td align="left" width="14%">
                                            <%= jEffectiveDate %>
                                        </td>
                                        <td align="left" width="14%">
                                            <%= jProcess %>
                                        </td>
                                        <td align="left" width="14%">
                                            <%= jEvent %>
                                        </td>
                                        <td align="left" width="14%">
                                           <%= jEventType %>
                                        </td>
                                        <td align="left" width="14%">
                                            <%= jElement %>
                                        </td>
                                        </tr>
                    <%
                                }// end while
                            } //end if
                    %>
                        </table>
                    </span>
                </td>
            </tr>
        </table>
    </span>

        <h3 align="center">- Cloning To -</h3>

    Company Structure:
    <span class="contentArea" style="border-style:solid; border-width:1; position:relative; width:100%; height:30%; top:0; left:0; z-index:0; overflow:visible">

        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

            <tr>

                <th class="dataHeading3" width="20%">
                    Company
                </th>
                <th class="dataHeading3" width="20%">
                    Marketing Package
                </th>
                <th class="dataHeading3" width="20%">
                    Group Product
                </th>
                <th class="dataHeading3" width="20%">
                    Area
                </th>
                <th class="dataHeading3" width="20%">
                    Business Contract
                </th>

            </tr>

            <tr height="100%">

                <td colspan="5">

                    <span class="summaryTable" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:scroll">

                        <table id="companyStructuresTable" width="100%" border="0" cellspacing="0" cellpadding="0" >
<%
                            if (productStructureVOs != null){

                                for (int i = 0; i < productStructureVOs.length; i++){

                                    if (productStructureVOs[i].getProductStructurePK() == productStructureVO[0].getProductStructurePK())
                                    {
                                        continue;
                                    }

                                    String currentCompanyStructurePK = productStructureVOs[i].getProductStructurePK() + "";
                                    Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));
                                    String currentCompany = company.getCompanyName();
                                    String currentMarketingPackage = productStructureVOs[i].getMarketingPackageName();
                                    String currentGroupProduct = productStructureVOs[i].getGroupProductName();
                                    String currentArea = productStructureVOs[i].getAreaName();
                                    String currentBusinessContract = productStructureVOs[i].getBusinessContractName();

                                    String className = "mainEntry";
%>
                                    <tr height="15" class="<%= className %>" companyStructureId="<%= currentCompanyStructurePK %>" isSelected="false" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectDeselectRow()">

                                        <td align="center" width="20%">
                                            <%= currentCompany %>
                                        </td>
                                        <td align="center" width="20%">
                                            <%= currentMarketingPackage %>
                                        </td>
                                        <td align="center" width="20%">
                                            <%= currentGroupProduct %>
                                        </td>
                                        <td align="center" width="20%">
                                            <%= currentArea %>
                                        </td>
                                        <td align="center" width="20%">
                                            <%= currentBusinessContract %>
                                        </td>
                                    </tr>
<%
                                }
                            }
%>
                        </table>

                    </span>
                </td>

            </tr>
        </table>
    </span>

    <table width="100%">

        <tr>

            <td align="right" valign="bottom">
                <input type="button" name="clone" value=" Clone " onClick="cloneCompanyStructure()">
                <input type="button" name="clone" value="Cancel" onClick="cancelDialog()">
            </td>
        </tr>
    </table>

    <!-- ****** HIDDEN FIELDS ***** //-->
    <input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="selectedCompanyStructureId"      value="<%= companyStructureId %>">
    <input type="hidden" name="cloneCompanyStructurePK"      value="null">

</form>
</body>
</html>
