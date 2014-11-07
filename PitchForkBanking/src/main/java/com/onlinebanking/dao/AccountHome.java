package com.onlinebanking.dao;

// Generated using hibernate tools

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import com.onlinebanking.models.Account;

/**
 * Home object for domain model class Account.
 * @see com.onlinebanking.dao.Account
 * @author Hibernate Tools
 */
public class AccountHome {

	private static final Log log = LogFactory.getLog(AccountHome.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public void persist(Account transientInstance) {
		log.debug("persisting Account instance");
		try {
			Session s = sessionFactory.getCurrentSession();
			s.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Account instance) {
		log.debug("attaching dirty Account instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(Account instance) {
		log.debug("attaching clean Account instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Account persistentInstance) {
		log.debug("deleting Account instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Account merge(Account detachedInstance) {
		log.debug("merging Account instance");
		try {
			Account result = (Account) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Account findById(int id) {
		log.debug("getting Account instance with id: " + id);
		try {
			Account instance = (Account) sessionFactory.getCurrentSession()
					.get("com.onlinebanking.models.Account", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Account> findByExample(Account instance) {
		log.debug("finding Account instance by example");
		try {
			List<Account> results = sessionFactory.getCurrentSession().createCriteria("com.onlinebanking.models.Account")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Account> getUserAccounts(String userId) {
		log.debug("finding User instance by example");
		try {
			String queryString = "Select * from account A where A.userId = :userId";
			Session s = sessionFactory.getCurrentSession();
			List<Account> results = s.createSQLQuery(queryString).
					addEntity(Account.class).
					setParameter("userId", userId).
					list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Account> getAllUserAccounts() {
		try {
			String queryString = "Select * from account";
			Session s = sessionFactory.getCurrentSession();
			List<Account> results = s.createSQLQuery(queryString).
					addEntity(Account.class).
					list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("get all user accounts failed", re);
			throw re;
		}
	}
}
