package com.editsolutions.prd.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.editsolutions.prd.service.DataHeaderService;
import com.editsolutions.prd.service.DataHeaderServiceImpl;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.FileTemplate;
import com.editsolutions.prd.vo.ImportRecord;
import com.editsolutions.prd.vo.PRDSettings;
import com.editsolutions.prd.vo.SampleImportRecord;

import edit.services.db.hibernate.SessionHelper;

public class SampleImportRecordDao extends Dao<ImportRecord> {

	public SampleImportRecordDao() {
        super(ImportRecord.class);
    }
	

	public int getAllODSRecords() {
		Connection connection = SessionHelper.getSession(SessionHelper.ODS).connection();
		String sql = "select count(*) from vw_PRD_MF_Import"; 
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
			    return rs.getInt(1);   	
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return 0;
		
	}

    
    public List<ImportRecord> getImportRecordsForProductQuery(PRDSettings prdSettings) {
		
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		List<ImportRecord> importRecords = new ArrayList<>();
    	
    	String sql = "select AccountNumber, EmployeeID, EmployeeName, sum(DeductionAmount) as 'DeductionAmount', " 
                     + "DeductionDate, ProductType from PRD_Record "  
                     + "where DeductionDate = ? " 
                     + "and GroupNumber = ? "
                     + "group by EmployeeId, ProductType, DeductionAmount, EmployeeName, AccountNumber, DeductionDate";
    	
		try {

			PreparedStatement preparedStatement = connection.prepareStatement(sql);
//			preparedStatement.setDate(1, prdSettings.getNextPRDDueDate());
			if (prdSettings.getSystemCT().equals("ES")) {
			    preparedStatement.setDate(1, prdSettings.getLastPRDExtractDate());
			} else {
			    preparedStatement.setDate(1, prdSettings.getNextPRDExtractDate());
			}
		    preparedStatement.setString(2, prdSettings.getGroupSetup().getGroupNumber());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ImportRecord importRecord = new ImportRecord();
				importRecord.setAccountNumber(rs.getString("AccountNumber"));
				importRecord.setEmployeeID(rs.getString("EmployeeID"));
				importRecord.setEmployeeName(rs.getString("EmployeeName"));
				importRecord.setDeductionAmount(rs.getDouble("DeductionAmount"));
				importRecord.setDeductionDate(rs.getDate("DeductionDate"));
				importRecord.setProductType(rs.getString("ProductType"));
				importRecords.add(importRecord);
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return importRecords; 
    }

	public List<ImportRecord> getImportRecordsByEmployeeID(String employeeID, PRDSettings prdSettings) {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(ImportRecord.class);
		if (employeeID != null) {
		    cr.add(Restrictions.eq("employeeID", employeeID));
			if (prdSettings.getSystemCT().equals("ES")) {
			    cr.add(Restrictions.le("deductionDate", new java.sql.Date((DateUtils.addDays(prdSettings.getLastPRDExtractDate(), 1)).getTime())));
			} else {
		        cr.add(Restrictions.le("deductionDate", prdSettings.getNextPRDDueDate()));
			}
		    cr.add(Restrictions.eq("groupNumber", prdSettings.getGroupSetup().getGroupNumber()));
		    cr.addOrder(Order.desc("deductionDate"));
		}
		@SuppressWarnings("unchecked")
		List<ImportRecord> list = cr.list();
		return list;
	}
	
	public List<ImportRecord> getImportRecordList(String groupNumber) {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(ImportRecord.class);
		if (groupNumber != null) {
		    cr.add(Restrictions.eq("groupNumber", groupNumber));
		}
		@SuppressWarnings("unchecked")
		List<ImportRecord> list = cr.list();
		return list;
	}

	public List<SampleImportRecord> getSampleImportRecords(FileTemplate fileTemplate) {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(SampleImportRecord.class);
		//cr.add(Restrictions.eq("groupNumber", groupNumber));
		//cr.add(Restrictions.eq("deductionDate", deductionDate));
		//cr.addOrder(Order)
		@SuppressWarnings("unchecked")
		List<SampleImportRecord> list = cr.list();
		return list;
	}
	

}

