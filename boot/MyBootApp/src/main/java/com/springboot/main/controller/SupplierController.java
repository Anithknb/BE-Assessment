package com.springboot.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.main.model.Supplier;
import com.springboot.main.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	private SupplierService supplierService;

	@PostMapping("/add")
	public ResponseEntity<Supplier> addSupplier(@RequestBody Supplier supplier) {
		Supplier createdSupplier = supplierService.insert(supplier);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdSupplier);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<?> getSupplier(@PathVariable("id") int id) {
		Supplier supplier = supplierService.getById(id);
		if (supplier == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(supplier);
	}

	@PutMapping("update/{id}")
	public ResponseEntity<Supplier> updateSupplier(@PathVariable("id") int id, @RequestBody Supplier updatedSupplier) {
		Supplier supplier = supplierService.getById(id);
		if (supplier == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		supplier.setName(updatedSupplier.getName());
		supplier.setAddress(updatedSupplier.getAddress());
		// ... update other fields as needed

		Supplier updatedSupplierObj = supplierService.update(supplier);
		return ResponseEntity.status(HttpStatus.CREATED).body(updatedSupplierObj);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteSupplier(@PathVariable("id") int id) {
		Supplier supplier = supplierService.getById(id);
		if (supplier == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found");
		}
		supplierService.delete(supplier);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
