package com.geekbang.work07;

import com.geekbang.work07.service.OrderDynamicService;
import com.geekbang.work07.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@SpringBootTest
class Work07ApplicationTests {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDynamicService orderDynamicService;

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

}
