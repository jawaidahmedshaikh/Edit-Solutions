<%@ page import="contract.Requirement,
                 fission.utility.Util"%><!--
 * User: sprasad
 * Date: Aug 10, 2005
 * Time: 10:58:26 AM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    Requirement requirement = (Requirement) request.getAttribute("requirement");

    String requirementId = (String) Util.initObject(requirement, "requirementId", "");
    String requirementDescription = (String) Util.initObject(requirement, "requirementDescription", "");

    Requirement[] requirements = (Requirement[]) request.getAttribute("requirements");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>Case Tracking Manual Requirement</title>
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<%-- ****************************** BEGIN JavaScript ****************************** --%>
<script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

<script>

    var f = null;

    var responseMessage = "<%= responseMessage %>";

    /**
     * Called after the body of the page is loaded.
     */
    function init()
    {
        f = document.theForm;

        checkForResponseMessage();
    }

    function saveManualRequirement()
    {
        if (selectElementIsEmpty(f.requirementId))
        {
            alert('Please Select Requirement Id');
            return;
        }
        else if (textElementIsEmpty(f.requirementDescription))
        {
            alert('Please Enter Requirement Description');
            return;
        }

        sendTransactionAction("CaseTrackingTran", "saveManualRequirement", "main");

        window.close();
    }

    function showRequirementDescription()
    {
        sendTransactionAction("CaseTrackingTran", "showRequirementDescription", "_self");
    }

</script>
<%-- ****************************** END JavaScript ****************************** --%>

</head>
<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

<%-- ****************************** BEGIN Form Data ****************************** --%>
<table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
<%--    BEGIN Form Content --%>
    <tr>
        <td align="right" nowrap>
            Requirement Id:&nbsp;
        </td>
        <td align="left" nowrap>
            <select name="requirementId" onChange="showRequirementDescription()">
                <option value="null">Please Select</option>
                <%
                    if (requirements != null)
                    {
                        for(int i = 0; i < requirements.length; i++)
                        {
                            String currentCodeDesc    = requirements[i].getRequirementId();
                            String currentCode        = requirements[i].getRequirementId();

                            if (currentCode.equals(requirementId))
                            {
                                out.println("<option selected name=\"id\" value=\"" + currentCode + "\">" + currentCodeDesc + "</option>");
                            }
                            else
                            {
                                out.println("<option name=\"id\" value=\"" + currentCode + "\">" + currentCodeDesc + "</option>");
                            }
                        }
                    }
                %>
            </select>
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
    <tr>
        <td align="right" nowrap>
            Requirement Description:&nbsp;
        </td>
        <td align="left" nowrap>
            <input type="text" name="requirementDescription" size="50" value="<%= requirementDescription %>" DISABLED>
        </td>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            &nbsp;
        </td>
    </tr>
<%--    END Form Content --%>

    <tr class="filler">
        <td colspan="4">
            &nbsp; <!--Filler Row -->
        </td>
    </tr>

    <tr>
        <td align="right" colspan="4">
            <input type="button" value=" Save " onClick="saveManualRequirement()">
            <input type="button" value="Cancel" onClick="window.close()">
        </td>
    </tr>

</table>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>