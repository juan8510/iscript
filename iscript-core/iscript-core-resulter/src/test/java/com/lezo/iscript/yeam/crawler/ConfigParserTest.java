package com.lezo.iscript.yeam.crawler;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lezo.iscript.service.crawler.service.BarCodeItemService;
import com.lezo.iscript.spring.context.SpringBeanUtils;
import com.lezo.iscript.yeam.service.ConfigParser;
import com.lezo.iscript.yeam.writable.TaskWritable;

public class ConfigParserTest {

	@Test
	public void testJdBar() throws Exception {
		TaskWritable task = new TaskWritable();
		task.put("barCode", "8934760211005");
		// task.put("barCode", "4719778004771");
		ConfigParser parser = new JdBarCodeSimilar();
		String rs = parser.doParse(task);
		System.out.println(rs);
	}

	@Test
	public void testLsdBarCodeGetter() throws Exception {
		String[] configs = new String[] { "classpath:spring-config-ds.xml" };
		ApplicationContext cx = new ClassPathXmlApplicationContext(configs);
		BarCodeItemService barCodeItemService = SpringBeanUtils.getBean(BarCodeItemService.class);
		TaskWritable task = new TaskWritable();
		LsdBarCodeGetter parser = new LsdBarCodeGetter();
		parser.setBarCodeItemService(barCodeItemService);
		String url = "http://www.lsd.hk/search.php?encode=YToxNDp7czo4OiJrZXl3b3JkcyI7czo2OiLHyb/LwaYiO3M6ODoiY2F0ZWdvcnkiO3M6MToiMCI7czo1OiJicmFuZCI7czoxOiIwIjtzOjQ6InNvcnQiO3M6ODoiZ29vZHNfaWQiO3M6NToib3JkZXIiO3M6NDoiREVTQyI7czo5OiJtaW5fcHJpY2UiO3M6MToiMCI7czo5OiJtYXhfcHJpY2UiO3M6MToiMCI7czo2OiJhY3Rpb24iO3M6MDoiIjtzOjU6ImludHJvIjtzOjA6IiI7czoxMDoiZ29vZHNfdHlwZSI7czoxOiIwIjtzOjU6InNjX2RzIjtzOjE6IjAiO3M6ODoib3V0c3RvY2siO3M6MToiMCI7czo0OiJwYWdlIjtzOjE6IjEiO3M6MTg6InNlYXJjaF9lbmNvZGVfdGltZSI7aToxNDA1MTAxMjM5O30=";
		List<String> urlList = new ArrayList<String>();
		urlList.add(url);
		urlList.add("http://www.lsd.hk/search.php?encode=YToyOntzOjU6ImludHJvIjtzOjM6Im5ldyI7czoxODoic2VhcmNoX2VuY29kZV90aW1lIjtpOjE0MDUxMDMxNjU7fQ==");
		urlList.add("http://www.lsd.hk/category-29-b0.html");
		urlList.add("http://www.lsd.hk/category-47-b0.html");
		urlList.add("http://www.lsd.hk/category-30-b0.html");
		urlList.add("http://www.lsd.hk/category-100-b0.html");
		for (String sUrl : urlList) {
			task.put("url", sUrl);
			String rs = parser.doParse(task);
			System.out.println(rs);
		}
		Thread.currentThread().join();
	}
}
