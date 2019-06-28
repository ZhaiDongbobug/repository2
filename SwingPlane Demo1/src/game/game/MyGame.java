package game.game;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.sprite.Bee;
import game.sprite.Bullet;
import game.sprite.Enemy;
import game.sprite.Hero;
import game.sprite.MySprite;
import game.util.Rect;

public class MyGame extends JPanel implements Runnable {

	private static final boolean DEBUG = false;
	public static final int FLUSHTIME = 100;//游戏的每帧刷新间隔时间，匀速刷新
	//显示游戏屏幕的宽高
	public static final int ScreenWidth = 480;
	public static final int ScreenHeight = 852;
	public static final Rect SCREEN_RECT = new Rect(0,0,
			ScreenWidth,ScreenHeight);
	public static BufferedImage[] ImagePool;
	String[] ImageNames = {
		"airplane.png",
		"background.png",
		"bee.png",
		"bullet.png",
		"gameover.png",
		"hero0.png",
		"hero1.png",
		"pause.png",
		"start.png"
	};
	public static final int IMG_ENEMY1 = 0;
	public static final int IMG_BACKGROUND = 1;
	public static final int IMG_BEE = 2;
	public static final int IMG_BULLET = 3;
	public static final int IMG_GAMEOVER = 4;
	public static final int IMG_HERO0 = 5;
	public static final int IMG_HERO1 = 6;
	public static final int IMG_PAUSE = 7;
	public static final int IMG_START = 8;
	
	int game_state = 0;//控制游戏状态的状态机
	public static final int STATE_START = 0;
	public static final int STATE_GAMING = STATE_START + 1;
	public static final int STATE_PAUSE = STATE_GAMING + 1;
	public static final int STATE_OVER = STATE_PAUSE + 1;
	
	Random ran;
	public static MyGame instance;
	
	Hero hero;
	ArrayList<Enemy> enemys;
	int enemySpeed = 1;
	int enemyCoolDown = 1;
	int enemyBirthTime = 1;
	
	ArrayList<Bullet> bullets;
	int BulletSpeed = 1;
	int BulletCoolDown = 1;
	int BulletBirthTime = 1;
	
	public int score;
	
	public MyGame() {
		instance = this;
		ran = new Random();
		initImage();
		initAdapter();
		initSprite();
		
		score = 0;
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
		ImagePool = new BufferedImage[ImageNames.length];
		for(int i=0; i<ImagePool.length; i++) {
		try {
			ImagePool[i] = ImageIO.read(
					MyGame.class.getResource(
							"/res/" + ImageNames[i]));
		} catch (IOException e) {
			e.printStackTrace();
			}
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(ImagePool[IMG_BACKGROUND],
				0, 0, null);
		switch(game_state) {
		case STATE_START:
			BufferedImage img = ImagePool[IMG_START];
			drawState(g,img);
			break;
		case STATE_GAMING:
			for(int i = 0; i < bullets.size(); i++) {
			   bullets.get(i).draw(g);
			}
			for(int i = 0; i < enemys.size(); i++) {
			   enemys.get(i).draw(g);
			}
			hero.draw(g);
			break;
		case STATE_PAUSE:
			BufferedImage img1 = ImagePool[IMG_PAUSE];
			drawState(g,img1);
			break;
		case STATE_OVER:
			BufferedImage img2 = ImagePool[IMG_GAMEOVER];
			drawState(g,img2);
			break;
		}
			
	}
	private void drawState(Graphics g, BufferedImage img) {
		// TODO Auto-generated method stub
		int imgW = img.getWidth();
		int imgH = img.getHeight();
		int logolX = 200;
		int logolY = 200;
		g.drawImage(img, logolX, logolY, null);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				long startTime = System.currentTimeMillis();
				logic();
				repaint();
				long endTime = System.currentTimeMillis();
				long runTime = endTime - startTime;
				if(runTime < FLUSHTIME) {
					Thread.sleep(FLUSHTIME - runTime);
				}
			}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}

	private void logic() {
		// TODO Auto-generated method stub
		switch(game_state) {
		case STATE_GAMING :
			hero.logic();
			for(int i = 0; i < enemys.size(); i++) {
				if(!inScreen(enemys.get(i))) {
					enemys.remove(i);
					i--;
					continue;
				}
				enemys.get(i).logic();
				if(enemys.get(i).hit(hero)) {
					enemys.remove(i);
					i--;
					if(!DEBUG) {
						hero.hp--;
					}
					if(hero.hp < 1) {
						game_state = STATE_OVER;
						resetGame();
						return;//返回什么
					}
					
				}
			}
			for(int i = 0; i < bullets.size(); i++) {
				if(!inScreen(bullets.get(i))) {
					bullets.remove(i);
					i--;
				}
				bullets.get(i).logic();
				for(int j = 0; j < enemys.size(); j++) {
					if(bullets.get(i).hit(enemys.get(j))) {
						bullets.remove(i);
						score += enemys.get(j).score;
						if(enemys.get(i).type == Enemy.TYPE_BEE) {
							if(hero.fireLevel < Hero.TOP_FIRE) {
								hero.fireLevel ++;
							}
						}
						enemys.remove(j);//为什么没有 j--
						i--;
						break;
					}
				}
				
			}
			generateEnemy();
			break;
		}
	}

	private void generateEnemy() {
		if(enemyCoolDown < 0) {
			Enemy temp;
			int randomEnemy = ran.nextInt(100);
			if(randomEnemy < 10) {
				temp = birthBee();
			}else {
				temp = birthPlane();
			}
			enemyCoolDown = enemyBirthTime;
			enemys.add(temp);
		}
		enemyCoolDown --;
		
	}

	private Enemy birthPlane() {
		int ex = 24 + ran.nextInt(ScreenWidth - 72);
		int ey = 0;
		Enemy temp = new Enemy(ImagePool[IMG_ENEMY1], ex, ey);
		temp.setSpeed(0, enemySpeed);
		return temp;
	}

	private Enemy birthBee() {
		Bee bee;
		int dir = ran.nextInt(1);
		if(dir == 0) {
			int ex = 0;
			int ey = 100 + ran.nextInt(100);
			bee = new Bee(ImagePool[IMG_BEE], ex, ey);
		}else{
			int ex = ScreenWidth;
			int ey = 100 + ran.nextInt(100);
			bee = new Bee(ImagePool[IMG_BEE], ex, ey);
		}
		bee.dir = dir;
		return bee;
	}

	private void resetGame() {
		// TODO Auto-generated method stub
		enemys.clear();
		bullets.clear();
		score = 0;
		game_state = STATE_START;
	}

	public boolean inScreen(MySprite sprite) {
		return SCREEN_RECT.nineCollsion(sprite.myRect);
	}

	public void start() {
		new Thread(this).start();
		
	}
	
	
}
