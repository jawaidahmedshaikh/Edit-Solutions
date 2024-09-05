/*
 * User: sdorman
 * Date: Oct 26, 2004
 * Time: 12:15:15 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.services.db;

import edit.common.EDITDate;
import edit.common.EDITDateTime;

import edit.services.db.hibernate.SessionHelper;
import fission.utility.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 * Set of utility methods for dealing with database specific functionality.
 */
public class DBUtil
{

    /** The max length for an IN-CLAUSE.  In Oracle this is currently 1000.
     *  For SQL Server 2000 it is 300.
     */
     public static final int MAX_IN_CLAUSE_LENGTH = 300;

    /**
     * Reads a Date from the ResultSet and converts it to a properly formatted string.
     *
     * @param rs                result set of the database query
     * @param columnIndex       index of the column to be read from the ResultSet
     *
     * @return  string containing the properly converted date
     */
    public static String readAndConvertDate(ResultSet rs, int columnIndex)
    {
        java.sql.Date date = null;

        try
        {
            date = rs.getDate(columnIndex);
        }
        catch (SQLException e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return DBUtil.convertDateToString(date);
    }

    /**
     * Reads a Date from the ResultSet and converts it to a properly formatted string.
     *
     * @param rs                result set of the database query
     * @param columnName        column name of column to be read from the ResultSet
     *
     * @return  string containing the properly converted date
     */
    public static String readAndConvertDate(ResultSet rs, String columnName)
    {
        java.sql.Date date = null;

        try
        {
            date = rs.getDate(columnName);
        }
        catch (SQLException e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return DBUtil.convertDateToString(date);
    }

    /**
     * Converts the java.sql.Date to a properly formatted String
     *
     * @param date              the java.sql.Date to be converted
     *
     * @return  properly formatted string
     */
    public static String convertDateToString(java.sql.Date date)
    {
        String columnValue = null;

        if (date != null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat(EDITDate.DATE_FORMAT);
            columnValue = sdf.format(date);
        }

        return columnValue;
    }

     /**
     * Reads a Timestamp from the ResultSet and converts it to a properly formatted string.
     *
     * @param rs                result set of the database query
     * @param columnIndex       index of the column to be read from the ResultSet
     *
     * @return  string containing the properly converted date
     */
    public static String readAndConvertTimestamp(ResultSet rs, int columnIndex)
    {
        java.sql.Timestamp timestamp = null;

        try
        {
            timestamp = rs.getTimestamp(columnIndex);
        }
        catch (SQLException e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return DBUtil.convertTimestampToString(timestamp);
    }

    /**
     * Reads a Timestamp from the ResultSet and converts it to a properly formatted string.
     *
     * @param rs                result set of the database query
     * @param columnName        column name of column to be read from the ResultSet
     *
     * @return  string containing the properly converted date
     */
    public static String readAndConvertTimestamp(ResultSet rs, String columnName)
    {
        java.sql.Timestamp timestamp = null;
        try
        {
            timestamp = rs.getTimestamp(columnName);
        }
        catch (SQLException e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return DBUtil.convertTimestampToString(timestamp);
    }

    /**
     * Converts the java.sql.Timestamp to a properly formatted String
     *
     * @param timestamp         the java.sql.Timestamp to be converted
     *
     * @return  properly formatted string
     *
     * @throws SQLException
     */
    public static String convertTimestampToString(java.sql.Timestamp timestamp)
    {
        String columnValue = null;

        if (timestamp != null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat(EDITDateTime.DATETIME_FORMAT);
            columnValue = sdf.format(timestamp);
        }

        return columnValue;
    }

    /**
     * Converts a string to a java.sql.Timestamp using the proper format
     *
     * @param dateTime          the string to be converted
     *
     * @return  properly formatted java.sql.Timestamp
     */
    public static java.sql.Timestamp convertStringToTimestamp(String dateTime)
    {
        java.util.Date d = null;

        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(EDITDateTime.DATETIME_FORMAT);
            sdf.setLenient(false);
            d = sdf.parse(dateTime);
        }
        catch (ParseException e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        java.sql.Timestamp sqlTimeStamp = new java.sql.Timestamp(d.getTime());

        return sqlTimeStamp;
    }

    /**
     * Converts a String to a java.sql.Date using the proper format
     *
     * @param date              the string to be converted
     *
     * @return  properly formatted java.sql.Date
     */
    public static java.sql.Date convertStringToDate(String date)
    {
        java.util.Date d = null;

        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(EDITDate.DATE_FORMAT);
            sdf.setLenient(false);
            d = sdf.parse(date);
        }
        catch (ParseException e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }

        java.sql.Date sqlDate = new java.sql.Date(d.getTime());

        return sqlDate;
    }

    /**
     * Builds the fully qualified column name by surrounding it with double quotes and prefixing it with the
     * fully qualified table name
     * <P>
     * NOTE: double quotes are required for mixed case column names in some DBMSs (Oracle).
     *
     * @param schemaName            schema name associated with this table
     * @param databaseName          database name associated with this table (is null for Oracle)
     * @param tableName             name of the database table
     * @param columnName            name of the database column
     *
     * @return built columnName
     *
     * @see DBUtil.formatTableName()
     * @see DBUtil.quote()
     */
    public static String formatColumnName(String schemaName, String databaseName, String tableName, String columnName)
    {
        return DBUtil.formatTableName(schemaName, databaseName, tableName) + "." + DBUtil.quote(columnName);
    }

    /**
     * Builds the fully qualified table name by surrounding it with double quotes and prefixing it with the schemaName
     * <P>
     * NOTE: double quotes are required for mixed case column names in some DBMSs (Oracle).
     *
     * @param schemaName            schema name associated with this table (may be null when formatting table aliases)
     * @param databaseName          database name associated with this table (is null for Oracle)
     * @param tableName             name of the database table
     *
     * @return built tableName
     *
     * @see DBUtil.quote()
     */
    public static String formatTableName(String schemaName, String databaseName, String tableName)
    {
        if (databaseName == null)
        {
            if (schemaName == null)
            {
                return DBUtil.quote(tableName);
            }
            else
            {
                return schemaName + "." + DBUtil.quote(tableName);
            }
        }
        else
        {
            return databaseName + "." + schemaName + "." + DBUtil.quote(tableName);
        }
    }

    /**
     * Adds double quotes around the string
     *
     * @param str                   string to be quoted
     *
     * @return  quoted string
     */
    public static String quote(String str)
    {
        return "\"" + str + "\"";
    }

    /**
     * Determines which name is the proper databaseName.  For SQL Server, databaseName is the database name and
     * schemaName is the owner (dbo).  For Oracle, databaseName is null and schemaName is the true database name.
     * @param schemaName
     * @param databaseName
     * @return true database name
     */
    public static String determineDatabaseName(String schemaName, String databaseName)
    {
        if (databaseName == null)
        {
            return schemaName;
        }
        else
        {
            return databaseName;
        }
    }


    /**
     * This will create an In Clause that respects the MAX_IN_CLAUSE_LENGTH.
     * If there are more keys than that, it will create a series of
     * IN CLAUSES with OR's between them and parentheses around the whole
     * result string.
     * <p/>
     * <PRE>
     *
     * Example - if the MAX_IN_CLAUSE_LENGTH were 2 then passing
     * in COLNAME and two longs would return this String
     *
     *      COLNAME IN (1,2)
     *
     * passing in 1, 2, 3 would return this String
     *
     *     (  COLNAME IN (1,2)  OR  COLNAME IN (3)  )
     *
     *
     * </PRE>
     * @param columnName
     * @param keys
     * @return
     */
    public static String createInClause(String columnName, long[] keys)
    {
        if (keys.length <= MAX_IN_CLAUSE_LENGTH)
        {
            return createSimpleInClause(columnName, keys);
        }

        int startpos = 0;

        List inClauses = new ArrayList();

        while (startpos < keys.length)
        {
            int numToCopy = Math.min(keys.length - startpos, MAX_IN_CLAUSE_LENGTH);

            long[] subset = new long[numToCopy];

            System.arraycopy(keys, startpos, subset, 0, numToCopy);

            String inclause = createSimpleInClause(columnName, subset);

            inClauses.add(inclause);

            startpos += numToCopy;
        }

        StringBuilder combinedInClauseSB = new StringBuilder(2000);

        combinedInClauseSB.append(" ( ");

        for (int i = 0; i < inClauses.size(); i++)
        {

            String s = (String) inClauses.get(i);

            if (i > 0)
            {
                combinedInClauseSB.append(" OR ");
            }

            combinedInClauseSB.append(s);
        }

        combinedInClauseSB.append(" ) ");

        return combinedInClauseSB.toString();
    }

    private static String createSimpleInClause(String columnName, long[] keys)
    {
        StringBuilder inclauseSB = new StringBuilder(12 * keys.length);
            // Start out big enough so that it shouldn't have to
            // reallocate storage.

        inclauseSB.append(" ").append(columnName).append(" IN (");

        for (int i = 0; i < keys.length; i++)
        {
            if (i > 0)
            {
                inclauseSB.append(",");
            }

            inclauseSB.append(keys[i]);
        }

        inclauseSB.append(") ");

        return inclauseSB.toString();
    }

    /**
     * <p>
     * WARNING: Deletes all current indexes, and rebuilds a PK/FK index for every
     * parent/child table relationship that can be found.
     * </p>
     * <p>
     * It has been our experience that guaranteeing that an index exists for 
     * every PK/FK association has significant performance advantages for Hibernate.
     * </p>
     * @param connectionFactoryPool 
     * @see ConnectionFactory#EDITSOLUTIONS_POOL
     */
    public static void reindexDB(String connectionFactoryPool)
    {
        deleteIndexes(connectionFactoryPool);
        
        reindexDatabases(connectionFactoryPool);
        
    }
    
    /**
     * Builds PK/FK indexes for every PK/FK relationship that can be found.
     * 
     * We are only considering tables within the same DB - we don't jump
     * across DBs (such as Segment.ProductStructureFK).
     * @param connectFactoryPool 
     */
    private static void reindexDatabases(String connectionFactoryPool)
    {
        StringBuilder sql = new StringBuilder();
        
        DBDatabase dBDatabase = getDBDatabase(connectionFactoryPool);
        
        DBTable[] dBTables = dBDatabase.getDBTables();
        
        // Let's just build a big List of the indexes and their associated tables (e.g. IX;Table).
        for (DBTable parentDBTable:dBTables)
        {
            DBColumn[] dBColumns = parentDBTable.getDBColumns();
            
            for (DBColumn childDBColumn:dBColumns)
            {
                if (childDBColumn.getColumnName().endsWith("FK"))
                {
                    String childTableName = childDBColumn.getColumnName().substring(0, childDBColumn.getColumnName().length() - 2);
                    
                    // Found FK column, but is table really valid?
                    DBTable validDBTable = dBDatabase.getDBTable(childTableName);   
                    
                    if (validDBTable != null)
                    {
                        sql.append("CREATE INDEX [IX_" + parentDBTable.getTableName() + "_" + childTableName + "FK] ON [" + parentDBTable.getTableName() + "] (" + childDBColumn.getColumnName() + ")").append("\n");
                    }
                }
            }
        }
        
        // There are some indexes which have proved very useful - let's add them.

        // EDITSolutions
        if (connectionFactoryPool.equals(ConnectionFactory.EDITSOLUTIONS_POOL))
        {
            sql.append("CREATE INDEX [IX_Segment_ContractNumber] ON [Segment](ContractNumber)").append("\n");
            sql.append("CREATE INDEX [IX_PremiumDue_EffectiveDate_PendingExtractIndicator_SegmentFK] ON [PremiumDue](EffectiveDate, PendingExtractIndicator, SegmentFK)").append("\n");
            sql.append("CREATE INDEX [IX_CommissionHistory_CommissionTypeCT] ON [CommissionHistory](CommissionTypeCT)").append("\n");
            sql.append("CREATE INDEX [IX_Log_LogName] ON [Log](LogName)").append("\n");
        }
        
        // ENGINE
        else if (connectionFactoryPool.equals(ConnectionFactory.EDITSOLUTIONS_POOL))
        {
        }
        
        // Let's also update the statistics while we're here.
        sql.append("EXEC sp_MSforeachtable @command1=\"print '?' DBCC DBREINDEX ('?', ' ', 80)\"").append("\n");
        sql.append("EXEC sp_updatestats").append("\n");
        
        // Now let's actually build the indexes in the DB.
        Connection c = null;
        
        Statement s = null;

        try
        {
            
            if (!sql.toString().isEmpty())
            {
                c = getConnection(connectionFactoryPool);
                c.setAutoCommit(true);

                s = c.createStatement();            
                s.executeUpdate(sql.toString());     
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (s != null)
                {
                    s.close();
                }
                
                if (c != null)
                {
                    c.close();
                }
            }
            catch (Exception e)
            {
                System.out.println(e);
                
                e.printStackTrace();
            }
        }        
        
    }   
    
    /**
     * Gets the DBDatabase targeted by the specified connectionFactoryPool.
     * @param connectionFactoryPool
     * @return 
     */
    private static DBDatabase getDBDatabase(String connectionFactoryPool)
    {
        DBDatabase dBDatabase = null;
        
        for (DBDatabase currentDBDatabase:DBDatabase.getDBDatabases())
        {
            if (currentDBDatabase.getPoolName().equals(connectionFactoryPool))
            {
                dBDatabase = currentDBDatabase;
                
                break;
            }
        }
        
        return dBDatabase;
    }
    
    private static void deleteIndexes(String connectionFactoryPool)
    {
        List<String> indexes = getIndexes(connectionFactoryPool);
        
        StringBuilder sql = new StringBuilder();
        
        for (String index:indexes)
        {
            String[] indexTokens = Util.fastTokenizer(index, ":");
            
            String indexName = indexTokens[0];
         
            // These indexes are immutable as dictated by SQL Server.
            if (!indexName.toUpperCase().startsWith("PK_") && !indexName.toUpperCase().startsWith("_WA_SYS_") && !indexName.toUpperCase().startsWith("UK_") && !indexName.toUpperCase().startsWith("_DTA_"))
            {
                String tableName = indexTokens[1];

                sql.append("DROP INDEX [" + indexName + "] ON [" + tableName + "] ").append("\n");                
            }
        }

        Connection c = null;
        
        Statement s = null;

        try
        {
            
            if (!sql.toString().isEmpty())
            {
                c = getConnection(connectionFactoryPool);
                c.setAutoCommit(true);

                s = c.createStatement();            
                s.executeUpdate(sql.toString());             
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (s != null)
                {
                    s.close();
                }
                
                if (c != null)
                {
                    c.close();
                }
            }
            catch (Exception e)
            {
                System.out.println(e);
                
                e.printStackTrace();
            }
        }
    }  
    
    private static Connection getConnection(String connectionFactoryPool)
    {
        String targetDB = null;

        if (connectionFactoryPool.equals(ConnectionFactory.EDITSOLUTIONS_POOL))
        {
            targetDB = SessionHelper.EDITSOLUTIONS;
        }
        else if (connectionFactoryPool.equals(ConnectionFactory.ENGINE_POOL))
        {
            targetDB = SessionHelper.ENGINE;
        }

        Session session = SessionHelper.getSession(targetDB);

        Connection connection = session.connection();

        return connection;
    }
    
    /**
     * Builds a List of String tokens as: <br/>
     * 
     * index:table
     * 
     * @param connectionFactoryPool
     * @return 
     */
    public static List<String> getIndexes(String connectionFactoryPool)
    {
        List<String> indexes = new ArrayList<String>();
        
        String sql = "        SELECT          [sysindexes].[name] AS [Index], \n" +
                    "                        [sysobjects].[name] AS [Table] \n" +
                    "         \n" +
                    "        FROM            [sysindexes] \n" +
                    "         \n" +
                    "        INNER JOIN      [sysobjects] \n" +
                    "        ON              [sysindexes].[id] = [sysobjects].[id] \n" +
                    "         \n" +
                    "        WHERE           [sysindexes].[name] IS NOT NULL \n" +
                    "        AND             [sysobjects].[type] = 'U' ";
        
        Connection c = null;
        
        Statement s = null;
        
        ResultSet rs = null;

        try
        {
            c = getConnection(connectionFactoryPool);
        
            s = c.createStatement();            
            
            rs = s.executeQuery(sql);
            
            while (rs.next())
            {
                String indexName = rs.getString(1);
                
                String tableName = rs.getString(2);
                
                indexes.add(indexName + ":" + tableName);                
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                
                if (s != null)
                {
                    s.close();
                }
                
                if (c != null)
                {
                    c.close();
                }
            }
            catch (Exception e)
            {
                System.out.println(e);
                
                e.printStackTrace();
            }
        }        
        
        return indexes;
    }
}
