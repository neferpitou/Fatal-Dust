package views;

import interfaces.FatalView;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import kernel.FatalKernel;

/**
 * Displays an image panel while threads handle loading of resources
 * 
 * @author Marcos Davila
 */
@SuppressWarnings("serial")
public class ImagePanel extends JPanel implements FatalView {

	// Background image
	private Image i;

	/**
	 * Creates a JPanel that paints an image as it's background.
	 * 
	 * @param fk kernel instance
	 * @param imgpath name of image in resources folder
	 */
	public ImagePanel(final FatalKernel fk, String imgpath) {
		this.i = fk.loadImage(imgpath);	
	}
	
	/**
	 * Returns the image drawn on the background of the JPanel
	 * 
	 * @return an Image object that is the background of this JPanel
	 */
	public Image getImage(){
		return i;
	}
	
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
