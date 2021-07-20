package com.wastedrivinggroup.naming;

import java.lang.reflect.Method;

/**
 * 二级服务名称的创建者
 *
 * @author chen
 * @date 2021/6/16
 **/
public class SimpleServiceNameBuilder {

	public static String buildServiceName(Method method) {
		return buildServiceName(method.getDeclaringClass().getSimpleName(), method);
	}

	public static String buildServiceName(String className, Method method) {
		assert method != null;
		final Class<?>[] parameterTypes = method.getParameterTypes();
		StringBuilder ans = new StringBuilder();
		ans.append(className)
				.append(":")
				.append(method.getName());
		for (Class<?> clazz : parameterTypes) {
			ans.append(":").append(clazz.getSimpleName());
		}
		return ans.toString();
	}
}
