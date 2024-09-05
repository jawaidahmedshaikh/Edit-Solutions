/*
 * User: gfrosti
 * Date: Jan 15, 2009
 * Time: 10:42:14 AM
 *
 * (c) 2000-2009 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package query.business;

import org.dom4j.Document;

/**
 * Users have the ability to create, edit, and run predefined db queries
 * against the system databases. The supported query language is HQL and SQL,
 * although HQL will be the initial implementation. The ability for a user
 * to establish predefined queries offers several advantages. Among these are:
 *
 * 1. A scripter can write and test queries before implementing them within
 * a conversion script. Once executing within a script, the queries are referred
 * to by name.
 *
 * 2. A Business Analyst can define queries often used for their personal
 * data analysis.
 *
 * 3. Clients can check the state of the system.
 *
 * 4. Reports can consume the queries for rendering their documents.
 * @author gfrosti
 */
public interface Query
{
    /**
     * Persists a Query. If the Query is new, it must have a unique name.
     *
     * @param   requestDocument
     *          <SEGRequestVO>
     *              <RequestParameters>
     *                  <QueryVO></QueryVO>
     *              </RequestParameters>
     *          </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *          <SEGResponseVO>
     *              <ResponseMessageVO>
     *                  ...
     *              </ResponseMessageVO>
     *          </SEGResponseVO>
     */
    public Document saveQuery(Document requestDocument);

    /**
     * Front-ends can execute hql directly against the system without the need
     * to expose each query indvidually within a component.
     *
     * @param   requestDocument
     *          <SEGRequestVO>
     *              <RequestParameters>
     *                  <QueryStatementVO>
     *                      <Hql></Hql>
     *                      <TargetDB></TargetDB>
     *                      <MaxResults></MaxResults>
     *                      <ParameterVO> // Repeated for every parameter within the query
     *                          <Name></Name>
     *                          <Value></Value>
     *                      </ParameterVO>
     *                  </QueryStatementVO>
     *              </RequestParameters>
     *          </SEGRequestVO>
     *
     * @return  SEGResponseVO containing the following structure:
     *
     *          <SEGResponseVO>
     *              <ResponseMessageVO>
     *                      <QueryResultVO/> // Repeated for every row of the resultset.
     *              </ResponseMessageVO>
     *          </SEGResponseVO>
     */
    public Document executeQuery(Document requestDocument);
}
