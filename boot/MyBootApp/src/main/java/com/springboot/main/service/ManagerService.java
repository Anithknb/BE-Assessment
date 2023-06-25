package com.springboot.main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.main.model.Manager;
import com.springboot.main.repository.ManagerRepository;

@Service
public class ManagerService {

	@Autowired
	private ManagerRepository managerRepository;
	
	public Manager insert(Manager manager) {
		return managerRepository.save(manager);
	}

	public Manager getById(int managerId) {
		Optional<Manager> optional = managerRepository.findById(managerId);
		return optional.orElse(null);
	}

	public Manager update(Manager manager) {
		return managerRepository.save(manager);
	}

	public void delete(Manager manager) {
		managerRepository.delete(manager);
	}
}
