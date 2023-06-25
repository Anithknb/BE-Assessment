package com.springboot.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.main.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}