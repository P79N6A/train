package com.petzm.training.module.home.event;

public class RefreshEvent {

    private  int  position;

    public RefreshEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
