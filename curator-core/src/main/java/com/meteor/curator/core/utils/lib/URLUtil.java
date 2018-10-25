package com.meteor.curator.core.utils.lib;

import com.meteor.curator.core.utils.lib.excpetion.UtilException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * created by Meteor on 2018/10/21
 */
public class URLUtil {

    /**
     * 从URL对象中获取不被编码的路径Path<br>
     * 对于本地路径，URL对象的getPath方法对于包含中文或空格时会被编码，导致本读路径读取错误。<br>
     * 此方法将URL转为URI后获取路径用于解决路径被编码的问题
     *
     * @param url {@link URL}
     * @return 路径
     * @since 3.0.8
     */
    public static String getDecodedPath(URL url) {
        String path = null;
        try {
            // URL对象的getPath方法对于包含中文或空格的问题
            path = URLUtil.toURI(url).getPath();
        } catch (UtilException e) {
            // ignore
        }
        return (null != path) ? path : url.getPath();
    }


    /**
     * 转URL为URI
     *
     * @param url URL
     * @return URI
     * @exception UtilException 包装URISyntaxException
     */
    public static URI toURI(URL url) throws UtilException {
        if (null == url) {
            return null;
        }
        try {
            return url.toURI();
        } catch (URISyntaxException e) {
            throw new UtilException(e);
        }
    }
}
