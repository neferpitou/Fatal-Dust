package objects;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

/**
 * A class to show a blinking label to catch the player's eye and
 * transmit useful information.
 * 
 * @author Marcos Davila
 *
 */
public class BlinkLabel extends JLabel {
	private static final long serialVersionUID = 1L;

	private static final int BLINKING_RATE = 1000; // in ms

	/**
	 * Creates a new BlinkLabel with the text specified
	 */
	public BlinkLabel(String text) {
		super(text);
		Timer timer = new Timer(BLINKING_RATE, new TimerListener(this));
		timer.setInitialDelay(0);
		timer.start();
	}

	private class TimerListener implements ActionListener {
		private BlinkLabel bl;
		private boolean isForeground = true;

		/**
		 * Associates a BlinkLabel with a listener
		 * @param bl the BlinkLabel the timer should listen on
		 */
		public TimerListener(BlinkLabel bl) {
			this.bl = bl;
		}

		/**
		 * Alternate between showing and hiding the JLabel
		 */
		public void actionPerformed(ActionEvent e) {
			if (isForeground) {
				bl.setVisible(true);
			} else {
				bl.setVisible(false);
			}

			isForeground = !isForeground;
		}
	}
}