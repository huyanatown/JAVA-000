package io.github.kimmking.gateway.inbound;

import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.filter.HttpRequestHeaderFilter;
import io.github.kimmking.gateway.outbound.OutboundHandler;
import io.github.kimmking.gateway.outbound.netty.NettyClientOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final String proxyServer;
    private OutboundHandler handler;
    private HttpRequestFilter filter;

    public HttpInboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
        this.filter = new HttpRequestHeaderFilter();

        // http 方式调用
//        this.handler = new HttpclientOutboundHandler(proxyServer);
        // netty 方式调用
        this.handler = new NettyClientOutboundHandler(proxyServer);


    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            filter.filter(fullHttpRequest, ctx);
            handler.handle(fullHttpRequest, ctx);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
