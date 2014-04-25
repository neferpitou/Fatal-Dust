package characters;


import java.awt.Color;
import java.awt.Graphics;

/**
 * Constructs a health bar rectangle. It starts green then becomes gradually more red with each hit. 
 * 
 * @author Dana Smith
 */


public class HealthBar 
{	
    // TODO Assign x, y coordinates for both player's health bars
    // and width and height of the health bar
    
	//w is the green health status
	private int w = 100;
	private int h;
	
	private int y;
	private int x;
	
	//RGB color 
	private final Color cRed     = new Color(240, 11, 0);
	private final Color cGreen   = new Color(0, 255, 0);

	/*HIT TYPES - may need to be changed*/
	private int headHit = -10;
	private int bodyHit = -5;
	private int limbHit = -3;
	
	
	// TODO Create one constructor that takes a boolean argument. True should mean that 
	// the health bar belongs to player one and should be on the left side of the screen.
	// False means that it belongs to player two and should be on the right side of the
	// screen. Initialize x and y here. 
	public HealthBar(int x, int y, int w, int h)
	{
			this.x = x;
			this.y = y;
			
			this.w = w;
			this.h = h;
			
		}
		
		// TODO Take one graphics object and update the health based off of both character's
		// current condition.
		public void draw(Graphics gRed, Graphics gGreen)
		{
			//draw red and green rectangles
			gRed.drawRect(x, y, w, h);
			gRed.setColor(cRed);	
			gRed.fillRect(x, y, w, h);
			
			gGreen.drawRect(x, y, w, h);
			gGreen.setColor(cGreen);	
			gGreen.fillRect(x, y, w, h);
			
		}
		
		public void diminishBy(int hitType, int w)
		{
			int currentHealth = w + hitType; 
			
			if(currentHealth > 1)
			{
				w += hitType;
	
			}
			else if (currentHealth <= 0)
			{
				w = 0;
				 
				//player dies. 
				//Code must be written for this.
			}
			
			
		}
}