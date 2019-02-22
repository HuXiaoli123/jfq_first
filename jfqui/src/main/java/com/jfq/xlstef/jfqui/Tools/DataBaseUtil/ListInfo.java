package com.jfq.xlstef.jfqui.Tools.DataBaseUtil;


public class ListInfo{

    private  int _id;
    private  String timer;
    private  String content;

    private String icon;//图片名称

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {

        return icon;
    }

    public ListInfo(){

    }
    public ListInfo(String timer, String content) {
        this.timer = timer;
        this.content = content;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimer() {

        return timer;
    }

    public String getContent() {
        return content;
    }
}

