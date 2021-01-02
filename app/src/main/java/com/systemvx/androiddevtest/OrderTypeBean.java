package com.systemvx.androiddevtest.bean;

public class OrderTypeBean {
    private String text;
    private boolean check;

    public OrderTypeBean(String text, boolean check) {
        this.text = text;
        this.check = check;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
