package Main;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Animation
{
	int x = 0;
	int y = 0;
	int width = 0;
	int height = 0;
	float rotation = 0;
	int length = 0;
	int currentFrame = 0;
	public boolean isFlipped = false;
	
	// contructor, takes the starting position on image, how wide and tall the sprite is on the image, and how many frames there are to the sprite animation
	Animation(int x, int y, int width, int height, int length)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.length = length;
	}
	
	// update just moves the animation to the next frame to create a feel of animation
	void update()
	{
		this.currentFrame++;
		if(currentFrame >= length)
		{
			currentFrame = 0;
		}
	}
	
	//when an animation is drawn, it requires the orignal image, the position of the world, and how tall and wide you want the sprite to be
	void draw(Graphics2D g2d, ImageLayer image, int x, int y, int width, int height)
	{
		//transforms for making sure that rotating the sprite doesn't mess up other animations
		AffineTransform backup = g2d.getTransform();
		AffineTransform trans = new AffineTransform();
		
		trans.setToRotation(Math.toRadians(rotation), x + width / 2, y + height / 2);
		
		// d is the world position coordinates and s is the source image coordinates
		int dx1 = x;
		int dy1 = y;
		int dx2 = x + width;
		int dy2 = y + height;
		int sx1 = this.x + currentFrame * this.width;
		int sy1 = this.y;
		int sx2 = sx1 + this.width;
		int sy2 = this.y + this.height;
		

		g2d.transform( trans );
		
		//isFlipped if when the user wants the sprite to be flipped horizontally to create a mirror image, 
		//so that if a sprite only moves left, you can mirror it to also move right
		
		if(isFlipped)
		{
			g2d.drawImage(image.image, dx2, dy1, dx1, dy2, sx1, sy1, sx2, sy2, null);
		}
		else
		{
			g2d.drawImage(image.image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		}

		g2d.setTransform( backup );
	}
}
