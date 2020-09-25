package com.example.corso.rest.config.front;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//Decido di usare il progetto spring-webmvc con l'annotation @EnableWebMvc
//Sono obbligato a implementare l'interface WebMvcConfigurer e/o estendere
//WebMvcConfigurerAdapter (con Spring >= 5 posso evitare l'Adapter).
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.example.corso.rest.controller")
public class CorsoRestFrontConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// <mvc:resources mapping="/resources/webjars/**" location="/resources/
		// classpath:META-INF/resources/webjars/"/>
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/",
				"classpath:META-INF/resources/webjars/");
		// <mvc:resources mapping="/sandbox*" location="/"/>
		registry.addResourceHandler("/sandbox*").addResourceLocations("/");
	}

}
