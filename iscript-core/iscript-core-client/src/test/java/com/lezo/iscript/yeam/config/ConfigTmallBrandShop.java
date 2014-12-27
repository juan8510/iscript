package com.lezo.iscript.yeam.config;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lezo.iscript.utils.JSONUtils;
import com.lezo.iscript.yeam.file.PersistentCollector;
import com.lezo.iscript.yeam.http.HttpClientManager;
import com.lezo.iscript.yeam.http.HttpClientUtils;
import com.lezo.iscript.yeam.mina.utils.HeaderUtils;
import com.lezo.iscript.yeam.service.ConfigParser;
import com.lezo.iscript.yeam.writable.TaskWritable;

public class ConfigTmallBrandShop implements ConfigParser {
	private DefaultHttpClient client = HttpClientManager.getDefaultHttpClient();
	private static final String EMTPY_RESULT = new JSONObject().toString();
	public static final int SITE_ID = 1013;

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String doParse(TaskWritable task) throws Exception {
		JSONObject itemObject = getDataObject(task);
		doCollect(itemObject, task);
		return EMTPY_RESULT;
	}

	private JSONObject getDataObject(TaskWritable task) throws Exception {
		String url = task.get("url").toString();
		HttpGet get = new HttpGet(url);
		get.addHeader("Referer", url);
		String html = HttpClientUtils.getContent(client, get, "gbk");
		Document dom = Jsoup.parse(html, url);
		ResultBean rsBean = new ResultBean();
		Elements itemEls = dom.select("div.brandShop ul.brandShop-slide-list a[href][target]");
		if (!itemEls.isEmpty()) {
			String region = getRegion(dom);
			Set<String> brandSet = getBrandSet(dom, task);
			String mainBrand = getMainBrandName(brandSet);
			String synonym = brandSet.toString();
			String brandCode = (String) task.get("brandCode");
			for (Element item : itemEls) {
				ShopVo shopVo = new ShopVo();
				shopVo.setShopUrl(item.attr("href"));
				shopVo.setShopName(item.select("h3").first().text().trim());
				parserType(shopVo);
				shopVo.setBrandName(mainBrand);
				shopVo.setBrandUrl(url);
				shopVo.setBrandCode(brandCode == null ? getHashCode(mainBrand) : brandCode);
				shopVo.setSynonyms(synonym);
				shopVo.setRegion(region);
				rsBean.getDataList().add(shopVo);
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		mapper.writeValue(writer, rsBean);
		return new JSONObject(writer.toString());
	}

	private String getHashCode(String mainBrand) {
		String hCode = "" + mainBrand.hashCode();
		return hCode.replace("-", "H");
	}

	private String getMainBrandName(Set<String> brandSet) {
		Pattern oReg = Pattern.compile(".*?[\u4e00-\u9fa5]+.*");
		String main = null;
		for (String brand : brandSet) {
			Matcher matcher = oReg.matcher(brand);
			if (matcher.find()) {
				if (main == null) {
					main = brand;
				} else if (main.length() < brand.length()) {
					main = brand;
				}
			}
		}
		if (main == null) {
			for (String brand : brandSet) {
				if (main == null) {
					main = brand;
				} else if (main.length() < brand.length()) {
					main = brand;
				}
			}
		}
		return main;
	}

	private String getRegion(Document dom) {
		Elements destEls = dom.select("div.brandWiki ul.brandWiki-con li:contains(发源地) em");
		if (!destEls.isEmpty()) {
			return destEls.first().ownText().trim();
		}
		return null;
	}

	private Set<String> getBrandSet(Document dom, TaskWritable task) {
		Elements destEls = dom.select("div.brandWiki ul.brandWiki-con li:contains(品牌名) em");
		String sText = destEls.first().ownText();
		// Bejirog/北极绒
		Set<String> brandSet = new HashSet<String>();
		String[] sArr = sText.split("/");
		for (String sUnit : sArr) {
			brandSet.add(sUnit.toLowerCase());
		}
		String brandName = (String) task.get("brandName");
		if (StringUtils.isNotEmpty(brandName)) {
			// 夏新(Amoi)
			int fromIndex = brandName.indexOf("(");
			if (fromIndex < 0) {
				sArr = brandName.split("/");
				for (String sUnit : sArr) {
					brandSet.add(sUnit.toLowerCase());
				}
			} else {
				int toIndex = brandName.indexOf(")");
				String leftString = brandName.substring(0, fromIndex);
				brandSet.add(leftString.toLowerCase());
				String rightString = brandName.substring(fromIndex + 1, toIndex);
				brandSet.add(rightString.toLowerCase());
			}
		}
		return brandSet;
	}

	private void parserType(ShopVo shopVo) {
		// 0-旗舰店，1-专卖店，2-专营店，3-其他
		if (StringUtils.isEmpty(shopVo.getShopName())) {
			shopVo.setShopType(3);
		} else if (shopVo.getShopName().indexOf("旗舰") > 0) {
			shopVo.setShopType(0);
		} else if (shopVo.getShopName().indexOf("专卖") > 0) {
			shopVo.setShopType(1);
		} else if (shopVo.getShopName().indexOf("专营") > 0) {
			shopVo.setShopType(2);
		}
	}

	private void doCollect(JSONObject dataObject, TaskWritable task) {
		JSONObject gObject = new JSONObject();
		JSONObject argsObject = new JSONObject(task.getArgs());
		JSONUtils.put(argsObject, "name@client", HeaderUtils.CLIENT_NAME);

		JSONArray tArray = new JSONArray();
		tArray.put("BrandConfigVo");
		tArray.put("BrandShopDto");
		JSONUtils.put(argsObject, "target", tArray);
		JSONUtils.put(gObject, "args", argsObject);

		JSONUtils.put(gObject, "rs", dataObject.toString());
		System.err.println("data:" + dataObject);
		List<JSONObject> dataList = new ArrayList<JSONObject>();
		dataList.add(gObject);
		PersistentCollector.getInstance().getBufferWriter().write(dataList);
	}

	private static class ShopVo {
		private Integer siteId = SITE_ID;
		private String brandCode;
		private String brandName;
		private String brandUrl;
		private String synonyms;
		private String region;
		// private Date createTime;
		// private Date updateTime;
		private String shopName;
		private String shopUrl;
		private Integer shopType = 3;

		public String getShopName() {
			return shopName;
		}

		public void setShopName(String shopName) {
			this.shopName = shopName;
		}

		public String getShopUrl() {
			return shopUrl;
		}

		public void setShopUrl(String shopUrl) {
			this.shopUrl = shopUrl;
		}

		public Integer getShopType() {
			return shopType;
		}

		public void setShopType(Integer shopType) {
			this.shopType = shopType;
		}

		public Integer getSiteId() {
			return siteId;
		}

		public void setSiteId(Integer siteId) {
			this.siteId = siteId;
		}

		public String getBrandCode() {
			return brandCode;
		}

		public void setBrandCode(String brandCode) {
			this.brandCode = brandCode;
		}

		public String getBrandName() {
			return brandName;
		}

		public void setBrandName(String brandName) {
			this.brandName = brandName;
		}

		public String getBrandUrl() {
			return brandUrl;
		}

		public void setBrandUrl(String brandUrl) {
			this.brandUrl = brandUrl;
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public String getSynonyms() {
			return synonyms;
		}

		public void setSynonyms(String synonyms) {
			this.synonyms = synonyms;
		}
	}

	private final static class ResultBean {
		private List<Object> dataList = new ArrayList<Object>();
		private List<Object> nextList = new ArrayList<Object>();

		public List<Object> getDataList() {
			return dataList;
		}

		public void setDataList(List<Object> dataList) {
			this.dataList = dataList;
		}

		public List<Object> getNextList() {
			return nextList;
		}

		public void setNextList(List<Object> nextList) {
			this.nextList = nextList;
		}

	}
}