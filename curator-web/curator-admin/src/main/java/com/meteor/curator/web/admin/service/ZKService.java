package com.meteor.curator.web.admin.service;




import com.meteor.curator.core.pojo.ZKNode;

import java.util.Map;

public interface ZKService {

	/**
	 * 获取ZK树
	 * @param path
	 * @return
	 * @throws Exception
	 */
	ZKNode tree(String path) throws Exception;

	//获取当前服务下的zNode
	Map<String,Object> getTreeByCurServer(String path) throws Exception;

	void importZKNode(ZKNode zkNode)  throws Exception;
	
	
	
}
