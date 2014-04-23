/**
 * Camera class that moves with players but stops panning when players are to the far left or right of the screen.
 * 
 * @author Dana Smith, Marcos Davila
 * 
 */
package objects;

import kernel.FatalKernel;

public class Camera {

	// The x coordinate on the screen that the camera is focused on
	private int cameraFocus;
	
	// The x coordinates of the camera bounds
	private int leftBounds, rightBounds;
	
	// Kernel instance
	public static FatalKernel kernel = FatalKernel.getInstance();
	
	// get resolution width of entire screen.
	private int screenWidth = kernel.getScreenWidth();

	// Half of the width of the entire screen
	private int halfWidth = screenWidth / 2;

	/**
	 * Initializes camera with the upper left hand corner signified by pos
	 * 
	 * @param p1xpos - location of player one on screen
	 * @param p2xpos - location of player two on screen
	 * @param lbounds - x location of how far the camera can go left
	 * @param rbounds - x location of how far the camera can go right
	 */
	public Camera(int p1xpos, int p2xpos, int lbounds, int rbounds) {
		leftBounds = lbounds;
		rightBounds = rbounds;
		setCenter(p1xpos, p2xpos);
	}

	/**
	 * Focuses the camera at the point between the two specified coordinates.
	 * This method ensures that the camera will never be focused on a part of
	 * the screen that does not have a painted stage background.
	 * 
	 * @param p1xpos
	 *            - location of player one on screen
	 * @param p2xpos
	 *            - location of player two on screen
	 */
	public void setCenter(int p1xpos, int p2xpos) {
		int tentativeFocus = (p1xpos + p2xpos) / 2;
		
		if (tentativeFocus + halfWidth > rightBounds){
			tentativeFocus = rightBounds - halfWidth;
		} else if (tentativeFocus - halfWidth < leftBounds){
			tentativeFocus = leftBounds + halfWidth;
		} 
		
		cameraFocus = tentativeFocus;		
	}
	
	/** 
	 * Moves camera to always be in the center of the two characters.
	 * 
	 * @param p1xpos - location of player one on screen
	 * @param p2xpos - location of player two on screen
	 * @return the new x coordinate that the camera is looking at
	 */
	/*
	 * TODO The value that is being returned here can be used by 
	 * the view in conjunction with the screen width to paint
	 * only the parts of the image that fall within that range.
	 * 
	 * The idea for the paintComponent in the VersusView is:
	 * lbounds = cameraFocus - halfWidth
	 * rbounds = cameraFocus + halfWidth
	 * 
	 * g.drawImage(img, lbounds, 0, rbounds, getHeight(), this);
	 */
	public int move(int p1xpos, int p2xpos) {
		setCenter(p1xpos, p2xpos);
		return cameraFocus;
	}
}
