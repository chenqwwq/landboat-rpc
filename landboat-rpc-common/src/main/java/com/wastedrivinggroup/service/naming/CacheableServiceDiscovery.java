package com.wastedrivinggroup.service.naming;

import com.wastedrivinggroup.service.pojo.ServiceEndpoint;

import java.util.List;
import java.util.Set;

/**
 * 带缓存的服务发现类,采用装饰器模式
 *
 * @author chen
 * @date 2021/6/19
 **/
public class CacheableServiceDiscovery implements DiscoveryPolicy {

	private DiscoveryPolicy discoveryPolicy;

	private CacheableServiceDiscovery(DiscoveryPolicy discoveryPolicy) {
		this.discoveryPolicy = discoveryPolicy;
	}

	public static CacheableServiceDiscovery wrap(DiscoveryPolicy policy) {
		return new CacheableServiceDiscovery(policy);
	}

	@Override
	public Set<ServiceEndpoint> discovery(String serviceName) {
		final Set<ServiceEndpoint> endpoints = ServiceDiscoveryCache.getEndpointList(serviceName);
		if (!endpoints.isEmpty()) {
			return endpoints;
		}
		final Set<ServiceEndpoint> ans = discoveryPolicy.discovery(serviceName);
		ServiceDiscoveryCache.addEndpoint(serviceName, ans);
		return ServiceDiscoveryCache.getEndpointList(serviceName);
	}
}
