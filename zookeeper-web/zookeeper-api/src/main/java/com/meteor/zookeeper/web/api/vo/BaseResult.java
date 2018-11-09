package com.meteor.zookeeper.web.api.vo;

/**
 * Created by y747718944 on 2018/4/28
 * 一个简单的基础返回集
 */
public class BaseResult {
    private int status = 200;
    private String message;

    public BaseResult() {
    }

    public BaseResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
