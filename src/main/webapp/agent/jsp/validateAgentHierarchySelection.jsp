<%@ page import="agent.*" %>
<%--
  Created by IntelliJ IDEA.
  User: gfrosti
  Date: Nov 12, 2004
  Time: 3:08:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <meta http-equiv="Cache-Control" content="no-store">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">

    <title>Validate Hierarchy Selection</title>
    <link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

    <%-- ****************************** BEGIN JavaScript ****************************** --%>
    <script src="/PORTAL/common/javascript/commonJavascriptFunctions.js"></script>

    <script language="javascript">

        var f = null;

        /**
        * Called after the body of the page is loaded.
        */
        function init()
        {
            f = document.theForm;
        }

        /**
         * If the user selects/deselects "all", the associated checkboxes should be enabled/disabled.
         */
        function enableDisableCheckboxes()
        {
            if (document.all.validateAll.checked)
            {
                document.all.validate<%= ValidateHierarchy.REPORT_BOUNDARIES %>.checked = false;
                document.all.validate<%= ValidateHierarchy.REPORT_LEVELS %>.checked = false;
                document.all.validate<%= ValidateHierarchy.REPORT_ASSOCIATIONS %>.checked = false;
            }
        }


        /**
         * Unchecks the "validateAll" checkbox.
         */
        function uncheckValidateAll()
        {
            document.all.validateAll.checked = false;
        }

        /**
        * Launches the validation process.
        */
        function validateAgentHierarchy()
        {
            if (document.all.validate<%= ValidateHierarchy.REPORT_BOUNDARIES %>.checked == false &&
                document.all.validate<%= ValidateHierarchy.REPORT_LEVELS %>.checked == false &&
                document.all.validate<%= ValidateHierarchy.REPORT_ASSOCIATIONS %>.checked == false &&
                document.all.validateAll.checked == false)
            {
                alert("Selection Required");
            }
            else
            {

                var width = 0.95 * screen.width;

                var height = 0.95 * screen.height;

                openDialog("validateHierarchy", "left=0,top=0,resizable=no,scrollbars=yes", width, height);

                sendTransactionAction("AgentDetailTran", "validateAgentHierarchy", "validateHierarchy");

                closeWindow();
            }
        }
    </script>
    <%-- ****************************** END JavaScript ****************************** --%>

</head>

<body class="mainTheme" onLoad="init()">
<form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

    <%-- ****************************** BEGIN Form Data ****************************** --%>
    <table class="formData" width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr>
            <td>
                Performs a comprehensive analysis of the Agent Hierarchy to find faulty structural elements. (Note: Reports can take several minutes)
            </td>
        </tr>
        <tr>
            <td>
                <input type="checkbox" id="validate<%= ValidateHierarchy.REPORT_BOUNDARIES %>"
                       name="validate<%= ValidateHierarchy.REPORT_BOUNDARIES %>" onClick="uncheckValidateAll()">
                Validate Left/Right Boundaries<br>
                <input type="checkbox" id="validate<%= ValidateHierarchy.REPORT_LEVELS %>"
                       name="validate<%= ValidateHierarchy.REPORT_LEVELS %>" onClick="uncheckValidateAll()"> Validate
                Hierarchy Levels<br>
                <input type="checkbox" id="validate<%= ValidateHierarchy.REPORT_ASSOCIATIONS %>"
                       name="validate<%= ValidateHierarchy.REPORT_ASSOCIATIONS %>" onClick="uncheckValidateAll()">
                Validate Entity Associations<br>
                <input type="checkbox" id="validateAll" name="validateAll" onClick="enableDisableCheckboxes()"> Validate
                All (of above)
                <hr size="1" width="100%" noshade>
            </td>
        </tr>
        <tr>
            <td align="right">
                <input type="button" value="Cancel" onClick="closeWindow()">
                <input type="button" value="Validate" onClick="validateAgentHierarchy()">
            </td>
        </tr>

        <tr class="filler">
            <td colspan="1">
                &nbsp; <!--Filler Row -->
            </td>
        </tr>
    </table>
    <%-- ****************************** END Form Data ****************************** --%>

    <br>

    <%-- ****************************** BEGIN Hidden Variables ****************************** --%>
    <input type="hidden" name="transaction">
    <input type="hidden" name="action">
    <%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>