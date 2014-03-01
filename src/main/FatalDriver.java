package main;

import kernel.FatalKernel;

/**
 * 
 * @author Robert Abbondanza
 * @author Marcos Davila
 * @author Stuart Kleinman
 * @author Dana Smith
 * 
 * Fatal Dust 0.1
 * 
 * The main method of the game, which starts a new 
 * instance of the kernel.
 * 
 */
public class FatalDriver {

	/**
	 * Starts the kernel
	 * 
	 * @param args optional command line arguments
	 */
	public static void main(String[] args) {
		new FatalKernel(args);
	}

}
