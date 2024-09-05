package com.editsolutions.prd.dao;

/**
 *
 * @since 1.5
 * @version 1.0
 * 
 * A generic DAO abstract class.
 * Uses Hibernate
 * Provides all necessary DAO functionality
 * Fully customizable 
 * 
 * Suitable for Spring 3 
 * 
 * For batch updates see the hibernate documentation:
 * http://docs.jboss.org/hibernate/orm/3.3/reference/en/html/batch.html
 * 
 */

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.beanutils.*;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.TransientObjectException;
import org.hibernate.criterion.Order;

import edit.services.db.hibernate.SessionHelper;

public abstract class Dao<T> {

	private Class<T> entityClass;

	/*
	 * @Autowired private SessionFactory sessionFactory = new SessionFactory() {
	 * 
	 * private static final long serialVersionUID = 1L;
	 * 
	 * @Override public Reference getReference() throws NamingException { //
	 * TODO Auto-generated method stub return null; }
	 * 
	 * @Override public StatelessSession openStatelessSession(Connection arg0) {
	 * // TODO Auto-generated method stub return null; }
	 * 
	 * @Override public StatelessSession openStatelessSession() { // TODO
	 * Auto-generated method stub return null; }
	 * 
	 * @Override public org.hibernate.classic.Session openSession(Connection
	 * arg0, Interceptor arg1) { // TODO Auto-generated method stub return null;
	 * }
	 * 
	 * @Override public org.hibernate.classic.Session openSession(Interceptor
	 * arg0) throws HibernateException { // TODO Auto-generated method stub
	 * return null; }
	 * 
	 * @Override public org.hibernate.classic.Session openSession(Connection
	 * arg0) { // TODO Auto-generated method stub return null; }
	 * 
	 * @Override public org.hibernate.classic.Session openSession() throws
	 * HibernateException { // TODO Auto-generated method stub return null; }
	 * 
	 * @Override public boolean isClosed() { // TODO Auto-generated method stub
	 * return false; }
	 * 
	 * @Override public Statistics getStatistics() { // TODO Auto-generated
	 * method stub return null; }
	 * 
	 * @Override public FilterDefinition getFilterDefinition(String arg0) throws
	 * HibernateException { // TODO Auto-generated method stub return null; }
	 * 
	 * @Override public Set getDefinedFilterNames() { // TODO Auto-generated
	 * method stub return null; }
	 * 
	 * @Override public org.hibernate.classic.Session getCurrentSession() throws
	 * HibernateException { // TODO Auto-generated method stub return null; }
	 * 
	 * @Override public CollectionMetadata getCollectionMetadata(String arg0)
	 * throws HibernateException { // TODO Auto-generated method stub return
	 * null; }
	 * 
	 * @Override public ClassMetadata getClassMetadata(String arg0) throws
	 * HibernateException { // TODO Auto-generated method stub return null; }
	 * 
	 * @Override public ClassMetadata getClassMetadata(Class arg0) throws
	 * HibernateException { // TODO Auto-generated method stub return null; }
	 * 
	 * @Override public Map getAllCollectionMetadata() throws HibernateException
	 * { // TODO Auto-generated method stub return null; }
	 * 
	 * @Override public Map getAllClassMetadata() throws HibernateException { //
	 * TODO Auto-generated method stub return null; }
	 * 
	 * @Override public void evictQueries(String arg0) throws HibernateException
	 * { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void evictQueries() throws HibernateException { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void evictEntity(String arg0, Serializable arg1) throws
	 * HibernateException { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void evictEntity(String arg0) throws HibernateException
	 * { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void evictCollection(String arg0, Serializable arg1)
	 * throws HibernateException { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void evictCollection(String arg0) throws
	 * HibernateException { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void evict(Class arg0, Serializable arg1) throws
	 * HibernateException { // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void evict(Class arg0) throws HibernateException { //
	 * TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void close() throws HibernateException { // TODO
	 * Auto-generated method stub
	 * 
	 * } };
	 */

	public Dao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/*
	 * Return a list defined by the Criteria
	 */
	public List<T> findAll(Criteria criteria) {
		return criteria.list();
	}

	/*
	 * Return all of type 'T' (as determined by the generic member variable type
	 * 'entityClass')
	 */
	public List<T> findAll() {
		return findAll(SessionHelper.getSession(SessionHelper.PRD)
				.createCriteria(entityClass));

	}

	/*
	 * Use for pagination Filter by Criteria
	 */
	public List<T> findRange(Criteria criteria, int beginIndex, int endIndex) {
		criteria.setFirstResult(beginIndex);
		criteria.setMaxResults(endIndex - beginIndex);
		return criteria.list();
	}

	/*
	 * Use for pagination
	 */
	public List<T> findRange(int beginIndex, int pageSize) {
		Criteria criteria = SessionHelper.getSession(SessionHelper.PRD)
				.createCriteria(entityClass);
		return findRange(criteria, beginIndex, pageSize);
	}

	/*
	 * Use for pagination but order by 'propertyName' before paging Filter by
	 * criteria before paging
	 */
	public List<T> findRangeOrderFirst(Criteria criteria, int beginIndex,
			int pageSize, String propertyName, boolean asc) {
		if (asc)
			criteria.addOrder(Order.asc(propertyName));
		else
			criteria.addOrder(Order.desc(propertyName));
		criteria.setFirstResult(beginIndex);
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}

	/*
	 * Use for pagination but order by 'propertyName' before paging
	 */
	public List<T> findRangeOrderFirst(int beginIndex, int pageSize,
			String propertyName, boolean asc) {
		Criteria criteria = SessionHelper.getSession(SessionHelper.PRD)
				.createCriteria(entityClass);
		return findRangeOrderFirst(criteria, beginIndex, pageSize,
				propertyName, asc);
	}

	/*
	 * Use for pagination Filter by Criteria before paging
	 */
	public List<T> findPage(Criteria criteria, int pageNumber, int pageSize) {
		int beginIndex = pageNumber * pageSize;
		return findRange(criteria, beginIndex, pageSize);
	}

	/*
	 * Use for pagination
	 */
	public List<T> findPage(int pageNumber, int pageSize) {
		int beginIndex = pageNumber * pageSize;
		return findRange(beginIndex, pageSize);
	}

	/*
	 * Return a page Filter by criteria first Order by propertyName before
	 * returning a page
	 */
	public List<T> findPageOrderFirst(Criteria criteria, int pageNumber,
			int pageSize, String propertyName, boolean asc) {
		int beginIndex = pageNumber * pageSize;
		return findRangeOrderFirst(criteria, beginIndex, pageSize,
				propertyName, asc);
	}

	/*
	 * Return a page Order by propertyName before returning a page
	 */
	public List<T> findPageOrderFirst(int pageNumber, int pageSize,
			String propertyName, boolean asc) {
		int beginIndex = pageNumber * pageSize;
		return findRangeOrderFirst(beginIndex, pageSize, propertyName, asc);
	}

	/*
	 * CRUD Retrieve
	 */
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		return get(entityClass, id);
	}

	/*
	 * CRUD Retrieve
	 */
	@SuppressWarnings("unchecked")
	public T get(Class c, Serializable id) {
		return (T) SessionHelper.getSession(SessionHelper.PRD).get(c, id);
	}

	/*
	 * CRUD Retrieve
	 */
	@SuppressWarnings("unchecked")
	public T get(Criteria criteria) {
		return (T) criteria.uniqueResult();
	}

	/*
	 * CRUD Delete
	 */
	public void delete(T entity) {
		SessionHelper.getSession(SessionHelper.PRD).delete(entity);
	}

	/*
	 * CRUD Create
	 */
	public T insert(T entity) throws HibernateException {
		//try {
			Session session = SessionHelper.getSession(SessionHelper.PRD);
			session.clear();
  	        //try {
			//	session.connection().setAutoCommit(false);
			//} catch (SQLException e) {
	//			e.printStackTrace();
	//		}
	//		session.getTransaction().begin();
			session.save(entity);
//			session.getTransaction().commit();
		//} catch (Exception e) {
		//	System.out.println(e.toString());
		//}
		return entity;
	}

	/*
	 * CRUD Create or Update
	 */

	public T save(T entity) throws HibernateException {
		boolean pkOK = true;
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		try {
			Long primaryKey = getPrimaryKey(entity);
			if (primaryKey != null && primaryKey > 0) {
				update(entity);
//				session.connection().commit();
			} else {
				entity = insert(entity);
//				session.connection().commit();
			}
//			session.getTransaction().commit();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			pkOK = false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			pkOK = false;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			pkOK = false;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			pkOK = false;
		}

		if (!pkOK) {
			try {
				update(entity);
				session.flush();
			} catch (TransientObjectException e) {
				// insert not update
				entity = insert(entity);
			}
		}

		return entity;
	}

	/*
	 * CRUD Update
	 */
	public void update(T entity) throws HibernateException {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		session.clear();
	    try {
			session.connection().setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.getTransaction().begin();
		session.update(entity);
		session.getTransaction().commit();
	}

	public Session getCurrentSession() {
		return SessionHelper.getSession(SessionHelper.PRD);
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	private Long getPrimaryKey(T entity) throws IllegalAccessException,
	InvocationTargetException, NoSuchMethodException {
		String primaryKeyFieldName = getPrimaryKeyFieldName();
		Long pk = null;
		if (primaryKeyFieldName != null) {
			if (BeanUtils.getSimpleProperty(entity, primaryKeyFieldName) != null) {
				pk = Long.parseLong(BeanUtils.getSimpleProperty(entity,
						primaryKeyFieldName));
			}
		}

		return pk;
	}

	private String getPrimaryKeyFieldName() {
		Session session = SessionHelper.getSession(SessionHelper.PRD);
		return session.getSessionFactory().getClassMetadata(entityClass)
				.getIdentifierPropertyName();
	}

}
