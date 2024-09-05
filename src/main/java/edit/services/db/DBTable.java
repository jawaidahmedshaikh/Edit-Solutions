package edit.services.db;

import fission.utility.Util;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 7, 2003
 * Time: 10:25:27 AM
 * To change this template use Options | File Templates.
 */
public class DBTable
{
    private String tableName;

    private String fullyQualifiedTableName;

    private DBDatabase dbDatabase;

    private List dbColumnList;

    private Map parentTables;
    private Map adjacentTables;


    public DBTable()
    {
        dbColumnList = new ArrayList();
        parentTables = new HashMap();
        adjacentTables = new HashMap();
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setFullyQualifiedTableName(String fullyQualifiedTableName)
    {
        this.fullyQualifiedTableName = fullyQualifiedTableName;
    }

    public String getFullyQualifiedTableName()
    {
        return fullyQualifiedTableName;
    }

    public void addDBColumn(DBColumn dbColumn)
    {
        dbColumnList.add(dbColumn);
    }

    public void setDBDatabase(DBDatabase dbDatabase)
    {
        this.dbDatabase = dbDatabase;
    }

    public DBDatabase getDBDatabase()
    {
        return dbDatabase;
    }

    public static DBTable getDBTableForTable(String tableName)
    {
        DBTable dbTable = null;
    
        DBDatabase dbDatabase = DBDatabase.getDBDatabaseForTable(tableName);
        
        if (dbDatabase != null)
        {
          dbTable = dbDatabase.getDBTable(tableName);
        }

        return dbTable;
    }

    public DBColumn[] getDBColumns()
    {
        return (DBColumn[]) dbColumnList.toArray(new DBColumn[dbColumnList.size()]);
    }

    public DBColumn getDBColumn(String columnName)
    {
        DBColumn dbColumn = null;

        boolean foundColumn = false;

        for (int i = 0; i < dbColumnList.size(); i++)
        {
            dbColumn = (DBColumn) dbColumnList.get(i);

            if (dbColumn.getColumnName().equals(columnName))
            {
                foundColumn = true;
                break;
            }
        }

        if (foundColumn)
        {
            return dbColumn;
        }
        else
        {
            throw new RuntimeException(
                    "DBTable.getDBColumn: cannot find column named '"
                    + columnName + "' in table " + tableName);
        }

        // BACK OUT THIS CHECK FOR NOW 4-13-2005
        // UNTIL THE PROBLEM QUERIES CAN BE TESTED MORE THOROUGHLY
        //
        //    if (foundColumn)
        //    {
        //        return dbColumn;
        //    }
        //    else
        //    {
        //        // this used to return dbColumn regardless - if not there
        //        // it was the last dbColumn checked.
        //        throw new RuntimeException(
        //                "DBTable.getDBColumn: cannot find column named '"
        //                + columnName + "' in table " + tableName);
        //    }

    }

    public Map getParentTables()
    {
        if (this.parentTables.isEmpty())
        {
            findNeighboringTables();
        }

        return this.parentTables;
    }

    public Map getAdjacentTables()
    {
        if (this.adjacentTables.isEmpty())
        {
            findNeighboringTables();
        }

        return this.adjacentTables;
    }

    private void findNeighboringTables()
    {
        Iterator fkPKKeys = DBDatabase.getAllFKPKDBColumns().keySet().iterator();

        int count = 0;
        while (fkPKKeys.hasNext())
        {
            String[] tokenizedFKPKKeys = Util.fastTokenizer(fkPKKeys.next().toString(), ".");
            String keyDatabase = tokenizedFKPKKeys[0];
            String keyTable = tokenizedFKPKKeys[1];
            String keyColumn = tokenizedFKPKKeys[2];

//            System.out.println("count = " + count++ + ", keyTable = " + keyTable + ", keyColumn = " + keyColumn);

            // Look for FKs of current table (parents)
            if (keyTable.equals(this.tableName) && keyColumn.endsWith("FK"))
            {
                String parentTrueTableName = keyColumn.substring(0, keyColumn.length() - 2);

                DBDatabase dbDatabase = DBDatabase.getDBDatabaseForTable(parentTrueTableName);

                if (dbDatabase != null)     // if null, probably found an FK that does not correspond to a table
                {
                    if (dbDatabase.getDatabaseName() != null)
                    {
                        DBTable dbTable = dbDatabase.getDBTable(parentTrueTableName);

                        this.parentTables.put(parentTrueTableName, dbTable);
                    }
                }
            }

            // Look for all other tables with FKs referring to current table (adjacents)
            if (keyColumn.equals(this.tableName + "FK"))
            {
                DBDatabase dbDatabase = DBDatabase.getDBDatabaseForTable(keyTable);

                if (dbDatabase.getDatabaseName() != null)
                {
                    DBTable dbTable = dbDatabase.getDBTable(keyTable);

                    this.adjacentTables.put(keyTable, dbTable);
                }
            }
        }
    }
}
