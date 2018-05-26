package com.util;

import org.apache.commons.collections.map.HashedMap;

/**
 * 
 * @ClassName: AllowUnderlineMap
 * @Description: 接口数据接受集合（数据量大的不建议用这个map）
 * @author hutao
 * @date 2016年11月10日 上午11:41:27
 */
public class AllowUnderlineMap extends HashedMap {
	@Override
	public Object put(Object key, Object value) {
		// 带下划线
		if (key instanceof String && ((String) key).contains("_")) {
			key = formatColumName((String) key);
		}
		// 如果value为空 返回空字符
		if (null == value) {
			value = "";
		}
		return super.put(key, value);
	}

	// 生成类名
	public static String formatColumName(String columName) {
		String modelName = "";
		if (columName.indexOf("_") > 0) {
			modelName = columName.substring(0, columName.indexOf("_"));
			columName = columName.substring(columName.indexOf("_") + 1, columName.length());
			columName = capitalized(columName);
			if (columName.indexOf("_") > 0) {
				modelName += formatColumName(columName);
			} else {
				modelName += columName;
			}
		} else {
			modelName = columName;
		}
		return modelName;
	}

	// 首字母大写
	public static String capitalized(String s) {
		String one = s.substring(0, 1);
		return one.toUpperCase() + s.substring(1, s.length());
	}

}
