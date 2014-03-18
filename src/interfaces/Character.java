package interfaces;

import java.awt.Graphics;

public interface Character {
	
	// Direction the character is looking in
	public enum Direction {
		LEFT, RIGHT
	};
	
	// Positions the character can be in
	public enum Position {
		RTW, LTW, RTJ, LTJ, RTJB, LTJB, RTD, LTD, RTK, LTK, RTKD, LTKD, RTPD, LTPD, RTAP, LTAP
	};
	
	//positions (18 of them)
	final static int RIGHT_WALK = 0;
	final static int LEFT_WALK = 1;
	
	final static int RIGHT_JUMP = 2;
	final static int LEFT_JUMP = 3;
	
	final static int RIGHT_PUNCH = 4;
	final static int LEFT_PUNCH= 5;
	
	final static int RIGHT_JUMP_BACK = 6;
	final static int LEFT_JUMP_BACK = 7;
	
	final static int RIGHT_DUCK = 8;
	final static int LEFT_DUCK = 9;
	
	final static int RIGHT_KICK = 10;
	final static int LEFT_KICK = 11;
	
	final static int RIGHT_KICK_DUCKING = 12;
	final static int LEFT_KICK_DUCKING = 13;
	
	final static int RIGHT_PUNCH_DUCKING = 14;
	final static int LEFT_PUNCH_DUCKING = 15;
	
	final static int RIGHT_ALT_PUNCH = 16;
	final static int LEFT_ALT_PUNCH = 17;
	
	/**
	 * Draws a character on the screen
	 * @return the name of the character
	 */
	/*
	 * Non-default methods which should be defined in the classes
	 * that implement this interface
	 */
	public void decreaseHealthBy(int dHealth);
	public void draw(Graphics g);
	public String identify();
	
	// TODO Determine what direction the player is
	// looking in and return either LEFT or RIGHT
	public Direction directionFacing();
	
	/*
	 * Default methods are methods which are automatically inherited
	 * by any class that implements this interface. Since all characters
	 * will perform the same motions and it is only their images that
	 * differ, each of these methods should take a parameter of type
	 * Character (that is, this interface), get some idea of what
	 * character it is (identify() should return a String that names
	 * each character), and then loading these images. What we do after
	 * we load those images depends on our implementation.
	 */
	default public void punch(){}
	default public void kick(){}
	default public void jump(){}
	default public void duck(){}
	default public void die(){}
	default public void walk(){}
	default public void idle(){}
	default public void block(){}
	default public void victory(){}
	default public void takeHit(){}
}
