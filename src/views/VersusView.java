package views;

import interfaces.FatalView;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import objects.Camera;

/**
 * This is the actual game panel where rules, AI, collision detection,
 * and other necessary details for the fighting game to function will be.
 * 
 * @author Marcos Davila
 *
 */
@SuppressWarnings("serial")
public class VersusView extends JPanel implements FatalView {

	private Image img;
	private final double SCALE = 1.3;
	private int left_img_bounds, right_img_bounds;
	private Camera camera;
	
	/**
	 * Paints the stage background.
	 * 
	 * @param g a Graphics object to do the rendering
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// Start the image with the left quarter of the image off the left edge of the screen
		// Also scale the width of this image to 1.3 times the original ratio
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}

	@Override
	public boolean startThreads() {
		setStageAttributes();
		kernel.playBGM("27 Sneakman (Toronto Mix).mp3");
		return true;
	}

	@Override
	public boolean stopThreads() { 
		kernel.stopBGM();
		return true;
	}

	/*
	 * Sets a random stage background from a selection of possible stage 
	 * backgrounds
	 */
	private void setStageAttributes() {
    	img = kernel.setStage();    	
    	left_img_bounds = 0;
    	right_img_bounds = new ImageIcon(img).getIconWidth();
    	
    	// TODO: Initialize characters and set their positions here
    	// TODO: Initialize camera and pass it the bounds
	}
}
