package com.wastedrivinggroup.provider.service;


import com.wastedrivinggroup.exception.ServiceNotFoundException;
import com.wastedrivinggroup.provider.pojp.ReflectFunction;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * 服务调用代理,主要处理服务的各类调用情景
 * {@link com.wastedrivinggroup.provider.netty.handle.MethodInvokeHandler}
 *
 * @author chen
 * @date 2021/6/15
 **/
public class FunctionDelegate {

	public static Object invoke(String serviceName, Object[] args) throws InvocationTargetException, IllegalAccessException {
		final FunctionDict holder = FunctionDict.getInstance();
		final ReflectFunction service = holder.findService(serviceName);
		if (Objects.isNull(service)) {
			throw new ServiceNotFoundException(serviceName);
		}

		return service.getMethod().invoke(service.getObject(), args);
	}
}
