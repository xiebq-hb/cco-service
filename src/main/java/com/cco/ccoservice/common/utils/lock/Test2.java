package com.cco.ccoservice.common.utils.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author xiebq
 * @create 2020/3/3 0003
 * @since 1.0.0
 */
public class Test2 {
    private ReentrantReadWriteLock rw1 = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        final Test2 test = new Test2();
        new Thread(){
            public void run(){
                test.get(Thread.currentThread());
            }
        }.start();

        new Thread(){
            public void run() {
                test.get(Thread.currentThread());
            };
        }.start();
    }

    private void get(Thread thread) {
        rw1.readLock().lock();
        try {
            long start = System.currentTimeMillis();

            while(System.currentTimeMillis() - start <= 1) {
                System.out.println(thread.getName()+"正在进行读操作");
            }
            System.out.println(thread.getName()+"读操作完毕");

        }finally {
            rw1.readLock().unlock();
        }
    }
}
