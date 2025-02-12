package com.product.discount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.discount.entity.ProductDiscount;

@Repository
public interface ProductDiscountRepository extends JpaRepository<ProductDiscount, Long> {
	
	@Query(value="select * from product_discount where product_id = :productId",nativeQuery=true)
	ProductDiscount findByProductId(@Param(value = "productId") Long productId);

	@Query(value="select count(*) > 0 from product_discount WHERE product_id =:productId",nativeQuery=true)
	boolean existsByProductId(@Param(value = "productId") Long productId);

}
