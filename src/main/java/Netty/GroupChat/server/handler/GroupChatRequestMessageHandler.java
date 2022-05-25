package Netty.GroupChat.server.handler;

import Netty.GroupChat.message.GroupChatRequestMessage;
import Netty.GroupChat.message.GroupChatResponseMessage;
import Netty.GroupChat.server.session.GroupSessionFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, GroupChatRequestMessage msg) {
        List<Channel> channels = GroupSessionFactory.getGroupSession()
                .getMembersChannel(msg.getGroupName());

        for (Channel channel : channels) {
            channel.writeAndFlush(new GroupChatResponseMessage(msg.getFrom(), msg.getContent()));
        }
    }
}
