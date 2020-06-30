package com.nnc.validator;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nnc.entity.Product;
import com.nnc.service.ProductService;

@Component
public class ProductValidator implements Validator {

	@Autowired
	private ProductService productService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == Product.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Product product = (Product) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "msg.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "msg.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "isbn", "msg.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "qty", "msg.required");

		// validate Productname
		if (product.getIsbn() != null) {
			List<Product> results = productService.findProductByProperty("isbn", product.getIsbn());
			if (results != null && !results.isEmpty()) {
				if (product.getId() != null) {
					if (results.get(0).getId() != product.getId()) {
						errors.rejectValue("isbn", "msg.isbn.exist");
					}
				} else {
					errors.rejectValue("isbn", "msg.isbn.exist");
				}
			}
		}

		// validate qty
		if (product.getQty() <= 0) {
			errors.rejectValue("qty", "msg.wrong.format");
		}
		// validate price
		if (product.getPrice()!=null) {
			if(product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
				errors.rejectValue("price", "msg.wrong.format");
			}
		}
	}

}
