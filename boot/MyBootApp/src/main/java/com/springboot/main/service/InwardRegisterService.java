package com.springboot.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.springboot.main.model.InwardRegister;
import com.springboot.main.repository.InwardRegisterRepository;

@Service
public class InwardRegisterService {

	@Autowired
	private InwardRegisterRepository inwardRegisterRepository;

	public InwardRegister insert(InwardRegister inwardRegister) {
		return inwardRegisterRepository.save(inwardRegister);
	}

	public InwardRegister getById(int inwardRegisterId) {
		Optional<InwardRegister> optional = inwardRegisterRepository.findById(inwardRegisterId);
		if (!optional.isPresent()) {
			return null;
		}
		return optional.get();
	}

	public List<InwardRegister> getAll() {
		return inwardRegisterRepository.findAll();
	}

	public InwardRegister update(InwardRegister inwardRegister) {
		return inwardRegisterRepository.save(inwardRegister);
	}

	public void delete(InwardRegister inwardRegister) {
		inwardRegisterRepository.delete(inwardRegister);
	}
}

