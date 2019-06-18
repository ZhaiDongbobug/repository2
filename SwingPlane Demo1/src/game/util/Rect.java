package game.util;

public class Rect {
	public int left,right,top,bottom;
	public Rect(int x, int y, int w, int h) {
		setPostion(x, y, w, h);
	}

	private void setPostion(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		left = x;
		top = y;
		right = x + w;
		bottom = y + h;
	}
}
