package com.editsolutions.prd.dao;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.editsolutions.prd.util.DAOException;
import com.editsolutions.prd.vo.BusinessDay;




import edit.services.db.hibernate.SessionHelper;

public class BusinessDayDao extends Dao<BusinessDay> {

	public BusinessDayDao() {
        super(BusinessDay.class);
    }
	
	public List<BusinessDay> getBusinessDays(String year) throws DAOException {
		    Session session = SessionHelper.getSession(SessionHelper.PRD);
		    session.clear();
		    Criteria cr = session.createCriteria(BusinessDay.class);
		    String firstDayOfYearString = year + "-01-01 00:00:00.000";
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.ms");
		    Date firstDayOfYear = null;

		try {
		    firstDayOfYear = formatter.parse(firstDayOfYearString);
		} catch (ParseException e) {
			throw new DAOException(e);
		}
	    	
		cr.add(Restrictions.ge("businessDate", firstDayOfYear));
		cr.addOrder(Order.asc("businessDate"));
		@SuppressWarnings("unchecked")
		List<BusinessDay> list = cr.list();
		return list;
	}
	
}
