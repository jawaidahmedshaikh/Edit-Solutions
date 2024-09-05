<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/xml;charset=windows-1252"%>

<%@ page import="edit.services.command.*,
                org.dom4j.*, 
                edit.services.db.hibernate.*" %>

<!--
In the ansense of SOAP services for this iteration, we need to resonse to client requests outside
of the Transaction/Action of our JSPs.
    
This dispatcher expects to receive XML Requests in the form as defined by the
SEGRequest class.
-->
<%  
    try
    {
        String segRequestVOAsXML = request.getParameter("segRequestVO");

        SEGRequest segRequest = new SEGRequest(segRequestVOAsXML);
    
        SEGRequestDispatcher dispatcher = new SEGRequestDispatcher(segRequest);
    
        Document responseDocument = dispatcher.dispatch();
    
        out.print(responseDocument.getRootElement().asXML());
    }
    finally
    {
        SessionHelper.clearSessions();

        SessionHelper.closeSessions();
        
        SessionHelper.clearThreadLocals();
    }
%>






