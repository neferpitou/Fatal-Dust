package Main;

public class EntityBall extends EntityEnemy
{

	EntityBall(ImageLayer image, ImageLayer weaponImage, int x, int y, int width, int height)
	{
		super(image, weaponImage, x, y, width, height);
		
		this.downAnimation = new Animation(0, 0, 190, 188, 1);
		this.upAnimation = new Animation(0, 0, 190, 188, 1);
		this.leftAnimation = new Animation(0, 0, 190, 188, 1);
		
		this.currentAnimation = downAnimation;
	}

	float xMove = 0;
	float moveRate = 0.1f;
	public void updateAI(EntityPlayer player)
	{
		super.updateAI(player);
		
		// moves the ball in a circular motion using cos and sin
		this.collisionRect.x = this.collisionRect.x + (int) (10 * Math.cos(xMove));
		this.collisionRect.y = this.collisionRect.y + (int) (10 * Math.sin(xMove));
		
		xMove += moveRate;
	}
}
