package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.TooManyListenersException;

import config.Global;
import listener.ShapeListener;


/**
 * 图形
 * 
 * @version 1.0
 * 
 * @author 李泽坤
 * 
 */
public class Shape {

	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	//旋转
	public static final int ROTATE = 5;
	//是否变化操作图形
	protected ShapeListener listener;
	//图形占的坐标
	protected int[][] body;
	//显示状态
	protected int status;
	protected int height;
	protected int left;
	protected int top;
	protected int speed;
	protected boolean life;
	protected boolean pause;
	//是否加速
	protected boolean swift;
	//加速速度
	protected int swiftSpeed = Global.SWIFT_SPEED;
	//图形线程和加速线程
	protected Thread shapeThread, swiftThread;
	protected Color color = Color.BLUE;
	public Shape(int[][] body, int status) {
		super();
		this.body = body;
		this.status = status;
		for (int y = 0; y < 4; y++)
			for (int x = 0; x < 4; x++)
				if (isMember(x, y, false))
					height = y + 1;
		init();
	}
	public void init() {
		life = true;
		pause = false;
		swift = false;
		left = Global.WIDTH / 2 - 2;
		top = 0 - height;
		speed = Global.CURRENT_SPEED;
	}
	public void rotate() {status = (status + 1) % body.length;}
	public void moveUp() {top--;}
	public void moveDown() {top++;}
	public void moveLeft() {left--;}
	public void moveRight() {left++;}

	//线程
	protected class ShapeDriver implements Runnable {

		public void run() {
			if (listener == null)
				throw new RuntimeException("请先注册 ShapeListener");
			while (life && listener.isShapeMoveDownable(Shape.this)) {
				if (!swift) {
					if (!pause) {
						moveDown();
						listener.shapeMovedDown(Shape.this);
					}
				}
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			life = false;
		}

	}
	//显示图形
	public void drawMe(Graphics g) {
		if (!life)return;
		g.setColor(color);
		for (int x = 0; x < 4; x++)
			for (int y = 0; y < 4; y++)
				if (getFlagByPoint(status, x, y))
					drawUnit(g, (left + x) * Global.CELL_WIDTH, (top + y)* Global.CELL_HEIGHT, Global.CELL_WIDTH,Global.CELL_HEIGHT);
	}

	public void drawUnit(Graphics g, int x, int y, int width, int height) {
		g.fill3DRect(x, y, width, height, true);
	}
	protected boolean getFlagByPoint(int status, int x, int y) {
		return body[status][y * 4 + x] == 1;
	}
	public boolean isMember(int x, int y, boolean isRotate) {
		return getFlagByPoint(isRotate ? (status + 1) % body.length : status,
				x, y);
	}


	public void speedUp() {
		if (speed > Global.SPEED_STEP)
			speed -= Global.SPEED_STEP;
		Global.CURRENT_SPEED = speed;
	}
	public void speedDown() {
		speed += Global.SPEED_STEP;
		Global.CURRENT_SPEED = speed;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public boolean isPause() {
		return pause;
	}
	public void setPause(boolean pause) {
		this.pause = pause;
	}
	public void changePause() {
		this.pause = !this.pause;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void setBody(int[][] body) {
		this.body = body;
	}
	public void addShapeListener(ShapeListener l) {
		if (l == null || this.listener == l)
			return;
		if (this.listener != null)
			throw new RuntimeException(new TooManyListenersException());
		this.listener = l;
		start();
	}
	protected void start() {
		shapeThread = new Thread(new ShapeDriver());
		shapeThread.start();
	}
	public boolean isLife() {
		return life;
	}
	public synchronized void die() {
		this.life = false;
	}
	public boolean isSwift() {
		return swift;
	}
	//加速下落
	public void setSwift(boolean swift) {

		if (this.swift == swift)
			return;

		this.swift = swift;
		if (this.swift) {
			swiftThread = new Thread(new ShapeSwiftDriver());
			swiftThread.start();
		}
	}

	protected class ShapeSwiftDriver implements Runnable {
		public void run() {
			while (swift && life) {
				if (listener == null)
					throw new RuntimeException("请先注册 ShapeListener");
				if (listener.isShapeMoveDownable(Shape.this)) {
					if (!pause) {
						moveDown();
						listener.shapeMovedDown(Shape.this);
					}
					try {
						Thread.sleep(swiftSpeed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					life = false;
				}
			}
		}

	}

	public int getSwiftSpeed() {
		return swiftSpeed;
	}

	public void setSwiftSpeed(int swiftSpeed) {
		this.swiftSpeed = swiftSpeed;
	}

	public int getHeight() {
		return height;
	}

}
