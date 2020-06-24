package com.sn.thread.class_loader;

public class ClassLoader {
    public static void main(String[] args) throws ClassNotFoundException {
        // 根类加载器
        System.out.println(System.getProperty("sun.boot.class.path"));
        // 扩展类加载器
        System.out.println(System.getProperty("java.ext.dirs"));
        // 系统类加载器
        Class<?> clazz = Class.forName("com.sn.thread.class_loader.SimpleObject");
        System.out.println(clazz.getClassLoader());

        System.out.println(clazz.getClassLoader().getParent());
        System.out.println(clazz.getClassLoader().getParent().getParent());
    }
}
