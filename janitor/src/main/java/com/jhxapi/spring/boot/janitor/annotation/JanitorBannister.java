package com.jhxapi.spring.boot.janitor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JanitorBannister {
	
	/**
	 * 是否配置为通配符
	 * @return
	 */
	boolean value() default false;
	
}
