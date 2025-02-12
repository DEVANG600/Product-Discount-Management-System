package com.product.discount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.discount.dto.DiscountDTO;
import com.product.discount.service.DiscountService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private DiscountService discountService;
	
	@PostMapping("/discount")
	public ResponseEntity<?> getDiscountedProductPrice(@RequestBody DiscountDTO discountDto) {
		return discountService.getDiscountedProductPrice(discountDto);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<?>  getProductDetail(@PathVariable Long productId) {
		return discountService.getProductDetail(productId);
	}
	
}
