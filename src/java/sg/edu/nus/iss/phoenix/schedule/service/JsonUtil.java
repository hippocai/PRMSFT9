/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.service;

/**
 *
 * @author hippo
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.PropertyFilter;

@SuppressWarnings("rawtypes")
public class JsonUtil {

	
	public static String getJsonString4JavaPOJO(Object javaObj) {

		JSONObject json;
		JsonConfig cfg = new JsonConfig();
		cfg.setJsonPropertyFilter(new PropertyFilter() {
			
			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
				if ("callbacks".equalsIgnoreCase(arg1) || "callback".equalsIgnoreCase(arg1)) {
					return true;
				}
				return false;
			}
		});
		json = JSONObject.fromObject(javaObj, cfg);
		return json.toString();

	}
	

	public static Object getObject4JsonString(String jsonString, Class pojoCalss) {
		JSONUtils.getMorpherRegistry().registerMorpher(   
                new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss" }));  
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;
	}
	

    public static String getJsonString4JavaList(List objList) {
    	JSONArray jsonArr;
    	JsonConfig cfg = new JsonConfig();
		cfg.setJsonPropertyFilter(new PropertyFilter() {
			
			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
				if ("callbacks".equalsIgnoreCase(arg1) || "callback".equalsIgnoreCase(arg1)) {
					return true;
				}
				return false;
			}
		});
    	jsonArr = JSONArray.fromObject(objList, cfg);
    	return jsonArr.toString();
    }

    public static JSONArray getJson4JavaList(List objList) {
    	JSONArray jsonArr;
    	JsonConfig cfg = new JsonConfig();
		cfg.setJsonPropertyFilter(new PropertyFilter() {
			
			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
				if ("callbacks".equalsIgnoreCase(arg1) || "callback".equalsIgnoreCase(arg1)) {
					return true;
				}
				return false;
			}
		});
    	jsonArr = JSONArray.fromObject(objList, cfg);
    	return jsonArr;
    }


	@SuppressWarnings("unchecked")
	public static List getList4Json(String jsonString, Class pojoClass) {
		JSONUtils.getMorpherRegistry().registerMorpher(   
                new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss" })); 
		
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;

		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);
		}
		return list;

	}


	@SuppressWarnings("unchecked")
	public static Map getMap4Json(String jsonString) {
		JSONUtils.getMorpherRegistry().registerMorpher(   
                new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss" })); 
		
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();

		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}

		return valueMap;
	}


	@Deprecated
	public static Object[] getObjectArray4Json(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return jsonArray.toArray();

	}
	

	public static String[] getStringArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArray[i] = jsonArray.getString(i);
		}

		return stringArray;
	}


	public static Long[] getLongArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Long[] longArray = new Long[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			longArray[i] = jsonArray.getLong(i);
		}
		return longArray;
	}


	public static Integer[] getIntegerArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Integer[] integerArray = new Integer[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			integerArray[i] = jsonArray.getInt(i);
		}
		return integerArray;
	}


	public static Double[] getDoubleArray4Json(String jsonString) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Double[] doubleArray = new Double[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			doubleArray[i] = jsonArray.getDouble(i);
		}
		return doubleArray;
	}

}
