<%@ page import="edit.common.vo.*,
                 java.util.*,
                 fission.utility.*,
                 edit.common.vo.user.*,
                 engine.*"%>
<%@ page errorPage="/common/jsp/error.jsp" autoFlush="true" %>

<%
    String message = (String) request.getAttribute("message");
    message = (message == null)?"":message;

    ProductStructureVO activeProductStructureVO = (ProductStructureVO) request.getAttribute("companyStructureVO");
    String activeCompanyStructurePK = null;
    String activeCompany = null;
    String activeMarketingPackage = null;
    String activeGroupProduct = null;
    String activeArea = null;
    String activeBusinessContract = null;
    String activeProductStructure = null;

    if (activeProductStructureVO != null)
    {
        activeCompanyStructurePK = activeProductStructureVO.getProductStructurePK() + "";
        Company company = Company.findByPK(new Long(activeProductStructureVO.getCompanyFK()));

        activeCompany = company.getCompanyName();
        activeMarketingPackage = activeProductStructureVO.getMarketingPackageName();
        activeGroupProduct = activeProductStructureVO.getGroupProductName();
        activeArea = activeProductStructureVO.getAreaName();
        activeBusinessContract = activeProductStructureVO.getBusinessContractName();
        activeProductStructure = activeCompany + ", " + activeMarketingPackage + "," +
                                  activeGroupProduct + ", " + activeArea + ", " + activeBusinessContract;
    }

    activeCompanyStructurePK = Util.initString(activeCompanyStructurePK, "0");

    ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("companyStructureVOs");

    String relationCodeTableDefPK = Util.initString((String) request.getAttribute("relationCodeTableDefPK"), "0");

    BIZCodeTableVO[] bizCodeTableVOs = (BIZCodeTableVO[]) request.getAttribute(("bizCodeTableVOs"));
%>

<html>

<head>
<title>Build Code Table Dialog</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script language="Javascript1.2">

	var f = null;
    var message = "<%= message %>";

	function init()
    {
		f = document.theForm;

        if (message.length > 0)
        {
            alert(message);
        }

        f.cloneButton.disabled=true;
    }

    function enableDisableCodeTableSelection()
    {
        var select = window.event.srcElement;

        if (select.selectedIndex == 0) // If "Please Select"
        {
            f.attachButton.disabled = false;
            f.detachButton.disabled = false;
            f.cloneButton.disabled = true;
        }
        else
        {
            f.attachButton.disabled = true;
            f.detachButton.disabled = true;
            f.cloneButton.disabled = false;
        }
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

    function attachCompanyAndCodeTable()
    {
        if (f.relationCodeTableDefPK.value == "0")
        {
            alert("Code Table Name Required");

            return;
        }

        f.selectedCodeTablePKs.value = getSelectedCodeTablePKs();

        if (f.selectedCodeTablePKs.value == "null" || f.activeCompanyStructurePK.value == "0")
        {
            alert("Company Structure And CodeTable Selection Required");

            return;
        }
        else
        {
            sendTransactionAction("CodeTableTran", "attachCompanyAndCodeTable", "buildCodeTableDialog");
        }
    }

    function detachCompanyAndCodeTable()
    {
        f.selectedCodeTablePKs.value = getSelectedCodeTablePKs();

        if (f.relationCodeTableDefPK.value == "0")
        {
            alert("Code Table Name Required");

            return;
        }

        if (f.selectedCodeTablePKs.value == "null" || f.activeCompanyStructurePK.value == "0")
        {
            alert("Company Structure And CodeTable Selection Required");

            return;
        }
        else
        {
            sendTransactionAction("CodeTableTran", "detachCompanyAndCodeTable", "buildCodeTableDialog");
        }
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

        for (var i = 0; i < codeTableTable.rows.length; i++)
        {
            if (codeTableTable.rows[i].isSelected == "true")
            {
                if (selectedCodeTablePKs == "null") selectedCodeTablePKs = "";

                selectedCodeTablePKs += codeTableTable.rows[i].codeTablePK + ",";
            }
        }

        return selectedCodeTablePKs;
    }

    function cloneFilteredCodeTable()
    {
        if (f.cloneToCompanyStructurePK.value == "null")
        {
            alert("Clone To Company Structure Required");
        }
        else
        {
            sendTransactionAction("CodeTableTran", "cloneFilteredCodeTable", "buildCodeTableDialog");
        }
    }

</script>
</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm">

<%--Header--%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" nowrap>Active Company Structure:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled type="text" size="50" maxlength="50" name="activeCompanyStructure" value="<%= activeProductStructure %>">
    </td>
    <td align="right" nowrap>Clone To:&nbsp;</td>
    <td align="left" nowrap>
      <select name="cloneToCompanyStructurePK" onChange="enableDisableCodeTableSelection()">
        <option name="id" value="0">Please Select</option>
        <%
            if (productStructureVOs != null)
            {
                for(int i = 0; i < productStructureVOs.length; i++)
                {
                    String cloneCompanyStructurePK = productStructureVOs[i].getProductStructurePK() + "";
                    Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));

                    String cloneCompany = company.getCompanyName();
                    String cloneMarketingPackage = productStructureVOs[i].getMarketingPackageName();
                    String cloneGroupProduct = productStructureVOs[i].getGroupProductName();
                    String cloneArea = productStructureVOs[i].getAreaName();
                    String cloneBusinessContract = productStructureVOs[i].getBusinessContractName();
                    String cloneCompanyStructure = cloneCompany + ", " + cloneMarketingPackage + "," +
                                              cloneGroupProduct + ", " + cloneArea + ", " + cloneBusinessContract;

                    out.println("<option name=\"id\" value=\"" + cloneCompanyStructurePK + "\">" + cloneCompanyStructure + "</option>");
                }
            }
        %>
      </select>
    </td>
  </tr>
</table>

<br>

<table class="summary" width="100%" height="40%" border="0" cellspacing="0" cellpadding="0">
  <tr class="heading">
    <th width="50%" align="left">Code</th>
    <th width="50%" align="left">Code Description </th>
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
                    boolean isSelected = false;

                    if (bizCodeTableVOs[i].getIsCodeTableAttached())
                    {
                        className = "associated";
                    }
                    else
                    {
                        className = "default";
                    }
        %>
        <tr class="<%= className %>" codeTablePK="<%= currentCodeTablePK %>" filteredCodeTablePK="<%= filteredCodeTablePK %>"
            isSelected="<%= isSelected %>" onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectRow(true)">
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
      <input type="button" name="cloneButton" value=" Clone " onClick="cloneFilteredCodeTable()">
      <input type="button" name="attachButton" value="Attach" onClick="attachCompanyAndCodeTable()">
      <input type="button" name="detachButton" value="Detach" onClick="detachCompanyAndCodeTable()">
      <input type="button" name="cancelButton" value="Cancel" onClick="cancelDialog()">
    </td>
  </tr>
</table>

    <!-- ****** HIDDEN FIELDS ***** //-->
    <input type="hidden" name="transaction" value="">
    <input type="hidden" name="action"      value="">
    <input type="hidden" name="activeCompanyStructurePK" value="<%= activeCompanyStructurePK %>">
    <input type="hidden" name="relationCodeTableDefPK" value="<%= relationCodeTableDefPK %>">
    <input type="hidden" name="cloneToCompanyStructurePK" value="null">
    <input type="hidden" name="selectedCodeTablePKs" value="">

</form>
</body>
</html>
