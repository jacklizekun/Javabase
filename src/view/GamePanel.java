package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import config.Global;


/**
 * 游戏的显示界面
 * @version 1.0
 * @author 李泽坤
 */
public class GamePanel extends JPanel {
	private Image image;
	private Graphics graphics;
	//设置默认背景颜色
	public static final Color DEFAULT_BACKGROUND_COLOR = new Color(0xcfcfcf);
	//设置实际背景颜色
	protected Color backgroundColor = DEFAULT_BACKGROUND_COLOR;
	//构造器
	public GamePanel() {
	//设置游戏面板的大小和边框
		this.setSize(Global.WIDTH * Global.CELL_WIDTH, Global.HEIGHT* Global.CELL_HEIGHT);
		this.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		this.setFocusable(true);
	}
	public synchronized void redisplay(Ground ground, Shape shape) {
		if (graphics == null) {
			image = createImage(getSize().width, getSize().height);
			if (image != null)
				graphics = image.getGraphics();
		}
		if (graphics != null) {
			graphics.setColor(backgroundColor);
			graphics.fillRect(0, 0, Global.WIDTH * Global.CELL_WIDTH, Global.HEIGHT* Global.CELL_HEIGHT);
			ground.drawMe(graphics);
			if (shape != null)
				shape.drawMe(graphics);
			this.paint(this.getGraphics());
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, this);
	}
	//获取背景颜色
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	//设置背景颜色
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}
