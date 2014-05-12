package Main;

import java.awt.*;

public class Rect {
	
	public int x;
	public int y;
	public int w;
	public int h;
	public float rotation = 0;
	public ImageLayer image = null;

	public Rect(int x, int y,int w, int h, ImageLayer image)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.image = image;
	}

	public void moveY(int dy)
	{
		y += dy;
	}

	public void moveX(int dx)
	{
		x += dx;
	}
	
	//Detects Collision
	public boolean hasCollidedWith(Rect r)
	{
		return
		(x + w > r.x) && 
		(r.x + r.w > x) &&
		(r.y + r.h > y) &&
		(y + h > r.y);	
	}
	
	// Detects collision with entity
	public boolean hasCollidedWith(Entity e)
	{

		return
		(x + w > e.collisionRect.x) && 
		(e.collisionRect.x + e.collisionRect.w > x) &&
		(e.collisionRect.y + e.collisionRect.h > y) &&
		(y + h > e.collisionRect.y);	
	}
	
	
	public boolean contains(int mx, int my)
	{
		return ( (my < y +h) && (my > y) && (mx > x) && (mx < x + w) );
	}
	
	public void draw(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		
		if(this.image == null)
		{
			g2d.drawRect(x, y, w, h);
		}
		else
		{
			image.x = this.x;
			image.y = this.y;
			image.width = this.w;
			image.height = this.h;
			
			image.draw(g2d);
		}
	}

}
