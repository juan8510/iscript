package com.lezo.iscript.scope;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lezo
 * @email lcstore@126.com
 * @since 2014年10月14日
 */
public class ScriptableUtils {
	private static Logger logger = LoggerFactory.getLogger(ScriptableUtils.class);

	static class MyFactory extends ContextFactory {
		@Override
		protected boolean hasFeature(Context cx, int featureIndex) {
			boolean useDynamicScope = true;
			if (featureIndex == Context.FEATURE_DYNAMIC_SCOPE) {
				return useDynamicScope;
			}
			return super.hasFeature(cx, featureIndex);
		}
	}

	static {
		ContextFactory.initGlobal(new MyFactory());
	}

	private static class QueryInstance {
		private static Scriptable instance;
		static {
			try {
				instance = new ResourceScriptableFactory("script/core/iQuery.js").createScriptable(null, false);
			} catch (Exception e) {
				logger.warn("creat query scriptable.cause:", e);
			}
		}
	}

	public static Scriptable getQueryScriptable() {
		return QueryInstance.instance;
	}

	private static class JSONInstance {
		private static Scriptable instacne;
		static {
			try {
				Scriptable temp = new ResourceScriptableFactory("script/core/json2.js").createScriptable(null, false);
				instacne = newScriptable(temp);
			} catch (Exception e) {
				logger.warn("creat json scriptable.cause:", e);
			}
		}
	}

	public static Scriptable getJSONScriptable() {
		return JSONInstance.instacne;
	}

	private static class CoreInstance {
		private static Scriptable instacne;
		static {
			try {
				instacne = new ResourceScriptableFactory("script/core/core.js", "script/core/iQuery.js").createScriptable((ScriptableObject) getJSONScriptable(), false);
			} catch (Exception e) {
				logger.warn("creat json scriptable.cause:", e);
			}
		}
	}

	public static Scriptable getCoreScriptable() {
		return CoreInstance.instacne;
	}

	public static Scriptable newScriptable(Scriptable parent) {
		Context cx = Context.enter();
		Scriptable newScope = cx.newObject(parent);
		newScope.setPrototype(parent);
		newScope.setParentScope(null);
		return newScope;
	}
}
