package panels;

import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * The view class from which all views in this game are derived. It holds common state
 * for all views, multiple constructors for which views can be constructed from, and
 * identifies methods that all views must implement.
 * 
 * @author Marcos Davila
 * @date 2/27/2014
 * @revisionhistory 
 */

@SuppressWarnings("serial")
public abstract class AbstractPanel extends JPanel {

	public AbstractPanel() {
		// TODO Auto-generated constructor stub
	}

	public AbstractPanel(LayoutManager arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public AbstractPanel(boolean arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public AbstractPanel(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void startThreads();
	public abstract void stopThreads();

}
