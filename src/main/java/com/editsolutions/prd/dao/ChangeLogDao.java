package com.editsolutions.prd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.editsolutions.prd.vo.ChangeLog;

import edit.services.db.hibernate.SessionHelper;

public class ChangeLogDao extends Dao<ChangeLog> {

	public ChangeLogDao() {
        super(ChangeLog.class);
    }
	
	public List<ChangeLog> getPayrollDeductionDataList(String prdSetupPK) {
		Long setupPK = Long.valueOf(prdSetupPK);
		List<ChangeLog> changeLogs = new ArrayList<>();

		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "SELECT l.* " 
				+ " FROM vw_PRD_Setup s, PRD_DataHeader h, PRD_Data d, PRD_Change_Log l " 
				+ " WHERE s.PRD_SetupPK = h.PRD_SetupFK "
				+ " AND h.PRD_DataHeaderPK = d.PRD_DataHeaderFK "
				+ " AND d.PRD_DataPK = l.PKValue1 "
				+ " and s.PRD_SetupPK = ? ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, setupPK);
			ResultSet rSet = preparedStatement.executeQuery();
			while(rSet.next()) {
				ChangeLog changeLog = new ChangeLog();
				changeLog.setChangeLogPK(rSet.getLong("PRD_Change_LogPK"));
				changeLog.setTableName(rSet.getString("TableName"));
				changeLog.setPkValue1(rSet.getLong("PKValue1"));
				changeLog.setPkValue2(rSet.getLong("PKValue2"));
				changeLog.setFieldName(rSet.getString("FieldName"));
				changeLog.setOldValue(rSet.getString("Old_Value"));
				changeLog.setNewValue(rSet.getString("New_Value"));
				changeLog.setModDate(rSet.getDate("ModDate"));
				changeLog.setModUser(rSet.getString("ModUser"));
				
				changeLogs.add(changeLog);
				
			}

		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return changeLogs;
	}
	

}
