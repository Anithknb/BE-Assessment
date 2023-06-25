package com.springboot.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.ResourceNotFoundException;
import com.springboot.main.model.CustomerProduct;
import com.springboot.main.repository.CustomerProductRepository;

@Service
public class CustomerProductService {

	@Autowired
	private CustomerProductRepository customerProductRepository;

	public CustomerProduct insert(CustomerProduct customerProduct) {
		return customerProductRepository.save(customerProduct);
	}
	
	public List<CustomerProduct> getAll() {
		return customerProductRepository.findAll();
	}
	
	public CustomerProduct getById(int id) throws ResourceNotFoundException {
		Optional<CustomerProduct> optional = customerProductRepository.findById(id);
		
		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Invalid ID given");
		}
		
		return optional.get();
	}
	
	public List<CustomerProduct> getByCustomerId(int customerId) throws ResourceNotFoundException {
		List<CustomerProduct> customerProductList = customerProductRepository.findAllByCustomerId(customerId);
		
		if (customerProductList == null) {
			throw new ResourceNotFoundException("Invalid ID given");
		}
		
		return customerProductList;
	}
	
	public void delete(int id) {
		customerProductRepository.deleteById(id);
	}

}