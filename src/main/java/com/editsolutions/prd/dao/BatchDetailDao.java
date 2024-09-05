package com.editsolutions.prd.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.editsolutions.prd.service.BatchService;
import com.editsolutions.prd.service.BatchServiceImpl;
import com.editsolutions.prd.service.PRDSettingsService;
import com.editsolutions.prd.service.PRDSettingsServiceImpl;
import com.editsolutions.prd.util.DAOException;




import com.editsolutions.prd.vo.Batch;
import com.editsolutions.prd.vo.BatchDetail;
import com.editsolutions.prd.vo.PRDSettings;

import edit.services.db.hibernate.SessionHelper;

public class BatchDetailDao extends Dao<BatchDetail> {

	public BatchDetailDao() {
        super(BatchDetail.class);
    }

	public List<BatchDetail> saveBatchDetails(Batch batch) throws DAOException {
		List<BatchDetail> batchDetails = batch.getBatchDetails();
		List<BatchDetail> savedBatchDetails = new ArrayList<>();

		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "insert into PRD_BatchDetail (PRD_BatchFK, Group_ContractGroupFK, "
	                  +  "PRD_SetupFK, Case_ContractGroupFK, IsDefaultGroup, AddDate, AddUser, ModDate, ModUser) " 
				   + " values (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql,
	                   Statement.RETURN_GENERATED_KEYS);
			
			Iterator<BatchDetail> it = batchDetails.iterator();
			while (it.hasNext()) {
				BatchDetail batchDetail = it.next();
			    preparedStatement.setLong(1, batch.getBatchPK());
			    preparedStatement.setLong(2, batchDetail.getGroupFK());
			    preparedStatement.setLong(3, batchDetail.getPrdSettings().getPrdSetupPK());
			    preparedStatement.setLong(4, batchDetail.getCaseFK());
			    preparedStatement.setBoolean(5, batchDetail.isDefaultGroup());
			    preparedStatement.setString(6, null);
			    preparedStatement.setDate(7, null);
			    preparedStatement.setString(8, null);
			    preparedStatement.setDate(9, null);
			    preparedStatement.executeUpdate();
	            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	       	        batchDetail.setBatchDetailPK(generatedKeys.getLong(1));
	            } else {
	                throw new SQLException("DataIssue: Creating case failed, no ID obtained.");
	            }
	            savedBatchDetails.add(batchDetail);
			}
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}
		return savedBatchDetails;
		

	}
	
	public List<BatchDetail> getDefaultBatchDetails() throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(BatchDetail.class);
		cr.add(Restrictions.eq("defaultGroup", true));
		@SuppressWarnings("unchecked")
		List<BatchDetail> list = cr.list();
		return list;
	}
	
	public boolean isInBatch(PRDSettings prdSettings) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(BatchDetail.class);
		cr.add(Restrictions.eq("prdSettings", prdSettings));
		BatchDetail bd = (BatchDetail)cr.uniqueResult();
		if (bd != null) {
			return true;
		}
		return false;
	}
	
	public List<PRDSettings> getBatchPRDSettings(Date extractDate) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(BatchDetail.class);
		cr.add(Restrictions.eq("defaultGroup", true));
		@SuppressWarnings("unchecked")
		List<BatchDetail> list = cr.list();
		PRDSettingsService pss = new PRDSettingsServiceImpl();
		List<PRDSettings> prdSettingsList = new ArrayList<>();
		Iterator<BatchDetail> it = list.iterator();
		while (it.hasNext()) {
			BatchDetail bd = it.next();
//			PRDSettings prds = pss.getPRDSettings(bd.getPrdSettings().getPrdSetupPK()); 
			PRDSettings prds = bd.getPrdSettings(); 
			if (prds.getSystemCT().equals("ES")) {
			    if ((prds.getLastPRDExtractDate() != null) && prds.getLastPRDExtractDate().equals(extractDate)) {
		            prds = createBatch(prds, bd);
		            prdSettingsList.add(prds);	
			    }
			} else {
			    if ((prds.getNextPRDExtractDate() != null) && prds.getNextPRDExtractDate().equals(extractDate)) {
		            prds = createBatch(prds, bd);
		            prdSettingsList.add(prds);	
			    }
				
			}
		}
		return prdSettingsList;
	}
	
	private PRDSettings createBatch(PRDSettings prdSettings, BatchDetail batchDetail) {
		prdSettings.setAdditionalPRDSettings(new ArrayList<PRDSettings>());
		List<BatchDetail> batchDetails = getBatchDetails(batchDetail.getBatchFK());
		Iterator<BatchDetail> it = batchDetails.iterator();
		while (it.hasNext()) {
			BatchDetail bd = it.next();
			if (!bd.getBatchDetailPK().equals(batchDetail.getBatchDetailPK())) {
			    prdSettings.getAdditionalPRDSettings().add(bd.getPrdSettings());	
			}
		}
	    return prdSettings;	
	}


	/*
	public List<PRDSettings> getBatchPRDSettingsByExtractDate(Date extractDate) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(BatchDetail.class);
		cr.add(Restrictions.eq("defaultGroup", true));
		@SuppressWarnings("unchecked")
		List<BatchDetail> list = cr.list();
		PRDSettingsService pss = new PRDSettingsServiceImpl();
		List<PRDSettings> prdSettings = new ArrayList<>();
		Iterator<BatchDetail> it = list.iterator();
		while (it.hasNext()) {
			BatchDetail bd = it.next();
			PRDSettings prds = pss.getPRDSettings(bd.getPrdSetupFK()); 
			if ((prds.getNextPRDExtractDate() != null) && prds.getNextPRDExtractDate().equals(extractDate)) {
		         prdSettings.add(prds);	
			}
		}
		return prdSettings;
	}
	*/
	
	public BatchDetail getBatchDetail(PRDSettings prdSettings) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(BatchDetail.class);
		cr.add(Restrictions.eq("prdSettings.prdSetupPK", prdSettings.getPrdSetupPK()));
		return (BatchDetail)cr.uniqueResult();
	}

	public List<BatchDetail> getBatchDetails(Long batchPK) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(BatchDetail.class);
		cr.add(Restrictions.eq("batchFK", batchPK));
		cr.addOrder(Order.asc("batchFK"));
		@SuppressWarnings("unchecked")
		List<BatchDetail> list = cr.list();
		return list;
	}

	public List<BatchDetail> getBatchDetails(Batch batch) throws DAOException {
		return getBatchDetails(batch.getBatchPK());
	}
	
}
