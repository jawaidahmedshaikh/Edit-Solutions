<%@ page import="agent.*" %>
<%@ page import="edit.services.db.hibernate.*" %>
<%@ page import="java.util.*" %>
<%@ page import="fission.utility.*" %>
<%@ page import="java.math.*" %>
<%@ page import="java.io.*" %>
<%
    String validateBoundaries = Util.initString(request.getParameter("validate" + ValidateHierarchy.REPORT_BOUNDARIES), "off");

    String validateLevels = Util.initString(request.getParameter("validate" + ValidateHierarchy.REPORT_LEVELS), "off");

    String validateAssociations = Util.initString(request.getParameter("validate" + ValidateHierarchy.REPORT_ASSOCIATIONS), "off");

    String validateAll = Util.initString(request.getParameter("validateAll"), "off");

    ValidateHierarchy validate = new ValidateHierarchy();

    out.println("<html>");

    out.println("<head><title>Validate Hierarchy Report</title>\n" +
            "\n" +
            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n" +
            "<meta http-equiv=\"Cache-Control\" content=\"no-store\">\n" +
            "<meta http-equiv=\"Pragma\" content=\"no-cache\">\n" +
            "<meta http-equiv=\"Expires\" content=\"0\">\n" +
            "\n" +
            "<link href=\"/PORTAL/common/css/EDITSolutions.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
            "\n" +
            "</head>");

    out.println("<body  class='mainTheme'>");

    if (validateBoundaries.equals("on"))
    {
        generateReport(validate, ValidateHierarchy.REPORT_BOUNDARIES, out);
    }

    if (validateLevels.equals("on"))
    {
        generateReport(validate, ValidateHierarchy.REPORT_LEVELS, out);
    }

    if (validateAssociations.equals("on"))
    {
        generateReport(validate, ValidateHierarchy.REPORT_ASSOCIATIONS, out);
    }

    if (validateAll.equals("on"))
    {
        generateReport(validate, ValidateHierarchy.REPORT_BOUNDARIES, out);

        generateReport(validate, ValidateHierarchy.REPORT_LEVELS, out);

        generateReport(validate, ValidateHierarchy.REPORT_ASSOCIATIONS, out);
    }

    out.println("<table width='100%'><tr><td align='right'><input type='button' value='close' onClick='window.close()'></td></tr></table>");

    out.println("</body></html>");
%>
<%!
    private void generateReport(ValidateHierarchy validate, Integer reportId, JspWriter out) throws IOException
    {
        try
        {
            ValidateHierarchyReport hierarchyReport = validate.run(reportId);

            String reportName = hierarchyReport.getReportName();

            String reportDescription = hierarchyReport.getReportDescription();

            String hasErrors = (hierarchyReport.getHasErrors()) ? "(<font color='red'> <b>Errors</b> </font>)" : "(<font color='green'> <b>O.K.</b> </font>)";

            out.println("<font size='4'>" + reportName + " " + hasErrors + ":</font><br>");

            out.println("<table width='100%' border='0' cellspacing='0' cellpadding='3' class='formData'>");

            out.println("<tr bgColor='#D3D3D3'><td colspan='6'><i>" + reportDescription + "</i></td></tr>");

            out.println("<tr>");

            out.println("<td><u>Message</u></td>");
            out.println("<td><u>Agent #</u></td>");
            out.println("<td><u>Level</u></td>");
            out.println("<td><u>Left Boundary</u></td>");
            out.println("<td><u>Right Boundary</u></td>");
            out.println("<td><u>Primary Key</u></td>");

            out.println("</tr>");

            List reportEntries = hierarchyReport.getReportEntries();

            for (Iterator iterator = reportEntries.iterator(); iterator.hasNext();)
            {
                out.println("<tr>");

                ValidateHierarchyReportEntry reportEntry = (ValidateHierarchyReportEntry) iterator.next();

                String message = reportEntry.getMessage();

                String agentNumber = "&nbsp;";

                String level = "";

                String leftBoundary = "";

                String rightBoundary = "";

                String pk = "";

                PlacedAgent placedAgent = reportEntry.getPlacedAgent();

                level = String.valueOf(placedAgent.getHierarchyLevel());

                leftBoundary = String.valueOf(placedAgent.getLeftBoundary());

                rightBoundary = String.valueOf(placedAgent.getRightBoundary());

                pk = String.valueOf(placedAgent.getPlacedAgentPK());

                AgentContract agentContract = placedAgent.getAgentContract();

                if (agentContract != null)
                {
                    Agent agent = agentContract.getAgent();

                    agentNumber = agent.getAgentNumber();
                }

                out.println("<td>" + message + "</td>");
                out.println("<td>" + agentNumber + "</td>");
                out.println("<td>" + level + "</td>");
                out.println("<td>" + Util.formatDecimal("###,###,##0", new BigDecimal(leftBoundary)) + "</td>");
                out.println("<td>" + Util.formatDecimal("###,###,##0", new BigDecimal(rightBoundary)) + "</td>");
                out.println("<td>" + pk + "</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<br><br>");
        }
        catch (Exception e)
        {
            out.println(e);

            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
%>