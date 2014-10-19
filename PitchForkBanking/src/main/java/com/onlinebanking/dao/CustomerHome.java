package com.onlinebanking.dao;

// Generated Oct 18, 2014 8:48:50 AM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import com.onlinebanking.models.Customer;

/**
 * Home object for domain model class Customer.
 * @see com.onlinebanking.dao.Customer
 * @author Hibernate Tools
 */
public class CustomerHome {

	private static final Log log = LogFactory.getLog(CustomerHome.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public void persist(Customer transientInstance) {
		log.debug("persisting Customer instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Customer instance) {
		log.debug("attaching dirty Customer instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(Customer instance) {
		log.debug("attaching clean Customer instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Customer persistentInstance) {
		log.debug("deleting Customer instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Customer merge(Customer detachedInstance) {
		log.debug("merging Customer instance");
		try {
			Customer result = (Customer) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Customer findById(java.lang.String id) {
		log.debug("getting Customer instance with id: " + id);
		try {
			Customer instance = (Customer) sessionFactory.getCurrentSession().get("com.onlinebanking.models.Customer", id);
			
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
	public List<Customer> findByExample(Customer instance) {
		log.debug("finding Customer instance by example");
		try {
			List<Customer> results = sessionFactory.getCurrentSession().createCriteria("com.onlinebanking.models.Customer")
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
	public List<Customer> findAll() {
		log.debug("finding Customer instance by example");
		try {
			String queryString = "Select * from customer";
			List<Customer> results = sessionFactory.getCurrentSession().createSQLQuery(queryString).addEntity(Customer.class).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
