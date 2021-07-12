package com.wastedrivinggroup.service;

/**
 * 服务的持有者，单例形式
 * <p>
 * 客户端或者服务端本地做缓存
 * <p>
 * 服务提供方的映射关系是二级服务名 -> 实际调用方法
 * 服务消费方的映射关系是服务名 -> 服务地址
 *
 * @author chen
 * @date 2021-06-18
 **/
public interface ServiceHolder<S> {

	/**
	 * 添加方法映射
	 *
	 * @param name    服务名称
	 * @param service 服务对象
	 */
	void addService(String name, S service);

	/**
	 * 获取服务
	 *
	 * @param name 服务名称
	 * @return 服务
	 */
	S getService(String name);
}
