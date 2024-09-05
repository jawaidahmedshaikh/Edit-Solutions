package com.editsolutions.prd.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.AppSetting;
import com.editsolutions.prd.vo.DataIssue;
import com.editsolutions.prd.vo.GroupSetup;
import com.editsolutions.prd.vo.GroupStats;
import com.editsolutions.prd.vo.PRDStatistics;

import edit.services.db.hibernate.SessionHelper;

public class PRDStatisticsDao extends Dao<PRDStatistics> {

	public PRDStatisticsDao() {
		super(PRDStatistics.class);
	}

	public void savePRDStatistics(PRDStatistics stats) throws DAOException {
		save(stats);
	}

	public HashMap<Date, PRDStatistics> getAllPRDStatistics()
			throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
		Criteria cr = session.createCriteria(PRDStatistics.class);
		cr.addOrder(Order.desc("prdDate"));
		@SuppressWarnings("unchecked")
		List<PRDStatistics> list = cr.list();
		Iterator<PRDStatistics> it = list.iterator();
		HashMap<Date, PRDStatistics> hm = new HashMap<>();
		while (it.hasNext()) {
			PRDStatistics as = it.next();
			hm.put(as.getPrdDate(), as);
		}
		return hm;
	}

	public List<Object> getGroupsWithExcessNewRecords(Date prdDate,
			Double percent) throws DAOException {
		return getGroupsWithExcessRecords(prdDate, percent, "N");
    }

	public List<Object> getGroupsWithExcessRecords(Date prdDate,
			Double percent, String recordType) throws DAOException {
		String sqlDates = "select distinct PRD_Date from PRD_DataHeader order by PRD_Date desc";
		List<Object> groupSetups = new ArrayList<>();
		String sql = ";with dataset as ( "
				+ "      select      g.Group_ContractGroupPK, g.GroupNumber, g.GroupName, g.CaseNumber, h.PRD_Date, "
				+ "                   COUNT(*) as issueCount, "
				+ "                  (     select      COUNT(distinct PRD_RecordFK) "
				+ "                        from  PRD_Data "
				+ " where PRD_DataHeaderFK = h.PRD_DataHeaderPK "
				+ "                        group by PRD_DataHeaderFK "
				+ "                  ) as allCount "
				+ "      from  PRD_DataIssues i "
				+ "      join  PRD_DataHeader h on i.PRD_DataHeaderFK = h.PRD_DataHeaderPK "
				+ "      join  PRD_Setup s on h.PRD_SetupFK = s.PRD_SetupPK "
				+ "      join  PRD_Group g on s.Group_ContractGroupFK = g.Group_ContractGroupPK "
				+ "      where i.IssueLookup_CT = ? "
				+ "      and         h.PRD_Date = ? "
				+ "      group by g.Group_ContractGroupPK, g.GroupNumber, g.GroupName, g.CaseNumber, h.PRD_Date, h.PRD_DataHeaderPK "
				+ " ) "
				+ " select      *, "
				+ "            cast(issueCount as decimal(10,2)) / cast(allCount as decimal(10,2)) as issuePercentage "
				+ " from  dataset "
				+ " where cast(issueCount as decimal(10,2)) / cast(allCount as decimal(10,2)) >= ?  "
				+ " order by GroupNumber, PRD_Date ";
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		PreparedStatement preparedStatement;
		try {
			if (prdDate == null) {
				preparedStatement = connection.prepareStatement(sqlDates);
				ResultSet rsDates = preparedStatement.executeQuery();
				if (rsDates.next()) {
					prdDate = rsDates.getDate(1);
				}
			}
			if (percent == null) {
				percent = .25;
			}

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, recordType);
			preparedStatement.setDate(2, prdDate);
			preparedStatement.setDouble(3, percent);
			;
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				GroupStats groupSetup = new GroupStats();
				groupSetup.setGroupContractPk(rs
						.getLong("Group_ContractGroupPK"));
				groupSetup.setCaseNumber(rs.getString("CaseNumber"));
				groupSetup.setGroupNumber(rs.getString("GroupNumber"));
				groupSetup.setGroupName(rs.getString("GroupName"));
				groupSetup.setIssueCount(rs.getInt(6));
				groupSetup.setAllCount(rs.getInt(7));
				long issuePercent = Math.round(rs.getDouble(8) * 100.0);
				groupSetup.setIssuePercent(Long.toString(issuePercent) + '%');
				groupSetup.setPrdDate(rs.getDate(5));
				groupSetups.add(groupSetup);
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}

		return groupSetups;

	}

	public List<Object> getGroupsWithExcessTerminationRecords(Date prdDate,
			Double percent) throws DAOException {
		return getGroupsWithExcessRecords(prdDate, percent, "T");
	}

	public List<GroupSetup> getNewPRDGroups(Date prdDate) throws DAOException {
		List<GroupSetup> groupSetups = new ArrayList<>();
		/*
		 * String sql =
		 * "select g.* from PRD_Setup s, PRD_Group g, PRD_DataHeader dh " +
		 * "where s.Group_ContractGroupFK = g.Group_ContractGroupPK " +
		 * "and dh.PRD_SetupFK = s.PRD_SetupPK " +
		 * "and dh.PRD_Date = (select MAX(PRD_Date) from PRD_DataHeader) " +
		 * "and PRD_SetupPK in ( " + "select dh.PRD_SetupFK " +
		 * "from PRD_DataHeader dh " + "where dh.PRD_Date <= ? " +
		 * "group by dh.PRD_SetupFK " + "having COUNT(dh.PRD_SetupFK) = 1)";
		 */
		String sql = "select * from vw_PRD_Group where AddDate > DATEADD(day,-7, ?) "
				+ "and AddDate <= ? ";
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			if (prdDate != null) {
				preparedStatement.setDate(1, prdDate);
				preparedStatement.setDate(2, prdDate);
			} else {
				preparedStatement.setDate(1, new java.sql.Date(Calendar
						.getInstance().getTime().getTime()));
				preparedStatement.setDate(2, new java.sql.Date(Calendar
						.getInstance().getTime().getTime()));
			}
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				GroupSetup groupSetup = new GroupSetup();
				groupSetup.setGroupContractPk(rs
						.getLong("Group_ContractGroupPK"));
				groupSetup.setCaseNumber(rs.getString("CaseNumber"));
				groupSetup.setGroupNumber(rs.getString("GroupNumber"));
				groupSetup.setGroupName(rs.getString("GroupName"));
				groupSetups.add(groupSetup);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return groupSetups;

	}

	public List<GroupSetup> getGroupsWithNoRecords(Date prdDate)
			throws DAOException {
		List<GroupSetup> groupSetups = new ArrayList<>();
		String sql = "select * from PRD_Group where GroupNumber not in ("
				+ "select g.groupNumber from PRD_DataHeader h, vw_PRD_Setup s, PRD_Group g "
				+ "where h.PRD_SetupFK = s.PRD_SetupPK "
				+ "and h.PRD_Date <= ? "
				+ "and s.Group_ContractGroupFK = g.Group_ContractGroupPK) ";
		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			if (prdDate != null) {
				preparedStatement.setDate(1, prdDate);
			} else {
				preparedStatement.setDate(1, new java.sql.Date(Calendar
						.getInstance().getTime().getTime()));
			}
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				GroupSetup groupSetup = new GroupSetup();
				groupSetup.setGroupContractPk(rs
						.getLong("Group_ContractGroupPK"));
				groupSetup.setCaseNumber(rs.getString("CaseNumber"));
				groupSetup.setGroupNumber(rs.getString("GroupNumber"));
				groupSetup.setGroupName(rs.getString("GroupName"));
				groupSetups.add(groupSetup);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return groupSetups;

	}

	public List<PRDStatistics> getPRDStatistics() throws DAOException {
		List<PRDStatistics> prdStatistics = new ArrayList<>();
		HashMap<Date, PRDStatistics> hm = getAllPRDStatistics();
		Connection connection = SessionHelper.getSession(
				SessionHelper.EDITSOLUTIONS).connection();

		String sqlDates = "select distinct top 6 PRD_Date from PRD_DataHeader order by PRD_Date desc";

		String sqlPRDSumTotal = "with sumTotals as ( "
				+ " select d.fieldValue "
				+ " from PRD_DataIssues i, PRD_DataHeader h, "
				+ " PRD_Data d, PRD_FileTemplateField f, PRD_SourceField s "
				+ " where i.PRD_DataHeaderFK = h.PRD_DataHeaderPK  "
				+ " and d.PRD_DataPK = i.PRD_DataFK "
				+ " and d.PRD_FileTemplateFieldPK = f.PRD_FileTemplateFieldPK "
				+ " and f.PRD_SourceFieldFK = s.PRD_SourceFieldPK "
				+ " and s.SQLFieldName = 'DeductionAmount' "
				+ " and h.PRD_Date = ? "
				+ " and CHARINDEX('.', d.FieldValue) > 0 "
				+ " union "
				+ " select      REVERSE(STUFF(REVERSE(  substring(d.fieldValue, patindex('%[^0]%',d.fieldValue), 10)), 3, 0, '.')) "
				+ " from PRD_DataIssues i, PRD_DataHeader h, "
				+ " PRD_Data d, PRD_FileTemplateField f, PRD_SourceField s "
				+ " where i.PRD_DataHeaderFK = h.PRD_DataHeaderPK  "
				+ " and d.PRD_DataPK = i.PRD_DataFK  "
				+ " and d.PRD_FileTemplateFieldPK = f.PRD_FileTemplateFieldPK  "
				+ " and f.PRD_SourceFieldFK = s.PRD_SourceFieldPK  "
				+ " and s.SQLFieldName = 'DeductionAmount' "
				+ " and h.PRD_Date = ?  "
				+ " and CHARINDEX('.', d.FieldValue) = 0 " + " ) "
				+ " select SUM(cast(fieldValue as money)) as sum1 "
				+ " from sumTotals ";

		String sqlHoldForReview = "select count(dh.PRD_DataHeaderPK) as 'On Hold' "
				+ "from PRD_DataHeader dh, vw_PRD_Setup s where dh.PRD_Date = ? "
				+ "and dh.PRD_SetupFK = s.PRD_SetupPK and s.HoldForReview = 1 ";

		String sqlPRDCount = "select count(PRD_DataHeaderPK) as 'PRD Count' from PRD_DataHeader where PRD_Date = ? and StatusCT != 'N'";

		// String sqlIssuesCount =
		// "select distinct IssueLookup_CT, COUNT(IssueLookup_CT) from PRD_DataIssues where PRD_DataHeaderFK in ("
		// + "select PRD_DataHeaderPK from PRD_DataHeader where PRD_Date = ?) "
		// + "group by IssueLookup_CT " + "order by IssueLookup_CT ";

		String sqlIssuesCount = "select  distinct di.IssueLookup_CT, COUNT(distinct d.PRD_RecordFK) "
				+ "from PRD_Data d, PRD_DataIssues di where d.PRD_DataPK = di.PRD_DataFK "
				+ "and d.PRD_DataPK in ( "
				+ "select i.PRD_DataFK from PRD_DataIssues i, PRD_DataHeader h "
				+ "where i.PRD_DataHeaderFK = h.PRD_DataHeaderPK "
				+ "and h.PRD_Date = ? ) " + "group by di.IssueLookup_CT ";

		String sqlActiveGroupCount = "select COUNT(*) from vw_PRD_Setup where PRD_TypeCT = 'Standard' and StatusCT = 'Active' "
				+ "and EffectiveDate <= ?";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sqlDates);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				PRDStatistics stats = new PRDStatistics();
				java.sql.Date dueDate = rs.getDate(1);

				if (hm.containsKey(new java.sql.Date(dueDate.getTime()))) {
					stats = hm.get(new java.sql.Date(dueDate.getTime()));
				} else {

					stats.setPrdDate(dueDate);

					preparedStatement = connection
							.prepareStatement(sqlActiveGroupCount);
					preparedStatement.setDate(1, dueDate);
					ResultSet rsActiveGroupCount = preparedStatement
							.executeQuery();
					if (rsActiveGroupCount.next()) {
						stats.setTotal(rsActiveGroupCount.getInt(1));
					}
					// PRD Sum Total
					preparedStatement = connection
							.prepareStatement(sqlPRDSumTotal);
					preparedStatement.setDate(1, dueDate);
					preparedStatement.setDate(2, dueDate);
					ResultSet rsPRDSumTotal = preparedStatement.executeQuery();
					if (rsPRDSumTotal.next()) {
						stats.setPrdSumTotal(rsPRDSumTotal.getDouble(1));
					}

					preparedStatement = connection
							.prepareStatement(sqlHoldForReview);
					preparedStatement.setDate(1, dueDate);
					ResultSet rsHoldForReview = preparedStatement
							.executeQuery();
					if (rsHoldForReview.next()) {
						stats.setOnHoldCount(rsHoldForReview.getInt(1));
					}

					//
					preparedStatement = connection
							.prepareStatement(sqlPRDCount);
					preparedStatement.setDate(1, dueDate);
					ResultSet rsPRDCount = preparedStatement.executeQuery();
					if (rsPRDCount.next()) {
						stats.setPrdCount(rsPRDCount.getInt(1));
					}

					preparedStatement = connection
							.prepareStatement(sqlIssuesCount);
					preparedStatement.setDate(1, dueDate);
					ResultSet rsIssuesCount = preparedStatement.executeQuery();
					while (rsIssuesCount.next()) {
						if (rsIssuesCount.getString(1).trim()
								.equals(DataIssue.CHANGE)) {
							stats.setChangeCount(rsIssuesCount.getInt(2));
						} else if (rsIssuesCount.getString(1).trim()
								.equals(DataIssue.NEW)) {
							stats.setNewCount(rsIssuesCount.getInt(2));
						} else if (rsIssuesCount.getString(1).trim()
								.equals(DataIssue.TERMINATED)) {
							stats.setTerminatedCount(rsIssuesCount.getInt(2));
						}
					}
				    stats.setAsOf(new Timestamp(System.currentTimeMillis()));
				    savePRDStatistics(stats);

				}
				prdStatistics.add(stats);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return prdStatistics;
	}

	public PRDStatistics getPRDRunningStatistics() throws DAOException {
		PRDStatistics stats = new PRDStatistics();
		Connection connection = SessionHelper.getSession(
				SessionHelper.EDITSOLUTIONS).connection();
		String sql = "select * from vw_PRD_Stats_RunningTotal ";
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				stats.setPrdDate(rs.getDate(1));
				stats.setTotal(rs.getInt(3));
				stats.setStandardCount(rs.getInt(4));
				stats.setCustomCount(rs.getInt(5));
				stats.setOnHoldCount(rs.getInt(6));
				stats.setIssuesCount(rs.getInt(7));
				stats.setTotalGroups(rs.getInt(8));
				stats.setActiveGroups(rs.getInt(9));
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
		return stats;

	}

}
