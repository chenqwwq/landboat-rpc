package com.wastedrivinggroup.client;

import com.wastedrivinggroup.consumer.rpc.InvokeProxy;

/**
 * @author chen
 * @date 2021/5/2
 **/
public class Consumer {

	public static final String serviceName = "echo";

	public static void main(String[] args) throws Exception{
		final EchoServiceApi proxy = InvokeProxy.createProxy(EchoServiceApi.class);
		final String echo = proxy.echo("老子天下无敌");
		System.out.println(echo);
	}
}
