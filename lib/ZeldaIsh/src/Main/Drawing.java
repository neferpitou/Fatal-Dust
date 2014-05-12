package Main;

import java.awt.*;
//import java.io.*;
//import java.util.*;
import java.awt.event.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.ArrayList;


public class Drawing extends Applet implements MouseListener, KeyListener, Runnable
{
	// This is where the game loop is and where everything is drawn
	
	private static final long serialVersionUID = 1L;//it gets rid of warning
	
	
	// GUI heart image for display
	ImageLayer heart = new ImageLayer("heart.png");//load the image
	Rect heartRect = new Rect(0, 0, 20, 20, heart);//creates rectangle for the heart image
	
	// Input and drawing variables
	int mx;
	int my;
	boolean[] isPressed = new boolean[256];
	Image offScreen;
	Graphics2D offScreen_g;
	
	//level counter and level class for loading levels
	int numberOfLevels = 4;
	int currentLevelNumber = 1;
	Level currentLevel = new Level();

	// Contructor for drawing applet class
	Drawing()
	{
		//Adds listeners for input and sets up the applet for input
		for (int i = 0; i < isPressed.length; i++)
		{
			isPressed[i] = false;
		}

		this.requestFocus();
		this.addKeyListener(this);
		this.addMouseListener(this);
		
		
		// Loads the first level
		String levelPath = "level" + Integer.toString(currentLevelNumber) + ".txt";//sets what levelPath is equal to
		currentLevel.loadLevel(levelPath);
	}
	
	// resets the game to the current level
	public void resetGame()
	{
		String levelPath = "level" + Integer.toString(currentLevelNumber) + ".txt";
		currentLevel.loadLevel(levelPath);
	}
	
	// Once the player reaches their destination, loads the next level
	public void loadNextLevel()
	{
		currentLevelNumber++;
		//if their currentlevel is the above the max level, go back to the max level
		if(currentLevelNumber > numberOfLevels)
		{
			currentLevelNumber--;
		}
		
		String levelPath = "level" + Integer.toString(currentLevelNumber) + ".txt";
		
		currentLevel.loadLevel(levelPath);
	}

	//This is the game loops
	public void run()	//checking for the key presses constantly
	{
		while (true)
		{
			// Key inputs
			if(isPressed[KeyEvent.VK_LEFT] && isPressed[KeyEvent.VK_UP])
			{
				currentLevel.player.moveUpLeft();
			}
			
			if(isPressed[KeyEvent.VK_RIGHT] && isPressed[KeyEvent.VK_UP])
			{
				currentLevel.player.moveUpRight();
			}
			
			if(isPressed[KeyEvent.VK_LEFT] && isPressed[KeyEvent.VK_DOWN])
			{
				currentLevel.player.moveDownLeft();
			}
			
			if(isPressed[KeyEvent.VK_RIGHT] && isPressed[KeyEvent.VK_DOWN])
			{
				currentLevel.player.moveDownRight();
			}
			
			if (isPressed[KeyEvent.VK_LEFT])
			{
				if(currentLevel.player.currentDirection != Entity.Direction.Left.ordinal())
				{
					currentLevel.player.lookLeft();
				}
				else
				{
					currentLevel.player.moveLeft();
				}
			}

			if (isPressed[KeyEvent.VK_RIGHT])
			{
				if(currentLevel.player.currentDirection != Entity.Direction.Right.ordinal())
				{
					currentLevel.player.lookRight();
				}
				else
				{
					currentLevel.player.moveRight();
				}
			}
			
			if (isPressed[KeyEvent.VK_UP])
			{
				if(currentLevel.player.currentDirection != Entity.Direction.Up.ordinal())
				{
					currentLevel.player.lookUp();
				}
				else
				{
					currentLevel.player.moveUp();
				}
			}

			if (isPressed[KeyEvent.VK_DOWN])
			{
				if(currentLevel.player.currentDirection != Entity.Direction.Down.ordinal())
				{
					currentLevel.player.lookDown();
				}
				else
				{
					currentLevel.player.moveDown();
				}
			}
			
			if(isPressed[KeyEvent.VK_SPACE])
			{
				currentLevel.player.attack();
			}
			
			// Updates enemy entities (AI, movement, animations, etc.)
			for(int count = 0; count < currentLevel.enemyEntities.size(); count++)
			{
				//IF alive update, else remove from list for efficiency
				if(currentLevel.enemyEntities.get(count).isAlive)//if alive update it
				{
					currentLevel.enemyEntities.get(count).update(currentLevel.player, currentLevel.collidableRects);
					//collidableRects so can't walk into the water
				}
				else
				{
					currentLevel.enemyEntities.remove(count);
				}
			}
			//Updates player entity (movement, animations, etc.)
			currentLevel.player.update(currentLevel.collidableRects, currentLevel.enemyEntities);//so not walking into things
			//if i touch them, i'll get hit back and take damage
			
			// If the player died, reset the level
			if(currentLevel.player.finishedDeath)
			{
				System.out.println("RESET GAME");
				resetGame();
			}
			// If the player reached the end of the level, go on to the next one
			if(currentLevel.player.reachedEndOfLevel)
			{
				System.out.println("NEXT LEVEL");
				loadNextLevel();
			}
			
			// CAMERA "follows" player around the world
			offScreen = this.createImage(1000, 1000);
			offScreen_g = (Graphics2D) offScreen.getGraphics();
			
			int cameraWidth = 500;
			int cameraHeight = 500;
			int cameraX = (currentLevel.player.collisionRect.x + currentLevel.player.collisionRect.w / 2) - cameraWidth / 2;
			int cameraY = (currentLevel.player.collisionRect.y + currentLevel.player.collisionRect.h / 2) - cameraHeight / 2;
			
			offScreen_g.translate(-cameraX, -cameraY);
			
			
			requestFocus();

			repaint();
		}
	}

	// INPUT stuff
	public void mousePressed(MouseEvent e)
	{
		//int mx = e.getX();
		//int my = e.getY();
	}

	public void update(Graphics g)
	{
		paint(offScreen_g);
		g.drawImage(offScreen, 0, 0, null);
	}

	public void repaint()
	{
		Graphics g = getGraphics();
		update(g);
		g.dispose();
	}

	public void mouseReleased(MouseEvent e)
	{
		//System.out.println("Released");
	}

	public void mouseEntered(MouseEvent e)
	{
		//System.out.println("Entered");
	}

	public void mouseExited(MouseEvent e)
	{
		//System.out.println("Exited");
	}

	public void mouseClicked(MouseEvent e)
	{
		//System.out.println("Click");
	}

	public void keyPressed(KeyEvent e)
	{
		isPressed[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e)
	{
		isPressed[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e)
	{
	}

	// This is where all of the rectangles and images are drawn
	public void paint(Graphics2D g2d)
	{
		
		//BACKGROUND / MAP STUFF HERE
		for(int count = 0; count < currentLevel.map.size(); count++)//goes through map array of images and draws them
		{
			currentLevel.map.get(count).draw(g2d);
		}

		// ENTITY STUFF HERE
		for(int count = 0; count <  currentLevel.enemyEntities.size(); count++)//entity stuff in array and draw that
		{
			currentLevel.enemyEntities.get(count).draw(g2d);
		}
		
		currentLevel.player.draw(g2d);
		
		//GUI STUFF HERE
		
		offScreen_g = (Graphics2D) offScreen.getGraphics();//drawing on offscreen and pasting to javas canvas(method paint)
		
		heartRect.x = 0;
		heartRect.y = 0;
	
		for(int count = 0; count < currentLevel.player.health; count++)
		{
			heartRect.x += heartRect.w;
			heartRect.draw(offScreen_g);
		}
	}
}

