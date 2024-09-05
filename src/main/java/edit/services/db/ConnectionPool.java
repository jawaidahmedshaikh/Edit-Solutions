/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Feb 8, 2002
 * Time: 11:44:55 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package edit.services.db;

import edit.services.config.ConfigServicesJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {

    private Stack connections;
    private int noBorrowed;

    private String jdbcDriver;
    private String jdbcURL;
    private int    poolSize;
    private String poolName;
    private String username;
    private String password;

    //  Set the transaction isolation level.  Only READ COMMITTED and SERIALIZABLE are supported by Oracle.
    public static final int TRX_ISOLATION_LEVEL = Connection.TRANSACTION_READ_COMMITTED;

    public ConnectionPool(ConfigServicesJdbc configJDBC) {

        this(configJDBC.getDriver(), configJDBC.getUrl(), configJDBC.getPoolSize(), configJDBC.getPoolName(), "sa", "");
    }

    public ConnectionPool(String jdbcDriver, String jdbcURL, int poolSize, String poolName, String username, String password){

        this.jdbcDriver = jdbcDriver;
        this.jdbcURL    = jdbcURL;
        this.poolSize   = poolSize;
        this.poolName   = poolName;
        this.username   = username;
        this.password   = password;

        init();

        Thread.yield();
    }

    public synchronized Connection borrowConnection() {

        try {

            while (noBorrowed >= poolSize) {

                System.out.println("!!!! Waiting for connection ......................");

                wait();

                System.out.println("!!!! Got connection ......................");
            }
        }
        catch (InterruptedException e) {

            System.out.println(e);
            e.printStackTrace();
        }

        noBorrowed++;

        if (poolName.equals(ConnectionFactory.EDITSOLUTIONS_POOL)){

            System.out.println("EDITSOLUTIONS_POOL: Borrowed Connection: " + noBorrowed);
        }

        return (Connection) connections.pop();
    }

    public synchronized void returnConnection(Connection connection) {

        connections.push(connection);

        noBorrowed--;

        if (poolName.equals(ConnectionFactory.EDITSOLUTIONS_POOL)){

            System.out.println("EDITSOLUTIONS_POOL: Returned Connection: " + noBorrowed);
        }

        notifyAll();
    }

    private final void init() {

        connections = new Stack();

        try {

            Class.forName(jdbcDriver);

            for (int i = 0; i < poolSize; i++) {

                Connection conn = DriverManager.getConnection(jdbcURL, username, password);
                conn.setAutoCommit(false);
                conn.setTransactionIsolation(TRX_ISOLATION_LEVEL);

                connections.push(conn);
            }
        }
        catch (ClassNotFoundException e) {

            System.out.println(e);
            e.printStackTrace();
        }
        catch(SQLException e){

            System.out.println(e);
            e.printStackTrace();
        }
    }

    public String getPoolName(){

        return poolName;
    }
}

