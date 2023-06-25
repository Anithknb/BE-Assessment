package com.springboot.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.main.model.Vendor;
import com.springboot.main.service.VendorService;

@RestController
@RequestMapping("/vendor")
public class VendorController {

	@Autowired
	private VendorService vendorService;

	@PostMapping("/add")
	public ResponseEntity<Vendor> addVendor(@RequestBody Vendor vendor) {
		Vendor createdVendor = vendorService.insert(vendor);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdVendor);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<?> getVendor(@PathVariable("id") int id) {
		Vendor vendor = vendorService.getById(id);
		if (vendor == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor not found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(vendor);
	}

	@PutMapping("update/{id}")
	public ResponseEntity<Vendor> updateVendor(@PathVariable("id") int id, @RequestBody Vendor updatedVendor) {
		Vendor vendor = vendorService.getById(id);
		if (vendor == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		vendor.setName(updatedVendor.getName());
		vendor.setAddress(updatedVendor.getAddress());
		vendor.setCity(updatedVendor.getCity());

		Vendor updatedVendorObj = vendorService.update(vendor);
		return ResponseEntity.status(HttpStatus.OK).body(updatedVendorObj);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteVendor(@PathVariable("id") int id) {
		Vendor vendor = vendorService.getById(id);
		if (vendor == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor not found");
		}
		vendorService.delete(vendor);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
