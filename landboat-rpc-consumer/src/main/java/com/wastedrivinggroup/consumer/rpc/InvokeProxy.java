package com.wastedrivinggroup.consumer.rpc;

import com.wastedrivinggroup.service.RpcInvoker;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * 针对一个单个接口的 {@link InvocationHandler}
 * <p>
 * 构造函数需要传递一个接口类
 *
 * @author chen
 * @date 2021/6/16
 **/
@Slf4j
public class InvokeProxy implements InvocationHandler {

	private RpcInvokerDispatcher dispatcher;

	private InvokeProxy(Class<?> clazz) {
		this.dispatcher = new RpcInvokerDispatcher(clazz);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		final String methodName = method.getName();
		if ("toString".equals(methodName) || "hashCode".equals(methodName) || "equals".equals(methodName)) {
			return method.invoke(proxy, args);
		}

		final RpcInvoker rpcInvoker = dispatcher.getRpcInvoker(method);
		if (Objects.isNull(rpcInvoker)) {
			log.error("rpc invoker not found,[className:{},methodName:{}]", method.getClass().getName(), method.getName());
			throw new IllegalArgumentException("invok failure, rpc invoker not found");
		}
		return rpcInvoker.invoke(args);
	}

	public static <T> T createProxy(Class<T> interfaces) {
		return createProxy(Thread.currentThread().getContextClassLoader(), interfaces);
	}

	@SuppressWarnings("unchecked")
	public static <T> T createProxy(ClassLoader classLoader, Class<T> interfaces) {
		// 使用 JDK 动态代理只能作用于接口
		if (!interfaces.isInterface()) {
			throw new IllegalArgumentException("can only be applied to interface");
		}
		// 直接使用线程上下文类加载器
		return (T) Proxy.newProxyInstance(classLoader, new Class[]{interfaces}, new InvokeProxy(interfaces));
	}

}
