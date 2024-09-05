<%@page import="edit.common.exceptions.EDITCaseException"%>
<%@page import="engine.sp.*"%>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="fission.utility.*, java.sql.*, edit.services.db.hibernate.*, java.io.*, java.sql.*, edit.services.db.*, edit.common.*"%>
<%@ page import="java.util.*, edit.services.db.hibernate.*, event.*, agent.*, contract.*"%>
 <%@page import="fission.utility.*, java.sql.*, edit.services.db.hibernate.*, java.io.*, edit.services.db.*, edit.common.*,
                 edit.common.vo.*,
                 java.util.*,
                 event.dm.dao.*,
                 contract.*,
                 engine.*" %>
<%
    String startConversion = Util.initString(request.getParameter("startConversion"), "false");

    if (startConversion.equals("true"))
    {
        long startTime = System.currentTimeMillis();
        Segment segment = null;
        long stopTime = 0;
         String stateCT = "*";
    
        try
        {
            List segmentPKs = getSegmentPKs();
            
            int size = segmentPKs.size();
            
            for (int i = 0; i < size; i++)
            {
              SessionHelper.clearSessions();
              
              SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
            
              Long currentSegmentPK = (Long) segmentPKs.get(i);
              
              System.out.println("Processing [" + (i + 1) + " of " + size + "] Segments - PK [" + currentSegmentPK + "]");

              segment = Segment.findByPK(currentSegmentPK);
              
              
              if(segment.getGroupPlan() == null)
              {
                 
                 String areaGroup = null;
                 long productStructurePK = segment.getProductStructureFK().longValue();
                 String areaCT = "*";
                 EDITDate areaDate = segment.getEffectiveDate();
                 String qualifierCT = "*";
                 String areaField = null;
                 String relationshipToEmployeeCT = null;
                 String batchContractSetupPKString = null;
                 String groupContractGroupPKString = null;
                 
                 if(segment.getSegmentFK() == null)
                   {
                       areaGroup = "CASEBASE";
                       batchContractSetupPKString =  Util.initString(segment.getBatchContractSetup().getBatchContractSetupPK().toString(),null);
                       groupContractGroupPKString =  Util.initString(segment.getContractGroup().getContractGroupPK().toString(),null); 
                
                   }
                   if(segment.getSegmentFK() != null)
                   {
                       areaGroup = "CASERIDERS";
                       batchContractSetupPKString =  Util.initString(segment.getSegment().getBatchContractSetup().getBatchContractSetupPK().toString(),null);
                       groupContractGroupPKString =  Util.initString(segment.getSegment().getContractGroup().getContractGroupPK().toString(),null); 
                   }  
                
                Map<String, Object> results = new HashMap<String, Object>();
                try {
                    new Getareatable().runAreaTableLookup(productStructurePK, areaCT, areaGroup, areaDate, qualifierCT, areaField, batchContractSetupPKString, groupContractGroupPKString, relationshipToEmployeeCT, results);
                } catch (SPException ex) {
                    throw new EDITCaseException(ex.getMessage());
                }
                if (!results.isEmpty())
                {
                    if(results.containsKey("GROUPPLAN"))
                    {
                        segment.setGroupPlan(results.get("GROUPPLAN").toString());
                    }
                }
               }
               SessionHelper.saveOrUpdate(segment, SessionHelper.EDITSOLUTIONS);
               SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
             
            }
            showSuccess(out, "Update of groupPlan from caseProductunderWriting/Areatable to segment is Complete");
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
       
            System.out.println(e);
            
            e.printStackTrace();

            showError(out, e);
        }
        finally
        {
            SessionHelper.clearSessions();
        
            stopTime = System.currentTimeMillis();
            
            double totalTime = 1.0 * (stopTime - startTime) / 1000.0;
            
            System.out.println("Total Time: " + totalTime + " seconds.");
        }
    }
%>
<%!
    

  /**
   * Get all SegmentPK's from Editsolutions.
   */
  private List getSegmentPKs() throws Exception
  {
    String hql = " select distinct (segment.SegmentPK)" +
                " from Segment segment";

    List segmentPKs = null; 

    try
    {
      segmentPKs = SessionHelper.executeHQL(hql, null, SessionHelper.EDITSOLUTIONS);
    }
    catch (Exception e)
    {
      throw e;
    }
    finally
    {

    }
    
    return segmentPKs;
  }

    /**
     * Convenience method - Displays any successes from the conversion.
     */
    private void showSuccess(Writer out, String message) throws Exception
    {
        out.write("<span style='background-color:lightskyblue; width:100%'>");

        out.write("<hr>");

        out.write("The conversion was successful in completing the following:<br><br>");

        out.write("<font face='' color='blue'>");

        out.write(message);

        out.write("</font>");

        out.write("<hr>");

        out.write("</span>");
    }

    /**
     * Convenience method - Displays any errors from the conversion.
     */
    private void showError(Writer out, Exception e) throws Exception
    {
        out.write("<span style='background-color:yellow; width:100%'>");

        out.write("<hr>");

        out.write("The conversion was aborted for the following reason(s):<br><br>");

        out.write("<font face='' color='red'>");
     
        e.printStackTrace(new java.io.PrintWriter(out));
        
        out.write("</font>");

        out.write("<hr>");

        out.write("</span>");
    }

    /**
     * Convenience method to display conversion status.
     */
    private void attempting(Writer out, String message) throws Exception
    {
        out.write("<font face='' color='blue'>");

        out.write("Attempting...<br>");

        out.write("</font>");

        out.write(message);

        out.write("<br><br>");
    }
%>
<%-- The taglib directive below imports the JSTL library. If you uncomment it,
     you must also add the JSTL library to the project. The Add Library...
     action on Libraries node in Projects view can be used to add the JSTL 1.1
     library.--%>
<%-- <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
        <title>JSP Page</title>
    </head>
    <body><h1>Copy GroupPlan from caseProductUnderWriting/AreaTable To Segment Table</h1>
    
<form name="theForm" action="">

<p>
  Copy GroupPlan details found in caseProductUnderWriting  to segment Table with appropriate Values.
  
  <br><br>
</p>
<p>
  In order to map  GroupPlan details to its corresponding segment table, the 
  following approach was used:
</p>
<p>
 1. Retrive defined GroupPlan's from caseProductUnderwriting Table to its respective segment's .
</p>
<p>
 2. If GroupPlan is not Defined in CaseProductUnderwriting Table retrive from AreaTable to Segment's GroupPlan.
</p>

         <input type="submit" value="Start Mapping"
                                   name="btnStartConversion"
                                   onclick="theForm.startConversion.value='true'"/>            
            <input type="hidden" name="startConversion" value="false"/>
        </form></body>
</html>