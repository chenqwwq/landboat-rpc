package com.wastedrivinggroup.service.loadbalance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @date 2021/7/19
 **/
public abstract class ServiceList<T> {

	private final List<T> services;

	public ServiceList() {
		services = new ArrayList<>();
	}

	public void add(T t) {
		services.add(t);
	}
}
