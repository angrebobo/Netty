package Netty.GroupChat.server.handler;

import Netty.GroupChat.message.GroupMembersRequestMessage;
import Netty.GroupChat.message.GroupMembersResponseMessage;
import Netty.GroupChat.server.session.GroupSessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Set;

@ChannelHandler.Sharable
public class GroupMembersRequestMessageHandler extends SimpleChannelInboundHandler<GroupMembersRequestMessage> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, GroupMembersRequestMessage msg) {
        Set<String> members = GroupSessionFactory.getGroupSession()
                .getMembers(msg.getGroupName());
        ctx.writeAndFlush(new GroupMembersResponseMessage(members));
    }
}
