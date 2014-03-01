package kernel;

import interfaces.FatalView;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import views.CharacterSelectionPanel;
import views.FightingPanel;
import views.ImagePanel;
import views.MainMenuPanel;
import views.OptionsPanel;

/**
 * The kernel of the game which deals with the logic of the program which should
 * not be handled by specific instances of objects that the kernel maintains.
 * Currently, duties of the kernel include:
 * 
 * • Loading and initializing all resources and views before the game begins.
 * 
 * • Managing the screen object. Other classes that require this instance to
 * function properly should be passed this instance as a parameter, and the
 * parameter should be final to avoid accidental changes. Any updates that need
 * to happen to this instance should be returned to the kernel, and the kernel
 * should update these values.
 * 
 * @author Marcos Davila
 * @date 2/27/2014
 * @revisionhistory 2/28/2014 - The Kernel no longer extends from JPanel. The
 *                  subclass Screen has been made more functional and supports
 *                  only the user's best screen resolution. The kernel also runs
 *                  the game in it's own thread, leaving room for other threads
 *                  to handle I/O.
 * 
 *                  2/27/2014 - Views now placed into a hashmap that identifies
 *                  them by a string. A new method getView() has been created to
 *                  index the hashmap and return the panel identified by the
 *                  string parameter. The kernel now implements an interface
 *                  ViewLabels which holds only variables that identify the
 *                  views. This enables the use of one object for all views in
 *                  the game, and views do not have to be repeatedly created.
 * 
 *                  Kernel threading has also been updated to reflect the
 *                  changes in how views are accessed.
 * 
 *                  Parameters for the game are now represented by collections.
 * 
 *                  2/3/2014 - Panel classes refactored into the kernel to
 *                  simplify access issues
 * 
 *                  1/30/2014 - File created
 */
@SuppressWarnings("serial")
public class FatalKernel implements Runnable {

	/**
	 * The Screen class manages behavior common to all views for the video game.
	 * Since only the kernel should know about the screen and how to draw to it,
	 * it is an inner class within the kernel.
	 * 
	 * @author Marcos Davila
	 * @date 1/31/2014
	 */
	public class Screen extends JFrame {

		private final GraphicsDevice device = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		private final int RESOLUTION_WIDTH = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDisplayMode().getWidth();
		private final int RESOLUTION_HEIGHT = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDisplayMode().getHeight();
		private final int BIT_DEPTH = 24;
		private final DisplayMode displayMode = new DisplayMode(
				RESOLUTION_WIDTH, RESOLUTION_HEIGHT, BIT_DEPTH,
				DisplayMode.REFRESH_RATE_UNKNOWN);

		/*
		 * A variant of the screen constructor with no arguments.
		 */
		public Screen() {
			// Frame properties such as title, close operation, etc. go here
			// DISPOSE_ON_CLOSE is used because EXIT_ON_CLOSE shuts down the
			// JVM when a frame exits. DISPOSE_ON_CLOSE only removes the
			// frame and kills the current program but leaves the JVM up
			this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setFullScreen(displayMode);

			// Create the loading screen to show the user while the rest of the
			// resources are being loaded
			// TODO: Find/create a better loading screen and put it into
			// resources
			// folder
			this.add(ip);
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
		 * 
		 * Enters full screen mode and changes the display mode
		 * 
		 * @return
		 */
		public void setFullScreen(final DisplayMode displayMode) {
			this.setUndecorated(true);
			this.setResizable(false);
			device.setFullScreenWindow(this);

			if (displayMode != null && device.isDisplayChangeSupported()) {
				try {
					device.setDisplayMode(displayMode);
				} catch (final IllegalArgumentException ex) {
					// ignore -- illegal display
				} catch (final Exception e) {
					// Simulate full-screen with RESOLUTION variables
					this.setPreferredSize(new Dimension(RESOLUTION_WIDTH,
							RESOLUTION_HEIGHT));
				}
			}
		}
	}

	// The kernel has a copy of these fields that are found in
	// FatalView. The kernel should not have to implement the
	// method if it doesn't need to.
	public final String MAIN = "MAIN";
	public final String OPTIONS = "OPTIONS";
	public String SELECT = "SELECT";
	public String VERSUS = "VERSUS";

	public String LOADING = "LOADING";
	private final Screen screen;
	private final ImagePanel ip = new ImagePanel("game-loader.gif");
	private final FatalKernel kernel_memory_reference = this;
	private final ArrayList<String> parameters = new ArrayList<String>();
	private HashMap<String, FatalView> views;

	private final Thread game_thread = new Thread(this);

	/**
	 * Create a screen to render images to and start the main loop of the game
	 * at the main menu
	 */
	public FatalKernel(final String[] args) {
		screen = new Screen();
		// Start the kernel in it's own thread
		game_thread.start();
	}

	/**
	 * Shuts down the kernel, kills the display, and ends all running threads
	 */
	public void exit() {
		try {
			game_thread.join();
		} catch (final InterruptedException e) {
			// Nothing needs to be done since the game is exiting anyway.
		}
		screen.dispose();
	}

	/**
	 * Gets parameters for the game to function.
	 * 
	 * @return a list of the game settings
	 */
	public ArrayList<String> getGameParameters() {
		final ArrayList<String> p = new ArrayList<String>(parameters.size());
		return p;
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
	public FatalView getView(final String screen) {
		// TODO Auto-generated method stub
		return views.get(screen);
	}

	/**
	 * Removes one panel and replaces it with another panel
	 * 
	 * @param remove
	 *            JPanel to remove from the JFrame
	 * @param add
	 *            JPanel to add to the JFrame
	 */
	public void redrawScreen(final FatalView remove, final FatalView add) {
		// TODO Auto-generated method stub
		screen.remove((JPanel) remove);
		remove.stopThreads();

		screen.add((JPanel) add);
		add.startThreads();

		screen.revalidate();
		screen.repaint();
	}

	/**
	 * Starts the core activities for the game to function
	 */
	@Override
	public void run() {

		// The loading screen is visible while this thread runs
		final Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				parameters.add("Medium");

				views = new HashMap<String, FatalView>();

				views.put(MAIN, new MainMenuPanel(kernel_memory_reference));
				views.put(OPTIONS, new OptionsPanel(kernel_memory_reference));
				views.put(VERSUS, new FightingPanel(kernel_memory_reference));
				views.put(SELECT, new CharacterSelectionPanel(
						kernel_memory_reference));
				views.put(LOADING, ip);
			}

		});

		t.start();

		try {
			t.join(); // Wait on the thread loading materials to finish
			this.redrawScreen(this.getView(LOADING), this.getView(MAIN));
		} catch (final InterruptedException e) {
			// If all of the materials cannot be loaded, the kernel should exit
			this.exit();
			e.printStackTrace();
		}
	}

	/**
	 * Sets parameters for the game to function.
	 */
	public void updateGameParameters(final ArrayList<String> params) {
		for (int i = 0; i < params.size(); i++) {
			parameters.add(params.get(i));
		}
	}

}
