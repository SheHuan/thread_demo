package com.sn.thread.compare_and_set_lock;

public class GetLockException extends Exception {
    public GetLockException() {
        super();
    }

    public GetLockException(String message) {
        super(message);
    }
}
