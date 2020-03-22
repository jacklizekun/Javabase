package view;

import java.awt.Color;


/**
 * 障碍物设置
 * 
 * @version 1.0
 * 
 * @author 李泽坤
 * 
 */
public class UnitType implements Cloneable {
	private UnitType(int value) {
		super();
		this.value = value;
	}
	private UnitType(int value, Color color) {
		super();
		this.value = value;
		this.color = color;
	}
	//空白格
	private static final int BLANK_VALUE = 0;
	//边框
	private static final int STUBBORN_OBSTACLE_VALUE = 1;
	//障碍物
	private static final int OBSTACLE_VALUE = 2;
	//空白格子颜色设置
	public static final UnitType BLANK = new UnitType(BLANK_VALUE, Color.WHITE);
	//边框颜色设置
	public static final UnitType STUBBORN_OBSTACLE = new UnitType(STUBBORN_OBSTACLE_VALUE, new Color(0x808000));
	//障碍物设置
	public static final UnitType OBSTACLE = new UnitType(OBSTACLE_VALUE,Color.DARK_GRAY);

	//类型：空白格、边框、障碍物
	private int value;
	//类型对应的颜色
	private Color color;


	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	//相同复制单元块
	@Override
	public UnitType clone() {
		//返回相同类型和颜色的单元块
		return new UnitType(this.value, this.color);
	}
	public void cloneProperties(UnitType ut) {
		this.color = ut.color;
		this.value = ut.value;
	}
	//重写哈希算法
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}
	//重写比较方法
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		//获取类对象，并进行强转
		if (getClass() != obj.getClass())
			return false;
		final UnitType other = (UnitType) obj;
		if (value != other.value)
			return false;
		return true;
	}

}
