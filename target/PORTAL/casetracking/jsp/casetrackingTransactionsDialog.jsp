<%@ page import="edit.common.vo.CodeTableVO,
                 edit.common.CodeTableWrapper,
                 fission.utility.Util"%><!--
 * User: sprasad
 * Date: Mar 21, 2005
 * Time: 11:39:34 AM
 *
 * (c) 2000-2005 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 -->
 <%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- ****************************** BEGIN Java Code ****************************** --%>
<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    CodeTableVO[] casetrackingOptions = CodeTableWrapper.getSingleton().getCodeTableEntries("CASETRACKINGOPTIONS");

    String casetrackingOptionCode = Util.initString((String) request.getAttribute("casetrackingOptionCode"), "");

    String segmentPK = (String) request.getAttribute("segmentPK");
%>
<%-- ****************************** End Java Code ****************************** --%>

<html>
<head>
<title>EDIT SOLUTIONS - Case Tracking - Transactions</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
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

    function showSelectedTransaction()
    {
        if (selectElementIsEmpty(f.casetrackingOption))
        {
            alert('Please Select Case Tracking Option');
            return;
        }

        var width = 0.99 * screen.width;
        var height = 0.90 * screen.height;

	    openDialog("transactionDialog", "top=0,left=0,resizable=no", width,  height);
        sendTransactionAction("CaseTrackingTran", "showSelectedTransaction", "transactionDialog");

        window.close();
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
            Case Tracking Options:&nbsp;
        </td>
        <td align="left" nowrap>
            <select name="casetrackingOption">
                <option value="null">Please Select</option>
                <%
                    if (casetrackingOptions != null)
                    {
                        for(int i = 0; i < casetrackingOptions.length; i++)
                        {
                            String currentCodeDesc    = casetrackingOptions[i].getCodeDesc();
                            String currentCode        = casetrackingOptions[i].getCode();

                            if (currentCode.equals(casetrackingOptionCode))
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
    </tr>
    <tr>
        <td align="right" nowrap>
            &nbsp;
        </td>
        <td align="left" nowrap>
            <input type="button" value=" Enter " onClick="showSelectedTransaction()">
            <input type="button" value=" Close " onClick="window.close()">
        </td>
    </tr>
<%--    END Form Content --%>

</table>
<%-- ****************************** END Form Data ****************************** --%>

<%-- ****************************** BEGIN Hidden Variables ****************************** --%>
<input type="hidden" name="transaction">
<input type="hidden" name="action">
<input type="hidden" name="segmentPK" value="<%= segmentPK %>">
<%-- ****************************** END Hidden Variables ****************************** --%>

</form>
</body>
</html>
