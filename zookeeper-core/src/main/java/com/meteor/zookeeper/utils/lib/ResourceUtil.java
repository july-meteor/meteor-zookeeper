package com.meteor.zookeeper.utils.lib;

import com.meteor.zookeeper.utils.lib.excpetion.IORuntimeException;

import java.net.URL;

/**
 * created by Meteor on 2018/10/21
 */
public class ResourceUtil {


    /**
     * 获得资源的URL<br>
     * 路径用/分隔，例如:
     * <pre>
     * config/a/db.config
     * spring/xml/test.xml
     * </pre>
     *
     * @param resource 资源（相对Classpath的路径）
     * @return 资源URL
     */
    public static URL getResource(String resource) throws IORuntimeException{
        return getResource(resource, null);
    }

    /**
     * 获得资源相对路径对应的URL
     * @param resource 资源相对路径
     * @param baseClass 基准Class，获得的相对路径相对于此Class所在路径，如果为{@code null}则相对ClassPath
     * @return {@link URL}
     */
    public static URL getResource(String resource, Class<?> baseClass){
        return (null != baseClass) ? baseClass.getResource(resource) : ClassLoaderUtil.getClassLoader().getResource(resource);
    }
}
