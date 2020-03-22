package com.jhxapi.spring.boot.janitor.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器的处理类接口
 * @author Jiang Huaxin
 * @version 1.0.0
 */
public interface InterceptorProcessor {
	
	boolean processor(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception;

}
