package com.jhxapi.spring.boot.janitor.entity;

import java.util.Set;

public class Path {

	private Set<String> interceptor;

	private Set<String> exclude;

	public Set<String> getInterceptor() {
		return interceptor;
	}

	public void setInterceptor(Set<String> interceptor) {
		this.interceptor = interceptor;
	}

	public Set<String> getExclude() {
		return exclude;
	}

	public void setExclude(Set<String> exclude) {
		this.exclude = exclude;
	}

}
