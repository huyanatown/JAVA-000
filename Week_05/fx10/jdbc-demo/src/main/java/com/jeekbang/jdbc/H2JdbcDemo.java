package com.jeekbang.jdbc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 使用 JDBC 原生接口，实现数据库的增删改查操作
 */
public class H2JdbcDemo {
    public static void main(String[] args) throws ClassNotFoundException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
        jdbc();
    }

    public static void jdbc() {
        String url = "jdbc:h2:mem:dataSource;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false";
        String username = "sa";
        String password = "";
        try {
            final Connection connection = DriverManager.getConnection(url, username, password);
            // 查询
            find(connection);

            // 新增
            String sql = "insert into student(name) values('测试')";
            update(connection, sql);
            find(connection);

            // 修改
            sql = "update student set name='测试-修改' where name='测试'";
            update(connection, sql);
            find(connection);

            // 删除
            sql = "delete from student where name='测试-修改'";
            update(connection, sql);
            find(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void find(Connection connection) throws SQLException {
        final Statement statement = connection.createStatement();
        statement.execute("select * from student");
        final ResultSet resultSet = statement.getResultSet();
        List<Student> students = new ArrayList<>();
        while (resultSet.next()){
            final int id = resultSet.getInt("id");
            final String name = resultSet.getString("name");
            Student student = new Student();
            student.setId(id);
            student.setName(name);
            students.add(student);
        }
        System.out.println(students);
    }

    private static void update(Connection connection, String sql) throws SQLException {
        final Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }
}
