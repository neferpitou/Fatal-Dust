package interfaces;

/**
 * An interface for all views that describe the things views must be capable of doing.
 * 
 * @author Marcos Davila
 * @date 2/27/2014
 * @revisionhistory 2/28/2014 - AbstractPanel class transformed into FatalDustViewParameters 
 * 								interface to provide better modularity to the panels in terms
 * 								of implementation of start and stop thread functions. This
 * 								interface also merges the String constants from ViewLabels,
 * 								which enable panels to switch on command.
 * 
 * 					2/27/2014 - Class created
 */

public interface FatalView {
	
	public String MAIN = "MAIN";
	public String OPTIONS = "OPTIONS";
	public String SELECT = "SELECT";
	public String VERSUS = "VERSUS";
	public String LOADING = "LOADING";
	
	// All classes need to start their own threads when they come into view
	public abstract void startThreads();
	
	// All classes need to know how to kill their own threads
	public abstract void stopThreads();

}
