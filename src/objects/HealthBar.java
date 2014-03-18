package objects;


import java.awt.Color;
import java.awt.Graphics;

/**
 * Constructs a health bar rectangle. It starts green then becomes gradually more red with each hit. 
 * 
 * @author Dana Smith
 */


public class HealthBar 
{	
	//w is the green health status
	int w = 100;
	int h;
	
	int y;
	int x;
	
	//RGB color 
	Color cRed     = new Color(240, 11, 0);
	Color cGreen   = new Color(0, 255, 0);

	/**HIT TYPES - may need to be changed**/
	int headHit = -10;
	int bodyHit = -5;
	int limbHit = -3;
	
	
	
	public HealthBar(int x, int y, int w, int h)
	{
			this.x = x;
			this.y = y;
			
			this.w = w;
			this.h = h;
			
		}
			
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