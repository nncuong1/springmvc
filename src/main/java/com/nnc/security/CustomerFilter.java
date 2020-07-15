package com.nnc.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.nnc.entity.User;
import com.nnc.util.Constant;

public class CustomerFilter implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		User user = (User) request.getSession().getAttribute(Constant.CUSTOMER_INFOR);
		if(user==null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}
		String url = request.getServletPath();
		System.out.println("Path la : "+url);
		return true;
	}
}
