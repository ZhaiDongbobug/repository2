package game.util;

//一个方块的类，用于检测碰撞
public class Rect {
	public int left,right,top,bottom;
	int[][] ninePoints;//九点判断法使用的坐标
	public Rect(int x, int y, int w, int h) {
		setPostion(x, y, w, h);
	}

	public void setPostion(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		left = x;
		top = y;
		right = x + w;
		bottom = y + h;
		
		setNinePoints(x,y,w,h);
	}

	private void setNinePoints(int x, int y, int w, int h) {
		ninePoints = new int[9][];
		for(int i = 0; i < ninePoints.length; i++) {
			int[] points = {x+(i%3)*w/2,y + (i/3)*h/2};
					ninePoints[i] = points;
			}	
	}
	
	public boolean nineCollsion(Rect other) {
		for(int i = 0; i < ninePoints.length; i++) {
			if(other.isPointIn(ninePoints[i])) {
				return true;
			}
		}
		for(int i = 0; i < other.ninePoints.length; i++) {
			if(this.isPointIn(other.ninePoints[i])) {
				return true;
			}
		}
		return false;
	}

	private boolean isPointIn(int[] point) {
		if(point[0] >= left && point[0] <= right &&
				point[1] >= top && point[1] <= bottom){
			return true;
		}
		return false;
	}
}
