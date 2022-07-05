package com.wastedrivinggroup.provider.service.iface;

import com.wastedrivinggroup.provider.service.command.ICommand;

/**
 * @author 沽酒
 * @since 2022/6/1
 **/
public interface IFunctionRegister {
    /**
     * ，命令注册
     *
     * @param func {@link IFunction}
     * @return 是否注册成功
     */
    boolean register(IFunction func);
}
