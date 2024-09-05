<%@ page import="edit.common.vo.*,
                 java.util.*,
                 fission.utility.*,
                 edit.common.*,
                 codetable.OnlineReport,
                 engine.*"%><!--      *********Java Code *******-->
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>


<%
    String message = (String) request.getAttribute("message");
    message = (message != null)?message:null;

    ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("companyStructureVOs");

    OnlineReportVO[] onlineReportVOs = (OnlineReportVO[]) request.getAttribute("onlineReportVOs");

    String selectedCompanyStructurePK = (String) request.getAttribute("selectedCompanyStructurePK");

    BIZOnlineReportVO[] bizOnlineReportVOs = (BIZOnlineReportVO[]) request.getAttribute(("bizOnlineReportVOs"));

    String pageMode = (String) request.getAttribute("pageMode");

    String selectedFilteredOnlineReportPK = "";
%>


<html>
<head>

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

var message = "<%= message %>";
var pageMode = "<%= pageMode %>";

function multiSelectDeselectRow()
{
    var tdElement = window.event.srcElement;

    var currentRow = tdElement.parentElement;

    if (currentRow.tagName != "TR") // Bubble up if necessary
    {
        currentRow = currentRow.parentElement;
    }

    var className = currentRow.className;

    if (currentRow.isSelected == "false")
    {
        currentRow.style.backgroundColor = "#FFFFCC";

        currentRow.isSelected = "true";
    }
    else if (currentRow.isSelected == "true")
    {
        if (className == "default")
        {
            currentRow.style.backgroundColor = "#BBBBBB";
        }
        else if (className == "associated")
        {
            currentRow.style.backgroundColor = "#00BB00";
        }

        currentRow.isSelected = "false";
    }
}

function attachCompanyAndOnlineReport()
{
	f.selectedOnlineReportPKs.value = getSelectedOnlineReportPKs();

    if (f.selectedOnlineReportPKs.value == "null" || f.selectedCompanyStructurePK.value == "null")
    {
        alert("Company Structure And Online Report Selection Required");

        return;
    }
    else
    {
    	sendTransactionAction("CodeTableTran", "attachCompanyAndOnlineReport", "main");
    }
}

function showCloneFilteredOnlineReportDialog()
{
    if (f.selectedCompanyStructurePK.value == "null")
    {
        alert("Company Structure Required");
    }
    else
    {
        var width   = 1.00 * screen.width;
        var height  = 0.90 * screen.height;

        openDialog("","cloneFilteredOnlineReportDialog","left=0,top=0,resizable=yes,scrollbars=yes,width=" + width + ",height=" + height,"CodeTableTran", "showCloneFilteredOnlineReportDialog", "cloneFilteredOnlineReportDialog");
    }
}

function openDialog(theURL, winName, features, transaction, action, target) {

    dialog = window.open(theURL,winName,features);

    sendTransactionAction(transaction, action, target);
}

function cancelOnlineReportRelations()
{
    sendTransactionAction("CodeTableTran","cancelOnlineReportRelations", "main");
}

function getSelectedOnlineReportPKs()
{
    var onlineReportTable = document.all.onlineReportTable;

    var selectedOnlineReportPKs = "null";

    for (var i = 0; i < onlineReportTable.rows.length; i++) {

        if (onlineReportTable.rows[i].isSelected == "true") {

            if (selectedOnlineReportPKs == "null") selectedOnlineReportPKs = "";

            selectedOnlineReportPKs += onlineReportTable.rows[i].onlineReportPK + ",";
        }
    }

    return selectedOnlineReportPKs;
}



function detachCompanyAndOnlineReport()
{

	f.selectedOnlineReportPKs.value = getSelectedOnlineReportPKs();

    if (f.selectedOnlineReportPKs.value == "null" || f.selectedCompanyStructurePK.value == "null")
    {
        alert("Company Structure And Online Report Selection Required");

        return;
    }
    else
    {
    	sendTransactionAction("CodeTableTran", "detachCompanyAndOnlineReport", "main");
    }
}

function showAttachedOnlineReport()
{
    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement;
    f.selectedCompanyStructurePK.value = currentRow.companyStructurePK;

    if (f.selectedCompanyStructurePK.value == "null")
    {
        alert("Company Structure Required");
    }
    else
    {
        sendTransactionAction("CodeTableTran", "showOnlineReportRelations", "main");
    }
}

function sendTransactionAction(transaction, action, target)
{

	f.transaction.value = transaction;
	f.action.value = action;
	f.target = target;
	f.submit();
}

function showOnlineReportSummary()
{
    sendTransactionAction("CodeTableTran", "showOnlineReportSummary", "main");
}

function init()
{

	f = document.theForm;

    var scrollToRow = document.getElementById("<%= selectedCompanyStructurePK %>");

    if (scrollToRow != null) {

        scrollToRow.scrollIntoView(false);
    }

    alertMessage();
}

function alertMessage()
{
    if (message != "null")
    {
        alert(message);
    }
}

</script>
<title>Online Report Relation</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>

<body class="mainTheme" onLoad="init()">


<form name="theForm">
  <span class="tableHeading">Company Structure Summary</span><br>
    <table class="summary" width="100%" height="35%" border="0" cellspacing="0" cellpadding="0">
      <tr class="heading">
        <th width="20%" align="left">Company</th>
        <th width="20%" align="left">Marketing Package</th>
        <th width="20%" align="left">Group Product</th>
        <th width="20%" align="left">Area</th>
        <th width="20%" align="left">Business Contract</th>
      </tr>

      <tr>
        <td height="98%" colspan="5">
          <span class="summary" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:scroll">
            <table class="summary" id="companyStructuresTable" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" >
            <%
                if (productStructureVOs != null)
                {
                    for (int i = 0; i < productStructureVOs.length; i++)
                    {
                        String currentCompanyStructurePK = productStructureVOs[i].getProductStructurePK() + "";
                        Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));

                        String currentCompany = company.getCompanyName();
                        String currentMarketingPackage = productStructureVOs[i].getMarketingPackageName();
                        String currentGroupProduct = productStructureVOs[i].getGroupProductName();
                        String currentArea = productStructureVOs[i].getAreaName();
                        String currentBusinessContract = productStructureVOs[i].getBusinessContractName();

                        String className = null;

                        if (selectedCompanyStructurePK != null && selectedCompanyStructurePK.equals(currentCompanyStructurePK))
                        {
                            className = "highlighted";
                        }
                        else
                        {
                            className = "default";
                        }
            %>
            <tr height="15" class="<%= className %>" id="<%= currentCompanyStructurePK %>"
                companyStructurePK="<%= currentCompanyStructurePK %>" isSelected="false"
                onMouseOver="highlightRow()" onMouseOut="unhighlightRow()"
                onClick="showAttachedOnlineReport()">
              <td width="20%" align="left">
                <%= currentCompany %>
              </td>
              <td width="20%" align="left">
                <%= currentMarketingPackage %>
              </td>
              <td width="20%" align="left">
                <%= currentGroupProduct %>
              </td>
              <td width="20%" align="left">
                <%= currentArea %>
              </td>
              <td width="20%" align="left">
                <%= currentBusinessContract %>
              </td>
            </tr>
            <%
                    }
                }
            %>
            <tr class="filler">
              <td colspan="5">&nbsp;</td>
            </tr>
            </table>
          </span>
        </td>
      </tr>
    </table>

    <br>

    <table width="100%">
      <tr>
        <td width="33%">&nbsp;</td>
        <td align="center" valign="bottom" width="33%">
          <span class="tableHeading">Online Report Summary</span>
        </td>
        <td align="right" valign="bottom" width="33%">
          <input type="button" value="Online Report Summary" onClick="showOnlineReportSummary()">
        </td>
      </tr>
    </table>

    <table class="summary" width="100%" height="40%" border="0" cellspacing="0" cellpadding="0">
      <tr class="heading">
        <th width="33%" align="left">File Name</th>
        <th width="33%" align="left">Description</th>
        <th width="33%" align="left">Category</th>
      </tr>
      <tr>
        <td height="98%" colspan="3">
          <span class="scrollableContent">
            <table id="onlineReportTable" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <%
                if (onlineReportVOs != null)
                {
                    for (int i = 0; i < onlineReportVOs.length; i++)
                    {
                        String currentFileName = onlineReportVOs[i].getFileName();
                        String currentDescription = onlineReportVOs[i].getDescription();
                        String currentCategory = onlineReportVOs[i].getReportCategoryCT();
                        String currentOnlineReportPK = onlineReportVOs[i].getOnlineReportPK() + "";

                        String filteredOnlineReportPK = null;
                        String className = null;
                        String isSelected = null;

                        if (bizOnlineReportVOs != null)
                        {
                            for (int j = 0; j < bizOnlineReportVOs.length; j++)
                            {
                                OnlineReportVO onlineReportVO = bizOnlineReportVOs[j].getOnlineReportVO();

                                if (currentOnlineReportPK.equals(onlineReportVO.getOnlineReportPK() + ""))
                                {
                                    filteredOnlineReportPK = bizOnlineReportVOs[i].getFilteredOnlineReportPK() + "";
                                    className = null;
                                    isSelected = "false";

                                    if (bizOnlineReportVOs[j].getIsOnlineReportAttached())
                                    {
                                        className = "associated";
                                    }
                                    else
                                    {
                                        className = "default";
                                    }
                                    break;
                                }

                            }
                        }
                        else
                        {
                            className = "default";
                        }
            %>
            <tr class="<%= className %>" onlineReportPK="<%= currentOnlineReportPK %>"
                filteredOnlineReportPK="<%= filteredOnlineReportPK %>" isSelected="<%= isSelected %>"
                onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="multiSelectDeselectRow()">
              <td nowrap width="2%">
                <%
                    if (className.equalsIgnoreCase("associated"))
                    {
                       out.println("<img src=\"/PORTAL/codetable/images/repeating_b2.gif\" width=\"28\" height=\"15\" alt=\"showFilteredOnlineReportDialog\" onClick='showFilteredOnlineReportDialog()' onMouseOver=\"this.src='/PORTAL/codetable/images/repeating_b2_over.gif'\" onMouseOut=\"this.src='/PORTAL/codetable/images/repeating_b2.gif'\">");
                    }
                    else
                    {
                        out.println("&nbsp");
                    }
                %>
              </td>
              <td nowrap width="33%" align="left">
                <%= currentFileName %>
              </td>
              <td nowrap width="33%" align="left">
                <%= currentDescription %>
              </td>
              <td nowrap width="33%" align="left">
                <%= currentCategory %>
              </td>
            </tr>
            <%
                    } //end for loop for onlineReportVOs
                } // end if
            %>
            <tr class="filler">
              <td colspan="3">&nbsp;</td>
            </tr>
            </table>
          </span>
        </td>
      </tr>
    </table>

    <table width="100%">
      <tr>
        <td align="right" valign="bottom">
          <input type="button" name="attach" value="Attach" onClick="attachCompanyAndOnlineReport()">
          <input type="button" name="detach" value="Detach" onClick="detachCompanyAndOnlineReport()">
          <input type="button" name="clone" value=" Clone " onClick="showCloneFilteredOnlineReportDialog()">
          <input type="button" name="cancel" value="Cancel" onClick="cancelOnlineReportRelations()">
        </td>
      </tr>
    </table>

 <input type="hidden" name="transaction">
 <input type="hidden" name="action">
 <input type="hidden" name="selectedCompanyStructurePK" value="<%= selectedCompanyStructurePK %>">
 <input type="hidden" name="selectedOnlineReportPK" value="">
 <input type="hidden" name="selectedOnlineReportPKs" value="">
 <input type="hidden" name="selectedFilteredOnlineReportPKs" value="null">
 <input type="hidden" name="selectedFilteredOnlineReportPK" value="<%= selectedFilteredOnlineReportPK %>">


</form>
</body>
</html>