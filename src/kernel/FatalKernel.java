package kernel;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import etc.FatalCardLayout;
import panels.CharacterSelectionScreen;
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
 * buttons.
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
 *                  2/3/2014 - Panel classes refactored into the kernel to
 *                  simplify access issues 1/30/2014 - File created
 */
public class FatalKernel {

	private JPanel cards;
	private Screen screen;
	private FatalCardLayout fcl = new FatalCardLayout();

	/**
	 * Create a screen to render images to and start the main loop of the game
	 * at the main menu
	 */
	public FatalKernel(String[] args) {
		// Create a screen object to be able to initialize images
		// and content as well as have a way to write to it
		screen = new Screen(args);

		// Creates all the views for the game when first initialized
		// JPanel properties such as a CardLayout to move between JPanels,
		// etc. goes here. Helper methods make moving between JPanels easy.
		createCards();

		// Start the game
		startGame();
	}

	/**
	 * TODO: Where the main bulk of the game will be
	 */
	public void startGame() {
		// Set main menu screen as visible
		screen.showScreen(MainMenuPanel.tag);
	}

	/**
	 * From this section on, all methods have to do with initializing
	 * CardLayout, panels to display images, and all things related to the
	 * screen class or initialization before the startGame() method
	 */

	/**
	 * Private helper method to initialize as many cards as possible at the
	 * beginning of the game. This should only be called once.
	 */
	private void createCards() {
		cards = fcl.getPanel();
		cards.setPreferredSize(new Dimension(screen.getRESOLUTION_WIDTH(),
				screen.getRESOLUTION_HEIGHT()));
		initFirstSetOfGamePanels();
		loadMaterials();
	}

	/**
	 * Present the opening loading screen while as many things as possible are
	 * loaded into memory and made available for the game to use. This can also
	 * be where many other things are initialized.
	 * 
	 * This method is multithreaded - one thread shows the loading screen and
	 * the other fetches from disk.
	 */
	private void loadMaterials() {
		screen.setFullScreen(screen.getDisplayMode());

		// Set loading screen
		screen.showScreen(ImagePanel.tag);

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Insert materials for this thread to run here
			
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		t.start();	// start loading materials

		try {
			t.join(); // Wait on the thread loading materials to finish
		} catch (InterruptedException e) {
			// TODO Handle if threads are interrupted whilst we wait
			e.printStackTrace();
		} finally {
			
		}
	}

	/**
	 * At the beginning of the game, adds all the game panels that the game will
	 * use before the main menu is shown. All game panels should be subclasses
	 * of JPanel that describe the layout of the page
	 */
	private void initFirstSetOfGamePanels() {
		// Create the panels here
		JPanel mm_panel = new MainMenuPanel(screen);
		
		// TODO: Find/create a better loading screen and put it into resources folder
		JPanel ld_panel = new ImagePanel("game-loader.gif");
		
		// TODO: Find/create a better background screen and put it into resources folder
		JPanel cs_panel = new CharacterSelectionScreen();

		JPanel opt_panel = new OptionsPanel(fcl);
		
		// Add them to the cardlayout
		fcl.addScreen(ld_panel, ImagePanel.tag);
		fcl.addScreen(mm_panel, MainMenuPanel.tag);
		fcl.addScreen(cs_panel, CharacterSelectionScreen.tag);
		fcl.addScreen(opt_panel, OptionsPanel.tag);

		// Add the layout to the screen and make it visible
		screen.add(cards);
		screen.pack();
	}

	/**
	 * The Screen class manages behavior that should be common to all views for
	 * the video game. Since only the kernel should know about the screen and
	 * how to draw to it, it is an inner class within the kernel.
	 * 
	 * @author Marcos Davila
	 * @date 1/31/2014
	 */
	@SuppressWarnings("serial")
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

		public void showScreen(String tag) {
			CardLayout cl = (CardLayout) (cards.getLayout());
			cl.show(cards, tag);
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
}
