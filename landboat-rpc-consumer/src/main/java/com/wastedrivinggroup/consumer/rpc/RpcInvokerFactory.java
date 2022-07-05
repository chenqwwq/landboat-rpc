package com.wastedrivinggroup.consumer.rpc;

import java.lang.reflect.Method;

/**
 * {@link RpcInvoker} 的创建工厂
 *
 * @author 沽酒
 * @since 2021/7/13
 **/
@FunctionalInterface
public interface RpcInvokerFactory {

    /**
     * 创建 {@link RpcInvoker}
     *
     * @param method 对应的方法
     * @return 具体的调用实现类
     */
    RpcInvoker getInvoke(Method method, InvokeProxy proxy);

}
