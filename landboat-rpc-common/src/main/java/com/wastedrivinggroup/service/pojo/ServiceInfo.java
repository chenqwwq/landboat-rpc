package com.wastedrivinggroup.service.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author chen
 * @date 2021/7/11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ServiceInfo {

	/**
	 * 服务名称
	 */
	String name;

	/**
	 * 服务列表
	 */
	List<ServiceEndpoint> endpoints;
}
