package com.wastedrivinggroup.consumer.rpc;

import com.wastedrivinggroup.pojo.InvokeResponse;
import com.wastedrivinggroup.pojo.Request;

/**
 * 执行具体的 Rpc 调用接口
 * <p>
 *
 * @author chen
 * @date 2021/7/13
 **/
@FunctionalInterface
public interface RpcInvoker {

    /**
     * 是否支持 Request 的请求发送
     *
     * @param request 请求内容 {@link SimpleRpcRequest}
     * @return 是否支持调用
     */
    default boolean support(Request request) {
        return true;
    }

    /**
     * 远程调用方法
     *
     * @param request 远程调用请求，{@link com.wastedrivinggroup.pojo.Request}
     * @return 返回值 {@link InvokeResponse} 返回值
     * @throws Exception 包含如下:
     *                   - InterruptedException 调用中断
     */
    Object invoke(Request request) throws Exception;

}
