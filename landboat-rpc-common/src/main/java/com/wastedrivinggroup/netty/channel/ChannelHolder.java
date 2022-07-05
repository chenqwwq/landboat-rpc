package com.wastedrivinggroup.netty.channel;

import io.netty.channel.Channel;

/**
 * Channel 的持有这
 *
 * @author 沽酒
 * @since 2021/6/21
 **/
public interface ChannelHolder<T> {

	/**
	 * 获取一个 Channel
	 *
	 * @param key Channel特征,服务名称
	 * @return {@link Channel}
	 */
	Channel get(T key);

	/**
	 * 新增Channel
	 *
	 * @param key     键
	 * @param channel {@link Channel]}
	 */
	void add(T key, Channel channel);

	/**
	 * 删除 Channel
	 *
	 * @param key 键
	 * @return 被删除的 Channel
	 */
	Channel remove(T key);
}
