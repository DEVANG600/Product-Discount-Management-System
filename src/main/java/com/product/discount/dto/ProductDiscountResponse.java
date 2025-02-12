package com.product.discount.dto;

import lombok.Data;

@Data
public class ProductDiscountResponse {
	
	private Long productId;

	private String discountType;

	private Double discountValue;

	private Boolean seasonalDiscountActive;

	private Double productPrice;

	private Integer quantity;
	
	private Double totalDiscountedProductPrice;
}
