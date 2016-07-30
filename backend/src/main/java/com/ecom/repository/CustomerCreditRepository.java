package com.ecom.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecom.model.CustomerCredit;

@Repository
public interface CustomerCreditRepository extends CrudRepository<CustomerCredit, String> {

	/**
	 * @param pageRequest
	 * @return
	 */
	@Query("select customerCredit from CustomerCredit customerCredit")
	List<CustomerCredit> findAll(Pageable pageRequest);

}
