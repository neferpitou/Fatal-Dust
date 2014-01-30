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
@SuppressWarnings("serial")
public class LoadScreen extends JFrame {
	private GraphicsDevice device;

	/**
	 * Creates a new SimpleScreenManager object
	 */
	public LoadScreen() {
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
	public Window getFullScreenWindow() {
		return device.getFullScreenWindow();
	}

	/**
	 * Restores the screen's display mode.
	 */
	public void restoreScreen() {
		Window window = device.getFullScreenWindow();
		if (window != null) {
			window.dispose();
		}
		device.setFullScreenWindow(null);
	}

	/**
	 * The job of init is twofold: preload all large materials so the game runs smoothly,
	 * and presents a loading screen while it takes place.
	 */
	public void init(DisplayMode displayMode) {
		setBackground(Color.blue);
		setBackground(Color.white);
		setFont(new Font("Dialog", Font.PLAIN, 24));

		LoadScreen screen = new LoadScreen();
		Thread t = new Thread(new LoadingThread()); // Launches a new thread where loading takes place
		 
		try {
			screen.setFullScreen(displayMode, this);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO: Set loading screen
			
		} finally {
			try {
				t.join();	// Wait on the thread loading materials to finish
			} catch (InterruptedException e) {
				// TODO Handle if threads are interrupted whilst we wait			
				e.printStackTrace();
			}
			// TODO: Remove when class is finalized
			screen.restoreScreen();
		}
		
		// TODO: Instantiate and load main menu panel
	}

	public void paint(Graphics g) {

		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
		g.drawString("Hello  World!", 20, 50);
	}

	
	/**
	 * A dedicated thread class that determines what to load and how to load it
	 * at the beginning of the game. It is an inner class to simplify access
	 * issues in conjunction with the loading screen.
	 * @author Marcos Davila
	 * @date 1/29/2014
	 */
	class LoadingThread implements Runnable {
		
		Thread t;
		LoadingThread(){
			t = new Thread(this);
			t.start();
		}
		
		@Override
		public void run() {
			// TODO Load Materials
			System.out.println("Thread!");
		}

	}
}