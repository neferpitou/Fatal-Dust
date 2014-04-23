package rafael;
//package characters;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

//import objects.PositionAnimation;

//import interfaces.Character;

//TODO: fix l0G1k !

public class AyakoTurner //implements Character
{
	
	int health;	
	int centerX;
	int centerY;
	
	int x;
	int y;

	boolean isDucking;
	boolean lookingRight;
	boolean isBlocking;
	boolean isPunching;
	boolean isJumping;
	boolean reachedJumpApex;
	boolean isKicking;
	
	boolean isWalking;
	
	final String IMG_PREFIX = "Ayako";
	
	final int H = 300;
	final int W = 150;
	
	final int FLOOR_Y = 600;
	final int _Y = FLOOR_Y - H ; 
	
	final int BLOCK_Y = _Y + 10;
	
	final int DUCK_Y = _Y + 100;
	final int DUCK_W = W + 10;
	
	final int KICK_W = W + 50;
	final int KICK_H = H + 15;
	final int KICK_Y = _Y - 15;
	
	final int BLOCK_W = W - 10;
	
	final int PUNCH_W = W + 30;
	final int PUNCH_H = H - 20;
	final int PUNCH_Y = _Y + 20;
	
	final int WALK_H = H - 25;
	final int WALK_Y = _Y + 25;
	
	final boolean DEBUG_MODE_ON = true;
	
	int vy;
	
	final int INIT_JUMP_V = 40;
	final int GRAVITY = 2;
	
	
	//Image i = Toolkit.getDefaultToolkit().getImage("resources/idle_left_0.gif");
	
	SpriteAnimated a; 
	SpriteAnimated a_lt;
	SpriteAnimated a_rt;
	
	Rectangle greenHitBox; //Box where character gets hit 
	Rectangle g_mid; //Box where character hits another 
	Rectangle g_bottom; //Box for defense; doesn't decrease health if hit
	
	//PositionAnimation[] animations;
	
	public AyakoTurner(boolean lookingRight, int centerX)
	{
		this.lookingRight = lookingRight;
		this.centerX = centerX;
		
		isDucking = false;
		isBlocking = false;
		isPunching = false; 
		isJumping = false;
		isKicking = false;
		
		vy = 0;
		
		x = centerX - (W/2) ;
		y = _Y ; 
		
		//TODO: fix these numbers
		
		
		if(lookingRight)
		{
			greenHitBox = new Rectangle( centerX - 45, y+50, 60, 150, Color.GREEN);
		}
		
		else
		{
			greenHitBox = new Rectangle( centerX - 15, y+50, 60, 150, Color.GREEN);
		}
		
	
		
		//TODO: THESE ARE INVERSED FOR SOME REASON!
		a_lt = new SpriteAnimated(x, y , W, H, "right", IMG_PREFIX);
		a_rt = new SpriteAnimated(x, y , W, H, "left",  IMG_PREFIX);
		
		//a =  new SpriteAnimated(topLeftX, topLeftY , W, H, lookingRight, IMG_PREFIX);
		
		if(lookingRight)
			a = a_rt;
		
		else
			a = a_lt;
		
	}
	
	

	
	public void punch()
	{
		//Punch iff not ducking or jumping
		if (!isDucking && !isJumping)
		{
			isPunching = true;
			
			a.setWidth(PUNCH_W);
			a.setHeight(PUNCH_H);
			a.setYPosition(PUNCH_Y);
			
			a.setPos(2);
			
			//redHitBox.setXPosition(centerX+75);
		}
		
		
	}
	
	public void block()
	{
		if(!isDucking && !isPunching && !isJumping)
		{
			isBlocking = true;
			
			a.setWidth(BLOCK_W);
			a.setYPosition(BLOCK_Y);
			
			a.setPos(4);
		}
		
	}

	public void kick()
	{
		
		if(!isDucking && !isJumping)
		{
			
			isKicking = true;
		
			a.setWidth(KICK_W);
			a.setHeight(KICK_H);
			a.setYPosition(KICK_Y);
		
			a.setPos(5);

		}
	}
	
	public void jump()
	{
		if ( !isJumping && !isDucking )
		{
			isJumping = true;
			
			//Initial Jump Velocity
			vy = INIT_JUMP_V;
			
			//a.setPos(4);
			//greenHitBox.setYPosition( JUMP_Y );
			
			System.out.println("JUMP");
		}
		
	}
	
	public void duck()
	{
		if(!isJumping && !isKicking)
		{
			isDucking = true;
			
			a.setYPosition(DUCK_Y);
			a.setWidth(DUCK_W);
			
			a.setPos(1);
		}
		

	}
	
	
	public void draw(Graphics g)
	{
	
		
		if(isJumping)//Jumping
			{
				
				a.moveUpBy( vy );
				greenHitBox.moveUpBy(vy);
				
				vy -= GRAVITY;
				
				if (a.y  >= _Y)
				{
					isJumping = false;
				}
				
				
			}
				
		
		a.draw(g);
		
		resetDefaults();
		
		
		if(DEBUG_MODE_ON)
		{
			//greenHitBox.draw(g);
			//redHitBox.draw(g);
			//blueHitBox.draw(g);
			
			greenHitBox.draw(g);
			g.setColor(Color.black);
		}
		
		
		
		
	}
	
	public void moveForward(int dx)
	{
		
		
		
		if (lookingRight)
		{
			if( !isDucking && !isBlocking && !isPunching && !isKicking)
			{
				
				centerX += dx;
				greenHitBox.moveRightBy(dx);
				//redHitBox.moveRightBy(dx);
				
				a.moveRightBy(dx);
				a_lt.moveRightBy(dx);
				
				a.setHeight(WALK_H);
				if(!isJumping) a.setYPosition(WALK_Y);
				
				isWalking = true;
				a.setPos(3);
			
			}
		}
		else
		{
			if( !isDucking && !isBlocking && !isPunching && !isKicking)
			{
				
				centerX -= dx;
				greenHitBox.moveLeftBy(dx);
				//redHitBox.moveLeftBy(dx);
				
				a.moveLeftBy(dx);
				a_rt.moveLeftBy(dx);
				
				a.setHeight(WALK_H);
				if(!isJumping) a.setYPosition(WALK_Y);
				
				isWalking = true;
				a.setPos(3);
			
			}
		}

		
		
	}
	
	public void moveBackward(int dx)
	{
		
		
		
		if(lookingRight)
		{
			if (!isDucking && !isPunching && !isKicking)
			{
				centerX -= dx;
				greenHitBox.moveLeftBy(dx);
				//redHitBox.moveLeftBy(dx);
				
				a.moveLeftBy(dx);
				a_lt.moveLeftBy(dx);
				
				a.setHeight(WALK_H);
				if(!isJumping) a.setYPosition(WALK_Y);
				
				isWalking = true;
				a.setPos(3);
			}
		}
		else
		{
			
			if (!isDucking && !isPunching && !isKicking)
			{
				centerX += dx;
				
				greenHitBox.moveRightBy(dx);
				//redHitBox.moveRightBy(dx);
				
				a.moveRightBy(dx);
				a_rt.moveRightBy(dx);
				
				a.setHeight(WALK_H);
				if(!isJumping) a.setYPosition(WALK_Y);
				
				isWalking = true;
				a.setPos(3);
			}
			
		}
		
		
		
		
		
	}
	
	
	public void decreaseHealthBy(int dHealth)
	{
		health -= dHealth;
	}
	
	public void setDirection( boolean lookingRight )
	{
		this.lookingRight = lookingRight;
		
		
		if(lookingRight)
		{
			a = a_rt;
		}
		
		else
		{
			a = a_lt;
		}
		
		//invertHitBoxes(greenHitBox);
		
	}
	
	public int getHealth()
	{
		return health;
	}
	
	private void setHitBoxCoords( )
	{
		if (lookingRight)
		{
			
			
		}
	}
	
	public void resetDefaults()
	{
		isBlocking = false;
		isDucking = false;
		isWalking = false;
		isPunching = false;
		isKicking = false;
		
		a.setWidth(W);
		a.setHeight(H);
		
		if(!isJumping ) a.setYPosition(_Y);
		
		a.setPos(0);
	}

/*
	@Override
	public String identify() {
		// TODO Auto-generated method stub
		return null;
	}
	



	@Override
	public Direction directionFacing() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
*/
}
