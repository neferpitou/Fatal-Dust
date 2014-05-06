package characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import factory.CharacterType;

public abstract class VanillaCharacter 
{
	int centerX;
	int centerY;

	public boolean lookingRight;
	boolean isDead;
	boolean perfection;

	final int FLOOR_Y = 600;
	final int GRAVITY = 2;

	public SpriteAnimated a; 
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
	final int characterWidth = 200;

	final int HEIGHT_Y = FLOOR_Y - characterWidth;
	int vy;
	final int INIT_JUMP_V = 30;

	final int LEFT_PLAYER_X  = 400;
	final int RIGHT_PLAYER_X = 800;
	final int MOVEMENT = 13;
	
	//set health = 300 because of how code is written. Will eventually change it to 100.
	final int HEALTH_INIT = 300;
	
	
	//Defines player positions
	
	//String[] a_position = {"idle", "duck", "punch", "walking", "block", "kick", "die", "takeHit"};
	
	protected final int IDLE = 0;
	protected final int DUCK = 1;
	protected final int PUNCH = 2;
	protected final int WALKING = 3;
	protected final int BLOCK = 4;
	protected final int KICK = 5;
	protected final int DIE = 6;
	protected final int TAKE_HIT = 7;
	protected final int JUMP = 8;

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
		
		health = HEALTH_INIT;

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
	public void jump()
	{
		if (!isJumping() && !isDucking) {
			setJumping(true);

			// Initial Jump Velocity
			vy = INIT_JUMP_V;

			a.setCurrentAnimation(JUMP);
			// greenHitBox.setYPosition( JUMP_Y );
		}
	}
	
	public void takeHit()
	{
		isJumping = true;
		moveBackward(10);
		vy = 6 ;
		a.setCurrentAnimation(TAKE_HIT);
		if(!isBlocking) decreaseHealthBy(1);
		perfection  = false;
		//need to add code to finish the animation. Because now, when player gets hit, it keeps cycling through the array and doesnt stop the hit animation.
	}
	
	public abstract void block();
	public abstract void die();
	public abstract void victory();

	public abstract void moveForward(int dx);
	public abstract void moveBackward(int dx);
	public abstract void fightAgainst(VanillaCharacter other); //For future AI implementions

	public void idle()
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
		
		a.setCurrentAnimation(IDLE);
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
