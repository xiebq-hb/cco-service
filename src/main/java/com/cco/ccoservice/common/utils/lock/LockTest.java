package com.cco.ccoservice.common.utils.lock;

/**
 * @author xiebq
 * @create 2020/3/3 0003
 * @since 1.0.0
 */
public class LockTest {
    static final LockTest o = new LockTest();
    static final LockTest o1 = new LockTest();

    public static void main(String[] args) {
        LockTest lt = new LockTest();

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    lt.test();
                }
            }).start();
        }
    }

    private void test() {
//        synchronized (o) {
            // 进入同步块，获取锁
            try {
                System.out.println(Thread.currentThread().getId() + " 获得锁");
                Thread.sleep(3000);
                // 当前同步块结束，释放锁
                System.out.println(Thread.currentThread().getId() + " 释放锁");
            }catch (InterruptedException e){
                e.printStackTrace();
            }

//        }
    }

}
