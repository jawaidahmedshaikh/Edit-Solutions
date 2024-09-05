<%@ page contentType="text/plain" %>

<%
    String responseMessage = (String) request.getAttribute("responseMessage");

    out.print(responseMessage);
%>