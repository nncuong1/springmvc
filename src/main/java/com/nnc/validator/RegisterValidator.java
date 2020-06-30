package com.nnc.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nnc.entity.User;
import com.nnc.service.UserService;

@Component
public class RegisterValidator implements Validator {

	@Autowired
	private UserService userService;

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == User.class;
	}

	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "msg.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "msg.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "msg.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "msg.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "msg.required");
		if(user.getPassword()!=null) {
			if (user.getPassword().length() < 5 || user.getPassword().length() > 32) {
				errors.rejectValue("password", "msg.length.password");
			}
		}
		if (!user.getConfirmPassword().equals(user.getPassword())) {
			errors.rejectValue("confirmPassword", "msg.diff.passwordConfirm");
		}
		if (user.getUsername() != null || !user.getUsername().isEmpty()) {
			if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
				errors.rejectValue("username", "msg.length.username");
			}
			List<User> u = userService.findUserByProperty("username", user.getUsername());
			if (u != null && !u.isEmpty()) {
				errors.rejectValue("username", "msg.username.exist");
			}
		}
		if (user.getEmail() != null) {
			List<User> u = userService.findUserByProperty("email", user.getEmail());
			if (u != null && !u.isEmpty()) {
				errors.rejectValue("username", "msg.email.exist");
			}
		}

	}

}
