package interfaces;

import kernel.FatalKernel;

/**
 * An interface that all views implement. It grants access to identifiers that
 * views can use to refer to other views and requires all views to be capable of
 * starting and stopping their threads.
 * 
 * @author Marcos Davila
 * 
 */

public interface FatalView {
	
	/*
	 * Identifies all the possible screens in the hashmap
	 */
	public enum Identifier {
		SELECT, VERSUS, LOADING, CHARACTER_VIEW, BACKGROUND_VIEW, VERSUS_VIEW, ERROR 
	};
	
	/**
	 * The instance of the kernel 
	 */
	public FatalKernel kernel = FatalKernel.getInstance();
	
	/**
	 *  Starts all threads of the associated view.
	 * @return 
	 */
	default boolean startThreads(){
		return true;
	}
	
	/**
	 * Stops all threads of the associated view.
	 */
	default boolean stopThreads(){
		return true;
	}

	/**
	 * Panel should be able to identify itself
	 */
	public String toString();
}
