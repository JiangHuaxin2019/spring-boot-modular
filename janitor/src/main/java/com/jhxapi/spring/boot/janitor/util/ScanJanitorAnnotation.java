package com.jhxapi.spring.boot.janitor.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Janitor的扫描注解类
 * 
 * @author Jiang Huaxin
 *
 */
public class ScanJanitorAnnotation {
	
	/**
	 * 扫描拦截器注解修饰的对象的请求路径信息(仅适用Spring web)
	 * 
	 * @param reflections 反射工具类，用于扫描对应的类或者方法
	 * @param target      拦截器注解 如JanitorBannister JanitorToken
	 * @return 对目前的请求路径
	 */
	public Set<String> scanInterceptorAnnotation(Reflections reflections, Class<? extends Annotation> target) {
		// 获取指定注解的类或者方法
		Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(target);
		Set<Method> methodSet = reflections.getMethodsAnnotatedWith(target);
		// 处理对应类或者注解,获取拦截路径
		Set<String> classPath = classInterceptorAnnotation(classSet,target);
		Set<String> methodPath = methodInterceptorAnnotation(methodSet,target);
		// 合并拦截目录
		Set<String> result = mergeInterceptorPath(classPath, methodPath);
		return result;
	}

	/**
	 * 用于合并class和method上扫描出来的路径合并
	 * 
	 * @param classPath
	 * @param methodPath
	 * @return 合并或的Set容器
	 */
	private Set<String> mergeInterceptorPath(Set<String> classPath, Set<String> methodPath) {
		HashSet<String> result = new HashSet<String>();
		result.addAll(classPath);
		result.addAll(methodPath);
		return result;
	}

	/**
	 * 根据对应Method的Set集合扫描出包含拦截器需要的路径信息
	 * 
	 * @param methodSet 扫描目标
	 * @return 路径信息的Set容器
	 */
	private Set<String> methodInterceptorAnnotation(Set<Method> methodSet,Class<? extends Annotation> target) {
		Set<String> result = new HashSet<String>();
		// 通过方法获取拦截路径
		for (Method method : methodSet) {

			Class<?> cls = method.getDeclaringClass();

			Controller ca = AnnotatedElementUtils.findMergedAnnotation(cls, Controller.class);
			if (ca == null) {
				continue;
			}

			RequestMapping rma = AnnotatedElementUtils.findMergedAnnotation(cls, RequestMapping.class);
			
			RequestMapping mrqa = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);

			mergePath(rma, mrqa, result);
		}

		return result;
	}

	/**
	 * 根据对应Class的Set集合扫描出包含拦截器需要的路径信息
	 * 
	 * @param classSet 扫描目标
	 * @return 路径信息的Set容器
	 */
	private Set<String> classInterceptorAnnotation(Set<Class<?>> classSet,Class<? extends Annotation> target) {
		Set<String> result = new HashSet<String>();
		for (Class<?> cls : classSet) {
			Controller ca = AnnotatedElementUtils.findMergedAnnotation(cls, Controller.class);
			if (ca == null) {
				continue;
			}

			RequestMapping rma = AnnotatedElementUtils.findMergedAnnotation(cls, RequestMapping.class);
			try {
				Annotation annotation = cls.getAnnotation(target);
				boolean invoke = (boolean) annotation.getClass().getMethod("value").invoke(annotation);
				if(!arrayisEmpty(rma.value()) && invoke) {
					for(String path : rma.value()) {
						result.add("/"+path+"/*");
					}
					continue;
				}
			} catch (Exception e) { }

			for (Method method : cls.getDeclaredMethods()) {

				RequestMapping mrqa = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);

				mergePath(rma, mrqa, result);
			}

		}
		return result;
	}

	/**
	 * 根据对应Spring的web注解添加对应的路径
	 * 
	 * @param crqma   Controller注解修饰的 类(class)上的RequestMapping实例 如果没有则为null
	 * @param mrqma   Controller注解修饰的 类 下的 RequestMapping注解修饰的 方法 上的RequestMapping实例
	 * @param pathSet 添加路径的Set容器
	 */
	private void mergePath(RequestMapping crqma, RequestMapping mrqma, Set<String> pathSet) {

		if (mrqma == null) {
			return;
		}
		String[] mrqmav = mrqma.value();
		String[] crqmav = null;

		if (crqma != null) {
			String[] value = crqma.value();
			if (!arrayisEmpty(value)) {
				crqmav = value;
			}
		}

		if (crqmav == null) {
			crqmav = new String[] { "" };
		}

		for (String crqp : crqmav) {

			if (arrayisEmpty(mrqmav)) {
				pathSet.add("/" + crqp);
			} else {
				for (String path : mrqmav) {
					if (crqp.equals("")) {
						pathSet.add("/" + path);
					} else {
						pathSet.add("/" + crqp + "/" + path);
					}
				}
			}
		}
	}

	/**
	 * 判断数据是否为空
	 * 
	 * @param array 目标数组
	 * @return 空： true 否则为false
	 */
	private boolean arrayisEmpty(Object[] array) {
		return array == null || array.length == 0;
	}

}
