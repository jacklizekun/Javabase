package listener;

import config.Global;
import view.Ground;


/**
 * 不可消除的障碍物监听器
 * 
 * @version 1.0
 * 
 * @author 李泽坤
 * 
 */

public class MyGroundListener extends GroundAdapter {

	int deletedLineCount = 0;

	@Override
	public void fullLineDeleted(Ground ground, int deletedLineCount) {

		this.deletedLineCount += deletedLineCount;
		if ((deletedLineCount %= 10) == 9 || deletedLineCount > 2)
			for (int y = 0; y < Global.HEIGHT; y++)
				for (int x = 0; x < Global.WIDTH; x++)
					if (ground.isStubbornObstacle(x, y))
						ground.addObstacle(x, y);
	}

}
