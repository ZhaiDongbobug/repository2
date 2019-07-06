package game.main;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import game.game.MyGame;

public class PlaneMain {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//声明并创建一个窗体
		JFrame window = new JFrame("SWing");
		//声明并创建游戏类的内容对象
		MyGame game = new MyGame();
		//根据显示内容进行窗体设置
		setFrame(window,game);
		//开始游戏
		game.start();
	}

	private static void setFrame(JFrame window, MyGame game) {
		window.add(game);//设置显示内容
		window.setSize(MyGame.ScreenWidth, MyGame.ScreenHeight);
		//设置窗体大小
		window.setAlwaysOnTop(true);//一直在最前
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置关闭时的默认操作
		window.setIconImage(
				new ImageIcon("image/icon.jpg").getImage());//设置窗体图标
		window.setLocationRelativeTo(null);//设置相关显示内容
		window.setVisible(true);//设置显示,立即绘制
	}

	
}
