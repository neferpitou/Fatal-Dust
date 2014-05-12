package Main;

public class RectEnd extends Rect
{
	// RectEnd is the rect that displays the end of level paltform
	public RectEnd(int x, int y, int w, int h, ImageLayer image)
	{
		super(x, y, w, h, image);
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.image = image;
	}
	
	public boolean hasCollidedWith(Rect r)
	{
			return
			(x + w > r.x) && 
			(r.x + r.w > x) &&
			(r.y + r.h > y) &&
			(y + h > r.y);	

	}
	
	// If player touches the end of level rect, then the player has reached the end of level
	public boolean hasCollidedWith(Entity e)
	{
		if((x + w > e.collisionRect.x) && 
			(e.collisionRect.x + e.collisionRect.w > x) &&
			(e.collisionRect.y + e.collisionRect.h > y) &&
			(y + h > e.collisionRect.y))
		{
			e.reachedEndOfLevel = true;
		}
		return false;
	}
}
