package com.geekbang.work;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyDemo {
    public static void main(String[] args) {
        Hello hello = new HelloWorld();
        final Class<?>[] interfaces = hello.getClass().getInterfaces();
        final InvocationHandler helloWorldProxy = new HelloWorldProxy(hello);
        final Hello proxy = (Hello)Proxy.newProxyInstance(
                helloWorldProxy.getClass().getClassLoader(),
                interfaces,
                helloWorldProxy);
        System.out.println(proxy.getClass());
        proxy.sayHello();

    }


    public interface Hello{
        void sayHello();
    }
    public static class HelloWorld implements Hello{
        public void sayHello() {
            System.out.println("Hello World!");
        }
    }
    public static class HelloWorldProxy implements InvocationHandler{
        private Hello helloProxy;
        public HelloWorldProxy(Hello helloProxy){
            this.helloProxy = helloProxy;
        }
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Proxy Before ---");
            System.out.println(proxy.getClass().getSimpleName());
            final Object invoke = method.invoke(helloProxy, args);
            System.out.println("Proxy After ---");
            return invoke;
        }
    }
}
