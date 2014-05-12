package Main;

import java.util.ArrayList;

public class EntityPlayer extends Entity
{
	
	float deathTurns = 0.0f;
	int deathRotation = 0;
	boolean finishedDeath = false;
	boolean hasAttacked = false;
	
	EntityPlayer(ImageLayer image, ImageLayer weaponImage, int x, int y, int width, int height)
	{
		super(image, weaponImage, x, y, width, height);
		this.health = 10;
		this.downAnimation = new Animation(0, 0, 32, 32, 3);
		this.upAnimation = new Animation(0, 32 * 3, 32, 32, 3);
		this.leftAnimation = new Animation(0, 32 * 1, 32, 32, 3);
		this.deathTurns = 0.0f;
		this.deathRotation = 0;
		this.finishedDeath = false;
	
		this.currentAnimation = this.downAnimation;
	}
	
	public void takeDamage(int damage)
	{
		super.takeDamage(damage);
		
		System.out.println("Player TOOK DAMAGE: " + damage);
	}
	

	// This is the same as entity update, except when the player is attacking
	// If the player is attacking and hits an enemy, the enemy takes damage and is pushed back if it lives
	
	public void update(ArrayList<Rect> collidables, ArrayList<EntityEnemy> enemies)
	{
		if(this.isAlive)
		{
			super.update(collidables);
			if(this.isAttacking)
			{
				for(int count = 0; count < enemies.size(); count++)
				{
					if(!hasAttacked && this.weaponRect.hasCollidedWith(enemies.get(count).collisionRect))
					{
						hasAttacked = true;
						enemies.get(count).takeDamage(3);
						
						int distX = this.collisionRect.x - enemies.get(count).collisionRect.x;
						int distY = this.collisionRect.y - enemies.get(count).collisionRect.y;
						
						if(Math.abs(distX) < Math.abs(distY))
						{
							if(distY < 0)
							{
								enemies.get(count).pushDown();
							}
							else
							{
								enemies.get(count).pushUp();
							}
						}
						else
						{
							if(distX < 0)
							{
								enemies.get(count).pushRight();
							}
							else
							{
								enemies.get(count).pushLeft();
							}
						}
					}
					else
					{
						hasAttacked = false;
					}
				}
			}
		}
		else
		{
			this.playDeathAnimation();
		}
	}
	
	// death animation, you could have a sprite animation and play that if the artist did one for you
	// in this case, there is no death animation and made my own
	// the player spins in circles a few times and then falls over
	// once the player has fallen, it is officially dead
	public void playDeathAnimation()
	{
		if(deathTurns < 16)
		{
			if(Math.round(deathTurns) % 4 == 0)
			{
				this.lookLeft();
			}
			else if(Math.round(deathTurns) % 3 == 0)
			{
				this.lookDown();
			}
			else if(Math.round(deathTurns) % 2 == 0)
			{
				this.lookRight();
			}
			else if(Math.round(deathTurns) % 1 == 0)
			{
				this.lookUp();
			}
			deathTurns += 0.2f;
		}
		else
		{
			this.lookLeft();
			if(deathRotation < 90)
			{
				this.currentAnimation.rotation = deathRotation;
				deathRotation += 3;
			}
			else
			{
				finishedDeath = true;
			}
		}
	}
}
