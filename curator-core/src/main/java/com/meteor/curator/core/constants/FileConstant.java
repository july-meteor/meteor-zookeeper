package com.meteor.curator.core.constants;

import java.io.File;


import com.meteor.curator.core.utils.lib.PathUtil;
import org.apache.commons.lang.StringUtils;


/**
 *  Created by Meteor on 2018/4/18 文件读取常量
 */
public class FileConstant {
    // 文件存放路径 win 下
    public static final String FILE_ROOT_WIN_PATH = getRootDir() + File.separator + ".zk";
    // 文件存放路径 linux 下
    public static final String FILE_ROOT_LINUX_PATH = getRootDir() + File.separator + ".zk";
    public static String FILE_ROOT_PATH = isWindows() ? FILE_ROOT_WIN_PATH : FILE_ROOT_LINUX_PATH;
    public static final String FILE_NAME = File.separator + "data.txt";

    public final static String OS_NAME = "os.name";
    /**
     * 规定路径 参数为root.dir = 程序根目录， 如果找不到在使用 user.dir
     *
     * @return
     */
    public static final String getRootDir() {
        String rootDir = System.getProperty("user.home");
        return rootDir;
    }


    /**
     * 判断操作系统是否Linux
     *
     * @return true-是，false-否
     */
    public static boolean isLinux() {
        return StringUtils.containsIgnoreCase(OS_NAME, "linux");
    }

    /**
     * 判断操作系统是否Windows
     *
     * @return true-是，false-否
     */
    public static boolean isWindows() {
        return StringUtils.containsIgnoreCase(OS_NAME, "windows");
    }

}
