package com.ecom.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecom.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String>{

}
