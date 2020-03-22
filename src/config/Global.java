package config;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;


/**
 * 配置文件
 * 
 * @version 1.0
 * 
 * @author 李泽坤
 * 
 */
public class Global {
	//构造器，不能生成实例
	private Global() { }
	//新建配置文件
	private static Properties properties = new Properties();
	//配置文件
	private static String CONFIG_FILE = "Tetris.txt";
	//格子的宽度
	public static final int CELL_WIDTH;
	//格子的高度
	public static final int CELL_HEIGHT;
	//宽度，单位（格子数）
	public static final int WIDTH;
	//高度，单位（格子数）
	public static final int HEIGHT;
	//图形下落的初始速度
	public static final int DEFAULT_SPEED;
	//现在图形的下落速度
	public static int CURRENT_SPEED;
	//图形快速下落的速度
	public static final int SWIFT_SPEED;
	//变速幅度
	public static final int SPEED_STEP;
	//消除前的暂停时间
	public static final int DEFAULT_STAY_TIME;
	//自定义消除延迟时间
	public static int STAY_TIME;
	//0-1的随机数
	private static Random random = new Random();
	//说明文字
	public static final String TITLE_LABEL_TEXT;
	//简介文字
	public static final String INFO_LABEL_TEXT;
	//默认的颜色
	private static final Color[] DEFAULT_COLORS = new Color[] {
			new Color(0x990066), new Color(0x990099), new Color(0x330099),
			new Color(0x663300), new Color(0x009966), new Color(0x003333) };
	//常用的颜色
	public static final List<Color> COMMON_COLORS;
	//返回随机的颜色
	public static Color getRandomColor() {
		return DEFAULT_COLORS[random.nextInt(DEFAULT_COLORS.length)];
	}

	//常量
	static {
		//配置输入流，从配置文件中获取值
		InputStream inputStream = null;
		try {
			//读取配置文件进入输入流
			inputStream = new FileInputStream(CONFIG_FILE);
			//配置实例对应输入流信息
			properties.load(inputStream);
		} catch (FileNotFoundException e) {
			System.out.println("");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Integer temp = null;
		WIDTH = (temp = getIntValue("width")) != null && temp <= 80 && temp >= 10 ? temp : 15;
		HEIGHT = (temp = getIntValue("height")) != null && temp <= 60 && temp >= 10 ? temp : 20;
		DEFAULT_SPEED = CURRENT_SPEED = (temp = getIntValue("speed")) != null && temp >= 10 ? temp : 300;
		SWIFT_SPEED = (temp = getIntValue("swift_speed")) != null && temp >= 0 ? temp : 15;
		SPEED_STEP = (temp = getIntValue("speed_step")) != null && temp >= 1 ? temp: 25;
		DEFAULT_STAY_TIME = STAY_TIME = (temp = getIntValue("stay_time")) != null && temp >= 0 ? temp : 200;
		int defaultCellSize = (temp = getIntValue("cell_size")) != null && temp > 0 && temp <= 100 ? temp : 23;
		CELL_WIDTH = (temp = getIntValue("cell_width")) != null && temp > 0 && temp <= 100 ? temp : defaultCellSize;
		CELL_HEIGHT = (temp = getIntValue("cell_height")) != null && temp > 0 && temp <= 100 ? temp : defaultCellSize;

		String tempStr = null;
		TITLE_LABEL_TEXT = (tempStr = getValue("title")) == null ? "坤坤为您介绍：": tempStr;
		INFO_LABEL_TEXT = (tempStr = getValue("info")) == null ? "方向键控制方向, 回车键暂停/继续\nPAGE UP, PAGE DOWN 加速或减速\n ": tempStr;

		COMMON_COLORS = loadColors();
	}


	@SuppressWarnings("unchecked")
	private static List<Color> loadColors() {
		//新建数组集合
		List<Color> l = new ArrayList<Color>(7);
		for (int i = 0; i < 7; i++)
			l.add(null);
		//新建SET集合，用于获取配置文件信息
		Set set = properties.keySet();
		//遍历器
		Iterator iter = set.iterator();
		//去掉获取key对应的值的前后空格
		while (iter.hasNext()) {
			String key = (String) iter.next();
			if ("1".equals(key.trim()))
				addColor(l, 0, getValue(key));
			else if ("2".equals(key.trim()))
				addColor(l, 1, getValue(key));
			else if ("3".equals(key.trim()))
				addColor(l, 2, getValue(key));
			else if ("4".equals(key.trim()))
				addColor(l, 3, getValue(key));
			else if ("5".equals(key.trim()))
				addColor(l, 4, getValue(key));
			else if ("6".equals(key.trim()))
				addColor(l, 5, getValue(key));
			else if ("7".equals(key.trim()))
				addColor(l, 6, getValue(key));
		}

		for (int i = 0; i < 7; i++) {
			l.remove(null);
		}

		if (l.size() < 1) {
			for (int i = 0; i < DEFAULT_COLORS.length; i++) {
				l.add(DEFAULT_COLORS[i]);
			}
		} else {
			if (l.size() != 7)
				System.out.println("您一共设置了 " + l.size() + " 种有效颜色， 建议设置七种");

			return l.subList(0, l.size() > 7 ? 7 : l.size());
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	private static void addColor(List l, int index, String str) {
		str = str.trim();
		if (!str.startsWith("0x") || str.length() < 3) {
			System.out.println("颜色设置有误，请检查 : " + str + " (key)");
			return;
		}

		try {
			String strRGB = str.substring(2, str.length() >= 8 ? 8 : str
					.length());
			int rgb = Integer.valueOf(strRGB, 16);
			Color c = new Color(rgb);
			if (c != null) {
				l.add(index, c);
			}
		} catch (Exception e) {
			System.out.println("(e)颜色设置有误，请检查:" + str + "(key)");
			e.printStackTrace();
			return;
		}
	}
	//获取配置文件中的数
	private static Integer getIntValue(String key) {
		if (key == null)
			throw new RuntimeException("请注意，key 不能为空");
		try {
			//返回配置文件中的key值对应的值
			return new Integer(properties.getProperty(key));
		} catch (NumberFormatException e) {
			return null;
		}
	}
	//返回配置文件中的字符串
	private static String getValue(String key) {
		try {
			//返回经过getBytes配置编码的配置信息
			return new String(properties.getProperty(key).getBytes("iso8859-1"));
		} catch (Exception e) {
			return null;
		}
	}

}
