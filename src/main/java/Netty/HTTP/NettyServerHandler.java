package Netty.HTTP;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 16:20 2022/5/3
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpObject msg) {
        System.out.println("msg 类型: " + msg.getClass());
        /*if(msg instanceof HttpRequest){
            System.out.println("msg 类型: " + msg.getClass());
            System.out.println("客户端地址" + ctx.channel().remoteAddress());

            ByteBuf byteBuf = Unpooled.copiedBuffer("Hello! 我是服务器", StandardCharsets.UTF_8);
            FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK, byteBuf);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes()+"");

            ctx.writeAndFlush(httpResponse);
        }*/
    }
}
