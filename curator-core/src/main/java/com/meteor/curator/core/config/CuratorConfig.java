package com.meteor.curator.core.config;

        import com.meteor.curator.core.utils.lib.StrUtil;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Meteor-Curator配置信息
 */
public class CuratorConfig {

    public static String ZK_SERVERS = "localhost:2181";
    public static String ROOT_PATH = StrUtil.SLASH;//项目的根目录 不是很推荐'/' 作为根目录
    public static int RETRY_CONN_COUNT = 10;
    public static int RETRY_CONN_INTERVAL = 5000;
    public static int DEFAULT_SESSION_TIMEOUT_MS = 180000;
    public static int DEFAULT_CONNECTION_TIMEOUT_MS = 60000;

    //监听列表
    public static List<String> LISTENER = new ArrayList<String>() {
        private static final long serialVersionUID = 5281239410190516462L;

        {
//			add("/");
        }

    };

    // 监听忽略列表
    public static List<String> LISTENER_IGNORES = new ArrayList<String>() {
        private static final long serialVersionUID = 5281239410190516462L;

        {
//			add("/kafka");
        }

    };


    public static void init(String servers, String rootPath, Integer connCount, Integer connInterval,
                            Integer seessionTimeOut, Integer connTimeOut, List<String> listener, List<String> listenerIgnores) {
        if (!StrUtil.isEmpty(servers)) {
            ZK_SERVERS = servers;
        }
        if (!StrUtil.isEmpty(rootPath)) {
            ROOT_PATH = rootPath;
        }
        if (null != connCount) RETRY_CONN_COUNT = connCount;
        if (null != connInterval) RETRY_CONN_INTERVAL = connInterval;
        if (null != seessionTimeOut) DEFAULT_SESSION_TIMEOUT_MS = seessionTimeOut;
        if (null != connTimeOut) DEFAULT_CONNECTION_TIMEOUT_MS = connTimeOut;
        if (null != listener && listener.size() > 0) LISTENER = listener;

        if (null != listenerIgnores && listenerIgnores.size() > 0) LISTENER_IGNORES = listenerIgnores;
    }


}
