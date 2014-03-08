package views;

import interfaces.FatalView;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * Displays an image panel while threads handle loading of resources.
 * 
 * @author Marcos Davila
 */
@SuppressWarnings("serial")
public class BackgroundView extends JPanel implements FatalView {

	// Background image
	private Image i;

	/**
	 * Creates a JPanel that paints an image as it's background.
	 * 
	 * @param imgpath name of image in resources folder
	 */
	public BackgroundView(String imgpath) {
		this.i = kernel.loadImage(imgpath);	
	}
	
	/**
	 * Creates a blank image panel which signifies an error with the view
	 */
	public BackgroundView() {
		
	}

	/**
	 * Returns the image drawn on the background of the JPanel
	 * 
	 * @return an Image object that is the background of this JPanel
	 */
	public Image getImage(){
		return i;
	}
	
	/**
	 * Renders the image on the view.
	 * 
	 * @param g a Graphics object to do the rendering
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(i, 0, 0, this);
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
