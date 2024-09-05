package com.editsolutions.prd.service;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.CodeTable;

import edit.services.db.hibernate.SessionHelper;

public class CodeTableService {

	public List<CodeTable> getReportTypeList() throws DAOException {
		return getCodes("PRDREPORTTYPE");
	}

	public List<CodeTable> getWeekdayList() throws DAOException {
		return getCodes("WEEKDAY");
	}

	public List<CodeTable> getCaseTypeList() throws DAOException {
		return getCodes("CASETYPE");
	}

	public List<CodeTable> getStateList() throws DAOException {
		return getCodes("STATE");
	}

	public List<CodeTable> getPRDTypeList() throws DAOException {
		return getCodes("PRDTYPE");
	}

	public List<CodeTable> getPRDOutputTypeList() throws DAOException {
		return getCodes("PRDOUTPUTTYPE");
	}

	public List<CodeTable> getPRDSortOptionTypeList() throws DAOException {
		return getCodes("SORTOPTION");
	}

	public List<CodeTable> getPRDSummaryTypeList() throws DAOException {
		return getCodes("PRDSUMMARY");
	}

	public List<CodeTable> getPRDDeliveryMethodList() throws DAOException {
		return getCodes("DELIVERYMETHOD");
	}

	public List<CodeTable> getPRDStatusList() throws DAOException {
		return getCodes("STATUS");
	}

	public List<CodeTable> getFileTemplateTypeList() throws DAOException {
		return getCodes("FILETEMPLATETYPE");
	}

	public List<CodeTable> getTemplateTypeList() throws DAOException {
		return getCodes("TEMPLATETYPE");
	}

	public List<CodeTable> getPRDReviewTypeList() throws DAOException {
		return getCodes("PRDREVIEWVEIW");
	}

	public List<CodeTable> getCodes(String codeTableName) throws DAOException {

		List<CodeTable> list = new ArrayList<>();
		Connection c = null;

		try {
			c = SessionHelper.getSession(SessionHelper.PRD).connection();
			PreparedStatement ps = c
					.prepareStatement("SELECT * FROM vw_CodeTable where CodeTableName = ? order by CodeTablePK");
			ps.setString(1, codeTableName);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new CodeTable(rs.getLong("CodeTableDefPK"), rs
						.getLong("CodeTablePK"), rs.getString("CodeTableName"),
						rs.getString("Code"), rs.getString("CodeDesc")));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return list;
	}

}
