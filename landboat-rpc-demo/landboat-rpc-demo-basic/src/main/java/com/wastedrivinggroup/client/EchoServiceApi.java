package com.wastedrivinggroup.client;

import com.wastedrivinggroup.consumer.annotation.Func;
import com.wastedrivinggroup.service.Service;

/**
 * @author chen
 * @date 2021/6/16
 **/
@Service("echo")
public interface EchoServiceApi {

    @Func("echo")
    String echo(String sentence);
}
