<%@ page import="edit.common.vo.*,
                 logging.*"%>
<%
    String[] messageList = (String[]) request.getAttribute("messageList");
%>
<html>

<head>
<title>Message List</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

<script language="Javascript1.2">

    function closeWindow()
    {
        window.close();
    }

</script>
</head>
<body  class="mainTheme">

    <span class="tableHeading">Message List</span>

    <table class="summary" id="codeTableSubstitutionsTableHeader" width="100%" height="94%" border="0" borderColor="black" cellspacing="0" cellpadding="0">

        <tr class="heading" >
            <th nowrap width="5%">
                #
            </th>
            <th nowrap>
                Message
            </th>
        </tr>
        <tr>
            <td height="99%" colspan="5">
                <span class="scrollableContent" style="border-style:solid; border-width:1; position:relative; width:100%; height:100%; top:0; left:0; z-index:0;">
                    <table class="summary" id="messageList" width="100%" height="100%" border="0" borderColor="black" cellspacing="0" cellpadding="2">
                    <%
                        for (int i = 0; i < messageList.length; i++)
                        {
                            int messageOrder = messageList.length - i - 1;
                    %>
                            <tr class="default">

                                <td width="5%">
                                    <%= messageOrder %>
                                </td>
                                <td>
                                    <%= messageList[i] %>
                                </td>
                            </tr>
                    <%
                        }
                    %>
                            <tr class="filler"> <!-- A dummy row to help with sizing -->
                                <td colspan="2">
                                    &nbsp;
                                </td>
                            </tr>

                    </table>
                </span>
            </td>

        </tr>


    </table>

<table id="transformSummaryTable" width="100%" border="0" cellspacing="0" cellpadding="0">

    <tr>

        <td align="right">

            <input type="button" name="btnClose" value="Close" onClick="closeWindow()">

        </td>

    </tr>

</table>

    <!-- ****** HIDDEN FIELDS ***** //-->


</body>
</html>
