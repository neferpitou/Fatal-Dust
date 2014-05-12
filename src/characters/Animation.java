package characters;

import java.awt.Image;
import java.util.ArrayList;

import kernel.FatalKernel;

/**
 * The Animation class manages a series of images (frames) and the amount of
 * time to display each frame.
 * 
 * @author Marcos Davila, Rafael Abbondanza
 */

public class Animation {
	private ArrayList<AnimFrame> frames;
	private int currFrameIndex;
	private long animTime;
	private long totalDuration;
	private FatalKernel kernel = FatalKernel.getInstance();
	private final int ANIMATION_SPEED = 200;

	/**
	 * Creates a new, empty Animation
	 */
	public Animation() {
		frames = new ArrayList<AnimFrame>();
		totalDuration = 0;
		start();
	}

	/**
	 * Creates an animation for a specific character along with the
	 * number of positions, direction, and amount of each.
	 * 
	 * @param fighterName character's name
	 * @param position number of possible stances
	 * @param direction left or right
	 * @param count how many images it takes to complete one animation
	 */
	public Animation(String fighterName, String position, String direction,
			int count) {
		frames = new ArrayList<AnimFrame>();
		totalDuration = 0;

		for (int j = 0; j < count; j++) {
			String file = "/resources/" + fighterName + "/" + position + "/"
					+ position + "_" + direction + "_" + j + ".gif";

			if (FatalKernel.DEBUG_MODE_ON) {
				System.err.println(file);
			}

			Image img = kernel.loadCharacters(file);
			addFrame(img, ANIMATION_SPEED);
		}

		start();
	}

	/**
	 * Adds an image to the animation with the specified duration
	 * 
	 * @param image a fighter pose
	 * @param duration how long the pose should be shown
	 */
	public synchronized void addFrame(Image image, long duration) {
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
	}

	/**
	 * Starts this animation over from the beginning
	 */
	public synchronized void start() {
		animTime = 0;
		currFrameIndex = 0;
	}

	/**
	 * Updates this animation's current image if necessary
	 * 
	 * @param elapsedTime amount of time since last image
	 * was shown
	 */
	public synchronized void update(long elapsedTime) {
		if (frames.size() > 1) {
			animTime += elapsedTime;

			if (animTime >= totalDuration) {
				// Loop the animation
				animTime = animTime % totalDuration;
				currFrameIndex = 0;
			}

			while (animTime > getFrame(currFrameIndex).endTime) {
				currFrameIndex++;
			}
		}
	}

	/**
	 * Gets this Animation's current image. Return null if this animation has no
	 * images
	 */
	public synchronized Image getImage() {
		if (frames.size() == 0) {
			return null;
		} else {
			return getFrame(currFrameIndex).image;
		}
	}

	/**
	 * Returns the specified animation at the index
	 * 
	 * @param index index of the list
	 */
	private AnimFrame getFrame(int index) {
		return frames.get(index);
	}

	/*
	 * Associates an image that's a character pose with
	 * the amount of time it should be shown on screen.
	 *  
	 * @author Marcos Davila
	 *
	 */
	private class AnimFrame {
		Image image;
		long endTime;

		public AnimFrame(Image image, long endTime) {
			this.image = image;
			this.endTime = endTime;
		}
	}
}