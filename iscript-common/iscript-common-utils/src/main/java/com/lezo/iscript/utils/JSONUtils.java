package com.lezo.iscript.utils;

import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONUtils {
	private static Logger log = Logger.getLogger(JSONUtils.class);

	public static JSONObject getJSONObject(Object content) {
		if (content == null) {
			return null;
		}
		JSONObject jObject = null;
		try {
			if (content instanceof JSONObject) {
				return (JSONObject) content;
			} else if (content instanceof String) {
				String sContent = content.toString();
				jObject = new JSONObject(sContent);
			} else if (content instanceof Map) {
				Map<?, ?> map = (Map<?, ?>) content;
				jObject = new JSONObject(map);
			} else if (content instanceof JSONTokener) {
				JSONTokener tokener = (JSONTokener) content;
				jObject = new JSONObject(tokener);
			} else {
				jObject = new JSONObject(content);
			}
		} catch (JSONException e) {
			log.warn("Fail to convert JSONObject..");
		}
		return jObject;
	}

	public static void put(JSONObject jObj, String key, Object value) {
		try {
			jObj.putOpt(key, value);
		} catch (JSONException e) {
			log.warn("Fail to put " + key, e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(JSONObject jObj, String key, Class<T> destClass) {
		if (!jObj.isNull(key)) {
			try {
				return (T) jObj.get(key);
			} catch (JSONException e) {
				log.warn("Fail to get " + key, e);
			}
		}
		return null;
	}

	public static Object getObject(JSONObject jObj, String key) {
		return get(jObj, key, Object.class);
	}

	public static String getString(JSONObject jObj, String key) {
		return get(jObj, key, String.class);
	}

	public static Integer getInteger(JSONObject jObj, String key) {
		return get(jObj, key, Integer.class);
	}

}
