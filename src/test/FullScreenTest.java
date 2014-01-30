package test;

import java.awt.*;
import javax.swing.JFrame;
import screen.LoadScreen;

/**
 * The FullScreenTest class tests initializing and displaying full screen
 * graphics modes to verify that the SimpleScreenManager class functions
 * properly
 * 
 * @author Marcos Davila
 * @date 1/29/2014
 */

@SuppressWarnings("serial")
public class FullScreenTest extends JFrame {

	final static int RESOLUTION_WIDTH = 1366;
	final static int RESOLUTION_HEIGHT = 768;
	final static int BIT_DEPTH = 24;
	
	public static void main(String[] args) {

		DisplayMode displayMode;

		if (args.length == 3) {
			displayMode = new DisplayMode(Integer.parseInt(args[0]),
					Integer.parseInt(args[1]), Integer.parseInt(args[2]),
					DisplayMode.REFRESH_RATE_UNKNOWN);
		} else {
			displayMode = new DisplayMode(RESOLUTION_WIDTH, RESOLUTION_HEIGHT, BIT_DEPTH,
					DisplayMode.REFRESH_RATE_UNKNOWN);
		}

		FullScreenTest test = new FullScreenTest();
		test.run(displayMode);
	}

	private static final long DEMO_TIME = 5000;

	public void run(DisplayMode displayMode) {
		setBackground(Color.blue);
		setBackground(Color.white);
		setFont(new Font("Dialog", Font.PLAIN, 24));

		LoadScreen screen = new LoadScreen();
		try {
			screen.setFullScreen(displayMode, this);
			try {
				Thread.sleep(DEMO_TIME);
			} catch (InterruptedException ex) {
				System.out
						.println("Seems like your computer does not support this display mode!");
			}
		} finally {
			screen.restoreScreen();
		}
	}
	
	public void paint(Graphics g){
		
		if (g instanceof Graphics2D){
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
		g.drawString("Hello  World!", 20, 50);
	}
}
