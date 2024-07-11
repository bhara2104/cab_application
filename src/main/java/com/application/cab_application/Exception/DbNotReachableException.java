package com.application.cab_application.Exception;

public class DbNotReachableException extends Exception{
    public DbNotReachableException(String message){
        super(message);
    }

    public DbNotReachableException(String message, Throwable throwable){
        super(message, throwable);
    }
}
