package game.game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import game.sprite.Bullet;
import game.sprite.Enemy;
import game.sprite.Hero;
import game.util.Rect;

public class MyGame extends JPanel implements Runnable {

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
	
	Hero hero;
	ArrayList<Enemy> enemys;
	int EnemySpeed = 1;
	int EnemyCoolTime = 1;
	int EnemyBirthTime = 1;
	
	ArrayList<Bullet> bullets;
	int BulletSpeed = 1;
	int BulletCoolTime = 1;
	int BulletBirthTime = 1;
	
	public MyGame() {
		initImage();
		initAdapter();
		initSprite();
		
		game_state = STATE_START;
	}

	private void initSprite() {
		// TODO Auto-generated method stub
		BufferedImage[] heroimgs = new BufferedImage[2];
		heroimgs[0] = ImagePool[IMG_HERO0];
		heroimgs[1] = ImagePool[IMG_HERO1];
		int herox = 0;
		int heroy = 0;
		hero = new Hero(heroimgs, herox, heroy);
		enemys = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();
	}

	private void initAdapter() {
		// TODO Auto-generated method stub
		MouseAdapter ma = new MouseAdapter() {
			public void mouseMoved(MouseEvent event) {
				if(game_state == STATE_GAMING) {
					hero.setPostion(event.getX(), event.getY());
					return;//为什么要return
				}
				super.mouseMoved(event);//查一下日志，了解其逻辑
			}
			public void mouseEntered(MouseEvent arg0) {//了解参数的含义
				if(game_state == STATE_PAUSE) {
					game_state = STATE_GAMING;
					return;
				}
				super.mouseEntered(arg0);
			}
			public void mouseExited(MouseEvent arg0) {
				if(game_state == STATE_GAMING) {
					game_state = STATE_PAUSE;
					return;
				}
				super.mouseExited(arg0);
			}
			public void mouseClicked(MouseEvent e) {
				if(game_state == STATE_PAUSE) {
					game_state = STATE_GAMING;
					return;
				}else if(game_state == STATE_OVER) {
					game_state = STATE_START;
					return;
				}
				super.mouseClicked(e);
			}
		};
		this.addMouseListener(ma);
		this.addMouseMotionListener(ma);
		
	}

	private void initImage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
}
