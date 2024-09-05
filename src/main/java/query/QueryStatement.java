/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package query;

import edit.services.db.hibernate.SessionHelper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.xalan.lib.sql.QueryParameter;
import org.dom4j.Element;
import org.hibernate.HibernateException;

/**
 * Executes the specified hql and specified String-based name/value pairs
 * against the DB. Results are returned as Dom4J Elements in row form.
 * @author gfrosti
 */
public class QueryStatement
{
    /**
     * The hql to executeQuery.
     */
    private String hql;
    /**
     * The targeted DB.
     * @see SessionHelper#EDITSOLUTIONS
     * @see SessionHelper#ENGINE
     */
    private String targetDB;
    /**
     * The maximum number of rows to return.
     * Defaults to 0 suggesting that there is no limit.
     * Any number > 0 will restrict the result size.
     */
    private int maxResults = 0;

    /**
     * QueryStatments executed as hql will
     * have their results marshalled as an
     * entity composition (e.g. SegmentVO.InvestmentVO etc.).
     *
     * Query statements executed as sql will
     * have their results marshalled as RowVOs.
     *
     * The default is NOT as RowVOs.
     */
    private boolean marshalAsRows = false;

    /**
     * The named parameters to use in the hql statement.
     */
    private Map<String, String> queryParameters = new HashMap();

    public QueryStatement()
    {

    }

    public QueryStatement(String hql, String targetDB, int maxResults)
    {
        this.hql = hql;

        this.targetDB = targetDB;

        this.maxResults = maxResults;
    }

    /**
     * @see #marshalAsRows
     * @return
     */
    public boolean getMarshalAsRows()
    {
        return marshalAsRows;
    }

    /**
     * @see #marshalAsRows
     * @param marshalAsRows
     */
    public void setMarshalAsRows(boolean marshalAsRows)
    {
        this.marshalAsRows = marshalAsRows;
    }

    /**
     * Executes the hql returning the results in row form.
     * Each row has the following format:
     * @param Map<String, String> namedparameters the named-value pairs used within the hql query
     * @return
     * <RowVO>
     *  <ColumnVO>
     *      <Name/> // The actual column name as defined in the DB
     *      <Type/> // The Java data type
     *      <Value/> // The value in String format
     *  </ColumnVO>
     * </RowVO>
     */
    public QueryResult executeQuery() throws SQLException, HibernateException
    {
        QueryResult queryResult = null;

        if (getMarshalAsRows() == true)
        {
            queryResult = SessionHelper.executeHQLAsSQL(getHql(), getQueryParameters(), getTargetDB(), getMaxResults());
        }
        else
        {
            queryResult = SessionHelper.executeHQLAsHQL(getHql(), getQueryParameters(), getTargetDB(), getMaxResults());
        }

        return queryResult;
    }

    /**
     * @return the hql
     */
    public String getHql()
    {
        return hql;
    }

    /**
     * @param hql the hql to set
     */
    public void setHql(String hql)
    {
        this.hql = hql;
    }

    /**
     * @return the maxResults
     */
    public int getMaxResults()
    {
        return maxResults;
    }

    /**
     * @param maxResults the maxResults to set
     */
    public void setMaxResults(int maxResults)
    {
        this.maxResults = maxResults;
    }

    /**
     * @return the targetDB
     */
    public String getTargetDB()
    {
        return targetDB;
    }

    /**
     * @param targetDB the targetDB to set
     */
    public void setTargetDB(String targetDB)
    {
        this.targetDB = targetDB;
    }

    /**
     * @see #queryParameters
     * @return
     */
    public Map<String, String> getQueryParameters()
    {
        return this.queryParameters;
    }

    /**
     * @param namedParameters the namedParameters to set
     */
    public void setQueryParameters(Map<String, String> queryParameters)
    {
        this.queryParameters = queryParameters;
    }

    /**
     * Unmarshals assuming the specified format:
			<QueryStatementVO>
                <Hql></Hql>
				<TargetDB></TargetDB>
				<MaxResults></MaxResults>
                <QueryParameterVO>
                    <Name></Name>
					<Value></Value>
				</QueryParameterVO>;
			</QueryStatementVO>
     * @param queryStatementVOElement
     */
    public void unmarshal(Element queryStatementVOElement)
    {
        if (queryStatementVOElement.element("Hql") != null)
        {
            this.setHql(queryStatementVOElement.element("Hql").getText());
        }

        if (queryStatementVOElement.element("TargetDB") != null)
        {
            this.setTargetDB(queryStatementVOElement.elementText("TargetDB"));
        }

        if (queryStatementVOElement.element("MaxResults") != null)
        {
            this.setMaxResults(Integer.parseInt(queryStatementVOElement.element("MaxResults").getText()));
        }

        if (queryStatementVOElement.element("MarshalAsRows") != null)
        {
            this.setMarshalAsRows(queryStatementVOElement.element("MarshalAsRows").getText().equals("true")?true:false);
        }

        if (queryStatementVOElement.element("QueryParameterVO") != null)
        {
            List<Element> queryParameterVOElements = queryStatementVOElement.elements("QueryParameterVO");

            for (Element queryParameterVOElement:queryParameterVOElements)
            {
                getQueryParameters().put(queryParameterVOElement.element("Name").getText(), queryParameterVOElement.element("Value").getText());
            }
        }
    }
}
