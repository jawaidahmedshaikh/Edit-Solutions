<%-- 
    Document   : conversionAuditHome
    Created on : Dec 4, 2009, 3:37:50 PM
    Author     : sprasad
--%>

<%@page import="conversion.audit.*" %>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%
    String runReports = (String) request.getParameter("runReports");

    if (runReports != null && runReports.equals("Y"))
    {
        new ActiveCaseCountGenerator().generate();

        new TerminatedCaseCountGenerator().generate();

        new CaseNameGenerator().generate();

        new NameAddressRoleGenerator().generate();

        new ActiveMIFMembersGenerator().generate();

        new TotalBilledAmountByCaseGroupDueDateGenerator().generate();

        new ContractDetailGenerator().generate();

        new PremiumDetailGenerator().generate();

        new CommissionDetailGenerator().generate();

        System.out.println("Finished Running Audit Reports");
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Audit Home Page</title>
    </head>
    <body>
        <table>
            <tr>
                <td><a href="conversionAuditHome.jsp?runReports=Y">Generate Audit Reports</a></td>
            </tr>
        </table>
    </body>
</html>