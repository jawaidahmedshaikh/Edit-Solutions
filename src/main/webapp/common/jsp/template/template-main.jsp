<jsp:include page="/common/jsp/template/template-core.jsp" flush="true"/>


<%
    // The template needs the 'main' pages to render.    
    String main = (String) request.getAttribute("main");
%>

<body class="mainTheme" onLoad="onBodyLoad()" style="overflow:hidden; width:100%; height:100%; padding:5px; margin:0px; border-width:0px;">
<form name="templateForm" action="/PORTAL/servlet/RequestManager" method="post"
      enctype="application/x-www-form-urlencoded">
<%--    <table cellpadding="0" cellspacing="0"--%>
<%--           style="width:100%; height:100%; border:black; border-width:0px; border-style:solid;">--%>
<%--        <tr>--%>
<%--            <td>--%>
                <jsp:include page="<%= main %>" flush="true"/>
<%--            </td>--%>
<%--        </tr>--%>
<%--    </table>--%>
    <input type="hidden" name="transaction" value=""></input>
    <input type="hidden" name="action" value=""></input>
</form>

</body>
</html>