<%@ page import="edit.common.vo.*,
                 fission.utility.*,
                 edit.common.vo.user.*,
                 java.util.*,
                 edit.common.*,
                 engine.*"%>
<%
    String message = (String) request.getAttribute("message");

    ProductStructureVO cloneFromCompanyStructureVO = (ProductStructureVO) request.getAttribute("cloneFromCompanyStructureVO");

    String cloneFromCompanyStructurePK = cloneFromCompanyStructureVO.getProductStructurePK() + "";

    ProductStructureVO[] companyStructureVOs = (ProductStructureVO[]) request.getAttribute("companyStructureVOs");

    FilteredAreaVO[] filteredAreaVOs = (FilteredAreaVO[]) request.getAttribute("filteredAreaVOs");

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

	function init() {

		f = document.theForm;

        alertMessage();
    }

    function alertMessage()
    {
        if (message != "null")
        {
            alert(message);

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

    f.cloneToCompanyStructurePK.value = currentRow.companyStructurePK;
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

function cloneFilteredArea()
{
    if (f.cloneToCompanyStructurePK.value == "null")
    {
        alert("Clone To Company Structure Required");
    }
    else
    {
        sendTransactionAction("TableTran", "cloneFilteredArea", "_self");
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

                <th class="dataHeading4" width="20%">
                    Company
                </th>
                <th class="dataHeading4" width="20%">
                    Marketing Package
                </th>
                <th class="dataHeading4" width="20%">
                    Group Product
                </th>
                <th class="dataHeading4" width="20%">
                    Area
                </th>
                <th class="dataHeading4" width="20%">
                    Business Contract
                </th>

            </tr>

            <tr height="100%">

                <td colspan="5">

                    <span class="summaryTable" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">

                        <table id="companyStructureTable" width="100%" border="0" cellspacing="0" cellpadding="0" >
<%
                            if (cloneFromCompanyStructureVO != null){

                                String currentCompanyStructurePK = cloneFromCompanyStructureVO.getProductStructurePK() + "";
                                Company company = Company.findByPK(cloneFromCompanyStructureVO.getCompanyFK());

                                String currentCompany = company.getCompanyName();
                                String currentMarketingPackage = cloneFromCompanyStructureVO.getMarketingPackageName();
                                String currentGroupProduct = cloneFromCompanyStructureVO.getGroupProductName();
                                String currentArea = cloneFromCompanyStructureVO.getAreaName();
                                String currentBusinessContract = cloneFromCompanyStructureVO.getBusinessContractName();

                                String className = "mainEntry";
%>
                                    <tr height="15" class="<%= className %>" companyStructurePK="<%= currentCompanyStructurePK %>">

                                        <td width="20%">
                                            <%= currentCompany %>
                                        </td>
                                        <td width="20%">
                                            <%= currentMarketingPackage %>
                                        </td>
                                        <td width="20%">
                                            <%= currentGroupProduct %>
                                        </td>
                                        <td width="20%">
                                            <%= currentArea %>
                                        </td>
                                        <td width="20%">
                                            <%= currentBusinessContract %>
                                        </td>
                                    </tr>
<%
                            }
%>
                        </table>

                    </span>
                </td>

            </tr>
        </table>

        </span>

    <br><br>
    Area:
    <br>

    <span class="contentArea" style="border-style:solid; border-width:1; position:relative; width:100%; height:30%; top:0; left:0; z-index:0; overflow:visible">

        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

            <tr>

                <th class="dataHeading4" width="2%">
                    &nbsp;
                </th>
                <th class="dataHeading4" width="14%">
                    Effective Date
                </th>
                <th class="dataHeading4" width="14%">
                    Process
                </th>
                <th class="dataHeading4" width="14%">
                    Event
                </th>
                <th class="dataHeading4" width="14%">
                    Event Type
                </th>
                <th class="dataHeading4" width="14%">
                    Rule Name
                </th>
                <th class="dataHeading4" width="14%">
                    Script/Table Name
                </th>

            </tr>

            <tr height="100%">

                <td colspan="8">

                    <span class="summaryTable" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:scroll">

                        <table id="filteredAreaTable" width="100%" border="0" cellspacing="0" cellpadding="0" >
<%
                            if (filteredAreaVOs != null){

                                for (int i = 0; i < filteredAreaVOs.length; i++){

                                    FilteredAreaVO filteredAreaVO = filteredAreaVOs[i];

                                    AreaVO currentAreaVO = (AreaVO) filteredAreaVO.getParentVO(AreaVO.class);

                                    String currentAreaPK = currentAreaVO.getAreaPK() + "";

                                    String currentAreaDesc = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("STATE", currentAreaVO.getAreaCT());

                                    String currentStatementModeDesc = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("STATEMENTMODE", currentAreaVO.getStatementModeCT());

                                    String currentFreeLookTypeDesc = CodeTableWrapper.getSingleton().getCodeDescByCodeTableNameAndCode("FREELOOKTYPE", currentAreaVO.getFreeLookTypeCT());

                                    int currentFreeLookDaysNB = currentAreaVO.getFreeLookDaysNB();

                                    int currentLookBackDays = currentAreaVO.getLookBackDays();

                                    String className = "mainEntry";
%>
                                    <tr height="15" class="<%= className %>">

                                        <td width="20%">
                                            <%= currentAreaDesc %>
                                        </td>
                                        <td width="20%">
                                            <%= currentStatementModeDesc %>
                                        </td>
                                        <td width="20%">
                                            <%= currentFreeLookTypeDesc %>
                                        </td>
                                        <td width="20%">
                                            <%= currentFreeLookDaysNB %>
                                        </td>
                                        <td width="20%">
                                            <%= currentLookBackDays %>
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


        <h3 align="center">- Cloning To -</h3>

    Company Structure:
    <span class="contentArea" style="border-style:solid; border-width:1; position:relative; width:100%; height:30%; top:0; left:0; z-index:0; overflow:visible">

        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

            <tr>

                <th class="dataHeading4" width="20%">
                    Company
                </th>
                <th class="dataHeading4" width="20%">
                    Marketing Package
                </th>
                <th class="dataHeading4" width="20%">
                    Group Product
                </th>
                <th class="dataHeading4" width="20%">
                    Area
                </th>
                <th class="dataHeading4" width="20%">
                    Business Contract
                </th>

            </tr>

            <tr height="100%">

                <td colspan="5">

                    <span class="summaryTable" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:scroll">

                        <table id="companyStructuresTable" width="100%" border="0" cellspacing="0" cellpadding="0" >
<%
                            if (companyStructureVOs != null){

                                for (int i = 0; i < companyStructureVOs.length; i++){

                                    if (companyStructureVOs[i].getProductStructurePK() == cloneFromCompanyStructureVO.getProductStructurePK())
                                    {
                                        continue;
                                    }

                                    String currentCompanyStructurePK = companyStructureVOs[i].getProductStructurePK() + "";
                                    Company company = Company.findByPK(cloneFromCompanyStructureVO.getCompanyFK());

                                    String currentCompany = company.getCompanyName();
                                    String currentMarketingPackage = companyStructureVOs[i].getMarketingPackageName();
                                    String currentGroupProduct = companyStructureVOs[i].getGroupProductName();
                                    String currentArea = companyStructureVOs[i].getAreaName();
                                    String currentBusinessContract = companyStructureVOs[i].getBusinessContractName();

                                    String className = "mainEntry";
%>
                                    <tr height="15" class="<%= className %>" companyStructurePK="<%= currentCompanyStructurePK %>" isSelected="false" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectDeselectRow()">

                                        <td width="20%">
                                            <%= currentCompany %>
                                        </td>
                                        <td width="20%">
                                            <%= currentMarketingPackage %>
                                        </td>
                                        <td width="20%">
                                            <%= currentGroupProduct %>
                                        </td>
                                        <td width="20%">
                                            <%= currentArea %>
                                        </td>
                                        <td width="20%">
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
             <td align="left" valign="bottom">
                &nbsp;
             </td>

            <td align="right" valign="bottom">
                <input type="button" name="clone" value=" Clone " onClick="cloneFilteredArea()">
                <input type="button" name="clone" value="Cancel" onClick="cancelDialog()">
            </td>
        </tr>
    </table>

    <!-- ****** HIDDEN FIELDS ***** //-->
    <input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="cloneFromCompanyStructurePK"      value="<%= cloneFromCompanyStructurePK %>">
    <input type="hidden" name="cloneToCompanyStructurePK"      value="null">

</form>
</body>
</html>
