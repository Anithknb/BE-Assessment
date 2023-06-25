package com.springboot.main.controller;

import java.time.LocalDate;
import java.util.HashMap;
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
import com.springboot.main.model.CustomerProduct;
import com.springboot.main.model.Godown;
import com.springboot.main.model.OutwardRegister;
import com.springboot.main.model.Product;
import com.springboot.main.service.CustomerProductService;
import com.springboot.main.service.GodownService;
import com.springboot.main.service.OutwardRegisterService;
import com.springboot.main.service.ProductService;

@RestController
@RequestMapping("/outwardregister")
public class OutwardRegisterController {

	@Autowired
	private ProductService productService;

	@Autowired
	private GodownService godownService;

	@Autowired
	private OutwardRegisterService outwardRegisterService;
	
	@Autowired
	private CustomerProductService customerProductService;

	@PostMapping("/add/{productId}/{godownId}")
	public ResponseEntity<?> postInwardRegister(@RequestBody OutwardRegister outwardRegister,
			@PathVariable("productId") int productId, @PathVariable("godownId") int godownId) throws ResourceNotFoundException {
		Product product;
		product = productService.getById(productId);
		
		Godown godown;
		godown = godownService.getById(godownId);
		
		outwardRegister.setProduct(product);
		outwardRegister.setGodown(godown);

		outwardRegister.setDateOfDelivery(LocalDate.now());

		outwardRegister = outwardRegisterService.insert(outwardRegister);
		return ResponseEntity.status(HttpStatus.OK).body(outwardRegister);
	}
	
	@GetMapping("/all")
	public List<OutwardRegister> getAll() {
		return outwardRegisterService.getAll();
	}
	
	@GetMapping("/one/{id}")
	public ResponseEntity<?> getOne(@PathVariable int id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(outwardRegisterService.getById(id));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@PutMapping("/update/{id}/{productId}/{godownId}")
	public ResponseEntity<?> update(@PathVariable int id, @PathVariable("productId") int productId, @PathVariable("godownId") int godownId, @RequestBody OutwardRegister outwardRegister) throws ResourceNotFoundException {
		try {
			outwardRegisterService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		
		outwardRegister.setId(id);
		
		Product product;
		product = productService.getById(productId);
		
		Godown godown;
		godown = godownService.getById(godownId);
		
		outwardRegister.setProduct(product);
		outwardRegister.setGodown(godown);

		outwardRegister.setDateOfDelivery(outwardRegister.getDateOfDelivery());

		outwardRegister = outwardRegisterService.insert(outwardRegister);
		return ResponseEntity.status(HttpStatus.OK).body(outwardRegister);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		try {
			outwardRegisterService.getById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(e.getMessage());
		}
		
		outwardRegisterService.delete(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
		
	
	@GetMapping("/report/customer/{customerId}")
	public ResponseEntity<?> outwardReportByCustomer(@PathVariable int customerId) {
		List<CustomerProduct> list;
		try {
			list = customerProductService.getByCustomerId(customerId);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid customer ID given");
		}
		
		HashMap<String, Integer> map = new HashMap<>();
		list.stream().forEach(entry -> {
			if (map.containsKey(entry.getProduct().getTitle())) {
				map.put(entry.getProduct().getTitle(), map.get(entry.getProduct().getTitle()) + entry.getQuantityPurchased());
			}
			else {
				map.put(entry.getProduct().getTitle(), entry.getQuantityPurchased());				
			}
		});
		
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

}