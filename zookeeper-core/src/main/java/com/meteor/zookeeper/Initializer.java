package com.meteor.zookeeper;

import com.meteor.zookeeper.constants.FileConstant;
import com.meteor.zookeeper.config.CuratorConfig;
import com.meteor.zookeeper.exception.ZKServerConnectionException;
import com.meteor.zookeeper.listener.TreeListenter;
import com.meteor.zookeeper.utils.FileDefineUtil;
import com.meteor.zookeeper.utils.lib.StrUtil;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.RetryNTimes;
import org.ho.yaml.Yaml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * by ybh zookeeper 监控初始化
 */
public class Initializer {
	private static final Logger logger = LoggerFactory.getLogger(Initializer.class);


	static {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(FileConstant.ZK_PATH_CONFIG);
			Map<String, Object> configtMap = Yaml.loadType(fis, HashMap.class);
			if (null != configtMap) {

				String servers = (String) configtMap.get("servers");
				if (!StrUtil.isEmpty(servers)){
					CuratorConfig.ZK_SERVERS = servers;
					logger.info("zookeeper服务地址{}",servers);
				}

				String rootPath = (String) configtMap.get("rootPath");
				if (!StrUtil.isEmpty(rootPath)) {
					CuratorConfig.ROOT_PATH = rootPath;
					logger.info("RootPath={}",servers);
				}

				Integer connCount = (Integer) configtMap.get("connCount");
				if (null != connCount) CuratorConfig.RETRY_CONN_COUNT = connCount;

				Integer connInterval = (Integer) configtMap.get("connInterval");
				if (null != connCount) CuratorConfig.RETRY_CONN_INTERVAL = connInterval;

				Integer sesstionTimeOut = (Integer) configtMap.get("sesstionTimeOut");
				if (null != connCount) CuratorConfig.DEFAULT_SESSION_TIMEOUT_MS = sesstionTimeOut;

				Integer connTimtOut = (Integer) configtMap.get("connTimtOut");
				if (null != connCount) CuratorConfig.DEFAULT_CONNECTION_TIMEOUT_MS = connTimtOut;


				List<String> listener = (List<String>) configtMap.get("listener");
				if (null == listener || listener.size() <= 0) {
					CuratorConfig.LISTENER = listener;
				}
				List<String> listenerIgnores = (List<String>) configtMap.get("listenerIgnores");
				if (null == listenerIgnores || listenerIgnores.size() <= 0) {
					CuratorConfig.LISTENER_IGNORES = listenerIgnores;
				}
			}
		} catch (FileNotFoundException e) {
			// 允许不配置，不输出错误
			logger.warn("在路径{},未找到zk监控配置文件", FileConstant.ZK_PATH_CONFIG);
			e.printStackTrace();
		}


	}
	
	
	static CuratorFramework curatorFramework = null;

	public static synchronized CuratorFramework getCuratorFramework() {
		if (null == curatorFramework) {
			init();
		} else if (curatorFramework.getState() == CuratorFrameworkState.STOPPED) {
			close();
			init();
		}
		return curatorFramework;
	}

	private static void init() throws ZKServerConnectionException {
		// 配置日志在初始化的时候加载
		logger.info("zkdata 本地保存路径: {}", FileConstant.FILE_ROOT_PATH);
//		logger.info("zk.yml路径: {}", FileConstant.ZK_PATH_CONFIG);
		try{
			FileDefineUtil.remove(null);}catch (Exception e){logger.warn(e.getMessage());}
		// 初始化连接
		RetryPolicy retryPolicy = new RetryNTimes(CuratorConfig.RETRY_CONN_COUNT, CuratorConfig.RETRY_CONN_INTERVAL);
		curatorFramework = CuratorFrameworkFactory.newClient(CuratorConfig.ZK_SERVERS,
				CuratorConfig.DEFAULT_SESSION_TIMEOUT_MS, CuratorConfig.DEFAULT_CONNECTION_TIMEOUT_MS, retryPolicy);
		curatorFramework.start();

		// 初始化监听
		new TreeListenter().lisenter(curatorFramework);
	}

	private static void close() {
		TreeListenter.close();
		curatorFramework.close();
	}

	// 手动重启
	public static void reInit() {
		if (null == curatorFramework) {
			init();
		} else {
			close();
			init();
		}
	}

	public static void addListener() {
		// curatorFramework.getConnectionStateListenable().addListener((client,state)->{
		// if (state == ConnectionState.CONNECTED) { //连接新建
		// logger.info("connected with zookeeper");
		// } else if (state == ConnectionState.LOST) {//连接丢失
		// logger.info("lost session with zookeeper");
		// } else if (state == ConnectionState.RECONNECTED) { //重新连接
		// logger.info("reconnected with zookeeper");
		// }
		// });
	}

}
