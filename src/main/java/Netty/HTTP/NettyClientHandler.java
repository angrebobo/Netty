package Netty.HTTP;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 12:32 2022/5/4
 */
public class NettyClientHandler extends SimpleChannelInboundHandler {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush("我是客户端1");
        ctx.writeAndFlush("我是客户端2");
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器回复的消息:" + buf.toString(StandardCharsets.UTF_8));
        System.out.println("服务器的地址： "+ ctx.channel().remoteAddress());
    }
}
