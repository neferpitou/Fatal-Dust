package kernel;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import panel.LoadingPanel;
import panel.MainMenuPanel;

public class VGKernel {

	private JPanel cards;
	private Screen screen;
	CardLayout cl;

	/**
	 * Create a screen to render images to and start the
	 * main loop of the game at the main menu
	 */
	public VGKernel(String[] args) {
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
	 * CardLayout, panels to display images, and all things related to
	 * the screen class or initialization before the startGame() method 
	 */
	
	/**
	 * Private helper method to initialize as many cards as possible at the
	 * beginning of the game. This should only be called once.
	 */
	private void createCards() {
		cards = new JPanel(new CardLayout());
		cards.setPreferredSize(new Dimension(screen.getRESOLUTION_WIDTH(),
				screen.getRESOLUTION_HEIGHT()));
		initFirstSetOfGamePanels();
		loadMaterials();
	}

	/**
	 * Present the opening loading screen while as many things as possible
	 * are loaded into memory and made available for the game to use. This
	 * can also be where many other things are initialized.
	 * 
	 * This method is multithreaded - one thread shows the loading screen
	 * and the other fetches from disk.
	 */
	private void loadMaterials(){
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Insert materials for this thread to run here
				
				try{
					Thread.sleep(5000);
				} catch (Exception e){
					
				}
				
			}});

			screen.setFullScreen(screen.getDisplayMode());
			
			// Set loading screen
			screen.showScreen(LoadingPanel.tag);
			
			try {
				t.join();	// Wait on the thread loading materials to finish
			} catch (InterruptedException e) {
				// TODO Handle if threads are interrupted whilst we wait			
				e.printStackTrace();
			}
	}

	/**
	 * At the beginning of the game, adds all the game panels that the game will
	 * use before the main menu is shown. All game panels should be subclasses
	 * of JPanel that describe the layout of the page
	 */
	private void initFirstSetOfGamePanels() {
		// Create the panels here
		JPanel mm_panel = new MainMenuPanel();
		JPanel ld_panel = new LoadingPanel("loading1-screen-1.gif");

		// Add them to the cardlayout
		addScreen(ld_panel, LoadingPanel.tag);
		addScreen(mm_panel, MainMenuPanel.tag);

		// Add the layout to the screen and make it visible
		screen.add(cards);
		screen.pack();
	}

	/**
	 * Adds a card to the CardLayout to enable a screen to be chosen.
	 * 
	 * @param newCard
	 *            - the new card to be added
	 * @param tag
	 *            - a tag to be able to uniquely identify cards
	 */
	public void addScreen(JPanel newCard, String tag) {
		cards.add(newCard, tag);
	}

	/**
	 * Displays the card in the panel that matches the specified tag.
	 * 
	 * @param tag
	 *            - a tag to be able to uniquely identify cards
	 */
	public void showPanel(String tag) {
		CardLayout cl = (CardLayout) (cards.getLayout());
		cl.show(cards, tag);
	}

	/**
	 * The Screen class manages behavior that should be common to all
	 * views for the video game. Since only the kernel should know about the
	 * screen and how to draw to it, it is an inner class within the kernel.
	 * 
	 * @author Marcos Davila
	 * @date 1/31/2014
	 */
	@SuppressWarnings("serial")
	class Screen extends JFrame {

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
			GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
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
			screen.dispose();	// Hide the screen if it's visible
			setUndecorated(true);
			setResizable(false);
			screen.pack(); // make the screen visible again
			device.setFullScreenWindow(this);
			
			if (displayMode != null && device.isDisplayChangeSupported()) {
				try {
					device.setDisplayMode(displayMode);
				} catch (IllegalArgumentException ex) {
					// ignore -- illegal display
				} catch (Exception e){
					//Simulate full-screen with RESOLUTION variables
					setPreferredSize(new Dimension(RESOLUTION_WIDTH, RESOLUTION_HEIGHT));
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
		
		public DisplayMode getDisplayMode(){
			return displayMode;
		}

	}

}
