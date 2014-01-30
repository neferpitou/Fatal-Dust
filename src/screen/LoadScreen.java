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
public class LoadScreen extends Screen {
	
	private Runnable task;
	private String image;
	
	/**
	 * A LoadScreen object is created by passing in an image to show as the loading
	 * screen, and a task that should be run in the background while the loading
	 * screen is up
	 */
	public LoadScreen(String image, Runnable task) {
		super();
		this.task = task;
		this.image = image;
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
	public void init() {
		Thread t = new Thread(task); // Launches a new thread where loading takes place
		 
		try {
			setFullScreen(displayMode, this);
			
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
			restoreScreen();
		}
		
		// TODO: Instantiate and load main menu panel
	}

	/**
	 * What gets painted on the screen, eg. the background image
	 */
	public void paint(Graphics g) {

		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}
		
		// The specified background image for this loading panel
		setBackgroundImage(image);
	}

}