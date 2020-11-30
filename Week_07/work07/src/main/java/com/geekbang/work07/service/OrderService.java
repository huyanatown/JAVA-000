package com.geekbang.work07.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    @Qualifier("masterJdbcTemplate")
    private JdbcTemplate masterJdbcTemplate;
    @Autowired
    @Qualifier("slave1JdbcTemplate")
    private JdbcTemplate slave1JdbcTemplate;

    public List<Map<String, Object>> getOrders(){
        String sql = "SELECT * FROM java0.order";
        return slave1JdbcTemplate.queryForList(sql);
    }

    public int saveOrder(){
        String sql = "insert into java0.order(oder_no,user_id,seller_id," +
                "item_id,item_name,status," +
                "price,number,total_price," +
                "address,gmt_create) values(?,?,?," +
                "?,?,0," +
                "?,?,?," +
                "?,now())";
        Object[] params = new Object[]{UUID.randomUUID().toString(), 1,2,UUID.randomUUID().toString().substring(0,5),"测试商品",new BigDecimal(1),1,new BigDecimal(1),"浙江"};
        return masterJdbcTemplate.update(sql, params);
    }

}
