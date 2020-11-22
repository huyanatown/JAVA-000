package com.geekbang;

import java.util.ArrayList;
import java.util.List;

public class Test {
    int x = 1;

    public static void main(String[] args) {
        Test test = new Test();
        test.test();
        System.out.println(test.x);
    }

    void test(){
        List<Integer> b = new ArrayList<>();
        b.add(1);
        b.forEach((x) -> {
            x = 2;
        });
    }
}
