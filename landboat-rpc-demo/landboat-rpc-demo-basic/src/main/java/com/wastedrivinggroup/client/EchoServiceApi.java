package com.wastedrivinggroup.client;

import com.wastedrivinggroup.consumer.rpc.RpcClient;
import com.wastedrivinggroup.service.annotation.Consumer;

/**
 * @author chen
 * @date 2021/6/16
 **/
@Consumer("echo")
public interface EchoServiceApi {

	@RpcClient("EchoService:echo:String")
	String echo(String sentence);
}
