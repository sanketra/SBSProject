package com.onlinebanking.dao;

// Generated Oct 21, 2014 6:59:51 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
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
					.get("com.onlinebanking.dao.Requests", id);
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
					.createCriteria("com.onlinebanking.dao.Requests")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}