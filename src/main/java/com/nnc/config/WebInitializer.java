package com.nnc.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;


public class WebInitializer implements WebApplicationInitializer{

	public void onStartup(ServletContext container) throws ServletException {
		//
		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.register(InventoryConfig.class);
		//SpringDispatcher
		ServletRegistration.Dynamic dispatcher = container.addServlet("SpringDispatcher", new DispatcherServlet(appContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
		
		CharacterEncodingFilter cef = new CharacterEncodingFilter("UTF-8",true);
		container.addFilter("encodingFilter", cef)
			.addMappingForUrlPatterns(null, false, "/*");
	}
	
}
