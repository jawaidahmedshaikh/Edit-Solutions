package com.editsolutions.prd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.Rep;

import edit.services.db.hibernate.SessionHelper;

public class RepDao extends Dao<Rep> {

	public RepDao() {
        super(Rep.class);
    }
	
	public List<Rep> getRepList() throws DAOException {
		List<Rep> reps = new ArrayList<>();

		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "SELECT distinct RepName, RepPhoneNumber " +
                     "FROM vw_PRD_Setup " +
                     "where RepPhoneNumber is not null";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet rSet = preparedStatement.executeQuery();
			while(rSet.next()) {
				Rep rep = new Rep();
				rep.setRepName(rSet.getString("RepName"));
				rep.setRepPhoneNumber(rSet.getString("RepPhoneNumber"));
				reps.add(rep);
				
			}

		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
		return reps;
	}
	

}
