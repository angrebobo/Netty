package Netty.GroupChat.server.handler;

import Netty.GroupChat.message.GroupJoinRequestMessage;
import Netty.GroupChat.message.GroupJoinResponseMessage;
import Netty.GroupChat.server.session.Group;
import Netty.GroupChat.server.session.GroupSessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) {
        Group group = GroupSessionFactory.getGroupSession().joinMember(msg.getGroupName(), msg.getUsername());
        if (group != null) {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, msg.getGroupName() + "群加入成功"));
        } else {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, msg.getGroupName() + "群不存在"));
        }
    }
}
