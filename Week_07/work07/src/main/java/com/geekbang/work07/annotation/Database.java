package com.geekbang.work07.annotation;

public @interface Database {
    boolean readOnly() default true;
}
