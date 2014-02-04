package etc;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * LoadImage class was seperated from the ImagePanel class to be able to load images
 * into any panel that requires a background iamge.
 * 
 * @author Marcos Davila
 * @date 2/3/2014
 * @revisionhistory
 * 		2/3/2014 - LoadImage class refactored from ImagePanel class
 */
public class LoadImage {

	private int RESOLUTION_WIDTH;
	private int RESOLUTION_HEIGHT;
	private Image i;

	public LoadImage() {

		// Get default screen resolution for this computer
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();

		RESOLUTION_WIDTH = gd.getDisplayMode().getWidth();
		RESOLUTION_HEIGHT = gd.getDisplayMode().getHeight();
	}

	public Image loadImage(String imgpath) {
		// Get the image and load it into memory. Resource path should be added
		// to string here
		// before finding the image
		imgpath = "resources/" + imgpath;
		i = Toolkit.getDefaultToolkit().createImage(
				getClass().getClassLoader().getResource(imgpath));

		// Get the image from the pathname and resize the image to the user's
		// native resolution
		i = i.getScaledInstance(RESOLUTION_WIDTH, RESOLUTION_HEIGHT,
				Image.SCALE_FAST);
		
		return i;
	}
	
	/**
	 * Changes the image of the panel from the old image to a new image
	 * @param newImg - location of the new image to be displayed
	 */
	public void changeImage(String newImg){
		i = loadImage(newImg);
	}
}
