package com.wastedrivinggroup.consumer.rpc;

import com.google.gson.Gson;
import com.wastedrivinggroup.netty.channel.SingleChannelHolder;
import com.wastedrivinggroup.netty.proto.demo.InvokeReqProto;
import com.wastedrivinggroup.service.RpcInvoker;
import com.wastedrivinggroup.service.RpcInvokerFactory;
import com.wastedrivinggroup.service.naming.utils.SimpleServiceNameBuilder;

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
	private RpcInvokerFactory factory;
	private Class<?> clazz;

	public RpcInvokerDispatcher(Class<?> clazz) {
		this(DefaultRpcInvoker::new, clazz);
	}

	public RpcInvokerDispatcher(RpcInvokerFactory factory, Class<?> clazz) {
		if (!clazz.isInterface()) {
			throw new IllegalArgumentException("initial RpcInvokerDispatcher failure,must be interface");
		}
		this.dispatcher = new ConcurrentHashMap<>();
		this.factory = factory;
		this.clazz = clazz;
	}

	public RpcInvoker getRpcInvoker(Method method) {
		return dispatcher.get(method);
	}

	static class DefaultRpcInvoker implements RpcInvoker {

		private final Gson gson;
		private final Method method;

		public DefaultRpcInvoker(Method method) {
			this.gson = new Gson();
			this.method = method;
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
					.setServiceName(SimpleServiceNameBuilder.buildServiceName(method))
					.setArgs(args);

		}

	}

}
