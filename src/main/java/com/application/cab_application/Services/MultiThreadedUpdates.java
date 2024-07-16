package com.application.cab_application.Services;

public class MultiThreadedUpdates {
    public static void main(String[] args) {
        Runnable obj1 = new UpdateThread();
        Runnable obj2 = new UpdateThread1();
        Thread t1 = new Thread(obj2);
        Thread t2 = new Thread(obj1);
        t1.start();
        t2.start();
    }
}
