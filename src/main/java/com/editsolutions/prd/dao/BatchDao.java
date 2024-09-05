package com.editsolutions.prd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.editsolutions.prd.service.BatchDetailService;
import com.editsolutions.prd.service.BatchDetailServiceImpl;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.Batch;
import com.editsolutions.prd.vo.BatchDetail;
import com.editsolutions.prd.vo.PRDSettings;

import edit.services.db.hibernate.SessionHelper;

public class BatchDao extends Dao<Batch> {

	public BatchDao() {
        super(Batch.class);
    }
	
	public Batch saveBatch(Batch batch) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "insert into PRD_Batch (BatchName, BatchDescription, "
	                  +  "AddDate, AddUser, ModDate, ModUser) " 
				   + " values (?, ?, ?, ?, ?, ?) ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql,
	                   Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, batch.getBatchName());
			preparedStatement.setString(2, batch.getBatchDescription());
			preparedStatement.setDate(3, null);
			preparedStatement.setString(4, null);
			preparedStatement.setDate(5, null);
			preparedStatement.setString(6, null);
			preparedStatement.executeUpdate();
	        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	        if (generatedKeys.next()) {
	       	    batch.setBatchPK(generatedKeys.getLong(1));
	        } else {
	            throw new SQLException("DataIssue: Creating case failed, no ID obtained.");
	        }
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}
		BatchDetailService bds = new BatchDetailServiceImpl();
		batch.setBatchDetails(bds.saveBatchDetails(batch));
		return batch;
	}

	public Batch getBatch(Long prdSettingsPK) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(Batch.class);
		cr.add(Restrictions.eq("prdSettingsFK", prdSettingsPK));
		@SuppressWarnings("unchecked")
		Batch batch = (Batch) cr.uniqueResult();
		return batch;
	}
	
	public Batch getBatch(String name) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(Batch.class);
		cr.add(Restrictions.eq("batchName", name));
		@SuppressWarnings("unchecked")
		Batch batch = (Batch) cr.uniqueResult();
		return batch;
	}

	public List<Batch> getBatches() throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(Batch.class);
	    	
		cr.addOrder(Order.asc("batchName"));
		@SuppressWarnings("unchecked")
		List<Batch> list = cr.list();
		Iterator<Batch> it = list.iterator();
		while (it.hasNext()) {
		    Batch b = it.next();	
		    List<BatchDetail> bds = b.getBatchDetails();
		}
		return list;
	}


	
}
