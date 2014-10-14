package com.onlinebanking.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.onlinebanking.models.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerDAOImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addPerson(Customer p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(p);
		logger.info("Person saved successfully, Person Details="+p);
	}

	@Override
	public void updatePerson(Customer p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		logger.info("Person updated successfully, Person Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> listPersons() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Customer> personsList = session.createQuery("from Customer").list();
		for(Customer p : personsList){
			logger.info("Person List::"+p);
		}
		return personsList;
	}

	@Override
	public Customer getPersonById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Customer p = (Customer) session.load(Customer.class, new Integer(id));
		logger.info("Person loaded successfully, Person details="+p);
		return p;
	}

	@Override
	public void removePerson(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Customer p = (Customer) session.load(Customer.class, new Integer(id));
		if(null != p){
			session.delete(p);
		}
		logger.info("Person deleted successfully, person details="+p);
	}

}
