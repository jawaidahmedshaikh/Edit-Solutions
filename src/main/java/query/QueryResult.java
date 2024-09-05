/*
 * User: gfrosti
 * Date: Feb 15, 2009
 * Time: 10:45:04 AM
 *
 * (c) 2000-2009 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package query;

import edit.services.db.hibernate.HibernateEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * A user supplies an hql query that gets executed
 * as an sql query. The result of executing such
 * an hql as sql is a JDBC ResultSet and the sql that
 * resulted from the conversion of hql to sql.
 *
 * The QueryResult
 */
public class QueryResult
{
    /**
     * The original hql.
     */
    private String hql;
    /**
     * The converted hql to sql.
     */
    private String sql;
    /**
     * The ordered name(s) of the columns used in the sql. Their indexed
     * position matches the order of the columns in the sql itself.
     */
    private List<String> columnNames = new ArrayList<String>();

    /**
     * Used to stored/cache the RE pattern used to parse the hql to sql
     * String or SQL Server.
     */
    private static Pattern sqlServerPattern;
    /**
     * Used to stored/cache the RE pattern used to parse the hql to sql
     * String or Oracle.
     */
    private static Pattern oraclePattern;
    
    /**
     * The results of the executed query when executed as SQL.
     */
    private List<QueryRow> queryRows = new ArrayList<QueryRow>();

    /**
     * The results as compositions when executed as HQL. Each composition is, in effect,
     * the root of the Hibernate composition. The user is
     * expected to call segMarshallable.marshal(..) if desired.
     */
    private List<HibernateEntity> queryCompositions = new ArrayList<HibernateEntity>();

    /**
     * Constructor used when the query is executed as HQL and
     * the results are marshalled as a DOM4J Element composition.
     * @param hql
     * @param results
     */
    public QueryResult(String hql, List<HibernateEntity> results)
    {
        this.hql = hql;

        parseResultsAsComposition(results);
    }

    /**
     * Constructor used when the query is to be executed as SQL and
     * the results marshalled as rows.
     * @param hql
     * @param sql
     * @param rs
     * @throws java.sql.SQLException
     */
    public QueryResult(String hql, String sql, ResultSet rs) throws SQLException
    {
        this.hql = hql;

        this.sql = sql;

        populateColumnNames();

        parseResultsAsRows(rs);
    }

    /**
     * Attaches the results to the queryCompositions
     * @param results
     */
    private void parseResultsAsComposition(List<HibernateEntity> results)
    {
        if (!results.isEmpty())
        {
            for (Iterator hibernateEntityIterator = results.iterator(); hibernateEntityIterator.hasNext();)
            {
                HibernateEntity hibernateEntity = (HibernateEntity) hibernateEntityIterator.next();

                getQueryCompositions().add(hibernateEntity);
            }
        }
    }

    /**
     * Iterates through the ResultSet building the corresponding QueryRow/QueryColumn structure.
     */
    private void parseResultsAsRows(ResultSet rs) throws SQLException
    {
        while (rs.next())
        {
            QueryRow queryRow = new QueryRow(rs, getColumnNames());

            getQueryRows().add(queryRow);
        }
    }

    /**
     * @return the hql
     */
    public String getHql()
    {
        return hql;
    }

    /**
     * @return the sql
     */
    public String getSql()
    {
        return sql;
    }

    /**
     * @see #queryCompositions
     * @return
     */
    public List<HibernateEntity> getQueryCompositions()
    {
        return queryCompositions;
    }

    /**
     * I was hoping to rely on the ResultSetMetaData to
     * get both the Column Name and Column Alias. However,
     * the hql to sql to PreparedStatement is giving me
     * the Column Alias only. I searched and searched the Hibernate API
     * trying to find something that would readily give me this information,
     * but alas - no luck! This parses the generated sql for the Column Names
     * used in the query. As of this writing, this is working for SQL Server, but
     * will have to be enhanced for other DB vendors.
     * @param index
     * @return
     */
    public String getColumnName(int index) throws SQLException
    {
        return getColumnNames().get(index);
    }

    /**
     * @see #columnNames
     * @return
     */
    public List<String> getColumnNames()
    {
        return this.columnNames;
    }

    /**
     * Determines which Regular Expression Pattern to return
     * based on the DB engine being used.
     * @return
     */
    private Pattern getPattern()
    {
        Pattern p = null;
        // blah blah blah - determine which DB is being used (consider DBDatabase?) and
        // return the correct Pattern. However, I'm just going to assume SQL Server
        // until otherwise needed.

        //if (dbType == ORACLE)
        //{

        //}
        //else if (dbType == SQLSERVER)
        //{
        if (sqlServerPattern == null)
        {
            sqlServerPattern = Pattern.compile("\\[(.+?)\\]");
        }

        p = sqlServerPattern;
        //}

        return p;
    }

    /**
     * Parses the generated hql to sql for the column names. This is grunt work.
     * This first pass assumes SQL Server.
     */
    private void populateColumnNames()
    {
        int indexOfFrom = getSql().indexOf(" from ");

        String targetSubstring = getSql().substring(0, indexOfFrom);

        Pattern p = getPattern();

        Matcher m = p.matcher(targetSubstring);

        while (m.find())
        {
            String columnName = m.group(1);

            getColumnNames().add(columnName);
        }
    }

    /**
     * @return the queryRows
     */
    public List<QueryRow> getQueryRows()
    {
        return queryRows;
    }

    /**
     * @param queryRows the queryRows to set
     */
    public void setQueryRows(List<QueryRow> queryRows)
    {
        this.queryRows = queryRows;
    }

    /**
     * Builds the following structure:
     * <QueryResultVO>
     *  <ColumnName/> // repeated for every Column found in the same order as was used in the sql
     *  <QueryRowVO> // repeated for every row in the result
     *      <QueryColumnVO/> // for every column used in the originating sql statement
     *  </QueryRowVO>
     * </QueryResultVO>
     *
     * or
     *
     * <QueryResultVO>
     *  <FooEntity1VO>
     *      <FooEntity2VO/> // etc for the depth of the composition
     *  </FooEntity1VO>
     * </QueryResultVO>
     * @return
     */
    public Element marshal(boolean marshalAsRows)
    {
        // QueryResultVO
        Element queryResultVOElement = new DefaultElement("QueryResultVO");

        if (marshalAsRows)
        {
            marshalAsRows(queryResultVOElement);
        }
        else
        {
            marshalAsComposition(queryResultVOElement);
        }

        return queryResultVOElement;
    }

    /**
     * Marshal the result as repeated RowVOs.
     * @param queryResultVOElement
     */
    private void marshalAsRows(Element queryResultVOElement)
    {
        // Column Names
        for (String columnName:getColumnNames())
        {
            Element columnNameElement = new DefaultElement("ColumnName");

            columnNameElement.setText(columnName);

            queryResultVOElement.add(columnNameElement);
        }

        // QueryRowVO(s)
        for (QueryRow queryRow:getQueryRows())
        {
            Element queryRowVOElement = queryRow.marshal();

            queryResultVOElement.add(queryRowVOElement);
        }
    }

    /**
     * Marshals the result as a collection of compositions.
     * @param queryResultVOElement
     */
    private void marshalAsComposition(Element queryResultVOElement)
    {
        for (Iterator iterator = this.getQueryCompositions().iterator(); iterator.hasNext();)
        {
            HibernateEntity hibernateEntity = (HibernateEntity) iterator.next();

            Element element = hibernateEntity.marshal(null);

            queryResultVOElement.add(element);
        }
    }
}
