package com.meteor.zookeeper;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.ho.yaml.Yaml;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AppConfig  {


    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);


    /**
     * 获得一个配置项的值
     *
     * @param key   路径
     * @param clazz 类型
     * @param <T>   返回类型
     * @return
     */
    public static <T> T getItemValue(String key, Class<T> clazz) {
        T result = null;
        try {
            if (StringUtils.isBlank(key)) {
                return result;
            }
            key = ZKUtil.checkPath(key,true);
            T value = ZKUtil.getData(key, clazz);
            if (null == value) {
                return result;
            }
            result = value;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return result;
        }
        return result;
    }


    /**
     * 获得一个配置项的值
     *
     * @param key
     * @return
     */
    public static String getItemValue(String key) {
        return getItemValue(key, String.class);
    }


    /**
     * 获得一个配置项的值
     *
     * @param key 路径
     * @return
     */
    public static int getInt(String key) {
        Integer result = 0;
        try {
            if (StringUtils.isBlank(key)) {
                return result;
            }
            String value = getItemValue(key);
            if (StringUtils.isBlank(value)) {
                return result;
            }
            result = Integer.parseInt(value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return result;
        }
        return result;
    }

    /**
     * 获得一个配置项的值
     *
     * @param key 路径
     * @return
     */
    public static Boolean getBoolean(String key) {
        Boolean result = null;
        try {
            if (StringUtils.isBlank(key)) {
                return result;
            }
            String value = getItemValue(key);
            if (StringUtils.isBlank(value)) {
                return result;
            }
            result = Boolean.valueOf(value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return result;
        }
        return result;


    }

    /**
     * 获得一个配置项的值
     *
     * @param key        查询的值
     * @param defaultInt 默认值
     * @return
     */
    public static int getInt(String key, Integer defaultInt) {
        int value = getInt(key);
        if (value == 0) {
            return defaultInt;
        }
        return value;
    }

    /**
     * 获得一个配置项的值
     *
     * @param key        查询的值
     * @param defaultInt 默认值
     * @return
     */
    public static Boolean getBoolean(String key, Boolean defaultInt) {
        return BooleanUtils.toBooleanDefaultIfNull(getBoolean(key), defaultInt);
    }

    /**
     * 获得一个配置项的值
     *
     * @param key        查询的值
     * @param defaultStr 默认值
     * @return
     */
    public static String getItemValue(String key, String defaultStr) {
        return StringUtils.defaultIfBlank(getItemValue(key), defaultStr);
    }

    /**
     * 获得 properties
     *
     * @param key 路径
     * @return
     */
    public static Properties getProp(String key) {
        return getItemValue(key, Properties.class);
    }


    public static List<String> getList(String key) {
        return getItemValue(key, List.class);
    }

    /**
     * @param key 路径
     * @return
     */
    public static Map<String, String> getMap(String key) {
        return getItemValue(key, Map.class);
    }


    public static <T> T getYml(String key, Class<T> clazz) {
        T t = null;
        String itemValue = getItemValue(key);
        if (StringUtils.isEmpty(itemValue)) {
            return t;
        }
        t = Yaml.loadType(itemValue, clazz);
        return t;
    }


    public static <T> T getJson(String key, Class<T> clazz) {
        T t = null;
        String itemValue = getItemValue(key);
        if (StringUtils.isEmpty(itemValue)) {
            return t;
        }
        t = JSON.parseObject(itemValue, clazz);
        return t;
    }
}
