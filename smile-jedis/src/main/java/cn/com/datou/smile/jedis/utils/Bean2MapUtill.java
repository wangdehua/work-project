package cn.com.datou.smile.jedis.utils;

import org.apache.commons.lang3.StringUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Bean2MapUtill {

	/**
	 * 将java bean转成map 仅适用于java bean属性为String的对象
	 * 
	 * @param t
	 *            java bean
	 * @return redis Hash对象
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> Map<String, String> bean2Map(T t) throws Exception {
		Class<?> clazz = t.getClass();
		Field[] fields = clazz.getDeclaredFields();
		PropertyDescriptor pd;
		Method method;
		Map<String, String> map = new HashMap<String, String>();
		Object value;
		String putValue;
		for (Field f : fields) {
			if (2 == f.getModifiers()) {
				pd = new PropertyDescriptor(f.getName(), clazz);
				method = pd.getReadMethod();
				value = method.invoke(t);
				putValue = null == value ? "" : value + "";
				map.put(f.getName(), putValue);
			}
		}
		return map;
	}

	/**
	 * 将redis的hash对象转换为java bean对象
	 * 
	 * @param map
	 *            redis hash
	 * @param clazz
	 *            java bean的Class对象
	 * @return java bean对象
	 * @throws Exception
	 */
	public static <T> T map2Bean(Map<String, String> map, Class<T> clazz) throws Exception {
		Set<String> set = map.keySet();
		PropertyDescriptor pd;
		Method method;
		T t = clazz.newInstance();
		String mapValue;
		for (String fieldName : set) {
			pd = new PropertyDescriptor(fieldName, clazz);
			method = pd.getWriteMethod();
			mapValue = map.get(fieldName);
			if (Integer.class == pd.getPropertyType()) {
				if (StringUtils.isNotBlank(mapValue)) {
					method.invoke(t, Integer.valueOf(mapValue));
				}
			} else if (Long.class == pd.getPropertyType()) {
				if (StringUtils.isNotBlank(mapValue)) {
					method.invoke(t, Long.valueOf(mapValue));
				}
			} else if (Boolean.class == pd.getPropertyType()) {
				if (StringUtils.isNotBlank(mapValue)) {
					method.invoke(t, Boolean.valueOf(mapValue));
				}
			} else if (Double.class == pd.getPropertyType()) {
				if (StringUtils.isNotBlank(mapValue)) {
					method.invoke(t, Double.valueOf(mapValue));
				}
			} else {
				if (StringUtils.isNotBlank(mapValue)) {
					method.invoke(t, mapValue);
				}
			}
		}
		return t;
	}
}
