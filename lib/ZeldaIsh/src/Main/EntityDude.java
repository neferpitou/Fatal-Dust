package Main;

public class EntityDude extends EntityEnemy
{

	EntityDude(ImageLayer image, ImageLayer weaponImage, int x, int y, int width, int height)
	{
		super(image, weaponImage, x, y, width, height);
		this.downAnimation = new Animation(0, 0, 32, 32, 3);
		this.upAnimation = new Animation(0, 32 * 3, 32, 32, 3);
		this.leftAnimation = new Animation(0, 32 * 1, 32, 32, 3);
		
		this.currentAnimation = downAnimation;
		
		this.speed = 2;
	}
	
	// Dudes sit at their spot until the player comes within Agro distance
	public void updateAI(EntityPlayer player)
	{
		super.updateAI(player);
		if(150 >= Math.sqrt(Math.pow(this.collisionRect.x - player.collisionRect.x, 2) + Math.pow(this.collisionRect.y - player.collisionRect.y, 2)))
		{	
			int distX = this.collisionRect.x - player.collisionRect.x;
			int distY = this.collisionRect.y - player.collisionRect.y;
			
			if(Math.abs(distX) < Math.abs(distY))
			{
				if(distY < 0)
				{
					this.moveDown();
				}
				else
				{
					this.moveUp();
				}
			}
			else
			{
				if(distX < 0)
				{
					this.moveRight();
				}
				else
				{
					this.moveLeft();
				}
			}
		}
	}

}
