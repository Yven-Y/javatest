package com.yven.jvm;

/**
 * 代码清单3-2
 *
 * 使用finalize()方法来拯救对象，不推荐使用
 */
public class FinalizeEscapeGC {

    private static  FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive(){
        System.out.println("I am still alive!");
    }


    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed");
        FinalizeEscapeGC.SAVE_HOOK = this;

    }

    public static void main(String[] args) throws  Throwable {
        SAVE_HOOK = new FinalizeEscapeGC();

        // 1 对象第一次可复活
        SAVE_HOOK = null;
        System.gc();// gc 会去调用finalie(),让对象复活（重写finalize()方法）

        Thread.sleep(500);
        if( SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else{
            System.out.println("I am dead!");
        }

        // 2 复活失败，应为finalize() 方法只能自动被调用一次
        SAVE_HOOK = null;
        System.gc();

        Thread.sleep(500);
        if( SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else{
            System.out.println("I am dead!");
        }

    }

}
