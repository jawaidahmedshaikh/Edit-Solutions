package fission.threading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 5, 2005
 * Time: 9:37:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class SEGPooledExecutor extends ThreadPoolExecutor
{
    private int jobCount;
    private final Object objectMonitor = new Object();

    public SEGPooledExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue)
    {
      super(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, blockingQueue);
    }

    /**
     * @see #execute(Runnable)
     * @param runnable
     * @throws InterruptedException
     */
    public void execute(Runnable runnable)
    {
        super.execute(runnable);
    }

    /**
     * Upon the completion of a job, invokation of this method notifies the Executor that a job was finished, and the
     * job count is decreased by one.
     */
    public void jobCompleted()
    {
        synchronized (objectMonitor)
        {
            jobCount--;

            Thread.yield(); // Yield for moment because another thread may be adding to the job count.

            if (jobCount == 0)
            {
                notifyJobCompleted();
            }
        }
    }

    /**
     * Adds to the total number of jobs that have to be completed before threads waiting for the jobs to be completed
     * can be notified and continue.
     * @param numberOfJobs
     */
    public void addToJobCount(int numberOfJobs)
    {
        synchronized (objectMonitor)
        {
            jobCount += numberOfJobs;
        }
    }

    /**
     * A thread (most likely the main thread) can wait until all threads have returned and the job is completed.
     */
    public void waitUntilJobCompleted()
    {
        try
        {
            synchronized (objectMonitor)
            {
                objectMonitor.wait();

                Thread.yield();
            }
        }
        catch (InterruptedException e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    /**
     * When the job-count is down to zero, the last reporting thread notifies any waiting threads of the completed status.
     */
    private void notifyJobCompleted()
    {
        synchronized (objectMonitor)
        {
            objectMonitor.notifyAll();

//            shutdownAfterProcessingCurrentlyQueuedTasks();

              shutdown();
        }
    }
}
