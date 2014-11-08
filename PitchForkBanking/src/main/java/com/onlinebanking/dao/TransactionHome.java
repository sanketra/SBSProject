package com.onlinebanking.dao;

// Generated using hibernate tools

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import com.onlinebanking.models.Transaction;
import com.onlinebanking.models.TransactionStatus;

/**
 * Home object for domain model class Transaction.
 * @see com.onlinebanking.dao.Transaction
 * @author Hibernate Tools
 */
public class TransactionHome {

	private static final Log log = LogFactory.getLog(TransactionHome.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public void persist(Transaction transientInstance) {
		log.debug("persisting Transaction instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Transaction instance) {
		log.debug("attaching dirty Transaction instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(Transaction instance) {
		log.debug("attaching clean Transaction instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Transaction persistentInstance) {
		log.debug("deleting Transaction instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Transaction merge(Transaction detachedInstance) {
		log.debug("merging Transaction instance");
		try {
			Transaction result = (Transaction) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Transaction findById(java.lang.String id) {
		log.debug("getting Transaction instance with id: " + id);
		try {
			Transaction instance = (Transaction) sessionFactory
					.getCurrentSession().get(
							"com.onlinebanking.models.Transaction", id);
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
	public List<Transaction> findByExample(Transaction instance) {
		log.debug("finding Transaction instance by example");
		try {
			List<Transaction> results = sessionFactory.getCurrentSession()
					.createCriteria("com.onlinebanking.models.Transaction")
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
	public List<Transaction> getAllTransactionsForAccountId(int id) {
		log.debug("finding User instance by example");
		try {
			String queryString = "Select * from transaction T where T.fromAcountNum = :id OR T.toAccountNum = :id";
			Session s = sessionFactory.getCurrentSession();
			List<Transaction> results = s.createSQLQuery(queryString).
					addEntity(Transaction.class).
					setParameter("id", id).
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
	public List<Transaction> getAllCriticalTransactions() {
		log.debug("finding Critical Transaction Requests");
		try {
			String queryString = "Select * from transaction T where T.transactionStatus = :status ";
			Session s = sessionFactory.getCurrentSession();
			List<Transaction> results = s.createSQLQuery(queryString).
					addEntity(Transaction.class).setParameter("status", TransactionStatus.ADMINPENDING).
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
	public List<Transaction> getPaymentRequestForAccountId(int id) {
		log.debug("finding User instance by example");
		try {
			String queryString = "Select * from transaction T where T.fromAcountNum = :id AND T.transactionStatus = :status";
			Session s = sessionFactory.getCurrentSession();
			List<Transaction> results = s.createSQLQuery(queryString).
					addEntity(Transaction.class).
					setParameter("id", id).setParameter("status", TransactionStatus.USERPENDING).
					list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public void updatePaymentRequests(Transaction t) {
		log.debug("finding User instance by example");
		try {
			Session s = sessionFactory.getCurrentSession();
			s.update(t);
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
