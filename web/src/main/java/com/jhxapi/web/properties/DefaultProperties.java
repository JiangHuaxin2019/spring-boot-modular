package com.jhxapi.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "spring.default")
public class DefaultProperties {
	
	private String name;
	
	private Integer age;
	
}
