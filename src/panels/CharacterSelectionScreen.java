package panels;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import etc.LoadImage;

import javax.swing.JButton;

import kernel.FatalKernel.Screen;

/**
 * Character selection menu
 * 
 * @author: Marcos Davila
 */
@SuppressWarnings("serial")
public class CharacterSelectionScreen extends JPanel {
	public final static String tag = "CHARACTERSELECT";
	private Image img;
	private static javax.swing.Timer timer;
	private final static int SELECTION_TIME = 60;
	private final static int ONE_MINUTE = 1000;
	private static Screen screen;
	
	public CharacterSelectionScreen(final Screen screen) {
		// Store this screen object (it should never change) in a local
		// variable to be referenced if a fight should commence
		CharacterSelectionScreen.screen = screen;
		
		/*
		 * Create a back and a start button to either back out of this menu
		 * or begin the game
		 */
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Show the main menu and stop all running threads
				stopThreads();
				screen.showScreen(MainMenuPanel.tag);			
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
     * When this class is displayed by the CardLayout , start
     * any threads that this panel must run
     */
    public static void startThreads(){
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
    }
    
    /*
     * This method holds tasks that should be done when a fight is about
     * to commence
     */
    private static void startFight(final Screen screen) {
		// TODO Takes the selected characters and proceeds to the fighting screen
    	timer.stop();
    	screen.showScreen(FightingPanel.tag);
	}

	/*
     * When this class is no longer the displayed class, stop any threads this panel is
     * running
     */
    public static void stopThreads(){
    	timer.stop();
    }
}