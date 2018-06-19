package com.yven.other;

public class Main {

    public  static void main(String[] agrs){
        System.out.println("test");
        int i = 1 , j=0;
        j = i ;
        System.out.println("i="+i+" j="+j);
        i = 2;
        System.out.println("i="+i+" j="+j);
    }

    Thread thread = new Thread();
}
