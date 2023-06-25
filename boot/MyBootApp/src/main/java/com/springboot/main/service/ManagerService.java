package com.springboot.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.ResourceNotFoundException;
import com.springboot.main.model.Manager;
import com.springboot.main.repository.ManagerRepository;

@Service
public class ManagerService {

	@Autowired
	private ManagerRepository managerRepository;

	public Manager insert(Manager manager) {
		return managerRepository.save(manager);
	}

	public List<Manager> getAll() {
		return managerRepository.findAll();
	}

	public Manager getById(int id) throws ResourceNotFoundException {
		Optional<Manager> managerFound = managerRepository.findById(id);

		if (managerFound.isEmpty()) {
			throw new ResourceNotFoundException("Invalid ID given");
		}

		return managerFound.get();
	}

	public void delete(int id) {
		managerRepository.deleteById(id);
	}

}