package com.nbicc.ita.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.operation.DeleteOperation;
import com.nbicc.ita.background.dao.CloudDao;
import com.nbicc.ita.background.dao.CloudDaoPro;
import com.nbicc.ita.util.CommonUtil;

/**
 * @author zhuolin(zl@nbicc.com)
 * @date 2015年6月4日 类说明
 */
@Repository("cloudDaoPro")
public class CloudDaoProImpl<T> implements CloudDaoPro<T> {

	@Autowired
	private SessionFactory sessionFactory;

	private static final int batchSize = 10;

	@Override
	public void add(T t) {
		Session session = getSession();
		session.persist(t);
		closeSession(session);
		;
	}

	@Override
	public void update(T t) {
		Session session = getSession();
		session.update(t);
		closeSession(session);
	}

	@Override
	public void delete(T t) {
		Session session = getSession();
		session.delete(t);
		closeSession(session);
	}
	
	@Override
	public void addMany(Object brand,Object qr,List<Object>...lists){
		Transaction tx = null;
		Session session = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			addOperation(session,brand,qr, lists);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			closeSession(session);
		}
	}
	
	public void addOperation(Session session,Object brand,Object qr,List<Object>...lists){
		session.persist(brand);
		if(qr!=null){
			session.persist(qr);
		}
		int i=0;
		for(List<Object> objects:lists){
			if (!CommonUtil.isEmpty(objects)) {
				for (Object t:objects) {
					i++;
					session.persist(t);
					if ((i + 1) % batchSize == 0) {
						session.flush();
						session.clear();
					}
				}
			}
		}
	}

	@Override
	public void deleteMany(List<Object>list,Object... objects) {
		Transaction tx = null;
		Session session = null;
		try {
			session = getSession();
			tx = session.beginTransaction();
			for(Object object:objects){
				if(object!=null){
					session.delete(object);
				}
			}
			
			if (!CommonUtil.isEmpty(list)) {
				int listSize = list.size();
				for (int i = 0; i < listSize; i++) {
					session.delete(list.get(i));
					if ((i + 1) % batchSize == 0) {
						session.flush();
						session.clear();
					}
				}
			}
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			closeSession(session);
		}
	}

	@Override
	public boolean deleteById(Serializable id) {
		Session session = getSession();
		T t = findById(id);
		if (t == null) {
			return false;
		}
		session.delete(t);
		closeSession(session);
		return true;
	}

	@Deprecated
	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Deprecated
	@Override
	public T findById(Serializable id) {
		return null;
		// return (T) (getSession().get(persistentClass, id));
	}

	@Deprecated
	@Override
	public T findForObject(T entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Session getSession() {
		Configuration configure = new Configuration();
		sessionFactory = configure.configure().buildSessionFactory();
		return sessionFactory.openSession();
	}

	public void closeSession(Session session) {
		if (session != null && session.isOpen()) {
			session.close();
		}
	}

	@Override
	public List<T> findByHql(String hql, Object... objects) {
		Session session = getSession();
		Query query = session.createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		List<T> list = query.list();
		closeSession(session);
		return list;
	}

	@Override
	public T getByHql(String hql, Object... objects) {
		Session session = getSession();
		Query query = session.createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		List<T> list = query.list();
		T t = null;
		if (CommonUtil.isEmpty(list) || list.size() > 1) {
			t = null;
		} else {
			t = list.get(0);
		}
		closeSession(session);
		return t;
	}

	@Override
	public void batchAdd(List<T> list) {
		Session session = getSession();
		if (!CommonUtil.isEmpty(list)) {
			int listSize = list.size();
			for (int i = 0; i < listSize; i++) {
				session.persist(list.get(i));
				if ((i + 1) % batchSize == 0) {
					session.flush();
					session.clear();
				}
			}
		}
		closeSession(session);
	}

	@Override
	public List<T> findByLimit(String hql, int firstResult, int maxResults, Object... objects) {
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i, objects[i]);
		}
		List<T> list = query.list();
		closeSession(session);
		return list;
	}

	@Override
	public void deleteBySql(String sql) {
		Session session = getSession();
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();
		closeSession(session);
	}

	@Override
	public void batchDelete(List<T> list) {
		Session session = getSession();
		if (!CommonUtil.isEmpty(list)) {
			int listSize = list.size();
			for (int i = 0; i < listSize; i++) {
				session.delete(list.get(i));
				if ((i + 1) % batchSize == 0) {
					session.flush();
					session.clear();
				}
			}
		}
		closeSession(session);
	}

	@Override
	public void merge(T t) {
		// TODO Auto-generated method stub

	}
}
