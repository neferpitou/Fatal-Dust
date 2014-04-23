package rafael;



import java.awt.*;
import java.applet.Applet;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JApplet;



public class GameEngine extends Applet implements MouseListener, KeyListener, Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	boolean[] isPressed = new boolean[256]; 
	
	final int MOVEMENT = 13;
	
	final int LEFT_PLAYER_X  = 400;
	final int RIGHT_PLAYER_X = 800;
	
	boolean lookRight = true;
	

	
	
	
	AyakoTurner ayako = new AyakoTurner( lookRight , LEFT_PLAYER_X );
	
	AyakoTurner ayako2 = new AyakoTurner( !lookRight , RIGHT_PLAYER_X );
	
	
	//Animation gg = new Animation("g_dn", 5);
	

	
	
	
	
	public void init()
	{
		
		
		setSize(1200,750);
		setLayout(null);
		
		
		for(int i = 0; i < isPressed.length ; i++)
		{
			isPressed[i] = false;
		}
		

		requestFocus();
		addKeyListener(this);
		this.addMouseListener(this);
	
		Thread t = new Thread(this);
		
		//will call this' run function
		t.start();
		
	}
	
	public void run()
	{
		//Our code can run in this thread
		
		while(true)
		{
			
			if(isPressed[KeyEvent.VK_UP])     ayako.jump();
			if(isPressed[KeyEvent.VK_DOWN])   ayako.duck();
			
			if(isPressed[KeyEvent.VK_W])      ayako.punch();
			
			if(isPressed[KeyEvent.VK_A])      ayako.kick();
			
			    
			
			if(ayako.lookingRight)
			{
				if(isPressed[KeyEvent.VK_B])   ayako.block(); 
				if(isPressed[KeyEvent.VK_RIGHT])  ayako.moveForward(MOVEMENT);
				if(isPressed[KeyEvent.VK_LEFT])      ayako.moveBackward(MOVEMENT);
				//if(isPressed[KeyEvent.VK_D])      ayako.setDirection(false);
			}
			else
			{
				
				if(isPressed[KeyEvent.VK_B])   ayako.block(); 
				if(isPressed[KeyEvent.VK_LEFT])   ayako.moveForward(MOVEMENT);
				if(isPressed[KeyEvent.VK_RIGHT])      ayako.moveBackward(MOVEMENT);
				//if(isPressed[KeyEvent.VK_D])      ayako.setDirection(true);
				
			}
			
			//if(isPressed[KeyEvent.VK_S]) ayako.lookingRight = !ayako.lookingRight;
			
			
			
			if ( ayako.centerX > ayako2.centerX)
			{
				if ( !ayako.isJumping )
				{
					ayako.setDirection(!lookRight);
					ayako2.setDirection(lookRight);
					
				}
				
	
					
			}
			
			else
			{
				if ( !ayako.isJumping )
				{
					ayako.setDirection(lookRight);
					ayako2.setDirection(!lookRight);
					
				}
				
				
			}
			
			
			
			repaint();
			
			try{
			
				Thread.sleep(16);
			}
			catch(Exception ex){}
		}
		
	}
	


	public void keyTyped(KeyEvent e) 
	{

		
	}


	public void keyPressed(KeyEvent e) 
	{
	
		isPressed[e.getKeyCode()] = true;
	
	}

	public void keyReleased(KeyEvent e) 
	{
		
		isPressed[e.getKeyCode()] = false;
		
		
	}
	
	public void mouseClicked(MouseEvent e)
	{
	
	
	}
	
	public void mouseReleased(MouseEvent e){
		System.out.println("Released");
	}
	
	public void mousePressed(MouseEvent e){
		 System.out.println("Pressed");
	}
	
	public void mouseEntered(MouseEvent e) {
		System.out.println("Entered");
	}

	public void mouseExited(MouseEvent e) {
		System.out.println("Exited");
	}
	
	public void paint(Graphics g)
	{
	
		
		ayako2.draw(g);
		ayako.draw(g);
		
		g.drawString( "Ayako #1: "+ ayako.centerX , 10, 40);
		g.drawString( "Ayako #2: "+ ayako2.centerX , 10, 60);
		
		if(ayako.lookingRight)
		{
			g.drawString("AYAKO LOOKING RIGHT", 10, 20);
		
		}
		else
		{
			g.drawString("AYAKO LOOKING LEFT", 10, 20);
		}
		
	
	}

}
