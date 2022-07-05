package com.wastedrivinggroup.provider.service.reflect;

import com.wastedrivinggroup.provider.service.AbstractFunction;
import com.wastedrivinggroup.provider.service.iface.IFunctionHandler;

import java.lang.reflect.Method;

/**
 * 服务元数据类
 * <p>
 * // TODO: 可以增加 version 字段
 *
 * @author 沽酒
 * @since 2021/7/21
 **/
public final class ReflectMethodFunction extends AbstractFunction {

    /**
     * 实际方法
     */
    private final Method method;

    /**
     * 实例对象
     * <p>
     * 不同的实例对象可能存在不同的返回方法
     */
    private final Object object;

    public ReflectMethodFunction(String funcName, Method method, Object object) {
        super(funcName);
        this.method = method;
        this.object = object;
    }

    public static ReflectMethodFunction create(String funcName, Method method, Object object) {
        return new ReflectMethodFunction(funcName, method, object);
    }


    public Method getMethod() {
        return method;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public IFunctionHandler getHandler() {
        return new ReflectMethodFunctionHandler();
    }
}
