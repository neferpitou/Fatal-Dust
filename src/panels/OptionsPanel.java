package panels;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import etc.FatalCardLayout;

/**
 * Displays an options menu where a user can change settings such as display and
 * difficulty to their liking
 * 
 * @author Marcos Davila
 * @date 2/2/2014
 * @revisionhistory 
 * 2/5/2014 - File Created
 */

@SuppressWarnings("serial")
public class OptionsPanel extends JPanel {
	
	public static final String tag = "OPTIONS";

	public OptionsPanel(final FatalCardLayout fcl){
		// TODO: Insert background image for optionsPanel
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		
		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: Persist changes and notify the kernel
				
			}
			
			
		});
		panel.add(btnApply);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Persist changes and notify the kernel
				fcl.showPanel(MainMenuPanel.tag);
			}
			
		});
		panel.add(btnBack);
	}
	
	// TODO: Overwrite paintComponent() method to draw image

}
