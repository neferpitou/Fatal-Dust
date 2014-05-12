package Main;


public class RectLava extends Rect
{
	// RectLava is just a pool of lava
	public RectLava(int x, int y, int w, int h, ImageLayer image)
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
	
	// If an entity touches the lava, it is burned to death
	public boolean hasCollidedWith(Entity e)
	{
		if((x + w > e.collisionRect.x) && 
			(e.collisionRect.x + e.collisionRect.w > x) &&
			(e.collisionRect.y + e.collisionRect.h > y) &&
			(y + h > e.collisionRect.y))
		{
			System.out.println("LAVA");
			e.takeDamage(20);
		}
		return false;
	}
	
}
