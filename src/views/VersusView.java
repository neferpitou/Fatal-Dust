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
	 * Sets up the view for the players to fight
	 */
	public VersusView(){
		startThreads();
	}
	
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
	public void startThreads() {
		setStageAttributes();
	}

	@Override
	public void stopThreads() { 
		
	}

	/*
	 * Sets a random stage background from a selection of possible stage 
	 * backgrounds
	 */
	private void setStageAttributes() {
    	img = kernel.setStage();
    	
    	// TODO: Play back music depending on what stage was loaded
	
	}
}
