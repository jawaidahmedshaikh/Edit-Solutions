package com.editsolutions.prd.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.DataIssue;

import edit.services.db.hibernate.SessionHelper;

public class DataIssuesDao extends Dao<DataIssue> {

	public DataIssuesDao() {
        super(DataIssue.class);
    }
	


	@SuppressWarnings("unchecked")
	public List<DataIssue> getUnresolvedDataIssues(Serializable dataHeaderPK) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
        Criteria c = session.createCriteria(DataIssue.class);
        c.add(Restrictions.eq("dataHeaderFK", dataHeaderPK));
        c.add(Restrictions.eq("isResolved", false));
        return c.list();
	}
	
	/*
	public DataIssue get(String dataIssuePK) {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
        Criteria c = session.createCriteria(DataIssue.class);
        c.add(Restrictions.eq("dataIssuePK", Long.getLong(dataIssuePK)));
        DataIssue di = (DataIssue)c.uniqueResult();
		return di;
	}
	*/


	public void deleteDataIssue(String dataIssuePK)  throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "delete PRD_DataIssues " 
				+ "  where PRD_DataIssuesPK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, Long.parseLong(dataIssuePK));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}
	}
	

	public void updateDataIssues(List<DataIssue> dataIssues) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "update PRD_DataIssues " 
				+ " set IsResolved = ? where PRD_DataIssuesPK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			Iterator<DataIssue> it = dataIssues.iterator();
			while(it.hasNext()) {
			    DataIssue dataIssue = it.next();	
			    preparedStatement.setBoolean(1, dataIssue.getIsResolved());
			    preparedStatement.setLong(2, dataIssue.getDataIssuePK());
			    preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}
	}

	public DataIssue updateDataIssue(DataIssue dataIssue) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "update PRD_DataIssues " 
				+ " set IsResolved = ? where PRD_DataIssuesPK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setBoolean(1, dataIssue.getIsResolved());
			preparedStatement.setLong(2, dataIssue.getDataIssuePK());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return dataIssue;
	}
	
	public void deleteDataIssuesFromDataHeader(DataHeader dataHeader) throws DAOException {

		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
        
		String sql = "delete PRD_DataIssues " + " where PRD_DataHeaderFK = ?";
		try {
				PreparedStatement preparedStatement = connection
						.prepareStatement(sql);
				preparedStatement.setLong(1, dataHeader.getDataHeaderPK());
				preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}

	}

	public boolean deleteDataIssueFromDataFK(Long dataFK) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "delete PRD_DataIssues where PRD_DataFK = ? ";
		try {
		    PreparedStatement preparedStatement = connection.prepareStatement(sql);
		    preparedStatement.setLong(1, dataFK);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}
		return true;
	}

	public boolean deleteDataIssues(List<DataIssue> dataIssues) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "delete PRD_DataIssues where dataIssuePK = ? ";
		try {
		    PreparedStatement preparedStatement = connection.prepareStatement(sql);
			Iterator<DataIssue> it = dataIssues.iterator();

			while(it.hasNext()) {
			    DataIssue dataIssue = it.next();	
		        preparedStatement.setLong(1, dataIssue.getDataIssuePK());
			    preparedStatement.executeUpdate();
			}
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}
		return true;
	}
	
	public DataIssue insertDataIssue(DataIssue dataIssue) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "insert into PRD_DataIssues (PRD_DataHeaderFK, PRD_DataFK, IssueLookup_CT, "
                   +  "IsResolved, AddDate, AddUser, ModDate, ModUser) " 
				   + " values (?, ?, ?, ?, ?, ?, ?, ?) ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setLong(1, dataIssue.getDataHeaderFK());
			preparedStatement.setLong(2, dataIssue.getDataFK());
			preparedStatement.setString(3, dataIssue.getIssueLookupCT());
			preparedStatement.setBoolean(4, dataIssue.getIsResolved());
			preparedStatement.setDate(5, dataIssue.getAddDate());
			preparedStatement.setString(6, dataIssue.getAddUser());
			preparedStatement.setDate(7, dataIssue.getModDate());
			preparedStatement.setString(8, dataIssue.getModUser());
			preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
           	    dataIssue.setDataIssuePK(generatedKeys.getLong(1));
            } else {
                throw new SQLException("DataIssue: Creating case failed, no ID obtained.");
            }
		} catch (SQLException e) {
			throw new DAOException(e.toString());
		}
		return dataIssue;
	}
	
}
