package batch;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: May 20, 2005
 * Time: 4:47:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BatchMBean
{
    public int executeBatchJob(String jobName, String parameters, String username, String password);
}
