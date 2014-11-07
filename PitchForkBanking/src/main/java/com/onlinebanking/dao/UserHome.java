package com.onlinebanking.dao;

// Generated using hibernate tools

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.transaction.annotation.Transactional;

import com.onlinebanking.models.User;
import com.onlinebanking.models.Role;

/**
 * Home object for domain model class User.
 * 
 * @see com.onlinebanking.dao.User
 * @author Hibernate Tools
 */
public class UserHome {

	private static final Log log = LogFactory.getLog(UserHome.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public void persist(User transientInstance) {
		log.debug("persisting User instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(User instance) {
		log.debug("attaching dirty User instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation")
	public void attachClean(User instance) {
		log.debug("attaching clean User instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(User persistentInstance) {
		log.debug("deleting User instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public User merge(User detachedInstance) {
		log.debug("merging User instance");
		try {
			User result = (User) sessionFactory.getCurrentSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	
	public int isUserUnique(User u) {
		try {
			String queryString = "Select * from user U where U.emailId = :emailId or U.ssn = :ssn or U.phoneno = :phoneno";
			@SuppressWarnings("unchecked")
			List<User> results = sessionFactory.getCurrentSession()
					.createSQLQuery(queryString).addEntity(User.class)
					.setParameter("emailId", u.getEmailId())
					.setParameter("ssn", u.getSsn())
					.setParameter("phoneno", u.getPhoneno())
					.list();
			return results.size();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public User getAdmin() {
		try {
			String role = Role.ADMIN;
			String queryString = "Select * from user U where U.role = :role";
			@SuppressWarnings("unchecked")
			List<User> results = sessionFactory.getCurrentSession()
					.createSQLQuery(queryString).addEntity(User.class)
					.setParameter("role", role).list();

			if (results.size() > 0) {
				log.debug("get successful, no instance found");
				return results.get(0);
			} else {
				log.debug("get successful, instance found");
				return null;
			}
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public User findById(String id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) sessionFactory.getCurrentSession().get(
					"com.onlinebanking.models.User", id);

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
	public List<User> findByExample(User instance) {
		log.debug("finding User instance by example");
		try {
			List<User> results = sessionFactory.getCurrentSession()
					.createCriteria("com.onlinebanking.models.User")
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
	public List<User> findAll() {
		log.debug("finding User instance by example");
		try {
			String queryString = "Select * from user";
			List<User> results = sessionFactory.getCurrentSession()
					.createSQLQuery(queryString).addEntity(User.class).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAllNewRegistrations() {
		log.debug("finding User instance by example");
		try {
			String queryString = "Select * from user U where U.enabled = 0";
			List<User> results = sessionFactory.getCurrentSession()
					.createSQLQuery(queryString).addEntity(User.class).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllCustomers() {
		log.debug("finding User instance by example");
		try {
			String role1 = Role.USER;
			String role2 = Role.MERCHANT;
			String queryString = "Select * from user U where U.role= :role1 OR U.role = :role2 AND U.enabled = 1";
			List<User> results = sessionFactory.getCurrentSession()
					.createSQLQuery(queryString).addEntity(User.class)
					.setParameter("role1", role1).setParameter("role2", role2).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}


	@SuppressWarnings("unchecked")
	public List<User> findAllEmployees() {
		log.debug("finding User instance by example");
		try {
			String role = Role.EMPLOYEE;
			String queryString = "Select * from user U where U.role= :role";
			List<User> results = sessionFactory.getCurrentSession()
					.createSQLQuery(queryString).addEntity(User.class)
					.setParameter("role", role).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public User getUserByEmailId(String emailId) {
		log.debug("getting User instance with emailId: " + emailId);
		try {
			User instance = null;
			String queryString = "Select * from user U where U.emailId = :emailId";
			List<User> results = sessionFactory.getCurrentSession()
					.createSQLQuery(queryString).addEntity(User.class)
					.setParameter("emailId", emailId).list();

			if (results.size() > 0) {
				instance = results.get(0);
			}

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

}
