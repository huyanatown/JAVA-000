package io.github.kimmking.gateway.outbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface OutboundHandler {
    void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) throws InterruptedException;
}
