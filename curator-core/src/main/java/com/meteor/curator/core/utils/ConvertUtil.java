package com.meteor.curator.core.utils;

import com.alibaba.fastjson.JSON;
import com.meteor.curator.core.listener.TreeListenter;
import com.meteor.curator.core.utils.lib.io.IoUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

public class ConvertUtil  {

	private static Logger log = LoggerFactory.getLogger(TreeListenter.class);


	/**
	 * Map.toSting 转换会Map
	 *
	 * @param mapString
	 *            { kk= vvv, kk= vvv, kk= vvv} 这种格式的
	 * @return 一个Map
	 */
	public static Map<String, String> stringToMap(String mapString) {
		// 拆分空格
		String[] values = mapString.split("\r|\n");
		Map<String, String> resultMap = new HashMap<>();
		for (String tempMap : values) {// 拆分出一个map
			String[] map = tempMap.split("=");// 拆分出 key and vaule
			resultMap.put(map[0], map[1]);
		}
		return resultMap;
	}

	/**
	 * 将list String 转换成 List
	 * <p>
	 * aaaasd asdasd 转成 ['aaaasd','asdasd']
	 *
	 * @param listString
	 * @return
	 */
	public static List<String> stringToList(String listString) {
		String[] values = listString.split("\r|\n");
		List<String> result = new ArrayList<>();
		for (String value : values) {
			if (!StringUtils.isEmpty(value)) {
				result.add(value.trim());
			}
		}
		return result;
	}

	public static Properties stringToProp(String propString) throws IOException {
		Properties props = new Properties();
		ByteArrayInputStream stream = IoUtil.toStream(propString, "UTF-8");
		props.load(stream);
		stream.close();
		return props;
	}

	/**
	 * Map转换层Bean，使用泛型免去了类型转换的麻烦。
	 *
	 * @param <T>
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> T mapToBean(Map<String, String> map, Class<T> clazz) throws Exception {
		T bean = null;
		bean = clazz.newInstance();
		BeanUtils.populate(bean, map);
		return bean;
	}

	/**
	 * 泛型转换
	 *
	 * @param value
	 *            获得到的值
	 * @param clazz
	 *            转换类型
	 * @param <T>
	 *            返回类型
	 * @return
	 */
	public static <T> T convert(String value, Class<T> clazz) {
		T result = null;
		try {
			if (clazz == Integer.class) {
				result = (clazz.cast(Integer.parseInt(value)));
			} else if (clazz == Double.class) {
				result = clazz.cast(Double.parseDouble(value));
			} else if (clazz == Float.class) {
				result = clazz.cast(Float.parseFloat(value));
			} else if (clazz == Long.class) {
				result = clazz.cast(Long.parseLong(value));
			} else if (clazz == Boolean.class) {
				result = clazz.cast(Boolean.parseBoolean(value));
			} else if (clazz == String.class) {
				result = (T) value;
			} else if (clazz == Map.class) { // 复合类型
				result = (T) stringToMap(value);
			} else if (clazz == List.class) {
				result = (T) stringToList(value);
			} else if (clazz == Properties.class) {
				result = (T) stringToProp(value);
			} else {
				// 默认使用json,构建对象
				result = JSON.parseObject(value, clazz);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result = (T) value;
		}
		return result;
	}

}
