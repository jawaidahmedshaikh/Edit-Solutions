package com.editsolutions.web.utility;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ConfigPropertiesExposerListener implements ServletContextListener {

	public static final String PROP_BEAN_NAME = "environment";
	
	public static final String CONFIG_PROPERTY = "configProperties";

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
//		ServletContext servletContext = sce.getServletContext();
//		WebApplicationContext context = WebApplicationContextUtils
//				.getRequiredWebApplicationContext(servletContext);
//		ExposablePropertyPaceholderConfigurer configurer = 
//				(ExposablePropertyPaceholderConfigurer) context.getBean(PROP_BEAN_NAME);
//		sce.getServletContext().setAttribute(CONFIG_PROPERTY,
//				configurer.getResolvedProps());
	}

}
