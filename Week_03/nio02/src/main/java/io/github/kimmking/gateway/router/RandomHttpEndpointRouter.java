package io.github.kimmking.gateway.router;

import java.util.List;
import java.util.Random;

public class RandomHttpEndpointRouter implements HttpEndpointRouter {
    private Random random = new Random();

    @Override
    public String route(List<String> endpoints) {
        final int i = random.nextInt(endpoints.size());
        System.out.println("i:" + i);
        return endpoints.get(i);
    }
}
