package screen;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import panel.MainMenuPanel;

/**
 * The abstract Screen class manages behavior that should be common to all views
 * for the video game
 * 
 * @author Marcos Davila
 * @date 1/29/2014
 */

@SuppressWarnings("serial")
public class Screen extends JFrame {

	private GraphicsDevice device;
	private int RESOLUTION_WIDTH;
	private int RESOLUTION_HEIGHT;
	private final int BIT_DEPTH = 24;
	private DisplayMode displayMode;
	private JPanel cards;

	/**
	 * Constructor sets the default screen device and sets up the panel to hold cards
	 * 
	 * @param args - Command line specification of desired width, height, depth of 
	 * window. Defaults to screens max resolution if not specified.
	 */
	public Screen(String[] args) {
		// Handle resolution issues before making frames and panels
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = environment.getDefaultScreenDevice();

		// Get default screen resolution for this computer
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		RESOLUTION_WIDTH = gd.getDisplayMode().getWidth();
		RESOLUTION_HEIGHT = gd.getDisplayMode().getHeight();
		
		// Set the display mode
		if (args.length == 3) {
			displayMode = new DisplayMode(Integer.parseInt(args[0]),
			Integer.parseInt(args[1]), Integer.parseInt(args[2]),
			DisplayMode.REFRESH_RATE_UNKNOWN);
		} else {
			displayMode = new DisplayMode(RESOLUTION_WIDTH, RESOLUTION_HEIGHT, BIT_DEPTH,
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
		
		// JPanel properties such as a CardLayout to move between JPanels,
		// etc. goes here. Helper methods make moving between JPanels easy.
		cards = new JPanel(new CardLayout());
		cards.setPreferredSize(new Dimension(RESOLUTION_WIDTH, RESOLUTION_HEIGHT));
		addGamePanels();
		add(cards);
		pack();
		setVisible(true);
		showScreen(MainMenuPanel.tag);
	}
	
	/**
	 * At the beginning of the game, adds all the game panels that the game
	 * will use before the main menu is shown. All game panels should be
	 * subclasses of JPanel that describe the layout of the page
	 */
	private void addGamePanels() {
		addScreen(new MainMenuPanel(), MainMenuPanel.tag);
	}

	/**
	 * Adds a card to the CardLayout to enable a screen to be chosen.
	 * 
	 * @param newCard - the new card to be added
	 * @param tag - a tag to be able to uniquely identify cards
	 */
	private void addScreen(JPanel newCard, String tag){
		cards.add(newCard, tag);
	}
	
	/**
	 * Displays the card in the panel that matches the specified tag.
	 * 
	 * @param tag - a tag to be able to uniquely identify cards
	 */
	public void showScreen(String tag){
		CardLayout cl = (CardLayout)(cards.getLayout());
	    cl.show(cards, tag);
	}

}
