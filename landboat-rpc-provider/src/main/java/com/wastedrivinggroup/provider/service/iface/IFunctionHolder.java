package com.wastedrivinggroup.provider.service.iface;

/**
 * @author chenqwwq
 * @date 2022/5/30
 **/
public interface IFunctionHolder {

    /**
     * 根据服务名称查找
     *
     * @param name 服务名称
     * @return {@link IFunction}
     */
    IFunction findFunc(String name);
}
