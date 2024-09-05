<%-- 
    Document   : reindexDBs
    Created on : Dec 2, 2011, 12:49:50 PM
    Author     : gfrosti
--%>

<%@page import="edit.services.db.DBUtil"%>
<%@page import="edit.services.db.DBTable"%>
<%@page import="edit.services.db.ConnectionFactory"%>
<%@page import="edit.services.db.DBDatabase"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.List"%>
<%@page import="org.hibernate.Session"%>
<%@page import="edit.services.db.hibernate.SessionHelper"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>




<%!
    // Reindexes the Engine and Solutions databases.
%>

<%


    DBUtil.reindexDB(ConnectionFactory.EDITSOLUTIONS_POOL);
    
    DBUtil.reindexDB(ConnectionFactory.ENGINE_POOL);    
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Finished Reindexing!</h1>
    </body>
</html>
