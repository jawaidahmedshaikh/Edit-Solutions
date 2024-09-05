package batch;

import java.util.concurrent.atomic.AtomicInteger;
import batch.business.Batch;
import edit.common.EDITDateTime;
import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import fission.utility.DateTimeUtil;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: May 20, 2005
 * Time: 3:18:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class BatchStat extends HibernateEntity implements Comparable 
{
	private static final long serialVersionUID = 1L;
	
	private long batchStatPK;
	private AtomicInteger successCount;
    private AtomicInteger failureCount;
    private AtomicInteger skipCount;
    private long startTime;
    private long stopTime;
    private String elementName;
    private String processName;
    private String batchMessage;
    private boolean jobCompleted;
    
    private String jobName;
    private String formattedStartTime;
    private String formattedStopTime;
    private String batchTime;
    private String finalBatchStatus;
    private boolean batchStatsLogged;

    public BatchStat()
    {
    	this.setBatchStatPK(0);
    	successCount = new AtomicInteger(0);
    	failureCount = new AtomicInteger(0);
    	skipCount = new AtomicInteger(0);
    }

    /**
     * Getter.
     * @return
     */
    public String getProcessName()
    {
        return processName;
    }

    /**
     * Setter.
     * @param successCount
     */
    synchronized private void setSuccessCount(int successCount)
    {
        this.successCount.set(successCount);
    }

    /**
     * Getter.
     * @return
     */
    public long getBatchStatPK()
    {
        return batchStatPK;
    }

    /**
     * Setter.
     * @param batchStatPK
     */
    private void setBatchStatPK(long batchStatPK)
    {
        this.batchStatPK = batchStatPK;
    }
    
    /**
     * Setter.
     * @param failureCount
     */
    synchronized private void setFailureCount(int failureCount)
    {
        this.failureCount.set(failureCount);
    }
    
    /**
     * Setter.
     * @param skipCount
     */
    synchronized private void setSkipCount(int skipCount)
    {
        this.skipCount.set(skipCount);
    }

    /**
     * Setter.
     * @param startTime
     */
    private void setStartTime(long startTime)
    {
        this.startTime = startTime;
        setFormattedStartTime(new EDITDateTime(startTime).getFormattedDateTime());
    }
    
    /**
     * Setter.
     * @param formattedStartTime
     */
    private void setFormattedStartTime(String formattedStartTime)
    {
        this.formattedStartTime = formattedStartTime;
    }

    /**
     * Setter.
     * @param stopTime
     */
    private void setStopTime(long stopTime)
    {
        this.stopTime = stopTime;
        setFormattedStopTime(new EDITDateTime(stopTime).getFormattedDateTime());

    }
    
    /**
     * Setter.
     * @param formattedStopTime
     */
    private void setFormattedStopTime(String formattedStopTime)
    {
        this.formattedStopTime = formattedStopTime;
    }
    
    /**
     * Setter.
     * @param batchMessage
     */
    public void setBatchMessage(String batchMessage)
    {
        this.batchMessage = batchMessage;
    }

    /**
     * Starts the timer for the batch process. Calls to the update...() methods permit benchmarking.
     */
    public void tagBatchStart(String processName, String elementName)
    {
        reset();

        setStartTime(System.currentTimeMillis());

        setProcessName(processName);

        setElementName(elementName);
        
        setJobName(elementName);
    }

    /**
     * Setter.
     * @param b
     */
    private void setJobCompleted(boolean b)
    {
        this.jobCompleted = b;
    }

    /**
     * Getter.
     * @return
     */
    public boolean getJobCompleted()
    {
        return this.jobCompleted;
    }

    /**
     * Resets the values for this BatchStat.
     */
    private void reset()
    {
        setFailureCount(0);

        setStartTime(0);

        setStopTime(0);
        
        setBatchTime(DateTimeUtil.convertTimeToHHMMSSss(getTotalBatchTime()));

        setSuccessCount(0);
        
        setSkipCount(0);

        setJobCompleted(false);
        
        //setJobName("");
        
        setBatchMessage("");
        
        this.batchStatsLogged = false;
    }

    /**
     * Stops the timer for the batch process.
     */
    public void tagBatchStop()
    {
        setStopTime(System.currentTimeMillis());

        setJobCompleted(true);
        
        setBatchTime(DateTimeUtil.convertTimeToHHMMSSss(getTotalBatchTime()));
        
        String status = this.getBatchStatus().replaceAll("<seg>", "");
        status = status.replaceAll("</seg>", "");
        
        setFinalBatchStatus(status);
        
        if (!batchStatsLogged)
        {
        	logBatchStats();
        }
    }

    /**
     * Setter.
     * @param elementName
     */
    private void setElementName(String elementName)
    {
        this.elementName = elementName;
    }
    
    /**
     * Setter.
     * @param elementName
     */
    private void setJobName(String elementName)
    {
        this.jobName = elementName.replaceAll("\\s+","");  
    }

    /**
     * Setter.
     * @param processName
     */
    private void setProcessName(String processName)
    {
        this.processName = processName;
    }
    
    /**
     * Setter.
     * @param batchTime
     */
    private void setBatchTime(String batchTime)
    {
        this.batchTime = batchTime;
    }
    
    /**
     * Setter.
     * @param finalBatchStatus
     */
    private void setFinalBatchStatus(String finalBatchStatus)
    {
        this.finalBatchStatus = finalBatchStatus;
    }
    
    /**
     * Getter.
     */
    private String getFinalBatchStatus()
    {
        return this.finalBatchStatus;
    }
    
    /**
     * Getter.
     */
    private String getBatchTime()
    {
        return this.batchTime;
    }

    /**
     * Called upon the completion of a success.
     */
    synchronized public void updateSuccess()
    {
        successCount.getAndIncrement();
    }

    /**
     * Called upon the completion of a failure.
     */
    synchronized public void updateFailure()
    {
        failureCount.getAndIncrement();
    }
    
    /**
     * Called upon the completion of a skip.
     */
    synchronized public void updateSkip()
    {
        skipCount.getAndIncrement();
    }

    /**
     * Getter.
     * @return
     */
    public int getSuccessCount()
    {
        return this.successCount.get();
    }

    /**
     * Getter.
     * @return
     */
    public int getFailureCount()
    {
        return this.failureCount.get();
    }
    
    /**
     * Getter.
     * @return
     */
    public int getSkipCount()
    {
        return this.skipCount.get();
    }

    /**
     * Getter.
     * @return
     */
    public String getElementName()
    {
        return this.elementName;
    }
    
    /**
     * Getter.
     * @return
     */
    public String getJobName()
    {
        return this.jobName;
    }

    /**
     * The elapsed time in milliseconds of the batch run.
     * @return
     */
    public long getTotalBatchTime()
    {
        long time = 0;

        if (!getJobCompleted())
        {
            time = System.currentTimeMillis();
        }
        else
        {
            time = getStopTime();
        }

        long timeDiff = time - getStartTime();

        return timeDiff;
    }

    /**
     * The current number of batch elements / second
     * @return
     */
    public double getBatchRate()
    {
        double batchElementsProcessed = getSuccessCount() + getFailureCount() + getSkipCount();

        double millisPerSec = 1000.0;

        double totalBatchTimeSecs = getTotalBatchTime() / millisPerSec;

        double batchRate = 0.0;

        if (batchElementsProcessed != 0 && totalBatchTimeSecs != 0)
        {
            batchRate = batchElementsProcessed / totalBatchTimeSecs;
        }

        return batchRate;
    }

    /**
     * Getter.
     * @return
     */
    public long getStopTime()
    {
        return this.stopTime;
    }

    /**
     * Getter.
     * @return
     */
    public String getFormattedStopTime()
    {
        return this.formattedStopTime;
    }
    
    /**
     * Compares two BatchStats by their respective start times.
     * @param o
     * @return
     */
    public int compareTo(Object o)
    {
        BatchStat visitingBatchStat = (BatchStat) o;

        long visitingStartTime = visitingBatchStat.getStartTime();

        long thisStartTime = getStartTime();

        return new Long(thisStartTime).compareTo(new Long(visitingStartTime));
    }

    /**
     * Getter.
     * @return
     */
    public long getStartTime()
    {
        return this.startTime;
    }
    
    /**
     * Getter.
     * @return
     */
    public String getFormattedStartTime()
    {
        return this.formattedStartTime;
    }

    /**
     * The sum total of success and failure operations.
     * @return
     */
    public int getTotalCount()
    {
        int totalCount = 0;

        totalCount = getSuccessCount() + getFailureCount() + getSkipCount();

        return totalCount;
    }

    /**
     * Returns the state of the Batch job.
     * @return
     */
    public String getBatchStatus()
    {
        String batchStatus = null;

        if (!getJobCompleted() && (getTotalCount() == 0))
        {
            batchStatus = Batch.PROCESSING_NO_STATS_AVAILABLE;
        }

        else if (!getJobCompleted() && (getTotalCount() > 0))
        {
            batchStatus = Batch.PROCESSING;
        }

        else if (getJobCompleted() && (getFailureCount() == 0) && (getSuccessCount() == 0) && (getSkipCount() == 0))
        {
            batchStatus = Batch.PROCESSED_BUT_NO_ELEMENTS_FOUND;
        }

        else if (getJobCompleted() && (getFailureCount() > 0))
        {
            batchStatus = Batch.PROCESSED_WITH_ERRORS;
        }

        else if (getJobCompleted() && (getSuccessCount() > 0))
        {
            batchStatus = Batch.PROCESSED_WITHOUT_ERRORS;
        }

        else
        {
            batchStatus = "Invalid Batch Status";
        }

        return batchStatus;
    }
    
    public String getBatchMessage()
    {
    	return batchMessage;
    }
    
    private void logBatchStats()
    {
        	
        try
        {	
        	SessionHelper.beginTransaction(SessionHelper.EDITSOLUTIONS);
            
            SessionHelper.save(this, SessionHelper.EDITSOLUTIONS);
            
            SessionHelper.commitTransaction(SessionHelper.EDITSOLUTIONS);
            
            this.batchStatsLogged = true;
        
        }catch (Exception e) {
        	
            SessionHelper.rollbackTransaction(SessionHelper.EDITSOLUTIONS);

        	System.out.println("Exception: " + e.getMessage());
        	
        }finally{
        	
        	SessionHelper.clearSessions();
        }
    }
}
