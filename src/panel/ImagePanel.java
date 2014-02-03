package panel;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.JPanel;

/**
 * Displays a loading GIF on a panel while threads handle loading of resources
 * 
 * @author Marcos Davila
 * @date 2/2/2014
 * @revisionhistory
 * 		2/2/2014 - Changed from ImageIO to Toolkit to be able to load and run GIF animations
 * 		1/30/2014 - File created
 */
@SuppressWarnings("serial")
public class ImagePanel extends JPanel {

	
	private int RESOLUTION_WIDTH;
	private int RESOLUTION_HEIGHT;
	public static String tag;
	private Image i;
	
	public ImagePanel(String imgPath, LayoutManager newLayout){
		setLayout(newLayout);
		new ImagePanel(imgPath);
	}
	
	public ImagePanel(String imgpath) {
		tag = imgpath;
		
		// Get default screen resolution for this computer
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

		RESOLUTION_WIDTH = gd.getDisplayMode().getWidth();
		RESOLUTION_HEIGHT = gd.getDisplayMode().getHeight();
		
		// Create a new JPanel with a BorderLayout to eventually store the JLabel in
		setLayout(new BorderLayout());
		
		// Get the image and load it into memory. Resource path should be added to string here
		// before finding the image
		imgpath = "resources/" + imgpath;
		i = Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource(imgpath));
		
		// Get the image from the pathname and resize the image to the user's 
		// native resolution
		i = i.getScaledInstance(RESOLUTION_WIDTH, RESOLUTION_HEIGHT, Image.SCALE_FAST);
	}
	
	@Override
	public void paintComponent(Graphics g){
		g.drawImage(i, 0, 0, this);
	}

	/**
	 * Getters and setters
	 */
	public int getRESOLUTION_WIDTH() {
		return RESOLUTION_WIDTH;
	}

	public void setRESOLUTION_WIDTH(int rESOLUTION_WIDTH) {
		RESOLUTION_WIDTH = rESOLUTION_WIDTH;
	}

	public int getRESOLUTION_HEIGHT() {
		return RESOLUTION_HEIGHT;
	}

	public void setRESOLUTION_HEIGHT(int rESOLUTION_HEIGHT) {
		RESOLUTION_HEIGHT = rESOLUTION_HEIGHT;
	}
}
