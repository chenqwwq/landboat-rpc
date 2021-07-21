package com.wastedrivinggroup.provider.service;

import java.util.Map;

/**
 * s
 *
 * @author chen
 * @date 2021/7/21
 **/
@FunctionalInterface
public interface FunctionLoader<V> {
	/**
	 * 加载服务
	 * <p>
	 * 使用 Class 作为服务加载对象,可能包含太多的创建流程
	 *
	 * @param target 从该实例中加载服务
	 * @return 返回所有加载的服务
	 */
	Map<String, V> loadService(Object target);
}
