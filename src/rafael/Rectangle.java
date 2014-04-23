package rafael;


import java.awt.Color;
import java.awt.Graphics;

public class Rectangle 
{

	public int x;
	public int y;
	
	public int w;
	public int h;
	
	public Color color;
	
	public Rectangle(int x, int y, int w, int h, Color color)
	{
		this.x = x;
		this.y = y;
		
		this.w = w;
		this.h = h;
		
		this.color = color;
	}
	
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
	
	public void setHeight(int h)
	{
		this.h = h;
	}
	
	public void setWidth(int w)
	{
		this.w = w;
	}
	
	public void setYPosition(int y)
	{
		this.y = y;
	}
	
	public void setXPosition(int x)
	{
		this.x = x;
	}
	
	
	public void draw(Graphics g)
	{
		g.setColor(color);
		g.drawRect(x,y,w,h);
		
	}


}
