package com.springboot.main.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.main.model.InwardRegister;
import com.springboot.main.model.Product;
import com.springboot.main.model.Godown;
import com.springboot.main.model.Supplier;
import com.springboot.main.service.InwardRegisterService;
import com.springboot.main.service.ProductService;
import com.springboot.main.service.GodownService;
import com.springboot.main.service.SupplierService;

@RestController
@RequestMapping("/inwardregister")
public class InwardRegisterController {

	@Autowired
	private InwardRegisterService inwardRegisterService;

	@Autowired
	private ProductService productService;

	@Autowired
	private GodownService godownService;

	@Autowired
	private SupplierService supplierService;

	@PostMapping("/add/{productId}/{godownId}/{supplierId}")
	public ResponseEntity<InwardRegister> addInwardRegister(@RequestBody InwardRegister inwardRegister,
	                                                       @PathVariable("productId") int productId,
	                                                       @PathVariable("godownId") int godownId,
	                                                       @PathVariable("supplierId") int supplierId) {
		inwardRegister.setDateOfSupply(LocalDate.now());

		// Fetch the necessary objects using the provided IDs
		Product product = productService.getById(productId);
		if (product == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		Godown godown = godownService.getById(godownId);
		if (godown == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		Supplier supplier = supplierService.getById(supplierId);
		if (supplier == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		// Set the fetched objects in the inward register
		inwardRegister.setProduct(product);
		inwardRegister.setGodown(godown);
		inwardRegister.setSupplier(supplier);

		InwardRegister createdInwardRegister = inwardRegisterService.insert(inwardRegister);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdInwardRegister);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<?> getInwardRegister(@PathVariable("id") int id) {
		InwardRegister inwardRegister = inwardRegisterService.getById(id);
		if (inwardRegister == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inward Register not found");
		}
		return ResponseEntity.status(HttpStatus.OK).body(inwardRegister);
	}

	@GetMapping("/get/all")
	public ResponseEntity<List<InwardRegister>> getAllInwardRegisters() {
		List<InwardRegister> inwardRegisters = inwardRegisterService.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(inwardRegisters);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<InwardRegister> updateInwardRegister(@PathVariable("id") int id,
	                                                           @RequestBody InwardRegister updatedInwardRegister) {
		InwardRegister inwardRegister = inwardRegisterService.getById(id);
		if (inwardRegister == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		inwardRegister.setQuantity(updatedInwardRegister.getQuantity());
		inwardRegister.setInvoiceNumber(updatedInwardRegister.getInvoiceNumber());
		inwardRegister.setRecieptNo(updatedInwardRegister.getRecieptNo());
		inwardRegister.setDateOfSupply(updatedInwardRegister.getDateOfSupply());

		InwardRegister updatedInwardRegisterObj = inwardRegisterService.update(inwardRegister);
		return ResponseEntity.status(HttpStatus.OK).body(updatedInwardRegisterObj);
	}


	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteInwardRegister(@PathVariable("id") int id) {
		InwardRegister inwardRegister = inwardRegisterService.getById(id);
		if (inwardRegister == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Inward Register not found");
		}
		inwardRegisterService.delete(inwardRegister);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}

