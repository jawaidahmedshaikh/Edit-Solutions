/*
 * Created by IntelliJ IDEA.
 * User: dlataill
 * Date: Jan 24, 2002
 * Time: 3:42:04 PM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package accounting.dm.dao;

import edit.common.vo.ElementCompanyRelationVO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DAO;
import edit.services.db.DBTable;
import edit.services.logging.Logging;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ElementCompanyRelationDAO extends DAO {
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    static Logger logger = Logging.getLogger(Logging.GENERAL_EXCEPTION);
    private static ConcurrentHashMap<Long, List<ElementCompanyRelationVO>> elementCompanyRelations = new ConcurrentHashMap<>();

    static {
        loadElementCompanyRelations();
    }

    private static void loadElementCompanyRelations() {

        String sql = "SELECT\n" +
                "  ElementCompanyRelationPK,\n" +
                "  ProductStructureFK,\n" +
                "  ElementFK\n" +
                "FROM EDIT_SOLUTIONS.dbo.\"ElementCompanyRelation\"";

        try (Connection conn = ConnectionFactory.getSingleton().getConnection(ConnectionFactory.EDITSOLUTIONS_POOL)) {
            try (Statement s = conn.createStatement()) {
                try (ResultSet rs = s.executeQuery(sql)) {
                    while (rs.next()) {
                        ElementCompanyRelationVO relationVO = new ElementCompanyRelationVO();
                        List<ElementCompanyRelationVO> relationVOS = elementCompanyRelations.get(rs.getLong("ElementFK"));
                        if (relationVOS == null) {
                            relationVOS = new ArrayList<>();
                        }

                        relationVO.setProductStructureFK(rs.getLong("ProductStructureFK"));
                        relationVO.setElementCompanyRelationPK(rs.getLong("ElementCompanyRelationPK"));
                        relationVO.setElementFK(rs.getLong("ElementFK"));
                        relationVOS.add(relationVO);
                        elementCompanyRelations.put(rs.getLong("ElementFK"), relationVOS);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e);
            logger.error("SQL: " + sql);
            System.out.println(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public ElementCompanyRelationDAO() {
        POOLNAME = ConnectionFactory.EDITSOLUTIONS_POOL;
        DBTABLE = DBTable.getDBTableForTable("ElementCompanyRelation");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();
        elementCompanyRelations = new ConcurrentHashMap<>();
        if (elementCompanyRelations.isEmpty()) {
            ElementCompanyRelationDAO.loadElementCompanyRelations();
        }
    }

    public ElementCompanyRelationVO[] findRelationsByElementId(long elementId) {
        String elementFKCol = DBTABLE.getDBColumn("ElementFK").getFullyQualifiedColumnName();
        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + elementFKCol + " = " + elementId;
//       return (ElementCompanyRelationVO[]) executeQuery(ElementCompanyRelationVO.class,
//                                                         sql,
//                                                          POOLNAME,
//                                                           false,
//                                                            null);
        if (elementCompanyRelations.isEmpty()) {
            ElementCompanyRelationDAO.loadElementCompanyRelations();
        }
       if(elementCompanyRelations.get(elementId) != null)
           return elementCompanyRelations.get(elementId).toArray(new ElementCompanyRelationVO[0]);
        else
       {
           System.out.println("Could not find elementFK: "+elementId);
           System.out.println(sql);
           return null;
       }


    }

    public ElementCompanyRelationVO[] findRelationsByProductStructureId(long productStructureId) {
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                " WHERE " + productStructureFKCol + " = " + productStructureId;

        return (ElementCompanyRelationVO[]) executeQuery(ElementCompanyRelationVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public ElementCompanyRelationVO[] findAllProductStructureIds() {
        String sql = "SELECT * FROM " + TABLENAME;

        return (ElementCompanyRelationVO[]) executeQuery(ElementCompanyRelationVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public ElementCompanyRelationVO[] findRelationByElementCompanyRelationId(long elementCompanyRelationId) {
        String elementCompanyRelationPKCol = DBTABLE.getDBColumn("ElementCompanyRelationPK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                " WHERE " + elementCompanyRelationPKCol + " = " + elementCompanyRelationId;

        return (ElementCompanyRelationVO[]) executeQuery(ElementCompanyRelationVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public ElementCompanyRelationVO[] findElementCompanyRelationByIds(long elementId, long productStructureId) {
        String elementFKCol = DBTABLE.getDBColumn("ElementFK").getFullyQualifiedColumnName();
        String productStructureFKCol = DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                " WHERE " + elementFKCol + " = " + elementId +
                " AND " + productStructureFKCol + " = " + productStructureId;

        return (ElementCompanyRelationVO[]) executeQuery(ElementCompanyRelationVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }
}