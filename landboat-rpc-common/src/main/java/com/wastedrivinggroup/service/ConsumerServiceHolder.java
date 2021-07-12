package com.wastedrivinggroup.service;

import com.google.common.collect.Lists;
import com.wastedrivinggroup.service.pojo.ServiceEndpoint;
import com.wastedrivinggroup.service.pojo.ServiceInfo;

/**
 * @author chen
 * @date 2021/7/11
 **/
public class ConsumerServiceHolder extends AbstractServiceHolder<ServiceInfo> {

	private static final ConsumerServiceHolder INSTANCE = new ConsumerServiceHolder();

	private ConsumerServiceHolder() {
		super();
	}

	public void addService(String name, ServiceEndpoint endpoint) {
		final ServiceInfo serviceInfo = new ServiceInfo(name, Lists.newArrayList(endpoint));
	}

	@Override
	public void addService(String name, ServiceInfo service) {
		super.addService(name, service);
	}

	@Override
	public ServiceInfo getService(String name) {
		return super.getService(name);
	}
}
