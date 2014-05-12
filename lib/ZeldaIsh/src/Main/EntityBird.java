package Main;

import java.util.ArrayList;

public class EntityBird extends EntityEnemy
{
	int startPositionX;
	boolean isFlyingRight = true;
	
	EntityBird(ImageLayer image, ImageLayer weaponImage, int x, int y, int width, int height)
	{
		super(image, weaponImage, x, y, width, height);
		startPositionX = x;
		this.downAnimation = new Animation(0, 0, 150, 150, 4);
		this.upAnimation = new Animation(0, 0, 150, 150, 4);
		this.leftAnimation = new Animation(0, 0, 150, 150, 4);
		
		this.currentAnimation = downAnimation;
		this.isFlying = true;
		this.speed = 2;
		this.isAlive = true;
	}
	
	public void moveLeft()
	{
		if(!this.isMoving)
		{
			this.isMoving = true;
			displacement = 0;
			xVel = -speed;
			yVel = 0;
			
			leftAnimation.isFlipped = true;
			this.currentAnimation = leftAnimation;
		}
	}
	public void moveRight()
	{
		if(!this.isMoving)
		{
			this.isMoving = true;
			displacement = 0;
			xVel = speed;
			yVel = 0;
			
			leftAnimation.isFlipped = false;
			this.currentAnimation = leftAnimation;
		}
	}
	
	public void updateAI(EntityPlayer player)
	{
		super.updateAI(player);
		// birds only move left and right and that is it
		
		if(isFlyingRight)
		{
			this.moveRight();
		}
		else
		{
			this.moveLeft();
		}

		if(Math.abs(this.collisionRect.x - this.startPositionX) >= 200)
		{
			this.startPositionX = this.collisionRect.x;
			isFlyingRight = !isFlyingRight;
		}
	}
	
	public void update(EntityPlayer player, ArrayList<Rect> collidables)
	{
		this.updateAI(player);
		
		// Birds are a flying object, so they don't follow the same rules as other ground entities.
		// They are able to fly over things that other entities can't walk through
		// These functions are the same as the other moving methods, except this doesn't check for colliding rectangles
		
		if(this.isMoving)
		{
			displacement += Math.abs(xVel) + Math.abs(yVel);
			
			int diff = 0;
			if(displacement > collisionRect.w)
			{
				this.isMoving = false;
				diff = displacement - collisionRect.w;
			}
			
			if(xVel < 0)
			{
				xVel += diff;
			}
			else if(xVel > 0)
			{
				xVel -= diff;
			}
			else if(yVel < 0)
			{
				yVel += diff;
			}
			else if(yVel > 0)
			{
				yVel -= diff;
			}
			
		
			collisionRect.moveX(xVel);
			collisionRect.moveY(yVel);
			
			this.currentAnimation.update();
		}
		else if(isPushed)
		{
			displacement += Math.abs(xVel) + Math.abs(yVel);
			
			int diff = 0;
			if(displacement > collisionRect.w)
			{
				this.isPushed = false;
				diff = displacement - collisionRect.w;
			}
			
			if(xVel < 0)
			{
				xVel += diff;
			}
			else if(xVel > 0)
			{
				xVel -= diff;
			}
			else if(yVel < 0)
			{
				yVel += diff;
			}
			else if(yVel > 0)
			{
				yVel -= diff;
			}
			collisionRect.moveX(xVel);
			collisionRect.moveY(yVel);

		}
		
	}
}
