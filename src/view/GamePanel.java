package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import config.Global;


/**
 * ��Ϸ����ʾ����
 * @version 1.0
 * @author ������
 */
public class GamePanel extends JPanel {
	private Image image;
	private Graphics graphics;
	//����Ĭ�ϱ�����ɫ
	public static final Color DEFAULT_BACKGROUND_COLOR = new Color(0xcfcfcf);
	//����ʵ�ʱ�����ɫ
	protected Color backgroundColor = DEFAULT_BACKGROUND_COLOR;
	//������
	public GamePanel() {
	//������Ϸ���Ĵ�С�ͱ߿�
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
	//��ȡ������ɫ
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	//���ñ�����ɫ
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}
