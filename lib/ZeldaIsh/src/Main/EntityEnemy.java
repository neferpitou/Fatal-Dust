package Main;

import java.util.ArrayList;

public class EntityEnemy extends Entity
{
	boolean collidedWithPlayer = false;
	boolean isFlying = false;
	
	EntityEnemy(ImageLayer image, ImageLayer weaponImage, int x, int y, int width, int height)
	{
		super(image, weaponImage, x, y, width, height);
	}

	// if a player is touched by an enemy, the player takes damage and is pushed back
	public void updateAI(EntityPlayer player)
	{
		if(!collidedWithPlayer && this.collisionRect.hasCollidedWith(player.collisionRect))//if player collides with enemy
		{	//plays takes damage
			collidedWithPlayer = true;
			player.takeDamage(1);
			
			int distX = this.collisionRect.x - player.collisionRect.x;//directing to where you push them to
			int distY = this.collisionRect.y - player.collisionRect.y;
			
			if(Math.abs(distX) < Math.abs(distY))
			{
				if(distY < 0)
				{
					player.pushDown();
				}
				else
				{
					player.pushUp();
				}
			}
			else
			{
				if(distX < 0)
				{
					player.pushRight();
				}
				else
				{
					player.pushLeft();
				}
			}
		}
		else
		{
			collidedWithPlayer = false;
		}
	}
	
	public void update(EntityPlayer player, ArrayList<Rect> collidables)//always being called
	{
		updateAI(player);
		
		super.update(collidables);
	}
}
