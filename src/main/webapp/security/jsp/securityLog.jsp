<%@ page  errorPage="/common/jsp/error.jsp" autoFlush="true" %>
<%@ page import="security.SecurityLog"%>

<%
    SecurityLog[] securityLogs = (SecurityLog[]) request.getAttribute("securityLogs");
%>

<html>

    <head>

        <title>EDITSOLUTIONS - Security Log</title>

        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <meta http-equiv="Cache-Control" content="no-store">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">

        <link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">

        <script language="javascript1.2">

            var f = null;

            function init()
            {
                f = document.theForm;
            }

            function sendTransactionAction(transaction, action, target)
            {
                f.transaction.value = transaction;
                f.action.value = action;

                f.target = target;

                f.submit();
            }

        </script>

    </head>

    <body  class="mainTheme" onLoad="init()">

    <form name="theForm" method="post" action="/PORTAL/servlet/RequestManager">

        <span class="tableHeading"> Security Log</span><br>

        <table class="summary" width="100%" height="80%" border="0" cellspacing="0" cellpadding="0">

            <tr class="heading">

                <th NOWRAP width="25%">
                    Operator
                </th>
                <th width="25%">
                    DateTime
                </th>
                <th width="25%">
                    Message
                </th>
                <th width="25%">
                    Type
                </th>
            </tr>

            <tr>

                <td colspan="7" height="99%">

                    <span class="scrollableContent">

                        <table class="summary" width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" >
    <%
                            if (securityLogs != null){

                                for (int i = 0; i < securityLogs.length; i++){

                                    String operatorName = securityLogs[i].getOperatorName();
                                    String dateTime = securityLogs[i].getMaintDateTime().getFormattedDateTime();
                                    String message = securityLogs[i].getMessage();
                                    String type = securityLogs[i].getType();
    %>
                                    <tr class="default">

                                        <td width="25%">
                                            <font size="2"><%= operatorName %></font>
                                        </td>
                                        <td width="25%">
                                            <font size="2"><%= dateTime %></font>
                                        </td>
                                        <td width="25%">
                                            <font size="2"><%= message %></font>
                                        </td>
                                        <td width="25%">
                                            <font size="2"><%= type %></font>
                                        </td>
                                    </tr>
    <%
                                }
                            }
    %>
                        <tr class="filler">
                            <td colspan="4">
                                &nbsp;
                            </td>
                        </tr>
                        </table>

                    </span>

                </td>
            </tr>

        </table>

        <input type="hidden" name="transaction" value="">
        <input type="hidden" name="action"      value="">        

    </form>

    </body>

</html>



