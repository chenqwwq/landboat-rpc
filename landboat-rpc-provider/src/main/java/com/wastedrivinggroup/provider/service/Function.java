package com.wastedrivinggroup.provider.service;

/**
 * @author chenqwwq
 * @date 2022/4/7
 **/
public interface Function {
    /**
     * 获取方法版本
     *
     * @return -1 表示不关注
     */
    default long getVersion() {
        return -1;
    }

    FunctionHandler getHandler();
}
