package com.lezo.iscript.envjs.dom;

import java.util.HashMap;
import java.util.Map;

import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;

public class DocumentJavaObject extends NativeJavaObject {
	private static final long serialVersionUID = -3084942115819596740L;
	private static Map<String, String> adaptMap = new HashMap<String, String>();
	static{
		adaptMap.put("attachEvent", "dispatchEvent");
	}
	public DocumentJavaObject(Scriptable scope, Object javaObject, Class<?> staticType) {
		super(scope, javaObject, staticType);
	}

	@Override
	public Object get(int index, Scriptable start) {
		Object result = null;
		if (has(index, start)) {
			result = super.get(index, start);
		}
		return doReturn(result);
	}

	@Override
	public void put(int index, Scriptable start, Object value) {
		super.put(index, start, value);
	}

	@Override
	public void put(String name, Scriptable start, Object value) {
		super.put(name, start, value);
	}

	@Override
	public Object get(String name, Scriptable start) {
		Object result = null;
		if (has(name, start)) {
			result = super.get(name, start);
		}
		return doReturn(result);
	}

//	private String toAdaptName(String name) {
//		String adaptName = adaptMap.get(name);
//		return adaptName==null?name:adaptName;
//	}

	private Object doReturn(Object result) {
		return result;
	}

}