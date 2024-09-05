package com.editsolutions.prd.util;

import java.util.Iterator;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.xml.sax.helpers.DefaultHandler;

import com.editsolutions.prd.service.PRDSettingsService;
import com.editsolutions.prd.service.PRDSettingsServiceImpl;
import com.editsolutions.prd.vo.PRDSettings;

import edit.services.db.hibernate.SessionHelper;

public class RemoveDuplicateSetups  {

	public static void main(String[] args) {

		/*
		for (int i = 0; i < args.length; i++) {
			filename = args[i];
			if (i != args.length - 1) {
				usage();
			}
		}
		*/
		new RemoveDuplicateSetups();

	}
	
	
	
	
	public RemoveDuplicateSetups() {
        this.removeDuplicatePRDSetups();
	}




	public void removeDuplicatePRDSetups() {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		String sql = "SELECT PRD_SetupName " +
		             "FROM [EDIT_SOLUTIONS].[dbo].[vw_PRD_Setup] " +
				     "group by PRD_SetupName " +
				     "having COUNT(PRD_SetupName) = 2 ";
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(String.class);
		@SuppressWarnings("unchecked")
		List<String> prdData = query.list();
		Iterator<String> it1 = prdData.iterator();
		while (it1.hasNext()) {
			String name = it1.next();
		    String sql2 = "select * from vw_PRD_Setup " +
                          "where PRD_SetupName = '" + name + "' " +
                          "and PayrollDeductionScheduleFK > 0"; 

		    String sql3 = "select * from vw_PRD_Setup " +
                          "where PRD_SetupName = '" + name + "' " +
                          "and PayrollDeductionScheduleFK = 0"; 

		    SQLQuery query2 = session.createSQLQuery(sql2);
		    SQLQuery query3 = session.createSQLQuery(sql3);
		    query2.addEntity(PRDSettings.class);
		    query3.addEntity(PRDSettings.class);
		    PRDSettings p2 = (PRDSettings)query2.uniqueResult();
		    PRDSettings p3 = (PRDSettings)query3.uniqueResult();
		    
		    p2.setContractCaseFK(p3.getContractCaseFK());
		    p2.setContractGroupFK(p3.getContractGroupFK());
		    p2.setPayrollDeductionScheduleFK(p3.getPayrollDeductionScheduleFK());
		    PRDSettingsService pss = new PRDSettingsServiceImpl(); 
		    pss.savePRDSettings(p2);
			
		}

		session.close();
		
	}
	
	/*
	private static void usage() {
		System.err.println("Usage: ParseAgent <file.xml>");
		System.err.println("       -usage or -help = this message");
		System.exit(1);
	}
	*/

}

