package views;

import interfaces.CollisionHandler;
import interfaces.FatalView;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import kernel.FatalKernel;
import characters.HealthBar;
import characters.VanillaCharacter;

/**
 * This is the actual game panel where rules, AI, collision detection, and other
 * necessary details for the fighting game to function will be.
 *
 * @author Marcos Davila
 *
 */
@SuppressWarnings("serial")
public class VersusView extends JPanel implements FatalView, KeyListener,
		CollisionHandler {

	private Image img;
	private int right_img_bounds;
	private final VanillaCharacter playerOne, playerTwo;
	boolean[] isPressed = new boolean[256];
	private final HealthBar healthBarLeft = new HealthBar(20, 40, 300, 300, 30,
			"AYAKO");
	private final HealthBar healthBarRight = new HealthBar(1000, 40, 300, 300,
			30, "AYAKO");

	public VersusView(final VanillaCharacter playerOne,
			final VanillaCharacter playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.addKeyListener(this);

		kernel.execute(() -> {
			for (int i = 0; i < isPressed.length; i++) {
				isPressed[i] = false;
			}
		});
	}

	@Override
	public void handleCollisionBetween(final VanillaCharacter c1,
			final VanillaCharacter c2) {

		// c1 hits c2
		if (c1.strikeBox.hasCollidedWith(c2.hitBox)) {
			c2.takeHit();
		}

		// c2 hit c1
		if (c1.hitBox.hasCollidedWith(c2.strikeBox)) {
			c1.takeHit();
		}

	}

	/*
	 * TODO This method should detect if there are any collisions triggered
	 * after updatePositions was called, redraw the characters in their proper
	 * poses, change the healthbar, etc.
	 */
	public void handleCollisions() {

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
			// edge
			// of the screen
			// Also scale the width of this image to 1.3 times the original
			// ratio
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			playerOne.draw(g);
			playerTwo.draw(g);
			healthBarLeft.draw(g);
			healthBarRight.draw(g);
		}
	}

	/**
	 * Listens for the key presses and determine the character's future
	 * positions, which get updated by updatePositions(). The kernel calls this
	 * function.
	 */
	public void respondToInput() {
		final long t1 = System.currentTimeMillis();

		// Must request focus to listen for KeyEvents
		this.requestFocus();

		int left, right;

		/*
		 * Check input for player one.
		 */
		// Maps the proper keys to the direction that should be
		// moved in relative to the current position of the character
		if (playerOne.lookingRight) {
			right = KeyEvent.VK_RIGHT;
			left = KeyEvent.VK_LEFT;
		} else {
			right = KeyEvent.VK_LEFT;
			left = KeyEvent.VK_RIGHT;
		}

		if (isPressed[right]) {
			playerOne.moveForward(playerOne.getMovement());
		} else if (isPressed[left]) {
			playerOne.moveBackward(playerOne.getMovement());
		} else if (isPressed[KeyEvent.VK_UP]) {
			playerOne.jump();
		} else if (isPressed[KeyEvent.VK_DOWN]) {
			playerOne.duck();
		} else if (isPressed[KeyEvent.VK_W]) {
			playerOne.punch();
		} else if (isPressed[KeyEvent.VK_A]) {
			playerOne.kick();
		} else if (isPressed[KeyEvent.VK_B]) {
			playerOne.block();
		} else {
			playerOne.idle();
		}

		/*
		 * Check player two
		 */

		/*
		 * PLAYER TWO will only defend for now..
		 */

		/*
		 * if (isPressed[KeyEvent.VK_UP]) { playerTwo.jump(); } else if
		 * (isPressed[KeyEvent.VK_DOWN]) { playerTwo.duck(); } else if
		 * (isPressed[KeyEvent.VK_W]) { playerTwo.punch(); } else if
		 * (isPressed[KeyEvent.VK_A]) { playerTwo.kick(); }
		 * 
		 * if (isPressed[KeyEvent.VK_B]) { playerTwo.block(); } if
		 * (isPressed[KeyEvent.VK_RIGHT]) {
		 * playerTwo.moveForward(playerOne.getMovement()); } if
		 * (isPressed[KeyEvent.VK_LEFT]) {
		 * playerTwo.moveBackward(playerOne.getMovement()); //
		 * if(isPressed[KeyEvent.VK_D]) // playerOne.setDirection(false); }
		 */

		// If it takes longer than five seconds to register
		// an input as pressed, display it on the console
		// only if debug mode is on

		if (FatalKernel.DEBUG_MODE_ON) {
			final int ABNORMAL_DELAY = 5;
			final long t2 = System.currentTimeMillis();

			if (t2 - t1 > ABNORMAL_DELAY) {
				System.err.println("Time to read input: " + (t2 - t1) + "ms");
			}
		}
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
