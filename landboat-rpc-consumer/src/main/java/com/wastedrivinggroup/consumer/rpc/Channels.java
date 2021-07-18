package com.wastedrivinggroup.consumer.rpc;

import com.wastedrivinggroup.netty.client.BootstrapFactory;
import com.wastedrivinggroup.netty.handler.DebugLogHandler;
import com.wastedrivinggroup.netty.handler.ResponseReceiveHandler;
import com.wastedrivinggroup.netty.proto.decoder.GsonDecoder;
import com.wastedrivinggroup.netty.proto.demo.InvokeRespProto;
import com.wastedrivinggroup.netty.proto.encoder.GsonEncoder;
import com.wastedrivinggroup.service.pojo.ServiceEndpoint;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.pool.AbstractChannelPoolMap;
import io.netty.channel.pool.ChannelPool;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * 保存客户端的 Channel 集合,保存服务到 Channel 的映射
 *
 * @author chen
 * @date 2021/7/12
 **/
@Slf4j
public class Channels extends AbstractChannelPoolMap<ServiceEndpoint, ChannelPool> {

	private static final Channels INSTANCE = new Channels();

	public static Channels getInstance() {
		return INSTANCE;
	}

	private Channels() {

	}

	public static final String CHANNEL_BELONG_ATTRIBUTE_NAME = "channel.belong";

	public static final AttributeKey<ServiceEndpoint> CHANNEL_BELONG_ENDPOINT;

	public static BootstrapFactory bootstrapFactory;

	static {
		try {
			CHANNEL_BELONG_ENDPOINT = AttributeKey.newInstance(CHANNEL_BELONG_ATTRIBUTE_NAME);
		} catch (IllegalArgumentException ex) {
			log.error("channel belong attribute name exist, initial failure, [attribute name:{}]", CHANNEL_BELONG_ATTRIBUTE_NAME);
			throw ex;
		}
		bootstrapFactory = new NioBootstrapFactory();
	}

	@Override
	protected ChannelPool newPool(ServiceEndpoint key) {
		return new FixedChannelPool(bootstrapFactory.getBootstrap(key.getHost(), key.getPort()), new SimpleChannelPoolChannel(key), 2);
	}


	// inner class

	static class SimpleChannelPoolChannel implements ChannelPoolHandler {

		private final ServiceEndpoint serviceEndpoint;

		public SimpleChannelPoolChannel(ServiceEndpoint endpoint) {
			this.serviceEndpoint = endpoint;
		}

		@Override
		public void channelReleased(Channel ch) throws Exception {
			if (log.isInfoEnabled()) {
				log.info("channel released,[host:{},port:{}]", serviceEndpoint.getHost(), serviceEndpoint.getPort());
			}
		}

		@Override
		public void channelAcquired(Channel ch) throws Exception {
			// TODO: 一些属性是应该在 ChannelAcquired 中设置还是在 channelCreated 中设置存疑
			ch.attr(CHANNEL_BELONG_ENDPOINT).compareAndSet(null, this.serviceEndpoint);
			if (log.isInfoEnabled()) {
				log.info("acquired channel success,[host:{},port:{}]", serviceEndpoint.getHost(), serviceEndpoint.getPort());
			}
		}

		@Override
		public void channelCreated(Channel ch) throws Exception {
			ch.pipeline().addLast(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2));
					// LengthFieldPrepender是一个编码器，主要是在响应字节数据前面添加字节长度字段
					ch.pipeline().addLast(new LengthFieldPrepender(2));
					// 对经过粘包和拆包处理之后的数据进行json反序列化，从而得到User对象
					ch.pipeline().addLast(new GsonDecoder<>(InvokeRespProto.class));
					ch.pipeline().addLast(new GsonEncoder());
					ch.pipeline().addLast(new ResponseReceiveHandler());
					// 对响应数据进行编码，主要是将User对象序列化为json
					ch.pipeline().addLast(new DebugLogHandler());
				}
			});
			if (log.isInfoEnabled()) {
				log.info("create channel success,[host:{},port:{}]", serviceEndpoint.getHost(), serviceEndpoint.getPort());
			}
		}
	}
}