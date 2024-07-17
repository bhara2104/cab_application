package com.application.cab_application.Services;


public class Alist {
    public static void main(String[] args) {
        java.util.List<Integer> arrayList = new java.util.ArrayList<Integer>();
        arrayList.add(10);
        arrayList.add(20);
        arrayList.add(45);
        arrayList.forEach(System.out::println);
    }
}
