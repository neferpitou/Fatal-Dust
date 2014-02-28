package panels;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import etc.LoadImage;
import etc.ViewLabels;

import javax.swing.JButton;

import kernel.FatalKernel;

/**
 * Character selection menu
 * 
 * @author: Marcos Davila
 * @revisionhistory
 * 		2/27/2014 - Timer thread rewritten into this class' run method. Translucent
 * 					panel turned into transparent panel.
 */
@SuppressWarnings("serial")
public class CharacterSelectionPanel extends AbstractPanel implements ViewLabels, Runnable {

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
		timerThread = new Thread(this);
		timerThread.start();
	}

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