package com.editsolutions.prd.dao;

import java.util.Date;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.aop.ThrowsAdvice;

import com.editsolutions.prd.service.ImportRecordService;
import com.editsolutions.prd.service.ImportRecordServiceImpl;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.ImportRecord;
import com.editsolutions.prd.vo.PolicyHolder;

import java.sql.Connection;

import edit.services.db.hibernate.SessionHelper;

public class ODSDataDao extends Dao<ImportRecord> {

	public ODSDataDao() {
        super(ImportRecord.class);
    }
	
	public List<ImportRecord> getODSDataRecords(Date extractDate)
			throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.ODS).connection();
		String sql = "select count(*) from dbo.vw_PRD_Import_PHL where deductionDate = ?"; 
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDate(1, new java.sql.Date(extractDate.getTime()));
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
			    System.out.println("COUNT: " +  rs.getInt(1));   	
			}
		} catch (SQLException e) {
	        throw new DAOException(e);	
		}
		List<ImportRecord> list = null;

		return list;
	}
	
	public List<PolicyHolder> getPolicyHolder(String recordFK) throws DAOException {
		ImportRecordService importRecordService = new ImportRecordServiceImpl();
		ImportRecord importRecord = importRecordService.getImportRecord(Long.parseLong(recordFK));
		String employeeId = importRecord.getEmployeeID();
		String groupNumber = importRecord.getGroupNumber();
		String lastFourDigits;
		employeeId = employeeId.trim().replace("-", "");
		//if (employeeId.length() >= 5) {
		//    lastFourDigits = employeeId.substring(employeeId.length() - 4, employeeId.length());
		//} else {
		    lastFourDigits = employeeId;	
	//	}
		
		Session session = SessionHelper.getSession(SessionHelper.ODS);
		SQLQuery query = session
				.createSQLQuery("open symmetric key OdsEncryptionKey decryption by certificate OdsEncryptionCert; " + 
		                  "select distinct * from PolicyHolder "
						+ "where MarketCode = '" + groupNumber.trim() + "' "  
						+ "and (replace(EmployeeUniqueId, '-', '') = replace('" + employeeId + "', '-', '') " 
						+ "or replace(OwnerSSN, '-', '') = replace('" + employeeId + "', '-', '')) " 
						+ "order by PolicyNumber; close symmetric key OdsEncryptionKey ");
		/*
		SQLQuery query = session
				.createSQLQuery("open symmetric key OdsEncryptionKey decryption by certificate OdsEncryptionCert; " + 
		                  "select distinct * from PolicyHolder "
						+ "where MarketCode = '" + groupNumber.trim() + "' "  
						+ "and (RTRIM(EmployeeUniqueId) like '%" + lastFourDigits + "' " 
						+ "or RTRIM(OwnerSSN) like '%" + lastFourDigits + "') " 
						+ "order by PolicyNumber; close symmetric key OdsEncryptionKey ");
						*/
		query.addEntity(PolicyHolder.class);
		@SuppressWarnings("unchecked")
		List<PolicyHolder> policyHolders = query.list();

		session.close();

        
		return policyHolders;
    }

}
