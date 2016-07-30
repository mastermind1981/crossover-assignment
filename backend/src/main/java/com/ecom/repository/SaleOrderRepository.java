package com.ecom.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecom.model.SaleOrder;

/**
 * @author raghunandangupta
 *
 */
@Repository
public interface SaleOrderRepository extends CrudRepository<SaleOrder, String> {

	
	/**
	 * @param pageRequest
	 * @return
	 */
	@Query("select saleOrder from SaleOrder saleOrder")
	List<SaleOrder> findAll(Pageable pageRequest);

}
