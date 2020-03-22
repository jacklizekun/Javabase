package view;

import java.awt.Color;
import java.util.Random;

import config.Global;
import listener.ShapeListener;


/**
 * 多种图形
 * @version 1.0
 * 
 * @author 李泽坤
 * 
 */
public class ShapeFactory {

	protected static int shapes[][][] = new int[][][] {
	{ { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },

	{ { 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
	  { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
      { 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } },
	{ { 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 } },
	{ { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, } },
	{ { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
	{ { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
      { 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
	{ { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 } },};

	protected Random random = new Random();
	public static final Color DEFAULT_SHAPE_COLOR = new Color(0x990066);
	protected Color defaultShapeColor = DEFAULT_SHAPE_COLOR;
	protected boolean colorfulShape;

	public Shape getShape(ShapeListener shapeListener) {
		int type = random.nextInt(shapes.length);
		Shape shape = new Shape(shapes[type], random.nextInt(shapes[type].length));
		shape.setColor(colorfulShape ? Global.getRandomColor(): defaultShapeColor);
		shape.addShapeListener(shapeListener);
		return shape;
	}

	public Color getDefaultShapeColor() {
		return defaultShapeColor;
	}
	public void setDefaultShapeColor(Color defaultShapeColor) {
		this.defaultShapeColor = defaultShapeColor;
	}
	public boolean isColorfulShape() {
		return colorfulShape;
	}
	public void setColorfulShape(boolean colorfulShape) {
		this.colorfulShape = colorfulShape;
	}

}
