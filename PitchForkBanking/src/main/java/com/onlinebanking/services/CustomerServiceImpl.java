package com.onlinebanking.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlinebanking.dao.CustomerDAO;
import com.onlinebanking.models.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private CustomerDAO personDAO;

	public void setPersonDAO(CustomerDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public void addPerson(Customer p) {
		this.personDAO.addPerson(p);
	}

	@Override
	@Transactional
	public void updatePerson(Customer p) {
		this.personDAO.updatePerson(p);
	}

	@Override
	@Transactional
	public List<Customer> listPersons() {
		return this.personDAO.listPersons();
	}

	@Override
	@Transactional
	public Customer getPersonById(int id) {
		return this.personDAO.getPersonById(id);
	}

	@Override
	@Transactional
	public void removePerson(int id) {
		this.personDAO.removePerson(id);
	}

}
