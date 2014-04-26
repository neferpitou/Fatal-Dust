package views;

import interfaces.FatalView;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
public class VersusView extends JPanel implements FatalView, KeyListener {

	private Image img;
	private int right_img_bounds;
	private final VanillaCharacter playerOne, playerTwo;
	boolean[] isPressed = new boolean[256];
	private HealthBar healthBarLeft = new HealthBar(20, 40, 300, 300, 30,
			"AYAKO");
	private HealthBar healthBarRight = new HealthBar(1000, 40, 300, 300, 30,
			"AYAKO");

	public VersusView(final VanillaCharacter playerOne,
			final VanillaCharacter playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		addKeyListener(this);

		kernel.execute(() -> {
			for (int i = 0; i < isPressed.length; i++) {
				isPressed[i] = false;
			}
		});
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
		// Must request focus to listen for KeyEvents
		requestFocus();

		/*
		 * Check input for player one.
		 */
		
		
		if(isPressed[KeyEvent.VK_UP])          playerOne.jump();
		else if(isPressed[KeyEvent.VK_DOWN])   playerOne.duck();
		else if(isPressed[KeyEvent.VK_W])      playerOne.punch();
		else if(isPressed[KeyEvent.VK_A])      playerOne.kick();
		
		   
		if(playerOne.lookingRight)
		{
			if(isPressed[KeyEvent.VK_B])   playerOne.block(); 
			
			if(!playerOne.hitBox.hasCollidedWith(playerTwo.hitBox))
			{
				if(isPressed[KeyEvent.VK_RIGHT])  playerOne.moveForward(playerOne.getMovement());
			
			}
			
			if(isPressed[KeyEvent.VK_LEFT])      playerOne.moveBackward(playerOne.getMovement());
		
		}
		else
		{
			
			if(isPressed[KeyEvent.VK_B])   playerOne.block();
			
			if(!playerOne.hitBox.hasCollidedWith(playerTwo.hitBox))
			{
				if(isPressed[KeyEvent.VK_LEFT])   playerOne.moveForward(playerOne.getMovement());
				
			}
			//if(isPressed[KeyEvent.VK_D])      ayako.setDirection(true);
			if(isPressed[KeyEvent.VK_RIGHT])      playerOne.moveBackward(playerOne.getMovement());
		}

		
		//Uncomment for unbeatable character.. 
		//if(Math.abs(ayako2.centerX - ayako.centerX) < 100) ayako2.defendAgainst(ayako);
		
		if ( playerOne.getCenterX() > playerTwo.getCenterX())
		{
			if ( !playerOne.isJumping() )
			{
				playerOne.setLookingRight(false);
				playerTwo.setLookingRight(true);
				
			}
			

				
		}
		
		else
		{
			if ( !playerOne.isJumping() )
			{
				playerOne.setLookingRight(true);
				playerTwo.setLookingRight(false);
				
			}
			
			
		}
		
		if(playerOne.strikeBox.hasCollidedWith(playerTwo.hitBox))
		{
			playerTwo.takeHit();
		}
		
		if(playerTwo.getHealth() <= 0)
		{
			playerTwo.die();
		}
		
		
		/*
		 * Check player two
		 */
		
		/*
		 * PLAYER TWO will only defend for now.. 
		 * 
		 */
		
		/*
		if (isPressed[KeyEvent.VK_UP]) {
			playerTwo.jump();
		} else if (isPressed[KeyEvent.VK_DOWN]) {
			playerTwo.duck();
		} else if (isPressed[KeyEvent.VK_W]) {
			playerTwo.punch();
		} else if (isPressed[KeyEvent.VK_A]) {
			playerTwo.kick();
		}

		if (isPressed[KeyEvent.VK_B]) {
			playerTwo.block();
		}
		if (isPressed[KeyEvent.VK_RIGHT]) {
			playerTwo.moveForward(playerOne.getMovement());
		}
		if (isPressed[KeyEvent.VK_LEFT]) {
			playerTwo.moveBackward(playerOne.getMovement());
			// if(isPressed[KeyEvent.VK_D])
			// playerOne.setDirection(false);
		}
	*/
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
