package com.onlinebanking.dao;

// Generated using tools

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import com.onlinebanking.models.Userotp;

/**
 * Home object for domain model class Userotp.
 * 
 * @see com.onlinebanking.dao.Userotp
 * @author Hibernate Tools
 */
public class UserotpHome {

	private static final Log log = LogFactory.getLog(UserotpHome.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public void persist(Userotp transientInstance) {
		log.debug("persisting Userotp instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Userotp instance) {
		log.debug("attaching dirty Userotp instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(Userotp instance) {
		log.debug("attaching clean Userotp instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Userotp persistentInstance) {
		log.debug("deleting Userotp instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Userotp merge(Userotp detachedInstance) {
		log.debug("merging Userotp instance");
		try {
			Userotp result = (Userotp) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Userotp findById(java.lang.String id) {
		log.debug("getting Userotp instance with id: " + id);
		try {
			Userotp instance = (Userotp) sessionFactory.getCurrentSession()
					.get("com.onlinebanking.models.Userotp", id);
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
	public List<Userotp> findByExample(Userotp instance) {
		log.debug("finding Userotp instance by example");
		try {
			List<Userotp> results = sessionFactory.getCurrentSession()
					.createCriteria("com.onlinebanking.models.Userotp")
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
