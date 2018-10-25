package com.meteor.curator.web.admin.service.impl;



import com.meteor.curator.core.Initializer;
import com.meteor.curator.core.ZKUtil;
import com.meteor.curator.core.constants.ZKConstant;
import com.meteor.curator.core.exception.ZKDataNullException;
import com.meteor.curator.core.pojo.ZKNode;
import com.meteor.curator.web.admin.service.ZKService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ZKServiceImpl  implements ZKService {

	private static Logger log = LoggerFactory.getLogger(ZKServiceImpl.class);

	@Override
	public ZKNode tree(String path) throws Exception {
		ZKNode root = null;
			if (StringUtils.isEmpty(path)) {
				path = ZKConstant.ROOT_PATH;
			}
			CuratorFramework client = Initializer.getCuratorFramework();
			GetChildrenBuilder children = client.getChildren();
			root = new ZKNode(path, path);
			buildTree(children, root);

		return root;
	}

	@Override
	public Map<String, Object> getTreeByCurServer(String path) throws Exception {
		Map<String,Object> params = new HashMap<>();
		//当前服务
		params.put("curServsers",	ZKConstant.ZK_SERVERS);
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


	private void buildTree(GetChildrenBuilder children, ZKNode node) throws Exception {
		String fullPath = node.getFullPath();
		List<String> child_path_list = children.forPath(fullPath);
		if (CollectionUtils.isEmpty(child_path_list)) {
			String data = null;
			try {
				data = ZKUtil.getData(fullPath);
			} catch (ZKDataNullException e) {
				log.error(e.getMessage());
			}
			node.setData(data);
			return;
		}

		List<ZKNode> childs = node.getChilds();
		if (CollectionUtils.isEmpty(childs)) {
			childs = new ArrayList<ZKNode>();
			node.setChilds(childs);
		}

		for (String child_path : child_path_list) {
			ZKNode children_node = null;
			if (StringUtils.equalsIgnoreCase(fullPath, ZKConstant.ROOT_PATH)) {
				children_node = new ZKNode(child_path, fullPath + child_path);
				buildTree(children, children_node);
			} else {
				children_node = new ZKNode(child_path, fullPath + "/" + child_path);
				buildTree(children, children_node);
			}
			childs.add(children_node);
		}
	}


}
