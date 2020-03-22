package view;

import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;


/**
 * 设置项面板
 * 
 * @version 1.0
 * 
 * @author 李泽坤
 * 
 */
public class GameOptionPanel extends JPanel {
	//图片路径src路径下
	URL imageURL = GameOptionPanel.class.getResource("/kunkun.png");
	private ImageIcon Icon = imageURL != null ? new ImageIcon(imageURL): null;
	//容器
	private JFrame frame;
	//满行效果时间
	private final JTextField textField_stayTime = new JTextField();
	//随机障碍物数量
	private final JTextField textField_obstacleNum = new JTextField();
	//障碍物行数
	private JTextField textField_lineNum = new JTextField();
	//开启新游戏按钮
	private final JButton newGameButton = new JButton();
	//停止游戏按钮
	private final JButton stopGameButton = new JButton();
	//暂停游戏按钮
	private final JButton pauseButton = new JButton();
	//确定关闭显示网格
	private final JCheckBox checkBox_drawGridding = new JCheckBox();
	//确定关闭彩色图形的按钮
	private final JCheckBox checkBox_colorfulShape = new JCheckBox();
	//确认关闭彩色障碍物的按钮
	private final JCheckBox checkBox_colorfulObstacle = new JCheckBox();
	//障碍物颜色的按钮
	private final JButton button_obstacleColor;
	//设置网格颜色
	private final JButton button_griddingColor;
	//设置彩色的图形颜色
	private final JButton button_shapeColor;
	//设置满行的颜色
	private final JButton button_fullLineColor;
	//设置背景颜色
	private final JButton buttonBackgroundColor;
	//恢复默认设置的按钮
	private final JButton button_default;
	//选项面板
	public GameOptionPanel() {
		//设置大小，整个容器面板的宽度为280px
		setSize(280, 334);
		setLayout(null);
		setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		setFocusable(false);
		//面板分隔组件
		final JSeparator separator = new JSeparator();
		separator.setBounds(12, 69, 255, 30);
		add(separator);
		//设置颜色的按钮
		button_griddingColor = new JButton();
		button_griddingColor.setBounds(135, 5, 96, 23);
		separator.add(button_griddingColor);

		button_griddingColor.setFont(new Font("宋体", Font.PLAIN, 12));
		button_griddingColor.setFocusable(false);
		button_griddingColor.setText("设置颜色");

		button_griddingColor.setVisible(false);

		checkBox_drawGridding.setBounds(5, 5, 105, 23);
		separator.add(checkBox_drawGridding);

		checkBox_drawGridding.setText("显示网格");
		checkBox_drawGridding.setFont(new Font("宋体", Font.PLAIN, 12));

		final JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(12, 105, 255, 39);
		add(separator_1);

		button_shapeColor = new JButton();
		button_shapeColor.setBounds(135, 10, 96, 23);
		separator_1.add(button_shapeColor);

		button_shapeColor.setFont(new Font("宋体", Font.PLAIN, 12));
		button_shapeColor.setFocusable(false);
		button_shapeColor.setText("设置颜色");

		checkBox_colorfulShape.setText("关闭彩色图形");
		checkBox_colorfulShape.setBounds(5, 10, 105, 23);
		separator_1.add(checkBox_colorfulShape);

		checkBox_colorfulShape.setSelected(true);
		checkBox_colorfulShape.setFont(new Font("宋体", Font.PLAIN, 12));

		final JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(12, 145, 255, 39);
		add(separator_2);

		button_obstacleColor = new JButton();
		button_obstacleColor.setBounds(135, 10, 96, 23);
		separator_2.add(button_obstacleColor);

		button_obstacleColor.setFont(new Font("宋体", Font.PLAIN, 12));
		button_obstacleColor.setFocusable(false);
		button_obstacleColor.setText("设置颜色");

		checkBox_colorfulObstacle.setText("关闭彩色障碍物");
		checkBox_colorfulObstacle.setBounds(5, 10, 122, 23);
		separator_2.add(checkBox_colorfulObstacle);

		checkBox_colorfulObstacle.setSelected(true);
		checkBox_colorfulObstacle.setFont(new Font("宋体", Font.PLAIN, 12));

		final JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(12, 185, 255, 33);
		add(separator_3);

		final JLabel label_7 = new JLabel();
		label_7.setBounds(10, 10, 48, 15);
		separator_3.add(label_7);
		label_7.setFont(new Font("宋体", Font.PLAIN, 12));
		label_7.setFocusable(false);
		label_7.setText("随机生成");

		textField_obstacleNum.setFont(new Font("宋体", Font.PLAIN, 12));
		textField_obstacleNum.setBounds(65, 10, 32, 15);
		separator_3.add(textField_obstacleNum);
		textField_obstacleNum.setText("10");

		final JLabel label_2 = new JLabel();
		label_2.setBounds(100, 10, 18, 15);
		separator_3.add(label_2);
		label_2.setText("个");
		label_2.setFont(new Font("宋体", Font.PLAIN, 12));

		textField_lineNum.setBounds(120, 10, 24, 15);
		textField_lineNum.setFont(new Font("宋体", Font.PLAIN, 12));
		separator_3.add(textField_lineNum);
		//

		textField_lineNum.setText("0");

		final JLabel label_8 = new JLabel();
		label_8.setBounds(150, 10, 66, 15);
		separator_3.add(label_8);
		label_8.setFont(new Font("宋体", Font.PLAIN, 12));
		label_8.setFocusable(false);
		label_8.setText("行障碍物");

		final JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(12, 220, 255, 33);
		add(separator_4);

		textField_stayTime.setFont(new Font("宋体", Font.PLAIN, 12));
		textField_stayTime.setBounds(110, 14, 42, 15);
		separator_4.add(textField_stayTime);
		textField_stayTime.setText("300");

		final JLabel label_1 = new JLabel();
		label_1.setBounds(160, 14, 24, 15);
		separator_4.add(label_1);
		label_1.setFont(new Font("宋体", Font.PLAIN, 12));
		label_1.setFocusable(false);
		label_1.setText("毫秒");

		button_fullLineColor = new JButton();
		button_fullLineColor.setBounds(195, 10, 60, 23);
		separator_4.add(button_fullLineColor);

		button_fullLineColor.setFont(new Font("宋体", Font.PLAIN, 12));
		button_fullLineColor.setFocusable(false);
		button_fullLineColor.setText("颜色");

		final JLabel label = new JLabel();
		label.setBounds(10, 14, 120, 15);
		separator_4.add(label);
		label.setFont(new Font("宋体", Font.PLAIN, 12));
		label.setFocusable(false);
		label.setText("消除效果时间：");

		final JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(15, 260, 250, 69);
		add(separator_5);

		stopGameButton.setText("停止游戏");

		stopGameButton.setBounds(10, 10, 101, 23);
		separator_5.add(stopGameButton);
		stopGameButton.setFont(new Font("宋体", Font.PLAIN, 12));
		stopGameButton.setFocusable(false);

		pauseButton.setBounds(135, 10, 103, 23);
		separator_5.add(pauseButton);
		pauseButton.setText("暂停/继续");
		pauseButton.setFont(new Font("宋体", Font.PLAIN, 12));
		pauseButton.setFocusable(false);

		newGameButton.setFont(new Font("宋体", Font.PLAIN, 12));
		newGameButton.setBounds(65, 40, 110, 23);
		separator_5.add(newGameButton);
		newGameButton.setFocusable(false);
		newGameButton.setText("开始新游戏");
		
		final JLabel label_logo = Icon == null ? new JLabel() : new JLabel(
				Icon);
		label_logo.setBounds(140, 10, 125, 50);
		add(label_logo);

		final JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(12, 35, 119, 33);
		add(separator_6);

		buttonBackgroundColor = new JButton();
		buttonBackgroundColor.setBounds(5, 5, 110, 23);
		separator_6.add(buttonBackgroundColor);
		buttonBackgroundColor.setFont(new Font("宋体", Font.PLAIN, 12));
		buttonBackgroundColor.setFocusable(false);
		buttonBackgroundColor.setText("设置背景颜色");

		button_default = new JButton();
		button_default.setFont(new Font("宋体", Font.PLAIN, 12));
		button_default.setText("恢复默认设置");
		button_default.setBounds(17, 5, 110, 23);
		button_default.setFocusable(false);
		add(button_default);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public int getObstacleNum() {
		try {
			return Integer.parseInt(textField_obstacleNum.getText());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public int getStayTime() {
		try {
			return Integer.parseInt(textField_stayTime.getText());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public JButton getNewGameButton() {
		return newGameButton;
	}

	public JButton getStopGameButton() {
		return stopGameButton;
	}

	public int getLineNum() {
		try {
			return Integer.parseInt(textField_lineNum.getText());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public void setTextField_lineNum(JTextField textField_lineNum) {
		this.textField_lineNum = textField_lineNum;
	}

	public JCheckBox getCheckBox_colorfulShape() {
		return checkBox_colorfulShape;
	}

	public JCheckBox getCheckBox_drawGridding() {
		return checkBox_drawGridding;
	}

	public JTextField getTextField_stayTime() {
		return textField_stayTime;
	}

	public JTextField getTextField_obstacleNum() {
		return textField_obstacleNum;
	}

	public JTextField getTextField_lineNum() {
		return textField_lineNum;
	}

	public boolean isDrawGridding() {

		return checkBox_drawGridding.isSelected();
	}

	public boolean isColorfulShape() {
		return checkBox_colorfulShape.isSelected();
	}

	public boolean isColorfulObstacle() {
		return checkBox_colorfulObstacle.isSelected();
	}

	public ImageIcon getIcon() {
		return Icon;
	}

	public void setIcon(ImageIcon Icon) {
		this.Icon = Icon;
	}

	public JButton getPauseButton() {
		return pauseButton;
	}

	public JCheckBox getCheckBox_colorfulObstacle() {
		return checkBox_colorfulObstacle;
	}

	public JButton getButton_obstacleColor() {
		return button_obstacleColor;
	}

	public JButton getButton_griddingColor() {
		return button_griddingColor;
	}

	public JButton getButton_shapeColor() {
		return button_shapeColor;
	}

	public JButton getButton_fullLineColor() {
		return button_fullLineColor;
	}

	public JButton getButtonBackgroundColor() {
		return buttonBackgroundColor;
	}

	public JButton getButton_default() {
		return button_default;
	}

}
