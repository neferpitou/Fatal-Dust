package panels;

import java.awt.Graphics;
import java.awt.Image;

import kernel.FatalKernel;
import etc.LoadImage;

@SuppressWarnings("serial")
public class FightingPanel extends AbstractPanel {

	private Image img;

	public FightingPanel(FatalKernel fk_reference){
		// TODO: Get a better background image
    	LoadImage li = new LoadImage();
    	img = li.loadImage("index.jpg");
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
}
