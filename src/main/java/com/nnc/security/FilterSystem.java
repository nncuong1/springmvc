package com.nnc.security;

import java.net.URLEncoder;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

import com.nnc.entity.Authority;
import com.nnc.entity.User;
import com.nnc.util.Constant;

public class FilterSystem implements HandlerInterceptor {
	private static final Logger log = Logger.getLogger(FilterSystem.class);
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		User user = (User) request.getSession().getAttribute(Constant.USER_INFOR);
//		String from = URLEncoder.encode(request.getRequestURI(), "UTF-8");
//		if (request.getQueryString() != null) {
//		    from += "?" + request.getQueryString();
//		}
//		log.info("from : "+from);
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/admin/login");
			return false;
		}
		if (user != null) {
			if (user.getRole().getRoleName().equals("member")) {
				response.sendRedirect(request.getContextPath() + "/admin/access-denied");
				return false;
			} else {
				// List<Authority> auths = (List<Authority>)
				// request.getSession().getAttribute(Constant.AUTHS_SESSION);

				Set<Authority> auths = (Set<Authority>) user.getRole().getAuths();
				for(Authority auth : auths) {
					//log.info(auth.getMenu().getId()+" : "+auth.getPermission());
				}
				String url = request.getServletPath();
				if (!hasPermission(url, auths)) {
					log.error("ACCESS DENIED URI ="+request.getRequestURI());
					response.sendRedirect(request.getContextPath() + "/admin/access-denied");
					return false;
				}
			}
		}
		return true;
	}

	private boolean hasPermission(String url, Set<Authority> auths) {
		if (url.contains("/admin/home") || url.contains("/admin/access-denied") || url.contains("/admin/logout")) {
			return true;
		}
		for (Object obj : auths) {
			Authority auth = (Authority) obj;
			if (url.contains(auth.getMenu().getUrl()) && auth.getMenu().getParentId()!=0) {
				//log.info("url : "+url+" : "+auth.getMenu().getId()+", p : "+auth.getPermission());
				return auth.getPermission() == 1;
			}
		}
		return false;
	}
}
