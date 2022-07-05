package com.wastedrivinggroup.service;

import com.google.common.collect.Lists;
import com.wastedrivinggroup.annotation.NoInstanceObject;
import com.wastedrivinggroup.pojo.ServiceEndpoint;
import com.wastedrivinggroup.naming.ServiceDiscoveries;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务中心
 * <p>
 * 在消费者端,提供 <ServiceName,{@link ServiceEndpoint} 集合 > 的隐射关系
 * <p>
 * 统一的管理所有的服务端内容，方便统一的更新以及监听
 *
 * @author 沽酒
 * @since 2021/6/19
 **/
@Slf4j
@NoInstanceObject
public class ServiceCenter {
	/**
	 * 在构建 {@link com.wastedrivinggroup.loadbalance.LoadBalance} 的时候最好不要重新包装集合,
	 * <p>
	 * 原集合的修改可以直接反应到负载均衡的服务列表
	 */
	private static final Map<String, List<ServiceEndpoint>> DATA;
	private static final List<ServiceListener> LISTENERS;

	static {
		DATA = new ConcurrentHashMap<>();
		LISTENERS = new ArrayList<>();
	}

	public static List<ServiceEndpoint> getEndpointList(String name) {
		return DATA.getOrDefault(name, addEndpoint(name, Lists.newArrayList(ServiceDiscoveries.getInstance().discovery(name))));
	}

	public static List<ServiceEndpoint> addEndpoint(String serviceName, List<ServiceEndpoint> endpoints) {
		synchronized (DATA) {
			final List<ServiceEndpoint> orDefault = DATA.getOrDefault(serviceName, Lists.newArrayList());
			orDefault.addAll(endpoints);
			DATA.put(serviceName, orDefault);
			return orDefault;
		}
	}

	public static List<ServiceEndpoint> addEndpoint(String serviceName, ServiceEndpoint endpoint) {
		synchronized (DATA) {
			final List<ServiceEndpoint> orDefault = DATA.getOrDefault(serviceName, Lists.newArrayList());
			orDefault.add(endpoint);
			DATA.put(serviceName, orDefault);
			return orDefault;
		}
	}
}
