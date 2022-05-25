package Netty.GroupChat.server.handler;

import Netty.GroupChat.message.GroupJoinResponseMessage;
import Netty.GroupChat.message.GroupQuitRequestMessage;
import Netty.GroupChat.server.session.Group;
import Netty.GroupChat.server.session.GroupSessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class GroupQuitRequestMessageHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, GroupQuitRequestMessage msg) {
        Group group = GroupSessionFactory.getGroupSession().removeMember(msg.getGroupName(), msg.getUsername());
        if (group != null) {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, "已退出群" + msg.getGroupName()));
        } else {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, msg.getGroupName() + "群不存在"));
        }
    }
}
