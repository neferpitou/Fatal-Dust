package interfaces;

/**
 * An interface for all views that describe the things views must be capable of
 * doing.
 * 
 * @author Marcos Davila
 */

public interface FatalView {
	
	/**
	 * Identifier for main menu
	 */
	public String MAIN = "MAIN";
	
	/**
	 * Identifier for options menu
	 */
	public String OPTIONS = "OPTIONS";
	
	/**
	 * Identifier for select menu
	 */
	public String SELECT = "SELECT";
	
	/**
	 * Identifier for versus menu
	 */
	public String VERSUS = "VERSUS";
	
	/**
	 * Identifier for loading screen
	 */
	public String LOADING = "LOADING";	
	
	/**
	 *  All classes need to start their own threads when they come into view
	 */
	public abstract void startThreads();
	
	/**
	 * All classes need to know how to kill their own threads
	 */
	public abstract void stopThreads();

}
