package listener;

import view.Ground;

/**
 * �ϰ����¼�������
 * 
 * @version 1.0
 * 
 * @author ������
 * 
 */

public class GroundAdapter implements GroundListener {
	public void beforeDeleteFullLine(Ground ground, int lineNum) { }

	public void fullLineDeleted(Ground ground, int deletedLineCount) { }

	public void groundIsFull(Ground ground) { }

}
