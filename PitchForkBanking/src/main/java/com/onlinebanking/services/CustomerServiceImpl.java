package com.onlinebanking.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlinebanking.dao.CustomerHome;
import com.onlinebanking.models.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private CustomerHome personHome;

	public void setPersonHome(CustomerHome personDAO) {
		this.personHome = personDAO;
	}

	@Override
	@Transactional
	public void addPerson(Customer p) {
		this.personHome.persist(p);
	}

	@Override
	@Transactional
	public void updatePerson(Customer p) {
		this.personHome.merge(p);
	}

	@Override
	@Transactional
	public List<Customer> listPersons() {
		return this.personHome.findAll();
	}

	@Override
	@Transactional
	public Customer getPersonById(int id) {
		return this.personHome.findById(id);
	}

	@Override
	@Transactional
	public void removePerson(int id) {
		this.personHome.delete(this.personHome.findById(id));
	}

}
