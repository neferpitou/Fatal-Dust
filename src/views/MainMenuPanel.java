package views;

import interfaces.FatalView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import kernel.FatalKernel;

/**
 * Displays the main menu with buttons to move through the functions of the game.
 * 
 * @author Marcos Davila
 */
@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel implements FatalView {
	
	/**
	 * An Image object holding the background image
	 */
	private Image img;
	
	/**
	 * The transparent panel that holds the buttons along the
	 * bottom of the screen.
	 */
	private TransparentPanel transparentPanel;
	
	/**
	 * Creates a view of the main menu panel
	 * 
	 * @param fk an instance of the kernel
	 */
    public MainMenuPanel(final FatalKernel fk){
		// TODO: Get a better background image
    	img = new ImagePanel(fk, "index.jpg").getImage();
    	
    	setLayout(new BorderLayout(0, 0));
    	setDoubleBuffered(true);
    	
    	transparentPanel = new TransparentPanel(fk, this);
    	
    	add(transparentPanel, BorderLayout.SOUTH);    	
    }
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this);	
	}

	@Override
	public void startThreads() {
		// Do nothing
	}

	@Override
	public void stopThreads() {
		// Do nothing
	}
}

/**
 * The transparent nested panel that holds the buttons.
 * 
 * @author Marcos Davila
 */
@SuppressWarnings("serial")
class TransparentPanel extends JPanel implements FatalView {
	
	public TransparentPanel(final FatalKernel fk, FatalView reference){
		renderButtons(fk);
	}
	
	private void renderButtons(final FatalKernel fk) {
    	JButton startGame = new JButton("Versus");
    	startGame.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Shows the character selection screen
				fk.redrawScreen(fk.getView(MAIN), fk.getView(SELECT));
			}
    		
    	});
    	add(startGame);
    	
    	JButton options = new JButton("Options");
    	options.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				fk.redrawScreen(fk.getView(MAIN), fk.getView(OPTIONS));
			}
    		
    	});
    	add(options);
    	
    	JButton btnQuit = new JButton("Quit");
    	btnQuit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Closes the screen and exits the game
				fk.exit();
			}
    		
    	});
    	add(btnQuit);

	}

	/**
	 * Sets the transparency of the panel.
	 */
	@Override
	public void paintComponent(Graphics g) {
		final int ALPHA_VALUE = 125;
		final int DELAY = 16;
		
		// Set the transparency
        setBackground(new Color(0, 0, 0, ALPHA_VALUE));
        setDoubleBuffered(true);
        
        try {
        	Thread.sleep(DELAY);
        } catch (InterruptedException e) {
        	
        }
	}

	/**
	 * Does nothing
	 */
	@Override
	public void startThreads() {
		// Do nothing
		
	}

	/**
	 * Does nothing
	 */
	@Override
	public void stopThreads() {
		// Do nothing
		
	}
}