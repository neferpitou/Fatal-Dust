package etc;
//panel is the reoslution that is always the same
//cards are different for the panels
import java.awt.CardLayout;

import javax.swing.JPanel;

/**
 * A custom layout that functions as a CardLayout wrapper with additional methods
 * to reduce boilerplate code in managing CardLayout's ability to switch JPanels
 * on the fly.
 * 
 * @author Marcos Davila
 * @date 2/2/2014
 * @revisionhistory 
 * 2/5/2014 - File created
 */
@SuppressWarnings("serial")
public class FatalCardLayout extends CardLayout {
	
	// Initialize an instance of the JPanel with a FatalCardLayout
	private JPanel panel = new JPanel(this);

	public JPanel getPanel(){
		return panel;
	}
	
	/**
	 * Adds a card to the FatalCardLayout to enable a screen to be chosen.
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
