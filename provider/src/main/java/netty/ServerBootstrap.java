package netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import netty.handler.DebugLogHandler;
import netty.proto.decoder.GsonDecoder;
import netty.proto.encoder.GsonEncoder;
import netty.server.AbstractNettyServerBootstrap;
import netty.server.config.ServerConfig;

/**
 * @author chen
 * @date 2021/4/16
 **/
public class ServerBootstrap extends AbstractNettyServerBootstrap {

    @Override
    protected ServerConfig getConfig() {
        // TODO: 先采用默认的
        return new ServerConfig();
    }

    @Override
    protected ChannelInitializer<NioServerSocketChannel> serverChannelInitializer() {
        return new ChannelInitializer<NioServerSocketChannel>() {
            @Override
            protected void initChannel(NioServerSocketChannel ch) throws Exception {
                ch.pipeline().addLast(new DebugLogHandler());
            }
        };
    }

    @Override
    protected ChannelInitializer<NioSocketChannel> channelInitializer() {
        return new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                // 因为是链表 所以添加的顺序很关键

                // 这里将LengthFieldBasedFrameDecoder添加到pipeline的首位，因为其需要对接收到的数据
                // 进行长度字段解码，这里也会对数据进行粘包和拆包处理
                ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2));
                // LengthFieldPrepender是一个编码器，主要是在响应字节数据前面添加字节长度字段
                ch.pipeline().addLast(new LengthFieldPrepender(2));
                // 对经过粘包和拆包处理之后的数据进行json反序列化，从而得到User对象
                ch.pipeline().addLast(new GsonDecoder());
                // 对响应数据进行编码，主要是将User对象序列化为json
                ch.pipeline().addLast(new GsonEncoder());
                ch.pipeline().addLast(new DebugLogHandler());
            }
        };
    }
}
