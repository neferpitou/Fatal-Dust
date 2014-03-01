package views;

import interfaces.FatalView;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import javax.swing.JButton;

import kernel.FatalKernel;

/**
 * A visual character selection menu built using Java Swing.
 * 
 * @author Marcos Davila
 */
@SuppressWarnings("serial")
public class CharacterSelectionPanel extends JPanel implements Runnable, FatalView {

	private Image img;
	private javax.swing.Timer timer;
	private final static int SELECTION_TIME = 60;
	private final static int ONE_MINUTE = 1000;
	private FatalKernel fk;
	private Thread timerThread;

	public CharacterSelectionPanel(final FatalKernel fk){
		// TODO: Insert background image for optionsPanel		
		setLayout(new BorderLayout(0, 0));
		this.fk = fk;
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		add(panel, BorderLayout.SOUTH);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Persist changes and notify the kernel
				stopThreads();
				fk.redrawScreen(fk.getView(SELECT), fk.getView(MAIN));			
			}		
		});
		panel.add(btnBack);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Persist changes and notify the kernel
				stopThreads();
				fk.redrawScreen(fk.getView(SELECT), fk.getView(VERSUS));			
			}		
		});
		panel.add(btnStart);
		
		// TODO: Get a better background image
    	img = new ImagePanel(fk, "index.jpg").getImage();	
    	
	}
	
	/**
	 * Render image background
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}

	/**
	 * Start all threads associated with this view
	 */
	@Override
	public void startThreads() {
		// TODO Auto-generated method stub
		timerThread = new Thread(this);
		timerThread.start();
	}

	/**
	 * Stop all threads associated with this view
	 */
	@Override
	public void stopThreads() {
		// TODO Auto-generated method stub
		try {
			timer.stop();
			timerThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Launches timer thread
	 */
	@Override
	public void run() {
		timer = new javax.swing.Timer(ONE_MINUTE, new ActionListener() {
			int ctr = SELECTION_TIME;

			public void actionPerformed(ActionEvent tc) {
				ctr--;
				if (ctr >= 1) {
					System.out.println("Time left: " + ctr);
				} else {
					// TODO: Launch the fighting panel
					fk.redrawScreen(fk.getView(SELECT), fk.getView(VERSUS));
				}
			}
		});
		timer.start();
		
	}
}