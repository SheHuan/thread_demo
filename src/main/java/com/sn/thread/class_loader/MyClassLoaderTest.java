package com.sn.thread.class_loader;

import java.lang.reflect.Method;

public class MyClassLoaderTest {
    public static void main(String[] args) {
        MyClassLoader myClassLoader = new MyClassLoader("MyClassLoader");
        try {
            Class<?> clazz = myClassLoader.loadClass("com.sn.thread.class_loader.SimpleObject2");
            System.out.println(clazz);
            System.out.println(clazz.getClassLoader());

            Object obj = clazz.newInstance();
            Method method = clazz.getMethod("hello", new Class<?>[]{});
            Object result = method.invoke(obj, new Object[]{});
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
