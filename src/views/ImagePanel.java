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
 * @date 2/2/2014
 * @revisionhistory 
 * 3/1/2014 - Made into a more general image class.
 * 2/5/2014 - Moved out of kernel to reduce kernel size
 * 2/3/2014 - Details of loading images refactored into LoadImage class, this class
 * 			  refactored into the kernel
 * 2/2/2014 - Changed from ImageIO to Toolkit to be able to load and run GIF 
 * 			  animations. Moved drawing of images into overriden 
 * 			  paintComponent() method 
 * 1/30/2014 - File created
 */
@SuppressWarnings("serial")
public class ImagePanel extends JPanel implements FatalView {

	private Image i;

	public ImagePanel(final FatalKernel fk, String imgpath) {
		this.i = fk.loadImage(imgpath);	
	}
	
	/**
	 * Return the image
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
