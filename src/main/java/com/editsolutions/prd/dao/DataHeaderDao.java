package com.editsolutions.prd.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.editsolutions.prd.service.DataIssuesService;
import com.editsolutions.prd.service.DataIssuesServiceImpl;
import com.editsolutions.prd.service.DataService;
import com.editsolutions.prd.service.DataServiceImpl;
import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.DataHeader;
import com.editsolutions.prd.vo.DataIssue;
import com.editsolutions.prd.vo.PayrollDeductionData;

import edit.services.db.hibernate.SessionHelper;

public class DataHeaderDao extends Dao<DataHeader> {

	public DataHeaderDao() {
        super(DataHeader.class);
    }
	
	public DataHeader get(Serializable id) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
        Criteria c = session.createCriteria(DataHeader.class);
        c.add(Restrictions.eq("dataHeaderPK", id));
		return get(c);
	}
	
	public int getCountByIssueLookupCT(Long dataHeaderPK, String issueLookupCT) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		try {
			PreparedStatement ps = connection
					.prepareStatement("SELECT COUNT(*) FROM PRD_DataIssues where PRD_DataHeaderFK = ? "
							         + "and IssueLookup_CT = ?");
			ps.setLong(1, dataHeaderPK);
			ps.setString(2, issueLookupCT);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
			    return rs.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}
	
	public int getZeroCount(Long dataHeaderPK) throws DAOException {
		return getCountByIssueLookupCT(dataHeaderPK, "T");
	}
	
	public int getIssueCount(Long dataHeaderPK) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		StringBuilder sql = new StringBuilder("select count(distinct a.PRD_RecordFK) from  PRD_Data a ");
		sql.append("join  PRD_DataIssues b on b.PRD_DataFK = a.PRD_DataPK and b.isResolved = 0 ");
		sql.append("where b.PRD_DataHeaderFK = ? ");
		sql.append("group by a.PRD_DataHeaderFK ");
		sql.append("order by a.PRD_DataHeaderFK ");
		try {
			PreparedStatement ps = connection.prepareStatement(sql.toString());
			ps.setLong(1, dataHeaderPK);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
			    return rs.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}

	public int getPRDCount(Long dataHeaderPK) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		try {
			PreparedStatement ps = connection
					.prepareStatement("SELECT COUNT(distinct PRD_RecordFK) FROM PRD_Data where PRD_DataHeaderFK = ? ");
			ps.setLong(1, dataHeaderPK);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
			    return rs.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
	}
	
	public void resolveAllDataIssues(DataHeader dataHeader) throws DAOException{

		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "update PRD_DataIssues " 
				+ " set IsResolved = ? where PRD_dataHeaderFK = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setBoolean(1, true);
			preparedStatement.setLong(2, dataHeader.getDataHeaderPK());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new DAOException(e);
		}
		
	}

	public List<DataHeader> getAllDataHeadersOnHold() throws DAOException {
		List<DataHeader> dhOnHold = new ArrayList<>();
		Session session = SessionHelper.getSession(SessionHelper.PRD);
        Criteria c = session.createCriteria(DataHeader.class);
        List<DataHeader> dhs = findAll(c);
        Iterator<DataHeader> it = dhs.iterator();
        while (it.hasNext()) {
        	DataHeader dHeader = it.next();
        	if (dHeader.getPrdSettings().isHoldForReview()) {
        	    dHeader.setPrdCount(getPRDCount(dHeader.getDataHeaderPK()));
        	    dHeader.setIssueCount(getIssueCount(dHeader.getDataHeaderPK()));
        		dhOnHold.add(dHeader);
        	} 
        }
        return dhOnHold;
	}

	public List<DataHeader> getAllDataHeadersWithIssues() throws DAOException {
		List<DataHeader> dhWithIssues = new ArrayList<>();
		Session session = SessionHelper.getSession(SessionHelper.PRD);
        Criteria c = session.createCriteria(DataHeader.class);
        List<DataHeader> dhs = findAll(c);
        Iterator<DataHeader> it = dhs.iterator();
        while (it.hasNext()) {
        	DataHeader dHeader = it.next();
       		dHeader.setUnresolvedDataIssues(new DataIssuesServiceImpl().getUnresolvedDataIssues(dHeader.getDataHeaderPK()));
        	if (!dHeader.getUnresolvedDataIssues().isEmpty()) {
        	    dHeader.setPrdCount(getPRDCount(dHeader.getDataHeaderPK()));
        	    dHeader.setIssueCount(getIssueCount(dHeader.getDataHeaderPK()));
        		dhWithIssues.add(dHeader);
        	} 
        }
        return dhWithIssues;
	}

	public List<DataHeader> getDataHeadersWithIssues(Serializable prdSetupFK) throws DAOException {
		List<DataHeader> dhWithIssues = new ArrayList<>();
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		//System.out.println(prdSetupFK);
        Criteria c = session.createCriteria(DataHeader.class);
        c.add(Restrictions.eq("setupFK", prdSetupFK));
        List<DataHeader> dhs = findAll(c);
        Iterator<DataHeader> it = dhs.iterator();
        while (it.hasNext()) {
        	DataHeader dHeader = it.next();
       		dHeader.setUnresolvedDataIssues(new DataIssuesServiceImpl().getUnresolvedDataIssues(dHeader.getDataHeaderPK()));
        	if (!dHeader.getUnresolvedDataIssues().isEmpty() || dHeader.getPrdSettings().isHoldForReview()) {
        	    dHeader.setPrdCount(getPRDCount(dHeader.getDataHeaderPK()));
        	    dHeader.setIssueCount(getIssueCount(dHeader.getDataHeaderPK()));
        		dhWithIssues.add(dHeader);
        	} 
        }
        return dhWithIssues;
	}

	public DataHeader getDataHeaderWithUnresolvedIssues(Serializable dataHeaderPK) throws DAOException {
        DataHeader dataHeader = this.get(dataHeaderPK);
       	dataHeader.setUnresolvedDataIssues(new DataIssuesServiceImpl().getUnresolvedDataIssues(dataHeaderPK));
       	return dataHeader;
	}


	public List<DataHeader> getCurrentWeeksReleasedDataHeaders() throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		SQLQuery query = session.createSQLQuery("select distinct b.*, g.GroupNumber from PRD_DataHeader b, vw_PRD_Setup c, vw_PRD_Group g "
				+ "where b.PRD_SetupFK = c.PRD_SetupPK and " 
				+ "c.Group_ContractGroupFK = g.Group_ContractGroupPK and "
				+ "b.StatusCT = 'R' and "
				+ "PRD_Date = (select MAX(PRD_Date) from PRD_DataHeader) "
				+ "order by g.GroupNumber ");
		query.addEntity(DataHeader.class);
		@SuppressWarnings("unchecked")
		List<DataHeader> dataHeaders = query.list();
        Iterator<DataHeader> it = dataHeaders.iterator();
        while (it.hasNext()) {
        	DataHeader dHeader = it.next();
       		dHeader.setUnresolvedDataIssues(new DataIssuesServiceImpl().getUnresolvedDataIssues(dHeader.getDataHeaderPK()));
       	    dHeader.setPrdCount(getPRDCount(dHeader.getDataHeaderPK()));
       	    dHeader.setZeroCount(getZeroCount(dHeader.getDataHeaderPK()));
       	    dHeader.setIssueCount(getIssueCount(dHeader.getDataHeaderPK()));
        }

		return dataHeaders;
	}

	public List<DataHeader> getPreviousWeeksUnreleasedDataHeaders() throws DAOException {
		return getUnreleasedDataHeaders("select distinct b.*, g.GroupNumber from PRD_DataHeader b, vw_PRD_Setup c, vw_PRD_Group g "
				+ "where b.PRD_SetupFK = c.PRD_SetupPK and " 
				+ "c.Group_ContractGroupFK = g.Group_ContractGroupPK and "
				+ "b.StatusCT != 'R' and b.StatusCT != 'N' and b.StatusCT != 'V' and "
				+ "PRD_Date < (select MAX(PRD_Date) from PRD_DataHeader) "
				+ "order by g.GroupNumber ");
	}

	// includes dataheaders with unresolved issues and those on hold
	public List<DataHeader> getAllUnreleasedDataHeaders() throws DAOException {
		return getUnreleasedDataHeaders("select distinct b.*, g.GroupNumber from PRD_DataHeader b, vw_PRD_Setup c, vw_PRD_Group g "
				+ "where b.PRD_SetupFK = c.PRD_SetupPK and " 
				+ "c.Group_ContractGroupFK = g.Group_ContractGroupPK and "
				+ "b.StatusCT != 'R' and b.StatusCT != 'N' and b.StatusCT != 'V' and b.StatusCT != 'P' order by g.GroupNumber ");
	}

	public List<DataHeader> getUnreleasedDataHeaders(String sql) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(DataHeader.class);
		@SuppressWarnings("unchecked")
		List<DataHeader> dataHeaders = query.list();
        Iterator<DataHeader> it = dataHeaders.iterator();
        while (it.hasNext()) {
        	DataHeader dHeader = it.next();
        	//dHeader.setPayrollDeductionDatas(new DataDao().getPayrollDeductionDataList(
        	//		Long.toString(dHeader.getDataHeaderPK()), false));
       		dHeader.setUnresolvedDataIssues(new DataIssuesServiceImpl().getUnresolvedDataIssues(dHeader.getDataHeaderPK()));
        	if (!dHeader.getUnresolvedDataIssues().isEmpty() || dHeader.getPrdSettings().isHoldForReview()) {
        	    dHeader.setPrdCount(getPRDCount(dHeader.getDataHeaderPK()));
        	    dHeader.setZeroCount(getZeroCount(dHeader.getDataHeaderPK()));
        	    dHeader.setIssueCount(dHeader.getUnresolvedDataIssues().size());
        	    /*
        	    System.out.println("----------");
        	    System.out.println(dHeader.getIssueCount());
        	    System.out.println(dHeader.getUnresolvedDataIssues().size());
        	    if (dHeader.getIssueCount() != dHeader.getUnresolvedDataIssues().size()) {
        	    	System.out.println("not equal");
        	    }
        	    */
        	} 

        }
		
		return dataHeaders;

	}

	public List<DataHeader> getDataHeaders(Serializable prdSetupFK) throws DAOException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		//System.out.println(prdSetupFK);
        Criteria c = session.createCriteria(DataHeader.class);
        c.add(Restrictions.eq("setupFK", prdSetupFK));
        List<DataHeader> dhs = findAll(c);
        return dhs;
	}
	
	public DataHeader updateDataHeader(DataHeader dataHeader) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "update PRD_DataHeader" 
				+ " set StatusCT = ?, isTest = ? where PRD_DataHeaderPK = ? ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, dataHeader.getStatusCT());
			preparedStatement.setBoolean(2, dataHeader.isTestPrd());
			preparedStatement.setLong(3, dataHeader.getDataHeaderPK());
			if (dataHeader.getStatusCT() == null) {
				dataHeader.setStatusCT(DataHeader.PENDING);
			}
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	    return dataHeader;	
	}
	
	public void deleteDataHeaders(List<DataHeader> dataHeaders) {

		Connection connection = SessionHelper.getSession(SessionHelper.PRD)
				.connection();
		String sql = "delete PRD_DataHeader " + " where PRD_DataHeaderPK = ?";
		try {
			Iterator<DataHeader> it = dataHeaders.iterator();
			while (it.hasNext()) {
				DataHeader dataHeader = it.next();

   			    DataIssuesService dis = new DataIssuesServiceImpl();
   			    dis.deleteDataIssuesFromDataHeader(dataHeader);

    			DataService ds = new DataServiceImpl();
    			ds.deletePayrollDeductionDataFromDataHeader(dataHeader);
	    				
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setLong(1, dataHeader.getDataHeaderPK());
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
		}

	}
	
	
	public DataHeader saveDataHeader(DataHeader dataHeader) throws DAOException {
		Connection connection = SessionHelper.getSession(SessionHelper.PRD).connection();
		String sql = "insert into PRD_DataHeader (PRD_SetupFK, PRD_Date, Comments, AddDate, "
                     + "AddUser, ModDate, ModUser, StatusCT, isTest) " 
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, dataHeader.getSetupFK());
			preparedStatement.setDate(2, dataHeader.getPrdDate());
			preparedStatement.setString(3,dataHeader.getComments());
			preparedStatement.setDate(4, null);
			preparedStatement.setString(5, null);
			preparedStatement.setDate(6, null);
			preparedStatement.setString(7, null);
			if (dataHeader.getStatusCT() == null) {
				dataHeader.setStatusCT(DataHeader.PENDING);
			}
			preparedStatement.setString(8, dataHeader.getStatusCT());
			preparedStatement.setBoolean(9, dataHeader.isTestPrd());

			preparedStatement.executeUpdate();
	        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	dataHeader.setDataHeaderPK(generatedKeys.getLong(1));
	            }
	            else {
	                System.out.println("Creating messageTemplate failed, no ID obtained.");
	                throw new SQLException("Creating messageTemplate failed, no ID obtained.");
	            }
	        }

		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	    return dataHeader;	
	}

}
