package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import objects.BlinkLabel;
import interfaces.FatalView;

import java.awt.BorderLayout;

/**
 * A screen to display when the game first loads up. It is a very simple screen, used only to
 * greet the player, and a new screen is displayed when the user presses a button.
 * 
 * @author Marcos Davila
 *
 */
@SuppressWarnings("serial")
public class SplashView extends JPanel implements FatalView {

	private Image menu_background = kernel.loadImage("main_menu_image.jpg");
	
	/**
	 * Set up the layout, listen for key events to redraw the screen, draw
	 * and place two JLabels for the title and the prompt to continue
	 */
	public SplashView() {
		setLayout(new BorderLayout(0, 0));
		// Set up a callback to listen for any type of input from the keyboard
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent e) {
		        // TODO Redraw screen to fighting screen   
		    	kernel.redrawScreen(kernel.getView("SPLASH"), kernel.getView("LOADING"));
		    	kernel.redrawScreen(kernel.getView("LOADING"), kernel.getView("VERSUS"));
		    	KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this);
		        return false;
		      }
		});
		
		JLabel title = new JLabel("Fatal Dust");
		title.setForeground(Color.WHITE);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont( new Font("Segoe Script", Font.BOLD, 72) );
		add(title, BorderLayout.NORTH);
		
		BlinkLabel continueLabel = new BlinkLabel("Press any button to continue");
		continueLabel.setForeground(Color.WHITE);
		continueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		continueLabel.setFont( new Font("Serif", Font.BOLD, 18) );
		add(continueLabel, BorderLayout.SOUTH);
	}
	
	/**
	 * Start the background music
	 */
	@Override
	public boolean startThreads() {
		// TODO Start BGM
		kernel.playBGM("streets.wav");
		return true;
	}

	/**
	 * Stop the background music
	 */
	@Override
	public boolean stopThreads() { 
		kernel.stopBGM();
		return true;
	}

	/**
	 * Paint an image as the background to the panel
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(menu_background, 0, 0, getWidth(), getHeight(), null);
	}
}
