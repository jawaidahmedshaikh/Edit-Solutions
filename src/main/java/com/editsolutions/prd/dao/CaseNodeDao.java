package com.editsolutions.prd.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.editsolutions.prd.service.NotificationNodeService;
import com.editsolutions.prd.service.NotificationNodeServiceImpl;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.node.CaseNode;
import com.editsolutions.prd.node.GroupNode;
import com.editsolutions.prd.node.NotificationNode;

import edit.services.db.hibernate.SessionHelper;
import electric.application.tools.NewApp;

public class CaseNodeDao extends Dao<CaseNode> {

	public CaseNodeDao() {
        super(CaseNode.class);
    }

	
	@SuppressWarnings("unchecked")
	public List<CaseNode> getCaseNodesWithCorrespondence(String searchString, String searchType) throws DAOException {
		String searchTypeCriteria = "";
		String sortString = "";
		if (searchType.equals("GROUP")) {
			searchTypeCriteria = "and UPPER(g2.GroupNumber) = '" + searchString.toUpperCase() + "'";
            //sortString = ") order by g.GroupNumber ";
		} else if (searchType.equals("NAME")) {
			searchTypeCriteria = "and UPPER(g2.GroupName) like '%" + searchString.toUpperCase() + "%'";
//            sortString = ") order by g.GroupName ";
            //sortString = ") order by c.CaseNumber ";
		} else {
			if (!searchString.trim().isEmpty()) {
			    searchTypeCriteria = "and UPPER(c2.CaseNumber) = '" + searchString.toUpperCase() + "'";
			}
		}
        sortString = ") order by c.CaseNumber  ";

		List<CaseNode> caseNodes = new ArrayList<>();
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		StringBuilder sql = new StringBuilder("select distinct c.*, c.CaseNumber ");
        sql.append("from vw_PRD_Case c, vw_PRD_Group g, vw_PRD_Setup p ");
        sql.append("where p.Case_ContractGroupFK = g.Case_ContractGroupPK ");
        sql.append("and g.Case_ContractGroupPK = c.Case_ContractGroupPK ");
        sql.append("and p.PRD_SetupPK in ("); 
        sql.append("select distinct n.PRD_SetupFK from PRD_Notification n, vw_PRD_Setup p2, vw_PRD_Case c2, vw_PRD_Group g2 ");
        sql.append("where n.PRD_SetupFK = p2.PRD_SetupPK ");
        sql.append("and p2.Case_ContractGroupFK = c2.Case_ContractGroupPK ");
        sql.append("and p2.Group_ContractGroupFK = g2.Group_ContractGroupPK ");
        sql.append(searchTypeCriteria);
        sql.append(sortString);

		SQLQuery sqlQuery = session.createSQLQuery(sql.toString()); 
		sqlQuery.addEntity(CaseNode.class);
		caseNodes = sqlQuery.list();
		
		Iterator<CaseNode> caseNodeIterator = caseNodes.iterator();
		while (caseNodeIterator.hasNext()) {
			CaseNode caseNode = caseNodeIterator.next();
			NotificationNodeService nns = new NotificationNodeServiceImpl();
			Iterator<GroupNode> groupNodeIterator = caseNode.getChildren().iterator();
			while (groupNodeIterator.hasNext()) {
				GroupNode groupNode = groupNodeIterator.next();
				if (groupNode.getPrdNode() == null) {
					// log - no prd setup for this group
				} else {
				    groupNode.setChildren(nns.getNotificationNodes(groupNode.getPrdNode().getPrdSetupPK()));
				}
				
			}
			
		}
		
		return caseNodes;
	}
	


}
