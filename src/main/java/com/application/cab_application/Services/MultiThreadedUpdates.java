package com.application.cab_application.Services;

public class MultiThreadedUpdates {
    public static void main(String[] args) {
        Runnable obj1 = new UpdateThread();
        Runnable obj2 = new UpdateThread1();
        Thread t1 = new Thread(obj2);
        Thread t2 = new Thread(obj1);
        Thread t3 = new Thread(obj1);
        t1.start();
        t2.start();
        t3.start();
    }

    // with 1 Thread
    // connection creating
    //1
    //connection creating
    //1
    //1
    //1
    //1
    //1
    //1
    //1
    //1
    //1
    //1
    //0
    //There is no connection left in the connection pool to reuse
    // Since last thread never returned the connection back to the pool

    // Here race condition does not occurs but if all the threads tries to execute at the same time and access the same resource deadlock will occur
}
