package com.meteor.zookeeper.listener;

import java.util.ArrayList;
import java.util.List;

import com.meteor.zookeeper.ZKUtil;
import com.meteor.zookeeper.config.CuratorConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
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
		try {



			List<String> rootPaths = CuratorConfig.LISTENER;
			if (null == rootPaths || rootPaths.size() <= 0) {
				return;
			}

			// 获取忽略列表
//			CuratorConfig.LISTENER_IGNORES = CuratorConfig.LISTENER_IGNORES;
			for (String path : rootPaths) {
				boolean abort = false;
				if ( null != CuratorConfig.LISTENER_IGNORES) {
					for (String ignore : CuratorConfig.LISTENER_IGNORES) {
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

		}catch (Exception e) {
			log.error("treeCache initialize error", e);
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
