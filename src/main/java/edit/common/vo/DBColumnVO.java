package edit.common.vo;

/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 18, 2003
 * Time: 10:16:01 AM
 * To change this template use Options | File Templates.
 */
public class DBColumnVO
{
    private String columnName;
    private String tableName;
    private String columnType;
    private int columnSize;

    public void setColumnSize(int columnSize)
    {
        this.columnSize = columnSize;
    }

    public int getColumnSize()
    {
        return columnSize;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getColumnType()
    {
        return columnType;
    }

    public void setColumnType(String columnType)
    {
        this.columnType = columnType;
    }
}
