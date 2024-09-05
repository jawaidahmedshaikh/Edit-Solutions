/*
 * User: gfrosti
 * Date: Jan 15, 2009
 * Time: 10:32:04 AM
 *
 * (c) 2000-2009 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package query;

import edit.services.db.hibernate.HibernateEntity;
import edit.services.db.hibernate.SessionHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.*;

/**
 * BA's often have a need to define queries that can be executed realtime against
 * the system. It is useful to define and store these queries to
 * be executed independently at some later time. Some examples of their use
 * may be:
 *
 * 1. During a Conversion, the scripter often needs to retrieve data back from the DB.
 * 2. Reports and Correspondence.
 * 3. A often-repeated ad-hoc query that a BA may wish to store.
 *
 * Since the system is largely grounded in Hibernate, we are consolidating on
 * using hql, although this is not an ultimate restriction in that sql may also
 * be ultimately used. Hql has proven intuitive even for the BAs once they have
 * some initial queries from which to copy and paste. Hql's easy use of
 * parameters is also useful.
 *
 */
public class Query extends HibernateEntity
{
    /**
     * Database this HibernateEntity is mapped to
     */
    public static String DATABASE = SessionHelper.ENGINE;


    /**
     * Defines that hql will be the target query language.
     */
    public static String TYPE_HQL = "HQL";
    /**
     * Defines that sql will be the target query language.
     */
    public static String TYPE_SQL = "SQL";

    /**
     * A unique name to identify the query. It must
     * match normal "java" naming standards (e.g. no spaces, etc.).
     */
    private String name;
    /**
     * A meaningful free-form description.
     */
    private String description;
    /**
     * HQL or SQL.
     * @see #TYPE_HQL
     * @see #TYPE_SQL
     */
    private String type;
    /**
     * The expression defined in the target language (almost certainly hql).
     */
    private String expression;

    /**
     * The database against which to execute any query.
     * @see SessionHelper#EDITSOLUTIONS
     * @see SessionHelper#ENGINE
     */
    private String databaseName;

    /**
     * PK
     */
    private Long queryPK;

    public Query()
    {

    }

    public Long getQueryPK()
    {
        return this.queryPK;
    }

    public void setQueryPK(Long queryPK)
    {
        this.queryPK = queryPK;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @see #name
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @see #description
     * @param description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the type
     */
    public String getType()
    {
        return type;
    }

    /**
     * @see #type
     * @param type
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * @return the expression
     */
    public String getExpression()
    {
        return expression;
    }

    /**
     * @see #expression
     * @param expression
     */
    public void setExpression(String expression)
    {
        this.expression = expression;
    }

    /**
     * @param database the database to execute queries against
     */
    public void setDatabaseName(String databaseName)
    {
        this.databaseName = databaseName;
    }

    /**
     * @return the databaseName
     */
    public String getDatabaseName()
    {
        return databaseName;
    }

    /**
     * Determines if this Query has a unique name. This would mostly apply
     * to a transient Query trying to be saved. It needs to confirm that
     * it is being saved with a unique name.
     * @return
     */
    public boolean nameIsUnique()
    {
        boolean nameIsUnique = false;

        if (SessionHelper.isPersisted(this))
        {
            nameIsUnique = true;
        }
        else
        {
            Query query = Query.findBy_Name(this.getName());

            nameIsUnique = (query == null);
        }

        return nameIsUnique;
    }

    /**
     * Finds a single Query based on the name which must be unique
     *
     * @param name
     *
     * @return  single Query object
     */
    public static Query findBy_Name(String name)
    {
        Query query = null;

        String hql = "from Query query where query.Name = :name";

        Map params = new HashMap();

        params.put("name", name);

        List<Query> results = SessionHelper.executeHQL(hql, params, Query.DATABASE);

        if (!results.isEmpty())
        {
            query = results.get(0); // Should only ever be one.
        }

        return query;
    }

    /**
     * Finds all Query objects in persistence
     *
     * @return  array of Query objects
     */
    public static Query[] findAllQueries()
    {
        String hql = "from Query query";

        Map params = new HashMap();

        List<Query> results = SessionHelper.executeHQL(hql, params, Query.DATABASE);

        return results.toArray(new Query[results.size()]);
    }

    /**
     * Marshal this object as an Element
     *
     * @param version
     * @return
     */
    public Element marshal(String version)
    {
        return this.getAsElement();
    }
}
