/*
 * User: sdorman
 * Date: Jul 6, 2007
 * Time: 9:30:23 AM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement. 
 */

package edit.services.business;

import org.dom4j.*;

/**
 * Interface to services that provide information about the EDITSolutions system.
 */
public interface SEGMetaData
{
    /**
     * Generates an xml representation of a given database table.  The document is "stubbed out", meaning that the xml fields have
     * no values.
     * <P>
     * @param requestDocument               SEGRequestVO containing the following structure:
     *                                          <SEGRequestVO>
     *                                              <RequestParameters>
     *                                                  <TableName>CodeTable</TableName>
     *                                              </RequestParameters>
     *                                          <SEGRequestVO>
     * <P>
     * @return  SEGResponseVO containing the xml representation of the table
     * <P>
     *                      Ex.
     *                                          <SEGResponseVO>
     *                                              <CodeTableVO>
     *                                                  <CodeTablePK></CodeTablePK>
     *                                                  <CodeTableDefFK></CodeTableDefFK>
     *                                                  <Code></Code>
     *                                                  <CodeDesc></CodeDesc>
     *                                              </CodeTableVO>
     *                                          </SEGResponseVO>
     *
     */
    public Document getStubbedTableDocument(Document requestDocument);
}
