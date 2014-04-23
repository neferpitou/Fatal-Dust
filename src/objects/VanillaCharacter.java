package objects;

import java.awt.Graphics;

import factory.CharacterType;

public class VanillaCharacter {

	// Direction the character is looking in
	public enum Direction {
		LEFT, RIGHT
	};

	// Positions the character can be in
	public enum Position {
		RTW, LTW, RTJ, LTJ, RTJB, LTJB, RTD, LTD, RTK, LTK, RTKD, LTKD, RTPD, LTPD, RTAP, LTAP
	};

	// All possible positions a character can be in
	final static int RIGHT_WALK 			= 0;
	final static int LEFT_WALK 				= 1;
	final static int RIGHT_JUMP 			= 2;
	final static int LEFT_JUMP 				= 3;
	final static int RIGHT_PUNCH 			= 4;
	final static int LEFT_PUNCH 			= 5;
	final static int RIGHT_JUMP_BACK 		= 6;
	final static int LEFT_JUMP_BACK 		= 7;
	final static int RIGHT_DUCK 			= 8;
	final static int LEFT_DUCK 				= 9;
	final static int RIGHT_KICK 			= 10;
	final static int LEFT_KICK 				= 11;
	final static int RIGHT_KICK_DUCKING 	= 12;
	final static int LEFT_KICK_DUCKING 		= 13;
	final static int RIGHT_PUNCH_DUCKING 	= 14;
	final static int LEFT_PUNCH_DUCKING 	= 15;
	final static int RIGHT_ALT_PUNCH 		= 16;
	final static int LEFT_ALT_PUNCH 		= 17;

	// Characters need health to know how much fight is left in them
	protected int health;

	// Characters need boolean values to know what state it is currently in
	protected boolean isOnLeft, isBlocking, isHitting, isDucking;

	// Characters need hitboxes to know when they've been struck by another
	// player
	protected Rectangle hitBox, strikeBox, guardBox;

	// Characters need to self-identify
	protected CharacterType character;

	// Characters need to be able to find their animations
	private String imgpath;
	
	// Characters should know their location on screen
	private int x, y;

	/**
	 * Defines and instantiates all attributes characters need to have
	 * 
	 * @param playerOne
	 *            if true, player faces right. if false, player faces left
	 */
	public VanillaCharacter(boolean playerOne, CharacterType character, final int x, final int y) {
		isOnLeft = (playerOne) ? false : true;
		isDucking = false;
		isBlocking = false;
		isHitting = false;
		this.character = character;
		this.x = x;
		this.y = y;
	}

	public void punch() {
		// Get the location of punch animation on disk
		imgpath = character + "/punch";

		// Get direction character is facing
		if (isOnLeft) {
			// TODO: Load left punching animation using load()
			// method in kernel
		} else {
			// TODO: Load right punching animation using load()
			// method in kernel
		}

		// TODO: Display punching animation
	}

	public void kick() {
		// Get the location of images on disk
		imgpath = character + "/kick";

		// Get direction character is facing
		if (isOnLeft) {
			// TODO: Load left animation using load()
			// method in kernel
		} else {
			// TODO: Load right animation using load()
			// method in kernel
		}

		// TODO: Display animation
	}

	public void jump() {
		// Get the location of images on disk
		imgpath = character + "/jump";

		// Get direction character is facing
		if (isOnLeft) {
			// TODO: Load left animation using load()
			// method in kernel
		} else {
			// TODO: Load right animation using load()
			// method in kernel
		}

		// TODO: Display animation
	}

	public void duck() {
		// Get the location of images on disk
		imgpath = character + "/duck";

		// Get direction character is facing
		if (isOnLeft) {
			// TODO: Load left animation using load()
			// method in kernel
		} else {
			// TODO: Load right animation using load()
			// method in kernel
		}

		// TODO: Display animation
	}

	public void die() {
		// Get the location of images on disk
		imgpath = character + "/die";

		// Get direction character is facing
		if (isOnLeft) {
			// TODO: Load left animation using load()
			// method in kernel
		} else {
			// TODO: Load right animation using load()
			// method in kernel
		}

		// TODO: Display animation
	}

	public void walk() {
		// Get the location of images on disk
		imgpath = character + "/walk";

		// Get direction character is facing
		if (isOnLeft) {
			// TODO: Load left animation using load()
			// method in kernel
		} else {
			// TODO: Load right animation using load()
			// method in kernel
		}

		// TODO: Display animation
	}

	public void idle() {
		// Get the location of images on disk
		imgpath = character + "/idle";

		// Get direction character is facing
		if (isOnLeft) {
			// TODO: Load left animation using load()
			// method in kernel
		} else {
			// TODO: Load right animation using load()
			// method in kernel
		}

		// TODO: Display animation
	}

	public void block() {
		// Get the location of images on disk
		imgpath = character + "/block";

		// Get direction character is facing
		if (isOnLeft) {
			// TODO: Load left animation using load()
			// method in kernel
		} else {
			// TODO: Load right animation using load()
			// method in kernel
		}

		// TODO: Display animation
	}

	public void victory() {
		// Get the location of images on disk
		imgpath = character + "/victory";

		// Get direction character is facing
		if (isOnLeft) {
			// TODO: Load left animation using load()
			// method in kernel
		} else {
			// TODO: Load right animation using load()
			// method in kernel
		}

		// TODO: Display animation
	}

	public void takeHit() {
		// Get the location of images on disk
		imgpath = character + "/takeHit";

		// Get direction character is facing
		if (isOnLeft) {
			// TODO: Load left animation using load()
			// method in kernel
		} else {
			// TODO: Load right animation using load()
			// method in kernel
		}

		// TODO: Display animation
	}

	// Draw the character on the screen
	public void draw(Graphics g){
		
	};

	/**
	 * Decreases health when hit
	 * 
	 * @param dHealth
	 *            amount to decrease health by
	 */
	public void decreaseHealthBy(int dHealth) {
		health -= dHealth;
	}

	/**
	 * Tells the character to face left
	 */
	public void lookLeft() {
		isOnLeft = true;
	}

	/**
	 * Tells the character to face right
	 */
	public void lookRight() {
		isOnLeft = false;
	}

	/**
	 * Returns the character's current health
	 */
	public int getHealth() {
		return health;
	}
}
