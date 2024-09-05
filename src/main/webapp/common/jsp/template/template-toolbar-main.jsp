<jsp:include page="/common/jsp/template/template-core.jsp" flush="true"/>
<%
    // The template needs the 'toolbar' and 'main' pages to render.
    String toolbar = (String) request.getAttribute("toolbar");

    String main = (String) request.getAttribute("main");
%>

<script language="javascript" type="text/javascript">
    window.name = "template-toolbar-main";
</script>

<body class="mainTheme" onLoad="onBodyLoad()" scroll="auto"
      style="overflow:auto; width:100%; height:100%; padding:5px; margin:0px; border-width:0px;">
<form name="templateForm" action="/PORTAL/servlet/RequestManager" method="post"
      enctype="application/x-www-form-urlencoded">
<%--    <table cellpadding="0" cellspacing="0"--%>
<%--           style="width:100%; height:100%; border:black; border-width:0px; border-style:solid;padding:0px">--%>
<%--        <tr>--%>
<%--            <td valign="top" style="height:75px">--%>
                <jsp:include page="<%= toolbar %>" flush="true"/>
<%--            </td>--%>
<%--        </tr>--%>
<%--        </table>--%>
<%--        <valign="top">--%>
<%--        <tr>--%>
<%--            <td valign="top" style="width:100%; height=100%">--%>
<%--            <td valign="top">--%>
                <jsp:include page="<%= main %>" flush="true"/>
<%--            </td>--%>
<%--        </tr>--%>
<%--    </table>--%>
    <input type="hidden" name="transaction" value=""></input>
    <input type="hidden" name="action" value=""></input>
</form>

</body>
</html>