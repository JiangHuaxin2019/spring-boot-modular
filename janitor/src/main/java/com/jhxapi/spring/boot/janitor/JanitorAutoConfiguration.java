package com.jhxapi.spring.boot.janitor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(JanitorProperties.class)
public class JanitorAutoConfiguration {
	
}
