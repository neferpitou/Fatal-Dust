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
import etc.LoadImage;

/**
 * Displays the main menu with buttons to move through the functions of the game.
 * 
 * @author Marcos Davila
 * @date 2/27/2014
 * @revisionhistory 2/27/2014 - TransPanel and MainMenuPanel more cleanly separated. This
 * 					view now extends a parent class AbstractPanel which helps in the 
 * 					management of this view's threads when the page is not active.
 * 
 * 					1/30/2014 - File created
 */
@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel implements FatalView {
	
	private Image img;
	private TransPanel transPanel;
	private LoadImage li = new LoadImage();
	
    public MainMenuPanel(final FatalKernel fk){
		// TODO: Get a better background image
    	img = li.loadImage("index.jpg");
    	
    	setLayout(new BorderLayout(0, 0));
    	setDoubleBuffered(true);
    	
    	transPanel = new TransPanel(fk, this);
    	
    	add(transPanel, BorderLayout.SOUTH);    	
    }
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this);	
	}

	@Override
	public void startThreads() {
		// TODO Auto-generated method stub
		;
	}

	@Override
	public void stopThreads() {
		// TODO Auto-generated method stub
		;
	}
}

/**
 * The transparent nested panel that holds the buttons.
 * 
 * @author Marcos Davila
 * @revisionhistory
 * 		2/27/2014 - TransPanel separated more cleanly from the MainMenuPanel.
 * 					redrawScreen calls now call only one instance of a view.
 *
 */
@SuppressWarnings("serial")
class TransPanel extends JPanel implements FatalView {
	
	public TransPanel(final FatalKernel fk, FatalView reference){
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

	@Override
	public void startThreads() {
		// Do nothing
		
	}

	@Override
	public void stopThreads() {
		// Do nothing
		
	}
}