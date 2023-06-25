package com.springboot.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.ResourceNotFoundException;
import com.springboot.main.model.ReturnRegister;
import com.springboot.main.repository.ReturnRegisterRepository;

@Service
public class ReturnRegisterService {

	@Autowired
	private ReturnRegisterRepository returnRegisterRepository;

	public ReturnRegister insert(ReturnRegister returnRegister) {
		return returnRegisterRepository.save(returnRegister);
	}

	public List<ReturnRegister> getAll() {
		return returnRegisterRepository.findAll();
	}
	
	public ReturnRegister getById(int id) throws ResourceNotFoundException {
		Optional<ReturnRegister> optional = returnRegisterRepository.findById(id);
		
		if (optional.isEmpty()) {
			throw new ResourceNotFoundException("Invalid ID given");
		}
		
		return optional.get();
	}
	
	public void delete(int id) {
		returnRegisterRepository.deleteById(id);
	}
	
	public boolean checkQuantity(int productId, int quantityPuchased) {
		ReturnRegister returnRegister = returnRegisterRepository.checkQuantity(productId, quantityPuchased);
		
		if(returnRegister == null) {
			return false;
		}
		
		return true;
	}
	
}