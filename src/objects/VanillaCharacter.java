package objects;

import java.awt.Graphics;
import interfaces.Character;

@Deprecated
public abstract class VanillaCharacter implements Character {
	private int health;
	private boolean isOnLeft, isOnRight, isBlocking, isHitting, isDucking;
	
	// Green is box where character gets hit
	// Red is box where character hits another
	// Blue is only when character is guarding
	private Rectangle greenHitBox, redHitBox, blueHitBox;
	
	//PositionAnimation[] animations;
	
	// TODO define imgPrefix
	public VanillaCharacter(boolean lookingRight, String imgPrefix) {
		this.isOnRight = lookingRight;
		isDucking = false;
		isBlocking = false;
		isHitting = false; 
		
		/*animations = new PositionAnimation[POSITIONS.length];
		
		for(int i = 0 ; i < animations.length ; i++)
		{
			//the third parameter (in this case 5) will depend on how many frames we have for each position image
			animations[i] = new PositionAnimation(imgPrefix, POSITIONS[i], 5);	
		}*/
		
	}

	
	public abstract void punch();
	
	public abstract void kick();
	
	public abstract void jump();
	
	public abstract void duck();
	
	public abstract void die();
	
	public abstract void walk();
	
	public abstract void idle();
	
	public abstract void block();
	
	public abstract void victory();
	
	public abstract void takeHit();
	
	public abstract void draw(Graphics g);
	
	public void decreaseHealthBy(int dHealth)
	{
		health -= dHealth;
	}
	
	public void lookLeft()
	{
		isOnRight = false;
	}
	
	public void lookRight()
	{
		isOnRight = true;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	

}
