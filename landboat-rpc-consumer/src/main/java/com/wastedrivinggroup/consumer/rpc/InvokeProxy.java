package com.wastedrivinggroup.consumer.rpc;

import com.wastedrivinggroup.service.RpcInvoker;
import com.wastedrivinggroup.service.annotation.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * JDK 动态代理所需要的 {@link InvocationHandler}
 * <p>
 * 创建一个 Rpc 接口的代理的时候需要新建一个，不能共用
 * 不共用的原因是隔离一些基本的处理类，类似异常处理等，每个服务可能会有不同的报错信息
 * // TODO: 异常处理可以后续实现
 * <p>
 * 构造函数需要传递一个接口类,在创建的时候可以直接解析接口上的注解,{@link com.wastedrivinggroup.service.annotation.Consumer}
 * 所以一个 InvokeProxy 只对应一个服务
 * // TODO: 注解中可以添加该类的异常处理
 * <p>
 * 每个 {@link InvokeProxy} 包含一个 {@link RpcInvokerDispatcher}
 * 其中包含了每个方法的的调用对象 {@link RpcInvoker}，{@link RpcInvoker} 使用 {@link RpcInvokerFactory} 解析方法产生
 * 采用单个方法不同实际调用类的形式，可以使方法之间相互隔离，可以包含一些额外的功能，比如不同的重试策略等
 *
 * @author chen
 * @date 2021/6/16
 **/
@Slf4j
public class InvokeProxy implements InvocationHandler {

	/**
	 * 方法和具体调用的调度类
	 */
	private final RpcInvokerDispatcher dispatcher;

	/**
	 * 服务名称
	 */
	private final String name;

	private InvokeProxy(Class<?> clazz) {
		this.name = getServiceName(clazz);
		this.dispatcher = new RpcInvokerDispatcher(name, clazz);
	}

	/**
	 * 获取服务名称
	 *
	 * @param T Class 对象
	 * @return 一级服务名称
	 */
	private String getServiceName(Class<?> T) {
		final Consumer annotation = T.getAnnotation(Consumer.class);
		if (annotation == null) {
			throw new IllegalArgumentException("Can't found annotation in the interface,[interface name:{" + T.getSimpleName() + "}]");
		}
		final String value = annotation.value();
		if (StringUtils.isNoneBlank(value)) {
			throw new IllegalArgumentException("require value in consumer annotation");
		}
		return value;
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

	// Create Proxy

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