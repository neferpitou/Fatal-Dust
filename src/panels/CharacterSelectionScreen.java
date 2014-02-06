package panels;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import etc.LoadImage;

/**
 * Character selection menu
 * 
 * @author: Marcos Davila
 */
@SuppressWarnings("serial")
public class CharacterSelectionScreen extends JPanel {
	public final static String tag = "CHARACTERSELECT";
	private Image img;
	
	public CharacterSelectionScreen() {
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