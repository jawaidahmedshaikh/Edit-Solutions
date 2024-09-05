/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 11, 2002
 * Time: 12:38:10 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.services.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionFactory
{
	private static Logger logger = LogManager.getLogger(ConnectionFactory.class);
	
	private static final String JDBC_SECURITY = "java:comp/env/jdbc/security";
	private static final String JDBC_EDITSOLUTIONS = "java:comp/env/jdbc/editsolutions";
	private static final String JDBC_PRASE = "java:comp/env/jdbc/prase";
	
//    private static final String EDITSOLUTIONS_SERVICE = "EDITSOLUTIONS";
//    private static final String SECURITY_SERVICE = "SECURITY";
//    private static final String PRASE_SERVICE = "PRASE";

//    private static final String ACCOUNTING_SERVICE = "ACCOUNTING";
//    private static final String CLIENT_SERVICE = "CLIENT";
//    private static final String CODETABLE_SERVICE = "CODETABLE";
//    private static final String CONTRACT_SERVICE = "CONTRACT";
//    private static final String EDITING_SERVICE = "EDITING";
//    private static final String REPORTING_SERVICE = "REPORTING";
//    private static final String TRANSFORM_SERVICE = "TRANSFORM";
//    private static final String QUERY_SERVICE = "QUERY";
//    private static final String EVENT_SERVICE = "EVENT";
//    private static final String ROLE_SERVICE = "ROLE";

    public static final String EDITSOLUTIONS_POOL = "editSolutions-pool";
    public static final String SECURITY_POOL = "security-pool";
    public static final String ENGINE_POOL = "engine-pool";

//    public static final String ACCOUNTING_POOL = "jdbc:apache:commons:dbcp:accounting-pool";
//    public static final String CLIENT_POOL = "jdbc:apache:commons:dbcp:client-pool";
//    public static final String CODETABLE_POOL = "jdbc:apache:commons:dbcp:codetable-pool";
//    public static final String CONTRACT_POOL = "jdbc:apache:commons:dbcp:contract-pool";
//    public static final String EDITING_POOL = "jdbc:apache:commons:dbcp:editing-pool";
//    public static final String REPORTING_POOL = "jdbc:apache:commons:dbcp:reporting-pool";
//    public static final String TRANSFORM_POOL = "jdbc:apache:commons:dbcp:transform-pool";
//    public static final String QUERY_POOL = "jdbc:apache:commons:dbcp:query-pool";
//    public static final String EVENT_POOL = "jdbc:apache:commons:dbcp:event-pool";
//    public static final String ROLE_POOL = "jdbc:apache:commons:dbcp:role-pool";

    private static String[] poolNames = null;

    private static ConnectionFactory connectionFactory = null;

//    private static final String DBCP_URL = "jdbc:apache:commons:dbcp:";

    private Map<String, String> poolToSchemaNames = new HashMap<String, String>();

    private static Map<String, DataSource> poolsMap = new HashMap<String, DataSource>();

    /**
     * Constructor is only accessible as part of the singleton pattern
      */
    private ConnectionFactory()
    {
        try
        {
            buildConnections();
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }

    /**
     * Singleton pattern to return the instance
     * @return
     */
    public static ConnectionFactory getSingleton()
    {
        if (connectionFactory == null)
        {
            connectionFactory = new ConnectionFactory();
        }

        return connectionFactory;
    }


    /**
     * Returns a listing of all possible connection pool names. There is no guarantee that the pool is active it has
     * not been configured.
     * @return
     */
    public String[] getPoolNames()
    {
        return poolNames;
    }

    /**
     * Returns a connection from the pool. The connection pool will grow to accomodate all requests, but will settle
     * back to its configured steady-state when possible.
     * @param poolName
     * @return
     */
    public final Connection getConnection(String poolName)
    {
        Connection conn = null;

        try
        {
            conn = ( (DataSource) poolsMap.get(poolName) ).getConnection();
        }
        catch (SQLException e)
        {
            System.out.println(" ===================== Error connecting to " + poolName + " ==============================");
            System.out.println(e);

            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

            throw new RuntimeException(e);
        }

        return conn;
    }

    public String getSchemaName(String poolName)
    {
        String schemaName = (String) poolToSchemaNames.get(poolName);

        return schemaName;
    }

    /**
     * Builds all connections as specified in the configuration file and makes them available within the connection
     * pooling mechanism.
     */
    private void buildConnections()
    {
        try {
            poolNames = new String[3];
        	
    		InitialContext ictx = new InitialContext();
    		DataSource securityDS = (DataSource)ictx.lookup(JDBC_SECURITY);
            poolNames[0] = SECURITY_POOL;
			poolsMap.put(SECURITY_POOL, securityDS);
			mapPoolNameToSchemaName(SECURITY_POOL, "dbo");
			
			DataSource esDS = (DataSource)ictx.lookup(JDBC_EDITSOLUTIONS);
            poolNames[1] = EDITSOLUTIONS_POOL;
			poolsMap.put(EDITSOLUTIONS_POOL, esDS);
			mapPoolNameToSchemaName(EDITSOLUTIONS_POOL, "dbo");
			
			DataSource praseDS = (DataSource)ictx.lookup(JDBC_PRASE);
            poolNames[2] = ENGINE_POOL;
			poolsMap.put(ENGINE_POOL, praseDS);
			mapPoolNameToSchemaName(ENGINE_POOL, "dbo");
			
		} catch (NamingException e) {
			logger.error(e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    }

    private void mapPoolNameToSchemaName(String poolName, String schemaName)
    {
        poolToSchemaNames.put(poolName, schemaName);
    }

}
