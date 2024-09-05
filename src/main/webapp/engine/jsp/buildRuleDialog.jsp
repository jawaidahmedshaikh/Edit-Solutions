<!--
 * User: unknown
 * Date: Jan 1, 2000
 * Time: unknown
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 -->
<%@ page import="edit.common.vo.*,
                 java.util.*,
                 fission.utility.*,
                 edit.common.vo.user.*,
                 edit.common.EDITDate,
                 engine.*"%>
<%
    String message = (String) request.getAttribute("message");
    message = (message == null)?"":message;

    ProductStructureVO[] productStructureVO = (ProductStructureVO[]) request.getAttribute("productStructureVO");
    String activeProductStructurePK = null;
    String activeCompany = null;
    String activeMarketingPackage = null;
    String activeGroupProduct = null;
    String activeArea = null;
    String activeBusinessContract = null;
    String activeProductStructure = null;
    if (productStructureVO != null)
    {
        activeProductStructurePK = productStructureVO[0].getProductStructurePK() + "";
        Company company = Company.findByPK(new Long(productStructureVO[0].getCompanyFK()));
        activeCompany = company.getCompanyName();
        activeMarketingPackage = productStructureVO[0].getMarketingPackageName();
        activeGroupProduct = productStructureVO[0].getGroupProductName();
        activeArea = productStructureVO[0].getAreaName();
        activeBusinessContract = productStructureVO[0].getBusinessContractName();
        activeProductStructure = activeCompany + ", " + activeMarketingPackage + "," +
                                  activeGroupProduct + ", " + activeArea + ", " + activeBusinessContract;
    }
    
    activeProductStructurePK = Util.initString(activeProductStructurePK, "0");

    ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("productStructureVOs");

    UIRulesVO[] uiRulesVOs = (UIRulesVO[]) request.getAttribute("uiRulesVOs");

    List attachedRulesPKs = (List) request.getAttribute("attachedRulesPKs");

    List repeatingRulePKs = (List) request.getAttribute("repeatingRulePKs");
    
    String scriptFilterType = Util.initString((String) request.getAttribute("scriptFilterType"), "All");
    String scriptFilterName = Util.initString((String) request.getAttribute("scriptFilterName"), "");
    String tableFilterType = Util.initString((String) request.getAttribute("tableFilterType"), "All");
    String tableFilterName = Util.initString((String) request.getAttribute("tableFilterName"), "");
%>

<html>

<head>
<title>Build Dialog</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">
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

        f.cloneButton.disabled = true;
    }

    function enableDisableRuleSelection()
    {
        var select = window.event.srcElement;

        var selectName = select.name;
        var rootSelectName = selectName.substring(0, selectName.length - 6);
        var textField = document.getElementById(rootSelectName);

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

    function filterScriptRules()
    {
        var width   = .35 * screen.width;
        var height  = .15 * screen.height;

        openDialog("","filterScriptRulesDialog","left=0,top=0,width=" + width + ",height=" + height,"ProductRuleStructureTran", "showFilterScriptRulesDialog", "filterScriptRulesDialog");
    }

    function filterTableRules()
    {
        var width   = .35 * screen.width;
        var height  = .15 * screen.height;

        openDialog("","filterTableRulesDialog","left=0,top=0,width=" + width + ",height=" + height,"ProductRuleStructureTran", "showFilterTableRulesDialog", "filterTableRulesDialog");
    }

    function attachRules()
    {
        f.selectedRulePKs.value = getSelectedRulePKs();

        sendTransactionAction("ProductRuleStructureTran", "attachProductAndRule", "buildRuleDialog");
    }

    function detachRules()
    {
        f.selectedRulePKs.value = getSelectedRulePKs();

        sendTransactionAction("ProductRuleStructureTran", "detachProductAndRule", "buildRuleDialog");
    }

    function getSelectedRulePKs()
    {
        var scriptRulesTable = document.all.scriptRulesTable;
        var tableRulesTable = document.all.tableRulesTable;

        var selectedRulePKs = "";

        for (var i = 0; i < scriptRulesTable.rows.length; i++)
        {
            if (scriptRulesTable.rows[i].isSelected == "true")
            {
                selectedRulePKs += scriptRulesTable.rows[i].rulesPK + ",";
            }
        }

        for (var i = 0; i < tableRulesTable.rows.length; i++)
        {
            if (tableRulesTable.rows[i].isSelected == "true")
            {
                selectedRulePKs += tableRulesTable.rows[i].rulesPK + ",";
            }
        }

        return selectedRulePKs;
    }

    function openDialog(theURL, winName, features, transaction, action, target)
    {
        dialog = window.open(theURL,winName,features);

        sendTransactionAction(transaction, action, target);
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

        if (tdElement.tagName == "IMG") // The IMG could have been the source of the click, therefore take a different path.
        {
            if (tdElement.id == "splashButtonScript")
            {
                showAssociatedScriptForRule();
            }
            else if (tdElement.id == "splashButtonTable")
            {
                // Do nothing. This feature will be added in the future.
            }
        }
        else
        {
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
            else
            {
                if (className == "mainEntry")
                {
                    currentRow.style.backgroundColor = "#BBBBBB";
                }
                else if (className == "assocEntry")
                {
                    currentRow.style.backgroundColor = "#00BB00";
                }

                currentRow.isSelected = "false";
            }
        }
    }

    function showAssociatedScriptForRule()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement.parentElement; // img -> td -> TR (currentRow)

        f.scriptOrTablePK.value = currentRow.scriptOrTablePK;

        var width   = 0.60 * screen.width;
        var height  = 0.50 * screen.height;

        openDialog("","associatedScriptForRuleDialog","left=50,top=50,resizable=yes,width=" + width + ",height=" + height,"ProductRuleStructureTran", "showAssociatedScriptForRule", "associatedScriptForRuleDialog");
    }

    function highlightRow()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        currentRow.style.backgroundColor = "#FFFFCC";
    }

    function unhighlightRow()
    {
        var tdElement = window.event.srcElement;
        var currentRow = tdElement.parentElement;

        if (currentRow.tagName != "TR") // Bubble up if necessary
        {
            currentRow = currentRow.parentElement;
        }

        var className = currentRow.className;

        if (currentRow.isSelected != "true")
        {
            if (className == "assocEntry")
            {
                currentRow.style.backgroundColor = "#00BB00";
            }
            else if (className == "highLighted")
            {
                currentRow.style.backgroundColor = "#FFFFCC";
            }
            else
            {
                currentRow.style.backgroundColor = "#BBBBBB";
            }
        }
    }

    function cloneRules()
    {
        if (f.cloneProductStructurePK.value == "null")
        {
            alert("Clone To Company Structure Required");
        }
        else
        {
            sendTransactionAction("ProductRuleStructureTran", "cloneProductStructure", "_self");
        }
    }

</script>
</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="right" nowrap>Active Company Structure:&nbsp;</td>
    <td align="left" nowrap>
      <input disabled type="text" size="50" maxlength="50" name="activeProductStructure" value="<%= activeProductStructure %>">
    </td>
    <td align="right" nowrap>Clone To:&nbsp;</td>
    <td align="left" nowrap>
      <select name="cloneProductStructurePK" onChange="enableDisableRuleSelection()">
        <option name="id" value="0">Please Select</option>
        <%
            if (productStructureVOs != null)
            {
                for(int i = 0; i < productStructureVOs.length; i++)
                {
                    String cloneProductStructurePK = productStructureVOs[i].getProductStructurePK() + "";
                    Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));

                    String cloneCompany = company.getCompanyName();
                    String cloneMarketingPackage = productStructureVOs[i].getMarketingPackageName();
                    String cloneGroupProduct = productStructureVOs[i].getGroupProductName();
                    String cloneArea = productStructureVOs[i].getAreaName();
                    String cloneBusinessContract = productStructureVOs[i].getBusinessContractName();
                    String cloneProductStructure = cloneCompany + ", " + cloneMarketingPackage + "," +
                                              cloneGroupProduct + ", " + cloneArea + ", " + cloneBusinessContract;

                    out.println("<option name=\"id\" value=\"" + cloneProductStructurePK + "\">" + cloneProductStructure + "</option>");
                }
            }
        %>
      </select>
    </td>
  </tr>
</table>

  <br><br>
  Script Rules:
  <br>

  <table width="100%">
    <tr>
      <td align="right" valign="bottom">
        <input type="button" name="scriptFilterButton" value="Filter" onClick="filterScriptRules()">
      </td>
    </tr>
  </table>

  <span class="contentArea" style="border-style:solid; border-width:1; position:relative; width:100%; height:30%; top:0; left:0; z-index:0; overflow:visible">
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <th class="dataHeading3" width="2%">
            &nbsp;
        </th>
        <th class="dataHeading3" width="14%">
            Effective Date
        </th>
        <th class="dataHeading3" width="14%">
            Process
        </th>
        <th class="dataHeading3" width="14%">
            Event
        </th>
        <th class="dataHeading3" width="14%">
            Event Type
        </th>
        <th class="dataHeading3" width="14%">
            Rule Name
        </th>
        <th class="dataHeading3" width="14%">
            Script/Table Name
        </th>
      </tr>
      <tr height="100%">
        <td colspan="8">
          <span class="summaryTable" style="border-style:solid; border-width:1; position:relative;
                                           width:100%; height:100%; top:0; left:0; z-index:0; overflow:scroll">
            <table id="scriptRulesTable" width="100%" border="0" cellspacing="0" cellpadding="0" >
              <%
                if (uiRulesVOs != null)
                {
                    Arrays.sort(uiRulesVOs);

                    for (int i = 0; i < uiRulesVOs.length; i++)
                    {
                        if (uiRulesVOs[i].getRulesVO().getTableDefFK() == 0)
                        {
                            if (scriptFilterType.equalsIgnoreCase("All") ||
                                (scriptFilterType.equalsIgnoreCase("RuleName") &&
                                 scriptFilterName.equalsIgnoreCase(uiRulesVOs[i].getRulesVO().getRuleName())) ||
                                (scriptFilterType.equalsIgnoreCase("Process") &&
                                 scriptFilterName.equalsIgnoreCase(uiRulesVOs[i].getRulesVO().getProcessName())))
                            {
                                String currentRulesPK = uiRulesVOs[i].getRulesVO().getRulesPK() + "";
                                String currentEffectiveDate = new EDITDate(uiRulesVOs[i].getRulesVO().getEffectiveDate()).getFormattedDate();
                                String currentProcess = uiRulesVOs[i].getRulesVO().getProcessName();
                                String currentEvent = uiRulesVOs[i].getRulesVO().getEventName();
                                String currentEventType = uiRulesVOs[i].getRulesVO().getEventTypeName();
                                String currentRule = uiRulesVOs[i].getRulesVO().getRuleName();
                                String scriptOrTableName = uiRulesVOs[i].getScriptVO().getScriptName();
                                String scriptOrTablePK = uiRulesVOs[i].getScriptVO().getScriptPK() + "";

                                String className = null;

                                if (attachedRulesPKs.contains(new Long(currentRulesPK)))
                                {
                                    className = "assocEntry";
                                }
                                else{

                                    className = "mainEntry";
                                }
              %>
              <tr height="15" class="<%= className %>" rulesPK="<%= currentRulesPK %>" scriptOrTablePK="<%= scriptOrTablePK %>" isSelected="false"
                  onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectDeselectRow()">
                <td width="2%">
                <%
                    if (repeatingRulePKs.contains(new Long(currentRulesPK)))
                    {
                        out.println("<img src=\"/PORTAL/engine/images/repeating_b1.gif\" width=\"28\" height=\"15\" alt=\"Repeating Rule Name\">");
                    }
                    else
                    {
                        out.println("&nbsp");
                    }
                %>
                </td>
                <td width="14%">
                    <%= currentEffectiveDate %>
                </td>
                <td width="14%">
                    <%= currentProcess %>
                </td>
                <td width="14%">
                    <%= currentEvent %>
                </td>
                <td width="14%">
                    <%= currentEventType %>
                </td>
                <td width="14%">
                    <%= currentRule %>
                </td>
                <td width="14%">
                    <%= scriptOrTableName %> <img id="splashButtonScript" src="/PORTAL/engine/images/scripttable_b1_over.gif" width="24" height="12" alt="Script">
                </td>
              </tr>
              <%
                            }
                        }
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
  Table Rules:
  <br>

  <table width="100%">
    <tr>
      <td align="right" valign="bottom">
        <input type="button" name="tableFilterButton" value="Filter" onClick="filterTableRules()">
      </td>
    </tr>
  </table>

  <span class="contentArea" style="border-style:solid; border-width:1; position:relative; width:100%; height:30%; top:0; left:0; z-index:0; overflow:visible">
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <th class="dataHeading3" width="2%">
            &nbsp;
        </th>
        <th class="dataHeading3" width="14%">
            Effective Date
        </th>
        <th class="dataHeading3" width="14%">
            Process
        </th>
        <th class="dataHeading3" width="14%">
            Event
        </th>
        <th class="dataHeading3" width="14%">
            Event Type
        </th>
        <th class="dataHeading3" width="14%">
            Rule Name
        </th>
        <th class="dataHeading3" width="14%">
            Script/Table Name
        </th>
      </tr>
      <tr height="100%">
        <td colspan="8">
          <span class="summaryTable" style="border-style:solid; border-width:1; position:relative;
                                           width:100%; height:100%; top:0; left:0; z-index:0; overflow:scroll">
            <table id="tableRulesTable" width="100%" border="0" cellspacing="0" cellpadding="0" >
              <%
                if (uiRulesVOs != null)
                {
                    Arrays.sort(uiRulesVOs);

                    for (int i = 0; i < uiRulesVOs.length; i++)
                    {
                        if (uiRulesVOs[i].getRulesVO().getTableDefFK() > 0)
                        {
                            if (tableFilterType.equalsIgnoreCase("All") ||
                                (tableFilterType.equalsIgnoreCase("RuleName") &&
                                 tableFilterName.equalsIgnoreCase(uiRulesVOs[i].getRulesVO().getRuleName())) ||
                                (tableFilterType.equalsIgnoreCase("Process") &&
                                 tableFilterName.equalsIgnoreCase(uiRulesVOs[i].getRulesVO().getProcessName())))
                            {
                                String currentRulesPK = uiRulesVOs[i].getRulesVO().getRulesPK() + "";
                                String currentEffectiveDate = new EDITDate(uiRulesVOs[i].getRulesVO().getEffectiveDate()).getFormattedDate();
                                String currentProcess = uiRulesVOs[i].getRulesVO().getProcessName();
                                String currentEvent = uiRulesVOs[i].getRulesVO().getEventName();
                                String currentEventType = uiRulesVOs[i].getRulesVO().getEventTypeName();
                                String currentRule = uiRulesVOs[i].getRulesVO().getRuleName();
                                String scriptOrTableName = uiRulesVOs[i].getTableDefVO().getTableName();
                                String scriptOrTablePK = uiRulesVOs[i].getTableDefVO().getTableDefPK() + "";

                                String className = null;

                                if (attachedRulesPKs.contains(new Long(currentRulesPK)))
                                {
                                    className = "assocEntry";
                                }
                                else{

                                    className = "mainEntry";
                                }
              %>
              <tr height="15" class="<%= className %>" rulesPK="<%= currentRulesPK %>" scriptOrTablePK="<%= scriptOrTablePK %>" isSelected="false"
                  onMouseOver="highlightRow()" onMouseOut="unhighlightRow()" onClick="selectDeselectRow()">
                <td width="2%">
                <%
                    if (repeatingRulePKs.contains(new Long(currentRulesPK)))
                    {
                        out.println("<img src=\"/PORTAL/engine/images/repeating_b1.gif\" width=\"28\" height=\"15\" alt=\"Repeating Rule Name\">");
                    }
                    else
                    {
                        out.println("&nbsp");
                    }
                %>
                </td>
                <td width="14%">
                    <%= currentEffectiveDate %>
                </td>
                <td width="14%">
                    <%= currentProcess %>
                </td>
                <td width="14%">
                    <%= currentEvent %>
                </td>
                <td width="14%">
                    <%= currentEventType %>
                </td>
                <td width="14%">
                    <%= currentRule %>
                </td>
                <td width="14%">
                    <%= scriptOrTableName %> <img id="splashButtonTable" src="/PORTAL/engine/images/scripttable_b2_over.gif" width="24" height="12" alt="Table">
                </td>
              </tr>
              <%
                            }
                        }
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
        <img src="/PORTAL/engine/images/repeating_b1.gif" width="28" height="15" alt="Repeating Rule Name"> = Repeating Rule Name.
      </td>
      <td align="right" valign="bottom">
        <input type="button" name="cloneButton" value=" Clone " onClick="cloneRules()">
        <input type="button" name="attachButton" value="Attach" onClick="attachRules()">
        <input type="button" name="detachButton" value="Detach" onClick="detachRules()">
        <input type="button" name="cancelButton" value="Cancel" onClick="cancelDialog()">
      </td>
    </tr>
  </table>

    <!-- ****** HIDDEN FIELDS ***** //-->
    <input type="hidden" name="transaction" value="">
    <input type="hidden" name="action" value="">
    <input type="hidden" name="productStructurePK" value="<%= activeProductStructurePK %>">
    <input type="hidden" name="cloneProductStructurePK" value="null">
    <input type="hidden" name="selectedRulePKs" value="">
    <input type="hidden" name="scriptFilterType" value="<%= scriptFilterType %>">
    <input type="hidden" name="scriptFilterName" value="<%= scriptFilterName %>">
    <input type="hidden" name="tableFilterType" value="<%= tableFilterType %>">
    <input type="hidden" name="tableFilterName" value="<%= tableFilterName %>">
    <input type="hidden" name="scriptOrTablePK">

</form>
</body>
</html>
