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

    // In our case the thread wait till the db connection is over

    // Creating
    //1
    //1
    //There is no connection left in the connection pool to reuse
    //1
    //1
    //1

    // This may occur whenever some Thread takes more time to execute and connection pool size is less
}
