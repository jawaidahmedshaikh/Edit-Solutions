/*
 * User: gfrosti
 * Date: Mar 7, 2003
 * Time: 10:21:04 AM
 *
 * (c) 2000-2006 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package edit.services.db;

import fission.utility.Util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class DBDatabase
{
    private String poolName;

    private List dbTableList;

    private String databaseName;

    private String databaseServiceName;

    private DatabaseVersion databaseVersion;

    private static List dbDatabaseList;

    private static Map dbPoolByTableName;

    private static Map allFKPKDBColumns;




    // Map all tables to their appropriate pool.
    static
    {
        dbDatabaseList = new ArrayList();
        dbPoolByTableName = new HashMap();
        allFKPKDBColumns = new HashMap();

        Connection conn = null;
        String currentPoolName = null;
        String schemaName = null;

        String[] poolNames = ConnectionFactory.getSingleton().getPoolNames();

        for (int i = 0; i < poolNames.length; i++)
        {
            currentPoolName = poolNames[i];

            DBDatabase dbDatabase = new DBDatabase();
            dbDatabase.setPoolName(currentPoolName);

            try
            {
                conn = ConnectionFactory.getSingleton().getConnection(currentPoolName);
                schemaName = ConnectionFactory.getSingleton().getSchemaName(currentPoolName);

                DatabaseMetaData dbMetaData = conn.getMetaData();

                ResultSet rs = dbMetaData.getTables(null, schemaName, null, new String[]{"TABLE"});

                String databaseName = null;

                // Find all tables...
                while (rs.next())
                {
                    String tableName = rs.getString("TABLE_NAME");
                    databaseName = rs.getString("TABLE_CAT");

//                    if (databaseName == null || databaseName.equals(""))
//                    {
//                        //  Catalogs are not supported by this DBMS, get the databaseName without using the catalog
//                        databaseName = DBDatabase.getDatabaseNameWithoutUsingCatalogs(dbMetaData);
//                    }

                    // To exclude Oracle 10g recycle bin tables
                    // When you drop a table in Oracle10g it moves the tables to recycle bin and renames them
                    // The renaming convention is as follows:
                    // BIN$unique_id$version

                    if (!tableName.startsWith("BIN$"))
                    {
                        DBTable dbTable = new DBTable();
                        dbTable.setTableName(tableName);
                        dbTable.setFullyQualifiedTableName(DBUtil.formatTableName(schemaName, databaseName, tableName));
                        dbDatabase.setDatabaseName(DBUtil.determineDatabaseName(schemaName, databaseName));
                        dbTable.setDBDatabase(dbDatabase);

                        dbDatabase.addDBTable(dbTable);
                        dbPoolByTableName.put(tableName, currentPoolName);
                    }
                }

                rs.close();

                dbDatabase.setDatabaseVersion(DatabaseVersion.findByDatabaseName(schemaName, databaseName, currentPoolName));

                // Find all columns for each table...
                DBTable[] dbTables = dbDatabase.getDBTables();

                for (int j = 0; j < dbTables.length; j++)
                {
                    DBTable dbTable = dbTables[j];

                    String tableName = dbTable.getTableName();

                    rs = dbMetaData.getColumns(null, schemaName, tableName, null);

                    while (rs.next())
                    {
                        String columnName = rs.getString("COLUMN_NAME");
                        int columnType = rs.getShort("DATA_TYPE");
                        int columnSize = rs.getInt("COLUMN_SIZE");
                        int decimalDigits = DBColumn.DEFAULT_DECIMAL_DIGIT;

                        try
                        {
                            decimalDigits = rs.getInt("DECIMAL_DIGITS");
                        }
                        catch(Exception e)
                        {
                            decimalDigits = DBColumn.DEFAULT_DECIMAL_DIGIT;
                        }

                        DBColumn dbColumn = new DBColumn();
                        dbColumn.setColumnName(columnName);
                        dbColumn.setFullyQualifiedColumnName(DBUtil.formatColumnName(schemaName, databaseName, tableName, columnName));
                        dbColumn.setColumnSQLType(columnType);
                        dbColumn.setDBTable(dbTable);
                        dbColumn.setColumnSize(columnSize);
                        dbColumn.setDecimalDigits(decimalDigits);

                        dbTable.addDBColumn(dbColumn);

                        if (columnName.endsWith("FK") || columnName.endsWith("PK"))
                        {
                            allFKPKDBColumns.put(dbDatabase.getDatabaseName() + "." + tableName + "." + columnName, dbColumn);
                        }
                    }

                    rs.close();
                }

                dbDatabaseList.add(dbDatabase);

            }
            catch (Exception e)
            {
              System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use Options | File Templates.

                throw new RuntimeException(e.getMessage());
            }
            finally
            {
                try
                {
                    if (conn != null) conn.close();
                }
                catch (SQLException e)
                {
                    System.out.println(e);

                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                    throw new RuntimeException(e);
                }
            }
        }
    }

    public DBDatabase()
    {
        dbTableList = new ArrayList();
    }

    public String getDatabaseName()
    {
        return this.databaseName;
    }

    public String getDatabaseServiceName()
    {
        if (databaseServiceName == null)
        {
            databaseServiceName = Util.fastTokenizer(poolName, "-")[0].toUpperCase();
        }

        return databaseServiceName;
    }

    public void setDatabaseName(String databaseName)
    {
        this.databaseName = databaseName;
    }

    public DatabaseVersion getDatabaseVersion()
    {
        return this.databaseVersion;
    }

    public void setDatabaseVersion(DatabaseVersion databaseVersion)
    {
        this.databaseVersion = databaseVersion;
    }

    public void setPoolName(String poolName)
    {
        this.poolName = poolName;
    }

    public String getPoolName()
    {
        return this.poolName;
    }

    public void addDBTable(DBTable dbTable)
    {
        dbTableList.add(dbTable);
    }

    public DBTable getDBTable(String tableName)
    {
        for (int i = 0; i < dbTableList.size(); i++)
        {
            DBTable dbTable = (DBTable) dbTableList.get(i);

            String dbTableName = dbTable.getTableName();

            if (tableName.equals(dbTableName))
            {
                return dbTable;
            }
        }

        return null;
    }

    public DBTable[] getDBTables()
    {
        return (DBTable[]) dbTableList.toArray(new DBTable[dbTableList.size()]);
    }

    public static DBDatabase[] getDBDatabases()
    {
        return (DBDatabase[]) dbDatabaseList.toArray(new DBDatabase[dbDatabaseList.size()]);
    }

    public static DBDatabase getDBDatabaseForTable(String tableName)
    {
        DBDatabase dbDatabase = null;

        String poolName = (String) dbPoolByTableName.get(tableName);

        if (poolName != null)
        {
            for (int i = 0; i < dbDatabaseList.size(); i++)
            {
                if (poolName.equals((dbDatabase = (DBDatabase) dbDatabaseList.get(i)).poolName))
                {
                    break;
                }
            }
        }

        return dbDatabase;
    }

    public static DBTable[] findAllDBTables()
    {
        DBDatabase[] dbDatabases = getDBDatabases();

        List allDBTables = new ArrayList();

        for (int i = 0; i < dbDatabases.length; i++)
        {
            DBTable[] dbTables = dbDatabases[i].getDBTables();

            for (int j = 0; j < dbTables.length; j++)
            {
                allDBTables.add(dbTables[j]);
            }
        }

        return (DBTable[]) allDBTables.toArray(new DBTable[allDBTables.size()]);
    }

    public static Map getAllFKPKDBColumns()
    {
        return allFKPKDBColumns;
    }

    private boolean containsDBTable(String tableName)
    {
        boolean containsTable = false;

        for (int i = 0; i < dbTableList.size(); i++)
        {
            String currentTableName = ((DBTable) dbTableList.get(i)).getTableName();

            if (currentTableName.equals(tableName))
            {
                containsTable = true;

                break;
            }
        }

        return containsTable;
    }

    /**
     * Finds the database name for DBMSes that don't support catalogs
     * <P>
     * For Oracle, it gets the SID from the URL.
     *          Ex:
     *          dbMetaData.getDatabaseProductName() = Oracle
     *          dbMetaData.getURL() = jdbc:oracle:thin:@SEG-DATABASE:1521:orcl
     *
     * @param dbMetaData
     *
     * @return databaseName
     */
    private static String getDatabaseNameWithoutUsingCatalogs(DatabaseMetaData dbMetaData) throws Exception
    {
        String databaseName = null;

        if (dbMetaData.getDatabaseProductName().equalsIgnoreCase("Oracle"))
        {
            String url = dbMetaData.getURL();

            String[] tokens = Util.fastTokenizer(url, ":");

            databaseName = tokens[tokens.length - 1];   // last token
        }

        return databaseName;
    }
}
