package com.application.cab_application.Services;

import com.application.cab_application.Util.ConnectionPool;

public class MultiThreadedUpdates {
    public static void main(String[] args) {
        try {
            ConnectionPool connectionPool = new ConnectionPool();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Runnable obj1 = new UpdateThread();
        Runnable obj2 = new UpdateThread1();
        Thread t1 = new Thread(obj2);
        Thread t2 = new Thread(obj1);
        Thread t3 = new Thread(obj2);
        long startTime = System.nanoTime();
        t1.start();
        t2.start();
        t3.start();
        long endTime = System.nanoTime();
        System.out.println(endTime - startTime);
    }

    // with 1 Thread -> 149584
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

    // By making it as single connection if connection does not returned by the first thread second thread will never get chance to execute

    //One Connection -> 95458 It is less because 1'st thread exited due to connection pool problem
    // Second try -> 154333
    // Two connection -> 110000

    // Five connection -> 91542

}
