package characters;

//package characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import factory.CharacterType;

public class AyakoTurner extends VanillaCharacter {

	final String IMG_PREFIX = CharacterType.AyakoTurner+"";

	final int BLOCK_Y = HEIGHT_Y + 10;

	final int DUCK_Y = HEIGHT_Y + 100;
	final int DUCK_W = characterWidth + 10;

	final int KICK_W = characterWidth + 50;
	final int KICK_H = characterHeight + 15;
	final int KICK_Y = HEIGHT_Y - 15;

	final int BLOCK_W = characterWidth - 10;

	final int PUNCH_W = characterWidth + 30;
	final int PUNCH_H = characterHeight - 20;
	final int PUNCH_Y = HEIGHT_Y + 20;

	final int WALK_H = characterHeight - 25;
	final int WALK_Y = HEIGHT_Y + 25;

	final boolean DEBUG_MODE_ON = true;

	public AyakoTurner(boolean lookingRight) {
		super(lookingRight, CharacterType.AyakoTurner);
		vy = 0;

		// TODO: THESE ARE INVERSED FOR SOME REASON!
		a_lt = new SpriteAnimated(x, y, characterWidth, characterHeight,
				"right", IMG_PREFIX);
		a_rt = new SpriteAnimated(x, y, characterWidth, characterHeight,
				"left", IMG_PREFIX);
		
		a = lookingRight ? a_rt : a_lt;
	}

	public void punch() {
		// Punch iff not ducking or jumping
		if (!isDucking && !isJumping) {
			isPunching = true;

			a.setWidth(PUNCH_W);
			a.setHeight(PUNCH_H);
			a.setYPosition(PUNCH_Y);

			a.setPos(2);

			// redHitBox.setXPosition(centerX+75);
		}

	}

	public void block() {
		if (!isDucking && !isPunching && !isJumping) {
			isBlocking = true;

			a.setWidth(BLOCK_W);
			a.setYPosition(BLOCK_Y);

			a.setPos(4);
		}

	}

	public void kick() {

		if (!isDucking && !isJumping) {

			isKicking = true;

			a.setWidth(KICK_W);
			a.setHeight(KICK_H);
			a.setYPosition(KICK_Y);

			a.setPos(5);

		}
	}

	public void jump() {
		if (!isJumping && !isDucking) {
			isJumping = true;

			// Initial Jump Velocity
			vy = INIT_JUMP_V;

			// a.setPos(4);
			// greenHitBox.setYPosition( JUMP_Y );

			System.out.println("JUMP");
		}

	}

	public void duck() {
		if (!isJumping && !isKicking) {
			isDucking = true;

			a.setYPosition(DUCK_Y);
			a.setWidth(DUCK_W);

			a.setPos(1);
		}

	}

	public void draw(Graphics g) {

		if (isJumping)// Jumping
		{

			a.moveUpBy(vy);
			greenHitBox.moveUpBy(vy);

			vy -= GRAVITY;

			if (a.y >= HEIGHT_Y) {
				isJumping = false;
			}

		}

		a.draw(g);

		resetDefaults();

		if (DEBUG_MODE_ON) {
			// greenHitBox.draw(g);
			// redHitBox.draw(g);
			// blueHitBox.draw(g);

			greenHitBox.draw(g);
			g.setColor(Color.black);
		}

	}

	public void moveForward(int dx) {

		if (lookingRight) {
			if (!isDucking && !isBlocking && !isPunching && !isKicking) {

				centerX += dx;
				greenHitBox.moveRightBy(dx);
				// redHitBox.moveRightBy(dx);

				a.moveRightBy(dx);
				a_lt.moveRightBy(dx);

				a.setHeight(WALK_H);
				if (!isJumping)
					a.setYPosition(WALK_Y);

				isWalking = true;
				a.setPos(3);

			}
		} else {
			if (!isDucking && !isBlocking && !isPunching && !isKicking) {

				centerX -= dx;
				greenHitBox.moveLeftBy(dx);
				// redHitBox.moveLeftBy(dx);

				a.moveLeftBy(dx);
				a_rt.moveLeftBy(dx);

				a.setHeight(WALK_H);
				if (!isJumping)
					a.setYPosition(WALK_Y);

				isWalking = true;
				a.setPos(3);

			}
		}

	}

	public void moveBackward(int dx) {

		if (lookingRight) {
			if (!isDucking && !isPunching && !isKicking) {
				centerX -= dx;
				greenHitBox.moveLeftBy(dx);
				// redHitBox.moveLeftBy(dx);

				a.moveLeftBy(dx);
				a_lt.moveLeftBy(dx);

				a.setHeight(WALK_H);
				if (!isJumping)
					a.setYPosition(WALK_Y);

				isWalking = true;
				a.setPos(3);
			}
		} else {

			if (!isDucking && !isPunching && !isKicking) {
				centerX += dx;

				greenHitBox.moveRightBy(dx);
				// redHitBox.moveRightBy(dx);

				a.moveRightBy(dx);
				a_rt.moveRightBy(dx);

				a.setHeight(WALK_H);
				if (!isJumping)
					a.setYPosition(WALK_Y);

				isWalking = true;
				a.setPos(3);
			}

		}

	}

	public void decreaseHealthBy(int dHealth) {
		health -= dHealth;
	}

	public void setDirection(boolean lookingRight) {
		this.lookingRight = lookingRight;

		if (lookingRight) {
			a = a_rt;
		}

		else {
			a = a_lt;
		}

		// invertHitBoxes(greenHitBox);

	}

	public int getHealth() {
		return health;
	}

	public void resetDefaults() {
		isBlocking = false;
		isDucking = false;
		isWalking = false;
		isPunching = false;
		isKicking = false;

		a.setWidth(characterWidth);
		a.setHeight(characterHeight);

		if (!isJumping)
			a.setYPosition(HEIGHT_Y);

		a.setPos(0);
	}

	@Override
	public void dodge() {
		// TODO Auto-generated method stub

	}

	@Override
	public void die() {
		// TODO Auto-generated method stub

	}

	@Override
	public void walk() {
		// TODO Auto-generated method stub

	}

	@Override
	public void idle() {
		// TODO Auto-generated method stub

	}

	@Override
	public void victory() {
		// TODO Auto-generated method stub

	}

	@Override
	public void takeHit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDirection() {
		// TODO Auto-generated method stub

	}

	/*
	 * @Override public String identify() { // TODO Auto-generated method stub
	 * return null; }
	 * 
	 * 
	 * 
	 * 
	 * @Override public Direction directionFacing() { // TODO Auto-generated
	 * method stub return null; }
	 */
}
