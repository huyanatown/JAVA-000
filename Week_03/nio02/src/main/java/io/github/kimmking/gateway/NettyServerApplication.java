package io.github.kimmking.gateway;

import io.github.kimmking.gateway.inbound.HttpInboundServer;
import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.github.kimmking.gateway.router.RandomHttpEndpointRouter;

import java.util.ArrayList;
import java.util.List;

public class NettyServerApplication {
    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";

    public static List<String> routers = new ArrayList<>();
    static {
        routers.add("http://localhost:8088");
        routers.add("http://localhost:8088");
        routers.add("http://localhost:8088");
    }

    public static void main(String[] args) {
        HttpEndpointRouter httpEndpointRouter = new RandomHttpEndpointRouter();
        final String route = httpEndpointRouter.route(routers);

        String proxyServer = System.getProperty("proxyServer", route);
        int port = Integer.parseInt(System.getProperty("proxyPort","8888"));

        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" starting...");
        HttpInboundServer server = new HttpInboundServer(port, proxyServer);
        System.out.println(GATEWAY_NAME + " " + GATEWAY_VERSION +" started at http://localhost:" + port + " for server:" + proxyServer);

        try {
            server.run();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
