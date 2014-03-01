package views;

import interfaces.FatalView;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import etc.LoadImage;

/**
 * Displays a loading GIF on a panel while threads handle loading of resources
 * 
 * @author Marcos Davila
 * @date 2/2/2014
 * @revisionhistory 
 * 
 * 2/5/2014 - Moved out of kernel to reduce kernel size
 * 2/3/2014 - Details of loading images refactored into LoadImage class, this class
 * 			  refactored into the kernel
 * 2/2/2014 - Changed from ImageIO to Toolkit to be able to load and run GIF 
 * 			  animations. Moved drawing of images into overriden 
 * 			  paintComponent() method 
 * 1/30/2014 - File created
 */
@SuppressWarnings("serial")
public class ImagePanel extends JPanel implements FatalView {

	public final String tag = "LOADING";
	private Image i;

	public ImagePanel(String imgPath, LayoutManager newLayout) {
		setLayout(newLayout);
		new ImagePanel(imgPath);
	}

	public ImagePanel(String imgpath) {
		LoadImage li = new LoadImage();
		i = li.loadImage(imgpath);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(i, 0, 0, this);
	}

	public String getTag() {
		return tag;
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
