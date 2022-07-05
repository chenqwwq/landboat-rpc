package com.wastedrivinggroup.netty;

/**
 * 服务启动接口
 *
 * @author 沽酒
 * @since 2021/4/16
 **/
public interface RpcBootstrap {
    /**
     * 服务启动
     */
    void start() throws InterruptedException;
}
