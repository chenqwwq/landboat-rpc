package com.wastedrivinggroup.netty.channel;


import io.netty.channel.Channel;

import java.util.Objects;

/**
 * Channel 的持有类
 * TODO: 后续可能提升为接口，改用{@link io.netty.channel.pool.ChannelPool},依旧可以使用该类获取
 *
 * @author 沽酒
 * @since 2021/6/16
 **/
public class SingleChannelHolder implements ChannelHolder<Void> {

	private static final SingleChannelHolder INSTANCE = new SingleChannelHolder();

	private SingleChannelHolder() {
	}

	private Channel channel;

	public static SingleChannelHolder getInstance() {
		return INSTANCE;
	}

	public Channel getChannel() {
		if (Objects.isNull(channel)) {
			throw new RuntimeException("Channel is null");
		}
		return channel;
	}

	@Override
	public Channel get(Void key) {
		if (Objects.isNull(channel)) {
			throw new RuntimeException("Channel is null");
		}
		return channel;
	}

	@Override
	public void add(Void key, Channel channel) {
		this.channel = channel;
	}

	@Override
	public Channel remove(Void key) {
		Channel ans = channel;
		channel = null;
		return ans;
	}
}
