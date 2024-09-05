package batch;

import com.sun.jdmk.comm.*;

import javax.management.*;

import batch.component.*;
import batch.business.*;

import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: May 20, 2005
 * Time: 4:49:01 PM
 *
 * Adds a wrapper as an MBean for our Batch processes. This allows an external process (a
 */
public class BatchAgent
{
    public static final String DOMAIN_NAME = "segllc.com";
    public static final int HTMLADAPTER_PORT = 9092;
    private MBeanServer mBeanServer;
    private Map batchStats;

    public BatchAgent()
    {
        batchStats = new HashMap();
    }

    /**
     * Starts the MBeanServer for the Batch Beans, and the HtmlAdapter so that the MBeans can be invoked via HTTP requests
     * on the configured port number.
     */
    public void startService()
    {
        try
        {
            mBeanServer = MBeanServerFactory.createMBeanServer("Batch MBeanServer");

            // HtmlAdapterServer MBean
            HtmlAdaptorServer htmlAdapterServer = new HtmlAdaptorServer();
            ObjectName htmlAdapterServerName = getObjectName("HtmlAdapterServer");
            htmlAdapterServer.setPort(HTMLADAPTER_PORT);

            // Batch Component
            Batch batch = new BatchComponent();
            ObjectName batchName = getObjectName("Batch");

            // HtmlParser
            HtmlParser htmlParser = new HtmlParserImpl();
            ObjectName htmlParserName = getObjectName("HtmlParser");

            mBeanServer.registerMBean(htmlAdapterServer, htmlAdapterServerName);
            mBeanServer.registerMBean(batch, batchName);
            mBeanServer.registerMBean(htmlParser, htmlParserName);

            // ... HtmlParser continued
            htmlAdapterServer.setParser(htmlParserName);

            System.out.println("\n\t\t ** -----> Batch Agent Is Running [port:" + HTMLADAPTER_PORT + "]");

            // Put the htmlAdapterServer into a daemon thread, otherwise it won't terminate the JVM when shutting down.
            Thread daemonThread = new Thread(htmlAdapterServer);
            daemonThread.setDaemon(true);
            daemonThread.start();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    /**
     * Getter.
     * @return
     */
    private MBeanServer getMBeanServer()
    {
        return mBeanServer;
    }

    /**
     * Generates an ObjectName based on the domain name, and class name. The String representation is as follows:
     * "DOMAIN_NAME:className"
     * For example "segllc.com:name=a.b.MyBatchJob"
     * @return
     */
    public ObjectName getObjectName(String simpleName)
    {
        ObjectName objectName = null;

        try
        {
            objectName = new ObjectName(DOMAIN_NAME + ":name=" + simpleName);
        }
        catch (MalformedObjectNameException e)
        {
            System.out.println(e);

            e.printStackTrace();
        }

        return objectName;
    }

    /**
     * Returns an instance of BatchStatus for the specified batchJob. If the instance does not exist, one is created.
     * If the instance already exists, it is returned.
     * @param batchJob
     * @return BatchStat
     */
    public synchronized BatchStat getBatchStat(String batchJob)
    {
        BatchStat batchStat = null;

        if (batchStats.containsKey(batchJob))
        {
            batchStat = (BatchStat) batchStats.get(batchJob);
        }
        else
        {
            batchStat = new BatchStat();

            batchStats.put(batchJob, batchStat);
        }

        return batchStat;
    }

    /**
     * Returns the current BatchStats ordered by start date descending.
     * @return
     */
    public BatchStat[] getBatchStats()
    {
        BatchStat[] currentBatchStats = (BatchStat[]) batchStats.values().toArray(new BatchStat[batchStats.size()]);

        List asList = Arrays.asList(currentBatchStats);

        Collections.sort(asList);

        Collections.reverse(asList);

        return (BatchStat[]) asList.toArray(new BatchStat[asList.size()]);
    }
    
    /**
     * Removes the BatachStat entry for a given key.
     * @param batchJob
     */
    public void clearBatchStat(String batchJob)
    {
        if (batchStats.containsKey(batchJob))
        {
            batchStats.remove(batchJob);
        }        
    }
}
