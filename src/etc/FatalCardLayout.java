package etc;

import java.awt.CardLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class FatalCardLayout extends CardLayout {
	
	private JPanel panel = new JPanel(new CardLayout());

	public JPanel getPanel(){
		return panel;
	}
	
	/**
	 * Adds a card to the CardLayout to enable a screen to be chosen.
	 * 
	 * @param newCard
	 *            - the new card to be added
	 * @param tag
	 *            - a tag to be able to uniquely identify cards
	 */
	public void addScreen(JPanel newCard, String tag) {
		panel.add(newCard, tag);
	}

	/**
	 * Displays the card in the panel that matches the specified tag.
	 * 
	 * @param tag
	 *            - a tag to be able to uniquely identify cards
	 */
	public void showPanel(String tag) {
		CardLayout cl = (CardLayout) (panel.getLayout());
		cl.show(panel, tag);
	}

}
