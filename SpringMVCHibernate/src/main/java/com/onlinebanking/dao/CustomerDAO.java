package com.onlinebanking.dao;

import java.util.List;

import com.onlinebanking.models.Customer;

public interface CustomerDAO {

	public void addPerson(Customer p);
	public void updatePerson(Customer p);
	public List<Customer> listPersons();
	public Customer getPersonById(int id);
	public void removePerson(int id);
}
