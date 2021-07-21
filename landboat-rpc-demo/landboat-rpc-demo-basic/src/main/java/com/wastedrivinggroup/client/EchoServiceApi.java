package com.wastedrivinggroup.client;

import com.wastedrivinggroup.service.Func;
import com.wastedrivinggroup.service.Consumer;

/**
 * @author chen
 * @date 2021/6/16
 **/
@Consumer("echo")
public interface EchoServiceApi {

	@Func("EchoService:echo:String")
	String echo(String sentence);
}
