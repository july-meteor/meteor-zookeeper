package com.meteor.zookeeper.exception;

/**
 * Created by Meteor on 2018/4/24
 *  这是一个自定义运行异常，主要针对 zk 获取不到值的异常，
 */
public class ZKDataNullException  extends RuntimeException{

    public ZKDataNullException(){

    }

    public  ZKDataNullException(String message){
        super(message);
    }
}
