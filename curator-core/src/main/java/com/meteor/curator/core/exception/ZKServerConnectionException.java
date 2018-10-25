package com.meteor.curator.core.exception;

/**
 * Created by Meteor on 2018/4/24
 *  判断服务是否是空的
 */
public class ZKServerConnectionException extends RuntimeException {

    public  ZKServerConnectionException(){

    }
    public  ZKServerConnectionException(String message){
        super(message);
    }
}
