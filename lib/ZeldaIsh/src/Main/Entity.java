package Main;


import java.awt.*;
import java.util.ArrayList;

public class Entity
{
	// Class variables
	
	//rectangles for sprite and weapon
	Rect collisionRect;
	Rect weaponRect;
	
	//Variables for movement
	boolean isMoving;
	boolean isPushed;
	boolean isColliding;
	boolean isAttacking = false;
	int xVel = 0;
	int yVel = 0;
	int futureX = 0;
	int futureY = 0;
	int xDisplacement = 0;
	int yDisplacement = 0;
	int displacement = 0;
	int speed = 3;
	int currentDirection = -1;
	
	//Variables for entity personal stuff
	int health = 10;
	public boolean isAlive = true;
	boolean reachedEndOfLevel = false;
	
	//Universalizes direction
	public enum Direction
	{
		Up,
		Down,
		Left,
		Right
	};
	
	Animation downAnimation;
	Animation upAnimation;
	Animation leftAnimation;
	
	Animation currentAnimation;
	
	Entity(ImageLayer image, ImageLayer weaponImage, int x, int y, int width, int height)
	{
		// Creates the rectangles and animations for the entity4
		this.collisionRect = new Rect(x, y, width, height, image);
		
		this.weaponRect = new Rect(x, y, width, height, weaponImage);
		
		this.downAnimation = new Animation(0, 0, 32, 32, 3);
		this.upAnimation = new Animation(0, 32 * 3, 32, 32, 3);
		this.leftAnimation = new Animation(0, 32 * 1, 32, 32, 3);
		
		this.currentAnimation = downAnimation;
		this.currentDirection = Direction.Down.ordinal();
		this.isPushed = false;
	}
	
	//Look methods make the character look in a direction
	public void lookLeft()
	{
		this.currentDirection = Direction.Left.ordinal();
		leftAnimation.isFlipped = false;
		this.currentAnimation = leftAnimation;
	}
	
	public void lookRight()
	{
		this.currentDirection = Direction.Right.ordinal();
		leftAnimation.isFlipped = true;
		this.currentAnimation = leftAnimation;
	}
	
	public void lookUp()
	{
		this.currentDirection = Direction.Up.ordinal();
		this.currentAnimation = upAnimation;
	}
	
	public void lookDown()
	{
		this.currentDirection = Direction.Down.ordinal();
		this.currentAnimation = downAnimation;
	}
	
	//Move methods move a character in a direction
	public void moveLeft()
	{
		if(!this.isMoving)
		{
			this.isMoving = true;
			xDisplacement = 0;
			yDisplacement = 0;
			xVel = -speed;
			yVel = 0;
			
			this.currentDirection = Direction.Left.ordinal();
			leftAnimation.isFlipped = false;
			this.currentAnimation = leftAnimation;
			futureX = this.collisionRect.x - this.collisionRect.w;
			futureX = this.collisionRect.w * (Math.round(futureX / this.collisionRect.w));
		}
	}
	public void moveRight()
	{
		if(!this.isMoving)
		{
			this.isMoving = true;
			xDisplacement = 0;
			yDisplacement = 0;
			xVel = speed;
			yVel = 0;
			
			this.currentDirection = Direction.Right.ordinal();
			leftAnimation.isFlipped = true;
			this.currentAnimation = leftAnimation;
			
			futureX = this.collisionRect.x + this.collisionRect.w;
			futureX = this.collisionRect.w * (Math.round(futureX / this.collisionRect.w));
		}
	}
	public void moveUp()
	{
		if(!this.isMoving)
		{
			this.isMoving = true;
			xDisplacement = 0;
			yDisplacement = 0;
			xVel = 0;
			yVel = -speed;
			
			this.currentDirection = Direction.Up.ordinal();
			this.currentAnimation = upAnimation;
			
			futureY = this.collisionRect.y - this.collisionRect.h;
			futureY = this.collisionRect.h * (Math.round(futureY / this.collisionRect.h));
		}
	}
	public void moveDown()
	{
		if(!this.isMoving)
		{
			this.isMoving = true;
			xDisplacement = 0;
			yDisplacement = 0;
			xVel = 0;
			yVel = speed;
			
			this.currentDirection = Direction.Down.ordinal();
			this.currentAnimation = downAnimation;
			
			futureY = this.collisionRect.y + this.collisionRect.h;
			futureY = this.collisionRect.h * (Math.round(futureY / this.collisionRect.h));
		}
	}
	
	public void moveUpLeft()
	{
		if(!this.isMoving)
		{
			this.isMoving = true;
			xDisplacement = 0;
			yDisplacement = 0;
			xVel = -speed;
			yVel = -speed;
			
			this.currentDirection = Direction.Up.ordinal();
			this.currentAnimation = upAnimation;
			
			futureX = this.collisionRect.x - this.collisionRect.w;
			futureX = this.collisionRect.w * (Math.round(futureX / this.collisionRect.w));
			
			futureY = this.collisionRect.y - this.collisionRect.h;
			futureY = this.collisionRect.h * (Math.round(futureY / this.collisionRect.h));
		}
	}
	
	public void moveDownLeft()
	{
		if(!this.isMoving)
		{
			this.isMoving = true;
			xDisplacement = 0;
			yDisplacement = 0;
			xVel = -speed;
			yVel = +speed;
			
			this.currentDirection = Direction.Up.ordinal();
			this.currentAnimation = upAnimation;
			
			futureX = this.collisionRect.x - this.collisionRect.w;
			futureX = this.collisionRect.w * (Math.round(futureX / this.collisionRect.w));
			
			futureY = this.collisionRect.y + this.collisionRect.h;
			futureY = this.collisionRect.h * (Math.round(futureY / this.collisionRect.h));
		}
	}
	
	public void moveDownRight()
	{
		if(!this.isMoving)
		{
			this.isMoving = true;
			xDisplacement = 0;
			yDisplacement = 0;
			xVel = +speed;
			yVel = +speed;
			
			this.currentDirection = Direction.Up.ordinal();
			this.currentAnimation = upAnimation;
			
			futureX = this.collisionRect.x + this.collisionRect.w;
			futureX = this.collisionRect.w * (Math.round(futureX / this.collisionRect.w));
			
			futureY = this.collisionRect.y + this.collisionRect.h;
			futureY = this.collisionRect.h * (Math.round(futureY / this.collisionRect.h));
		}
	}
	
	public void moveUpRight()
	{
		if(!this.isMoving)
		{
			this.isMoving = true;
			xDisplacement = 0;
			yDisplacement = 0;
			xVel = +speed;
			yVel = -speed;
			
			this.currentDirection = Direction.Up.ordinal();
			this.currentAnimation = upAnimation;
			
			futureX = this.collisionRect.x + this.collisionRect.w;
			futureX = this.collisionRect.w * (Math.round(futureX / this.collisionRect.w));
			
			futureY = this.collisionRect.y - this.collisionRect.h;
			futureY = this.collisionRect.h * (Math.round(futureY / this.collisionRect.h));
		}
	}
	
	//Push methods push a character in a direction
	public void pushUp()
	{

		this.isMoving = false;
		this.isPushed = true;
		
		xDisplacement = 0;
		yDisplacement = 0;
		xVel = 0;
		yVel = -speed * 2;
	}
	
	public void pushDown()
	{

		this.isMoving = false;
		this.isPushed = true;
		
		xDisplacement = 0;
		yDisplacement = 0;
		xVel = 0;
		yVel = speed * 2;
	}
	
	public void pushRight()
	{

		this.isMoving = false;
		this.isPushed = true;
		
		xDisplacement = 0;
		yDisplacement = 0;
		xVel = speed * 2;
		yVel = 0;
	}
	
	public void pushLeft()
	{

		this.isMoving = false;
		this.isPushed = true;
		
		xDisplacement = 0;
		yDisplacement = 0;
		xVel = -speed * 2;
		yVel = 0;
	}
	
	//Revert move is here in case the character moves somewhere they are not suppose to be and moves them back a square
	public void revertMove()
	{
		this.isMoving = false;
		
		if(xVel < 0)
		{
			xVel = xDisplacement;
		}
		else if(xVel > 0)
		{
			xVel = -xDisplacement;
		}
		
		if(yVel < 0)
		{
			yVel = yDisplacement;
		}
		else if(yVel > 0)
		{
			yVel = -yDisplacement;
		}
		
		collisionRect.moveX(xVel);
		collisionRect.moveY(yVel);
	}
	
	//entity takes damage according to damage given and removes it from it's health
	//if the entity has no health, it is dead
	public void takeDamage(int damage)
	{
		this.health -= damage;
		
		if(health <= 0)
		{
			this.isAlive = false;
		}
	}
	
	//Attack draws out a sword and places it in front of the entity and rotates it according to direction
	public void attack()
	{
		if(!this.isMoving)
		{
			this.isAttacking = true;
			
			if(this.currentDirection == Direction.Up.ordinal())
			{
				weaponRect.x = collisionRect.x;
				weaponRect.y = collisionRect.y - weaponRect.h;
				weaponRect.image.rotation = 0;
			}
			else if(this.currentDirection == Direction.Down.ordinal())
			{
				weaponRect.x = collisionRect.x;
				weaponRect.y = collisionRect.y + weaponRect.h;
				weaponRect.image.rotation = 180;
			}
			else if(this.currentDirection == Direction.Left.ordinal())
			{
				weaponRect.x = collisionRect.x - weaponRect.w;
				weaponRect.y = collisionRect.y;
				weaponRect.image.rotation = 270;
			}
			else if(this.currentDirection == Direction.Right.ordinal())
			{
				weaponRect.x = collisionRect.x + weaponRect.w;
				weaponRect.y = collisionRect.y;
				weaponRect.image.rotation = 90;
			}
			
		}
	}
	
	int weaponTime = 0;
	public void update(ArrayList<Rect> collidables)
	{
		//limits how long the sword is displayed
		if(this.isAttacking)
		{
			
			weaponTime++;
			if(weaponTime > 7)
			{
				this.isAttacking = false;
				weaponTime = 0;
			}
			else
			{
				this.isMoving = false;
			}
		}
		
		//movement is handled here to make sure the character doesn't walk through walls and to make sure they snap into the right gride place
		if(this.isMoving)
		{
			xDisplacement += Math.abs(xVel);
			yDisplacement += Math.abs(yVel);

			if(xVel < 0 && this.collisionRect.x + xVel < this.futureX)
			{
				this.isMoving = false;
				xVel = 0;
				this.collisionRect.x = futureX;
			}
			else if(xVel > 0 && this.collisionRect.x + xVel > this.futureX)
			{
				this.isMoving = false;
				xVel = 0;
				this.collisionRect.x = futureX;
			}
			
			if(yVel < 0 && this.collisionRect.y + yVel < this.futureY)
			{
				this.isMoving = false;
				yVel = 0;
				this.collisionRect.y = futureY;
			}
			else if(yVel > 0 && this.collisionRect.y + yVel > this.futureY)
			{
				this.isMoving = false;
				yVel = 0;
				this.collisionRect.y = futureY;
			}
			
			collisionRect.moveX(xVel);
			collisionRect.moveY(yVel);
			
			isColliding = false;
			for(int count = 0; count < collidables.size(); count++)
			{
				if (collidables.get(count).hasCollidedWith(this))
				{
					isColliding = true;
					break;
				}
			}
			
			if(isColliding)
			{
				this.revertMove();
			}
			
			if(currentAnimation != null)
			{
				this.currentAnimation.update();
			}
		}
		else if(isPushed) // being pushed is essentially the same as movement, except it is done with a faster speed
		{
			xDisplacement += Math.abs(xVel);
			yDisplacement += Math.abs(yVel);
			
			int diff = 0;
			if(xDisplacement + yDisplacement > collisionRect.w)
			{
				this.isPushed = false;
				diff = xDisplacement + yDisplacement - collisionRect.w;
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
			
			isColliding = false;
			for(int count = 0; count < collidables.size(); count++)
			{
				if (collidables.get(count).hasCollidedWith(this))
				{
					isColliding = true;
					break;
				}
			}
			
			if(isColliding)
			{
				this.revertMove();
			}

		}
		
		if(currentAnimation == null)
		{
			currentAnimation = downAnimation;
		}
	}
	
	//draws the current animation, if there wasn't an animation or image given, then a rect is drawn to show that you didn't program something right
	void draw(Graphics2D g2d)
	{
		if(currentAnimation != null)
		{
			currentAnimation.draw(g2d, collisionRect.image, collisionRect.x, collisionRect.y, collisionRect.w, collisionRect.h);
		}
		else
		{
			collisionRect.draw(g2d);
		}
		
		// If entity is attacking, draw a weapon that is given to entity
		if(this.isAttacking)
		{
			weaponRect.draw(g2d);
		}
	}
}
