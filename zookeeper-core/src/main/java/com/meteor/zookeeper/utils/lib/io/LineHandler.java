package com.meteor.zookeeper.utils.lib.io;

/**
 * created by Meteor on 2018/10/21
 */
public interface LineHandler {
    /**
     * 处理一行数据，可以编辑后存入指定地方
     * @param line 行
     */
    void handle(String line);
}
