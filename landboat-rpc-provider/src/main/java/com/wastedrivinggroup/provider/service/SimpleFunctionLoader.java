package com.wastedrivinggroup.provider.service;

import com.wastedrivinggroup.provider.pojp.ReflectFunction;
import com.wastedrivinggroup.provider.service.annotation.Expose;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author chen
 * @date 2021/7/21
 **/
public class SimpleFunctionLoader implements FunctionLoader<ReflectFunction> {

	private static final Class<Expose> TARGET_ANNOTATION = Expose.class;

	@Override
	public Map<String, ReflectFunction> loadService(Object target) {
		final Class<?> aClass = target.getClass();
		final String className = aClass.getSimpleName();
		// ！！！declaredMethods 就是当前类中所有实现的方法，不包括从父类继承的方法
		final Method[] declaredMethods = aClass.getDeclaredMethods();

		Map<String, ReflectFunction> services = new HashMap<>();
		for (Method method : declaredMethods) {
			if (shouldSkip(method)) {
				continue;
			}
			final Expose annotation = method.getAnnotation(TARGET_ANNOTATION);
			final String funcName = annotation.value();
			services.put(funcName, new ReflectFunction(funcName, method, target));
		}

		return services;
	}


	private boolean shouldSkip(Method method) {
		final String name = method.getName();
		if ("hashCode".equals(name) || "equals".equals(name) || "clone".equals(name)) {
			return true;
		}
		return Objects.isNull(method.getAnnotation(SimpleFunctionLoader.TARGET_ANNOTATION));
	}


}