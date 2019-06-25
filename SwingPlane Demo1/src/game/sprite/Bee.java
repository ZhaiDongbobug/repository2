package game.sprite;

import java.awt.image.BufferedImage;

public class Bee extends Enemy{

	public int dir;

	public Bee(BufferedImage img, int x, int y) {
		type = TYPE_BEE;
	}
}
