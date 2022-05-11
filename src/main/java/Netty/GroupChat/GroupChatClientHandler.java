package Netty.GroupChat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 14:36 2022/5/11
 */
public class GroupChatClientHandler extends SimpleChannelInboundHandler {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) {
        System.out.println(msg.toString().trim());
    }
}
