package Netty.HTTP;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 16:20 2022/5/3
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        //HttpServerCodec是netty提供的关于HTTP的编解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        pipeline.addLast( "MyHttpServerHandler", new NettyServerHandler());

    }
}
