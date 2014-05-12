package Main;

import java.awt.*;
import java.awt.geom.*;

public class ImageLayer
{
	// This class is basically the same as the src code, except it has been given a rotation to be able to rotate images
	// All this does is load an image
	Image image;

	public int x = 0;
	public int y = 0;
	public int width = 0;
	public int height = 0;
	public float rotation = 0;

	int d;

	public ImageLayer(String filename)
	{
		image = Toolkit.getDefaultToolkit().getImage(filename);
	}

	public void draw(Graphics2D g2d)
	{
		AffineTransform backup = g2d.getTransform();
		AffineTransform trans = new AffineTransform();
		trans.setToRotation(Math.toRadians(rotation), this.x + this.width / 2, this.y + this.height / 2); // the points to rotate around (the center in my example, your left side for your problem)

		g2d.transform( trans );
		g2d.drawImage(this.image, this.x, this.y, this.width, this.height, null);  // the actual location of the sprite

		g2d.setTransform( backup );
		

	}
}
