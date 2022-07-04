package com.wastedrivinggroup.provider.service.reflect;

import com.wastedrivinggroup.provider.service.annotation.Expose;
import com.wastedrivinggroup.provider.service.iface.IFunctionLoader;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 加载 {@link ReflectMethodFunction } 的服务加载器,解析
 *
 * @author chen
 * @date 2021/7/21
 **/
@Slf4j
public class ReflectMethodAnnotationFunctionLoader implements IFunctionLoader<ReflectMethodFunction> {

    private static final Class<Expose> TARGET_ANNOTATION = Expose.class;

    @Override
    public Map<String, ReflectMethodFunction> loadFunc(Object target) {
        final Class<?> clazz = target.getClass();
        if (clazz.isAssignableFrom(Class.class)) {
            throw new IllegalArgumentException("can't load service in class,maybe should instantiate before");
        }
        if (log.isInfoEnabled()) {
            final String className = clazz.getSimpleName();
            log.info("load service from {}", className);
        }
        // ！！！declaredMethods 就是当前类中所有实现的方法，不包括从父类继承的方法
        final Method[] declaredMethods = clazz.getDeclaredMethods();

        Map<String, ReflectMethodFunction> services = new HashMap<>();
        for (Method method : declaredMethods) {
            if (shouldSkip(method)) {
                continue;
            }
            final Expose annotation = method.getAnnotation(TARGET_ANNOTATION);
            final String funcName = annotation.value();
            services.put(funcName, ReflectMethodFunction.create(funcName, method, target));
        }

        return services;
    }


    private boolean shouldSkip(Method method) {
        final String name = method.getName();
        if ("hashCode".equals(name) || "equals".equals(name) || "clone".equals(name)) {
            return true;
        }
        return Objects.isNull(method.getAnnotation(ReflectMethodAnnotationFunctionLoader.TARGET_ANNOTATION));
    }


}