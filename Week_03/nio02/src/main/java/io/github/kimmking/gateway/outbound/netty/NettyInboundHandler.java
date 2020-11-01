package io.github.kimmking.gateway.outbound.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.util.CharsetUtil.UTF_8;

public class NettyInboundHandler extends ChannelInboundHandlerAdapter {
    public static String result;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpContent)
        {
            HttpContent content = (HttpContent)msg;
            final String byteBuf = content.content().toString(UTF_8);
//            System.out.println("接受到内容： " + byteBuf);
            result = byteBuf;

            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(byteBuf.getBytes()));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", byteBuf.length());
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            ctx.flush();

        }
    }
}
