package Netty.协议设计;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(),
                new LengthFieldBasedFrameDecoder(
                        1024, 12, 4, 0, 0),
                new MessageCodec()
        );

        //测试消息编码
        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123");
        // encode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null, message, buf);
        channel.writeOutbound(buf);

        //模拟半包的情况，只收到一部分消息
        ByteBuf s1 = buf.slice(0, 30);
        ByteBuf s2 = buf.slice(30, buf.readableBytes() - 30);
        s1.retain(); // 引用计数 2
        channel.writeInbound(s1); // release 1
//        channel.writeInbound(s2);
    }
}
