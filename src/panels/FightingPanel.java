package panels;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import etc.LoadImage;

public class FightingPanel extends JPanel {

	private Image img;
	public static final String tag = "FIGHT";

	public FightingPanel(){
		// TODO: Get a better background image
    	LoadImage li = new LoadImage();
    	img = li.loadImage("index.jpg");
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}
}
