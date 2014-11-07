package com.onlinebanking.dao;

// Generated using hibernate tools

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import com.onlinebanking.models.Requests;

/**
 * Home object for domain model class Requests.
 * @see com.onlinebanking.dao.Requests
 * @author Hibernate Tools
 */
public class RequestsHome {

	private static final Log log = LogFactory.getLog(RequestsHome.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public void persist(Requests transientInstance) {
		log.debug("persisting Requests instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Requests instance) {
		log.debug("attaching dirty Requests instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(Requests instance) {
		log.debug("attaching clean Requests instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Requests persistentInstance) {
		log.debug("deleting Requests instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Requests merge(Requests detachedInstance) {
		log.debug("merging Requests instance");
		try {
			Requests result = (Requests) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Requests findById(String id) {
		log.debug("getting Requests instance with id: " + id);
		try {
			Requests instance = (Requests) sessionFactory.getCurrentSession()
					.get("com.onlinebanking.models.Requests", id);
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
	public List<Requests> findByExample(Requests instance) {
		log.debug("finding Requests instance by example");
		try {
			List<Requests> results = sessionFactory.getCurrentSession()
					.createCriteria("com.onlinebanking.models.Requests")
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
	public List<Requests> getAllRequestsToUser(String userId) {
		log.debug("finding all requests from user "+userId);
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM Requests where toUserId = :userId");
			query.setParameter("userId", userId);
			return query.list();
			
		} catch (RuntimeException re) {
			log.error("error occurred while retrieving requests", re);
			throw re;
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<Requests> getAllRequestsFromUser(String userId) {
		log.debug("finding all requests from user "+userId);
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM Requests where fromUserId = :userId");
			query.setParameter("userId", userId);
			return query.list();
			
		} catch (RuntimeException re) {
			log.error("error occurred while retrieving requests", re);
			throw re;
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<Requests> getApprovedTransactionRequestsForUser(String userId) {
		log.debug("finding transaction ids of approved requests from user "+userId);
		try {
			Session session = sessionFactory.getCurrentSession();
			SQLQuery query = session.createSQLQuery("SELECT * FROM Requests where fromUserId = :userId and status = 'approved' and type = 'transaction'");
			query.setParameter("userId", userId);
			return query.addEntity(Requests.class).list();
			
		} catch (RuntimeException re) {
			log.error("error occurred while retrieving transaction ids", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Requests> getApprovedProfileRequestsForUser(String userId) {
		log.debug("finding profile ids of approved requests from user "+userId);
		try {
			Session session = sessionFactory.getCurrentSession();
			SQLQuery query = session.createSQLQuery("SELECT * FROM Requests where fromUserId = :userId and status = 'approved' and type = 'profile'");
			query.setParameter("userId", userId);
			return query.addEntity(Requests.class).list();
			
			} catch (RuntimeException re) {
			log.error("error occurred while retrieving transaction ids", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Requests> getAllPendingRequests(String userId) {
		log.debug("finding transaction ids of approved requests from user "+userId);
		try {
			Session session = sessionFactory.getCurrentSession();
			SQLQuery query = session.createSQLQuery("SELECT * FROM Requests where fromUserId = :userId and status = 'pending'");
			query.setParameter("userId", userId);
			return query.addEntity(Requests.class).list();
			
		} catch (RuntimeException re) {
			log.error("error occurred while retrieving transaction ids", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Requests> getAllPendingRequests() {
		log.debug("Getting all Pending requests");
		try {
			Session session = sessionFactory.getCurrentSession();
			SQLQuery query = session.createSQLQuery("SELECT * FROM Requests where status = 'pending'");
			return query.addEntity(Requests.class).list();
			
		} catch (RuntimeException re) {
			log.error("error occurred while retrieving pending requests", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Requests> getAllPendingUserAccessRequests() {
		log.debug("Getting all Pending additional account requests");
		try {
			String status = "pending";
			String type1 = "Profile";
			String type2 = "Transaction";
			String queryString = "SELECT * FROM Requests R where R.status = :status AND (R.type = :type1 OR R.type = :type2) ";
			List<Requests> results = sessionFactory.getCurrentSession()
					.createSQLQuery(queryString).addEntity(Requests.class)
					.setParameter("status", status).setParameter("type1", type1).setParameter("type2", type2).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Requests> getAllPendingAdditionalAccountRequests() {
		log.debug("Getting all Pending additional account requests");
		try {
			String status = "pending";
			String type = "createaccount";
			String queryString = "SELECT * FROM Requests R where R.status = :status AND R.type = :type ";
			List<Requests> results = sessionFactory.getCurrentSession()
					.createSQLQuery(queryString).addEntity(Requests.class)
					.setParameter("status", status).setParameter("type", type).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Requests> getAllApprovedRequests() {
		log.debug("Getting all Approved requests");
		try {
			Session session = sessionFactory.getCurrentSession();
			SQLQuery query = session.createSQLQuery("SELECT * FROM Requests where status = 'approved'");
			return query.addEntity(Requests.class).list();
			
		} catch (RuntimeException re) {
			log.error("error occurred while retrieving approved requests", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Requests> getAllDeclinedRequests() {
		log.debug("Getting all Declined requests");
		try {
			Session session = sessionFactory.getCurrentSession();
			SQLQuery query = session.createSQLQuery("SELECT * FROM Requests where status = 'declined'");
			return query.addEntity(Requests.class).list();
			
		} catch (RuntimeException re) {
			log.error("error occurred while retrieving declined requests", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Requests> getRequestsFor(String fromUserId, String toUserId, String type)
	{
		log.debug("Getting requests from user id"+fromUserId+"to user id"+toUserId);
		try
		{
			Session session = sessionFactory.getCurrentSession();
			SQLQuery query = session.createSQLQuery("SELECT * FROM Requests where fromUserId = :fromUserId and toUserId = :toUserId and type = :type");
			return query.addEntity(Requests.class)
					.setParameter("fromUserId", fromUserId)
					.setParameter("toUserId", toUserId)
					.setParameter("type", type)
					.list();
			
		} catch (RuntimeException re) {
			log.error("error occurred while retrieving requests for pair"+fromUserId+" and "+toUserId);
			throw re;
		}
	}
	
	public int getPendingAccountCreationRequests(String userId) {
		try
		{
			Session session = sessionFactory.getCurrentSession();
			SQLQuery query = session.createSQLQuery("SELECT * FROM Requests where fromUserId = :userId and type = :type and status = :status");
			return query.addEntity(Requests.class)
					.setParameter("userId", userId)
					.setParameter("type", "createaccount")
					.setParameter("status", "pending")
					.list().size();
		} catch (RuntimeException re) {
			throw re;
		}
	}
}
