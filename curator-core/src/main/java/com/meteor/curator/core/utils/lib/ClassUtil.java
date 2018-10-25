package com.meteor.curator.core.utils.lib;

import com.meteor.curator.core.utils.lib.excpetion.IORuntimeException;

import java.net.URL;

/**
 * created by Meteor on 2018/10/21
 */
public class ClassUtil {

    /**
     * 获得ClassPath
     *
     * @return ClassPath
     */
    public static String getClassPath() {
        return getClassPathURL().getPath();
    }

    /**
     * 获得ClassPath URL
     *
     * @return ClassPath URL
     */
    public static URL getClassPathURL() {
        return getResourceURL(StrUtil.EMPTY);
    }


    /**
     * 获得资源的URL<br>
     * 路径用/分隔，例如:
     *
     * <pre>
     * config/a/db.config
     * spring/xml/test.xml
     * </pre>
     *
     * @param resource 资源（相对Classpath的路径）
     * @return 资源URL
     * @see ResourceUtil#getResource(String)
     */
    public static URL getResourceURL(String resource) throws IORuntimeException {
        return ResourceUtil.getResource(resource);
    }

    /**
     * 获得资源相对路径对应的URL
     *
     * @param resource 资源相对路径
     * @param baseClass 基准Class，获得的相对路径相对于此Class所在路径，如果为{@code null}则相对ClassPath
     * @return {@link URL}
     * @see ResourceUtil#getResource(String, Class)
     */
    public static URL getResourceUrl(String resource, Class<?> baseClass) {
        return ResourceUtil.getResource(resource, baseClass);
    }


}
