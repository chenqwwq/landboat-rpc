package com.wastedrivinggroup.consumer.rpc;

import com.wastedrivinggroup.netty.client.BootstrapFactory;
import com.wastedrivinggroup.netty.client.DirectionBootstrapFactory;
import io.netty.bootstrap.Bootstrap;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chen
 * @date 2021/7/12
 **/
@Slf4j
public class CachedBootstrapFactory implements BootstrapFactory {

	private static final Map<SocketAddress, Bootstrap> BOOTSTRAP_MAP;

	private DirectionBootstrapFactory factory;

	public CachedBootstrapFactory() {
		this.factory = new NioBootstrapFactory();
	}

	public CachedBootstrapFactory(DirectionBootstrapFactory defaultFactory) {
		this.factory = defaultFactory;
	}

	static {
		BOOTSTRAP_MAP = new ConcurrentHashMap<>();
	}

	@Override
	public Bootstrap getBootstrap(SocketAddress address) {
		return BOOTSTRAP_MAP.getOrDefault(address, factory.getBootstrap(address));
	}

	public void setFactory(DirectionBootstrapFactory factory) {
		this.factory = factory;
	}
}
