package views;

import interfaces.FatalView;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import kernel.FatalKernel;

/**
 * Displays an options menu where a user can change settings.
 * 
 * @author Marcos Davila
 */

@SuppressWarnings("serial")
public class OptionsPanel extends JPanel implements FatalView {
	
	private Image img;
	private ArrayList<String> parameters;
	
	/**
	 * Difficulty level of the game
	 */
	public final int DIFFICULTY = 0;

	/**
	 * Creates the view of the options panel.
	 * 
	 * @param fk an instance of the kernel
	 */
	public OptionsPanel(final FatalKernel fk){
		parameters = fk.getGameParameters();
		
		// TODO: Insert background image for optionsPanel		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		add(panel, BorderLayout.SOUTH);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Persist changes and notify the kernel
				fk.updateGameParameters(parameters);
				fk.redrawScreen(fk.getView(OPTIONS), fk.getView(MAIN));			
			}		
		});
		panel.add(btnBack);
		
		// TODO: Get a better background image
    	img = new ImagePanel(fk, "index.jpg").getImage();	
	}
	
	/**
	 * Paints the image as the background of the JPanel
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}

	/**
	 * This class has no threads to start, so this method does nothing.
	 */
	@Override
	public void startThreads() {
		// Do nothing
		
	}

	/**
	 * This class has no threads to stop, so this method does nothing.
	 */
	@Override
	public void stopThreads() {
		// Do nothing
		
	}

}
