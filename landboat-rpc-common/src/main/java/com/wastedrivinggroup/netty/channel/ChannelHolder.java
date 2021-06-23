package com.wastedrivinggroup.netty.channel;

import io.netty.channel.Channel;

/**
 * @author chen
 * @date 2021/6/22
 **/
public interface ChannelHolder<K> {

	/**
	 * 增加 Channel
	 *
	 * @param key     Channel 的相关信息
	 * @param channel 建立的Channel
	 */
	void addChannel(K key, Channel channel);

	/**
	 * 删除一个 Channel
	 *
	 * @param key 键
	 */
	void removeChannel(K key);
}
