package com.wastedrivinggroup.provider.service;

import com.wastedrivinggroup.provider.service.iface.IFunction;

/**
 * @author 沽酒
 * @since 2022/5/30
 **/
public abstract class AbstractFunction implements IFunction {

    private final String name;

    public AbstractFunction(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

}
