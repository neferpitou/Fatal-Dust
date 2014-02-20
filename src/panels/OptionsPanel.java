package panels;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import kernel.FatalKernel;
import etc.LoadImage;

import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	
	private OptionsPanel op_reference = this;
	private FatalKernel fk_reference;
	private Image img;
	private String[] parameters;
	public final int DIFFICULTY = 0;

	public OptionsPanel(final FatalKernel fk){
		fk_reference = fk;
		parameters = fk.getGameParameters();
		
		// TODO: Insert background image for optionsPanel		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0,0,0,125));
		add(panel, BorderLayout.SOUTH);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Persist changes and notify the kernel
				fk.setGameParameters(parameters);
				MainMenuPanel mmp = new MainMenuPanel(fk_reference);
				fk.redrawScreen(op_reference, mmp);			
			}		
		});
		panel.add(btnBack);
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new MigLayout("", "[][][][][][][][][][][]", "[][][]"));
		
		// Difficulty label and spinner to allow user to select their difficulty
		// Defaults to Medium difficulty by the Kernel but can be changed by the
		// user
		JLabel lblDifficulty = new JLabel("Difficulty");
		panel_1.add(lblDifficulty, "cell 2 2");	
		
		final JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerListModel(new String[] {"Easy", "Medium", "Hard"}));
		spinner.setValue(parameters[DIFFICULTY]);
		spinner.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// Records the new difficulty chosen by the player
				parameters[DIFFICULTY] = (String) spinner.getValue();
			}
			
		});
		panel_1.add(spinner, "cell 10 2");

		
		// TODO: Get a better background image
    	LoadImage li = new LoadImage();
    	img = li.loadImage("index.jpg");	
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}

}
