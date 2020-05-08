package com.jhxapi.spring.boot.janitor.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器的处理类接口
 * @author Jiang Huaxin
 * @version 1.0.0
 */
public interface InterceptorProcessor {
	
	/**
	 * 处理器的名称
	 * @return
	 */
	String getName();
	
	/**
	 * 处理器的拦截处理方法
	 * @param request 请求体
	 * @param response 响应体
	 * @param handler 
	 * @return true：通过 false:不通过
	 * @throws Exception
	 */
	boolean processor(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception;

}
