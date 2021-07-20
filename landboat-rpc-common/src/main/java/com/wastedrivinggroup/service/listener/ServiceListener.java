package com.wastedrivinggroup.service.listener;

import com.wastedrivinggroup.pojo.ServiceEndpoint;
import com.wastedrivinggroup.service.ServiceCenter;

import java.util.Set;

/**
 * 对于 {@link ServiceCenter} 的服务变动的监听器
 * <p>
 * 所有类都可以借由监听器感知服务的并作出对应动作
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
