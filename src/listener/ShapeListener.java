package listener;

import view.Shape;

/**
 * 图形监听器，判断图形的运动状态
 * @version 1.0
 * @author 李泽坤
 * 
 */

public interface ShapeListener {
	boolean isShapeMoveDownable(Shape shape);
	void shapeMovedDown(Shape shape);
}
