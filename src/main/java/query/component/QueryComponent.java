/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * User: gfrosti
 * Date: Jan 15, 2009
 * Time: 10:32:16 AM
 *
 * (c) 2000-2009 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package query.component;

import edit.services.db.hibernate.SessionHelper;
import fission.utility.*;
import org.dom4j.Document;
import org.dom4j.Element;
import webservice.SEGRequestDocument;
import webservice.SEGResponseDocument;
import query.*;



public class QueryComponent implements query.business.Query
{
    /**
     * @see query.business.Query#saveQuery(org.dom4j.Document)
     */
    public Document saveQuery(Document requestDocument)
    {
        //  Initialize response
        SEGResponseDocument responseDocument = new SEGResponseDocument();

        //  Get input information
        Element queryVOElement = SEGRequestDocument.getRequestParameter(requestDocument, "QueryVO");

        try
        {
            SessionHelper.beginTransaction(Query.DATABASE);

            Query query = new Query();

            SessionHelper.mapToHibernateEntity(query, queryVOElement, Query.DATABASE, true);

            if (!query.nameIsUnique())
            {
                responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, "A Query with the name [" + query.getName() + "] already exists.");

                SessionHelper.rollbackTransaction(Query.DATABASE);
            }
            else
            {
                SessionHelper.saveOrUpdate(query, Query.DATABASE);
                SessionHelper.commitTransaction(Query.DATABASE);

                responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Query [" + query.getName() + "] successfully saved");
            }
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, Util.initString(e.getMessage(), "N/A"));

            SessionHelper.rollbackTransaction(Query.DATABASE);
        }

        return responseDocument.getDocument();
    }

    /**
     * @see query.business.Query#executeHQL(org.dom4j.Document)
     */
    public Document executeQuery(Document requestDocument)
    {
        //  Initialize response
        SEGResponseDocument responseDocument = new SEGResponseDocument();

        //  Get input information
        Element queryStatementVOElement = SEGRequestDocument.getRequestParameter(requestDocument, "QueryStatementVO");

        QueryStatement queryStatement = new QueryStatement();

        queryStatement.unmarshal(queryStatementVOElement);

        try
        {
            QueryResult queryResult = queryStatement.executeQuery();

            responseDocument.addToRootElement(queryResult.marshal(queryStatement.getMarshalAsRows()));

            responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_SUCCESS, "Query statement [" + queryStatement.getHql() + "] successfully executed");
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            responseDocument.addResponseMessage(SEGResponseDocument.MESSAGE_TYPE_ERROR, Util.initString(e.getMessage(), "N/A"));
        }

        return responseDocument.getDocument();
    }
}
