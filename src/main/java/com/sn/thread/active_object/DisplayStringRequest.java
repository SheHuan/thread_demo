package com.sn.thread.active_object;

/**
 * {@link ActiveObject#displayString(String)}
 */
public class DisplayStringRequest extends MethodRequest {
    private final String text;

    protected DisplayStringRequest(Servant servant, String text) {
        super(servant, null);
        this.text = text;
    }

    @Override
    public void execute() {
        servant.displayString(text);
    }
}
