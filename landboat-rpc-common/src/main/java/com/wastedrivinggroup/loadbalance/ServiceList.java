package com.wastedrivinggroup.loadbalance;

import java.util.List;

/**
 * @author 沽酒
 * @since 2021/7/19
 **/
public abstract class ServiceList<T> {

	private final List<T> services;

	public ServiceList(List<T> services) {
		this.services = services;
	}

	public List<T> getServices() {
		return services;
	}

	public int getCnt() {
		return services.size();
	}

	public boolean isEmpty() {
		return services == null || services.isEmpty();
	}
}
