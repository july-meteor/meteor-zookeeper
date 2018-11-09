package com.meteor.zookeeper.web.api.vo;

/**
 * Created by y747718944 on 2018/2/6
 *
 */

public class Result<T> extends BaseResult {

   private T data;
   private  boolean rel;




    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isRel() {
        return rel;
    }

    public void setRel(boolean rel) {
        this.rel = rel;
    }




    public Result(Object data){
        this.rel=true;
        this.data= (T) data;
    }
    public Result(boolean flag){
        this.rel=flag;
    }

    public Result(String  message, Object data){
        this.rel=true;
        this.data= (T) data;
        super.setMessage(message);
    }
    public Result(String  message, boolean flag){
        this.rel=flag;
        super.setMessage(message);
    }

    public Result() {
        this.rel = true;
    }

    public Result(String  message, Integer status){
        this.rel=false;
        super.setMessage(message);
        super.setStatus(status);
    }
}
