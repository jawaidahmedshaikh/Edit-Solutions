<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="edit.portal.widgettoolkit.TableModel" %>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="Cache-Control" content="no-store">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<html>
    <head>
        <link href="/PORTAL/common/css/EDITSolutions.css" rel="stylesheet" type="text/css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body class="dialog" style="border-style:solid; border-width:1;">
        <jsp:include page="/common/jsp/widget/tableWidget.jsp" flush="true">
            <jsp:param name="tableId" value="ContactInformationTableModel"/>
            <jsp:param name="tableWidth" value="100"/>
            <jsp:param name="tableHeight" value="100"/>
            <jsp:param name="multipleRowSelect" value="false"/>
            <jsp:param name="singleOrDoubleClick" value="<%= TableModel.PROPERTY_SINGLE_OR_DOUBLE_CLICK_VALUE_SINGLE %>"/>
        </jsp:include>
        <div align="right">
            <input type="submit" value="Close" onclick="window.close()"/>
        </div>
    </body>
</html>
