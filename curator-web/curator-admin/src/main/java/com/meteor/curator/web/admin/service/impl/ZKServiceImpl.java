package com.meteor.curator.web.admin.service.impl;


import com.meteor.curator.core.ZKUtil;
import com.meteor.curator.core.config.CuratorConfig;
import com.meteor.curator.core.pojo.ZKNode;
import com.meteor.curator.web.admin.service.ZKService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class ZKServiceImpl  implements ZKService {

	private static Logger log = LoggerFactory.getLogger(ZKServiceImpl.class);

	@Override
	public ZKNode tree(String path) throws Exception {

		return ZKUtil.tree(path);
	}

	@Override
	public Map<String, Object> getTreeByCurServer(String path) throws Exception {
		Map<String,Object> params = new HashMap<>();
		//当前服务
		params.put("curServsers",	CuratorConfig.ZK_SERVERS);
		params.put("ROOT",CuratorConfig.ROOT_PATH);
		//获取zNode
		ZKNode tree = tree(path);
		params.put("ZKNode",tree);
		return params;
	}

	@Override
	public void importZKNode(ZKNode zkNode) throws Exception {
		importTree(zkNode);
	}
	//遍历zknode
	private void  importTree(ZKNode zkNode) throws Exception {
		ZKUtil.setData(zkNode.getFullPath(),zkNode.getData());
		if (CollectionUtils.isNotEmpty(zkNode.getChilds()) ){
			zkNode.getChilds().stream().forEach(index ->{
				try {
					importTree(index);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
	}




}
