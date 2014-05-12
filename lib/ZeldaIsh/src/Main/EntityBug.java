package Main;

public class EntityBug extends EntityEnemy
{
	EntityBug(ImageLayer image, ImageLayer weaponImage, int x, int y, int width, int height)
	{
		super(image, weaponImage, x, y, width, height);
		this.downAnimation = new Animation(0, 61, 85, 61, 4);
		this.upAnimation = new Animation(0, 61 * 3, 85, 61, 4);
		this.leftAnimation = new Animation(0, 61 * 4, 85, 61, 4);
		
		this.currentAnimation = downAnimation;
		
		this.speed = 2;
	}
	
	// Bugs move in a random direction when they stop moving
	public void updateAI(EntityPlayer player)
	{
		super.updateAI(player);
		
		int Min = 1;
		int Max = 5;
		int choice = Min + (int)(Math.random() * ((Max - Min) + 1));//every number between 1 and 5 by not 5; 1,2,3,4 generating
		
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
