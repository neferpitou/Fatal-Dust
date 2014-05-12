package views;

import interfaces.CollisionHandler;
import characters.Rectangle;
import interfaces.FatalView;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import kernel.FatalKernel;
import characters.HealthBar;
import characters.Rectangle;
import characters.VanillaCharacter;

/**
 * This is the actual game panel where rules, AI, collision detection, and other
 * necessary details for the fighting game to function will be.
 *
 * @author Marcos Davila, Rafael Abbodanza, Dana Smith
 *
 */
@SuppressWarnings("serial")
public class VersusView extends JPanel implements FatalView, KeyListener {

	private Image img;
	private int right_img_bounds;
	private final VanillaCharacter playerOne, playerTwo;
	boolean[] isPressed = new boolean[256];

	private final int LEFT_SCREEN_BOUNDARY = 0;
	private final int RIGHT_SCREEN_BOUNDARY = kernel.getScreenWidth();

	// Screen Boundaries that prevent characters from going off-screen
	// Rectangle rightScreenBoundary = new Rectangle(kernel.getScreenWidth(), 0,
	// 0, 768);
	// Rectangle leftScreenBoundary = new Rectangle(0, 0, 0, 768);

	private final HealthBar healthBarLeft;
	private final HealthBar healthBarRight;

	public VersusView(final VanillaCharacter playerOne,
			final VanillaCharacter playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;

		healthBarLeft = new HealthBar(this.playerOne, true);
		healthBarRight = new HealthBar(this.playerTwo, false);

		this.addKeyListener(this);

		kernel.execute(() -> {
			for (int i = 0; i < isPressed.length; i++) {
				isPressed[i] = false;
			}
		});
	}

	public void handleCollisions() {
		handleCollisionsBetween(playerOne, playerTwo);
	}

	private void handleCollisionsBetween(final VanillaCharacter c1,
			final VanillaCharacter c2) {

		if (!insideBoundaries(c1)) {
			c1.setBackwardCapable(false);
		}

		if (!insideBoundaries(c2)) {
			c2.setBackwardCapable(false);
		}

		// c1 hits c2 - playerOne hits playerTwo
		if (c1.strikeBox.hasCollidedWith(c2.hitBox)) {
			c2.takeHit();

		}

		if (c1.hitBox.hasCollidedWith(c2.hitBox)) {
			c1.setForwardCapable(false);
			c2.setForwardCapable(false);
		} else {
			c1.setForwardCapable(true);
			c2.setForwardCapable(true);
		}

		// c2 hit c1 - playerTwo hits playerOne
		if (c1.hitBox.hasCollidedWith(c2.strikeBox)) {
			c1.takeHit();

		}

	}

	@Override
	public void keyPressed(final KeyEvent e) {
		isPressed[e.getKeyCode()] = true;

		if (isPressed[KeyEvent.VK_P]) {
			FatalKernel.PAUSED = !FatalKernel.PAUSED;
		}
	}

	@Override
	public void keyReleased(final KeyEvent e) {
		isPressed[e.getKeyCode()] = false;

	}

	@Override
	public void keyTyped(final KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Paints the stage background.
	 *
	 * @param g
	 *            a Graphics object to do the rendering
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		if (!FatalKernel.PAUSED) {
			// Start the image with the left quarter of the image off the left
			// edge of the screen. Also scale the width of this image to 1.3
			// times the original
			// ratio
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			playerOne.draw(g);
			playerTwo.draw(g);
			healthBarLeft.draw(g);
			healthBarRight.draw(g);

			// x,y coordinates of two lines that are on left and right of screen
			// so players do not
			// pass them and cannot go off-screen. There are 4 x points and 4 y
			// points (one in each corner).
			// screen resolution: 1280 x 800
			g.setColor(Color.black);
			// leftScreenBoundary.draw(g);
			// // rightScreenBoundary.draw(g);
			//
			// handleCollisionsWithWall(playerOne, playerTwo);
		}
	}

	/**
	 * Listens for the key presses and determine the character's future
	 * positions, which get updated by updatePositions(). The kernel calls this
	 * function.
	 */
	public void respondToInput() {
		final long t1 = System.currentTimeMillis();

		kernel.executeEDT(()->{
			requestFocusInWindow();
		});
		
		
		// Must request focus to listen for KeyEvents
		if(!hasFocus()) {
		    // ensure requestFocus is enabled
		    if(!isRequestFocusEnabled()) { 
		    	setRequestFocusEnabled(true); 
		    }
		    requestFocus();
		}

		int left, right;

		/*
		 * Check input for player one.
		 */
		if (playerOne.lookingRight) {
			right = KeyEvent.VK_D;
			left = KeyEvent.VK_A;
		} else {
			right = KeyEvent.VK_A;
			left = KeyEvent.VK_D;
		}

		if (isPressed[right]) {
			if (playerOne.getForwardCapable())
				playerOne.moveForward(playerOne.getMovement());
		} else if (isPressed[left]) {
			if (playerOne.getBackwardCapable())
				playerOne.moveBackward(playerOne.getMovement());
		} else if (isPressed[KeyEvent.VK_W]) {
			playerOne.jump();
		} else if (isPressed[KeyEvent.VK_S]) {
			playerOne.duck();
		} else if (isPressed[KeyEvent.VK_Q]) {
			playerOne.punch();
		} else if (isPressed[KeyEvent.VK_E]) {
			playerOne.kick();
		} else if (isPressed[KeyEvent.VK_Z]) {
			playerOne.block();
		} else {
			playerOne.idle();
		}

		// Maps the proper keys to the direction that should be
		// moved in relative to the current position of the character
		if (playerTwo.lookingRight) {
			right = KeyEvent.VK_RIGHT;
			left = KeyEvent.VK_LEFT;
		} else {
			right = KeyEvent.VK_LEFT;
			left = KeyEvent.VK_RIGHT;
		}

		if (isPressed[right]) {
			if (playerTwo.getForwardCapable())
				playerTwo.moveForward(playerTwo.getMovement());
		} else if (isPressed[left]) {
			if (playerTwo.getBackwardCapable())
				playerTwo.moveBackward(playerTwo.getMovement());
		} else if (isPressed[KeyEvent.VK_UP]) {
			playerTwo.jump();
		} else if (isPressed[KeyEvent.VK_DOWN]) {
			playerTwo.duck();
		} else if (isPressed[KeyEvent.VK_SHIFT]) {
			playerTwo.punch();
		} else if (isPressed[KeyEvent.VK_ENTER]) {
			playerTwo.kick();
		} else if (isPressed[KeyEvent.VK_CONTROL]) {
			playerTwo.block();
		} else {
			playerTwo.idle();
		}

		// If it takes longer than five seconds to register
		// an input as pressed, display it on the console
		// only if debug mode is on

		if (FatalKernel.DEBUG_MODE_ON) {
			final int ABNORMAL_DELAY = 5;
			final long t2 = System.currentTimeMillis();

			if (t2 - t1 > ABNORMAL_DELAY) {
				System.err.println("Delayed input detected. Time to read input: " + (t2 - t1) + "ms");
			}
		}
	}

	private boolean insideBoundaries(VanillaCharacter player) {
		int leftCharacterEdge = player.getLeftEdge();
		int rightCharacterEdge = player.getRightEdge();

		boolean toLeftOfBoundary = leftCharacterEdge >= LEFT_SCREEN_BOUNDARY;
		boolean toRightOfBoundary = rightCharacterEdge <= RIGHT_SCREEN_BOUNDARY;

		return toLeftOfBoundary && toRightOfBoundary;
	}

	/*
	 * Sets a random stage background from a selection of possible stage
	 * backgrounds
	 */
	private void setStageAttributes() {
		img = kernel.setStage();
		right_img_bounds = new ImageIcon(img).getIconWidth();

		// TODO: Initialize camera and pass it the bounds
	}

	@Override
	public boolean startThreads() {
		this.setStageAttributes();
		kernel.playBGM("27 Sneakman (Toronto Mix).mp3");
		return true;
	}

	@Override
	public boolean stopThreads() {
		kernel.stopBGM();
		return true;
	}

	/**
	 * Updates the position of the characters on the screen, the healthbars,
	 * etc. The kernel calls this in the game loop to refresh the screen.
	 */
	public void updatePositions() {
		if (playerOne.getCenterX() > playerTwo.getCenterX()) {
			if (!playerOne.isJumping()) {
				playerOne.setLookingRight(false);
				playerTwo.setLookingRight(true);
			}
		}

		else {
			if (!playerOne.isJumping()) {
				playerOne.setLookingRight(true);
				playerTwo.setLookingRight(false);
			}
		}

	}
}
