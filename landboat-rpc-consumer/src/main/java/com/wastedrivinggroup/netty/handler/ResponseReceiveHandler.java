package com.wastedrivinggroup.netty.handler;

import com.wastedrivinggroup.netty.proto.demo.InvokeRespProto;
import com.wastedrivinggroup.consumer.rpc.ResponseBuffer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 沽酒
 * @since 2021/6/18
 **/
@Slf4j
public class ResponseReceiveHandler extends SimpleChannelInboundHandler<InvokeRespProto> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, InvokeRespProto msg) throws Exception {
		if (log.isInfoEnabled()) {
			log.info("receive a new response,invoke:{}", msg.getInvokeId());
		}
		ResponseBuffer.setResp(msg.getInvokeId(), msg.getRet());
	}
}
