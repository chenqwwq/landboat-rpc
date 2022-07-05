package com.wastedrivinggroup.provider.service.command;

import com.google.common.base.Strings;
import com.wastedrivinggroup.provider.service.AbstractFunction;
import com.wastedrivinggroup.provider.service.iface.IFunctionHandler;

import java.util.function.Function;

/**
 * @author 沽酒
 * @since 2022/5/30
 **/
public abstract class AbstractCommand<T, R> extends AbstractFunction implements ICommand<T, R> {

    public static final String PRE = "CMD:";

    public AbstractCommand(String name) {
        super(PRE + name);
    }

    public static boolean isCmd(String name) {
        if (Strings.isNullOrEmpty(name)) {
            return false;
        }
        return name.startsWith(PRE);
    }

    @Override
    public IFunctionHandler getHandler() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AbstractCommand && getName().equals(((AbstractCommand<?, ?>) obj).getName());
    }
}
