package kernel;

import factory.CharacterType;
import factory.FatalFactory;
import interfaces.FatalView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import characters.AyakoTurner;
import views.BackgroundView;
import views.MainMenuView;
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
			setBackground(Color.black);
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
	
	/**
	 * Identifier for splash screen 
	 */
	public final String SPLASH = "SPLASH";

	// The screen object which the game resides in
	private Screen screen = new Screen();

	// Private instance reference of the kernel
	private static final FatalKernel FATAL_KERNEL_INSTANCE = new FatalKernel();

	// The views are identified and retrieved by their identifiers.
	private WeakHashMap<String, FatalView> views = new WeakHashMap<String, FatalView>();

	// The game thread
	private final Thread game_thread = new Thread(this);
	private BGMRunnable bgm_thread;
	
	private volatile boolean finished = false;
	public static boolean PAUSED = false;
	public static final long GAME_SPEED = 17; // Yields 30 FPS
	private VersusView stageView;
	private ThreadPool thread_pool;
	private final int MAX_NUM_THREADS = 5;
	
	private AyakoTurner ayakoTurnerplayerOne, ayakoTurnerplayerTwo;
	//private MalMartinez malMartinez;

	public static final boolean DEBUG_MODE_ON = true;

	/*
	 * Initializes an instance of the kernel Private constructor that is only
	 * getting called once. Kernel also insures that the game can run on the
	 * provided JVM. If it is not up to date, it prints a message and then exits.
	 */
	private FatalKernel() {
		long t1 = System.currentTimeMillis();
		thread_pool = new ThreadPool(MAX_NUM_THREADS);
		
		// Instantiate two instances of each character (in case both players
		// want to use the same character) in a worker thread
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			finished = true;
			thread_pool.close();
		}));
		
		thread_pool.runTask(game_thread);
		
		if (DEBUG_MODE_ON){
			long t2 = System.currentTimeMillis();
			System.err.println("Time to execute kernel constructor: " + (t2 - t1) + "ms");
		}
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
	 * Run shutdown hooks and exits.
	 */
	public void exit() {
		finished = true;
		thread_pool.close();
		System.exit(0);
	}

	/**
	 * Returns the screen object's width, height, and bit depth.
	 * 
	 * @return an ArrayList object holding the screen's width, height, and bit
	 *         depth in that order
	 */
	public List<Integer> getScreenInfo() {
		return Collections.unmodifiableList(screen.screenInfo());
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
	
	/**
	 * Loads character sprites from a specified resource location
	 * 
	 * @param location absolute resource location on disc
	 * @return an image of frame a particular character in a specified pose
	 */
	public Image loadCharacters(String location){
		return new ImageIcon(FatalKernel.class.getResource(location)).getImage();
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
		long t1 = System.currentTimeMillis();
		
		executeEDT(() -> {
			remove.stopThreads();
			
			screen.setContentPane((JPanel) add);		
			add.startThreads();
			screen.revalidate();		
		});
		
		if (DEBUG_MODE_ON){
			long t2 = System.currentTimeMillis();
			System.err.println("Time to execute redrawScreen: " + (t2 - t1) + "ms");
		}
			
	}
	
	/**
	 * Adds a task to the thread pool for execution. Returns true
	 * to signify that the task has been started.
	 */
	public boolean execute(Runnable task){
		thread_pool.runTask(task);
		return true;
	}
	
	/**
	 * Adds a task to the Swing Event thread. Returns true to signify
	 * that the task has been started.
	 */
	public boolean executeEDT(Runnable task){
		thread_pool.runEDTTask(task);
		return true;
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
			// Determines the user input and moves the character
			if (!PAUSED) {
				try {
				stageView.respondToInput();

				// Handles collisions as a result of input
				stageView.handleCollisions();

				// Moves the game objects and refreshes the screen
				stageView.updatePositions();
				} catch (NullPointerException e){
					;
				}
			}

			executeEDT(()->{
				stageView.repaint();
			});
			
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
		return ClassLoader.getSystemResource("resources/" + location);
	}
	
	/**
	 * Streams the desired resource from the resources folder
	 * @param location location to stream from
	 * @return an Input stream of the resource
	 */
	public BufferedInputStream loadResourceAsStream(String location){
		return new BufferedInputStream(ClassLoader.getSystemResourceAsStream("resources/" + location));
	}

	/**
	 * Plays the specified file as looping background music in it's own thread.
	 * @param filename name of the file to be played sitting in resources folder.
	 */
	public void playBGM(String filename) {
		long t1 = System.currentTimeMillis();
		bgm_thread = new BGMRunnable(filename);
		thread_pool.runTask(bgm_thread);
		
		if (DEBUG_MODE_ON){
			long t2 = System.currentTimeMillis();
			System.err.println("Time to execute playBGM: " + (t2 - t1) + "ms");
		}
	}
		
	
	/**
	 * Stops the background music from playing
	 */
	/*
	 * Only stops the background music if there is a non-null Runnable instance in the kernel.
	 * Every class that plays background music should stop the BGM before moving to the next 
	 * screen.
	 */
	public boolean stopBGM() {
		bgm_thread.halt();
		thread_pool.removeTask(bgm_thread);
		return true;	// signals that the bgm thread has been halted
	}

	/*
	 * Logic to happen before the game loop starts
	 */
	private void preGameLoop() {
		long t1 = System.currentTimeMillis();
		
		ayakoTurnerplayerOne = (AyakoTurner) FatalFactory.spawnCharacter(
				CharacterType.AyakoTurner, true);
		ayakoTurnerplayerTwo = (AyakoTurner) FatalFactory.spawnCharacter(
				CharacterType.AyakoTurner, false);

		views.put(LOADING, new BackgroundView("game-loader.gif"));
		views.put(SPLASH, new MainMenuView());
		views.put(ERROR, new BackgroundView()); // error screen is blank
												// panel
		views.put(VERSUS, new VersusView(ayakoTurnerplayerOne,
				ayakoTurnerplayerTwo));

		redrawScreen(this.getView(ERROR), this.getView(SPLASH));

		if (DEBUG_MODE_ON) {
			long t2 = System.currentTimeMillis();
			System.err.println("Time to execute preGameLoop: " + (t2 - t1)
					+ "ms");
		}
	}

	/*
	 * Logic that should happen when the match is over
	 */
	private void postGameLoop() {
		stageView.stopThreads();
	}

	/**
	 * Returns the width of the screen
	 * @return the resolution of the width in pixels
	 */
	public int getScreenWidth() {
		return screen.RESOLUTION_WIDTH;
	}
	
	/**
	 * Returns the height of the screen
	 * 
	 * @return the resolution of the height in pixels
	 */
	public int getScreenHeight(){
		return screen.RESOLUTION_HEIGHT;
	}
}
