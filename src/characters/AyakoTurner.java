package characters;

//package characters;

import java.awt.Color;
import java.awt.Graphics;

import factory.CharacterType;

public class AyakoTurner extends VanillaCharacter {

	final String IMG_PREFIX = CharacterType.AyakoTurner + "";

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

	String[] a_position = { "idle", "crouch", "light_punch", "walking",
			"block", "light_kick", "die", "hit", "jump" };
	int[] a_count = { 3, 2, 3, 8, 2, 4, 4, 2, 3 };

	final boolean DEBUG_MODE_ON = true;

	public AyakoTurner(boolean lookingRight) {
		super(lookingRight, CharacterType.AyakoTurner);
		vy = 0;

		a_lt = new SpriteAnimated(x, y, characterWidth, characterHeight,
				"right", IMG_PREFIX, a_position, a_count);
		a_rt = new SpriteAnimated(x, y, characterWidth, characterHeight,
				"left", IMG_PREFIX, a_position, a_count);

		a = lookingRight ? a_rt : a_lt;
	}

	public void punch() {
		// Punch iff not ducking or jumping
		if (!isDucking && !isJumping) {
			isPunching = true;

			a.setWidth(PUNCH_W);
			a.setHeight(PUNCH_H);
			a.setYPosition(PUNCH_Y);

			strikeBox.setYPosition(a.y + 20);

			strikeBox.setHeight(60);
			strikeBox.setWidth(75);

			if (lookingRight)
				strikeBox.setXPosition(centerX + 15);
			else
				strikeBox.setXPosition(centerX - 90);

			a.setCurrentAnimation(PUNCH);
			
		}

	}

	public void block() {
		if (!isDucking && !isPunching && !isJumping) {
			isBlocking = true;

			a.setWidth(BLOCK_W);
			a.setYPosition(BLOCK_Y);

			if (lookingRight)
				strikeBox.setXPosition(a.x);
			else
				strikeBox.setXPosition(a.x + characterHeight);

			guardBox.setHeight(hitBox.h + 30);
			guardBox.setWidth(hitBox.w);

			a.setCurrentAnimation(BLOCK);
		}

	}

	public void kick() {
		if (!isDucking && !isJumping) {

			isKicking = true;

			a.setWidth(KICK_W);
			a.setHeight(KICK_H);
			a.setYPosition(KICK_Y);

			strikeBox.setHeight(40);
			strikeBox.setWidth(100);
			strikeBox.setYPosition(a.y + 60);

			if (lookingRight)
				strikeBox.setXPosition(centerX + 15);
			else
				strikeBox.setXPosition(centerX - 115);

			a.setCurrentAnimation(KICK);
		}
	}

	public void duck() {
		if (!isJumping && !isKicking) {
			isDucking = true;

			hitBox.setWidth(DUCK_W - 75);

			a.setYPosition(DUCK_Y);
			a.setWidth(DUCK_W);

			hitBox.setYPosition(DUCK_Y + 50);

			a.setCurrentAnimation(DUCK);
		}

	}

	public void draw(Graphics g) {

		if (isJumping)// Jumping
		{

			a.moveUpBy(vy);

			strikeBox.moveUpBy(vy);
			hitBox.moveUpBy(vy);
			guardBox.moveUpBy(vy);

			vy -= GRAVITY;

			if (isDead) {
				if (a.y >= HEIGHT_Y + characterWidth) {
					isJumping = false;
				}

			} else {
				if (a.y >= HEIGHT_Y) {
					isJumping = false;
				}
			}
		}

		a.draw(g);

		if (DEBUG_MODE_ON) {
			strikeBox.draw(g);
			hitBox.draw(g);
			guardBox.draw(g);

			g.setColor(Color.black);
		}
	}

	public void moveForward(int dx) {

		if (isLookingRight()) {
			if (!isDucking && !isBlocking && !isPunching && !isKicking) {

				setCenterX(getCenterX() + dx);

				strikeBox.moveRightBy(dx);
				hitBox.moveRightBy(dx);
				guardBox.moveRightBy(dx);

				a.moveRightBy(dx);
				a_lt.moveRightBy(dx);

				a.setHeight(WALK_H);
				if (!isJumping())
					a.setYPosition(WALK_Y);

				isWalking = true;
				a.setCurrentAnimation(WALKING);

			}
		} else {
			if (!isDucking && !isBlocking && !isPunching && !isKicking) {

				setCenterX(getCenterX() - dx);

				strikeBox.moveLeftBy(dx);
				hitBox.moveLeftBy(dx);
				guardBox.moveLeftBy(dx);

				a.moveLeftBy(dx);
				a_rt.moveLeftBy(dx);

				a.setHeight(WALK_H);
				if (!isJumping())
					a.setYPosition(WALK_Y);

				isWalking = true;
				a.setCurrentAnimation(WALKING);

			}
		}

	}

	public void moveBackward(int dx) {

		if (isLookingRight()) {
			if (!isDucking && !isPunching && !isKicking) {
				setCenterX(getCenterX() - dx);

				strikeBox.moveLeftBy(dx);
				hitBox.moveLeftBy(dx);
				guardBox.moveLeftBy(dx);

				a.moveLeftBy(dx);
				a_lt.moveLeftBy(dx);

				a.setHeight(WALK_H);
				if (!isJumping())
					a.setYPosition(WALK_Y);

				isWalking = true;
				a.setCurrentAnimation(WALKING);
			}
		} else {

			if (!isDucking && !isPunching && !isKicking) {
				setCenterX(getCenterX() + dx);

				strikeBox.moveRightBy(dx);
				hitBox.moveRightBy(dx);
				guardBox.moveRightBy(dx);

				a.moveRightBy(dx);
				a_rt.moveRightBy(dx);

				a.setHeight(WALK_H);
				if (!isJumping())
					a.setYPosition(WALK_Y);

				isWalking = true;
				a.setCurrentAnimation(WALKING);
			}

		}

	}

	public void decreaseHealthBy(int dHealth) {
		health -= dHealth;
	}

	public void setDirection(boolean lookingRight) {
		this.setLookingRight(lookingRight);

		if (lookingRight) {
			a = a_rt;
		}

		else {
			a = a_lt;
		}
	}

	public int getHealth() {
		return health;
	}
	
	@Override
	public void die() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void victory() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fightAgainst(VanillaCharacter other) {
		// TODO Auto-generated method stub
		if (other.isKicking || other.isPunching) {
			duck();
		}

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return IMG_PREFIX;
	}

	@Override
	public String getHealthBarDisplayName() {
		// TODO Auto-generated method stub
		return "AYAKO TURNER";
	}
}
