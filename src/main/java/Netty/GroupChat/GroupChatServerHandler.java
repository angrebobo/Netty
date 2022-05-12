package Netty.GroupChat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: HuangSiBo
 * @Description:
 * channelHandler的生命周期
 * https://zhouze-java.github.io/2019/07/02/Netty-11-channelHandler%E7%9A%84%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F/
 * @Data: Created in 14:54 2022/5/11
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "加入聊天  " + sdf.format(new Date()) + "\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 离开了  " + sdf.format(new Date()) + "\n");
        System.out.println("channelGroup size: " + channelGroup.size());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress() + " 上线了~");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().remoteAddress() + " 离线了~");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel channel = ctx.channel();

        channelGroup.forEach(ch -> {
            if(channel != ch){
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + ": " + msg + "\n");
            }
            else {
                ch.writeAndFlush("[我]" + channel.remoteAddress() + ": " + msg + "\n");
            }
        });
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) {

    }
}
