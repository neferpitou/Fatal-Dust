package screen;

import java.awt.*;

import javax.swing.JFrame;

/**
 * The SimpleScreenManager class manages initializing and displaying full screen
 * graphics modes
 * 
 * @author Marcos Davila
 * @date 1/29/2014
 */
public class SimpleScreenManager extends JFrame {
	private GraphicsDevice device;

	/**
	 * Creates a new SimpleScreenManager object
	 */
	public SimpleScreenManager() {
		GraphicsEnvironment environment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
	}

	/**
	 * Enters full screen mode and changes the display mode
	 */
	public void setFullScreen(DisplayMode displayMode, JFrame window) {
		window.setUndecorated(true);
		window.setResizable(false);

		device.setFullScreenWindow(window);
		if (displayMode != null && device.isDisplayChangeSupported()) {
			try {
				device.setDisplayMode(displayMode);
			} catch (IllegalArgumentException ex) {
				// ignore - illegal mode for this device
			}
		}
	}
	
	/**
	 * Returns the window currently used in full screen mode
	 */
	public Window getFullScreenWindow(){
		return device.getFullScreenWindow();
	}
	
	/**
	 * Restores the screen's display mode.
	 */
	public void restoreScreen(){
		Window window = device.getFullScreenWindow();
		if (window != null){
			window.dispose();
		}
		device.setFullScreenWindow(null);
	}
	
	private static final long DEMO_TIME = 5000;

	public void run(DisplayMode displayMode) {
		setBackground(Color.blue);
		setBackground(Color.white);
		setFont(new Font("Dialog", Font.PLAIN, 24));

		SimpleScreenManager screen = new SimpleScreenManager();
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
