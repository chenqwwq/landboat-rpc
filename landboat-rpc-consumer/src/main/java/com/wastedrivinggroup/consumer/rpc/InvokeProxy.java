package com.wastedrivinggroup.consumer.rpc;

import com.google.common.io.BaseEncoding;
import com.wastedrivinggroup.consumer.BaseEndpoint;
import com.wastedrivinggroup.consumer.Endpoint;
import com.wastedrivinggroup.service.Service;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * JDK 动态代理所需要的 {@link InvocationHandler}
 * <p>
 * 每个接口创建一个 {@link InvokeProxy}
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
     * 调用地址
     */
    private final BaseEndpoint endpoint;

    private InvokeProxy(Class<?> clazz, BaseEndpoint endpoint) throws InstantiationException, IllegalAccessException {
        this.dispatcher = new RpcInvokerDispatcher(clazz, this);
        this.endpoint = endpoint;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final String methodName = method.getName();
        if ("toString".equals(methodName) || "hashCode".equals(methodName) || "equals".equals(methodName)) {
            return method.invoke(proxy, args);
        }

        // 获取执行的方法
        final RpcInvoker rpcInvoker = dispatcher.getRpcInvoker(method);
        if (Objects.isNull(rpcInvoker)) {
            log.error("rpc invoker not found,[className:{},methodName:{}]", method.getClass().getName(), method.getName());
            throw new IllegalArgumentException("invok failure, rpc invoker not found");
        }

        // 封装请求
        SimpleRpcRequest request = new SimpleRpcRequest()
                .setArgs(args)
                .setChannel(getChannel());

        // 调用
        return rpcInvoker.invoke(request);
    }

    protected BaseEndpoint getRemote() {
        return endpoint;
    }

    protected Channel getChannel() throws ExecutionException, InterruptedException {
        return Channels.getInstance().syncGet(endpoint);
    }

    public static <T> T createProxy(Class<T> interfaces, String host, int port) throws Exception {
        return createProxy(interfaces, BaseEndpoint.create(host, port));
    }

    public static <T> T createProxy(Class<T> interfaces, BaseEndpoint endpoint) throws Exception {
        return createProxy(Thread.currentThread().getContextClassLoader(), interfaces, endpoint);
    }

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(ClassLoader classLoader, Class<T> interfaces, BaseEndpoint endpoint) throws Exception {
        // 使用 JDK 动态代理只能作用于接口
        if (!interfaces.isInterface()) {
            throw new IllegalArgumentException("can only be applied to interface");
        }
        // 直接使用线程上下文类加载器
        return (T) Proxy.newProxyInstance(classLoader, new Class[]{interfaces}, new InvokeProxy(interfaces, endpoint));
    }

}
