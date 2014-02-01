package panel;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LoadingPanel extends JPanel {

	
	private int RESOLUTION_WIDTH;
	private int RESOLUTION_HEIGHT;
	public static String tag;
	
	public LoadingPanel(String imgpath){
		tag = imgpath;
		
		
		
		// Get default screen resolution for this computer
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

		RESOLUTION_WIDTH = gd.getDisplayMode().getWidth();
		RESOLUTION_HEIGHT = gd.getDisplayMode().getHeight();
		
		// Create a new JPanel with a BorderLayout to eventually store the JLabel in
		setLayout(new BorderLayout());
		JFrame dummyFrame = new JFrame();
		
		// Get relative pathname for the img resource directory
		URL pathname = getClass().getResource("/img/" + imgpath);
		
		// Create the JLabel the image will be in
		JLabel label = new JLabel();
		
		// Get the image from the pathname and resize the image to the user's 
		// native resolution
		Image image = dummyFrame.getToolkit().createImage(pathname);
		Image resizedImage = 
		image.getScaledInstance(RESOLUTION_WIDTH, RESOLUTION_HEIGHT, Image.SCALE_FAST);
		ImageIcon icon = new ImageIcon(resizedImage);
		
		// Put the resized image into the label, add the label to the 
		// panel, add the panel to the frame, set the frame visible
		label.setIcon(icon);
		add(label, BorderLayout.CENTER);
		
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
