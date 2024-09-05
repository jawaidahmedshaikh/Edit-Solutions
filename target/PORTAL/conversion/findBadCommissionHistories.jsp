<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="fission.utility.*, java.sql.*, edit.services.db.hibernate.*, java.io.*, java.sql.*, edit.services.db.*, edit.common.*"%>
<%@ page import="java.util.*, edit.services.db.hibernate.*, event.*, agent.*, contract.*"%>
<%
    BufferedWriter report = getWriter(out);
    
    String startConversion = Util.initString(request.getParameter("startConversion"), "false");

    if (startConversion.equals("true"))
    {
        long startTime = System.currentTimeMillis();
        
        long stopTime = 0;
    
        try
        {
            List candidateCommissionHistoryPKs = getCandidateCommissionHistoryPKs();
            
            int badCount = 0;
            
            int size = candidateCommissionHistoryPKs.size();
            
            for (int i = 0; i < size; i++)
            {
              SessionHelper.clearSessions();

              Long currentCommissionHistoryPK = (Long) candidateCommissionHistoryPKs.get(i);
              
              CommissionHistory currentCommissionHistory = getCommissionHistory(currentCommissionHistoryPK);

              System.out.println("Processing [" + i + " of " + size + "] CommissionHistories. PK - [" + currentCommissionHistoryPK + "]");
              
              if (currentCommissionHistory != null)
              {
                boolean validSourcePlacedAgent = validateSourcePlacedAgent(currentCommissionHistory);
                
                if (! validSourcePlacedAgent)
                {
                  badCount++;
                  
                  report.write("<br>" + badCount + ") Bad CommissionHistory.SOURCE_PLACED_AGENT_FK! [CommissionHistoryPK=" + currentCommissionHistoryPK + "] [ContractNumber=" + currentCommissionHistory.getEDITTrxHistory().getEDITTrx().getClientSetup().getContractSetup().getSegment().getContractNumber() + "]\n");
                  
                  report.flush();
                }
                
                boolean validAgentSnapshot = validateAgentSnapshot(currentCommissionHistory);
                
                if (! validAgentSnapshot)
                {
                  badCount++;
                  
                  report.write("<br>" + badCount + ") Bad CommissionHistory.PLACED_AGENT_FK! [CommissionHistoryPK=" + currentCommissionHistoryPK + "] [ContractNumber=" + currentCommissionHistory.getEDITTrxHistory().getEDITTrx().getClientSetup().getContractSetup().getSegment().getContractNumber() + "]\n");
                  
                  report.flush();
                }
              }
              else
              {
                  badCount++;
                  
                  report.write("<br>" + badCount + ") CommissionHistory Join Failed! [CommissionHistoryPK=" + currentCommissionHistoryPK + "] [ContractNumber=Use CommissionHistoryPK]\n");
                  
                  report.flush();                
              }
            }
        }
        catch (Exception e)
        {
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);
       
            System.out.println(e);
            
            e.printStackTrace();
        }
        finally
        {
            SessionHelper.clearSessions();
            
            if (report != null) report.close();
        
            stopTime = System.currentTimeMillis();
            
            double totalTime = 1.0 * (stopTime - startTime) / 1000.0;
            
            System.out.println("Total Time: " + totalTime + " seconds.");
        }
    }
%>
<%!

  private BufferedWriter getWriter(JspWriter writer) throws Exception
  {
    java.io.BufferedWriter bw = new BufferedWriter(writer);
    
    return bw;
  }
  
  /**
   * Every CommissionHistory with a SourcePlacedAgentFK MUST have a corresponding AgentSnapshot that
   * matches on AgentSnapshot.PlacedAgentFK = CommissionHistory.PlacedAgentFK.
   */
  private boolean validateAgentSnapshot(CommissionHistory commissionHistory) throws Exception
  {
      boolean validAgentSnapshot = false;
      
      Segment segment = commissionHistory.getEDITTrxHistory().getEDITTrx().getClientSetup().getContractSetup().getSegment();
      
      List agentHierarchies = new ArrayList(segment.getAgentHierarchies());
      
      for (int i = 0; i < agentHierarchies.size(); i++)
      {
        AgentHierarchy currentAgentHierarcy = (AgentHierarchy) agentHierarchies.get(i);
      
        List agentSnapshots = new ArrayList(currentAgentHierarcy.getAgentSnapshots());
        
        for (int j = 0; j < agentSnapshots.size(); j++)
        {
          AgentSnapshot currentAgentSnapshot = (AgentSnapshot) agentSnapshots.get(j);
        
          long commissionHistory_PlacedAgentFK = commissionHistory.getPlacedAgent().getPlacedAgentPK().longValue();
        
          long agentSnapshot_PlacedAgentFK = currentAgentSnapshot.getPlacedAgent().getPlacedAgentPK().longValue();
        
          if (commissionHistory_PlacedAgentFK == agentSnapshot_PlacedAgentFK)     
          {
            validAgentSnapshot = true;
           
            break;
          }
        }
      }
      
      return validAgentSnapshot;    
  
  }
  
  /**
   * Adds join/fetch to the specified CommissionHistoryPK.
   */ 
  private CommissionHistory getCommissionHistory(Long commissionHistoryPK) throws Exception
  {
      String hql = " select commissionHistory" +
                  " from CommissionHistory commissionHistory" +
                  " join fetch commissionHistory.EDITTrxHistory editTrxHistory" +
                  " join fetch editTrxHistory.EDITTrx editTrx" +
                  " join fetch editTrx.ClientSetup clientSetup" +
                  " join fetch clientSetup.ContractSetup contractSetup" +
                  " join fetch contractSetup.Segment segment" +
                  " join fetch segment.AgentHierarchies agentHierarchy" +
                  " join fetch agentHierarchy.AgentSnapshots" +
                  " where commissionHistory.CommissionHistoryPK = :commissionHistoryPK";
                  
      Map params = new HashMap();
      
      params.put("commissionHistoryPK", commissionHistoryPK);
      
      List results = SessionHelper.executeHQL(hql, params, SessionHelper.EDITSOLUTIONS);
      
      CommissionHistory commissionHistory = null;
      
      if (! results.isEmpty())
      {
        commissionHistory = (CommissionHistory) results.get(0);  
      }
  
      return commissionHistory;  
    }

  /**
   * Verifies that the CommissionHistory.SourcePlacedAgentFK actually refers to an AgentSnapshot that is the writing agent.
   */
   private boolean validateSourcePlacedAgent(CommissionHistory commissionHistory) throws Exception
   {
      boolean validSourcePlacedAgent = false;
      
      Segment segment = commissionHistory.getEDITTrxHistory().getEDITTrx().getClientSetup().getContractSetup().getSegment();
      
      List agentHierarchies = new ArrayList(segment.getAgentHierarchies());
      
      for (int i = 0; i < agentHierarchies.size(); i++)
      {
        AgentHierarchy currentAgentHierarcy = (AgentHierarchy) agentHierarchies.get(i);
      
        AgentSnapshot writingAgent = currentAgentHierarcy.getLowestLevelAgent();
        
        long commissionHistory_SourcePlacedAgentFK = commissionHistory.getSourcePlacedAgent().getPlacedAgentPK().longValue();
        
        long writingAgent_PlacedAgentFK = writingAgent.getPlacedAgent().getPlacedAgentPK().longValue();
        
        if (commissionHistory_SourcePlacedAgentFK == writingAgent_PlacedAgentFK)     
        {
           validSourcePlacedAgent = true;
           
           break;
        }
      }
      
      return validSourcePlacedAgent;
   }

  /**
   * CommissionHistoryPKs that have some association
   * with an AgentSnapshot. 
   */
  private List getCandidateCommissionHistoryPKs() throws Exception
  {
    String hql = " select distinct (commissionHistory.CommissionHistoryPK)" +
                " from Segment segment" +
                " inner join segment.ContractSetups contractSetup" +
                " inner join contractSetup.ClientSetups clientSetup" +
                " inner join clientSetup.EDITTrxs editTrx" +
                " inner join editTrx.EDITTrxHistories editTrxHistory" +
                " inner join editTrxHistory.CommissionHistories commissionHistory" +
                " inner join commissionHistory.SourcePlacedAgent placedAgent";
                
    List commissionHistoryPKs = null;
  
    try
    {
      commissionHistoryPKs = SessionHelper.executeHQL(hql, null, SessionHelper.EDITSOLUTIONS);
    }
    catch (Exception e)
    {
      throw e;
    }
    finally
    {

    }
    
    return commissionHistoryPKs;          
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
    <body><h1>Find Bad Commission Histories</h1><p>
            <br></br>All CommissionHistories are analyzed to verify two criteria:

            <br></br>1. Verify that the CommissionHistory.SourcePlacedAgentFK actually refers to an AgentSnapshot that is a Writing Agent.
             
            <br></br>2. Verify that the CommissionHistory with a SourcePlacedAgentFK matches a corresponding AgentSnapshot on AgentSnapshot.PlacedAgentFK = CommissionHistory.PlacedAgentFK.
            
            <br></br> Any CommissionHistory that does not meet [either] criteria will be listed on this web page. There are (likely) thousands.
            
            <hr></hr>
<form name="theForm" action="">
                            <input type="submit" value="Start Report"
                                   name="btnStartConversion"
                                   onclick="theForm.startConversion.value='true'"/>            
            <input type="hidden" name="startConversion" value="false"/>
        </form></body>
</html>