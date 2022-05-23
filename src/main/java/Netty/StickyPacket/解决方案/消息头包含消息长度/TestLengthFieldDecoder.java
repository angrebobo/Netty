package Netty.StickyPacket.解决方案.消息头包含消息长度;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author: HuangSiBo
 * @Description:
 * @Data: Created in 17:27 2022/5/23
 */
public class TestLengthFieldDecoder {
    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024, 0, 4, 1, 5),
                new LoggingHandler(LogLevel.INFO)
        );


        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        send(buf, "Hello World");
        send(buf, "Hi");
        channel.writeInbound(buf);
    }

    private static void send(ByteBuf buf, String content){
        byte[] bytes = content.getBytes();
        int len = bytes.length;
        buf.writeInt(len);
        buf.writeByte(1);
        buf.writeBytes(bytes);
    }
}
