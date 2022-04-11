package com.wastedrivinggroup.provider.service;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 * 服务元数据类
 * <p>
 * // TODO: 可以增加 version 字段
 *
 * @author chen
 * @date 2021/7/21
 **/
public final class ReflectFunction implements Function {

    /**
     * 功能名称
     */
    private final String funcName;

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

    public ReflectFunction(String funcName, Method method, Object object) {
        this.funcName = funcName;
        this.method = method;
        this.object = object;
    }

    public static ReflectFunction create(String funcName, Method method, Object object) {
        return new ReflectFunction(funcName, method, object);
    }

    public String getFuncName() {
        return funcName;
    }

    public Method getMethod() {
        return method;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public FunctionHandler getHandler() {
        return new ReflectFunctionHandler();
    }
}
