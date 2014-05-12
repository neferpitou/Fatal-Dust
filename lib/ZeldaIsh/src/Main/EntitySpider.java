package Main;

public class EntitySpider extends EntityEnemy
{

	EntitySpider(ImageLayer image, ImageLayer weaponImage, int x, int y, int width, int height)
	{
		super(image, weaponImage, x, y, width, height);
		this.downAnimation = new Animation(0, 0, 35, 35, 5);
		this.upAnimation = new Animation(0, 35 * 2, 35, 35, 5);
		this.leftAnimation = new Animation(0, 35 * 1, 35, 35, 5);
		
		this.currentAnimation = downAnimation;
		
		this.speed = 2;
	}
	
	// Spiders will start to follow you and attack you, if you enter it's Agro distance
	// If you are too far away, then it will just move randomly like  a bug
	
	public void updateAI(EntityPlayer player)
	{
		super.updateAI(player);
		if(100 >= Math.sqrt(Math.pow(this.collisionRect.x - player.collisionRect.x, 2) + Math.pow(this.collisionRect.y - player.collisionRect.y, 2)))
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
		else
		{
			int Min = 1;
			int Max = 5;
			int choice = Min + (int)(Math.random() * ((Max - Min) + 1));
			
			if(choice == 1)
			{
				this.moveDown();
			}
			else if(choice == 2)
			{
				this.moveUp();
			}
			else if(choice == 3)
			{
				this.moveLeft();
			}
			else if(choice == 4)
			{
				this.moveRight();
			}
		}
	}
}
