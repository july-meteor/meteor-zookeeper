package com.meteor.zookeeper.utils.lib;


/**
 * Created by y747718944 on 2018/4/23
 * 路径工具类
 */
public class PathUtil {

    public static String getPackPath(){
        String path = PathUtil.class.getResource("").getPath();
        return path;
    }

    public static String getClassesPath(){
        return  PathUtil.class.getClassLoader().getResource("").getPath();
    }

    public static String getWebClassesPath() {
        String path = PathUtil.class.getProtectionDomain().getCodeSource()
                .getLocation().getPath();
        return path;

    }
}
