package com.onlinebanking.services;

import java.util.List;

import com.onlinebanking.models.Customer;

public interface CustomerService {

	public void addPerson(Customer p);
	public void updatePerson(Customer p);
	public List<Customer> listPersons();
	public Customer getPersonById(String id);
	public void removePerson(String id);
	
}
