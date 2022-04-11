package com.wastedrivinggroup.provider.service;


import com.wastedrivinggroup.exception.ServiceNotFoundException;
import com.wastedrivinggroup.provider.netty.handle.MethodInvokeChannelHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * 服务调用代理,主要处理服务的各类调用情景
 * {@link MethodInvokeChannelHandler}
 *
 * @author chen
 * @date 2021/6/15
 **/
public class ReflectFunctionHandler implements FunctionHandler {


    @Override
    public Object handle(Function function, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (!(function instanceof ReflectFunction)) {
            throw new IllegalArgumentException("function and function handler not match");
        }
        ReflectFunction func = (ReflectFunction) function;
        return func.getMethod().invoke(func.getObject(),args);
    }

}
