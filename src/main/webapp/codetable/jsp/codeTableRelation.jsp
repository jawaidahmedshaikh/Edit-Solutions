<%@ page import="edit.common.vo.*,
                 java.util.*,
                 fission.utility.*,
                 edit.common.*,
                 engine.*"%><!--      *********Java Code *******-->
<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>


<%
	// The page that control is returned to if the Cancel button
	// is clicked.

    String message = (String) request.getAttribute("message");
    message = (message != null)?message:null;

    ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("companyStructureVOs");

    CodeTableDefVO[] codeTableDefVOs = (CodeTableDefVO[]) request.getAttribute("codeTableDefVOs");

    String activeCompanyStructurePK = Util.initString((String) request.getAttribute("activeCompanyStructurePK"), "0");

    String relationCodeTableDefPK = Util.initString((String) request.getAttribute("relationCodeTableDefPK"), "0");

    BIZCodeTableVO[] bizCodeTableVOs = (BIZCodeTableVO[]) request.getAttribute(("bizCodeTableVOs"));

    String pageMode = (String) request.getAttribute("pageMode");

    String selectedFilteredCodeTablePK = "";
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

function showFilteredCodeTableDialog()
{
    if (f.relationCodeTableDefPK.value == "0")
    {
        alert("Code Table Name Required");

        return;
    }

    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement.parentElement; // img -> td -> TR (currentRow)

    f.selectedCodeTablePK.value = currentRow.codeTablePK;
    f.selectedFilteredCodeTablePK.value = currentRow.filteredCodeTablePK;

    var width   = 0.90 * screen.width;
    var height  = 0.50 * screen.height;

<%--    openDialog("","filteredCodeTableDialog","left=50,top=50,resizable=yes,width=" + width + ",height=" + height,"CodeTableTran", "showFilteredCodeTableDialog", "filteredCodeTableDialog");--%>
    openDialog("filteredCodeTableDialog", null, width, height);

    sendTransactionAction("CodeTableTran", "showFilteredCodeTableDialog", "filteredCodeTableDialog");
}

function showBuildCodeTableDialog()
{
    if (f.activeCompanyStructurePK.value == "0")
    {
        alert("Please Select Company Structure For Build")
    }
    else
    {
        var width   = 1.00 * screen.width;
        var height  = 0.90 * screen.height;

<%--        openDialog("","buildCodeTableDialog","left=0,top=0,resizable=yes,scrollbars=yes,width=" + width + ",height=" + height,"CodeTableTran", "showBuildCodeTableDialog", "buildCodeTableDialog");--%>
        openDialog("buildCodeTableDialog", null, width, height);

        sendTransactionAction("CodeTableTran", "showBuildCodeTableDialog", "buildCodeTableDialog");
    }
}

<%--function openDialog(theURL, winName, features, transaction, action, target) {--%>
<%----%>
<%--    dialog = window.open(theURL,winName,features);--%>
<%----%>
<%--    sendTransactionAction(transaction, action, target);--%>
<%--}--%>

function cancelCodeTableRelations()
{
    sendTransactionAction("CodeTableTran","cancelCodeTableRelations", "main");
}

function getSelectedCodeTablePKs()
{
    if (f.relationCodeTableDefPK.value == "0")
    {
        alert("Code Table Name Required");

        return;
    }
    var codeTableTable = document.all.codeTable;

    var selectedCodeTablePKs = "null";

    for (var i = 0; i < codeTableTable.rows.length; i++) {

        if (codeTableTable.rows[i].isSelected == "true") {

            if (selectedCodeTablePKs == "null") selectedCodeTablePKs = "";

            selectedCodeTablePKs += codeTableTable.rows[i].codeTablePK + ",";
        }
    }

    return selectedCodeTablePKs;
}

function showAttachedCodeTables()
{
    var tdElement = window.event.srcElement;
    var currentRow = tdElement.parentElement;
    f.activeCompanyStructurePK.value = currentRow.companyStructurePK;

    if (f.relationCodeTableDefPK.value == "0")
    {
        alert("Code Table Name Required");
    }
    else
    {
        sendTransactionAction("CodeTableTran", "showCodeTableRelations", "main");
    }
}

function showCodeTableSummary()
{
    sendTransactionAction("CodeTableTran", "showCodeTableDefSummary", "main");
}
function init()
{
	f = document.theForm;

    var scrollToRow = document.getElementById("<%= activeCompanyStructurePK %>");

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
<title>Code Table Relation</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

</head>

<body class="mainTheme" onLoad="init()">
<form name="theForm">

  <table class="formData" width="100%" border="0"  cellspacing="6" cellpadding="0">
    <tr>
      <td colspan="2" align="left">
        Code Table Name
        <select name="relationCodeTableDefPK">
        <option value="0">Please Select</option>
        <%
            if (codeTableDefVOs != null)
            {
                for (int i = 0; i < codeTableDefVOs.length; i++)
                {
                    String codeTableName = codeTableDefVOs[i].getCodeTableName();
                    String currentCodeTableDefPK = codeTableDefVOs[i].getCodeTableDefPK() + "";

                    if ( relationCodeTableDefPK.equals(currentCodeTableDefPK))
                    {
                        out.println("<option selected value=\"" + currentCodeTableDefPK + "\">" + codeTableName + "</option>");
                    }
                    else
                    {
                        out.println("<option value=\"" + currentCodeTableDefPK + "\">" + codeTableName + "</option>");
                    }
                }
            }
        %>
        </select>
      </td>
    </tr>
  </table>

  <span class="tableHeading">Company Structure Summary</span><br>
    <table class="summary" width="100%" height="35%" border="0" cellspacing="0" cellpadding="0">
      <tr class="heading">
        <th width="20%">Company</th>
        <th width="20%">Marketing Package</th>
        <th width="20%">Group Product</th>
        <th width="20%">Area</th>
        <th width="20%">Business Contract</th>
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

                        if (activeCompanyStructurePK != null && activeCompanyStructurePK.equals(currentCompanyStructurePK))
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
                onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="showAttachedCodeTables()">
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

    <br>

    <table width="100%">
      <tr>
        <td width="33%">
          &nbsp;
        </td>
        <td align="center" valign="bottom" width="33%">
          <span class="tableHeading">Code Table Summary</span>
        </td>
        <td align="right" valign="bottom" width="33%">
          <input type="button" value="Code Table Summary" onClick="showCodeTableSummary()">
        </td>
      </tr>
    </table>

    <table class="summary" width="100%" height="40%" border="0" cellspacing="0" cellpadding="0">
      <tr class="heading">
        <th width="33%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Code</th>
        <th width="33%">Code Description  </th>
      </tr>
      <tr>
        <td height="98%" colspan="2">
          <span class="scrollableContent">
            <table id="codeTable" class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
            <%
                if (bizCodeTableVOs != null)
                {
                    for (int i = 0; i < bizCodeTableVOs.length; i++)
                    {
                        CodeTableVO codeTableVO = bizCodeTableVOs[i].getCodeTableVO();
                        String currentCodeTablePK =  codeTableVO.getCodeTablePK() + "";
                        String currentCode = codeTableVO.getCode();
                        String currentCodeDesc = codeTableVO.getCodeDesc();
                        String codeDescriptionOverride = bizCodeTableVOs[i].getCodeDescriptionOverride();
                        String filteredCodeTablePK = bizCodeTableVOs[i].getFilteredCodeTablePK() + "";

                        if (codeDescriptionOverride != null)
                        {
                            currentCodeDesc = codeDescriptionOverride;
                        }

                        String className = null;

                        if (bizCodeTableVOs[i].getIsCodeTableAttached())
                        {
                            className = "associated";
                        }
                        else
                        {
                            className = "default";
                        }
            %>
            <tr class="<%= className %>" codeTablePK="<%= currentCodeTablePK %>" filteredCodeTablePK="<%= filteredCodeTablePK %>">
              <td nowrap width="2%">
              <%
                  if (bizCodeTableVOs[i].getIsCodeTableAttached())
                  {
                     out.println("<img src=\"/PORTAL/codetable/images/repeating_b2.gif\" width=\"28\" height=\"15\" alt=\"showFilteredCodeTableDialog\" onClick='showFilteredCodeTableDialog()' onMouseOver=\"this.src='/PORTAL/codetable/images/repeating_b2_over.gif'\" onMouseOut=\"this.src='/PORTAL/codetable/images/repeating_b2.gif'\">");
                  }
                  else
                  {
                     out.println("&nbsp");
                  }
              %>
              </td>
              <td nowrap width="33%">
                <%= currentCode %>
              </td>
              <td nowrap width="33%">
                <%= currentCodeDesc %>
              </td>
            </tr>
            <%
                    } //end for loop
                } // end if
            %>
            <tr class="filler">
              <td colspan="2">
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
          <input type="button" name="build" value="Build" onClick="showBuildCodeTableDialog()">
          <input type="button" name="cancel" value="Cancel" onClick="cancelCodeTableRelations()">
        </td>
      </tr>
    </table>

 <input type="hidden" name="transaction">
 <input type="hidden" name="action">
 <input type="hidden" name="activeCompanyStructurePK" value="<%= activeCompanyStructurePK %>">
 <input type="hidden" name="selectedCodeTablePK" value="">
 <input type="hidden" name="selectedCodeTablePKs" value="">
 <input type="hidden" name="selectedFilteredCodeTablePKs" value="null">
 <input type="hidden" name="selectedFilteredCodeTablePK" value="<%= selectedFilteredCodeTablePK %>">

</form>
</body>
</html>