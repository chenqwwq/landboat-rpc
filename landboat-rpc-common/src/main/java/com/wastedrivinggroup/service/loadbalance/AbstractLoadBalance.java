package com.wastedrivinggroup.service.loadbalance;

import com.wastedrivinggroup.netty.channel.AbstractMappingChannelHolder;
import com.wastedrivinggroup.service.pojo.ServiceEndpoint;
import io.netty.channel.Channel;

/**
 * @author chen
 * @date 2021-06-23
 **/
public class AbstractLoadBalance extends AbstractMappingChannelHolder<ServiceEndpoint> implements LoadBalance<Channel> {
	@Override
	public Channel choose() {
		return null;
	}
}
