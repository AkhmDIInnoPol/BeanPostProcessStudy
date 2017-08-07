package ru.spring.exper;

public class ProfilingController implements ProfilingControllerMBean {

    private boolean isEnable;


    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
