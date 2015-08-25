package com.kingscastle.nuzi.towerdefence.framework;

import java.util.List;

public interface Input {



	public static class TouchEvent {
		public static final int TOUCH_DOWN = 0;
		public static final int TOUCH_UP = 1;
		public static final int TOUCH_DRAGGED = 2;
		public static final int TOUCH_HOLD = 3;

		public int type;
		public int x, y;
		public int pointer;

		@Override
		public String toString()
		{
			switch( type )
			{
			case 0: return "TouchEvent TOUCH_DOWN [ " + x + " , " + y + "]";
			case 1: return "TouchEvent TOUCH_UP [ " + x + " , " + y + "]";
			case 2: return "TouchEvent TOUCH_DRAGGED [ " + x + " , " + y + "]";
			case 3: return "TouchEvent TOUCH_HOLD [ " + x + " , " + y + "]";
			}

			return "TouchEvent UnknownTypeWtf [ " + x + " , " + y + "]";
		}
	}

	public boolean isTouchDown(int pointer);

	public int getTouchX(int pointer);

	public int getTouchY(int pointer);

	public List<TouchEvent> getTouchEvents();

}