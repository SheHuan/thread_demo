package com.sn.thread.active_object;

/**
 * 对应ActiveObject的每一个方法
 */
public abstract class MethodRequest {
    protected final Servant servant;

    protected final FutureResult futureResult;

    protected MethodRequest(Servant servant, FutureResult futureResult) {
        this.servant = servant;
        this.futureResult = futureResult;
    }

    public abstract void execute();
}
