package Netty.HeartBeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 15:27 2022/5/12
 */
public class NettyServerHandler extends SimpleChannelInboundHandler {
    private int lossConnectCount = 0;

    /**
     * 当IdleStateEvent触发后,就会进入该userEventTriggered事件
     * 这里设定了一个参数lossConnectCount，当读空闲的次数超过lossConnectCount后，就会判定
     * 该通道是不活跃的，可以关闭了。
     * @param ctx
     * @param evt
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        System.out.println("服务器已经5秒没有收到" + ctx.channel().remoteAddress() + "的消息了");
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if(event.state() == IdleState.READER_IDLE){
                lossConnectCount++;
                if(lossConnectCount > 2){
                    System.out.println("关闭不活跃通道" + ctx.channel().remoteAddress() + "!!!");
                    ctx.channel().close();
                }
            }
        }
    }

    /**
     * 当发生一次读事件，将lossConnectCount置0
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        lossConnectCount = 0;
        System.out.println(ctx.channel().remoteAddress() + "发送消息: " + msg.toString().trim());
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) {

    }
}
