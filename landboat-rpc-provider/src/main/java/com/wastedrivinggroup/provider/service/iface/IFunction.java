package com.wastedrivinggroup.provider.service.iface;

/**
 * @author 沽酒
 * @since 2022/4/7
 **/
public interface IFunction {

    /**
     * 服务名称
     *
     * @return {@link String} 全局范围内唯一
     */
    String getName();


    /**
     * 获取方法版本
     *
     * @return -1 表示不关注
     */
    default long getVersion() {
        return -1;
    }

    /**
     * 获取方法的执行器
     *
     * @return {@link IFunctionHandler}
     */
    IFunctionHandler getHandler();
}
