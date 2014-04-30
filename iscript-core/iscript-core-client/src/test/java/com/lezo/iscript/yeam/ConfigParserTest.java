package com.lezo.iscript.yeam;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.junit.Test;

import com.lezo.iscript.utils.JSONUtils;
import com.lezo.iscript.yeam.config.HuihuiSigner;
import com.lezo.iscript.yeam.config.JDCid2PList;
import com.lezo.iscript.yeam.config.ScriptConfigParser;
import com.lezo.iscript.yeam.config.StringLinker;
import com.lezo.iscript.yeam.service.ConfigParser;
import com.lezo.iscript.yeam.writable.TaskWritable;

public class ConfigParserTest {

	@Test
	public void test() throws Exception {
		ConfigParser parser = new StringLinker();
		TaskWritable task = new TaskWritable();
		task.put("x", "i am lezo");
		task.put("y", "i am lezo");
		try {
			String result = parser.doParse(task);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void testHuihuiSigner() throws Exception {
		System.setProperty("jsse.enableSNIExtension", "false");
		ConfigParser parser = new HuihuiSigner();
		TaskWritable task = new TaskWritable();
		task.put("user", "lcstore@126.com");
		task.put("pwd", "126@9Lezo");
		task.put("user", "ajane2009@163.com");
		task.put("pwd", "AJ3251273aj");
		try {
			String result = parser.doParse(task);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testNullArgs() throws Exception {
		String config = FileUtils.readFileToString(new File("src/test/java/com/lezo/iscript/yeam/config/sum.xml"));
		ConfigParser parser = new ScriptConfigParser(config);
		TaskWritable task = new TaskWritable();
		task.put("type", "sum");
		task.put("x", null);
		task.put("y", "i am lezo");
		try {
			String result = parser.doParse(task);
			JSONObject jObject = new JSONObject(result);
			jObject = JSONUtils.get(jObject, "args", JSONObject.class);
			System.out.println(result);
			System.out.println(jObject);
			String x = JSONUtils.get(jObject, "x", String.class);
			System.out.println("x:" + x);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Test
	public void testJDC2list() throws Exception {
		ConfigParser parser = new JDCid2PList();
		TaskWritable task = new TaskWritable();
		try {
			String result = parser.doParse(task);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}