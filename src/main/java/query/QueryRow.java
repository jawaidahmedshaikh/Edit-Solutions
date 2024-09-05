/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * Represents a single row in the results of having executed a QueryStatement.
 * @see QueryStatement
 * @author gfrosti
 */
class QueryRow
{
    /**
     * The columns of this row.
     */
    private List<QueryColumn> queryColumns = new ArrayList<QueryColumn>();

    QueryRow(ResultSet resultSet, List<String> columnNames) throws SQLException
    {
        parseRow(resultSet, columnNames);
    }

    /**
     * @return the queryColumns
     */
    public List<QueryColumn> getQueryColumns()
    {
        return queryColumns;
    }

    /**
     * @param queryColumns the queryColumns to set
     */
    public void setQueryColumns(List<QueryColumn> queryColumns)
    {
        this.queryColumns = queryColumns;
    }

    /**
     * Builds the corresponding QueryRow for the current cursor position of
     * the specified ResultSet.
     * @param resultSet
     * @param columnCount
     */
    private void parseRow(ResultSet resultSet, List<String> columnNames) throws SQLException
    {
        int columnCount = columnNames.size();

        for (int columnIndex = 0; columnIndex < columnCount; columnIndex++)
        {
            QueryColumn queryColumn = new QueryColumn(resultSet, columnIndex, columnNames.get(columnIndex));

            getQueryColumns().add(queryColumn);
        }
    }

    /**
     * Builds the structure:
     * <QueryRowVO>
     *  <QueryColumnVO/> // repeated for every column in the original sql
     * </QueryRowVO>
     * @return
     */
    public Element marshal()
    {
        // QueryRowVO
        Element queryRowVOElement = new DefaultElement("QueryRowVO");

        // QueryColumnVO(s)
        for (QueryColumn queryColumn:getQueryColumns())
        {
            Element queryColumnVOElement = queryColumn.marshal();

            queryRowVOElement.add(queryColumnVOElement);
        }

        return queryRowVOElement;
    }
}
