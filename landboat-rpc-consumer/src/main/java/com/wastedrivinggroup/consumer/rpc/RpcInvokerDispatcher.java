package com.wastedrivinggroup.consumer.rpc;

import com.google.gson.Gson;
import com.wastedrivinggroup.service.ServiceCenter;
import com.wastedrivinggroup.consumer.pojo.ServiceEndpoint;
import com.wastedrivinggroup.netty.proto.demo.InvokeReqProto;
import com.wastedrivinggroup.service.RpcInvoker;
import io.netty.channel.Channel;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * 在 {@link InvokeProxy} 中被持有
 * <p>
 * 一个接口对映的一个 {@link RpcInvokerDispatcher} 类
 * <p>
 * 保存一个类中的 <{@link Method},{@link RpcInvoker}> 映射
 * <p>
 *
 * @author chen
 * @date 2021/7/13
 **/
public class RpcInvokerDispatcher {
	/**
	 * 每个方法对应的调用的实现
	 * <p>
	 * 根据方法上对应的注解实现不同的 {@link RpcInvoker}
	 */
	private final Map<Method, RpcInvoker> dispatcher;

	/**
	 * 创建 {@link RpcInvoker} 的工厂类
	 */
	private final RpcInvokerFactory factory;

	/**
	 * 服务名称
	 */
	private final String name;


	public RpcInvokerDispatcher(String name, Class<?> clazz) {
		this(name, method -> new DefaultRpcInvoker(method, name), clazz);
	}

	public RpcInvokerDispatcher(String name, RpcInvokerFactory factory, Class<?> clazz) {
		this.name = name;
		if (!clazz.isInterface()) {
			throw new IllegalArgumentException("initial RpcInvokerDispatcher failure,must be interface");
		}
		// TODO: 初始化时就发现服务,是否直接建立到服务端的连接
		final List<ServiceEndpoint> endpointList = ServiceCenter.getEndpointList(name);
		// 构造一个 LoadBalance 对象
		this.dispatcher = new ConcurrentHashMap<>();
		this.factory = factory;
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
		private final String functionName;
		private final String serviceName;

		public DefaultRpcInvoker(Method method, String name) {
			this.gson = new Gson();
			this.method = method;
			this.serviceName = name;
			final RpcClient annotation = method.getAnnotation(RpcClient.class);
			this.functionName = annotation.value();

		}

		@Override
		public Object invoke(Object[] args) throws InterruptedException, ExecutionException {
			// 封装请求
			final InvokeReqProto req = wrapInvokeReq(method, args);
			// 发送请求
////			final Channel channel = Channels.getInstance().get(serviceEndpoint).acquire().sync().get();
//			channel.writeAndFlush(req);
			// 发送请求后在接收请求前应该阻塞当前线程
			// TODO: 异步转同步的方案可以重新设计以下,例如可以使用和 Dubbo 一样 CompletableFuture
			String res = ResponseBuffer.getResp(req.getInvokeId());
			return gson.fromJson(res, method.getGenericReturnType());
		}

		private InvokeReqProto wrapInvokeReq(Method method, Object[] args) {
			return new InvokeReqProto()
					.setInvokeId(1L)
					.setServiceName(functionName)
					.setArgs(args);
		}
	}
}
