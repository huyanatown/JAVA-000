package com.jeekbang.jdbc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 使用事务，PrepareStatement 方式，批处理方式
 */
public class H2JdbcDemo2 {
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
            add(connection);
            find(connection);

            // 修改
            update(connection);
            find(connection);

            // 删除
            delete(connection);
            find(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void find(Connection connection) throws SQLException {
        final PreparedStatement statement = connection.prepareStatement("select * from student");
        final ResultSet resultSet = statement.executeQuery();
        List<Student> students = new ArrayList<>();
        while (resultSet.next()) {
            final int id = resultSet.getInt("id");
            final String name = resultSet.getString("name");
            Student student = new Student();
            student.setId(id);
            student.setName(name);
            students.add(student);
        }
        System.out.println(students);
    }

    private static void add(Connection connection) throws SQLException {
        String sql = "insert into student(name) values(?)";
        connection.setAutoCommit(false);
        final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "测试 - add");
        preparedStatement.executeUpdate();
        connection.commit();
    }


    private static void update(Connection connection) throws SQLException {
        String sql = "update student set name=? where name=?";
        connection.setAutoCommit(false);
        final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "测试 - update");
        preparedStatement.setString(2, "测试 - add");
        preparedStatement.executeUpdate();
        connection.commit();
    }

    private static void delete(Connection connection) throws SQLException {
        String sql = "delete from student where name=?";
        connection.setAutoCommit(false);
        final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "测试 - update");
        preparedStatement.executeUpdate();
        connection.commit();
    }
}
