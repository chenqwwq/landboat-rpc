package com.wastedrivinggroup.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chen
 * @date 2021/7/11
 **/
public class AbstractServiceHolder<S> implements ServiceHolder<S> {

	/**
	 * 服务映射关系的保存
	 */
	private final Map<String, S> SERVICE_MAP;

	public AbstractServiceHolder() {
		this.SERVICE_MAP = new ConcurrentHashMap<>();
	}

	@Override
	public void addService(String name, S service) {
		SERVICE_MAP.put(name, service);
	}

	@Override
	public S getService(String name) {
		return SERVICE_MAP.get(name);
	}
}
