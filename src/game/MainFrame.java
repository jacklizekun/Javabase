package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import config.Global;
import listener.GameListener;
import listener.MyGroundListener;
import view.GameOptionPanel;
import view.GamePanel;
import view.Ground;
import view.ShapeFactory;


/**
 * 主界面
 * 
 * @version 1.0
 * 
 * @author 李泽坤
 * 
 */
public class MainFrame extends JFrame implements GameListener {

	//游戏控制对象
	private final Controller controller;
	//游戏操作选项
	private final GameOptionPanel gameOptionPanel;
	//游戏面板
	private final GamePanel gamePanel;
	//画面
	private final Ground ground;
	//下落的方块图形
	private final ShapeFactory shapeFactory;
	//构造器MainFrame
	public MainFrame(Controller c) {
		//容器的标题
		this.setTitle("坤坤——俄罗斯方块");
		//设置可以关闭
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//取消布局器
		this.setLayout(null);
		//不可以改变大小
		this.setResizable(false);
		//如果没有说明框
		if(c.getGameInfoLabel() == null)
			c.setGameInfoLabel(new JLabel());
		this.controller = c;
		
		//获取图形工厂
		this.shapeFactory = c.getShapeFactory();
		//设置画面
		this.ground = c.getGround();
		this.gamePanel = c.getGamePanel();
		this.gameOptionPanel = new GameOptionPanel();
		//游戏提示框
		final JLabel infoLabel = c.getGameInfoLabel();
		//游戏障碍物的监听器新建
		MyGroundListener mgl = new MyGroundListener();
		//为游戏画面添加监听器
		ground.addGroundListener(mgl);
		//设置新建游戏的按钮
		gameOptionPanel.getNewGameButton().setEnabled(true);
		//不设置终止游戏的按钮
		gameOptionPanel.getStopGameButton().setEnabled(false);
		//聚焦监听器
		this.addFocusListener(new FocusAdapter() {
			//焦点不变
			public void focusGained(FocusEvent arg0) {
			}
			//失去焦点
			public void focusLost(FocusEvent arg0) {
				//暂停游戏
				controller.pauseGame();
				if (gameOptionPanel.getPauseButton().isEnabled())
					gameOptionPanel.getPauseButton().setText("继续游戏");
			}
		});
		//游戏面板失去焦点
		gamePanel.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0) {
			}
			public void focusLost(FocusEvent arg0) {
				controller.pauseGame();
				if (gameOptionPanel.getPauseButton().isEnabled())
					gameOptionPanel.getPauseButton().setText("继续游戏");
			}
		});
		//游戏主框架配置面板
		gameOptionPanel.getNewGameButton().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (controller.isPlaying()) {
							return;
						}
						int lineNum = gameOptionPanel.getLineNum();
						int obstacleNum = gameOptionPanel.getObstacleNum();
						controller.newGame();
						ground.generateSomeStochasticObstacle(obstacleNum,
								lineNum);
					}
				});

		gameOptionPanel.getStopGameButton().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						controller.stopGame();
					}
				});

		gameOptionPanel.getPauseButton().setEnabled(false);
		gameOptionPanel.getPauseButton().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (controller.isPausingGame()) {
							controller.continueGame();

						} else {
							controller.pauseGame();
						}
						if (controller.isPausingGame())
							gameOptionPanel.getPauseButton().setText("继续游戏");
						else
							gameOptionPanel.getPauseButton().setText("暂停游戏");
					}
				});

		gameOptionPanel.getCheckBox_drawGridding().addChangeListener(
				new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						gameOptionPanel.getButton_griddingColor().setVisible(
								gameOptionPanel.getCheckBox_drawGridding()
										.isSelected());
						MainFrame.this.refreshOption();
					}
				});
		gameOptionPanel.getCheckBox_colorfulShape().addChangeListener(
				new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						gameOptionPanel.getButton_shapeColor().setVisible(
								gameOptionPanel.getCheckBox_colorfulShape()
										.isSelected());
						MainFrame.this.refreshOption();
					}
				});
		gameOptionPanel.getCheckBox_colorfulObstacle().addChangeListener(
				new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						gameOptionPanel.getButton_obstacleColor().setVisible(
								gameOptionPanel.getCheckBox_colorfulObstacle()
										.isSelected());
						MainFrame.this.refreshOption();
					}
				});

		gameOptionPanel.getButton_shapeColor().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Color shapeColor = JColorChooser
								.showDialog(MainFrame.this, "请选择图形的颜色",
										new Color(0xFF4500));
						if (shapeColor != null)
							shapeFactory.setDefaultShapeColor(shapeColor);
					}
				});
		gameOptionPanel.getButton_griddingColor().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Color griddingColor = JColorChooser.showDialog(
								MainFrame.this, "请选择网格的颜色", Color.LIGHT_GRAY);
						if (griddingColor != null)
							ground.setGriddingColor(griddingColor);
					}
				});
		gameOptionPanel.getButton_obstacleColor().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Color obstacleColor = JColorChooser.showDialog(
								MainFrame.this, "请选择障碍物的颜色", Color.DARK_GRAY);
						if (obstacleColor != null)
							ground.setObstacleColor(obstacleColor);
					}
				});
		gameOptionPanel.getButton_fullLineColor().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Color fullLineColor = JColorChooser.showDialog(
								MainFrame.this, "请选择满行的效果的颜色", Color.DARK_GRAY);
						if (fullLineColor != null) {
							ground.setFullLineColor(fullLineColor);
						}
					}
				});
		gameOptionPanel.getButtonBackgroundColor().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Color backgroundColor = JColorChooser
								.showDialog(MainFrame.this, "请选择背景的颜色",
										new Color(0xcfcfcf));
						if (backgroundColor != null)
							gamePanel.setBackgroundColor(backgroundColor);
					}
				});

		gameOptionPanel.getButton_default().addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						gamePanel
								.setBackgroundColor(GamePanel.DEFAULT_BACKGROUND_COLOR);
						gameOptionPanel.getCheckBox_drawGridding().setSelected(
								false);
						ground.setGriddingColor(Ground.DEFAULT_GRIDDING_COLOR);
						gameOptionPanel.getCheckBox_colorfulShape()
								.setSelected(true);
						shapeFactory
								.setDefaultShapeColor(ShapeFactory.DEFAULT_SHAPE_COLOR);
						gameOptionPanel.getCheckBox_colorfulObstacle()
								.setSelected(true);
						ground.setObstacleColor(Ground.DEFAULT_OBSTACLE_COLOR);
						gameOptionPanel.getTextField_obstacleNum()
								.setText("30");
						gameOptionPanel.getTextField_lineNum().setText("0");
						gameOptionPanel.getTextField_stayTime().setText("300");
						ground.setFullLineColor(Ground.DEFAULT_FULL_LINE_COLOR);
					}
				});

		JPanel subPanel = new JPanel();
		subPanel.setLayout(null);
		subPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

		subPanel.setSize(gamePanel.getSize().width + 3,infoLabel.getSize().height + gamePanel.getSize().height + 2);
		infoLabel.setBounds(5, 0, infoLabel.getSize().width - 5, infoLabel.getSize().height);
		gamePanel.setBounds(1, infoLabel.getSize().height,gamePanel.getSize().width, gamePanel.getSize().height);
		subPanel.add(infoLabel);
		subPanel.add(gamePanel);

		int left = 10, left2 = 5;
		gameOptionPanel.setBounds(left, 21, gameOptionPanel.getSize().width,
				gameOptionPanel.getSize().height);
		subPanel.setBounds(left + left2 + gameOptionPanel.getSize().width, 1,
				subPanel.getSize().width, subPanel.getSize().height);

		JPanel infoPanel = new JPanel();
		infoPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		infoPanel.setLayout(null);
		infoPanel.setBounds(10, 25 + gameOptionPanel.getSize().height,
				gameOptionPanel.getSize().width, subPanel.getSize().height
						- gameOptionPanel.getSize().height - 25);

		final JLabel infoTitleLable = new JLabel();
		infoTitleLable.setFont(new Font("宋体", Font.PLAIN, 12));
		infoTitleLable.setText(Global.TITLE_LABEL_TEXT);
		infoTitleLable.setBounds(10, 5, infoPanel.getSize().width - 10, 20);

		final JTextArea InfoTextArea = new JTextArea();
		InfoTextArea.setFont(new Font("宋体", Font.PLAIN, 12));
		InfoTextArea.setText(Global.INFO_LABEL_TEXT);
		InfoTextArea.setFocusable(false);
		InfoTextArea.setBackground(this.getBackground());
		InfoTextArea.setBounds(10, 25, infoPanel.getSize().width - 20,
				infoPanel.getSize().height - 50);

		infoPanel.add(infoTitleLable);
		infoPanel.add(InfoTextArea);

		gameOptionPanel.getCheckBox_colorfulObstacle().setFocusable(false);
		gameOptionPanel.getCheckBox_colorfulShape().setFocusable(false);
		gameOptionPanel.getCheckBox_drawGridding().setFocusable(false);

		this.setSize(
						gameOptionPanel.getSize().width+ gamePanel.getSize().width + left + left2 + 15,
						gamePanel.getSize().height > gameOptionPanel.getSize().height + 20 ? gamePanel.getSize().height + 60: gameOptionPanel.getSize().height + 60);

		this.setLocation(this.getToolkit().getScreenSize().width / 2- this.getWidth() / 2, this.getToolkit().getScreenSize().height/ 2 - this.getHeight() / 2);

		gamePanel.addKeyListener(controller);
		gameOptionPanel.addKeyListener(controller);
		this.addKeyListener(controller);
		controller.addGameListener(this);
		subPanel.addKeyListener(controller);

		this.getContentPane().add(gameOptionPanel);
		this.getContentPane().add(infoPanel);
		this.getContentPane().add(subPanel);
	}

	public void gameOver() {

		gameOptionPanel.getTextField_lineNum().setFocusable(true);
		gameOptionPanel.getTextField_stayTime().setFocusable(true);
		gameOptionPanel.getTextField_obstacleNum().setFocusable(true);
		gameOptionPanel.getPauseButton().setEnabled(false);

		gameOptionPanel.getStopGameButton().setEnabled(false);
		gameOptionPanel.getNewGameButton().setEnabled(true);

		gameOptionPanel.getPauseButton().setText("暂停/继续");
	}

	public void gameStart() {
		gameOptionPanel.getTextField_lineNum().setFocusable(false);
		gameOptionPanel.getTextField_stayTime().setFocusable(false);
		gameOptionPanel.getTextField_obstacleNum().setFocusable(false);
		gameOptionPanel.getPauseButton().setEnabled(true);
		gameOptionPanel.getNewGameButton().setEnabled(false);
		gameOptionPanel.getStopGameButton().setEnabled(true);
	}

	public void gameContinue() {
		gameOptionPanel.getPauseButton().setText("暂停游戏");
	}

	public void gamePause() {
		gameOptionPanel.getPauseButton().setText("继续游戏");
	}

	private void refreshOption() {
		int stayTime = gameOptionPanel.getStayTime();

		ground.setDrawGridding(gameOptionPanel.isDrawGridding());
		shapeFactory.setColorfulShape(!gameOptionPanel.isColorfulShape());
		ground.setColorfulSupport(!gameOptionPanel.isColorfulObstacle());

		Global.STAY_TIME = stayTime;

	}

	public static void main(String args[]) {
		try {
			//创建控制实例，注入图形、画面、容器面板
			Controller controller = new Controller(new ShapeFactory(),new Ground(), new GamePanel());
			//在主框架页面中添加控制器
			MainFrame frame = new MainFrame(controller);
			//主页面可见
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
