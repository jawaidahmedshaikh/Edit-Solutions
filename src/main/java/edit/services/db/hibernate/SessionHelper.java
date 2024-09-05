/* User: gfrosti
 * Date: Mar 21, 2005
 * Time: 9:22:29 AM
 *
 * (c) 2000-2008 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */

package edit.services.db.hibernate;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import query.QueryResult;
import edit.common.EDITBigDecimal;
import edit.common.EDITDate;
import edit.common.EDITDateTime;
import edit.common.exceptions.EDITNonFinancialException;
import edit.common.vo.VOObject;
import edit.common.vo.ValidationVO;
import edit.services.logging.Logging;
import edit.services.db.*;
import engine.common.Constants;
import fission.ui.servlet.SEGResponseWrapper;
import fission.utility.*;

import java.beans.Statement;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import logging.Log;
import logging.LogEvent;

import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;
import org.hibernate.EntityMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.hql.ParameterTranslations;
import org.hibernate.hql.ast.QueryTranslatorImpl;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.mapping.*;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.tuple.IdentifierProperty;
import org.hibernate.tuple.StandardProperty;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.type.CollectionType;
import org.hibernate.type.EntityType;
import org.hibernate.type.Type;


public class SessionHelper {
	// Just a token - no distinct value required. The Non Financial
	// Framework will see if this token in in ThreadLocal - if so,
	// it will skip NF processing.
	public static final String DISABLE_NF_FRAMEWORK = "true";

	private static final DBSessionFactory dbSessionFactory;

	private static final ThreadLocal editSolutionsThreadTransaction = new ThreadLocal();
	private static final ThreadLocal odsThreadTransaction = new ThreadLocal();
	private static final ThreadLocal prdThreadTransaction = new ThreadLocal();
	private static final ThreadLocal controlThreadTransaction = new ThreadLocal();
	private static final ThreadLocal engineThreadTransaction = new ThreadLocal();
	private static final ThreadLocal securityThreadTransaction = new ThreadLocal();
	private static final ThreadLocal stagingThreadTransaction = new ThreadLocal();
	private static final ThreadLocal datawarehouseThreadTransaction = new ThreadLocal();
	private static final ThreadLocal miscellaneousThreadTransaction = new ThreadLocal();

	private static final ThreadLocal editSolutionsThreadSession = new ThreadLocal();
	private static final ThreadLocal odsThreadSession = new ThreadLocal();
	private static final ThreadLocal prdThreadSession = new ThreadLocal();
	private static final ThreadLocal controlThreadSession = new ThreadLocal();
	private static final ThreadLocal engineThreadSession = new ThreadLocal();
	private static final ThreadLocal securityThreadSession = new ThreadLocal();
	private static final ThreadLocal stagingThreadSession = new ThreadLocal();
	private static final ThreadLocal datawarehouseThreadSession = new ThreadLocal();
	private static final ThreadLocal miscellaneousThreadSession = new ThreadLocal();

	/**
	 * Used to stored thread-scoped named values. Behind-the-scenes, the
	 * ThreadLocal contains a Map to stored the desired named values.
	 */
	private static final ThreadLocal namedValuesThread = new ThreadLocal();

	public static final String EDITSOLUTIONS = DBSessionFactory.EDITSOLUTIONS;
	public static final String ENGINE = DBSessionFactory.PRASE;
	public static final String SECURITY = DBSessionFactory.SECURITY;
	public static final String STAGING = DBSessionFactory.STAGING;
	public static final String DATAWAREHOUSE = DBSessionFactory.DATAWAREHOUSE;
	public static final String MISCELLANEOUS = DBSessionFactory.MISCELLANEOUS;
	public static final String PRD = DBSessionFactory.PRD;
	public static final String ODS = DBSessionFactory.ODS;
	public static final String CONTROL = DBSessionFactory.CONTROL;

	public static final String[] TARGET_DATABASES = { EDITSOLUTIONS, ENGINE,
			SECURITY, STAGING, DATAWAREHOUSE, PRD, ODS, CONTROL };

	private static final Map voToHibernate = new HashMap();

	private static final Map hibernateToVO = new HashMap();

	/**
	 * Stores, as needed, the corresponding Hibernate class as a value for the
	 * specified table name as a key. This works only in that table names are
	 * considered unique within our system.
	 */
	private static final Map tableToHibernate = new HashMap();

	static {
		dbSessionFactory = DBSessionFactory.getInstance();
	}

	private SessionHelper() {
	}

	/**
	 * Starts a transaction for the underlying Hibernate Session associated with
	 * this SessionHelper.
	 */
	public static void beginTransaction(String targetDB) {
		Transaction transaction = getTransaction(targetDB);

		try {
			if (transaction == null) {
				transaction = getSession(targetDB).beginTransaction();

				ThreadLocal threadTransaction = getTransactionThreadLocal(targetDB);

				threadTransaction.set(transaction);
			}
		} catch (HibernateException e) {
			System.out.println(e);

			e.printStackTrace();

			throw e;
		}
	}

	/**
	 * Convenience method to retrieve the appropriate ThreadLocal for Hibernate
	 * Sessions by DB name.
	 * 
	 * @param targetDB
	 * 
	 * @return
	 */
	private static ThreadLocal getSessionThreadLocal(String targetDB) {
		Object threadLocal = null;

		if (targetDB.equals(EDITSOLUTIONS)) {
			threadLocal = editSolutionsThreadSession;
		} else if (targetDB.equals(ENGINE)) {
			threadLocal = engineThreadSession;
		} else if (targetDB.equals(SECURITY)) {
			threadLocal = securityThreadSession;
		} else if (targetDB.equals(STAGING)) {
			threadLocal = stagingThreadSession;
		} else if (targetDB.equals(DATAWAREHOUSE)) {
			threadLocal = datawarehouseThreadSession;
		} else if (targetDB.equals(MISCELLANEOUS)) {
			threadLocal = miscellaneousThreadSession;
		} else if (targetDB.equals(PRD)) {
			threadLocal = prdThreadSession;
		} else if (targetDB.equals(ODS)) {
			threadLocal = odsThreadSession;
		} else if (targetDB.equals(CONTROL)) {
			threadLocal = controlThreadSession;
		}

		return (ThreadLocal) threadLocal;
	}

	/**
	 * Convenience method to retrieve the appropriate ThreadLocal for Hibernate
	 * Sessions by DB name.
	 * 
	 * @param targetDB
	 * 
	 * @return
	 */
	private static ThreadLocal getTransactionThreadLocal(String targetDB) {
		Object threadLocal = null;

		if (targetDB.equals(EDITSOLUTIONS)) {
			threadLocal = editSolutionsThreadTransaction;
		} else if (targetDB.equals(ENGINE)) {
			threadLocal = engineThreadTransaction;
		} else if (targetDB.equals(SECURITY)) {
			threadLocal = securityThreadTransaction;
		} else if (targetDB.equals(STAGING)) {
			threadLocal = stagingThreadTransaction;
		} else if (targetDB.equals(DATAWAREHOUSE)) {
			threadLocal = datawarehouseThreadTransaction;
		} else if (targetDB.equals(MISCELLANEOUS)) {
			threadLocal = miscellaneousThreadTransaction;
		} else if (targetDB.equals(PRD)) {
			threadLocal = prdThreadTransaction;
		} else if (targetDB.equals(ODS)) {
			threadLocal = odsThreadTransaction;
		} else if (targetDB.equals(CONTROL)) {
			threadLocal = controlThreadTransaction;
		}

		return (ThreadLocal) threadLocal;
	}

	/**
	 * Commits the current transaction for the underlying Hibernate Session
	 * associated with this SessionHelper.
	 */
	public static void commitTransaction(String targetDB) {
		Transaction transaction = getTransaction(targetDB);

		try {
			if ((transaction != null) && !transaction.wasCommitted()
					&& !transaction.wasRolledBack()) {
				// "Remove" the transaction from the thread BEFORE the commit.
				// The commit triggers a call to
				// HibernateEntityDifferenceInterceptor's
				// beforeTransactionComplete and afterTransactionComplete
				// methods as part of the Non-Financial framework. Transactions
				// may be begun and committed during these
				// methods and they will conflict with transactions that are
				// still on the thread but have already
				// been committed.
				ThreadLocal threadTransaction = getTransactionThreadLocal(targetDB);

				threadTransaction.set(null);

				transaction.commit();

				// Yes, this is odd. I wanted to throw the
				// EDITNonFinancialException from
				// within the HibernateEntityDifferenceInterceptor. However,
				// Hibernate
				// was eating any exception thrown from within that callback
				// framework.
				// I am force to throw the exception from higher within the call
				// stack.
				if (SEGResponseWrapper.nonFinancialEditsExist()) {
					List<ValidationVO> nonFinancialEdits = SEGResponseWrapper
							.getNonFinancialEdits();

					throw new EDITNonFinancialException(nonFinancialEdits);
				}
			}
		}
		// We are purposely catching Throwable for the opportunity to rollback
		// on any exception.
		// Should this same session be rolled-back a second time, it will be
		// ignored.
		catch (Throwable e) {
			rollbackTransaction(transaction, targetDB);

			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		}
	}

	/**
	 * Creates a new Hibernate session for the given database, disconnected from
	 * any specific ThreadLocal instance
	 * 
	 * @param targetDB
	 *            The database to construct a new session for
	 * @return The newly created session
	 */
	public static Session getNewSession(String targetDB) {
		return dbSessionFactory.getSession(targetDB);
	}

	/**
	 * The underlying Hibernate Session.
	 * 
	 * @return
	 */
	public static Session getSession(String targetDB) {
		ThreadLocal threadSession = getSessionThreadLocal(targetDB);

		Session session = (Session) threadSession.get();

		if (session == null || !session.isOpen()) {
			session = dbSessionFactory.getSession(targetDB);
			if (targetDB.equals("ODS")) {
			    Connection connection = session.connection();
			    String sql = "OPEN MASTER KEY DECRYPTION BY PASSWORD = '6Od[S1~U7bK9(CP5h}izM0kme_k9ie#6YP!Np1hJ'; " +
			    
			    "open symmetric key OdsEncryptionKey decryption by certificate OdsEncryptionCert";
			    java.sql.Statement statement;
				try {
					statement = connection.createStatement();
			        statement.execute(sql);
			        
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (connection != null && !connection.isClosed()) {
							connection.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			if (session != null) {
				threadSession.set(session);
			}
		}

		return session;
	}
	
	public static Session getSessionToClose(String targetDB) {
		ThreadLocal threadSession = getSessionThreadLocal(targetDB);

		Session session = (Session) threadSession.get();

		if (session == null || !session.isOpen()) {
			session = dbSessionFactory.getSession(targetDB);
			
			if (session != null) {
				threadSession.set(session);
			}
		}

		return session;
	}

	/**
	 * Returns a DOMJ Session from the current Hibernate Session. This DOM4J
	 * Session is <b>not</b> preserved throughout the life-cycle of the thread
	 * via ThreadLocal.
	 * 
	 * @param targetDB
	 * 
	 * @return
	 */
	public static Session getDOM4JSession(String targetDB) {
		Session dom4JSession;

		Session hibernateSession = getSession(targetDB);

		dom4JSession = hibernateSession.getSession(EntityMode.DOM4J);

		return dom4JSession;
	}

	/**
	 * Closes the Hibernate Session associated with this SessionHelper.
	 */
	public static void closeSession(String targetDB) {
		try {
			Session session = getSessionToClose(targetDB);

			if ((session != null) && session.isOpen()) {
				session.close();

				ThreadLocal threadSession = getSessionThreadLocal(targetDB);

				threadSession.set(null);
			}
		} catch (HibernateException e) {
			rollbackTransaction(targetDB);

			System.out.println(e);

			e.printStackTrace();

			throw e;
		}
	}

	/**
	 * Deletes the specified object.
	 * 
	 * @param obj
	 */
	public static void delete(Object obj, String targetDB) {
		delete(obj, getSession(targetDB));
	}

	/**
	 * Deletes the specified object.
	 * 
	 * @param obj
	 * @param session
	 */
	public static void delete(Object obj, Session session) {
		try {
			session.delete(obj);
		} catch (HibernateException e) {
			System.out.println(e);

			e.printStackTrace();

			throw e;
		}
	}

	/**
	 * Executes a the Query from Session.createQuery(HQL).
	 * 
	 * @param hql
	 * @param namedParameters
	 * @param maxResults
	 *            limits the results to the specified size - if 0, then no max
	 *            size is set
	 * @param maxResults
	 *            limits the results to the specified size
	 * 
	 * @return
	 */
	public static List executeHQL(Session session, String hql,
			Map namedParameters, int maxResults) {
		return executeHQL(session, hql, namedParameters, 0, maxResults);
	}

	/**
	 * Executes a the Query from Session.createQuery(HQL).
	 * 
	 * @param hql
	 * @param namedParameters
	 * @param firstResult
	 *            the starting point within the results from which to extract
	 * @param maxResults
	 *            limits the results to the specified size
	 * 
	 * @return
	 */
	public static List executeHQL(Session session, String hql,
			Map namedParameters, int firstResult, int maxResults) {
		List results = null;

		Query q;

		try {
			q = buildQuery(maxResults, firstResult, hql, session,
					namedParameters);

			results = q.list();
		} catch (HibernateException e) {
			System.out.println(e);

			e.printStackTrace();

			throw e;
		}

		if (results == null) {
			results = new ArrayList();
		}

		return results;
	}

	/**
	 * Executes a the Query using a StatelessSession to prevent entities from
	 * getting into the Hibernate Session.
	 * 
	 * @param hql
	 * @param namedParameters
	 * 
	 * @return
	 */
	public static List executeHQLStateless(String hql, Map namedParameters,
			String targetDB) {
		StatelessSession session = getStatelessSession(targetDB);

		// This is so [rarely] used that I eliminated it from the parameter list
		// of the method and defaulted it to 0.
		int maxResults = 0;

		List results;

		Query q;

		try {
			q = session.createQuery(hql);

			if (maxResults > 0) {
				q.setMaxResults(maxResults);
			}

			if (namedParameters != null) {
				Set parameterNames = namedParameters.keySet();

				for (Iterator iterator = parameterNames.iterator(); iterator
						.hasNext();) {
					String parameterName = (String) iterator.next();

					Object parameterObject = namedParameters.get(parameterName);

					if (parameterObject instanceof java.util.List) {
						q.setParameterList(parameterName,
								(List) parameterObject);
					} else if (parameterObject instanceof Object[]) {
						q.setParameterList(parameterName,
								(Object[]) parameterObject);
					} else {
						q.setParameter(parameterName, parameterObject);
					}
				}
			}

			results = q.list();
		} catch (HibernateException e) {
			System.out.println(e);

			e.printStackTrace();

			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}

		if (results == null) {
			results = new ArrayList();
		}

		return results;
	}

	/**
	 * Executes a the Query from Session.createQuery(HQL).
	 * 
	 * @param hql
	 * @param namedParameters
	 * 
	 * @return
	 */
	public static List executeHQL(String hql, Map namedParameters,
			String targetDB) {
		Session session = getSession(targetDB);

		return executeHQL(session, hql, namedParameters, -1, -1);
	}

	/**
	 * Allows for pagigation of Hibernate results.
	 * 
	 * @param hql
	 * @param namedParameters
	 * @param targetDB
	 * @param page
	 *            which page to render in a multi-paged result set - it is
	 *            zero-indexed
	 * @param pageSize
	 *            the size of each page to render
	 * 
	 * @return
	 * 
	 * @see Pagination
	 */
	public static List executeHQLPage(String hql, Map namedParameters,
			String targetDB, int page, int pageSize) {
		Session session = getSession(targetDB);

		int firstResult = page * pageSize;

		int maxResults = pageSize;

		List results = executeHQL(session, hql, namedParameters, firstResult,
				maxResults);

		return results;
	}

	/**
	 * Returns the count of records. This is useful when count is needed without
	 * initializing the collection.
	 * 
	 * @param hql
	 * @param namedParameters
	 * @param targetDB
	 * 
	 * @return
	 */
	public static int executeHQLForCount(String hql, Map namedParameters,
			String targetDB) {
		Session session = getSession(targetDB);

		Query query = session.createQuery(hql);

		if (namedParameters != null) {
			Set parameterNames = namedParameters.keySet();

			for (Iterator iterator = parameterNames.iterator(); iterator
					.hasNext();) {
				String parameterName = (String) iterator.next();

				Object parameterObject = namedParameters.get(parameterName);

				query.setParameter(parameterName, parameterObject);
			}
		}

		return ((Long) query.iterate().next()).intValue();
	}

	/**
	 * A complimentary method to the stand get(...), except that this version
	 * uses a separate Session so as not to conflict with any current Hibernate
	 * Session.
	 * 
	 * @param entityClass
	 * @param entityPK
	 * @param targetDB
	 * 
	 * @return
	 */
	public static Object getFromSeparateSession(Class entityClass,
			Long entityPK, String targetDB) {
		Object hibernateEntity = null;

		Session session = null;

		try {
			session = getSeparateSession(targetDB);

			hibernateEntity = session.get(entityClass, entityPK);
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return hibernateEntity;
	}

	/**
	 * A convenience method to find a Hibernate Entity by class and pk.
	 * 
	 * @param entityClass
	 * @param entityPK
	 * 
	 * @return
	 */
	public static Object get(Class entityClass, Long entityPK, String targetDB) {
		Object entity;
		if (entityClass == null) {
			System.out.println("entityClass is null");
		}
		if (entityPK == null) {
			System.out.println("entityPK is null");
		}
		if (targetDB == null) {
			System.out.println("targetDB is null");
		}
		try {
			entity = getSession(targetDB).get(entityClass, entityPK);
		} catch (HibernateException e) {
			System.out.println("SessionHelper.get(Class entityClass, Long entityPK, String targetDB): " + e);
			e.printStackTrace();
			throw e;
		}

		return entity;
	}

	/**
	 * The associatied Configuration for the Hibernate Session.
	 * 
	 * @return
	 */
	public static Configuration getConfiguration(String targetDB) {
		return DBSessionFactory.getInstance().getConfiguration(targetDB);
	}

	public static boolean isInTransaction(String targetDB) {
		Transaction transaction = getTransaction(targetDB);

		if (transaction == null) {
			return false;
		} else {
			if (transaction.wasCommitted()) {
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * The Transaction in the current ThreadLocal.
	 * 
	 * @param targetDB
	 * 
	 * @return
	 */
	private static Transaction getTransaction(String targetDB) {
		ThreadLocal threadTransaction = getTransactionThreadLocal(targetDB);

		return (Transaction) threadTransaction.get();
	}

	/**
	 * Associates the unmodified object to this Session.
	 * 
	 * @param obj
	 */
	public static void lock(Object obj, String targetDB) {
		try {
			getSession(targetDB).lock(obj, LockMode.NONE);
		} catch (HibernateException e) {
			System.out.println(e);

			e.printStackTrace();

			throw e;
		}
	}

	/**
	 * Rolls back a specified transaction. This method can be useful if the
	 * transaction has been disconnected from the thread-local of the associated
	 * database.
	 * 
	 * @param transaction
	 *            The transaction to roll back
	 * @param targetDB
	 *            The database for which to disconnect the thread-local's
	 *            transaction. If already disconnected, supply null.
	 */
	public static void rollbackTransaction(Transaction transaction,
			String targetDB) {
		try {
			if ((transaction != null) && !transaction.wasCommitted()
					&& !transaction.wasRolledBack()) {
				transaction.rollback();

				if (targetDB != null) {
					ThreadLocal threadTransaction = getTransactionThreadLocal(targetDB);

					threadTransaction.set(null);
				}
			}
		} catch (HibernateException e) {
			System.out.println(e);

			e.printStackTrace();

			throw e;
		}
	}

	/**
	 * Rolls-back the current transaction for the underlying Hibernate Session
	 * associated with this SessionHelper.
	 */
	public static void rollbackTransaction(String targetDB) {
		Transaction transaction = getTransaction(targetDB);
		rollbackTransaction(transaction, targetDB);
	}

	/**
	 * Saves or updates the Hibernate entity.
	 * 
	 * @param object
	 */
	public static void saveOrUpdate(Object object, String targetDB) {
		try {
			getSession(targetDB).saveOrUpdate(object);
		} catch (HibernateException e) {
			System.out.println(e);

			e.printStackTrace();

			throw e;
		}
	}

	/**
	 * A pk value of null needs to be mapped to 0 for CRUD.
	 * 
	 * @param pk
	 * 
	 * @return
	 */
	public static long getPKValue(Long pk) {
		long pkValue = 0;

		if (pk != null) {
			pkValue = pk.longValue();
		}

		return pkValue;
	}

	/**
	 * A pk value of 0 is needs to be mapped to null for Hibernate.
	 * 
	 * @param pk
	 * 
	 * @return
	 */
	public static Long getPKValue(long pk) {
		Long pkValue = null;

		if (pk != 0) {
			pkValue = new Long(pk);
		}

		return pkValue;
	}

	/**
	 * Populates a proxy with its db values.
	 * 
	 * @param entity
	 */
	public static void initialize(Object entity) {
		try {

			Hibernate.initialize(entity);
		} catch (HibernateException e) {
			System.out.println(e);

			e.printStackTrace();

			throw e;
		}
	}

	/**
	 * Attempts to close all potention sessions for this ThreadLocal.
	 */
	public static void closeSessions() {
		String[] targetDBs = SessionHelper.TARGET_DATABASES;

		for (int i = 0; i < targetDBs.length; i++) {
			String targetDB = targetDBs[i];

			closeSession(targetDB);
		}
	}

	/**
	 * Reassociates a JDBC Connection with the specified Hiberate Session.
	 * 
	 * @param session
	 * @param targetDB
	 */
	private static void reconnect(Session session, String targetDB) {
		try {
			session.reconnect();

			ThreadLocal threadSession = getSessionThreadLocal(targetDB);

			threadSession.set(session);
		} catch (HibernateException e) {
			System.out.println(e);

			e.printStackTrace();

			throw e;
		}
	}

	/**
	 * Closes the Hibernate Session associated with this SessionHelper.
	 */
	private static Session disconnectSession(String targetDB) {
		Session session;

		try {
			session = getSession(targetDB);

			if ((session != null) && session.isConnected() & session.isOpen()) {
				session.disconnect();

				ThreadLocal threadSession = getSessionThreadLocal(targetDB);

				threadSession.set(null);
			}
		} catch (HibernateException e) {
			rollbackTransaction(targetDB);

			System.out.println(e);

			e.printStackTrace();

			throw e;
		}

		return session;
	}

	/**
	 * Flushes all objects from Hibernate Session's memory.
	 */
	public static void flushSessions() {
		for (int i = 0; i < TARGET_DATABASES.length; i++) {
			String targetDB = TARGET_DATABASES[i];

			flushSession(targetDB);
		}
	}

	/**
	 * Flushes current state to the specified DB.
	 * 
	 * @param targetDB
	 */
	private static void flushSession(String targetDB) {
		try {
			Session session = getSession(targetDB);

			if ((session != null) && session.isOpen()) {
				getSession(targetDB).flush();
			}
		} catch (HibernateException e) {
			rollbackTransaction(targetDB);

			System.out.println(e);

			e.printStackTrace();

			throw e;
		}
	}

	/**
	 * Clears the session-level cache of the specified DB.
	 * 
	 * @param targetDB
	 */
	public static void clearSession(String targetDB) {
		try {
			Session session = getSessionToClose(targetDB);

			if ((session != null) && session.isOpen()) {
				session.clear();
			}
		} catch (HibernateException e) {
			rollbackTransaction(targetDB);

			System.out.println(e);

			e.printStackTrace();

			throw e;
		}
	}

	/**
	 * Removes all objects from Hibernate Session's memory.
	 */
	public static void clearSessions() {
		for (int i = 0; i < TARGET_DATABASES.length; i++) {
			String targetDB = TARGET_DATABASES[i];

			clearSession(targetDB);
		}
	}

	/**
	 * Builds a unique Hibernate Session name.
	 * 
	 * @param targetDB
	 * 
	 * @return
	 */
	public static String getSessionName(String targetDB) {
		return "hibernateSession." + targetDB;
	}

	/**
	 * A convenience method to build a DOM4J Element from a Hibernate Pojo.
	 * 
	 * @param pojo
	 *            the target class as pojo may be a Hibernate proxy.
	 * @return
	 */
	public static Element mapToElement(HibernateEntity pojo, String targetDB) {
		String fullyQualifiedClassName = Util.getFullyQualifiedClassName(pojo
				.getClass());

		String className = Util.getClassName(fullyQualifiedClassName);

		Element pojoAsElement = new DefaultElement(className + "VO");

		mapToElement(pojo, pojoAsElement, targetDB, false, false);

		return pojoAsElement;
	}

	/**
	 * A convenience method to build a DOM4J Element from a Hibernate Pojo.
	 * 
	 * @param pojo
	 *            the target class as pojo may be a Hibernate proxy.
	 * @param targetDB
	 * @param mapNullFields
	 * @param convertDates
	 * 
	 * @return
	 */
	public static Element mapToElement(HibernateEntity pojo, String targetDB,
			boolean mapNullFields, boolean convertDates) {
		String fullyQualifiedClassName = Util.getFullyQualifiedClassName(pojo
				.getClass());

		String className = Util.getClassName(fullyQualifiedClassName);

		Element pojoAsElement = new DefaultElement(className + "VO");

		mapToElement(pojo, pojoAsElement, targetDB, mapNullFields, convertDates);

		return pojoAsElement;
	}

	/**
	 * A convenience method to build a DOM4J Element from a Hibernate Pojo. FK's
	 * that are null are mapped to zeros (0).
	 * 
	 * @param pojo
	 *            the target class as pojo may be a Hibernate proxy.
	 * @param dom4jElement
	 *            a supplied dom4jElement should the calling client want to
	 *            preserve the element by reference
	 * @param mapNullFields
	 * @param convertDates
	 */
	public static void mapToElement(HibernateEntity pojo, Element dom4jElement,
			String targetDB, boolean mapNullFields, boolean convertDates) {
		try {
			Column[] columns = getColumns(pojo.getClass(), targetDB);

			Column column;

			String columnName;

			Object columnValue;

			Method columnMethod;

			Class columnReturnType;

			Element columnElement;

			for (int i = 0; i < columns.length; i++) {
				column = columns[i];

				columnName = column.getName();

				columnMethod = pojo.getClass().getMethod("get" + columnName,
						null);

				columnReturnType = columnMethod.getReturnType();

				columnValue = columnMethod.invoke(pojo, null);

				if (columnValue != null) {
					columnElement = new DefaultElement(columnName);

					if ((columnReturnType.equals(EDITDate.class))
							&& convertDates) {
						columnElement
								.setText(DateTimeUtil
										.formatEDITDateAsMMDDYYYY((EDITDate) columnValue));
					} else if ((columnReturnType.equals(EDITDateTime.class))
							&& convertDates) {
						columnElement
								.setText(DateTimeUtil
										.formatEDITDateTimeAsMMDDYYYY((EDITDateTime) columnValue));
					} else {
						columnElement.setText(columnValue.toString());
					}

					dom4jElement.add(columnElement);
				} else if (columnValue == null && mapNullFields) {
					// is null, just create an empty tag
					columnElement = new DefaultElement(columnName);

					dom4jElement.add(columnElement);
				} else if (columnName.endsWith("FK")) // Don't forget the FK's.
				{
					columnElement = new DefaultElement(columnName);

					// The FK is null, need to see if the parent object exists.
					String parentName = Util.stripString(columnName, "FK"); // strip
																			// the
																			// FK
																			// off
																			// to
																			// get
																			// the
																			// parent

					// First, see if the parent is even mapped for this pojo
					// (the FK may be mapped but not the parent object)
					ClassMetadata pojoMetadata = getClassMetadata(
							pojo.getClass(), getSession(targetDB));

					// In the case of CompanyFK - no metadata is found
					if (pojoMetadata != null) {
						String[] pojoPropertyNames = pojoMetadata
								.getPropertyNames();

						if (Util.verifyStringExistsInArray(pojoPropertyNames,
								parentName)) {
							// Parent is mapped to this pojo. Now see if the
							// parent object is attached.
							Method parentMethod = pojo.getClass().getMethod(
									"get" + parentName, null);

							Object parentObject = parentMethod.invoke(pojo,
									null);

							// If the parent does not exist, set the FK to zero.
							// If it does exist, set the FK to the parent's PK.
							if (parentObject == null) {
								columnElement.setText("0");
							} else {
								Method parentPKMethod = parentObject.getClass()
										.getMethod("get" + parentName + "PK",
												null);

								Long parentPKValue = (Long) parentPKMethod
										.invoke(parentObject, null);

								columnElement.setText(parentPKValue.toString());
							}
						} else {
							columnElement.setText("0");
						}
					} else {
						columnElement.setText("0");
					}

					dom4jElement.add(columnElement);
				}
			}
		} catch (Exception e) {
			Logging.getLogger(Logging.GENERAL_EXCEPTION).fatal(new LogEvent(e));

			Log.logGeneralExceptionToDatabase(null, e);

			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		}
	}

	/**
	 * This is a convenience method to get the mapToElementWithoutKeyLogic
	 * method (so the element doesn't need to be passed in)
	 * 
	 * @param pojo
	 * @param targetDB
	 * @param mapNullFields
	 * @param convertDates
	 * @return
	 */
	public static Element mapToElementWithoutKeyLogic(HibernateEntity pojo,
			String targetDB, boolean mapNullFields, boolean convertDates) {
		String fullyQualifiedClassName = Util.getFullyQualifiedClassName(pojo
				.getClass());

		String className = Util.getClassName(fullyQualifiedClassName);

		Element pojoAsElement = new DefaultElement(className + "VO");

		mapToElementWithoutKeyLogic(pojo, pojoAsElement, targetDB,
				mapNullFields, convertDates);

		return pojoAsElement;
	}

	/**
	 * A convenience method to build a DOM4J Element from a Hibernate Pojo. It
	 * does not use "smart" logic for handling pks and fks. FK's that are null
	 * are mapped to zeros (0). If you want smart key logic, Use the
	 * mapToElement methods.
	 * 
	 * @param pojo
	 *            the target class as pojo may be a Hibernate proxy.
	 * @param dom4jElement
	 *            a supplied dom4jElement should the calling client want to
	 *            preserve the element by reference
	 * @param mapNullFields
	 * @param convertDates
	 */
	public static void mapToElementWithoutKeyLogic(HibernateEntity pojo,
			Element dom4jElement, String targetDB, boolean mapNullFields,
			boolean convertDates) {
		try {
			Column[] columns = getColumns(pojo.getClass(), targetDB);

			Column column;

			String columnName;

			Object columnValue;

			Method columnMethod;

			Class columnReturnType;

			Element columnElement;

			for (int i = 0; i < columns.length; i++) {
				column = columns[i];

				columnName = column.getName();

				columnMethod = pojo.getClass().getMethod("get" + columnName,
						null);

				columnReturnType = columnMethod.getReturnType();

				columnValue = columnMethod.invoke(pojo, null);

				if (columnValue != null) {
					columnElement = new DefaultElement(columnName);

					if ((columnReturnType.equals(EDITDate.class))
							&& convertDates) {
						columnElement
								.setText(DateTimeUtil
										.formatEDITDateAsMMDDYYYY((EDITDate) columnValue));
					} else {
						columnElement.setText(columnValue.toString());
					}

					dom4jElement.add(columnElement);
				} else if (columnValue == null && mapNullFields) {
					// is null, just create an empty tag
					columnElement = new DefaultElement(columnName);

					dom4jElement.add(columnElement);
				} else if (columnName.endsWith("FK")) // Don't forget the FK's.
														// Set null FK's to 0.
				{
					columnElement = new DefaultElement(columnName);

					columnElement.setText("0");

					dom4jElement.add(columnElement);
				}
			}
		} catch (Exception e) {
			Logging.getLogger(Logging.GENERAL_EXCEPTION).fatal(new LogEvent(e));

			Log.logGeneralExceptionToDatabase(null, e);

			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		}
	}

	/**
	 * Maps a VOObject to a Hibernate entity. Includes mapping of FKs to
	 * hibernate objects.
	 * <P>
	 * First, converts the VOObject to an Element. Then passes the Element to
	 * the standard mapToHibernateEntity method.
	 * 
	 * @param voObject
	 *            VO to be mapped
	 * @param targetDB
	 *            target database the hibernate entity belongs to
	 * 
	 * @return Hibernate object populated with objects for the FKs
	 */
	public static Object mapVOToHibernateEntity(VOObject voObject,
			String targetDB) {
		Element element = Util.getVOAsElement(voObject);

		Class hibernateClass = getHibernateClass(voObject.getClass(), targetDB);

		return mapToHibernateEntity(hibernateClass, element, targetDB, false);
	}

	/**
	 * A convenience method to build a Hibernate object from a DOM4J Element.
	 * This method creates a session from the specified targetDB.
	 * 
	 * @param hibernateEntityClass
	 *            Class of the hibernate entity
	 * @param hibernateEntityAsElement
	 *            the hibernate entity as an element
	 * @param targetDB
	 *            target database the hibernate entity belongs to. A Session is
	 *            created for this targetDB
	 * @param convertDates
	 *            determines whether the dates should be converted to the
	 *            standard EDITDate format NOTE: we don't worry about date
	 *            formats for EDITDateTime fields. Our front end always shows
	 *            date/time fields in their standard format
	 *            (EDITDateTime.DATETIME_FORMAT).
	 * 
	 * @return a hibernate entity populated with values from the element
	 */
	public static HibernateEntity mapToHibernateEntity(
			Class hibernateEntityClass, Element hibernateEntityAsElement,
			String targetDB, boolean convertDates) {
		Session session = getSession(targetDB);

		return mapToHibernateEntity(hibernateEntityClass,
				hibernateEntityAsElement, session, convertDates);
	}

	/**
	 * A convenience method to build a Hibernate object from a DOM4J Element.
	 * This method expects a session to already exist
	 * 
	 * @param hibernateEntityClass
	 *            Class of the hibernate entity
	 * @param hibernateEntityAsElement
	 *            the hibernate entity as an childElement
	 * @param session
	 *            Session the hibernate entity belongs to
	 * @param convertDates
	 *            determines whether the dates should be converted to the
	 *            standard EDITDate format NOTE: we don't worry about date
	 *            formats for EDITDateTime fields. Our front end always shows
	 *            date/time fields in their standard format
	 *            (EDITDateTime.DATETIME_FORMAT).
	 * 
	 * @return a hibernate entity populated with values from the childElement
	 */
	public static HibernateEntity mapToHibernateEntity(
			Class hibernateEntityClass, Element hibernateEntityAsElement,
			Session session, boolean convertDates) {
		HibernateEntity hibernateEntity = null;

		try {
			String entityName = Util.getClassName(hibernateEntityClass
					.getName());

			String pkName = entityName + "PK";

			Element pkNameElement = hibernateEntityAsElement.element(pkName);

			Long pkValue = (pkNameElement != null) ? new Long(Util.initString(
					pkNameElement.getText(), "0")) : new Long(0);

			// There is no value PK value - it is a new Hibernate Entity.
			if (pkValue.longValue() == 0) {
				hibernateEntity = SessionHelper.newInstance(
						hibernateEntityClass, session);
			}
			// It's a normal everyday PK - just get the entity.
			else {
				hibernateEntity = (HibernateEntity) session.get(
						hibernateEntityClass, pkValue);

				// Check to be sure the entity was created. Could have a pk
				// value but the object no longer exists in db
				// or the pk is zero. If so, create a new instance
				if (hibernateEntity == null) {
					hibernateEntity = SessionHelper.newInstance(
							hibernateEntityClass, session);
				}
			}

			List elements = hibernateEntityAsElement.elements();

			for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();

				String columnName = element.getName();

				// Will be null for null Elements or an Element.Text of "".
				String valueString = XMLUtil.getText(element);

				// On top of looking for null or "", we also need to consider
				// the reserved word #NULL to satisfy some cases where
				// Elements were created from within PRASE. It is the hope of
				// this code (OK - it's me, Gregg) that this assertion will
				// hold.
				if (valueString != null) {
					valueString = (valueString
							.equals(Constants.ScriptKeyword.NULL) ? null
							: valueString);
				}

				String getterName = "get" + columnName;

				String setterName = "set" + columnName;

				Method getterMethod = null;

				if (!getterName.endsWith("VO")) {
					getterMethod = hibernateEntity.getClass().getMethod(
							getterName, null);

					if (columnName.equals(pkName)) {
						// ignore, don't do anything
					} else if (columnName.endsWith("FK")) {
						if (valueString != null && !valueString.equals("0")) {
							// It's an FK, need to find the real object and set
							// it
							String foreignObjectName = Util.stripString(
									columnName, "FK");

							String formerName = "";
							
							if (foreignObjectName.equalsIgnoreCase("SegmentParent") ||
								foreignObjectName.equalsIgnoreCase("SegmentChild")) 
							{
								formerName = foreignObjectName;
								foreignObjectName = "Segment";
							}

							// Only set the foreign object if it belongs to the
							// same database as the hibernate entity
							String hibernateEntityDatabase = hibernateEntity
									.getDatabase();

							if (belongsToSameDatabase(foreignObjectName,
									hibernateEntityDatabase)) {
								Class foreignObjectClass = getHibernateClass(
										foreignObjectName,
										hibernateEntityDatabase);

								Object foreignObject = foreignObject = session
										.get(foreignObjectClass, new Long(
												valueString));

								if (!formerName.equals("")) 
								{
									foreignObjectName = formerName;
								}
								
								Method setterMethod = hibernateEntity
										.getClass()
										.getMethod(
												"set" + foreignObjectName,
												new Class[] { foreignObjectClass });

								setterMethod.invoke(hibernateEntity,
										new Object[] { foreignObject });
							} else {
								setColumnValue(hibernateEntity, valueString,
										getterMethod, setterName, convertDates);
							}
						} else {
							// We don't want FK's of 0 when unmarshalling a
							// Document/Element
							// to it's Hibernate equivalent. Hibernate no-likey.
							valueString = ((valueString == null) || valueString
									.equals("0")) ? null : valueString;

							setColumnValue(hibernateEntity, valueString,
									getterMethod, setterName, convertDates);
						}
					} else {
						setColumnValue(hibernateEntity, valueString,
								getterMethod, setterName, convertDates);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		}

		return hibernateEntity;
	}

	/**
	 * @param destinationHibernateEntity
	 *            the hibernate entity to which the state of the specified
	 *            hibernateEntityElement will be mapped
	 * @param hibernateEntityAsElement
	 *            the hibernate entity as an element
	 * @param targetDB
	 *            the target db
	 * @param convertDates
	 *            determines whether the dates should be converted to the
	 *            standard EDITDate format NOTE: we don't worry about date
	 *            formats for EDITDateTime fields. Our front end always shows
	 *            date/time fields in their standard format
	 *            (EDITDateTime.DATETIME_FORMAT).
	 * 
	 * @return a hibernate entity populated with values from the element
	 */
	public static void mapToHibernateEntity(
			HibernateEntity destinationHibernateEntity,
			Element hibernateEntityAsElement, String targetDB,
			boolean convertDates) {
		try {
			Session session = getSession(targetDB);

			String entityName = Util.getClassName(destinationHibernateEntity
					.getClass().getName());

			String pkName = entityName + "PK";

			Element pkNameElement = hibernateEntityAsElement.element(pkName);

			String pkValueAsString = null;

			if (pkNameElement != null) // in case PK field was not supplied
			{
				pkValueAsString = pkNameElement.getText();
			}

			if (pkValueAsString == null || pkValueAsString.equals("")) {
				session.save(destinationHibernateEntity);
			} else {
				HibernateEntity hibernateEntityDB = (HibernateEntity) session
						.get(destinationHibernateEntity.getClass(), new Long(
								pkValueAsString));

				// Check to be sure the entity was created. Could have a pk
				// value but the object no longer exists in db
				// or the pk is zero. If so, create a new instance
				if (hibernateEntityDB != null) {
					// Map from HibernateEntityDB to specified HibernateEntity,
					// then discard the HibernateEntityDB.
					// Remember, the user passed-in the EXACT HibernateEntity
					// instance they wanted to use for the mapping.
					// Our goal is to merge the state of the specified Element
					// into the HibernateEntity.
					shallowMerge(hibernateEntityDB, destinationHibernateEntity,
							targetDB, true);

					evict(hibernateEntityDB, targetDB);
				}
			}

			List elements = hibernateEntityAsElement.elements();

			for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();

				String columnName = element.getName();

				// Will be null for null Elements or an Element.Text of "".
				String valueString = XMLUtil.getText(element);

				// On top of looking for null or "", we also need to consider
				// the reserved word #NULL to satisfy some cases where
				// Elements were created from within PRASE. It is the hope of
				// this code (OK - it's me, Gregg) that this assertion will
				// hold.
				if (valueString != null) {
					valueString = (valueString
							.equals(Constants.ScriptKeyword.NULL) ? null
							: valueString);
				}

				String getterName = "get" + columnName;

				String setterName = "set" + columnName;

				Method getterMethod = null;

				if (!getterName.endsWith("VO")) {
					try {
						getterMethod = destinationHibernateEntity.getClass()
								.getMethod(getterName, null);

						if (columnName.equals(pkName)) {
							// ignore, don't do anything
						} else if (columnName.endsWith("FK")) {
							if (valueString != null && !valueString.equals("0")) {
								// It's an FK, need to find the real object and
								// set it
								String foreignObjectName = Util.stripString(
										columnName, "FK");

								Class foreignObjectClass = destinationHibernateEntity
										.getClass()
										.getMethod("get" + foreignObjectName,
												null).getReturnType();

								// I hit the scenario where this method is
								// trying to associate Segment to
								// ProductStructure.
								// This is not possible since they exist in
								// separate DBs. I can find no clean way to
								// address
								// this other than to assume that if the desired
								// foreign object does not have metadata
								// via the targetDB, then the association should
								// not be attempted. FKs will still be preserved
								// since they should have been mapped as
								// properties in the Foo.hbm.xml file.
								if (getClassMetadata(foreignObjectClass,
										session) != null) {
									Object foreignObject = session.get(
											foreignObjectClass, new Long(
													valueString));

									Method setterMethod = destinationHibernateEntity
											.getClass()
											.getMethod(
													"set" + foreignObjectName,
													new Class[] { foreignObjectClass });

									setterMethod.invoke(
											destinationHibernateEntity,
											new Object[] { foreignObject });
								} else {
									setColumnValue(destinationHibernateEntity,
											valueString, getterMethod,
											setterName, convertDates);
								}
							}
						} else {
							setColumnValue(destinationHibernateEntity,
									valueString, getterMethod, setterName,
									convertDates);
						}
					} catch (Exception e) {
						// Deliberately squashing this "error" - we are allowing
						// the Element
						// to drive the mapping process. The Element may have
						// non "db table" .
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		}
	}

	/**
	 * Converts the specified string value to an intance of its true data type
	 * as specified by dataType.
	 * 
	 * @param convertDates
	 *            if true, then String dates are expected to be coming in as
	 *            mm/dd/yyyy - i.e. they must now be converted to yyyy/mm/dd
	 *            which is our standard format
	 * @param valueString
	 * @param dataType
	 * 
	 * @return
	 */
	public static Object convertStringToData(boolean convertDates,
			String valueString, Class dataType) {
		// Convert string value to the appropriate data type
		// Each utility init method returns null if valueString is null or empty

		Object value = null;

		if (dataType == Long.class) {
			value = Util.initLong(valueString, new Long(0));
		} else if (dataType == int.class) {
			value = Util.initInt(valueString, new Integer(0));
		} else if (dataType == EDITDate.class) {
			if (convertDates) {
				value = DateTimeUtil.getEDITDateFromMMDDYYYY(valueString); // assumes
																			// date
																			// in
																			// element
																			// is
																			// in
																			// mm/dd/yyyy
																			// format
			} else {
				value = DateTimeUtil.initEDITDate(valueString); // assumes date
																// in element is
																// in standard
																// EDITDate
																// format
			}
		} else if (dataType == EDITDateTime.class) {
			if (convertDates) {
				value = DateTimeUtil.getEDITDateTimeFromMMDDYYYY(valueString); // assumes
																				// date
																				// in
																				// element
																				// is
																				// in
																				// mm/dd/yyyy
																				// format
			} else {
				value = DateTimeUtil.initEDITDateTime(valueString); // assumes
																	// datetime
																	// in
																	// element
																	// is in
																	// standard
																	// EDITDateTime
																	// format
			}
		} else if (dataType == EDITBigDecimal.class) {
			value = Util.initEDITBigDecimal(valueString, null);
		} else {
			value = valueString;
		}

		return value;
	}

	/**
	 * This is a helper method to (@see).
	 * <p/>
	 * The specified valueString is considered valid if: 1. The valueString !=
	 * null 2. The valueString != "" 3. The getterName does not end with "VO" 4.
	 * The getterName does not end with "FK".
	 * 
	 * @param valueString
	 * 
	 * @see #mapToHibernateEntity(Class, Element, String, boolean)
	 */
	private static boolean validValueString(String valueString,
			String getterName) {
		boolean validValueString = false;

		if (valueString != null) {
			if (!valueString.equals("")) {
				if (!getterName.endsWith("VO")) {
					if (!getterName.endsWith("FK")) {
						validValueString = true;
					}
				}
			}
		}

		return validValueString;
	}

	/**
	 * The set of Columns for the specified Hibernate entity.
	 * 
	 * @param pojoClass
	 * @param targetDB
	 * 
	 * @return the associated Columns
	 */
	public static Column[] getColumns(Class pojoClass, String targetDB) {
		String fullyQualifiedClassName = Util
				.getFullyQualifiedClassName(pojoClass);

		Configuration c = getConfiguration(targetDB);

		PersistentClass pc = c.getClassMapping(fullyQualifiedClassName);

		if (pc == null) {
			String message = "No Mapping Can Be Found For ["
					+ pojoClass.getName()
					+ "] - The Class Must Be Added To The Set Of Classes Recognized By DBSessionFactory";

			Logging.getLogger(Logging.GENERAL_EXCEPTION).fatal(
					new LogEvent(message));

			Log.logGeneralExceptionToDatabase(null, new Exception(message));
		}

		Table pojoTable = pc.getIdentityTable();

		List columns = new ArrayList();

		Iterator columnIterator = pojoTable.getColumnIterator();

		while (columnIterator.hasNext()) {
			columns.add(columnIterator.next());
		}

		return (Column[]) columns.toArray(new Column[columns.size()]);
	}

	/**
	 * Creates hibernate entity for DOM4J element passed in.
	 * 
	 * @param hibernateClass
	 *            - the entity class for which hibernate object need to be
	 *            created.
	 * @param element
	 *            - the element to be represented as hibernate entity.
	 * @param targetDB
	 *            - the database name where the corresponding hibernate entity
	 *            belongs to.
	 * @return hibernate object that is not assoicated to any hibernate session.
	 */
	public static HibernateEntity mapToHibernateEntity(Class hibernateClass,
			Element element, String targetDB) {
		HibernateEntity hibernateEntity = null;

		try {
			hibernateEntity = (HibernateEntity) hibernateClass.newInstance();

			ClassMetadata classMetadata = SessionHelper.getClassMetadata(
					hibernateClass, targetDB);

			String[] propertyNames = classMetadata.getPropertyNames();

			for (String propertyName : propertyNames) {
				String getterMethodName = "get" + propertyName;

				Method getterMethod = hibernateClass.getMethod(
						getterMethodName, null);

				Class returnTypeClass = getterMethod.getReturnType();

				if (isPropertySetter(returnTypeClass)) {
					// Get the value from XML element.
					List childElements = DOMUtil.getChildren(propertyName,
							element);

					Object value = null;

					if (!childElements.isEmpty()) {
						Node node = (Node) childElements.get(0);

						value = Util.convertStringToObject(node.getText(),
								returnTypeClass, false);
					}

					String setterMethodName = "set" + propertyName;

					Method setterMethod = hibernateClass.getMethod(
							setterMethodName, new Class[] { returnTypeClass });

					setterMethod
							.invoke(hibernateEntity, new Object[] { value });
				}
			}
		} catch (InstantiationException e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		}

		return hibernateEntity;
	}
	
	/**
	 * Creates hibernate entity for DOM4J element passed in.
	 * 
	 * @param hibernateClass
	 *            - the entity class for which hibernate object need to be
	 *            created.
	 * @param element
	 *            - the element to be represented as hibernate entity.
	 * @param targetDB
	 *            - the database name where the corresponding hibernate entity
	 *            belongs to.
	 * @return hibernate object that is not assoicated to any hibernate session.
	 */
	public static HibernateEntity mapToHibernateEntity_WithPK(Class hibernateClass,
			Element element, String targetDB) {
		HibernateEntity hibernateEntity = null;

		try {
			hibernateEntity = (HibernateEntity) hibernateClass.newInstance();

			ClassMetadata classMetadata = SessionHelper.getClassMetadata(
					hibernateClass, targetDB);
			
			String identifierPropertyName = classMetadata.getIdentifierPropertyName();
			String pkGetterMethodName = "get" + identifierPropertyName;

			Method pkGetterMethod = hibernateClass.getMethod(
					pkGetterMethodName, null);

			Class pkReturnTypeClass = pkGetterMethod.getReturnType();

			if (isPropertySetter(pkReturnTypeClass)) {
				// Get the value from XML element.
				List pkChildElements = DOMUtil.getChildren(identifierPropertyName,
						element);

				Object value = null;

				if (!pkChildElements.isEmpty()) {
					Node node = (Node) pkChildElements.get(0);

					value = Util.convertStringToObject(node.getText(),
							pkReturnTypeClass, false);
				}

				String setterMethodName = "set" + identifierPropertyName;

				Method setterMethod = hibernateClass.getMethod(
						setterMethodName, new Class[] { pkReturnTypeClass });

				setterMethod
						.invoke(hibernateEntity, new Object[] { value });
			}
			
			String[] propertyNames = classMetadata.getPropertyNames();

			for (String propertyName : propertyNames) {
				String getterMethodName = "get" + propertyName;

				Method getterMethod = hibernateClass.getMethod(
						getterMethodName, null);

				Class returnTypeClass = getterMethod.getReturnType();

				if (isPropertySetter(returnTypeClass)) {
					// Get the value from XML element.
					List childElements = DOMUtil.getChildren(propertyName,
							element);

					Object value = null;

					if (!childElements.isEmpty()) {
						Node node = (Node) childElements.get(0);

						value = Util.convertStringToObject(node.getText(),
								returnTypeClass, false);
					}

					String setterMethodName = "set" + propertyName;

					Method setterMethod = hibernateClass.getMethod(
							setterMethodName, new Class[] { returnTypeClass });

					setterMethod
							.invoke(hibernateEntity, new Object[] { value });
				}
			}
		} catch (InstantiationException e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		}

		return hibernateEntity;
	}

	/**
	 * Returns true if class is simple property setter.
	 * 
	 * @param classType
	 * @return
	 */
	private static boolean isPropertySetter(Class classType) {
		boolean isPropertySetter = false;

		if (classType == String.class || classType == long.class
				|| classType == Long.class || classType == int.class
				|| classType == Integer.class
				|| classType == EDITBigDecimal.class
				|| classType == EDITDate.class
				|| classType == EDITDateTime.class) {
			isPropertySetter = true;
		}

		return isPropertySetter;
	}

	/**
	 * Returns a shallow copy of the specified pojo. This does not include
	 * relationships to other HibernateEntities other than the PK/FK values (if
	 * specified).
	 * 
	 * @param sourceHibernateEntity
	 *            the entity to merge from
	 * @param destinationHibernateEntity
	 *            the entity to merge to
	 * @param targetDB
	 *            the targetDB
	 * @param includePKsFKs
	 *            if true, the PKs and FKs are also mapped
	 */
	public static void shallowMerge(HibernateEntity sourceHibernateEntity,
			HibernateEntity destinationHibernateEntity, String targetDB,
			boolean includePKsFKs) {
		try {
			Column[] columns = getColumns(sourceHibernateEntity.getClass(),
					targetDB);

			for (int i = 0; i < columns.length; i++) {
				Column column = columns[i];

				String columnName = column.getName();

				if ((!columnName.endsWith("FK") && !columnName.endsWith("PK"))
						|| includePKsFKs) {
					String getterName = "get" + column.getName();

					String setterName = "set" + column.getName();

					Method getterMethod = sourceHibernateEntity.getClass()
							.getMethod(getterName, null);

					Object getterValue = getterMethod.invoke(
							sourceHibernateEntity, null);

					Class dataType = getterMethod.getReturnType();

					Method setterMethod = destinationHibernateEntity.getClass()
							.getMethod(setterName, new Class[] { dataType });

					setterMethod.invoke(destinationHibernateEntity,
							new Object[] { getterValue });
				}
			}
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns a shallow copy of the specified pojo. Only the DB related columns
	 * are copied. All PKs and FKs are ignored. This method uses the
	 * SessionHelper.newInstance(..) method so the returned entity is already
	 * participating in the Hibernate Session.
	 * 
	 * @param pojo
	 * 
	 * @return
	 */
	public static Object shallowCopy(Object pojo, String targetDB) {
		Object copiedPojo;

		if (pojo instanceof HibernateProxy) {
			HibernateProxy proxy = (HibernateProxy) pojo;

			pojo = (HibernateEntity) proxy.writeReplace();
		}

		try {
			Class pojoClass = Class.forName(Util
					.getFullyQualifiedClassName(pojo.getClass()));

			Column[] columns = getColumns(pojo.getClass(), targetDB);

			copiedPojo = newInstance(pojoClass, targetDB);

			for (int i = 0; i < columns.length; i++) {
				Column column = columns[i];

				String columnName = column.getName();

				if (!columnName.endsWith("FK") && !columnName.endsWith("PK")) {
					String getterName = "get" + column.getName();

					String setterName = "set" + column.getName();

					Method getterMethod = pojo.getClass().getMethod(getterName,
							null);

					Object getterValue = getterMethod.invoke(pojo, null);

					if (getterValue != null) {
						Class dataType = getterMethod.getReturnType();

						Method setterMethod = pojo.getClass().getMethod(
								setterName, new Class[] { dataType });

						setterMethod.invoke(copiedPojo,
								new Object[] { getterValue });
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		}

		return copiedPojo;
	}

	/**
	 * Returns a shallow copy of the specified pojo. Only the DB related columns
	 * are copied. All PKs and FKs are ignored. This method uses the
	 * SessionHelper.newInstance(..) method so the returned entity is already
	 * participating in the Hibernate Session.
	 * 
	 * @param pojo
	 * 
	 * @return
	 */
	public static Object shallowCopy(Object pojo, String targetDB,
			boolean createNewObject) {
		Object copiedPojo;

		try {
			String className = Util.getFullyQualifiedClassName(pojo.getClass());
			Class pojoClass = Class.forName(Util
					.getFullyQualifiedClassName(pojo.getClass()));

			Column[] columns = getColumns(pojo.getClass(), targetDB);

			if (createNewObject) {
				copiedPojo = newInstance(pojoClass, targetDB);
			} else {
				copiedPojo = pojoClass.newInstance();
			}

			for (int i = 0; i < columns.length; i++) {
				Column column = columns[i];

				String columnName = column.getName();

				if (!columnName.endsWith("FK") && !columnName.endsWith("PK")) {
					String getterName = "get" + column.getName();

					String setterName = "set" + column.getName();

					Method getterMethod = pojo.getClass().getMethod(getterName,
							null);

					Object getterValue = getterMethod.invoke(pojo, null);

					if (getterValue != null) {
						Class dataType = getterMethod.getReturnType();

						Method setterMethod = pojo.getClass().getMethod(
								setterName, new Class[] { dataType });

						setterMethod.invoke(copiedPojo,
								new Object[] { getterValue });
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		}

		return copiedPojo;
	}

	public static void prettyPrint(Element element, Writer writer) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter xmlWriter = null;

		try {
			xmlWriter = new XMLWriter(writer, format);
			xmlWriter.write(element);
			xmlWriter.flush();
		} catch (IOException e) {
			Logging.getLogger(Logging.GENERAL_EXCEPTION).fatal(new LogEvent(e));

			Log.logGeneralExceptionToDatabase(null, e);

			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		} finally {
			try {
				xmlWriter.close();
			} catch (Exception e) {
				System.out.println(e);

				e.printStackTrace();

				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * VOs with or without a PK are mapped to a corresponding and appropriate
	 * Hibernate entity. VOs with a PK are "looked-up", with the values of the
	 * VO mapped to the Hibernate entity. The generated Hibernate Entity is [IS]
	 * added to the Hibernate Session via saveUpdate, etc.
	 * 
	 * @param voObject
	 * 
	 * @return
	 */
	public static Object map(VOObject voObject, String targetDB) {
		Object hibernateObject;

		try {
			Class hibernateClass = getHibernateClass(voObject.getClass(),
					targetDB);

			String hibernateClassName = Util.getClassName(Util
					.getFullyQualifiedClassName(hibernateClass));

			Long pk = (Long) voObject.getClass()
					.getMethod("get" + hibernateClassName + "PK", null)
					.invoke(voObject, null);

			if (pk.longValue() > 0) {
				hibernateObject = SessionHelper.get(hibernateClass, pk,
						targetDB);
			} else {
				hibernateObject = SessionHelper.newInstance(hibernateClass,
						targetDB);

				// Don't let the entity get into the Hibernate Session.
				// hibernateObject = hibernateClass.newInstance();
			}

			Column[] columns = getColumns(hibernateObject.getClass(), targetDB);

			for (int i = 0; i < columns.length; i++) {
				Column column = columns[i];

				String columnName = column.getName();

				Method voGetter = voObject.getClass().getMethod(
						"get" + columnName, null);

				Object voValue = voGetter.invoke(voObject, null);

				// A manual mapping of certain VO types to the Hibernate
				// equivalent.
				// Hibernate does [not like] messing with the PKs, so we avoid
				// all
				// getters that end with PK. This is OK because if the VO had a
				// PK,
				// we just found it by PK. If the VO did not have a PK, the
				// Hibernate
				// entity remains a null PK.
				if (!columnName.endsWith("PK")) {
					Class hibernateGetterType = null;

					Method hibernateGetter = hibernateClass.getMethod("get"
							+ columnName, null);

					hibernateGetterType = hibernateGetter.getReturnType();

					// Allow null fields to be set, fields changes to null were
					// not getting reset
					if (voValue != null) {
						if (hibernateGetterType == EDITDate.class) {
							voValue = new EDITDate(voValue.toString());
						} else if (hibernateGetterType == EDITDateTime.class) {
							voValue = new EDITDateTime(voValue.toString());
						} else if (hibernateGetterType == EDITBigDecimal.class) {
							voValue = new EDITBigDecimal(voValue.toString());
						} else if (hibernateGetterType == Integer.class) {
							hibernateGetterType = int.class;
						}
					}

					Method hibernateSetter = hibernateObject.getClass()
							.getMethod("set" + columnName,
									new Class[] { hibernateGetterType });

					hibernateSetter.invoke(hibernateObject,
							new Object[] { voValue });
				}
			}
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		}

		return hibernateObject;
	}

	/**
	 * Maps a HibernateEntity to its corresponding VO. This method should be
	 * used instead of wrapping a HibernateEntity around a VO. It does not map
	 * children.
	 * 
	 * @param hibernateEntity
	 * @param voObjectClass
	 * @param targetDB
	 * 
	 * @return
	 */
	public static VOObject map(HibernateEntity hibernateEntity, String targetDB) {
		VOObject voObject = null;

		try {
			Class voObjectClass = getVOClass(hibernateEntity.getClass());

			voObject = (VOObject) voObjectClass.newInstance();

			Column[] columns = getColumns(hibernateEntity.getClass(), targetDB);

			for (int i = 0; i < columns.length; i++) {
				Column column = columns[i];

				String columnName = column.getName();

				Method hibernateGetter = hibernateEntity.getClass().getMethod(
						"get" + columnName, null);

				Object hibernateValue = hibernateGetter.invoke(hibernateEntity,
						null);

				if (hibernateValue != null) {
					Method voGetter = voObjectClass.getMethod("get"
							+ columnName, null);
					Class voGetterType = voGetter.getReturnType();

					Object voValue = null;

					Class hibernateGetterType = hibernateGetter.getReturnType();

					if (hibernateGetterType == EDITDate.class) {
						// VO type is a String
						voValue = hibernateValue.toString();
					} else if (hibernateGetterType == EDITDateTime.class) {
						// VO type is a String
						voValue = hibernateValue.toString();
					} else if (hibernateGetterType == EDITBigDecimal.class) {
						// VO type is a BigDecimal
						voValue = new BigDecimal(hibernateValue.toString());
					} else if (hibernateGetterType == Long.class) {
						voValue = hibernateValue;
					} else {
						// Strings and ints
						voValue = hibernateValue;
					}

					Method voSetter = voObjectClass.getMethod("set"
							+ columnName, new Class[] { voGetterType });

					voSetter.invoke(voObject, new Object[] { voValue });
				}
			}
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		}

		return voObject;

	}

	// /**
	// * A deep mapping of a VO composition to its Hibernate equivalent.
	// * @param voObject
	// * @param targetDB
	// * @return the Hibernate equivalent of the VO composition.
	// */
	// public static Object mapComposition(VOObject voObject, String targetDB)
	// {
	// RecursionContext recursionContext = new RecursionContext();
	//
	// recursionContext.addToMemory("targetDB", targetDB);
	//
	// // Recurse the composite VO model, and map it to its Hibernate
	// equivalent.
	// CRUD.recurseVOObjectModel(voObject, null, CRUD.RECURSE_TOP_DOWN, new
	// RecursionListener()
	// {
	// public void currentNode(Object currentNode, Object parentNode,
	// RecursionContext recursionContext)
	// {
	// String targetDB = (String) recursionContext.getFromMemory("targetDB");
	//
	// VOObject currentVOObject = (VOObject) currentNode;
	//
	// Object currentHibernateEntity = map(currentVOObject, targetDB);
	//
	// Object parentHibernateEntity = null;
	//
	// if (parentNode != null)
	// {
	// parentHibernateEntity =
	// recursionContext.getFromMemory(parentNode.getClass().getName());
	// }
	// else
	// {
	// recursionContext.addToMemory("rootHibernateEntity",
	// currentHibernateEntity);
	// }
	//
	// // A new Hibernate entity must be added to its parent Hibernate entity.
	// // Otherwise, Hibernate will already be managing the association(s).
	// String className =
	// Util.getClassName(currentHibernateEntity.getClass().getName());
	//
	// String getterPKName = "get" + className + "PK";
	//
	// Long getterPKValue;
	//
	// try
	// {
	// getterPKValue = (Long) currentVOObject.getClass().getMethod(getterPKName,
	// null).invoke(currentVOObject, null);
	// }
	// catch (Exception e)
	// {
	// System.out.println(e);
	//
	// e.printStackTrace();
	//
	// throw new RuntimeException(e);
	// }
	//
	// if (getterPKValue.longValue() == 0) // It is a new entity
	// {
	// if (parentHibernateEntity != null)
	// {
	// String currentClassName =
	// Util.getClassName(Util.getFullyQualifiedClassName(currentHibernateEntity.getClass()));
	//
	// String parentAdderName = "add" + currentClassName;
	//
	// try
	// {
	// Method parentAdderMethod =
	// parentHibernateEntity.getClass().getMethod(parentAdderName, new Class[] {
	// currentHibernateEntity.getClass() });
	//
	// parentAdderMethod.invoke(parentHibernateEntity, new Object[] {
	// currentHibernateEntity });
	// }
	// catch (Exception e)
	// {
	// System.out.println(e);
	//
	// e.printStackTrace();
	//
	// throw new RuntimeException(e);
	// }
	// }
	// }
	//
	// recursionContext.addToMemory(currentNode.getClass().getName(),
	// currentHibernateEntity);
	// }
	// }, recursionContext, null);
	//
	// return recursionContext.getFromMemory("rootHibernateEntity");
	// }

	/**
	 * The associated Hibernate class for the specified VOObject.
	 * 
	 * @param voObject
	 * 
	 * @return the corresponding Hibernate class
	 */
	public static Class getHibernateClass(Class voClass, String targetDB) {
		Class hibernateClass = null;

		if (voToHibernate.containsKey(voClass)) {
			hibernateClass = (Class) voToHibernate.get(voClass);
		} else {
			String voClassName = Util.getClassName(voClass.getName());

			String dotHibernateClassName = "."
					+ voClassName.substring(0, voClassName.length() - 2);

			Configuration c = DBSessionFactory.getInstance().getConfiguration(
					targetDB);

			Iterator i = c.getClassMappings();

			try {
				while (i.hasNext()) {
					PersistentClass pc = (PersistentClass) i.next();

					if (pc.getClassName().endsWith(dotHibernateClassName)) {
						String fullyQualifiedHibernateClassName = pc
								.getClassName();

						hibernateClass = Class
								.forName(fullyQualifiedHibernateClassName);

						voToHibernate.put(voClass, hibernateClass);

						break;
					}
				}
			} catch (ClassNotFoundException e) {
				System.out.println(e);

				e.printStackTrace();

				throw new RuntimeException(
						"Unable To Find Associated Hibernate Entity For VOObject ["
								+ voClass.getName() + "] " + e.getMessage());
			}
		}

		return hibernateClass;
	}

	/**
	 * Finds the Hibernate Class that corresponds the the specified tableName.
	 * Any results are cached for further use.
	 * 
	 * @param tableName
	 * @param targetDB
	 * 
	 * @return
	 * 
	 * @see #tableToHibernate
	 */
	public static Class getHibernateClass(String tableName, String targetDB) {
		Class hibernateClass = null;

		if (tableToHibernate.containsKey(tableName)) {
			hibernateClass = (Class) tableToHibernate.get(tableName);
		} else {
			Configuration c = DBSessionFactory.getInstance().getConfiguration(
					targetDB);

			Iterator i = c.getClassMappings();

			try {
				while (i.hasNext()) {
					PersistentClass pc = (PersistentClass) i.next();

					String pcClassName = Util.getClassName(pc.getClassName());

					if (pcClassName.equals(tableName)) {
						String fullyQualifiedHibernateClassName = pc
								.getClassName();

						hibernateClass = Class
								.forName(fullyQualifiedHibernateClassName);

						tableToHibernate.put(tableName, hibernateClass);

						break;
					}
				}
			} catch (ClassNotFoundException e) {
				System.out.println(e);

				e.printStackTrace();

				throw new RuntimeException(
						"Unable To Find Associated Hibernate Entity For Table ["
								+ tableName + "] " + e.getMessage());
			}
		}

		return hibernateClass;
	}

	/**
	 * Checks to see if the specified entityName is a mapped entity (i.e. is
	 * mapped in the *.hbm.xml files or its equivalent.
	 * 
	 * @param entityName
	 *            the non-qualified name of the entity - almost certainly the
	 *            name of a table since our Hibernate entities and table names
	 *            are synonimous and unique
	 * @param targetDB
	 * 
	 * @return
	 * 
	 * @see #tableToHibernate
	 */
	public static boolean isMappedEntity(String entityName, String targetDB) {
		boolean isMappedEntity = false;

		if (tableToHibernate.containsKey(entityName)) {
			isMappedEntity = true;
		} else {
			Configuration c = DBSessionFactory.getInstance().getConfiguration(
					targetDB);

			Iterator i = c.getClassMappings();

			while (i.hasNext()) {
				PersistentClass pc = (PersistentClass) i.next();

				String pcClassName = Util.getClassName(pc.getClassName());

				if (pcClassName.equals(entityName)) {
					isMappedEntity = true;

					break;
				}
			}
		}

		return isMappedEntity;
	}

	/**
	 * Returns the associated VO class for the specified HibernateEntity.
	 * 
	 * @param hibernateClass
	 * @param targetDB
	 * 
	 * @return the corresponding Hibernate class
	 */
	public static Class getVOClass(Class hibernateClass) {
		Class voClass = null;

		if (hibernateToVO.containsKey(hibernateClass)) {
			voClass = (Class) hibernateToVO.get(hibernateClass);
		} else {
			String hibernateClassName = Util.getClassName(Util
					.getFullyQualifiedClassName(hibernateClass));

			String voClassName = hibernateClassName + "VO";

			String fullyQualifiedVOClassName = "edit.common.vo." + voClassName;

			try {
				voClass = Class.forName(fullyQualifiedVOClassName);

				hibernateToVO.put(hibernateClass, voClass);
			} catch (ClassNotFoundException e) {
				System.out.println(e);

				e.printStackTrace();

				throw new RuntimeException(
						"Unable To Find Associated VOObject for Hibernate Entity ["
								+ hibernateClass.getName() + "] "
								+ e.getMessage());
			}
		}

		return voClass;
	}

	/**
	 * Convenience method to convert the specified pojo to an Element, and then
	 * add it as a child to the specified Element.
	 * 
	 * @param pojo
	 * @param element
	 * 
	 * @return the newly converted pojo to Element.
	 */
	public static Element addPojoToElement(HibernateEntity pojo, Element element) {
		Element pojoElement = mapToElement(pojo, SessionHelper.EDITSOLUTIONS,
				false, false);

		element.add(pojoElement);

		return pojoElement;
	}

	/**
	 * Instantiates a HibernateEntity of the specified Class, and then
	 * associates it with the current Hibernate Session.
	 * 
	 * @param hibernateEntityClass
	 * 
	 * @return a HibernateEntity participating in the current Hibernate Session.
	 */
	public static HibernateEntity newInstance(Class hibernateEntityClass,
			String targetDB) {
		Session session = getSession(targetDB);

		return newInstance(hibernateEntityClass, session);
	}

	/**
	 * Instantiates a HibernateEntity of the specified Class, and then
	 * associates it with the current Hibernate Session.
	 * 
	 * @param hibernateEntityClass
	 * 
	 * @return a HibernateEntity participating in the current Hibernate Session.
	 */
	public static HibernateEntity newInstance(Class hibernateEntityClass,
			Session session) {
		HibernateEntity hibernateEntity;

		try {
			hibernateEntity = (HibernateEntity) hibernateEntityClass
					.newInstance();

			// hibernateEntity.setNewlyInstantiated(true);

			session.saveOrUpdate(hibernateEntity);

			hibernateEntity.onCreate();
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.

			throw new RuntimeException(e);
		}

		return hibernateEntity;
	}

	/**
	 * Uses reflection and creates a method as "get" + "ClassName + "PK".
	 * 
	 * @param hibernateClass
	 * 
	 * @return the PK method of the specified Hibernate Entity.
	 */
	public static Method getPKMethod(HibernateEntity hibernateEntity) {
		Method pkMethod;

		Class hibernateEntityClass = hibernateEntity.getClass();

		String pkMethodName = "get"
				+ Util.getClassName(Util
						.getFullyQualifiedClassName(hibernateEntityClass))
				+ "PK";

		try {
			pkMethod = hibernateEntityClass.getMethod(pkMethodName, null);
		} catch (NoSuchMethodException e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		}

		return pkMethod;
	}

	/**
	 * Uses reflection to determine the "getFooPK" and thus, the pk value.
	 * 
	 * @param hibernateEntity
	 * 
	 * @return the pk value of the specified HibernateEntity.
	 */
	public static Long getPKValue(HibernateEntity hibernateEntity) {
		Long pkValue;

		Method pkMethod = getPKMethod(hibernateEntity);

		try {
			pkValue = (Long) pkMethod.invoke(hibernateEntity, null);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			throw new RuntimeException(e);
		}

		return pkValue;
	}

	/**
	 * 1) Disconnects the Hibernate Session from the current thread. 2) Clears
	 * the Hibernate Session. 3) Stores the Hibernate Session is the specified
	 * HttpSession.
	 * 
	 * @param httpSession
	 */
	public static void suspendHibernateSessions(HttpSession httpSession) {
		try {
			for (int i = 0; i < TARGET_DATABASES.length; i++) {
				String sessionName = getSessionName(TARGET_DATABASES[i]);

				Session hibernateSession = disconnectSession(TARGET_DATABASES[i]);

				if ((hibernateSession != null) && hibernateSession.isOpen()) {
					hibernateSession.clear();

					httpSession.setAttribute(sessionName, hibernateSession);
				}
			}
		} catch (IllegalStateException e) {
			// HttpSession is invalid, simply ignore.
		}
	}

	/**
	 * Reconnects the Hibernate Session to the current thread.
	 * 
	 * @param httpSession
	 */
	public static void resumeHibernateSessions(HttpSession httpSession) {
		for (int i = 0; i < TARGET_DATABASES.length; i++) {
			String sessionName = getSessionName(TARGET_DATABASES[i]);

			Session hibernateSession = (Session) httpSession
					.getAttribute(sessionName);

			if ((hibernateSession != null) && (hibernateSession.isConnected())) {
				// A HibernateSession should always be disconnected at this
				// point. A thread must have "slipped" through.
				disconnectSession(TARGET_DATABASES[i]);
			}

			if ((hibernateSession != null) && hibernateSession.isOpen()) {
				reconnect(hibernateSession, TARGET_DATABASES[i]);
			}
		}
	}

	/**
	 * Convenience method to return a valid EDITDate or null for the specified
	 * editDateStr.
	 * 
	 * @param editDateStr
	 * 
	 * @return
	 */
	public static EDITDate getEDITDate(String editDateStr) {
		EDITDate editDate = null;

		if (editDateStr != null) {
			editDate = new EDITDate(editDateStr);
		}

		return editDate;
	}

	/**
	 * Convenience method to return a valid String date or null for the
	 * specified editDate.
	 * 
	 * @param editDate
	 * 
	 * @return
	 */
	public static String getEDITDate(EDITDate editDate) {
		String editDateStr = null;

		if (editDate != null) {
			editDateStr = editDate.getFormattedDate();
		}

		return editDateStr;
	}

	/**
	 * Convenience method to return a valid EDITDateTime or null for the
	 * specified editDateTimeStr.
	 * 
	 * @param editDateTimeStr
	 * 
	 * @return
	 */
	public static EDITDateTime getEDITDateTime(String editDateTimeStr) {
		EDITDateTime editDateTime = null;

		if (editDateTimeStr != null) {
			editDateTime = new EDITDateTime(editDateTimeStr);
		}

		return editDateTime;
	}

	/**
	 * Convenience method to return a valid String dateTime or null for the
	 * specified editDateTime.
	 * 
	 * @param editDateTime
	 * 
	 * @return
	 */
	public static String getEDITDateTime(EDITDateTime editDateTime) {
		String editDateTimeStr = null;

		if (editDateTime != null) {
			editDateTimeStr = editDateTime.getFormattedDateTime();
		}

		return editDateTimeStr;
	}

	/**
	 * Convenience method to return a valid EDITBigDecimal or null for the given
	 * bigDecimal.
	 * 
	 * @param bigDecimal
	 * 
	 * @return
	 */
	public static EDITBigDecimal getEDITBigDecimal(BigDecimal bigDecimal) {
		EDITBigDecimal editBigDecimal = new EDITBigDecimal(bigDecimal);

		return editBigDecimal;
	}

	/**
	 * Convenience method to return a BigDecimal value or null for the given
	 * editBigDecimal.
	 * 
	 * @param editBigDecimal
	 * 
	 * @return
	 */
	public static BigDecimal getEDITBigDecimal(EDITBigDecimal editBigDecimal) {
		BigDecimal bigDecimal = null;

		if (editBigDecimal != null) {
			bigDecimal = editBigDecimal.getBigDecimal();
		}

		return bigDecimal;
	}

	/**
	 * Verifies whether the object is associated with hibernate session.
	 * 
	 * @param entityPK
	 * @param targetDB
	 * 
	 * @return
	 */
	public static boolean contains(HibernateEntity entity, String targetDB) {
		return getSession(targetDB).contains(entity);
	}

	/**
	 * Evicts the object from the hibernate targetDB session.
	 * 
	 * @param obj
	 * @param targetDB
	 */
	public static final void evict(Object obj, String targetDB) {
		try {
			getSession(targetDB).evict(obj);
		} catch (HibernateException e) {
			System.out.println(e);

			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
	}

	/**
	 * Returns the unique HibernateEntities (only) of the specified results.
	 * 
	 * @param results
	 *            a List of HibernateEntities
	 * 
	 * @return a unique List of HibernateEntities
	 */
	public static List makeUnique(List results) {
		return new ArrayList(new LinkedHashSet(results));
	}

	/**
	 * Returns the unique HibernateEntities (only) of the specified results.
	 * 
	 * @param results
	 *            a List of HibernateEntities
	 * 
	 * @return a unique List of HibernateEntities
	 */
	public static List<HibernateEntity> makeUnique(HibernateEntity[] results) {
		List<HibernateEntity> uniqueResults = new ArrayList<HibernateEntity>();

		for (HibernateEntity hibernateEntity : results) {
			if (!uniqueResults.contains(hibernateEntity)) {
				uniqueResults.add(hibernateEntity);
			}
		}

		return uniqueResults;
	}

	/**
	 * Returns all of the Hibernate-mapped classes as ClassMetadata.
	 * 
	 * @param targetDB
	 * 
	 * @return
	 */
	public static ClassMetadata[] getClassMetadata(String targetDB) {
		List results = new ArrayList();

		Session session = getSession(targetDB);

		Map classMetadatas = session.getSessionFactory().getAllClassMetadata();

		Iterator keys = classMetadatas.keySet().iterator();

		while (keys.hasNext()) {
			String key = (String) keys.next();

			AbstractEntityPersister entityPersister = (AbstractEntityPersister) classMetadatas
					.get(key);

			ClassMetadata classMetadata = entityPersister.getClassMetadata();

			results.add(classMetadata);
		}

		return (ClassMetadata[]) results.toArray(new ClassMetadata[results
				.size()]);
	}

	/**
	 * Seeks the specified HibernateEntity metadata for the specified targetDB.
	 * 
	 * @param hibernateEntityClass
	 * @param targetDB
	 * 
	 * @return the HibernateEntity's ClassMetadata
	 */
	public static ClassMetadata getClassMetadata(Class hibernateEntityClass,
			String targetDB) {
		Session session = getSession(targetDB);

		return getClassMetadata(hibernateEntityClass, session);
	}

	/**
	 * Seeks the specified HibernateEntity metadata for the specified targetDB.
	 * 
	 * @param hibernateEntityClass
	 * @param targetDB
	 * 
	 * @return the HibernateEntity's ClassMetadata
	 */
	public static ClassMetadata getClassMetadata(Class hibernateEntityClass,
			Session session) {
		ClassMetadata classMetadata = null;

		classMetadata = session.getSessionFactory().getClassMetadata(
				hibernateEntityClass);

		return classMetadata;
	}

	/**
	 * Saves a transient object and assigns an identifier.
	 * 
	 * @param object
	 * @param targetDB
	 */
	public static void save(Object object, String targetDB) {
		try {
			getSession(targetDB).save(object);
		} catch (HibernateException e) {
			System.out.println(e);

			e.printStackTrace();

			throw e;
		}
	}

	/**
	 * A convenience method to the DBSession's getSession() method.
	 * <p/>
	 * It can happend that a developer does [not] want to participate in the
	 * underlying Hibernate Session when querying. A good example of this is the
	 * building of a Document that will be read-only. Using a separate Session
	 * prevents the underlying Hibernate Session (via ThreadLocal) from becoming
	 * too large.
	 * <p/>
	 * Don't forget to close the Session to release the underlying JDBC
	 * Connection.
	 * 
	 * @param targetDB
	 * 
	 * @return
	 * 
	 * @see DBSessionFactory#getStatelessSession(String)
	 */
	public static Session getSeparateSession(String targetDB) {
		Session session = dbSessionFactory.getSession(targetDB);

		return session;
	}

	public static String getFullyQualifiedClassName(String className,
			String targetDB) {
		String fullyQualifiedClassName = null;

		Configuration configuration = getConfiguration(targetDB);

		Iterator iterator = configuration.getClassMappings();

		while (iterator.hasNext()) {
			RootClass o = (RootClass) iterator.next();

			String name = o.getClassName(); // fully qualified
			String nodeName = o.getNodeName(); // short name

			if (nodeName.equalsIgnoreCase(className)) {
				fullyQualifiedClassName = name;

				break;
			}
		}

		return fullyQualifiedClassName;
	}

	/**
	 * True if the specified HibernateEntity has been saved to the DB at some
	 * prior time. It is not sufficient to check for the value of the PK (only).
	 * It is possible for the PK to be non-zero, yet [never] have been persisted
	 * to the DB. This happens because Hibernate is willing to assign a PK
	 * before it is actually committed to the DB.
	 * <p/>
	 * This method will actually strike the DB for any PK != null, so use this
	 * method with great care as it is a performance hit.
	 * 
	 * @param hibernateEntity
	 * 
	 * @return
	 */
	public static boolean isPersisted(HibernateEntity hibernateEntity) {
		boolean isPersisted = false;

		Long pkValue = getPKValue(hibernateEntity);

		if (pkValue != null) {
			if (pkValue.longValue() > 0) {
				Session session = null;

				try {
					session = getSeparateSession(hibernateEntity.getDatabase());

					Object inDBHibernateEntity = session.get(
							hibernateEntity.getClass(), pkValue);

					if (inDBHibernateEntity != null) {
						isPersisted = true;
					}
				} catch (Exception e) {
					System.out.println(e);

					e.printStackTrace();
				} finally {
					if (session != null)
						session.close();
				}
			}
		}

		return isPersisted;
	}

	/**
	 * The set of fields (name only) contained in the specified HibernateEntity
	 * class. The fields quate to the fields found on the associated db table
	 * and include FK and PK fields.
	 * 
	 * @return
	 */
	public static String[] getFieldNames(Class hibernateEntityClass,
			String targetDB) {
		System.out.println("TODO: This method is buggy .. don't use.");

		List<String> fieldNames = new ArrayList<String>();

		Configuration configuration = getConfiguration(targetDB);

		PersistentClass persistentClass = configuration
				.getClassMapping(hibernateEntityClass.getName());

		SessionFactory sessionFactory = DBSessionFactory.getInstance()
				.getSessionFactory(targetDB);

		EntityMetamodel metamodel = new EntityMetamodel(persistentClass,
				(SessionFactoryImplementor) sessionFactory);

		StandardProperty[] standardProperties = metamodel.getProperties();

		for (StandardProperty standardProperty : standardProperties) {
			Type standardPropertyType = standardProperty.getType();

			if (!(standardPropertyType instanceof CollectionType)
					&& !(standardPropertyType instanceof EntityType)) {
				fieldNames.add(standardProperty.getName());
			}
		}

		IdentifierProperty identifierProperty = metamodel
				.getIdentifierProperty();

		String identifierName = identifierProperty.getName();

		fieldNames.add(identifierName);

		return fieldNames.toArray(new String[fieldNames.size()]);
	}

	/**
	 * True if the specified fieldName: 1. Does not end with 'PK' or 'FK' 2. Is
	 * not an Entity type (via ManyToOne associations) 3. Is not a Collection
	 * type (via OneToMany associations)
	 * 
	 * @param fieldName
	 * 
	 * @return
	 */
	public static boolean isSimpleField(String fieldName, Type fieldType) {
		boolean isSimpleField = true;

		if (fieldType.isCollectionType() || fieldType.isEntityType()
				|| fieldName.endsWith("PK") || fieldName.endsWith("FK")) {
			isSimpleField = false;
		}

		return isSimpleField;
	}

	/**
	 * Puts the specified named value in the ThreadLocal of the current
	 * Hibernate session context. Users may want to store named values within
	 * the scope of the current thread of the Hibernate session. Named values
	 * placed here would exist until the current thread of execution terminates.
	 * 
	 * @param name
	 * @param value
	 */
	public static void putInThreadLocal(String name, Object value) {
		Map namedValuesMap = (Map) namedValuesThread.get();

		if (namedValuesMap == null) {
			namedValuesMap = new HashMap();

			namedValuesThread.set(namedValuesMap);
		}

		namedValuesMap.put(name, value);
	}

	/**
	 * Returns the named value from the named-value thread's context.
	 * 
	 * @param name
	 * 
	 * @return
	 */
	public static Object getFromThreadLocal(String name) {
		Object namedValue = null;

		Map namedValueMap = (Map) namedValuesThread.get();

		if (namedValueMap != null) {
			namedValue = namedValueMap.get(name);
		}

		return namedValue;
	}

	/**
	 * Removes the specified named-value from the thread's context.
	 * 
	 * @param name
	 * 
	 * @return the named-value that was removed from thread local
	 */
	public static Object removeFromThreadLocal(String name) {
		Object namedValue = null;

		Map namedValueMap = (Map) namedValuesThread.get();

		if (namedValueMap != null) {
			namedValue = namedValueMap.remove(name);
		}

		return namedValue;
	}

	/**
	 * Clears all content stored in the Thread locals for the current thread.
	 */
	public static void clearThreadLocals() {
		editSolutionsThreadTransaction.remove();
		odsThreadTransaction.remove();
		prdThreadTransaction.remove();
		controlThreadTransaction.remove();
		engineThreadTransaction.remove();
		securityThreadTransaction.remove();
		stagingThreadTransaction.remove();
		datawarehouseThreadTransaction.remove();
		miscellaneousThreadTransaction.remove();

		editSolutionsThreadSession.remove();
		odsThreadSession.remove();
		prdThreadSession.remove();
		controlThreadSession.remove();
		engineThreadSession.remove();
		securityThreadSession.remove();
		stagingThreadSession.remove();
		datawarehouseThreadSession.remove();
		miscellaneousThreadSession.remove();

		namedValuesThread.remove();
	}

	/**
	 * Sets the value on the HibernateEntity's column
	 * 
	 * @param hibernateEntity
	 * @param valueString
	 * @param getterMethod
	 * @param setterName
	 * @param convertDates
	 * 
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private static void setColumnValue(HibernateEntity hibernateEntity,
			String valueString, Method getterMethod, String setterName,
			boolean convertDates) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Class dataType = getterMethod.getReturnType();

		Object value = convertStringToData(convertDates, valueString, dataType);

		Method setterMethod = hibernateEntity.getClass().getMethod(setterName,
				new Class[] { dataType });

		setterMethod.invoke(hibernateEntity, new Object[] { value });
	}

	/**
	 * Builds the Hibernate Query from the specified parameters.
	 * 
	 * @param maxResults
	 *            the maximum number of records to return
	 * @param firstResult
	 *            the starting point for returning up to the maximum number of
	 *            records specified
	 * @param hql
	 *            the hql expression
	 * @param session
	 * @param namedParameters
	 *            name/value pairs to insert into the hql expression
	 * 
	 * @return
	 * 
	 * @throws org.hibernate.HibernateException
	 * 
	 */
	private static Query buildQuery(int maxResults, int firstResult,
			String hql, Session session, Map namedParameters)
			throws HibernateException {
		Query q = session.createQuery(hql);

		if (firstResult > 0) {
			q.setFirstResult(firstResult);
		}

		if (maxResults > 0) {
			q.setMaxResults(maxResults);
		}

		if (namedParameters != null) {
			Set parameterNames = namedParameters.keySet();

			for (Iterator iterator = parameterNames.iterator(); iterator
					.hasNext();) {
				String parameterName = (String) iterator.next();

				Object parameterObject = namedParameters.get(parameterName);

				if (parameterObject instanceof java.util.List) {
					q.setParameterList(parameterName, (List) parameterObject);
				} else {
					if (parameterObject instanceof Object[]) {
						q.setParameterList(parameterName,
								(Object[]) parameterObject);
					} else {
						q.setParameter(parameterName, parameterObject);
					}
				}
			}
		}

		return q;
	}

	/**
	 * A helper method. Locates the matching Hibernate Entity using the
	 * specified Hibernate Session. We never allow a proxy to be the result of
	 * this get. If the Entity is a proxy, then we re-perfored an actual hql
	 * query as opposed to Hibernate's get() or load() methods to guarantee that
	 * we never attain a proxy.
	 * 
	 * @param entityElement
	 *            a DOM4J Element where the name is FooVO and PK is FooPK
	 * @param session
	 * @return a Hibernate Entity or null - never a proxy
	 */
	public static HibernateEntity getHibernateEntity(Element entityElement,
			Session session) {
		HibernateEntity hibernateEntity = null;

		String entityName = entityElement.getName();

		entityName = entityName.substring(0, entityName.length() - 2); // remove
																		// the
																		// "VO"
																		// at
																		// the
																		// end.

		Class entityClass = SessionHelper.getHibernateClass(entityName,
				SessionHelper.EDITSOLUTIONS);

		String entityPKName = entityName + "PK";

		Long entityPKValue = new Long(entityElement.element(entityPKName)
				.getText());

		hibernateEntity = (HibernateEntity) session.get(entityClass,
				entityPKValue);

		if (hibernateEntity instanceof HibernateProxy) {
			HibernateProxy proxy = (HibernateProxy) hibernateEntity;

			hibernateEntity = (HibernateEntity) proxy.writeReplace();
		}

		return hibernateEntity;
	}

	/**
	 * Runs the specified hql with the specified named parameters as though it
	 * were sql being executed via JDBC.
	 * 
	 * @param hql
	 * @param namedParameters
	 * @param targetDB
	 * @maxResults the maximum number of rows to return - only considered for
	 *             any int > 0
	 * @return QueryResult
	 */
	public static QueryResult executeHQLAsSQL(String hql, Map namedParameters,
			String targetDB, int maxResults) {
		QueryResult queryResult = null;

		ResultSet rs = null;

		PreparedStatement ps = null;

		Session session = getSession(targetDB);

		// Convert the hql to sql
		SessionFactoryImpl factoryImpl = (SessionFactoryImpl) session
				.getSessionFactory();

		QueryTranslatorImpl qt = new QueryTranslatorImpl("foo", hql, null,
				factoryImpl);

		qt.compile(new HashMap(), false);

		ParameterTranslations parameterTranslations = qt
				.getParameterTranslations();

		String sql = qt.getSQLString();

		// Grab a PreparedStatement and set its parameters
		try {
			// Create a PreparedStatement and set the named parameters.
			ps = session.connection().prepareStatement(sql);

			if (maxResults > 0) {
				ps.setMaxRows(maxResults);
			}

			Set<String> namedParameterNames = namedParameters.keySet();

			for (String namedParameterName : namedParameterNames) {
				String namedParameterValueString = (String) namedParameters
						.get(namedParameterName);

				int[] namedParameterLocations = qt
						.getNamedParameterLocs(namedParameterName);

				for (int namedParameterLocation : namedParameterLocations) {
					Type namedParameterExpectedType = parameterTranslations
							.getNamedParameterExpectedType(namedParameterName);

					Class returnedClass = namedParameterExpectedType
							.getReturnedClass();

					Constructor stringBasedConstructor = returnedClass
							.getConstructor(new Class[] { String.class });

					Object namedParameterValue = stringBasedConstructor
							.newInstance(new Object[] { namedParameterValueString });

					int namedParameterIndex = namedParameterLocation + 1; // JDBC
																			// is
																			// 1-indexe

					if (returnedClass == EDITDate.class) {
						ps.setObject(namedParameterIndex, DBUtil
								.convertStringToDate(namedParameterValueString));
					} else if (returnedClass == EDITDateTime.class) {
						ps.setObject(
								namedParameterIndex,
								DBUtil.convertStringToTimestamp(namedParameterValueString));
					} else if (returnedClass == EDITBigDecimal.class) {
						ps.setObject(namedParameterIndex,
								((EDITBigDecimal) namedParameterValue)
										.getBigDecimal());
					} else {
						ps.setObject(namedParameterIndex, namedParameterValue);
					}
				}
			}

			// Execute the query
			rs = ps.executeQuery();

			queryResult = new QueryResult(hql, sql, rs);
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();

			throw new HibernateException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
					System.out.println(ex);

					ex.printStackTrace();

					Logger.getLogger(SessionHelper.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}

		return queryResult;
	}

	/**
	 * Runs the specified hql with the specified named parameters as HQL. The
	 * Hibernate Session used is a stateless one. This is purposeful since we
	 * risk triggering lazy loading. The results are marshalled according to the
	 * marshalling rules of the implementing SEGEntities.
	 * 
	 * @param hql
	 * @param namedParameters
	 * @param targetDB
	 * @maxResults the maximum number of rows to return - only considered for
	 *             any int > 0
	 * @return QueryResult
	 */
	public static QueryResult executeHQLAsHQL(String hql, Map namedParameters,
			String targetDB, int maxResults) {
		QueryResult queryResult = null;

		Session session = null;

		try {
			session = getSeparateSession(targetDB);

			List results = executeHQL(session, hql, namedParameters, maxResults);

			queryResult = new QueryResult(hql, results);
		} finally {
			if (session != null)
				session.close();
		}

		return queryResult;
	}

	/**
	 * Returns a Hiberante StatelessSession. Most likely used to guarantee that
	 * "closed Session" errors won't occur when traversing the object graph. All
	 * objects should be explicitly defined in utlimate HQL.
	 * 
	 * The caller is expected to close the StatelessSession.
	 * 
	 * @param targetDB
	 * @return
	 */
	public static StatelessSession getStatelessSession(String targetDB) {
		StatelessSession statelessSession = dbSessionFactory
				.getStatelessSession(targetDB);

		return statelessSession;
	}

	/**
	 * Returns true if the entity belongs to same database as passed in
	 * targetDB.
	 * 
	 * @param element
	 * @param targetDB
	 * @return
	 */
	private static boolean belongsToSameDatabase(Element element,
			String targetDB) {
		boolean belongsToSameDatabase = false;

		String elementName = element.getName();

		String tableName = elementName.substring(0, elementName.length() - 2);

		Class entityClass = getHibernateClass(tableName, targetDB);

		String databaseTheEntityBelongsTo = null;

		if (entityClass != null) {
			try {
				databaseTheEntityBelongsTo = (String) entityClass.getField(
						"DATABASE").get(null);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();

				throw new RuntimeException(e);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();

				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				e.printStackTrace();

				throw new RuntimeException(e);
			} catch (SecurityException e) {
				e.printStackTrace();

				throw new RuntimeException(e);
			}
		}

		if (targetDB.equals(databaseTheEntityBelongsTo)) {
			belongsToSameDatabase = true;
		}

		return belongsToSameDatabase;
	}

	/**
	 * Returns true if the entity belongs to same database as the passed in
	 * targetDB.
	 * 
	 * @param tableName
	 *            name of the table or class to check against the targetDB
	 * @param targetDB
	 *            name of the database to check for the specified tableName
	 * 
	 * @return
	 */
	private static boolean belongsToSameDatabase(String tableName,
			String targetDB) {
		boolean belongsToSameDatabase = false;

		Class entityClass = getHibernateClass(tableName, targetDB);

		String databaseTheEntityBelongsTo = null;

		if (entityClass != null) {
			try {
				databaseTheEntityBelongsTo = (String) entityClass.getField(
						"DATABASE").get(null);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();

				throw new RuntimeException(e);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();

				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				e.printStackTrace();

				throw new RuntimeException(e);
			} catch (SecurityException e) {
				e.printStackTrace();

				throw new RuntimeException(e);
			}
		}

		if (targetDB.equals(databaseTheEntityBelongsTo)) {
			belongsToSameDatabase = true;
		}

		return belongsToSameDatabase;
	}
}
