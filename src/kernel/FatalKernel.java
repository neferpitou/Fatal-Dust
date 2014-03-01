package kernel;

import interfaces.FatalView;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
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
 * The kernel of the game, which manages the top level threads of the game and
 * provides the game with services for loading images, drawing to the screen,
 * asynchronous input, collision detection, and background music.
 * 
 * The kernel of the game deals with the logic needed in the program which
 * should not be handled by specific instances of panels that the kernel
 * maintains. The panels should be passed an instance of the kernel as a
 * parameter to it's constructor. This parameter should be final to avoid
 * accidental changes. Any updates that need to happen to this instance should
 * be returned to the kernel, and the kernel should update these values.
 * 
 * @author Marcos Davila
 */
@SuppressWarnings("serial")
public class FatalKernel implements Runnable {

	/**
	 * The Screen class manages behavior common to all views for the video game.
	 * Since only the kernel should know about the screen and how to draw to it,
	 * it is an inner class within the kernel.
	 * 
	 * @author Marcos Davila
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

		/**
		 * A no-argument constructor which sets the default frame properties.
		 */
		public Screen() {
			
			this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setFullScreen(displayMode);
		}

		/**
		 * Restores the screen's display mode.
		 */
		public void restoreScreen() {
			if (device.getFullScreenWindow() != null) {
				this.dispose();
			}

			device.setFullScreenWindow(null);
		}

		/**
		 * Returns the bit depth, resolution width and resolution height of the
		 * screen in an ArrayList.
		 * 
		 * @return info the information about the screen object. The list is
		 *         		sorted by width, height, depth.
		 */
		public ArrayList<Integer> screenInfo() {
			return new ArrayList<Integer>(Arrays.asList(RESOLUTION_WIDTH,
					RESOLUTION_HEIGHT, BIT_DEPTH));
		}

		/**
		 * 
		 * Enters full screen mode and changes the display mode
		 * 
		 * @param displayMode
		 * 				a displayMode object to set as the screen
		 * 
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

	/*
	 * The kernel has a copy of these fields that are found in FatalView. The
	 * kernel should not have to implement the interface if it doesn't need to.
	 */
	/**
	 * Identifier for main menu
	 */
	public final String MAIN = "MAIN";
	
	/**
	 * Identifier for options menu
	 */
	public final String OPTIONS = "OPTIONS";
	
	/**
	 * Identifier for the character selection menu
	 */
	public String SELECT = "SELECT";
	
	/**
	 * Identifier for the versus screen
	 */
	public String VERSUS = "VERSUS";

	/**
	 * Identifier for the loading screen
	 */
	public String LOADING = "LOADING";
	
	//The screen object which the game resides in
	private final Screen screen = new Screen();
	
	//An image panel to show a loading screen
	private ImagePanel loadingScreen;
	
	//Memory reference of the kernel
	private final FatalKernel kernel_memory_reference = this;
	
	//The settings of the game
	private final ArrayList<String> parameters = new ArrayList<String>();
	
	// A hashmap that holds the views. The views are identified and retrieved by their identifiers.
	private HashMap<String, FatalView> views;

	//The game thread
	private final Thread game_thread = new Thread(this);

	/**
	 * Create a screen to render images to and start the main loop of the game
	 * at the main menu
	 * 
	 * @param args optional command line arguments
	 */
	public FatalKernel(final String[] args) {
		// Start the kernel in it's own thread
		game_thread.start();
	}

	/**
	 * Shuts down the kernel gracefully
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
	 * @return an ArrayList object with the game settings
	 */
	public ArrayList<String> getGameParameters() {
		return new ArrayList<String>(parameters.size());
	}

	/**
	 * Get attributes of the screen
	 * 
	 * @return an ArrayList object holding the screen's width, height, and bit
	 *         depth in that order
	 */
	public ArrayList<Integer> getScreenInfo() {
		return screen.screenInfo();
	}

	/**
	 * Returns a view from the hashmap.
	 * 
	 * @param screen a string identifier of the object that should be retrieved
	 *            	from the hashmap
	 * 
	 * @return the desired view from the hashmap
	 */
	public FatalView getView(final String screen) {
		return views.get(screen);
	}

	/**
	 * Loads an image from disk and returns it
	 * 
	 * @param imgpath name of file on hard drive
	 * @return an Image object of the file
	 */
	public Image loadImage(String imgpath) {
		// Get the image and load it into memory. Resource path should be added
		// to string here before finding the image
		imgpath = "resources/" + imgpath;
		final Image i = Toolkit.getDefaultToolkit().createImage(
				this.getClass().getClassLoader().getResource(imgpath));

		// Get the image from the pathname and resize the image to the user's
		// native resolution
		final ArrayList<Integer> info = this.getScreenInfo();
		return i.getScaledInstance(info.get(0), info.get(1), Image.SCALE_FAST);
	}

	/**
	 * Removes one panel and replaces it with another panel
	 * 
	 * @param remove desired view to remove
	 * @param add desired view to add
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

		// Create the loading screen to show the user while the rest of the
		// resources are being loaded
		loadingScreen = new ImagePanel(kernel_memory_reference, "game-loader.gif");
		screen.add(loadingScreen);

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
				views.put(LOADING, loadingScreen);
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
