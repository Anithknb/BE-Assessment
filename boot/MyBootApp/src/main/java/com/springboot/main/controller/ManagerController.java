package com.springboot.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.main.model.Manager;
import com.springboot.main.service.ManagerService;

@RestController
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private ManagerService managerService; 
	
	@PostMapping("/add")
	public ResponseEntity<Manager> addManager(@RequestBody Manager manager) {
		Manager createdManager = managerService.insert(manager);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdManager);
	}
	
	@GetMapping("get/{id}")
	public ResponseEntity<?> getManager(@PathVariable("id") int id) {
		Manager manager = managerService.getById(id);
		if (manager == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Manager not found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(manager);
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<Manager> updateManager(@PathVariable("id") int id, @RequestBody Manager updatedManager) {
		Manager manager = managerService.getById(id);
		if (manager == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		manager.setName(updatedManager.getName());
		manager.setAddress(updatedManager.getAddress());
		
		Manager updatedManagerObj = managerService.update(manager);
		return ResponseEntity.status(HttpStatus.OK).body(updatedManagerObj);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteManager(@PathVariable("id") int id) {
		Manager manager = managerService.getById(id);
		if (manager == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Manager not found");
		}
		managerService.delete(manager);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
