package com.lezo.iscript.yeam.config;

import org.json.JSONObject;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lezo.iscript.utils.JSONUtils;
import com.lezo.iscript.yeam.config.strategy.BarCodeStrategy;
import com.lezo.iscript.yeam.config.strategy.PromotionStrategy;
import com.lezo.iscript.yeam.config.strategy.ProxyCollectorStrategy;
import com.lezo.iscript.yeam.config.strategy.YhdCollectorStrategy;
import com.lezo.iscript.yeam.strategy.ResultStrategy;
import com.lezo.iscript.yeam.writable.ResultWritable;

public class ConfigResultStrategyTest {

	public static void main(String[] args) throws Exception {
		String[] configs = new String[] { "classpath:spring-config-ds.xml" };
		ApplicationContext cx = new ClassPathXmlApplicationContext(configs);
		ConfigResultStrategyTest strategyTest = new ConfigResultStrategyTest();
		// strategyTest.testBarCodeStrategy();
		// strategyTest.testProxyCollectorStrategy();
		strategyTest.testYhdCollectorStrategy();
	}

	public void testBarCodeStrategy() throws Exception {

		String type = "Config1688Product";
		BarCodeStrategy barCodeStrategy = new BarCodeStrategy();
		ResultWritable rWritable = new ResultWritable();
		rWritable.setStatus(ResultWritable.RESULT_SUCCESS);
		String result = "{\"特产\":\"是\",\"地方风味\":\"其他风味\",\"生产日期\":\"最新\",\"保质期\":\"365（天）\",\"原料与配料\":\"芒果干\",\"有无中文标签\":\"有\",\"售卖方式\":\"包装\",\"生产厂家\":\"Mangoes,Sugar and Sodium Me\",\"原产地\":\"菲律宾\",\"包装规格\":\"芒果干100g/包\",\"name\":\"进口食品菲律宾芒果干7D芒果干100克防伪新包装*100包一箱\",\"barCode\":\"4809010272010\",\"等级\":\"A\",\"brand\":\"7D\",\"产品类别\":\"果干\",\"是否进口\":\"是\",\"净含量（规格）\":\"100（g）\",\"储藏方法\":\"避光干燥\",\"加工工艺\":\"果干类\"}";
		JSONObject rsObject = new JSONObject();
		JSONObject argsObject = new JSONObject();
		JSONUtils.put(argsObject, "url", "http://detail.1688.com/offer/37687586366.html");
		JSONUtils.put(argsObject, "level", 0);
		JSONUtils.put(argsObject, "type", type);
		JSONUtils.put(rsObject, "rs", result);
		JSONUtils.put(rsObject, "args", argsObject);
		rWritable.setResult(rsObject.toString());
		rWritable.setType(type);
		barCodeStrategy.handleResult(rWritable);
	}

	public void testProxyCollectorStrategy() throws Exception {

		String type = "ConfigProxyCollector";
		ProxyCollectorStrategy proxyCollectorStrategy = new ProxyCollectorStrategy();
		ResultWritable rWritable = new ResultWritable();
		rWritable.setStatus(ResultWritable.RESULT_SUCCESS);
		String result = "{\"proxys\":[{\"port\":\"7808\",\"ip\":\"185.49.15.25\"},{\"port\":\"7808\",\"ip\":\"92.222.153.153\"},{\"port\":\"3127\",\"ip\":\"188.241.141.112\"},{\"port\":\"7808\",\"ip\":\"23.89.198.161\"},{\"port\":\"7808\",\"ip\":\"62.244.31.16\"},{\"port\":\"7808\",\"ip\":\"107.182.16.221\"},{\"port\":\"7808\",\"ip\":\"93.115.8.229\"},{\"port\":\"8089\",\"ip\":\"198.52.199.152\"},{\"port\":\"3127\",\"ip\":\"198.52.217.44\"},{\"port\":\"8080\",\"ip\":\"195.175.201.170\"},{\"port\":\"3127\",\"ip\":\"192.227.139.227\"},{\"port\":\"3127\",\"ip\":\"107.182.135.43\"},{\"port\":\"3127\",\"ip\":\"199.200.120.140\"},{\"port\":\"3128\",\"ip\":\"41.74.79.136\"},{\"port\":\"3128\",\"ip\":\"210.212.97.179\"},{\"port\":\"3128\",\"ip\":\"202.29.243.36\"},{\"port\":\"7808\",\"ip\":\"199.241.137.180\"},{\"port\":\"3127\",\"ip\":\"184.105.18.60\"},{\"port\":\"3128\",\"ip\":\"200.135.250.130\"},{\"port\":\"80\",\"ip\":\"196.29.140.130\"}],\"nexts\":[\"http://www.cool-proxy.net/proxies/http_proxy_list/sort:score/direction:desc/page:2\"]}";
		JSONObject rsObject = new JSONObject();
		JSONObject argsObject = new JSONObject();
		JSONUtils.put(argsObject, "url", "http://detail.1688.com/offer/37687586366.html");
		JSONUtils.put(argsObject, "level", 0);
		JSONUtils.put(argsObject, "type", type);
		JSONUtils.put(rsObject, "rs", result);
		JSONUtils.put(rsObject, "args", argsObject);
		rWritable.setResult(rsObject.toString());
		rWritable.setType(type);
		proxyCollectorStrategy.handleResult(rWritable);
	}

	public void testYhdCollectorStrategy() throws Exception {
		String type = "ConfigYhdCategory";
		// type = "ConfigYhdProduct";
		YhdCollectorStrategy strategy = new YhdCollectorStrategy();
		ResultWritable rWritable = new ResultWritable();
		rWritable.setStatus(ResultWritable.RESULT_SUCCESS);
		String result = "{\"list\":[{\"commentNum\":\"802\",\"marketPrice\":188,\"stockNum\":195,\"productCode\":18505771,\"promotPrice\":128,\"productUrl\":\"http://item.yhd.com/item/18505771\",\"yhdPrice\":165,\"productPrice\":128,\"productName\":\"Ferrero费列罗 榛果威化巧克力3粒16条装 意大利进口 该商品周一至周五15点30分之前订单可当天发货 周末订单下周一发货\",\"productId\":\"16055232\"},{\"commentNum\":\"752\",\"marketPrice\":25,\"stockNum\":90,\"productCode\":15562490,\"promotPrice\":22.5,\"productUrl\":\"http://item.yhd.com/item/15562490\",\"yhdPrice\":23.9,\"productPrice\":22.5,\"productName\":\"Kinder 健达 奇趣蛋 可可球及牛奶可可酱 60g 印度进口 该商品周一至周五17点之前订单可当天发货 周末订单下周一发货 文描更新升级中 玩具随机发送\",\"productId\":\"13784410\"},{\"commentNum\":\"482\",\"marketPrice\":118,\"stockNum\":1420,\"productCode\":6534749,\"promotPrice\":59.9,\"productUrl\":\"http://item.yhd.com/item/6534749\",\"yhdPrice\":99,\"productPrice\":59.9,\"productName\":\"Truffles德菲丝 浓郁奶香系列 800g 比利时进口 该商品周一至周五15点30分之前订单可当天发货 周末订单下周一发货 黑猫宅急便全程冷链配送\",\"productId\":\"5512526\"},{\"commentNum\":\"11725\",\"marketPrice\":15,\"stockNum\":4653,\"productCode\":1845898,\"promotPrice\":7.9,\"productUrl\":\"http://item.yhd.com/item/1845898\",\"yhdPrice\":13.9,\"productPrice\":7.9,\"productName\":\"Toblerone瑞士三角 黑巧克力含蜂蜜及奶油杏仁 50g 瑞士进口 该商品周一至周五15点30分之前订单可当天发货 周末订单下周一发货\",\"productId\":\"1623095\"},{\"commentNum\":\"98\",\"marketPrice\":80,\"stockNum\":292,\"productCode\":31928711,\"promotPrice\":36,\"productUrl\":\"http://item.yhd.com/item/31928711\",\"yhdPrice\":56,\"productPrice\":36,\"productName\":\"如胜 送冰袋 黑巧克力 乌克兰 进口巧克力 经典黑巧克力 盒装 100g*2 进口食品 巧克力 零食 下午茶\",\"productId\":\"27178872\"},{\"commentNum\":\"93\",\"marketPrice\":208,\"stockNum\":1776,\"productCode\":8749344,\"promotPrice\":98,\"productUrl\":\"http://item.yhd.com/item/8749344\",\"yhdPrice\":108,\"productPrice\":98,\"productName\":\"[包邮] 天之星 瑞士进口Chocolat Stella77%无糖纯黑巧克力100gX2片保护心血管 顺丰发货 无糖黑巧克力，健康，好吃不怕胖\",\"productId\":\"7610968\"},{\"commentNum\":\"107\",\"marketPrice\":9.9,\"stockNum\":940,\"productCode\":17278672,\"promotPrice\":9.5,\"productUrl\":\"http://item.yhd.com/item/17278672\",\"yhdPrice\":9.8,\"productPrice\":9.5,\"productName\":\"乐天 LOTTE 黑加纳纯黑巧克力90g 韩国人气休闲零食品 进口糖果 方块 独立包装 黑巧克力 不增肥 一盒有18块 独立小包装\",\"productId\":\"15140314\"},{\"commentNum\":\"3508\",\"marketPrice\":24.8,\"stockNum\":77,\"productCode\":104794,\"promotPrice\":0,\"productUrl\":\"http://item.yhd.com/item/104794\",\"yhdPrice\":23.9,\"productPrice\":23.9,\"productName\":\"Lindt瑞士莲 经典排装纯味黑巧克力 100g 瑞士进口 进口食品 进口糖果 巧克力 节日必备 送礼 零食 下午茶\",\"productId\":\"38939\"},{\"commentNum\":\"129\",\"marketPrice\":138,\"stockNum\":363,\"productCode\":14588807,\"promotPrice\":72,\"productUrl\":\"http://item.yhd.com/item/14588807\",\"yhdPrice\":138,\"productPrice\":72,\"productName\":\"[包邮] 吉利莲 金贝壳巧克力礼盒250g 11种独创贝壳形状 100％纯可可脂 绝不含代可可脂 15年3月到期 新鲜美味，满99减5 满199减20\",\"productId\":\"13126831\"},{\"commentNum\":\"425\",\"marketPrice\":5.9,\"stockNum\":6963,\"productCode\":23725977,\"promotPrice\":2.2,\"productUrl\":\"http://item.yhd.com/item/23725977\",\"yhdPrice\":5.9,\"productPrice\":2.2,\"productName\":\"Ferrero/费列罗 金莎榛子仁巧克力 散装1颗粒 结婚 婚庆装喜糖专用 婚庆结婚 喜糖 多买整盒包装发 防止压坏\",\"productId\":\"20101662\"},{\"commentNum\":\"7358\",\"marketPrice\":22.9,\"stockNum\":9818,\"productCode\":974803,\"promotPrice\":10.9,\"productUrl\":\"http://item.yhd.com/item/974803\",\"yhdPrice\":22.9,\"productPrice\":10.9,\"productName\":\"Toblerone瑞士三角 牛奶巧克力含蜂蜜及奶油杏仁 100g 瑞士进口 该商品周一至周五15点30分之前订单可当天发货 周末订单下周一发货\",\"productId\":\"958746\"},{\"commentNum\":\"93\",\"marketPrice\":42.9,\"stockNum\":3229,\"productCode\":8007280,\"promotPrice\":21.9,\"productUrl\":\"http://item.yhd.com/item/8007280\",\"yhdPrice\":39,\"productPrice\":21.9,\"productName\":\"Truffles德菲丝 馥郁浓醇系列巧克力 250g 法国进口 该商品周一至周五15点30分之前订单可当天发货 周末订单下周一发货 黑猫宅急便全程冷链配送\",\"productId\":\"6916870\"},{\"commentNum\":\"765\",\"marketPrice\":75,\"stockNum\":294,\"productCode\":104778,\"promotPrice\":0,\"productUrl\":\"http://item.yhd.com/item/104778\",\"yhdPrice\":66.9,\"productPrice\":66.9,\"productName\":\"Lindt瑞士莲 经典薄片黑巧克力 125g 瑞士进口 进口食品 进口糖果 巧克力 节日必备 送礼 零食 下午茶\",\"productId\":\"38955\"},{\"commentNum\":\"132\",\"marketPrice\":39,\"stockNum\":212,\"productCode\":7286446,\"promotPrice\":19.9,\"productUrl\":\"http://item.yhd.com/item/7286446\",\"yhdPrice\":37.6,\"productPrice\":19.9,\"productName\":\"Ritter Sport/瑞特斯波德 德国进口黑巧克力 可可含量为73% 浓醇黑巧克力 100g七夕情人节礼物 礼品 3件9折专区 限时限量 德国高品质 店铺爆款\",\"productId\":\"6226306\"},{\"commentNum\":\"163\",\"marketPrice\":168,\"stockNum\":1546,\"productCode\":6856698,\"promotPrice\":99,\"productUrl\":\"http://item.yhd.com/item/6856698\",\"yhdPrice\":138,\"productPrice\":99,\"productName\":\"[包邮] 爱妃 Althaea比利时原装进口贝壳巧克力 礼盒装250g代写生日祝福卡片 七夕情人节婚庆新年圣诞节高档礼盒装包邮\",\"productId\":\"5822301\"},{\"commentNum\":\"77\",\"marketPrice\":158,\"stockNum\":7542,\"productCode\":29297402,\"promotPrice\":98,\"productUrl\":\"http://item.yhd.com/item/29297402\",\"yhdPrice\":118,\"productPrice\":98,\"productName\":\"[包邮] 德芙 巧克力碗装*3碗 榛仁/黑巧/牛奶巧克力碗装14g*18片/份 全新包装新日期 榛仁碗装*1、黑巧碗装*1份、榛仁碗装*1份\",\"productId\":\"24957534\"},{\"commentNum\":\"2579\",\"marketPrice\":28,\"stockNum\":108,\"productCode\":7602112,\"promotPrice\":24.5,\"productUrl\":\"http://item.yhd.com/item/7602112\",\"yhdPrice\":25.9,\"productPrice\":24.5,\"productName\":\"Kinder健达 巧克力迷你型 138g 23粒装 德国进口 该商品周一至周五15点30分之前订单可当天发货 周末订单下周一发货\",\"productId\":\"6529402\"},{\"commentNum\":\"15\",\"marketPrice\":133,\"stockNum\":489,\"productCode\":18221094,\"promotPrice\":72,\"productUrl\":\"http://item.yhd.com/item/18221094\",\"yhdPrice\":75,\"productPrice\":72,\"productName\":\"[包邮] 吉利莲 贝壳浓黑巧克力礼盒250g 比利时进口休闲零食品 生日节日礼盒 74%可可含量 年中大促，满99立减5元，满199立减20元\",\"productId\":\"15847803\"},{\"commentNum\":\"181\",\"marketPrice\":168,\"stockNum\":23,\"productCode\":7602098,\"promotPrice\":130,\"productUrl\":\"http://item.yhd.com/item/7602098\",\"yhdPrice\":158,\"productPrice\":130,\"productName\":\"Ferrero费列罗 榛果威化巧克力 400g 32粒装 意大利进口 该商品周一至周五15点30分之前订单可当天发货 周末订单下周一发货\",\"productId\":\"6529393\"},{\"commentNum\":\"1263\",\"marketPrice\":428,\"stockNum\":4878,\"productCode\":6260021,\"promotPrice\":149.8,\"productUrl\":\"http://item.yhd.com/item/6260021\",\"yhdPrice\":371.9,\"productPrice\":149.8,\"productName\":\"[包邮] Lindt/瑞士莲 顺丰美国进口Lindt混装软心松露巧克力球 600g 约50粒 5种口味混合 婚庆喜糖 顺丰免运费 加冰袋和泡沫盒\",\"productId\":\"5259750\"},{\"commentNum\":\"116\",\"marketPrice\":81,\"stockNum\":592,\"productCode\":4990502,\"promotPrice\":58,\"productUrl\":\"http://item.yhd.com/item/4990502\",\"yhdPrice\":70,\"productPrice\":58,\"productName\":\"[包邮] 宝瑞淇 X5 花生夹心巧克力棒36g*16支 大满足 韩国进口零食 因进货价格上调，由原先的18支，更改为16支\",\"productId\":\"4926252\"},{\"commentNum\":\"12\",\"marketPrice\":177,\"stockNum\":51,\"productCode\":33044370,\"promotPrice\":0,\"productUrl\":\"http://item.yhd.com/item/33044370\",\"yhdPrice\":177,\"productPrice\":177,\"productName\":\"[包邮] 劳士 德国原装进口 480g*3盒 纯可可 黑巧克力60%70%75%80% 礼盒 独立 包装\",\"productId\":\"28100080\"},{\"commentNum\":\"189\",\"marketPrice\":88,\"stockNum\":227,\"productCode\":7286630,\"promotPrice\":34,\"productUrl\":\"http://item.yhd.com/item/7286630\",\"yhdPrice\":76,\"productPrice\":34,\"productName\":\"Ritter Sport/瑞特斯波德 德国进口巧克力 7种口味 迷你七彩什锦巧克力 150g/袋装 七夕情人节巧克力 加送冰袋 德国高品质巧克力 店铺爆款\",\"productId\":\"6226490\"},{\"commentNum\":\"69\",\"marketPrice\":188,\"stockNum\":853,\"productCode\":4675777,\"promotPrice\":128,\"productUrl\":\"http://item.yhd.com/item/4675777\",\"yhdPrice\":158,\"productPrice\":128,\"productName\":\"[包邮] 迪克多 比利时进口经典木盒酒心黑巧克力250g 酒心巧克力礼盒 顺丰包邮 圣诞节礼盒 内含五种世界名酒\",\"productId\":\"4587536\"},{\"commentNum\":\"73\",\"marketPrice\":118,\"stockNum\":1817,\"productCode\":6534751,\"promotPrice\":49.5,\"productUrl\":\"http://item.yhd.com/item/6534751\",\"yhdPrice\":99,\"productPrice\":49.5,\"productName\":\"Truffles德菲丝 浪漫慕斯系列 800g 比利时进口 该商品周一至周五15点30分之前订单可当天发货 周末订单下周一发货 黑猫宅急便全程冷链配送\",\"productId\":\"5512527\"},{\"commentNum\":\"39\",\"marketPrice\":70,\"stockNum\":1224,\"productCode\":8274674,\"promotPrice\":45,\"productUrl\":\"http://item.yhd.com/item/8274674\",\"yhdPrice\":60,\"productPrice\":45,\"productName\":\"天之星 瑞士进口Chocolat Stella 100%纯黑巧克力70g 超苦\",\"productId\":\"7170260\"},{\"commentNum\":\"12\",\"marketPrice\":48,\"stockNum\":638,\"productCode\":5305562,\"promotPrice\":18.8,\"productUrl\":\"http://item.yhd.com/item/5305562\",\"yhdPrice\":19.8,\"productPrice\":18.8,\"productName\":\"赫蒂 85%可可 特浓纯黑巧克力80g 罗马尼亚原装进口 进口品质，最美味，口味出众\",\"productId\":\"5008832\"},{\"commentNum\":\"307\",\"marketPrice\":133,\"stockNum\":923,\"productCode\":14910763,\"promotPrice\":72,\"productUrl\":\"http://item.yhd.com/item/14910763\",\"yhdPrice\":133,\"productPrice\":72,\"productName\":\"[包邮] 吉利莲 贝壳巧克力礼盒250g 比利时进口 品尝经典贝壳巧克力的醇香风味 年中大促，满99立减5元，满199立减20元\",\"productId\":\"13364769\"},{\"commentNum\":\"14\",\"marketPrice\":28.5,\"stockNum\":1411,\"productCode\":19952501,\"promotPrice\":0,\"productUrl\":\"http://item.yhd.com/item/19952501\",\"yhdPrice\":22.8,\"productPrice\":22.8,\"productName\":\"1895薇瑞驰 85%可可含量 黑巧克力 100克 德国进口 七夕情人节\",\"productId\":\"17158348\"},{\"commentNum\":\"12\",\"marketPrice\":35,\"stockNum\":4,\"productCode\":16615993,\"promotPrice\":0,\"productUrl\":\"http://item.yhd.com/item/16615993\",\"yhdPrice\":14.8,\"productPrice\":14.8,\"productName\":\"Hershey's/好时 175g Reese's 锐滋 美国原产 花生牛奶巧克力棒 儿童零食 橙色风暴 美国巧克力品牌 好吃又带劲\",\"productId\":\"14605254\"},{\"commentNum\":\"138\",\"marketPrice\":140,\"stockNum\":4923,\"productCode\":18079413,\"promotPrice\":68,\"productUrl\":\"http://item.yhd.com/item/18079413\",\"yhdPrice\":136,\"productPrice\":68,\"productName\":\"[包邮] 如胜 乌克兰进口巧克力礼盒 蒙特里尼樱桃伏特加196g+白兰地酒心巧克力192g 七夕情人节礼盒 礼品 送礼必备组合装\",\"productId\":\"15748343\"},{\"commentNum\":\"27\",\"marketPrice\":16,\"stockNum\":85,\"productCode\":9652470,\"promotPrice\":8.9,\"productUrl\":\"http://item.yhd.com/item/9652470\",\"yhdPrice\":10.6,\"productPrice\":10.6,\"productName\":\"Lotte/乐天 韩国 56%梦幻纯黑巧克力90g 进口零食\",\"productId\":\"8483046\"},{\"commentNum\":\"17\",\"marketPrice\":25,\"stockNum\":137,\"productCode\":4609570,\"promotPrice\":15.8,\"productUrl\":\"http://item.yhd.com/item/4609570\",\"yhdPrice\":18.8,\"productPrice\":15.8,\"productName\":\"Ritter Sport/瑞特斯波德 德国进口 阿尔卑斯牛奶巧克力100g\",\"productId\":\"4520338\"},{\"commentNum\":\"21\",\"marketPrice\":132,\"stockNum\":494,\"productCode\":26373635,\"promotPrice\":59.9,\"productUrl\":\"http://item.yhd.com/item/26373635\",\"yhdPrice\":132,\"productPrice\":59.9,\"productName\":\"[包邮] Toblerone 瑞士三角原装进口牛奶巧克力100G*6支量贩装 含蜂蜜奶油杏仁糖 进口食品\",\"productId\":\"22410618\"},{\"commentNum\":\"96\",\"marketPrice\":80,\"stockNum\":326,\"productCode\":31930307,\"promotPrice\":32,\"productUrl\":\"http://item.yhd.com/item/31930307\",\"yhdPrice\":50,\"productPrice\":32,\"productName\":\"如胜 送冰袋 白巧克力 乌克兰 进口巧克力 深色牛奶充气巧克力 盒装 100g*2 进口食品 巧克力 零食 下午茶\",\"productId\":\"27180411\"},{\"commentNum\":\"28\",\"marketPrice\":48,\"stockNum\":245,\"productCode\":19021313,\"promotPrice\":0,\"productUrl\":\"http://item.yhd.com/item/19021313\",\"yhdPrice\":19.8,\"productPrice\":19.8,\"productName\":\"赫蒂 特黑巧克力 80g 含85%可可固形物 瑞士品牌 原装进口手工休闲零食品 购买赫蒂多种口味 5盒全国包邮\",\"productId\":\"16450855\"}],\"nexts\":[\"http://www.yhd.com/ctg/s2/c33827-0//#page=2&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=3&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=4&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=5&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=6&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=7&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=8&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=9&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=10&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=11&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=12&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=13&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=14&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=15&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=16&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=17&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=18&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=19&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=20&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=21&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=22&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=23&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=24&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=25&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=26&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=27&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=28&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=29&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=30&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=31&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=32&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=33&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=34&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=35&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=36&sort=2\",\"http://www.yhd.com/ctg/s2/c33827-0//#page=37&sort=2\"]}";
		result = "[{\"name\":\"进口食品、进口牛奶\",\"children\":[{\"name\":\"进口牛奶乳品\",\"children\":[{\"name\":\"进口牛奶\",\"url\":\"http://www.yhd.com/ctg/s2/c22882-0/\"},{\"name\":\"脱脂牛奶\",\"url\":\"http://www.yhd.com/ctg/s2/c22882-0-59582/b/a119274-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k/?tp=15.22882.107.0.52.Rec4SD\"},{\"name\":\"低脂牛奶\",\"url\":\"http://www.yhd.com/ctg/s2/c22882-0-59582/b/a119275-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k/?tp=15.22882.107.0.73.Rec5LO\"},{\"name\":\"全脂牛奶\",\"url\":\"http://www.yhd.com/ctg/s2/c22882-0-59582/b/a119276-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k/?tp=15.22882.107.0.44.RecFWv\"},{\"name\":\"德国牛奶\",\"url\":\"http://www.yhd.com/ctg/s2/c22882-0/b/a101419-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k/?tp=15.22882.107.0.22.RemfWs\"},{\"name\":\"新西兰牛奶\",\"url\":\"http://www.yhd.com/ctg/s2/c22882-0/b/a101446-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k/?tp=15.22882.107.0.34.Res|Vz\"},{\"name\":\"澳大利亚牛奶\",\"url\":\"http://www.yhd.com/ctg/s2/c22882-0-59582/b/a110626-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k/?tp=15.22882.107.0.11.ReuSzZ\"},{\"name\":\"法国牛奶\",\"url\":\"http://www.yhd.com/ctg/s2/c22882-0-59582/b/a101418-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k/?tp=15.22882.107.0.25.ResWg7\"},{\"name\":\"酸奶\",\"url\":\"http://www.yhd.com/ctg/s2/c22884-0/\"},{\"name\":\"婴儿奶粉\",\"url\":\"http://www.yhd.com/ctg/s2/vc1730\"},{\"name\":\"成人奶粉\",\"url\":\"http://www.yihaodian.com/ctg/s2/c22886-0/\"},{\"name\":\"酸奶DIY\",\"url\":\"http://search.yhd.com/themeBuy.do?themeId=633\"}],\"url\":\"http://channel.yhd.com/jinkou/\"},{\"name\":\"进口酒\",\"children\":[{\"name\":\"进口啤酒\",\"url\":\"http://www.yhd.com/ctg/s2/c22971-0/\"},{\"name\":\"德国啤酒\",\"url\":\"http://www.yhd.com/ctg/s2/c0-0/b/a101419-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k%E5%BE%B7%E5%9B%BD%E5%95%A4%E9%85%92/\"},{\"name\":\"韩式烧酒\",\"url\":\"http://www.yhd.com/ctg/s2/c33930-0-59612/b/a101408-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k/?tp=15.33930.107.0.32.TNE1Va\"},{\"name\":\"日式清酒\",\"url\":\"http://www.yhd.com/ctg/s2/c33930-0-59612/b/a101410-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k/?tp=15.33930.107.0.31.TNDZeq\"},{\"name\":\"进口葡萄酒\",\"url\":\"http://www.yhd.com/ctg/s2/c33945-0/\"},{\"name\":\"进口红酒\",\"url\":\"http://www.yhd.com/ctg/s2/c33947-0/\"},{\"name\":\"进口起泡酒/香槟\",\"url\":\"http://www.yhd.com/ctg/s2/c33949-0/\"},{\"name\":\"进口洋酒\",\"url\":\"http://www.yhd.com/ctg/s2/c33930-0/\"},{\"name\":\"伏特加\",\"url\":\"http://www.yhd.com/ctg/s2/c33933-0/\"},{\"name\":\"威士忌\",\"url\":\"http://www.yhd.com/ctg/s2/c33931-0/\"},{\"name\":\"白兰地\",\"url\":\"http://www.yhd.com/ctg/s2/c33932-0/\"},{\"name\":\"伏特加\",\"url\":\"http://www.yhd.com/ctg/s2/c33933-0/\"}],\"url\":\"http://channel.yhd.com/jinkou/\"},{\"name\":\"进口粮油\",\"children\":[{\"name\":\"进口食用油\",\"url\":\"http://www.yhd.com/ctg/s2/c22838-0/\"},{\"name\":\"进口橄榄油\",\"url\":\"http://www.yhd.com/ctg/s2/c33837-0/\"},{\"name\":\"进口意面\",\"url\":\"http://www.yhd.com/ctg/s2/c22868-0/\"},{\"name\":\"进口大米\",\"url\":\"http://www.yhd.com/ctg/s2/c22866-0/\"},{\"name\":\"进口挂面\",\"url\":\"http://www.yhd.com/ctg/s2/c30467-0/\"},{\"name\":\"进口方便面\",\"url\":\"http://www.yhd.com/ctg/s2/c22869-0/\"},{\"name\":\"进口烘焙原料/辅料\",\"url\":\"http://www.yhd.com/ctg/s2/c33815-0/\"},{\"name\":\"进口米面制品\",\"url\":\"http://www.yhd.com/ctg/s2/c33818-0/\"},{\"name\":\"泰国香米\",\"url\":\"http://www.yhd.com/ctg/s2/c22866-0/b/a101413-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k/\"}],\"url\":\"http://channel.yhd.com/jinkou/\"},{\"name\":\"进口水/饮料\",\"children\":[{\"name\":\"进口饮用水\",\"url\":\"http://www.yhd.com/ctg/s2/c22854-0/\"},{\"name\":\"进口碳酸饮料\",\"url\":\"http://www.yhd.com/ctg/s2/c22857-0/\"},{\"name\":\"进口咖啡饮料\",\"url\":\"http://www.yhd.com/ctg/s2/c22859-0/\"},{\"name\":\"进口果蔬汁\",\"url\":\"http://www.yhd.com/ctg/s2/c33886-0/\"},{\"name\":\"进口果味饮料\",\"url\":\"http://www.yhd.com/ctg/s2/c33890-0/\"},{\"name\":\"进口茶饮料\",\"url\":\"http://www.yhd.com/ctg/s2/c22856-0/\"},{\"name\":\"进口含乳饮料\",\"url\":\"http://www.yhd.com/ctg/s2/c33891-0/\"},{\"name\":\"进口气泡水\",\"url\":\"http://www.yhd.com/ctg/s2/c22854-0/b/a116029-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k/?tp=15.22854.107.0.42.RmdQbe\"},{\"name\":\"星巴克\",\"url\":\"http://www.yhd.com/ctg/s2/c22859-0/b478-5464/?tp=15.22859.107.0.1.RmdwO0\"}],\"url\":\"http://channel.yhd.com/jinkou/\"},{\"name\":\"进口饼干/糕点\",\"children\":[{\"name\":\"进口饼干\",\"url\":\"http://www.yhd.com/ctg/s2/c33699-0/\"},{\"name\":\"进口曲奇\",\"url\":\"http://www.yhd.com/ctg/s2/c22889-0/\"},{\"name\":\"进口威化\",\"url\":\"http://www.yhd.com/ctg/s2/c22890-0/\"},{\"name\":\"进口糕点\",\"url\":\"http://www.yhd.com/ctg/s2/c33700-0/\"},{\"name\":\"进口苏打饼干\",\"url\":\"http://www.yhd.com/ctg/s2/c33980-0/\"},{\"name\":\"进口夹心饼干\",\"url\":\"http://www.yhd.com/ctg/s2/c33703-0/\"},{\"name\":\"松塔\",\"url\":\"http://search.yhd.com/s2/c0-0/k%25E6%259D%25BE%25E5%25A1%2594/5/\"},{\"name\":\"蓝罐\",\"url\":\"http://www.yhd.com/ctg/s2/c22887-0/b1347-3247/?tp=15.22887.107.0.2.RzDztO\"},{\"name\":\"EDO\",\"url\":\"http://www.yhd.com/ctg/s2/c22887-0/b947724-22361/?tp=15.22887.107.0.13.RzEIlR\"},{\"name\":\"莱家\",\"url\":\"http://www.yhd.com/ctg/s2/c22887-0/b951719-3546/?tp=15.22887.107.0.1.RzE1CW\"},{\"name\":\"Tipo\",\"url\":\"http://www.yhd.com/ctg/s2/c22887-0/b950978-5758/?tp=15.22887.107.0.5.RzELDo\"}],\"url\":\"http://channel.yhd.com/jinkou/\"},{\"name\":\"进口罐头调料\",\"children\":[{\"name\":\"进口意面酱\",\"url\":\"http://www.yhd.com/ctg/s2/c22871-0/\"},{\"name\":\"进口果酱\",\"url\":\"http://www.yhd.com/ctg/s2/c33921-0/\"},{\"name\":\"进口咖喱\",\"url\":\"http://www.yhd.com/ctg/s2/c33924-0/\"},{\"name\":\"进口调味油\",\"url\":\"http://www.yhd.com/ctg/s2/c33912-0/\"},{\"name\":\"进口调味酱\",\"url\":\"http://www.yhd.com/ctg/s2/c33901-0/\"},{\"name\":\"进口调味汁\",\"url\":\"http://www.yhd.com/ctg/s2/c33916-0/\"},{\"name\":\"进口调味粉\",\"url\":\"http://www.yhd.com/ctg/s2/c33893-0/\"},{\"name\":\"进口肉罐头\",\"url\":\"http://www.yhd.com/ctg/s2/c22878-0/\"},{\"name\":\"进口水产罐头\",\"url\":\"http://www.yhd.com/ctg/s2/c22877-0/\"},{\"name\":\"进口水果罐头\",\"url\":\"http://www.yhd.com/ctg/s2/c22879-0/\"}],\"url\":\"http://channel.yhd.com/jinkou/\"},{\"name\":\"进口冲饮谷物\",\"children\":[{\"name\":\"进口早餐谷物\",\"url\":\"http://www.yhd.com/ctg/s2/c22842-0/\"},{\"name\":\"进口蜂蜜\",\"url\":\"http://www.yhd.com/ctg/s2/c22843-0/\"},{\"name\":\"进口巧克力粉\",\"url\":\"http://www.yhd.com/ctg/s2/c33813-0/\"},{\"name\":\"进口柚子茶\",\"url\":\"http://www.yhd.com/ctg/s2/c22844-0/\"},{\"name\":\"进口天然粉\",\"url\":\"http://www.yhd.com/ctg/s2/c22845-0/\"},{\"name\":\"进口奶茶粉\",\"url\":\"http://www.yhd.com/ctg/s2/c33814-0/\"},{\"name\":\"进口豆奶/豆浆粉\",\"url\":\"http://www.yhd.com/ctg/s2/c33812-0/\"}],\"url\":\"http://channel.yhd.com/jinkou/\"},{\"name\":\"进口坚果/蜜饯\",\"children\":[{\"name\":\"进口坚果\",\"url\":\"http://www.yhd.com/ctg/s2/c33797-0/\"},{\"name\":\"进口果干\",\"url\":\"http://www.yhd.com/ctg/s2/c33723-0/\"},{\"name\":\"进口芒果干\",\"url\":\"http://www.yhd.com/ctg/s2/c33737-0/\"},{\"name\":\"进口开心果\",\"url\":\"http://www.yhd.com/ctg/s2/c33804-0/\"},{\"name\":\"进口腰果\",\"url\":\"http://www.yhd.com/ctg/s2/c33808-0/\"},{\"name\":\"进口蔓越莓干\",\"url\":\"http://www.yhd.com/ctg/s2/c33736-0/\"},{\"name\":\"进口扁杏仁/巴旦木\",\"url\":\"http://www.yhd.com/ctg/s2/c33807-0/\"},{\"name\":\"进口夏威夷果\",\"url\":\"http://www.yhd.com/ctg/s2/c33806-0/\"},{\"name\":\"进口花生\",\"url\":\"http://www.yhd.com/ctg/s2/c33803-0/\"},{\"name\":\"进口榴莲干\",\"url\":\"http://www.yhd.com/ctg/s2/c33735-0/\"},{\"name\":\"进口混合坚果\",\"url\":\"http://www.yhd.com/ctg/s2/c33811-0/\"}],\"url\":\"http://channel.yhd.com/jinkou/\"},{\"name\":\"进口生鲜\",\"children\":[{\"name\":\"1号生鲜\",\"url\":\"http://www.yhd.com/ctg/s2/vc1733\"},{\"name\":\"进口水果\",\"url\":\"http://www.yhd.com/ctg/s2/c33628-0/\"},{\"name\":\"进口海鲜\",\"url\":\"http://www.yhd.com/ctg/s2/vc1738\"},{\"name\":\"进口速冻\",\"url\":\"http://www.yhd.com/ctg/s2/vc1731\"},{\"name\":\"进口低温乳品\",\"url\":\"http://www.yhd.com/ctg/s2/c33620-0/k%25E8%25BF%259B%25E5%258F%25A3|/\"},{\"name\":\"进口鲜肉/肉制品\",\"url\":\"http://www.yhd.com/ctg/s2/vc1737\"},{\"name\":\"进口火腿\",\"url\":\"http://www.yhd.com/ctg/s2/vc1739\"},{\"name\":\"进口禽/蛋\",\"url\":\"http://www.yhd.com/ctg/s2/c33621-0/b/a100563-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k/\"},{\"name\":\"���口蔬菜\",\"url\":\"http://www.yhd.com/ctg/s2/vc1735\"},{\"name\":\"方便菜\",\"url\":\"http://www.yhd.com/ctg/s2/vc1732\"}],\"url\":\"http://channel.yhd.com/jinkou/\"},{\"name\":\"进口咖啡/茶叶\",\"children\":[{\"name\":\"速溶咖啡\",\"url\":\"http://www.yhd.com/ctg/s2/c22851-0/\"},{\"name\":\"咖啡豆\",\"url\":\"http://www.yhd.com/ctg/s2/c33856-0/\"},{\"name\":\"咖啡粉\",\"url\":\"http://www.yhd.com/ctg/s2/c33857-0/\"},{\"name\":\"白咖啡\",\"url\":\"http://www.yhd.com/ctg/s2/c22851-0/b/a116270-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k/?tp=15.22851.107.0.48.Rz0AVa\"},{\"name\":\"G7\",\"url\":\"http://www.yhd.com/ctg/s2/c22846-0-59485/b8592-2297/?tp=15.22846.107.0.4.Rz0O1V\"},{\"name\":\"旧街场\",\"url\":\"http://www.yhd.com/ctg/s2/c22846-0-59485/b940778-4442/?tp=15.22846.107.0.3.Rz0|vC\"},{\"name\":\"illy\",\"url\":\"http://www.yhd.com/ctg/s2/c22846-0-59485/b926597-2827/\"},{\"name\":\"ucc\",\"url\":\"http://www.yhd.com/ctg/s2/c22846-0-59485/b351-5921/\"},{\"name\":\"印尼\",\"url\":\"http://www.yhd.com/ctg/s2/c22846-0-59485/b/a101409-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k/?tp=15.22846.107.0.35.Rz4PL1\"},{\"name\":\"哥伦比亚\",\"url\":\"http://www.yhd.com/ctg/s2/c22846-0-59485/b/a101444-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k/?tp=15.22846.107.0.2.Rz4Tx5\"},{\"name\":\"红茶\",\"url\":\"http://www.yhd.com/ctg/s2/c22847-0/\"},{\"name\":\"锡兰红茶\",\"url\":\"http://www.yhd.com/ctg/s2/c22847-0/b/a127519-s1-v0-p1-price-d0-f0-m1-rt0-pid-mid0-k/?tp=15.22847.107.0.29.Rz5VUW\"},{\"name\":\"花果茶\",\"url\":\"http://www.yhd.com/ctg/s2/c22848-0/\"},{\"name\":\"AKBAR\",\"url\":\"http://www.yhd.com/ctg/s2/vc1122/c33859/b432-16356/a-s1-v0-p1-price-d0-mid0-f0?tp=15.33859.107.0.3.Rz6EjF\"},{\"name\":\"川宁\",\"url\":\"http://www.yhd.com/ctg/s2/c33859-0-59651/b937092-5901/?tp=15.33859.107.0.1.Rz66E|\"}],\"url\":\"http://channel.yhd.com/jinkou/\"},{\"name\":\"进口休闲零食\",\"children\":[{\"name\":\"进口膨化食品\",\"url\":\"http://www.yhd.com/ctg/s2/c33865-0/\"},{\"name\":\"进口薯片\",\"url\":\"http://www.yhd.com/ctg/s2/c33870-0/\"},{\"name\":\"进口糖果\",\"url\":\"http://www.yhd.com/ctg/s2/c34032-0/\"},{\"name\":\"进口硬糖\",\"url\":\"http://www.yhd.com/ctg/s2/c34033-0/\"},{\"name\":\"进口软糖\",\"url\":\"http://www.yhd.com/ctg/s2/c34034-0/\"},{\"name\":\"进口巧克力\",\"url\":\"http://www.yhd.com/ctg/s2/c33827-0/\"},{\"name\":\"进口果冻/布丁/果泥\",\"url\":\"http://www.yhd.com/ctg/s2/c33862-0/\"},{\"name\":\"进口肉干/豆干\",\"url\":\"http://www.yhd.com/ctg/s2/c33874-0/\"},{\"name\":\"进口海鲜零食\",\"url\":\"http://www.yhd.com/ctg/s2/c33880-0/\"}],\"url\":\"http://channel.yhd.com/jinkou/\"},{\"name\":\"进口营养品\",\"children\":[{\"name\":\"维生素钙\",\"url\":\"http://www.yhd.com/ctg/s2/c31625-0-69065/?tc=3.0.9.69065.2&tp=52.35808.100.0.2.Uix3jP\"},{\"name\":\"胶原蛋白\",\"url\":\"http://www.yhd.com/ctg/s2/c31800-0-69084/?tc=3.0.9.69084.3&tp=52.31625.100.0.3.UixGQu\"},{\"name\":\"左旋肉碱\",\"url\":\"http://www.yhd.com/ctg/s2/c31812-0-69087/?tc=3.0.9.69087.4&tp=52.31800.100.0.4.UixLpz\"},{\"name\":\"蛋白粉\",\"url\":\"http://www.yhd.com/ctg/s2/c31791-0-69089/?tc=3.0.9.69089.5&tp=52.31812.100.0.5.UixO%60Q\"},{\"name\":\"葡萄籽\",\"url\":\"http://www.yhd.com/ctg/s2/c31809-0-69095/?tc=3.0.9.69095.6&tp=52.31791.100.0.6.UixUfH\"},{\"name\":\"蓝莓\",\"url\":\"http://www.yhd.com/ctg/s2/c31879-0-69144/?tc=3.0.9.69144.12&tp=52.31626.100.0.12.UjedV%60\"},{\"name\":\"鱼油/卵磷脂\",\"url\":\"http://www.yhd.com/ctg/s2/c31853-0-69147/?tc=3.0.9.69147.13&tp=52.31879.100.0.13.UjejR2\"},{\"name\":\"美体瘦身\",\"url\":\"http://www.yhd.com/ctg/s2/c31807-0-69103/?tc=3.0.9.69103.7&tp=52.31809.100.0.7.UixYfE\"},{\"name\":\"美容养颜\",\"url\":\"http://www.yhd.com/ctg/s2/c31804-0-69115/?tc=3.0.9.69115.10&tp=52.31807.100.0.10.UixhWn\"},{\"name\":\"基础营养\",\"url\":\"http://www.yhd.com/ctg/s2/c31626-0-69128/?tc=3.0.9.69128.9&tp=52.31804.100.0.9.UixmR7\"}],\"url\":\"http://channel.yhd.com/jinkou/\"}],\"url\":\"http://channel.yhd.com/jinkou/\"}]";
		// result =
		// "{\"marketPrice\":118,\"productCode\":\"6534749\",\"stockNum\":1513,\"promotPrice\":59.9,\"yhdPrice\":99,\"brandName\":\"Truffles/德菲丝\",\"soldNum\":20,\"commentNum\":482,\"shopName\":\"1号店\",\"productUrl\":\"http://item.yhd.com/item/6534749\",\"shopUrl\":\"http://www.yhd.com/\",\"productName\":\"Truffles德菲丝 浓郁奶香系列 800g 比利时进口\",\"productPrice\":59.9}";
		JSONObject rsObject = new JSONObject();
		JSONObject argsObject = new JSONObject();
		JSONUtils.put(argsObject, "url", "http://www.yhd.com/ctg/s2/c33827-0//#page=1&sort=2");
		JSONUtils.put(argsObject, "level", 0);
		JSONUtils.put(argsObject, "type", type);
		JSONUtils.put(argsObject, "strategy", strategy.getName());
		JSONUtils.put(rsObject, "rs", result);
		JSONUtils.put(rsObject, "args", argsObject);
		rWritable.setResult(rsObject.toString());
		rWritable.setType(type);
		strategy.handleResult(rWritable);
	}

	@Test
	public void testStrategy() throws Exception {
		String type = "ConfigJdPromotList";
		ResultStrategy strategy = new PromotionStrategy();
		ResultWritable rWritable = new ResultWritable();
		rWritable.setStatus(ResultWritable.RESULT_SUCCESS);
		String result = "{\"data\":[\"http://item.jd.com/182282.html\",\"http://item.jd.com/954227.html\",\"http://item.jd.com/810286.html\",\"http://item.jd.com/1026319.html\",\"http://item.jd.com/1031249.html\",\"http://item.jd.com/888214.html\",\"http://item.jd.com/867615.html\",\"http://item.jd.com/235440.html\",\"http://item.jd.com/1015634.html\",\"http://item.jd.com/953879.html\",\"http://item.jd.com/782199.html\",\"http://item.jd.com/726667.html\",\"http://item.jd.com/1136193.html\",\"http://item.jd.com/611648.html\",\"http://item.jd.com/318139.html\",\"http://item.jd.com/767466.html\",\"http://item.jd.com/947694.html\"],\"nexts\":[\"http://sale.jd.com/act/odFZ4qHOERbA70.html\",\"http://sale.jd.com/act/K4WxosIimTBQRq8.html\",\"http://xuan.jd.com/youhui/1-0-0-2-1.html\",\"http://xuan.jd.com/youhui/1-0-0-3-1.html\",\"http://xuan.jd.com/youhui/1-0-0-4-1.html\",\"http://xuan.jd.com/youhui/1-0-0-5-1.html\",\"http://xuan.jd.com/youhui/1-0-0-6-1.html\",\"http://xuan.jd.com/youhui/1-0-0-7-1.html\",\"http://xuan.jd.com/youhui/1-0-0-8-1.html\",\"http://xuan.jd.com/youhui/1-0-0-9-1.html\",\"http://xuan.jd.com/youhui/1-0-0-10-1.html\",\"http://xuan.jd.com/youhui/1-0-0-11-1.html\",\"http://xuan.jd.com/youhui/1-0-0-12-1.html\",\"http://xuan.jd.com/youhui/1-0-0-13-1.html\",\"http://xuan.jd.com/youhui/1-0-0-14-1.html\",\"http://xuan.jd.com/youhui/1-0-0-15-1.html\"]}";
		JSONObject rsObject = new JSONObject();
		JSONObject argsObject = new JSONObject();
		JSONUtils.put(argsObject, "url", "http://xuan.jd.com/youhui/1-0-0-0-1.html");
		JSONUtils.put(argsObject, "level", 0);
		JSONUtils.put(argsObject, "type", type);
		JSONUtils.put(argsObject, "strategy", strategy.getName());
		JSONUtils.put(rsObject, "rs", result);
		JSONUtils.put(rsObject, "args", argsObject);
		rWritable.setResult(rsObject.toString());
		rWritable.setType(type);
		strategy.handleResult(rWritable);
	}
}
