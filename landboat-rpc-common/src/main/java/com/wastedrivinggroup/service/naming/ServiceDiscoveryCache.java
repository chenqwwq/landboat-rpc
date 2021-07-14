package com.wastedrivinggroup.service.naming;

import com.google.common.collect.Sets;
import com.wastedrivinggroup.service.pojo.ServiceEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务的缓存,暂时用 {@link ConcurrentHashMap} 简单实现
 *
 * @author chen
 * @date 2021/6/19
 **/
@Slf4j
public class ServiceDiscoveryCache {
	private static final Map<String, Set<ServiceEndpoint>> cache;

	static {
		cache = new ConcurrentHashMap<>();
	}

	public static Set<ServiceEndpoint> getEndpointList(String serviceName) {
		return cache.get(serviceName);
	}

	public static void addEndpoint(String serviceName, Set<ServiceEndpoint> endpoints) {
		synchronized (cache) {
			final Set<ServiceEndpoint> orDefault = cache.getOrDefault(serviceName, Sets.newHashSet());
			orDefault.addAll(endpoints);
			cache.put(serviceName, orDefault);
		}
	}

	public static void addEndpoint(String serviceName, ServiceEndpoint endpoint) {
		synchronized (cache) {
			final Set<ServiceEndpoint> orDefault = cache.getOrDefault(serviceName, Sets.newHashSet());
			orDefault.add(endpoint);
			cache.put(serviceName, orDefault);
		}
	}
}
