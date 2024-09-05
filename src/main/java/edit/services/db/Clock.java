package edit.services.db;

import edit.common.vo.ChargeCodeVO;

import java.util.*;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Helps with timing checks for db queries.  It tracks the stats by using the
 * stack trace and also the set of table names that are composed in the return
 * as the key.
 */
public class Clock
{

    private long startTime = -1L;
    private long endTime = -1L;

    /** if this is false, there will be no logging of statistics */
    private static final boolean KEEP_STATISTICS = false;

    /** When the execute time of a query exceeds this threshold
     * its statistics will be captured if the KEEP_STATISTICS setting
     * above is true.  This threshold is in seconds.
     */
    private static final float THRESHOLD_SECS = 0.5F;

    private static Map clockStats = new HashMap(10013);

    private static long totalCounter = 0L;

    private static long MODULO = 100L;

    /**
     * This will instantiate - it will track the values
     * based on the stack trace as the key.
     */
    public Clock()
    {
        this.startTime = System.currentTimeMillis();
    }

    public void stop(Object[] voObjects)
    {
        this.endTime = System.currentTimeMillis();
        if (KEEP_STATISTICS)
        {
            try
            {
                trackStats(voObjects);
            }
            catch(Exception ignore){}
            // if there is any exception while tracking
            // ignore it.
        }
    }


    public static boolean isTurnedOn()
    {
        return KEEP_STATISTICS;
    }

    public static String getThreshhold()
    {
        return THRESHOLD_SECS + "";
    }

    public static List getWorstStats()
    {

        List newList = new ArrayList();
        List listToSort = new ArrayList();

        try
        {
            synchronized (Clock.class)
            {
                Iterator it = Clock.clockStats.keySet().iterator();

                while (it.hasNext())
                {
                    String name = (String) it.next();
                    long[] statsArray = (long[]) Clock.clockStats.get(name);

                    float totaltime = (float) statsArray[1];
                    float avg = ((totaltime / statsArray[0]) / 1000F);

                    if (avg < THRESHOLD_SECS)
                    {
                        continue;
                    }

                    String[] stat = new String[]
                    {
                        name, avg + "", statsArray[0] + ""
                    };

                    listToSort.add(new Object[] {new Float(avg), stat});
                    // we want to sort on avg after this is assembled
                }
            }

            // don't need synchronized now

            Collections.sort(listToSort,
                    new Comparator()
                    {
                        public int compare(Object a, Object b)
                        {
                            Object[] aObj = (Object[]) a;
                            Object[] bObj = (Object[]) b;
                            Float aF = (Float) aObj[0];
                            Float bF = (Float) bObj[0];

                            return (bF.compareTo(aF));  // reversed
                        }
                    });

            // now peel out only the String array


            for (int i = 0; i < listToSort.size(); i++)
            {
                Object[] o =  (Object[]) listToSort.get(i);
                newList.add(o[1]);  // just the String array
            }
        }
        catch (Exception ignore)
        {
            // DON'T WORRY ABOUT AN EXCEPTION
            // IF JUST TRYING TO DUMP MAP CONTENTS
        }

        return newList;

    }

    /**
     * Keep the stats in the Map.  Note - we synchronize
     * on the class.
     */
    private void trackStats(Object[] voObjects)
    {
        long millisecs = this.endTime - this.startTime;

        float secs = millisecs / 1000F;

        if (secs < THRESHOLD_SECS)
        {
            return;
        }

        String tableNames = getTableNames(voObjects);

        StackTraceElement[] els = (new Exception()).getStackTrace();

        String key = makeKey(els);

        key = tableNames + key;

        synchronized (Clock.class)
        {
            totalCounter++;
            long[] statArray = (long[]) Clock.clockStats.get(key);
            if (statArray == null)
            {
                statArray = new long[]{1L, millisecs};
            }
            else
            {
                statArray[0] += 1;
                statArray[1] += millisecs;
            }
            Clock.clockStats.put(key, statArray);
        }
    }

    private String getTableNames(Object[] voObjects)
    {

        if (voObjects == null || voObjects.length == 0)
        {
             return "(no rows) ";
        }

        // FIRST GET THE SET OF TABLE NAMES RETURNED
        final Set tables = new TreeSet();  // keep them ordered because part of key


        // NOTE - we could just go thru one branch - it would probably be representative
        // We don't show how many rows for each because that will upset the accumulation
        // of statistics
        for (int i = 0; i < voObjects.length; i++)
        {
            Object voObject = voObjects[i];

            CRUD.recurseVOObjectModel(voObject, null, CRUD.RECURSE_TOP_DOWN, new RecursionListener()
            {
                public void currentNode(Object currentNode, Object parentNode, RecursionContext recursionContext)
                {
                    VOClass voClassMD = VOClass.getVOClassMetaData(currentNode.getClass());
                    String key = voClassMD.getTableName();
                    if (!tables.contains(key))
                    {
                        tables.add(key);
                    }
                }
            }, null, null);
        }

        if (tables.size() > 0)
        {
            StringBuffer sb = new StringBuffer(80);
            sb.append("TABLES=(");
            Iterator it = tables.iterator();

            while (it.hasNext())
            {
                String tableName = (String) it.next();
                // Should not put html tags but this is used
                // internally so doing it easy.  Could be changed to jsp if needed.
                sb.append("<b>").append(tableName).append("</b>").append(" ");
            }
            sb.append(") ");
            return sb.toString();
        }
        else
        {
            return "(no rows) ";
        }
    }

    /**
     * Makes a key consisting of a stack trace.  It ignores
     * the last two method entries if there.
     * @param els
     * @return
     */
    private String makeKey(StackTraceElement[] els)
    {
        StringBuffer sb = new StringBuffer(1000);

        for (int i = 3; i < els.length; i++)
        {
            StackTraceElement el = els[i];
            String className = el.getClassName();
            String methodName = el.getMethodName();
            int lineNumber = el.getLineNumber();

            if (className.indexOf("org.apache") > -1)
            {
                continue;  // skip org.apache internals
            }

            if (className.indexOf("java.lang") > -1)
            {
                continue;  // skip java.lang
            }

            if (className.indexOf("weblogic.") > -1)
            {
                continue;  // skip weblogic internals
            }

            if (className.indexOf("sun.reflect") > -1)
            {
                continue;  // skip sun reflect
            }

            // get last part of classname - don't need
            // entire package for these purposes

            int last = className.lastIndexOf(".");
            if (last > -1)
            {
                className = className.substring(last + 1);
            }

            sb.append(className)
                    .append(".")
                    .append("<b>")     // I know shouldn't put here but
                    .append(methodName)
                    .append("</b>")    // it's an internal tool so easiest
                    .append(" ")
                    .append(lineNumber)
                    .append(", ");
        }

        if (sb.length() == 0)
        {
            // certain VMs could pass in 0 length or maybe less than 2?
            return ("mixed: stack trace info not available");
        }
        else
        {
            return sb.toString();
        }
    }

    //    public static void main(String[] args)
    //    {
    //        for (int i = 0; i < 20; i++)
    //        {
    //            Clock clock1 = new Clock();
    //
    //            // DO SOMETHING FOR A BIT...
    //            try
    //            {
    //                Thread.sleep(520);
    //            }
    //            catch (Exception ignore)
    //            {
    //            }
    //
    //            clock1.stop();
    //        }
    //        // WILL DISPLAY THIS
    //        // CLOCK FOR time for a sleep  secs elapsed = 2.0
    //    }
}
