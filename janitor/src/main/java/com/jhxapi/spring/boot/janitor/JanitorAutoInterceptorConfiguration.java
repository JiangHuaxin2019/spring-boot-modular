package com.jhxapi.spring.boot.janitor;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jhxapi.spring.boot.janitor.entity.Path;
import com.jhxapi.spring.boot.janitor.processor.InterceptorProcessor;
import com.jhxapi.spring.boot.janitor.util.JanitorInterceptorUtils;

@Configuration
@AutoConfigureAfter(JanitorAutoConfiguration.class)
public class JanitorAutoInterceptorConfiguration implements WebMvcConfigurer {

	private final Log log = LogFactory.getLog(JanitorAutoInterceptorConfiguration.class);

	@Autowired
	JanitorProperties properties;

	@Autowired(required = false)
	InterceptorProcessor processor;

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

	}

	@Override
	public void addFormatters(FormatterRegistry registry) {

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if(processor == null) {
			log.error("janitor processor is null");
			return;
		}
		
		JanitorInterceptorUtils util = new JanitorInterceptorUtils();
		if (properties.getPath() == null || !util.isAllPath(properties.getPath().getExclude())) {
			
			Path path = util.mergeInterceptorPath(util.sacnInterceptorAnnotation(properties),properties.getPath());
			
			if (path != null && path.getInterceptor() != null) {
				String[] interceptorPath = path.getInterceptor().toArray(new String[] {});
				if (interceptorPath != null && interceptorPath.length > 0) {
					InterceptorRegistration interceptor = registry.addInterceptor(new JanitorInterceptor(processor));
					interceptor.addPathPatterns(interceptorPath);

					Set<String> excludePath = path.getExclude();
					
					if (excludePath != null && excludePath.size() > 0) {
						interceptor.excludePathPatterns(excludePath.toArray(new String[] {}));
					}
					log.info("Janitor interceptor configuration success");
				} else {
					log.error("Janitor interceptor path is null");
				}
			} else {
				log.error("Janitor interceptor path is null");
			}
		} else {
			log.error("Janitor exclude all path");
		}

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {

	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {

	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

	}

	@Override
	public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {

	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

	}

	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

	}

	@Override
	public Validator getValidator() {
		return null;
	}

	@Override
	public MessageCodesResolver getMessageCodesResolver() {
		return null;
	}

}
