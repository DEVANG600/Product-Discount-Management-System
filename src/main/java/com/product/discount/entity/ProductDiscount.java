package com.product.discount.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "product_discount")
public class ProductDiscount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="product_id")
	private Long productId;
	
	@Column(name="discount_type")
	private String discountType;
	
	@Column(name="discount_value")
	private Double discountValue;
	
	@Column(name="is_seasonal_discount_active")
	private Boolean seasonalDiscountActive;
	
	@Column(name="product_price")
	private Double productPrice;
	
	@Column(name="quentity")
	private Integer quentity;
	
	@Column(name="total_discounted_product_price")
	private Double totalDiscountedProductPrice;
	
	@Column(name="created_date")
	@CreationTimestamp
	private Date createdDate;
	
	@Column(name="updated_date")
	@UpdateTimestamp
	private Date updatedDate;

}
