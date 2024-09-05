<%@ page import="java.util.List, 
                 java.util.ArrayList, 
                 org.hibernate.Session, 
                 edit.services.db.hibernate.SessionHelper,
                 contract.ChangeHistory"%>

<%!
    /**
     * Returns the specified number of PKs in asc order. This
     * finder is most likely to be used for performance tests.
     * @return
     * @param maxResults the max number of pks to return
     */
    public static List<Long> findSeparate_PKs(int maxResults)
    {
        List<Long> pks = null;
        
        String hql = "select changeHistory.ChangeHistoryPK " +
                    " from ChangeHistory changeHistory " +
                    " order by changeHistory.ChangeHistoryPK asc";
        
        Session separateSession = null;
        
        try
        {
            separateSession = SessionHelper.getSeparateSession(SessionHelper.EDITSOLUTIONS);
            
            pks = SessionHelper.executeHQL(separateSession, hql, null, maxResults);
        }
        finally
        {
            if (separateSession != null)
            {
                separateSession.close();
            }
        }
        
        
        return pks;
    }    
%>

<%
/**
 * Over time, this will represent a repository of performance tests to check
 * such things as network latency.
 */
class PerformanceTest
{
    /**
     * Many tests involve iterating through some unit of work.
     * This tracks the number of units iterated through.
     */
    private int unitCount;
    
    /**
     * A meaningful description to represent the test itself. e.g. "Network Latency Test / Loading CodeTables".
     */
    private String testDescription;
    
    /**
     * The time in millis that the test began.
     */
    private double startTimeMillis;
    
    /**
     * The time in millis that the test stopped.
     */
    private double stopTimeMillis;
    
    public PerformanceTest()
    {
    }
    
    /**
     * Captures the start time. Closes any SessionHelper sessions.
     */
    private void startTest()
    {
        SessionHelper.closeSessions();
        
        this.setStartTimeMillis(System.currentTimeMillis());        
    }
    
    /**
     * Captures the stopTime. Closes any SessionHelper sessions.
     */
    private void stopTest()
    {
        this.setStopTimeMillis(System.currentTimeMillis());
        
        SessionHelper.closeSessions();
    }

    /**
     * The difference in millis of the test's startTime and stopTime.
     * @return
     */
    public double getTotalTimeMillis()
    {
        double totalTimeMillis = getStopTimeMillis() - getStartTimeMillis();
        
        return totalTimeMillis;
    }
    
    /**
     * Effectively divides the total time by the total number of units of work.
     * @return
     */
    public double getUnitTimeMillis()
    {
        double unitTime = (getTotalTimeMillis() / getUnitCount());
        
        return unitTime;
    }

    /**
     * @see PerformanceTest#unitCount
     * @param numberOfUnits
     */
    public void setUnitCount(int numberOfUnits)
    {
        this.unitCount = numberOfUnits;
    }

    /**
     * @see PerformanceTest#unitCount
     * @return
     */
    public int getUnitCount()
    {
        return unitCount;
    }

    /**
     * @see PerformanceTest#testDescription
     * @param testName
     */
    public void setTestDescription(String testName)
    {
        this.testDescription = testName;
    }

    /**
     * @see PerformanceTest#testDescription
     * @return
     */
    public String getTestDescription()
    {
        return testDescription;
    }
    
    /**
     * Loads each ChangeHistory over the network one-by-one. ChangeHistory
     * is a pretty-good choice since there are over often over 1000 entries and
     * it's a table that will always be there.
     */
    public void testNetworkLatencyChangeHistory()
    {
        setTestDescription("Load ChangeHistory One-by-One Over Network");
        
        List<Long> changeHistoryPKs = findSeparate_PKs(1000); // up to 1000
        
        startTest();
        
        for (Long changeHistoryPK:changeHistoryPKs)
        {
            ChangeHistory changeHistory = (ChangeHistory) SessionHelper.get(ChangeHistory.class, changeHistoryPK, SessionHelper.EDITSOLUTIONS);
            
            SessionHelper.clearSession(SessionHelper.EDITSOLUTIONS);
            
            updateUnitCount();
        }
        
        stopTest();
    }
    
    public void testNetworkLatencyEDITTrxHistoryComposer()
    {
        setTestDescription("Compose EDITTrxHistory");
        
        startTest();
        
        List voInclusionList = new ArrayList();
        
        voInclusionList.add(edit.common.vo.EDITTrxVO.class);
        voInclusionList.add(edit.common.vo.ClientSetupVO.class);
        voInclusionList.add(edit.common.vo.ContractSetupVO.class);
        voInclusionList.add(edit.common.vo.SegmentVO.class);
        voInclusionList.add(edit.common.vo.InvestmentHistoryVO.class);
        voInclusionList.add(edit.common.vo.BucketHistoryVO.class);
        voInclusionList.add(edit.common.vo.BucketChargeHistoryVO.class);
        voInclusionList.add(edit.common.vo.CommissionHistoryVO.class);
        voInclusionList.add(edit.common.vo.WithholdingHistoryVO.class);
        
        for (int i = 0; i < 1000; i++)
        {
            new event.dm.composer.EDITTrxHistoryComposer(voInclusionList).compose(1210691042968L);
            
            updateUnitCount();
        }
        
        stopTest();
    }
    
    /**
     * Resets all the state variables of the test.
     */
    public void reset()
    {
        setStartTimeMillis(0.0D);
        
        setStopTimeMillis(0.0D);
        
        setTestDescription(null);
        
        setUnitCount(0);
    }
    
    /**
     * Updates the unitCount by one.
     */
    private void updateUnitCount()
    {
        int currentUnitCount = getUnitCount();
        
        currentUnitCount++;
        
        setUnitCount(currentUnitCount);
    }

    /**
     * @see #startTimeMillis
     * @param startTimeMillis
     */
    public void setStartTimeMillis(double startTimeMillis)
    {
        this.startTimeMillis = startTimeMillis;
    }

    /**
     * @see #startTimeMillis
     * @return
     */
    public double getStartTimeMillis()
    {
        return startTimeMillis;
    }

    /**
     * @see #stopTimeMillis
     * @param stopTimeMillis
     */
    public void setStopTimeMillis(double stopTimeMillis)
    {
        this.stopTimeMillis = stopTimeMillis;
    }

    /**
     * @see #stopTimeMillis
     * @return
     */
    public double getStopTimeMillis()
    {
        return stopTimeMillis;
    }
}

    out.println("Start Test" + "<br>");

    PerformanceTest performanceTest = new PerformanceTest();

    performanceTest.testNetworkLatencyEDITTrxHistoryComposer();
    
    out.println("Total time millis: " + performanceTest.getTotalTimeMillis() + "<br>");
        
    out.println("Unit time millis: " + performanceTest.getUnitTimeMillis() + "<br>");
    
    out.println("End Test" + "<br>");
%>