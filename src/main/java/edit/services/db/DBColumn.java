package edit.services.db;

import java.math.BigDecimal;

import java.sql.Types;


/**
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Mar 7, 2003
 * Time: 10:32:30 AM
 * To change this template use Options | File Templates.
 */
public class DBColumn
{
    public static final int DEFAULT_DECIMAL_DIGIT = -1;
    private String columnName;
    private String fullyQualifiedColumnName;
    private DBTable dbTable;
    private int columnSQLType;
    private int columnSize;

    /**
     * Number of digits for the decimal, -1 if it does not apply.
     */
    private int decimalDigits = DEFAULT_DECIMAL_DIGIT;

    public DBColumn()
    {
    }

    public void setColumnSize(int columnSize)
    {
        this.columnSize = columnSize;
    }

    public int getColumnSize()
    {
        return columnSize;
    }

    public void setColumnSQLType(int columnSQLType)
    {
        this.columnSQLType = columnSQLType;
    }

    public int getColumnSQLType()
    {
        return columnSQLType;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setFullyQualifiedColumnName(String fullyQualifiedColumnName)
    {
        this.fullyQualifiedColumnName = fullyQualifiedColumnName;
    }

    public String getFullyQualifiedColumnName()
    {
        return fullyQualifiedColumnName;
    }

    public void setDBTable(DBTable dbTable)
    {
        this.dbTable = dbTable;
    }

    public DBTable getDBTable()
    {
        return dbTable;
    }

    public static Class convertSQLToJavaType(int sqlType)
    {
        //        System.out.println("sqlType = " + sqlType);
        switch (sqlType)
        {
        case java.sql.Types.CHAR:

            //                System.out.println("CHAR");
            return String.class;

        case java.sql.Types.VARCHAR:

            //                System.out.println("VARCHAR");
            return String.class;

        case java.sql.Types.LONGVARCHAR:

            //                System.out.println("LONGVARCHAR");
            return String.class;

        case java.sql.Types.NUMERIC:

            //                System.out.println("NUMERIC");
            return BigDecimal.class;

        case java.sql.Types.DECIMAL:

            //                System.out.println("DECIMAL");
            return BigDecimal.class;

        case java.sql.Types.BIT:

            //                System.out.println("BIT");
            return boolean.class;

        case java.sql.Types.TINYINT:

            //                System.out.println("TINYINT");
            return byte.class;

        case java.sql.Types.SMALLINT:

            //                System.out.println("SMALLINT");
            return short.class;

        case java.sql.Types.INTEGER:

            //                System.out.println("INTEGER");
            return int.class;

        case java.sql.Types.BIGINT:

            //                System.out.println("BIGINT");
            return long.class;

        case java.sql.Types.REAL:

            //                System.out.println("REAL");
            return float.class;

        case java.sql.Types.FLOAT:

            //                System.out.println("FLOAT");
            return double.class;

        case java.sql.Types.DOUBLE:

            //                System.out.println("DOUBLE");
            return double.class;

        case java.sql.Types.BINARY:

            //                System.out.println("BINARY");
            return byte[].class;

        case java.sql.Types.VARBINARY:

            //                System.out.println("VARBINARY");
            return byte[].class;

        case java.sql.Types.LONGVARBINARY:

            //                System.out.println("LONGVARBINARY");
            return byte[].class;

        case java.sql.Types.DATE:

            //                System.out.println("DATE");
            return java.sql.Date.class;

        case java.sql.Types.TIME:

            //                System.out.println("TIME");
            return java.sql.Time.class;

        case java.sql.Types.TIMESTAMP:

            //                System.out.println("TIMESTAMP");
            return java.sql.Timestamp.class;

        default:
            throw new RuntimeException("DBColumn: Unable to map sql to java datatype.");
        }
    }

    /**
     * Determines if the column is a date or a date and time.
     *
     * The SQL type comes in as a TIMESTAMP for SQLServer (Datetime field) and a DATE for Oracle (Date field) (depending on the driver)
     *
     * @param dbColumn
     *
     * @return true if the column's database type is a date or a date and time, false otherwise
     */
    public boolean isDateOrDateTime()
    {
        if ((this.getColumnSQLType() == Types.TIMESTAMP) || (this.getColumnSQLType() == Types.DATE))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Determines if the column is a dateTime field
     *
     * The only way to do this at this time is to use the column name - if it ends with the string "DateTime", we are
     * using it as a date and time field.
     *
     * @param dbColumn
     *
     * @return true if the field should contain both a date and a time
     */
    public boolean isDateTime()
    {
        if (this.getColumnName().endsWith("DateTime"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Setter.
     * @param decimalDigits
     */
    public void setDecimalDigits(int decimalDigits)
    {
        this.decimalDigits = decimalDigits;
    }

    /**
     * Getter.
     * @return
     */
    public int getDecimalDigits()
    {
        return decimalDigits;
    }
}
