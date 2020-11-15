package com.geekbang.entity;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@ConfigurationProperties(prefix = "student")
public class Student implements Serializable {

    private int id;
    private String name;

    public Student(){}

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void init() {
        System.out.println("hello...........");
    }

    public static Student create() {
        return new Student(101, "KK101");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
