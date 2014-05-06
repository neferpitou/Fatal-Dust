package characters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

//to make character have health, in main class, just make a character object and healthbar object. 
//In main class, say character.health = healthbar.health



/**
 * Constructs a health bar rectangle. It starts green then becomes gradually more red with each hit. 
 * 
 * @author Dana Smith
 */


public class HealthBar 
{	
    // TODO Assign x, y coordinates for both player's health bars
    // and width and height of the health bar
    
	//healthBarWGreen is the green health status.
	//healthBarWRed is the red health status. It never changes.
	private int healthBarWGreen;
	private int healthBarWRed;
	private int h;
	
	private int y;
	private int x;

//	private int playerNamex;
//	private int playerNamey;
	
	int currentHealth;
	
	/*HIT TYPES - may need to be changed*/
	private int headHit = -10;
	private int c = -5;
	private int limbHit = -3;
	
	private String playerName;
	
	//Image playerFace;
	
	
	// TODO Create one constructor that takes a boolean argument. True should mean that 
	// the health bar belongs to player one and should be on the left side of the screen.
	// False means that it belongs to player two and should be on the right side of the
	// screen. Initialize x and y here. 
	public HealthBar(int x, int y,int healthBarWGreen,  int healthBarWRed, int h, String playerName)
	{
			this.x = x;
			this.y = y;
			
			this.healthBarWRed = healthBarWRed;
			this.healthBarWGreen = healthBarWGreen;
			this.h = h;
			
			this.playerName = playerName;
			
			this.currentHealth = healthBarWGreen;  
			
		}
		
		// TODO Take one graphics object and update the health based off of both character's
		// current condition.
		public void draw(Graphics g)
		{
			//grey box behind player name
			g.setColor(Color.white);
			g.fillRoundRect(x-5, y/2, healthBarWRed + 60, h*2, 20, 20);
			
			
			//Player Name
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.PLAIN, 16));
			g.drawString(playerName, x, y-5);
			
			
			//health bar draw green on top of red rectangle
			g.setColor(Color.red);	
			g.fillRoundRect(x, y, healthBarWRed, h, 20, 20);
			g.setColor(Color.green);	
			g.fillRoundRect(x, y, healthBarWGreen, h, 20, 20);
			
			if (healthBarWGreen == 0 )
			{
				//Player dies
			}
			
		}
		
		public void diminishBy(int hitType)
		{
			currentHealth = healthBarWGreen + hitType; 
		
			if(currentHealth > 1)
			{
				healthBarWGreen += hitType;
	
			}
			else if (currentHealth <= 0)
			{
				healthBarWGreen = 0;
								 
				//player dies. Load character die animation  
				//Code must be written for this.
			}
			
			
		}
}