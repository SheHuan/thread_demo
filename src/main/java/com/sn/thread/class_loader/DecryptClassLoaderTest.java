package com.sn.thread.class_loader;

public class DecryptClassLoaderTest {
    public static void main(String[] args) {
//        MyClassLoader myClassLoader = new MyClassLoader("MyClassLoader");
//        myClassLoader.setDir("E:\\thread_demo\\classes3\\");
        DecryptClassLoader myClassLoader = new DecryptClassLoader("DecryptClassLoader");
        try {
            Class<?> clazz = myClassLoader.loadClass("com.sn.thread.class_loader.SimpleObject2");
            System.out.println(clazz);
            System.out.println(((DecryptClassLoader) (clazz.getClassLoader())).getClassLoaderName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
