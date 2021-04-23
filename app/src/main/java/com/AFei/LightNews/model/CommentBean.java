package com.AFei.LightNews.model;

import com.AFei.LightNews.entity.Comment;

//
public class CommentBean {
    private int status;
    private String info;
    private Comment object;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }




    public Comment getObject() {
        return object;
    }

    public void setObject(Comment object) {
        this.object = object;
    }
}