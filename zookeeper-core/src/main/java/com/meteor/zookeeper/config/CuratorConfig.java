package com.meteor.zookeeper.config;

import com.meteor.zookeeper.utils.lib.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Meteor-Curator配置信息
 */
public class CuratorConfig {

    private static final Logger log = LoggerFactory.getLogger(CuratorConfig.class);


    public static String ZK_SERVERS = "localhost:2181";
    public static String ROOT_PATH = StrUtil.SLASH;//项目的根目录 不是很推荐'/' 作为根目录
    public static Integer RETRY_CONN_COUNT = 10;
    public static Integer RETRY_CONN_INTERVAL = 5000;
    public static Integer DEFAULT_SESSION_TIMEOUT_MS = 180000;
    public static Integer DEFAULT_CONNECTION_TIMEOUT_MS = 60000;

    //监听列表
    public static List<String> LISTENER = new ArrayList<String>() {
        private static final long serialVersionUID = 5281239410190516462L;

        {
            add(ROOT_PATH);
        }

    };

    // 监听忽略列表
    public static List<String> LISTENER_IGNORES = new ArrayList<String>() {
        private static final long serialVersionUID = 5281239410190516462L;

        {
//			add("/kafka");
        }

    };


}
