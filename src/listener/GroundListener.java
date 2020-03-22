package listener;

import view.Ground;

/**
 * 障碍物监听器
 * 
 * @version 1.0
 * 
 * @author 李泽坤
 * 
 */

public interface GroundListener {
	//消除的行数
	void beforeDeleteFullLine(Ground ground, int lineNum);
	//消除
	void fullLineDeleted(Ground ground, int deletedLineCount);
	//障碍物满
	void groundIsFull(Ground ground);
}
