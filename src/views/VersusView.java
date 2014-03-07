package views;

import interfaces.FatalView;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import kernel.FatalKernel;

/**
 * This is the actual game panel where rules, AI, collision detection,
 * and other necessary details for the fighting game to function will be.
 * 
 * @author Marcos Davila
 *
 */
@SuppressWarnings("serial")
public class VersusView extends JPanel implements Runnable, FatalView {

	private Image img;
	private boolean isRunning;
	private FatalKernel fk = FatalKernel.getInstance();

	/**
	 * Sets up the view for the players to fight
	 */
	public VersusView(){
		init();
	}
	
	/**
	 * Paints the stage background.
	 * 
	 * @param g a Graphics object to do the rendering
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}

	@Override
	public void startThreads() {
		
	}

	@Override
	public void stopThreads() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			init();
			gameLoop();
		} finally {
			stopThreads();
		}
	}

	/*
	 * The main loop of the video game
	 */
	private void gameLoop() {
		// TODO Auto-generated method stub
		while (isRunning){
			
		}
	}

	// Sets up the view and starts threads for the game to run
	private void init() {
		// TODO: Get a better background image
    	img = fk.loadImage("stage background.gif");
    	startThreads();
	}

}
