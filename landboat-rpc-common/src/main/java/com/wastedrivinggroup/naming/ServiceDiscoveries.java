package com.wastedrivinggroup.naming;

import com.google.common.collect.Sets;
import com.wastedrivinggroup.annotation.SingleObject;
import com.wastedrivinggroup.naming.consul.ConsulServiceDiscovery;
import com.wastedrivinggroup.pojo.ServiceEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 沽酒
 * @since 2021/6/20
 **/
@Slf4j
@SingleObject
public class ServiceDiscoveries implements DiscoveryPolicy {

	private static final ServiceDiscoveries INSTANCE = new ServiceDiscoveries();

	public static ServiceDiscoveries getInstance() {
		return INSTANCE;
	}

	private static final List<DiscoveryPolicy> discoveries;

	static {
		discoveries = new ArrayList<>();
		discoveries.add(new ConsulServiceDiscovery());
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
