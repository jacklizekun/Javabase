package game;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;

import config.Global;
import listener.GameListener;
import listener.GroundListener;
import listener.ShapeListener;
import view.GamePanel;
import view.Ground;
import view.Shape;
import view.ShapeFactory;


/**
 * ������
 * 
 * @version 1.0
 * 
 * @author ������
 * 
 */
public class Controller extends KeyAdapter implements ShapeListener,GroundListener {

	protected Set<GameListener> listeners = new HashSet<GameListener>();
	protected ShapeFactory shapeFactory;
	protected Shape shape;
	protected Ground ground;
	protected GamePanel gamePanel;
	protected JLabel gameInfoLabel;
	//��ǰ��Ϸ״̬
	protected boolean playing;
	//������
	public Controller(ShapeFactory shapeFactory, Ground ground,GamePanel gamePanel) {
		super();
		this.shapeFactory = shapeFactory;
		this.ground = ground;
		this.gamePanel = gamePanel;
	}
	public Controller(ShapeFactory shapeFactory, Ground ground,GamePanel gamePanel, JLabel gameInfoLabel) {
		this(shapeFactory, ground, gamePanel);
		this.setGameInfoLabel(gameInfoLabel);
	}

	/**
	 * ������̰��� <BR>
	 * LEFT: �����ƶ�<BR>
	 * RIGHT�������ƶ�<BR>
	 * DOWN: �����ƶ�<BR>
	 * UP: ����<BR>
	 * PAGE UP: �ӿ��ٶ�<BR>
	 * PAGE DOWN: �����ٶ�<BR>
	 * Y: ���¿�ʼ��Ϸ<BR>
	 * ENTER: ��ͣ/����
	 */
	//���ü��̼���������
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() != KeyEvent.VK_Y && !playing)
			return;

		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
				if (isPausingGame()) {
					this.continueGame();
				}
			shape.setSwift(false);
				if (isPlaying() && ground.isMoveable(shape, Shape.LEFT))
			shape.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			if (isPausingGame()) {
				this.continueGame();
			}
			shape.setSwift(false);

			if (isPlaying() && ground.isMoveable(shape, Shape.RIGHT))
				shape.moveRight();
			break;
		case KeyEvent.VK_UP:

			if (isPlaying()) {
				if (!shape.isPause()) {
					if (ground.isMoveable(shape, Shape.ROTATE)) {
						shape.setSwift(false);
						shape.rotate();
					}
				} else {
					if (ground.isMoveable(shape, Shape.UP))
						shape.moveUp();
					else {
						shape.die();
						shape = shapeFactory.getShape(this);
					}
				}
			}

			break;
		case KeyEvent.VK_DOWN:
			if (isPausingGame()) {
				this.continueGame();
			}
			if (isPlaying() && isShapeMoveDownable(shape))
				shape.moveDown();
			break;
		case KeyEvent.VK_PAGE_UP:
			shape.speedUp();
			break;
		case KeyEvent.VK_PAGE_DOWN:
			shape.speedDown();
			break;
		case KeyEvent.VK_BACK_QUOTE:
			if (isPlaying()) {
				shape.die();
				shape = shapeFactory.getShape(this);
			}
			break;
		case KeyEvent.VK_ENTER:
			if (isPausingGame())
				this.continueGame();
			else
				this.pauseGame();
			break;
		case KeyEvent.VK_Y:
			if (!isPlaying())
				newGame();
			break;
		case KeyEvent.VK_SPACE:

			if (isPlaying() && !isPausingGame())
				shape.setSwift(true);
			break;
		}
		gamePanel.redisplay(ground, shape);
		if (gameInfoLabel != null)
			gameInfoLabel.setText(this.getNewInfo());
	}

	//�Ƿ���Լ����½�ͼ��
	public synchronized boolean isShapeMoveDownable(Shape s) {

		if (shape == null)
			return true;
		if (!playing || shape != s)
			return false;

		if (ground.isMoveable(shape, Shape.DOWN))
			return true;

		shape.die();
		ground.accept(shape);
		if (playing && !ground.isFull()) {
			shape = shapeFactory.getShape(this);
		}
		gamePanel.redisplay(ground, shape);
		if (gameInfoLabel != null)
			gameInfoLabel.setText(this.getNewInfo());

		return false;
	}
	public void shapeMovedDown(Shape s) {
		if (playing && ground != null && shape != null)
			gamePanel.redisplay(ground, shape);
	}
	public void newGame() {
		playing = true;
		ground.init();
		ground.addGroundListener(this);
		Global.CURRENT_SPEED = Global.DEFAULT_SPEED;
		shape = shapeFactory.getShape(this);
		if (playing)gamePanel.redisplay(ground, shape);
		if (gameInfoLabel != null)gameInfoLabel.setText(this.getNewInfo());

		for (GameListener l : listeners)
			l.gameStart();
	}

	public void stopGame() {
		if (shape == null)return;
		playing = false;
		for (GameListener l : listeners)l.gameOver();
	}
	public void pauseGame() {
		if (shape == null)
			return;
		shape.setPause(true);
		for (GameListener l : listeners)
			l.gamePause();
	}
	public void continueGame() {
		shape.setPause(false);
		for (GameListener l : listeners)
			l.gameContinue();
	}
	public boolean isPausingGame() {
		return shape.isPause();
	}
	public String getNewInfo() {
		if (!playing || ground.isFull())
			return "������ܰ��ʾ: �� Y ��ʼ����Ϸ";
		else
			return new StringBuffer().append("��ʾ: ").append(" �ٶ� ").append(
					shape.getSpeed()).append("����/��").toString();
	}

	public ShapeFactory getShapeFactory() {
		return shapeFactory;
	}

	public void setShapeFactory(ShapeFactory shapeFactory) {
		this.shapeFactory = shapeFactory;
	}

	public Ground getGround() {
		return ground;
	}

	public void setGround(Ground ground) {
		this.ground = ground;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public void beforeDeleteFullLine(Ground ground, int lineNum) {
		ground.changeFullLineColor(lineNum);
		gamePanel.redisplay(ground, shape);
		try {
			Thread.sleep(Global.STAY_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void fullLineDeleted(Ground ground, int deletedLineCount) {
		System.out.println("�ɹ����� " + deletedLineCount + " ��");
	}

	public boolean isPlaying() {
		if (playing && !ground.isFull())
			return true;
		return false;
	}

	public JLabel getGameInfoLabel() {
		return gameInfoLabel;
	}
	public void setGameInfoLabel(JLabel gameInfoLabel) {
		this.gameInfoLabel = gameInfoLabel;
		this.gameInfoLabel.setSize(Global.WIDTH * Global.CELL_WIDTH, 20);
		this.gameInfoLabel.setFont(new Font("����", Font.PLAIN, 12));
		gameInfoLabel.setText(this.getNewInfo());
	}

	public void groundIsFull(Ground ground) {
		if (playing) {
			playing = false;
			for (GameListener l : listeners)
				l.gameOver();
		}
	}

	public void addGameListener(GameListener l) {
		if (l != null)
			this.listeners.add(l);
	}

	public void removeGameListener(GameListener l) {
		if (l != null)
			this.listeners.remove(l);
	}

}
