package com.wastedrivinggroup.provider.service;

import com.wastedrivinggroup.annotation.SingleObject;
import com.wastedrivinggroup.exception.ServiceNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务字典
 * <p>
 * 保存功能名称到真实方法的映射关系
 * <p>
 * 持有 {@link FunctionLoader} 的引用，可以用于加载服务
 *
 * @author chen
 * @date 2021/6/15
 **/
@Slf4j
@SingleObject
public class FunctionDict implements FunctionLoader<ReflectFunction> {

    /**
     * 服务名称到具体执行方法的隐射关系
     */
    private final ConcurrentHashMap<String, ReflectFunction> services;

    /**
     * 服务加载器
     * <p>
     * {@link SimpleFunctionLoader}
     */
    private final FunctionLoader<ReflectFunction> functionLoader;

    public FunctionDict() {
        if (log.isInfoEnabled()) {
            log.info("initialize ServiceHolder success");
        }
        services = new ConcurrentHashMap<>();
        functionLoader = new SimpleFunctionLoader();
    }

    public static FunctionDict getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public ReflectFunction findService(String serviceName) {
        return services.get(serviceName);
    }

    @Override
    public Map<String, ReflectFunction> loadService(Object target) {
        final Map<String, ReflectFunction> map = functionLoader.loadService(target);
        services.putAll(map);
        return map;
    }

    public static Object invoke(String name, Object[] args) throws InvocationTargetException, IllegalAccessException {
        final FunctionDict holder = FunctionDict.getInstance();
        final ReflectFunction func = holder.findService(name);
        if (Objects.isNull(func)) {
            throw new ServiceNotFoundException(name);
        }

        return func.getHandler().handle(func,args);
    }

    /**
     * 静态内部类实现单例
     */
    private static final class InstanceHolder {

        private static final FunctionDict INSTANCE = new FunctionDict();

    }

}
