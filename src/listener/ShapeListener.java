package listener;

import view.Shape;

/**
 * ͼ�μ��������ж�ͼ�ε��˶�״̬
 * @version 1.0
 * @author ������
 * 
 */

public interface ShapeListener {
	boolean isShapeMoveDownable(Shape shape);
	void shapeMovedDown(Shape shape);
}
