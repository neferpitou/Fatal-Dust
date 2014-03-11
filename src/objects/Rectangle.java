package objects;

import java.awt.Graphics;

public class Rectangle 
{

	int x;
	int y;
	
	int w;
	int h;
	
	public Rectangle(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		
		this.w = w;
		this.h = h;
	}
	
	public boolean hasCollidedWith(Rectangle r)
	{
		return 
			(
				(x + w     >= r.x) &&
				(r.x + r.w >= x)   &&
				(r.y + r.h >= y)   &&
				( y + h    >= r.y)
		
			);
		
	}
	

	public void moveUpBy(int dy)
	{
		y -= dy;
	}
	
	public void moveDownBy(int dy)
	{
		y += dy;
	}
	
	public void moveRightBy(int dx)
	{
		x += dx;
	}
	
	public void moveLeftBy(int dx)
	{
		x -= dx;
	}
	
	public void increaseWidthBy(int dw)
	{
		w += dw;
	}
	
	public void decreaseWidthBy(int dw)
	{
		h -= dw;
	}
	
	public void increaseHeightBy(int dh)
	{
		w += dh;
	}
	
	public void decreaseHeightBy(int dh)
	{
		h -= dh;
	}
	

	/*public boolean contains(int mx, int my)
	{
		 return (my < y+h) && (my > y) && (mx > x) && (mx < x+w);
	}*/
	
	public void draw(Graphics g)
	{
		g.drawRect(x,y,w,h);
	}


}
