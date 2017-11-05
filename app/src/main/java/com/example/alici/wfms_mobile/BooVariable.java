package com.example.alici.wfms_mobile;

/**
 * Created by Libby Jennings on 20/09/17.
 * Description: Class for listening for changes in boolean variable. Used for stopping loading progress wheel
 */

public class BooVariable {
    private boolean boo = false;
    private ChangeListener listener;

    public boolean isBoo() {
        return boo;
    }

    void setBoo() {
        this.boo = true;
        if (listener != null) listener.onChange();
    }

    public ChangeListener getListener() {
        return listener;
    }

    void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onChange();
    }
}