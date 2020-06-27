package com.sn.thread.class_loader;

import java.lang.reflect.Method;

public class MyClassLoaderTest2 {
    public static void main(String[] args) {
        MyClassLoader myClassLoader = new MyClassLoader("MyClassLoader-1");
        MyClassLoader myClassLoader2 = new MyClassLoader("MyClassLoader-2", myClassLoader);
        myClassLoader2.setDir("E:\\thread_demo\\classes2\\");

        try {
            Class<?> clazz = myClassLoader2.loadClass("com.sn.thread.class_loader.SimpleObject2");
            System.out.println(clazz);
            System.out.println(((MyClassLoader) (clazz.getClassLoader())).getClassLoaderName());

            Object obj = clazz.newInstance();
            Method method = clazz.getMethod("hello", new Class<?>[]{});
            Object result = method.invoke(obj, new Object[]{});
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
