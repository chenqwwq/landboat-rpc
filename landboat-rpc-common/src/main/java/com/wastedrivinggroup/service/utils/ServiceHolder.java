package com.wastedrivinggroup.service.utils;

/**
 * 服务的持有者，单例形式
 * <p>
 * 客户端或者服务端本地做缓存
 *
 * @author chen
 * @date 2021-06-18
 **/
public interface ServiceHolder<S> {

	/**
	 * 添加方法映射
	 *
	 * @param service 服务对象
	 * @return 是否添加成功
	 */
	boolean addService(S service);

	/**
	 * 添加方法映射
	 *
	 * @param services 服务对象组
	 */
	default void addService(S[] services) {
		for (S service : services) {
			addService(service);
		}
	}


}
