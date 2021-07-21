package com.wastedrivinggroup.client;

import com.wastedrivinggroup.service.Func;
import com.wastedrivinggroup.service.Service;

/**
 * @author chen
 * @date 2021/6/16
 **/
@Service("echo")
public interface EchoServiceApi {

	@Func("EchoService:echo:String")
	String echo(String sentence);
}
