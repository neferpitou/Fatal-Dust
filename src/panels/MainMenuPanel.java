package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import kernel.FatalKernel.Screen;
import etc.LoadImage;

/**
 * Displays the main menu with buttons to move through the functions of the game
 * 
 * @author Marcos Davila
 * @date 2/2/2014
 * @revisionhistory 
 * 2/3/2014 - Class refactored into the kernel because the buttons may or may not
 * 			  affect the state of the screen and only the kernel has access to it
 * 1/30/2014 - File created
 */
@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel {

	public final static String tag = "MAINMENU";
	private Image img;
		
    public MainMenuPanel(final Screen screen){
    	setLayout(new BorderLayout(0, 0));
    	
    	JPanel panel = new JPanel();
    	// Set the background, black with 125 as alpha value
        // This is less transparent
        panel.setBackground(new Color(0,0,0,125));
    	add(panel, BorderLayout.SOUTH);
    	
    	JButton startGame = new JButton("Versus");
    	startGame.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Shows the character selection screen
				screen.showScreen(CharacterSelectionScreen.tag);
			}
    		
    	});
    	panel.add(startGame);
    	
    	JButton btnQuit = new JButton("Quit");
    	btnQuit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Closes the screen and exits the game
				screen.restoreScreen();
			}
    		
    	});
    	panel.add(btnQuit);

    
    	/*
    	 * Describes the layout of all components on this panel
    	 */
    		    	
    	// TODO: Get a better background image
    	LoadImage li = new LoadImage();
    	img = li.loadImage("index.jpg");	    	
    }
    
    @Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}
}