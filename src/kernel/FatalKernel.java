package kernel;

import interfaces.FatalView;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import views.SelectionView;
import views.VersusView;
import views.BackgroundView;

/**
 * The kernel of the video game, where the bulk of the logic will be. It houses
 * functionality common to many classes in the project and also handles
 * additional logic that should not be in the domain of those classes.
 * 
 * The kernel is also responsible for managing many of the most important
 * threads in the game. It also provides the game with services for loading
 * images, drawing to the screen, asynchronous input, collision detection, and
 * background music.
 * 
 * In order for classes to be able to utilize the methods within the kernel, an
 * instance of the kernel is be passed as a parameter to any constructor of that
 * class. This parameter is always final to avoid accidental changes. Any
 * updates that need to happen to the kernel should be handled by a function
 * call to the kernel from within that class.
 * 
 * @author Marcos Davila
 */
@SuppressWarnings("serial")
public class FatalKernel implements Runnable {

	/*
	 * Manages the view objects for the video game.
	 * Since only the kernel should know about the screen and how to draw to it,
	 * it is an inner class within the kernel.
	 * 
	 * @author Marcos Davila
	 */
	private class Screen extends JFrame {

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
		 * A no-argument constructor which sets the default frame properties.
		 */
		public Screen() {			
			this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setFullScreen(displayMode);
		}


		/*
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

		/*
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
		
		/*
		 * Returns the full screen window object
		 * 
		 * @returns the fullscreen window object
		 */
		public Window getFullScreenWindow(){
			return device.getFullScreenWindow();
		}

	}

	/*
	 * The kernel has a copy of these fields that are found in FatalView. The
	 * kernel should not have to implement the interface if it doesn't need to.
	 */
	
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
	
	/**
	 * Identifier for error screen in hashmap
	 */
	public String ERROR = "ERROR";
	
	//The screen object which the game resides in
	private final Screen screen = new Screen();
	
	//An image panel to show a loading screen
	private BackgroundView loadingScreen;
	
	//Memory reference of the kernel
	private final FatalKernel kernel_memory_reference = this;
	
	//The settings of the game
	private final ArrayList<String> parameters = new ArrayList<String>();
	
	// A hashmap that holds the views. The views are identified and retrieved by their identifiers.
	private HashMap<String, FatalView> views;

	//The game thread
	private final Thread game_thread = new Thread(this);

	/**
	 * Initializes an instance of the kernel
	 */
	public FatalKernel( ) {
		// Start the kernel in it's own thread
		game_thread.start();
	}

	/**
	 * Exits the kernel. This method stops all currently active threads and disposes of the screen.
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
	 * Returns the settings for the game. The contents returned in the list respectively are
	 * the difficulty settings.
	 * 
	 * @return an ArrayList object with the game settings
	 */
	public ArrayList<String> getSettings() {
		return new ArrayList<String>(parameters.size());
	}

	/**
	 * Returns the screen object's width, height, and bit depth.
	 * 
	 * @return an ArrayList object holding the screen's width, height, and bit
	 *         depth in that order
	 */
	public ArrayList<Integer> getScreenInfo() {
		return screen.screenInfo();
	}

	/**
	 * Returns a view from the hashmap that matches the name specified, or returns
	 * an error screen if no such view was found.
	 * 
	 * @param screen a string identifier of the object that should be retrieved
	 *            	from the hashmap
	 * 
	 * @return the desired view from the hashmap
	 */
	public FatalView getView(final String screen) {
		FatalView newView = views.get(screen);
		
		return ((newView != null) ? newView : views.get(ERROR));
	}
	
	/**
	 * Returns the Window object that represents the screen to the user
	 * 
	 */
	public Window getFullScreenWindow(){
		return screen.getFullScreenWindow();
	}

	/**
	 * Returns an image with the filename specified.
	 * 
	 * @param imgpath name of file on hard drive
	 * @return an Image object that contains the image specified
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
	 * Removes one view and replaces it with another view
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
	 * Starts the game thread
	 */
	@Override
	public void run() {

		// Create the loading screen to show the user while the rest of the
		// resources are being loaded
		loadingScreen = new BackgroundView(kernel_memory_reference, "game-loader.gif");
		screen.add(loadingScreen);

		// The loading screen is visible while this thread runs
		final Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				parameters.add("Medium");

				views = new HashMap<String, FatalView>();

				views.put(VERSUS, new VersusView(kernel_memory_reference));
				views.put(SELECT, new SelectionView(
						kernel_memory_reference));
				views.put(LOADING, loadingScreen);
				views.put(ERROR, new BackgroundView()); // for now, error screen is blank panel
			}

		});

		t.start();

		try {
			t.join(); // Wait on the thread loading materials to finish
			this.redrawScreen(this.getView(LOADING), this.getView(VERSUS));
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
