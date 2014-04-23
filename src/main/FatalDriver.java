package main;

import kernel.FatalKernel;

/**
 * The main method of the game, which starts the kernel.
 * 
 * @author Robert Abbondanza
 * @author Marcos Davila
 * @author Dana Smith
 */
public class FatalDriver {

	/**
	 * Starts the kernel
	 * 
	 * @param args optional command line arguments
	 */
	public static void main(String[] args) {
		FatalKernel.getInstance();
	}

}
