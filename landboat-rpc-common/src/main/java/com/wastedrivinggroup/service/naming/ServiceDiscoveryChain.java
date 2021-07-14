package com.wastedrivinggroup.service.naming;

import com.google.common.collect.Sets;
import com.wastedrivinggroup.service.naming.consul.ConsulServiceDiscovery;
import com.wastedrivinggroup.service.pojo.ServiceEndpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author chen
 * @date 2021/6/20
 **/
public class ServiceDiscoveryChain implements DiscoveryPolicy {

	private static final ServiceDiscoveryChain INSTANCE = new ServiceDiscoveryChain();

	public static ServiceDiscoveryChain getInstance() {
		return INSTANCE;
	}

	private static final List<DiscoveryPolicy> discoveries;

	static {
		discoveries = new ArrayList<>();
		discoveries.add(CacheableServiceDiscovery.wrap(new ConsulServiceDiscovery()));
	}

	public void addDiscoveryPolicy(DiscoveryPolicy policy) {
		discoveries.add(policy);
	}

	@Override
	public Set<ServiceEndpoint> discovery(String serviceName) {
		Set<ServiceEndpoint> endpoints = Sets.newHashSet();
		for (DiscoveryPolicy discoveryPolicy : discoveries) {
			endpoints.addAll(discoveryPolicy.discovery(serviceName));
		}
		return endpoints;
	}
}
