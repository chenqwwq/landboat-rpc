package com.wastedrivinggroup.service.naming;

import com.wastedrivinggroup.service.pojo.ServiceEndpoint;

import java.util.Set;

/**
 * 装饰器模式，实现经过 {@link ServiceCenter} 缓存的服务发现
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
		final Set<ServiceEndpoint> endpoints = ServiceCenter.getEndpointList(serviceName);
		if (endpoints != null && !endpoints.isEmpty()) {
			return endpoints;
		}
		final Set<ServiceEndpoint> ans = discoveryPolicy.discovery(serviceName);
		ServiceCenter.addEndpoint(serviceName, ans);
		return ServiceCenter.getEndpointList(serviceName);
	}
}
