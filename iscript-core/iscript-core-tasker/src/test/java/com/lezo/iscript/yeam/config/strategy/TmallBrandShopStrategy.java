package com.lezo.iscript.yeam.config.strategy;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lezo.iscript.common.storage.StorageBuffer;
import com.lezo.iscript.common.storage.StorageBufferFactory;
import com.lezo.iscript.service.crawler.dto.TaskPriorityDto;
import com.lezo.iscript.service.crawler.service.TaskPriorityService;
import com.lezo.iscript.spring.context.SpringBeanUtils;
import com.lezo.iscript.utils.JSONUtils;
import com.lezo.iscript.yeam.strategy.ResultStrategy;
import com.lezo.iscript.yeam.task.TaskConstant;
import com.lezo.iscript.yeam.writable.ResultWritable;

public class TmallBrandShopStrategy implements ResultStrategy, Closeable {
	private static Logger logger = LoggerFactory.getLogger(TmallBrandShopStrategy.class);
	private static volatile boolean running = false;
	private Timer timer;

	public TmallBrandShopStrategy() {
		CreateTaskTimer task = new CreateTaskTimer();
		this.timer = new Timer("CreateTaskTimer");
		this.timer.schedule(task, 60 * 1000, 240 * 60 * 60 * 1000);
	}

	private class CreateTaskTimer extends TimerTask {
		private Map<String, Set<String>> typeMap;

		public CreateTaskTimer() {
			typeMap = new HashMap<String, Set<String>>();
			Set<String> urlSet = new HashSet<String>();
			urlSet.add("http://brand.tmall.com/brandMap.htm?spm=a3200.2192449.0.0.6OiqFL");
			for (int i = 65; i <= 90; i++) {
				char word = (char) i;
				urlSet.add("http://brand.tmall.com/azIndexInside.htm?firstLetter=" + word);
			}
			typeMap.put("ConfigTmallBrandList", urlSet);
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=100&categoryId=50025135&etgId=59");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=100&categoryId=50025174&etgId=58");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=100&categoryId=50023887&etgId=60");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=100&categoryId=50025983&etgId=61");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=109&categoryId=50025829&etgId=64");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=109&categoryId=50026637&etgId=63");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=109&categoryId=51052003&etgId=65");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=109&categoryId=51042006&etgId=66");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=109&categoryId=50072916&etgId=188");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=109&categoryId=50095658&etgId=68");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=111&categoryId=50108176&etgId=190");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=111&categoryId=50026474&etgId=74");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=111&categoryId=50026478&etgId=78");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=111&categoryId=50026461&etgId=80");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=111&categoryId=50023064&etgId=82");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=101&categoryId=50026502&etgId=70");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=101&categoryId=50026391&etgId=69");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=101&categoryId=50026506&etgId=73");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=101&categoryId=50026505&etgId=71");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=101&categoryId=50026426&etgId=72");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=101&categoryId=50026393&etgId=187");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=101&categoryId=50043479&etgId=138");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=110&categoryId=50020894&etgId=83");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=110&categoryId=50020909&etgId=84");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=110&categoryId=50043669&etgId=194");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=110&categoryId=50022787&etgId=195");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=108&categoryId=50024400&etgId=99");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=108&categoryId=50024399&etgId=100");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=108&categoryId=50024401&etgId=101");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=108&categoryId=50047403&etgId=103");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=108&categoryId=50047396&etgId=110");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=108&categoryId=50024407&etgId=102");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=108&categoryId=50024406&etgId=104");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=108&categoryId=50043917&etgId=105");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=108&categoryId=50099232&etgId=106");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=108&categoryId=50024410&etgId=107");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=108&categoryId=50094901&etgId=108");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=108&categoryId=50024411&etgId=109");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=103&categoryId=50900004&etgId=94");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=103&categoryId=50892008&etgId=95");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=103&categoryId=50902003&etgId=96");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=103&categoryId=50886005&etgId=97");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=103&categoryId=50894004&etgId=98");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=102&categoryId=50030787&etgId=111");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=102&categoryId=50067162&etgId=112");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=102&categoryId=50067174&etgId=113");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=102&categoryId=50051691&etgId=114");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=102&categoryId=50097362&etgId=115");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=102&categoryId=50030207&etgId=116");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=102&categoryId=50030215&etgId=117");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=102&categoryId=50030223&etgId=118");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=102&categoryId=50030221&etgId=119");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=102&categoryId=50030212&etgId=120");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=102&categoryId=50030213&etgId=121");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=102&categoryId=50030204&etgId=122");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=102&categoryId=50030220&etgId=124");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=102&categoryId=50030203&etgId=125");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=102&categoryId=50069204&etgId=126");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=102&categoryId=50069234&etgId=127");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=102&categoryId=50067917&etgId=128");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=104&categoryId=50024531&etgId=129");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=104&categoryId=50068087&etgId=130");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=104&categoryId=50072436&etgId=133");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=104&categoryId=50068090&etgId=131");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=104&categoryId=50036568&etgId=134");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=104&categoryId=50067939&etgId=132");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=104&categoryId=50036640&etgId=135");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=104&categoryId=50034368&etgId=136");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=104&categoryId=50072285&etgId=137");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=107&categoryId=50100151&etgId=174");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=107&categoryId=50072046&etgId=168");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=107&categoryId=50072044&etgId=169");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=107&categoryId=50100152&etgId=175");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=107&categoryId=50100153&etgId=176");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=107&categoryId=50100154&etgId=177");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=107&categoryId=50099890&etgId=178");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=107&categoryId=50074901&etgId=171");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=107&categoryId=50099887&etgId=179");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=107&categoryId=50100167&etgId=180");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=107&categoryId=50100166&etgId=181");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=107&categoryId=50099298&etgId=182");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=107&categoryId=50074804&etgId=170");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=107&categoryId=50074917&etgId=172");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=107&categoryId=50074933&etgId=173");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=105&categoryId=50025137&etgId=139");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=105&categoryId=50023647&etgId=148");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=105&categoryId=50029253&etgId=152");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=105&categoryId=50036697&etgId=147");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=105&categoryId=50024803&etgId=191");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=105&categoryId=50033500&etgId=193");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=106&categoryId=50106135&etgId=155");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=106&categoryId=50029838&etgId=157");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=106&categoryId=50029836&etgId=158");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=106&categoryId=50029852&etgId=159");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=106&categoryId=50029840&etgId=162");
			urlSet.add("http://brand.tmall.com/categoryIndex.htm?industryId=106&categoryId=50044102&etgId=163");
		}

		@Override
		public void run() {
			if (running) {
				logger.warn("CreateTaskTimer is working...");
				return;
			}
			long start = System.currentTimeMillis();
			try {
				logger.info("CreateTaskTimer is start...");
				running = true;
				JSONObject argsObject = new JSONObject();
				JSONUtils.put(argsObject, "strategy", getName());
				// ignore old task
				typeMap.clear();
				for (Entry<String, Set<String>> entry : typeMap.entrySet()) {
					List<TaskPriorityDto> taskList = new ArrayList<TaskPriorityDto>();
					String taskId = UUID.randomUUID().toString();
					JSONUtils.put(argsObject, "bid", taskId);
					JSONUtils.put(argsObject, "retry", 0);
					String type = entry.getKey();
					for (String url : entry.getValue()) {
						TaskPriorityDto taskDto = createPriorityDto(url, type, argsObject);
						taskList.add(taskDto);
					}
					getTaskPriorityDtoBuffer().addAll(taskList);
					logger.info("Offer task:{},size:{}", type, taskList.size());
				}
				TaskPriorityService taskPriorityService = SpringBeanUtils.getBean(TaskPriorityService.class);

				// 进口食品，1206
				Integer[] brandIdArray = new Integer[] { 111512, 3422124, 76097500, 683798426, 718482514, 4536992,
						7042789, 6057170, 243262366, 4536999, 36629018, 11251751, 322554631, 78005572, 111500, 3503333,
						74604174, 110071, 110072, 255754159, 111508, 41396507, 9934015, 110074, 362226542, 243164839,
						110066, 33795268, 216552520, 9433123, 20038, 107647738, 74330945, 17301504, 7983608, 92410668,
						299596878, 4539516, 786120554, 3738153, 4536184, 122144911, 591574399, 3320243, 4536976,
						7474789, 51462495, 131286271, 78956277, 7393939, 11732619, 279900558, 4538948, 10452594,
						3333564, 22467434, 228204056, 249344958, 7930758, 542266377, 9540391, 33716, 4538260,
						217654204, 280064842, 300360126, 10453463, 152974195, 8278133, 7529950, 286360413, 7906370,
						35616568, 101219656, 3241236, 103277074, 76999432, 6700297, 8224326, 309338418, 153057356,
						5532244, 184008616, 213786444, 3422704, 10724435, 3219783, 707170733, 57295, 828432933,
						118663302, 8103320, 6895233, 3917178, 187130707, 201582773, 3335129, 54002234, 8590023,
						10140895, 84703, 104519737, 7634624, 258648876, 20080516, 118692231, 27043680, 596794513,
						4539760, 121740228, 615224520, 98182839, 3751374, 144076802, 606456040, 675618692, 25800371,
						138234744, 32648721, 9041130, 7080991, 81636461, 59274536, 8124813, 84105527, 282988160,
						68000482, 4060079, 137887959, 12916066, 54437723, 366252491, 132425778, 3336237, 86180791,
						16458815, 6817726, 203268132, 19979321, 200300349, 240460186, 107318579, 7585318, 8420015,
						25296856, 215462494, 665592004, 3287672, 7544095, 11498597, 365054210, 7971414, 46693129,
						293250470, 664030015, 142273576, 6128845, 27714, 3825942, 139960759, 619484009, 530438193,
						17512499, 94958271, 74626407, 68421736, 8441379, 325786043, 3358056, 8626000, 15415442,
						7290694, 16334037, 82885746, 25320736, 583608085, 204250887, 139258, 21912640, 15015411,
						204074749, 93928454, 664496020, 3951656, 619254168, 751890150, 4533616, 6778423, 18756822,
						175374560, 361490400, 114151517, 22070740, 70614341, 53657673, 10527220, 4533628, 7879469,
						4533829, 7045146, 74735845, 25466909, 119615, 8039947, 4535635, 133723996, 6811404, 17370867,
						31848584, 186476856, 543254204, 27354809, 69385267, 121721938, 90841573, 492916773, 7808611,
						6702871, 256120324, 8049223, 265498028, 30703004, 119062, 6088054, 4535620, 9391078, 119066,
						73010968, 96916718, 4535629, 8575827, 3984958, 6759140, 241430132, 156424147, 177264337,
						559174849, 21553551, 3352809, 46837762, 714996592, 277462309, 492290849, 541034248, 8038129,
						4532355, 5827242, 17325255, 94580, 94583, 6819087, 108290346, 4535615, 51612340, 141844360,
						98508189, 451720458, 7833769, 4535618, 230136819, 191768891, 247216307, 227366783, 119080,
						64196372, 110079, 6040977, 247150374, 130512999, 718254209, 4537662, 9548637, 88011067, 119855,
						5415920, 3230013, 4269924, 4535609, 6817947, 206418566, 7609754, 155793051, 7444256, 6757959,
						153452553, 3316522, 8228055, 588230350, 64449123, 322154818, 232286751, 119072, 66615, 7427519,
						27885342, 8725116, 119079, 110457, 309406323, 194582918, 4503419, 95475007, 7303413, 6850730,
						20000948, 84473, 14200873, 110459, 38743, 126177941, 132911907, 9254828, 20290944, 656962398,
						56638, 5049513, 8193255, 121204875, 110460, 54198669, 6850930, 587770567, 24827279, 7227013,
						98164800, 110462, 49922182, 158225540, 11972125, 8080580, 132094217, 3272716, 49960593,
						95321119, 248562165, 9797872, 9698333, 86184724, 140326947, 9604844, 235456820, 147696666,
						4536305, 212408420, 6709933, 723908357, 232908634, 91176967, 60418984, 3529486, 154109149,
						7349682, 6797010, 30416005, 4366397, 16304553, 6795247, 122387230, 206279000, 662442259,
						170178948, 721952211, 3524174, 9790953, 3982506, 18063069, 3815198, 110441, 110442, 110445,
						7436043, 26455189, 4537038, 125845115, 11714362, 74551491, 366590645, 67107964, 7648981,
						27879732, 10651874, 97815915, 644784170, 3328080, 242190607, 110418, 11364163, 103573005,
						82680404, 110416, 22342529, 110415, 92507698, 110413, 7871390, 152788134, 3277100, 25407510,
						44808, 3744084, 4536557, 7012827, 148547497, 10738414, 492644496, 7752154, 7259025, 241438600,
						142287484, 7326664, 142239218, 3790546, 9129198, 124854, 306272584, 7241808, 11827, 292326181,
						8046059, 4016675, 42624577, 612898206, 7312239, 3284238, 4533284, 24557059, 266768132,
						22066496, 323302271, 257638807, 4534218, 380972536, 131301771, 3868101, 7342536, 7076820,
						125251, 4537006, 92541, 92543, 548502459, 662578114, 274838064, 14323892, 4534622, 135385360,
						22353190, 39887589, 134103785, 4229814, 10871455, 4537002, 8986296, 32750309, 92538, 376998276,
						778772889, 7612611, 7293886, 148773685, 175416986, 593228176, 4537400, 118024463, 4051258,
						8740641, 123412612, 121865378, 4536321, 3346688, 8067784, 333898904, 127392494, 3986854,
						60972289, 49278058, 10069187, 595016096, 139041082, 6777710, 4534806, 7512197, 12188780,
						219144225, 215924248, 112642912, 66116323, 12962520, 559428814, 144019167, 30417, 7037893,
						254398165, 305866866, 191108424, 6789296, 7705830, 69269732, 4132700, 6491780, 4540695, 57020,
						36394924, 36213747, 9381299, 3512737, 715254050, 603988903, 8970905, 4534618, 13552626,
						119225411, 115912196, 7877873, 6325603, 6720359, 195272165, 40749964, 65580165, 3371061,
						111365, 11242321, 111367, 88521941, 230722431, 292738683, 107037750, 4535992, 43292502,
						7746177, 19821958, 100255622, 93848014, 338712047, 3979239, 7625358, 8058972, 7076326,
						621682448, 46669444, 588390738, 129379485, 11554304, 52914106, 3231764, 56730107, 109164,
						188736976, 132328517, 22247811, 4183199, 9256733, 245616477, 174922928, 6769075, 75808993,
						6761319, 24905750, 62764170, 6861096, 20842911, 56670, 649414120, 223588072, 131964571, 111356,
						31350380, 257498120, 3233257, 9187525, 111359, 6742212, 111358, 6297380, 92564431, 3917822,
						58184073, 3568189, 206812229, 7995915, 6738675, 4214900, 8053744, 3232752, 119837986, 9426570,
						111360, 111361, 341406960, 13610545, 57518, 8005649, 4424871, 611468464, 374128461, 3734614,
						22167107, 40872107, 7183767, 8104442, 56663, 76451710, 9814232, 6801575, 15761830, 6735462,
						7625988, 11881929, 137887, 308002652, 51020015, 7061599, 105804, 216570006, 7079612, 3459447,
						300534803, 33483, 33482, 11498417, 33481, 33480, 7962590, 119538, 3670389, 147280915, 3495606,
						368770350, 233130402, 8930129, 556692221, 9081320, 8648602, 150191830, 30526701, 65613782,
						39655781, 232594802, 119543, 120421039, 119542, 746430201, 10272519, 3228163, 44261578,
						211522919, 8645843, 11021721, 554076409, 147798992, 8690881, 156067577, 57367, 112890385,
						76042714, 4539417, 4540495, 4417988, 26567436, 108639387, 117564022, 25999160, 3266761,
						90952622, 62610905, 11917216, 156460959, 4070094, 4539429, 239630321, 3229833, 374322542,
						4341264, 7043677, 52725390, 14597813, 8243941, 7702122, 319498065, 33477, 33478, 4142891,
						15854428, 8380310, 3227278, 599844834, 3293454, 29408, 608588726, 567572218, 317370640,
						4101168, 10741694, 136282931, 14569271, 7337878, 122049492, 203882737, 43225899, 30558,
						246068021, 4539449, 52070483, 617150123, 16053381, 54972710, 157786198, 89214035, 656722046,
						257488506, 56895, 53857635, 559402824, 21663, 50846732, 4539015, 66557, 66554, 6752076,
						307100063, 4100405, 8418656, 30462431, 19009358, 8551740, 156205354, 3561443, 3274238,
						96137669, 30573133, 378744695, 4539451, 721678113, 14524936, 51260298, 3231201, 197870365,
						69553971, 4540208, 141275800, 243462758, 73483903, 58698794, 377642457, 8180828, 10355120,
						179626482, 52914076, 9875359, 6889360, 187650229, 120970533, 110912, 687280197, 238130595,
						110913, 828998026, 130639009, 110910, 201118392, 129850, 9354716, 110915, 26113244, 9966263,
						216618235, 107547, 9166597, 7724367, 25590188, 19980377, 11331650, 13639472, 152368641,
						270178104, 8702343, 4537745, 4461844, 4005853, 3694456, 7510530, 7041362, 659312238, 155147097,
						600524091, 83777901, 7588823, 134745355, 650782749, 7055307, 7028684, 172744493, 97097173,
						192526028, 118550485, 37435222, 137867649, 28263932, 3788315, 617072870, 7529812, 7011612,
						4194763, 3599502, 289188237, 6905051, 26594019, 66589, 66588, 66590, 66591, 93370, 8827039,
						14775737, 39589, 3471026, 4534137, 286030388, 6344276, 650118016, 374980073, 8558371,
						187690126, 7121617, 4266264, 26439913, 663024085, 18346753, 10643788, 4002477, 7874032,
						117777609, 3283425, 3428996, 6537007, 271820556, 151896892, 742594762, 3255485, 12106786,
						4247147, 115231050, 103472, 38240259, 726060111, 3227978, 364626958, 119588, 199152254,
						8839688, 121130887, 8358319, 6775656, 50641283, 3855740, 10123380, 559376774, 3227983,
						287156237, 12509254, 28792230, 116354671, 98673226, 3318554, 3232334, 13548797, 24263690,
						3232338, 16068324, 20330, 82747828, 4539085, 38860658, 12476582, 10010914, 8767913, 6873846,
						36976779, 146917374, 4504555, 6871081, 133424078, 11330189, 56849, 559376796, 6742606,
						25465796, 3328014, 3235392, 9532377, 4533324, 134585722, 7469769, 4264379, 109669745,
						656550695, 216798505, 317988527, 20328, 651616332, 13929072, 3232350, 3720662, 6741733,
						17902605, 102557649, 10472595, 64088949, 47571, 137508973, 107544, 8685226, 107545, 5413560,
						152402120, 19756596, 653084733, 105445120, 207664350, 115509760, 20316, 6827068, 3721262,
						225832815, 3564346, 110588453, 204752831, 7097649, 139065669, 4220523, 153899392, 4536417,
						4537166, 96161979, 4564676, 252204112, 96911897, 117217660, 9039401, 8101604, 50468875,
						12141107, 201580599, 3571128, 375310228, 110761, 33033588, 313114841, 56742, 3584437, 88288468,
						9396449, 4540190, 22583314, 3324608, 78435250, 37582, 705012725, 3356048, 8453350, 8237350,
						128588724, 7369515, 41005559, 263842776, 9288393, 43144936, 283612222, 29205596, 32160947,
						6306825, 7498937, 3483540, 216616331, 29544272, 4491663, 4533800, 10041156, 7459239, 4535579,
						15518510, 5416895, 149963213, 8203889, 74670811, 96025562, 44902751, 4013410, 29415975,
						95277052, 3250005, 197676596, 6614362, 22596408, 69962547, 49400041, 663772007, 333904640,
						131497134, 594184111, 234704113, 183672265, 15369950, 7527727, 310358648, 7684484, 137692901,
						105343, 29295, 14206546, 6845391, 6728647, 55574478, 177896382, 7643976, 134140, 17376083,
						204010941, 219178987, 4536459, 84779246, 7461278, 7671424, 9212371, 6481757, 6852943, 7553710,
						215104954, 3271526, 129586402, 12930374, 3604057, 4536640, 8746224, 14510504, 134836616,
						775078191, 3597021, 287612944, 138101365, 118948551, 56718, 115692432, 3789548, 45605911,
						608354359, 47797377, 211762978, 111333541, 49362093, 34294528, 3990321, 4536465, 137823913,
						605788893, 3864639, 543204160, 7611243, 3563551, 139342346, 40460756, 7643551, 11371277,
						285038789, 5771128, 54381, 3352112, 612766565, 7418525, 96486239, 45489901, 772306569,
						553536677, 108579, 111485, 144279835, 18028782, 18703815, 6805567, 89955972, 155485740,
						6133335, 645052854, 7909618, 3692885, 8375125, 44041237, 4536490, 4540103, 6965217, 4540104,
						4536492, 111495, 111494, 4537358, 100531456, 309718999, 265410110, 105807216, 111496, 3218590,
						11409065, 82632131, 3428314, 30521, 359976888, 6886862, 147570559, 3229193, 4540117, 4536485,
						188444440, 16743421, 132949809, 3278190, 11801191, 107802, 8908651, 46437724, 15172423,
						157803237, 18157638, 84178123, 4230060, 178964970, 137330879, 117549702, 6156967, 6466769,
						603816770, 25723974, 50051458, 59955386, 51824971, 127129759, 39482539, 28944961, 7233422,
						181350053, 602690672, 7244613, 546570549, 551664404, 225300406, 621348070, 7280906, 6811883,
						7410842, 71052504, 3228729, 31068662, 157728871, 21437275, 115273248, 30316800, 6783056,
						194616969, 9246941, 3945185, 121118663, 7351688, 19569222, 220256366, 8624004, 28260718,
						11505292, 7136408, 3936272, 7467956, 559434802, 9352155, 18810925, 120932, 83767967, 7504595,
						730170750, 7365268, 535786745, 8046107, 652498457, 36531865, 9426695, 109776845, 7307074,
						25641438, 7016234, 680314022, 11255011, 78957908, 155313303, 230630760, 18524685, 7104454,
						29220533, 89251511, 6109742, 39355795, 3250854, 26413021, 553332899, 34032112, 4513648,
						287048435, 3372444, 40192008, 20160976 };

				List<TaskPriorityDto> taskList = new ArrayList<TaskPriorityDto>();
				String taskId = UUID.randomUUID().toString();
				String type = "ConfigTmallBrandShop";
				JSONUtils.put(argsObject, "bid", taskId);
				JSONUtils.put(argsObject, "retry", 0);
				
				
				List<TaskPriorityDto> newList = buildTaskByBrandIds(brandIdArray, argsObject, type);

				taskList.addAll(newList);
				//数码家电，830
				brandIdArray=new Integer[]{304842962,757472390,42801,4536165,3257104,227266860,604040294,23264247,3402936,88970517,3493097,101594854,41263,21568493,135055,360122016,731318262,10728,57312342,23806411,115502080,3650659,33567,154672373,96002,33564,4215762,15743043,5446072,90848876,67994,28247,109846583,56748749,157907274,3307801,597986973,3373314,104029268,3626635,44338750,27197,134869390,6876274,211346501,6211017,4023071,22101,22103,126839845,6197784,10745,101650695,3656335,30793404,813432397,12902167,10955,122333459,603944323,89086282,184528343,636330649,17881278,316380797,20644947,201212469,10752,67971,5810870,54148773,3422155,151520475,113777764,67968,68757478,618500031,67555016,17040344,31463494,77731796,41028017,101132,647736774,126343821,101126,101124,198564313,8158149,10779,101128,280928896,175386126,835182283,11754939,40778,549162643,27152,137810631,676006358,67947,101136,6838415,559428798,6381797,30895707,3903083,4067378,579204246,32319774,319300707,32018,42889,42886,42885,10446017,21076,116719646,52916786,205574383,132214780,552812470,7138395,3323295,16584244,121069407,3263043,109559,184510203,7185336,82327112,42867,21989,7609314,93141571,52996680,8168287,100232,3283568,629836039,96485,21999,21997,3725843,26487995,21991,121433578,21992,21990,21728342,134501097,282068701,100159456,382242948,4245182,112314932,40507059,300118867,3971364,4463657,6633509,7285028,139127554,115701288,3497408,71045359,602368671,273218361,112877017,8538139,4533470,4468453,3472045,74625866,135543,260540772,44037,80946,3278450,3727199,128236515,82104722,44032,3318426,38640314,6986953,60942507,87549761,286058793,148324520,703766392,11161,44048,132429949,142882206,44045,12510454,79276134,11693333,74344119,73097155,25596166,39399078,94958494,29992076,7009753,4521579,113190408,213578358,109050683,3280272,4382576,134012123,3361428,93841993,56233,8628864,102981087,3567228,84596794,21579341,38317,203484498,103577167,11912089,31401,3818143,107135960,39873634,62625233,134767690,582926107,696882533,329054665,616784001,691570023,143105936,158731497,653848019,10590,11841,123953,207084136,3921519,11454,31001,30879,583648166,7236051,21409262,138563202,21930,30661,66878525,88634403,30663,30664,21936,30860,16645294,617080950,21929,559636368,30863,30866,9716810,151500795,114478573,20618974,11787415,30657,30654,30655,30652,30653,30651,3643542,30851,85384125,89863143,7913533,659540372,3589619,87856146,120972351,30854,110838702,30852,10909931,30838,30644,111395936,30645,11813,9752660,448806981,144761040,30649,96510691,14588082,7868217,11208,30641,374366269,105216,4146587,10040782,8193028,80121463,16677281,598856737,30840,132426770,6508169,6755779,30841,30844,375852082,30638,244718689,653978849,22096,22093,146872966,22094,21944,21943,121053777,316822492,172426466,3243080,41993221,3415118,40710269,30837,6104614,40903928,30836,3275751,30835,30834,4538358,48794964,278634630,4333872,22083,25502031,193822975,26756,359424722,3222883,22084,3542419,144689430,30812,31945,30811,3245130,29583,110479362,621164001,342108090,43940584,8925184,131799,381216916,6166738,131798,543968162,623066529,504296650,350204470,117218000,90256666,11656,269600489,84416,238804222,29579,7989758,25540947,26739,772238848,7073320,84787336,152458664,4504970,101447038,10123,27330734,4538501,73871762,8713591,155468553,106621476,593756682,129484705,81155,91621,81156,123294915,707116459,3729563,67661220,109184,604312001,81148,81147,22026,22024,30111,389220794,22022,3575000,118898,259746599,599348882,42228,22021,374510937,10905,78296,118821872,94688008,22013,178406259,118282447,149827497,242658505,6602344,20571,44483274,4536029,791998015,3660952,122163233,12796533,648016048,209854900,131926,150788227,7731866,91018768,286050735,96248664,21417,4536008,15165342,104515841,81603229,656522884,645636041,40326449,156921545,147161516,16639733,134520877,124709751,143550288,3543461,113338,22005,301688865,20802,133261702,20801,539704285,79761748,10858,3221127,662990038,8331159,8363767,122922196,3788343,5926902,187174998,3221126,775486237,111810973,41726334,3814100,9166551,775494304,58061,79711085,131751088,3221131,3782897,81185235,302032267,21660,32385911,57309830,118341575,656774055,21663,30267353,21666,83939792,175886926,20797,20796,20793,21657,6525354,59628340,20799,779614741,20798,3980929,576796572,305886939,91320508,21646,117748843,21642,4398708,4410318,364244217,87739536,20519628,3545510,639188075,5791452,80126564,40080,6217233,11016,44477220,206054053,97083238,12025478,543162911,3577776,3594270,61144183,555732792,210030115,582376578,42020,10882660,151648306,49747206,38398394,41745382,142288705,62499356,11674008,115260073,8582195,182306631,628452780,39588,559438801,50248011,7002358,3311245,118050401,154945710,29668,22277931,7639929,153556221,31896,60679,3626511,724258043,119974,69689763,535836704,17108119,3525357,13690079,102067851,180422626,4427449,16364679,217124309,4535518,29609,21461,144025117,98234969,231550194,192156155,47712416,31683,113550,45694061,81509,5437449,126678,123668771,155624269,17074727,40906325,674336630,23289931,12626340,36077726,18895674,100341,137808977,11263,29623,33264825,15617750,577568457,10486006,294688495,8170960,72559168,3257570,322162790,68427023,51017392,9002819,3898126,42775,114945963,200578894,17407253,3498845,322694008,81499,21210,644974865,3279905,142272147,30991,42789,31104463,42786,30049,3405547,4469030,4540718,638912150,30050,37975,594688218,137804452,71689218,16374678,204856342,30052,89437904,129351044,82085535,31109,19467604,80272596,343246357,101202215,17561257,64284,303966047,619234138,540188666,25806989,141022926,144931047,84583193,631752875,92058982,25143,241464991,58871858,52037523,26691,10246,6223605,692548325,133630114,3846288,82430936,11034801,3525540,260556253,177742715,6094112,268588786,35167,204446777,115860929,184048021,604262027,114150456,125876689,3971287,291231000,314572734,230704279,8576998,142771930,65983,79978262,691526257,3230969,40078849,3673279,203464071,171786600,149575320,80138043,114083393,49320943,123840,6341418,26078497,43886,4110526,11119,133785757,222506741,374338217,3341926,10459,115039412,26550580,3536963,549072354,11531,45539,6039268,406414866,111137536,39864706,269206318,11675927,26668,19796618,121984504,10272988,82478291,95410374,11801892,64602,84729293,8765828,354694832,74267653,8180543,32922614,681136674,209004010,10428,45553,5435727,5671881,3261618,32891598,6036704,123770878,86563794,449440225,36795434,712162603,146554053,11542,45547,45545,612852131,10161390,45540,3223459,622412133,3506680,4128269,15033872,26683,6119969,31128,723418329,3327216,745550206,222058055,206328224,49673634,30458860,271606299,14065702,4540146,3261263,37556,62959164,96246142,156740486,31132,207598670,31137,31136,31135,15919880,272316975,11717917,90394923,406328918,533896790,101072,66316661,107490293,559434663,34883494,540202827,57635872,19298901,11599,3868292,535386523,109925290,675950476,31140,35359818,98878216,5419428,130436205,3366755,234618719,99813056,9603272,268828981,105448665,625698291,105315213,47416,559386806,3350585,40974730};
				newList = buildTaskByBrandIds(brandIdArray, argsObject, type);
				taskPriorityService.batchInsert(taskList);
				logger.info("insert new task into db.type:" + type + ",count:" + taskList.size());
			} catch (Exception ex) {
				logger.warn(ExceptionUtils.getStackTrace(ex));
			} finally {
				long cost = System.currentTimeMillis() - start;
				logger.info("CreateTaskTimer is done.cost:{}", cost);
				running = false;
			}
		}
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	public List<TaskPriorityDto> buildTaskByBrandIds(Integer[] brandIdArray, JSONObject argsObject, String type) {
		List<TaskPriorityDto> taskList = new ArrayList<TaskPriorityDto>(brandIdArray.length);
		for (Integer brandId : brandIdArray) {
			String url = "http://list.tmall.com/search_product.htm?brand=" + brandId + "&sort=s&style=w#J_Filter";
			TaskPriorityDto taskDto = createPriorityDto(url, type, argsObject);
			taskList.add(taskDto);
		}
		return taskList;
	}

	@Override
	public void handleResult(ResultWritable rWritable) {
		if (ResultWritable.RESULT_SUCCESS != rWritable.getStatus()) {
			return;
		}
		JSONObject gObject = JSONUtils.getJSONObject(rWritable.getResult());
		JSONObject rsObject = JSONUtils.getJSONObject(gObject, "rs");
		JSONObject argsObject = JSONUtils.getJSONObject(gObject, "args");
		argsObject.remove("name@client");
		argsObject.remove("target");
		try {
			addNextTasks(rsObject, argsObject);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (rWritable.getType().indexOf("BrandList") > 0) {
			try {
				addOthers(rWritable, rsObject, argsObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void addOthers(ResultWritable rWritable, JSONObject rsObject, JSONObject argsObject) throws JSONException {
		JSONArray dataArray = JSONUtils.get(rsObject, "dataList");
		if (dataArray == null) {
			return;
		}
		int len = dataArray.length();
		List<TaskPriorityDto> dtoList = new ArrayList<TaskPriorityDto>(len * 2);
		JSONObject oParamObject = JSONUtils.getJSONObject(argsObject.toString());
		String url = JSONUtils.getString(argsObject, "url");
		JSONUtils.put(oParamObject, "fromUrl", url);
		String productType = rWritable.getType().replace("BrandList", "BrandShop");
		String argsString = oParamObject.toString();
		for (int i = 0; i < len; i++) {
			JSONObject bObject = dataArray.getJSONObject(i);
			JSONObject paramObject = new JSONObject(argsString);
			String brandUrl = JSONUtils.getString(bObject, "brandUrl");
			bObject.remove("brandUrl");
			Iterator<?> it = bObject.keys();
			while (it.hasNext()) {
				String key = it.next().toString();
				JSONUtils.put(paramObject, key, JSONUtils.get(bObject, key));
			}
			TaskPriorityDto taskPriorityDto = createPriorityDto(brandUrl, productType, paramObject);
			dtoList.add(taskPriorityDto);
		}
		getTaskPriorityDtoBuffer().addAll(dtoList);

	}

	private void addNextTasks(JSONObject rsObject, JSONObject argsObject) throws Exception {
		JSONArray nextArray = JSONUtils.get(rsObject, "nextList");
		if (nextArray == null) {
			return;
		}
		String type = JSONUtils.getString(argsObject, "type");
		List<TaskPriorityDto> dtoList = new ArrayList<TaskPriorityDto>();
		JSONUtils.put(argsObject, "fromUrl", JSONUtils.getString(argsObject, "url"));
		for (int i = 0; i < nextArray.length(); i++) {
			String nextUrl = nextArray.getString(i);
			TaskPriorityDto taskPriorityDto = createPriorityDto(nextUrl, type, argsObject);
			dtoList.add(taskPriorityDto);
		}
		getTaskPriorityDtoBuffer().addAll(dtoList);
	}

	private TaskPriorityDto createPriorityDto(String url, String type, JSONObject argsObject) {
		String taskId = JSONUtils.getString(argsObject, "bid");
		taskId = taskId == null ? UUID.randomUUID().toString() : taskId;
		TaskPriorityDto taskPriorityDto = new TaskPriorityDto();
		taskPriorityDto.setBatchId(taskId);
		taskPriorityDto.setType(type);
		taskPriorityDto.setUrl(url);
		taskPriorityDto.setLevel(JSONUtils.getInteger(argsObject, "level"));
		taskPriorityDto.setSource(JSONUtils.getString(argsObject, "src"));
		taskPriorityDto.setCreatTime(new Date());
		taskPriorityDto.setUpdateTime(taskPriorityDto.getCreatTime());
		taskPriorityDto.setStatus(TaskConstant.TASK_NEW);
		JSONObject paramObject = JSONUtils.getJSONObject(argsObject.toString());
		paramObject.remove("bid");
		paramObject.remove("type");
		paramObject.remove("url");
		paramObject.remove("level");
		paramObject.remove("src");
		paramObject.remove("ctime");
		if (taskPriorityDto.getLevel() == null) {
			taskPriorityDto.setLevel(0);
		}
		taskPriorityDto.setParams(paramObject.toString());
		return taskPriorityDto;
	}

	private StorageBuffer<TaskPriorityDto> getTaskPriorityDtoBuffer() {
		return (StorageBuffer<TaskPriorityDto>) StorageBufferFactory.getStorageBuffer(TaskPriorityDto.class);
	}

	@Override
	public void close() throws IOException {
		if (this.timer != null) {
			this.timer.cancel();
			this.timer = null;
		}
		logger.info("close " + getName() + " strategy..");
	}
}