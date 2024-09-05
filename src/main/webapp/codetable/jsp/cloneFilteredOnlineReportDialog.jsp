<%@ page import="edit.common.vo.*,
                 java.util.*,
                 fission.utility.*,
                 edit.common.vo.user.*,
                 engine.*"%>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%
    String message = (String) request.getAttribute("message");
    message = (message == null)?"":message;

    ProductStructureVO productStructureVO = (ProductStructureVO) request.getAttribute("companyStructureVO");
    String companyStructurePK = null;
    if (productStructureVO != null)
    {
        companyStructurePK = productStructureVO.getProductStructurePK() + "";
    }

    companyStructurePK = Util.initString(companyStructurePK, "0");

    ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("companyStructureVOs");

    String selectedCompanyStructurePK = (String) request.getAttribute("selectedCompanyStructurePK");

    BIZOnlineReportVO[] bizOnlineReportVOs = (BIZOnlineReportVO[]) request.getAttribute(("bizOnlineReportVOs"));
%>

<html>

<head>
<title>Clone Filtered Report Template Dialog</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
<script language="Javascript1.2">

	var f = null;
    var message = "<%= message %>";

	function init() {

		f = document.theForm;

        if (message.length > 0){

            alert(message);
        }
    }

    function sendTransactionAction(transaction, action, target)
    {

        f.transaction.value = transaction;
        f.action.value = action;

        f.target = target;

        f.submit();
    }

	function cancelDialog()
    {
		window.close();
	}

    function selectDeselectRow()
    {
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

    function highlightRow()
    {

        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.isSelected != "true")
        {
            currentRow.style.backgroundColor = "#FFFFCC";
        }
    }

    function unhighlightRow()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.isSelected != "true") {

            currentRow.style.backgroundColor = "#BBBBBB";
        }
    }

    function cloneFilteredOnlineReport()
    {
        if (f.cloneToCompanyStructurePK.value == "null")
        {
            alert("Clone To Company Structure Required");
        }
        else
        {
            sendTransactionAction("CodeTableTran", "cloneFilteredOnlineReport", "_self");
        }
    }

</script>
</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm">

    <h3 align="center">- Cloning From -</h3>

    <span>Company Structure:</span><br>
    <span class="formData" style="border-style:solid; border-width:1; position:relative; width:100%; height:10%; top:0; left:0; z-index:0; overflow:visible">

        <table  class="summary" width="100%" height="10%" border="0" cellspacing="0" cellpadding="0">

            <tr class="heading">

                <th width="20%">
                    Company
                </th>
                <th width="20%">
                    Marketing Package
                </th>
                <th width="20%">
                    Group Product
                </th>
                <th width="20%">
                    Area
                </th>
                <th width="20%">
                    Business Contract
                </th>

            </tr>

            <tr>

               <td height="100%" colspan="5">

                        <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" >
<%
                            if (productStructureVO != null)
                            {
                                Company company = Company.findByPK(new Long(productStructureVO.getCompanyFK()));

                                String currentCompany = company.getCompanyName();
                                String currentMarketingPackage = productStructureVO.getMarketingPackageName();
                                String currentGroupProduct = productStructureVO.getGroupProductName();
                                String currentArea = productStructureVO.getAreaName();
                                String currentBusinessContract = productStructureVO.getBusinessContractName();
%>
                                <tr height="15">

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
                                <tr class="filler">
                                    <td colspan="5">
                                        &nbsp;
                                    </td>
                                </tr>
                               <tr class="filler">
                                    <td colspan="5">
                                        &nbsp;
                                    </td>
                                </tr>
 <%
                            }
%>
                        </table>
                </td>
                               <tr class="filler">
                                    <td colspan="5">
                                        &nbsp;
                                    </td>
                                </tr>
                                <tr class="filler">
                                    <td colspan="5">
                                        &nbsp;
                                    </td>
                                </tr>
            </tr>
        </table>
        </span>


    <br><br>

<span >Online Report Structure:</span><br>

<table class="summary" width="100%" height="30%" border="0" cellspacing="0" cellpadding="0">

   <tr class="heading">
      <th width="33%">FileName</th>
      <th width="33%">Description  </th>
      <th width="33%">Category  </th>
   </tr>
    <td height="98%" colspan="3">
        <span class="scrollableContent">
            <table id="onlineReportTable" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">

<%
                if (bizOnlineReportVOs != null){
                    for (int i = 0; i < bizOnlineReportVOs.length; i++){

                        String currentOnlineReportPK =  "";
                        String currentFileName = "";
                        String currentDescription = "";
                        String currentCategory = "";
                        String filteredOnlineReportPK = "";

                        if (bizOnlineReportVOs[i].getIsOnlineReportAttached())
                        {
                            OnlineReportVO onlineReportVO = bizOnlineReportVOs[i].getOnlineReportVO();
                            currentOnlineReportPK = onlineReportVO.getOnlineReportPK() + "";
                            currentFileName = onlineReportVO.getFileName();
                            currentDescription = onlineReportVO.getDescription();
                            currentCategory = onlineReportVO.getReportCategoryCT();
                            filteredOnlineReportPK = bizOnlineReportVOs[i].getFilteredOnlineReportPK() + "";
                        }

                        String className = null;
                        String isSelected = "false";
%>
                <tr class="<%= className %>" onlineReportPK="<%= currentOnlineReportPK %>" filteredOnlineReportPK="<%= filteredOnlineReportPK %>" isSelected="<%= isSelected %>"  onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="multiSelectDeselectRow()">
                    <td align="left" nowrap width="33%">
                        <%= currentFileName %>
                    </td>
                    <td align="left" nowrap width="33%">
                        <%= currentDescription %>
                    </td>
                    <td align="left" nowrap width="33%">
                        <%= currentCategory %>
                    </td>
                </tr>
<%
} //end for loop
  } // end if
%>
            <tr class="filler">
                <td colspan="3">
                    &nbsp;
                </td>
            </tr>
        </table>
        </span>
    </td>
    </tr>
</table>


        <h3 align="center">- Cloning To -</h3>

    <span>Company Structure:</span><br>

        <table class="summary" width="100%" height="30%" border="0" cellspacing="0" cellpadding="0">

            <tr class="heading">

                <th width="20%">
                    Company
                </th>
                <th width="20%">
                    Marketing Package
                </th>
                <th width="20%">
                    Group Product
                </th>
                <th width="20%">
                    Area
                </th>
                <th width="20%">
                    Business Contract
                </th>

            </tr>

            <tr>

                <td height="98%" colspan="5">

                    <span class="summary" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:scroll">

                        <table class="summary" id="companyStructuresTable" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" >
<%
                        if (productStructureVOs != null){

                            for (int i = 0; i < productStructureVOs.length; i++){

                                if ((productStructureVOs[i].getProductStructurePK()+ "") == selectedCompanyStructurePK)
                                {
                                    continue;
                                }

                                Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));

                                String currentCompanyStructurePK = productStructureVOs[i].getProductStructurePK() + "";
                                String currentCompany = company.getCompanyName();
                                String currentMarketingPackage = productStructureVOs[i].getMarketingPackageName();
                                String currentGroupProduct = productStructureVOs[i].getGroupProductName();
                                String currentArea = productStructureVOs[i].getAreaName();
                                String currentBusinessContract = productStructureVOs[i].getBusinessContractName();

                                String className = "mainEntry";
%>
                                    <tr height="15" class="<%= className %>" id="<%= currentCompanyStructurePK %>" companyStructurePK="<%= currentCompanyStructurePK %>"  isSelected="false" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectDeselectRow()">

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
                            <tr class="filler">
                                <td colspan="5">
                                    &nbsp;
                                </td>
                            </tr>

                        </table>

                    </span>
                </td>

            </tr>
        </table>

    <table width="100%">

        <tr>
            <td align="right" valign="bottom">
                <input type="button" name="clone" value=" Clone " onClick="cloneFilteredOnlineReport()">
                <input type="button" name="clone" value="Cancel" onClick="cancelDialog()">
            </td>
        </tr>
    </table>

    <!-- ****** HIDDEN FIELDS ***** //-->
    <input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="selectedCompanyStructurePK" value="<%= selectedCompanyStructurePK %>">
    <input type="hidden" name="cloneToCompanyStructurePK" value="">

</form>
</body>
</html>
