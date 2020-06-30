package com.nnc.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import com.nnc.security.CustomerFilter;
import com.nnc.security.FilterSystem;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.nnc")
@PropertySource(value= {"classpath:db.properties"})
@EnableTransactionManagement
public class InventoryConfig implements WebMvcConfigurer {
	
	@Autowired
	Environment enviroment;
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {		
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
		registry.addResourceHandler("/files/**").addResourceLocations("file:G:\\data\\uploadInventory\\");
		//registry.addResourceHandler("/files/**").addResourceLocations("file:"+enviroment.getProperty("upload.servlet.location")+"\\");
	}
	
	@Bean
	public FilterSystem filterSystem() {
		return new FilterSystem();
	}
	@Bean
	public CustomerFilter customerFilter() {
		return new CustomerFilter();
	}
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(filterSystem())
		.addPathPatterns(("/admin/**")).excludePathPatterns("/admin/login","/admin/access-denied","/static/**","/admin/logout","/admin/paging/**","/files/**");
		registry.addInterceptor(customerFilter())
		.addPathPatterns(("/customer/**"));
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource bundleMessageSource = new ReloadableResourceBundleMessageSource();
		bundleMessageSource.setBasename("classpath:messages");
		bundleMessageSource.setDefaultEncoding("utf-8");
		return bundleMessageSource;
	}
	
	@Bean
	public TilesConfigurer tileConfigure() {
		TilesConfigurer configure = new TilesConfigurer();
		configure.setDefinitions("classpath:tiles.xml");
		configure.setCheckRefresh(true);
		return configure;
	}
	
	@Bean
	public ViewResolver viewResolver() {
		TilesViewResolver viewResolver = new TilesViewResolver();
		viewResolver.setOrder(0);
		return viewResolver;
	}
	
	@Bean
	public ViewResolver viewResol() {
		InternalResourceViewResolver vr = new InternalResourceViewResolver();
		vr.setViewClass(JstlView.class);
		vr.setPrefix("/WEB-INF/views/");
		vr.setSuffix(".jsp");
		return vr;
	}
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(enviroment.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(enviroment.getProperty("jdbc.url"));
		dataSource.setUsername(enviroment.getProperty("jdbc.username"));
		dataSource.setPassword(enviroment.getProperty("jdbc.password"));
		return dataSource;
	}
	
	@Bean(name="multipartResolver")
	public MultipartResolver multipartResolver () {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		// khong gioi han dung luong upload
		commonsMultipartResolver.setMaxUploadSize(-1);
		return commonsMultipartResolver;
	}
	
//	@Bean
//	public JdbcTemplate jdbcTemplate() {
//		return new JdbcTemplate(dataSource());
//	}
	
	@Bean(name="sessionFactory")
	public LocalSessionFactoryBean sessionFactoryBean() {
		LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
		bean.setDataSource(dataSource());
		bean.setPackagesToScan("com.nnc.entity");
		
		// khai bao truy van thuoc he csdl nao
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", enviroment.getProperty("hibernate.dialect"));
		hibernateProperties.put("hibernate.show_sql", enviroment.getProperty("hibernate.show_sql"));
		hibernateProperties.put("hibernate.format_sql", enviroment.getProperty("hibernate.format_sql"));
		hibernateProperties.put("hibernate.hbm2ddl.auto", enviroment.getProperty("hibernate.hbm2ddl.auto"));
		bean.setHibernateProperties(hibernateProperties);
		return bean;
	}
	
	@Bean(name="transactionManager")
	@Autowired
	public HibernateTransactionManager hibernateTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
		hibernateTransactionManager.setSessionFactory(sessionFactory);
		
		return hibernateTransactionManager;
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
