package com.jhxapi.spring.boot.janitor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jhxapi.spring.boot.janitor.processor.InterceptorProcessor;

public class JanitorInterceptor implements HandlerInterceptor{
	
	private InterceptorProcessor processor;
	
	public JanitorInterceptor(InterceptorProcessor processor) {
		this.processor = processor;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return processor.processor(request,response,handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
}
