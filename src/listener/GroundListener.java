package listener;

import view.Ground;

/**
 * �ϰ��������
 * 
 * @version 1.0
 * 
 * @author ������
 * 
 */

public interface GroundListener {
	//����������
	void beforeDeleteFullLine(Ground ground, int lineNum);
	//����
	void fullLineDeleted(Ground ground, int deletedLineCount);
	//�ϰ�����
	void groundIsFull(Ground ground);
}
