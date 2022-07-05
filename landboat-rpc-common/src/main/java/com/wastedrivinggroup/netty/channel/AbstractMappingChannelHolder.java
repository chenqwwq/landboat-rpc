package com.wastedrivinggroup.netty.channel;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 沽酒
 * @since 2021/6/22
 **/
@Slf4j
public class AbstractMappingChannelHolder<K> implements ChannelHolder<K> {

	protected final Map<K, Channel> channelCache = new ConcurrentHashMap<>();

	@Override
	public Channel get(K key) {
		return channelCache.get(key);
	}

	@Override
	public void add(K key, Channel channel) {
		channelCache.put(key, channel);
	}

	@Override
	public Channel remove(K key) {
		return channelCache.remove(key);
	}
}
