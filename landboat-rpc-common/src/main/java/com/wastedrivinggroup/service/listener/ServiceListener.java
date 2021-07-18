package com.wastedrivinggroup.service.listener;

import com.wastedrivinggroup.service.pojo.ServiceEndpoint;

import java.util.Set;

/**
 * 对于 {@link com.wastedrivinggroup.service.naming.ServiceCenter} 的监听器
 *
 * @author chen
 * @date 2021/7/15
 **/
public interface ServiceListener extends Listener {

	/**
	 * 服务新增
	 *
	 * @param name      服务名称
	 * @param endpoints 服务节点集合
	 */
	void serviceAdded(String name, Set<ServiceEndpoint> endpoints);


	/**
	 * 服务剔除
	 *
	 * @param name      服务名称
	 * @param endpoints 服务节点集合
	 */
	void serviceRemoved(String name, Set<ServiceEndpoint> endpoints);

}
