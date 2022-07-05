package com.wastedrivinggroup.consumer.rpc;

import com.wastedrivinggroup.consumer.BaseEndpoint;
import com.wastedrivinggroup.pojo.Request;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 沽酒
 * @since 2021/7/20
 **/
@Data
@Accessors(chain = true)
public class SimpleRpcRequest implements Request {

    /**
     * 调用目标地址
     */
    private Channel channel;

    /**
     * 调用参数
     */
    private Object[] args;
}
