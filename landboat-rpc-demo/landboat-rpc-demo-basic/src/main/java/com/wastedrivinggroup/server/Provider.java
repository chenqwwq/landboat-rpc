package com.wastedrivinggroup.server;

import com.wastedrivinggroup.provider.netty.ProviderBootstrap;
import com.wastedrivinggroup.provider.service.FunctionDict;
import com.wastedrivinggroup.naming.ServiceRegisters;
import lombok.extern.slf4j.Slf4j;

/**
 * 启动主类
 *
 * @author chen
 * @date 2021/5/2
 **/
@Slf4j
public class Provider {

	public static void main(String[] args) throws InterruptedException {
		// 加载服务列表
		FunctionDict.getInstance().loadService(EchoService.class);
		// 启动 Netty 服务端
		ProviderBootstrap server = new ProviderBootstrap();
		server.start();
		// 注册服务
		ServiceRegisters.getInstance().registered("echo");
	}
}
