package kernel;

import factory.FatalFactory;
import interfaces.FatalView;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import views.BackgroundView;
import views.VersusView;

/**
 * The kernel of the video game, where the bulk of the logic will be. It houses
 * functionality common to many classes in the project and also handles
 * additional logic that should not be in the domain of those classes.
 * 
 * The kernel is also responsible for managing many of the most important
 * threads in the game. It also provides the game with services for loading
 * images, drawing to the screen, asynchronous input, collision detection, and
 * background sounds.
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
	 * Manages the view objects for the video game. Since only the kernel should
	 * know about the screen and how to draw to it, it is an inner class within
	 * the kernel.
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
			this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			this.setFullScreen(displayMode);
		}

		/*
		 * Returns the bit depth, resolution width and resolution height of the
		 * screen in an ArrayList.
		 * 
		 * @return info the information about the screen object. The list is
		 * sorted by width, height, depth.
		 */
		public ArrayList<Integer> screenInfo() {
			return new ArrayList<Integer>(Arrays.asList(RESOLUTION_WIDTH,
					RESOLUTION_HEIGHT, BIT_DEPTH));
		}

		/*
		 * 
		 * Enters full screen mode and changes the display mode
		 * 
		 * @param displayMode a displayMode object to set as the screen
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
		public Window getFullScreenWindow() {
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
	public final String SELECT = "SELECT";

	/**
	 * Identifier for the versus screen
	 */
	public final String VERSUS = "VERSUS";

	/**
	 * Identifier for the loading screen
	 */
	public final String LOADING = "LOADING";

	/**
	 * Identifier for error screen in hashmap
	 */
	public final String ERROR = "ERROR";

	// The screen object which the game resides in
	private final Screen screen = new Screen();

	// Private instance reference of the kernel
	private static final FatalKernel FATAL_KERNEL_INSTANCE = new FatalKernel();

	// A hashmap that holds the views. The views are identified and retrieved by
	// their identifiers.
	private HashMap<String, FatalView> views;

	// The game thread
	private final Thread game_thread = new Thread(this);
	private Runnable bgm_thread;
	
	private boolean finished = false;
	private boolean paused = false;
	private long GAME_SPEED = 17; // roughly 1/60 of a second
	private VersusView stageView;
	private ThreadPool thread_pool;
	private final int MAX_NUM_THREADS = 5;
	

	/*
	 * Initializes an instance of the kernel Private constructor that is only
	 * getting called once
	 */
	private FatalKernel() {
		thread_pool = new ThreadPool(MAX_NUM_THREADS);
		thread_pool.runTask(game_thread);
	}

	/**
	 * Returns an instance of the kernel object
	 * 
	 * @return singleton instance of FatalKernel
	 */
	public static FatalKernel getInstance() {
		return FATAL_KERNEL_INSTANCE;
	}

	/**
	 * Exits the kernel. This method stops all currently active threads and
	 * disposes of the screen.
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
	 * Returns the screen object's width, height, and bit depth.
	 * 
	 * @return an ArrayList object holding the screen's width, height, and bit
	 *         depth in that order
	 */
	public ArrayList<Integer> getScreenInfo() {
		return screen.screenInfo();
	}

	/**
	 * Returns a view from the hashmap that matches the name specified, or
	 * returns an error screen if no such view was found.
	 * 
	 * @param screen
	 *            a string identifier of the object that should be retrieved
	 *            from the hashmap
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
	public Window getFullScreenWindow() {
		return screen.getFullScreenWindow();
	}

	/**
	 * Returns an image with the filename specified.
	 * 
	 * @param imgpath
	 *            name of file on hard drive
	 * @return an Image object that contains the image specified
	 */
	public Image loadImage(String imgpath) {
		return load(imgpath);
	}

	/*
	 * Loads and returns an image
	 */
	private Image load(String imgpath) {
		return new ImageIcon(loadResource(imgpath)).getImage();
	}

	/**
	 * Removes one view and replaces it with another view
	 * 
	 * @param remove
	 *            desired view to remove
	 * @param add
	 *            desired view to add
	 */
	public void redrawScreen(final FatalView remove, final FatalView add) {
		// Needs to be run on the Event Dispatcher Thread
		SwingUtilities.invokeLater(() -> {
			screen.remove((JPanel) remove);
			remove.stopThreads();

			screen.add((JPanel) add);
			add.startThreads();

			screen.revalidate();
		});
	}

	/**
	 * Sets a random stage background from a selection of possible stage
	 * backgrounds
	 */
	public Image setStage() {
		int numStage = (int) (Math.random() * FatalFactory.TOTAL_NUM_STAGES);
		return FatalFactory.setStage(numStage);
	}

	/**
	 * Starts the game thread from the kernel
	 */
	@Override
	public void run() {
		preGameLoop();

		// Paint the background
		stageView = (VersusView) getView(VERSUS);

		while (!finished) {
			if (!paused)
				inGameLoop();

			stageView.repaint();

			try {
				Thread.sleep(GAME_SPEED);
			} catch (InterruptedException e) {
				// Ignore
			}
		}

		postGameLoop();
	}
	
	/**
	 * Returns the desired file from the resources folder.
	 */
	public URL loadResource(String location){
		return getClass().getClassLoader().getResource("resources/" + location);
	}
	
	/**
	 * Streams the desired resource from the resources folder
	 * @param location location to stream from
	 * @return an Input stream of the resource
	 */
	public InputStream loadResourceAsStream(String location){
		return getClass().getClassLoader().getResourceAsStream("resources/" + location);
	}

	/**
	 * Plays the specified file as looping background music in it's own thread.
	 * @param filename name of the file to be played sitting in resources folder.
	 */
	public void playBGM(String filename, boolean loop) {
		bgm_thread = new Runnable() {

			@Override
			public void run() {
				int BUFFER_SIZE = 524288;
				
				do {
					try {
						AudioInputStream audio = AudioSystem
								.getAudioInputStream(loadResourceAsStream(filename));
						AudioFormat format = audio.getFormat();
						DataLine.Info info = new DataLine.Info(
								SourceDataLine.class, format);
						SourceDataLine auline = (SourceDataLine) AudioSystem
								.getLine(info);
						auline.open(format);
						auline.start();

						int nBytesRead = 0;
						byte[] abData = new byte[BUFFER_SIZE];
						while (nBytesRead != -1) {
							nBytesRead = audio.read(abData, 0, abData.length);
							if (nBytesRead >= 0) {
								auline.write(abData, 0, nBytesRead);
							}
						}					
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
					if (!loop || Thread.currentThread().isInterrupted()){
						break;
					}
					
				} while (true);
			}
		};

		thread_pool.runTask(bgm_thread);
	}
	
	/**
	 * Stops the background music from playing
	 */
	/*
	 * Only stops the background music if there is a non-null Runnable instance in the kernel.
	 * Every class that plays background music should stop the BGM before moving to the next 
	 * screen.
	 */
	public void stopBGM( ){
		if (bgm_thread != null){
			thread_pool.removeTask(bgm_thread);
			bgm_thread = null;
		} else {
			System.err.println("No bgm task to remove!");
		}
	}

	/*
	 * Logic to happen before the game loop starts
	 */
	private void preGameLoop() {
		views = new HashMap<String, FatalView>();
		views.put(LOADING, new BackgroundView("game-loader.gif"));
		views.put(VERSUS, new VersusView());
		views.put(ERROR, new BackgroundView()); // for now, error screen
												// is blank panel
		redrawScreen(this.getView(ERROR), this.getView(LOADING));

		// Everything in here runs on it's own thread
		final Thread t = new Thread(() -> {
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

	/*
	 * Logic that should happen in the game loop
	 */
	private void inGameLoop() {
		// respondToInput();
		// moveGameObjects();
		// handleCollisions();
	}

	/*
	 * Logic that should happen when the match is over
	 */
	private void postGameLoop() {
		stageView.stopThreads();
	}

	// private void respondToInput() { }

	// private void moveGameObjects() { }

	// private void handleCollisions() { }

}
