package panels;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import etc.LoadImage;

import javax.swing.JButton;

import kernel.FatalKernel;
import kernel.FatalKernel.Screen;

/**
 * Character selection menu
 * 
 * @author: Marcos Davila
 */
@SuppressWarnings("serial")
public class CharacterSelectionPanel extends JPanel {
	public final static String tag = "CHARACTERSELECT";
	private Image img;
	private javax.swing.Timer timer;
	private final static int SELECTION_TIME = 60;
	private final static int ONE_MINUTE = 1000;
	private Screen screen;
	private CharacterSelectionPanel csp_reference = this;
	private FatalKernel fk_reference;
	private String[] parameters;
	
	public CharacterSelectionPanel(final FatalKernel fk) {
		fk_reference = fk;
    	parameters = fk.getGameParameters();
		
		/*
		 * Create a back and a start button to either back out of this menu
		 * or begin the game
		 */
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Show the main menu and stop all running threads
				timer.stop();
				fk.redrawScreen(csp_reference, new MainMenuPanel(fk));
			}
			
		});
		add(btnBack);
		
		JButton btnGo = new JButton("Fight");
		btnGo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				startFight(screen);
			}
			
		});
		add(btnGo);
		
		timer = new javax.swing.Timer(ONE_MINUTE, new ActionListener(){
    		int ctr = SELECTION_TIME;
    		
    		public void actionPerformed(ActionEvent tc) {
    			ctr--;
    			if (ctr >= 1) {
    				System.out.println("Time left: " + ctr);
    			} else {
    				// TODO: Launch the fighting panel
    				startFight(screen);
    			}
    		}
    	});
    	timer.start();
		
		/*
    	 * Describes the layout of all components on this panel
    	 */
    		    	
    	// TODO: Get a better background image
    	LoadImage li = new LoadImage();
    	img = li.loadImage("index.jpg");
    	
	}
    
    @Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}
    
    /*
     * This method holds tasks that should be done when a fight is about
     * to commence
     */
    private void startFight(final Screen screen) {
		// TODO Takes the selected characters and proceeds to the fighting screen
    	timer.stop();
    	fk_reference.redrawScreen(csp_reference, new FightingPanel(fk_reference));
	}
}