/*
 * User: dlataill
 * Date: Feb 6, 2002
 * Time: 3:53:07 PM
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package accounting.dm.dao;


import edit.common.vo.*;
import edit.services.db.*;

import java.util.List;
import java.sql.*;


public class ElementDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    List voExclusionList = null;


    public ElementDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE   = DBTable.getDBTableForTable("Element");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
    }

    public ElementVO[] findAllElements()
    {
        String sql = "SELECT * FROM " + TABLENAME;

        return (ElementVO[]) executeQuery(ElementVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }

    public ElementVO[] findAllElementsForReport()
    {
        String processCol        = DBTABLE.getDBColumn("Process").getFullyQualifiedColumnName();
        String eventCol          = DBTABLE.getDBColumn("Event").getFullyQualifiedColumnName();
        String eventTypeCol      = DBTABLE.getDBColumn("EventType").getFullyQualifiedColumnName();
        String elementNameCol    = DBTABLE.getDBColumn("ElementName").getFullyQualifiedColumnName();
        String effectiveDateCol  = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();
        String sequenceNumberCol = DBTABLE.getDBColumn("SequenceNumber").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " ORDER BY " + processCol + ", " + eventCol + ", " + eventTypeCol + ", " + elementNameCol + ", " + effectiveDateCol + ", " + sequenceNumberCol;

        return (ElementVO[]) executeQuery(ElementVO.class,
                                           sql,
                                            POOLNAME,
                                             true,
                                              null);
    }

    public ElementVO[] findAllElementsInElementIdArray(Long[] elementIdArray)
    {
        String sqlIn = new String();

        for (int i = 0; i < elementIdArray.length; i++)
        {
            sqlIn = sqlIn + elementIdArray[i].longValue();

            if (i < elementIdArray.length - 1)
            {
                sqlIn = sqlIn + ", ";
            }
        }

        String elementPKCol = DBTABLE.getDBColumn("ElementPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + elementPKCol + " IN (" + sqlIn + ")";

        return (ElementVO[]) executeQuery(ElementVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }

    public ElementVO[] findElementsByElementNameAndEffDate(String elementName,
                                                            String effDate)
    {
        ElementVO[] elementVOs = null;

        String elementNameCol   = DBTABLE.getDBColumn("ElementName").getFullyQualifiedColumnName();
        String effectiveDateCol = DBTABLE.getDBColumn("EffectiveDate").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + elementNameCol + " = '" + elementName + "'" +
                     " AND " + effectiveDateCol + " <= ?" +
                     " ORDER BY " + effectiveDateCol + " ASC";

        Connection conn = null;

        PreparedStatement ps = null;

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            ps = conn.prepareStatement(sql);

            ps.setDate(1, DBUtil.convertStringToDate(effDate));

            elementVOs = (ElementVO[]) executeQuery(ElementVO.class,
                                                                ps,
                                                                POOLNAME,
                                                                true,
                                                                null);
        }
        catch (Exception e)
        {
            System.out.println(e);

            e.printStackTrace();

            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (ps != null) ps.close();

                ps = null;

                if (conn != null) conn.close();

                conn = null;
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();

                throw new RuntimeException(e);
            }
        }

        return elementVOs;
    }

    public ElementVO[] findByElementId(long elementId)
    {
        String elementPKCol = DBTABLE.getDBColumn("ElementPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + elementPKCol + " = " + elementId;

        return (ElementVO[]) executeQuery(ElementVO.class,
                                           sql,
                                            POOLNAME,
                                             true,
                                              null);
    }
	
    public ElementVO[] findAllNames()
    {
    	String sql = "SELECT * FROM " + TABLENAME;

        return (ElementVO[]) executeQuery(ElementVO.class,
                                           sql,
                                            POOLNAME,
                                             false,
                                              null);
    }
}
