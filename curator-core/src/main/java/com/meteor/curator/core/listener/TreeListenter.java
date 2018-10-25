package com.meteor.curator.core.listener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.meteor.curator.core.AppConfig;
import com.meteor.curator.core.ZKUtil;
import com.meteor.curator.core.constants.FileConstant;
import com.meteor.curator.core.constants.ZKConstant;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.ho.yaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by y747718944 on 2018/4/18 zookeeper 树节点监控初始化 单例模式
 */
public class TreeListenter  {
	private static Logger log = LoggerFactory.getLogger(TreeListenter.class);

	private static List<TreeCache> treeCaches = new ArrayList<TreeCache>();

	/**
	 * 创建 连接
	 *
	 * @param client
	 */
	@SuppressWarnings("unchecked")
	public static void lisenter(CuratorFramework client) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(FileConstant.ZK_PATH_CONFIG);
			Map<String, Object> configtMap = Yaml.loadType(fis, HashMap.class);
			if ( null != configtMap ) {
				return;
			}

			List<String> rootPaths = (List<String>) configtMap.get("ZK_ROOT_PATHS");
			if (null == rootPaths || rootPaths.size() <= 0) {
				return;
			}

			// 获取忽略列表
			ZKConstant.LISTENTER_IGNORES = AppConfig.getList("/zk/listenter/ignores");
			for (String path : rootPaths) {
				boolean abort = false;
				if ( null != ZKConstant.LISTENTER_IGNORES) {
					for (String ignore : ZKConstant.LISTENTER_IGNORES) {
						if (StringUtils.startsWithIgnoreCase(path, ignore)) {
							abort = true;
							break;
						}
					}
					if (abort) {
						continue;
					}
				}

				try {
					TreeCache cache = new TreeCache(client, path);
					// 添加监听
					addListener(cache);
					treeCaches.add(cache);
					cache.start();
					log.info("|path {} is do add lister|", path);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (FileNotFoundException e) {
			// 允许不配置，不输出错误
			log.warn("在路径{},未找到zk监控配置文件", FileConstant.ZK_PATH_CONFIG);
		} catch (Exception e) {
			log.error("treeCache initialize error", e);
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 关闭 监听
	 */
	public static void close() {
		if (null != treeCaches) {
			treeCaches.stream().forEach(index -> {
				index.close();
			});
		}
	}

	/**
	 * 添加监听
	 *
	 * @param cache
	 */
	public static void addListener(TreeCache cache) {
		cache.getListenable().addListener(new TreeCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
				if (null == event.getData() || null == event.getData().getData()) {
					return;
				}
				String path = event.getData().getPath();
				byte[] bytes = event.getData().getData();
				String value = new String(bytes, "UTF-8");
				switch (event.getType()) {
				case NODE_ADDED:
					ZKUtil.writeItem(path, value);
					break;
				case NODE_UPDATED:
					ZKUtil.writeItem(path, value);
					break;
				case NODE_REMOVED:
					ZKUtil.removeItem(path);
					break;
				default:
					break;
				}
			}
		});
	}

}
