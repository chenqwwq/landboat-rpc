package com.wastedrivinggroup.provider.service;

import java.lang.reflect.InvocationTargetException;

/**
 * @author chenqwwq
 * @date 2022/4/12
 **/
@FunctionalInterface
public interface FunctionHandler {

    default boolean support(Function function) {
        return true;
    }

     Object handle(Function function, Object[] args) throws InvocationTargetException, IllegalAccessException;
}
