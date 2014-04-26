package characters;

import java.awt.Color;
import java.awt.Graphics;

import factory.CharacterType;

public abstract class VanillaCharacter {

	int centerX;
	int centerY;

	public boolean lookingRight;
	boolean isDead;
	boolean perfection;

	final int FLOOR_Y = 600;
	final int GRAVITY = 2;

	SpriteAnimated a; 
	SpriteAnimated a_lt;
	SpriteAnimated a_rt;

	// Characters need health to know how much fight is left in them
	protected int health;

	// Characters need boolean values to know what state it is currently in
	protected boolean isOnLeft, isBlocking, isHitting, isDucking, isJumping, isKicking, isPunching, isWalking;

	// Characters need hitboxes to know when they've been struck by another
	// player
	public Rectangle hitBox, strikeBox, guardBox;

	// Characters need to self-identify
	protected CharacterType character;

	// Characters need to be able to find their animations
	private String imgpath;

	// Characters should know their location on screen
	protected int x;
	protected int y;

	final int characterHeight = 300;
	final int characterWidth = 150;

	final int HEIGHT_Y = FLOOR_Y - characterWidth;
	int vy;
	final int INIT_JUMP_V = 40;

	final int LEFT_PLAYER_X  = 400;
	final int RIGHT_PLAYER_X = 800;
	final int MOVEMENT = 13;

	/**
	 * Defines and instantiates all attributes characters need to have
	 * 
	 * @param playerOne
	 *            if true, player faces right. if false, player faces left
	 */
	public VanillaCharacter(boolean playerOne, CharacterType character) {
		if (playerOne){
			isOnLeft = false;
			setCenterX(LEFT_PLAYER_X);
		} else {
			isOnLeft = true;
			setCenterX(RIGHT_PLAYER_X);
		}

		isOnLeft = (playerOne) ? false : true;
		isDucking = false;
		isBlocking = false;
		isHitting = false;
		isPunching = false; 
		isJumping = false;
		isKicking = false;
		perfection = true;

		this.setCenterX(centerX);
		this.character = character;
		x = getCenterX() - (characterWidth/2);
		y = HEIGHT_Y; 


		//TODO: fix these numbers


		if(lookingRight)
		{
			hitBox   = new Rectangle( centerX - 45, y+50, 60, 150, Color.GREEN);
			strikeBox   = new Rectangle( 0, 0 , 0, 0, Color.RED );
			guardBox  = new Rectangle( centerX - 45, y+50, 20, 60, Color.BLUE );

		}

		else
		{
			hitBox  = new Rectangle( centerX - 15, y+50, 60, 150, Color.GREEN);
			strikeBox   = new Rectangle( 0, 0, 0, 0, Color.RED );
			guardBox   = new Rectangle( centerX + 25, y+50, 40, 60, Color.BLUE );
		}
	}

	public abstract void punch();
	public abstract void kick();
	public abstract void jump();
	public abstract void block();
	public abstract void die();
	public abstract void victory();
	public abstract void takeHit();
	public abstract void moveForward(int dx);
	public abstract void moveBackward(int dx);
	public abstract void fightAgainst(VanillaCharacter other); //For future AI implementions

	public void resetDefaults()
	{

		if(!isPunching && !isKicking)
		{
			strikeBox.setWidth(0);
			strikeBox.setHeight(0);
		}

		if(!isBlocking) 
		{
			guardBox.setWidth(0);
			guardBox.setHeight(0);
		}

		hitBox.setYPosition(a.y + 50);

		if(!isDucking) hitBox.setWidth(60);


		isBlocking = false;
		isDucking = false;
		isWalking = false;
		isPunching = false;
		isKicking = false;


		a.setWidth(characterWidth);
		a.setHeight(characterHeight);

		if(!isJumping ) a.setYPosition(HEIGHT_Y);

		a.setPos(0);
	}

	// Draw the character on the screen
	public abstract void draw(Graphics g);

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
	 * Returns the character's current health
	 */
	public int getHealth() {
		return health;
	}

	public abstract void duck();

	public boolean isLookingRight() {
		return lookingRight;
	}

	public void setLookingRight( boolean lookingRight )
	{
		this.lookingRight = lookingRight;


		if(lookingRight)
		{
			a = a_rt;
			hitBox.setXPosition(centerX - 45 );
		}

		else
		{
			a = a_lt;
			hitBox.setXPosition(centerX - 15 );
		}

		//invertHitBoxes(greenHitBox);

	}


	public int getMovement(){
		return MOVEMENT;
	}

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}
}
