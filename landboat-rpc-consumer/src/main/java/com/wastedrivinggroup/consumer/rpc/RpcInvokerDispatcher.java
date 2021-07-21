package com.wastedrivinggroup.consumer.rpc;

import com.google.gson.Gson;
import com.wastedrivinggroup.netty.proto.demo.InvokeReqProto;
import com.wastedrivinggroup.pojo.InvokeResponse;
import com.wastedrivinggroup.pojo.Request;
import com.wastedrivinggroup.pojo.RpcRequest;
import com.wastedrivinggroup.service.ExceptionHandler;
import com.wastedrivinggroup.service.Func;
import com.wastedrivinggroup.service.RpcInvoker;
import com.wastedrivinggroup.service.Service;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
@Slf4j
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

	/**
	 * 异常处理器
	 */
	private final ExceptionHandler exceptionHandler;


	public RpcInvokerDispatcher(Class<?> clazz) throws InstantiationException, IllegalAccessException {
		this(DefaultRpcInvoker::new, clazz);
	}

	public RpcInvokerDispatcher(RpcInvokerFactory factory, Class<?> clazz) throws InstantiationException, IllegalAccessException {
		if (!clazz.isInterface()) {
			throw new IllegalArgumentException("initial RpcInvokerDispatcher failure,must be interface");
		}
		final Service annotation = clazz.getAnnotation(Service.class);
		if (annotation == null) {
			throw new IllegalArgumentException("Can't found annotation in the interface,[interface name:{" + clazz.getSimpleName() + "}]");
		}
		final String value = annotation.value();
		if (StringUtils.isNoneBlank(value)) {
			throw new IllegalArgumentException("require value in consumer annotation");
		}
		this.name = value;
		this.exceptionHandler = annotation.exceptionHandler().newInstance();
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

		public DefaultRpcInvoker(Method method) {
			this.gson = new Gson();
			this.method = method;
			final Func annotation = method.getAnnotation(Func.class);
			this.functionName = annotation.value();

		}

		private InvokeReqProto wrapInvokeReq(Object[] args) {
			return new InvokeReqProto()
					.setInvokeId(1L)
					.setFunc(functionName)
					.setArgs(args);
		}

		@Override
		public InvokeResponse invoke(Request request) throws Exception {
			if (!(request instanceof RpcRequest)) {
				throw new IllegalArgumentException("Request And Invoker not match");
			}
			RpcRequest rpc = (RpcRequest) request;

			final InvokeReqProto invokeReq = wrapInvokeReq(rpc.getArgs());

			final Channel channel = Channels.getInstance().syncGet(rpc.getEndpoint());

			channel.writeAndFlush(invokeReq);

			// TODO: 异步转同步的方案可以重新设计以下,例如可以使用和 Dubbo 一样 CompletableFuture
			return gson.fromJson(ResponseBuffer.getResp(invokeReq.getInvokeId()), method.getGenericReturnType());
		}
	}

	public String getName() {
		return name;
	}
}
