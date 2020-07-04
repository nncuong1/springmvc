package com.nnc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nnc.entity.User;
import com.nnc.service.UserService;

@Component
public class LoginValidator implements Validator{
	
	@Autowired
	private UserService userService;
	
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz == User.class;
	}

	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "msg.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "msg.required");
		if(!StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getPassword())) {
			User u = userService.findByUserName(user.getUsername());
			if(user!=null && u!=null && u.getRole().getId()!=3 ) {
				if(!u.getPassword().equals(user.getPassword())) {
					errors.rejectValue("password", "msg.wrong.password");
				}
			}else {
				errors.rejectValue("username", "msg.wrong.username");
			}
		}
		
	}

}
