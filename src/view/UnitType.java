package view;

import java.awt.Color;


/**
 * �ϰ�������
 * 
 * @version 1.0
 * 
 * @author ������
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
	//�հ׸�
	private static final int BLANK_VALUE = 0;
	//�߿�
	private static final int STUBBORN_OBSTACLE_VALUE = 1;
	//�ϰ���
	private static final int OBSTACLE_VALUE = 2;
	//�հ׸�����ɫ����
	public static final UnitType BLANK = new UnitType(BLANK_VALUE, Color.WHITE);
	//�߿���ɫ����
	public static final UnitType STUBBORN_OBSTACLE = new UnitType(STUBBORN_OBSTACLE_VALUE, new Color(0x808000));
	//�ϰ�������
	public static final UnitType OBSTACLE = new UnitType(OBSTACLE_VALUE,Color.DARK_GRAY);

	//���ͣ��հ׸񡢱߿��ϰ���
	private int value;
	//���Ͷ�Ӧ����ɫ
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

	//��ͬ���Ƶ�Ԫ��
	@Override
	public UnitType clone() {
		//������ͬ���ͺ���ɫ�ĵ�Ԫ��
		return new UnitType(this.value, this.color);
	}
	public void cloneProperties(UnitType ut) {
		this.color = ut.color;
		this.value = ut.value;
	}
	//��д��ϣ�㷨
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}
	//��д�ȽϷ���
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		//��ȡ����󣬲�����ǿת
		if (getClass() != obj.getClass())
			return false;
		final UnitType other = (UnitType) obj;
		if (value != other.value)
			return false;
		return true;
	}

}
