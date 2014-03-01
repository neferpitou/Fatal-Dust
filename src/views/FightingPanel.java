package views;

import interfaces.FatalView;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import kernel.FatalKernel;

/**
 * This JPanel is the actual game panel where rules, AI, collision detection,
 * and other necessary details for the fighting game to function will be. It
 * is essentially the game.
 * 
 * @author Marcos Davila
 *
 */
@SuppressWarnings("serial")
public class FightingPanel extends JPanel implements Runnable, FatalView {

	private Image img;

	public FightingPanel(final FatalKernel fk){
		// TODO: Get a better background image
    	img = new ImagePanel(fk, "index.jpg").getImage();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}

	@Override
	public void startThreads() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopThreads() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
