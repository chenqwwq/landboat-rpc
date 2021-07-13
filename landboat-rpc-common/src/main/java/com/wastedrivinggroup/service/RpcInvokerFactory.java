package com.wastedrivinggroup.service;

import java.lang.reflect.Method;

/**
 * {@link RpcInvoker} 的创建工厂
 *
 * @author chen
 * @date 2021/7/13
 **/
@FunctionalInterface
public interface RpcInvokerFactory {

	/**
	 * 创建 {@link RpcInvoker}
	 *
	 * @param method 对应的方法
	 * @return 具体的调用实现类
	 */
	RpcInvoker getInvoke(Method method);

}
