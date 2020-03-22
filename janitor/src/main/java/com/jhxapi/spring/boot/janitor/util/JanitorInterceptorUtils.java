package com.jhxapi.spring.boot.janitor.util;

import java.util.LinkedHashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ConfigurationBuilder;

import com.jhxapi.spring.boot.janitor.JanitorProperties;
import com.jhxapi.spring.boot.janitor.annotation.JanitorBannister;
import com.jhxapi.spring.boot.janitor.annotation.JanitorToken;
import com.jhxapi.spring.boot.janitor.entity.Path;

/**
 * Janitor的工具类
 * @author Jiang Huaxin
 *
 */
public class JanitorInterceptorUtils {
	
	private static int PATH_EQUAL = 1;
	
	private static int PATH_WILDCARD_EQUAL = 2;
	
	private static int PATH_NO_EQUAL = -1;

	public Path sacnInterceptorAnnotation(JanitorProperties properties) {
		if(properties == null) {
			return null;
		}
		
		Set<String> packages = properties.getPackages();
		
		if(packages == null || packages.isEmpty()) {
			return null;
		}
		
		ScanJanitorAnnotation sacn = new ScanJanitorAnnotation();
		Reflections reflections = getReflections(packages.toArray(new String[] {}));
		Set<String> bannister = null;
		
		if(properties.getPath() == null || !isAllPath(properties.getPath().getInterceptor())) {
			bannister = sacn.scanInterceptorAnnotation(reflections, JanitorBannister.class);
		}
		
		Set<String> token = sacn.scanInterceptorAnnotation(reflections, JanitorToken.class);
		Path result = new Path();
		result.setInterceptor(bannister);
		result.setExclude(token);
		return result;
	}

	public Path mergeInterceptorPath(Path annotationPath, Path target) {
		Path path = getValidPath(annotationPath, target);
		
		if(path == null) {
			return null;
		}
		//移除无用的路径
		removeExcessPaths(path);
		
		return path;
	}

	private void removeExcessPaths(Path path) {
		Set<String> exclude = removeExcessPaths(path.getInterceptor(),path.getExclude());
		
		path.setExclude(exclude);
	}

	private Path getValidPath(Path annotationPath, Path target) {
		if(annotationPath == null && target == null) {
			return null;
		} else {
			if(annotationPath == null) {
				return target;
			} else if(target == null) {
				return annotationPath;
			} else {
				Path path = new Path();
				Set<String> interceptor = mergeVerificationPath(annotationPath.getInterceptor(),target.getInterceptor());
				Set<String> exclude = mergeVerificationPath(annotationPath.getExclude(),target.getExclude());
				path.setInterceptor(interceptor);
				path.setExclude(exclude);
				return path;
			}
		}
	}

	private Set<String> removeExcessPaths(Set<String> interceptor, Set<String> exclude) {
		Set<String> removeSet = new LinkedHashSet<String>(exclude);
		for(String excludePath : exclude) {
			int status = pathExistInterceptor(excludePath,interceptor);
			if(status == PATH_EQUAL) {
				interceptor.remove(excludePath);
				removeSet.remove(excludePath);
			} else if(status == PATH_NO_EQUAL){
				removeSet.remove(excludePath);
			}
		}
		return removeSet;
	}
	
	private Set<String> mergeVerificationPath(Set<String> data, Set<String> target) {
		LinkedHashSet<String> result = new LinkedHashSet<String>();

		if(target == null || target.isEmpty()) {
			if(data != null) {
				result.addAll(data);
			}
			return result;
		}
		
		result.addAll(target);
		
		if(data != null) {
			for (String path : data) {
				if(pathExistInterceptor(path,result) == PATH_NO_EQUAL) {
					result.add(path);
				}
			}
		}
		return result;
	}

	public static Reflections getReflections(String... packages) {
		ConfigurationBuilder config = new ConfigurationBuilder().forPackages(packages)
				.addScanners(new SubTypesScanner()) 
				.addScanners(new MethodAnnotationsScanner());
		return new Reflections(config);
	}
	
	public boolean isAllPath(Set<String> path) {
		if(path == null) {
			return false;
		}
		return path.contains("/**");
	}
	
	private int pathExistInterceptor(String path, Set<String> interceptor) {
		if(interceptor.contains(path)) {
			return PATH_EQUAL;
		}
		
		int lastIndexOf = path.lastIndexOf("/");
		
		if(lastIndexOf >= 0) {
			String prefix = path.substring(0, lastIndexOf);
			if(interceptor.contains(prefix+"/*") || interceptor.contains(prefix+"/**") || interceptor.contains("/**")) {
				return PATH_WILDCARD_EQUAL;
			}
		}
		
		return PATH_NO_EQUAL;
	}

}
