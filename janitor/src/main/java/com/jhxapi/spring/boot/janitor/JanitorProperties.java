package com.jhxapi.spring.boot.janitor;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import com.jhxapi.spring.boot.janitor.entity.Path;

/**
 * 
 * @author Jiang Huaxin
 *
 */
@ConfigurationProperties("spring.janitor")
public class JanitorProperties {

	/**
	 * 拦截器的路径相关配置
	 */
	@NestedConfigurationProperty
	private Path path;

	private Set<String> packages;

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Set<String> getPackages() {
		return packages;
	}

	public void setPackages(Set<String> packages) {
		this.packages = packages;
	}

}
