package com.sn.thread.active_object;

public class ActiveObjectTest {
    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        new MakerClientThread("Hello", activeObject).start();
        new MakerClientThread("World", activeObject).start();

        new DisplayClientThread("Mojito", activeObject).start();
    }
}
