/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package query;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * An column element of a QueryRow.
 * @author gfrosti
 */
class QueryColumn
{
    /**
     * The name, as defined in the DB, of this column.
     */
    private String name;

    /**
     * The datatype of this column expressed as a String.
     */
    private String type;

    /**
     * The value of this column expressed as a String. It could be null.
     */
    private String value;

    QueryColumn(ResultSet resultSet, int columnIndex, String columnName) throws SQLException
    {
        this.name = columnName;

        parseColumn(resultSet, columnIndex);
    }

    /**
     * From the specified parameters builds the following Element structure:
     * @param columnName
     * @param columnTypeName
     * @param columnValue
     * @return
     *  <ColumnVO>
     *      <Name/> // The actual column name as defined in the DB
     *      <Type/> // The Java data type
     *      <Value/> // The value in String format
     *  </ColumnVO>
     */
    private void parseColumn(ResultSet rs, int columnIndex) throws SQLException
    {
        setType(rs.getMetaData().getColumnTypeName(columnIndex + 1));

        setValue(rs.getString(columnIndex + 1));
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the type
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * @return the value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * Builds the structure:
     * <QueryColumnVO>
     *  <Name/>
     *  <Type/>
     *  <Value/>
     * </QueryColumnVO>
     * @return
     */
    public Element marshal()
    {
        // QueryColumnVO
        Element queryColumnVOElement = new DefaultElement("QueryColumnVO");

        // Name
        Element nameElement = new DefaultElement("Name");

        nameElement.setText(getName());

        // Type
        Element typeElement = new DefaultElement("Type");

        typeElement.setText(getType());

        // Value
        Element valueElement = null;

        if (getValue() != null)
        {
            valueElement = new DefaultElement("Value");

            valueElement.setText(getValue());
        }

        // Assemble
        queryColumnVOElement.add(nameElement);

        queryColumnVOElement.add(typeElement);

        if (valueElement != null)
        {
            queryColumnVOElement.add(valueElement);
        }

        return queryColumnVOElement;
    }
}
