package edit.services.db;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Apr 18, 2003
 * Time: 2:10:55 PM
 * To change this template use Options | File Templates.
 */
public class DBRecordKey
{
    private final String tableName;
    private final String columnName;

    public DBRecordKey(String tableName, String columnName)
    {
        this.tableName = tableName;
        this.columnName = columnName;
    }

    public final String getTableName()
    {
        return tableName;
    }

    public final String getColumnName()
    {
        return columnName;
    }

    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }

        if (obj == this)
        {
            return true;
        }

        if ( !(obj instanceof DBRecordKey) )
        {
            return false;
        }

        return ( ((DBRecordKey)obj).columnName.equals(columnName) && ((DBRecordKey)obj).tableName.equals(tableName));
    }

    public int hashCode()
    {
        return tableName.hashCode() ^ columnName.hashCode();
    }
}
