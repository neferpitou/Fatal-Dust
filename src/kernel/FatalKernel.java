package kernel;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import etc.ViewLabels;
import panels.AbstractPanel;
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
 * buttons. What is left to do is to implement the most efficient method of
 * loading such resources.
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
 * @date 2/27/2014
 * @revisionhistory 2/27/2014 - Views now placed into a hashmap that identifies
 *                  them by a string. A new method getView() has been created to
 *                  index the hashmap and return the panel identified by the
 *                  string parameter. The kernel now implements an interface
 *                  ViewLabels which holds only variables to be able to identify
 *                  the views to be switched in all of the actionlisteners for
 *                  each view. Now views do not have to be created and destroyed
 *                  every time a button is pressed.
 * 
 *                  Kernel threading has also been updated to reflect the
 *                  changes in how views are accessed.
 *                  
 *                  Parameters implementation changed from arrays to collections.
 * 
 *                  2/5/2014 - FatalCardLayout created as an extension of
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
 *                  result is that the loading screen is essentially skipped for
 *                  now.
 * 
 *                  2/3/2014 - Panel classes refactored into the kernel to
 *                  simplify access issues
 * 
 *                  1/30/2014 - File created
 */
@SuppressWarnings("serial")
public class FatalKernel extends JPanel implements ViewLabels {

	private Screen screen;
	private ImagePanel ip;
	private FatalKernel kernel_reference = this;
	private ArrayList<String> parameters = new ArrayList<String>();
	private HashMap<String, AbstractPanel> views;

	/**
	 * Create a screen to render images to and start the main loop of the game
	 * at the main menu
	 */
	public FatalKernel(String[] args) {
		screen = new Screen(args);
		screen.setFullScreen(screen.getDisplayMode());

		views = new HashMap<String, AbstractPanel>();

		// Start the kernel
		startKernel();
	}

	/**
	 * Starts the core activities for the game to function
	 */
	public void startKernel() {

		// Create the loading screen to show the user while the rest of the
		// resources are being loaded
		// TODO: Find/create a better loading screen and put it into resources
		// folder
		ip = new ImagePanel("game-loader.gif");
		screen.add(ip);

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				parameters.add("Medium");
				
				views.put(MAIN, new MainMenuPanel(kernel_reference));
				views.put(OPTIONS, new OptionsPanel(kernel_reference));
				views.put(VERSUS, new FightingPanel(kernel_reference));
				views.put(SELECT, new CharacterSelectionPanel(kernel_reference));
				views.put(LOADING, ip);
			}

		});

		t.start(); // start loading materials

		try {
			t.join(); // Wait on the thread loading materials to finish
		} catch (InterruptedException e) {
			// TODO Handle if threads are interrupted whilst we wait
			e.printStackTrace();
		} finally {
			redrawScreen(getView(LOADING), getView(MAIN));
		}

	}

	/**
	 * Removes one panel and replaces it with another panel
	 * 
	 * @param remove
	 *            JPanel to remove from the JFrame
	 * @param add
	 *            JPanel to add to the JFrame
	 */
	public void redrawScreen(AbstractPanel remove, AbstractPanel add) {
		// TODO Auto-generated method stub
		screen.remove(remove);
		remove.stopThreads();

		screen.add(add);
		add.startThreads();

		screen.revalidate();
		screen.repaint();
	}

	/**
	 * Gets parameters for the game to function.
	 * 
	 * @return a list of the game settings
	 */
	public ArrayList<String> getGameParameters() {
		ArrayList<String> p = new ArrayList<String>(parameters.size());
		return p;
	}

	/**
	 * Sets parameters for the game to function.
	 */
	public void setGameParameters(ArrayList<String> params) {
		for (int i = 0; i < params.size(); i++) {
			parameters.add(params.get(i));
		}
	}

	/**
	 * Shuts down the kernel, kills the display, and ends all running threads
	 */
	public void exit() {
		screen.dispose();
	}

	/**
	 * Returns a view from the hashmap.
	 * 
	 * @param screen
	 *            a string identifier of the object that should be retrieved
	 *            from the hashmap
	 *            
	 * @return a view from the hashmap
	 */
	public AbstractPanel getView(String screen) {
		// TODO Auto-generated method stub
		return views.get(screen);
	}

	/**
	 * The Screen class manages behavior common to all views for the video game.
	 * Since only the kernel should know about the screen and how to draw to it,
	 * it is an inner class within the kernel.
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

		public DisplayMode getDisplayMode() {
			DisplayMode defensiveDisplayMode = displayMode;
			return defensiveDisplayMode;
		}
	}

}
