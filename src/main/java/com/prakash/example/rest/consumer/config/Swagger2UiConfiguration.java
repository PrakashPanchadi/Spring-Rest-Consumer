package com.prakash.example.rest.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Prakash
 *
 */
@Configuration
@EnableSwagger2
public class Swagger2UiConfiguration {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.prakash.example.rest.consumer"))
				.paths(PathSelectors.regex("/api.*")).build().apiInfo(metaInfo());
	}

	private ApiInfo metaInfo() {
		ApiInfo apiInfo = new ApiInfo("Spring Boot Consumer API",
				"API to consume the Users from DWP Rest API and returns list of users who are listed as either living in London or whose current coordinates are within 50 miles of London",
				"1.0", null, new Contact("Prakash Panchadi", null, "prakash.panchadi@hotmail.com"), null, null);
		return apiInfo;
	}

	/*
	 * @Override public void addResourceHandlers(ResourceHandlerRegistry
	 * registry) { //enabling swagger-ui part for visual documentation
	 * registry.addResourceHandler("swagger-ui.html").addResourceLocations(
	 * "classpath:/META-INF/resources/");
	 * registry.addResourceHandler("/webjars/**").addResourceLocations(
	 * "classpath:/META-INF/resources/webjars/"); }
	 */
}