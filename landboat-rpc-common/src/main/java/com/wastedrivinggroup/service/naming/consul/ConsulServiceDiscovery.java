package com.wastedrivinggroup.service.naming.consul;

import com.google.common.collect.Sets;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.ConsulResponse;
import com.orbitz.consul.model.health.ServiceHealth;
import com.wastedrivinggroup.service.naming.DiscoveryPolicy;
import com.wastedrivinggroup.service.pojo.ServiceEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 基于 {@link com.orbitz.consul.Consul} 的服务发现方法
 *
 * @author chen
 * @date 2021/6/19
 **/
@Slf4j
public class ConsulServiceDiscovery implements DiscoveryPolicy {

	@Override
	public Set<ServiceEndpoint> discovery(String serviceName) {
		if (!ConsulClientHolder.existClient()) {
			throw new RuntimeException("No consul servers have been connected");
		}
		Set<ServiceEndpoint> endpoints = Sets.newHashSet();
		final List<HealthClient> healthClient = ConsulClientHolder.getHealthClient();
		for (HealthClient client : healthClient) {
			final ConsulResponse<List<ServiceHealth>> healthyServiceInstances = client.getHealthyServiceInstances(serviceName);
			final List<ServiceHealth> response = healthyServiceInstances.getResponse();
			if (response.isEmpty()) {
				continue;
			}
			for (ServiceHealth health : response) {
				endpoints.add(ServiceEndpoint.tran2(health.getService()));
			}
			if (log.isInfoEnabled()) {
				log.info("discovery service from consul,serviceName:{},cnt:{}", serviceName, endpoints.size());
			}
		}
		return endpoints;
	}
}
