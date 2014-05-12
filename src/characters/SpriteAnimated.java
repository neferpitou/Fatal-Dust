package characters;

import java.awt.*;

import kernel.FatalKernel;

/**
 * The SpriteAnimated class contains sprite animations
 * for a specific character in the game. Works in tandem
 * with the Animation class to render images on the screen.
 * 
 * @author Marcos Davila, Rafael Abbondanza
 *
 */
public class SpriteAnimated extends Rectangle {
	private Animation[] animation;
	private int currentAnimation;
	private long startTime = System.currentTimeMillis();

	/**
	 * Creates an instance of a sprite that contains all of it's
	 * possible animations, it's location on the screen, and
	 * the current animation it is on.
	 */
	public SpriteAnimated(int x, int y, int w, int h, String direction,
			String fighterName, String[] positions, int[] count) {

		super(x, y, 0, 0);

		this.x = x;
		this.y = y;

		this.w = w;
		this.h = h;

		animation = new Animation[positions.length];

		currentAnimation = 0;

		for (int i = 0; i < animation.length; i++)
			animation[i] = new Animation(fighterName, positions[i], direction,
					count[i]);
	}

	public void setCurrentAnimation(int newAnimation) {
		currentAnimation = newAnimation;
	}

	/**
	 * Draws an animation of the sprite onto the screen.
	 * 
	 * @param g graphics context
	 */
	public void draw(Graphics g) {
		// Draws the animation on the screen based off how much
		// time has passed since it was last drawn
		long elapsedTime = System.currentTimeMillis() - startTime;
		startTime = System.currentTimeMillis();
		animation[currentAnimation].update(elapsedTime);
		g.drawImage(animation[currentAnimation].getImage(), x, y, w, h, null);
		
		// Draws border around image
		if (FatalKernel.DEBUG_MODE_ON)
			super.draw(g);
	}
}
