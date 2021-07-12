package com.wastedrivinggroup.netty.client;

import io.netty.bootstrap.Bootstrap;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * 消费者端口的 Bootstrap
 *
 * @author chen
 * @date 2021/7/12
 **/
@FunctionalInterface
public interface BootstrapFactory {

	/**
	 * 获取 Bootstrap
	 *
	 * @param address 服务器地址
	 * @return {@link Bootstrap}
	 */
	Bootstrap getBootstrap(SocketAddress address);


	/**
	 * 获取 Bootstrap
	 *
	 * @param host 服务器主机地址
	 * @param port 服务器IP
	 * @return {@link Bootstrap}
	 */
	default Bootstrap getBootstrap(String host, int port) {
		return getBootstrap(new InetSocketAddress(host, port));
	}
}