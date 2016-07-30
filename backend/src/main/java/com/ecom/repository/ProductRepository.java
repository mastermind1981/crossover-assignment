package com.ecom.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecom.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, String>{

	/**
	 * @param pageRequest
	 * @return
	 */
	@Query("select product from Product product")
	List<Product> findAll(Pageable pageRequest);
}
