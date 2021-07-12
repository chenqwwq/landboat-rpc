package com.wastedrivinggroup.consumer.rpc;

import com.wastedrivinggroup.netty.client.BootstrapFactory;
import com.wastedrivinggroup.netty.client.DirectionBootstrapFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.SocketAddress;

/**
 * @author chen
 * @date 2021/7/12
 **/
public class NioBootstrapFactory implements DirectionBootstrapFactory {
	@Override
	public Bootstrap getBootstrap(SocketAddress address) {
		return new Bootstrap()
				.channel(NioSocketChannel.class)
				.group(new NioEventLoopGroup())
				.remoteAddress(address);
	}
}
