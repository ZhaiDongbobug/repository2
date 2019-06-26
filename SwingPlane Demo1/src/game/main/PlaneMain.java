package game.main;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import game.game.MyGame;

public class PlaneMain {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		JFrame window = new JFrame("SWing");
		MyGame game = new MyGame();
		setFrame(window,game);
		game.start();
	}

	private static void setFrame(JFrame window, MyGame game) {
		window.add(game);
		window.setSize(MyGame.ScreenWidth, MyGame.ScreenHeight);
		window.setAlwaysOnTop(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setIconImage(
				new ImageIcon("image/icon.jpg").getImage());
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	
}
