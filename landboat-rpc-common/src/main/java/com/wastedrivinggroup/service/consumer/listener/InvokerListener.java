package com.wastedrivinggroup.service.consumer.listener;

import com.wastedrivinggroup.service.listener.Listener;

import java.lang.reflect.Method;

/**
 * @author chen
 * @date 2021/7/18
 **/
public interface InvokerListener extends Listener {

	/**
	 * 调用之前
	 *
	 * @param name   服务名
	 * @param func   功能名
	 * @param method 远程调用的方法
	 * @param args   调用参数
	 */
	void beforeInvoke(String name, String func, Method method, Object[] args);

	void afterSuccess(String name, String func, Object ret);

	void afterException(String name, String func, Object error);
}
