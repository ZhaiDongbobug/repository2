package game.game;

import java.awt.image.BufferedImage;

import game.util.Rect;

public class MyGame {

	public static final int FLUSHTIME = 100;//游戏的每帧刷新间隔时间，匀速刷新
	//显示游戏屏幕的宽高
	public static final int ScreenWidth = 480;
	public static final int ScreenHeight = 852;
	public static final Rect SCREEN_RECT = new Rect(0,0,
			ScreenWidth,ScreenHeight);
	public static BufferedImage[] ImagePool;
	public static final int IMG_ENMEY1 = 0;
	public static final int IMG_BACKGROUND = 1;
	public static final int IMG_BEE = 2;
	public static final int IMG_BULLET = 3;
	public static final int IMG_GAMEOVER = 4;
	public static final int IMG_HERO0 = 5;
	public static final int IMG_HERO1 = 6;
	public static final int IMG_PASUE = 7;
	public static final int IMG_START = 8;
	
	int game_state = 0;//控制游戏状态的状态机
	public static final int STATE_START = 0;
	public static final int STATE_GAMING = STATE_START + 1;
	public static final int STATE_PAUSE = STATE_GAMING + 1;
	public static final int STATE_OVER = STATE_PAUSE + 1;
	
}
