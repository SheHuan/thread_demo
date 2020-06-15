package com.sn.thread.singleton;

public class Singleton {
    private Singleton() {
    }

    public static Singleton getInstance() {
        return SingletonEnum.INSTANCE.getInstance();
    }

    private enum SingletonEnum {
        INSTANCE;
        private Singleton instance;

        SingletonEnum() {
            instance = new Singleton();
        }

        public Singleton getInstance() {
            return instance;
        }
    }
}
