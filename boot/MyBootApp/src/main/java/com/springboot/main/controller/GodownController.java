package com.springboot.main.controller;

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
	@PostMapping("/add/{managerID}")
	public ResponseEntity<?> insertGodown(@PathVariable("managerID") int managerID, 
			@RequestBody Godown godown) {
		//Step 0: validation, if needed for Request body, is done is ProductController in PUT api. 
		
		/* Step 1: Validate and fetch Manager from managerId */
		Manager manager = managerService.getById(managerID);
		if(manager == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid Manager ID"); 
		
		/* Step 2: attach manager to godown object */
			godown.setManager(manager);
			
		/* Step 3: save godown object */
		godown = godownService.insert(godown);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(godown);
		
		
	}
	@GetMapping("get/{managerID}")
	public ResponseEntity<?> getGodown(@PathVariable("id") int id) {
		Godown godown = godownService.getById(id);
		if (godown == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Godown not found"); 
		}
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(godown);
	}
	@PutMapping("/update/{managerID}")
	public ResponseEntity<?> updateGodown(@PathVariable("id") int id, 
			@RequestBody Godown updatedGodown) {
		Godown godown = godownService.getById(id);
		if (godown == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Godown not found"); 
		}
		
		godown.setLocation(updatedGodown.getLocation());
		godown.setCapacityInQuintals(updatedGodown.getCapacityInQuintals());
		godown.setId(updatedGodown.getId());
		
		godown = godownService.update(godown);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(godown);
	}
	
	@DeleteMapping("/delete/{managerID}")
	public ResponseEntity<?> deleteGodown(@PathVariable("id") int id) {
		Godown godown = godownService.getById(id);
		if (godown == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Godown not found"); 
		}
		
		godownService.delete(godown);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body("Godown deleted successfully");
	}
}
