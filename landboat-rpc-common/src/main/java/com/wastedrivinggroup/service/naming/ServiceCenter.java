package com.wastedrivinggroup.service.naming;

import com.google.common.collect.Sets;
import com.wastedrivinggroup.service.ServiceListener;
import com.wastedrivinggroup.service.pojo.ServiceEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务中心
 * <p>
 * 在消费者端,提供 <ServiceName,{@link ServiceEndpoint} 集合 > 的隐射关系
 * <p>
 * 统一的管理所有的服务端内容，方便统一的更新以及监听
 *
 * @author chen
 * @date 2021/6/19
 **/
@Slf4j
public class ServiceCenter {
	private static final Map<String, Set<ServiceEndpoint>> DATA;

	private static final List<ServiceListener> LISTENERS;

	static {
		DATA = new ConcurrentHashMap<>();
		LISTENERS = new ArrayList<>();
	}

	public static Set<ServiceEndpoint> getEndpointList(String serviceName) {
		return DATA.get(serviceName);
	}

	public static void addEndpoint(String serviceName, Set<ServiceEndpoint> endpoints) {
		synchronized (DATA) {
			final Set<ServiceEndpoint> orDefault = DATA.getOrDefault(serviceName, Sets.newHashSet());
			orDefault.addAll(endpoints);
			DATA.put(serviceName, orDefault);
		}
	}

	public static void addEndpoint(String serviceName, ServiceEndpoint endpoint) {
		synchronized (DATA) {
			final Set<ServiceEndpoint> orDefault = DATA.getOrDefault(serviceName, Sets.newHashSet());
			orDefault.add(endpoint);
			DATA.put(serviceName, orDefault);
		}
	}
}
