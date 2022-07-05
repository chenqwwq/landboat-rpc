package com.wastedrivinggroup.server;

import com.wastedrivinggroup.netty.server.config.NettyServerConfig;
import com.wastedrivinggroup.provider.netty.ProviderBootstrap;
import com.wastedrivinggroup.provider.service.SimpleFunctionHolder;
import lombok.extern.slf4j.Slf4j;

/**
 * 启动主类
 *
 * @author 沽酒
 * @since 2021/5/2
 **/
@Slf4j
public class Provider {

    public static void main(String[] args) throws InterruptedException {
        NettyServerConfig.setHost("localhost");
        // 加载服务列表
        SimpleFunctionHolder.getInstance().loadFunc(new EchoService());
        // 启动 Netty 服务端
        ProviderBootstrap server = new ProviderBootstrap();
        server.start();
    }
}
