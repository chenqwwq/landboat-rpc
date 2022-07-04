package com.wastedrivinggroup.provider.service.command;

import com.wastedrivinggroup.provider.service.iface.IFunction;
import com.wastedrivinggroup.provider.service.iface.IFunctionHolder;

import java.util.function.Function;

/**
 * @author chenqwwq
 * @date 2022/5/30
 **/
public interface ICommand<T, R> extends Function<T, R>, IFunction {
}
