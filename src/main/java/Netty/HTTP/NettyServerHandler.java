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

    //通道就绪事件
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    //通道读取数据事件
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if(msg instanceof HttpRequest){
            //输出一下ctx能获取到的信息
            System.out.println("服务器读取线程" + Thread.currentThread().getName() + "  channel: " + ctx.channel());
//            System.out.println("msg 类型: " + msg.getClass());
//            System.out.println("客户端地址" + ctx.channel().remoteAddress());
//            System.out.println("channel: " + ctx.channel());
//            System.out.println("pipeline: " + ctx.pipeline());
//            System.out.println("handler: " + ctx.handler());


            ByteBuf buffer = ctx.alloc().buffer();
            buffer.writeBytes("Hello! 我是服务器".getBytes());
            FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK, buffer);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, buffer.readableBytes() + "");

            ctx.writeAndFlush(httpResponse);
        }
    }

    //通道读取完毕事件
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpObject msg) {

    }
}
