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
import etc.LoadImage;

/**
 * Displays an options menu where a user can change settings such as display and
 * difficulty to their liking
 * 
 * @author Marcos Davila
 * @date 2/2/2014
 * @revisionhistory 2/27/2014 - redrawScreen calls reworked to interact with hashMap objects.
 * 								Translucent panel turned into transparent panel.
 * 					2/5/2014 - File Created
 */

@SuppressWarnings("serial")
public class OptionsPanel extends JPanel implements FatalView {
	
	private Image img;
	private ArrayList<String> parameters;
	public final int DIFFICULTY = 0;

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
