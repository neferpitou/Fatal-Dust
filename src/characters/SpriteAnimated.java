package characters;

import java.awt.*;

public class SpriteAnimated extends Rectangle {
	private Animation[] animation;
	private int currentAnimation;
	private long startTime = System.currentTimeMillis();

	public SpriteAnimated(int x, int y, int w, int h, String direction,
			String fighterName, String[] positions, int[] count) {

		super(x, y, 0, 0);

		this.x = x;
		this.y = y;

		this.w = w;
		this.h = h;

		animation = new Animation[positions.length];

		currentAnimation = 0;

		for (int i = 0; i < animation.length; i++)
			animation[i] = new Animation(fighterName, positions[i], direction,
					count[i]);
	}

	public void setCurrentAnimation(int newAnimation) {
		currentAnimation = newAnimation;
	}

	
	
	public void draw(Graphics g) {
		// img, x, y, w, h, observer
		long elapsedTime = System.currentTimeMillis() - startTime;
		startTime = System.currentTimeMillis();
		animation[currentAnimation].update(elapsedTime);
		g.drawImage(animation[currentAnimation].getImage(), x, y, w, h, null);
		super.draw(g);
	}
}
