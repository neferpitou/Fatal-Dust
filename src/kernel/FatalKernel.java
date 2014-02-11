package kernel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import panels.CharacterSelectionPanel;
import panels.FightingPanel;
import panels.ImagePanel;
import panels.MainMenuPanel;
import panels.OptionsPanel;

/**
 * The kernel of the game which deals with the logic of the program which should
 * not be handled by specific instances of objects that the kernel maintains.
 * Currently, duties of the kernel include:
 * 
 * • Loading and initializing all resources and views before the game begins.
 * This is especially important because the entire game is created and running
 * in memory by the time the user sees the main menu. It is just not shown to
 * the user until they request it via navigating through menus by clicking
 * buttons. What is left TODO is to implement the most efficient method of loading
 * such resources.
 * 
 * • Housing the Screen class as an inner class and managing the sole instance
 * of that class. Other classes that require this instance to function properly
 * should be passed this instance as a parameter, and the parameter should be
 * final to avoid accidental changes. Any updates that need to happen to this
 * instance should be returned to the kernel, and the kernel should update these
 * values.
 * 
 * In the future, duties of the kernel may include:
 * 
 * • TBD
 * 
 * @author Marcos Davila
 * @date 1/30/2014
 * @revisionhistory 2/5/2014 - FatalCardLayout created as an extension of
 *                  CardLayout to function as a wrapper for the layout mechanism
 *                  with helper methods to reduce boilerplate code for accessing
 *                  the panels managed by FCL.
 * 
 *                  Panel classes refactored out of the kernel to slim the
 *                  kernel down and improve the modularity of the class. These
 *                  panel classes may require an instance of the FatalCardLayout
 *                  or Screen objects to function properly. These are provided
 *                  via parameters to the constructors with the final keyword,
 *                  so the instances cannot be modified by these classes.
 *                  
 *                  I envision the code that sets up the games panels to become
 *                  large very soon, so the order that panels are initialized
 *                  has changed. Most of the loading takes place in the thread
 *                  in the loadMaterials() class and the try-catch block that 
 *                  halted the thread for five seconds has been removed. The
 *                  result is that the loading screen is essentially skipped
 *                  for now.
 * 
 *                  2/3/2014 - Panel classes refactored into the kernel to
 *                  simplify access issues 
 *                  
 *                  1/30/2014 - File created
 */
@SuppressWarnings("serial")
public class FatalKernel extends JPanel {

	private Screen screen;
	private ImagePanel ip;
	private MainMenuPanel mmp;
	private FatalKernel kernel_reference = this;

	/**
	 * Create a screen to render images to and start the main loop of the game
	 * at the main menu
	 */
	public FatalKernel(String[] args) {
		screen = new Screen(args);
		screen.setFullScreen(screen.getDisplayMode());

		// Start the game
		startGame();
	}

	/**
	 * TODO: Where the main bulk of the game will be
	 */
	public void startGame() {
		// Create the loading screen to show the user while the rest of the
		// resources are being loaded
		// TODO: Find/create a better loading screen and put it into resources
		// folder
		ip = new ImagePanel("game-loader.gif");
		screen.add(ip);

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO some tasks
				mmp = new MainMenuPanel(kernel_reference);
			}
		});

		t.start(); // start loading materials

		try {
			t.join(); // Wait on the thread loading materials to finish
		} catch (InterruptedException e) {
			// TODO Handle if threads are interrupted whilst we wait
			e.printStackTrace();
		} finally {
			redrawScreen(ip, mmp);
		}
	}

	/**
	 * Removes one panel and replaces it with another panel 
	 * 
	 * @param remove - JPanel to remove
	 * @param add - JPanel to add
	 */
	public void redrawScreen(JPanel remove, JPanel add) {
		// TODO Auto-generated method stub
		screen.remove(remove);
		screen.add(add);
		screen.revalidate();
		screen.repaint();
	}

	/**
	 * The Screen class manages behavior that should be common to all views for
	 * the video game. Since only the kernel should know about the screen and
	 * how to draw to it, it is an inner class within the kernel.
	 * 
	 * @author Marcos Davila
	 * @date 1/31/2014
	 */
	public class Screen extends JFrame {

		private GraphicsDevice device;
		private int RESOLUTION_WIDTH;
		private int RESOLUTION_HEIGHT;
		private final int BIT_DEPTH = 24;
		private DisplayMode displayMode;

		/**
		 * Constructor sets the default screen device and sets up the panel to
		 * hold cards
		 * 
		 * @param args
		 *            - Command line specification of desired width, height,
		 *            depth of window. Defaults to screens max resolution if not
		 *            specified.
		 */
		public Screen(String[] args) {
			setup(args);
		}

		/*
		 * A variant of the screen constructor with no arguments.
		 */
		public Screen() {
			setup(new String[0]);
		}

		/**
		 * A helper method to set up the screen with or without arguments
		 * 
		 * @param args
		 *            - possible command line arguments to set display
		 */

		private void setup(String[] args) {
			GraphicsEnvironment environment = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			device = environment.getDefaultScreenDevice();

			// Get default screen resolution for this computer
			GraphicsDevice gd = GraphicsEnvironment
					.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			RESOLUTION_WIDTH = gd.getDisplayMode().getWidth();
			RESOLUTION_HEIGHT = gd.getDisplayMode().getHeight();

			// Set the display mode
			if (args.length == 3) {
				displayMode = new DisplayMode(Integer.parseInt(args[0]),
						Integer.parseInt(args[1]), Integer.parseInt(args[2]),
						DisplayMode.REFRESH_RATE_UNKNOWN);
			} else {
				displayMode = new DisplayMode(RESOLUTION_WIDTH,
						RESOLUTION_HEIGHT, BIT_DEPTH,
						DisplayMode.REFRESH_RATE_UNKNOWN);
			}

			// Frame properties such as title, close operation, etc. go here
			// DISPOSE_ON_CLOSE is used because EXIT_ON_CLOSE shuts down the
			// JVM when a frame exits. DISPOSE_ON_CLOSE only removes the
			// frame and kills the current program but leaves the JVM up
			setTitle("Title Here");
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setUndecorated(true);
			setResizable(false);
			setVisible(true);
		}

		/**
		 * 
		 * Enters full screen mode and changes the display mode
		 */
		public void setFullScreen(DisplayMode displayMode) {
			screen.dispose(); // Hide the screen if it's visible
			setUndecorated(true);
			setResizable(false);
			screen.pack(); // make the screen visible again
			device.setFullScreenWindow(this);

			if (displayMode != null && device.isDisplayChangeSupported()) {
				try {
					device.setDisplayMode(displayMode);
				} catch (IllegalArgumentException ex) {
					// ignore -- illegal display
				} catch (Exception e) {
					// Simulate full-screen with RESOLUTION variables
					setPreferredSize(new Dimension(RESOLUTION_WIDTH,
							RESOLUTION_HEIGHT));
				}
			}
		}

		/**
		 * 
		 * Restores the screen's display mode.
		 */
		public void restoreScreen() {
			if (device.getFullScreenWindow() != null) {
				this.dispose();
			}

			device.setFullScreenWindow(null);
		}

		/**
		 * Getters and setters
		 */
		public int getRESOLUTION_WIDTH() {
			return RESOLUTION_WIDTH;
		}

		public void setRESOLUTION_WIDTH(int rESOLUTION_WIDTH) {
			RESOLUTION_WIDTH = rESOLUTION_WIDTH;
		}

		public int getRESOLUTION_HEIGHT() {
			return RESOLUTION_HEIGHT;
		}

		public void setRESOLUTION_HEIGHT(int rESOLUTION_HEIGHT) {
			RESOLUTION_HEIGHT = rESOLUTION_HEIGHT;
		}

		public DisplayMode getDisplayMode() {
			return displayMode;
		}
	}	
	
	public MainMenuPanel getMainMenu(){
		return mmp;
	}

	/*
	 * Shuts down the kernel
	 */
	public void exit() {
		screen.dispose();
	}
}
