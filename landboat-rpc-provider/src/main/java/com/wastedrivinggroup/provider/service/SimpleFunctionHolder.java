package com.wastedrivinggroup.provider.service;

import com.wastedrivinggroup.annotation.SingleObject;
import com.wastedrivinggroup.exception.ServiceNotFoundException;
import com.wastedrivinggroup.provider.service.iface.IFunction;
import com.wastedrivinggroup.provider.service.iface.IFunctionHolder;
import com.wastedrivinggroup.provider.service.iface.IFunctionLoader;
import com.wastedrivinggroup.provider.service.iface.IFunctionRegister;
import com.wastedrivinggroup.provider.service.reflect.ReflectMethodAnnotationFunctionLoader;
import com.wastedrivinggroup.provider.service.reflect.ReflectMethodFunction;
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
 * 持有 {@link IFunctionLoader} 的引用，可以用于加载服务
 *
 * @author chen
 * @date 2021/6/15
 **/
@Slf4j
@SingleObject
public class SimpleFunctionHolder implements IFunctionLoader<ReflectMethodFunction>, IFunctionHolder, IFunctionRegister {

    /**
     * 服务名称到具体执行方法的隐射关系
     */
    private final ConcurrentHashMap<String, AbstractFunction> services;

    /**
     * 服务加载器
     * <p>
     * {@link ReflectMethodAnnotationFunctionLoader}
     */
    private final IFunctionLoader<ReflectMethodFunction> IFunctionLoader;

    public SimpleFunctionHolder() {
        if (log.isInfoEnabled()) {
            log.info("initialize ServiceHolder success");
        }
        services = new ConcurrentHashMap<>();
        IFunctionLoader = new ReflectMethodAnnotationFunctionLoader();
    }


    @Override
    public Map<String, ReflectMethodFunction> loadFunc(Object target) {
        final Map<String, ReflectMethodFunction> map = IFunctionLoader.loadFunc(target);
        services.putAll(map);
        return map;
    }

    public static Object invoke(String name, Object[] args) throws InvocationTargetException, IllegalAccessException {
        final SimpleFunctionHolder holder = SimpleFunctionHolder.getInstance();
        final IFunction func = holder.findFunc(name);
        if (Objects.isNull(func)) {
            throw new ServiceNotFoundException(name);
        }

        return func.getHandler().handle(func, args);
    }

    @Override
    public IFunction findFunc(String name) {
        return services.get(name);
    }

    @Override
    public boolean register(IFunction function) {
        return false;
    }

    /**
     * 静态内部类实现单例
     */
    private static final class InstanceHolder {

        private static final SimpleFunctionHolder INSTANCE = new SimpleFunctionHolder();

    }

    public static SimpleFunctionHolder getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
