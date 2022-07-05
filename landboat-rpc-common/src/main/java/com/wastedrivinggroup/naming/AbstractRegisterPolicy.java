package com.wastedrivinggroup.naming;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 沽酒
 * @since 2021-06-18
 **/
public abstract class AbstractRegisterPolicy implements RegisterPolicy {

	protected Set<String> registeredService;

	public AbstractRegisterPolicy() {
		registeredService = new HashSet<>();
	}

	@Override
	public boolean isRegistered(String serviceName) {
		return registeredService.contains(serviceName);
	}
}
