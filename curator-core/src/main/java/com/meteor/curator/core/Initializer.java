package com.meteor.curator.core;

import com.meteor.curator.core.constants.FileConstant;
import com.meteor.curator.core.config.CuratorConfig;
import com.meteor.curator.core.exception.ZKServerConnectionException;
import com.meteor.curator.core.listener.TreeListenter;
import com.meteor.curator.core.utils.FileDefineUtil;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.RetryNTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * by ybh zookeeper 监控初始化
 */
public class Initializer {
	private static final Logger logger = LoggerFactory.getLogger(Initializer.class);

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
