package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import interfaces.FatalView;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JButton;

import java.awt.Insets;

/**
 * A screen to display when the game first loads up. It is a very simple screen
 * that provides buttons that lead to different screens.
 * 
 * @author Marcos Davila
 *
 */
@SuppressWarnings("serial")
public class SplashView extends JPanel implements FatalView {

	private Image menu_background = kernel.loadImage("main_menu_image.jpg");

	/**
	 * Set up the layout, listen for key events to redraw the screen, draw and
	 * place two JLabels for the title and the prompt to continue
	 */
	public SplashView() {
		// Place the buttons somewhere around the center of the screen on the
		// right side
		final int centerYLocation = (int) (kernel.getScreenInfo().get(1) * .4);
		setLayout(new BorderLayout(0, 0));

		SwingUtilities.invokeLater(() -> {
			JLabel title = new JLabel("Fatal Dust");
			title.setForeground(Color.WHITE);
			title.setHorizontalAlignment(SwingConstants.CENTER);
			title.setFont(new Font("Segoe Script", Font.BOLD, 72));
			add(title, BorderLayout.NORTH);

			JPanel panel = new JPanel();
			add(panel, BorderLayout.EAST);
			panel.setOpaque(false);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[] { 65, 0 };
			gbl_panel.rowHeights = new int[] { centerYLocation, 23, 23, 0 };
			gbl_panel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
			gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0,
					Double.MIN_VALUE };
			panel.setLayout(gbl_panel);

			JButton btnNewButton = new JButton("Versus");
			btnNewButton.addActionListener((e) -> {
				kernel.redrawScreen(kernel.getView("SPLASH"),
						kernel.getView("VERSUS"));
			});
			GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
			gbc_btnNewButton.anchor = GridBagConstraints.WEST;
			gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
			gbc_btnNewButton.gridx = 0;
			gbc_btnNewButton.gridy = 1;
			panel.add(btnNewButton, gbc_btnNewButton);

			JButton btnNewButton_1 = new JButton("Quit");
			btnNewButton_1.addActionListener((e) -> {
				kernel.exit();
			});
			GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
			gbc_btnNewButton_1.anchor = GridBagConstraints.NORTHWEST;
			gbc_btnNewButton_1.gridx = 0;
			gbc_btnNewButton_1.gridy = 2;
			panel.add(btnNewButton_1, gbc_btnNewButton_1);
		});
	}

	/**
	 * Start the background music
	 */
	@Override
	public boolean startThreads() {
		// Start menu music
		kernel.playBGM("01 menu.wav");
		return true;
	}

	/**
	 * Stop the background music
	 */
	@Override
	public boolean stopThreads() {
		kernel.stopBGM();
		return true;
	}

	/**
	 * Paint an image as the background to the panel
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(menu_background, 0, 0, getWidth(), getHeight(), null);
	}
}
