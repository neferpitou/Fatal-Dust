package characters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import factory.CharacterType;
import kernel.FatalKernel;

/**
 * Constructs a health bar rectangle. It starts green then becomes gradually
 * more red with each hit.
 * 
 * @author Dana Smith, Stu Kleinman, Marcos Davila, Rafael Abbondanza
 */
public class HealthBar {
	// healthBarWGreen is the green health status.
	// healthBarWRed is the red health status. It never changes.
	private final int HEALTH_BAR_RED_WIDTH = 300;
	private final int HEALTH_BAR_HEIGHT = 30;
	private int x, y = 40;
	private final double HEALTH_BAR_BOX_LOCATION_Y;
	private final double HEALTH_BAR_BOX_LOCATION_X;

	private String playerName;
	private FatalKernel kernel = FatalKernel.getInstance();
	private VanillaCharacter PLAYER_CHARACTER;
	private final int ARC_LENGTH = 20;
	private int PLAYER_NAME_X;
	private int PLAYER_NAME_Y;
	private final int PLAYER_PORTRAIT_X;
	private final int PLAYER_PORTRAIT_Y;

	private Image playerFace;
	private int PLAYER_PORTRAIT_WIDTH;
	private int PLAYER_PORTRAIT_HEIGHT;
	private int HEALTH_BAR_BOX_LOCATION_WIDTH;
	private int HEALTH_BAR_BOX_LOCATION_HEIGHT;
	private int HEALTH_BAR_X;
	private int HEALTH_BAR_Y;

	/**
	 * 
	 * @param playerName name of the player string
	 * @param isLeftSide determines if the healthbar is on the left or right
	 */
	public HealthBar(VanillaCharacter player, boolean isLeftSide) {
		if (isLeftSide)
			this.x = (int) (.036 * kernel.getScreenWidth());
		else 
			this.x = (int) (.67 * kernel.getScreenWidth());
		
		playerName = player.getName();
		
		if (playerName.equals(CharacterType.AyakoTurner+"")){
			playerName = player.getHealthBarDisplayName();
			
			if (isLeftSide)
				playerFace = kernel.loadImage("ayako_portrait_left.gif");
			else
				playerFace = kernel.loadImage("ayako_portrait_right.gif");
		} else if (playerName.equals(CharacterType.MalMartinez+"")){
			playerName = player.getHealthBarDisplayName();
			
			if (isLeftSide)
				playerFace = kernel.loadImage("mal_portrait_left.gif");
			else
				playerFace = kernel.loadImage("mal_portrait_right.gif");
		}
		
		PLAYER_PORTRAIT_X = x;
		PLAYER_PORTRAIT_Y = y - 5;
		PLAYER_PORTRAIT_WIDTH = playerFace.getWidth(null);
		PLAYER_PORTRAIT_HEIGHT = playerFace.getHeight(null);
		HEALTH_BAR_BOX_LOCATION_X = PLAYER_PORTRAIT_X + PLAYER_PORTRAIT_WIDTH - 5;
		HEALTH_BAR_BOX_LOCATION_Y = PLAYER_PORTRAIT_Y + PLAYER_PORTRAIT_HEIGHT / 4;
		HEALTH_BAR_BOX_LOCATION_WIDTH =  HEALTH_BAR_RED_WIDTH + 40;
		HEALTH_BAR_BOX_LOCATION_HEIGHT = 60;
		PLAYER_NAME_X = (int) HEALTH_BAR_BOX_LOCATION_X + 20;
		PLAYER_NAME_Y = (int) HEALTH_BAR_BOX_LOCATION_Y + 20;
		HEALTH_BAR_X = (int) PLAYER_NAME_X;
		HEALTH_BAR_Y = (int) PLAYER_NAME_Y + 5;
		PLAYER_CHARACTER = player;
	}

	/**
	 * Updates the health based off of the condition of the character
	 * @param g graphics context
	 */	
	public void draw(Graphics g) {	
		// grey box behind player name	
		g.setColor(new Color(245, 245, 245));
		g.fillRoundRect((int) HEALTH_BAR_BOX_LOCATION_X, (int) HEALTH_BAR_BOX_LOCATION_Y, (int) HEALTH_BAR_BOX_LOCATION_WIDTH, (int) HEALTH_BAR_BOX_LOCATION_HEIGHT, ARC_LENGTH, ARC_LENGTH);

		/*
		 * Draws the white box behind the player's portrait
		 */
		g.fillRoundRect(PLAYER_PORTRAIT_X - 5, PLAYER_PORTRAIT_Y - 5, PLAYER_PORTRAIT_WIDTH + 10, PLAYER_PORTRAIT_HEIGHT + 10, ARC_LENGTH, ARC_LENGTH);
		g.drawImage(playerFace,x,y-5,null);
		
		// Player Name
		g.setColor(new Color(120, 120, 120));
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		g.drawString(playerName, PLAYER_NAME_X, PLAYER_NAME_Y);

		// health bar draw green on top of red rectangle
		g.setColor(Color.red);
		g.fillRoundRect(HEALTH_BAR_X, HEALTH_BAR_Y, HEALTH_BAR_RED_WIDTH, HEALTH_BAR_HEIGHT, ARC_LENGTH, ARC_LENGTH);
		g.setColor(Color.green);
		g.fillRoundRect(HEALTH_BAR_X, HEALTH_BAR_Y, PLAYER_CHARACTER.getHealth() * 3, HEALTH_BAR_HEIGHT, ARC_LENGTH, ARC_LENGTH);
	}
}