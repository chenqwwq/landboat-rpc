package com.wastedrivinggroup.provider.service.iface;

import java.lang.reflect.InvocationTargetException;

/**
 * @author chenqwwq
 * @date 2022/4/12
 **/
@FunctionalInterface
public interface IFunctionHandler {

    /**
     * {@link IFunction} 和当前的 {@link IFunctionHandler} 是否适配
     *
     * @param func 需要判断
     * @return 是否适配，默认 false
     */
    default boolean support(IFunction func) {
        return false;
    }

    /**
     * 真实调用 Function
     *
     * @param func 服务
     * @param args     参数
     * @return 返回值
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    Object handle(IFunction func, Object[] args) throws InvocationTargetException, IllegalAccessException;
}
