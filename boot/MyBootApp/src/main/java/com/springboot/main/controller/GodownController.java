package com.springboot.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.exception.ResourceNotFoundException;
import com.springboot.main.model.Godown;
import com.springboot.main.model.Manager;
import com.springboot.main.service.GodownService;
import com.springboot.main.service.ManagerService;

@RestController
@RequestMapping("/godown")
public class GodownController {

	@Autowired
	private GodownService godownService;

	@Autowired
	private ManagerService managerService;

	@PostMapping("/add/{managerId}")
	public ResponseEntity<?> insertGodown(@PathVariable("managerId") int managerId, @RequestBody Godown godown) {
		Manager manager;
		try {
			manager = managerService.getById(managerId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Manager ID");
		}

		godown.setManager(manager);

		godown = godownService.insert(godown);

		return ResponseEntity.status(HttpStatus.OK).body(godown);
	}


	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOne(@PathVariable int id) throws ResourceNotFoundException {
		return ResponseEntity.status(HttpStatus.OK).body(godownService.getById(id));
	}

	@PutMapping("/update/{id}/{managerId}")
	public ResponseEntity<?> update(@PathVariable int id, @PathVariable("managerId") int managerId,
			@RequestBody Godown godown) throws ResourceNotFoundException {
		godownService.getById(id);

		godown.setId(id);
		Manager manager;
		try {
			manager = managerService.getById(managerId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Manager ID");
		}

		godown.setManager(manager);

		godown = godownService.insert(godown);

		return ResponseEntity.status(HttpStatus.OK).body(godown);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) throws ResourceNotFoundException {
		godownService.getById(id);

		godownService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	


}