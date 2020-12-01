package com.geekbang.work07;

import com.geekbang.work07.service.OrderDynamicService;
import com.geekbang.work07.service.OrderService;
import com.geekbang.work07.service.OrderShardingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SpringBootTest
class Work07ApplicationTests {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDynamicService orderDynamicService;
    @Autowired
    private OrderShardingService orderShardingService;

    /**
     * 多数据源，手动
     */
    @Test
    void work2_1() {
        int rt = orderService.saveOrder();
        System.out.println("插入" + rt + "条数据");

        System.out.println("-------");

        List<Map<String, Object>> maps2 = orderService.getOrders();
        for (Map<String, Object> map : maps2) {
            System.out.println(map.get("id") + ">>>" + map.get("item_name"));
        }
    }

    /**
     * 多数据源[master, slave1, slave2]，自动
     */
    @Test
    void work2_2() {
        int rt = orderDynamicService.saveOrder();
        System.out.println("插入" + rt + "条数据");

        System.out.println("-------");

        List<Map<String, Object>> maps2 = orderDynamicService.getOrders();
        for (Map<String, Object> map : maps2) {
            System.out.println(map.get("id") + ">>>" + map.get("item_name"));
        }
    }


    /**
     * Sharding-Jdbc
     */
    @Test
    void work3() throws SQLException {
        int rt = orderShardingService.saveOrder();
        System.out.println("插入" + rt + "条数据");

        System.out.println("-------");

        List<Map<String, Object>> maps2 = orderShardingService.getOrders();
        for (Map<String, Object> map : maps2) {
            System.out.println(map.get("id") + ">>>" + map.get("item_name"));
        }
    }

}
