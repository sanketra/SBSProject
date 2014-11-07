package com.onlinebanking.dao;

// Generated using hibernate tools

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import com.onlinebanking.models.UserPublicKey;

/**
 * Home object for domain model class UserPublicKey.
 * @see com.onlinebanking.dao.UserPublicKey
 * @author Hibernate Tools
 */
public class UserPublicKeyHome {

	private static final Log log = LogFactory.getLog(UserPublicKeyHome.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public void persist(UserPublicKey transientInstance) {
		log.debug("persisting UserPublicKey instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(UserPublicKey instance) {
		log.debug("attaching dirty UserPublicKey instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(UserPublicKey instance) {
		log.debug("attaching clean UserPublicKey instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(UserPublicKey persistentInstance) {
		log.debug("deleting UserPublicKey instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserPublicKey merge(UserPublicKey detachedInstance) {
		log.debug("merging UserPublicKey instance");
		try {
			UserPublicKey result = (UserPublicKey) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UserPublicKey findById(java.lang.String id) {
		log.debug("getting UserPublicKey instance with id: " + id);
		try {
			UserPublicKey instance = (UserPublicKey) sessionFactory
					.getCurrentSession().get(
							"com.onlinebanking.models.UserPublicKey", id);
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
	public List<UserPublicKey> findByExample(UserPublicKey instance) {
		log.debug("finding UserPublicKey instance by example");
		try {
			List<UserPublicKey> results = sessionFactory.getCurrentSession().createCriteria("com.onlinebanking.dao.UserPublicKey")
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
