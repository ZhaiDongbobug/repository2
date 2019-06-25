package game.sprite;

import java.awt.image.BufferedImage;

public class Enemy extends MySprite{

	public static final int TYPE_BEE = 0 ;
	public static final int TYPE_PLANE = 1;
	public int type;
	public int score;

	public Enemy(BufferedImage img, int x, int y) {
		super(img,x,y);
	}


}
