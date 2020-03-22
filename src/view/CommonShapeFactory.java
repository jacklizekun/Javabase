package view;


import java.awt.Color;

import config.Global;
import listener.ShapeListener;

/**
 * ≈‰÷√Œƒº˛
 * 
 * @version 1.0
 * 
 * @author ¿Ó‘Û¿§
 * 
 */
public class CommonShapeFactory extends ShapeFactory {

	@Override
	public Shape getShape(ShapeListener shapeListener) {
		int type = random.nextInt(shapes.length);
		int status = random.nextInt(shapes[type].length);
		Shape shape = new Shape(shapes[type], status);
		shape.setColor(isColorfulShape() ? getColorByType(type)
				: getDefaultShapeColor());
		shape.addShapeListener(shapeListener);
		return shape;
	}

	private Color getColorByType(int type) {
		if (type < 0 || type >= Global.COMMON_COLORS.size())
			return getDefaultShapeColor();
		return Global.COMMON_COLORS.get(type);
	}
}
