package com.wastedrivinggroup.provider.service.reflect;


import com.wastedrivinggroup.provider.netty.handle.MethodInvokeChannelHandler;
import com.wastedrivinggroup.provider.service.iface.IFunction;
import com.wastedrivinggroup.provider.service.iface.IFunctionHandler;

import java.lang.reflect.InvocationTargetException;

/**
 * 服务调用代理,主要处理服务的各类调用情景
 * {@link MethodInvokeChannelHandler}
 *
 * @author chen
 * @date 2021/6/15
 **/
public class ReflectMethodFunctionHandler implements IFunctionHandler {

    @Override
    public Object handle(IFunction IFunction, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (!(IFunction instanceof ReflectMethodFunction)) {
            throw new IllegalArgumentException("function and function handler not match");
        }
        ReflectMethodFunction func = (ReflectMethodFunction) IFunction;
        return func.getMethod().invoke(func.getObject(), args);
    }

}
