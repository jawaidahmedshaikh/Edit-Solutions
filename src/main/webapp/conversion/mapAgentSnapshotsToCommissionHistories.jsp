<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="fission.utility.*, java.sql.*, edit.services.db.hibernate.*, java.io.*, java.sql.*, edit.services.db.*, edit.common.*"%>
<%@ page import="java.util.*, edit.services.db.hibernate.*, event.*, agent.*, contract.*"%>
<%
    String startConversion = Util.initString(request.getParameter("startConversion"), "false");

    if (startConversion.equals("true"))
    {
        long startTime = System.currentTimeMillis();
        
        long stopTime = 0;
    
        try
        {
            List candidateSegmentPKs = getCandidateSegmentPKs();
            
            int size = candidateSegmentPKs.size();
            
            for (int i = 0; i < size; i++)
            {
              SessionHelper.clearSessions();
              
              SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
            
              Long currentSegmentPK = (Long) candidateSegmentPKs.get(i);
              
              System.out.println("Processing [" + (i + 1) + " of " + size + "] Segments - PK [" + currentSegmentPK + "]");

              Segment currentSegment = getSegment(currentSegmentPK);
              
              List agentHierarchies = new ArrayList(currentSegment.getAgentHierarchies());
              
              for (int j = 0; j < agentHierarchies.size(); j++)
              {
                AgentHierarchy currentAgentHierarchy = (AgentHierarchy) agentHierarchies.get(j);

                List candidateCommissionHistories = getCandidateCommissionHistories(currentAgentHierarchy);
                
                mapAgentSnapshotToCommissionHistory(currentAgentHierarchy, candidateCommissionHistories);
              }
              
              SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
            }
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
     * The writing agent of the specified AgentHierarcy is associated to a PlacedAgent.
     * This PlacedAgent is the same as the CommissionHistory.SourcePlacedAgent. The set of
     * CommissionHistories which can be associated to the AgentSnapshots of the specified
     * AgentHierarchy are retrieved.
     */
    private List getCandidateCommissionHistories(AgentHierarchy agentHierarchy) throws Exception
    {
        Segment segment = agentHierarchy.getSegment();
    
        AgentSnapshot writingAgent = agentHierarchy.getLowestLevelAgent();
        
        PlacedAgent sourcePlacedAgent = writingAgent.getPlacedAgent();
        
        String hql = " select commissionHistory" +
              " from CommissionHistory commissionHistory" +
              " join commissionHistory.EDITTrxHistory editTrxHistory" +
              " join editTrxHistory.EDITTrx editTrx" +
              " join editTrx.ClientSetup clientSetup" +
              " join clientSetup.ContractSetup contractSetup" +
              " where commissionHistory.SourcePlacedAgent = :sourcePlacedAgent" +
              " and contractSetup.Segment = :segment";
                    
        Map params = new HashMap();
        
        params.put("sourcePlacedAgent", sourcePlacedAgent);
        
        params.put("segment", segment);
        
        List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);    
        
        return results;
    }

    /**
    * Join fetches the Segment.AgentHierarchy.AgentSnapshot chain.
    */
    private Segment getSegment(Long segmentPK) throws Exception
    {
      String hql = " select segment " +
                  " from Segment segment" +
                  " join fetch segment.AgentHierarchies agentHierarchy" +
                  " join fetch agentHierarchy.AgentSnapshots agentSnapshot" +
                  " where segment.SegmentPK = :segmentPK";
                  
      Map params = new HashMap();
      
      params.put("segmentPK", segmentPK);
                    
      List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);
      
      Segment segment = (Segment) results.get(0);
      
      return segment;
    }


    /**
     * The specified CommissionHistories must have an association to the specified AgentHierarchy and its
     * Snapshots. Each CommissionHistory.SourcePlacedAgent is match to a corresponding AgentSnapshot.PlacedAgentFK.
     */
    private void mapAgentSnapshotToCommissionHistory(AgentHierarchy agentHierarchy, List commissionHistories) throws Exception
    {            
        try
        {
          List agentSnapshots = new ArrayList(agentHierarchy.getAgentSnapshots());
          
          for (int i = 0; i < commissionHistories.size(); i++)
          {
            CommissionHistory currentCommissionHistory = (CommissionHistory) commissionHistories.get(i);
          
            long commissionHistory_PlacedAgentFK = currentCommissionHistory.getPlacedAgent().getPlacedAgentPK().longValue();
            
            boolean mapSuccessful = false;
            
            for (int j = 0; j < agentSnapshots.size(); j++)
            {
              AgentSnapshot currentAgentSnapshot = (AgentSnapshot) agentSnapshots.get(j);
            
              long agentSnapshot_PlacedAgentFK = currentAgentSnapshot.getPlacedAgent().getPlacedAgentPK().longValue();
              
              if (commissionHistory_PlacedAgentFK == agentSnapshot_PlacedAgentFK)
              {
                currentAgentSnapshot.add(currentCommissionHistory);
                
                mapSuccessful = true;
                
                break;
              }
            }
            
            if (! mapSuccessful)
            {
              //throw new Exception("Unable to Map CommissionHistory [" + currentCommissionHistory.getCommissionHistoryPK() + "]"); 
            }
          }
        }
        catch (Exception e)
        {
          throw e;
        }
        finally
        {

        }
    }  

  /**
   * Segments with CommissionHistories that can be associated with an AgentSnapshot
   * must have CommissionHistories associated with an AgentSnapshot via the
   * CommissionHistory.PlacedAgentFK
   */
  private List getCandidateSegmentPKs() throws Exception
  {
    String hql = " select distinct (segment.SegmentPK)" +
                " from Segment segment" +
                " inner join segment.AgentHierarchies agentHierarchy" +
                " inner join agentHierarchy.AgentSnapshots";

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
    <body><h1>Map AgentSnapshot To CommissionHistory</h1>
    
<form name="theForm" action="">


<p>
  CommissionHistory.AgentSnapshotFK has been added to the CommissionHistory table to directly associate the AgentSnapshot to 
  it's CommissionHistory.
</p>

<p>
  In order to map a CommissionHistory to its corresponding AgentSnapshot, the 
  following approach was used:
</p>

<p>
1. A Segment and its AgentHierarchies are retrieved.
</p>
<p>
2. For each Segment.AgentHierarchy, the "writing agent" Snapshot is identified by
finding the AgentSnapshot with the greatest AgentSnapshot.HierarchyLevel value.
</p>
<p>
3. The writing agent's AgentSnapshot.PlacedAgentFK is identified and is used to 
  located all CommissionHistories (of the current Segment) whose CommissionHistory.SourcePlacedAgentFK = AgentSnapshot.PlacedAgentFK.
  
  <br><br>
  
  The CommissionHistories that are retrieved belong to the set of AgentSnapshots of the current AgentHierarchy and no other AgentHieararchy of the current Segment.
</p>
<p>
4. Match each retrieved CommissionHistory to its AgentSnapshot using AgentSnapshot.PlacedAgentFK = CommissionHistory.PlacedAgentFK.

</p>





                            <input type="submit" value="Start Mapping"
                                   name="btnStartConversion"
                                   onclick="theForm.startConversion.value='true'"/>            
            <input type="hidden" name="startConversion" value="false"/>
        </form></body>
</html>