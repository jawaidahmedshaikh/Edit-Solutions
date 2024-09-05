<%@ page import="edit.common.vo.*,
                 edit.common.vo.user.*,
                 java.util.*,
                 engine.*"%>
<%
    ProductStructureVO[] productStructureVOs = (ProductStructureVO[]) request.getAttribute("productStructureVOs");

    UIRulesVO[] uiRulesVOs = (UIRulesVO[]) request.getAttribute("uiRulesVOs");
%>
<html>

<head>
<title>Attached Company Stuctures By Rule</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/PORTAL.css" rel="stylesheet" type="text/css">

<script language="Javascript1.2">

	function init() {

	}

    function closeWindow()
    {
        window.close();
    }

</script>
</head>
<body  class="mainTheme" onLoad="init()">

    <span class="tableHeading">Rule</span><br>
    <table width="100%" height="15%" border="0" cellspacing="0" cellpadding="0">

        <tr>

            <th class="dataHeading3" width="4%">
                &nbsp;
            </th>
            <th class="dataHeading3" width="16%">
                Effective Date
            </th>
            <th class="dataHeading3" width="16%">
                Process
            </th>
            <th class="dataHeading3" width="16%">
                Event
            </th>
            <th class="dataHeading3" width="16%">
                Event Type
            </th>
            <th class="dataHeading3" width="16%">
                Rule Name
            </th>
            <th class="dataHeading3" width="16%">
                Script/Table Name
            </th>

        </tr>

        <tr height="100%">

            <td colspan="8">

                <span class="summaryTable" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0; overflow:visible">

                    <table id="rulesTable" width="100%" border="0" cellspacing="0" cellpadding="0" >
<%
                        if (uiRulesVOs != null){

                            Arrays.sort(uiRulesVOs);

                            for (int i = 0; i < uiRulesVOs.length; i++){

                                String currentRulesPK = uiRulesVOs[i].getRulesVO().getRulesPK() + "";
                                String currentEffectiveDate = uiRulesVOs[i].getRulesVO().getEffectiveDate();
                                String currentProcess = uiRulesVOs[i].getRulesVO().getProcessName();
                                String currentEvent = uiRulesVOs[i].getRulesVO().getEventName();
                                String currentEventType = uiRulesVOs[i].getRulesVO().getEventTypeName();
                                String currentRule = uiRulesVOs[i].getRulesVO().getRuleName();
                                String scriptOrTableName = null;
                                boolean isScript = false;

                                if (uiRulesVOs[i].getRulesVO().getScriptFK() != 0)
                                {
                                    scriptOrTableName = uiRulesVOs[i].getScriptVO().getScriptName();
                                    isScript = true;
                                }
                                else
                                {
                                    scriptOrTableName = uiRulesVOs[i].getTableDefVO().getTableName();
                                    isScript = false;
                                }

                                String className = "mainEntry";
%>
                                <tr height="15" class="<%= className %>" id="<%= currentRulesPK %>" rulesPK="<%= currentRulesPK %>">

                                    <td width="4%">
                                        &nbsp;
                                    </td>
                                    <td width="16%">
                                        <%= currentEffectiveDate %>
                                    </td>
                                    <td width="16%">
                                        <%= currentProcess %>
                                    </td>
                                    <td width="16%">
                                        <%= currentEvent %>
                                    </td>
                                    <td width="16%">
                                        <%= currentEventType %>
                                    </td>
                                    <td width="16%">
                                        <%= currentRule %>
                                    </td>
                                    <td width="16%">
                                        <%= scriptOrTableName %> <%= (isScript)?"<img src=\"/PORTAL/engine/images/scripttable_b1_over.gif\" width=\"24\" height=\"12\" alt=\"Script\">":"<img src=\"/PORTAL/engine/images/scripttable_b2_over.gif\" width=\"24\" height=\"12\" alt=\"Table\">" %>
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

    <br>
    <span class="tableHeading">Attached Company Structures</span><br>
        <table width="100%" height="50%" border="0" cellspacing="0" cellpadding="0">

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

                                    String currentCompanyStructurePK = productStructureVOs[i].getProductStructurePK() + "";
                                    Company company = Company.findByPK(new Long(productStructureVOs[i].getCompanyFK()));
                                    String currentCompany = company.getCompanyName();
                                    String currentMarketingPackage = productStructureVOs[i].getMarketingPackageName();
                                    String currentGroupProduct = productStructureVOs[i].getGroupProductName();
                                    String currentArea = productStructureVOs[i].getAreaName();
                                    String currentBusinessContract = productStructureVOs[i].getBusinessContractName();

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
                            }
%>
                        </table>

                    </span>
                </td>

            </tr>
        </table>

<table width="100%">
    <tr>
         <td align="right" valign="bottom">
            <input type="button" name="clone" value="Close" onClick="closeWindow()">
        </td>
    </tr>
</table>

</body>
</html>
