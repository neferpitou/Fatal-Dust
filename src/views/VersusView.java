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
		g.drawImage(img, 0, 0, this);
	}

	@Override
	public void startThreads() {
		setStageBackground();
	}

	@Override
	public void stopThreads() { }

	/*
	 * Logic that should happen before the game starts
	 */
	private void setStageBackground() {
		// TODO: Get a better background image
    	img = kernel.loadStageImage("ayako_stage.gif");
	}
}
