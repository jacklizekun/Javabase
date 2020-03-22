package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import config.Global;
import listener.GroundListener;


/**
 * 障碍物界面
 * @version 1.0
 * 
 * @author 李泽坤
 * 
 */
public class Ground {
	//设置障碍物监听器
	protected Set<GroundListener> listeners = new HashSet<GroundListener>();
	//设置障碍物
	protected UnitType[][] obstacles = new UnitType[Global.WIDTH][Global.HEIGHT];
	//无法消除的障碍物颜色
	protected Color stubbornObstacleColor = UnitType.STUBBORN_OBSTACLE.getColor();
	//默认网格颜色
	public static final Color DEFAULT_GRIDDING_COLOR = Color.LIGHT_GRAY;
	//实际网格颜色
	protected Color griddingColor = DEFAULT_GRIDDING_COLOR;
	//设置默认障碍物颜色
	public static final Color DEFAULT_OBSTACLE_COLOR = UnitType.OBSTACLE.getColor();
	//设置障碍物颜色
	protected Color obstacleColor = DEFAULT_OBSTACLE_COLOR;
	//默认满行的颜色
	public static final Color DEFAULT_FULL_LINE_COLOR = Color.DARK_GRAY;
	//实际满行的颜色
	protected Color fullLineColor = DEFAULT_FULL_LINE_COLOR;
	//是否显示网格
	protected boolean drawGridding;
	//是否彩色图像
	protected boolean colorfulSupport;
	//是否满行
	protected boolean full;
	//随机数
	protected Random random = new Random();
	//初始化
	public Ground() {
		init();
	}
	//初始清除画面
	public void init() {
		clear();
		full = false;
	}
	//清除操作
	public void clear() {
		//统一复制克隆成白色方块
		for (int x = 0; x < Global.WIDTH; x++)
			for (int y = 0; y < Global.HEIGHT; y++)
				obstacles[x][y] = UnitType.BLANK.clone();
	}
	//随机生成下落的方块的位置
	public void genernateAStubbornStochasticObstacle() {
		Random random = new Random();
		if (Global.HEIGHT < 5)return;
		int y = random.nextInt(5) + Global.HEIGHT - 5;
		int x = random.nextInt(Global.WIDTH);
		addStubbornObstacle(x, y);
	}

	//随机生成障碍物
	public void generateSomeStochasticObstacle(int amount, int lineNum) {
		if (lineNum < 1)return;
		if (lineNum > Global.HEIGHT)lineNum = Global.HEIGHT;
		for (int i = 0; i < amount; i++) {
			int x = random.nextInt(Global.WIDTH);
			int y = random.nextInt(lineNum) + Global.HEIGHT - lineNum;
			obstacles[x][y] = UnitType.OBSTACLE.clone();
			obstacles[x][y].setColor(Global.getRandomColor());
		}
	}

	//画出方块
	public void accept(Shape shape) {
		int left = shape.getLeft();
		int top = shape.getTop();

		for (int x = 0; x < 4; x++)
			for (int y = 0; y < 4; y++)
				if (left + x < Global.WIDTH && top + y < Global.HEIGHT) {
					if (shape.isMember(x, y, false))
						if (top + y < 0) {
							full = true;
							for (GroundListener l : listeners)
								l.groundIsFull(this);
						} else {
							obstacles[left + x][top + y]
									.cloneProperties(UnitType.OBSTACLE);
							obstacles[left + x][top + y]
									.setColor(colorfulSupport ? shape
											.getColor() : obstacleColor);
						}
				}
		//删除满行的行
		deleteFullLine();
	}

	public void deleteFullLine() {
		//消除行数
		int deletedLineCount = 0;
		for (int y = Global.HEIGHT - 1; y >= 0; y--) {
			boolean isFull = true;
			for (int x = 0; x < Global.WIDTH; x++) {
				if (obstacles[x][y].equals(UnitType.BLANK))
					isFull = false;
			}
			if (isFull) {
				deleteLine(y++);
				deletedLineCount++;
			}
		}
		if (deletedLineCount > 0)
			for (GroundListener l : listeners)
				l.fullLineDeleted(this, deletedLineCount);
	}
	public void deleteLine(int lineNum) {

		for (GroundListener l : listeners)
			l.beforeDeleteFullLine(this, lineNum);

		for (int y = lineNum; y > 0; y--)
			for (int x = 0; x < Global.WIDTH; x++)
				if (!obstacles[x][y].equals(UnitType.STUBBORN_OBSTACLE))
					if (obstacles[x][y - 1].equals(UnitType.STUBBORN_OBSTACLE)) {
						obstacles[x][y].cloneProperties(UnitType.BLANK);
						obstacles[x][y].setColor(this.griddingColor);
					} else
						obstacles[x][y].cloneProperties(obstacles[x][y - 1]);
		for (int x = 0; x < Global.WIDTH; x++)
			if (!obstacles[x][0].equals(UnitType.STUBBORN_OBSTACLE))
				obstacles[x][0] = UnitType.BLANK.clone();
	}
	public boolean isFull() {
		return full;
	}
	public synchronized boolean isMoveable(Shape shape, int action) {
		int left = shape.getLeft();
		int top = shape.getTop();
		switch (action) {
			case Shape.UP:		top--;	break;
			case Shape.DOWN:	top++;	break;
			case Shape.LEFT:	left--;	break;
			case Shape.RIGHT:	left++;	break;
		}

		if (top < 0 - shape.getHeight()) {
			return false;
		}
		for (int x = 0; x < 4; x++)
			for (int y = 0; y < 4; y++)
				if ((left + x < 0 || left + x >= Global.WIDTH || top + y >= Global.HEIGHT)
						&& shape.isMember(x, y, action == Shape.ROTATE))
					return false;
				else if (top + y < 0)
					continue;
				else {
					if (shape.isMember(x, y, action == Shape.ROTATE))
						if (!obstacles[left + x][top + y]
								.equals(UnitType.BLANK))
							return false;
				}
		return true;
	}

	//消除行前的延时效果
	public void changeFullLineColor(int lineNum) {

		for (int x = 0; x < Global.WIDTH; x++)
			obstacles[x][lineNum].setColor(fullLineColor);
	}
	//添加障碍物
	public void addObstacle(int x, int y) {
		if (x < 0 || x >= Global.WIDTH || y < 0 || y >= Global.HEIGHT)
			throw new RuntimeException("这个位置超出了显示区域 (x:" + x + "  y:" + y + ")");
		obstacles[x][y].cloneProperties(UnitType.OBSTACLE);
	}
	public void addStubbornObstacle(int x, int y) {
		if (x < 0 || x >= Global.WIDTH || y < 0 || y >= Global.HEIGHT)
			throw new RuntimeException("这个位置超出了显示区域 (x:" + x + "  y:" + y + ")");
		obstacles[x][y].cloneProperties(UnitType.STUBBORN_OBSTACLE);
	}
	public void drawMe(Graphics g) {
		for (int x = 0; x < Global.WIDTH; x++)
			for (int y = 0; y < Global.HEIGHT; y++) {
				if (drawGridding && obstacles[x][y].equals(UnitType.BLANK)) {
					g.setColor(griddingColor);
					drawGridding(g, x * Global.CELL_WIDTH, y* Global.CELL_HEIGHT, Global.CELL_WIDTH,Global.CELL_HEIGHT);
				}
				else if (obstacles[x][y].equals(UnitType.STUBBORN_OBSTACLE)) {
					g.setColor(stubbornObstacleColor);
					drawStubbornObstacle(g, x * Global.CELL_WIDTH, y
							* Global.CELL_HEIGHT, Global.CELL_WIDTH,
							Global.CELL_HEIGHT);
				}
				else if (obstacles[x][y].equals(UnitType.OBSTACLE)) {
					g.setColor(obstacles[x][y].getColor());
					drawObstacle(g, x * Global.CELL_WIDTH, y
							* Global.CELL_HEIGHT, Global.CELL_WIDTH,
							Global.CELL_HEIGHT);
				}
			}
	}

	//网格
	public void drawGridding(Graphics g, int x, int y, int width, int height) {
		g.drawRect(x, y, width, height);
	}
	//不可消除
	public void drawStubbornObstacle(Graphics g, int x, int y, int width,int height) {
		g.fill3DRect(x, y, width, height, true);
	}
	public void drawObstacle(Graphics g, int x, int y, int width, int height) {
		g.fill3DRect(x, y, width, height, true);
	}
	public Color getStubbornObstacleColor() {
		return stubbornObstacleColor;
	}
	public void setStubbornObstacleColor(Color stubbornObstacleColor) {
		this.stubbornObstacleColor = stubbornObstacleColor;
	}
	public Color getGriddingColor() {
		return griddingColor;
	}
	public void setGriddingColor(Color griddingColor) {
		this.griddingColor = griddingColor;
	}
	public Color getObstacleColor() {
		return obstacleColor;
	}
	public void setObstacleColor(Color obstacleColor) {
		this.obstacleColor = obstacleColor;
	}
	public Color getFullLineColor() {
		return fullLineColor;
	}
	public void setFullLineColor(Color fullLineColor) {
		this.fullLineColor = fullLineColor;
	}
	public boolean isDrawGridding() {
		return drawGridding;
	}
	public void setDrawGridding(boolean drawGridding) {
		this.drawGridding = drawGridding;
	}
	public boolean isColorfulSupport() {
		return colorfulSupport;
	}
	public void setColorfulSupport(boolean colorfulSupport) {
		this.colorfulSupport = colorfulSupport;
	}
	public void addGroundListener(GroundListener l) {
		if (l != null)
			this.listeners.add(l);
	}
	public void removeGroundListener(GroundListener l) {
		if (l != null)
			this.listeners.remove(l);
	}
	public boolean isStubbornObstacle(int x, int y) {
		if (x >= 0 && x < Global.WIDTH && y >= 0 && y < Global.HEIGHT)
			return obstacles[x][y].equals(UnitType.STUBBORN_OBSTACLE);
		else
			throw new RuntimeException("这个坐标超出了显示区域: (x:" + x + " y:" + y + ")");
	}
	public boolean isObstacle(int x, int y) {
		if (x >= 0 && x < Global.WIDTH && y >= 0 && y < Global.HEIGHT)
			return obstacles[x][y].equals(UnitType.OBSTACLE);
		else
			throw new RuntimeException("这个坐标超出了显示区域: (x:" + x + " y:" + y + ")");
	}
	public boolean isBlank(int x, int y) {
		if (x >= 0 && x < Global.WIDTH && y >= 0 && y < Global.HEIGHT)
			return obstacles[x][y].equals(UnitType.BLANK);
		else
			throw new RuntimeException("这个坐标超出了显示区域: (x:" + x + " y:" + y + ")");
	}
}
