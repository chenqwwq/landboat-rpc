package com.wastedrivinggroup.consumer.rpc;

import com.wastedrivinggroup.netty.proto.demo.InvokeReqProto;
import com.wastedrivinggroup.pojo.InvokeResponse;
import com.wastedrivinggroup.pojo.Request;
import com.wastedrivinggroup.consumer.annotation.Func;
import com.wastedrivinggroup.utils.GsonUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
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

    private final InvokeProxy proxy;

    public RpcInvokerDispatcher(Class<?> clazz, InvokeProxy proxy) throws InstantiationException, IllegalAccessException {
        this(SimpleRpcInvoker::new, clazz, proxy);
    }

    public RpcInvokerDispatcher(RpcInvokerFactory factory, Class<?> clazz, InvokeProxy proxy) throws InstantiationException, IllegalAccessException {
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException("initial RpcInvokerDispatcher failure,must be interface");
        }
        this.proxy = proxy;
        this.dispatcher = new ConcurrentHashMap<>();
        this.factory = factory;
    }

    public RpcInvoker getRpcInvoker(Method method) {
        synchronized (dispatcher) {
            RpcInvoker invoker = dispatcher.getOrDefault(method, factory.getInvoke(method, proxy));
            if (dispatcher.containsKey(method)) {
                dispatcher.put(method, invoker);
            }
            return invoker;
        }
    }

    static class SimpleRpcInvoker implements RpcInvoker {

        private final Method method;
        private final String functionName;
        private final InvokeProxy invokeProxy;

        @Override
        public boolean support(Request request) {
            return request instanceof SimpleRpcRequest;
        }

        public SimpleRpcInvoker(Method method, InvokeProxy proxy) {
            this.invokeProxy = proxy;
            this.method = method;
            final Func annotation = method.getAnnotation(Func.class);
            this.functionName = annotation.value();

        }

        private InvokeReqProto wrapInvokeReq(Object[] args) {
            return new InvokeReqProto().setInvokeId(1L).setFunc(functionName).setArgs(args);
        }

        @Override
        public Object invoke(Request request) throws Exception {
            if (!(request instanceof SimpleRpcRequest)) {
                throw new IllegalArgumentException("Request And Invoker not match");
            }

            SimpleRpcRequest req = (SimpleRpcRequest) request;

            // 获取参数和 Channel
            final InvokeReqProto invokeReq = wrapInvokeReq(req.getArgs());
            final Channel channel = req.getChannel();

            channel.writeAndFlush(invokeReq);

            // TODO: 异步转同步的方案可以重新设计以下,例如可以使用和 Dubbo 一样 CompletableFuture
            String resp = ResponseBuffer.getResp(invokeReq.getInvokeId());
            Type targetType = method.getGenericReturnType();
            return GsonUtils.fromJson(resp, targetType);
        }
    }
}
