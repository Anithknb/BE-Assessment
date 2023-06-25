package com.springboot.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.main.model.ReturnRegister;

public interface ReturnRegisterRepository extends JpaRepository<ReturnRegister, Integer> {

	@Query("select rr from ReturnRegister rr where rr.product.id=?1 AND rr.quantity >= ?2")
	ReturnRegister checkQuantity(int productId, int quantityPuchased);
	
}