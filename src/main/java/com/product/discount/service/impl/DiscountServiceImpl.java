package com.product.discount.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.discount.constant.DiscountType;
import com.product.discount.dto.DiscountDTO;
import com.product.discount.dto.ProductDiscountResponse;
import com.product.discount.entity.ProductDiscount;
import com.product.discount.repository.ProductDiscountRepository;
import com.product.discount.service.DiscountService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DiscountServiceImpl implements DiscountService {
	
	@Autowired
	private ProductDiscountRepository productRepository;

	@Override
	public ResponseEntity<?>  getDiscountedProductPrice(DiscountDTO discountDto) {
		try {
			if(discountDto!=null) {
				if(discountDto.getQuantity()>0) {
					if(discountDto.getProductId()!=null && discountDto.getProductId()>0) {
						if(discountDto.getDiscountType()!=null && (DiscountType.PERCENTAGE.equals(discountDto.getDiscountType().trim()) || 
								DiscountType.FLAT.equals(discountDto.getDiscountType().trim()))) {
							double totalDiscountedPrice=getDiscountApplyedPrice(discountDto);
							if(totalDiscountedPrice > 0) {
								saveProductDiscountInDB(discountDto,totalDiscountedPrice);
								return new ResponseEntity<>("Total Discounted Price: "+totalDiscountedPrice, HttpStatus.OK);	
							}else {
								return new ResponseEntity<>("The discount exceeds the product price.", HttpStatus.OK);
							}
						}else {
							return new ResponseEntity<>("Invalid Discount Type, Please Enter flat or percentage discount type.", HttpStatus.BAD_REQUEST);
						}
					}else {
						return new ResponseEntity<>("Invalid productId.", HttpStatus.BAD_REQUEST);
					}
				}else {
					return new ResponseEntity<>("The Product is out of stock.", HttpStatus.BAD_REQUEST);
				}
			}else {
				return new ResponseEntity<>("Invalid Request.", HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			log.error("General Error in getDiscountedProductPrice. discountDto :: {}, msg :: {}",discountDto.toString(),e.getMessage());
		}
		return new ResponseEntity<>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void saveProductDiscountInDB(DiscountDTO discountDto, double totalDiscountedPrice) {
		try {
			boolean existProductId=productRepository.existsByProductId(discountDto.getProductId());
			if(existProductId) {
				ProductDiscount product=productRepository.findByProductId(discountDto.getProductId());
				setProductDiscountAndSave(product,discountDto,totalDiscountedPrice);
			}else {
				ProductDiscount product=new ProductDiscount();
				product.setProductId(discountDto.getProductId());
				setProductDiscountAndSave(product,discountDto,totalDiscountedPrice);
			}
		}catch(Exception e) {
			log.error("General Error in saveProductDiscountInDB. discountDto :: {}, totalDiscountedPrice, msg :: {}",discountDto.toString(),totalDiscountedPrice,e.getMessage());
		}
		
	}

	private void setProductDiscountAndSave(ProductDiscount product, DiscountDTO discountDto, double totalDiscountedPrice) {
		try {
			product.setDiscountType(discountDto.getDiscountType());
			product.setDiscountValue(discountDto.getDiscountValue());
			product.setProductPrice(discountDto.getProductPrice());
			product.setQuentity(discountDto.getQuantity());
			product.setSeasonalDiscountActive(discountDto.getSeasonalDiscountActive());
			product.setTotalDiscountedProductPrice(totalDiscountedPrice);
			productRepository.save(product);
		}catch(Exception e) {
			log.error("General Error in setProductDiscountAndSave. discountDto :: {}, totalDiscountedPrice:: {}, msg :: {}",discountDto.toString(),totalDiscountedPrice,e.getMessage());
		}
		
	}

	private double getDiscountApplyedPrice(DiscountDTO discountDto) {
		double totalDiscountedPrice=0;
		try {
			if(discountDto.getProductPrice()!=null && discountDto.getProductPrice()>0) {
				totalDiscountedPrice=discountDto.getProductPrice();
				if (discountDto.getDiscountValue()!=null && discountDto.getDiscountValue() > 0
						&& discountDto.getProductPrice()!=null && discountDto.getProductPrice()>0) {
					if(DiscountType.PERCENTAGE.equals(discountDto.getDiscountType())) {
						totalDiscountedPrice -= discountDto.getProductPrice() * (discountDto.getDiscountValue() / 100);
					}else if(DiscountType.FLAT.equals(discountDto.getDiscountType())) {
						totalDiscountedPrice -= discountDto.getDiscountValue();
					}
		        } 
				if(discountDto.getSeasonalDiscountActive()!=null && discountDto.getSeasonalDiscountActive()) {
					totalDiscountedPrice-=totalDiscountedPrice * 0.25;    // if seasonal discount is true so, applying the 25 % discount
				}
				totalDiscountedPrice=Math.round(totalDiscountedPrice * 10000.0) / 10000.0;
			}
		}catch(Exception e) {
			log.error("General Error in getDiscountApplyedPrice. discountDto :: {}, msg :: {}",discountDto.toString(),e.getMessage());
		}
		return totalDiscountedPrice;
	}

	@Override
	public ResponseEntity<?>  getProductDetail(Long productId) {
		try {
			if(productId!=null && productId > 0) {
				ProductDiscount productDiscount=productRepository.findByProductId(productId);
				if(productDiscount!=null) {
					ProductDiscountResponse response=getProductDiscountResponse(productDiscount);
					return new ResponseEntity<>(response, HttpStatus.OK);
				}else {
					return new ResponseEntity<>("Product id is not found.", HttpStatus.NOT_FOUND);
				}
			}else {
				return new ResponseEntity<>("Invalid productId.", HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			log.error("General Error in getProductDetail. productId :: {}, msg :: {}",productId,e.getMessage());
		}
		return new ResponseEntity<>("Internal Server Error.", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ProductDiscountResponse getProductDiscountResponse(ProductDiscount productDiscount) {
		ProductDiscountResponse response=new ProductDiscountResponse();
		try {
			response.setProductId(productDiscount.getProductId());
			response.setDiscountType(productDiscount.getDiscountType());
			response.setDiscountValue(productDiscount.getDiscountValue());
			response.setProductPrice(productDiscount.getProductPrice());
			response.setQuantity(productDiscount.getQuentity());
			response.setSeasonalDiscountActive(productDiscount.getSeasonalDiscountActive());
			response.setTotalDiscountedProductPrice(productDiscount.getTotalDiscountedProductPrice());
		}catch(Exception e) {
			log.error("General Error in getProductDiscountResponse. productDiscount :: {}, msg :: {}",productDiscount.toString(),e.getMessage());
		}
		return response;
	}

}
