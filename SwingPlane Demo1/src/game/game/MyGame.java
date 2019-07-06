package game.game;

import java.awt.Color;
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

	public static final boolean DEBUG = false;
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
	};//给定了图片的数量，并且从资源文件调用时会用到
	public static final int IMG_ENEMY1 = 0;
	public static final int IMG_BACKGROUND = 1;
	public static final int IMG_BEE = 2;
	public static final int IMG_BULLET = 3;
	public static final int IMG_GAMEOVER = 4;
	public static final int IMG_HERO0 = 5;
	public static final int IMG_HERO1 = 6;
	public static final int IMG_PAUSE = 7;
	public static final int IMG_START = 8;
	// 到时用其引用相应图片
	int game_state = 0;//控制游戏状态的状态机
	public static final int STATE_START = 0;
	public static final int STATE_GAMING = STATE_START + 1;
	public static final int STATE_PAUSE = STATE_GAMING + 1;
	public static final int STATE_OVER = STATE_PAUSE + 1;
	
	Random ran;//游戏中的随机数
	public static MyGame instance;//单例模式，本类对象
	
	Hero hero;//主角精灵
	ArrayList<Enemy> enemys;//敌人精灵们
	int enemySpeed = 10;
	int enemyCoolDown = 0;//生成冷却时间
	int enemyBirthTime = 4;//敌机生成间隔
	
	ArrayList<Bullet> bullets;//子弹精灵们
	
	public int score;//分数
	
	public MyGame() {
		instance = this;
		ran = new Random();//初始化随机数
		initImage();//初始化图片资源
		initAdapter();//初始化适配器
		initSprite();//初始化各种精灵
		
		score = 0;
		game_state = STATE_START;//游戏开始时显示开始界面
	}

	private void initSprite() {
		// TODO Auto-generated method stub
		BufferedImage[] heroimgs = new BufferedImage[2];//有两种机型
		heroimgs[0] = ImagePool[IMG_HERO0];
		heroimgs[1] = ImagePool[IMG_HERO1];
		int herox = (ScreenWidth-heroimgs[0].getWidth())/2;
		int heroy = ScreenHeight - heroimgs[0].getHeight()-50;
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
					return;
				}
				super.mouseMoved(event);//查一下日志，了解其逻辑
			}
			public void mouseEntered(MouseEvent arg0) {//鼠标滑入游戏界面
				if(game_state == STATE_PAUSE) {
					game_state = STATE_GAMING;
					return;
				}
				super.mouseEntered(arg0);
			}
			public void mouseExited(MouseEvent arg0) {//鼠标滑出游戏界面
				if(game_state == STATE_GAMING) {
					game_state = STATE_PAUSE;
					return;
				}
				super.mouseExited(arg0);
			}
			public void mouseClicked(MouseEvent e) {//鼠标点击状态
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
		System.out.println(System.getProperty("user.dir"));
		//初始化图片数组
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
			drawUI(g);
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
		int imgW = img.getWidth();//获得图片宽度
		int imgH = img.getHeight();//获得图片高度
		int logolX = (ScreenWidth - imgW)/2;
		int logolY = (ScreenHeight - imgH)>>1 - 50;
		g.drawImage(img, logolX, logolY, null);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				long startTime = System.currentTimeMillis();
				logic();//游戏逻辑，所有需要自动执行的操作
				repaint();//重新绘制，刷新屏幕
				long endTime = System.currentTimeMillis();
				long runTime = endTime - startTime;
				if(runTime < FLUSHTIME) {
					//匀速刷新，运行太过卡顿则不休眠
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
			//主角逻辑
			hero.logic();
			//敌机的逻辑
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
						return;
					}
					
				}
			}
			//子弹的逻辑
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
			//生成敌人的逻辑
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
	public void addBullet(int tx, int ty, int vx, int vy) {
		Bullet temp = new Bullet(
				ImagePool[IMG_BULLET],tx, ty);
		temp.setSpeed(vx, vy);
		bullets.add(temp);
	}
	private void drawUI(Graphics g) {
		g.setColor(Color.red);
		g.drawString("生命值: "+hero.hp, 20, 20);
		g.drawString("得分: "+score, 20, 60);
	}
}
