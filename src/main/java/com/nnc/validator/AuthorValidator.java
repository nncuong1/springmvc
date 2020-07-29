package com.nnc.validator;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.nnc.entity.Author;
import com.nnc.service.AuthorService;

@Component
public class AuthorValidator implements Validator {

	@Autowired
	AuthorService authorService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz == Author.class;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Author author = (Author) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "msg.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "msg.required");
		if (author.getCode() != null) {
			List<Author> results = authorService.findAuthor("code", author.getCode());
			if (results != null && !results.isEmpty()) {
				if (author.getId() != null && author.getId() != 0) {
					if (results.get(0).getId() != author.getId()) {
						errors.rejectValue("code", "msg.code.exist");
					}
				} else {
					errors.rejectValue("code", "msg.code.exist");
				}

			}
		}
	}

}
