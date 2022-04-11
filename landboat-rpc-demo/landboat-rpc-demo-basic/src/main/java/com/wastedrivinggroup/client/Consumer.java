package com.wastedrivinggroup.client;

import com.wastedrivinggroup.consumer.rpc.InvokeProxy;

/**
 * @author chen
 * @date 2021/5/2
 **/
public class Consumer {

    public static final String serviceName = "echo";

    public static void main(String[] args) throws Exception {

        final EchoServiceApi proxy = InvokeProxy.createProxy(EchoServiceApi.class, "localhost", 8889);
        System.out.println(proxy.echo("1231"));
    }
}
