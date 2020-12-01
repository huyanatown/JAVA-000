package com.geekbang.work07.service;


import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class OrderShardingService {
    @Autowired
    private DataSource dataSource;

    public List<Map<String, Object>> getOrders() throws SQLException {
        String sql = "SELECT  *  FROM  java0.order where 1=1";
        final Connection connection = dataSource.getConnection();
        final PreparedStatement ps = connection.prepareStatement(sql);
        final ResultSet resultSet = ps.executeQuery();
        List<Map<String, Object>> result = new ArrayList<>();
        while (resultSet.next()){
            Map<String, Object> map = new HashMap<>();
            final int id = resultSet.getInt("id");
            final String oder_no = resultSet.getString("oder_no");
            final String user_id = resultSet.getString("user_id");
            final String seller_id = resultSet.getString("seller_id");
            final String item_id = resultSet.getString("item_id");
            final String item_name = resultSet.getString("item_name");
            final int status = resultSet.getInt("status");
            final BigDecimal price = resultSet.getBigDecimal("price");
            final int number = resultSet.getInt("number");
            final BigDecimal total_price = resultSet.getBigDecimal("total_price");
            final String address = resultSet.getString("address");
            final Date gmt_create = resultSet.getDate("gmt_create");

            map.put("id", id);
            map.put("oder_no", oder_no);
            map.put("user_id", user_id);
            map.put("seller_id", seller_id);
            map.put("item_id", item_id);
            map.put("item_name", item_name);
            map.put("status", status);
            map.put("price", price);
            map.put("number", number);
            map.put("total_price", total_price);
            map.put("address", address);
            map.put("gmt_create", gmt_create);

            result.add(map);
        }
        return result;
    }
    public int saveOrder() throws SQLException {
        String sql = "insert into java0.order(oder_no,user_id,seller_id," +
                "item_id,item_name,status," +
                "price,number,total_price," +
                "address,gmt_create) values(?,?,?," +
                "?,?,0," +
                "?,?,?," +
                "?,now())";
        final Connection connection = dataSource.getConnection();
        final PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, UUID.randomUUID().toString());
        ps.setInt(2, 1);
        ps.setInt(3, 1);
        ps.setString(4, "i_"+1);
        ps.setString(5, "测试商品"+1);
        ps.setBigDecimal(6, new BigDecimal(1));
        ps.setInt(7, 1);
        ps.setBigDecimal(8, new BigDecimal(1));
        ps.setString(9, "浙江"+1);
        ps.addBatch();
        ps.executeBatch();
        ps.close();
        connection.close();
        return 1;
    }
}
