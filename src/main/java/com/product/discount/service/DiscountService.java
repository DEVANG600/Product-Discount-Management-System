package com.product.discount.service;

import org.springframework.http.ResponseEntity;

import com.product.discount.dto.DiscountDTO;

public interface DiscountService {

	ResponseEntity<?> getDiscountedProductPrice(DiscountDTO discountDto);

	ResponseEntity<?> getProductDetail(Long productId);
}
