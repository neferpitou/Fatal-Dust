package objects;

import java.awt.Graphics;
import interfaces.Character;

public abstract class VanillaCharacter implements Character
{
	private int health;
	boolean isDucking;
	boolean lookingRight;
	boolean isBlocking;
	boolean isHitting;
	
	Rectangle greenHitBox; //Box where character gets hit 
	Rectangle redHitBox; //Box where character hits another 
	Rectangle blueHitBox; //Box for defense; doesn't decrease health if hit
	
	//PositionAnimation[] animations;
	
	

	
	public VanillaCharacter(boolean lookingRight, String imgPrefix)
	{
		this.lookingRight = lookingRight;
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
	
	public abstract void draw(Graphics g);
	
	public void decreaseHealthBy(int dHealth)
	{
		health -= dHealth;
	}
	
	public void lookLeft()
	{
		lookingRight = false;
	}
	
	public void lookRight()
	{
		lookingRight = true;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	

}
