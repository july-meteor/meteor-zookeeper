package com.meteor.curator.core.utils.lib.excpetion;


/**
 * 工具类异常
 * 
 */
public class UtilException extends RuntimeException{

	public UtilException(Throwable e) {
		super(e.getMessage(), e);
	}
	private static final long serialVersionUID = 8247610319171014183L;


	
	public UtilException(String message) {
		super(message);
	}
	

}
