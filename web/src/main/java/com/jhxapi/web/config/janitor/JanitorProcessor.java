package com.jhxapi.web.config.janitor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.jhxapi.spring.boot.janitor.processor.InterceptorProcessor;

@Component
public class JanitorProcessor implements InterceptorProcessor{

	@Override
	public boolean processor(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

}
