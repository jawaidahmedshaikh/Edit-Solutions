/*
 * User: sdorman
 * Date: Jul 5, 2007
 * Time: 4:54:10 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.services.component;

import org.dom4j.*;
import org.dom4j.tree.*;
import fission.utility.*;
import edit.services.db.*;
import edit.services.business.*;

/**
 * Component that implments the SEGMetaData interface
 * @see SEGMetaData
 */
public class SEGMetaDataComponent implements SEGMetaData
{
    /**
     * @see SEGMetaData#getStubbedTableDocument(org.dom4j.Document)
     */
    public Document getStubbedTableDocument(Document requestDocument)
    {
        Document document = new DefaultDocument();

        Element rootElement = new DefaultElement("SEGResponseVO");

        Element requestParametersElement = XMLUtil.getElementFromRoot(requestDocument, "RequestParameters");

        Element tableNameElement = requestParametersElement.element("TableName");

        String tableName = tableNameElement.getText();

        Element newElement = new DefaultElement(tableName + "VO");

        DBTable dbTable = DBTable.getDBTableForTable(tableName);

        DBColumn[] dbColumns = dbTable.getDBColumns();

        for (int i = 0; i < dbColumns.length; i++)
        {
            DBColumn dbColumn = dbColumns[i];

            String columnName = dbColumn.getColumnName();

            Element columnElement = new DefaultElement(columnName);

            newElement.add(columnElement);
        }

        rootElement.add(newElement);
        document.setRootElement(rootElement);

        return document;
    }
}
