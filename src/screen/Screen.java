package screen;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Window;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The abstract Screen class manages behavior that should be common to all views
 * for the video game
 * 
 * @author Marcos Davila
 * @date 1/29/2014
 */

@SuppressWarnings("serial")
public abstract class Screen extends JFrame {

	protected GraphicsDevice device;
	private int RESOLUTION_WIDTH;
	private int RESOLUTION_HEIGHT;
	private final int BIT_DEPTH = 24;
	protected DisplayMode displayMode;


	/**
	 * Constructor sets the default screen device
	 */
	public Screen() {
		GraphicsEnvironment environment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();
		
		// Get default screen resolution for this computer
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		RESOLUTION_WIDTH = gd.getDisplayMode().getWidth();
		RESOLUTION_HEIGHT = gd.getDisplayMode().getHeight();
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
	 * Every class that extends this base class must know how to draw their
	 * images.
	 */
	public abstract void paint(Graphics g);

	/**
	 * 
	 * A wrapper method to add an image to a JLabel. The parameter passed is the
	 * file as a string that is to be the background image.
	 */
	protected void setBackgroundImage(String filename) {
		try {
			// Create a new JPanel with a BorderLayout to eventually store the JLabel in
			JPanel p = new JPanel(new BorderLayout());
			
			// Get relative pathname for the img resource directory
			URL pathname = getClass().getResource("/img/" + filename);
			
			// Create the JLabel the image will be in
			JLabel label = new JLabel();
			
			// Get the image from the pathname and resize the image to the user's 
			// native resolution
			Image image = this.getToolkit().createImage(pathname);
			Image resizedImage = 
				    image.getScaledInstance(RESOLUTION_WIDTH, RESOLUTION_HEIGHT, Image.SCALE_FAST);
			ImageIcon icon = new ImageIcon(resizedImage);
			
			// Put the resized image into the label, add the label to the 
			// panel, add the panel to the frame, set the frame visible
			label.setIcon(icon);
			p.add(label, BorderLayout.CENTER);
			add(p);
			setVisible(true);
		} catch (NullPointerException e) {
			System.out.println("Error: image could not be found!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Detects and sets the proper display mode based on the users configuration
	 * 
	 * @param args
	 */
	public void setDisplayMode(String[] args){
		if (args.length == 3) {
			displayMode = new DisplayMode(Integer.parseInt(args[0]),
					Integer.parseInt(args[1]), Integer.parseInt(args[2]),
					DisplayMode.REFRESH_RATE_UNKNOWN);
		} else {
			displayMode = new DisplayMode(RESOLUTION_WIDTH, RESOLUTION_HEIGHT, BIT_DEPTH,
					DisplayMode.REFRESH_RATE_UNKNOWN);
		}
		
	}
	
	/**
	 * Returns the displayMode variable
	 * @param args
	 * @return
	 */
	public DisplayMode getDisplayMode(String[] args){			
		return displayMode;
	}
}
