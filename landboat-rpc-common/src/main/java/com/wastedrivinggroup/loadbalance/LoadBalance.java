package com.wastedrivinggroup.loadbalance;

/**
 * 负载均衡顶级接口
 *
 * @author chen
 * @date 2021-06-18
 **/
@FunctionalInterface
public interface LoadBalance<T> {
	/**
	 * 选择一个服务
	 *
	 * @return 服务地址或者 channel
	 */
	T choose();
}
