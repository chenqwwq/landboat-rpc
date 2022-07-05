package com.wastedrivinggroup.provider.service.iface;

import java.util.Map;

/**
 * Function Loader
 *
 * @author 沽酒
 * @since 2021/7/21
 **/
@FunctionalInterface
public interface IFunctionLoader<V> {
    /**
     * 加载 Function
     * <p>
     * 使用 Class 作为服务加载对象,可能包含太多的创建流程
     *
     * @param target 从该实例中加载服务
     * @return 返回所有加载的服务
     */
    <C> Map<String, V> loadFunc(C target);
}
