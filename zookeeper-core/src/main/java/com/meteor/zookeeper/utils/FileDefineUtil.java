package com.meteor.zookeeper.utils;


import com.meteor.zookeeper.constants.FileConstant;
import com.meteor.zookeeper.utils.lib.io.file.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

/**
 * Created by y747718944 on 2018/4/18 本地磁盘文件操作类
 */
public class FileDefineUtil {

	private static final Logger logger = LoggerFactory.getLogger(FileDefineUtil.class);

	/**
	 * 写入配置文件
	 * 
	 * @param fullPath
	 *            相对路径
	 * @param value
	 *            值
	 * @return
	 */
	public static boolean write(String fullPath, byte[] value) {
		String path = FileConstant.FILE_ROOT_PATH + fullPath;
		try {
			// 创建文件夹
			File file = FileUtil.mkdir(path);
			if (!FileUtil.exist(file)) {
				logger.error("文件夹创建失败！相对路径: {}", fullPath);
				return false;
			}

			FileUtil.writeString(new String(value), path + FileConstant.FILE_NAME, "UTF-8");


			return true;
		} catch (Exception e) {
			logger.error(path + "   " + e.getMessage(), e);
		}
		return false;
	}

	/**
	 * 读取文件内容
	 * 
	 * @param fullPath
	 *            相对路径
	 * @return
	 */
	public static String read(String fullPath) {
		String path = FileConstant.FILE_ROOT_PATH + fullPath + FileConstant.FILE_NAME;
		if (!FileUtil.exist(path)) {
			return null;
		}
		return FileUtil.readUtf8String(FileConstant.FILE_ROOT_PATH + fullPath + FileConstant.FILE_NAME);
	}

	/**
	 * 删除配置文件 会删除子目录
	 * 
	 * @param fullPath
	 *            相对路径 is null 就删除整个路径
	 * @return
	 */
	public static boolean remove(String fullPath) {
		if (StringUtils.isEmpty(fullPath)) {
			return delete(FileConstant.FILE_ROOT_PATH);
		} else {
			return delete(FileConstant.FILE_ROOT_PATH + fullPath);
		}

	}

	/**
	 * 删除文件，可以是文件或文件夹
	 *
	 * @param fileName
	 *            要删除的文件名
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean delete(String fileName) {
		if (!FileUtil.exist(fileName)) {
			return false;
		}
		return FileUtil.del(fileName);
	}

}
