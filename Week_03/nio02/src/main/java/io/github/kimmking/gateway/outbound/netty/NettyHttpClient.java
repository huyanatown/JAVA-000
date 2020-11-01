package io.github.kimmking.gateway.outbound.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;


public class NettyHttpClient {
    private String host;
    private int port;

    public NettyHttpClient(String proxyServer) {
        try {
            URI uri = new URI(proxyServer);
            this.host = uri.getHost();
            this.port = uri.getPort();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public void connect(String path) {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                            ch.pipeline().addLast(new HttpResponseDecoder());
                            // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                            ch.pipeline().addLast(new HttpRequestEncoder());
                            ch.pipeline().addLast(new NettyInboundHandler());
                        }
                    });

            ChannelFuture f = b.connect(host, port).sync();

            URI uri = new URI(path);
            FullHttpRequest fullRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString());
            // 构建http请求
            fullRequest.headers().set(HttpHeaders.Names.HOST, host);
            fullRequest.headers().set(HttpHeaders.Names.CONTENT_LENGTH, fullRequest.content().readableBytes());
            // 发送http请求
            f.channel().writeAndFlush(fullRequest);
            f.channel().closeFuture().sync();
        } catch (InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }
    }
}
