package com.jhxapi.web.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultConfig {
	
	@Resource
	private DataSource dataSource;
	
}
