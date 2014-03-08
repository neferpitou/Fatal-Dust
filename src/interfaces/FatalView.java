package interfaces;

import kernel.FatalKernel;

/**
 * An interface that all views implement. It grants access to identifiers that
 * views can use to refer to other views and requires all views to be capable of
 * starting and stopping their threads.
 * 
 * @author Marcos Davila
 */

public interface FatalView {
	
	/**
	 * Identifier for the character selection menu
	 */
	public String SELECT = "SELECT";
	
	/**
	 * Identifier for versus menu.
	 */
	public String VERSUS = "VERSUS";
	
	/**
	 * Identifier for loading screen.
	 */
	public String LOADING = "LOADING";
	
	/**
	 * Identifier for error screen.
	 */
	public String ERROR = "ERROR";
	
	/**
	 * Character panel identifier
	 */
	public String CHARACTER_VIEW = "CHARACTER_VIEW";
	
	/**
	 * Background panel identifier
	 */
	public String BACKGROUND_VIEW = "BACKGROUND_VIEW";
	
	/**
	 * Versus panel identifier
	 */
	public String VERSUS_VIEW = "VERSUS_VIEW";
	
	/**
	 * The instance of the kernel 
	 */
	public FatalKernel kernel = FatalKernel.getInstance();
	
	/**
	 *  Starts all threads of the associated view.
	 */
	public abstract void startThreads();
	
	/**
	 * Stops all threads of the associated view.
	 */
	public abstract void stopThreads();

	/**
	 * Panel should be able to identify itself
	 */
	public abstract String toString();
}
