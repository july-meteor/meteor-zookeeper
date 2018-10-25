package com.meteor.curator.core.constants;

import java.util.ArrayList;
import java.util.List;

public class ZKConstant {

	public static String ZK_SERVERS = "111.230.210.81:2181";

	public static int RETRY_CONN_COUNT = 10;

	public static int RETRY_CONN_INTERVAL = 5000;

	public static final String ROOT_PATH = "/";//项目的根目录

	public static int DEFAULT_SESSION_TIMEOUT_MS = 180000;
	public static int DEFAULT_CONNECTION_TIMEOUT_MS = 60000;

	/**
	 * 监听忽略列表
	 */
	public static List<String> LISTENTER_IGNORES = new ArrayList<String>() {
		private static final long serialVersionUID = 5281239410190516462L;
		{
//			add("/kafka");
		}

	};

}
