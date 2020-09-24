package com.example.ecoclan_v2;

public class List_guide {

    String Title, msg;

    public List_guide() {
    }

    public List_guide(String title, String msg) {
        this.Title = title;
        this.msg = msg;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
