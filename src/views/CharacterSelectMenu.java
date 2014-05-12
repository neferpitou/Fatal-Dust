package views;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import factory.CharacterType;
import interfaces.FatalView;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CharacterSelectMenu extends JPanel implements FatalView,
		KeyListener, MouseListener {
	/**
	 * @author Stuart Kleinman
	 */
	private static final long serialVersionUID = 1L;

	boolean[] isPressed = new boolean[256];

	private Image menu_background = kernel.loadImage("main_menu_image.jpg");

	private int numberOfCharacters = 2;
	private int portraitX;
	private int portraitY;
	private int portraitWidth;
	private int portraitHeight;
	private int totalPortraitWidth;

	private Image malPortrait;
	private Image ayakoPortrait;
	private ArrayList<Image> portraits;

	private int character1X;
	private int character2X;
	private int characterY;
	private int characterWidth;
	private int characterHeight;

	private Image malCharacter;
	private Image ayakoCharacter;

	public int player1Selection = 0;
	public int player2Selection = 1;

	public boolean charactersSelected;

	private Image malStory1;
	private Image malStory2;
	private Image malStory3;
	private Image malStory4;

	private Image ayakoStory1;
	private Image ayakoStory2;
	private Image ayakoStory3;
	private Image ayakoStory4;

	private int storyBoardX;
	private int storyBoardY;
	private int storyBoardWidth;
	private int storyBoardHeight;

	private int currentStoryPiece = 0;

	private String titleText = "CHARACTER SELECT";
	private JLabel title;
	private String continueText = "PRESS ENTER TO CONTINUE";
	private String clickText = "CLICK TO CONTINUE...";

	private String name1Text = "NAME: ";
	private String height1Text = "HEIGHT: ";
	private String weight1Text = "WEIGHT: ";
	private String birth1Text = "BIRTHDATE: ";
	private String birthPlace1Text = "BIRTH PLACE: ";
	private String eye1Text = "EYE COLOR: ";
	private String hair1Text = "HAIR COLOR: ";
	private String blood1Text = "BLOOD TYPE: ";
	private String fight1Text = "FIGHTING STYLE: ";

	private String name2Text = "NAME: ";
	private String height2Text = "HEIGHT: ";
	private String weight2Text = "WEIGHT: ";
	private String birth2Text = "BIRTHDATE: ";
	private String birthPlace2Text = "BIRTH PLACE: ";
	private String eye2Text = "EYE COLOR: ";
	private String hair2Text = "HAIR COLOR: ";
	private String blood2Text = "BLOOD TYPE: ";
	private String fight2Text = "FIGHTING STYLE: ";

	public CharacterSelectMenu() {
		charactersSelected = false;

		// PORTRAIT STUFF
		portraits = new ArrayList<Image>();

		malPortrait = kernel.loadImage("mal_portrait_left.gif");
		ayakoPortrait = kernel.loadImage("ayako_portrait_left.gif");

		portraits.add(ayakoPortrait);
		portraits.add(malPortrait);

		portraitWidth = malPortrait.getWidth(null);
		portraitHeight = malPortrait.getHeight(null);
		totalPortraitWidth = portraitWidth * numberOfCharacters;
		portraitX = kernel.getScreenWidth() / 2 - totalPortraitWidth / 2;
		portraitY = (int) (kernel.getScreenHeight() * 0.8f);

		// CHARACTER STUFF
		malCharacter = kernel.loadImage("MalMartinez/idle/idle_left_0.gif");
		ayakoCharacter = kernel.loadImage("AyakoTurner/idle/idle_left_0.gif");

		characterWidth = malCharacter.getWidth(null) * 5;
		characterHeight = malCharacter.getHeight(null) * 5;
		character1X = (int) (kernel.getScreenWidth() * 0.2f);
		character2X = (int) (kernel.getScreenWidth() * 0.6f);
		characterY = (int) (kernel.getScreenHeight() * 0.3f);

		// STORYBOARD STUFF
		malStory1 = kernel.loadImage("StoryBoardMal/MalFrame1.gif");
		malStory2 = kernel.loadImage("StoryBoardMal/MalFrame2.gif");
		malStory3 = kernel.loadImage("StoryBoardMal/MalFrame3.gif");
		malStory4 = kernel.loadImage("StoryBoardMal/MalFrame4.gif");

		ayakoStory1 = kernel.loadImage("StoryBoardAyako/AyakoFrame1.gif");
		ayakoStory2 = kernel.loadImage("StoryBoardAyako/AyakoFrame2.gif");
		ayakoStory3 = kernel.loadImage("StoryBoardAyako/AyakoFrame3.gif");
		ayakoStory4 = kernel.loadImage("StoryBoardAyako/AyakoFrame4.gif");

		storyBoardX = 0;
		storyBoardY = 0;
		storyBoardWidth = kernel.getScreenWidth();
		storyBoardHeight = kernel.getScreenHeight();

		// LABEL STUFF
		title = new JLabel(titleText);
		title.setForeground(Color.WHITE);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Segoe Script", Font.BOLD, 72));

		kernel.executeEDT(() -> {
			add(title, BorderLayout.NORTH);
		});

		this.addMouseListener(this);
		this.addKeyListener(this);
		kernel.execute(() -> {
			for (int i = 0; i < isPressed.length; i++) {
				isPressed[i] = false;
			}
		});
	}

	@Override
	public void keyPressed(final KeyEvent e) {
		isPressed[e.getKeyCode()] = true;

		if (isPressed[KeyEvent.VK_ESCAPE]) {
			kernel.exit();
		}

		if (isPressed[KeyEvent.VK_ENTER]) {
			this.charactersSelected = true;
			title.setVisible(false);
			setFocusable(false);
			
			kernel.addVersusView(player1Selection, player2Selection);
		}
	}

	@Override
	public void keyReleased(final KeyEvent e) {
		isPressed[e.getKeyCode()] = false;

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void run() {
		requestFocusInWindow();

		if (isPressed[KeyEvent.VK_LEFT]) {
			if (player2Selection > 0) {
				player2Selection--;
			}
		} else if (isPressed[KeyEvent.VK_RIGHT]) {
			if (player2Selection < numberOfCharacters - 1) {
				player2Selection++;
			}
		}

		if (isPressed[KeyEvent.VK_A]) {
			if (player1Selection > 0) {
				player1Selection--;
			}
		} else if (isPressed[KeyEvent.VK_D]) {
			if (player1Selection < numberOfCharacters - 1) {
				player1Selection++;
			}
		}
	}

	@Override
	protected void paintComponent(final Graphics g) {
		this.run();

		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(menu_background, 0, 0, getWidth(), getHeight(), null);

		if (!charactersSelected) {
			// CHARACTER PORTRAITS
			g2d.setColor(Color.WHITE);
			for (int count = 0; count < portraits.size(); count++) {
				g2d.fillRect(portraitX + count * portraitWidth, portraitY,
						portraitWidth, portraitHeight);
				g2d.drawImage(portraits.get(count), portraitX + count
						* portraitWidth, portraitY, portraitWidth,
						portraitHeight, null);
			}

			g2d.setStroke(new BasicStroke(5));

			// PLAYER SELECTION
			if (player2Selection == player1Selection) {
				g2d.setColor(Color.BLUE);
				g2d.drawRoundRect(portraitX + player2Selection * portraitWidth
						+ 5, portraitY + 5, portraitWidth - 10,
						portraitHeight - 10, 5, 5);
			} else {
				g2d.setColor(Color.BLUE);
				g2d.drawRoundRect(portraitX + player2Selection * portraitWidth,
						portraitY, portraitWidth, portraitHeight, 5, 5);
			}

			g2d.setColor(Color.RED);
			g2d.drawRoundRect(portraitX + player1Selection * portraitWidth,
					portraitY, portraitWidth, portraitHeight, 5, 5);

			// CHARACTERS
			Image temp = portraits.get(player1Selection);
			if (temp == malPortrait) {
				g2d.drawImage(malCharacter, character1X, characterY,
						characterWidth, characterHeight, null);
				name1Text = "NAME: " + "Mal Martinez";
				height1Text = "HEIGHT: " + "5'9'' (175 cm)";
				weight1Text = "WEIGHT: " + "159 lbs (72 kg)";
				birth1Text = "BIRTHDATE: " + "February 14, 1965";
				birthPlace1Text = "BIRTH PLACE: "
						+ "United States, United States of America";
				eye1Text = "EYE COLOR: " + "Brown";
				hair1Text = "HAIR COLOR: " + "Blonde";
				blood1Text = "BLOOD TYPE: " + "B";
				fight1Text = "FIGHTING STYLE: "
						+ "Martial art rooted in Ansatsuken";
			} else if (temp == ayakoPortrait) {
				g2d.drawImage(ayakoCharacter, character1X, characterY,
						characterWidth, characterHeight, null);
				name1Text = "NAME: " + "Ayako Turner";
				height1Text = "HEIGHT: " + "5'7'' (169 cm)";
				weight1Text = "WEIGHT: " + "Secret";
				birth1Text = "BIRTHDATE: " + "March 1, 1968";
				birthPlace1Text = "BIRTH PLACE: " + "China, China";
				eye1Text = "EYE COLOR: " + "Brown";
				hair1Text = "HAIR COLOR: " + "Dark Brown";
				blood1Text = "BLOOD TYPE: " + "A";
				fight1Text = "FIGHTING STYLE: "
						+ "Unnamed chinese martial arts, Tai Chi";
			}

			temp = portraits.get(player2Selection);
			if (temp == malPortrait) {
				g2d.drawImage(malCharacter, character2X, characterY,
						characterWidth, characterHeight, null);
				name2Text = "NAME: " + "Mal Martinez";
				height2Text = "HEIGHT: " + "5'9'' (175 cm)";
				weight2Text = "WEIGHT: " + "159 lbs (72 kg)";
				birth2Text = "BIRTHDATE: " + "February 14, 1965";
				birthPlace2Text = "BIRTH PLACE: "
						+ "United States, United States of America";
				eye2Text = "EYE COLOR: " + "Brown";
				hair2Text = "HAIR COLOR: " + "Blonde";
				blood2Text = "BLOOD TYPE: " + "B";
				fight2Text = "FIGHTING STYLE: "
						+ "Martial art rooted in Ansatsuken";
			} else if (temp == ayakoPortrait) {
				g2d.drawImage(ayakoCharacter, character2X, characterY,
						characterWidth, characterHeight, null);
				name2Text = "NAME: " + "Ayako Turner";
				height2Text = "HEIGHT: " + "5'7'' (169 cm)";
				weight2Text = "WEIGHT: " + "Secret";
				birth2Text = "BIRTHDATE: " + "March 1, 1968";
				birthPlace2Text = "BIRTH PLACE: " + "China, China";
				eye2Text = "EYE COLOR: " + "Brown";
				hair2Text = "HAIR COLOR: " + "Dark Brown";
				blood2Text = "BLOOD TYPE: " + "A";
				fight2Text = "FIGHTING STYLE: "
						+ "Unnamed chinese martial arts, Tai Chi";
			}

			// TEXT STUFF
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
			int distanceBetweenInfo = 50;
			g2d.drawString(name1Text, character1X + characterWidth, characterY
					+ distanceBetweenInfo * 0);
			g2d.drawString(height1Text, character1X + characterWidth,
					characterY + distanceBetweenInfo * 1);
			g2d.drawString(weight1Text, character1X + characterWidth,
					characterY + distanceBetweenInfo * 2);
			g2d.drawString(birth1Text, character1X + characterWidth, characterY
					+ distanceBetweenInfo * 3);
			g2d.drawString(birthPlace1Text, character1X + characterWidth,
					characterY + distanceBetweenInfo * 4);
			g2d.drawString(eye1Text, character1X + characterWidth, characterY
					+ distanceBetweenInfo * 5);
			g2d.drawString(hair1Text, character1X + characterWidth, characterY
					+ distanceBetweenInfo * 6);
			g2d.drawString(blood1Text, character1X + characterWidth, characterY
					+ distanceBetweenInfo * 7);
			g2d.drawString(fight1Text, character1X + characterWidth, characterY
					+ distanceBetweenInfo * 8);

			g2d.drawString(name2Text, character2X + characterWidth, characterY
					+ distanceBetweenInfo * 0);
			g2d.drawString(height2Text, character2X + characterWidth,
					characterY + distanceBetweenInfo * 1);
			g2d.drawString(weight2Text, character2X + characterWidth,
					characterY + distanceBetweenInfo * 2);
			g2d.drawString(birth2Text, character2X + characterWidth, characterY
					+ distanceBetweenInfo * 3);
			g2d.drawString(birthPlace2Text, character2X + characterWidth,
					characterY + distanceBetweenInfo * 4);
			g2d.drawString(eye2Text, character2X + characterWidth, characterY
					+ distanceBetweenInfo * 5);
			g2d.drawString(hair2Text, character2X + characterWidth, characterY
					+ distanceBetweenInfo * 6);
			g2d.drawString(blood2Text, character2X + characterWidth, characterY
					+ distanceBetweenInfo * 7);
			g2d.drawString(fight2Text, character2X + characterWidth, characterY
					+ distanceBetweenInfo * 8);

			g2d.drawString(continueText, kernel.getScreenWidth() * 0.8f,
					kernel.getScreenHeight() * 0.95f);
		} else // DISPLAYS STORY BOARD PIECES
		{
			if (player1Selection == CharacterType.AyakoTurner.ordinal()) {
				switch (currentStoryPiece) {
				case 0:
					g2d.drawImage(ayakoStory1, storyBoardX, storyBoardY,
							storyBoardWidth, storyBoardHeight, null);
					break;
				case 1:
					g2d.drawImage(ayakoStory2, storyBoardX, storyBoardY,
							storyBoardWidth, storyBoardHeight, null);
					break;
				case 2:
					g2d.drawImage(ayakoStory3, storyBoardX, storyBoardY,
							storyBoardWidth, storyBoardHeight, null);
					break;
				case 3:
					g2d.drawImage(ayakoStory4, storyBoardX, storyBoardY,
							storyBoardWidth, storyBoardHeight, null);
					break;
				default:
					g2d.drawImage(ayakoStory4, storyBoardX, storyBoardY,
							storyBoardWidth, storyBoardHeight, null);
					kernel.redrawScreen(kernel.getView("SPLASH"),
							kernel.getView("VERSUS"));
					break;
				}
			} else if (player1Selection == CharacterType.MalMartinez.ordinal()) {
				switch (currentStoryPiece) {
				case 0:
					g2d.drawImage(malStory1, storyBoardX, storyBoardY,
							storyBoardWidth, storyBoardHeight, null);
					break;
				case 1:
					g2d.drawImage(malStory2, storyBoardX, storyBoardY,
							storyBoardWidth, storyBoardHeight, null);
					break;
				case 2:
					g2d.drawImage(malStory3, storyBoardX, storyBoardY,
							storyBoardWidth, storyBoardHeight, null);
					break;
				case 3:
					g2d.drawImage(malStory4, storyBoardX, storyBoardY,
							storyBoardWidth, storyBoardHeight, null);
					break;
				default:
					g2d.drawImage(malStory4, storyBoardX, storyBoardY,
							storyBoardWidth, storyBoardHeight, null);
					kernel.redrawScreen(kernel.getView("SPLASH"),
							kernel.getView("VERSUS"));
					break;
				}
			}

			g.setColor(Color.BLUE);
			g.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
			g2d.drawString(clickText, kernel.getScreenWidth() * 0.7f,
					kernel.getScreenHeight() * 0.95f);
		}

		// Needed to update the screen
		kernel.executeEDT(() -> {
			this.repaint();
		});

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

		if (charactersSelected) {
			if (currentStoryPiece < 3) {
				currentStoryPiece++;
			} else {
				kernel.redrawScreen(kernel.getView("SELECT"),
						kernel.getView("VERSUS"));
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	public boolean startThreads(){
		kernel.playBGM("26 Rock It On (D.S. Remix).mp3");
		return true;
	}
	
	public boolean stopThreads(){
		kernel.stopBGM();
		return true;
	}
}
