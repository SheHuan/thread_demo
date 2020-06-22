package com.sn.thread.active_object;

/**
 * 接收异步消息的主动对象
 */
public interface ActiveObject {
    Result makeString(int count, char fillChar);

    void displayString(String text);
}
