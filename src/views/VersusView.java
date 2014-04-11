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
	
	/**
	 * Paints the stage background.
	 * 
	 * @param g a Graphics object to do the rendering
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}

	@Override
	public boolean startThreads() {
		setStageAttributes();
		kernel.playBGM("streets.wav");
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
