package com.meteor.curator.core.pojo;

import java.io.Serializable;
import java.util.List;

public class ZKNode implements Serializable{
	private static final long serialVersionUID = -548861697874421559L;

	public ZKNode() {

	}

	public ZKNode(String path, String fullPath) {
		super();
		this.path = path;
		this.fullPath = fullPath;
	}

	private String fullPath;
	private String path;

	private String data;

	private List<ZKNode> childs;

	public String getFullPath() {
		return fullPath;
	}



	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<ZKNode> getChilds() {
		return childs;
	}

	public void setChilds(List<ZKNode> childs) {
		this.childs = childs;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}






}
