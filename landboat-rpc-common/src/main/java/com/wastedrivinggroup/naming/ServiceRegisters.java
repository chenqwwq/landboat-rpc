package com.wastedrivinggroup.naming;

import com.wastedrivinggroup.annotation.SingleObject;
import com.wastedrivinggroup.naming.consul.ConsulServiceRegister;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 将已有的{@link RegisterPolicy}组成一个链，顺序调用，用来支持向多个注册中心注册
 * <p>
 * 可以在构造函数中通过{@link java.util.ServiceLoader}的SPI机制，完成自定义的注册
 * <p>
 * Haha 过度优化一笔
 *
 * @author 沽酒
 * @since 2021/6/19
 **/
@Slf4j
@SingleObject
public class ServiceRegisters implements RegisterPolicy {
	private static final ServiceRegisters INSTANCE = new ServiceRegisters();
	private static final List<RegisterPolicy> POLICIES;

	static {
		POLICIES = new ArrayList<>();
		POLICIES.add(new ConsulServiceRegister());
	}

	@Override
	public void registered(String serviceName) {
		for (RegisterPolicy node : POLICIES) {
			if (node.isValid()) {
				node.registered(serviceName);
			}
		}
	}

	@Override
	public void disRegistered(String serviceName) {
		for (RegisterPolicy node : POLICIES) {
			if (node.isRegistered(serviceName)) {
				node.disRegistered(serviceName);
			}
		}
	}

	// single instance

	public static ServiceRegisters getInstance() {
		return INSTANCE;
	}

}
