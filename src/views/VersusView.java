package views;

import interfaces.FatalView;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

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
	
	/**
	 * Paints the stage background.
	 * 
	 * @param g a Graphics object to do the rendering
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// Start the image with the left quarter of the image off the left edge of the screen
		// Also scale the width of this image to 1.3 times the original ratio
		g.drawImage(img, getWidth() / -4, 0, (int)(getWidth() * SCALE), getHeight(), this);
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
    	
    	// TODO: Play back sounds depending on what stage was loaded
	
	}
}
