package com.wastedrivinggroup.netty.channel;

import com.wastedrivinggroup.service.pojo.ServiceEndpoint;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chen
 * @date 2021/6/22
 **/
@Slf4j
public class AbstractMappingChannelHolder implements ChannelHolder<ServiceEndpoint> {

	protected static final Map<ServiceEndpoint, Channel> channelCache = new ConcurrentHashMap<>();

	@Override
	public void addChannel(ServiceEndpoint key, Channel channel) {
		channelCache.put(key, channel);
	}

	@Override
	public void removeChannel(ServiceEndpoint key) {
		channelCache.remove(key);
	}
}
