import sun.awt.windows.ThemeReader;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class Homework03 {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        int result = function11(); //这是得到的返回值

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程

    }


    // 使用线程池 submit 异步提交任务
    public static int function1() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        final Future<Integer> result = executorService.submit(() -> sum());
        try {
            final Integer integer = result.get();
            return integer;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        return 0;
    }

    // 使用 FutureTask 异步执行任务
    public static int function2() {
        FutureTask futureTask = new FutureTask(() -> sum());
        try {
            Thread thread = new Thread(futureTask);
            thread.start();
            return (Integer) futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Semaphore
    public static int function3() {
        AtomicInteger sum = new AtomicInteger();
        Semaphore semaphore = new Semaphore(1);
        new Thread(() -> {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sum.set(sum());
            semaphore.release();
        }).start();
        while (sum.get() <= 0) {
        }
        return sum.get();
    }

    // CountDownLatch
    public static int function4() {
        AtomicInteger sum = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            sum.set(sum());
            countDownLatch.countDown();
        }).start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sum.get();
    }

    // CyclicBarrier
    public static int function5() {
        AtomicInteger sum = new AtomicInteger();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(1, () -> new Thread(() -> {
            sum.set(sum());
        }).start());
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        while (sum.get() <= 0) {
        }
        return sum.get();
    }

    // CompletableFuture
    public static int function6() {
        final Integer join = CompletableFuture.supplyAsync(() -> sum()).join();
        return join;
    }

    // 线程 join
    public static int function7() {
        AtomicInteger sum = new AtomicInteger();
        final Thread thread = new Thread(() -> {
            sum.set(sum());
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sum.get();
    }

    // 线程 sleep
    public static int function8() {
        AtomicInteger sum = new AtomicInteger();
        new Thread(() -> {
            sum.set(sum());
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sum.get();
    }

    // wait -> notify
    public static int function9() {
        AtomicInteger sum = new AtomicInteger();
        Object obj = new Object();
        synchronized (obj){
            new Thread(() -> {
                synchronized (obj){
                    sum.set(sum());
                    obj.notify();
                }
            }).start();
            try {
                obj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return sum.get();
    }

    // Lock -> condition
    public static int function10() {
        AtomicInteger sum = new AtomicInteger();
        Lock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();

        try {
            lock.lock();
            new Thread(() -> {
                lock.lock();
                sum.set(sum());
                condition.signalAll();
                lock.unlock();
            }).start();
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return sum.get();
    }

    // LockSupport
    public static int function11() {
        AtomicInteger sum = new AtomicInteger();
        Thread paren = Thread.currentThread();
        Thread thread = new Thread(() -> {
            sum.set(sum());
            LockSupport.unpark(paren);
        });
        thread.start();
        LockSupport.park();
        return sum.get();
    }

    private static int sum() {
        System.out.println("执行任务的线程：" + Thread.currentThread().getName());
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
