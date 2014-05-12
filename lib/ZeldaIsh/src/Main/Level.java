package Main;

import java.io.*;
import java.util.ArrayList;

public class Level
{
	String path = "";
	int spriteSize = 50;
	EntityPlayer player;
	public ArrayList<Rect> map = new ArrayList<Rect>();
	public ArrayList<Rect> collidableRects = new ArrayList<Rect>();
	public ArrayList<EntityEnemy> enemyEntities = new ArrayList<EntityEnemy>();
	
	ImageLayer link;
	ImageLayer bug;
	ImageLayer spider;
	ImageLayer dude;
	ImageLayer bird;
	ImageLayer grass;
	ImageLayer brick;
	ImageLayer water;
	ImageLayer ball;
	ImageLayer sword;
	ImageLayer lava;
	ImageLayer end;
	
	Level()
	{
		// This loads every image needed to play the game
		// all of them are kept in this class, so when a new level is started, there is no need to reload every image from disk space
		link = new ImageLayer("link.png");
		bug = new ImageLayer("bug.png");
		spider = new ImageLayer("spider.png");
		dude = new ImageLayer("dudes.png");
		bird = new ImageLayer("bird.png");
		grass = new ImageLayer("grass.jpg");
		brick = new ImageLayer("brick.jpg");
		water = new ImageLayer("water.jpg");
		ball = new ImageLayer("ball.png");
		sword = new ImageLayer("sword.png");
		lava = new ImageLayer("lava.jpg");
		end = new ImageLayer("win.png");
	}
	
	public void loadLevel(String path)//the path to the level.txt file
	{
		try
		{
			// clears out the level lists to make sure there is nothing there from previous levels
			enemyEntities.clear();
			collidableRects.clear();
			map.clear();
			
			// reads in the level file line by line and character by character
			FileReader fr = new FileReader(path);
			BufferedReader textReader = new BufferedReader(fr);//tells it where to start reading it from
			
			String line = "";
			int lineCount = 0;
			
			// depending on the character it reads, the level will load a particular object or tile.
			// Examples
			// O for grass, P for player spawn, E for end of level, S for spider, D for dude, b for bug, etc.
			
			//reads the whole line and goes to the next line
			while((line = textReader.readLine()) != null)//line is the spaces and characters which are the pictures
			{//sticks all that into line variable; null is the end of the line or text file with no information
				System.out.println(line);//just showing you the map in console; can be commented out
				
				for(int count = 0; count < line.length(); count++)//count increments for each line
				{
					Rect r = new Rect(count * spriteSize, lineCount * spriteSize, spriteSize, spriteSize, null);
					//where placed in the map(x), y position,width,height, null(image placed on rectangle)
					
					//every character is going to be in rectangle sized box
					if('O' == line.charAt(count))
					{
						r.image = grass;
					}
					else if('W' == line.charAt(count))
					{
						r.image = water;
						collidableRects.add(r);//adding r to collidableRects
					}
					else if('L' == line.charAt(count))
					{
						r = new RectLava(count * spriteSize, lineCount * spriteSize, spriteSize, spriteSize, lava);
						collidableRects.add(r);
					}
					else if('P' == line.charAt(count))
					{
						this.player = new EntityPlayer(link, sword, count * spriteSize, lineCount * spriteSize, spriteSize, spriteSize);
						r.image = grass;
						//player.collisionRect.x = count * spriteSize;
						//player.collisionRect.y = lineCount * spriteSize;
					}
					else if('b' == line.charAt(count))
					{
						r.image = grass;
						EntityBug enemy = new EntityBug(bug, sword, count * spriteSize, lineCount * spriteSize, spriteSize, spriteSize);
						enemyEntities.add(enemy);
					}
					else if('B' == line.charAt(count))
					{
						r.image = grass;
						EntityBird enemy = new EntityBird(bird, sword, count * spriteSize, lineCount * spriteSize, spriteSize, spriteSize);
						enemyEntities.add(enemy);
					}
					else if('s' == line.charAt(count))
					{
						r.image = grass;
						EntityBall enemy = new EntityBall(ball, sword, count * spriteSize, lineCount * spriteSize, spriteSize, spriteSize);
						enemyEntities.add(enemy);
					}
					else if('S' == line.charAt(count))
					{
						r.image = grass;
						EntitySpider enemy = new EntitySpider(spider, sword, count * spriteSize, lineCount * spriteSize, spriteSize, spriteSize);
						enemyEntities.add(enemy);
					}
					else if('D' == line.charAt(count))
					{
						r.image = grass;
						EntityDude enemy = new EntityDude(dude, sword, count * spriteSize, lineCount * spriteSize, spriteSize, spriteSize);
						enemyEntities.add(enemy);
					}
					else if('E' == line.charAt(count))
					{
						r = new RectEnd(count * spriteSize, lineCount * spriteSize, spriteSize, spriteSize, end);
						collidableRects.add(r);
						
						Rect rg = new Rect(count * spriteSize, lineCount * spriteSize, spriteSize, spriteSize, grass);
						map.add(rg);
					}
					else	//for any white space or any unknown character, water automatically
					{
						r.image = water;
						collidableRects.add(r);
					}
					
					map.add(r);//keeps track of all the map rectangles being drawn (all the map specific images)
				}
				
				lineCount++;//keeping track of the line we're at
			}
			
			textReader.close();//once its reading the file it closes it to get rid of it
		}
		catch (IOException e)//couldn't load or couldn't find the file; safety measure
		{
			System.err.println("Failed to load: " + path);
			e.printStackTrace();
		}
	}
}
