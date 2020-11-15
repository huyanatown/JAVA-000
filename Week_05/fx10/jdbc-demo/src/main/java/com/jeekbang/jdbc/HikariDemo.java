package com.jeekbang.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 配置 Hikari 连接池
 */
public class HikariDemo {

    public static DataSource hikariDataSource(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName("org.h2.Driver");
        hikariDataSource.setJdbcUrl("jdbc:h2:mem:dataSource;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false");
        hikariDataSource.setUsername("sa");
        hikariDataSource.setPassword("");

        return hikariDataSource;
    }


    public static void main(String[] args) throws SQLException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
        final DataSource dataSource = hikariDataSource();
        final Connection connection = dataSource.getConnection();
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
