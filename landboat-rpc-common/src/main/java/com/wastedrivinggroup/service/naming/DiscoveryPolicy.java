package com.wastedrivinggroup.service.naming;

import com.wastedrivinggroup.service.pojo.ServiceEndpoint;

import java.util.Set;

/**
 * @author chen
 * @date 2021-06-18
 **/
public interface DiscoveryPolicy {
	/**
	 * 服务发现
	 *
	 * @param serviceName 服务名称
	 * @return 服务点 {@link ServiceEndpoint}
	 */
	Set<ServiceEndpoint> discovery(String serviceName);
}
