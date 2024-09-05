package com.editsolutions.prd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.PRDExclusion;

import edit.services.db.hibernate.SessionHelper;

public class PRDExclusionDao extends Dao<PRDExclusion> {

	public PRDExclusionDao() {
        super(PRDExclusion.class);
    }
	
	public PRDExclusion createPRDExclusion(PRDExclusion prdExclusion) throws DAOException {
			Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
			String sql = "insert into PRD_Exclusion" 
					+ " values (?, ?, ?, ?, ?, ?, ?, ?) ";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setLong(1, prdExclusion.getPrdSetupFK());
				preparedStatement.setString(2, prdExclusion.getExclusionType());
				preparedStatement.setString(3, prdExclusion.getExclusionCode());
				preparedStatement.setDate(4, null);
				preparedStatement.setString(5, null);
				preparedStatement.setDate(6, null);
				preparedStatement.setString(7, null);

				preparedStatement.executeUpdate();
		        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		            	prdExclusion.setPrdExclusionPK(generatedKeys.getLong(1));
		            }
		            else {
		                throw new SQLException("Creating prdExclusion failed, no ID obtained.");
		            }
		        }

			} catch (SQLException e) {
				System.out.println(e.toString());
			    throw new DAOException(e);
			}
		return prdExclusion;
	}
	
	public PRDExclusion updatePRDExclusion(PRDExclusion prdExclusion) throws DAOException {
			Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
			String sql = "update PRD_Exclusion" 
					+ " set PRD_SetupFK = ?, ExclusionType = ?, ExclusionCode = ?  "
					+ " where PRD_ExclusionPK = ?";
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setLong(1, prdExclusion.getPrdSetupFK());
				preparedStatement.setString(2, prdExclusion.getExclusionType());
				preparedStatement.setString(3, prdExclusion.getExclusionCode());
				preparedStatement.setLong(4, prdExclusion.getPrdExclusionPK());

				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				System.out.println(e.toString());
			    throw new DAOException(e);
			}
		return prdExclusion;
	}
	
	public void deletePRDExclusion(PRDExclusion prdExclusion) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "delete PRD_PRDExclusion" 
				+ " where PRD_ExclusionPK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, prdExclusion.getPrdExclusionPK());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
    }	

}
