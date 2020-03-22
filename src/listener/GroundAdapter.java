package listener;

import view.Ground;

/**
 * 障碍物事件适配器
 * 
 * @version 1.0
 * 
 * @author 李泽坤
 * 
 */

public class GroundAdapter implements GroundListener {
	public void beforeDeleteFullLine(Ground ground, int lineNum) { }

	public void fullLineDeleted(Ground ground, int deletedLineCount) { }

	public void groundIsFull(Ground ground) { }

}
