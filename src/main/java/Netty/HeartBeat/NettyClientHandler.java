package Netty.HeartBeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Date;

/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 16:45 2022/5/12
 */
public class NettyClientHandler extends SimpleChannelInboundHandler {

    /**
     * 如果4秒内write()方法没有被调用，则触发一次userEventTriggered事件，
     * 在事件中给服务器发送一个消息
     * @param ctx
     * @param evt
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        System.out.println("客户端循环心跳监测发送: "+ new Date());
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state()== IdleState.WRITER_IDLE){
                ctx.writeAndFlush("biubiu");
            }
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress() + "和服务器已断开连接");
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) {

    }
}
