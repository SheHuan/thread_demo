package com.sn.thread.class_loader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class DecryptClassLoader extends ClassLoader{
    private static final String DEFAULT_DIR = "E:\\thread_demo\\classes3\\";

    private String dir = DEFAULT_DIR;

    private String classLoaderName;

    public DecryptClassLoader() {
        super();
    }

    public DecryptClassLoader(String classLoaderName) {
        super();
        this.classLoaderName = classLoaderName;
    }

    public DecryptClassLoader(String classLoaderName, ClassLoader parent) {
        super(parent);
        this.classLoaderName = classLoaderName;
    }

    /**
     * @param name 类的全包名路径
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = name.replace(".", "/");
        File classFile = new File(dir, classPath + ".class");
        if (!classFile.exists()) {
            throw new ClassNotFoundException("The class " + name + " not found under " + dir);
        }
        byte[] classBytes = loadClassBytes(classFile);
        if (classBytes == null || classBytes.length == 0) {
            throw new ClassNotFoundException("Load the class " + name + " failed ");
        }
        return this.defineClass(name, classBytes, 0, classBytes.length);

    }

    private byte[] loadClassBytes(File classFile) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             FileInputStream fis = new FileInputStream(classFile)) {
            int data;
            while ((data = fis.read()) != -1) {
                baos.write(data^EncryptUtils.ENCRYPT_FACTOR);
            }
            baos.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getClassLoaderName() {
        return classLoaderName;
    }
}
