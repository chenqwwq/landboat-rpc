package com.wastedrivinggroup.provider.netty.handle;

import com.google.gson.Gson;
import com.wastedrivinggroup.env.InvokeCode;
import com.wastedrivinggroup.exception.RpcException;
import com.wastedrivinggroup.netty.proto.demo.InvokeReqProto;
import com.wastedrivinggroup.netty.proto.demo.InvokeRespProto;
import com.wastedrivinggroup.provider.service.FunctionDelegate;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * 服务调用的处理类
 *
 * @author chen
 * @date 2021/6/16
 **/
@Slf4j
public class MethodInvokeHandler extends SimpleChannelInboundHandler<InvokeReqProto> {

	Gson gson = new Gson();

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, InvokeReqProto msg) throws Exception {
		if (log.isInfoEnabled()) {
			log.info("receive an invoke request,invoke Id:{},serviceName:{},args:{}", msg.getInvokeId(), msg.getFunc(), Arrays.toString(msg.getArgs()));
		}
		// 实际调用得到结果
		InvokeRespProto result;
		try {
			final Object invoke = FunctionDelegate.invoke(msg.getFunc(), msg.getArgs());
			result = InvokeRespProto.ofSuccess(msg.getInvokeId(), gson.toJson(invoke));
		} catch (RpcException rpcException) {
			result = InvokeRespProto.ofFailure(rpcException.getCode(), msg.getInvokeId(), rpcException.getMessage());
		} catch (Exception exception) {
			result = InvokeRespProto.ofFailure(InvokeCode.ErrCode.UNKNOWN_ERR, msg.getInvokeId(), exception.getMessage());
		}
		ctx.writeAndFlush(result);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (log.isWarnEnabled()) {
			log.warn("捕获到未处理异常,throws:{}", cause.getMessage());
		}
		super.exceptionCaught(ctx, cause);
	}
}
