package com.nnc.validator;

import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nnc.entity.User;
import com.nnc.service.UserService;

@Component
public class UserValidator implements Validator {

	@Autowired
	private UserService userService;

	public boolean supports(Class<?> clazz) {
		return clazz == User.class;
	}

	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "msg.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "msg.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "msg.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "msg.required");

		// validate username
		if (user.getUsername() != null) {
			List<User> results = userService.findUserByProperty("username", user.getUsername());
			if (results != null && !results.isEmpty()) {
				if (user.getId() != null) {
					if (results.get(0).getId() != user.getId()) {
						errors.rejectValue("username", "msg.username.exist");
					}
				} else {
					errors.rejectValue("username", "msg.username.exist");
				}
			}
		}

		// validate email
		if (user.getEmail() != null) {
			List<User> results = userService.findUserByProperty("email", user.getEmail());
			if (results != null && !results.isEmpty()) {
				if (user.getId() != null) {
					if (results.get(0).getId() != user.getId()) {
						errors.rejectValue("email", "msg.email.exist");
					}
				} else {
					errors.rejectValue("email", "msg.email.exist");
				}
			}
		}

		// validate file
		if (!user.getMultipartFile().getOriginalFilename().isEmpty()) {
			String extension = FilenameUtils.getExtension(user.getMultipartFile().getOriginalFilename());
			if (!extension.equals("jpg") || !extension.equals("png")) {
				errors.rejectValue("multipartFile", "msg.file.extension.error");
			}
		}
	}
}
