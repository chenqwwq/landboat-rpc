package com.wastedrivinggroup.consumer.rpc;

import com.google.gson.Gson;
import com.wastedrivinggroup.netty.channel.SingleChannelHolder;
import com.wastedrivinggroup.netty.proto.demo.InvokeReqProto;
import com.wastedrivinggroup.service.RpcInvoker;
import com.wastedrivinggroup.service.annotation.Consumer;
import com.wastedrivinggroup.service.naming.ServiceDiscoveryChain;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 保存一个类中的 <{@link Method},{@link RpcInvoker}> 映射
 * <p>
 * 持有 {@link RpcInvokerFactory} 引用，在解析接口时创建 {@link RpcInvoker}
 *
 * @author chen
 * @date 2021/7/13
 **/
public class RpcInvokerDispatcher {
	private final Map<Method, RpcInvoker> dispatcher;
	private final RpcInvokerFactory factory;


	public RpcInvokerDispatcher(Class<?> clazz) {
		this(method -> new DefaultRpcInvoker(method, method.getClass().getSimpleName()), clazz);
	}

	public RpcInvokerDispatcher(RpcInvokerFactory factory, Class<?> clazz) {
		if (!clazz.isInterface()) {
			throw new IllegalArgumentException("initial RpcInvokerDispatcher failure,must be interface");
		}
		this.dispatcher = new ConcurrentHashMap<>();
		this.factory = factory;
	}

	private String getName(Class<?> T) {
		final Consumer annotation = T.getAnnotation(Consumer.class);
		if (annotation == null) {
			throw new IllegalArgumentException("Can't found annotation in the interface,[interface name:{" + T.getSimpleName() + "}]");
		}
		final String[] value = annotation.value();
		if (value.length == 0) {
			throw new IllegalArgumentException("require value in consumer annotation");
		}
		return value[0];
	}

	public RpcInvoker getRpcInvoker(Method method) {
		synchronized (dispatcher) {
			RpcInvoker invoker = dispatcher.getOrDefault(method, factory.getInvoke(method));
			if (dispatcher.containsKey(method)) {
				dispatcher.put(method, invoker);
			}
			return invoker;
		}
	}

	static class DefaultRpcInvoker implements RpcInvoker {

		private final Gson gson;
		private final Method method;
		private final String serviceFullName;

		public DefaultRpcInvoker(Method method, String name) {
			this.gson = new Gson();
			this.method = method;

			StringBuilder sb = new StringBuilder(name);
			sb.append(":").append(method.getName());
			for (Class<?> clazz : method.getParameterTypes()) {
				sb.append(":").append(clazz.getSimpleName());
			}
			serviceFullName = sb.toString();

			ServiceDiscoveryChain.getInstance().discovery(serviceFullName);
		}

		@Override
		public Object invoke(Object[] args) throws InterruptedException {
			// 封装请求
			final InvokeReqProto req = wrapInvokeReq(method, args);
			// 发送请求
			SingleChannelHolder.getInstance().getChannel().writeAndFlush(req);
			// 发送请求后在接收请求前应该阻塞当前线程
			String res = ResponseBuffer.getResp(req.getInvokeId());
			return gson.fromJson(res, method.getGenericReturnType());
		}

		private InvokeReqProto wrapInvokeReq(Method method, Object[] args) {
			return new InvokeReqProto()
					.setInvokeId(1L)
					.setServiceName(serviceFullName)
					.setArgs(args);

		}

	}

}
