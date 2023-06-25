package com.springboot.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.main.model.CustomerProduct;

public interface CustomerProductRepository extends JpaRepository<CustomerProduct, Integer> {

	@Query("select cp from CustomerProduct cp where cp.customer.id=?1")
	List<CustomerProduct> findAllByCustomerId(int customerId);
	
}